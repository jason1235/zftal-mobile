function checkedOperation(data){
	if(data.is(":checked")){
		var id = data.attr("id");
		$("#list_body1 input:checkbox[id^='"+id+"']:not(:disabled)").attr("checked","true");
		setColor(id);
		if(isAll()){
			$("#list_body1 input:checkbox[id='checkAll']:not(:disabled)").attr("checked","true");
		}
	}else{
		var id = data.attr("id");
		$("#list_body1 input:checkbox[id^='"+id+"']:not(:disabled)").removeAttr("checked");
		$("#checkAll").removeAttr("checked");
		removeColor(id);
	}
}

function expandOperaton(data){
	var id=data.closest("td").find("input[name='selectId']").attr("id");
	var tid='tr_'+id;
	if(data.attr("opened") == 'false'){
		$("#list_body1 tr[pid='"+id+"']").css("display","");
		data.attr("opened","true");
	}
	else{
		$("#list_body1 tr[id^='"+tid+"'][id!='"+tid+"']").css("display","none");
		data.attr("opened","false")
	}
}

function isAll(){
	var size = $("#list_body1 input:checkbox:not(:disabled)[checked!='true']").size();
	if(size == 0){
	return true;
  	}
	return false;
}

function initColor(){
	var cks = $("#list_body1 input:checkbox");
	if(cks == null || cks.size() == 0){
		return;
	}
	for(var i = 0; i < cks.size(); i++){
		if($(cks[i]).is(":checked")){
			$(cks[i]).closest("td").find("font").attr("color","#0457A7");
			setColorForParent($(cks[i]).attr("id"));
		}
	}
}

function setColor(id){
  $("#list_body1 tr[id^='tr_"+id+"']").find("font").attr("color","#0457A7");
	setColorForParent(id);
 }

function removeColor(id){
  $("#list_body1 tr[id^='tr_"+id+"']").find("font").removeAttr("color");
	removeColorForParent(id);
}

function setColorForParent(id){
	var pid = $("#tr_"+id).attr("pid");
	if(pid == null || pid == ''){
		return;
	}
	if($("#tr_"+pid).find("font").attr("color") == null || $("#tr_"+pid).find("font").attr("color") == '' || $("#tr_"+pid).find("font").attr("color").toUpperCase() != '#0457A7'){
		$("#tr_"+pid).find("font").attr("color","#0457A7");
		setColorForParent(pid);
	}
}

function removeColorForParent(id){
  var pid = $("#tr_"+id).attr("pid");
   if(pid == null || pid == ''){
		return;
   }
   var rpid = $("#tr_"+pid).find("input[name='selectId']").attr("id");
   if($("#list_body1 tr[pid='"+rpid+"']").find("font[color^='#04']").size() == 0){
   	$("#tr_"+pid).find("font").removeAttr("color");
   	removeColorForParent(pid);
   }
}

function checkAll(){
  if($("#checkAll").is(":checked")){
  	$("#list_body1 input:checkbox:not(:disabled)").attr("checked","true");
  	$("#list_body1 font").attr("color","#0457A7");
  }else{
  	$("#list_body1 input:checkbox:not(:disabled)").removeAttr("checked");
  	$("#list_body1 font").removeAttr("color");
  }
}

function save(url){
  $.post(_path+url,$("#configs input:checkbox").serialize(),function(data){
  	var callback = function(){
  		$("#searchForm").submit();
  	};
  	processDataCall(data, callback);
  },"json");
}