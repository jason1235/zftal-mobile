function loadBatchConfig(){
	if($("#propertyName").val()==''){
		return false;
	}
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/baseinfo/conditionBatch_load.html',$("#propertyName").serialize(),function(data){
		if(data.success){
			$("#list_body1").empty();
			$("#list_body1").append(data.result);
			initElementEvent();
			fillRows("10", "list_head1", "list_body1", false);//填充空行
		}else{
			alert("加载失败:"+data.text);
		}
	},"json");
}

function checkAll(){
	if($("#checkAll").is(":checked")){
		$("#list_body1 input:checkbox:not(:disabled)").attr("checked","true");
	}else{
		$("#list_body1 input:checkbox:not(:disabled)").removeAttr("checked");
	}
}

function initElementEvent(){
	$("#list_body1 input:checkbox").click(function(){
		if($(this).is(":checked")){
			var id = $(this).attr("id");
			$("#list_body1 input:checkbox[id^='"+id+"']:not(:disabled)").attr("checked","true");
		}else{
			var id = $(this).attr("id");
			$("#list_body1 input:checkbox[id^='"+id+"']:not(:disabled)").removeAttr("checked");
		}
	});
}

function commitBatchConfig(){
	var param = $("#configs input:hidden,#configs input:checkbox,#propertyName").serialize();
	$.post(_path+'/baseinfo/conditionBatch_save.html',param,function(data){
		var callback = function(){
			location.href = location.href;
		};
		processDataCall(data, callback);
	},"json");
}