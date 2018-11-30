function editRequest(){
	if($("input[id='id']:checked").length==0){
        alert("请先选中操作行");
        return false;
    }
	var id = $("input[id='id']:checked").val();
	showWindow("编辑",_path+"/wpjs/wpjsdeclare_modify.html?declare.id="+id,720,500);
}

function inputRequest(){
	showWindow("新增",_path+"/wpjs/wpjsdeclare_input.html",720,500);
}

function deleteRequest(){
	if($("input[id='id']:checked").length==0){
        alert("请先选中操作行");
        return false;
    }
	showConfirm("确认要删除吗?");
	var id = $("input[id='id']:checked").val();
	$("#why_cancel").click(function(){
		divClose();
	});
	$("#why_sure").click(function(){
		delRequest(id);
	});
}

function delRequest(id){
	$.post(_path+'/wpjs/wpjsdeclare_delete.html',"declare.id="+id,function(data){
		var callback = function(){
			$("#search").submit();
		};
		processDataCall(data,callback);
	},"json");
}

function saveRequest(){
	var param = $("#form1").serialize();
	$.post(_path+'/wpjs/wpjsdeclare_save.html',param,function(data){
		var callback = function(){
			$("#search").submit();
		};
		processDataCall(data,callback);
	},"json");
}

function updateRequest(){
	if(!validate()){
		return false;
	}
	var param = $("#form1").serialize();
	$.post(_path+'/wpjs/wpjsdeclare_update.html',param,function(data){
		var callback = function(){
			$("#search").submit();
		};
		processDataCall(data,callback);
	},"json");
}

function validate(){
	return true;
}

