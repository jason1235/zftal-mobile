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
$(function(){
	
});

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
<c:if test="${nullFlag eq true }">
	<div class="am-modal am-modal-no-btn" >
	  <div class="am-modal-dialog">
	    <div class="am-modal-hd">提示
	      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
	    </div>
	    <div class="am-modal-bd">
	     	 没有可以显示的数据!
	    </div>
	  </div>
	</div>
</c:if>

<br>	
<div data-am-widget="list_news" class="am-list-news am-list-news-default" >
	
	<div class="am-list-news-bd list">
	  <ul class="" id="itemslist" style="border-top: 0px solid;border-bottom: 0px solid;">
			<s:iterator value="assetList" var="menu" status="st">
               <li style="margin: 0px;    border-bottom: 0px solid;">
               		<a onclick="clientHelpGView('4D72D55EDF143659E0538513470A2200')">
               				<span style="display:inline;">
               					<p style="margin-left: 5px;border: 0px;padding: 0px;color:black;">
               						<font size="5px" style="font-size:15px;font-family: !important; font-weight:normal;">
               							${menu.title }
              						</font>
              						<span class="" style="float:right;color:#CDC9C9;">
              							${menu.time }
              						</span>
            					</p>
            				</span>
            		</a>
            	</li>
            </s:iterator>
	  </ul>
	 </div>
</div>

</body>
</html>