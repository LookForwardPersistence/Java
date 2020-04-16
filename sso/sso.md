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
