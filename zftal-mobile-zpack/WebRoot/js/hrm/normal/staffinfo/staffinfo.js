var hasBusiness=false;
/**
 * 简历页面脚本对象
 * @return
 */
function Resume(){//IE6直接调用此函数，故函数体内容复制至页面执行
	if($.browser.msie){
		if($.browser.version=="6.0"){
			//IE6暂不支持菜单浮动
		}else if($.browser.version=="7.0"){
			//IE7暂不支持菜单浮动
		}else{
			initFloatMenu();
		}
	}else{
		initFloatMenu();
	}
	buttonInitialize();//简历页面按钮事件初始化
	menuLayerInitialize();//菜单层事件初始化
	allInfoLayerDatafill();//所有信息层数据填充，即请求数据	
}

function buttonInitialize(){
	$("#back").click(function(){//功能条返回按钮
		//location.href= _path + "/normal/overallInfo_list.html";
		window.history.go( -1 );
	});
	
	$("a.down").click(function(e){//展开按钮
		var title = $(this).closest("div[class='demo_xxxx']");
		if($(title).find("div[name='conDiv']").children().length>0){
			if($(this).attr("class")=='up'){
				$(title).find("div[name='conDiv']").slideUp();
				$(this).attr("class","down");
				$(this).text("展开");
			}else{
				$(title).find("div[name='conDiv']").slideDown();
				$(this).attr("class","up");
				$(this).text("收起");
			}
		}else{
			var a = $(this);
			var callback = function(){
				$(title).find("div[name='conDiv']").slideDown();
				$(a).attr("class","up");
				$(a).text("收起");
			};
			viewInfo(title,callback);
		}
		e.stopPropagation();
	});
	$("div[class='demo_xxxx']").find(":first-child").click(function(e){
		var title = $(this).closest("div[class='demo_xxxx']");
		var a = $(this).find("a[id='open']");
		if($(title).find("div[name='conDiv']").children().length>0){
			if($(a).attr("class")=='up'){
				$(title).find("div[name='conDiv']").slideUp();
				$(a).attr("class","down");
				$(a).text("展开");
			}else{
				$(title).find("div[name='conDiv']").slideDown();
				$(a).attr("class","up");
				$(a).text("收起");
			}
		}else{
			var callback = function(){
				$(title).find("div[name='conDiv']").slideDown();
				$(a).attr("class","up");
				$(a).text("收起");
			};
			viewInfo(title,callback);
		}
	});

	$("a.lsjl").click(function(){//历史记录按钮
		if($(this).next().val()=="false")
			location.href= _path + "/normal/staffResume_list.html?globalid="+getPersonGlobalid()+"&history=true";
		else
			location.href= _path + "/normal/staffResume_list.html?globalid="+getPersonGlobalid();
	});
}

function menuLayerInitialize(){//菜单层事件初始化
	$(".list_xxxx li").hover(function(){
		$(this).children(".list_xxxx_downmenu").show();
		$(this).css("position","relative");
	},function(){
		$(this).children(".list_xxxx_downmenu").hide();
		$(this).css("position","");
	});
	$(".list_xxxx_downmenu").hover(function(){
		$(this).show();
		$(this).prev().attr("class","hover");
	},function(){
		$(this).hide();
		$(this).prev().removeClass("hover");
	});
	$(".list_xxxx_downmenu a").click(function(){//下拉菜单点击时的页面视窗定位
		var clzid = $(this).next().val();
		var top = $("#"+clzid+" h3 a").position().top;
		$("#"+clzid+" h3 a.down").click();
		top += $(window.parent.document).find("#xg_rightFrame").position().top;
		top += $(window.parent.document).find(".type_mainframe").position().top;
		$(window.parent).scrollTop(top);
	});
}

function allInfoLayerDatafill(){
	var all = $("a.down");
	all.each(function(){
		$(this).click();
	});
}

function viewInfo(title,callback){
	var classId = $(title).attr("id");
	var param = "classId="+classId+"&gh="+getPersonGh(); 
	if($("a.lsjl").next().val()=='true')
		param += "&history=true";
	$.post(_path + '/normal/staffInfo_query.html',param,function(data){
		if( !data.success ) {
			processDataCall(data, null);
		}else{
			if($(data.result).length>0){
				var bean;//信息类结构
				$(title).data("props",data.properties);
				$(data.result).each(function(i){
					if($(this.values).length==0){
						//无数据
					}else{
						var content = display(this);
						$(content).appendTo($(title).find("div[name='conDiv']"));
					}
					bean = this;
				});
				addButton(title,bean);
			}
		}
		doPermission(title);
		if(callback != null){
			callback();
		}
	},"json");
}

