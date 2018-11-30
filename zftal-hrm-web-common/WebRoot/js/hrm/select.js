
/**
 * 选择控件
 * @param obj 控件事件绑定对象 需要为input type=text输入框
 * @param catalogId 代码编目编号
 * @return
 */
function initSelectConsole(obj,url){
	if(obj==null){
		throw "function need an object to bind"; 
	}
	if(url==null){
		throw "request url not found";
	}
    var consoleName = $(obj).siblings().attr("name");
    var domc = $("div.selectbox[id='"+consoleName+"']");
    createPanel(consoleName);
    initEvent(obj);
    
    function createPanel(consoleName){
    	if($(domc).length>0){
    		bindNodeEvent(obj);
    		return;
    	}
    	var html = "";
    	html +=	"<div class=\"selectbox\" id=\""+consoleName+"\" style=\"position:absolute;z-index:2000;\" >";
        html += "<div class=\"tosel\" id=\"inputsel\" style=\"display:none;left:0px;top:0px;width:400px;\">";
        html += "	<ul class=\"sel_search\">";
    	html += "		<li></li>";
        html += "    	<li>";
        html += "        	<input style=\"width:200px;\" class=\"text_nor text_nor_rs\" id=\"searchValue\" type=\"text\" value=\"\" />";
    	html += "			<button class=\"btn_common\" id=\"search\">搜索</button>";
    	html += "			<button class=\"btn_common\" id=\"close\">关闭</button>";
        html += "        </li>";
        html += "    </ul>";
        html += "    <ul class=\"sel_tab\" id=\"input_sel_tab\"></ul>";
        html += "    <ul class=\"sel_con\" id=\"input_sel_con\">";
        html += "    </ul>";
        html += "</div>";
        html += "</div>";
        var content;
    	content = $(html);
    	$(content).appendTo($("body"));
    	domc = $("div.selectbox[id='"+consoleName+"']");
    	loadData(consoleName,url);
    	bindSearchButtonEvent();
    	bindCloseButtonEvent();
    	initSearchLayer();
    }
    
    function loadData(consoleName,url){
    	$("#windown-content").unbind("ajaxStart");
    	$.post(_path+url,"",function(data){
			dataProcess(data,consoleName);
    	},"json");
    }
    
    function dataProcess(data,consoleName){
    	if(data.success){
    		createNodePanel(data,consoleName);
    		bindNodeEvent(obj);
    		createTabPanel(consoleName);
    		bindTabEvent();
    	}else{
    		$("ul.sel_search li:first",$(domc)).empty();
			$("ul.sel_search li:first",$(domc)).append("<font color=\"red\">查询发生了异常，请检查参数及代码库配置</font>");
    	}
    }
    
    function createNodePanel(data,consoleName){
		var nodeHtml=$("<ul class=\"sel_con\" id=\"input_sel_con\">");
		$.each(data.result,function(i,n){
			createNodeItem(n);
		});
		$("#input_sel_con",$(domc)).replaceWith(nodeHtml);
		
		function createNodeItem(node){
			var html="<li style='height:12px;'><a style=\"cursor:pointer;\">"+node.name+"</a></li>";
			$(nodeHtml).append($(html));
			var v = $(nodeHtml).find("a:last");
			appendTitle(v,node.value);
			
			function appendTitle(obj,text){
				var title = obj.attr("title");
				obj.attr("title",title!=null?(obj.attr("title")+","+text):text);
			}
		}
	}
    
    function bindNodeEvent(obj){//节点选项事件初始化
    	$("#input_sel_con a",$(domc)).unbind("click");
    	$("#input_sel_con a",$(domc)).click(function(){
    		//var id = $(this).next().val();
    		var value = $(this).attr("title");
    		var name = $(this).text();
    		$(obj).val(name);
    		$(obj).siblings().val(value);
    	});
    }
    
    function createTabPanel(consoleName){
    	var cnt=0;
		var pageSize=20;
		var data = $("#input_sel_con li[filter!='true']",$(domc));
		var totalSize = $(data).length;
    	var tabHtml=$("<ul class=\"sel_tab\" id=\"input_sel_tab\"></ul>");
    	$.each(data,function(i,n){
			createViewTab(cnt,pageSize);
			cnt++;
		});
		$("#input_sel_tab",$(domc)).replaceWith(tabHtml);
		
		function createViewTab(cnt,pageSize){
			var html="";
			if(cnt%pageSize==0&&totalSize>pageSize){
				if(cnt==0)
					html = "<li class=\"sel_tab1\" >"+(cnt+1)+"~"+(cnt+pageSize)+"</li>";
				else
					html = "<li class=\"sel_tab2\" >"+(cnt+1)+"~"+(cnt+pageSize)+"</li>";
				$(tabHtml).append(html);
			}
		}
    }
    
    function bindTabEvent(){
    	//页签初始化及事件绑定
    	$("#input_sel_tab li",$(domc)).click(function(){
    		$("#input_sel_tab li",$(domc)).removeClass().addClass("sel_tab2");
    		$("#input_sel_tab li",$(domc)).unbind("mouseenter mouseleave");
    		tabHover();
    		$(this).unbind("mouseenter mouseleave");
    		$(this).removeClass().addClass("sel_tab1");
    		var text = $(this).text().split("~");
    		$("#input_sel_con li[filter!='true']",$(domc)).each(function(i){
    			if((i+1)>=text[0]&&(i+1)<=text[1]){
    				$(this).show();
    			}else{
    				$(this).hide();
    			}
    		});
    	});
    	$("#input_sel_tab li:first",$(domc)).click();
    	
    	function tabHover(){
        	$("#input_sel_tab li",$(domc)).hover(
        		function(){
        			$(this).removeClass().addClass("sel_tab1");
        		},	
        		function(){
        			$(this).removeClass().addClass("sel_tab2");
        		}
        	);
        }
    }
    
    function initEvent(obj){//初始化表单控件
    	$(obj).removeAttr("onmouseover");
    	$(obj).unbind("mouseover");
    	$(obj).focus(function(){
    		$(this).blur();
    	});
    	$(obj).click(function(e){
    		showPanel(obj,consoleName);
    		e.stopPropagation();
    	});
    }
    
    function bindSearchButtonEvent(){
    	$("#searchValue",$(domc)).keydown(function(e){
    		if(e.keyCode == 13){
    			$("#search",$(domc)).click();
    		}
    	});
		$("#search",$(domc)).click(function(){
			var v = $(this).prev().val();
			if(v == ''){
				$("#input_sel_con li",$(domc)).removeAttr("filter");
				$("#input_sel_con li",$(domc)).show();
			}else{
				$("#input_sel_con li",$(domc)).each(function(){
					var txt =$(this).find("a").text();
					if(txt.indexOf(v)>-1){
						$(this).show();
						$(this).removeAttr("filter");
					}else{
						$(this).hide();
						$(this).attr("filter","true");
					}
				});
			}
			createTabPanel();
    		bindTabEvent();
		});
	}
    
    function bindCloseButtonEvent(){
		$("div[id='"+consoleName+"'] #close").click(function(){
			if($("#inputsel",$(domc)).is(":visible"))
				$("#inputsel",$(domc)).hide();
		});
	}
    /**
     * 显示选择面板并且重新定位坐标
     * @param obj
     * @return
     */
    function showPanel(obj,consoleName){
    	var left = $(obj).offset().left;
    	if(left+415>$(document).width()){
    		left = left+$(obj).width()-415;
    	}
    	$(domc).css("left",left);
    	$(domc).css("top",$(obj).offset().top+$(obj).height());
    	if($("#inputsel",$(domc)).is(":hidden")){
    		$("#inputsel",$(domc)).slideDown("fast");
    	}
    }
    
    function initSearchLayer(){
    	$("#inputsel",$(domc)).click(function(e){
    		e.stopPropagation();
    	});
    	$("body").click(function(e){
    		if($("#inputsel",$(domc)).is(":visible"))
    			$("#inputsel",$(domc)).hide();
    	});
    }
}


