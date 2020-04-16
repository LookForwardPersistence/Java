

~~~
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfiguration {

    @Autowired
    ShiroProperties shiroProperties;


    @Bean
    public RedisManager redisManager() {
        //RedisManager内的属性springboot会自动注入
        return new RedisManager();
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setGlobalSessionTimeout(shiroProperties.getSessionTimeout());
        sessionManager.setSessionValidationInterval(300000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    @Bean
    public LocationCustomCasRealm customCasRealm() {
        LocationCustomCasRealm customCasRealm = new LocationCustomCasRealm();
        customCasRealm.setCasService(shiroProperties.getAppUrl() + "/cas");
        customCasRealm.setCasServerUrlPrefix(shiroProperties.getCasUrl());
        return customCasRealm;
    }

    @Bean
    public CommonLdapRealm commonLdapRealm() {
        CommonLdapRealm commonLdapRealm = new CommonLdapRealm();
        commonLdapRealm.setContextFactory(jndiLdapContextFactory());
        commonLdapRealm.setCachingEnabled(false);
        commonLdapRealm.setAuthenticationCachingEnabled(false);
        commonLdapRealm.setAuthorizationCachingEnabled(false);
        return commonLdapRealm;
    }

    @Bean
    public JndiLdapContextFactory jndiLdapContextFactory() {
        JndiLdapContextFactory jndiLdapContextFactory = new JndiLdapContextFactory();
        jndiLdapContextFactory.setUrl(shiroProperties.getLdapUrl());
        jndiLdapContextFactory.setSystemUsername(shiroProperties.getLdapSystemUsername());
        jndiLdapContextFactory.setSystemPassword(shiroProperties.getLdapSystemPassword());
        return jndiLdapContextFactory;
    }

    @Bean
    public CasSubjectFactory casSubjectFactory() {
        return new CasSubjectFactory();
    }

    @Bean
    public LocationShiroPrincipalsAssembler shiroPrincipalsAssembler() {
        return  new LocationShiroPrincipalsAssembler();
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setCacheManager(redisCacheManager());
        defaultWebSecurityManager.setSubjectFactory(casSubjectFactory());
        Collection<Realm> realms = new ArrayList<>();
        // 是否开启CAS集成验证
        if (shiroProperties.getOpenCas()) {
            realms.add(customCasRealm());
        }
        // 是否开启LDAP验证
        if (shiroProperties.getOpenLdap()) {
            realms.add(commonLdapRealm());
        }
        defaultWebSecurityManager.setRealms(realms);
        return defaultWebSecurityManager;
    }

    /**
     * filter不能用@bean初始化，不然会被自动注入到过滤链，导致集成失败
     * @return
     */
    public CustomFormAuthenticationFilter customFormAuthenticationFilter() {
        CustomFormAuthenticationFilter customFormAuthenticationFilter = new CustomFormAuthenticationFilter();
        customFormAuthenticationFilter.setUsernameParam("username");
        customFormAuthenticationFilter.setPasswordParam("password");
        customFormAuthenticationFilter.setRememberMeParam("rememberMe");
        customFormAuthenticationFilter.setFailureKeyAttribute("shiroLoginFailure");
        customFormAuthenticationFilter.setLoginUrl("/login");

        customFormAuthenticationFilter.setSuccessExcludeGotoUrls(new String[]{"/login"});
        return customFormAuthenticationFilter;
    }

    public CasFilter casFilter() {
        CasFilter casFilter = new CasFilter();
        casFilter.setFailureUrl("/unauthorized");
        return casFilter;
    }


    public CustomCasLogoutFilter customCasLogoutFilter() {
        CustomCasLogoutFilter customCasLogoutFilter = new CustomCasLogoutFilter();
        customCasLogoutFilter.setCasRedirectUrl(shiroProperties.getCasUrl() + "/logout?service=" + shiroProperties.getAppUrl());
        customCasLogoutFilter.setCasServiceBasePath(shiroProperties.getCasUrl());
        customCasLogoutFilter.setRedirectUrl(shiroProperties.getCasUrl() + "/logout?service=" + shiroProperties.getAppUrl() + "/cas");
        return customCasLogoutFilter;
    }

    public CustomCasUserFilter customCasUserFilter() {
//        CustomCasUserFilter customCasUserFilter = new CustomCasUserFilter();
        LoginInterceptor customCasUserFilter = new LoginInterceptor();
        customCasUserFilter.setCasLoginUrl(shiroProperties.getCasUrl() + "/login?service=" + shiroProperties.getAppUrl() + "/cas");
        customCasUserFilter.setLoginUrl("/login");
        customCasUserFilter.setCasServiceBasePath(shiroProperties.getAppUrl());
        customCasUserFilter.setOriginAddress(shiroProperties.getOriginAddress());
        customCasUserFilter.setWebCors(shiroProperties.getWebCors());
        return customCasUserFilter;
    }

    public AnyRoleAuthorizationFilter anyRoleAuthorizationFilter() {
        return new AnyRoleAuthorizationFilter();
    }

    /**
     * 格式化chain字符串到map，在yml中的chain串不能直接被shiro解析
     * @param chain
     * @param excludeFilters
     * @return
     */
    private Map<String, String> formatChainToMap(String chain, List<String>  excludeFilters) {
        Map<String, String> map = new LinkedHashMap<>();
        if(StringUtils.isNotEmpty(chain)) {
            for (String str : chain.split(" ")) {
                String[] strArr = str.trim().split("=");
                // 排除不要的filter
                if(!excludeFilters.contains(strArr[1])) {
                    map.put(strArr[0], strArr[1]);
                }
            }
        }
        return map;
    }

    @Bean("filterChainManager")
    public CustomDefaultFilterChainManager customDefaultFilterChainManager() {
        CustomDefaultFilterChainManager customDefaultFilterChainManager = new CustomDefaultFilterChainManager();
        customDefaultFilterChainManager.setUnauthorizedUrl("/unauthorized");
        // 定义filter列表
        Map<String, Filter> customFilters = new LinkedHashMap<>();
        // 排除过滤链列表
        List<String> excludeFilters = new ArrayList<>();
        if(shiroProperties.getOpenCas()) {
            customFilters.put("user", customCasUserFilter());
            customFilters.put("cas", casFilter());
            customFilters.put("logout", customCasLogoutFilter());
        } else if(shiroProperties.getOpenLdap()) {
            customFilters.put("user", customFormAuthenticationFilter());
            excludeFilters.add("cas");
            excludeFilters.add("logout");
        } else {
            customFilters.put("user", anyRoleAuthorizationFilter());
            excludeFilters.add("cas");
            excludeFilters.add("logout");
        }
        customFilters.put("anyRole", anyRoleAuthorizationFilter());
        customDefaultFilterChainManager.setCustomFilters(customFilters);
        // 配置过滤路径
        customDefaultFilterChainManager.setFilterChainDefinitionMap(formatChainToMap(shiroProperties.getChainDefinitions(), excludeFilters));
        return customDefaultFilterChainManager;
    }

    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        CustomDefaultFilterChainManager customDefaultFilterChainManager = customDefaultFilterChainManager();
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        shiroFilterFactoryBean.setFilters(customDefaultFilterChainManager.getFilters());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(customDefaultFilterChainManager.getFilterChainDefinitionMap());
        shiroFilterFactoryBean.setUnauthorizedUrl(customDefaultFilterChainManager.getUnauthorizedUrl());
        return shiroFilterFactoryBean;
    }

    /**
     * 加入注解
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager());
        return advisor;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() throws Exception {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter((Filter) shiroFilterFactoryBean().getObject());
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(1);
        return filterRegistration;
    }

    /**
     * 跨域支持
     * @return
     */
    @Bean
    @ConditionalOnProperty("shiro.webCors")
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
~~~
