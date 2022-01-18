package com.backend.util;

import java.util.List;

import org.springframework.http.HttpStatus;

public class DontWorryMomException extends Exception {
	int statusCode;
	List<String> reasons;

	public static final int DEFAULT_STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
	
	public DontWorryMomException(int statusCode, List<String> reasons) {
		super();
		this.statusCode = statusCode;
		this.reasons = reasons;
	}
	
	public DontWorryMomException(String reason) {
		super();
		this.statusCode = DEFAULT_STATUS_CODE;
		this.reasons = List.of(reason);
	}
}