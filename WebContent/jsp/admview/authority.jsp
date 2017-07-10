<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${requestScope.item}</title>
 	<link rel="stylesheet" href="/Admin/css/pintuer.css">
    <link rel="stylesheet" href="/Admin/css/admin.css">
    <script src="/Admin/js/jquery.js"></script>
    <script src="/Admin/js/uploadPreview.js"></script>
</head>
<body>
  <div class="panel admin-panel">
  <div class="panel-head"><strong><span class="icon-pencil-square-o"></span>${requestScope.item}</strong></div>
  <div class="body-content">
	  	<div align="center">
	  		 当前所有管理员用户：
	  		 <c:if test="${requestScope.aduser!=null}">
	  		 	<c:forEach items="${requestScope.aduser}" var="adu">
	  		 		<c:if test="${adu.name!=sessionScope.sessionVo.userName}">
	  		 			<div>
	  		 				<a href="##" style="text-decoration: underline;" onclick=";"><font color="green" size="5">${adu.name}</font></a>
	  		 			</div>
	  		 		</c:if>
	  		 	</c:forEach>
	  		 </c:if>
	  	</div>
  </div>
</div>
</body>
</html>