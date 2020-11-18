<!--  We need to include the taglist for JSLT use as well as the 2 librairies 
	  (no more java code in this file, easier to read)-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Student todoList Page</title>
		<link type="text/css" rel="stylesheet" href="CSS/styles.css"> <!-- CSS name of the folder -->

	</head>
	<body>
		<div id="wrapper">
			<div id="header">
			<!-- We display the instructor name thanks to session variable username -->
				<h2>Welcome dear student ${sessionScope.username}</h2>
			</div>
		</div>
		
		<div id="container">
			<div id="content">
				<table>
					<tr>
						<th>Todo Id </th>
						<th>Description</th>
					</tr>
					<c:forEach var="todo" items="${TODO_LIST }" >
						<tr>
							<td> ${todo.id}</td>
							<td> ${todo.description}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<!-- We add a Logout button to return to the log page -->
		<div style="clear:both;">
			<a href="LoginControllerServlet">Logout</a>
		</div>

	</body>
</html>