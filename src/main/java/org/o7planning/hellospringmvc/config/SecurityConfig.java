package org.o7planning.hellospringmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{		
	@Autowired
	private SuccessHandler successHandler;
	@Autowired
	public void configGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("user1").password("123456").roles("USER");
		auth.inMemoryAuthentication().withUser("admin1").password("123456").roles("ADMIN");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception{		
		http.authorizeRequests()
		.antMatchers("/","home").permitAll()
		.antMatchers("/admin/**").access("hasRole('ADMIN')")
		.and().formLogin().successHandler(successHandler)
		.and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}	
}
