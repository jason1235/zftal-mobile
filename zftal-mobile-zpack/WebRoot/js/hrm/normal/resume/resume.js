/**
 * 简历页面脚本对象
 * @return
 */
function loadDataByCatalog(callback){
	var catalogId = getCatalogId();
	var param = "gh="+getPersonGh()+"&catalogId="+catalogId+"&history="+getHistory()+"&editable="+getEditable(); 
	$.post(_path + '/normal/staffResume_loadInfoByCatalog.html',param,function(data){
		if( !data.success ) {
			alert(data.title);
		}else{
			$("#infoContent").html(data.html);
			bindViewPageEvent($("#infoContent"));
			$(".list_xxxx li").each(function(){
				var v = $(this).attr("id");
				if(!!v){
					if(v==catalogId){
						$(".list_xxxx li>a").removeClass("selected");
						$(this).children("a").addClass("selected");
					}
				}
			});
			if(callback != null){
				callback();
			}
		}
	},"json");
}

/**
 * 简历页面脚本对象(点子页面)
 * @return
 */
function loadDataByCatalogAndClzId(clzid,callback){
	var obj = $("span.history")[0];
	$("#history").val("true");
	$(obj).addClass("on");
	$(obj).text("已显示完整记录");
	var catalogId = getCatalogId();
	($(window.parent.document).find("#xg_rightFrame"))[0].height=500;
	var param = "gh="+getPersonGh()+"&catalogId="+catalogId+"&history="+getHistory()+"&editable="+getEditable()+"&clzid="+clzid; 
	$.post(_path + '/normal/staffResume_loadInfoByCatalog.html',param,function(data){
		if( !data.success ) {
			alert(data.title);
		}else{
			$("#infoContent").html(data.html);
			bindViewPageEvent($("#infoContent"));
			$(".list_xxxx li").each(function(){
				var v = $(this).attr("id");
				if(v==catalogId){
					$(".list_xxxx li>a").removeClass("selected");
					$(this).children("a").addClass("selected");
				}
			});
			if(callback != null){
				callback();
			}
		}
	},"json");
}

function loadDataByClass(target){
	var classId = getCurrentRecordClassid(target);
	var param = "gh="+getPersonGh()+"&classId="+classId+"&history="+getHistory()+"&editable="+getEditable();
	$.post(_path + '/normal/staffResume_loadInfoByClass.html',param,function(data){
		if( !data.success ) {
			alert(data.title);
		}else{
			//回调处理,删除原类信息展示块，追加更新后的信息
			var doc = $(target).closest("div.demo_xxxx");
			var newDoc = $(data.html);
			$(doc).before(newDoc);
			$(doc).remove();
			bindViewPageEvent(newDoc);
		}
	},"json");
}

function bindViewPageEvent(doc){
	var tr = $("tr[name='infoclass']");
	tr.click(function(){
		$(this).closest("div[name='record']")
			.find("input[name='globalid']").val($(this).attr('globalid'));
		$(tr).removeClass("current");
		$(this).addClass("current");
	});
	
	
	
	var editOperate = $("li[class='btn_xxxx_bj']>a",$(doc));
	$(editOperate).click(function(){
		editRequest(this);
	});
	var deleteOperate = $("li[class='btn_xxxx_sc']>a",$(doc));
	$(deleteOperate).click(function(){
		var obj = this;
		showConfirm("确定要删除该数据吗");
		$("#why_sure").click(function(){
			deleteRequest(obj);
		});
		$("#why_cancel").click(function(){
			alertDivClose();
		});
	});
	var addOperate = $("div.demo_add_02>a",$(doc));
	$(addOperate).click(function(){
		inputRequest(this);
	});
	$("h3.college_title a",$(doc)).click(function(e){//展开按钮
		var title = $(this).closest("div[class='demo_xxxx']");
		if($(this).attr("class")=='up'){
			$(title).find("div[name='clazz']").hide();
			$(this).attr("class","down");
			$(this).text("展开");
		}else{
			$(title).find("div[name='clazz']").show();
			$(this).attr("class","up");
			$(this).text("收起");
		}
	});
}

function bindEditPageEvent(doc){
	var saveOperate = $("li[class='btn_xxxx_bc']>a",$(doc));
	$(saveOperate).click(function(){
		updateRequest(this);
	});
	var cancelOperate = $("li[class='btn_xxxx_cx']>a",$(doc));
	$(cancelOperate).click(function(){
		loadDataByClass(this);
	});
}

function bindSavePageEvent(doc){
	var saveOperate = $("li[class='btn_xxxx_bc']>a",$(doc));
	$(saveOperate).click(function(){
		saveRequest(this);
		//alert("保存");
	});
	var cancelOperate = $("li[class='btn_xxxx_cx']>a",$(doc));
	$(cancelOperate).click(function(){
		$(this).closest("div[name='clazz']").find("div.demo_add_02").show();
		$(this).closest("div[name='record']").remove();
	});
}

