package com.hexa.assetmanagement.exception;

public class AssetUnavailableException extends Exception{
	
	private static final long serialVersionUID = 1L;
private String message;

public AssetUnavailableException(String message) {
	super();
	this.message = message;
}

public String getMessage() {
	return message;
}

}