/**
 * 权限过滤
 * @param title
 */
function doPermission(title){
	var permissions = $(title).find("h3>div>span");
	if($(permissions).filter(":contains('xg')").length==0){
		$(title).find(".btn_xxxx_bj").remove();
	}
	if($(permissions).filter(":contains('sc')").length==0){
		$(title).find(".btn_xxxx_sc").remove();
	}
	if($(permissions).filter(":contains('zj')").length==0){
		$(title).find(".demo_add_02").remove();
	}
}

function addButton(title,bean){
	if(bean.moreThanOne||$(title).find("div[name='conDiv']").children().length==0){//当该title的数据为多条或者没有数据时 （length=1为只有展开栏）
		var html = "<div class=\"demo_add_02\">";
		if(hasBusiness){
			html+="<a style='cursor:pointer;'>添加申请</a>";
		}else{
			html+="<a style='cursor:pointer;'>添加</a>";
		}
		html+="</div>";
		var content = $(html);
		$(content).find("a").click(function(){
			if(hasBusiness){
				$.post(_path+"/infochange/infochange_create.html?query.classId="+bean.clazz.guid+"&guid=","",function(data){
					if(data.success){
						location.href=_path+"/infochange/infochange_detail.html?query.classId="+bean.clazz.guid+"&infoChange.id="+data.infoChange.id;
					}else{
						alert(data.message);
					}
				},"json");
			}else{
				if($(title).find("#newData").length>0){
				showWarning("请先完成上一个数据输入");
				$("#war_sure").click(function(){
					divClose();
				});
				return false;
				}
				var form = addInputForm(bean);
				$(form).insertBefore($(content));
				if(!bean.moreThanOne){
					$(content).remove();
				}
			}
		});
		$(content).appendTo($(title).find("div[name='conDiv']"));
	}
}

function addInputForm(bean){
	var html = "<ul class=\"btn_xxxx\" id=\"newData\">"
		+"<li class=\"btn_xxxx_cx\"><a>撤销</a></li>"
		+"<li class=\"btn_xxxx_bc\"><a>保存</a></li></ul>"
    	+"<table width=\"100%\"  border=\"0\" class=\"formlist\" cellpadding=\"0\" cellspacing=\"0\">"
    	+	"<tbody></tbody></table>";
	var content = $(html);
	$(content).find(".btn_xxxx_bc a").click(function(){//提交按钮
    	if($(content).find("td[name='edit']").length==0){
    		showWarning("请检查信息类配置及可编辑的属性");
			$("#war_sure").click(function(){
				divClose();
			});
			return false;
    	}
		
		insertEntity(content);
	});
	$(content).find(".btn_xxxx_cx a").click(function(){//撤销按钮
		//撤销动作
		var title = $(this).closest("div[class='demo_xxxx']");
		$(content).remove();
		if(!bean.moreThanOne)
			addButton(title,bean);
	});
	
	$(content).filter("ul").data("bean",bean);
	$(content).find("li>a").css("cursor","pointer");
	createEditTable(content);
	//由于bean不为空，新增时需要把编辑框设置为空值
	$(content).find("tr > td").children().not("input[type='radio']").val("");
    return content;
}

