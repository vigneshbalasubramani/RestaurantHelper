<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "org.vigneshb.restaurants.delegates.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin New Food</title>
<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
<fmt:bundle basename="org.vigneshb.restaurants.properties.config" >
		<fmt:message key = "addNewFood" var = "addNewFood"/>
	</fmt:bundle>
<span>Welcome <%= session.getAttribute("userName") %></span>
<nav><a href = "AdminHomePage.jsp">Home</a><a href = "AdminNewFood.jsp" class = "active">New Food Item</a><a href = "AdminNewOffer.jsp">New Offer</a><a href = "LogoutServlet?option=1">Log Out</a></nav>
	<h3>${addNewFood}</h3>
	<div>
	<form method = "post" action = "RestaurantManagerServlet">
		<input type = "hidden" name = "option" value = "2"/>
		<i class = 'fa fa-cutlery'></i><input type = "text" name = "foodName" placeholder = "dish name" pattern = "[a-z A-Z0-9]+" title = "alphabets and numbers only" required/><br>
		<i class = 'fa fa-money'></i><input type = "text" name = "foodCost" placeholder = "food cost" pattern = "[0-9]{1,3}" title = "numbers only" required/><br>
		<input type = "submit" value = "ADD NEW FOOD" id ="addFood" />
	</form>
	</div>
	<div class = "food-list"><h3>MENU</h3>
	<% DeliveryManagerDelegate deliveryManagerDelegate = new DeliveryManagerDelegate();
			String[] foodNames = deliveryManagerDelegate.getFoodNames();
			int foodCount =  deliveryManagerDelegate.getFoodCount();
			for(int count = 0; count < foodCount; count++){
			%> <%= foodNames[count]%> <br> <% } %>
	</div>
</body>
</html>