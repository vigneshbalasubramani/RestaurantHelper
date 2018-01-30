<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ page import = "org.vigneshb.restaurants.delegates.*" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OFFER PAGE</title>
<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:bundle basename="org.vigneshb.restaurants.properties.config" >
		<fmt:message key = "addNewOffer" var = "addNewOffer"/>
		<fmt:message key = "deleteOffer" var = "deleteOffer"/>
	</fmt:bundle>
	<span>Welcome <%= session.getAttribute("userName") %></span>
	<nav><a href = "AdminHomePage.jsp">Home</a><a href = "AdminNewFood.jsp">New Food Item</a><a href = "AdminNewOffer.jsp" class = "active">New Offer</a><a href = "LogoutServlet?option=1">Log Out</a></nav>
	<div>
		<h3>${addNewOffer}</h3>
		<form method = "post" action = "RestaurantManagerServlet">
			<input type = "hidden" name = "option" value = "3"/>
			<input type = "submit" value = "ADD AUTOMATICALLY"/><br><br>
		</form>
		<form method = "post" action = "RestaurantManagerServlet">
			<input type = "hidden" name = "option" value = "5"/>
			<label>Food Name 1 :</label>
			<select name = "foodName1">
				<% DeliveryManagerDelegate deliveryManagerDelegate = new DeliveryManagerDelegate();
					String[] foodNames = deliveryManagerDelegate.getFoodNames();
					int foodCount =  deliveryManagerDelegate.getFoodCount();
					for(int count = 0; count < foodCount; count++){
				%>
				<option value = "<%= foodNames[count] %>"><%= foodNames[count]%></option>
				<% } %>
			</select><br>
			<label>Food Name 2 :</label>
			<select name = "foodName2">
				<% for(int count = 0; count < foodCount; count++){
				%>
				<option value = "<%= foodNames[count] %>"><%= foodNames[count]%></option>
				<% } %>
			</select><br>
			<input type = "submit" value = "ADD MANUALLY"/>		
		</form>
	</div>
	<div>
	<h3>${deleteOffer}</h3>
	<form method = "post" action = "RestaurantManagerServlet">
		<input type = "hidden" name = "option" value = "4"/>
		<input type = "submit" value = "DELETE CURRENT OFFER"/>
	</form>
	</div>
</body>
</html>