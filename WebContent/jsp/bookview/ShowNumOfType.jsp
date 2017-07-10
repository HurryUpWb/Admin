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
     #td1{
		background:#dcddc0 url('images/cell-grey.jpg');
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #999999;
		table-layout: fixed;
		width: 310px;
	}
	
	 #td2{
		background:#dcddc0 url('images/cell-grey.jpg');
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #999999;
		table-layout: fixed;
		width: 30px;
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
	
</style>
</head>
<body style="background-color:#F2F9FD;" id="bd">
	<table class="imagetable" align="center" style="margin-left: 280px; margin-top: 40px;">
		<tr>
			<th style="width: 20px;">书籍类型</th>
			<th style="width: 80px;">数量</th>
		</tr>
		<c:if test="${requestScope.list!=null}">
			<c:forEach items="${requestScope.list}" var="b">
				<tr>
					<td align="center" id="td1">${b.TYPEINFO}</td>
					<td align="center" id="td2">${b.num}</td>	
				</tr>
			</c:forEach>
				<tr>
					<td>--------------------------------------------------------------</td>
					<td>----------------</td>
				</tr>
				<tr>
					<td id="td1" align="center" >总数</td>
					<td id="td2" align="center">${requestScope.sum}</td>
				</tr>
		</c:if>
	</table>
</body>


</html>