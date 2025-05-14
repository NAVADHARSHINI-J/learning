package com.springboot.rest_api.exception;

public class UsernameInvalidException extends Exception{
	private static final long serialVersionUID = 6998311583552640115L;
	private String message;
	
	public UsernameInvalidException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
