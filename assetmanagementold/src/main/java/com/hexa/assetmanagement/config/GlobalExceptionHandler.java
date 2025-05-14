package com.hexa.assetmanagement.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hexa.assetmanagement.exception.AssetUnavailableException;
import com.hexa.assetmanagement.exception.InvalidContactException;
import com.hexa.assetmanagement.exception.InvalidIdException;
import com.hexa.assetmanagement.exception.UsernameInvalidException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	Logger logger=LoggerFactory.getLogger("GlobalExceptionHandler");
	@ExceptionHandler(InvalidIdException.class)
	public ErrorResponse idInvalidErrorHandler(InvalidIdException e) {
		logger.error(e.getMessage());
		return ErrorResponse.create(e, HttpStatusCode.valueOf(400), e.getMessage());
	}
	
	@ExceptionHandler(InvalidContactException.class)
	public ErrorResponse contactErrorHandler(InvalidContactException e) {
		logger.error(e.getMessage());
		return ErrorResponse.create(e, HttpStatusCode.valueOf(400), e.getMessage());
	}
	
	@ExceptionHandler(UsernameInvalidException.class)
	public ErrorResponse usernameErrorHandler(UsernameInvalidException e) {
		logger.error(e.getMessage());
		return ErrorResponse.create(e, HttpStatusCode.valueOf(400), e.getMessage());
	}
	@ExceptionHandler(Exception.class)
	public ErrorResponse ErrorHandler(Exception e) {
		logger.error(e.getMessage());
		return ErrorResponse.create(e, HttpStatusCode.valueOf(400), e.getMessage());
	}
	@ExceptionHandler(AssetUnavailableException.class)
	public ErrorResponse AssetUnavailableExceptionHandler(AssetUnavailableException e) {
		logger.error(e.getMessage());
		return ErrorResponse.create(e, HttpStatusCode.valueOf(400), e.getMessage());
	}
	@ExceptionHandler(IOException.class)
	public ErrorResponse IOExceptionHandler(IOException e) {
		logger.error(e.getMessage());
		return ErrorResponse.create(e, HttpStatusCode.valueOf(400), e.getMessage());
	}
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse RuntimeExceptionHandler(RuntimeException e) {
		logger.error(e.getMessage());
		return ErrorResponse.create(e, HttpStatusCode.valueOf(400), e.getMessage());
	}
}
