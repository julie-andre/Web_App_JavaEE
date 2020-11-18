<!--  We need to include the taglist for JSLT use as well as the 2 librairies 
	  (no more java code in this file, easier to read)-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="CSS/add-student-style.css"> <!-- CSS name of the folder -->
<link type="text/css" rel="stylesheet" href="CSS/styles.css">
<title>Delete a todoList</title>
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<!-- We display the instructor name thanks to session variable username -->
			<h2>Welcome dear instructor ${sessionScope.username}</h2>
		</div>
	</div>
	
	<div id="container">
		<h3> Delete the TodoList</h3>
		<form action="DeleteTodoServlet" method = "post">
		<table>
			<tbody>
				<tr>
					<td><label>Id: </label> </td>
					<td>${Todo.id}</td>
				</tr>
				<tr>
					<td><label>Description: </label> </td>
					<td>${Todo.description}</td>
				</tr>
				<tr>
					<td><label></label> </td>
					<td><input type="submit" value = "Confirm Deletion"/></td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>
	
	<!-- We add a Back to List button to return to the todo list page -->
	<div style="clear:both;">
		<a href="InstructorTodoServlet">Back to List</a>
	</div>
	<!-- We add a Logout button to return to the log page -->
	<div style="clear:both;">
		<a href="LoginControllerServlet">Logout</a>
	</div>
</body>

</html>