<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ include file="taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ENI-Auction | Home</title>
<%@ include file="includeCDN.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/FragHeading.jspf"%>
	<main>
		<div class="container">
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
			<c:choose>
				<c:when test="${not empty idUserConnected }">
				<h2>Auction list</h2>
			Filter :
				<form action="${pageContext.request.contextPath }/Filter"
						method="post">
						<div class="form-inline mr-auto">
						<input class="form-control mr-sm-2" name="search" type="text"
							placeholder="Search" aria-label="Search"><select
							name="category" class="browser-default custom-select">
							<!-- TODO: recuperer en base les categories -->
							<option selected>All</option>
							<c:forEach var="category" items="${listCategories}">
								<option value="${category.libelle}">${category.libelle}</option>
							</c:forEach>
						</select>
						</div>
						<br>
						<fieldset>
							<span> <label for="btnRadioAchat">Purchase</label> <input
								type="radio" class="change" name="btnRadioGroup"
								id="btnRadioGroupAchat" value="achat" checked>

							</span> <span style="margin: 20%;"> <label for="btnRadioVente">Sale</label>
								<input type="radio" class="change" name="btnRadioGroup"
								value="vente" id="btnRadioGroupVente">
							</span>
						</fieldset>
						<fieldset>
							<div class="row">
								<div class="col-3">
									<input type="checkbox" class="changeCheckbox" name="checkbox_achat"
										value="enchereEnOuvertes"> Opened auctions<br>
									<input type="checkbox" class="changeCheckbox" name="checkbox_achat"
										value="enchereEnCours"> My current auctions<br> <input
										type="checkbox" class="changeCheckbox" name="checkbox_achat"
										value="enchereRemportees"> My won auctions<br>
								</div>
								<div class="col-3">
									<input type="checkbox" class="changeCheckbox" name="checkbox_vente"
										value="venteEnCours"> My current sales<br> <input
										type="checkbox" class="changeCheckbox" name="checkbox_vente"
										value="ventesNonDebutees"> My futures sales<br>
									<input type="checkbox" class="changeCheckbox" name="checkbox_vente"
										value="ventesTerminees"> My finished sales<br>
								</div>
							</div>
						</fieldset>
						<button type="submit" class="btn btn-dark" style="margin: 1%;">Search</button>
					</form>
				</c:when>
				<c:otherwise>
					<h2>Auction list</h2>
			Filter :
			<form action="${pageContext.request.contextPath }/Filter"
						method="post" class="form-inline mr-auto">
						<input class="form-control mr-sm-2" name="search" type="text"
							placeholder="Search" aria-label="Search"> <select
							name="category" class="browser-default custom-select">
							<!-- TODO: recuperer en base les categories -->
							<option selected>All</option>
							<c:forEach var="category" items="${listCategories}">
								<option value="${category.libelle}">${category.libelle}</option>
							</c:forEach>
						</select>
						<button type="submit" class="btn btn-dark" style="margin: 1%;">Search</button>
					</form>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${not empty filterList }">
					<c:forEach var="article" items="${filterList}">
						<div class="row">
							<div class="col-sm-6">
								<div class="card">
									<div class="card-body">
										<a href="./sell-detail?id=${article.no_article}" class="btn btn-dark">${article.name_article }</a>
										<c:choose>
											<c:when test="${article.price_auction > 0 }">
												<p class="card-text">Price : ${article.price_auction }</p>
											</c:when>
											<c:otherwise>
												<p class="card-text">Price : ${article.initial_price }</p>
											</c:otherwise>
										</c:choose>
										<p class="card-text">End Auction : ${article.end_date }</p>
										<p class="card-text">Seller : ${article.userSeller.pseudo }</p>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty listArticles }">
						<c:forEach var="article" items="${listArticles}">
							<div class="row">
								<div class="col-sm-6">
									<div class="card">
										<div class="card-body">
											<a href="./sell-detail?id=${article.no_article}" class="btn btn-dark">${article.name_article }</a>
											<c:choose>
												<c:when test="${article.price_auction > 0 }">
													<p class="card-text">Price : ${article.price_auction }</p>
												</c:when>
												<c:otherwise>
													<p class="card-text">Price : ${article.initial_price }</p>
												</c:otherwise>
											</c:choose>
											<p class="card-text">End Auction : ${article.end_date }</p>
											<p class="card-text">Seller : ${article.userSeller.pseudo }</p>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty listArticles }">
						<p style="text-align: center">No articles for the moment...</p>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</main>
	<script>
		btnRadioAchat = $("#btnRadioGroupAchat");
		btnRadioVente = $("#btnRadioGroupVente");
		checkboxAchat = $("input[name='checkbox_achat']");
		checkboxVente = $("input[name='checkbox_vente']");

		if (btnRadioAchat.is(":checked")) {
			checkboxVente.attr("disabled", true);
			checkboxAchat.attr("disabled", false);
		} else {
			checkboxVente.attr("disabled", false);
			checkboxAchat.attr("disabled", true);
		}

		$(".change").change(function() {

			if (this.id == "btnRadioGroupAchat") {

				checkboxVente.prop("checked", false);
				checkboxVente.attr("disabled", true);
				checkboxAchat.attr("disabled", false);
			} else {
				checkboxAchat.prop("checked", false);
				checkboxVente.attr("disabled", false);
				checkboxAchat.attr("disabled", true);
			}
		});
		
		$(".changeCheckbox").change(function() {
			
			name = this.name
			object = $("input[name="+name+"]");
			
			if(object.is(":checked")){
				checkboxVente.attr("disabled", true);
				checkboxAchat.attr("disabled", true);
				$("input[value="+this.value+"]").attr("disabled", false);			
			}else{
				if(btnRadioAchat.is(":checked")){
					checkboxAchat.attr("disabled", false);
					checkboxVente.attr("disabled", true);
				}else{
					checkboxAchat.attr("disabled", true);
					checkboxVente.attr("disabled", false);
				}
			}		
		});
		
	</script>
</body>
</html>