function createEditTable(content){
	
	var pos = $(content).find("tbody");
	var bean = $(content).filter("ul").data("bean");
	var id = bean.clazz.guid;
	var title = $("div.demo_xxxx#"+id);
	var props = $(title).data("props");
	//alert($(title).data("props"));
    var zp = 0;
    var columnCnt = 0;
    var num=6;
    var a=0;
    var displayNum = bean.clazz.displayNum;
    var zpView='';
    if (displayNum==1) {
		num=1;
	}
    
    for(var cnt=0;cnt<$(props).length;cnt++){
    	 var prop = props[cnt];
    	 var pNameValue = '';
    	 if(prop.editable){
     		pNameValue = bean.editHtml[prop.fieldName];
  	    }else if(prop.viewable){
  	    	pNameValue = bean.viewHtml[prop.fieldName];
  	    }else{
  	    	continue;
  	    }
 	    if(pNameValue==undefined){
 	    	pNameValue = "";
 	    }
    	if(prop.fieldName=='zp'){
	    	zp=1;
	    	//$(pos).append("<td width='35%' name='view' style=\"vertical-align:top;\">"+pNameValue+"</td>");
	    	zpView = "<th width='15%' rowspan='"+num+"' ><span name='p'>"+prop.name+"</span></th><td name='edit' rowspan='"+num+"' width='25%' name='view'>"+pNameValue+"</td>";
	    	//$(pos).append(row);
	    	if (displayNum==1) {
	    		var row = $("<tr></tr>").append(zpView);
				$(pos).append(row);
			}
			break;
	    }
    }
    for(var cnt=0;cnt<$(props).length;cnt++){
	    var prop = props[cnt];
	    
	    var pNameValue = '';
    	if(prop.editable){
    		pNameValue = bean.editHtml[prop.fieldName];
 	    }else if(prop.viewable){
 	    	pNameValue = bean.viewHtml[prop.fieldName];
 	    }else{
 	    	continue;
 	    }
	    if(pNameValue==undefined){
	    	pNameValue = "";
	    }
	    
	    if(prop.fieldName=='zp'){
	    	continue;
	    }
	    var size = $(pos).find("tr").length;
	    /**
	     * 当前存在行数小于 照片占用行数 则计入影响行
	     */
    	if (size<(num+1)*zp) {
    		columnCnt+=size-a;
    		a=size;
		}
    	var pName=prop.name;
    	if(prop.need){
    		pName="<span class='red'>*</span>"+pName;
    	}
//	    if(bean.moreThanOne){
//	    	var row = $("<tr></tr>").append("<th width='15%'><span name='p'>"+prop.name+"</span></th><td width='35%' name='view'>"+pNameValue+"</td>");
//			$(pos).append(row);
//	    }else{
			if(columnCnt%displayNum==0){
				var row = $("<tr></tr>").append("<th width='15%'><span name='p'>"+pName+"</span></th><td name='edit' width='25%' name='view'>"+pNameValue+"</td>");
				$(pos).append(row);
			}else{
				$(pos).find("tr:last").append("<th width='15%'><span name='p'>"+pName+"</span></th><td name='edit' width='25%' name='view'>"+pNameValue+"</td>");
			}
//	    }
		if(prop.need){
			//$(pos).find("th:last").prepend("<span class=\"red\">*</span>");
		}
		if(prop.highlight){
			//$(pos).find("span[name='p']:last").css("color","red");
		}
		if(columnCnt==0){
	    	$(pos).find("tr:last").append("<input name=\"gh\" type=\"hidden\" value=\""+getPersonGh()+"\"/>");
	    	//var classId = bean.clazz.guid;
	    	$(pos).find("tr:last").append("<input name=\"classId\" type=\"hidden\" value=\""+id+"\"/>");
	    }
		columnCnt++;
	}
    /**拼接照片*/
    if(displayNum>1&&zpView!='')
    	$(pos).find("tr:first").append(zpView);
//    if(!bean.moreThanOne){
//    	if(($(props).length+(zp*(num-1)-1))%displayNum==1)//填充空TD
    		
//    }
    if (size<(num)*zp) {
    	columnCnt++;
	}
    for ( ;columnCnt%displayNum!=0; columnCnt++) {
    	$(pos).find("tr:last").append("<th width='15%'></th><td width='35%'></td>");
	}
    
    if($(bean.values).length>0){//编辑模式中含有全局ID
    	$(pos).find("tr:last").append("<input name=\"globalid\" type=\"hidden\" value=\""+bean.values["globalid"]+"\"/>");
    	$(pos).find("tr td").children().filter(":radio").each(function(){//处理多条同类数据中，同名radio控件的默认选择问题
    		var _self_name = $(this).attr("name")+"_"+bean.values["globalid"];
    		$(this).attr("name",_self_name);
    	});
    }
}

