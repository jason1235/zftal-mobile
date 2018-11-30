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
<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/global.css">
<style>   
  .ellipsis_div{   
    overflow:hidden;   
    text-overflow:ellipsis;   
    white-space:nowrap;   
     
  }    
  </style>
<script type="text/javascript"> 
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
	var type = ${type};
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

function clientBaseData(){
		var classId = $("#xxlid").val();
		var type   = $("#type").val();
		window.location.href = "<%=request.getContextPath() %>/basedata/basedata_clientBaseData.html?classId="+classId+"&type="+type;
};

function clientBaseDetailData(globalid){
		var classId = $("#xxlid").val();
		var type   = $("#type").val();
		$.post("<%=request.getContextPath() %>/basedata/basedata_clientBaseDetailCheck.html",
		"globalid="+globalid+"&classId="+classId,function(data){
                if(data.success){
					window.location.href = "<%=request.getContextPath() %>/basedata/basedata_clientBaseDetailData.html?globalid="+globalid+"&classId="+classId+"&type="+type;
                }else{
                    //alert(data.text);
	                    var $modal = $('#your-modal');
					        $modal.modal();
					      
					   
                }
            },"json");
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
<title></title>
</head>
<body style="background: white;height: auto;">

<div data-am-sticky="" class="" style="margin: 0px;">
<input type="hidden" value="${classId}" id="xxlid" name="classId"/>
		<input type="hidden" value="${type}" id="type" name="type"/>
		
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
		              <a onclick="clientBaseData()" class="back">
		                  	返回
	              </a>
	          		<div class="header_title">
    		<c:choose>
          		<c:when test="${title != '' && title != null }">
          			${title }
          		</c:when>
          		<c:otherwise>
          			${clazz.name }详情
          		</c:otherwise>
          	</c:choose>
		</div>
    
      </header>
	          	</c:if>
	    
	                 
          
          
    
    	
</div>  
<c:if test="${type != '1' && type != '2'}" > 
<br><br>
 </c:if>
  <c:if test="${type == '1' || type == '2'}" >

</c:if>
<c:choose>
	<c:when test="${clazz.identityName eq 'M_DNTZ' || clazz.identityName eq 'M_XXGW' || clazz.identityName eq 'M_YQNC' }">
	
		
	
		<c:forEach items="${firstMenuList}" var="menu" varStatus="st">
			<c:forEach  var="data" items="${menu.sxData }" >
				<c:forEach var="map" items="${data}">
					<article class="am-article">
				       
				        <div class="am-article-bd">
				            <p>${map.value}</p>
				        </div>
				    </article>
				</c:forEach>
			</c:forEach>
		</c:forEach>
		
	</c:when>
	<c:otherwise>
	
	
    <div data-am-widget="tabs"  class="am-tabs am-tabs-d2" id="firstDiv" >
    
    	 <ul class="am-tabs-nav am-cf">
    	 	<c:forEach items="${firstMenuList}" var="menu" varStatus="st"> 
    	 
    	 		<li class=""><a href="[data-tab-panel-${st }]">${menu.menuName }</a></li>
    	 	</c:forEach>
        </ul>
    
    <div class="am-tabs-bd">
    
				
				<c:forEach items="${firstMenuList }" var="menu" varStatus="st">
            <div data-tab-panel-${st} class="am-tab-panel " style="padding: 0px">
                <div class="am-list-news-bd">
                    <ul class="am-list">
                    	<c:forEach  var="data" items="${menu.sxData }" >
                    		<li class="am-g am-list-item-date">
                    			<c:forEach var="map" items="${data}">
                    			
                    			<span style="display:inline; ">
							
										<p style="border: 0px;padding: 0px;">
										<font size="4px" style="padding-top: 5px;padding-bottom: 5px;margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${map.key }</font>
										<font size="2px" style="padding-top: 5px;padding-bottom: 5px;font-weight: normal;float: right;margin-top: 2px;">${map.value }</font>
										</p>
								</span>
                    			
                    			
	                            </c:forEach> 
                        	</li>
                    	</c:forEach>
                    	
                    	<c:forEach items="${secondMenuList }" var="secondMenu">
                    		<c:if test="${secondMenu.firstMenuId eq menu.classCdid }"> 
                    		
                    			<li class="am-panel" style="background-color:#0e90d2;">
							    <a data-am-collapse="{parent: '#collapase-nav-1', target: '#${secondMenu.menuName }'}" style="color: white">
							         ${secondMenu.menuName } 
							    </a>
							    
							    <c:forEach items="${secondMenu.sxData }" var="data" >
								    <ul class="am-list am-collapse am-in	 admin-sidebar-sub" id="${secondMenu.menuName }">
		                    		<li class="am-g am-list-item-date ">
		                    		<c:forEach var="map" items="${data}">
		                    		
		                    		
		                    		<span style="display:inline; ">
							
											<p style="border: 0px;padding: 0px;">
											<font size="4px" style="padding-top: 5px;padding-bottom: 5px;margin-left: 0px;font-size:16px;font-family: !important; font-weight:normal;margin-top: 5px;">${map.key }</font>
											<font size="2px" style="padding-top: 5px;padding-bottom: 5px;font-weight: normal;float: right;margin-top: 2px;">${map.value }</font>
											</p>
									</span>
					                        
				                      </c:forEach>
		                        	</li>
								    </ul>
		                    	</c:forEach>
							  </li>
                    		
							</c:if> 
                    	</c:forEach>
                    	
                    	
                    </ul>
                </div>
            </div>
    		
    	</c:forEach>
				
        </div>
    </div>  

</c:otherwise>
</c:choose>    
<c:if test="${clazz.identityName eq 'M_JSGZ' }" >
<c:if test="${ type != '1' && type != '2'}" >
<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default  testNavClass am-no-layout" id="">
          <ul class="am-navbar-nav am-cf am-avg-sm-3">
              <li>
                <a href="" class="">
                      <span class="am-icon-home"></span>
                    <span class="am-navbar-label">首页</span>
                </a>
              </li>
              <li>
                <a href="" class="">
                      <span class="am-icon-th-large"></span>
                    <span class="am-navbar-label">应用中心</span>
                </a>
              </li>
              <li>
                <a href="<%=request.getContextPath() %>/portal/portal_portal.html" class="">
                      <span class="am-icon-user"></span>
                    <span class="am-navbar-label">我的门户</span>
                </a>
              </li>
          </ul>
      </div>
</c:if>
</c:if>

</body>
</html>