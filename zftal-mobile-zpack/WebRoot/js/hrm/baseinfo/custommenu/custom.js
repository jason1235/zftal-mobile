/**
 * 请求快照分页数据
 */
function loadCodeSelect(value,i){
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
			$("#td_" + value).empty();
			$("#td_" + value).append(d);
			$("#th_" + value).removeAttr("style");
			$("#td_" + value).removeAttr("style");
		}
	};
	$("#windown-content").unbind("ajaxStart");
	if(value ==''){
		return false;
	}
	$.ajax({
		url:_path+"/baseinfo/customMenu_loadCodeSelect.html",
		type:"post",
		data:"i="+i+"&custom.condition.name=" + value,
		cache:false,
		dataType:"html",
		success:successCall
	});
	
}