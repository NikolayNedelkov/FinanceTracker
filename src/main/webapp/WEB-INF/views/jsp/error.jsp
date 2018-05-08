<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page isErrorPage = "true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Houston, we've got a problem!</title>
<style>
 #photo{
    margin: auto;
    width: 50%;
 }


</style>
</head>
<body>

	<h1>Houston, we've got a problem!</h1>
	<pre><% exception.printStackTrace(response.getWriter()); %></pre>

	<button type="submit" class="btn btn-primary" data-dismiss="modal">Back to the homepage</button>
	
	 <img id = "photo" src="https://preview.ibb.co/k9uQ4S/error.jpg">
</body>
</html>