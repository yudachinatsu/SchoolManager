<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>TOP画面</title>
<link href="css/style.css" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="ico/faviconSchoolManager.ico">
<link rel="apple-touch-icon" sizes="180x180"
	href="./apple-touch-icon-180x180.png">
<style type="text/css">
<!--
@import url('https://fonts.googleapis.com/css?family=Exo:400,700');

-->
</style>
</head>

<body>
	<header>
		<h1>
			<fmt:message key="message.header" />
		</h1>
	</header>

	<div id="data">
		<h1>SchoolManager</h1>
		<div class="wrap">
			<a href="login" class="btn">Start</a>
		</div>
	</div>
	<ul class="circles">
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
	</ul>
	<footer>
		<fmt:message key="message.footer" />
	</footer>
</body>

</html>