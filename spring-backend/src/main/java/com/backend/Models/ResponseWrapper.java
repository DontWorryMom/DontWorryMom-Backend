package com.backend.Models;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper <T> {

	T results;
	List<String> errors;
	boolean isSuccess;

	public static <T> ResponseWrapper<T> successResponse(T result) {
		return new ResponseWrapper<T>(result, Collections.emptyList(), true);
	}

	public static <T> ResponseWrapper<T> failureResponse(List<String> errorList) {
		return new ResponseWrapper<T>(null, errorList, false);
	}
}
