package com.backend.services.AuthenticationService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.DataAcquisitionObjects.UserDAO;
import com.backend.Models.User;
import com.backend.Models.UserDetailsWrapper;
import com.backend.util.DontWorryMomException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationService 
	extends BasicAuthenticationEntryPoint 
	implements UserDetailsService {

	UserDAO userDAO;

	@Autowired
	public AuthenticationService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/*
	 * User Details Service implementation
	 */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User activeUser = userDAO.getUserByName(username);
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


	/*
	 * Other methods
	 */

	@GetMapping("")
	public ResponseEntity<String> getCrashDetectedNotificationList() {
		return new ResponseEntity<>(
			"Hello world", 
			HttpStatus.OK);
	}

	@GetMapping("failure_test")
	public ResponseEntity<String> testException() throws DontWorryMomException {
		throw new RuntimeException("runtime exception - something wrong");
	}

	@GetMapping("failure_test_2")
	public ResponseEntity<String> testException2() throws DontWorryMomException {
		throw new DontWorryMomException("dwm exception - something wrong");
	}
}
