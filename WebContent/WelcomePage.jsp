<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset = "ISO-8859-1">
<title>Restaurant Helper Application</title>
<link rel = "stylesheet" href = "<c:url value = "styles/Style.css" />" >
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src = "<c:url value = "Scripts/modalFunctions.js"/>"></script>
</head>
<body>
	<fmt:setBundle basename = "org.vigneshb.restaurants.properties.config" var = "resource"/>
	<fmt:message key = "loginMessage" var = "loginMessage" bundle = "${resource}"/>
	<fmt:message key = "SignupMessage" var = "SignupMessage" bundle = "${resource}"/>
	<fmt:message key = "welcome" var = "welcome" bundle = "${resource}"/>
	<fmt:message key = "whatWeWant" var = "whatWeWant" bundle = "${resource}"/>
    <h1><img src = "images/download.gif" width = "7%" height = "auto"/>RESTAURANT CHENNAI</h1>
    <section>
    <div>
    	<h3>${loginMessage} </h3>
   	 	<form method = "post" action = "RestaurantLoginServlet">
        	<i class = 'fa fa-user'></i><input type = "text" placeholder = "user name" name = "userName" pattern = "[a-z A-Z]+" title = "alphabets only" required/><br>
        	<i class = 'fa fa-lock'></i><input type = "password" placeholder = "password" name = "password" pattern = ".{6,}" title = "atleast 6 characters" required/><br>
        	<input type = "submit" value = "LOGIN"/>
    	</form>
    	</div>
    	<img src = "images/welcome.jpg" class = "delivery"/>
    	<div>
    	<h3>${SignupMessage} </h3>
    	<form method = "post" action = "LogoutServlet">
    		<input type = "hidden" name = "option" value = "2"/>
    	    <i class = 'fa fa-user-plus'></i><input type = "text" placeholder = "user name" name = "userName" pattern = "[a-z A-Z]+" title = "alphabets only" required/><br>
    	    <i class = 'fa fa-lock'></i><input type = "password" placeholder = "password" name = "password" pattern = ".{6,}" title = "atleast 6 characters" required/><br>
    	    <i class = 'fa fa-phone'></i><input type = "text" placeholder = "phone number" name = "phone" pattern = "[0-9]{10}" title = "10 digits only" required/><br>		        
    	    <input type = "submit" value = "SIGNUP"/>
    	</form></div>
    	<div><p>${welcome}<br>${whatWeWant}.<br></p></div>
    </section> 
</body>
</html>