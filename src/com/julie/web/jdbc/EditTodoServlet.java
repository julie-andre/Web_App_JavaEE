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
//The JSP file edit-todo.jsp is the VIEW
/**
 * Servlet implementation class EditTodoServlet
 */
@WebServlet("/EditTodoServlet")
public class EditTodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//the servlet needs to call an instance of WebTodoListDBUtil 
	//and the reference of the database (datasource)
	private WebTodoListDBUtil webTodoListDBUtil;
				
	@Resource(name="jdbc/webtodolist")		// same name than in context.xml
	private DataSource dataSource;
				
	// Id of the todo list to be edited
	private int id;
	
	// In order to initialize the pool connection once the servlet is initialized
	@Override		
	public void init() throws ServletException {
		super.init();
		webTodoListDBUtil = new WebTodoListDBUtil(dataSource);
	}
			
	// Called from the edit link in instructor-todo.jsp
    // Sends the todo as a parameter of the request, the student is then displayed by the edit-todo.jsp
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// We retrieve the session variable role to check if the user is an instructor
		HttpSession session = request.getSession();
		String role="";
		if(session.getAttribute("role")!=null) {
			role= (session.getAttribute("role")).toString();
			
			if(role.equals("instructor")) {
				// We retrieve the todoId parameter sent with the request
				this.id = Integer.parseInt(request.getParameter("todoId"));
				
				// We use an instance of WebTodoListDBUtil to fetch the corresponding todo
				Todo todo = webTodoListDBUtil.fetchTodo(id);
				
				// We set this todo as a request attribute to the edit-todo.jsp view
				request.setAttribute("Todo",todo);
				request.getRequestDispatcher("/edit-todo.jsp").forward(request,response);
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

	
	// Called when the Save button is clicked in edit-todo.jsp view
	// the todo informations are carried as parameters in the request received by the doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//We retrieve the parameter
		String description = request.getParameter("description");
		
		if(description.length()>0) {
			// We create a new Todo object 
			Todo todo = new Todo(this.id,description);
			
			// We update the database by calling a method from the Helper class WebTodoListDBUtil.java
			webTodoListDBUtil.updateTodo(todo);
			
			//We redirect the request to the servlet InstructorTodoServlet to
			// display the updated list of the todos in the corresponding view (instructor-todo.jsp)
			response.sendRedirect("InstructorTodoServlet");
		}
		else {
			// The description is not valid (empty) 
			String message = "Invalid description, please enter at least one character for the description";
			
			//We set this message as a request attribute to the add-todo.jsp view
			request.setAttribute("InvalidMessage", message);
			
			// We don't forget to set the todo as a parameter of the request to display 
			// its details int he view
			Todo todo = webTodoListDBUtil.fetchTodo(id);
			request.setAttribute("Todo",todo);
			request.getRequestDispatcher("/edit-todo.jsp").forward(request,response);
		}
		
	}

}
