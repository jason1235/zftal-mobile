$(function(){//界面初始化
	var html = "";
	html +=	"<div class=\"selectbox\" id=\"_propertySelector\" style=\"position:absolute;z-index:1500;\" >";
    html += "<div class=\"tosel\" id=\"inputsel\" style=\"display:none;left:0px;top:0px;width:400px;\">";
    html += "	<ul class=\"sel_search\">";
	html += "		<li></li>";
    //html += "    	<li>";
    //html += "        	<input style=\"width:200px;\" class=\"text_nor text_nor_rs\" name=\"\" type=\"text\" value=\"\" readonly/>";
	//html += "			<button class=\"btn_common\" id=\"confirm\">确定</button>";
    //html += "        	<input type=\"hidden\" />";
    //html += "        </li>";
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
	
	function initSearchLayer(){
		$("#_propertySelector>#inputsel").click(function(e){
			e.stopPropagation();
		});
		$("body").click(function(e){
			if($("#_propertySelector>#inputsel").is(":visible"))
				$("#_propertySelector>#inputsel").hide();
		});
	}
});

/**
 * 代码选择控件
 * @param obj 控件事件绑定对象 需要为input type=text输入框
 * @param classid 信息类ID
 * @return
 */
function propertyPicker(obj,classid){
	if(obj==null){
		throw "function need an object to bind"; 
	}
	if(classid==null){
		throw "InfoClass id not found";
	}
    if($(obj).data("data")!=null){
    	showPanel();
    }else{
    	loadProperties(classid);
    }
    
    function loadProperties(classid){
    	$("#windown-content").unbind("ajaxStart");
    	$.post(_path+'/summary/property_load.html',"classid="+classid,function(data){
    		if(data.success){
    			parseProps(data.result);
    			$(obj).data("data",data);
    			$(obj).removeAttr("onclick");
    			$(obj).unbind("click");
    			$(obj).click(function(e){
    				propertyPicker(obj,classid);
    				e.stopPropagation();
    			});
    			showPanel();
    		}else{
    			$("#_propertySelector").find("ul.sel_search>li:first").append("<span>加载失败</span>");
    		}
    	},"json");
    }

    function parseProps(props){
    	var conArea = $("#_propertySelector").find("ul.sel_con");
    	$(conArea).empty();
    	$(props).each(function(){
    		$(conArea).append("<li><a href='#' title='"+this.name+"'>"+this.name+"</a></li>");
    		$(conArea).find("li:last").data("data",this);
    	});
    	$(conArea).find("li").click(function(){
    		var pData = $(this).data("data");
    		$(obj).val(pData.name);
    		$(obj).siblings(":hidden").val(pData.guid);
    		linkedColumnEvent();
    		$("#_propertySelector>#inputsel").hide();
    	});
    }
    
    function showPanel(){
    	var s = $(obj).offset();
    	$("#_propertySelector").css("left",s.left);
    	$("#_propertySelector").css("top",s.top+$(obj).height());
    	$("#_propertySelector").find("#inputsel").show();
    }
    
    
}

function linkedColumnEvent(callback){
	$("#windown-content").unbind("ajaxStart");
	var classid = $("#form1 input[name='config.classid']").val();
	var columnId = $("#form1 input[name='config.guid']").val();
	$.post(_path+'/summary/property_linkedColumn.html',"classid="+classid+"&columnid="+columnId,function(data){
		if(data.success){
			$("#queryType").empty();
			$("#queryType").append(data.result);
			if(callback!=null){
				callback();
			}
			$("#queryType>div").fadeIn();
		}
	},"json");
}
