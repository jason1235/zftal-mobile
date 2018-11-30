<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %> 
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/hrm/head_v5.ini" %>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="telephone=no" name="format-detection" />

<title>学校黄页</title>
<link href="<%=request.getContextPath() %>/assets/css/global.css" type="text/css" rel="stylesheet" />

<style type="text/css">
	.f_size14 {
		font-size:14px;
	}
</style>
</head>
<body>
    <div class="mainframe" id="wrapper" style="top:5px">
		<div class="mainframe1">
			<div id="list" class="list margin_b_0">
				<ul>
						<li>
						<span style="display:inline; ">
							
								<p style="border: 0px;padding: 0px;">
								<font size="5px" style="margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${commentsList[0].theVal }</font>
								<font size="2px" style="font-weight: normal;float: right;margin-top: 2px;">${model.dh }</font>
								</p>
						</span>
						</li>
						
						<li>
						<span style="display:inline; ">
							
								<p style="border: 0px;padding: 0px;">
								<font size="5px" style="margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${commentsList[1].theVal }</font>
								<font size="2px" style="font-weight: normal;float: right;margin-top: 2px;">${model.bm }</font>
								</p>
						</span>
						</li>
						<li>
						<span style="display:inline; ">
							
								<p style="border: 0px;padding: 0px;">
								<font size="5px" style="margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${commentsList[2].theVal }</font>
								<font size="2px" style="font-weight: normal;float: right;margin-top: 2px;">${model.ks }</font>
								</p>
						</span>
						</li>
						<li>
						<span>
							
								<p style="border: 0px;padding: 0px;">
								<font size="5px" style="margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${commentsList[3].theVal }</font>
								<font size="2px" style="font-weight: normal;float: right;margin-top: 2px;">${model.zyyw }</font>
								</p>
						</span>
						</li>
						
						<li>
						<span style="display:inline; ">
							
								<p style="border: 0px;padding: 0px;">
								<font size="5px" style="margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${commentsList[4].theVal }</font>
								<font size="2px" style="font-weight: normal;float: right;margin-top: 2px;">${model.bgdd1 }</font>
								</p>
						</span>
						</li>
						
						<li>
						<span style="display:inline; ">
							
								<p style="border: 0px;padding: 0px;">
								<font size="5px" style="margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${commentsList[5].theVal }</font>
								<font size="2px" style="font-weight: normal;float: right;margin-top: 2px;">${model.bgdd2 }</font>
								</p>
						</span>
						</li>
						<li>
						<span style="display:inline; ">
							
								<p style="border: 0px;padding: 0px;">
								<font size="5px" style="margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${commentsList[6].theVal }</font>
								<font size="2px" style="font-weight: normal;float: right;margin-top: 2px;">${model.bmbc }</font>
								</p>
						</span>
						</li>
						<li>
						<span style="display:inline; ">
							
								<p style="border: 0px;padding: 0px;">
								<font size="5px" style="margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${commentsList[7].theVal }</font>
								<font size="2px" style="font-weight: normal;float: right;margin-top: 2px;">${model.chuanzhen }</font>
								</p>
						</span>
						</li>
				</ul>
				<div style=" width: 100%; padding-top: 20px;">
				
			        <button  onclick="CallThePhone('${model.dh}');" type="button"" style="border: none; color:#fff; background:#357ebd;width:95%;margin-left:2%;font-size: 16px; text-align: center; height: 40px;">
			       		呼叫
			        </button>
			        <button onclick="history.back();" type="button"" style="border: 1px solid #357ebd; color:#357ebd; background:#ffffff;width:95%;margin-left:2%;mafont-size: 16px; text-align: center; height: 40px;margin-top: 15px;">
			       		返回
			        </button>
			    </div>
			</div>
				 
			</div>
		</div>
    </div>
</form>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/iscroll.js"></script>
<script type="text/javascript">

	
	
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
    
    function CallThePhone(phone){
    	if (mobileDevice === 'android') {
 				MobileJavaApi.CallPhone(phone);
 			}
    	if (mobileDevice === 'iphone') {
				window.location.href="tel:"+ phone +"";
			}
    }
   
    var mobileDevice = isMobile();
    
    
 	function goBack() {
 		if (mobileDevice) {
 			if (mobileDevice === 'android') {
 				MobileJavaApi.Quit();
 			}
 			
 		}
 		history.go(-1);
 	}
    
    connectWebViewJavascriptBridge(function(bridge) {
		var callbackButton = document.getElementsByClassName('back')[0]
		callbackButton.onclick = function(e) {
			e.preventDefault()
			bridge.callHandler('testObjcCallback', function(response) {
                               
			})
		}
	})
    
</script>	
</body>
</html>