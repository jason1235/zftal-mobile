function initFoldingEvent(){
	$("#columnList").find("li[name='title']>a").click(function(){
		
		$(this).parent().next().siblings("[name='list']").slideUp("slow");
		if($(this).parent().next().is(":visible")){
			$(this).parent().next().slideUp("slow");
		}else{
			$(this).parent().next().slideDown("slow");
		}
	});
}

function initColumnEvent(){
	$("#columnList").find("li[name='list']>a").click(function(){
		var checkbox = $(this).find("input[type='checkbox']");
		//$(this).append("<span name='tip' style='align:left'>处理中...</span>");
		if($(checkbox).is(":checked")){
			deleteColumn(this);
		}else{
			saveColumn(this);
		}
	});
	$("#columnList").find(":checkbox").click(function(e){
		var a = $(this).parent();
		if($(this).is(":checked")){
			saveColumn(a);
		}else{
			deleteColumn(a);
		}
		e.stopPropagation();
	});
}

function saveColumn(link){
	var a = $(link);
	var classId = $(a).parent().prev().find("input").val();
	var rosterId = $("#columnList").prev().val();
	var columnId = $(a).find("input").val();
	var param = "column.classId="+classId+"&column.rosterId="+rosterId+"&column.columnId="+columnId;
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/summary/rosterColumn_save.html',param,function(data){
		if(data.success){
			//$(a).find("[name='tip']").remove();//TODO 缺少操作提示
			$(a).find(":checkbox").attr("checked","true");
		}else{
			//$(a).find("[name='tip']").text(data.text);
			alert(data.text);
		}
	},"json");
}

function deleteColumn(link){
	var a = $(link);
	var classId = $(a).parent().prev().find("input").val();
	var rosterId = $("#columnList").prev().val();
	var columnId = $(a).find("input").val();
	var param = "column.classId="+classId+"&column.rosterId="+rosterId+"&column.columnId="+columnId;
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/summary/rosterColumn_delete.html',param,function(data){
		if(data.success){
			//$(a).find("[name='tip']").remove();
			$(a).find(":checkbox").removeAttr("checked");
		}else{
			//$(a).find("[name='tip']").text(data.text);
			alert(data.text);
		}
	},"json");
}