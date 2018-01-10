package com.syfri.userservice.config;

import com.syfri.userservice.filter.MyFormAuthenticationFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShiroConfig {

	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

	/**
	 * 会话管理
	 */
	@Bean("sessionManager")
	public SessionManager sessionManager(){
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(false);
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		//设置session失效时间30min
		sessionManager.setGlobalSessionTimeout(30*60*1000);
		return sessionManager;
	}

	/**
	 * Shiro缓存
	 */
	@Bean("ehCacheManager")
	public EhCacheManager ehCacheManager(){
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
		return ehCacheManager;
	}

	/**
	 * 安全管理
	 */
	@Bean("securityManager")
	public SecurityManager securityManager(SessionManager sessionManager){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setSessionManager(sessionManager);
		securityManager.setCacheManager(ehCacheManager());
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		logger.info("【shiro】shiroFilter start...");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		//将验证码的过滤器加入到shiroFilter中
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		filters.put("authc", new MyFormAuthenticationFilter());

		shiroFilterFactoryBean.setSecurityManager(securityManager);

		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		//未授权页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		//拦截器
		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		//不进行权限认证的静态资源
		filterMap.put("/static/**", "anon");
		filterMap.put("/webjars/**", "anon");
		filterMap.put("/druid/**", "anon");
		filterMap.put("/**/*.css", "anon");
		filterMap.put("/**/*.js", "anon");
		filterMap.put("/**/*.html", "anon");
		filterMap.put("/fonts/**", "anon");
		filterMap.put("/favicon.ico", "anon");
		filterMap.put("/*.jpg", "anon");

		//swagger
		filterMap.put("/swagger/**", "anon");
		//swagger api json
		filterMap.put("/v2/api-docs", "anon");
		//swagger 安全选项
		//filterMap.put("/swagger-resources/configuration/security", "anon");
		//用来获取支持的动作，如果不加入这个，显示swagger空白页面
		filterMap.put("/swagger-resources/configuration/ui", "anon");
		//用来获取api-docs的URI
		filterMap.put("/swagger-resources", "anon");
		filterMap.put("/swagger-ui.html", "anon");

		//验证码可以不经授权访问
		filterMap.put("/imageCode", "anon");

		//anon:所有URL均可以匿名访问，authc：需要认证才能访问，user：配置记住我或认证通过可以访问
		filterMap.put("/login", "authc");
		filterMap.put("/**", "user");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 自定义Realm
	 */
	@Bean("myShiroRealm")
	public MyShiroRealm myShiroRealm(){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	/**
	 * 采用MD5密码加密
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		hashedCredentialsMatcher.setHashIterations(1);
		//设置编码方式为Hex，默认编码方式为Base64
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		return hashedCredentialsMatcher;
	}

	/**
	 * rememberMe Cookie
	 */
	@Bean
	public SimpleCookie rememberMeCookie(){
		//此rememberMe对应的是前台页面的checkbox的name的rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		//设置Cookie的过期时间为7天
		simpleCookie.isHttpOnly();
		simpleCookie.setMaxAge(7*24*60*60);
		return simpleCookie;
	}

	/**
	 * rememberMe管理器
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(){
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

	/**
	 * 开启shiro aop注解支持
	 * */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 管理ShiroBean生命周期
	 */
	@Bean("lifecycleBeanPostProcess")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 代理所有的Advisor
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	/**
	 * 异常的捕获
	 */
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		Properties properties = new Properties();
		properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "redirect:/403");
		properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "redirect:/403");
//		properties.setProperty("java.lang.IllegalArgumentException", "redirect:/error");
//		properties.setProperty("java.lang.Exception", "redirect:/error");
		resolver.setExceptionMappings(properties);
		return resolver;
	}

}
