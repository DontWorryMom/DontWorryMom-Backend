package com.backend.Configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.security.core.Authentication;
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
				.successHandler(new AppAuthenticationSuccessHandler())
				.loginProcessingUrl("/login")
				.and()
				
			.logout()
				.logoutUrl("/logout")
				.and()
			
			.cors()
				.and()

			.authorizeRequests()
				// login and logout urls
				.antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll()

				// users endpoints
				.antMatchers(HttpMethod.POST, "/users").permitAll()						// anyone can create an account
				.antMatchers(HttpMethod.GET, "/users").hasAnyAuthority("admin")			// only admins can see the whole list of users
				.antMatchers(HttpMethod.GET, "/users/currentUser").authenticated()		// only signed in users can see their details
				.antMatchers(HttpMethod.GET, "/users/userId/*").authenticated()			// only signed in users can see their details
				.antMatchers(HttpMethod.PUT, "/users/userId/*").authenticated()			// only signed in users can update their details
				.antMatchers(HttpMethod.DELETE, "/users/userId/*").authenticated()		// only signed in users can delete their details

				// devices endpoints
				.antMatchers(HttpMethod.POST, "/devices").authenticated()				// only signed in users can add a device
				.antMatchers(HttpMethod.GET, "/devices").hasAnyAuthority("admin")		// only admins can see the whole list of devices
				.antMatchers(HttpMethod.GET, "/devices/deviceId/*").authenticated()		// only signed in users can see their devices
				.antMatchers(HttpMethod.GET, "/devices/userId/*").authenticated()		// only signed in users can see their devices
				.antMatchers(HttpMethod.PUT, "/devices/deviceId/*").authenticated()		// only signed in users can update their devices
				.antMatchers(HttpMethod.DELETE, "/devices/deviceId/*").authenticated()	// only signed in users can delete their devices

				// notifications endpoints
				.antMatchers(HttpMethod.POST, "/notifications/userId/*").authenticated()			// only signed in users can add a notification
				.antMatchers(HttpMethod.GET, "/notifications").hasAnyAuthority("admin")				// only admins can see the whole list of notifications
				.antMatchers(HttpMethod.GET, "/notifications/userId/*").authenticated()				// only signed in users can see their notifications
				.antMatchers(HttpMethod.GET, "/notifications/notificationId/*").authenticated()		// only signed in users can see their notifications

				// locations endpoints
				.antMatchers(HttpMethod.POST, "/locations/deviceId/*").permitAll()		// devices cannot sign in to send data
				.antMatchers(HttpMethod.GET, "/locations").hasAnyAuthority("admin")		// only admins can see the whole list of locations
				.antMatchers(HttpMethod.GET, "/locations/deviceId/*").authenticated()	// only signed in users can see their locations

				// CrashDetectedNotifications endpoints
				.antMatchers(HttpMethod.GET, "/crashDetectedNotification").hasAnyAuthority("admin")	// only admins can see the whole list of crash detected notifications

				// DeviceNotificationMethods endpoints
				.antMatchers(HttpMethod.POST, "/deviceNotificationMethods").authenticated()					// only logged in users can add device notification methods
				.antMatchers(HttpMethod.GET, "/deviceNotificationMethods").hasAnyAuthority("admin")			// only admins can see the whole list of Device Notification Methods
				.antMatchers(HttpMethod.GET, "/deviceNotificationMethods/deviceId/*").authenticated()		// only signed in users can see their device's notification methods
				.antMatchers(HttpMethod.DELETE, "/deviceNotificationMethods").authenticated()				// only logged in users can delete device notification methods

				// remaining requests (just block them in case we forgot anything)
				.anyRequest().hasAnyAuthority("admin")
				.and()
			.csrf()
				.disable();
	}

	public class AppAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
		protected void handle(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
		}
	}

}
