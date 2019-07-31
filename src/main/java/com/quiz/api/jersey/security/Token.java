package com.quiz.api.jersey.security;

public class Token {

	String token;
	String expiresAt;
	public Token() {
		
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	
}
