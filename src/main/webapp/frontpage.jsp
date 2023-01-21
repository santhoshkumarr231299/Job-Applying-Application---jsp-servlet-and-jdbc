<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ZOHO Login</title>
<style>
body{
font-family: 'verdana';
}
</style>
</head>
<body>
<form action = "login" method = "post">
<table bgcolor = "#EFC050" align = "center">
<tr><td align = "center"><h2>ZOHO</h2></td></tr>
<tr><td>Username : </td><td><input type = "text" name = "username" required></td></tr>
<tr><td>Password : </td><td><input type = "password" name = "password" required> </td> </tr>
<tr><td><a href = "newuser.jsp">New User</a></td><td></td></tr>
<tr><td></td><td align = "right"><input type = "submit" value = "login"></td></tr>
</form>
</table>
<br>
<center><font color = "red">${alert}</font></center>

</body>
</html>