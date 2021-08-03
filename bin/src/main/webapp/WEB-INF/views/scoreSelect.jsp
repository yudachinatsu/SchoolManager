<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績検索画面</title>
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
			検索する成績情報を入力してください<br> ※全て空白の場合は全検索を行います
		</p>




		<form:form action="scoreSelectResult" modelAttribute="scoreSelect"
			method="get">
			<c:if test="${not empty errMsg}">
				<span class="error">${fn:escapeXml(errMsg)}</span>
			</c:if>
			<fieldset>
				<div class="cp_ipselect cp_sl01">
					<label>生徒名</label>
					<form:select path="studentId">
						<form:options items="${studentInfo}" itemLabel="name"
							itemValue="id" />
					</form:select>
				</div>

				<div class="cp_ipselect cp_sl01">
					<label>テスト名</label>
					<form:select path="testId">
						<form:options items="${testInfo}" itemLabel="name" itemValue="id" />
					</form:select>
				</div>

				<div class="cp_ipselect cp_sl01 date">
					<label>受験日</label>
					<form:select path="year">
						<form:options items="${years}" itemLabel="Text" itemValue="Number" />
					</form:select>
					<form:select path="month">
						<form:options items="${monthes}" itemLabel="Text"
							itemValue="Number" />
					</form:select>
					<form:select path="day">
						<form:options items="${days}" itemLabel="Text" itemValue="Number" />
					</form:select>
				</div>
			</fieldset>

			<form:button class="submit_btn">検索</form:button>
		</form:form>
		<div>
			<a href="menu"><fmt:message key="message.menu" /></a>
		</div>
	</div>
	<footer><fmt:message key="message.footer" /></footer>
</body>
</html>