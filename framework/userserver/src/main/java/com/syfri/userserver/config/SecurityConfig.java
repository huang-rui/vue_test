package com.syfri.userserver.config;

import com.syfri.userserver.service.CustomUserDetailsService;
import com.syfri.userserver.service.LoginSuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//@Configuration
@EnableConfigurationProperties(SecuritySettings.class)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private SecuritySettings settings;
	@Autowired @Qualifier("baseDataSource")
	private DataSource dataSource;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		//remember me
		auth.eraseCredentials(false);
	}
	/**
	 * rememberMe().tokenValiditySeconds(86400).tokenRepository(tokenRepository())
	 * 用来记住用户登录状态的一个配置，其中86400指定记录的时间秒数，即为一天的时间
	 * @author li.xue  2017/11/29
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").permitAll().successHandler(loginSuccessHandler())
				.and().authorizeRequests()
				.antMatchers("/images/**", "/checkcode", "/js/**", "/css/**").permitAll()
				.antMatchers(settings.getPermitall().split(",")).permitAll()
				.anyRequest().authenticated()
				.and().csrf().requireCsrfProtectionMatcher(csrfSecurityRequestMatcher())
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and().logout().logoutSuccessUrl(settings.getLogoutsuccessurl())
				.and().exceptionHandling().accessDeniedPage(settings.getDeniedpage())
				.and().rememberMe().tokenValiditySeconds(86400).tokenRepository(tokenRepository());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JdbcTokenRepositoryImpl tokenRepository(){
		JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
		jtr.setDataSource(dataSource);
		return jtr;
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandler(){
		return new LoginSuccessHandler();
	}

	@Bean
	public CustomFilterSecurityInterceptor customFilter() throws Exception{
		CustomFilterSecurityInterceptor customFilter = new CustomFilterSecurityInterceptor();

		/*权限管理决断器.*/
		customFilter.setSecurityMetadataSource(securityMetadataSource());

		/*权限配置资源管理器.*/
		customFilter.setAccessDecisionManager(accessDecisionManager());
		customFilter.setAuthenticationManager(authenticationManager);
		return customFilter;
	}

	@Bean
	public CustomAccessDecisionManager accessDecisionManager() {
		return new CustomAccessDecisionManager();
	}

	@Bean
	public CustomSecurityMetadataSource securityMetadataSource() {
		return new CustomSecurityMetadataSource(settings.getUrlroles());
	}

	/**
	 * csrf跨域请求伪造
	 * @author  li.xue  2017/11/29
	 */
	private CsrfSecurityRequestMatcher csrfSecurityRequestMatcher(){
		CsrfSecurityRequestMatcher csrfSecurityRequestMatcher = new CsrfSecurityRequestMatcher();
		List<String> list = new ArrayList<String>();
		list.add("/rest/");
		csrfSecurityRequestMatcher.setExecludeUrls(list);
		return csrfSecurityRequestMatcher;
	}
}
