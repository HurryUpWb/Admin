<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html ; charset=UTF-8">
<title>后台登录</title>
<link href="<%=request.getContextPath()%>/css/login.css" rel="stylesheet"> 
</head>
<body>
	<div class="login">
    <div class="message">e_book-管理登录</div>
    <div id="darkbannerwrap"></div>
    
    <form action="<%=request.getContextPath()%>/logon.login" method="post">
		<input name="username" placeholder="用户名" required="required" type="text">
		<hr class="hr15">
		<input name="pass" placeholder="密码" required="required" type="password">
		<hr class="hr15">
		<input value="登录" style="width:100%;" type="submit">
		<hr class="hr20">
	</form>
</div>
</body>
</html>