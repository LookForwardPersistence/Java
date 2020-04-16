~~~
public class LocationCustomCasRealm extends CasRealm implements RoleHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationCustomCasRealm.class);
    @Inject
    private ShiroFilterChainManager shiroFilterChainManager;
    @Inject
    private PrincipalsAssembler principalsAssembler;

    public LocationCustomCasRealm() {
    }

    public PrincipalsAssembler getPrincipalsAssembler() {
        return this.principalsAssembler;
    }

    public void setPrincipalsAssembler(PrincipalsAssembler principalsAssembler) {
        this.principalsAssembler = principalsAssembler;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CasToken casToken = (CasToken)token;
        if(token == null) {
            return null;
        } else {
            String ticket = (String)casToken.getCredentials();
            if(!StringUtils.hasText(ticket)) {
                return null;
            } else {
                TicketValidator ticketValidator = this.ensureTicketValidator();

                try {
                    Assertion casAssertion = ticketValidator.validate(ticket, this.getCasService());
                    AttributePrincipal casPrincipal = casAssertion.getPrincipal();
                    String userId = casPrincipal.getAttributes().get("loginname")==null?casPrincipal.getAttributes().get("uid").toString():casPrincipal.getAttributes().get("loginname").toString();
                    LOGGER.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{ticket, this.getCasServerUrlPrefix(), userId});
                    Map<String, Object> attributes = casPrincipal.getAttributes();
                    casToken.setUserId(userId);
                    String rememberMeAttributeName = this.getRememberMeAttributeName();
                    String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
                    boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
                    if(isRemembered) {
                        casToken.setRememberMe(true);
                    }

                    Collection principals = this.principalsAssembler.createPrincipalCollection(userId);
                    SimpleAuthenticationInfo result = new SimpleAuthenticationInfo(principals, ticket, this.getName());
                    return result;
                } catch (TicketValidationException var14) {
                    throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", var14);
                }
            }
        }
    }

    private void settingShiroUserTelePhone(UserDTO user, ShiroUser shiroUser) {
        if(org.apache.commons.lang3.StringUtils.isBlank(user.getTelePhone())) {
            shiroUser.setTelePhone("您还没有联系电话，请添加电话！");
        } else {
            shiroUser.setTelePhone(user.getTelePhone());
        }

    }

    private void settingShiroUserEmail(UserDTO user, ShiroUser shiroUser) {
        if(org.apache.commons.lang3.StringUtils.isBlank(user.getEmail())) {
            shiroUser.setEmail("您还没有邮箱，请添加邮箱！");
        } else {
            shiroUser.setEmail(user.getEmail());
        }

    }

    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    protected void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        this.getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        this.getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        this.clearAllCachedAuthenticationInfo();
        this.clearAllCachedAuthorizationInfo();
    }

    public void switchOverRoleOfUser(String roleName) {
        PrincipalCollection principalCollection = CurrentUser.getPrincipals();
        ShiroUser shiroUser = (ShiroUser)principalCollection.getPrimaryPrincipal();
        shiroUser.setRoleName(roleName);
        this.doGetAuthorizationInfo(principalCollection);
    }

    public void resetRoleName(String name) {
        this.switchOverRoleOfUser(name);
        this.shiroFilterChainManager.initFilterChain();
    }

    public String getRoleNameByUserAccount(String userAccount) {
        return "暂未分配角色";
    }
}
~~~

~~~
public class LocationShiroPrincipalsAssembler implements PrincipalsAssembler {

    @Inject
    private EmployeeAccessFacade employeeAccessFacade;
    @Inject
    private SsoAccessFacade ssoAccessFacade;
    @Inject
    private UserAccessFacade userAccessFacade;


    public LocationShiroPrincipalsAssembler() {
    }

