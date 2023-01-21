<%@ page import="java.sql.*" %>
<html>
       <head>
       <title>Home Page</title>
       <style>
body{
font-family: 'verdana';
}
</style>
       </head>
       <body>
       <div align = "center">
       <h1> APPLICATION FORM </h1>
       <%
		       response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		       if(session.getAttribute("username")==(null))
		   		response.sendRedirect("frontpage.jsp");
		       ResultSet resultset = (ResultSet)request.getAttribute("data");
       %>
      <table border = "1">
      <% if(resultset.next()){ %>
      <tr><td>Photo : </td><td align = "center"><img src = "images/<%= resultset.getString("photo") %>" width="150" height="150"> </td></tr>
      <tr><td>First Name : </td><td><%= resultset.getString("firstName") %></td></tr>
      <tr><td>Last Name : </td><td><%= resultset.getString("lastName") %> </td></tr>
      <tr><td>Email : </td><td><%= resultset.getString("email") %></td></tr>
      <tr><td>Date of Birth : </td><td><%= resultset.getString("dob") %></td></tr>
      <tr><td>Phone Number : </td><td><%= resultset.getString("phoneNumber") %></td></tr>
      <tr><td>Address : </td><td><%= resultset.getString("address") %></td></tr>
      <tr><td>College : </td><td><%= resultset.getString("college") %></td></tr>
      <tr><td>Year of Passing : </td><td><%= resultset.getInt("yearOfPassing") %></td></tr>
      <tr><td>Roll Number : </td><td><%= resultset.getString("rollNumber") %></td></tr>
      <tr><td>College CGPA : </td><td><%= resultset.getDouble("clgCGPA") %></td></tr>
      <tr><td>10th Percentage : </td><td><%= resultset.getDouble("tenthPercentage") %></td></tr>
      <tr><td>12th Percentage : </td><td><%= resultset.getDouble("twelvethPercentage") %></td></tr>
      <tr><td>Diploma Percentage : </td><td><%= resultset.getDouble("diplomaPercentage") %></td></tr>
      <tr><td>PG CGPA : </td><td><%= resultset.getDouble("pg") %></td></tr>
      <tr><td>Resume : </td><td><a href = "docs/<%= resultset.getString("resume") %>" download>Download</a></td></tr>
       <% } else { %>
       	<center>No Data found</center>
       <%} %>
       <form action = "logout" method = "post">
       <tr><td></td><td align = "center">
       <input type = "submit" value = "logout"></td></tr>
       </form>
     </table>
     <br>
     <font color = "red">${message}</font>
     </div>
     </body>
</html>