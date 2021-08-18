<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Encheres | Connexion</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf" %>
		
		<div class="container">
			<p class="display-3">Connectez-vous ! </p>
			
			<c:forEach var="error" items="${errors}">	
				<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
				  ${error}
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    <span aria-hidden="true">&times;</span>
				  </button>
				</div>
			</c:forEach>
			
			<c:if test="${not empty success}">
				<div class="alert alert-success alert-dismissible fade show"
					role="alert" style="width: 35%;">
					${success}
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</c:if>
			
			<form method="POST" action="./sign-in" class="col-6">
			
				<div class="form-group">
					<label for="loginUser">votre pseudo ou email :</label>
					<input type="text" class="form-control" id="loginUser" name="loginUser" required>
				</div>
				
				<div class="form-group">
					<label for="passwordUser">votre mot de passe :</label>
					<input type="password" class="form-control" id="passwordUser" name="passwordUser" required>
					<a href="./ResetPassword">J'ai oublié mon mot de passe</a>
				</div>
				
				<div class="form-group">
					<button type="submit" class="btn btn-success btn-lg" id="btnSignIn" name="btnSignIn">Connexion</button>
				</div>
			
			</form>
			
			<p class="lead">Vous n'avez pas encore de compte ?</p>
			<a href="./sign-up" class="btn btn-info bt-large">S'inscrire</a>
		
		</div>
		
	</body>
</html>