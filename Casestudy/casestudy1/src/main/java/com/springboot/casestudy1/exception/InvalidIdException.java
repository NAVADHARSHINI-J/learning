package com.springboot.casestudy1.exception;

public class InvalidIdException extends Exception {
	private static final long serialVersionUID = -2150837803575963104L;
	private String message;

	public InvalidIdException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