function display(bean){
	var html = "<ul class=\"btn_xxxx\">"
		+"<li class=\"btn_xxxx_sc\"><a>删除</a></li>";
		if(hasBusiness){
			html+="<li class=\"btn_xxxx_bj\"><a>变更申请</a></li><input type=\"hidden\" value=\""+bean.values["globalid"]+"\"/>";
		}else{
			html+="<li class=\"btn_xxxx_bj\"><a>编辑</a></li><input type=\"hidden\" value=\""+bean.values["globalid"]+"\"/>";
		}
		html+="<li class=\"btn_xxxx_cx\" style=\"display:none;\"><a>撤销</a></li>"
		+"<li class=\"btn_xxxx_bc\" style=\"display:none;\"><a>保存</a></li></ul>"
    	+"<table width=\"100%\"  border=\"0\" class=\"formlist\" cellpadding=\"0\" cellspacing=\"0\">"
    	+	"<tbody></tbody></table>";
    var content = $(html);
    $(content).find(".btn_xxxx_bj a").click(function(){//编辑按钮及切换提交按钮
    	if(hasBusiness){
    		$.post(_path+"/infochange/infochange_create.html?query.classId="+bean.clazz.guid+"&guid="+bean.values['globalid'],"",function(data){
    			if(data.success){
    				location.href=_path+"/infochange/infochange_detail.html?query.classId="+bean.clazz.guid+"&infoChange.id="+data.infoChange.id;
    			}else{
    				alert(data.message);
    			}
    		},"json");
		}else{
			$(content).find("[name='view']").hide();
			$(content).find("[name='edit']").show();
	    	$(content).find("tbody").fadeOut("slow",function(){
	    		$(content).find("tbody").empty();
	        	createEditTable(content);
	    		$(content).find(".btn_xxxx_bc").show();
	    		$(content).find(".btn_xxxx_cx").show();
	    		$(content).find(".btn_xxxx_bj").hide();
	    		$(content).find("tbody").fadeIn("slow");
	    		//$(this).parent().hide();
	    	});
		}

    	
    	
	});
    $(content).find(".btn_xxxx_cx a").click(function(){//撤销按钮及切换编辑按钮
		//$(content).find("[name='view']").show();
		//$(content).find("[name='edit']").hide();
    	$(content).find("tbody").fadeOut("slow",function(){
	    	$(content).find("tbody").empty();
	    	createViewTable(content);
			$(content).find(".btn_xxxx_bc").hide();
			$(content).find(".btn_xxxx_cx").hide();
			$(content).find(".btn_xxxx_bj").show();
			$(content).find("tbody").fadeIn("slow");
    	});
	});
    $(content).find(".btn_xxxx_bc a").click(function(){//提交按钮及切换编辑按钮
		updateEntity(content);
	});
    $(content).find(".btn_xxxx_sc a").click(function(){
		preDel(content);
	});
    
    $(content).filter("ul").data("bean",bean);
    $(content).find("li>a").css("cursor","pointer");
    createViewTable(content);
    return content;
}

function createViewTable(content){
	var pos = $(content).find("tbody");
	var bean = $(content).filter("ul").data("bean");
	var id = bean.clazz.guid;
	var title = $("div.demo_xxxx#"+id);
	var props = $(title).data("props");
	//alert($(title).data("props"));
    var zp = 0;
    var columnCnt = 0;
    var num=6;
    var a=0;
    var displayNum = bean.clazz.displayNum;
    var zpView='';
    if (displayNum==1) {
		num=1;
	}
    
    for(var cnt=0;cnt<$(props).length;cnt++){
    	 var prop = props[cnt];
 	    
 	    var pNameValue;
 	    if(prop.viewable){
	    	pNameValue = bean.viewHtml[prop.fieldName];
	    }else{
	    	continue;
	    }
 	    if(pNameValue==undefined){
 	    	pNameValue = "";
 	    }
    	if(prop.fieldName=='zp'){
	    	zp=1;
	    	//$(pos).append("<td width='35%' name='view' style=\"vertical-align:top;\">"+pNameValue+"</td>");
	    	zpView = "<th width='15%' rowspan='"+num+"' ><span name='p'>"+prop.name+"</span></th><td rowspan='"+num+"' width='25%' name='view'>"+pNameValue+"</td>";
	    	//$(pos).append(row);
	    	if (displayNum==1) {
	    		var row = $("<tr></tr>").append(zpView);
				$(pos).append(row);
			}
			break;
	    }
    }
    for(var cnt=0;cnt<$(props).length;cnt++){
	    var prop = props[cnt];
	    var pNameValue;
	    if(prop.viewable){
 	    	pNameValue = bean.viewHtml[prop.fieldName];
 	    }else{
 	    	continue;
 	    }
	    if(pNameValue==undefined){
	    	pNameValue = "";
	    }
	    
	    if(prop.fieldName=='zp'){
	    	continue;
	    }
	    var size = $(pos).find("tr").length;
	    /**
	     * 当前存在行数小于 照片占用行数 则计入影响行
	     */
    	if (size<(num+1)*zp) {
    		columnCnt+=size-a;
    		a=size;
		}
//	    if(bean.moreThanOne){
//	    	var row = $("<tr></tr>").append("<th width='15%'><span name='p'>"+prop.name+"</span></th><td width='35%' name='view'>"+pNameValue+"</td>");
//			$(pos).append(row);
//	    }else{
    	var pName = prop.name;
    	if(prop.need){
    		pName="<span class='red'>*</span>"+pName;
    	}
			if(columnCnt%displayNum==0){
				var row = $("<tr></tr>").append("<th width='15%'><span name='p'>"+pName+"</span></th><td width='25%' name='view'>"+pNameValue+"</td>");
				$(pos).append(row);
			}else{
				$(pos).find("tr:last").append("<th width='15%'><span name='p'>"+pName+"</span></th><td width='25%' name='view'>"+pNameValue+"</td>");
			}
//	    }
		if(prop.need){
			//$(pos).find("th:last").prepend("<span class=\"red\">*</span>");
		}
		if(prop.highlight){
			//$(pos).find("span[name='p']:last").css("color","red");
		}
		columnCnt++;
	}
    /**拼接照片*/
    if(displayNum>1&&zpView!='')
    	$(pos).find("tr:first").append(zpView);
//    if(!bean.moreThanOne){
//    	if(($(props).length+(zp*(num-1)-1))%displayNum==1)//填充空TD
    		
//    }
    if (size<(num)*zp) {
    	columnCnt++;
	}
    /**填入空TD*/
    for ( ;columnCnt%displayNum!=0; columnCnt++) {
    	$(pos).find("tr:last").append("<th width='15%'></th><td width='35%'></td>");
	}
//    if(zp){
    	
//    	$(pos).find("[name='zp']").closest("td").attr("rowspan",size);
//    }
}

