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
<script type="text/javascript">
	function altRows(id){
		
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
</head>
<body style="background-color:#F2F9FD;" id="bd">
<c:if test="${(requestScope.list!=null) && (requestScope.size>0)}">
	<table class="imagetable" align="center" style="margin-left: 50px; ">
		<tr>
			<th style="width: 60px;">订单号</th>
			<th style="width: 80px;">书籍数量</th>
			<th style="width: 180px;">书名</th>
			<th style="width: 180px;">下单日期</th>
			<th style="width: 100px;">总价</th>
			<th style="width: 60px;">状态</th>
			<th style="width: 380px;">付款码</th>
		</tr>
			<c:forEach items="${requestScope.list}" var="b">
				<tr>
					<td align="center">${b.get('o_id')}</td>
					<td align="center">${b.get('o_booknum')}</td>	
					<td align="center">${b.get('bkname')}</td>
					<td align="center">${b.get('o_orderdate')}</td>
					<td align="center">${b.get('o_totalprice')}</td>
					<td align="center">${b.get('p_state')==0 ? '未付款':'已付款'}</td>
					<td align="center">${b.get('p_code')}</td>
				</tr>
			</c:forEach>
	</table>
</c:if>
<c:if test="${!(requestScope.size>0)}">
	<font color="light green" size="10px;" style="margin-left: 350px; margin-top: 200px;">当前用户没有订单</font>
</c:if>
</body>
</html>