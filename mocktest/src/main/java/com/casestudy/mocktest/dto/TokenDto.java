package com.casestudy.mocktest.dto;

import org.springframework.stereotype.Component;

@Component
public class TokenDto {
	private String token;
	private String expiry;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	
}
