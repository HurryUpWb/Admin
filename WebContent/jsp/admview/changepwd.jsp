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
    <script type="text/javascript">
    	function tj(){
    		var psw1=document.getElementById("npwd").value;
    		var psw2=document.getElementById("renpwd").value;
    		if(psw1=='' || psw2==''){
    			alert('请输入密码！');
    			return false;
    		}
    		if(psw1!=psw2){
    			alert('两次输入的密码不一致！');
    			return false;
    		}
    		return true;
    	}
    </script>
</head>
<body>
  <div class="panel admin-panel">
  <div class="panel-head"><strong><span class="icon-pencil-square-o"></span>${requestScope.item}</strong></div>
  <div class="body-content">
    <form  class="form-x" action="/Admin/Chage.adm" method="post" 
      			id="frm" name="frm" onsubmit="return tj();" enctype="application/x-www-form-urlencoded">
      		<div align="center">
      			<input type="hidden" name="id" id="id" value="${requestScope.user.id}"> 
	      		<div>
	      			用 户 名：<input type="text" name="uname" id="uname" value="${requestScope.user.name}" readonly="readonly">
	      		</div>
	      		<br>
	      		<div>
	      			新 密 码：<input type="password" name="npwd" id="npwd"/>
	      		</div>
	      		<br>
	      		<div>
	      			确认密码：<input type="password" name="renpwd" id="renpwd">
	      		</div>
	      		<br>
	      		<div>
	      			<input type="reset"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      			<input type="submit">
	      		</div>
      		</div>
    </form>
  </div>
</div>
</body>
</html>