function editRequest(target){
	var classId = getCurrentRecordClassid(target);
	var globalid = getCurrentRecordId(target);
	var param = "classId="+classId+"&globalid="+globalid;
	$.post(_path + '/normal/staffResume_editInfoById.html',param,function(data){
		if( !data.success ) {
			alert(data.text);
		}else{
			var doc = $(target).closest("div[name='record']");
			$(doc).html(data.html);
			$(doc).find("tr td").children().filter(":radio").each(function(){//处理多条同类数据中，同名radio控件的默认选择问题
	    		var _self_name = $(this).attr("name")+"_"+globalid;
	    		$(this).attr("name",_self_name);
	    	});
			bindEditPageEvent(doc);
		}
	},"json");
}

function updateRequest(target){
	$(target).closest("div[name='record']").find("tr td").children().filter(":radio").each(function(){//恢复radio name 使之正确提交
		var _self_name = $(this).attr("name");
		if(_self_name.indexOf("_")>0){
			_self_name = _self_name.substring(0,_self_name.indexOf("_"));
			$(this).attr("name",_self_name);
		}
	});
	var classId = getCurrentRecordClassid(target);
	//var globalid = getCurrentRecordId(target);
	var param = $(target).closest("div[name='record']").find("input").serialize();
	param += "&" + $(target).closest("div[name='record']").find("textarea").serialize();
	param += "&" + $(target).closest("div[name='record']").find("select").serialize();
	param = param + "&classId="+classId;
	$.post(_path + '/normal/staffResume_update.html',param,function(data){
		var callback = function(){
			//var doc = $(target).closest("div[name='record']");
			//viewRequest(target);
			loadDataByClass(target);
		};
		processDataCall(data,callback);
	},"json");
}

function viewRequest(target){
	var classId = getCurrentRecordClassid(target);
	var globalid = getCurrentRecordId(target);
	var param = "classId="+classId+"&globalid="+globalid+"&editable="+getEditable()+"gh="+getPersonGh()+"history="+getHistory();
	$.post(_path + '/normal/staffResume_loadInfoById.html',param,function(data){
		if( !data.success ) {
			alert(data.text);
		}else{
			var doc = $(target).closest("div[name='record']");
			$(doc).html(data.html);
			bindViewPageEvent(doc);
		}
	},"json");
}

function inputRequest(target){
	var classId = getCurrentRecordClassid(target);
	var param = "classId="+classId+"&editable="+getEditable();
	$.post(_path + '/normal/staffResume_input.html',param,function(data){
		if( !data.success ) {
			alert(data.text);
		}else{
			//回调处理，删除添加按钮，追加新增编辑区域，并绑定事件
			var doc = $(target).closest("div.demo_add_02");
			var newDoc = $("<div name='record'>"+data.html+"</div>");
			$(newDoc).find("input[name='gh']").val(getPersonGh());
			$(newDoc).find("tr td").children().filter(":radio").each(function(){//处理多条同类数据中，同名radio控件的默认选择问题
	    		var _self_name = $(this).attr("name")+"_";
	    		$(this).attr("name",_self_name);
	    	});
			$(doc).before(newDoc);
			$(doc).hide();
			bindSavePageEvent(newDoc);
		}
	},"json");
}

function saveRequest(target){
	$(target).closest("div[name='record']").find("tr td").children().filter(":radio").each(function(){//恢复radio name 使之正确提交
		var _self_name = $(this).attr("name");
		if(_self_name.indexOf("_")>0){
			_self_name = _self_name.substring(0,_self_name.indexOf("_"));
			$(this).attr("name",_self_name);
		}
	});
	var classId = getCurrentRecordClassid(target);
	var param = $(target).closest("div[name='record']").find("input").serialize();
	param += "&" + $(target).closest("div[name='record']").find("textarea").serialize();
	param += "&" + $(target).closest("div[name='record']").find("select").serialize();
	param = param + "&classId="+classId+"&editable="+getEditable();
	$.post(_path + '/normal/staffResume_insert.html',param,function(data){
		var callback = function(){
			loadDataByClass(target);
		};
		processDataCall(data,callback);
	},"json");
}

function deleteRequest(target){
	var classId = getCurrentRecordClassid(target);
	var globalid = getCurrentRecordId(target);
	var param = "classId="+classId+"&globalid="+globalid;
	$.post(_path + '/normal/staffResume_delete.html',param,function(data){
		var callback = function(){
			loadDataByClass(target);
		};
		processDataCall(data,callback);
	},"json");
}

function buttonInitialize(){
	$("#back").click(function(){//功能条返回按钮
		var v = $("span.people_xx>input[name='returnUrl']");
		if($(v).length>0){
			goUrl($(v).val());
		}else{
			window.history.go( -1 );
		}
	});

	$("span.history").click(
		function(){
			var obj = this;
			var v = $("#history").val();
			if(v == "false"){
				$("#history").val("true");
				loadDataByCatalog(function(){
					$(obj).addClass("on");
					$(obj).text("已显示完整记录");
				});
			}else{
				$("#history").val("false");
				loadDataByCatalog(function(){
					$(obj).removeClass("on");
					$(obj).text("未显示完整记录");
				});
			}
		}
	);
}

