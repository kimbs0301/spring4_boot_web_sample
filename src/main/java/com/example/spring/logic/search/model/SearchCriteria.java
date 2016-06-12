package com.example.spring.logic.search.model;

/**
 * @author gimbyeongsu
 * 
 */
public class SearchCriteria {
	private String username;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "SearchCriteria [username=" + username + ", email=" + email + "]";
	}
}