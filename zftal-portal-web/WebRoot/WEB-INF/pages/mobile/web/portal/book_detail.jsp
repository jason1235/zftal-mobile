<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<body>
	<script type="text/x-handlebars-template" id="amz-tpl">
	<div data-am-sticky="" class="" style="margin: 0px;">
 <%--
    <header class="header">

			<c:if test="${type == '1' }" >
          		<a onclick="GoBack()" class="back">
          	</c:if>
          	
          	<c:if test="${type == '2' }" >
          		<a id="buttons" class="back">
          	</c:if>
      --%>      	
          	<c:if test="${type != '1' && type != '2'}" >
          		<header class="header">
              <a href="<%=request.getContextPath() %>/portal/portal_booklist.html" class="back">
  返回
              </a>
    <div class="header_title">图书馆</div>
    
      </header>
          	</c:if>
    
                  
	</div>
<c:if test="${type != '1' && type != '2'}" > 
<br><br>
 </c:if>
  <c:if test="${type == '1' || type == '2'}" >

</c:if>
<input type="hidden" value="${type}" id="type" name="type"/>
<div data-am-widget="tabs"  class="am-tabs am-tabs-d2" >
    <div class="am-list-news-bd">
      <ul class="am-list">
    
          <li class="am-g am-list-item-desced">
			                   
              <div class="am-list-item-hd">条码</div>
    
    
              <div class="am-list-item-text">${bookDetail.classTxm}</div>
    
          </li>
          <li class="am-g am-list-item-desced">
			  <div class="am-list-item-hd">书名</div>
    
    
              <div class="am-list-item-text">${bookDetail.classZtm}</div>
    
          </li>
          <li class="am-g am-list-item-desced">
			  <div class="am-list-item-hd">作者</div>
    
    
              <div class="am-list-item-text">${bookDetail.classZz}</div>
    
          </li>
          <li class="am-g am-list-item-desced">
			  <div class="am-list-item-hd">借阅日期</div>
    
    
              <div class="am-list-item-text">${bookDetail.classJcsj}</div>
    
          </li>
          <li class="am-g am-list-item-desced">
			  <div class="am-list-item-hd">应还日期</div>
    
    
              <div class="am-list-item-text">${bookDetail.classXhsj}</div>
    
          </li>
          <li class="am-g am-list-item-desced">
			  <div class="am-list-item-hd">续借次数</div>
    
    
              <div class="am-list-item-text">${bookDetail.classXjcs}</div>
    
          </li>
          <li class="am-g am-list-item-desced">
			  <div class="am-list-item-hd">馆藏地</div>
    
    
              <div class="am-list-item-text">${bookDetail.classGcd}</div>
    
          </li>
          <li class="am-g am-list-item-desced">
			  <div class="am-list-item-hd">附件</div>
    
    
              <div class="am-list-item-text">无</div>
    
          </li>
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
    	//$(".am-list-item-hd").click(function(){
    	//	var Dzzh = $(this).closest("li").find("input[name='dzzh']").val();
    	//	var jcsj = (this).closest("li").find("input[name='jcsj']").val();
    	//	var txm = (this).closest("li").find("input[name='txm']").val();
    	//	alert(Dzzh + jcsj + txm);
    	//})
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
    data.header.content.title="图书馆";
    data.header.content.left=[
                    {
                        "link" : "<%=request.getContextPath() %>/portal/portal_booklist.html",
                        "icon" : "chevron-left"
                    }
                ];
    var html = template(data);
    $tpl.before(html);
    
</script>	
</body>
</html>