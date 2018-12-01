var alert1=window.alert;
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
 //示例:
 //------------------------------------------------------------------------
 //simpleWindown("例子","text:例子","500","400","true","3000","0","exa")
 //------------------------------------------------------------------------
var showWindown = true;
var templateSrc = "http://www.zfsoft.com:82/zfstyle_v4/"; //设置loading.gif路径
function tipsWindown2(title, content, width, height, drag, time, showbg, cssName, iframeurl, iframeheight, iframescrolling) {
	//iframe height width
	//jQuery("#iframeid").css("height",height);
	//jQuery("#iframeid").css("width","100%");
	jQuery("#windown-box").remove(); //请除内容
	var width = width >= 950 ? this.width = 950 : this.width = width;	    //设置最大窗口宽度
	var height = height >= 527 ? this.height = 527 : this.height = height;  //设置最大窗口高度
	if (showWindown == true) {
		var simpleWindown_html = new String;
		simpleWindown_html = "<div id=\"windownbg\" style=\"height:900px;filter:alpha(opacity=0);opacity:0;z-index: 998\"><iframe style=\"width:100%;height:100%;filter:alpha(opacity=0);-moz-opacity:0\" frameborder=\"0\" ></iframe></div>";
		simpleWindown_html += "<div id=\"windown-box\">";
		simpleWindown_html += "<div id=\"windown-title\"><h2></h2><span id=\"windown-close\">\u5173\u95ed</span></div>";
		simpleWindown_html += "<div id=\"windown-content-border\"><div id=\"windown-content\"></div></div>";
		simpleWindown_html += "</div>";
		var _version = jQuery.browser.version;
		if (_version == 6) {
			//simpleWindown_html += "<iframe id=\"DivShim\" scrolling=\"no\" frameborder=\"0\" style=\"position:absolute; top:0px; left:0px; width:100%;height:" + jQuery(document).height() + "px;\"></iframe>";//背景变成白色，盖掉下拉列表框
		}
		jQuery("body").append(simpleWindown_html);
			//show = false;
	}
	contentType = content.substring(0, content.indexOf(":"));
	content = content.substring(content.indexOf(":") + 1, content.length);
	switch (contentType) {
	  case "text":
		jQuery("#windown-content").html(content);
		break;
	  case "page":
		//jQuery("#windown-content").html(jQuery("#"+content+"").html());
		//jQuery("#windown-content").load(iframeurl)
//		var _html = jQuery('#windown-content-border').html();
//		var html = "<iframe style=\"position:absolute;width:100%;height:100%;top:0;left:0;overflow-x:no;overflow-y:no;\" id=\"iframeid\" allowtransparency=\"true\"  height=\"" + iframeheight + "\" width=\"100%\" scrolling=\"" + iframescrolling + "\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" >";
//			html = html+_html;
//			html = html+"</iframe>";
//		jQuery('#windown-content-border').html(html)
		jQuery("#windown-content").html("<iframe src=\"" + iframeurl + "\" style=\"position:absolute;z-index:999;width:100%;height:100%;top:0;left:0;overflow-x:no;overflow-y:no;\" id=\"iframeid\" allowtransparency=\"true\"  height=\"" + iframeheight + "\" width=\"100%\" scrolling=\"" + iframescrolling + "\" frameborder=\"0\" marginwidth=\"0\" marginheight=\"0\" ></iframe>");
		break;
	  case "ids":
		jQuery("#windown-content").html(iframeurl);
		break;
	  case "img":
		jQuery("#windown-content").ajaxStart(function () {
			jQuery(this).html("<img src='" + templateSrc + "/images/loading.gif' class='loading' />");
		});
		jQuery.ajax({error:function () {
			jQuery("#windown-content").html("<p class='windown-error'>\u52a0\u8f7d\u6570\u636e\u51fa\u9519...</p>");
		}, success:function (html) {
			jQuery("#windown-content").html("<img src=" + content + " alt='' />");
		}});
		break;
	  case "url":
		var content_array = content.split("?");
		jQuery("#windown-content").ajaxStart(function () {
			jQuery(this).html("<img src='" + templateSrc + "/images/loading.gif' class='loading' />");
		});
		jQuery.ajax({type:content_array[0], url:content_array[1], data:content_array[2], error:function () {
			jQuery("#windown-content").html("<p class='windown-error'>\u52a0\u8f7d\u6570\u636e\u51fa\u9519...</p>");
		}, success:function (html) {
			jQuery("#windown-content").html(html);
		}});
		break;
	  case "iframe":
		jQuery("#windown-content").ajaxStart(function () {
			jQuery(this).html("<img src='" + templateSrc + "/images/loading.gif' class='loading' />");
		});
		jQuery.ajax({error:function () {
			jQuery("#windown-content").html("<p class='windown-error'>\u52a0\u8f7d\u6570\u636e\u51fa\u9519...</p>");
		}, success:function (html) {
			jQuery("#windown-content").html("<iframe src=\"" + content + "\" width=\"100%\" height=\"" + parseInt(height) + "px" + "\" scrolling=\"auto\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>");
		}});
	}
	jQuery("#windown-title h2").html(title);
	if (showbg) {
		jQuery("#windownbg").show();
	} else {
		jQuery("#windownbg").remove();
	}
	jQuery("#windownbg").animate({opacity:"0.5"}, "normal");//设置透明度
	jQuery("#windown-box").show();
	if (height >= 527) {
		jQuery("#windown-title").css({width:(parseInt(width) + 22) + "px"});
		jQuery("#windown-content").css({width:(parseInt(width) + 17) + "px", height:height + "px"});
	} else {
		jQuery("#windown-title").css({width:(parseInt(width) + 10) + "px"});
		jQuery("#windown-content").css({width:width + "px", height:height + "px"});
	}
	var cw = document.documentElement.clientWidth, ch = document.documentElement.clientHeight, est = document.documentElement.scrollTop;
	var _version = jQuery.browser.version;
	if ("ids" == contentType) {
		if (_version == 6) {
			jQuery("#windown-box").css({left:"50%", top:(parseInt((ch) / 2) + est - 120) + "px", marginTop:-((parseInt(height) + 53) / 2) + "px", marginLeft:-((parseInt(width) + 32) / 2) + "px", zIndex:"999999"});
		} else {
			jQuery("#windown-box").css({left:"50%", top:"20%", marginTop:-((parseInt(height) + 53 - 100) / 2) + "px", marginLeft:-((parseInt(width) + 32) / 2) + "px", zIndex:"999999"});
		}
	} else {
		if (_version == 6) {
			jQuery("#windown-box").css({left:"50%", top:(parseInt((ch) / 2) + est) + "px", marginTop:-((parseInt(height) + 53) / 2) + "px", marginLeft:-((parseInt(width) + 32) / 2) + "px", zIndex:"999999"});
		} else {
			jQuery("#windown-box").css({left:"50%", top:"50%", marginTop:-((parseInt(height) + 53) / 2) + "px", marginLeft:-((parseInt(width) + 32) / 2) + "px", zIndex:"999999"});
		}
	}
	var Drag_ID = document.getElementById("windown-box"), DragHead = document.getElementById("windown-title");
	var moveX = 0, moveY = 0, moveTop, moveLeft = 0, moveable = false;
	if (_version == 6) {
		moveTop = est;
	} else {
		moveTop = 0;
	}
	var sw = Drag_ID.scrollWidth, sh = Drag_ID.scrollHeight;
	DragHead.onmouseover = function (e) {
		if (drag == "true") {
			DragHead.style.cursor = "move";
		} else {
			DragHead.style.cursor = "default";
		}
	};
	DragHead.onmousedown = function (e) {
		if (drag == "true") {
			moveable = true;
		} else {
			moveable = false;
		}
		e = window.event ? window.event : e;
		var ol = Drag_ID.offsetLeft, ot = Drag_ID.offsetTop - moveTop;
		moveX = e.clientX - ol;
		moveY = e.clientY - ot;
		document.onmousemove = function (e) {
			if (moveable) {
				e = window.event ? window.event : e;
				var x = e.clientX - moveX;
				var y = e.clientY - moveY;
				if (x > 0 && (x + sw < cw) && y > 0 && (y + sh < ch)) {
					Drag_ID.style.left = x + "px";
					Drag_ID.style.top = parseInt(y + moveTop) + "px";
					Drag_ID.style.margin = "auto";
				}
			}
		};
		document.onmouseup = function () {
			moveable = false;
		};
		Drag_ID.onselectstart = function (e) {
			return false;
		};
	};
	jQuery("#windown-content").attr("class", "windown-" + cssName);
	var closeWindown = function () {
		jQuery("#windownbg").remove();
		var _version = jQuery.browser.version;
		if (_version == 6) {
			//jQuery("#DivShim").remove();//背景变成白色，盖掉下拉列表框
		}
		jQuery("#windown-box").fadeOut("fast", function () {
			jQuery(this).remove();
		});
	};
	if (time == "" || typeof (time) == "undefined") {
		jQuery("#windown-close").click(function () {
			jQuery("#windownbg").remove();
			var _version = jQuery.browser.version;
			if (_version == 6) {
				//jQuery("#DivShim").remove();//背景变成白色，盖掉下拉列表框
			}
			jQuery("#windown-box").fadeOut("fast", function () {
				jQuery(this).remove();
			});
		});
	} else {
		setTimeout(closeWindown, time);
	}
	if (jQuery("#iframeid").length > 0) {
		jQuery("#iframeid").focus();
	}
}


