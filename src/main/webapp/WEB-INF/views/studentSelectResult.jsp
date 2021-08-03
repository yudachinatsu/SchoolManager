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
    <title>生徒検索結果画面</title>
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
		<form:form action="studentSelectUpdate" modelAttribute="studentSelect"
			method="post">
			<table class="student_select">
				<caption>検索結果</caption>
				<thead>


					<tr>
						<th>生徒ID</th>
						<th>生徒名</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${studentViewData}" var="student">
						<tr>
							<td>${fn:escapeXml(student.id)}</td>
							<td>${fn:escapeXml(student.name)}</td>
							<td><form:button class="edit_btn" name="id"
									value="${fn:escapeXml(student.id)}">編集</form:button></td>
						</tr>
					</c:forEach>


				</tbody>
			</table>
<%
			boolean flg = true;
			int count = (int) session.getAttribute("count");
			int nowPage = (Integer) session.getAttribute("nowPage");
			for (int i = 1; i <= count; i++) {
				if (i == 1 || i == count || i == nowPage || i == nowPage - 1 || i == nowPage + 1) {

					if (i == nowPage) {
				out.println("<button id=\"page_btn\" name=\"page_btn\" class=\"pageButton\" type=\"submit\" value=\"" + i
						+ "\" disabled=\"disabled\">" + i + "</button>");
					} else {
				out.println("<button id=\"page_btn\" name=\"page_btn\" class=\"pageButton\" type=\"submit\" value=\"" + i
						+ "\">" + i + "</button>");
					}

					flg = true;

				} else if (flg) {
					out.println("…");
					flg = false;
				}
			}
			%>


		</form:form>
		<div>
			<button onclick="location.href='studentSelect'; return false;"
				class="submit_btn">戻る</button>

		</div>
		<div>
			<a href="menu"><fmt:message key="message.menu" /></a>
		</div>
	</div>
	<footer>
		<fmt:message key="message.footer" />
	</footer>
</body>

</html>