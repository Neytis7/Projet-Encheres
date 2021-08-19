<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Encheres | Mon profil</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf"%>
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
				<a href="./modify-user" class="btn btn-success btn-lg" style="margin: 0 45% 0 45%">Modifier</a>
			</c:if>
		</div>
	</body>
</html>
	