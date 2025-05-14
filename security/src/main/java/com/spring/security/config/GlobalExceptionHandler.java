package com.spring.security.config;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.security.exception.InvalidUsernameException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidUsernameException.class)
	public ErrorResponse usernameErrorHandler(InvalidUsernameException e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
}
