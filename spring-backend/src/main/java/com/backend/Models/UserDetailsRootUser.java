package com.backend.Models;

import java.util.Arrays;
import java.util.Collection;

import com.backend.Configuration.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsRootUser implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(
			new SimpleGrantedAuthority("user")
		);
	}

	@Override
	public String getUsername() {
		return Config.SPRING_ADMIN_USER_NAME;
	}

	@Override
	public String getPassword() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encrypted_password = encoder.encode(Config.SPRING_ADMIN_USER_PASSWORD);
		return encrypted_password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
