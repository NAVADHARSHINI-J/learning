package com.test.testproject.exception;

public class InvalidIdException extends Exception{

	private static final long serialVersionUID = 854309560537529175L;
	private String message;
	public InvalidIdException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	

}
