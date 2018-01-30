<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page import = "org.vigneshb.restaurants.delegates.*" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Pending Deliveries</title>
	<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
	<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:bundle basename="org.vigneshb.restaurants.properties.config" >
		<fmt:message key = "viewOrders" var = "viewOrders"/>
	</fmt:bundle>
	<span>Welcome <%= session.getAttribute("userName") %></span>
	<nav><a href = "DeliveryHomePage.jsp">Home</a><a href = "NewDelivery.jsp">New Delivery</a><a href = "DeliveryPage.jsp" class = "active">Check Pending Deliveries</a><a href = "DeliveryCheckPage.jsp">Check Delivery Speed</a><a href = "LogoutServlet?option=1">Logout</a></nav>
	<div>
		<h3>${viewOrders}</h3>
		<form method = "post" action = "DeliveryManagerServlet">
			<input type = "hidden" name = "option" value = "3"/>
			<input type = "hidden" name = "typeOfOrder" value = "parcel"/>
			<select name = "userName">
				<% DeliveryManagerDelegate deliveryManagerDelegate = new DeliveryManagerDelegate();
					String[] userNames = deliveryManagerDelegate.getCustomersWithOrders("parcel");
					int userCount =  userNames.length;
					for(int count = 0; count < userCount; count++){
				%>
				<option value = "<%= userNames[count] %>"><%= userNames[count]%></option>
				<% } %>
			</select>
			<input type = "submit" value = "Check Pending Parcels"/>
		</form>
	</div>
	<img src = "images/delivery.gif" class = "delivery delivery-image"/>
	<table>
		<tr>
			<th>Sno</th>
			<th>Order ID</th>
			<th>Food Name</th>
			<th>count</th>
		</tr>
		<c:if test =  "${deliveries != null}">
			${deliveries}
		</c:if>
	</table>
	<div>
		<form method = "post" action = "DeliveryManagerServlet">
			<input type = "hidden" name = "option" value = "4"/>
			<input type = "hidden" name = "typeOfOrder" value = "parcel"/>
			<select name = "userName">
				<% for(int count = 0; count < userCount; count++){
				%>
				<option value = "<%= userNames[count] %>"><%= userNames[count]%></option>
				<% } %>
			</select>
			<input type = "submit" value = "Update Parcel Status"/>
		</form>
	</div>
</body>
</html>