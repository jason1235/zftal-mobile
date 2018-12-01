/**var params={idInput:$("input[training\\.id]"),
	container1:$("#content"),
	container2:$("#content2"),
	billConfigId:'${spBusiness.billId }',
	billInstanceId:'${training.billInstanceId }',
	approveBillClassesPrivilege:'${node.approveBillClassesPrivilege}',
	passAuditUrl:"/training/audit_pass.html",
	rejectAuditUrl:"/training/audit_reject.html",
	saveAuditUrl:"/training/audit_save.html",
	backAuditUrl:"/training/audit_back.html",
	returnUrl:"/training/audit_page.html"};
*/
function load(params){
	function successCall(data){
		try{
			$.parseJSON(data);
			alert(data.text);
		}catch(e){
			$(params.container1).html(data);
			bindContentEvent(
					params.container1,
					params.container2,
					params.idInput,
					params.passAuditUrl,
					params.rejectAuditUrl,
					params.saveAuditUrl,
					params.backAuditUrl,
					params.returnUrl);
			$(params.container2).show();
		}
	}
	var inputs ="spBillConfig.id="+params.billConfigId+"&spBillInstance.id="+params.billInstanceId
			+"&privilegeExpression="+params.approveBillClassesPrivilege;
	$.ajax({
		url:_path+"/bill/instance_list.html",
		type:"post",
		data:inputs,
		cache:false,
		dataType:"html",
		success:successCall
	});
}


function bindContentEvent(cont,container,idInput,passAuditUrl,rejectAuditUrl,saveAuditUrl,backAuditUrl,returnUrl){
	$("button[name='save']",container).click(function(){
		saveAuditRequest(idInput,saveAuditUrl,returnUrl);
	});
	$("button[name='pass']",container).click(function(){
		var saves=$(cont).find(".btn_xxxx_bc");
		if(saves.length>0){
			showWarning("请先保存再进行审核");
			return false;
		}
		var v = $("[name='node.suggestion']").val();
		if(v.length==0){
			$("[name='node.suggestion']").val("通过!");
		}
		passAuditRequest(idInput,passAuditUrl,returnUrl);
	});
	$("button[name='reject']",container).click(function(){
		var saves=$(cont).find(".btn_xxxx_bc");
		if(saves.length>0){
			showWarning("请先保存再进行审核");
			return false;
		}
		showConfirm("你确定要将该申请的状态置为不通过？");
        $("#why_cancel").click(function(){
            $("#windown-close").click();
          });
          $("#why_sure").click(function(){
        	  rejectAuditRequest(idInput,rejectAuditUrl,returnUrl);
          });
	});
	$("button[name='back']",container).click(function(){
		var offset = $(this).offset();
		var w = $(this).outerWidth(true);//margin
		var h = $(this).outerHeight();
		var mw = $("#menu").outerWidth(true);
		var mh = $("#menu").outerHeight();
		$("#menu").css("top",offset.top+h);
		$("#menu").css("left",offset.left+w-mw);
		if($("#menu").is(":visible")){
			$("#menu").slideUp();
		}else{
			$("#menu").slideDown();
		}
	});
	$("#menu>button").click(function(){
		var id=$(this).attr("id");
		backAuditRequest(idInput,backAuditUrl,returnUrl,id);
	});
	
	$("#logButton").toggle(
		function(){
			$("#logContent").slideDown();
		},
		function(){
			$("#logContent").slideUp();
		}
	);
}

function passAuditRequest(idInput,url,returnUrl){
	var param = $("#form1").add($(idInput)).serialize();
	$.post(_path+url,param,function(data){
		var callback = function(){
			location = _path+returnUrl;
		};
		processDataCall(data,callback);
	},"json");
}

function rejectAuditRequest(idInput,url,returnUrl){
	var param = $("#form1").add($(idInput)).serialize();
	$.post(_path+url,param,function(data){
		var callback = function(){
			location = _path+returnUrl;
		};
		processDataCall(data,callback);
	},"json");
}

function saveAuditRequest(idInput,url,returnUrl){
	var param = $("#form1").add(idInput).serialize();
	$.post(_path+url,param,function(data){
		var callback = function(){
			//location = _path+'/recruitPlan/formationAudit_page.html';
		};
		processDataCall(data,callback);
	},"json");
}

function backAuditRequest(idInput,url,returnUrl,backId){
	var param = $("#form1").add($(idInput)).serialize()+"&backId="+backId;
	$.post(_path+url,param,function(data){
		var callback = function(){
			location = _path+returnUrl;
		};
		processDataCall(data,callback);
	},"json");
}
