/*****************************
onmouseover-onmouseout(grcc)
Version: Ver 1.0
Passed : XHtml 1.0, CSS 2.0, IE5.0+, FF1.0+, Opera8.5+
Update : 2010-08-23
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

/*****************************
showhideDiv
Version: Ver 1.02
Passed : XHtml 1.0, CSS 2.0, IE5.0+, FF1.0+, Opera8.5+
Update : 2010-07-28
*****************************/
var temp;
var tempObj;
function getNext(o){
	while(o){
		if(o.nextSibling.nodeType==1)
		{return o.nextSibling;}
		o=o.nextSibling;
	}
	return o;
}	
function showhidediv(o,id){  
    var sbtitle = getNext(o);
    if(null!=temp&&temp!=sbtitle){
    	temp.style.display='none';
    	tempObj.className="open";
    }
		if(sbtitle){   
			temp=sbtitle;
	    tempObj=o;
   		if(sbtitle.style.display=='block'){
      	 	sbtitle.style.display = 'none';
       		o.className = "open";
   		}else{
   			sbtitle.style.display = 'block';
  			o.className = "close";
   		}
	
			if(sbtitle.style.display=='none'){
      	 	sbtitle.style.display = 'none';
       		o.className = "open";
   		}else{   
   			sbtitle.style.display = 'block';
  			o.className = "close";
   		}
	}
}

var ctx={
	getContextPath:function(){
		var path=ctx.getScriptContextPath();
		return path.substr(0,path.indexOf('script'));
	},
	getScriptContextPath:function(){
		var path,
	    i, ln,
	    scriptSrc,
	    match,
	    scripts = document.getElementsByTagName('script');

		for (i = 0, ln = scripts.length; i < ln; i++) {
		    scriptSrc = scripts[i].src;

		    match = scriptSrc.match(/jquery-common\.js$/);

		    if (match) {
		        path = scriptSrc.substring(0, scriptSrc.length - match[0].length);
		        break;
		    }
		}
		return path;
	},
	getBodyHeight:function(){
		return document.documentElement.clientHeight;
	},
	getBodyWidth:function(){
		return document.documentElement.clientWidth;
	},
	getBodyRealWidth:function(){
		return document.documentElement.scrollWidth-3;
	},
	getBodyScrollWidth:function(){
		return document.body.scrollWidth;
	},
	getBodyScrollHeight:function(){
		return document.body.scrollHeight;
	}
};


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
var showWindown=true; 
var bodyHeight=ctx.getBodyHeight();
var bodyWidth=ctx.getBodyWidth(); 

function hideWindown(winId){
	$("#windownbg").remove();
	$(winId).hide();
}

function tipsWindown(title,content,width,height,drag,time,showbg,cssName) {
	/**
	 * ===============================================================================
	 * 变量初始化
	 */
	var errMsg="加载数据出错...";
	var loadImgHtml="<div id='imgDiv' style='backgound-color:#FFFFFF;background-img:url()'><img src='"+ctx.getContextPath()+"images/loading.gif'/></div>";
	var errHandler=function(){
		$("#windown-content").html("<p class='windown-error'>"+errMsg+"</p>");
	};
	
	var waitHandler=function(o) {
		$("body").append($(loadImgHtml).css({
			width:90,
			height:90,
			position:'absolute',
			left:left+((width-90)/2),
			top:top+((height-90)/2),
			border:0, 
			zIndex: "999"
		}));
	};
	var hideHandler=function() {
		$("#windownbg").remove();
		$("#windown-box").fadeOut("slow",function(){$(this).remove();});
	};
	var hideLoadImg=function(){
		$('#imgDiv').fadeOut("slow",function(){$(this).remove();});
	}
	var borderPx=3;
	var titleHPx=22;
	if(width<(50))width=69 ;
	if(height<(titleHPx+borderPx*2)){height=58;} 
	
	var top=(bodyHeight-height)/4;
	//var top=100;
	var left=(bodyWidth-width)/2;
	//var left=180;
	if(top<0)top=0;if(left<0)left=0;
	/**
	 *===============================================================================
	 *实行预加载iframe内容 
	 */
	var winContent=document.createElement('div');
	winContent.id='windown-content';
	winContent.className='windown-content';
	
	contentType = content.substring(0,content.indexOf(":"));
	content = content.substring(content.indexOf(":")+1,content.length);
	if(contentType=='iframe'){
		winContent.innerHTML='<iframe allowtransparency="true" frameborder="0" width="'+(width-borderPx*3)+'" height="'+height+'" src="'+content+'"></iframe>';
	}
	
	/**
	 *===============================================================================
	 *初始化HTML
	 */
	var win=$("#windown-box");
	if(win!=null){
		win.remove(); //清除内容
		var dc=document.createDocumentFragment();
		var windiv=document.createElement("div");
		if(showWindown == true) {
			windiv.innerHTML="<div id=\"windownbg\" class=\"windownbg\" style=\"height:"+bodyHeight+"px;filter:alpha(opacity=0);opacity:0;z-index:998\"><iframe style=\"width:100%;height:100%;filter:alpha(opacity=0);-moz-opacity:0\" frameborder=\"0\" ></iframe></div>"
				 		  + "<div id=\"windown-box\" class=\"windown-box\" >"
				 		  + "<div id=\"windown-title\" class=\"windown-title\"><h2></h2><span id=\"windown-close\" class=\"windown-close\">关闭</span></div>"
				 		  + "<div id=\"windown-content-border\" class=\"windown-content-border\">"
				 		  + "</div></div>";
			dc.appendChild(windiv);
			$("body").append(dc);
			show = false;
		}
	}else{
		$(win).show();
	}
	
	$("#windown-title h2").html(title);
	$("#windownbg").animate({opacity:"0.5"},"normal");//设置透明度
	
	$("#windown-content-border").css({
		padding:3
	});
	$("#windown-box").css({
		width:width,
		height:height,
		position:'absolute',
		top:top,
		left:left,
		zIndex: "998"
	}).show();
	$(winContent).attr("class","windown-"+cssName); 
	switch(contentType) {
		case "text":
				$(winContent).html(content);
			break;
		case "id":
				$(winContent).html($("#"+content+"").html());
			break;
		case "img":
				$(winContent).html("<img src="+content+" alt='' />");
			break;
		case "url":
				var content_array=content.split("?");
				$(winContent).ajaxStart(waitHandler);
				$.ajax({
					type:content_array[0],
					url:content_array[1],
					data:content_array[2],
					error:errHandler,
					success:function(html){
						$(winContent).html(html);
					}
				});
			break;
		case "iframe": 
			waitHandler();
			break;
	}
	if(showbg == "true") {
		$("#windownbg").show();
	}else {
		$("#windownbg").remove();
	};
	$("#windown-close").click(hideHandler);
	$('#windown-content-border').append(winContent);
	dragger({control:'#windown-title',target:'#windown-box'});
	hideLoadImg();
	return {
			windown:$('#windown-box'),
			hide:hideHandler
		};
}

// 模块拖拽
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
