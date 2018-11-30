function loadView(){
	function successCall(data){
		try{
			$.parseJSON(data);
			alert(data.text);
		}catch(e){
			$("#content").html(data);
			bindContentEvent($("#content2"));
			$("#content2").show();
		}
	}
	var inputs = $("input[name='spBillConfig.id']").add($("input[name='spBillInstance.id']"))
		.add($("input[name='privilegeExpression']"));
	$.ajax({
		url:_path+"/bill/instance_list.html",
		type:"post",
		data:$(inputs).serialize(),
		cache:false,
		dataType:"html",
		success:successCall
	});
}

function bindContentEvent(doc){
	$("button[name='save']",doc).click(function(){
		saveAuditRequest();
	});
	$("button[name='pass']",doc).click(function(){
		var v = $("[name='node.suggestion']").val();
		if($.trim(v).length==0){
			$("[name='node.suggestion']").val("通过!");
		}
		passAuditRequest();
	});
	$("button[name='reject']",doc).click(function(){
		
		showConfirm("你确定要将该申请的状态置为不通过？");
        
        $("#why_cancel").click(function(){
          divClose();
        });
        $("#why_sure").click(function(){
        		rejectAuditRequest();
        });
	});
	$("button[name='back']",doc).click(function(){
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
		backAuditRequest(id);
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

function passAuditRequest(){
	var param = $("#form1").add($("input[name='expertDeclare.id']")).serialize();
	$.post(_path+'/expertmanage/audit_pass.html',param,function(data){
		var callback = function(){
			location = _path+'/expertmanage/audit_page.html';
		};
		processDataCall(data,callback);
	},"json");
}

function rejectAuditRequest(){
	var param = $("#form1").add($("input[name='expertDeclare.id']")).serialize();
	$.post(_path+'/expertmanage/audit_reject.html',param,function(data){
		var callback = function(){
			location = _path+'/expertmanage/audit_page.html';
		};
		processDataCall(data,callback);
	},"json");
}

function saveAuditRequest(){
	var param = $("#form1").add($("input[name='expertDeclare.id']")).serialize();
	$.post(_path+'/expertmanage/audit_save.html',param,function(data){
		var callback = function(){
			//location = _path+'/recruitPlan/formationAudit_page.html';
		};
		processDataCall(data,callback);
	},"json");
}

function backAuditRequest(id){
	var param = $("#form1").add($("input[name='expertDeclare.id']")).serialize()+"&backId="+id;
	$.post(_path+'/expertmanage/audit_back.html',param,function(data){
		var callback = function(){
			location = _path+'/expertmanage/audit_page.html';
		};
		processDataCall(data,callback);
	},"json");
}
