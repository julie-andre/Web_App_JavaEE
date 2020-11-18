package com.julie.web.jdbc;

//This is a MODEL javabean class to manage User objects
public class User {
	
	// Attributes
	private String username;
	private String password;
	private String role;
	
	// Constructor
	public User(String username, String password,String role) {
		this.setUsername(username);
		this.setPassword(password);
		this.setRole(role);
	}

	// Getters and Setters for the attributes
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	// Override the toString() method to display exactly what we want when this
	// method is implicitly called by other methods
	@Override
	public String toString() {
		return "Unsername : "+this.username+ "  |  Role : "+ this.role;
	}
	
}
