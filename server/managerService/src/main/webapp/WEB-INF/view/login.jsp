<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

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

	<div>
		<div id="loginbox" style="margin-top: 50px;" class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">Connection</div>
				</div>

				<div style="padding-top: 30px" class="panel-body">
		
					<form:form action="${pageContext.request.contextPath}/authenticateUser" method="POST">
						
						<!-- Place for messages: error, alert etc ... -->
					    <div class="form-group">
					        <div class="col-xs-15">
					            <div>
								<!-- Check for login "error" parameter -->
								<c:if test="${param.error != null}">
									<div class="alert alert-danger col-xs-offset-1 col-xs-10">Nom d'utilisateur ou mot de passe incorrect.</div>
								</c:if>
								
								<!-- Check for logout -->
								<c:if test="${param.logout != null}">		            
									<div class="alert alert-success col-xs-offset-1 col-xs-10">Déconnection.</div>
								</c:if>
								</div>
							</div>
						</div>	
						
						<!-- Username -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
							
							<input type="text" name="app_username" placeholder="username" class="form-control">
						</div>

						<!-- Password -->
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
							
							<input type="password" name="app_password" placeholder="password" class="form-control" >
						</div>

						<!-- Login/Submit Button -->
						<div style="margin-top: 10px" class="form-group">						
							<div class="col-sm-6 controls">
								<button type="submit" class="btn btn-success">Connection</button>
							</div>
						</div>
						
					</form:form>
					
				</div>
			</div>
		</div>
	</div>

</body>
</html>












