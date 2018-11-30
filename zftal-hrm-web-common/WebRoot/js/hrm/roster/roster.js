/**
 * 条件区域折叠事件
 */
function conditionAreaFoldingEvent(){
	$("div.more--item_bottom a").toggle(
   		function(){
   			$(this).removeClass().addClass("down");
   			$(this).text("更多");
   			$("#total").slideUp();
   		},
   		function(){
   			$(this).removeClass().addClass("up");
   			$(this).text("收起");
   			$("#total").slideDown();
   		}
   	);
}
/**
 * 条件列表悬停事件
 */
function conditionListHoverEvent(){
	$(".datetitle_01 li.ico_xl").hover(function(){
		$(this).parent().next().show();
		$(this).attr("class","sel_dropdown");
	},function(){
		$(this).parent().next().hide();
		$(this).attr("class","ico_xl");
	});
	$(".tosel").hover(function(){
		$(this).show();
		$(this).parent().find(".datetitle_01 li.sel_dropdown").attr("class","sel_dropdown");
	},function(){
		$(this).hide();
		$(this).parent().find(".datetitle_01 li.ico_xl").attr("class","ico_xl");
	});
}
/**
 * 返回按钮事件
 */
function backButtonEvent(){
	$("#back").click(function(){
		location.href = _path+"/summary/roster_page.html";
	});
}
/**
 * 条件选择列表池，选中事件初始化
 */
function configPoolInit(){
	$("ul.sel_con1 li").click(function(){
		var id = $(this).find(":hidden").val();
		if($(this).find("a").attr("class")!="current")
			loadCondition(id);
	});
	//搜索过滤框事件
	$("#tosel>ul button").click(function(){
		var v = $(this).prev().val();
		$("ul.sel_con1 li").show();
		$("ul.sel_con1 a").each(function(){
			if($(this).text().indexOf(v)<0){
				$(this).parent().hide();
			}
		});
	});
}
/**
 * 加载条件
 * @param id
 * @returns {Boolean}
 */
function loadCondition(id){
	var size = 10;
	var num = $("div.prop-item > dl").length;
	if(num>=size){
		alert("已经使用了太多条件，请删选一下，确保条件不多于"+size+"个");
		return false;
	}
	$.post(_path+'/summary/rosterConfig_load.html',"config.guid="+id+"&rosterId="+getRosterId(),function(data){
		if(data.success){
			var html = data.html;
			var content = $(html);
			initSingleConfigHtmlEvent(content);
			$(content).hide();
			$("div.prop-item").append(content);
			$(content).fadeIn();
		}else{
			tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
			$("#window-sure").click(function() {
				divClose();
			});
		}
	},"json");
}
/**
 * 初始化条件配置页面事件，用于条件加载后
 * @param dlHtml
 */
function initSingleConfigHtmlEvent(dlHtml){
	var content = dlHtml;
	var id = $(content).attr("id");
	if($(content).find("dt>span[name='type']").text()=="code"){
		//代码库默认显示5条
		$(content).find("li:gt(4)").hide();
		$(content).find("a.more_down").toggle(
			function(){
				$(this).removeClass().addClass("more_up");
    			$(this).text("收起");
    			$(content).find("li").fadeIn();
			},
			function(){
				$(this).removeClass().addClass("more_down");
    			$(this).text("更多");
    			$(content).find("li:gt(4)").fadeOut();
			}
		);
		$(content).find("li>a").click(function(){
			initItemOperation(this);
		});
	}
	/**
	 * 初始化单个元素操作
	 * @param obj
	 */
	function initItemOperation(obj){
		$(obj).removeClass().addClass("selectedValue");
		createTag(obj);
		$(obj).unbind("click");
	}
	//创建已选条件标签
	function createTag(obj){
		//obj 为A标签
		var tagName = $(obj).text();
		var tagValue = $(obj).prev().text();
		var tagCatalog = $(obj).closest("dl").find("dt>span[name='name']").text();
		var html = "<dd><input type='hidden' name='"+id+"' value='"+tagValue+"'><a href=\"#\"><h5>"+tagCatalog+"</h5>"+tagName+"<span title=\"取消\" class=\"close-icon\"></span></a> </dd>";
		var content = $(html);
		$(content).find("a").click(function(){
			$(obj).removeClass();
			$(obj).click(function(){
				initItemOperation(this);
			});
			$(this).closest("dd").remove();
		});
		$("div.selected-attr>dl").append($(content));
	}
	//条件区域删除按钮
	$(content).find("a.ico_close03").click(function(){
		$.post(_path+'/summary/rosterConfig_cancel.html',"config.guid="+id+"&rosterId="+getRosterId(),function(data){
			if(data.success){
				$(content).fadeOut("slow",function(){
					var id = $(content).attr("id");
					$("ul.sel_con1 li").find(":hidden[value='"+id+"']").next().removeClass();
					$("div.selected-attr input[name='"+id+"']").parent().find("a").click();
					$(content).remove();
				});
			}
		},"json");
	});
	$(content).find("a.ico_close03").hover(
		function(){
			$(this).removeClass().addClass("ico_close02");
		},
		function(){
			$(this).removeClass().addClass("ico_close03");
		}
	);
	
	//选择列表中样式修改为已选中
	$("ul.sel_con1 li").find(":hidden[value='"+id+"']").next().removeClass().addClass("current");
}
/**
 * 获取花名册ID，全局
 * @returns
 */
