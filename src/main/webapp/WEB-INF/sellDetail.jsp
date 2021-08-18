<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ENI AUCTION | Sell Detail</title>
<%@ include file="includeCDN.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/FragHeading.jspf"%>
	<div class="container">
		<p class="display-3">Détail de la vente</p>

		<c:forEach var="error" items="${errors}">
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert" style="width: 35%;">
				${error}
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:forEach>

		<div>
			<dl class="row">
				<dt class="col-sm-3"></dt>
				<dd class="col-sm-9">${article.name_article}</dd>
				
				<dt class="col-sm-3">Description :</dt>
				<dd class="col-sm-9">${article.description}</dd>

				<dt class="col-sm-3">Catégorie :</dt>
				<dd class="col-sm-9">${article.category.libelle}</dd>

				<dt class="col-sm-3">Meilleure offre :</dt>
				<dd class="col-sm-9">${article.getBestAuction()}</dd>

				<dt class="col-sm-3">Mise à prix :</dt>
				<dd class="col-sm-9">${article.initial_price}</dd>

				<dt class="col-sm-3">Fin de l'enchère :</dt>
				<dd class="col-sm-9">${article.getformatDate()}</dd>

				<dt class="col-sm-3">Retrait :</dt>
				<dd class="col-sm-9">${article.withdrawalPoint.displayFullAddress()}</dd>

				<dt class="col-sm-3">Vendeur :</dt>
				<dd class="col-sm-9">${article.userSeller.pseudo}</dd>
			</dl>
		</div>

	</div>
</body>
</html>