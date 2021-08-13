<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix ="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt"%>
<!DOCTYPE html>
<html>
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
        			  <c:forEach var="enchere" items="${filterList}">
  						<div class="row">
  							<div class="col-sm-6">
    							<div class="card">
      								<div class="card-body">
        							<a href="#" class="btn btn-dark">${enchere.nameArticle }</a>
        							<p class="card-text">Price : ${enchere.price }</p>
        							<p class="card-text">End Auction : ${enchere.endDate }</p>
       						    	<p class="card-text">Seller : ${enchere.pseudo }</p>
      								</div>
   								</div>
  							</div>
  						</div>
  					</c:forEach> 
    			</c:when>    
    		<c:otherwise>
        		<c:if test="${not empty listEncheres }">
  					<c:forEach var="enchere" items="${listEncheres}">
  						<div class="row">
  							<div class="col-sm-6">
    							<div class="card">
      								<div class="card-body">
        							<a href="#" class="btn btn-dark">${enchere.nameArticle }</a>
        							<p class="card-text">Price : ${enchere.price }</p>
        							<p class="card-text">End Auction : ${enchere.endDate }</p>
       						    	<p class="card-text">Seller : ${enchere.pseudo }</p>
      								</div>
   								</div>
  							</div>
  						</div>
  					</c:forEach>
  				</c:if>
  				<c:if test="${empty listEncheres }">
				<p style="text-align:center">No auctions for the moment...</p>
			</c:if>
   			</c:otherwise>
			</c:choose>		
		</div>
	</main>
</body>
</html>