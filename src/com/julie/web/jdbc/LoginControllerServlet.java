package com.julie.web.jdbc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



//This will be our CONTROLLER servlet
//The JSP file login.jsp is the VIEW associated
/**
 * Servlet implementation class LoginControllerServlet
 */
@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// The LoginControlServlet sets the connection pool with the database before calling the helper class
	// Then the helper class (WebTodoListDBUtil) will be able to communicate with the database
	// Therefore the servlet needs to call an instance of WebTodoListDBUtil and a reference of the database
	private WebTodoListDBUtil webTodoListDBUtil;
		
	@Resource(name="jdbc/webtodolist")		// same name than in context.xml
	private DataSource dataSource;
		
		
	@Override		// In order to initialize the pool connection once the servlet is initialized
	public void init() throws ServletException {
		super.init();
		webTodoListDBUtil = new WebTodoListDBUtil(dataSource);
	}
	
	// Called by default when no other method is specified
	// Called when the servlet is launched
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// We free the session variables to prevent when the login page is displayed (in case of logout)
		request.getSession().invalidate();
		
		// We retrieve the cookies if they exist to automatically fill the username
		Cookie [] cookies = request.getCookies();
		if(cookies!= null){
			// Normally we have only one cookie which name is username
			// Therefore the loop is not mandatory
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("username"))
					request.setAttribute("usernameC", cookie.getValue()) ;
			}
		}
		
		// The dispatcher calls the login.jsp file to display the view
		RequestDispatcher dispatcher =request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// We retrieve the parameters sent with the request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// We try to fetch the corresponding user if he exits
		User user = webTodoListDBUtil.fetchUser(username,password);
		
		if(user!=null) {
			// The user exists in the database and the password is correct
			
			// We keep the user object's username in a session variable on the server
			HttpSession session = request.getSession();
			session.setAttribute("username", user.getUsername());
			// We also keep the user role (student or instructor to limit the access to some pages)
			session.setAttribute("role", user.getRole());
			
			
			// We store the username in a Cookie on the browser to automatically fill
			// later logins with this username
			Cookie cookie = new Cookie("username", user.getUsername());
			cookie.setMaxAge(60*60*24) ; // in seconds, here stored for 24 hours
			response.addCookie(cookie) ;
			
			if(user.getRole().equals("instructor")){
				// This is the instructor
				// We redirect the request to the doGet() method of the servlet InstructorTodoServlet 
				//request.getRequestDispatcher("/instructor-todo.jsp").forward(request,response);
				response.sendRedirect("InstructorTodoServlet");
			}
			else {
				// This is a student
				// We redirect the request to the doGet() method of the servlet StudentTodoServlet 
				// which will then display the list of todos
				response.sendRedirect("StudentTodoServlet");
			}
		}
		else {
			//Authentication error
			String message = "Authentication error, wrong username our password";
			//We set this message as a request attribute to the loging.jsp view
			request.setAttribute("ErrorMessage", message);
			// We don't forget to refill the username 
			request.setAttribute("usernameC", username) ;
			request.getRequestDispatcher("/login.jsp").forward(request,response);
		}
	}

}
