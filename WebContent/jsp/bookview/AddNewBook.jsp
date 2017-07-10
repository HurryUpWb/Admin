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
	    window.onload = function () {
	        new uploadPreview({ UpBtn: "img", DivShow: "imgdiv1", ImgShow: "imgShow1" });
	        
	        var img=document.getElementById("getimg").value;
	        if(img!=null && img!=""){
	        	document.getElementById("imgShow1").src=img;	
	        }
	        
	        //确定select 
	        var val=document.getElementById("tty").value;
			var select=document.getElementById("btype");
			for(var i=0;i<select.options.length;i++){
				if(select.options[i].value==val){
					select.options[i].selected=true;
					break;
				}
			}
			
			//确定select 
	        var val=document.getElementById("shw").value;
			var select=document.getElementById("bshow");
			for(var i=0;i<select.options.length;i++){
				if(select.options[i].value==val){
					select.options[i].selected=true;
					break;
				}
			}
	 	 };
	 	 
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
	 		 if($("#bname").val()==''){
				alert("请输入书名");
				return false;
			}
	 		
	 		if($("#btype").val()==''){
				 alert("请选择类别");
				return false;
			}
	 		
	 		if($("#img").val()=='' && $("#getimg").val()==''){
				 alert("请选择图片");
				return false;
			}
	 		
	 		if($("#bauthor").val()==''){
				 alert("请输入作者名");
				return false;
			}
	 		
	 		if($("#bpubs").val()==''){
				 alert("请输入出版社名称");
				return false;
			}
	 		
	 		if($("#bprice").val()==''){
				alert("请输入价格");
				return false;
			}
	 		
	 		var regx=new RegExp("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
	 		if(!regx.test($("#bprice").val())){
	 			alert('请输入数字');
	 			return false;
	 		}
	 		
	 		if($("#desc").val()==''){
				 alert("请输入书籍描述");
				return false;
			} 
	 		return true;
	 	 }
	 	 
	 	 function upadteinfo(){
	 		 document.getElementById("frm").action='/Admin/SaveEdit.show';
	 	 }
	 	 
	 	 function deletebook(){
	 		 var id=document.getElementById("bid").value;
	 		 if(confirm('确定删除？')){
	 			 document.getElementById("deletebook").href='/Admin/Delete.show?bid='+id;
	 		 }
	 	 }
    </script>
</head>
<body>
  <div class="panel admin-panel">
  <div class="panel-head"><strong><span class="icon-pencil-square-o"></span>${requestScope.item}</strong></div>
  <div class="body-content">
    <form  class="form-x" action="/Admin/AddBook.show" method="post" 
      			id="frm" name="frm" onsubmit="return tj();" enctype="multipart/form-data">
      <div class="form-group">
        <div class="label">
          <label>书名：</label>
        </div>
        <div class="field">
          <input type="hidden" name="bid" id="bid" value="${requestScope.book.b_id}"/>
          <input type="text" name="bname" id="bname" value="${requestScope.book.b_bookname}"/>
         
          <div class="tips"></div>
        </div>
      </div>
      <div class="form-group">
        <div class="label">
          <label>所属类别：</label>
        </div>
        <input type="hidden" id="tty" name="tty" value="${requestScope.book.b_booktype}"/>
        <div class="field">
        	<select name="btype" id="btype">
        		<c:forEach items="${requestScope.types}" var="type">
        			<option value="${type.TYPENO}">${type.TYPEINFO}</option>
        		</c:forEach>
        	</select>
        </div>
      </div>
      
      <div class="form-group">
      	<input type="hidden" id="shw" name="shw" value="${requestScope.book.b_show}"/>
        <div class="label">
          <label>是否展示：</label>
        </div>
        <div class="field">
        	<select name="bshow" id=bshow>
        		<option value="1">是</option>
        		<option value="0">否</option>
        	</select>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>图片	：</label>
        </div>
	        <div id="imgdiv1" class="examp">
	        	 <input type="hidden" name="getimg" id="getimg" value="${requestScope.book.b_pic}"/>
	             <img id="imgShow1" src="/Admin/images/nopic.jpg" width="100" height="100" />
	        </div>
	        <br>
			<div class="info mt_5">
			   <a href="javascript:;" class="a-upload">
                 		<input type="file" name="img" id="img"> </a>
			</div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>作者：</label>
        </div>
        <div class="field">
		  <input type="text" name="bauthor" id="bauthor" value="${requestScope.book.b_author}">
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>出版社：</label>
        </div>
        <div class="field">
		  <input type="text" name="bpubs" id="bpubs" value="${requestScope.book.b_pubs}">
          <div class="tips"></div>
        </div>
      </div>
              
      <div class="form-group">
        <div class="label">
          <label>价格：</label>
        </div>
        <div class="field">
		  <input type="text" name="bprice" id="bprice" value="${requestScope.book.b_price}">
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label>作品描述：</label>
        </div>
        <div class="field">
        	<textarea  rows="10px" cols="80px" name="desc" id="desc">${requestScope.book.b_description}</textarea>
          <div class="tips"></div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="label">
          <label></label>
        </div>
        <div class="field">
           <c:if test="${requestScope.book.b_id==null}">
          	<button class="button bg-main icon-check-square-o" type="submit">保存</button>
          </c:if>
          <c:if test="${requestScope.book.b_id!=null}">
          	<button class="button bg-main " type="submit" onclick="upadteinfo();">修改</button>
          	<a href="##" id="deletebook" class="button bg-main" onclick="deletebook();">删除</a>
          </c:if>
          <a href="javascript:history.go(-1);" class="button bg-main">返回</a>
        </div>
      </div>
    </form>
  </div>
</div>
</body>
</html>