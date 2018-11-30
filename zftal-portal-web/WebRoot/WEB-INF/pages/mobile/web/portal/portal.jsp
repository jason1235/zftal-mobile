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
<title></title>
        <style type="text/css">
        .am-navbar-default .am-navbar-nav {
            background-color: #FAFAFA;
            border-top: 0.1rem solid #e7e7e7;

        }

       

        /* 头部背景颜色 */
        .am-header-default {
            
        }
        .am-panel {
            margin-bottom: 0;
        }
        .am-panel-bd {
            background-color: #D4E5FA;
        }

        .am-avg-sm-2 li {
            height: 10rem;
        }
        .box {
            padding: 2rem;
            background-position: bottom right;
            border: 1px solid #eeeeee;
            cursor: pointer;
        }
        .box-1 {
              background-image: url("<%=request.getContextPath()%>/assets/i/portal/zzjf.png");
              background-repeat: no-repeat;
          }
        .box-2 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/kb.png");
            background-repeat: no-repeat;
        }
        .box-3 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/tsg.png");
            background-repeat: no-repeat;
        }
        .box-4 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/ykt.png");
            background-repeat: no-repeat;
        }
        .box-5 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/ky.png");
            background-repeat: no-repeat;
        }
        .box-6 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/gz.png");
            background-repeat: no-repeat;
        }
    </style>
</head>
<body>
<div data-am-sticky style="height:45px;width:100%;background:#00A0E9;text-align:center; line-height:45px;font-size: 18px;color:#FFFFFF;font-family: inherit;">
		我的门户
	</div>
<section class="am-panel am-panel-default">
    <main class="am-panel-bd">
        <div class="am-g">
            <div class="am-u-sm-4" style="text-align:right">
                <img class="am-circle" src="<%=request.getContextPath()%>/assets/i/avatar.jpg" width="60" height="60">
            </div>
            <div class="am-u-sm-8"> 
                <div><span class="am-text-xl">周嘉伟</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="am-text-sm"></span></div>
                <div><span class="am-text-sm">2015-2016</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="am-text-sm">第十四教学周</span></div>
            </div>
        </div>

    </main>
</section>

<div id="gallery">
    <ul id="portalUL" data-am-widget="gallery" class="am-gallery am-avg-sm-2" data-am-gallery="{  }" style="margin: 0px;border: 0px;padding: 0px;">
    </ul>
</div>
   <div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default  testNavClass am-no-layout" id="" >
          <ul class="am-navbar-nav am-cf am-avg-sm-3" style="background-color: #FFFFFF;">
              <li>
                <a href="/zftal-mobile/mobile/web_index.html" class="" >
                      <span class="am-icon-home"></span>
                    <span class="am-navbar-label">首页</span>
                </a>
              </li>
              <li>
                <a href="/zftal-mobile/mobile/web_appCenter.html" class="">
                      <span class="am-icon-th-large"></span>
                    <span class="am-navbar-label">应用中心</span>
                </a>
              </li>
              <li>
                <a href="/zftal-mobile/mobile/web_portal.html" class="" style="color: #00A0E9;">
                      <span class="am-icon-user"></span>
                    <span class="am-navbar-label">我的门户</span>
                </a>
              </li>
          </ul>
      </div>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script>
	$(function(){
			
    });
	
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
    data.header.content.title="我的门户";
    var html = template(data);
    $tpl.before(html);
	$('.am-navbar-nav li:eq(2) a').css('color','#1157AA');
	var portalInfo = ${portalInfo};
	var portalHtml = "";
	for(var i = 0; i < portalInfo.length; i++){
		if(portalInfo[i].type != "WEB_SERVICE") continue;
		portalHtml += "<li style=\"margin: 0px;border: 5px;padding: 5px;height: auto;border: 1px solid #eeeeee;width:50%;height:70px;\">"+
						"<a  href=\""+portalInfo[i].url+"\">"+
        				"<div class=\"am-g\" style=\"margin: 5px;border: 0px;padding: 0px\">"+
        				"<div class=\"am-u-sm-8\">"+
            			"<div><span class=\"am-text-sm\">"+portalInfo[i].name+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"am-text-sm\"></span></div>"+
        				"</div><div class=\"am-u-sm-4\" style=\"text-align:right\">"+
            			"<img src=\""+portalInfo[i].icon+"\" width=\"40\" height=\"40\" style=\"padding: 5px;border: 5px;margin-right: 10px;\"></div> </div></a></li>";

	}
	$("#portalUL").append(portalHtml);
</script>	
</body>
</html>