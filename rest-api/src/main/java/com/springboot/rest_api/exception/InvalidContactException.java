package com.springboot.rest_api.exception;

public class InvalidContactException extends Exception{
	
	private static final long serialVersionUID = 5270599946913184577L;
	private String message;

	public InvalidContactException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
