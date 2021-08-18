<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Encheres | Création de mon nouveau mot de passe</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf" %>
		
		<div class="container">
			<p class="display-3">Création du nouveau mot de passe </p>
			
			<c:forEach var="error" items="${errors}">	
				<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
				  ${error}
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    <span aria-hidden="true">&times;</span>
				  </button>
				</div>
			</c:forEach>
			
			<form method="POST" action="./NewPassword" class="col-6">
				<div class="form-group">
					<label for="passwordUser">votre nouveau mot de passe :</label>
					<input type="password" class="form-control" id="passwordUser" name="passwordUser" required>
				</div>	
				<div class="form-group">
					<label for="passwordUserConfirm">confirmer mot de passe :</label>
					<input type="password" class="form-control" id="passwordUserConfirm" name="passwordUserConfirm" required>
				</div>	
				
				<input type="hidden" name="tokenUser" value="${token}">
				<input type="hidden" name="pseudoUser" value="${pseudo}">
				<div class="form-group">
					<button type="submit" class="btn btn-success btn-lg" id="btnNewPassword" name="btnNewPassword">Réinitialiser</button>
				</div>
			</form>
		</div>
	</body>
</html>