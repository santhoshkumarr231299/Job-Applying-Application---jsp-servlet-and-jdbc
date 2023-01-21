package demologininner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demologin.Login;

public class LoginDao {
	private String dbUrl = "jdbc:mysql://localhost:3306/userdb";
	private String dbUname = "root";
	private String dbPassword = "";
	 public void loadDriver(String dbDriver) throws InstantiationException, IllegalAccessException
	 {
		 try
		 {
		 Class.forName(dbDriver);
		 }
		 catch(ClassNotFoundException e)
		 {
			 
		 }
	 }
	 
	 public Connection getConnection() throws SQLException
	 {
		 Connection con = null;
		 con = DriverManager.getConnection(dbUrl,dbUname,dbPassword);
		 return con;
	 }
	 
	 public boolean validateLogin(Login lg) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
	 {
		 loadDriver("com.mysql.cj.jdbc.Driver");
		 Connection con = getConnection();

		 String sqlQuery = "select * from userdb where username = ? and password = ?";
		 
		 PreparedStatement ps;
		 ps = con.prepareStatement(sqlQuery);
		 
		 ps.setString(1, Login.getUname());
		 
		 ps.setString(2, Login.getPword());
		 
		 
		 
		 ResultSet rs = ps.executeQuery();
		 
		 return rs.next();
	 }
	 
public boolean validateNewUser(Login lg) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
{
	 loadDriver("com.mysql.cj.jdbc.Driver");
	 Connection con = getConnection();

	 String sqlQuery = "select * from userdb where username = ? or email = ?";
	 
	 PreparedStatement ps;
	 ps = con.prepareStatement(sqlQuery);
	 
	 ps.setString(1, Login.getUname());
	 
	 ps.setString(2, Login.getEmail());
	 
	 ResultSet rs = ps.executeQuery();
	 
	 return rs.next();
}
public boolean validateHomePage(Login Ig) throws InstantiationException, IllegalAccessException, SQLException
{
	loadDriver("com.mysql.cj.jdbc.Driver");
	Connection con = getConnection();
	PreparedStatement ps;
	
	String sqlQuery = "select * from userdb where username = '"+Login.getUname()+"' and firstName is not null";
	
	ps = con.prepareStatement(sqlQuery);
	
	ResultSet rs = ps.executeQuery();
	
	return rs.next();
}
}