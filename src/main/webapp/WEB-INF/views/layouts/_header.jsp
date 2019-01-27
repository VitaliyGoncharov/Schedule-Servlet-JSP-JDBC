<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>University Schedule</title>
<link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap.min.css"/>">
<link rel="stylesheet" href="<c:url value="/css/fontawesome/css/all.min.css"/>">
<link rel="stylesheet" href="<c:url value="/css/main.css" />">
</head>
<body>
	<div class="header clear">
		<jsp:include page="_menu.jsp"></jsp:include>
		
		<ul class="auth-links">
			<li><a href="#">Вход</a>
			<li><a href="#">Регистрация</a>
		</ul>
	</div>
	
	<div class="container">