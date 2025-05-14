package com.springboot.rest_api.config;

import java.io.IOException;

//import org.springframework.beans.factory.annotation.Autowired;
//import com.springboot.rest_api.dto.MessageDto;
//import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.rest_api.exception.InvalidContactException;
import com.springboot.rest_api.exception.InvalidIdException;
import com.springboot.rest_api.exception.UsernameInvalidException;



@RestControllerAdvice
public class GlobalErrorHandler {
	
//	@Autowired
//	private MessageDto messageDto;
//	@ExceptionHandler(InvalidIdException.class)
//	public ResponseEntity<?> errorHandler(InvalidIdException e) {
//		messageDto.setMessage(e.getMessage());
//		messageDto.setStatusCode(400);
//		return ResponseEntity.status(400).body(messageDto);
//	}
	
	//or
	
	@ExceptionHandler(InvalidIdException.class)
	public ErrorResponse errorHandler(InvalidIdException e) {
	  return ErrorResponse.create(e,HttpStatusCode.valueOf(400), e.getMessage());	
	}
	
	@ExceptionHandler(InvalidContactException.class)
	public ErrorResponse contactErrorHandler(InvalidContactException e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
	@ExceptionHandler(UsernameInvalidException.class)
	public ErrorResponse usernameErrorHandler(UsernameInvalidException e) {
		return ErrorResponse.create(e,HttpStatusCode.valueOf(400),e.getMessage());
	}
	
	 @ExceptionHandler(RuntimeException.class)
 	 public ErrorResponse invalidImageExceptionHandler(RuntimeException e) {
 		 return ErrorResponse.create
 				 			(e, 
 				 			HttpStatusCode.valueOf(400), 
 				 			e.getMessage()); 
 	 }
 	 
 	 @ExceptionHandler(IOException.class)
 	 public ErrorResponse invalidIOExceptionHandler(IOException e) {
 		 return ErrorResponse.create
 				 			(e, 
 				 			HttpStatusCode.valueOf(400), 
 				 			e.getMessage()); 
 	 }
 	 @ExceptionHandler(Exception.class)
 	 public ErrorResponse exceptionHandler(Exception e) {
 		 return ErrorResponse.create
 				 			(e, 
 				 			HttpStatusCode.valueOf(400), 
 				 			e.getMessage()); 
 	 }
	
}




