<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="whoowesme.Grocery" %>
<%@ page import="whoowesme.Bill" %>
<%@ page import="whoowesme.House" %>
<%@ page import="whoowesme.PMF" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

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
			List <House> myHouses = new ArrayList<House>();

			String url = userService.createLogoutURL(request.getRequestURI());
		
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String house = "select from whoowesme.House";
			//pm.newQuery(house).deletePersistentAll();
			List<House> houseList = (List<House>)pm.newQuery(house).execute();
			
			
			//create add house form
			%>
			<form action = "/addHouse" method="post">
				<input type="text" name="housename">
				<input type = "submit" value = "Add House">
			</form>
			<%
			
			if(!houseList.isEmpty())
			{
				for(House myhouse:houseList)
				{
					
					int numMembers = myhouse.getNumHouseMates();
					for(int i = 0; i < numMembers; i++)
					{
						if(myhouse.getHouseMate(i).equalsIgnoreCase(user.getNickname()))
						{
							myHouses.add(myhouse);
						}
					}
				}
			}
			
			if(!myHouses.isEmpty()){
				for(House h:myHouses){
					List<String> housemates = h.getAllHouseMates();
					//get all members
					%>
					<h2>House Name: <%=h.getHouseName() %></h2> 
					<p>
						<span>
							Hello <b><%= user.getNickname() %></b>!<br>
							<a href="<%= url %>">LOGOUT</a>
						</span>
					</p>
					<br>
					<form action = "/addHouseMate" method="post">
						<input type="hidden" name="housename" value="<%=h.getHouseName() %>">
						<input type="text" name="name">
						<input type = "submit" value = "Add House Mate">
					</form>
					<br><br>
					<h2>SUBMIT BILL or PAYMENT</h2>
						<form action="/addBill" method="post">
						<input type="hidden" name="housename" value="<%=h.getHouseName() %>">
						Amount---->	<input type="text" name="amount"><br>
						Who------->	
						<select name="myhousemates">
							<%
								for(String s: housemates)
								{%>
									<option value="<%=s %>"><%=s %></option>
								<%} 
							%>
						</select><br>
						What For-->	<input type="text" name="itemName"><br>
						
						<input type="submit" value="Submit Bill">
					</form>
					
					<h2>Who Owes You</h2>
					<%
					//print out who owes user means bills that are owed to user
					List<Bill> owes = h.getOwed(user);
					
					if(owes.isEmpty())
					{
						%><p>No one owes you!</p><%
					}
					else
					{
						%>
						<p>
	       				<table>
	       				<tr><td width="300"><b>Who Owes You</b></td><td width="200"><b>Amount</b></td><td width="300"><b>What For</b></td>
	       				<%
	       				for(Bill b:owes){
	       					double billAmt = b.getAmt();
	       					if (billAmt != 0  ){
		       				%>
		       					<tr>
		       					<td><%= b.getOwes() %></td><td><%= b.getAmt() %></td><td><%= b.getItem() %></td>
								</tr>
							<%} 
						}%>
						</table>
					<%} %>
					<h2>Who You Owe</h2>
					<%
					//print out who user owes
					List<Bill> owed = h.getOwes(user);
					
					if(owed.isEmpty())
					{
						%><p>You're all paid up!</p><%
					}
					else
					{
						%>
						<p>
						<table>
						<tr><td width = "300"><b>Who You Owe</b></td><td width ="200"><b>Amount</b></td><td width="300"><b>What For</b></td>
						<%
						for(Bill b:owed){
		       			%>
	   						<tr>
	   						<td><%= b.getOwed() %></td><td><%= b.getAmt() %></td><td><%= b.getItem() %></td>
							</tr>
						<%} %>
						</table>
					<%} %>
					<br><br>
					<h2>GROCERY LIST</h2>
					<form action="/addToGrocery" method="post">
						<input type="hidden" name="housename" value="<%=h.getHouseName() %>">
						<textarea name="msg" rows="1" cols="60"></textarea><br>
						<input type="submit" value="Add to Grocery List">
					</form>
					
					<% 
		  		    List<Grocery> groceryList = h.getGrocery();
	      
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
		       				<tr><td width="500"><b>Item</b></td><td width="200"><b>Who</b></td><td width="300"><b>When</b></td></tr>
		       			<%
		       				for(Grocery g:groceryList){
		       			%>
		       					<tr>
		       					<td><%= g.getMsg() %></td><td><%= g.getAuthor() %></td><td><%= g.getDate()%></td>
								</tr>
							<%} %>
							</table>
	       				</p>
	       				
					<%} %>
					</div>
					<form action="/emptyGrocery" method="post">
						<input type="hidden" name="housename" value="<%=h.getHouseName() %>">
						<input type="submit" value="Empty List">
					</form>
					<br>
					<br>
					
					<form action="/deleteHouse" method="post">
						<input type="hidden" name="housename" value="<%=h.getHouseName() %>">
						<input type="submit" value="Delete This House">
					</form>
					
					<br><br><br>
					----------------------------------------------------------End of House----------------------------------------------------------
					<br><br><br>
					<p>
				<%} %>
			<%} %>
						
		</center>
	</body>
</html>