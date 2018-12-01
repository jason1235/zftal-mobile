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
<body style="background: white;">
	<script type="text/x-handlebars-template" id="amz-tpl">
	<div data-am-sticky="" class="" style="margin: 0px;">
<%--
			<c:if test="${type == '1' }" >
          		<a onclick="Quit()" class="back">
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
    <div class="header_title">一卡通</div>
    
      </header>
          	</c:if>

                  
	</div>  
<c:if test="${type != '1' && type != '2'}" > 
<br><br>
 </c:if>
  <c:if test="${type == '1' || type == '2'}" >

</c:if>
<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd">
      暂时没有开通此功能！
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn">确定</span>
    </div>
  </div>
</div>
<input type="hidden" value="${type}" id="type" name="type"/>
			<div class="am-list-news-default" id="error">
        		<ul class="am-list am-list-static">
            		<li>您还没有一卡通账户</li>
        		</ul>
    		</div>

		<div class="am-panel" id="right"></br>
        <div class="am-panel-hd am-text-sm">余额（元）：</div>
        <div class="am-panel-bd am-text-xxxl">
            <fmt:formatNumber value="${yue}" pattern="0.00"/>
        </div>
    	</div>
 		<div data-am-widget="list_news" class="am-list-news am-list-news-default" >
		<div class="am-list-news-bd">
    	<ul class="am-list" id="liList">
        	<li><a onclick="cardCharge()"><img width="30" height="30" src="<%=request.getContextPath() %>/assets/i/portal/cz.png"/>一卡通充值<span class="am-icon-chevron-right accessory"></span></a></li>
        	<li><a onclick="businessFrom()"><img width="30" height="30" src="<%=request.getContextPath() %>/assets/i/portal/ls.png"/>流水及账单<span class="am-icon-chevron-right accessory"></span></a></li>
        	<li><a onclick="getLoss()"><img width="30" height="30" src="<%=request.getContextPath() %>/assets/i/portal/gs.png"/>卡片挂失<span class="am-icon-chevron-right accessory"></span></a></li>
		</ul>
		</div>
		</div>
<c:if test="${type != '1' && type != '2'}" >
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
	$(function(){
		$("#message").hide();
		var type = '${type}';
		if(type == '2'){
		   			connectWebViewJavascriptBridge(function(bridge) {
					var callbackButton = document.getElementById('buttons');
					callbackButton.onclick = function(e) {
						e.preventDefault();
						//bridge.callHandler('testObjcCallback', function(response) {
			            bridge.callHandler('ObjCQuitCallback', function(response) {                   
					});
				}
			});
	   		
	   	}
		var card = '${card}';
		if(card == ''){
			$("#right").hide();
			$("#liList").hide();
			$("#error").show();
		}else{
			$("#right").show();
			$("#liList").show();
			$("#error").hide();
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
    function cardCharge(){
    	var $modal = $('#my-alert');
		$modal.modal();
    	//var  type   = $("#type").val();
    	//window.location.href = "<%=request.getContextPath()%>/portal/portal_cardCharge.html?param=card"+"&type="+type;
    }
    function businessFrom(){
    	var  type   = $("#type").val();
    	window.location.href = "<%=request.getContextPath()%>/portal/portal_businessFrom.html"+"?type="+type;
    }
    function getLoss(){
    	var $modal = $('#my-alert');
		$modal.modal();
    	//var  type   = $("#type").val();
    	//window.location.href = "<%=request.getContextPath()%>/portal/portal_getLoss.html"+"?type="+type;
    }
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
    data.header.content.title="一卡通";
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