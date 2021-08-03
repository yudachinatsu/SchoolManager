<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link href="css/style.css" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="ico/faviconSchoolManager.ico">
<link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon-180x180.png">
<style type="text/css">
<!--
@import url('https://fonts.googleapis.com/css?family=Exo:400,700');

-->
</style>
</head>

<body>

	<header>
<h1>		<fmt:message key="message.header" /></h1>
	</header>

	<div id="data">


		<form:form action="login" modelAttribute="loginForm" method="post" autocomplete="off">
			<c:if test="${not empty errMsg}">
				<span class="error">${fn:escapeXml(errMsg)}</span>
			</c:if>
			<span class="error"><form:errors path="loginId" class="error" /></span>
			<br>
			<span class="error"><form:errors path="password" class="error" /></span>
			<fieldset>
				<div>
					<label class="boldText">ID</label>
					<form:input path="loginId" />
				</div>

				<div>
					<label class="boldText">PASS</label>
					<form:password path="password" />

				</div>
			</fieldset>

			<form:button class="submit_btn" type="submit">
          ログイン
        </form:button>
		</form:form>

		<div>
			<a href="index">TOP画面に戻る</a>
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

	<footer><fmt:message key="message.footer" /></footer>

</body>
</html>