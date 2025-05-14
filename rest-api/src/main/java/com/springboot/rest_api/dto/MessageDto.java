package com.springboot.rest_api.dto;

import org.springframework.stereotype.Component;

@Component
public class MessageDto {
	private String message;
	private int statusCode;
	public MessageDto() {
		super();
	}
	public MessageDto(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int i) {
		this.statusCode = i;
	}
	
	
}
