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
		<title>Instructor todoList Page</title>
		<link type="text/css" rel="stylesheet" href="CSS/styles.css"> <!-- CSS name of the folder -->
	</head>
	<body>
		<div id="wrapper">
			<div id="header">
			<!-- We display the instructor name thanks to session variable username -->
				<h2>Welcome dear instructor ${sessionScope.username}</h2>
			</div>
		</div>
		
		<br />
		
		<!-- We add the Add Todo button and calls the doGet() method of AddTodoServlet -->
		<form action="AddTodoServlet" method = "get">
			<input type="submit" value = "Add Todo"/>
		</form>
		
		<div id="container">
			<div id="content">
				<table>
					<tr>
						<th>Todo Id </th>
						<th>Description</th>
						<th>Actions</th>
					</tr>
					<!-- We use the JSTL to display the todos of the TODO_LIST parameter
					of the request sent by IntructorTodoServlet -->
					<c:forEach var="todo" items="${TODO_LIST }" >
						<!-- We use the url tag JSTL feature to call the servlet EditTodoServlet
						And carry some parameters (here the todo id) with the request -->
						<c:url var="EditLink" value= "EditTodoServlet">
							<c:param name="todoId" value="${todo.id}"/>
						</c:url>
						<!-- We do the same to call the DeleteTodoServlet with the Delete link -->
						<c:url var="DeleteLink" value ="DeleteTodoServlet">
							<c:param name="todoId" value = "${todo.id}"/>
						</c:url>
						<tr>
							<td> ${todo.id}</td>
							<td> ${todo.description}</td>
							<td> 
								<a href="${EditLink}"> Edit </a>|
								<a href="${DeleteLink}"> Delete </a>
							</td>
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