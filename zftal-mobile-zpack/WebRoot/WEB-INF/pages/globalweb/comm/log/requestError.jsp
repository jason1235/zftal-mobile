<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String msg=request.getParameter("msg");		
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ page language="java" contentType="text/html; charset=UTF-8"%>

		
		<%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>


		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" /> 
		<meta http-equiv="Pragma" http-equiv="no-cache" />
		<meta http-equiv="Cache-Control"
			http-equiv="no-cache, must-revalidate" />
		<meta http-equiv="Expires" http-equiv="0" />
	
	<link rel="icon" href="images/icon.ico" type="image/x-icon" />
	<script src="<%=systemPath %>/js/My97DatePicker/WdatePicker.js"></script>
	<base target="_self">
	
	<link rel="stylesheet" href="<%=stylePath %>css/public.css" type="text/css" media="all" />
	<link rel="stylesheet" href="<%=stylePath %>css/module.css" type="text/css" media="all" />
	<link rel="stylesheet" href="<%=stylePath %>css/skin_blue.css" type="text/css" media="all" />	
	
	<script type="text/javascript"
			src="<%=systemPath %>/js/jquery/jquery-1.6.4.min.js"></script>
	<script type="text/javascript"
			src="<%=systemPath %>/js/jquery/jquery-ui-1.8.16.custom.min.js"></script>
 
	<%
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	%>


		
	</head>

	<body>
		<table>
			<tbody>
				<tr>
				<td>${msg }</td>
				</tr>
				<tr>
				<td><a onclick="window.history.back();" style="cursor: pointer;">返回</a></td>
				</tr>
			</tbody>
		</table>
		
		
	</body>
</html>
