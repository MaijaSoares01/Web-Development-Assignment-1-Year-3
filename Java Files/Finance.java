//Student name: Maija Soares
//Student number: C19478224
//Web dev Project
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Finance")
public class Finance  extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		//1.Get the users input from the HTML Page
		String initialAmountStr =request.getParameter("initialAmount");
		String compoundIntStr =request.getParameter("compoundInt");
		String durationStr =request.getParameter("duration");
		String username =request.getParameter("username");
		double initialAmount = Double.valueOf(initialAmountStr);
		double compoundInt = Double.valueOf(compoundIntStr);
		double duration = Double.valueOf(durationStr);
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/InvestmentWeb?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement getUser = connection.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			double power = Math.pow(1+(compoundInt/100),duration);
			double totalCompInterest= initialAmount * (power-1);
			double totalAmountPlusComp = initialAmount + totalCompInterest;
			//P [(1 + i)^n – 1]
			// Begin table and table header
				out.println( "<html><head><title>Finance</title></head><body><h1>Finance Table</h1><table border=\"1\">"); 
				out.println( "<caption><b>Initial amount of " + initialAmount + " at an annual rate of " + compoundInt + "% for " + duration + " years.</b></caption>" );
				out.println( "<thead><tr><th>Year</th><th>Compound interest for the Year</th><th>Compound interest Total per year</th><th>Initial Amount plus interest per year</th></tr></thead>" );
				// Begin table body
				out.println("<tbody>" );
						
			// Calculate amount for x years of deposit at current rate
			for ( var year = 0; year <= duration; ++year )
			  {
				power = Math.pow(1+(compoundInt/100),year);
				double compIntAmount = initialAmount * (power-1);
				double amountPlusComp = initialAmount + compIntAmount;
				double interest = (amountPlusComp/100) * compoundInt;
				out.println( "<tr><td>" + year + "</td><td>" + interest + "</td><td>" + compIntAmount + "</td><td>" + amountPlusComp + "</td></tr>" );
			  } // End for loop
			out.println( "</tbody></table>" );
			out.println("<b>Total Compound interest: </b>" + totalCompInterest + "<br>");
			out.println("<b>Total Balance with Compound interest: </b>" + totalAmountPlusComp + "<br><br>");
			out.println("Return to <a href='index.html'>Login Page</a>.</p></body></html>");	
			PreparedStatement createUser = connection.prepareStatement("INSERT into requesters " + "(username,InitialAmount,Duration,CompInterest,TotalInterest,TotalBalance)" + " VALUES (?,?,?,?,?,?)");
			createUser.setString(1, username);
			createUser.setDouble(2, initialAmount);
			createUser.setDouble(3, duration);
			createUser.setDouble(4, compoundInt);
			createUser.setDouble(5, totalCompInterest);
			createUser.setDouble(6, totalAmountPlusComp);
			createUser.executeUpdate();
			createUser.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
