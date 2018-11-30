<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/commons/hrm/head_v5.ini" %>
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/global.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/application.js"></script>
<script type="text/javascript"> 
/**
网页中的JS调用IOS客户端函数，需要在网页的JS代码中添加如下函数：
*/
function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge);
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge);
			}, false);
		}
	};
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
</script>
<style type="text/css">
.center {
    text-align: center;
    border: 1px dotted black;
    width: 50%;
}
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
        
        .am-list-news-default {
		    margin-left: 0px;
		    margin-right: 0px;
		}

		.am-list li>a {
			padding:5px;
		}
</style>
</head>
<body style="background: #FAFAFA;height: auto;">


	
<div data-am-widget="list_news" class="am-list-news am-list-news-default" >
	<div class="am-list-news-bd list">
	<c:if test="${empty pageList }">
		<div class="am-panel am-panel-default" id="itemslistmessage">
		   	<div class="am-panel-bd">您还没有提交过意见哟</div>
		</div>
	</c:if>
	<c:if test="${not empty pageList }">
	<div id="hotQuest" class="title1 text_a_c f_size14" style="margin-left: 10px;" >&nbsp;&nbsp;您提交的意见反馈列表</div>
	  <ul class="" id="alllist" style="border-top: 0px solid;border-bottom: 0px solid;">
		  <c:forEach items="${pageList }" var="item">
					<li style="margin: 0px;    border-bottom: 0px solid;">
						<a href="<%=request.getContextPath() %>/suggest/suggest_getDetail.html?query.id=${item.id }">
							<span style="display:inline;">
								<p style="margin-left: 5px;border: 0px;padding: 0px;color:black;">
									<font size="5px" style="font-size:15px;font-family: !important; font-weight:normal;display:block;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;width: 75%;float: left;">
										${item.textContent }
									</font>
									<c:if test="${not empty item.replyContent }">
										<span style="float:right;color:green;width: 20%;float: left;">已回复</span>
									</c:if>
							  		<c:if test="${empty item.replyContent }">
										<span style="float:right;color:red;width: 20%;float: left;">未回复</span>
									</c:if>
									<span class="am-icon-chevron-right" style="float:right;color:#CDC9C9;width: 5%;float: left;"></span>
								</p>
							</span>
						</a>
					</li>
			</c:forEach>
	  </ul>
	 </div>
	 </c:if>
</div>

<script type="text/javascript">
$(function(){
	if($("itemslist").has("li").length > 0){
		$("#itemslistmessage").hide();
	}else{
		$("#itemslistmessage").show();
	}
	
	if($("alllist").has("li").length > 0){
		$("#alllistmessage").hide();
	}else{
		$("#alllistmessage").show();
	}
})
</script>

</body>
</html>