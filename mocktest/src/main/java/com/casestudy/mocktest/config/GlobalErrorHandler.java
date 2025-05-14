package com.casestudy.mocktest.config;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.casestudy.mocktest.exception.InvalidIdException;

@Component
public class GlobalErrorHandler {

	@ExceptionHandler(Exception.class)
	public ErrorResponse ExceptionHandler(Exception e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse RuntimeExceptionHandler(RuntimeException e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
	@ExceptionHandler(InvalidIdException.class)
	public ErrorResponse InvalidIdExceptionHandler(InvalidIdException e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
}
