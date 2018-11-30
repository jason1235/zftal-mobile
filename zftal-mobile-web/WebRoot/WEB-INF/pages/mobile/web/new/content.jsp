<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html style="background: #FFFFFF;">
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<script type="text/javascript" src="/zfstyle_v5/js/jquery-1.11.0.min.js"></script>
<title></title>

</head>
<body style="height: auto;padding:10px;">
	<script type="text/x-handlebars-template" id="amz-tpl">
<div class="am-article" id="main">
    <div style="display:block;text-align:center;font-size:15pt;font-family:Microsoft Yahei;">${news.title}</div>
	<div style="width:100%;min-height:25pt;font-size:12pt;color:#B5B5B5;padding:1pt;clear:both;">
		<p style="float:left;border-bottom:1px solid #B5B5B5;width:100%"><s:date name="#request.news.createTime" format="yyyy-MM-dd" /> &nbsp; ${news.author}</p>
		<!--<span style="float:right">发布日期：<s:date name="#request.news.createTime" format="yyyy-MM-dd" /></span>-->
	</div>
	<div class="am-article-bd" style="margin-top:10pt;font-size:15pt;" id="newcontent">${news.content}</div>
</div>

</script>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script type="text/javascript"> 
	$(function(){
		//var src = this.src;
		//var start = src.indexOf("_upload");
		//if(start != -1){
		//	this.src = "http://www.wnmc.edu.cn/"+ src.substring(start);
		//}
	})
	function ReplaceAll(str, sptr, sptr1){
                    while (str.indexOf(sptr) >= 0){
                       str = str.replace(sptr, sptr1);
                    }
                    return str;
             }
	function isMobile() {
        var agent = navigator.userAgent.toLowerCase();
        var patterns = ["android","iphone","windows phone","windows ce","ipad","ipod","rv:1.2.3.4","ucweb"];
        for (var i = 0; i < patterns.length; i++) {
            if (agent.indexOf(patterns[i]) > -1) {
                return patterns[i];
            }
        }
        return false;
    }
    
    function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge)
			}, false)
		}
	}
    
    
    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    data.header.content.title = '${title}';
    data.header.content.left = [{link:"javascript:goBack()",icon:"chevron-left",className:"back-button"}];
    var html = template(data);
    $tpl.before(html);
    var mobileDevice = isMobile();
    
    if (mobileDevice) {
    	//if ('${type}' === 'DETAIL_SHOW') {
    		$('.am-header').css('display','none');
    	//}
    }
    
 	function goBack() {
 		if (mobileDevice) {
 			if (mobileDevice === 'android') {
 				MobileJavaApi.Quit();
 			}
 		}
 		history.go(-1);
 	}
    
    connectWebViewJavascriptBridge(function(bridge) {
		var callbackButton = document.getElementsByClassName('back-button')[0]
		callbackButton.onclick = function(e) {
			e.preventDefault()
			bridge.callHandler('testObjcCallback', function(response) {
                               
			})
		}
	})
    
    
    data.header.content.left = [];
</script>	
</body>
</html>