//弹出类似window.alert的样式的层，但是弹层的形式是异步的
function showAlertDivLayer(msg) {
	if (jQuery("#WebOffice").length > 0) {
		alert1(msg);
		return;
	}
	var img_show = "img_smile";
	var argumentsArr = Array.prototype.slice.call(arguments);
	if (argumentsArr.length == 1) {
		img_show = "img_warm";
	} else {
		if (argumentsArr.length >= 2 && argumentsArr[1] === "fail") {
			img_show = "img_fail";
		}
	}
	var sureClickFunLayerDiv = divLayerClose;
	var height = 120;
	var width = 340;
	var scrollState = "no";
	var clickFun = false;
	if (argumentsArr[2] && typeof (argumentsArr[2]) == "object") {//function
		if (argumentsArr[2]["clkFun"]) {
			sureClickFunLayerDiv = argumentsArr[2]["clkFun"];
			clickFun = true;
		}
		if (argumentsArr[2]["height"]) {
			height = argumentsArr[2]["height"];
		}
		if (argumentsArr[2]["width"]) {
			width = argumentsArr[2]["width"];
		}
		if (argumentsArr[2]["scroll"]) {
			scrollState = argumentsArr[2]["scroll"];
		}
	}
	var magLeft = "109";
	if (msg.length > 30) {
		magLeft = "50";
	}
	var s = "<div id=\"testLayerDivID\">" + "<div class=\"open_prompt\">" + "<table width=\"100%\" border=\"0\" class=\"table01\">" + "<tr>" + "<td width=\"" + magLeft + "\">" + "<div class=\"img " + img_show + "\">" + "</div>" + "</td>" + "<th width=\"220\">" + "<p>" + msg + "</p>" + "</th>" + "</tr>" + "<tr>" + "<td colspan=\"2\" align=\"center\" class=\"btn01\">" + "<input id=\"testLayerDivIDSureBtn\" type=\"button\" class=\"button\" value=\"\u786e \u5b9a\"/>" + "</td>" + "</tr>" + "</table>" + "</div>" + "</div>";
	tipsWindown2("\u4fe1\u606f\u63d0\u793a", "ids:testLayerDivID", width, height, "true", "", true, "id", s, height, scrollState);
	jQuery("#testLayerDivIDSureBtn").click(function () {
		divLayerClose();
		if (clickFun) {
			sureClickFunLayerDiv();
		}
	});
	jQuery("#testLayerDivIDSureBtn").focus();
	 //document.getElementById("testLayerDivIDSureBtn").onclick = sureClickFunLayerDiv;
}
window.alert = showAlertDivLayer;




function divLayerClose() {
	var doc = jQuery(window.document);
	doc.find("#windownbg").remove();
	var _version = jQuery.browser.version;
	if (_version == 6) {
		//doc.find("#DivShim").remove();//背景变成白色，盖掉下拉列表框
	}
	doc.find("#windown-box").fadeOut("fast", function () {
		jQuery(this).remove();
	});
}
