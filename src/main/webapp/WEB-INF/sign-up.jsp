<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Encheres | Inscription</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	<body>
		<div class="container">
			<p class="display-3">Création de votre compte ! </p>
			
			<c:forEach var="error" items="${errors}">	
				<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
				  ${error}
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    <span aria-hidden="true">&times;</span>
				  </button>
				</div>
			</c:forEach>
			
			<form method="POST" action="./sign-up" class="col-6">
			
				<div class="form-group">
					<label for="pseudoUser">votre pseudo :</label>
					<input type="text" class="form-control" id="pseudoUser" name="pseudoUser" required>
				</div>
				
				<div class="form-group">
					<label for="nameUser">votre nom :</label>
					<input type="text" class="form-control" id="nameUser" name="nameUser" required>
				</div>
				
				<div class="form-group">
					<label for="fistNameUser">votre prenom :</label>
					<input type="text" class="form-control" id="fistNameUser" name="firstNameUser" required>
				</div>
				
				<div class="form-group">
					<label for="mailUser">votre email :</label>
					<input type="email" class="form-control" id="mailUser" name="mailUser" required>
				</div>
				
				<div class="form-group">
					<label for="phoneNumberUser">votre numéro de téléphone :</label>
					<input type="tel" class="form-control" id="phoneNumberUser" name="phoneNumberUser">
				</div>
		
				<div class="form-group">
					<label for="addressUser">votre n° de rue :</label>
					<input type="text" class="form-control" id="addressUser" name="addressUser" required>
				</div>
				
				<div class="form-group">
					<label for="cityUser">votre ville :</label>
					<input type="text" class="form-control" id="cityUser" name="cityUser" required>
				</div>
				
				<div class="form-group">
					<label for="zipCodeUser">votre code postal :</label>
					<input type="text" class="form-control" id="zipCodeUser" name="zipCodeUser" required>
				</div>
				
				<div class="form-group">
					<label for="passwordUser">votre mot de passe :</label>
					<input type="text" class="form-control" id="passwordUser" name="passwordUser" required>
				</div>
				
				<div class="form-group">
					<button type="submit" class="btn btn-success btn-lg" id="btnSignUp" name="btnSignUp">S'inscrire</button>
				</div>
			
			</form>
		
		</div>
		
	</body>
</html>