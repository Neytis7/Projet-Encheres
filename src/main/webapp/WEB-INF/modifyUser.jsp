<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Encheres | Modifications</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf"%>
		<div class="container">
			<p class="display-4">Modification de vos informations</p>
			
			<c:forEach var="error" items="${errors}">	
				<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
				  ${error}
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    <span aria-hidden="true">&times;</span>
				  </button>
				</div>
			</c:forEach>
			
			<form method="POST" action="./modify-user" class="col-6">
			
				<div class="form-group">
					<label for="pseudoUser">votre pseudo :</label>
					<input type="text" class="form-control" id="pseudoUser" name="pseudoUser" value = "${user.pseudo}"required> 
				</div>
				
				<div class="form-group">
					<label for="nameUser">votre nom :</label>
					<input type="text" class="form-control" id="nameUser" name="nameUser" value = "${user.name}" required>
				</div>
				
				<div class="form-group">
					<label for="fistNameUser">votre prenom :</label>
					<input type="text" class="form-control" id="fistNameUser" name="firstNameUser" value = "${user.first_name}" required>
				</div>
				
				<div class="form-group">
					<label for="mailUser">votre email :</label>
					<input type="email" class="form-control" id="mailUser" name="mailUser" value = "${user.mail}" required>
				</div>
				
				<div class="form-group">
					<label for="phoneNumberUser">votre numéro de téléphone :</label>
					<input type="tel" class="form-control" id="phoneNumberUser" name="phoneNumberUser" value = "${user.phone_number}">
				</div>
		
				<div class="form-group">
					<label for="addressUser">votre rue :</label>
					<input type="text" class="form-control" id="addressUser" name="addressUser" value = "${user.address}" required>
				</div>
				
				<div class="form-group">
					<label for="cityUser">votre ville :</label>
					<input type="text" class="form-control" id="cityUser" name="cityUser" value = "${user.city}" required>
				</div>
				
				<div class="form-group">
					<label for="zipCodeUser">votre code postal :</label>
					<input type="text" class="form-control" id="zipCodeUser" name="zipCodeUser" value = "${user.zip_code}" required>
				</div>
				
				<div class="form-group">
					<label for="passwordUser">votre mot de passe :</label>
					<input type="password" class="form-control" id="passwordUser" name="passwordUser" value = "${user.password}" required>
				</div>
				
				<div class="form-group">
					<label for="passwordUser">confirmez votre mot de passe :</label>
					<input type="password" class="form-control" id="confirmPasswordUser" name="confirmPasswordUser"  required>
				</div>
				
				<div class="form-group">
					<label for="credit">credit : ${user.credit}</label>
					<input type="hidden" name="credit" id="credit" value = "${user.credit}">
				</div>
				
				<div class="form-group">
					<button type="submit" class="btn btn-success btn-lg" id="btnValidateModification" name="btnValidateModification">Valider</button>
				</div>
			
			</form>
		
		</div>
		
	</body>
</html>