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
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>


		
	</head>

	<body>
		<table>
			<tbody>
				<tr>
				<td><%=msg %></td>
				</tr>
				<tr>
				<td><a onclick="window.history.back();" style="cursor: pointer;">返回</a></td>
				</tr>
			</tbody>
		</table>
		
		
	</body>
</html>
