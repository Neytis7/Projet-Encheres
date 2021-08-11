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