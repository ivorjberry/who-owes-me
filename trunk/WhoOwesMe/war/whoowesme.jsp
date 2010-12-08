<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="whoowesme.Grocery" %>
<%@ page import="whoowesme.PMF" %>
<%@ page import="java.util.List" %>

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
				String url = userService.createLogoutURL(request.getRequestURI());
			%>
				<p>
					<span>
						Hello <b><%= user.getNickname() %></b>!<br>
						<a href="<%= url %>">LOGOUT</a>
					</span>
				</p>
				
				<h2>SUBMIT BILL</h2>
				<form action="/updateOwe" method="post">
					Amount---->	<input type="text" name="amount"><br>
					Who------->	<input select name="dropdown"><br>
					What For-->	<input type="text" name="itemName"><br>
						
					<input type="submit" value="Submit">
				</form>
				
				<br><br>
				<h2>GROCERY LIST</h2>
				<form action="/addToGrocery" method="post">
					<textarea name="list" rows="1" cols="60"></textarea><br>
					<input type="submit" value="Add to Grocery List">
				</form>
				
				<% 
				PersistenceManager pm = PMF.get().getPersistenceManager();
	  		    String query = "select from whoowesme.Grocery";
	  		    List<Grocery> groceryList = (List<Grocery>)pm.newQuery(query).execute();
      
				%>
				
				<div id="msgs">
				<% 
		        if(groceryList.isEmpty()){
				%>
					<p>The grocery list is empty.</p>

				<% } 
       			else{ 
       			%>
       				<p>
       			<%
       				for(Grocery g:groceryList){
       			%>
       					<%= g.getMsg() %> -- <%= g.getAuthor() %> <%= g.getDate()%> <br>
					<%} %>
       				</p>
       				
				<%} %>
				</div>
			<%} %>
						
		</center>
	</body>
</html>