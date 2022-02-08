package com.backend.util;

import java.util.List;

import com.backend.Models.ResponseWrapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DontWorryMomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value={DontWorryMomException.class, UnauthorizedAccessException.class})
	protected ResponseEntity<ResponseWrapper<Object>> handleDontWorryMomException(DontWorryMomException e, WebRequest wr) {
		ResponseWrapper<Object> body = ResponseWrapper.failureResponse(e.reasons);
		return new ResponseEntity<ResponseWrapper<Object>>(
			body, HttpStatus.valueOf(e.statusCode)
		);
	}

	@ExceptionHandler()
	protected ResponseEntity<ResponseWrapper<Object>> handleGenericException(Exception e, WebRequest wr) {
		ResponseWrapper<Object> body = ResponseWrapper.failureResponse(List.of(e.getMessage()));
		return new ResponseEntity<ResponseWrapper<Object>>(
			body, HttpStatus.INTERNAL_SERVER_ERROR
		);
	}
}
