<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
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
			<p class="display-3">Réinitialiser mon mot de passe </p>
			
			<c:forEach var="error" items="${errors}">	
				<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
				  ${error}
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    <span aria-hidden="true">&times;</span>
				  </button>
				</div>
			</c:forEach>
			
			<div class="alert alert-info alert-dismissible fade show" role="alert" style="width:35%;">
			  Vous allez recevoir un lien valable 24h pour créer un nouveau mot de passe. <br>
			   Passé ce délai, vous devrez renouveller votre demande.
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
			
			<form method="POST" action="./ResetPassword" class="col-6">
				<div class="form-group">
					<label for="emailUser">votre email :</label>
					<input type="email" class="form-control" id="emailUser" name="emailUser" required>
				</div>	
				<div class="form-group">
					<button type="submit" class="btn btn-success btn-lg" id="btnResetPassword" name="btnResetPassword">Réinitialiser</button>
				</div>
			</form>
			
		</div>
	</body>
</html>