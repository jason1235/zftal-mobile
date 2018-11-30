<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
	   <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body>
		<!--出错提示页-->
		<div class="page_error">
			<div class="con_error">
		    	<h3>页面出错了，别着急！请尝试以下操作：</h3>
		        <span>给您带来不便，我们深表歉意。</span>
		        <p>1服务器未正常启动！请重新启动服务器再进行登录！</p>
				<p>2.服务器太忙，请稍候再访问！</p>
				<p>3.连接超时，请重新登录系统！</p>
				<p>4.该文件或页面被删除！请联系系统管理员！</p>
				<p>5.页面配置错误！请联系系统管理员！</p>
				<p>6.程序出错！请联系系统管理员！</p>
		    </div>
		    <br/>
		    <div style="text-align:center">
		    <button onclick="history.back();">返回</button>
		    </div>
		</div>
	</body>
</html>