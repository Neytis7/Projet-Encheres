<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Encheres | Inscription</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf" %>
		
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
			
			
			<c:if test="${not empty infoUser }">
			
				<c:set var="pseudo" value="${infoUser.pseudo }"></c:set>
				<c:set var="name" value="${infoUser.name }"></c:set>
				<c:set var="firstName" value="${infoUser.first_name }"></c:set>
				<c:set var="email" value="${infoUser.mail }"></c:set>
				<c:set var="phone" value="${infoUser.phone_number }"></c:set>
				<c:set var="address" value="${infoUser.address }"></c:set>
				<c:set var="city" value="${infoUser.zip_code }"></c:set>
				<c:set var="zipcode" value="${infoUser.city }"></c:set>
				<c:set var="password" value="${infoUser.password }"></c:set>
			
			</c:if>
			
			<form method="POST" action="./sign-up" class="col-6">
			
				<div class="form-group">
					<label for="pseudoUser">pseudo :</label>
					<input type="text" class="form-control" id="pseudoUser" name="pseudoUser" value="${pseudo}" required>
				</div>
				
				<div class="form-group">
					<label for="nameUser">nom :</label>
					<input type="text" class="form-control" id="nameUser" name="nameUser" value="${name}" required>
				</div>
				
				<div class="form-group">
					<label for="fistNameUser">prenom :</label>
					<input type="text" class="form-control" id="fistNameUser" name="firstNameUser" value="${firstName}" required>
				</div>
				
				<div class="form-group">
					<label for="mailUser">email :</label>
					<input type="email" class="form-control" id="mailUser" name="mailUser" value="${email}" required>
				</div>
				
				<div class="form-group">
					<label for="phoneNumberUser">numéro de téléphone :</label>
					<input type="tel" class="form-control" id="phoneNumberUser" name="phoneNumberUser" value="${phone}">
				</div>
		
				<div class="form-group">
					<label for="addressUser">n° et nom de rue :</label>
					<input type="text" class="form-control" id="addressUser" name="addressUser" value="${address}" required>
				</div>
				
				<div class="form-group">
					<label for="cityUser">ville :</label>
					<input type="text" class="form-control" id="cityUser" name="cityUser" value="${city}" required>
				</div>
				
				<div class="form-group">
					<label for="zipCodeUser">code postal :</label>
					<input type="text" class="form-control" id="zipCodeUser" name="zipCodeUser" value="${zipcode}" required>
				</div>
				
				<div class="form-group">
					<label for="passwordUser">mot de passe :</label>
					<input type="password" class="form-control" id="passwordUser" name="passwordUser" value="${password}" required>
				</div>
				
				<div class="form-group">
					<button type="submit" class="btn btn-success btn-lg" id="btnSignUp" name="btnSignUp">S'inscrire</button>
				</div>
			
			</form>
			
			<p class="lead">Vous avez déjà un compte ?</p>
			<a href="./sign-in" class="btn btn-info bt-large">Connexion</a>
		
		</div>
		
	</body>
</html>