function updateEntity(content){
	$(content).find("tr td").children().filter(":radio").each(function(){//恢复radio name 使之正确提交
		var _self_name = $(this).attr("name");
		if(_self_name.indexOf("_")>0){
			_self_name = _self_name.substring(0,_self_name.indexOf("_"));
			$(this).attr("name",_self_name);
		}
	});
	$(content).last().wrap("<form id=\"form\"></form>");
	var param = $("#form").serialize();
	$(content).last().unwrap();
	$.post(_path + '/normal/staffInfo_update.html',param,function(data){
		var callback = function(){
			//$(this).parent().hide();
			$(content).find(".btn_xxxx_bc a").parent().hide();
			reloadTagContent(content);
		};
		processDataCall(data, callback);
	},"json");
}

function insertEntity(content){
	$(content).find("tr td").children().filter(":radio").each(function(){//恢复radio name 使之正确提交
		var _self_name = $(this).attr("name");
		if(_self_name.indexOf("_")>0){
			_self_name = _self_name.substring(0,_self_name.indexOf("_"));
			$(this).attr("name",_self_name);
		}
	});
	$(content).last().wrap("<form id=\"form\"></form>");
	var param = $("#form").serialize();
	$(content).last().unwrap();
	$.post(_path + '/normal/staffInfo_insert.html',param,function(data){
		var callback = function(){
			reloadTagContent(content);
		};
		processDataCall(data, callback);
	},"json");
}

function preDel(content){//删除前操作
	showConfirm("确定要删除吗？");
	$("#why_cancel").click(function(){
		divClose();
	});
	$("#why_sure").click(function(){
		deleteEntity(content);
	});
}

function deleteEntity(content){
	var globalid = $(content).filter("ul").data("bean").values["globalid"];
	var title = $(content).closest("div[class='demo_xxxx']");
	var param = "globalid="+globalid+ "&classId="+$(title).attr("id");
	$.post(_path + '/normal/staffInfo_delete.html',param,function(data){
		var callback = function(){
			reloadTagContent(content);
		};
		processDataCall(data, callback);
	},"json");
}

function reloadTagContent(content){
	var title = $(content).closest("div[class='demo_xxxx']");
	$(title).find("div[name='conDiv']").slideUp("slow",function(){
		$(title).find("div[name='conDiv']").empty();
		$(title).find("h3>a").click();
	});
}

function getPersonGh(){
	var gh = $("span.people_xx>input[name='gh']").val();
	if(gh == null || gh == ""){
		throw "can't find staffid value";//未获取到用户工号
	}
	return gh;
}

function getPersonGlobalid(){
	var id = $("span.people_xx>input[name='globalid']").val();
	if(id == null || id == ""){
		throw "can't find globalid value";//未获取到用户全局编号
	}
	return id;
}

