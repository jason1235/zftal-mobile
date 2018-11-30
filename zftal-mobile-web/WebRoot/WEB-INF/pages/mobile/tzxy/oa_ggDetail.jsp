<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport"
        content="width=device-width, initial-scale=1">
  <title>公告详情</title>

  <!-- Set render engine for 360 browser -->
  <meta name="renderer" content="webkit">

  <!-- No Baidu Siteapp-->
  <meta http-equiv="Cache-Control" content="no-siteapp"/>

  <!--<link rel="icon" type="image/png" href="<%=request.getContextPath() %>/assets/i/favicon.png">

  --><!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">
  <!--<link rel="icon" sizes="192x192" href="<%=request.getContextPath() %>/assets/i/app-icon72x72@2x.png">

  --><!-- Add to homescreen for Safari on iOS -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
  <link rel="apple-touch-icon-precomposed" href="<%=request.getContextPath() %>/assets/i/app-icon72x72@2x.png">

  <!-- Tile icon for Win8 (144x144 + tile color) -->
  <meta name="msapplication-TileImage" content="<%=request.getContextPath() %>/assets/i/app-icon72x72@2x.png">
  <meta name="msapplication-TileColor" content="#0e90d2">

  <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/amazeui.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/app.css">
  
  <style type="text/css">
     li a:hover{
     	color:black;
     }
     #contentDiv table tr td{
     	word-break: keep-all;
     }
  </style>
</head>
<body>
<!--在这里编写你的代码start-->
<div class="am-container"><br/>
  <div class="am-u-sm-centered">
     <div id="titleDiv" style="text-align:center;"><h5><s:property value="result.title"/></h5></div>
     <div id="pubDiv" style="text-align:center;font-size:10px;"><s:property value="result.time"/></div>
     <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
     <div id="contentDiv" style="text-align:center;"><s:property escape="false" value="result.content"/></div><br/>
  </div>	
</div>	
<!--在这里编写你的代码end-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="<%=request.getContextPath() %>/assets/js/amazeui.min.js"></script>
</body>
</html>