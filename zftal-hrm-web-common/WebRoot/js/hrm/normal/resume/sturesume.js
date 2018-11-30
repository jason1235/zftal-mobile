/**
 * 简历页面脚本对象
 * @return
 */
function Resume(){
	
	buttonInitialize();//简历页面按钮事件初始化
	menuLayerInitialize();//菜单层事件初始化
	allInfoLayerDatafill();//所有信息层数据填充，即请求数据	
}

function buttonInitialize(){
	$("#back").click(function(){//功能条返回按钮
		location.href= _path + "/normal/overallInfo_stulist.html";
	});
	$("a.down").click(function(e){//展开按钮
		var title = $(this).closest("div[class='demo_xxxx']")
		if($(title).children().length>1){
			if($(this).attr("class")=='up'){
				$(title).children().first().nextAll().hide();
				$(this).attr("class","down");
				$(this).text("展开");
			}else{
				$(title).children().first().nextAll().show();
				$(this).attr("class","up");
				$(this).text("收起");
			}
		}else{
			//var classId = $(title).attr("id");
			viewInfo(title);
			$(this).attr("class","up");
			$(this).text("收起");
		}
		e.stopPropagation();
	});
	$("div[class='demo_xxxx']").find(":first-child").click(function(e){
		var title = $(this).closest("div[class='demo_xxxx']");
		var a = $(title).next().find("a");
		if($(title).children().length>1){
			if($(a).attr("class")=='up'){
				$(title).children().first().nextAll().hide();
				$(a).attr("class","down");
				$(a).text("展开");
			}else{
				$(title).children().first().nextAll().show();
				$(a).attr("class","up");
				$(a).text("收起");
			}
		}else{
			viewInfo(title);
			$(a).attr("class","up");
			$(a).text("收起");
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
		$(this).css("position","relative")
	},function(){
		$(this).children(".list_xxxx_downmenu").hide();
		$(this).css("position","")
	})
	$(".list_xxxx_downmenu").hover(function(){
		$(this).show();
		$(this).prev().attr("class","hover")
	},function(){
		$(this).hide();
		$(this).prev().removeClass("hover")
	})
	$(".list_xxxx_downmenu a").click(function(){//下拉菜单点击时的页面视窗定位
		var clzid = $(this).next().val();
		var top = $("#"+clzid+" h3 a").position().top;
		$("#"+clzid+" h3 a.down").click();
		top += $(window.parent.document).find("#frame_content").position().top;
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

function viewInfo(title){
	var classId = $(title).attr("id");
	var param = "classId="+classId+"&gh="+getPersonGh(); 
	if($("a.lsjl").next().val()=='true')
		param += "&history=true";
	$.post(_path + '/normal/staffResume_stuquery.html',param,function(data){
		if( !data.success ) {
			tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
			$("#window-sure").click(function() {
				divClose();
			})
		}else{
			if($(data.result).length>0){
				var bean;//信息类结构
				$(data.result).each(function(i){
					if($(this.values).length==0){
						//无数据
					}else{
						var content = display(this);
						$(content).appendTo($(title));
					}
					bean = this;
				});
				addButton(title,bean);
			}
		}
	},"json");
}

function addButton(title,bean){
	if(bean.moreThanOne||$(title).children().length==1){//当该title的数据为多条或者没有数据时 （length=1为只有展开栏）
		var html = "<div class=\"demo_add_02\"><a style='cursor:pointer;'>添 加</a></div>";
		var content = $(html);
		$(content).find("a").click(function(){
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
		});
		$(content).appendTo($(title));
	}
}

function addInputForm(bean){
	var html = "<ul class=\"btn_xxxx\" id=\"newData\">"
		+"<li class=\"btn_xxxx_cx\"><a>撤销</a></li>"
		+"<li class=\"btn_xxxx_bc\"><a>保存</a></li></ul>"
    	+"<table width=\"100%\"  border=\"0\" class=\"formlist\" cellpadding=\"0\" cellspacing=\"0\">"
    	+	"<tbody></tbody></table>";
	var content = $(html);
	$(content).find(".btn_xxxx_bc a").click(function(){//保存按钮
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
	createEditTable(content);
	//由于bean不为空，新增时需要把编辑框设置为空值
	$(content).find("tr > td").children().not(":hidden").val("");
    return content;
}

function createEditTable(content){
	var pos = $(content).find("tbody");
	var bean = $(content).filter("ul").data("bean");
    for(var cnt=0;cnt<$(bean.editables).length;cnt++){
	    var prop = bean.editables[cnt];
	    if(bean.moreThanOne){
	    	var row = $("<tr></tr>").append("<th width='15%'>"+prop.name+"</th>");
			$(row).append("<td width='35%' name='edit'>"+bean.editHtml[prop.fieldName]+"</td>");
			$(pos).append(row);
	    }else{
			if(cnt%2==0){
				var row = $("<tr></tr>").append("<th width='15%'>"+prop.name+"</th>");
				$(row).append("<td width='35%' name='edit'>"+bean.editHtml[prop.fieldName]+"</td>");
				$(pos).append(row);
			}else{
				$(pos).find("tr:last").append("<th width='15%'>"+prop.name+"</th>");
				$(pos).find("tr:last").append("<td width='35%' name='edit'>"+bean.editHtml[prop.fieldName]+"</td>");
			}
	    }
	    if(prop.need){
			$(pos).find("th:last").prepend("<span class=\"red\">*</span>");
		}
	    $(pos).find("td:last").data("prop",prop);
	    if(cnt==0){
	    	$(pos).find("tr:last").append("<input name=\"gh\" type=\"hidden\" value=\""+getPersonGh()+"\"/>");
	    	var classId = bean.clazz.guid;
	    	$(pos).find("tr:last").append("<input name=\"classId\" type=\"hidden\" value=\""+classId+"\"/>");
	    }
	}
    if($(bean.values).length>0){//编辑模式中含有全局ID
    	$(pos).find("tr:last").append("<input name=\"globalid\" type=\"hidden\" value=\""+bean.values["globalid"]+"\"/>");
    	$(pos).find("tr td").children().filter(":radio").each(function(){//处理多条同类数据中，同名radio控件的默认选择问题
    		var _self_name = $(this).attr("name")+"_"+bean.values["globalid"];
    		$(this).attr("name",_self_name);
    	});
    }
    if(!bean.moreThanOne){//判断多条数据展现形式（单例）
    	if($(bean.editables).length%2==1)//填充空TD
    		$(pos).find("tr:last").append("<th width='15%'></th><td width='35%'></td>");
    }
}

function display(bean){
	var html = "<ul class=\"btn_xxxx\" style=\"display:none;\">"
		//+"<li class=\"btn_xxxx_sc\"><a>删除</a></li>"
		//+"<li class=\"btn_xxxx_bj\"><a>编辑</a></li><input type=\"hidden\" value=\""+bean.values["globalid"]+"\"/>"
		//+"<li class=\"btn_xxxx_cx\" style=\"display:none;\"><a>撤销</a></li>"
		//+"<li class=\"btn_xxxx_bc\" style=\"display:none;\"><a>保存</a></li>
		+"</ul>"
    	+"<table width=\"100%\"  border=\"0\" class=\"formlist\" cellpadding=\"0\" cellspacing=\"0\">"
    	+	"<tbody></tbody></table>";
    var content = $(html);
    $(content).find(".btn_xxxx_bj a").click(function(){//编辑按钮及切换保存按钮
		//$(content).find("[name='view']").hide();
		//$(content).find("[name='edit']").show();
    	$(content).find("tbody").empty();
    	createEditTable(content);
		$(content).find(".btn_xxxx_bc").show();
		$(content).find(".btn_xxxx_cx").show();
		$(this).parent().hide();
	    });
    $(content).find(".btn_xxxx_cx a").click(function(){//撤销按钮及切换编辑按钮
		//$(content).find("[name='view']").show();
		//$(content).find("[name='edit']").hide();
    	$(content).find("tbody").empty();
    	createViewTable(content);
		$(content).find(".btn_xxxx_bc").hide();
		$(content).find(".btn_xxxx_bj").show();
		$(this).parent().hide();
	    });
    $(content).find(".btn_xxxx_bc a").click(function(){//保存按钮及切换编辑按钮
		$(content).find("tr td").children().filter(":radio").each(function(){//恢复radio name 使之正确提交
			var _self_name = $(this).attr("name");
			_self_name = _self_name.substring(0,_self_name.indexOf("_"));
			$(this).attr("name",_self_name);
		});
		$(this).parent().hide();
		updateEntity(content);
	    });
    $(content).find(".btn_xxxx_sc a").click(function(){
		preDel(content);
	});
    
    $(content).filter("ul").data("bean",bean);
    createViewTable(content);
    return content;
}

function createViewTable(content){
	var pos = $(content).find("tbody");
	var bean = $(content).filter("ul").data("bean");
    var zp = 0;
    for(var cnt=0;cnt<$(bean.viewables).length;cnt++){
	    var prop = bean.viewables[cnt];
	    if(prop.fieldName=='zp'){
	    	zp = 1;
	    	$(pos).find("tr:first").append("<td width='35%' name='view' style=\"vertical-align:top;\">"+bean.viewHtml[prop.fieldName]+"</td>");
	    	continue;
	    }
	    if(bean.moreThanOne){
	    	var row = $("<tr></tr>").append("<th width='15%'>"+prop.name+"</th><td width='35%' name='view'>"+bean.viewHtml[prop.fieldName]+"</td>");
			$(pos).append(row);
	    }else{
			if(cnt%2==0){
				var row = $("<tr></tr>").append("<th width='15%'>"+prop.name+"</th><td width='35%' name='view'>"+bean.viewHtml[prop.fieldName]+"</td>");
				$(pos).append(row);
			}else{
				$(pos).find("tr:last").append("<th width='15%'>"+prop.name+"</th><td width='35%' name='view'>"+bean.viewHtml[prop.fieldName]+"</td>");
			}
	    }
		if(prop.need){
			$(pos).find("th:last").prepend("<span class=\"red\">*</span>");
		}
	}
    if(!bean.moreThanOne){
    	if(($(bean.viewables).length-zp)%2==1)//填充空TD
    		$(pos).find("tr:last").append("<th width='15%'></th><td width='35%'></td>");
    }
    if(zp){
    	var size = $(pos).find("tr").length;
    	$(pos).find("[name='zp']").closest("td").attr("rowspan",size);
    }
}

function updateEntity(content){
	$(content).last().wrap("<form id=\"form\"></form>");
	var param = $("#form").serialize();
	$(content).last().unwrap();
	$.post(_path + '/normal/staffResume_update.html',param,function(data){
		tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");

		$("#window-sure").click(function() {
			divClose();
			if( data.success ) {//刷新模块
				reloadTagContent(content);
			}
		})
	},"json");
}

function insertEntity(content){
	$(content).last().wrap("<form id=\"form\"></form>");
	var param = $("#form").serialize();
	$(content).last().unwrap();
	$.post(_path + '/normal/staffResume_insert.html',param,function(data){
		tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");

		$("#window-sure").click(function() {
			divClose();
			if( data.success ) {//刷新模块
				reloadTagContent(content);
			}
		})
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
	var title = $(content).closest("div[class='demo_xxxx']")
	var param = "globalid="+globalid+ "&classId="+$(title).attr("id");
	$.post(_path + '/normal/staffResume_delete.html',param,function(data){
		tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");

		$("#window-sure").click(function() {
			divClose();
			if( data.success ) {//刷新模块
				reloadTagContent(content);
			}
		})
	},"json");
}

function reloadTagContent(content){
	var title = $(content).closest("div[class='demo_xxxx']");
	$(title).children().filter(":not(:first)").slideUp(1000);
	$(title).children().filter(":not(:first)").remove();
	$(title).find("h3>a").click();
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
