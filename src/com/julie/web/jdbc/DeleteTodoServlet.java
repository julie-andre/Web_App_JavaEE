package com.julie.web.jdbc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



//This is another CONTROLLER servlet
//The JSP file delete-todo.jsp is the VIEW
/**
 * Servlet implementation class DeleteTodoServlet
 */
@WebServlet("/DeleteTodoServlet")
public class DeleteTodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//the servlet needs to call an instance of WebTodoListDBUtil 
	//and the reference of the database (datasource)
	private WebTodoListDBUtil webTodoListDBUtil;
	
	@Resource(name="jdbc/webtodolist")		// same name than in context.xml
	private DataSource dataSource;
					
	// Id of the todo list to be deleted
	private int id;
		
	// In order to initialize the pool connection once the servlet is initialized
	@Override		
	public void init() throws ServletException {
		super.init();
		webTodoListDBUtil = new WebTodoListDBUtil(dataSource);
	}
	
	// Called from the delete link in instructor-todo.jsp
    // Sends the todo to the delete-todo.jsp as a parameter of the request
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// We retrieve the session variable role to check if the user is an instructor
		HttpSession session = request.getSession();
		String role="";
		if(session.getAttribute("role")!=null) {
			role= (session.getAttribute("role")).toString();
			
			if(role.equals("instructor")) {
				// We retrieve the studentId parameter sent with the request and convert it in int
				this.id = Integer.parseInt(request.getParameter("todoId"));
						
				// We use an instance of WebTodoListDBUtil to fetch the corresponding todo
				Todo todo = webTodoListDBUtil.fetchTodo(id);
						
				// We set this student as a request attribute to the delete-student.jsp view
				// The aim of this view is to ask for confirmation and to recall the todo details
				request.setAttribute("Todo",todo);
				request.getRequestDispatcher("/delete-todo.jsp").forward(request,response);
			}
			else {
				//Access denied error
				String message = "Access denied, you have been redirected to the login page because "
						+ "you do not have the rights to access this page";
				//We set this message as a request attribute to the loging.jsp view
				request.setAttribute("ErrorMessage", message);
				// We don't forget to refill the username 
				request.setAttribute("usernameC", session.getAttribute("username")) ;
				request.getRequestDispatcher("/login.jsp").forward(request,response);
			}
		}
		else {
			String message = "Session expired, please login with your username and password";
			//We set this message as a request attribute to the loging.jsp view
			request.setAttribute("ErrorMessage", message);
			request.getRequestDispatcher("/login.jsp").forward(request,response);
		}
				
		
		
	}

	
	// Called by the Confirm Deletion Button in delete-todo.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// We call the deleteTodo method from WebTodoListDBUtil
		webTodoListDBUtil.deleteTodo(id);
		
		//We redirect the request to the servlet InstructorTodoServlet to
		// display the updated list of the todos in the corresponding view (instructor-todo.jsp)
		response.sendRedirect("InstructorTodoServlet");
		
	}

}
