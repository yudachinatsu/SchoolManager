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
    <title>生徒登録確認画面</title>
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
    <p>登録内容はこちらでよろしいですか？</p>
    <form:form action="studentInsertResult" modelAttribute="studentInsert">
      <fieldset class="label-130">
        <div>
          <label>生徒ID</label>

          <form:input path="studentId" readonly="true" />
        </div>
        <div>
          <label>名前</label>

          <form:input path="studentName" readonly="true" />
        </div>
      </fieldset>
      <div>
        <form:button name="insert" class="submit_btn">登録</form:button>
        <form:button name="back" class="submit_btn">戻る</form:button>
      </div>
    </form:form>
    <div>
        <a href="menu"><fmt:message key="message.menu" /></a>
      </div>
    </div>
    <footer>
<fmt:message key="message.footer" />
  </footer>
  </body>
  </html>