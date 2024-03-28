//Student name: Maija Soares
//Student number: C19478224
//Web dev Project
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/login")
public class Login extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//1.Get the users input from the HTML Page
		String username =request.getParameter("username");
		String password =request.getParameter("password");
		Connection connection = null;
		boolean login = false;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/InvestmentWeb?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement getUser = connection.createStatement();
			ResultSet rs = getUser.executeQuery("SELECT * FROM users");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			while(rs.next()) {
				if((rs.getString(1).equalsIgnoreCase(username))&&(rs.getString(2).equalsIgnoreCase(password))) {
					login = true;	
					response.sendRedirect("Finance.jsp?username="+username);
				}
			}
			if(login == false) {
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Did not log in correctly!');");  
				out.println("</script>");
				out.println("<html><head><title>ERROR page</title></head>");
				out.println("<body><h1>~ERROR PAGE~</h1><hr>Hello User!</br>You did not log in correctly or you do not yet have an account.</br>Please enter a correct username and password...</br><hr><b>Your inputs:</b> </br><b>Username:</b> " + username + "</br>" + "<b>Password:</b> " + password + "<hr>");	
				out.println("<p>Return to <a href='index.html'>Login Page</a>.</p><p>Register for an account <a href='RegisterPage.html'>here</a>.</p></body></html>");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

