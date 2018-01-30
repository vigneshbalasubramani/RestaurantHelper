<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ page import = "org.vigneshb.restaurants.delegates.*" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>customer deliveries status</title>
<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:bundle basename="org.vigneshb.restaurants.properties.config" >
		<fmt:message key = "viewOrders" var = "viewOrders"/>
	</fmt:bundle>
	<span>Welcome <%= session.getAttribute("userName") %></span>
	<nav><a href = "CustomerHomePage.jsp">Home</a><a href = "CustomerNewDelivery.jsp">Make an order</a><a href = "CustomerDeliveriesStatus.jsp" class = "active">Check status of your deliveries</a><a href = "LogoutServlet?option=1">Logout</a></nav>
	<div>
		<h3>${viewOrders}</h3>
		<form method = "post" action = "CustomerServlet">
			<input type = "hidden" name = "option" value = "4"/>
			<input type = "submit" value = "CHECK REMAINING ORDERS"/>
		</form>
	</div>
	<table>
		<tr>
			<th>Sno</th>
			<th>Order ID</th>
			<th>Food Name</th>
			<th>count</th>
		</tr>
		<c:if test =  "${sessionScope.orders != null}">
			${sessionScope.orders}
		</c:if>
	</table>
	<div>
		<h3>Delete an order</h3>
		<form method = "post" action = "CustomerServlet">
			<input type = "hidden" name = "option" value = "5"/>
			<select name = "orderNumber">
				<%
					CustomerDelegate customerDelegate = new CustomerDelegate();
					int[] orderIds = customerDelegate.getDeliveryIds(session.getAttribute("userName").toString());
					int orderCount = customerDelegate.getDeliveryCount(session.getAttribute("userName").toString());
					for (int count = 0; count < orderCount; count++) {
				%>
				<option value="<%=orderIds[count]%>"><%=orderIds[count]%></option>
				<%
					}
				%>
			</select>
			<input type = "submit" value = "DELETE THE ORDER"/>
		</form>
	</div>
</body>
</html>