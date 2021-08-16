<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ include file="taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ENI-Auction</title>
		<%@ include file="includeCDN.jsp" %>
	</head>
	<body>
		<%@ include file="/WEB-INF/FragHeading.jspf"%>
		<main>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="CSS/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>ENI-Auction</title>
</head>
<body>
	<%@ include file="/WEB-INF/FragHeading.jspf"%>
	<main>
		<div class="container">
            				<c:forEach var="error" items="${errors}">	
					<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
					  ${error}
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					  </button>
					</div>
				</c:forEach>
					    <span aria-hidden="true">&times;</span>
				<c:if test="${not empty success}">	
					<div class="alert alert-success alert-dismissible fade show" role="alert" style="width:35%;">
					  ${success}
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					    <span aria-hidden="true">&times;</span>
					  </button>
					</div>
				</c:if>
  			<h2>Auction list</h2>  
  			Filter :
			<form action="${pageContext.request.contextPath }/Filter" method="post" class="form-inline mr-auto">
  				<input class="form-control mr-sm-2" name ="search" type="text" placeholder="Search" aria-label="Search">
  				<select name="category" class="browser-default custom-select">
  					<option selected>Toutes</option>
  					<option value="Informatique">Informatique</option>
  					<option value="Ameublement">Ameublement</option>
  					<option value="Vetement">Vêtement</option>
  					<option value="Sport et Loisir">Sport et Loisir</option>
				</select>
  				<button type="submit" class="btn btn-dark">Search</button>
			</form>
  			<br>
  			<c:choose>
    			<c:when test="${not empty filterList }">
        			  <c:forEach var="article" items="${filterList}">
  						<div class="row">
  							<div class="col-sm-6">
    							<div class="card">
      								<div class="card-body">
        							<a href="#" class="btn btn-dark">${article.name_article }</a>
        							<c:choose>
    									<c:when test="${article.price_auction > 0 }">
    									<p class="card-text">Price : ${article.price_auction }</p>
    									</c:when>
    									<c:otherwise>
    									<p class="card-text">Price : ${article.initial_price }</p>
    									</c:otherwise>
    								</c:choose>
        							<p class="card-text">End Auction : ${article.end_date }</p>
       						    	<p class="card-text">Seller : ${article.user.pseudo }</p>
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
        							<a href="#" class="btn btn-dark">${article.name_article }</a>
        							<c:choose>
    									<c:when test="${article.price_auction > 0 }">
    									<p class="card-text">Price : ${article.price_auction }</p>
    									</c:when>
    									<c:otherwise>
    									<p class="card-text">Price : ${article.initial_price }</p>
    									</c:otherwise>
    								</c:choose>
        							<p class="card-text">End Auction : ${article.end_date }</p>
       						    	<p class="card-text">Seller : ${article.user.pseudo }</p>
      								</div>
   								</div>
  							</div>
  						</div>
  					</c:forEach>
  				</c:if>
  				<c:if test="${empty listArticles }">
				<p style="text-align:center">No articles for the moment...</p>
			</c:if>
   			</c:otherwise>
			</c:choose>		
		</div>
	</main>
</body>
</html>