<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/global.css">
<title></title>
        <style type="text/css">
        .am-navbar-default .am-navbar-nav {
            /*background-color: #3c3c3c;*/
        }
        .am-offcanvas-bar {
            left: auto;
            right: 0;
            -webkit-transform: translateX(100%);
            -ms-transform: translateX(100%);
            transform: translateX(100%);
        }
        .am-offcanvas-bar:after {
            right: auto;
            left: 0;
        }
        .am-menu-nav {
            width: 33.33333333%;
        }

    </style>
</head>
<body>
	<script type="text/x-handlebars-template" id="amz-tpl">
<div data-am-sticky="" class="" style="margin: 0px;">
    
<%--
			<c:if test="${type == '1' }" >
          		<a onclick="GoBack()" class="back">
          	</c:if>
          	
          	<c:if test="${type == '2' }" >
          		<a id="buttons" class="back">
          	</c:if>
 --%>         	
          	<c:if test="${type != '1' && type != '2'}" >
          		 <header class="header">
              <a href="<%=request.getContextPath() %>/portal/portal_cardlist.html" class="back">
   返回
              </a>
    <div class="header_title">卡片挂失</div>
    
      </header>
          	</c:if>
    
                 
</div>		
<input type="hidden" value="${type}" id="type" name="type"/>
<div class="am-list-news-default" >
        <ul class="am-list am-list-static">
            <li>工号：${card.classRyh}</li>
            <li>姓名：${card.classXm}</li>
            <li>卡号：${card.classKh}</li>
        </ul>
    </div>
    <table border="0" width="100%">
        <tr><td align="center">
            <button type="button" class="am-btn am-btn-warning" style="width:200px;">我要挂失</button>
        </td></tr>
    </table>
<c:if test="${ type != '1' && type != '2'}" >
    {{>navbar navbar}}
</c:if>
--</script>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script>
/**
网页中的JS调用IOS客户端函数，需要在网页的JS代码中添加如下函数：
*/
function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge)
			}, false)
		}
	};
	$(function() {
		var type = '${type}';
		if(type == '2'){
		   			connectWebViewJavascriptBridge(function(bridge) {
					var callbackButton = document.getElementById('buttons');
					callbackButton.onclick = function(e) {
						e.preventDefault();
						bridge.callHandler('testObjcCallback', function(response) {
			                               
					});
				}
			});
	   		
	   	}
		
    });
    /**
	网页中的JS调用android客户端函数，只需在网页的JS代码中直接调用客户端提供的调用函数即可，网页例子如下所示
	*/
	function ShowAlert()
	{
	 MobileJavaApi.ShowAlertQuit("弹出消息提示");
	};
	function Quit()
	{
		MobileJavaApi.Quit();
	};
	
	function GoBack()
	{
		MobileJavaApi.GoBack();
	};
    //注册一个比较大小的Helper,判断v1是否大于v2
    Handlebars.registerHelper('compare',function(v1,v2,options){
        if (v1 > v2) {
            return options.fn(this);
        }  else {
            return options.inverse(this);
        }
    });

    //注册一个比较的Helper,判断v1是否等于v2
    Handlebars.registerHelper('eq',function(v1,v2,options){
        if (v1 == v2) {
            return options.fn(this);
        }  else {
            return options.inverse(this);
        }
    });

    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    data.header.content.title="卡片挂失";
    data.header.content.left=[
                    {
                        "link" : "<%=request.getContextPath() %>/portal/portal_cardlist.html",
                        "icon" : "chevron-left"
                    }
                ];
    var html = template(data);
    $tpl.before(html);
</script>	
</body>
</html>