<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body style="background-color:#F2F9FD;" id="bd">
<div style="margin-left:-60px;">
	<c:if test="${requestScope.clist!=null && requestScope.clist.size()>0}">
		<table class="imagetable" align="center" style="margin-left: 50px; margin-left:70px; position: static;">
			<tr align="right">
				<input type="hidden" value="${requestScope.category}" name="tty" id="tty"/>
				<td  colspan="4">
					<select name="category" id="category">
						<option value="">全部</option>
						<option value="cid">ID</option>
						<option value="u_account">用户名称</option>
						<option value="b_bookname">书籍名称</option>
						<option value="isshow">是否展示</option>
					</select>
				</td>
				<td align="center">
					<input name="val" id="val" value="${requestScope.val}"/>
				</td>
				<td align="center" colspan="1">
					<a name="find" id="find" class="button button-little bg-green" onclick="postinfo();"> 查  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;找  </a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a name="dropcomment" id="dropcomment" class="button button-little bg-red">隐&nbsp;&nbsp;&nbsp;&nbsp;藏</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a name="showcomment" id="showcomment" class="button button-little bg-red">显&nbsp;&nbsp;&nbsp;&nbsp;示</a>
				</td>
				<td></td>
						
			</tr>
			<tr>
				<th style="width: 30px;"><input type="checkbox" id="seleAll"></th>
				<th style="width: 20px;">ID</th>
				<th style="width: 60px;">评论用户</th>
				<th style="width: 100px;">书籍名称</th>
				<th style="width: 40px;">时间</th>
				<th style="width: 380px;">内容</th>
				<th style="width: 40px;">展示</th>
			</tr>
		<c:forEach items="${requestScope.clist}" var="u">
			<tr>
				<td align="center">
					<input name="chk" type="checkbox">
				    <input name="cid" id="cid" type="hidden" value="${u.cid}">
				</td>
				<td align="center">${u.cid}</td>
				<td align="center">${u.u_account}</td>	
				<td align="center">${u.b_bookname}</td>
				<td align="center">${u.time}</td>
				<td align="center">${u.content}</td>
				<td align="center">${u.isshow==1?'是':'否'}</td>
			</tr>
		</c:forEach>
			<%	request.getSession().setAttribute("page",request.getAttribute("page")); %>
		</table>
		<c:if test="${requestScope.page.totalPage>1}">
			<div style="margin-left: 680px;">
				<c:if test="${requestScope.page.hasPrve}"><a href="#" class="turn" onclick="turnpage('/Admin/SortCommentBycategory.user?C=F');">首页</a></c:if>&nbsp;&nbsp;
				<c:if test="${requestScope.page.hasPrve}"><a href="#" class="turn" onclick="turnpage('/Admin/SortCommentBycategory.user?C=P');">上一页</a></c:if>&nbsp;&nbsp;
				<c:if test="${requestScope.page.hasNext}"><a href="#" class="turn" onclick="turnpage('/Admin/SortCommentBycategory.user?C=N');">下一页</a></c:if>&nbsp;&nbsp;
				<c:if test="${requestScope.page.hasNext}"><a href="#" class="turn" onclick="turnpage('/Admin/SortCommentBycategory.user?C=L');">末页</a></c:if>&nbsp;&nbsp;
				${requestScope.page.pageIndex}/${requestScope.page.totalPage}页 &nbsp;
					去<input size="2" name="index" id="index"/>页&nbsp;
					<a href="#" class="turn" onclick="turnto();">Go</a>
			</div>
		</c:if>
	</c:if>
</div>
<c:if test="${requestScope.clist.size()==0 }">
		<h1 align="center"><font color="red">当前没有评论！</font></h1>
</c:if>
</body>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script type="text/javascript" src="/Admin/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#seleAll").click(function(){
			var flg=$(this).attr("checked")
			$("[name=chk]").attr("checked",flg=="checked")
		})
		
		$("#dropcomment").click(function(){
			$("[name=chk]").each(function(){
				var flag=$(this).attr("checked")
				if(flag=='checked'){
					var cid=$(this).next().val()
					$.ajax({
						type:'post',
						url:'/Admin/HideComment.user?cid='+cid,
						dataType: 'json',
						success:function(data){
							if(data.msg=='success'){
								window.location.reload()
							}
						},
						error:function(){
							alert("system error!")
						}
					})
				}
			})
		})
		
		$("#showcomment").click(function(){
			$("[name=chk]").each(function(){
				var flag=$(this).attr("checked")
				if(flag=='checked'){
					var cid=$(this).next().val()
					$.ajax({
						type:'post',
						url:'/Admin/HideComment.user?flag=shw&cid='+cid,
						dataType: 'json',
						success:function(data){
							if(data.msg=='success'){
								window.location.reload()
							}
						},
						error:function(){
							alert("system error!")
						}
					})
				}
			})
		})
		
	})
</script>
<script type="text/javascript">
	function altRows(id){
		var val=document.getElementById("tty").value;
		var select=document.getElementById("category");
		for(var i=0;i<select.options.length;i++){
			if(select.options[i].value==val){
				select.options[i].selected=true;
				break;
			}
		}
		
		if(document.getElementsByTagName){  
			
			var table = document.getElementById(id);  
			var rows = table.getElementsByTagName("tr"); 
			 
			for(i = 0; i < rows.length; i++){          
				if(i % 2 == 0){
					rows[i].className = "evenrowcolor";
				}else{
					rows[i].className = "oddrowcolor";
				}      
			}
		}
	}
	
	window.onload=function(){
		altRows('alternatecolor');
	}
	
	function postinfo() {        
		var v1=document.getElementById("category").value; 
		var v2=document.getElementById("val").value;
	    var temp = document.createElement("form");  
	    var PARAMS={category:v1,val:v2};
	    temp.action = "/Admin/SortCommentBycategory.user";        
	    temp.method = "post";        
	    temp.style.display = "none";        
	    for (var x in PARAMS) {        
	        var opt = document.createElement("textarea");        
	        opt.name = x;        
	        opt.value = PARAMS[x];
	        temp.appendChild(opt);        
	    }        
	    document.body.appendChild(temp);        
	    temp.submit();        
	    return temp;        
	}    
	
	function turnpage(url){
		var v1=document.getElementById("category").value; 
		var v2=document.getElementById("val").value;
		window.location.href=url+"&category="+v1+"&val="+v2;
	}
	
	function turnto(){
		var v=document.getElementById("index").value;
		var v1=document.getElementById("category").value; 
		var v2=document.getElementById("val").value;
		var url="/Admin/SortCommentBycategory.user?index="+v+"&category="+v1+"&val="+v2;
		window.location.href=url;
	}
</script>

<style type="text/css">
	table.imagetable {
		font-family: verdana,arial,sans-serif;
		font-size:11px;
		color:#333333;
		border-width: 1px;
		border-color: #999999;
		border-collapse: collapse;
	}
	table.imagetable th {
		background:#b5cfd2 url('images/cell-blue.jpg');
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #999999;
	}
	table.imagetable td {
		background:#dcddc0 url('images/cell-grey.jpg');
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #999999;
		table-layout: fixed;
	}
	#desc{
		background:url('images/cell-grey.jpg');
		padding: 8px;
		border: hidden;
	}
	option{
		width: 190px;
		font-style:oblique; 
	}
	a.turn{
		text-decoration: underline;
		font-size: 18px;
		font-style: oblique;
		color: green;
	}
	
	div.showdiv{background:#F00;color: #FFF; display:none;position:absolute;} 
</style>
</html>