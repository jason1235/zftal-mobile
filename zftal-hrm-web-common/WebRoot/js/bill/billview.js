function load(container,spBillConfigId,spBillInstanceId,privilegeExpression,localEdit,callback){
	function successCall(data){
		try{
			$.parseJSON(data);
			alert(data.text);
		}catch(e){
			$(container).html(data);
			if(callback!=null){
				callback();
			}
		}
	}
	if(localEdit==null){
		localEdit=false;
	}
	var inputs ="spBillConfig.id="+spBillConfigId+"&spBillInstance.id="+spBillInstanceId
			+"&privilegeExpression="+privilegeExpression+"&localEdit="+localEdit;
	$.ajax({
		url:_path+"/bill/instance_list.html",
		type:"post",
		data:inputs,
		cache:false,
		dataType:"html",
		success:successCall
	});
}

function loadByGh(container,spBillConfigId,spBillInstanceId,privilegeExpression,localEdit,staffId,callback){
	function successCall(data){
		try{
			$.parseJSON(data);
			alert(data.text);
		}catch(e){
			$(container).html(data);
			if(callback!=null){
				callback();
			}
		}
	}
	if(localEdit==null){
		localEdit=false;
	}
	var inputs ="spBillConfig.id="+spBillConfigId+"&spBillInstance.id="+spBillInstanceId
			+"&privilegeExpression="+privilegeExpression+"&staffId="+staffId+"&localEdit="+localEdit;
	$.ajax({
		url:_path+"/bill/instance_list.html",
		type:"post",
		data:inputs,
		cache:false,
		dataType:"html",
		success:successCall
	});
}
