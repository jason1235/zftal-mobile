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
<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script src="<%=request.getContextPath() %>/assets/js/amazeui.js"></script>
<script src="<%=request.getContextPath() %>/assets/js/handlebars.min.js"></script>
<script src="<%=request.getContextPath() %>/assets/js/amazeui.widgets.helper.js"></script>
<style type="text/css">
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
            padding:2px;
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
        
        .pet_hd_block_tag span.hd_tag_jh {
			text-align:center;
		    background: #00a0e9;
		}
		
		.pet_hd_block_tag span {
		    background: #f28d00;
		    border-radius: 4px;
		    width: 35px;
		    color: #fff;
		}
		.am-list-news-default .am-list-item-hd {
		    font-weight: bold;
		}
    </style>


<title></title>

</head>
<body >
	

<div data-am-sticky style="height:45px;width:100%;background:#00A0E9;text-align:center; line-height:45px;font-size: 18px;color:#FFFFFF;font-family: inherit;">
		移动校园
	</div>
       
<div id="typenews" class="pet_head" style="background:#FFFFFF;height:35px;    border-bottom: 1px solid #f1f1f1;">
        <div style="width:100%;overflow-y: hidden;">
<div style="width:100%;overflow:auto;">
  <div id="newstab" style="width:640px;">
	
  </div>
</div>
</div>
        
    </div>

<div class="am-slider am-slider-default" data-am-flexslider id="reslider" style="margin: 0 0 0;">
  <ul class="am-slides" id="recommondNews">
    
  </ul>
</div>

<div id="gallery" style="margin-top:20px;margin-bottom:2px;">
	
    <ul id="common" style="padding:0;" data-am-widget="gallery" class="am-gallery am-avg-sm-4  am-avg-md-4 am-avg-lg-4 am-gallery-default am-no-layout" data-am-gallery="{  }">
        
    </ul>
</div>





<div class="pet_hd" style="min-height: 550px;">
  <div class="pet_article_like" style="min-height: 550px;">
        <div class="pet_content_main pet_article_like_delete" style="min-height: 550px;">
          <div data-am-widget="list_news" class="am-list-news am-list-news-default am-no-layout" style="min-height: 550px;">
  <div class="am-list-news-bd" style="min-height: 550px;margin-bottom:50px;">
<div id="loadnews"  style="margin-top:45px;background: #FFFFFF;text-align: center;">正在载入..
      <span class="am-icon-spinner am-icon-spin"></span>
    </div>
	<ul class="am-list" id="newslist">
		



	</ul>

  </div>
  </div>
</div>
  </div>
