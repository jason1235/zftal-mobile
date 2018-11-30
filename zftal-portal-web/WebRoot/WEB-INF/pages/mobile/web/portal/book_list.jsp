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
<body style="background: white;height: auto;">
	<script type="text/x-handlebars-template" id="amz-tpl">
	<div data-am-sticky="" class="" style="margin: 0px;">

 <%--
      <header class="header">
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
	<div style="border-bottom:1px solid #dedede;">
        <table border="0" width="100%">
            <tr valign="center">
                <td align="right" style="font-size:1.6rem;">已借图书</td>
                <td width="15px"></td>
                <td align="left" style="font-size:2.6rem;font-weight:bold;">${booker.classLjcc}</td>
                <td align="right" style="font-size:1.6rem;">还可借图书</td>
                <td width="15px"></td>
                <td align="left" style="font-size:2.6rem;font-weight:bold;">${booker.classYjcs - booker.classLjcc}</td>
            </tr>
        </table>
    </div>
    <div data-am-widget="tabs"
         class="am-tabs am-tabs-default"
            >
        <ul class="am-tabs-nav am-cf">
            <li <c:if test="${findli eq 'all' }"> class="am-active" </c:if> id="all"><a href="#">全部</a></li>
            <li <c:if test="${findli eq 'warn' }"> class="am-active" </c:if> id="warn" ><a href="#">即将到期</a></li>
            <li <c:if test="${findli eq 'late' }"> class="am-active" </c:if> id="late" ><a href="#">已超期</a></li>
        </ul>
        <div class="am-tabs-bd">
            <div data-tab-panel-0 class="am-tab-panel am-active" id="allbook" >
                <div class="am-list-news am-list-news-default">
                    <ul class="am-list">
                    
                    	<c:forEach items="${allBooks }" var="book">
	                        <li class="am-g am-list-item-desced">
	                            <div style="float:left;">
									<a onclick="getDetail('${book.classDzzh}','${book.classTxm}','${book.classJcsj}')"  class="am-list-item-hd ">${book.classZtm}</a>
	                                <span class="am-list-item-text">${book.classJcsj}~${book.classXhsj}</span>
	                            </div>
	                            <div style="float:right; " align="right">
	                            	<c:if test="${book.classIsLate eq 0 }">
	                                	<div style="background-color:red;font-size:1.3rem;color:#ffffff; width:60px;"  align="center">已超期</div>
	                            	</c:if>
	                            	<c:if test="${book.classIsLate eq 1 }">
	                                	<div style="background-color:#ff9900;font-size:1.3rem;color:#ffffff; width:60px;"  align="center">即将到期</div>
	                            	</c:if>
	                            	<c:if test="${book.classIsLate eq 2 }">
	                                	<div style="background-color:#008B00;font-size:1.3rem;color:#ffffff; width:60px;"  align="center">未还</div>
	                            	</c:if>
	                            	<c:if test="${book.classIsLate eq 3 }">
	                                	<div style="background-color:#424242;font-size:1.3rem;color:#ffffff; width:60px;"  align="center">已还</div>
	                            	</c:if>
	                            </div>
	                        </li>
                    	</c:forEach>
                    
                    </ul>
                </div>
            </div>
            
            
            
            
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
    	//$(".am-list-item-hd").click(function(){
    	//	var Dzzh = $(this).closest("li").find("input[name='dzzh']").val();
    	//	var jcsj = (this).closest("li").find("input[name='jcsj']").val();
    	//	var txm = (this).closest("li").find("input[name='txm']").val();
    	//	alert(Dzzh + jcsj + txm);
    	//})
    	//var findli = '${findli}';
    	//getLi(findli);
    	$(".am-tabs-nav li").click(function(){
    		var  param = $(this).attr("id"); 
    		var  type   = $("#type").val();
    		window.location.href = "<%=request.getContextPath()%>/portal/portal_booklist.html?findli="+param+"&type="+type;
    	});
    	//$("#warn").click(function(){
    	//	window.location.href = "<%=request.getContextPath()%>/portal/portal_booklist.html?findli=warn";
    	//});
    	//$("#late").click(function(){
    	//	window.location.href = "<%=request.getContextPath()%>/portal/portal_booklist.html?findli=late";
    	//});
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
    });
    
    //function getLi(getli){
    //	$(".getbook").removeClass("am-active");
    //	$(".am-tab-panel").removeClass("am-active");
    //	if(getli == 'all' || getli == ''){
    //		$("#all").addClass("am-active");
    //		$("#allbook").addClass("am-active");
    //	}
    //	if(getli == 'warn'){
    //		$("#warn").addClass("am-active");
    //		$("#warnbook").addClass("am-active");
    //	}
    //	if(getli == 'late'){
    //		$("#late").addClass("am-active");
    //		$("#latebook").addClass("am-active");
    //	}
    //}
    function getDetail(dzzh,txm,jcsj){
    	var  type   = $("#type").val();
    	window.location.href = "<%=request.getContextPath()%>/portal/portal_getBookDetail.html?dzzh="+dzzh+"&txm="+txm+"&jcsj="+jcsj+"&type="+type;
    }
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
    //data.header.content.title="图书馆";
    //data.header.content.left=[
    //                {
    //                    "link" : "<%=request.getContextPath() %>/portal/portal_portal.html",
    //                    "icon" : "chevron-left"
    //                }
    //            ];
    var html = template(data);
    $tpl.before(html);
    
</script>	
</body>
</html>