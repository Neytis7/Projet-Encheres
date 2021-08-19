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
		<p class="display-3">Sell detail</p>

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

				<dt class="col-sm-3">Category :</dt>
				<dd class="col-sm-9">${article.category.libelle}</dd>

				<dt class="col-sm-3">Best bid :</dt>
				<dd class="col-sm-9">${article.getBestAuction()}</dd>

				<dt class="col-sm-3">Initial price :</dt>
				<dd class="col-sm-9">${article.initial_price}</dd>

				<dt class="col-sm-3">Auction end :</dt>
				<dd class="col-sm-9">${article.end_date}</dd>

				<dt class="col-sm-3">Withdrawal :</dt>
				<dd class="col-sm-9">${article.withdrawalPoint.displayFullAddress()}</dd>

				<dt class="col-sm-3">Seller :</dt>
				<dd class="col-sm-9">${article.userSeller.pseudo}</dd>
			</dl>
		
			<c:if test="${bid  == true}">
				<form method="POST" action="./sell-detail?id=${article.no_article}" class="col-6">
					<div class="form-group">
						<label for="priceItem">Make a bid :</label>
						<input type="number" class="form-control-file" id="priceItem" name="priceItem" min="1" required>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-success btn-lg" id="btnBid" name="btnBid">Confirm</button>
					</div>
				</form>
			</c:if>
			
			<c:if test="${finished  == true}">
				<dt class="col-sm-3">${article.finalAuction()}</dt>
			</c:if>
			
			
		</div>
	</div>
</body>
</html>