</div>
<script>
	var start = 1;
	var catalog = "";
	$("#loadnews").hide();
	$("#loading").hide();
	$(function() { 
		var newsTab = ${newsTab};
		var newsTabHtml = "";
		for ( var i = 0; i < newsTab.length; i++) {
			if(i > 0){
				newsTabHtml += "<div style='width:80px;float:left;text-align:center;margin-top:5px;' class='autocut tab' onclick='tabclick(this,\""+newsTab[i].catalog+"\");'>" + newsTab[i].name + 
							   "<input type='hidden' name ='catalog' value='" + newsTab[i].catalog + "'/></div>";
			}else{
				newsTabHtml += "<div style='width:80px;float:left;text-align:center;margin-top:5px;color:red;' class='autocut tab' onclick='tabclick(this,\""+newsTab[i].catalog+"\");'>" + newsTab[i].name + 
				   "<input type='hidden' name ='catalog' value='" + newsTab[i].catalog + "'/></div>";
			   catalog = newsTab[i].catalog;
			   getNewsContent(catalog,start);
			   
			}
		}
		$("#newstab").append(newsTabHtml);
		$("#typenews").hide();
		$(window).scroll(function() { 
			var topheight = window.scrollY; 
			console.info(topheight);
			if(topheight >= 270){
				$("#typenews").show();
			}else{
				$("#typenews").hide();
			}
			if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
    			start++;
    			$("#loading").show();
    			getNewsContent(catalog,start);
    			$("#loading").hide();
    		}
		}); 
	}); 
	
	var recommendList = ${recommendList};
	var relen = recommendList.length;
	var reArrList = new Array();
	if(typeof(relen) == "undefined"){
	   reArrList.push(recommendList.mhrecommend);
    }else{
       reArrList = recommendList
    }
	console.log(reArrList);
	var reHtml = "";
	if(reArrList.length == 0){
		defaultHtml = "<img src='<%=request.getContextPath() %>/img/recommend.png' style='width:100%;height:180px;'/>"
		$("#reslider").append(defaultHtml);
	}else{
		for ( var m = 0; m < reArrList.length; m++) {
			var logoImage = "";
			if(typeof(reArrList[m].logopath.length) == "undefined"){
				var imageurl = reArrList[m].logopath.logo;
				if(imageurl == null || imageurl == ""){
					logoImage = "<img src='<%=request.getContextPath() %>/img/zxnoimage.png' style='width:100%;height:180px;'/>";
				}else{
					logoImage = "<img src='"+reArrList[m].logopath.logo+"' style='width:100%;height:180px;'/>";
				}
			}else{
				logoImage = "<img src='"+reArrList[m].logopath[0]+"' style='width:100%;height:180px;'/>";
			}
		reHtml += "<li>" + logoImage +  "<div class='am-slider-desc'>远方 有一个地方 那里种有我们的梦想</div></li>";
		}
		$("#recommondNews").append(reHtml);
	}
	
	
    
    var apps = ${apps};
    var appHtml = "";
    var j = 0;
    for(var i=0;i<apps.length;i++){
    	if(apps[i].type != "WEB_SERVICE") continue;
    	j++;
		appHtml += "<li  style=\"border: 1px solid #eeeeee;height:75px;line-height:30px;\"><div class=\"am-gallery-item\"  > ";
		if(apps[i].otherFlag == '0'){
			if(apps[i].procode == '002' || apps[i].procode == '009'){
				appHtml += "<a href=\"javascript:doJWClick('" + apps[i].url + "');\"> ";
		    }else{
		    	appHtml += "<a href=\"javascript:doClick('" + apps[i].url + "');\"> ";
		    }
		}else{
			appHtml +=	"<a href=\"" + apps[i].url + "\"> ";
		}
			appHtml += "  <img  width=\"50\" height=\"50\" src=\""+ apps[i].icon +"\"> "+
                  "  <h3 class=\"am-gallery-title\">"+ apps[i].name +"</h3></a></div></li>"	;	
	}  
	appHtml += "<li style=\"border: 1px solid #eeeeee;height:75px;\"><div class=\"am-gallery-item\"> "+
               "<a href=\"\"> "+
               "<img  width=\"50\" height=\"50\" src=\"<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>/img/bgrc.png\">"+
               " <h3 class=\"am-gallery-title\">更多</h3></a></div></li>";
    j = j%4;         
    for(var i=0;i<3-j;i++){
    	appHtml += "<li style=\"border: 1px solid #eeeeee;height:75px;\"></li>";
    }
    $("#common").append(appHtml);
    
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
    function tabclick(obj,type){
    	  catalog = type;
    	  start = 1;
		  $(".tab").each(function(i){
			  $(this).css("color","");
  		  });
		  obj.style.color="red";
		  $("#newslist").empty();
		  $(window).scrollTop(270);
		  $("#loadnews").show();
		  getNewsContent(catalog,start);
		  $("#loadnews").hide();
		  $("#typenews").show();
	   }
    function getNewsContent(catalog,start){
    	$.post("<%=request.getContextPath()%>/mobile/web_getNewsContent.html","catalog="+catalog+"&start="+start,function(data){
    		if(data.success){
			   var content = JSON.parse(data.content);
			   var news = content.news;
			   if(typeof(news) == "undefined"){
				   return false;
			   }
			   //console.log(news);
			   var contentHtml = "";
			   var len = news.length;
			   if(typeof(len) == "undefined"){
				   var lenget = 0;
				   var heightHtml = "";
				   var imageHtml = "";
				   var intro = news.introduce;
				   var isintro = intro == "" || intro == null ? true : false;
				   var logoType = news.logopath;
				   if(logoType instanceof Array){
					   var len = logoType.length;
					   switch (len) {
						case 0:{
							if(isintro){
								heightHtml = " style='height:100px;' >";
								lenget = 1;
							}else{
								heightHtml = " style='height:125px;' >";
								lenget = 2;
							}
						}
							break;
						case 2:{
							heightHtml = " style='height:150px;' >";
							lenget = 5;
						}
						break;
						case 3:{
							heightHtml = " style='height:160px;' >";
							lenget = 6;
						}
						break;

						default:
							break;
						}
						   
				   }else{
					   if(isintro){
							lenget = 3;
						}else{
							lenget = 4;
						}
				   }
				   switch (lenget) {
					case 1:{
						imageHtml += "<div class='am-u-sm-12 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
							news.title+"</h3></div>";
					}
						break;
					case 2:{
						imageHtml += "<div class='am-u-sm-12 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
							news.title+"</h3><div class='am-list-item-text pet_list_one_text'>"+news.introduce+"</div></div>";						
					}
						break;
					case 3:{
						imageHtml += "<div class=' am-u-sm-8 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
							news.title+"</h3></div><div class='am-u-sm-4 am-list-thumb'>" + 
				            "<img src='"+news.logopath.logo+"' class='pet_list_one_img' alt='' height='80px;'/></div>";
					}
						break;
					case 4:{
						imageHtml += "<div class=' am-u-sm-8 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
							news.title+"</h3><div class='am-list-item-text pet_list_one_text'>"+news.introduce+"</div>"+
							"</div><div class='am-u-sm-4 am-list-thumb'>" + 
				            "<img src='"+news.logopath.logo+"' class='pet_list_one_img' alt='' height='80px;'/></div>";
					}
						break;
					case 5:{
						imageHtml += "<h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
							news.title+"</h3>"+
							"<div class=' am-u-sm-6 am-list-main pet_list_one_nr'>" +
			        		"<div class='am-gallery-item'>" +
			              	"<img src='"+news.logopath[0]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/>"+
			        		"</div></div><div class=' am-u-sm-6 am-list-main pet_list_one_nr'>" +
			      			"<div class='am-gallery-item'>" +
			              	"<img src='"+news.logopath[1]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/></div></div>";
					}
						break;
					case 6:{
						imageHtml += "<h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
						news.title+"</h3>"+
						"<div class='am-u-sm-4 am-list-main pet_list_one_nr'>" +
		        		"<div class='am-gallery-item'>" +
		              	"<img src='"+news.logopath[0]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/>"+
		        		"</div></div><div class=' am-u-sm-4 am-list-main pet_list_one_nr'>" +
		      			"<div class='am-gallery-item'>" +
		              	"<img src='"+news.logopath[1]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/></div></div>"+
		              	"<div class=' am-u-sm-4 am-list-main pet_list_one_nr'>" +
		      			"<div class='am-gallery-item'>" +
		              	"<img src='"+news.logopath[2]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/></div></div>";
					}
						break;

					default:
						break;
					}
				   contentHtml += 
	   				  "<li class='am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right pet_list_one_block'" +heightHtml+
	   				  "<a href='"+news[i].url+"'>"+
	   				  "<div class='pet_list_one_info'><div>" +
					  "<div class='pet_list_one_info_name pet_hd_block_tag' ><span class='hd_tag_jh'>来源</span>&nbsp;" + news.author + "</div>"+
					  "</div><div class='pet_list_one_info_r'><div class='pet_list_tag pet_list_tag_stj'>" + news.timecreate + "</div></div></div>"+
					  imageHtml+"</a></li>";
			   }else{
				   for(var i = 0; i < news.length; i++){
					   var lenget = 0;
					   var heightHtml = "";
					   var imageHtml = "";
					   var intro = news[i].introduce;
					   var isintro = intro == "" || intro == null ? true : false;
					   var logoType = news[i].logopath;
					   if(logoType instanceof Array){
						   var len = logoType.length;
						   switch (len) {
							case 0:{
								if(isintro){
									heightHtml = " style='height:100px;' >";
									lenget = 1;
								}else{
									heightHtml = " style='height:125px;' >";
									lenget = 2;
								}
							}
								break;
							case 2:{
								heightHtml = " style='height:150px;' >";
								lenget = 5;
							}
							break;
							case 3:{
								heightHtml = " style='height:160px;' >";
								lenget = 6;
							}
							break;
	
							default:
								break;
							}
							   
					   }else{
								heightHtml = " style='height:130px;' >";
						   if(isintro){
								lenget = 3;
							}else{
								lenget = 4;
							}
					   }
					   switch (lenget) {
						case 1:{
							imageHtml += "<div class='am-u-sm-12 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
								news[i].title+"</h3></div>";
						}
							break;
						case 2:{
							imageHtml += "<div class='am-u-sm-12 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
								news[i].title+"</h3><div class='am-list-item-text pet_list_one_text'>"+news[i].introduce+"</div></div>";						
						}
							break;
						case 3:{
							imageHtml += "<div class=' am-u-sm-8 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
								news[i].title+"</h3></div><div class='am-u-sm-4 am-list-thumb'>" + 
					            "<img src='"+news[i].logopath.logo+"' class='pet_list_one_img' alt='' height='80px;'/></div>";
						}
							break;
						case 4:{
							imageHtml += "<div class=' am-u-sm-8 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
								news[i].title+"</h3><div class='am-list-item-text pet_list_one_text'>"+news[i].introduce+"</div>"+
								"</div><div class='am-u-sm-4 am-list-thumb'>" + 
					            "<img src='"+news[i].logopath.logo+"' class='pet_list_one_img' alt='' height='80px;'/></div>";
						}
							break;
						case 5:{
							imageHtml += "<h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
								news[i].title+"</h3>"+
								"<div class=' am-u-sm-6 am-list-main pet_list_one_nr'>" +
				        		"<div class='am-gallery-item'>" +
				              	"<img src='"+news[i].logopath[0]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/>"+
				        		"</div></div><div class=' am-u-sm-6 am-list-main pet_list_one_nr'>" +
				      			"<div class='am-gallery-item'>" +
				              	"<img src='"+news[i].logopath[1]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/></div></div>";
						}
							break;
						case 6:{
							imageHtml += "<h3 class='am-list-item-hd pet_list_one_bt autocut'>"+
							news[i].title+"</h3>"+
							"<div class=' am-u-sm-4 am-list-main pet_list_one_nr'>" +
			        		"<div class='am-gallery-item'>" +
			              	"<img src='"+news[i].logopath[0]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/>"+
			        		"</div></div><div class=' am-u-sm-4 am-list-main pet_list_one_nr'>" +
			      			"<div class='am-gallery-item'>" +
			              	"<img src='"+news[i].logopath[1]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/></div></div>"+
			              	"<div class=' am-u-sm-4 am-list-main pet_list_one_nr'>" +
			      			"<div class='am-gallery-item'>" +
			              	"<img src='"+news[i].logopath[2]+"' class='pet_list_one_img' alt='' style='width:100%;height:80px;'/></div></div>";
						}
							break;
	
						default:
							break;
						}
					   contentHtml += 
		   				  "<li class='am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right pet_list_one_block'" +heightHtml+
		   				  "<a href='"+news[i].url+"'>"+
		   				  "<div class='pet_list_one_info'><div>" +
						  "<div class='pet_list_one_info_name pet_hd_block_tag' ><span class='hd_tag_jh'>来源</span>&nbsp;" + news[i].author + "</div>"+
						  "</div><div class='pet_list_one_info_r'><div class='pet_list_tag pet_list_tag_stj'>" + news[i].timecreate + "</div></div></div>"+
						  imageHtml+"</a></li>";
					  }   
				   }			  
			   }
    		//console.log(contentHtml);
    		$("#newslist").append(contentHtml);
        },"json");
    }
    </script>
    


<div id="loading" class="am-modal-bd " style="margin-bottom:45px;background: #FFFFFF;text-align: center;">正在载入..
      <span class="am-icon-spinner am-icon-spin"></span>
    </div>

<hr data-am-widget="divider"  style="margin: 0px;" class="am-divider am-divider-default" />
<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default  testNavClass am-no-layout" id="" >
          <ul class="am-navbar-nav am-cf am-avg-sm-3" style="background-color: #FFFFFF;">
              <li>
                <a href="/zftal-mobile/mobile/web_index.html" class="" style="color: rgb(17, 87, 170);">
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
                <a href="/zftal-mobile/mobile/web_portal.html" class="" >
                      <span class="am-icon-user"></span>
                    <span class="am-navbar-label">我的门户</span>
                </a>
              </li>
          </ul>
      </div>
</body>
</html>