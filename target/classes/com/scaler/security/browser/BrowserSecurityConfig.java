package com.scaler.security.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.scaler.security.handler.MyAuthenticationFailureHandler;
import com.scaler.security.handler.MyAuthenticationSucessHandler;
import com.scaler.security.handler.MyAccessDenyFilter;
import com.scaler.security.handler.MyAuthenticationEntryPoint;
import com.scaler.security.browser.LoginAuthenticationProvider;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyAuthenticationSucessHandler authenticationSucessHandler;

	@Autowired
	private MyAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private MyAccessDenyFilter myAccessDenyFilter;
	
	@Autowired
	private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

	@Autowired
	private DataSource dataSource;

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		jdbcTokenRepository.setCreateTableOnStartup(false);
		return jdbcTokenRepository;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin() // 表单登录
		
				// http.httpBasic() // HTTP Basic
				.loginPage("/login.html") // 登录跳转 URL
				.loginProcessingUrl("/login") // 处理表单登录 URL
				.successHandler(authenticationSucessHandler) // 处理登录成功
				.failureHandler(authenticationFailureHandler) // 处理登录失败
				
				.and().logout().logoutUrl("/logout") // 登出
				.logoutSuccessUrl("/index.html") // 登出成功后跳转
				
				.and().rememberMe().tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
				.tokenValiditySeconds(30 * 12 * 60 * 60) // remember 过期时间，单为秒
				.userDetailsService(userDetailService) // 处理自动登录逻辑
				
				.and()
				.exceptionHandling()
				.accessDeniedHandler(myAccessDenyFilter) // 处理当前帐号无权限时
				
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(myAuthenticationEntryPoint) // 处理未登陆时
				
				.and().authorizeRequests() // 授权配置
				.antMatchers("/", "/index.html", "/login.html", "/img/*", "/css/*", "/js/*").permitAll() // 无需认证
				.anyRequest() // 所有请求
				.authenticated() // 都需要认证
				
				.and().csrf().disable();
	}
}
