
/**
 * 填充行
 * <p>
 * 对表格进行行的填充，在最后进行调用
 * </p>
 * @param totleLength 总行数
 * @param thead 默认为：list_head
 * @param tbody 默认为：list_body
 * @param hasBox 是否含有复选框
 * @return
 */
function fillRows(totleLength, thead, tbody, hasBox) {
	thead = thead || 'list_head';
	tbodyname = tbody || 'list_body';
	
	hasBox=hasBox!=null?hasBox:true;

	var tbody = document.getElementById(tbodyname);
	var rowLength = $("#"+tbodyname+" tr:visible").length;
	var addLength = totleLength - rowLength;
	
	var columns = 0; //列数(不包含checkbox)
	try {
		columns = $("#"+thead+" > tr").children().length-1;
	} catch(e) {}
	
	for (var i = 0; i < addLength; i++) { //添加空行
		var tr = document.createElement("tr");
		
		var td = document.createElement("td");
		if(hasBox){
			td.innerHTML = "<input type='checkbox' disabled='disabled'>";
		}else{
			td.innerHTML="&nbsp;";
		}
		tr.appendChild(td);
		
		for (var j = 0; j < columns; j++) { //添加空列
			var td = document.createElement("td");
			td.innerHTML = "&nbsp;";
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}
	
}

/**
 * 工具条
 * <p>
 * 采用jQuery的形式对对obj对象进行监听，是否显示工具条
 * <br>
 * 工具条的内容为：修改、删除等按钮
 * <br>
 * 监听方式：鼠标上移、鼠标移开
 * </p>
 * @param obj 监听的对象
 * @return
 */
function toolbox(obj)
{
	$(obj).mouseover(function(e){
		var yy=$(this).height()+"px";
		$("tr .toolbox_fd").show().css({"left":0,"top":yy});
		if($(this).find("#positionbox").length<1){
			$("#positionbox").prependTo($(this));
		}
	});
	
	$(obj).mouseout(function(e){
		$("tr .toolbox_fd").hide();
		$(this).remove("#positionbox");
	});
	
	//$(".toolbox_fd").mouseover(function(e){
	//	$(this).show();
	//})
}

/**
 * 数据行操作栏，鼠标悬停动作
 * @return
 */
function operationHover(){
	$(".current_item").hover(function(){
		$(this).next("div.select_tools").show();
		$(this).parent().css("position","relative");
	},function(){
		$(this).next("div.select_tools").hide();
		$(this).parent().css("position","");
	});
	$(".select_tools").hover(function(){
		$(this).show();
		$(this).parent().css("position","relative");
	},function(){
		$(this).hide();
		$(this).parent().css("position","");
	});
}

function operationHoverUnbind(){
	$(".current_item").unbind("mouseenter mouseleave");
	$(".select_tools").unbind("mouseenter mouseleave");
}