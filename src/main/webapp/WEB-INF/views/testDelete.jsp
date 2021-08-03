<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>テスト削除画面</title>
<link href="css/style.css" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="ico/faviconSchoolManager.ico">
<link rel="apple-touch-icon" sizes="180x180"href="./apple-touch-icon-180x180.png">
</head>
<body>
	<header>
		<h1>
			<a href="menu"><fmt:message key="message.header" /></a>
		</h1>
		<h2>
			<a href="logout">ログアウト</a>
		</h2>
	</header>
	<div id="data">
		<p>
			削除するテスト情報を入力してください<br>
		</p>



		<form:form action="testDeleteConfirm" method="post"
			modelAttribute="testDelete">
			<c:if test="${not empty errMsg}">
				<span class="error">${fn:escapeXml(errMsg)}</span>
			</c:if>


			<span class="error"><form:errors path="testId" class="error" /></span>
			<br>

			<span class="error"> <form:errors path="testName"
					class="error" /></span>
			<fieldset>
				<div>
					<label class="required">テストID</label>
					<form:input path="testId" />
				</div>
				<div>
					<label class="required">テスト名</label>
					<form:input path="testName" />
				</div>
			</fieldset>
			<form:button class="submit_btn">確認</form:button>
		</form:form>
		<div>
			<a href="menu"><fmt:message key="message.menu" /></a>
		</div>
	</div>
	<footer><fmt:message key="message.footer" /></footer>
</body>
</html>