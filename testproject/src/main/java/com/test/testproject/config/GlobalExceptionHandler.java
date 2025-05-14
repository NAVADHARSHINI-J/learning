package com.test.testproject.config;


import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.test.testproject.exception.InvalidIdException;
import com.test.testproject.exception.UsernameInvalidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UsernameInvalidException.class)
	public ErrorResponse UsernameExceptionHandler(UsernameInvalidException e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
	
	@ExceptionHandler(InvalidIdException.class)
	public ErrorResponse InvalidIdExceptionHandler(InvalidIdException e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
	@ExceptionHandler(Exception.class)
	public ErrorResponse ExceptionHandler(Exception e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
}