    @Override
    public Collection createPrincipalCollection(String userAccount) {
        UserWithOrgDTO user = this.userAccessFacade.findUserByAccount(userAccount);
        // 重构支持现场员访问
        if(user==null){
            EmployeeDTO employeeDTO = employeeAccessFacade.getEmployeeByEmployeeCode(userAccount);
            user = new UserWithOrgDTO(employeeDTO.getId(),employeeDTO.getName(),employeeDTO.getEmpCode(),employeeDTO.getEmpCode(),employeeDTO.getEmail());
        }
        String userType = "0";


        if("0".equals(userType)) {
            ShiroUser shiroUser = this.getShiroUser(user.getId(), user.getUserAccount(), user.getName(), userType, this.getRoleNameByUserAccount(userAccount), user.getEmail());
            return CollectionUtils.asList(new Object[]{shiroUser});
        } else if("1".equals(userType)) {
            ShiroVendor shiroVendor = this.getShiroVendor(user.getId(), user.getUserAccount(), user.getName(), userType, user.getUserCode(), this.getRoleNameByUserAccount(userAccount));
            return CollectionUtils.asList(new Object[]{shiroVendor});
        } else {
            return null;
        }
    }

    public ShiroUser getShiroUser(String userId, String userAccount, String userName, String userType, String roleName, String email) {
        ShiroUser shiroUser = null;
        EmployeeDTO employee = this.getEmployee(userAccount);
        String companyCode = employee.getCompanyCode();
        if(!StringUtils.isEmpty(companyCode)) {
            CompanyDTO company = this.ssoAccessFacade.getCompanyByCompanycode(companyCode);
            shiroUser = new ShiroUser(userId, userAccount, userName, employee.getEmpCode(), employee.getOrganizationId(), employee.getOrganizationName(), roleName, company.getId(), company.getCode(), company.getName(), userType, employee.getCompanyCode(), email, employee.getCompanySubCode());
        } else {
            shiroUser = new ShiroUser(userId, userAccount, userName, employee.getEmpCode(), employee.getOrganizationId(), employee.getOrganizationName(), roleName, userType, employee.getCompanyCode(), email, employee.getCompanySubCode());
        }

        return shiroUser;
    }

    public ShiroVendor getShiroVendor(String userId, String userAccount, String userName, String userType, String userCode, String roleName) {
        ShiroVendor shiroVendor = new ShiroVendor(userId, userAccount, userName, userCode, roleName, userType);
        return shiroVendor;
    }

    public EmployeeDTO getEmployee(String userAccount) {
        EmployeeDTO employeeDTO = this.employeeAccessFacade.getEmployeeByAccount(userAccount);
        EmployeeDTO result = employeeDTO==null?employeeAccessFacade.getEmployeeByEmployeeCode(userAccount):employeeDTO;
        return result;
    }

    public String getRoleNameByUserAccount(String userAccount) {
        return "暂未分配角色";
    }
}
~~~


~~~
/**
 * 针对cas未授权统一返回401
 */
public class LoginInterceptor extends CustomCasUserFilter {
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            boolean isAuthenticated = SecurityUtils.getSubject().isAuthenticated();
            if(isAuthenticated){
                super.doFilterInternal(request, response, chain);
            }else {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                String token = httpServletRequest.getParameter("token");
                String sessionId = httpServletRequest.getParameter("sessionId");
                if(!token.isEmpty()) {
                    String casLoginUrl = this.getCasLoginUrl();
                    String ticket = HttpUtils.getTicketBySSOToken(casLoginUrl, token, null);
                    SecurityUtils.getSubject().login(new CasToken(ticket));
                    // 封装请求，防止字节流只能读取一次
                    ShiroHttpServletRequestWrapper wrapper = new ShiroHttpServletRequestWrapper(httpServletRequest);
                    if(CurrentUser.getPrincipals()!=null){
                        chain.doFilter(wrapper,response);
                    }else {
                        ((HttpServletResponse) response).setHeader("Content-type", "text/html;charset=UTF-8");
                        response.getWriter().write(new Gson().toJson(InvokeResult.failure("401")));
                        logger.error("授权失败");
                    }
                }else {
                    super.doFilterInternal(request,response,chain);
                }
            }
        }catch (Exception ex){
            ((HttpServletResponse) response).setHeader("Content-type", "text/html;charset=UTF-8");
           if(ex.getMessage()!=null&&ex.getMessage().contains("Authentication")){
               response.getWriter().write(new Gson().toJson(InvokeResult.failure("401")));
           }else {
               response.getWriter().write(new Gson().toJson(InvokeResult.failure(ex.getMessage())));
           }
        }
    }

}
~~~
