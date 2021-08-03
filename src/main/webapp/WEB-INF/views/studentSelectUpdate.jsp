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
<title>成績検索編集画面</title>
<link href="css/style.css" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="ico/faviconSchoolManager.ico">
<link rel="apple-touch-icon" sizes="180x180"
	href="./apple-touch-icon-180x180.png">
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
	<form:form action="studentSelectUpdate" method="post"
		modelAttribute="studentSelect">
		<div id="data">
			<table>
				<!-- <caption>検索結果</caption> -->
				<thead>
					<tr>
						<th>生徒ID</th>
						<th>生徒名</th>
					</tr>
				</thead>
				<tbody>

					<tr>
						<td>${fn:escapeXml(editStudent.id)}</td>
						<td>${fn:escapeXml(editStudent.name)}</td>
					</tr>
				</tbody>
			</table>
			<div>
				<form:button name="update" class="submit_btn"> 変更 </form:button>
				<form:button name="delete" class="submit_btn"> 削除 </form:button>
				<form:button name="back" class="submit_btn"> 戻る </form:button>
			</div>

			<div>
				<a href="menu"><fmt:message key="message.menu" /></a>
			</div>
		</div>
	</form:form>

	<footer>
		<fmt:message key="message.footer" />
	</footer>
</body>

</html>