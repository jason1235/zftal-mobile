<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini" %>
  </head>
  
  <body class="bg-white">

	<div class="tips">
		<!--<p class="t1"><span>您正在使用的浏览器版本过低，请立即升级至</span><a href="http://www.microsoft.com/zh-cn/download/internet-explorer-8-details.aspx" class="ico_browser ie ie8" target="_blank"></a></p>-->
		<p class="t1"><span>您正在使用的浏览器版本过低，请立即升级至</span><a href="http://www.microsoft.com/zh-cn/download/internet-explorer-9-details.aspx" class="ico_browser ie ie9" target="_blank"></a></p>
		<p class="t1">或安装/使用下列最新版本浏览器：</p>
		<ul class="browser_list">
			<li><a href="http://www.google.cn/intl/zh-CN/chrome/browser/" target="_blank"><i class="ico_browser c"></i>chrome</a></li>
			<li><a href="http://www.firefox.com.cn/download/" target="_blank"><i class="ico_browser f"></i>Firefox</a></li></a></li>
			<li><a href="http://support.apple.com/kb/DL1531?viewlocale=zh_CN" target="_blank"><i class="ico_browser s"></i>safari</a></li>
		</ul>
	</div>
	
	<div class="tips_top" style="display:none;">
		<div class="w_1000">
			<span>亲爱的用户，为了达到最优的网站体验效果，我们建议您安装/使用下列最新版本浏览器：</span>
			<ul class="browser_list">
				<li><a href="http://www.google.cn/intl/zh-CN/chrome/browser/" target="_blank"><i class="ico_browser c"></i>chrome</a></li>
				<li><a href="http://www.firefox.com.cn/download/" target="_blank"><i class="ico_browser f"></i>Firefox</a></li></a></li>
				<li><a href="http://support.apple.com/kb/DL1531?viewlocale=zh_CN" target="_blank"><i class="ico_browser s"></i>safari</a></li>
			</ul>
			<a href="#" class="close"><i class="ico_browser"></i></a>
		</div>
	</div>
	
	<div class="tips_top" style="display:none;">
		<div class="w_1000">
			<span>亲爱的用户，为了达到最优的网站体验效果，我们建议您安装/使用下列最新版本浏览器：</span>
			<ul class="browser_list">
				<li><a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" target="_blank"><i class="ico_browser i"></i>Internet Explorer 11</a></li>
				<li><a href="http://www.google.cn/intl/zh-CN/chrome/browser/" target="_blank"><i class="ico_browser c"></i>chrome</a></li>
				<li><a href="http://www.firefox.com.cn/download/" target="_blank"><i class="ico_browser f"></i>Firefox</a></li></a></li>
				<li><a href="http://support.apple.com/kb/DL1531?viewlocale=zh_CN" target="_blank"><i class="ico_browser s"></i>safari</a></li>
			</ul>
			<a href="#" class="close"><i class="ico_browser"></i></a>
		</div>
	</div>

<script type="text/javascript">
	$(document).ready(function(e) {
		$(window).resize();
	});
	//计算高度
	window.onresize = function(){  
		var body_height	=	$(window).height();
		var error_height	=	$(".tips").height() + 50;
		var margin_top	=	(body_height - error_height)/2;
		var margin_top	= 	(margin_top>0) ? margin_top : 0;
		$(".tips").css("margin-top",margin_top);
	}
</script>


</body>
</html>