function getRosterId(){
	var rosterId = $("#guid").val();
	if(rosterId==""){
		throw "no roster id found";
	}
	return rosterId;
}
/**
 * 日历控件初始化
 * @param obj
 * @param format
 */
function dateInit(obj,format){
	var fmt = "yy-mm-dd";
	if(format == "yyyy-MM"){
		fmt = "yy-mm";
	}
	if(format == "yyyy"){
		fmt = "yy";
	}
	$(obj).datepicker({
		changeMonth: true,
		changeYear: true,
		showOtherMonths: true,
		selectOtherMonths: true,
		dateFormat:fmt
	});
	$(obj).removeAttr("onmouseover");
	$(obj).unbind("mouseover");
}
/**
 * 初始化预览事件
 */
function initPreviewEvent(){
	$("#preview").click(function(){
		var param;
		var actionAddr;
		if($("#comp_title ul>li.ha").is("#li-1")){
			param = $("#guid,#query-con1 input,#conditionArea1 input,#dataArea input").serialize();
			actionAddr="/summary/rosterData_query.html";
		}else{
			param = $("#guid,#selectedConds :input,#dataArea input").serialize();
			actionAddr="/summary/rosterData_queryForAdv.html";
		}
		//$.post(_path+'/summary/rosterData_query.html',param,function(data){
		//},"json");
		$.ajax({
			url:_path+actionAddr,
			type:"post",
			data:param,
			cache:false,
			dataType:"html",
			success:successCall
		});
	});
	
	var successCall = function(d){
		try{
    		var data = $.parseJSON(d);
    		if(data.success==false){
    			tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
    			$("#window-sure").click(function() {
					divClose();
				});
    		}
		}catch(e){
			$("#dataArea").empty();
    		$("#dataArea").append(d);
    		var pageSize = $("#pageSize").val();
    		fillRows(pageSize, "", "", false);//填充空行
    		initColumnHeadEvent();
		}
	};
}

/**
 * 初始化保存参数事件
 */
function initSaveQueryEvent(){
	$("#saveQuery").click(function(){
    	var param = $("#guid,#query-con1 input,#conditionArea1 input,#dataArea input").serialize();
		$.post(_path+'/summary/rosterData_saveQuery.html',param,function(data){
			var callback=function(){
				$("#preview").click();
			};
			processDataCall(data,callback);
		},"json");
	});
}

/**
 * 初始化另存为事件
 */
function initSaveOtherEvent(){
	$("#saveOther").click(function(){
		showWindow("另存为",_path+"/summary/roster_saveOther.html","480","230");
	});
}

/**
 * 初始化导出事件
 */
function initExportEvent(){
	$("#export").click(function(){
		//模拟请求一次，用于参数验证
		var param;
		var actionAddr;
		if($("#comp_title ul>li.ha").is("#li-1")){
			param = $("#guid,#query-con1 input,#conditionArea1 input,#dataArea input").serialize();
			actionAddr="/summary/rosterData_export.html";
		}else{
			param = $("#guid,#selectedConds :input,#dataArea input").serialize();
			actionAddr="/summary/rosterData_exportForAdv.html";
		}
		location.href=_path+actionAddr+"?"+param;
		//window.open(_path+"/summary/rosterData_export.html?"+param);
	});
}
/**
 * 初始化字段选择事件
 */
function initConfigEvent(){
	$("#config").click(function(){
		var id = $("input[id='guid']").val();
		var callback = function(){
			$("#preview").click();
		};
		tipsWindown("维护","url:post?"+_path+"/summary/rosterColumn_list.html?rosterId="+id,"420","370","true","","true","id",callback);
	});
}
/**
 * 初始化表头字段列表操作事件
 */
function initColumnHeadEvent(){
	$("div.tab_szcd").hover(
		function(){
			$(this).css("position","relative");
			$(this).find("div").show();
		},
		function(){
			$(this).css("position","");
			$(this).find("div").hide();
		}
	);
	$("div.tab_szcd:first").find("div a.btn_zy").parent().hide();
	$("div.tab_szcd:last").find("div a.btn_yy").parent().hide();
	$("div.tab_szcd").find("div a.btn_zy").click(function(){
		var id = $(this).closest("div").next().val();
		$.post(_path+'/summary/rosterColumn_updateOrder.html',"column.columnId="+id+"&column.rosterId="+getRosterId()+"&type=up",function(data){
			columnOperationCallback(data);
		},"json");
	});
	$("div.tab_szcd").find("div a.btn_yy").click(function(){
		var id = $(this).closest("div").next().val();
		$.post(_path+'/summary/rosterColumn_updateOrder.html',"column.columnId="+id+"&column.rosterId="+getRosterId()+"&type=down",function(data){
			columnOperationCallback(data);
		},"json");
	});
	$("div.tab_szcd").find("div a.btn_sc").click(function(){
		var id = $(this).closest("div").next().val();
		$.post(_path+'/summary/rosterColumn_delete.html',"column.columnId="+id+"&column.rosterId="+getRosterId(),function(data){
			columnOperationCallback(data);
		},"json");
	});
}

function columnOperationCallback(data){
	if(data.success){
		$("#preview").click();
	}else{
		tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
		$("#window-sure").click(function() {
			divClose();
		});
	}
}