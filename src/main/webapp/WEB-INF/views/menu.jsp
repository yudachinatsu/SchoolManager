<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<link href="css/style.css" rel="stylesheet">
<link rel="icon" type="image/x-icon" href="ico/faviconSchoolManager.ico">
<link rel="apple-touch-icon" sizes="180x180"href="./apple-touch-icon-180x180.png">
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
		<h2>
			<a href="logout">ログアウト</a>
		</h2>
	</header>

	<div id="data">
		<p>${fn:escapeXml(loginUser.name)}先生こんにちは</p>
		<div class="menuScore">
			<div class="menuText">成績管理画面関係</div>
			<div id="menu">
			<p>
				<a href="scoreInsert">登録<img  src="img/add.png" class="active"><!-- <img  src="img/add2.png"> --></a>
			</p>
			<p>
				<a href="scoreUpdate">変更<img  src="img/change.png"></a>
			</p>
			<p>
				<a href="scoreDelete">削除<img  src="img/delete.png"></a>
			</p>
			<p>
				<a href="scoreSelect">検索<img  src="img/search.png"></a>
			</p>
			</div>
		</div>

		<div class="menuStudent">
			<div class="menuText">生徒管理画面関係</div>
			<div id="menu">
			<p>
				<a href="studentInsert">登録<img  src="img/add.png"></a>
			</p>
			<p>
				<a href="studentUpdate">変更<img  src="img/change.png"></a>
			</p>
			<p>
				<a href="studentDelete">削除<img  src="img/delete.png"></a>
			</p>
			<p>
				<a href="studentSelect">検索<img  src="img/search.png"></a>
			</p>
			</div>
		</div>

		<div class="menuTest">
			<div class="menuText">テスト管理画面関係</div>
			<div id="menu">
			<p>
				<a href="testInsert">登録<img  src="img/add.png"></a>
			</p>
			<p>
				<a href="testUpdate">変更<img  src="img/change.png"></a>
			</p>
			<p>
				<a href="testDelete">削除<img  src="img/delete.png"></a>
			</p>
			<p>
				<a href="testSelect">検索<img  src="img/search.png"></a>
			</p>
		</div>
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