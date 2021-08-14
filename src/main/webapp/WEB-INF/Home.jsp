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
			<div class="container">
			
				<c:forEach var="error" items="${errors}">	
					<div class="alert alert-danger alert-dismissible fade show" role="alert" style="width:35%;">
					  ${error}
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					    <span aria-hidden="true">&times;</span>
					  </button>
					</div>
				</c:forEach>
				
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
				<form class="form-inline mr-auto">
	  				<input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
	  				<select class="browser-default custom-select">
	  					<option selected>Toutes</option>
	  					<option value="1">Informatique</option>
	  					<option value="2">Ameublement</option>
	  					<option value="3">Vêtement</option>
	  					<option value="3">Sport et Loisir</option>
					</select>
	  				<button type="submit" class="btn btn-dark">Search</button>
				</form>
	  			<br>
			</div>
		</main>
	</body>
</html>