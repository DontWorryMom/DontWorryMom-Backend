package com.backend.services.AuthenticationService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.Configuration.Config;
import com.backend.DataAcquisitionObjects.UserDAO;
import com.backend.Models.User;
import com.backend.Models.UserDetailsRootUser;
import com.backend.Models.UserDetailsWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService 
	extends BasicAuthenticationEntryPoint 
	implements UserDetailsService {

	UserDAO userDAO;
	UserDetailsRootUser rootUser;

	@Autowired
	public AuthenticationService(UserDAO userDAO, UserDetailsRootUser rootUser) {
		this.userDAO = userDAO;
		this.rootUser = rootUser;
	}

	/*
	 * User Details Service implementation
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username.equals(Config.SPRING_ADMIN_USER_NAME)) {
			return rootUser;
		}
		User activeUser = userDAO.getUserByName(username);
		if(activeUser == null) {
			throw new UsernameNotFoundException("user not found with username "+username);
		}
		return new UserDetailsWrapper(activeUser);
	}

	/*
	 * BasicAuthenticationEntryPoint implementation
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		     AuthenticationException authException) throws IOException {
		response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}
	
	@Override
	public void afterPropertiesSet() {
		setRealmName("MY APP REALM");
	}
}
