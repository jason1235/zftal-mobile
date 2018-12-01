var _lastSelected;//记录前一次选择对象
var max_deep = 2;
$(function(){//界面初始化
	var html = "";
	html +=	"<div class=\"selectbox\" id=\"_codeSelector\" style=\"position:absolute;z-index:2000;\" >";
    html += "<div class=\"tosel\" id=\"inputsel\" style=\"display:none;left:0px;top:0px;width:400px;\">";
    html += "	<ul class=\"sel_search\">";
	html += "		<li></li>";
    html += "    	<li>";
    html += "        	<input style=\"width:200px;\" class=\"text_nor text_nor_rs\" name=\"\" type=\"text\" value=\"\" readonly/>";
	html += "			<button class=\"btn_common\" id=\"confirm\">确定</button>";
    html += "        	<input type=\"hidden\" />";
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
	initSearchLayer();
});

/**
 * 代码选择控件
 * @param obj 控件事件绑定对象 需要为input type=text输入框
 * @param catalogId 代码编目编号
 * @return
 */
function selectConsle(obj,catalogId){
	if(obj==null){
		throw "function need an object to bind"; 
	}
	if(catalogId==null){
		throw "Code catalogid not found";
	}
	var _value = "<input type=\"hidden\" class=\"text_nor\" style=\"width:180px\" value=\"\" />";
    var content;
    //判断代码选择层是否存在
    content = $("#_codeSelector");

    var valueObj;
    //判断代码名称显示框是否存在,id=_codeName 预设ID 做为判断条件
    if($(obj).attr("id")=="_codeName")
    	valueObj = $(obj).next();
    else{
    	if($(obj).siblings("input").length>0)//编辑页面
    		initInput(obj,$(obj).siblings("input"),catalogId);
    	else{//新增页面
	    	valueObj = $(_value);
	    	$(valueObj).insertAfter($(obj));
	    	$(valueObj).attr("name",$(obj).attr("name"));
	    	$(obj).removeAttr("name");
	    	initInput(obj,$(obj).siblings("input"),catalogId);
    	}
    	$(obj).click();
    }
    //$(obj).click();
    

    function nodeInfoEvent(catalogId,obj){//节点选项事件初始化
    	$("#input_sel_con a").click(function(){
    		if($("ul.sel_search a").length>3){
    			alert("父节点信息层次不能深于3级");
    			return false;
    		}
    		var data = $(this).data("data");
    		//var id = $(this).next().val();
    		var id = data.guid;
    		var name = data.title;
    		$("#confirm").prev().val(name);
    		$("#confirm").next().val(id);
    		if(data.children.length==0){
    			
    		}else{
    			createLevelLabel(catalogId,obj,data);//创建选择深度标签
    			loadChild(catalogId,id,obj);
    		}
    		
    	});
    	//页签初始化及事件绑定
    	$("#input_sel_tab li").click(function(){
    		$("#input_sel_tab li").removeClass().addClass("sel_tab2");
    		$("#input_sel_tab li").unbind("mouseenter mouseleave");
    		tabHover();
    		$(this).unbind("mouseenter mouseleave");
    		$(this).removeClass().addClass("sel_tab1");
    		var text = $(this).text().split("~");
    		$("#input_sel_con li").each(function(i){
    			if((i+1)>=text[0]&&(i+1)<=text[1]){
    				$(this).show();
    			}else{
    				$(this).hide();
    			}
    		});
    	});
    	$("#input_sel_tab li").filter(":first").click();
    	
    	function tabHover(){
        	$("#input_sel_tab li").hover(
        		function(){
        			$(this).removeClass().addClass("sel_tab1");
        		},	
        		function(){
        			$(this).removeClass().addClass("sel_tab2");
        		}
        	);
        }
    }
    
    function createLevelLabel(catalogId,obj,data){
		var labelArea = $("ul.sel_search li:first");
		var nodeSize = parseInt($("ul.sel_search a").length)+1;
		//var name = $("#confirm").prev().val();
		//var id = $("#confirm").next().val();
		var name='';
		var id='';
		if(data != null){
			name = data.title;
			id = data.guid;
		}
		if(nodeSize>1){
			labelArea.append("<span> - </span>");
		}else{
			//name = $(obj).data("catalog").name;受异步影响，可能为NULL
			//name = "数据加载中请稍后...";
			data.title;
		}
		labelArea.append("<a style=\"cursor:pointer;\" name=\""+id+"\" alt=\""+nodeSize+"\">"+name+"</a>");
		labelArea.find("a:last").data("data",data);
		labelArea.find("a:last").click(function(){
			//var label = $("ul.sel_search a[alt='"+nodeSize+"']");
			var data = $(this).data("data");
			if(nodeSize!=1){
				$("#confirm").prev().val($(this).text());
				$("#confirm").next().val($(this).attr("name"));
			}
			else{
				$("#confirm").prev().val("");
				$("#confirm").next().val(data.guid);
			}
			$(this).nextAll().remove();
			loadChild(catalogId,id,obj);
		});
	}
    
    function loadChild(catalogId,parentId,obj, level){
    	if(parentId == ''){
	    	var cache = $("body").data(catalogId);
	    	if(cache != null){
	    		dataProcess(cache);
	    		return false;
	    	}
    	}
    	$("#windown-content").unbind("ajaxStart");
    	var type = $("#form1 input[name='query.type']").val();
    	$.post(_path+'/baseinfo/conditionDefined_getChildren.html',"query.type="+type+"&query.parentId="+parentId,function(data){
			dataProcess(data);
			if(parentId == ''){
				$("body").data(catalogId,data);
			}
    	},"json");
    	
    	function dataProcess(data){
    		if(data.success&&data.result.length>0){
				//if($(obj).data("catalog")==null){
				//	$(obj).data("catalog",data.catalog);
				//}
				//$("ul.sel_search li:first").find("a:first").text(data.catalog.name);//异步更新部分节点显示信息
				//$("ul.sel_search li:first").find("a:first").text("test");//异步更新部分节点显示信息
				createNodePanel(data);
				nodeInfoEvent(catalogId,obj);
    		}
    		if(data.success&&data.result.length==0){
    			$("ul.sel_search li:first").empty();
    			$("ul.sel_search li:first").append("<span>无数据，请检查代码库配置</span>");
    		}
    		if(!data.success){
    			$("ul.sel_search li:first").empty();
    			$("ul.sel_search li:first").append("<font color=\"red\">查询发生了异常，请检查参数及代码库配置</font>");
    		}
    		showPanel(obj);
    	}
    	
    	function createNodePanel(data){
    		clearContent();
			var cnt=0;
			var pageSize=40;
			var totalSize = data.result.length;
			var tabHtml=$("<ul class=\"sel_tab\" id=\"input_sel_tab\"></ul>");
			var nodeHtml=$("<ul class=\"sel_con\" id=\"input_sel_con\"></ul>");
    		$.each(data.result,function(i,n){
				if(n.visible==0){
					return false;
				}
				createViewTab(cnt,pageSize);
				createNodeItem(n);
				cnt++;
			});
    		$("#input_sel_tab").replaceWith(tabHtml);
    		$("#input_sel_con").replaceWith(nodeHtml);
    		
    		function createViewTab(cnt,pageSize){
    			var html="";
    			if(cnt%pageSize==0&&totalSize>pageSize){
					if(cnt==0)
						html = "<li class=\"sel_tab1\" >"+(cnt+1)+"~"+(cnt+pageSize)+"</li>";
					else
						html = "<li class=\"sel_tab2\" >"+(cnt+1)+"~"+(cnt+pageSize)+"</li>";
					//$("#input_sel_tab").append(html);
					$(tabHtml).append(html);
				}
    		}
    		function createNodeItem(node){
    			var html="";
    			html = "<li><a style=\"cursor:pointer;\">"+node.title+"</a></li>";
				$(nodeHtml).append($(html));
				var v = $(nodeHtml).find("a:last");
				v.data("data",node);
				
				function appendTitle(obj,text){
					var title = obj.attr("title");
					obj.attr("title",title!=null?(obj.attr("title")+","+text):text);
				}
    		}
    	}
    }
    
    function clearContent(){
		$("#input_sel_con a").removeData("data");
		$("#input_sel_tab").empty();
		$("#input_sel_con").empty();
	}
    
    function initInput(obj,valueObj,catalogId){//初始化表单控件
    	$(obj).removeAttr("onclick");
    	$(obj).unbind("click");
    	//$(obj).attr("readOnly","readOnly");
    	$(obj).focus(function(){
    		$(this).blur();
    	});
    	$(obj).attr("id","_codeName");
    	$(obj).click(function(e){
    		bindModuleEvent(obj,valueObj,catalogId);
    		e.stopPropagation();
    	});
    	
    	function bindModuleEvent(obj,valueObj,catalogId){
    		//if($(_lastSelected).attr("name") != $(valueObj).attr("name")){
    			$("ul.sel_search li:first").empty();
    			$("#confirm").siblings("input").val("");
    			clearContent();//清除上次加载数据
    			var data = new Object();
    			data.guid = $("#form1 input[name='query.parentId']").val();
    			data.title = $("#form1 input[id='root.title']").val();
    			createLevelLabel(catalogId,obj,data);//创建层次等级标签
    			var p = $("#form1 input[name='query.parentId']").val();
    			loadChild(catalogId,p,obj, 1);//加载代码选择面板
    			
    			confirmButtonEvent(obj);
    	}
    	
    	function confirmButtonEvent(obj){
    		$("#confirm").unbind("click");//重新绑定选择层确认按钮
			$("#confirm").click(function(e){
				//$(valueObj).val($(this).next().val());
				$(obj).siblings(":hidden").val($(this).next().val());
				//var catalog = $(obj).data("catalog");
//				if(catalog.includeParentNode==1){
//					var delimiter = " ";
//					if(catalog.delimiter != null){
//						delimiter = catalog.delimiter;
//					}
//					var viewName="";
//					$("ul.sel_search a:not(:first)").each(function(){
//						if(viewName.length>0){
//							viewName += delimiter;
//						}
//						viewName += $(this).text();
//					});
//					if($("ul.sel_search a:not(:first)").last().text()!=$(this).prev().val()){//追加叶节点名称
//						if(viewName.length>0){
//							viewName += delimiter;
//						}
//						viewName += $(this).prev().val();
//					}
//					$(obj).val(viewName);
//				}else{
					$(obj).val($(this).prev().val());
				//}
				$("#inputsel").hide();
			});
    	}
    }
    
    /**
     * 显示选择面板并且重新定位坐标
     * @param obj
     * @return
     */
    function showPanel(obj){
    	var left = $(obj).offset().left;
    	if(left+415>$(document).width()){
    		left = left+$(obj).width()-415;
    	}
    	$("#_codeSelector").css("left",left);
    	$("#_codeSelector").css("top",$(obj).offset().top+$(obj).height());
    	if($("#inputsel").is(":hidden")){
    		$("#inputsel").slideDown("fast");
    	}
    }
    
}
function initSearchLayer(){
	$("#inputsel").click(function(e){
		e.stopPropagation();
	});
	$("body").click(function(e){
		if($("#inputsel").is(":visible"))
			$("#inputsel").hide();
	});
}

