package com.backend.util;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends DontWorryMomException {

	public UnauthorizedAccessException(String reason) {
		super(HttpStatus.UNAUTHORIZED.value(), reason);
	}
	
}
