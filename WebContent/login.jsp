<!-- import java.util librairies and com.julie.web package classes for scriplets -->
<%@ page import="java.util.*,com.julie.web.jdbc.*" %>		
<!--  We need to include the taglist for JSLT use as well as the 2 librairies 
	  (no more java code in this file, easier to read)-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login Page</title>
		<link type="text/css" rel="stylesheet" href="CSS/styles.css"> <!-- CSS name of the folder -->
		<link type="text/css" rel="stylesheet" href="CSS/add-student-style.css">
	</head>
	
	<body>
		<div id="wrapper">
			<div id="header">
				<h2>Welcome in the TodoList Login Page</h2>
			</div>
		</div>
		
		<div id="container">
		<form action="LoginControllerServlet" method = "post">
		<table>
			<tbody>
				<tr>
					<td><label>Username: </label> </td>
					<td><input type="text" name = "username" value="${usernameC }"/></td>
				</tr>
				<tr>
					<td><label>Password: </label> </td>
					<td><input type="text" name = "password" value=""/></td>
				</tr>
				<tr>
					<td><label></label> </td>
					<!-- We add the Login button and calls the doPost() method of LoginControllerServlet -->
					<td><input type="submit" value = "Login"/></td>
				</tr>
				
			</tbody>
		</table>
		</form>
		<p>${ErrorMessage }</p>
		</div>
	</body>
</html>