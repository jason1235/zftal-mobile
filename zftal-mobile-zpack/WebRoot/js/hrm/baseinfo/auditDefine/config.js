/**
 * 初始化预览事件
 */
function loadDefineList(){
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
			$("#defineList").remove();
    		$("#defineInfo").append(d);
    		operationHoverUnbind();
    		operationHover();
    		initOperateEvent();
    		fillRows(5, "list_head2", "list_body2", false);//填充空行
		}
	};
	
	var param = $("input[name='query.classId']").serialize();
	//$.post(_path+'/summary/rosterData_query.html',param,function(data){
	//},"json");
	$.ajax({
		url:_path+"/baseinfo/auditDefine_info.html",
		type:"post",
		data:param,
		cache:false,
		dataType:"html",
		success:successCall
	});
	
}

function initOperateEvent(){
	$("a[name='del']").click(function(){
		var id = $(this).closest("tr").find("input[id='guid']").val();
		delEntity(id);
	});
}

function delEntity(id){//删除
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/baseinfo/auditDefine_delete.html',"define.guid="+id,function(data){
		if(data.success){
			loadDefineList();
		}else{
			alert(data.text);
		}
	},"json");
}
