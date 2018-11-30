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
<div data-am-sticky>
    {{>header header}}
</div>		
<ul class="am-list">
        <li><a onclick="cardCharge()"><img width="30" height="30" src="<%=request.getContextPath()%>/assets/i/portal/cz.png"/>一卡通充值<span class="am-icon-chevron-right accessory"></span></a></li>
        <li><a onclick="electricCharge()"><img width="30" height="30" src="<%=request.getContextPath()%>/assets/i/df.png"/>电费充值<span class="am-icon-chevron-right accessory"></span></a></li>
        <li><a ><img width="30" height="30" src="<%=request.getContextPath()%>/assets/i/wf.png"/>网费充值<span class="am-icon-chevron-right accessory"></span></a></li>
        <!--<li><a href="consumption.html"><img width="30" height="30" src="<%=request.getContextPath()%>/assets/i/portal/zd.png"/>我的账单<span class="am-icon-chevron-right accessory"></span></a></li>-->
    </ul>
    {{>navbar navbar}}
</script>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script>
	$(function(){
			
    });
    function cardCharge(){
    	window.location.href = "<%=request.getContextPath()%>/portal/portal_cardCharge.html?param=paymyself";
    }
    function electricCharge(){
    	window.location.href = "<%=request.getContextPath()%>/portal/portal_pay.html";
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
    data.header.content.title="自助缴费";
    data.header.content.left= [
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