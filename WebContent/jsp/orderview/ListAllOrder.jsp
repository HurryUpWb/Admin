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
<c:if test="${requestScope.list!=null && requestScope.list.size()>0}">
	<table class="imagetable" align="center" style="margin-left: 50px; position: static;">
		<tr align="right">
					<td  colspan="6">
						<input type="hidden" name="tty" id="tty" value="${requestScope.category}">
						<select name="category" id="category">
							<option value="">全部</option>
							<option value="o.o_id">订单号</option>
							<option value="o.u_id">用户编号</option>
							<option value="o.o_isdeliver">是否发货</option>
						</select>
					</td>
					<td align="center"><input name="val" id="val" value="${requestScope.val}"/></td>
					<td align="center" ><a name="find" id="find" class="button button-little bg-green" onclick="postinfo();">
							   查      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   找  </a></td>
		</tr>
		<tr>
			<th style="width: 60px;">订单号</th>
			<th style="width: 80px;">用户编号</th>
			<th style="width: 50px;">数量</th>
			<th style="width: 200px;">书籍名称</th>
			<th style="width: 180px;">下单时间</th>
			<th style="width: 30px;">总价</th>
			<th style="width: 120px;">订单状态</th>
			<th style="width: 120px;">是否发货</th>
		</tr>
		
			<c:forEach items="${requestScope.list}" var="b">
				<tr>
					<td align="center">${b.get('o_id')}</td>
					<td align="center">${b.get('u_id')}</td>	
					<td align="center">${b.get('o_booknum')}</td><%-- ${b.get('o_booknum')} --%>
					<td align="center">${b.get('bkname')}</td>		
					<td align="center">${b.get('o_orderdate')}</td>
					<td align="center">${b.get('o_totalprice')}</td>
					<td align="center">${b.get('p_state')==1?'已付款':'未付款'}</td>
					<td align="center">${b.get('o_isdeliver')==1?'是':'否'}</td>
				</tr>
			</c:forEach>
	
		<%	request.getSession().setAttribute("page",request.getAttribute("page")); %>
	</table>
	<c:if test="${requestScope.page.totalPage>1}">
		<div style="margin-left: 800px;">
			<c:if test="${requestScope.page.hasPrve}"><a href="#" class="turn" onclick="turnpage('/Admin/Turn.ord?C=F');">首页</a></c:if>&nbsp;&nbsp;
			<c:if test="${requestScope.page.hasPrve}"><a href="#" class="turn" onclick="turnpage('/Admin/Turn.ord?C=P');">上一页</a></c:if>&nbsp;&nbsp;
			<c:if test="${requestScope.page.hasNext}"><a href="#" class="turn" onclick="turnpage('/Admin/Turn.ord?C=N');">下一页</a></c:if>&nbsp;&nbsp;
			<c:if test="${requestScope.page.hasNext}"><a href="#" class="turn" onclick="turnpage('/Admin/Turn.ord?C=L');">末页</a></c:if>&nbsp;&nbsp;
			${requestScope.page.pageIndex}/${requestScope.page.totalPage}页 &nbsp;去<input size="2" name="index" id="index"/>页&nbsp;
			<a href="#" class="turn" onclick="turnto();">Go</a>
		</div>
	</c:if>
</c:if>
<c:if test="${requestScope.list.size()==0}">
	<h1 align="center"><font color="red">当前没有订单！</font></h1>
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
	    temp.action = "/Admin/Turn.ord";        
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
		var url="/Admin/Turn.ord?index="+v+"&category="+v1+"&val="+v2;
		window.location.href=url;
	}
</script>

</html>