<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Enchères | Mon profil</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf"%>
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
		<div class="container">
			<p class="display-4 text-center">Mon profil</p>
			<c:if test="${user != null}">	
				<div style="margin-left : 40%;margin-right: 60%; width : 45%;margin-top: 4%;">
					<dl class="row">
			  			<dt class="col-sm-3">Pseudo :</dt>
			  			<dd class="col-sm-9">${user.pseudo}</dd>
			  			
			  			<dt class="col-sm-3">Nom :</dt>
			  			<dd class="col-sm-9">${user.name}</dd>
			  			
			  			<dt class="col-sm-3">Prénom :</dt>
			  			<dd class="col-sm-9">${user.first_name}</dd>
			  			
			  			<dt class="col-sm-3">EMail :</dt>
			  			<dd class="col-sm-9">${user.mail}</dd>
			  			
			  			<dt class="col-sm-3">Téléphone :</dt>
			  			<dd class="col-sm-9">${user.phone_number}</dd>
			  			
			  			<dt class="col-sm-3">Rue :</dt>
			  			<dd class="col-sm-9">${user.address}</dd>
			  			
			  			<dt class="col-sm-3">Code Postal :</dt>
			  			<dd class="col-sm-9">${user.zip_code}</dd>
			  			
			  			<dt class="col-sm-3">Ville :</dt>
			  			<dd class="col-sm-9">${user.city}</dd>	
					</dl>
				</div>
				<a href="./modify-user" class="btn btn-success btn-lg" style="margin: 0 45% 0 45%;">Modifier</a>
				<button type="button" class="btn btn-danger btn-lg"  style="margin: 1% 1% 0 40%;padding:1%" data-toggle="modal" data-target="#deleteAccount">
					Supprimer mon compte</button>
					
				<%@ include file="/WEB-INF/modalSuppressionCompte.jspf"%>
			</c:if>
		</div>
	</body>
</html>
	