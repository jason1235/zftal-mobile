/**
 * 角色选择控件
 */
$(function(){//界面初始化
	var html = "";
	html +=	"<div class=\"selectbox\" id=\"_roleSelector\" style=\"position:absolute;z-index:1500;\" >";
    html += "<div class=\"tosel\" id=\"inputsel\" style=\"display:none;left:0px;top:0px;width:400px;\">";
    html += "	<ul class=\"sel_search\">";
	html += "		<li></li>";
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
		$("#_roleSelector>#inputsel").click(function(e){
			e.stopPropagation();
		});
		$("body").click(function(e){
			if($("#_roleSelector>#inputsel").is(":visible"))
				$("#_roleSelector>#inputsel").hide();
		});
	}
});

/**
 * 代码选择控件
 * @param obj 控件事件绑定对象 需要为input type=text输入框
 * @param classid 信息类ID
 * @return
 */
function rolePicker(obj){
    if($(obj).data("data")!=null){
    	showPanel();
    }else{
    	loadProperties();
    }
    
    function loadProperties(){
    	$("#windown-content").unbind("ajaxStart");
    	$.post(_path+'/baseinfo/role_list.html',"",function(data){
    		if(data.success){
    			parseProps(data.result);
    			$(obj).data("data",data);
    			$(obj).removeAttr("onclick");
    			$(obj).unbind("click");
    			$(obj).click(function(e){
    				rolePicker(obj);
    				e.stopPropagation();
    			});
    			showPanel();
    		}else{
    			$("#_roleSelector").find("ul.sel_search>li:first").append("<span>加载失败</span>");
    		}
    	},"json");
    }

    function parseProps(props){
    	var conArea = $("#_roleSelector").find("ul.sel_con");
    	$(conArea).empty();
    	$(props).each(function(){
    		$(conArea).append("<li><a href='#' title='"+this.name+"'>"+this.name+"</a></li>");
    		$(conArea).find("li:last").data("data",this);
    	});
    	$(conArea).find("li").click(function(){
    		var pData = $(this).data("data");
    		$(obj).val(pData.name);
    		$(obj).siblings(":hidden").val(pData.guid);
    		$("#_roleSelector>#inputsel").hide();
    	});
    }
    
    function showPanel(){
    	var s = $(obj).offset();
    	$("#_roleSelector").css("left",s.left);
    	$("#_roleSelector").css("top",s.top+$(obj).height());
    	$("#_roleSelector").find("#inputsel").show();
    }
    
    
}
