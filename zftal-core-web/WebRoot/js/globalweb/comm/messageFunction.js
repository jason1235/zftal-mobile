function displayMsgDiv(id) {
	if (id != null){
		jQuery('#'+id).attr('class','msg_prompt');
	} else{
		jQuery('#msg_div').attr('class','msg_prompt');
	}
}
function hideMsgDiv(id) {
  	if (id != null){
		jQuery('#'+id).attr('class','hide');
	} else{
		jQuery('#msg_div').attr('class','hide');
	}
}

 function displayMsgDiv1() {
    var msgdiv = document.getElementById('msg_div1');
        msgdiv.className = "msg_error";
 	  
}
function hideMsgDiv1() {
    var msgdiv = document.getElementById('msg_div1');           
        msgdiv.className = "hide"; 
}

function showMessage(id,className){
	jQuery('#'+id).attr('class',className);
}

function checkNotNull(id){
	var isNotNulll = true;
	var tempvalue = trim(document.getElementById(id).value);
	if (tempvalue == ''||tempvalue==null){
		var message = id+"Message";
		showMessage(message,'msg_error');
		isNotNulll = false;
	}else{
		var message = id+"Message";
		showMessage(message,'hide');
		isNotNulll = true;
	}
	return isNotNulll;
}