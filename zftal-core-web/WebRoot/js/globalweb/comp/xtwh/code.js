var _lastSelected;//记录前一次选择对象
jQuery(function(){//界面初始化
	var html = "";
	html +=	"<div class=\"selectbox\" id=\"_codeSelector\" style=\"position:absolute;z-index:12000;\" >";
    html += "<div class=\"tosel\" id=\"inputsel\" style=\"display:none;left:0px;top:0px;width:400px;\">";
    html += "	<ul class=\"sel_search\">";
	html += "		<li></li>";
    html += "    	<li>";
    html += "        	<input style=\"width:200px;\" class=\"text_nor text_nor_rs\" name=\"\" type=\"text\" value=\"\" readonly/>";
	html += "			<button class=\"btn_common\" id=\"confirm\">确定</button>";
	html += "        	<input type=\"hidden\" />";
	html += "			<button class=\"btn_common\" id=\"close\">关闭</button>";
    html += "        </li>";
    html += "    </ul>";
    html += "    <ul class=\"sel_tab\" id=\"input_sel_tab\"></ul>";
    html += "    <ul class=\"sel_con\" id=\"input_sel_con\">";
    html += "    </ul>";
    html += "</div>";
    html += "</div>";
    var content;
	content = jQuery(html);
	jQuery(content).appendTo(jQuery("body"));
	initSearchLayer();
});

/**
 * 代码选择控件
 * @param obj 控件事件绑定对象 需要为input type=text输入框
 * @param catalogId 代码编目编号
 * @return
 */
