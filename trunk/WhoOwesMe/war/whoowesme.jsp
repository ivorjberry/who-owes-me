<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="style.css" rel="stylesheet" type="text/css">
		<title>Who Owes Me?</title>
	</head>
	<body>
		<center>
			<h1>Who Owes Me</h1>
			<%
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			
			if(user == null){
				String url = userService.createLoginURL(request.getRequestURI());
			
			%>
				<p>
					<span>
						Hello, <a href="<%= url %>">Please sign in</a>
					</span>
				</p>
				
			<%
			}
			else{
			%>
				<p>
					<span>
						Hello <b><%= user.getNickname() %></b>!
					</span>
				</p>
				
				<form action="/sign" method="post">
					<textarea name="list" rows="1" cols="60"></textarea><br>
					<input type="submit" value="Add to Grocery List">
				</form>
			<%}%>
						
		</center>
	</body>
</html>