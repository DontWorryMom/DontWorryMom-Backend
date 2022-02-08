package com.backend.Configuration;

import com.backend.services.AuthenticationService.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	AuthenticationService authenticationService;

	@Autowired
	WebSecurityConfig(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(authenticationService);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		authProvider.setPasswordEncoder(encoder);
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.loginProcessingUrl("/login")
				.and()
				
			.logout()
				.logoutUrl("/logout")
				.and()

			.authorizeRequests()
				// login and logout urls
				.antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll()

				// users endpoints
				.antMatchers(HttpMethod.POST, "/users").permitAll()						// anyone can create an account
				.antMatchers(HttpMethod.GET, "/users").hasAnyAuthority("admin")			// only admins can see the whole list of users
				.antMatchers(HttpMethod.GET, "/users/userId/*").authenticated()			// only signed in users can see their details
				.antMatchers(HttpMethod.PUT, "/users/userId/*").authenticated()			// only signed in users can update their details
				.antMatchers(HttpMethod.DELETE, "/users/userId/*").authenticated()		// only signed in users can delete their details

				// remaining requests (just secure them in case we forgot anything)
				.anyRequest().permitAll()
				.and()
			.csrf()
				.disable();
	}
	
}
