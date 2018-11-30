function checkedOperation(data){
	if(data.is(":checked")){
		$("#self").removeAttr("checked");
		$("#all").removeAttr("checked");
	  	$("font[name='self']").removeAttr("color");
	  	$("font[name='all']").removeAttr("color");
		var id = data.attr("id");
		$("#list_body1 input:checkbox[id^='"+id+"']:not(:disabled)").attr("checked","true");
		setColor(id);
		//if(isAll()){
		//	$("#checkAll").attr("checked","true");
		//}
		$("font[name='choose']").attr("color","#0457A7");
	}else{
		var id = data.attr("id");
		$("#list_body1 input:checkbox[id^='"+id+"']:not(:disabled)").removeAttr("checked");
		//$("#checkAll").removeAttr("checked");
		removeColor(id);
		if(isEmpty()){
			$("font[name='choose']").removeAttr("color");
		}
	}
}

function selfCleckdOperation(data){
	if(data.is(":checked")){
		$("#list_body1 input:checkbox:not(:disabled)[id!='self']").removeAttr("checked");
	  	$("#list_body1 font[name!='self']").removeAttr("color");
	  	//$("#checkAll").removeAttr("checked");
		$("font[name='self']").attr("color","#0457A7");
	}else{
		$("font[name='self']").removeAttr("color");
	}
}

function allCleckdOperation(data){
	if(data.is(":checked")){
		$("#list_body1 input:checkbox:not(:disabled)[id!='all']").removeAttr("checked");
	  	$("#list_body1 font[name!='all']").removeAttr("color");
	  	//$("#checkAll").removeAttr("checked");
		$("font[name='all']").attr("color","#0457A7");
	}else{
		$("font[name='all']").removeAttr("color");
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
		$("#list_body1 tr[id^='"+tid+"']").find("span[name='list_ico']").attr("opened","false");
	}
}

function chooseExpandOperaton(data){
	if(data.attr("opened") == 'false'){
		$("#list_body1 tr[pid='']").css("display","");
		data.attr("opened","true");
	}
	else{
		$("#list_body1 tr[id^='tr_']").css("display","none");
		data.attr("opened","false")
	}
}

function isAll(){
	var size = $("#list_body1 input:checkbox:not(:disabled)[checked!='true'][id!='self'][id!='all']").size();
	if(size == 0){
	return true;
  	}
	return false;
}

function isEmpty(){
	var size = $("#list_body1 input:checkbox:not(:disabled)[checked='true'][id!='self'][id!='all']").size();
	if(size == 0){
	return true;
  	}
	return false;
}

function initColor(){
	var cks = $("#list_body1 input:checkbox[id!='self'][id!='all']");
	if(cks == null || cks.size() == 0){
		return;
	}
	for(var i = 0; i < cks.size(); i++){
		if($(cks[i]).is(":checked")){
			$(cks[i]).closest("td").find("font").attr("color","#0457A7");
			setColorForParent($(cks[i]).attr("id"));
			$("font[name='choose']").attr("color","#0457A7");
		}
	}
	
}

function initE(){
	var totals = $("#list_body1 tr[pid='']");
	initEUnit(totals);
}

function initEUnit(units){
	for(var i = 0; i < units.size(); i++){
		var children = $("#list_body1 tr[pid='" + $(units[i]).find("input:checkbox[name='selectId']").attr("id") + "']").find("font[color='#0457A7']").closest("tr");
		if(children != null && children.size() > 0){
			$(units[i]).find("span[name='list_ico']").attr("opened","true");
			$("#list_body1 tr[pid='" + $(units[i]).find("input:checkbox[name='selectId']").attr("id") + "']").css("display","");
			initEUnit(children);
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
	$("#self").removeAttr("checked");
	$("font[name='self']").removeAttr("color");
  	$("#list_body1 input:checkbox:not(:disabled)[id!='self']").attr("checked","true");
  	$("#self").removeAttr("checked");
  	$("#list_body1 font[name!='self']").attr("color","#0457A7");
  }else{
  	$("#list_body1 input:checkbox:not(:disabled)[id!='self']").removeAttr("checked");
  	$("#list_body1 font[name!='self']").removeAttr("color");
  }
}

function save(url,userId,roleId){
  $.post(_path+url,$("#configs input:checkbox,#form1 input").serialize(),function(data){
	  parent.alert(data.text,'',{
		  'clkFun' : function() {
		  }
	  });
	  $.post( _path+"/dataprivilege/deptFilter_getOrgsText.html",{"deptFilter.userId":userId,"deptFilter.roleId":roleId},function(data){
		  $(window.parent.document).find("#sjfw_"+userId).html(data.simple);
		  $(window.parent.document).find("#sjfw_"+userId).attr("name",data.whole);
		  iFClose();
	   });
  },"json");
}

function saveWithRoleId(url,userId,roleId){
	  $.post(_path+url,$("#configs input:checkbox,#form1 input").serialize(),function(data){
		  parent.alert(data.text,'',{
			  'clkFun' : function() {
			  }
		  });
		  $.post( _path+"/dataprivilege/deptFilter_getOrgsTextForRow.html",{"deptFilter.userId":userId,"deptFilter.roleId":roleId},function(data){
			  $(window.parent.document).find("#sjfw_"+roleId).html(data.simple);
			  $(window.parent.document).find("#sjfw_"+roleId).attr("name",data.whole);
			  iFClose();
		   });
	  },"json");
}

function clear(){
	$("#list_body1 input:checkbox:not(:disabled)").removeAttr("checked");
	$("font").removeAttr("color");
}

