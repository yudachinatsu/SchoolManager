<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>timeout</title>
<meta http-equiv="Refresh" content="2;URL=index">
<link href="css/style.css" rel="stylesheet">
</head>
<body>
 <header>
    <h1>
  	 <fmt:message key="message.header" />
  	</h1>
  </header>

	<div id="data">
	    <p>タイムアウトしました</p>
		<p>2秒後トップに戻ります</p>
	</div>

	<footer>
		<fmt:message key="message.footer" />
	</footer>

</body>
</body>
</html>