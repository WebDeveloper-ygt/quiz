package com.quiz.api.jersey.security;

class LoginCredentials {

	private String emailId;
	private String password;
	public LoginCredentials() {

	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
