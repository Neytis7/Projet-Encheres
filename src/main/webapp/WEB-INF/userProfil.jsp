<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Mon profil</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
	</head>
	
	<body>
		<c:if test="${user != null}">	
			<div style="margin-left : 40%;margin-right: 60%; width : 30%;margin-top: 50vh;transform: translateY(-50%);">
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
		</c:if>
	</body>
</html>
	