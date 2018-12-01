/**
 * 输入框右方、下方提示
 * @author Penghui.Qu
 */

var z_index = 100;

/**
 * 显示右方友情提示
 * @param obj
 * @param msg
 * @return
 */
function showRightPrompt(obj,msg){
	hideRightError(obj);
	
	var promptHtml = '<span class="msg_prompt2">';
		promptHtml+= '<em class="prompcon">';
		promptHtml+= msg;
		promptHtml+= '</em></span>';
	jQuery(obj).parents('td:first').append(promptHtml);
	
	jQuery(obj).bind('blur',function(){
		hideRigthPrompt(obj);
	});
}

/**
 * 隐藏右方友情提示
 * @param obj
 * @return
 */
function hideRigthPrompt(obj){
	jQuery(obj).parents('td:first').find('.msg_prompt2').remove();
}




/**
 * 显示右方错误提示
 * @param obj
 * @param msg
 * @return
 */
function showRightError(obj,msg){

	hideRigthPrompt(obj);
	hideRightError(obj);
	
	var errorHtml = '<span style="padding-left:3px;padding-left:4px\\9;" class="msg_error2">';
		errorHtml+= '<em class="prompcon">';
		errorHtml+= msg;
		errorHtml+= '</em></span>';
	jQuery(obj).parents('td:first').append(errorHtml);
}


/**
 * 隐藏右方错误提示
 * @param obj
 * @return
 */
function hideRightError(obj){
	jQuery(obj).parents('td:first').find('.msg_error').remove();
	jQuery(obj).parents('td:first').find('.msg_error2').remove();
}



/**
 * 显示下方友情提示
 * @param obj
 * @param msg
 * @return
 */
function showDownPrompt(obj,msg,index){
	index = index || z_index--;
	hideDownError(obj);
	var pos = jQuery(obj).parents('td:first').find('.msg_prompt').html();
	if (pos == null){
		var promptHtml = '<div class="pos" style="z-index:'+index+'!important">';
			promptHtml+= '<div class="msg_prompt" style="top:100%">';
			promptHtml+= '<div class="prompcon"><p>';
			promptHtml+= msg;
			promptHtml+= '</p></div></div></div>';

		jQuery(obj).parents('td').append(promptHtml);
		jQuery(obj).insertBefore(jQuery(obj).parents('td:first').find('.msg_prompt'));

		setTimeout(function(){
			jQuery(obj).focus();
		},500);
		
		jQuery(obj).bind('blur',function(){
			setTimeout(function(){
				hideDownPrompt(obj);
			},500);
		});
	} 
}

/**
 * 显示下方友情提示
 * @param obj
 * @return
 */
function hideDownPrompt(obj){
	if (jQuery(obj).parents('td:first').find('.msg_prompt').html() != null){
		jQuery(obj).appendTo(jQuery(obj).parents('td:first'));
		jQuery(obj).parents('td:first').find('.pos').remove();
	}
}



/**
 * 下方错误提示
 * @param obj
 * @param msg
 * @return
 */
function showDownError(obj,msg,index){
	
	index = index || z_index--;
	hideDownPrompt(obj);
	var pos = jQuery(obj).parents('td:first').find('.msg_error').html();
	if (pos == null){
		var promptHtml = '<div class="pos" style="z-index:'+index+'!important">';
			promptHtml+= '<div class="msg_error" style="top:100%">';
			promptHtml+= '<div class="prompcon"><p>';
			promptHtml+= msg;
			promptHtml+= '</p></div></div></div>';

		jQuery(obj).parents('td').append(promptHtml);
		jQuery(obj).insertBefore(jQuery(obj).parents('td:first').find('.msg_error'));
	} 
}

/**
 * 显示下方错误提示
 * @param obj
 * @return
 */
function hideDownError(obj){
	if (jQuery(obj).parents('td:first').find('.msg_error').html() != null){
		jQuery(obj).appendTo(jQuery(obj).parents('td:first'));
		jQuery(obj).parents('td:first').find('.pos').remove();
	}
}


/**
 * 检测是否有错误消息显示
 * @return
 */
function inputResult(){
	var result = true;
	var errorDiv = jQuery('.msg_error');
	var errorSpan = jQuery('.msg_error2');
	
	return errorSpan.length == 0 && errorDiv.length == 0;
}
