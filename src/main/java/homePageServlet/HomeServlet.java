package homePageServlet;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import demologin.Login;
import demologininner.LoginDao;
@MultipartConfig
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String uploadPathImg = "E:/eclipse projects/demo/src/main/webapp/images/";
		String uploadPathDocs = "E:/eclipse projects/demo/src/main/webapp/docs/";
		
		Part file = request.getPart("photo");
		String imgName = file.getSubmittedFileName();
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String dob = request.getParameter("dob");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		String college = request.getParameter("college");
		String yearOfPassing = request.getParameter("yearOfPassing");
		String rollNumber = request.getParameter("rollNumber");
		String clgCGPA = request.getParameter("clgCGPA");
		String tenthPercentage = request.getParameter("tenthPercentage");
		String twelvethPercentage = request.getParameter("twelvethPercentage");
		String diplomaPercentage = request.getParameter("diplomaPercentage");
		String pg = request.getParameter("pg");
		
		Part resume = request.getPart("resume");
		String resumeName = resume.getSubmittedFileName();
		
		if(Double.parseDouble(clgCGPA) > 10 && Double.parseDouble(clgCGPA) < 0)
		{
			request.setAttribute("message", "Enter a valid College CGPA");
			request.getRequestDispatcher("homepage.jsp").forward(request,response);
			return;
		}
		else if(Double.parseDouble(tenthPercentage) > 100 && Double.parseDouble(tenthPercentage) < 0)
		{
			request.setAttribute("message", "Enter a valid 10th Percentage");
			request.getRequestDispatcher("homepage.jsp").forward(request,response);
			return;
		}
		else if(Double.parseDouble(twelvethPercentage) > 100 && Double.parseDouble(twelvethPercentage) < 0)
		{
			request.setAttribute("message", "Enter a valid 12th Percentage");
			request.getRequestDispatcher("homepage.jsp").forward(request,response);
			return;
		}
		else if(Double.parseDouble(diplomaPercentage) > 100 && Double.parseDouble(diplomaPercentage) < 0 )
		{
			request.setAttribute("message", "Enter a valid Diploma Percentage");
			request.getRequestDispatcher("homepage.jsp").forward(request,response);
			return;
		}
		else if(Double.parseDouble(pg) > 10 && Double.parseDouble(pg) < 0)
		{
			request.setAttribute("message", "Enter a valid PG CGPA");
			request.getRequestDispatcher("homepage.jsp").forward(request,response);
			return;
		}

		//Photo uploading to respected folder
		String uploadPath = uploadPathImg + imgName;
		FileOutputStream fos = new FileOutputStream(uploadPath);
		InputStream is = file.getInputStream();
		byte[] data = new byte[is.available()];
		is.read(data);
		fos.write(data);
		fos.close();
		//rename
		File photoName = new File(uploadPath);
		String photoRenamePath = uploadPathImg + Login.getUname() + imgName.substring(imgName.lastIndexOf('.'));
		File photoRenameTo = new File(photoRenamePath);
		photoName.renameTo(photoRenameTo);
		
		//Resume uploading to Respected Folder
		String uploadPath2 = uploadPathDocs + resumeName;
		FileOutputStream fos2 = new FileOutputStream(uploadPath2);
		InputStream is2 = resume.getInputStream();
		byte[] data2 = new byte[is2.available()];
		is2.read(data2);
		fos2.write(data2);
		fos2.close();
		
		//rename
		File resumePathName = new File(uploadPath2);
		String resumeRenamePath = uploadPathDocs + Login.getUname() + resumeName.substring(resumeName.lastIndexOf('.'));
		File resumeRenameTo = new File(resumeRenamePath);
		resumePathName.renameTo(resumeRenameTo);
		
		Login.setPhoto(Login.getUname() + imgName.substring(imgName.lastIndexOf('.')));
		Login.setFirstName(firstName);
		Login.setLastName(lastName);
		Login.setYearOfPassing(Integer.parseInt(yearOfPassing));
		Login.setDob(dob);
		Login.setPhoneNumber(phoneNumber);
		Login.setAddress(address);
		Login.setCollege(college);
		Login.setRollNumber(rollNumber);
		Login.setClgCGPA(Double.parseDouble(clgCGPA));
		Login.setTenthPercentage(Double.parseDouble(tenthPercentage));
		Login.setTwelvethPercentage(Double.parseDouble(twelvethPercentage));
		Login.setDiplomaPercentage(Double.parseDouble(diplomaPercentage));
		Login.setPg(Double.parseDouble(pg));
		Login.setResume(Login.getUname() + resumeName.substring(resumeName.lastIndexOf('.')));
		try
		{
			LoginDao login = new LoginDao();
			Connection con = null;
			con = login.getConnection();
					
			if(con == null)
			{ 
				pw.println("error connecting to database");
			}
			else
			{
				PreparedStatement ps;	
				
				String sqlQuery = "UPDATE userdb SET photo = '"+Login.getPhoto()+"', firstName = '"+Login.getFirstName()+"', lastName = '"+Login.getLastName()+"', yearOfPassing = '"+Login.getYearOfPassing()+"', dob = '"+Login.getDob()+"', phoneNumber = '"+Login.getPhoneNumber()+"', address = '"+Login.getAddress()+"', college = '"+Login.getCollege()+"', rollNumber = '"+Login.getRollNumber()+"', clgCGPA = '"+Login.getClgCGPA()+"', tenthPercentage = '"+Login.getTenthPercentage()+"', twelvethPercentage = '"+Login.getTwelvethPercentage()+"', diplomaPercentage = '"+Login.getDiplomaPercentage()+"', pg = '"+Login.getPg()+"', resume = '"+Login.getResume()+"'  WHERE username = '"+Login.getUname()+"'";
				
				ps = con.prepareStatement(sqlQuery);	
								
				ps.executeUpdate();
				
				sqlQuery = "select * from userdb where username = '"+Login.getUname()+"'";
				ps = con.prepareStatement(sqlQuery);;
				ResultSet rs = ps.executeQuery(sqlQuery);
				
				request.setAttribute("data",rs);
				request.setAttribute("message","You have submitted the Application form successfully!!!");
				request.getRequestDispatcher("homepage2.jsp").forward(request,response);			
			}
		}
		catch(Exception e)
		{
			pw.println("Home Servlet Error");
		}

		
 	}

}
