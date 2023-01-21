package newuserservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demologin.Login;
import demologininner.LoginDao;

public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("username");
		if(uname.length()<5)
		{
			request.setAttribute("alert", "Username must contain 5 letters and more");
			request.getRequestDispatcher("newuser.jsp").forward(request,response);
			return ;
		}
		if(uname.contains(" "))
		{
			request.setAttribute("alert", "Enter a valid username");
			request.getRequestDispatcher("newuser.jsp").forward(request,response);
			return ;
		}
		String email = request.getParameter("email");
		if(email.contains(" ")||(!email.contains("@") || !email.contains(".com")))
		{
			request.setAttribute("alert", "Enter a valid Email Address");
			request.getRequestDispatcher("newuser.jsp").forward(request,response);
			return ;
		}
		String pword = request.getParameter("password");
		if(pword.length()<5)
		{
			request.setAttribute("alert", "Password must contain atleast 5 letters");
			request.getRequestDispatcher("newuser.jsp").forward(request,response);
			return ;
		}
		Login Ig = new Login();
		Login.setUname(uname);
		Login.setEmail(email);
		Login.setPword(pword);
		
		LoginDao ld = new LoginDao();
		try {
			if(!ld.validateNewUser(Ig))
			{
				LoginDao login = new LoginDao();
				Connection con = login.getConnection();
				String sqlQuery = "insert into userdb (username,password,email) values (?,?,?)";
				PreparedStatement ps;
				ps = con.prepareStatement(sqlQuery);
				
				ps.setString(1, Login.getUname());
				ps.setString(2, Login.getPword());
				ps.setString(3, Login.getEmail());
				
				ps.executeUpdate();
				request.setAttribute("alert", "Registration Successfull");
				request.getRequestDispatcher("frontpage.jsp").forward(request,response);
				response.sendRedirect("frontpage.jsp");
			}
			else
			{
				request.setAttribute("alert", "username/password is already taken");
				request.getRequestDispatcher("newuser.jsp").forward(request,response);
				
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			PrintWriter pw = response.getWriter();
			pw.println("New user servlet Error");
		}
	}

}
