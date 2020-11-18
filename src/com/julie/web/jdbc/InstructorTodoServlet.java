package com.julie.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class InstructorTodoServlet
 */
@WebServlet("/InstructorTodoServlet")
public class InstructorTodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	//the servlet needs to call an instance of WebTodoListDBUtil 
	//and the reference of the database (datasource)
	private WebTodoListDBUtil webTodoListDBUtil;
			
	@Resource(name="jdbc/webtodolist")		// same name than in context.xml
	private DataSource dataSource;
			
		
	// In order to initialize the pool connection once the servlet is initialized
	@Override		
	public void init() throws ServletException {
		super.init();
		webTodoListDBUtil = new WebTodoListDBUtil(dataSource);
	}
		
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// We retrieve the session variable role to check if the user is an instructor
		HttpSession session = request.getSession();
		String role="";
		if(session.getAttribute("role")!=null) {
			role= (session.getAttribute("role")).toString();
			
			if(role.equals("instructor")){
				try {
					// We fetch the list of todos and send it to the jsp view
					listTodos(request,response);
					
					}catch(Exception e){
						e.printStackTrace();
					}
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

		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
		
	private void listTodos(HttpServletRequest request, HttpServletResponse response) 
			throws Exception{
			
		// We fetch the list
		List< Todo> todos = webTodoListDBUtil.fetchTodos();
		// We send the list of todos under the name TODO_LIST to the view through setAttribute method
		request.setAttribute("TODO_LIST", todos);
		// The dispatcher calls the instructor-todo.jsp file to display the list in the browser
		RequestDispatcher dispatcher =request.getRequestDispatcher("/instructor-todo.jsp");
		dispatcher.forward(request, response);
	}

}
