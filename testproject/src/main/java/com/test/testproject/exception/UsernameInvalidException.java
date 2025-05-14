package com.test.testproject.exception;

public class UsernameInvalidException extends Exception{

	private static final long serialVersionUID = 3266507742324238997L;
	private String message;

	public UsernameInvalidException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
