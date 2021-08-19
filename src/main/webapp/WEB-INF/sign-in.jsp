<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ENI-Auction | Sign in </title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf" %>
		
		<div class="container">
			<p class="display-3">Sign in ! </p>
			
			<c:forEach var="error" items="${errors}">	
				<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
				  ${error}
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    <span aria-hidden="true">&times;</span>
				  </button>
				</div>
			</c:forEach>
			
			<c:if test="${not empty rememberMe }">
				<c:set var="login" value="${rememberMe }"></c:set>
				<c:set var="checked" value="checked"></c:set>
			</c:if>
			<form method="POST" action="./sign-in" class="col-6">
			
				<div class="form-group">
					<label for="loginUser">Pseudo or Mail :</label>
					<input type="text" class="form-control" id="loginUser" name="loginUser" value="${login }" required>
					<label for="rememberMe">Remember me</label>
					<input type="checkbox" name="rememberMe" ${checked}>
				</div>
				
				<div class="form-group">
					<label for="passwordUser">Password :</label>
					<input type="password" class="form-control" id="passwordUser" name="passwordUser" required>
				</div>
				
				<div class="form-group">
					<button type="submit" class="btn btn-success btn-lg" id="btnSignIn" name="btnSignIn">Sign in</button>
				</div>
			
			</form>
			
			<p class="lead">You don't have an account ?</p>
			<a href="./sign-up" class="btn btn-info bt-large">Sign up</a>
		
		</div>
		
	</body>
</html>