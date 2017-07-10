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
	 		 var flag=false;
	 		 var cc=cfr();
	 		 if(cc){
		 			 if(confirm("确定提交！")){
		 				flag=true;
		 			 	return true;
		 			 }else{
		 				 return false;
		 			 }
	 			}else{
	 			 return false;
	 		 }
	 	 }
	 	 function cfr(){
			if($(":text").val()==''){
				alert('请输入值!');
				return false;
			}	 		
	 		var regx=new RegExp("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
	 		if($(".bprice").val()!=''){
		 		if(!regx.test($(".bprice").val())){
		 			alert('请输入数字');
		 			return false;
		 		}
	 		}
	 		return true;
	 	 }
	 	 
    </script>
    <script type="text/javascript">
	$(function(){
		var i=1;
		$("#addfile").click(function(){
			$(this).parent().parent().before(
					"<tr>"+
					"<td><input type='text' name='bname' id='bname' class='bname'/></td>"+
					"<td><select name='btype' id='btype' style='width: 160px;'><c:forEach items='${requestScope.types}' var='type'><option value='${type.TYPENO}'>${type.TYPEINFO}</option></c:forEach></select></td>"+
					"<td><input type='file' name='img' id='img' style='width: 185px;'>"+
					"<td><input class='in1' type='text' name='bauthor' id='bauthor' ></td>"+
					"<td><input  type='text' name='bpubs' id='bpubs' style='width: 185px;'></td>"+
					"<td><input class='bprice' type='text' name='bprice' id='bprice'></td>"+
					"<td><textarea  rows='0px' cols='20px' name='desc' id='desc' style='resize:none;'></textarea></td>"+
					"<td><input type='button' id='delete"+i+"' value='删除''></td></tr>");
				//获取新的删除按钮
				i++;	
				$("#delete"+(i-1)).click(function(){
					var $tr=$(this).parent().parent();
					$tr.remove();
				});
			});
			 
		
	});
    </script>
    
    <style type="text/css">
    	table{
    		border:black;
    		border-collapse: 1px;
    		border-spacing: 10px;
    	}
    	td{
    		azimuth: center;
    		width: 50px;
    	}
    	.in1{
    		width: 75px;
    	}
    	.bprice{
    		width: 75px;
    	}
    </style>
</head>
<body>
  <div class="panel admin-panel">
  <div class="panel-head"><strong><span class="icon-pencil-square-o"></span>${sessionScope.item}</strong></div>
  <div class="body-content">
    <form  class="form-x" action="/Admin/AddBook.show" method="post" 
      			id="frm" name="frm" onsubmit="return tj();" enctype="multipart/form-data">
      	 <table>
      	 	<tr>
      	 			<th>书名</th>
      	 			<th>类型</th>
      	 			<th>图片</th>
      	 			<th>作者</th>
      	 			<th>出版社</th>
      	 			<th>价格</th>
      	 			<th>描述</th>
      	 	</tr>
      		<tr>
	      		<td>	
	         	 	<input type="text" name="bname" id="bname" class='bname'/>
	         	</td>
	         	<td>
			 		<select name="btype" id="btype" style="width: 160px;">
			  		<c:forEach items="${requestScope.types}" var="type">
			  			<option value="${type.TYPENO}">${type.TYPEINFO}</option>
			  		</c:forEach>
			 		</select>
	   			</td>
	   			<td>
				       <input type="file" name="img" id="img" style="width: 185px;">
		        </td>
	      		<td>
			  		<input class="in1" type="text" name="bauthor" id="bauthor" >
			  	</td>
	      		<td>
			  		<input  type="text" name="bpubs" id="bpubs" style="width: 185px;">
	            </td>
	            <td>
			  		<input class="bprice" type="text" name="bprice" id="bprice">
			  	</td>
	      		<td>
	        		<textarea  rows="0px" cols="20px" name="desc" id="desc" style="resize:none;"></textarea>
	        	</td>
        	</tr>
        	<tr>
        		<td>
        			<button class="button button-little bg-green" type="submit">上传</button>
        			<a class="button button-little bg-blue" href="#" id="addfile">添加</a>
        		</td>
        	</tr>
      </table>
    </form>
  </div>
</div>
</body>
</html>