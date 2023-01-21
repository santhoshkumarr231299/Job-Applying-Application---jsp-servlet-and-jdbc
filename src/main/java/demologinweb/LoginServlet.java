package demologinweb;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demologin.Login;
import demologininner.LoginDao;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String uname = request.getParameter("username");
		String pword = request.getParameter("password");
		
		if(uname == "" || pword == "")
		{
			request.setAttribute("alert", "Username and Password should not be Empty");
			request.getRequestDispatcher("frontpage.jsp").forward(request,response);
			return;
		}
		
		Login lg = new Login();
		Login.setPword(pword);
		Login.setUname(uname);
		
		LoginDao ld = new LoginDao();
		try {
			if(ld.validateLogin(lg))
			{
				HttpSession session = request.getSession();
				if(ld.validateHomePage(lg))
				{
					LoginDao login = new LoginDao();
					Connection con = null;
					con = login.getConnection();
					
					String sqlQuery = "select * from userdb where username = '"+Login.getUname()+"'";
					PreparedStatement ps = con.prepareStatement(sqlQuery);
					ResultSet rs = ps.executeQuery(sqlQuery);
					
					request.setAttribute("data",rs);
					request.setAttribute("message", "You have already submitted the Application form");
					session.setAttribute("username", Login.getUname());
					request.getRequestDispatcher("homepage2.jsp").forward(request,response);
	
				}
				else
				{
					session.setAttribute("username", Login.getUname());
					request.getRequestDispatcher("homepage.jsp").forward(request,response);
				}
			}
			else
			{
				request.setAttribute("alert","Invalid Username and Password");
				request.getRequestDispatcher("frontpage.jsp").forward(request,response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			pw.println("Login Servlet CNFE/ SQLE");
		} catch (InstantiationException e) {
			
			pw.println("Login Servlet IE");
		} catch (IllegalAccessException e) {
			
			pw.println("Login Servlet IAE");
		}
		
	}

}
