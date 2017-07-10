<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">


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
</head>
<body style="background-color:#F2F9FD;" id="bd">
	<table class="imagetable" align="center" style="margin-left: 50px; position: static;">
		<tr align="right">
					<input type="hidden" value="${requestScope.category}" name="tty" id="tty"/>
					<td  colspan="7">
						<select name="category" id="category">
							<option value="">全部</option>
							<option value="b_id">ID</option>
							<option value="b_bookname">书名</option>
							<option value="b_author">作者</option>
							<option value="b_booktype">类型</option>
							<option value="b_pubs">出版社</option>
							<option value="b_price">价格</option>
							<option value="b_show">在线</option>
						</select>
					</td>
					<td align="center"><input name="val" id="val" value="${requestScope.val}"/></td>
					<td align="center" ><a name="find" id="find" class="button button-little bg-green" onclick="postinfo();">
							   查      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   找  </a></td>
				</tr>
		<tr>
			<th style="width: 20px;">ID</th>
			<th style="width: 80px;">书名</th>
			<th style="width: 50px;">作者</th>
			<th style="width: 160px;">类型</th>
			<th style="width: 100px;">出版社</th>
			<th style="width: 40px;">价格</th>
			<th style="width: 60px;">在线</th>
			<th style="width: 180px;">简介</th>
			<th style="width: 180px;"></th>
		</tr>
		<c:if test="${requestScope.blist!=null && requestScope.blist.size()>0}">
			<c:forEach items="${requestScope.blist}" var="b">
				<tr>
					<td align="center">${b.b_id}</td>
					<td align="center">${b.b_bookname}</td>	
					<td align="center">${b.b_author}</td>
					<td align="center" style="table-layout: fixed;">${b.b_booktype}</td>		
					<td align="center">${b.b_pubs}</td>
					<td align="center">${b.b_price}</td>
					<td align="center">${b.b_show==1?'是':'否'}</td>
					<td align="center">
						<input value="${b.b_description}" readonly="readonly" id="desc"/></td>
					<td align="center">
						<a href="/Admin/EditBook.show?id=${b.b_id}" class="button button-little bg-blue">
						<span class="icon-wrench"></span>编&nbsp;&nbsp;&nbsp;辑</a></td>
					
				</tr>
			</c:forEach>
		</c:if>
		<%	request.getSession().setAttribute("page",request.getAttribute("page")); %>
	</table>
	<c:if test="${requestScope.page.totalPage>1}">
		<div style="margin-left: 760px;">
			<c:if test="${requestScope.page.hasPrve}"><a href="#" class="turn" onclick="turnpage('/Admin/Turn.show?C=F');">首页</a></c:if>&nbsp;&nbsp;
			<c:if test="${requestScope.page.hasPrve}"><a href="#" class="turn" onclick="turnpage('/Admin/Turn.show?C=P');">上一页</a></c:if>&nbsp;&nbsp;
			<c:if test="${requestScope.page.hasNext}"><a href="#" class="turn" onclick="turnpage('/Admin/Turn.show?C=N');">下一页</a></c:if>&nbsp;&nbsp;
			<c:if test="${requestScope.page.hasNext}"><a href="#" class="turn" onclick="turnpage('/Admin/Turn.show?C=L');">末页</a></c:if>&nbsp;&nbsp;
			${requestScope.page.pageIndex}/${requestScope.page.totalPage}页 &nbsp;去<input size="2" name="index" id="index"/>页&nbsp;
			<a href="#" class="turn" onclick="turnto();">Go</a>
		</div>
	</c:if>
</body>
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
	    temp.action = "/Admin/Turn.show";        
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
		var url="/Admin/Turn.show?index="+v+"&category="+v1+"&val="+v2;
		window.location.href=url;
	}
</script>

</html>