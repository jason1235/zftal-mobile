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
  <title>通知公告</title>

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
  </style>
</head>
<body>
<!--在这里编写你的代码start-->
<div class="am-container"><br/>
  <div class="am-u-sm-centered">
    <div style="text-align:center;"><h4>通知公告</h4></div>
    <div id="searchBar" style="margin-bottom:5px;text-align:right;">
		<input type="text" id="newsTitle" name="newsTitle" placeholder="输入标题">
		<button type="button" class="am-btn am-btn-primary am-btn-xs am-radius" onclick="getList()">查询</button>
    </div>
	<div id="ggDiv" style="font-size:14px;">
	
	</div>
	<div id="getMore" style="text-align:center;">
		<button type="button" class="am-btn am-btn-primary am-btn-sm" onclick="getMore()">更多...</button>
	</div>
  </div>	
</div>	
<!--在这里编写你的代码end-->

<!-- 加载的数据条数隐藏域start -->
<input type="hidden" name="rownum" id="rownum" value="10"/>
<!-- 加载的数据条数隐藏域end -->

<div id="newsId" style="">${newsId}</div>

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

	//加载通用公告列表，rownum为加载条数
    function getList(){
        var rownum = $("#rownum").val();
    	var title = $("#newsTitle").val();
		var postUrl = "<%=request.getContextPath() %>/tzxynews/tzxynews_list.html";
		var ggHtml = "<table class='am-table am-table-striped am-table-hover'><tr><th>标题</th><th>发布时间</th></tr>";
		
		$.post(postUrl,{
		    "rownum":rownum,
		    "title":title
		  },
		  function(result){
			if(!!result&&result.length>0){
				for(var i=0;i<result.length;i++){
					ggHtml += ("<tr><td style='color:blue;'><a href='tzxynews/tzxynews_toNewsDetail.html?newsId="+result[i].id+"'>"+result[i].title+"</a></td><td>"+result[i].time+"</td></tr>");
				}
			}
			ggHtml +="</table>";
			$("#ggDiv").html(ggHtml);
		},"json");
    }
    
    //加载更多
    function getMore(){
        $("#newsTitle").val('');
    	var rownum = $("#rownum").val();
    	$("#rownum").val(parseInt(rownum)+5);
    	getList();
    }
    
     //公告详情
    function getGgDetail(){
        var ggId = $("newsId").text().trim();
        var webUrl = "zjtjNotice_getZjtjNotice.html?zjtjNoticeQuery.newsId="+ggId;
		$.get(webUrl, function(result){
			if(result.status=='success'){
				if(!!result.data&&result.data.length>0){
				}
			}
		},"json");
    }
    
	$(function(){
		getList();
	});
</script>
</body>
</html>