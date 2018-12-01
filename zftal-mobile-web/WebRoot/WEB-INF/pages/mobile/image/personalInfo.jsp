<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>会员信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="0">
			<tr>
				<td rowspan="2"><img src="<%=path%>${param.oldImgPath}" /> </td>
				<td><img src="<%=path%>${param.imgName}" /> </td>
			</tr>
			<tr>
				<td><a href="index.jsp">返回上传图片</a></td>
			</tr>
		</table>
	</body>
</html>
