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
@WebServlet("/Register")
public class Register extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//1.Get the users input from the HTML Page
		String username =request.getParameter("username");
		String password =request.getParameter("password");
		String repeatPassword =request.getParameter("repeatPassword");
		Connection connection = null;
		boolean register = true;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/InvestmentWeb?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement getUser = connection.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			if (password.equals(repeatPassword)) {
				ResultSet user = getUser.executeQuery("SELECT username FROM users");
				while(user.next()) {
					if((user.getString(1).equals(username))) {
						register = false;
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('That username is taken already!');");  
						out.println("</script>");
						out.println("<html><head><title>ERROR page</title></head>");
						out.println("<body><h1>~ERROR PAGE~</h1><hr>Hello User!</br>You cannot register as that username is taken</br><b>Sorry for the inconvience!</b><hr>");	
						out.println("<p>Return to <a href='RegisterPage.html'>Register Page</a>.</p></body></html>");
					}
				}
			}else {
				register = false;
				out.println("<script type=\"text/javascript\">");  
				out.println("alert('Passwords do not match!');");  
				out.println("</script>");
				out.println("<html><head><title>ERROR page</title></head>");
				out.println("<body><h1>~ERROR PAGE~</h1><hr>Hello User!</br>You cannot register as the password and repeat password do not match!!</br>Please enter a correct password and repeat password...</br><hr><b>Your inputs:</b></br>" + "<b>Password:</b> " + password + "</br>" + "<b>Repeat Password</b>: " + repeatPassword + "<hr></br><b>Sorry for the inconvience!</b>");	
				out.println("<p>Return to <a href='RegisterPage.html'>Register Page</a>.</p></body></html>");
			}
			if(register == true) {
				//pass in the values as parameters
				PreparedStatement createUser = connection.prepareStatement("INSERT into users " + "(username,password)" + " VALUES (?, ?)");
				createUser.setString(1, username);
				createUser.setString(2, password);
				createUser.executeUpdate();
				createUser.close();
				response.sendRedirect("index.html");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
