<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html style="background: #FFFFFF;">
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/global.css">
<title></title>
<style type="text/css">
        /*底部TabBar样式*/
        .am-navbar-default .am-navbar-nav {
            background-color: #FAFAFA;
            border-top: 0.1rem solid #e7e7e7;

        }

        /*底部TabBar图标字体颜色*/
        .am-navbar-default a {
            color:#919191;
        }

        /* 头部背景颜色 */
        .am-header-default {
            
        }
        .am-panel {
            margin-bottom: 0;
            background-color: #D4E5FA;
        }
        .am-panel-bd {
            background-color: #D4E5FA;
            text-align: center;
        }

        .accessory {
            float:right;
            color:#cccccc;
            margin-right:1.5rem;
        }
        .am-list {
            margin-top: 0;
        }
    </style>
</head>
<body style="background: #FFFFFF;">
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
              <a href="<%=request.getContextPath() %>/portal/portal_portal.html" class="back">

    
                    返回
              </a>
    <div class="header_title">我的科研</div>
    
      </header>
          	</c:if>

    </div> 

<c:if test="${type != '1' && type != '2'}" > 
<br><br>
 </c:if>
  <c:if test="${type == '1' || type == '2'}" >

</c:if>
   
<input type="hidden" value="${type}" id="type" name="type"/>
 <div data-am-widget="list_news" class="am-list-news am-list-news-default" >
<div class="am-list-news-bd">
  <ul class="am-list">
	<c:forEach var="s" items="${list}" varStatus="st">
	<li style="width:60;height:60;" class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left" onclick="document.location='${s.appUrl}';">
		<div onclick="document.location='${s.appUrl}';" style="padding:0px;margin:0px;border:0px;">
        <div class="am-u-sm-2 am-list-thumb">
			
          
            <img src="${s.classFwtbdz}" alt="${s.classFwmc}"  width="40" height="40"/>
          
        </div>

        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd">${s.classFwmc}</h3>

            <div class="am-list-item-text">${s.classFwms}</div>

        </div>
		<div class=" am-u-sm-2">
		<span class="am-icon-chevron-right accessory"></span>
		</div>
		</div>
      </li>
</c:forEach>
</ul>
</div>
</div>
<c:if test="${ type != '1' && type != '2'}" >
    {{>navbar navbar}}
</c:if>
	</script>

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
	$(function(){
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
	   	
	   	$('.am-navbar-nav li:eq(2) a').css('color','#1157AA');
		
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
   

    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    data.header.content.title="我的科研";
    data.header.content.left=[
                    {
                        "link" : "<%=request.getContextPath() %>/portal/portal_portal.html",
                        "icon" : "chevron-left"
                    }
                ];
    var html = template(data);
    $tpl.before(html);
    
</script>	
</body>
</html>