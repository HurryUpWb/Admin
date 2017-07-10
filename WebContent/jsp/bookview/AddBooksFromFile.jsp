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
    function selectFile(input) {
        var fileName = input.value;
        if(fileName.length > 1 && fileName!='') {       
            var ldot = fileName.lastIndexOf(".");
            var type = fileName.substring(ldot + 1);
            if(type!="xls") {
                alert('文件格式必须为 .xls');
                input.outerHTML=input.outerHTML.replace(/(value=\").+\"/i,"$1\"");
            }       
        }
    }
    
    function isSub(){
    	var value=document.getElementById("files").value;
    	if(value==null || value==''){
    		alert('请添加文件');
    		return false;
    	}
    	return true;
    }
    </script>
</head>
<body>
  <div class="panel admin-panel" >
  <div class="panel-head"><strong><span class="icon-pencil-square-o"></span>${requestScope.item}</strong></div>
  		<form action="/Admin/AddBooksFromFile.show" method="post" style="margin-left: 150px; margin-top: 50px;" enctype="multipart/form-data" onsubmit="return isSub();">
  			<div>
  				<input type="file" name="files" id="files"  onchange="selectFile(this);"/>
  				<button type="submit">上传</button>
  			</div>
  			<br><br>
	  		<div style="margin-left: 180px;">
	  			<font color="red">没有模板?</font><a href="/Admin/downloadModel.show">点击下载</a>
	  		</div>
	  		<br><br><br><br><br>
  		</form>
  		
  </div>
</body>
</html>