/*****************************
onmouseover-onmouseout(grcc)
Version: Ver 1.0
Passed : XHtml 1.0, CSS 2.0, IE5.0+, FF1.0+, Opera8.5+
Update : 2011-08-23
*****************************/
function mouseover(str) {
if(document.getElementById(str)) {
document.getElementById(str).style.display="";
}
}
function mouseout(str) {
if(document.getElementById(str)) {
document.getElementById(str).style.display="none";
}
}

//模块拖拽
function dragger(opt){
	var did=opt.control;
	var tid=opt.target;
	var bodyHeight=document.documentElement.clientHeight; 
	var bodyWidth=document.documentElement.clientWidth; 
	var _move=false;//移动标记
	var _x,_y;//鼠标离控件左上角的相对位置
	$(did).css({
		cursor: 'move' 
	});
    $(did).click(function(){
        //alert("click");//点击（松开后触发）
    }).mousedown(function(e){
        _move=true;
        var dragObj = this;
        
        if($.browser.msie){
        	dragObj.setCapture();
        }else{
        	window.captureEvents(Event.MOUSEMOVE);
        }
       
        //FireFox 去除容器内拖拽图片问题
        if (e.preventDefault) {
            e.preventDefault();
            e.stopPropagation();
        }
        _x=e.pageX-parseInt($(tid).css("left"));
        _y=e.pageY-parseInt($(tid).css("top"));
    });
    $(document).mousemove(function(e){
        if(_move){
        	var pos=$(tid).position();
            var x=e.pageX-_x;//移动时根据鼠标位置计算控件左上角的绝对位置
            var y=e.pageY-_y;
            if(x<0)x=0;
            if(y<0)y=0;
            if(x+$(tid).width()>bodyWidth)x=bodyWidth-$(tid).width();
            if(y+$(tid).height()>bodyHeight)y=bodyHeight-$(tid).height();
            $(tid).css({top:y,left:x});//控件新位置
        }
    }).mouseup(function(e){
	    _move=false; 
	    var dragObj = this;
	    if($.browser.msie){
	    	dragObj.releaseCapture();
	    }else{
	    	window.releaseEvents(Event.MOUSEMOVE);
	    }
    });
}

/*****************************
showhideDiv
Version: Ver 1.02
Passed : XHtml 1.0, CSS 2.0, IE5.0+, FF1.0+, Opera8.5+
Update : 2011-07-28
*****************************/
function getNext(o)
{
	while(o)
	{
		if(o.nextSibling.nodeType==1)
		{return o.nextSibling;}
		o=o.nextSibling;
	}
	return o;
}	
//function showhidediv(o,id)
//{
//    var sbtitle = getNext(o);
//	if(sbtitle)
//	{
//   		if(sbtitle.style.display=='block')
//		{
//      	 	sbtitle.style.display = 'none';
//       		o.className = "open";
//   		}
//		else
//		{
//   			sbtitle.style.display = 'block';
//  			o.className = "close";
//   		}
//	
//		if(sbtitle.style.display=='none')
//		{
//      	 	sbtitle.style.display = 'none';
//       		o.className = "open";
//   		}
//		else
//		{
//   			sbtitle.style.display = 'block';
//  			o.className = "close";
//   		}
//	}
//}
function showhidediv(o){
	var target = $(o);
	var siblings = $(o).parent().siblings().find("h3.open");
	siblings.next().slideUp();
	siblings.removeClass("open").addClass("close");
	
	var style = target.attr("class");
	if(style == 'open'){
		target.removeClass().addClass("close");
		target.next().slideUp();
	}else{
		target.removeClass().addClass("open");
		target.next().slideDown();
	}
}
/*=====实现弹出层的相关js=====*/
///-------------------------------------------------------------------------
//jQuery弹出窗口 By Await [2009-11-22]
//--------------------------------------------------------------------------
/*参数：[可选参数在调用时可写可不写,其他为必写]
----------------------------------------------------------------------------
    title:	窗口标题
  content:  内容(可选内容为){ text | id | img | url | iframe }
    width:	内容宽度
   height:	内容高度
	 drag:  是否可以拖动(ture为是,false为否)
     time:	自动关闭等待的时间，为空是则不自动关闭
   showbg:	[可选参数]设置是否显示遮罩层(0为不显示,1为显示)
  cssName:  [可选参数]附加class名称
 ------------------------------------------------------------------------*/
 //示例:黑色背景
 //------------------------------------------------------------------------
 //simpleWindown("例子","text:例子","500","400","true","3000","0","exa")
 //------------------------------------------------------------------------
