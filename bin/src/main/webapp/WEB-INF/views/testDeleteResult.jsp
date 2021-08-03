<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>テスト削除結果確認画面</title>
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
  <p>実行者：${fn:escapeXml(loginUser.name)}</p>
  <p>正常に削除されました</p>
  <div>
    <a href="menu"><fmt:message key="message.menu" /></a>
  </div>
  </div>
  <footer>
<fmt:message key="message.footer" />
  </footer>
</body>

</html>
