<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html>
<head>
	<title>evalApp</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- Reference Bootstrap files -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
</head>

<body>
	<h2>evalApp page d'acceuil</h2>
	<hr>
	
	<security:authorize access="isAnonymous()">
		<a href="${pageContext.request.contextPath}/login">Connection</a>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
	
		<h3>Bonjour <security:authentication property="principal.username" /> !</h3>
		Role(s) : <security:authentication property="principal.authorities" />
		
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<button type="submit" class="btn btn-danger">Déconnection</button>
		</form:form>

	</security:authorize>

	
</body>
</html>
