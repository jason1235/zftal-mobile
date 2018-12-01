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
var toPage = 1;

function liFun(id,title){
	var lihtml = "<li style='margin: 0px;    border-bottom: 0px solid;'>" +
				 "<a onclick=\"clientHelpGView('" + id+ "')\">" +
				 "<span style='display:inline;'>" +
				 "<p style='margin-left: 5px;border: 0px;padding: 0px;color:black;'>"+
				 "<font size='5px' style='font-size:15px;font-family: !important; font-weight:normal;'>"+
				 title+
				 "</font>"+
				 "<span class='am-icon-chevron-right' style='float:right;color:#CDC9C9;'></span>"+
				 "</p></span></a></li>";
	return lihtml;
}
	
$(function(){
	$("#bt").val("");
	
    var contentHtml = "";
    var allConetentHtml = "";
	var viewList=${viewList};
	var hotList=${hotList};
	
	if(viewList.length==0){
	  $("#message").show();
	}else{
	  $("#message").hide();
	  if(hotList&&hotList.length>0){
	     $("#hotQuest").show();
	     for (var i = 0, len =hotList.length; i < len; i++) {
	       contentHtml+=liFun(hotList[i].id,hotList[i].title);
	     }
	     contentHtml+="<br/>";
	  }
	  for (var k = 0, len =viewList.length; k < len; k++) {
		  allConetentHtml+=liFun(viewList[k].id,viewList[k].title);
	  }
	 }
	 $("#itemslist").append(contentHtml);	
	 $("#alllist").append(allConetentHtml);	
	 		
	   	$("#querydata").click(function(){
	   		var queryData = $(".s_input").val();
  			 $.post("/zftal-mobile/helpguide/helpguide_toView.html",
  				"title="+queryData+"&search=1",function(data){
                if(data.success){
                    var allConetentHtml = "";
                	viewList=data.viewList;
                	if(viewList&&viewList.length>0){
						  $("#message").hide();
						  $("#hotQuest").hide();
						  for (var m = 0, len =viewList.length; m < len; m++) {
							  allConetentHtml+=liFun(viewList[m].id,viewList[m].title);
						  }
						  $("#itemslist").empty();
						  $("#alllist").empty();
						  $("#alllist").append(allConetentHtml);
                	}else{
                		var $modal = $('#your-modal');
                		$modal.modal();
                	}
                }
	   		 },"json");
	   	});
	   	
	   	$("#bt").change(function(){
		   	var queryData = $(".s_input").val();
			if(queryData.length==0){
	  			window.location.href ="/zftal-mobile/helpguide/helpguide_toView.html";
			}
	   	});
	   	
	   	$(window).scroll(function(){
	   		if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
    			loadMoreList();
    		}
	   	});
});

function loadMoreList(){
		this.toPage++;
		var queryData = $(".am-form-field").val();
		$.post("/zftal-mobile/helpguide/helpguide_toViewmore.html",
		"query.toPage="+toPage+"&title="+queryData,function(data){
         if(data.success){
        	 var contentHtml = "";
             var viewList2=data.viewList2;
             if(viewList2&&viewList2.length>0){
				  $("#message").hide();
				  for (var m = 0, len =viewList2.length; m < len; m++) {
				       contentHtml+=liFun(viewList2[m].id,viewList2[m].title);
				  }
				  $("#alllist").append(contentHtml);
           	}
         }
    },"json");
}

Date.prototype.format =function(format)
{
    var o = {
    "M+" : this.getMonth()+1, //month
	"d+" : this.getDate(),    //day
	"h+" : this.getHours(),   //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
	"S" : this.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
    RegExp.$1.length==1? o[k] :
    ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
};

function clientHelpGView(guideid){
	$.post("/zftal-mobile/helpguide/helpguide_toViewCheck.html",
		"id="+guideid,function(data){
           if(data.success){
           var uri=encodeURIComponent("/zftal-mobile/helpguide/helpguide_toViewdetail.html?id="+guideid);
			window.location.href =decodeURIComponent(uri);
           }else{
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
<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
     	 没有可以显示的数据!
    </div>
  </div>
</div>

<div data-am-sticky="" class="" style="margin: 0px;">
	<div  class="search po_ab"  style="top:0px" >
		<div class="search_i">
		  <input type="search" id="bt" class="s_input am-form-field " placeholder="请根据标题查询">
		</div>
		<input id="querydata" type="button"  class="s_but " value="搜索">
	</div>
</div>
 <br><br>	
<div data-am-widget="list_news" class="am-list-news am-list-news-default" >
	<div class="am-panel am-panel-default" id="message">
	   	<div class="am-panel-bd">没有内容,请联系管理员！</div>
	</div>
	<div class="am-list-news-bd list">
	<div id="hotQuest" class="title1 text_a_c f_size14" style="margin-left: 10px;" >&nbsp;&nbsp;热门问题</div>
	  <ul class="" id="itemslist" style="border-top: 0px solid;border-bottom: 0px solid;">
	
	  </ul>
	 <div id="allQuest" class="title1 text_a_c f_size14" style="margin-left: 10px;" >&nbsp;&nbsp;全部问题</div>
	  <ul class="" id="alllist" style="border-top: 0px solid;border-bottom: 0px solid;">
	
	  </ul>
	 </div>
</div>

</body>
</html>