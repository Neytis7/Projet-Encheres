<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Encheres | Vendre un article</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf" %>
		
		<div class="container">
			<p class="display-3">Nouvelle vente ! </p>
			
			<c:forEach var="error" items="${errors}">	
				<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
				  ${error}
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
				    <span aria-hidden="true">&times;</span>
				  </button>
				</div>
			</c:forEach>
			
			<form method="POST" action="./sale-item" class="col-6">
			
				<div class="form-group">
					<label for="nameItem">Nom :</label>
					<input type="text" class="form-control" id="nameItem" name="nameItem" required>
				</div>
				
				<div class="form-group">
					<label for="descriptionItem">Description :</label>
					<textarea class="form-control" id="descriptionItem" name="descriptionItem" required></textarea>
				</div>
				
				<div class="form-group">
					<label for="categoyItem">Categorie :</label>
					<select class="browser-default custom-select" name="categoyItem">	  					
	  					<c:forEach var="category" items="${categories}">
	  						<option value="${category.no_category}">${category.libelle}</option>
	  					</c:forEach>
					</select>
				</div>
				
				<div class="form-group">
					<label for="priceItem">Mise à prix :</label>
					<input type="number" class="form-control-file" id="priceItem" name="priceItem" min="1" required>
				</div>
		
				<div class="form-group">
					<label for="startDateItem">Début de l'enchère</label>
					<input type="date" class="form-control" id="startDateItem" name="startDateItem" required>
				</div>
				
				<div class="form-group">
					<label for="endDateItem">Fin de l'enchère :</label>
					<input type="date" class="form-control" id="endDateItem" name="endDateItem" required>
				</div>
				
				
				<p>Retrait</p>
				
				<c:if test="${not empty user }">
					<c:set var="addressItemUser" scope="page" value="${user.address }"/>
					<c:set var="zipCodeItemUser" scope="page" value="${user.zip_code }"/>
					<c:set var="cityItemUser" scope="page" value="${user.city }"/>
				</c:if>
				
				
				
				<div class="form-group">
					<label for="adressItemUser">N° et nom de rue :</label>
					<input type="text" class="form-control" id="adressItemUser" name="adressItemUser" value="${addressItemUser}" required>
				</div>
				
				<div class="form-group">
					<label for="zipCodeItemUser">Code postal :</label>
					<input type="text" class="form-control" id="zipCodeItemUser" name="zipCodeItemUser" value="${zipCodeItemUser}" required>
				</div>
				
				<div class="form-group">
					<label for="cityItemUser">Ville :</label>
					<input type="text" class="form-control" id="cityItemUser" name="cityItemUser" value="${cityItemUser}" required>
				</div>
				
				
				
				<div class="form-group">
					<button type="submit" class="btn btn-success btn-lg" id="btnSubmitItem" name="btnSubmitItem">Enregistrer</button>
					<a href="./Home" class="btn btn-danger btn-lg">Cancel</a>
				</div>
			
			</form>		
		</div>
		
	</body>
</html>