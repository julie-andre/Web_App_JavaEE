package com.julie.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


//This is our helper class. The controller communicates with the database through this class
//Here we manage the communication by handling the dataSource(connection pool) sent by the servlet
//Like this we follow the DAO(Data Accessor Object) design pattern
public class WebTodoListDBUtil {
	private DataSource dataSource;
	
	// Constructor
	public WebTodoListDBUtil(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	// Does not close the connection but returns back the connection
	// to the connection pool (dataSource)
	private void close(Connection myCon, Statement myStmt, ResultSet myRs) {
		try {
			if(myCon!=null) {
				myCon.close();
			}
			if(myStmt!=null) {
				myStmt.close();
			}
			if(myRs!=null) {
				myRs.close();
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public User fetchUser(String username, String password) {
		User user = null;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		
		try {
			// Get the connection from the dataSource
			myCon = dataSource.getConnection();
			
			//With a prepared statement to prevent from sql injections
			myStmt = myCon.prepareStatement("SELECT * FROM user WHERE username= ? ");
			
			// Set the parameters
			myStmt.setString(1,username);
			
			// Execute the statement Query
			myRs = myStmt.executeQuery();
			
			// Process the result set
			while(myRs.next()) {
				String real_password =myRs.getString(2);
				// We verify the validity of the password by string comparison in Java
				// because MySQL ignores the case which is not what we want for a password
				if(real_password.equals(password)) {
					user = new User(myRs.getString(1),real_password, myRs.getNString("role"));
				}	
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			close(myCon,myStmt,myRs);
		}
		
		return user;
	}
	
	public List<Todo> fetchTodos() {
		List<Todo> todos =null;
		Connection myCon = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		
		try {
			todos = new ArrayList<Todo>();
			// Get the connection from the dataSource
			myCon = dataSource.getConnection();
			
			//Create the statement
			myStmt = myCon.createStatement();
			
			// Execute the statement Query
			String sql = "SELECT * FROM todo";
			myRs = myStmt.executeQuery(sql);
			
			// Process the result set
			while(myRs.next()) {
				Todo todo = new Todo(myRs.getInt("id"), myRs.getString("description"));
				todos.add(todo);
				
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			close(myCon,myStmt,myRs);
		}
		
		return todos;
	}
	
	public Todo fetchTodo(int id) {
		Todo todo = null;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		
		try {
			// Get the connection from the dataSource
			myCon = dataSource.getConnection();
			
			//With a prepared statement to prevent from sql injections
			myStmt = myCon.prepareStatement("SELECT * FROM todo WHERE id= ? ");
			
			// Set the parameter
			myStmt.setInt(1,id);
			
			// Execute the statement Query
			myRs = myStmt.executeQuery();
			
			// Process the result set
			while(myRs.next()) {
				todo=new Todo(id,myRs.getString("description"));
				
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			close(myCon,myStmt,myRs);
		}
		
		return todo;
	}
	
	public void updateTodo(Todo todo) {
		Connection myCon = null;
		PreparedStatement myStmt=null;
		
		try {
			// Get the connection from the dataSource
			myCon = dataSource.getConnection();
			
			//With a prepared statement to prevent from sql injections
			myStmt = myCon.prepareStatement("UPDATE todo"
					+ " SET description = ?"
					+ " WHERE id= ?");
			
			// Set the parameters
			myStmt.setString(1, todo.getDescription());
			myStmt.setInt(2, todo.getId());
			
			// Execute the update
			myStmt.executeUpdate();		
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			close(myCon,myStmt,null);
		}
	}
	
	public void deleteTodo(int id) {
		Connection myCon = null;
		PreparedStatement myStmt=null;
		
		try {
			// Get the connection from the dataSource
			myCon = dataSource.getConnection();
			
			String sql = "DELETE FROM todo WHERE id= ?";
			myStmt = myCon.prepareStatement(sql);
			
			// Set the parameter
			myStmt.setInt(1, id);
						
			// Execute the update
			myStmt.executeUpdate();			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			close(myCon,myStmt,null);
		}
	}
	
	public int fetchNewId() {
		int id = -1;
		Connection myCon = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		
		try {
			// Get the connection from the dataSource
			myCon = dataSource.getConnection();
			
			//Create the statement
			myStmt = myCon.createStatement();
			
			// Execute the statement Query
			String sql = "SELECT id FROM todo WHERE id>= ALL (SELECT T.id FROM todo T)";
			myRs = myStmt.executeQuery(sql);
			
			// Process the result set
			while(myRs.next()) {
				// We get the higher id of the table
				id= myRs.getInt("id");
				// We increment it by 1 to get the new next id
				id+=1;
				
			}

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			close(myCon,myStmt,myRs);
		}
		
		return id;
	}
	
	public void addTodo(int id, String description) {
		Connection myCon = null;
		PreparedStatement myStmt=null;
		
		try {
			// Get the connection from the dataSource
			myCon = dataSource.getConnection();
			
			//With a prepared statement to prevent from sql injections
			myStmt = myCon.prepareStatement("INSERT INTO todo (id,description) "
					+ "VALUES(?,?)");
			
			// Set the parameters
			myStmt.setInt(1, id);
			myStmt.setString(2, description);
			
			// Execute the update
			myStmt.executeUpdate();		
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			close(myCon,myStmt,null);
		}
	}
}
