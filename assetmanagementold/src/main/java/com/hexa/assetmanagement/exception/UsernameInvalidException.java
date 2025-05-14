package com.hexa.assetmanagement.exception;

public class UsernameInvalidException extends Exception{
	
	private static final long serialVersionUID = 7032426570129578508L;
    private String Message;
	public UsernameInvalidException(String message) {
		super();
		Message = message;
	}
	public String getMessage() {
		return Message;
	}
    
}