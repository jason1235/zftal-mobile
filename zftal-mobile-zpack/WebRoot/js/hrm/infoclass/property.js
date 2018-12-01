function initSaveEvent(){
	$("#cancel").click(function(){
        modalClose();
        return false;
    });
    
    $(".close").click(function(){
        modalClose();
        return false;
    });
	//保存
	$("#save").click(function(){
		if( $("#name").val() == "" ) {
			//alert("属性名称不得为空，请重新输入！");
			errorAlert("属性名称不得为空，请重新输入！");
			return false;
		}
		
		var str=$("#fieldName").val();
		var rname=/[\u4E00-\u9FA5]/;  //u4E00-u9FA5 这是汉字编码范围，首先检测str是否在这个范围之内，是的话，返回true 否则false   
	    if(rname.test(str)){
	    	errorAlert("字段名称不得包含汉字，请重新输入！");
			return false;
	    }     
		if(str  == "" ) {
			//alert("字段名不得为空，请重新输入！");
			errorAlert("字段名称不得为空，请重新输入！");
			return false;
		}
		var fieldType = $("select[name='fieldType']").val();
		if( fieldType == "" ) {
			//alert("字段类型不得为空，请重新输入！");
			errorAlert("字段类型不得为空，请重新输入！");
			return false;
		}
		var fieldLen = $("input[name='fieldLen']").val();
		if( fieldLen == "" ) {
			errorAlert("字段长度不得为空，请重新输入！");
			return false;
		}
		if(fieldType == 'COMMON' && Number(fieldLen) > 4000){
			errorAlert("varchar字段长度不能大与4000，请重新输入！");
			return false;
		}
		$("#windown-content").unbind("ajaxStart");
		var dis = $("form[id='form2']").find(":disabled");
		$(dis).removeAttr("disabled");
		$.post(_path+'/infoclass/infoproperty_save.html', $("form[id='form2']").serialize(), function(data){
			if(!data.success){
				errorAlert(data.text);
				//window.parent.openAlert(data.text, "e");
			}else{
				$(dis).attr("disabled","disabled");
				//window.parent.modal("hide");
				var callback = function(){
					$("form:first",window.parent.document).submit();
				};
				window.parent.openAlert("操作成功", "s","auto", callback);
			}
			
			//var callback = function(){
			//	$("form:first").submit();
				//window.location.reload();
			//};
			
			//processDataCall(data, callback);
		}, "json");
		
		return false;
	});
	
	$("#cancel").click(function(){
		divClose();
	});
}

function initVirtualColumnEvent(){
	$("input[name='virtual']").click(function(){
		if(!$(this).is(":checked")){
			return false;
		}
		loadVirtualColumnInfo();
	});
}
function loadVirtualColumnInfo(){
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/infoclass/infoproperty_virtualStep.html', $("form[id='form2']").serialize(), function(data){
		if(data.success){
			$("form[id='form2'] tbody").find("tr[name!='step1']").remove();
			$("form[id='form2'] tbody").find("tr:last").after(data.result);
			initColumnTypeEvent();
			initDefInputStyleEvent();
			//loadColumnTypeInfo();
			if($("form[id='form2']").find("input[name='guid']").val()!=''){
				$("input[name='virtual']").attr("disabled","disabled");
			}
			changeReferInput();
			var fieldType = $("select[name='fieldType'] option:selected").val();
			if(fieldType != 'COMMON' && fieldType != null && fieldType != ""){
				$("#alertHtml").remove();
				$("input[name='fieldLen']").hide();
				$("input[name='fieldLen']").closest("td").append("<input type='text' class='form-control' id='alertHtml'  disabled='disabled' value='无需填写'>");
			}else{
				$("input[name='fieldLen']").show();
				$("#alertHtml").remove();
			}
		}else{
			alert(data.text);
		}
	}, "json");
}
function initColumnTypeEvent(){
	$("select[name='fieldType']").change(function(){
		var fieldType = $(this).val();
		if(fieldType != 'COMMON' && fieldType != null && fieldType != ""){
			$("#alertHtml").remove();
			$("input[name='fieldLen']").hide();
			$("input[name='fieldLen']").closest("td").append("<input type='text' class='form-control' id='alertHtml' disabled='disabled' value='无需填写'>");
		}else{
			$("input[name='fieldLen']").show();
			$("#alertHtml").remove();
		}
		//loadColumnTypeInfo();
	});
}

//function loadColumnTypeInfo(){
//	if($("select[name='fieldType']").val()==''){
//		return false;
//	}
//	$("#windown-content").unbind("ajaxStart");
//	$.post(_path+'/infoclass/infoproperty_columnStep.html', $("form[id='form2']").serialize(), function(data){
//		if(data.success){
//			$("form[id='form2'] tbody").find("tr[name='step3']").remove();
//			$("form[id='form2'] tbody").find("tr:last").after(data.result);
//		}else{
//			alert(data.text);
//		}
//	}, "json");
//}
function initDefInputStyleEvent(){
	$("#getDefInputStyle").click(function(){
		getDefInputStyle();
	});
}
function getDefInputStyle(){
	$.post(_path+'/infoclass/infoproperty_getDefInputStyle.html', $("form[id='form2']").serialize(), function(data){
		if(data.success){
			$("#defValIpt").html(data.result);
		}else{
			alert(data.text);
		}
	}, "json");
	
}
function changeReferInput(){
	 if($("select[name='referFunc']").val()=="to_number"){
		 $("#refer0").hide();
		 $("#refer0").removeAttr("name");
		 $("#refer1").show();
		 $("#refer1").attr("name","refer");
	 }else{
		 $("#refer1").hide();
		 $("#refer1").removeAttr("name");
		 $("#refer0").show();
		 $("#refer0").attr("name","refer");
	 }
}