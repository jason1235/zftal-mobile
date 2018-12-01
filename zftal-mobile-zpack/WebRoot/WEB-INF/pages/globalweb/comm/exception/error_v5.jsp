<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
	   <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	   <link href="<%=stylePath %>css/error.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="error_v5">
	<div class="error_ico"><i class="error2"></i></div>
	<div class="error_con">
		<p class="error_title">出错啦！</p>
		<p class="error_text0 hidden">系统运行异常，请稍后再试</p>
		<p class="error_text"></p>
		<%--
		<p class="error_link"><a href="#" class="go_index"><i></i>返回首页</a><a href="javascript:history.go(-1)" class="go_back"><i></i>返回上一级</a></p>
		--%>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(e) {
		$(window).resize();
		
		changeText($(".error_text0"),$(".error_text"),50);
	});
	//计算高度
	window.onresize = function(){  
		var body_height	=	$(window).height();
		var error_height	=	$(".error_v5").height() + 50;
		var margin_top	=	(body_height - error_height)/2;
		var margin_top	= 	(margin_top>0) ? margin_top : 0;
		$(".error_v5").css("margin-top",margin_top);
	} 
	
	//文字打印效果
	function changeText(cont1,cont2,speed){
		var Otext=cont1.text();
		var Ocontent=Otext.split("");
		var i=0;
		function show(){
			if(i<Ocontent.length)
			{		
				cont2.append(Ocontent[i]);
				i=i+1;
			};
		};
			var Otimer=setInterval(show,speed);	
	};
</script>
	</body>
</html>