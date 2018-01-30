<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>ORDER A NEW DELIVERY</title>
	<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
	<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:bundle basename="org.vigneshb.restaurants.properties.config" >
		<fmt:message key = "newOrder" var = "newOrder"/>
	</fmt:bundle>
	<span>Welcome <%= session.getAttribute("userName") %></span>
	<nav><a href = "DeliveryHomePage.jsp">Home</a><a href = "NewDelivery.jsp" class = "active">New Delivery</a><a href = "DeliveryPage.jsp">Check Pending Deliveries</a><a href = "DeliveryCheckPage.jsp">Check Delivery Speed</a><a href = "LogoutServlet?option=1">Logout</a></nav>
	<h1>${newOrder}</h1>
	<div>
		<form method = "post" action = "DeliveryManagerServlet">
			<input type = "hidden" name = "option" value = "2"/>
			<i class = 'fa fa-user'></i><input type = "text" placeholder = "user name" name = "userName" pattern = "[a-z A-Z]+" title = "alphabets only" required/>
			<label>Delivery type :</label>
			<select name = "orderType">
				<option value = "dine-in">Dine in</option>
				<option value = "parcel">Parcel</option>
				<option value = "delivery">Delivery</option>
			</select><br>
			<input type = "submit" value = "CONFIRM ORDER"/>
		</form>
	</div>
</body>
</html>