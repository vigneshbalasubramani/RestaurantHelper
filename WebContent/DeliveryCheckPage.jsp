<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Delivery Speed Check</title>
	<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
	<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:bundle basename="org.vigneshb.restaurants.properties.config" >
		<fmt:message key = "newOrder" var = "newOrder"/>
	</fmt:bundle>
	<span>Welcome <%= session.getAttribute("userName") %></span>
	<nav><a href = "DeliveryHomePage.jsp">Home</a><a href = "NewDelivery.jsp">New Delivery</a><a href = "DeliveryPage.jsp">Check Pending Deliveries</a><a href = "DeliveryCheckPage.jsp" class = "active">Check Delivery Speed</a><a href = "LogoutServlet?option=1">Logout</a></nav>
	<div>
		<h3>${newOrder}</h3>
		<form method = "post" action = "DeliveryManagerServlet">
			<input type = "hidden" name = "option" value = "5"/>
			<label>Order type :</label>
			<select name = "deliveryType">
				<option value = "parcel">Parcel</option>
				<option value = "delivery">Delivery</option>
			</select>
			<input type = "submit" value = "Check Delivery Speed"/>
		</form>
	</div>
	<img src = "images/timer.gif" class = "delivery delivery-image"/>
</body>
</html>