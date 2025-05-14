package com.spring.security.exception;

public class InvalidUsernameException extends Exception{
	private static final long serialVersionUID = 1L;
	
	private String Message;

	public InvalidUsernameException(String message) {
		super();
		Message = message;
	}

	public String getMessage() {
		return Message;
	}
	
 
}