function menuLayerInitialize(){//菜单层事件初始化
	$(".list_xxxx li").hover(function(){
		$(this).children(".list_xxxx_downmenu").show();
		//$(this).css("position","relative");
	},function(){
		$(this).children(".list_xxxx_downmenu").hide();
		//$(this).css("position","");
	});
	$(".list_xxxx_downmenu").hover(function(){
		$(this).show();
		$(this).prev().addClass("hover");
	},function(){
		$(this).hide();
		$(this).prev().removeClass("hover");
	});
	$(".list_xxxx_downmenu a").click(function(){//下拉菜单点击时的页面视窗定位
		var clzid = $(this).next().val();
		$(".list_xxxx li>a").removeClass("selected");
		$(this).closest("li").children("a").addClass("selected");
		$("#currentCatalog").val($(this).closest("li").attr("id"));
		loadDataByCatalogAndClzId(clzid);
		return false;
	});

	$(".list_xxxx li").click(function(){
		if($(this).is("#all-info")){
		//	showWindow("全部信息",_path+"/normal/staffResume_listAll.html?globalid="+getPersonGh(),720,450);
			showTopWin( _path + "/normal/staffResume_listAll.html?globalid="+getPersonGh(), "810", "730", "yes" );
		}else{
			$("#currentCatalog").val($(this).attr("id"));
			loadDataByCatalog();
		}
		return false;
	});
}

function getPersonGh(){
	var gh = $("span.people_xx>input[name='gh']").val();
	if(gh == null || gh == ""){
		throw "can't find staffid value";//未获取到用户工号
	}
	return gh;
}

function getHistory(){
	var h = $("#history").val();
	if(h == null || h == ""){
		throw "can't find history value";//未获取到历史记录状态
	}
	return h;
}

function getCurrentRecordClassid(target){
	var v = $(target).closest("div.demo_xxxx").attr("id");
	if(v == null || v == ""){
		throw "can't find classid value";//未获取到当前记录的类别id
	}
	return v;
}

function getCurrentRecordId(target){
	var v = $(target).closest("ul").find("input[name='globalid']").val();
	if(v == null || v == ""){
		throw "can't find recordid value";//未获取到当前记录的id
	}
	return v;
}

function getCatalogId(){
	//var a = $(".list_xxxx li>a.selected").closest("li").attr("id");
	var a = $("#currentCatalog").val();
	if(a == null || a == ""){
		throw "can't find catalogid value";
	}
	return a;
}

function getEditable(){
	//var a = $(".list_xxxx li>a.selected").closest("li").attr("id");
	var editable = $("#editable").val();
	if(editable == null || editable == ""){
		editable = '';
	}
	return editable;
}

function initFloatMenu(){
	var parentDocument = parent.window.document;
	$(".typeright",$(parentDocument.body)).append($('#position-fixed').clone());
	$('#position-fixed',$(parentDocument.body)).css('position','fixed');
	$('#position-fixed',$(parentDocument.body)).css('display','none').css('z-index','9999').addClass("fixed_xxxx");
	var d=$("#position-fixed").offset().top;
	var height = 160+d;
	$(parent.window).bind('scroll',function(){
		var top = parentDocument.documentElement.scrollTop;
		if (top > height){
			$('#position-fixed').css('display','none');
			$('#position-fixed',$(parentDocument.body)).css('display','block').css('z-index','9999').addClass("fixed_xxxx");
			$('#position-fixed .title_xxxx,#position-fixed .position_xxxx',$(parentDocument.body)).css('margin','0');
		} else {
			$('#position-fixed').css('display','block');
			$('#position-fixed',$(parentDocument.body)).css('display','none');
			$('#position-fixed .title_xxxx,#position-fixed .position_xxxx').css('margin','');
		}
	});
	$(window).bind('beforeunload',function(){
		$('#position-fixed',$(parentDocument.body)).remove();
		$(parent.window).unbind("scroll");
	});
	
	var target = $('#position-fixed',$(parentDocument.body));
	$(target).find(".list_xxxx li").hover(function(){
		$(this).children(".list_xxxx_downmenu").show();
		$(this).css("position","relative");
	},function(){
		$(this).children(".list_xxxx_downmenu").hide();
		$(this).css("position","");
	});
	$(target).find(".list_xxxx_downmenu").hover(function(){
		$(this).show();
		$(this).prev().attr("class","hover");
	},function(){
		$(this).hide();
		$(this).prev().removeClass("hover");
	});
	$(target).find(".list_xxxx_downmenu a").click(function(){//下拉菜单点击时的页面视窗定位
		var clzid = $(this).next().val();
		var top = $("#"+clzid+" h3 a").position().top;
		$("#"+clzid+" h3 a.down").click();
		top += $(window.parent.document).find("#xg_rightFrame").position().top;
		top += $(window.parent.document).find(".type_mainframe").position().top;
		top -= $(target).height();
		$(window.parent).scrollTop(top);
	});
	//$(target).find("a.lsjl").click(function(){//历史记录按钮
	//	historyButtonInit(this);
	//});
}
