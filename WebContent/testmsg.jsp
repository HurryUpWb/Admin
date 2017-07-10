<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <script src="/Admin/js/jquery.js"></script>

<!-- 基于Comet的技术的消息推送主要分为流（streaming）方式和长轮询（long-polling）方式 -->
<script type="text/javascript">
	/* 流方式  */
	try {  
	    var request = new XMLHttpRequest();   
	} catch (e) {  
	    alert("Browser doesn't support window.XMLHttpRequest");  
	}  
	                           
	var pos = 0;  
	request.onreadystatechange = function () {  
	    if (request.readyState === 3) {   
	        var data = request.responseText;   
	        alert(data)
	        alert(pos)
	        $("#message").append(data.substring(pos));  
	        pos = data.length;  
	    }  
	};  
	request.open("POST", "/Admin/streaming.adm", true);  
	request.send(null);   
</script>
<!-- <script type="text/javascript">
	/*长轮询方式  */
	var updater = {  
		poll: function(){  
		    $.ajax({
		    		url: "/Admin/longPolling.adm",   
		            type: "POST",   
		            dataType: "text",  
		            success: updater.onSuccess,  
		            error: updater.onError
		        });  
			},
			onSuccess: function(data,dataStatus){  
			    try{  
			        $("#msg").append(data);  
			    }  
			    catch(e){  
			        updater.onError();  
			        return;  
			    }  
			    interval = window.setTimeout(updater.poll,0);  
			},  
			onError: function(){  
			    console.log("Poll error;");  
			}  
		};  
	updater.poll();  
</script>   -->
	
</head>
<body>
	<div id="message"></div>
	<div id="msg"></div>
</body>
</html>