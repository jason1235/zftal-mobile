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
  <title>调查问卷列表</title>

  <!-- Set render engine for 360 browser -->
  <meta name="renderer" content="webkit">

  <!-- No Baidu Siteapp-->
  <meta http-equiv="Cache-Control" content="no-siteapp"/>

  <link rel="icon" type="image/png" href="<%=request.getContextPath() %>/assets/i/favicon.png">

  <!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">
  <link rel="icon" sizes="192x192" href="<%=request.getContextPath() %>/assets/i/app-icon72x72@2x.png">

  <!-- Add to homescreen for Safari on iOS -->
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
  </style>
</head>
<body>
<!--在这里编写你的代码start-->
<div class="am-container"><br/>
 <div class="am-u-sm-centered">
	<div data-am-widget="list_news" class="am-list-news am-list-news-default" >
 	<div style="text-align: center;">我的调查问卷</div>
	  <!--列表标题-->
	    <div class="am-list-news-hd am-cf">
	       <!--带更多链接-->
	        <span>
	          <h2>名称</h2>
	          <span class="am-list-news-more am-fr">截止时间&nbsp;&nbsp;</span>
	        </span>
	    </div>
	
	  <div class="am-list-news-bd">
	  <ul class="am-list">
		<s:if test="exampaperList.size()==0">暂时没有您可参与的问卷调查！</s:if>	
		<s:iterator value="exampaperList" var="p" status="st">
	      <li class="am-g am-list-item-dated">
	         <div style="margin-top:10px;margin-bottom:10px;">
		          <a href="javascript:void(0)" class="am-list-item-hd ">
		          	<s:property value="#p.paperMainName"/><br/>
		          	<font style="color:gray;font-size:10px;">
		          	    <s:if test="''.equals(#p.instruction)||#p.instruction==null">
					         &nbsp;
					    </s:if>
		          		<s:property value="#p.instruction"/>
		          	</font>
		          </a>
		          <span class="am-list-date">
		              <span>
		                <s:date name="#p.endTime" format="yyyy-MM-dd hh:mm" /><br/>
		             	<font style="font-size:10px;float:right;">
				          	<s:if test="#p.shiFouCanYu!=null&&'yes'.equals(#p.shiFouCanYu)">
					          	<span class="am-badge am-badge-primary am-radius">已参与&nbsp;</span>
					        </s:if>
		            		<s:if test="'已结束'.equals(#p.status)">
					          	<font style="color:red;">已结束</font>
					        </s:if>
		             	    <s:else>
		             	        <font style="color:green;">进行中</font>
		             	    </s:else>   
		             	</font>
		              </span>
	              </span>
              </div>
	      </li>
      	  <div class="operation" style="height:40px;text-align: center;line-height:40px; display: none;">
      	  	<s:if test="#p.shiFouCanYu!=null&&'no'.equals(#p.shiFouCanYu)&&#p.status!='已结束'">
      	   		<span class="partIn am-icon-user-plus" id="${p.paperMainId}" style="cursor: pointer;margin-right:50px;">参与</span>
      	    </s:if>
      	     <span class="watch am-icon-eye" id="${p.paperMainId}" style="cursor: pointer;">查看</span>
      	  </div>
	    </s:iterator>  
	  </ul>
	  </div>
	
	</div>
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
<script type="text/javascript">
	$(function(){
		$("li").click(function(){
			var nextDiv = $(this).next("div");
			$(nextDiv).toggle(300);
		});
		
		$("div.operation span.partIn").click(function(){
			var idVal = $(this).attr("id");
			window.location.href="<%=request.getContextPath() %>/twoExampaper/twoExampaper_partIn.html?query.paperMainId="+idVal;
		});
		
		$("div.operation span.watch").click(function(){
			var idVal = $(this).attr("id");
			window.location.href="<%=request.getContextPath() %>/serviceManager/twoQuestion_getExamResult.html?papermainid="+idVal;
		});
	});
</script>
</body>
</html>