var showWindown = true;
//var templateSrc = ""; 设置loading.gif路径
function tipsWindown(title,content,width,height,drag,time,showbg,cssName,closeCallback) {
	$("#windown-box").remove(); //请除内容
	var width = width>= 950?this.width=950:this.width=width;	    //设置最大窗口宽度
	var height = height>= 527?this.height=527:this.height=height;  //设置最大窗口高度
	if(showWindown == true) {
		var simpleWindown_html = new String;
			
			simpleWindown_html = "<div id=\"windownbg\" class=\"windownbg\" style=\"height:"+$(document).height()+"px;filter:alpha(opacity=0);opacity:0;z-index:998\"><iframe style=\"width:100%;height:100%;filter:alpha(opacity=0);-moz-opacity:0\" frameborder=\"0\" ></iframe></div>";
			 //iframe试图解决IE6下浮动图片被select覆盖问题
			simpleWindown_html += "<div id=\"windown-box\" class=\"windown-box\" >";
			simpleWindown_html += "<div id=\"windown-title\" class=\"windown-title\"><h2></h2><span id=\"windown-close\" class=\"windown-close\">关闭</span></div>";
			simpleWindown_html += "<div id=\"windown-content-border\" class=\"windown-content-border\"><div id=\"windown-content\" class=\"windown-content\"></div></div>"; 
			simpleWindown_html += "</div>";
			$("body").append(simpleWindown_html);
			show = false;
	}
	contentType = content.substring(0,content.indexOf(":"));
	content = content.substring(content.indexOf(":")+1,content.length);
	switch(contentType) {
		case "text":
		$("#windown-content").html(content);
		break;
		case "id":
		$("#windown-content").html($("#"+content+"").html());
		break;
		case "img":
		$("#windown-content").ajaxStart(function() {
			$(this).html("<img src='../images/loading.gif' class='loading' />");
		});
		$.ajax({
			error:function(){
				$("#windown-content").html("<p class='windown-error'>??...</p>");
			},
			success:function(html){
				$("#windown-content").html("<img src="+content+" alt='' />");
			}
		});
		break;
		case "url":
		var content_array=content.split("?");
		$("#windown-content").ajaxStart(function(){
			$(this).html("<p class='loading'/></p>");
		});
		$.ajax({
			type:content_array[0],
			url:content_array[1],
			data:content_array[2],
			error:function(){
				$("#windown-content").html("<p class='windown-error'>加载数据出错...</p>");
			},
			success:function(html){
				$("#windown-content").html(html);
			}
		});
		break;
		case "iframe":
		$("#windown-content").ajaxStart(function(){
			$(this).html("<img src='../images/loading.gif' class='loading' />");
		});
		$.ajax({
			error:function(){
				$("#windown-content").html("<p class='windown-error'>加载数据出错...</p>");
			},
			success:function(html){
				$("#windown-content").html("<iframe src=\""+content+"\" width=\"100%\" height=\""+parseInt(height)+"px"+"\" scrolling=\"auto\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>");
			}
		});
	}
	$("#windown-title h2").html(title);
	if(showbg == "true") {$("#windownbg").show();}else {$("#windownbg").remove();};
	$("#windownbg").animate({opacity:"0.5"},"normal");//设置透明度
	$("#windown-box").show();
	if( height >= 527 ) {
		$("#windown-title").css({width:(parseInt(width)+22)+"px"});
		$("#windown-content").css({width:(parseInt(width)+17)+"px",height:height+"px"});
	}else {
		$("#windown-title").css({width:(parseInt(width)+10)+"px"});
		$("#windown-content").css({width:width+"px",height:height+"px"});
	}
	var	cw = document.documentElement.clientWidth,ch = document.documentElement.clientHeight,est = document.documentElement.scrollTop; 
	var _version = $.browser.version;
		var scrollTop=top.window.document.documentElement.scrollTop;
		var windowHeight=$(top.window).height();
		if(scrollTop==0){
			//$("#windown-box").css({left:"50%",top:"50%",marginTop:-((windowHeight/2)-100)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "998"});
			$("#windown-box").css({left:"50%",top:(windowHeight/2)-65+"px",marginTop:-((windowHeight/2)-100)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "998"});
			//$("#windown-box").css({left:"50%",top:(windowHeight/2)-65+"px",marginTop:-((parseInt(height)+53)/2)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "998"});
		}else{
			//$("#windown-box").css({left:"50%",top:"50%",marginTop:-((parseInt(height)+53)/2)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "998"});
			$("#windown-box").css({left:"50%",top:scrollTop-130+(windowHeight/2)+"px",marginTop:-((parseInt(height)+53)/2)+"px",marginLeft:-((parseInt(width)+32)/2)+"px",zIndex: "998"});
		}
	var Drag_ID = document.getElementById("windown-box"),DragHead = document.getElementById("windown-title");
		
	var moveX = 0,moveY = 0,moveTop,moveLeft = 0,moveable = false;
		if ( _version == 6.0 ) {
			moveTop = est;
		}else {
			moveTop = 0;
		}
	var	sw = Drag_ID.scrollWidth,sh = Drag_ID.scrollHeight;
		DragHead.onmouseover = function(e) {
			if(drag == "true"){DragHead.style.cursor = "move";}else{DragHead.style.cursor = "default";}
		};
		DragHead.onmousedown = function(e) {
		if(drag == "true"){moveable = true;}else{moveable = false;}
		e = window.event?window.event:e;
		var ol = Drag_ID.offsetLeft, ot = Drag_ID.offsetTop-moveTop;
		moveX = e.clientX-ol;
		moveY = e.clientY-ot;
		document.onmousemove = function(e) {
				if (moveable) {
				e = window.event?window.event:e;
				var x = e.clientX - moveX;
				var y = e.clientY - moveY;
					if ( x > 0 &&( x + sw < cw) && y > 0 && (y + sh < ch) ) {
						Drag_ID.style.left = x + "px";
						Drag_ID.style.top = parseInt(y+moveTop) + "px";
						Drag_ID.style.margin = "auto";
						}
					}
				}
		document.onmouseup = function () {moveable = false;};
		Drag_ID.onselectstart = function(e){return false;}
	}
	$("#windown-content").attr("class","windown-"+cssName);
	var closeWindown = function() {
		$("#windownbg").remove();
		$("#windown-box").fadeOut("slow",function(){
			$(this).remove();
			if(closeCallback != null){
				closeCallback();
			}
		});
	};
	if( time == "" || typeof(time) == "undefined") {
		$("#windown-close").click(function() {
			$("#windownbg").remove();
			$("#windown-box").fadeOut("slow",function(){
				$(this).remove();
				if(closeCallback != null){
					closeCallback();
				}
			});
		});
	}else { 
		setTimeout(closeWindown,time);
	}
}
