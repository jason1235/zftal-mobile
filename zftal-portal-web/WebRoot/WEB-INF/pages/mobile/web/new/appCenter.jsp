<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<%@ include file="/commons/hrm/head_v5.ini" %>
<title></title>
    <style type="text/css">
       .am-navbar-default .am-navbar-nav {
            background-color: #FAFAFA;
            border-top: 0.1rem solid #e7e7e7;

        }

        /*底部TabBar图标字体颜色*/
        .am-navbar-default a {
            color:#919191;
        }

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
        }
        .am-panel-bd {
            background-color: #D4E5FA;
        }

        .am-avg-sm-2 li {
            height: 10rem;
        }
        .am-gallery-title {
            text-align: center;
        }


        .am-gallery-item {
            text-align: center;
        }

        .am-gallery .am-gallery-item img{
            width: 2rem;
            height:2rem;
        }
        .heightstyle img {
        	width:100%;
        	height:200px;
        }
        .am-table td {
            cursor: pointer;
            text-align: center;
        }

        .am-table td:hover {
            background-color: #eeeeee;
            border-bottom: 1px solid deepskyblue;
        }
        
        
        ::-webkit-scrollbar {
        	display:none;
        }
        
    </style>
</head>
<body style="background: #FFFFFF;">
	<div data-am-sticky style="height:45px;width:100%;background:#00A0E9;text-align:center; line-height:45px;font-size: 18px;color:#FFFFFF;font-family: inherit;">
		应用中心
	</div>
	<div id="gallery" style="background: #FFFFFF;margin-bottom: 60px;">
    
	</div>
    <div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default  testNavClass am-no-layout" id="" style="background: #FFFFFF;">
          <ul class="am-navbar-nav am-cf am-avg-sm-3" style="background-color: #FFFFFF;">
              <li>
                <a href="/zftal-mobile/mobile/web_index.html" class="" >
                      <span class="am-icon-home"></span>
                    <span class="am-navbar-label">首页</span>
                </a>
              </li>
              <li>
                <a href="/zftal-mobile/mobile/web_appCenter.html" class="" style="color: rgb(17, 87, 170);">
                      <span class="am-icon-th-large"></span>
                    <span class="am-navbar-label">应用中心</span>
                </a>
              </li>
              <li>
                <a href="/zftal-mobile/mobile/web_portal.html" class="" >
                      <span class="am-icon-user"></span>
                    <span class="am-navbar-label">我的门户</span>
                </a>
              </li>
          </ul>
      </div>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script type="text/javascript"> 
    var businessList = ${business};
	var apps = ${apps};
	var showHtml = "";
	var tempHtml = "";
    for (var i = 0, len1 = businessList.length; i < len1; i++) {
		var ishas = false;
    	tempHtml = "<div style='padding:5px;'><div class='am-list-news-hd am-cf' " +
    				"style='margin-left:5px;margin-top:15px;margin-bottpm:10px;border-bottom:10px;padding:0px;border-top:3px;color:#1157AA;'>"+
    				"<h2>▎" + businessList[i].classXtmc + "</h2></div></div>" +
    				"<ul style='padding:0;' data-am-widget='gallery' " + 
    				"class='am-gallery am-avg-sm-4  am-avg-md-4 am-avg-lg-4 am-gallery-default am-no-layout'>"
    	var count = 0;
		for (var j = 0,len2 = apps.length; j < len2; j++) {
			if(apps[j].type != "WEB_SERVICE") continue;
	    	if(businessList[i].procode == apps[j].procode){
	    		count++;
    			ishas = true;
	    		tempHtml += "<li style='border: 1px solid #eeeeee;height:75px;line-height:30px;'><div class='am-gallery-item'>";
	    			if(apps[j].otherFlag == '0'){
		    			if(apps[j].procode == '002' || apps[j].procode == '009'){
		    				tempHtml +=	"<a href=\"javascript:doJWClick('" + apps[j].url + "');\"> ";
		    			}else{
		    				tempHtml +=	"<a href=\"javascript:doClick('" + apps[j].url + "');\"> ";
		    				
		    			}
	    			}else{
	    				tempHtml +=	"<a href=\"" + apps[j].url + "\"> ";
	    			}
	    			tempHtml += "<img src='" + apps[j].icon +"' alt='" + apps[j].name + "'>" +
	    					"<h3 class='am-gallery-title'>" + apps[j].name + "</h3></a></div></li>";
	    	}
    		
    	}
		count = count%4;
		if(count != 0){
	    	for(;count<4;count++){
	    		tempHtml += "<li style=\"border: 1px solid #eeeeee;width:25%;height:75px;\"></li>";
	        }
		}
    	tempHtml += "</ul>";
    	if(ishas) showHtml+= tempHtml;
    }
    $("#gallery").append(showHtml);
    
    function doClick(url) {
        var now = new Date();
        var nowStr = now.getFullYear() + "-"  + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
        location.href = url + "&time=" + nowStr;
    }
    
    function doJWClick(url) {
    	$.post(_path + "/mobile/web_getjwURL.html",{url : url},function(jwurl){
	        location.href = jwurl;
		});
    }
    
    //$(document).on('touchstart',function(e){
    //	e.stopPropagation();
    //	$('#srvDropdown').dropdown('close');
    //});
    
</script>	
</body>
</html>



