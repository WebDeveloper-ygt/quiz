package com.quiz.api.jersey.security;

public class TokenBean {

	private String authToken;
	private String expiresAt;
	public TokenBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	
}
