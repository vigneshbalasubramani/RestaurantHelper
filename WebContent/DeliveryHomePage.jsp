<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>DELIVERY HOME PAGE</title>
	<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:bundle basename="org.vigneshb.restaurants.properties.config" >
		<fmt:message key = "addNewUser" var = "addNewUser"/>
	</fmt:bundle>
	<span>Welcome <%= session.getAttribute("userName") %></span>
	<nav><a href = "DeliveryHomePage.jsp" class = "active">Home</a><a href = "NewDelivery.jsp">New Delivery</a><a href = "DeliveryPage.jsp">Check Pending Deliveries</a><a href = "DeliveryCheckPage.jsp">Check Delivery Speed</a><a href = "LogoutServlet?option=1">Logout</a></nav>
	<span>${addNewUser}</span>
	<br>
	<br>
	<div>
		<form method = "post" action = "DeliveryManagerServlet">
			<input type = "hidden" name = "option" value = "1"/>
			<i class = 'fa fa-user-plus'></i><input type = "text" placeholder = "user name" name = "userName" pattern = "[a-z A-Z]+" title = "alphabets only" required/><br>
			<i class = 'fa fa-lock'></i><input type = "text" placeholder = "password" name = "password" pattern = ".{6,}" title = "atleast 6 characters" required/><br>
			<i class = 'fa fa-phone'></i><input type = "text" placeholder = "phone number" name = "phone" pattern = "[0-9]{10}" title = "10 digits only" required/><br>
			<input type = "submit" value = "submit"/>
		</form>
	</div>
</body>
</html>