<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="whoowesme.Grocery" %>
<%@ page import ="whoowesme.House" %>
<%@ page import = "whoowesme.Bill" %>
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
			House theHouse = null;
			
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
				
				
				

				
				<!--  Need to add display of bills here 
					Maybe should be just a list of who they owe to instead
					of grid
				-->
				<%
				PersistenceManager pm = PMF.get().getPersistenceManager();
				String house = "select from whoowesme.House";
				
				List<House> houseList = (List<House>)pm.newQuery(house).execute();
				
				for(House h:houseList)
				{
					int numPeople = h.getNumPeople();
					for(int i = 0; i < numPeople; i++)
					{
						if(h.getUser(i) == user)
						{
							theHouse = h;
							break;
						}
					}
					if(theHouse != null)
					{
						break;
					}
				}
				
				if(theHouse == null)
				{
					//create add house form
					%>
					<form action = "/addHouse" method="post">
						House Name (Optional)<input type = "text" name = "houseName">
						<input type = "submit" value = "Add House">
					</form>
					<%
				}
				else
				{
					
					%>
					<h2>SUBMIT BILL/PAYMENT</h2>
						<form action="/addBill" method="post">
						Amount---->	<input type="text" name="amount"><br>
						Who------->	<input select name="dropdown"><br>
						What For-->	<input type="text" name="itemName"><br>
						
						<input type="submit" value="Submit Bill">
					</form>
					
					<h2>Who Owes You</h2>
					<%
					//print out who owes user
					List<Bill> owes = theHouse.getOwes(user);
					%>
					<p>
       				<table>
       				<tr><td width="300"><b>Who Owes</b></td><td width="200"><b>Amount</b></td>
       				<%
       				for(Bill b:owes){
       				%>
       					<tr>
       					<td><%= b.getOwes().getNickname() %></td><td><%= b.getAmt() %></td>
						</tr>
					<%} %>
					</table>
   					</p>
					%>
					<h2>Who You Owe</h2>
					<%
					//print out who user owes
					
				}
				%>
				
				<br><br>
				<h2>GROCERY LIST</h2>
				<form action="/addToGrocery" method="post">
					<textarea name="msg" rows="1" cols="60"></textarea><br>
					<input type="submit" value="Add to Grocery List">
				</form>
				
				<% 
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
	       				<table>
	       				<tr><td width="1200"><b>Item</b></td><td width="200"><b>Who</b></td><td width="500"><b>When</b></td></tr>
	       			<%
	       				for(Grocery g:groceryList){
	       			%>
	       					<tr>
	       					<td><%= g.getMsg() %></td><td><%= g.getAuthor().getNickname() %></td><td><%= g.getDate()%></td>
							</tr>
						<%} %>
						</table>
       				</p>
       				
       				
				<%} %>
				</div>
				<form action="/emptyGrocery" method="post">
					<input type="submit" value="Empty List">
				</form>
				<%} %>
						
		</center>
	</body>
</html>