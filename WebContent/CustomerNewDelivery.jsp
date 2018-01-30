<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.vigneshb.restaurants.delegates.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Order new food</title>
	<link rel="stylesheet" href="<c:url value = "styles/Style.css" />">
	<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:bundle basename="org.vigneshb.restaurants.properties.config">
		<fmt:message key="newOrder" var="newOrder" />
	</fmt:bundle>
	<span>Welcome <%=session.getAttribute("userName")%></span>
	<nav> <a href="CustomerHomePage.jsp">Home</a> <a
		href="CustomerNewDelivery.jsp" class="active">Make an order</a> <a
		href="CustomerDeliveriesStatus.jsp">Check status of your
		deliveries</a> <a href="LogoutServlet?option=1">Logout</a></nav>
	<div>
		<h3>${newOrder}</h3>
		<form method="post" action="CustomerServlet">
			<input type="hidden" name="option" value="2">
			<label>FoodName:</label> 
			<select name="foodName">
				<%
					DeliveryManagerDelegate deliveryManagerDelegate = new DeliveryManagerDelegate();
					String[] foodNames = deliveryManagerDelegate.getFoodNames();
					int foodCount = deliveryManagerDelegate.getFoodCount();
					for (int count = 0; count < foodCount; count++) {
				%>
				<option value="<%=foodNames[count]%>"><%=foodNames[count]%></option>
				<%
					}
				%>
			</select><br> <input type="text" name="foodCount" placeholder="quantity" pattern = "[0-9]{1,2}" title = "numbers only" required><br>
			<input type="submit" value="Add More">
		</form>
	</div>
	<div class="customer-order-page">
		<img src="images/customerHomePage.jpg" /> 
		<img src="images/customerHomePage1.jpg" /> 
		<img src="images/customerHomePage2.jpg" /> 
		<img src="images/customerHomePage3.jpg" />
	</div>
	<table>
		<tr>
			<th>FoodItem</th>
			<th>Count</th>
			<th>Cancel the Dish</th>
		</tr>
		<c:if test =  "${sessionScope.orderedFoods != null}">
			${sessionScope.orderedFoods}
		</c:if>
	</table>
	<div>
		<form method="post" action="CustomerServlet">
			<input type="hidden" name="option" value="3"> <input
				type="submit" value="CONFIRM ORDER">
		</form>
	</div>
</body>
</html>