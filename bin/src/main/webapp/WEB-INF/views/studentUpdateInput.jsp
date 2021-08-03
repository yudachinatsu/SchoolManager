<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>生徒情報変更入力画面</title>
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
			１箇所以上の項目を変更してください<br>
		</p>


		<form:form action="studentUpdateConfirm"
			modelAttribute="studentUpdate">
			<c:if test="${not empty errMsg}">
				<p class="error">${fn:escapeXml(errMsg)}</p>
			</c:if>

			<form:errors path="studentId" class="error"></form:errors>
			<br>

			<form:errors path="studentName" class="error"></form:errors>
			<fieldset>
				<div>
					<label>生徒ID</label>
					<form:input path="studentId" />
				</div>
				<div>
					<label>生徒名</label>
					<form:input path="studentName" />
				</div>

				<div>
					<form:button name="check" class="submit_btn">確認</form:button>
					<form:button name="back" class="submit_btn"> 戻る</form:button>
				</div>
			</fieldset>
		</form:form>
		<div>
			<a href="menu"><fmt:message key="message.menu" /></a>
		</div>
	</div>
	<footer><fmt:message key="message.footer" /></footer>
</body>

</html>