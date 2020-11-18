package com.julie.web.jdbc;

//This is a MODEL javabean class to manage Todo objects
public class Todo {
	
	//Attributes
	private int id;
	private String description;
	
	// Constructor
	public Todo(int id, String description) {
		this.setId(id);
		this.setDescription(description);
	}
	
	// A constructor without the id when a todo is added by the instructor, 
	//the is auto-incremented in the database
	public Todo(String description) {
		this.setDescription(description);
	}

	// Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	// Override the toString() method to display exactly what we want when this
	// method is implicitly called by other methods
	@Override
	public String toString() {
		return "Id : "+this.id+ "  |  Description : "+ this.description;
	}

}