function codePicker(obj,catalogId){
	if(obj==null){
		throw "function need an object to bind"; 
	}
	if(catalogId==null){
		throw "Code catalogid not found";
	}
	var _value = "<input type=\"hidden\" class=\"text_nor\" style=\"width:180px\" value=\"\" />";
    var content;
    //判断代码选择层是否存在
    //if(jQuery("#_codeSelector").length>0)
    	content = jQuery("#_codeSelector");
    //else{
    //	content = jQuery(html);
    //	jQuery(content).appendTo(jQuery("body"));
    //	initSearchLayer();
    //}
    var valueObj;
    //判断代码名称显示框是否存在,id=_codeName 预设ID 做为判断条件
    if(jQuery(obj).attr("id")=="_codeName")
    	valueObj = jQuery(obj).next();
    else{
    	if(jQuery(obj).siblings("input").length>0)//编辑页面
    		initInput(obj,jQuery(obj).siblings("input"),catalogId);
    	else{//新增页面
	    	valueObj = jQuery(_value);
	    	jQuery(valueObj).insertAfter(jQuery(obj));
	    	jQuery(valueObj).attr("name",jQuery(obj).attr("name"));
	    	jQuery(obj).removeAttr("name");
	    	initInput(obj,jQuery(obj).siblings("input"),catalogId);
    	}
    	jQuery(obj).click();
    }
    //jQuery(obj).click();
    

    function nodeInfoEvent(catalogId,obj){//节点选项事件初始化
    	jQuery("#input_sel_con a").click(function(){
    		if(jQuery("ul.sel_search a").length>3){
    			alert("父节点信息层次不能深于3级");
    			return false;
    		}
    		var data = jQuery(this).data("data");
    		//var id = jQuery(this).next().val();
    		var id = data.guid;
    		var name = data.description;
    		if(data.checked==1 && data.dumped==0){//当节点可选中并且不废弃时，更新选中框数据显示
	    		jQuery("#confirm").prev().val(name);
	    		jQuery("#confirm").next().val(id);
    		}
    		if(data.children.length==0){
    			
    		}else{
    			createLevelLabel(catalogId,obj,data);//创建选择深度标签
    			loadChild(catalogId,id,obj);
    		}
    		
    	});
    	//页签初始化及事件绑定
    	jQuery("#input_sel_tab li").click(function(){
    		jQuery("#input_sel_tab li").removeClass().addClass("sel_tab2");
    		jQuery("#input_sel_tab li").unbind("mouseenter mouseleave");
    		tabHover();
    		jQuery(this).unbind("mouseenter mouseleave");
    		jQuery(this).removeClass().addClass("sel_tab1");
    		var text = jQuery(this).text().split("~");
    		jQuery("#input_sel_con li").each(function(i){
    			if((i+1)>=text[0]&&(i+1)<=text[1]){
    				jQuery(this).show();
    			}else{
    				jQuery(this).hide();
    			}
    		});
    	});
    	jQuery("#input_sel_tab li").filter(":first").click();
    	
    	function tabHover(){
        	jQuery("#input_sel_tab li").hover(
        		function(){
        			jQuery(this).removeClass().addClass("sel_tab1");
        		},	
        		function(){
        			jQuery(this).removeClass().addClass("sel_tab2");
        		}
        	);
        }
    }
    
    function createLevelLabel(catalogId,obj,data){
		var labelArea = jQuery("ul.sel_search li:first");
		var nodeSize = parseInt(jQuery("ul.sel_search a").length)+1;
		//var name = jQuery("#confirm").prev().val();
		//var id = jQuery("#confirm").next().val();
		var name='';
		var id='';
		if(data != null){
			name = data.description;
			id = data.guid;
		}
		if(nodeSize>1){
			labelArea.append("<span> - </span>");
		}else{
			//name = jQuery(obj).data("catalog").name;受异步影响，可能为NULL
			name = "数据加载中请稍后...";
		}
		labelArea.append("<a style=\"cursor:pointer;\" name=\""+id+"\" alt=\""+nodeSize+"\">"+name+"</a>");
		labelArea.find("a:last").data("data",data);
		labelArea.find("a:last").click(function(){
			//var label = jQuery("ul.sel_search a[alt='"+nodeSize+"']");
			var data = jQuery(this).data("data");
			if(nodeSize!=1){
				if(data.checked==0){//判断节点是否可选中
					jQuery("#confirm").prev().val("");
					jQuery("#confirm").next().val("");
				}else{
					jQuery("#confirm").prev().val(jQuery(this).text());
					jQuery("#confirm").next().val(jQuery(this).attr("name"));
				}
			}
			else{
				jQuery("#confirm").prev().val("");
				jQuery("#confirm").next().val("");
			}
			jQuery(this).nextAll().remove();
			loadChild(catalogId,id,obj);
		});
	}
    
    function loadChild(catalogId,parentId,obj, level){
    	if(parentId == ''){
	    	var cache = jQuery("body").data(catalogId);
	    	if(cache != null){
	    		dataProcess(cache);
	    		return false;
	    	}
    	}
    	jQuery("#windown-content").unbind("ajaxStart");
    	jQuery.post(_path+'/code/codeItem_getChildren.html',"catalogId="+catalogId+"&parentId="+parentId,function(data){
			dataProcess(data);
			if(parentId == ''){
				jQuery("body").data(catalogId,data);
			}
    	},"json");
    	
    	function dataProcess(data){
    		if(data.success&&data.result.length>0){
				if(jQuery(obj).data("catalog")==null){
					jQuery(obj).data("catalog",data.catalog);
				}
				jQuery("ul.sel_search li:first").find("a:first").text(data.catalog.name);//异步更新部分节点显示信息
				createNodePanel(data);
				nodeInfoEvent(catalogId,obj);
    		}
    		if(data.success&&data.result.length==0){
    			jQuery("ul.sel_search li:first").empty();
    			jQuery("ul.sel_search li:first").append("<span>无数据，请检查代码库配置</span>");
    		}
    		if(!data.success){
    			jQuery("ul.sel_search li:first").empty();
    			jQuery("ul.sel_search li:first").append("<font color=\"red\">查询发生了异常，请检查参数及代码库配置</font>");
    		}
    		showPanel(obj);
    	}
    	
    	function createNodePanel(data){
    		clearContent();
			var cnt=0;
			var pageSize=40;
			var totalSize = data.result.length;
			var tabHtml=jQuery("<ul class=\"sel_tab\" id=\"input_sel_tab\"></ul>");
			var nodeHtml=jQuery("<ul class=\"sel_con\" id=\"input_sel_con\">");
    		jQuery.each(data.result,function(i,n){
				if(n.visible==0){
					
				}else{
					createNodeItem(n);
				}
				createViewTab(cnt,pageSize);
				cnt++;
			});
    		jQuery("#input_sel_tab").replaceWith(tabHtml);
    		jQuery("#input_sel_con").replaceWith(nodeHtml);
    		
    		function createViewTab(cnt,pageSize){
    			var html="";
    			if(cnt%pageSize==0&&totalSize>pageSize){
					if(cnt==0)
						html = "<li class=\"sel_tab1\" >"+(cnt+1)+"~"+(cnt+pageSize)+"</li>";
					else
						html = "<li class=\"sel_tab2\" >"+(cnt+1)+"~"+(cnt+pageSize)+"</li>";
					//jQuery("#input_sel_tab").append(html);
					jQuery(tabHtml).append(html);
				}
    		}
    		function createNodeItem(node){
    			var html="";
    			if(node.dumped==0){
					//jQuery("#input_sel_con").append("<li><a style=\"cursor:pointer;\">"+node.description+"</a></li>");
    				html = "<li><a style=\"cursor:pointer;\">"+node.description+"</a></li>";
    			}
				if(node.dumped==1){//如果条目废弃，显示红色
					//jQuery("#input_sel_con").append("<li><a style=\"cursor:pointer;\" title=\"已废弃\"><font color=\"red\">"+node.description+"</font></a></li>");
					html = "<li><a style=\"cursor:pointer;\" title=\"已废弃\"><font color=\"red\">"+node.description+"</font></a></li>";
				}
				//var v = jQuery("#input_sel_con").find(":last-child>a");
				jQuery(nodeHtml).append(jQuery(html));
				var v = jQuery(nodeHtml).find("a:last");
				if(node.checked==0){
					//appendTitle(v,"不可选中");
				}
				//if(node.children.length==0){
					appendTitle(v,node.description+"["+node.guid+"]");
    			//}
				v.data("data",node);
				
				function appendTitle(obj,text){
					var title = obj.attr("title");
					obj.attr("title",title!=null?(obj.attr("title")+","+text):text);
				}
    		}
    	}
    }
    
    function clearContent(){
		jQuery("#input_sel_con a").removeData("data");
		jQuery("#input_sel_tab").empty();
		jQuery("#input_sel_con").empty();
	}
    
    function initInput(obj,valueObj,catalogId){//初始化表单控件
    	jQuery(obj).removeAttr("onclick");
    	jQuery(obj).unbind("click");
    	//jQuery(obj).attr("readOnly","readOnly");
    	jQuery(obj).focus(function(){
    		jQuery(this).blur();
    	});
    	jQuery(obj).attr("id","_codeName");
    	jQuery(obj).click(function(e){
    		bindModuleEvent(obj,valueObj,catalogId);
    		e.stopPropagation();
    	});
    	
    	function bindModuleEvent(obj,valueObj,catalogId){
    		//if(jQuery(_lastSelected).attr("name") != jQuery(valueObj).attr("name")){
    			jQuery("ul.sel_search li:first").empty();
    			jQuery("#confirm").siblings("input").val("");
    			clearContent();//清除上次加载数据
    			createLevelLabel(catalogId,obj,null);//创建层次等级标签
    			loadChild(catalogId,'',obj, 1);//加载代码选择面板
    			
    			confirmButtonEvent(obj);
    			bindCloseButtonEvent();
    		//}else{
	    	//	showPanel(obj);
    		//}
    		//_lastSelected = jQuery(valueObj).clone();
    	}
    	
    	function confirmButtonEvent(obj){
    		jQuery("#confirm").unbind("click");//重新绑定选择层确认按钮
			jQuery("#confirm").click(function(e){
				//jQuery(valueObj).val(jQuery(this).next().val());
				jQuery(obj).siblings(":hidden").val(jQuery(this).next().val());
				var catalog = jQuery(obj).data("catalog");
				if(catalog.includeParentNode==1){
					var delimiter = " ";
					if(catalog.delimiter != null){
						delimiter = catalog.delimiter;
					}
					var viewName="";
					jQuery("ul.sel_search a:not(:first)").each(function(){
						if(viewName.length>0){
							viewName += delimiter;
						}
						viewName += jQuery(this).text();
					});
					if(jQuery("ul.sel_search a:not(:first)").last().text()!=jQuery(this).prev().val()){//追加叶节点名称
						if(viewName.length>0){
							viewName += delimiter;
						}
						if(jQuery(this).prev().val().length==0){
							viewName="";
						}else{
							viewName += jQuery(this).prev().val();
						}
					}
					jQuery(obj).val(viewName);
				}else{
					jQuery(obj).val(jQuery(this).prev().val());
				}
				jQuery("#_codeSelector>#inputsel").hide();
			});
    	}
    	
    	function bindCloseButtonEvent(){
    		jQuery("#close").click(function(){
    			if(jQuery("#_codeSelector>#inputsel").is(":visible"))
    				jQuery("#_codeSelector>#inputsel").hide();
    		});
    	}
    }
    
    /**
     * 显示选择面板并且重新定位坐标
     * @param obj
     * @return
     */
    function showPanel(obj){
    	var left = jQuery(obj).offset().left;
    	if(left+415>jQuery(document).width()){
    		left = left+jQuery(obj).width()-415;
    	}
    	jQuery("#_codeSelector").css("left",left);
    	jQuery("#_codeSelector").css("top",jQuery(obj).offset().top+jQuery(obj).height());
    	if(jQuery("#_codeSelector>#inputsel").is(":hidden")){
    		jQuery("#_codeSelector>#inputsel").slideDown("fast");
    	}
    }
    
}
function initSearchLayer(){
	jQuery("#_codeSelector>#inputsel").click(function(e){
		e.stopPropagation();
	});
	jQuery("body").click(function(e){
		if(jQuery("#_codeSelector>#inputsel").is(":visible"))
			jQuery("#_codeSelector>#inputsel").hide();
	});
}

