<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "org.vigneshb.restaurants.delegates.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Requesting Delivery Details</title>
	<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
	<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<div>
		<form method = "post" action = "DeliveryManagerServlet">
			<input type =  "hidden" name = "option" value = "6">
			<label>FoodName :</label>
			<select name = "foodName">
				<% DeliveryManagerDelegate deliveryManagerDelegate = new DeliveryManagerDelegate();
					String[] foodNames = deliveryManagerDelegate.getFoodNames();
					int foodCount =  deliveryManagerDelegate.getFoodCount();
					for(int count = 0; count < foodCount; count++){
				%>
				<option value = "<%= foodNames[count] %>"><%= foodNames[count]%></option>
				<% } %>
			</select><br>
			<input type = "text" name = "foodCount" placeholder = "quantity" pattern = "[0-9]{1,2}" title = "numbers only" required><br>
			<input type = "submit" value = "Add More">
		</form>
	</div>
	<table><tr><th>FoodItem</th><th>Count</th><th>Cancel the Dish</th></tr> <%= session.getAttribute("orderedFoods") %></table>
	<div>
		<form method = "post" action = "DeliveryManagerServlet">
			<input type = "hidden" name = "option" value = "7">
			<input type =  "submit" value = "CONFIRM ORDER">
		</form>
	</div>
</body>
</html>