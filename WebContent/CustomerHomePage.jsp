<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CUSTOMER HOME PAGE</title>
<link rel="stylesheet" href="<c:url value = "styles/Style.css" />">
<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:bundle basename="org.vigneshb.restaurants.properties.config">
		<fmt:message key="viewMenu" var="viewMenu" />
		<fmt:message key="viewOffer" var="viewOffer" />
	</fmt:bundle>
	<span>Welcome <%=session.getAttribute("userName")%></span>
	<nav>
	<a href="CustomerHomePage.jsp" class="active">Home</a>
	<a href="CustomerNewDelivery.jsp">Make an order</a>
	<a href="CustomerDeliveriesStatus.jsp">Check status of your
		deliveries</a>
	<a href="LogoutServlet?option=1">Logout</a></nav>
	<%
		String menu, offer;
		if (session.getAttribute("menu") == null) {
			menu = "";
		} else {
			menu = session.getAttribute("menu").toString();
		}
		if (session.getAttribute("offer") == null) {
			offer = "";
		} else {
			offer = session.getAttribute("offer").toString();
		}
	%>
	<div>
		<h3>${viewMenu}</h3>
		<form method="post" action="CustomerServlet">
			<input type="hidden" name="option" value="1" /> <input type="submit"
				value="VIEW MENU" />
		</form>
	</div>
	<table><%=menu%></table>
	<marquee><%=offer%></marquee>
</body>
</html>