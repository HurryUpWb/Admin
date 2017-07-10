<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>后台管理中心</title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="/Admin/js/jquery.js"></script>   
</head>
<body style="background-color:#f2f9fd;">
<div class="header bg-main">
  <div class="logo margin-big-left fadein-top">
    <h1><img src="images/y.jpg" class="radius-circle rotate-hover" height="50" alt="" />${sessionScope.sessionVo.userName}</h1>
  </div>
  <div class="head-l">
	  <a class="button button-little bg-green" href="http://60.205.187.190:8080/ebook/" target="_blank"><span class="icon-home"></span> 前台首页</a> &nbsp;&nbsp;
	  <a href="##" class="button button-little bg-blue"><span class="icon-wrench"></span> 清除缓存</a> &nbsp;&nbsp;
	  <a class="button button-little bg-red" href="/Admin/Quit.login" onclick="return IsQuit();"><span class="icon-power-off"></span> 退出登录</a> 
  </div>
  <input type="hidden" name="auth" id="auth" value="${sessionScope.sessionVo.authority}">
</div>
<div class="leftnav">
  <div class="leftnav-title"><strong><span class="icon-list"></span>菜单列表</strong></div>
  <div id="basic">
	  <h2><span class="icon-user"></span>基本设置</h2>
	  <ul>
	    <li><a href="/Admin/Changepwd.adm" target="right"><span class="icon-caret-right"></span>修改密码</a></li>
	    <li id="chgauth"><a href="/Admin/EditAuth.adm" target="right"><span class="icon-caret-right"></span>管理员权限控制</a></li>
	  </ul>
  </div> 
  <div id="bkviiew">  
	  <h2><span class="icon-user"></span>书籍管理</h2>
	  <ul>
	    <li id="addbook"><a href="/Admin/jsp/bookview/SelectAddWay.jsp" target="right"><span class="icon-caret-right"></span>新增图书</a></li>        
	    <li><a href="/Admin/ShowBook.show" target="right"><span class="icon-caret-right"></span>查看所有图书</a></li>
	    <li><a href="/Admin/Classify.show" target="right"><span class="icon-caret-right"></span>图书统计</a></li>
	  </ul>
  </div>
  <div id="ordview">
	  <h2><span class="icon-user"></span>订单管理</h2>
	  <ul>
	    <li><a href="/Admin/ListOrders.ord" target="right"><span class="icon-caret-right"></span>查看所有订单</a></li>        
	  </ul>
  </div>
  <div id="userview">  
	  <h2><span class="icon-user"></span>用户管理</h2>
	  <ul>
	    <li><a href="/Admin/getusers.user" target="right"><span class="icon-caret-right"></span>查看所有用户</a></li>
	    <li><a href="/Admin/getcomments.user" target="right"><span class="icon-caret-right"></span>管理用户评论</a></li>
	    <!-- <li><a href="##" target="right"><span class="icon-caret-right"></span>消息推送</a></li> -->        
	  </ul>
  </div>    
</div>
<script type="text/javascript">
 $(function(){
  $(".leftnav h2").click(function(){
	  $(this).next().slideToggle(200);	
	  $(this).toggleClass("on"); 
  })
  $(".leftnav ul li a").click(function(){
	    $("#a_leader_txt").text($(this).text());
  		$(".leftnav ul li a").removeClass("on");
		$(this).addClass("on");
  })
}); 
</script>
<script type="text/javascript">
	function IsQuit(){
		var r=confirm("确定退出?");
		if(r){
			return true;
		}else{
			return false;
		}
	}
	
	window.onload=function(){
		var auth=document.getElementById("auth").value;
		if(auth==2){
			document.getElementById("bkviiew").hidden=true;
			document.getElementById("chgauth").hidden=true;
		}
	}
</script>
<ul class="bread">
  <li><a href="{:U('Index/info')}" target="right" class="icon-home"> 首页</a></li>
  <li><a href="##" id="a_leader_txt">网站信息</a></li>
</ul>
<div class="admin">
  <iframe id="iframe" scrolling="auto" rameborder="0" src="/Admin/jsp/info.jsp" name="right" width="100%" height="100%"></iframe>
</div>
<div style="text-align:center;">
</div>
</body>
</html>