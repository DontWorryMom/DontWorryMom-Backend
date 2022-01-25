package com.backend.Models;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsWrapper implements UserDetails {

	User user;

	public UserDetailsWrapper(User user) {
		this.user = user;
	}
	
	/*
	 * Methods for overriding Spring's UserDetails
	 * these functions are used by Spring for authentication
	 */

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(
			new SimpleGrantedAuthority("user")
		);
	}

	@Override
	public String getUsername() {
		return this.user.username;
	}

	@Override
	public String getPassword() {
		return this.user.encrypted_password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
	
}
