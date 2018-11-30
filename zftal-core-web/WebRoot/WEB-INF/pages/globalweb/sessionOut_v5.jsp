<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
 	//用户当前页面框架  跳出
	if (top.location != self.location)  
	{
		top.location=self.location;
	}
</script>
<!doctype html>
  <head>
    <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
    <title>Session out</title>
    
	<link href="<%=stylePath %>css/error.css" rel="stylesheet" type="text/css">

  </head>
 
  <body>
  
  <div class="error_v5">
  	<input type="hidden" name="tzurl" id="tzurl" value="<%=request.getContextPath()%>/${tzurl}"/>
	<div class="error_ico"><i class="error3"></i></div>
	<div class="error_con">
		<p class="error_title">登录超时，</p>
		<p class="error_text2"><span class="time">5</span>秒后将自动返回登录页面<a href="javascript:void(0)" onclick="nowGo()" class="go_back0">立即返回</a></p>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(e) {
		$(window).resize();
		
		setTimeout("lazyGo();", 1000);
	});
	//计算高度
	window.onresize = function(){  
		var body_height	=	$(window).height();
		var error_height	=	$(".error_v5").height() + 50;
		var margin_top	=	(body_height - error_height)/2;
		var margin_top	= 	(margin_top>0) ? margin_top : 0;
		$(".error_v5").css("margin-top",margin_top);
	} 
	
	function lazyGo() {
		var url = $('#tzurl').val();
		var sec = $(".error_text2 .time").text();
		$(".error_text2 .time").text(--sec);
		if (sec > 0)
			setTimeout("lazyGo();", 1000);
		else
			window.location.href = url;
	}
	function nowGo(){
		window.location.href = $('#tzurl').val();
	}

</script>
  
  </body>
</html>
