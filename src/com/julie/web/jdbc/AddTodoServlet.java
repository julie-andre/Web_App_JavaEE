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
//The JSP file add-todo.jsp is the associated VIEW
/**
 * Servlet implementation class AddTodoServlet
 */
@WebServlet("/AddTodoServlet")
public class AddTodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//the servlet needs to call an instance of WebTodoListDBUtil 
	//and the reference of the database (datasource)
	private WebTodoListDBUtil webTodoListDBUtil;
					
	@Resource(name="jdbc/webtodolist")		// same name than in context.xml
	private DataSource dataSource;
	
	// New Id of the new todo list to be added
	private int id;
	
	// In order to initialize the pool connection once the servlet is initialized
	@Override		
	public void init() throws ServletException {
		super.init();
		webTodoListDBUtil = new WebTodoListDBUtil(dataSource);
	}
	
	// Called when the Add Todo button is clicked in the instructor-todo.jsp view
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// We retrieve the session variable role to check if the user is an instructor
		HttpSession session = request.getSession();
		String role="";
		if(session.getAttribute("role")!=null) {
			role= (session.getAttribute("role")).toString();
			
			if(role.equals("instructor")) {
				// We fetch a new id and send it to the view
				this.id=webTodoListDBUtil.fetchNewId();
				request.setAttribute("newId", id);
				
				// We send the request to the add-todo.jsp to display the view
				request.getRequestDispatcher("/add-todo.jsp").forward(request,response);
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

	
	// Called when Save button is clicked in the add-todo.jsp view
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// We retrieve the todo description which was send as parameter in the request
		String description = request.getParameter("description");
		
		if(description.length()>0) {
			// We call the addTodo method from the WebTodoListDBUtil helper class
			webTodoListDBUtil.addTodo(id, description);
			
			//We redirect the request to the servlet InstructorTodoServlet to
			// display the updated list of the todos in the corresponding view (instructor-todo.jsp)
			response.sendRedirect("InstructorTodoServlet");
		}
		else {
			// The description is not valid (empty) 
			String message = "Invalid description, please enter at least one character for the description";
			//We set this message as a request attribute to the add-todo.jsp view
			request.setAttribute("InvalidMessage", message);
			// We don't forget the id parameter
			request.setAttribute("newId", id);
			request.getRequestDispatcher("/add-todo.jsp").forward(request,response);
		}
		
	}

}
