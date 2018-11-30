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
	})
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
	jQuery(obj).parents('td:first').find('.msg_error2').remove();
}



/**
 * 显示下方友情提示
 * @param obj
 * @param msg
 * @return
 */
function showDownPrompt(obj,msg){
	hideDownError(obj);
	var pos = jQuery(obj).parents('td:first').find('.msg_prompt').html();
	if (pos == null){
		var promptHtml = '';
			promptHtml+= '<div class="msg_prompt" >';
			promptHtml+= '<div class="prompcon"><p>';
			promptHtml+= msg;
			promptHtml+= '</p></div></div>';

		jQuery(obj).parents('td').append(promptHtml);
		var p = jQuery(obj).position();
		jQuery(obj).parents('td:first').find('.msg_prompt').css('top',p.top+jQuery(obj).height());
		jQuery(obj).parents('td:first').find('.msg_prompt').css('left',p.left);
		//jQuery(obj).insertBefore(jQuery(obj).parents('td:first').find('.msg_prompt').css('top',p.top+jQuery(obj).height()));
		//jQuery(obj).insertBefore(jQuery(obj).parents('td:first').find('.msg_prompt').css('left',p.left);
		
		setTimeout(function(){
			jQuery(obj).focus();
		},500);
		
		jQuery(obj).bind('blur',function(){
			hideDownPrompt(obj);
		})
		
	} else {
		jQuery(obj).parents('td:first').find('.msg_prompt').css('display','');
	}
}

/**
 * 显示下方友情提示
 * @param obj
 * @return
 */
function hideDownPrompt(obj){
	//jQuery(obj).parents('td:first').find('.msg_prompt').css('display','none');
	jQuery(obj).parents('td:first').find('.msg_prompt').remove();
}



/**
 * 下方错误提示
 * @param obj
 * @param msg
 * @return
 */
function showDownError(obj,msg){
	hideDownPrompt(obj);
	var pos = jQuery(obj).parents('td:first').find('.msg_error').html();
	
	if (pos == null){
		var promptHtml = '';
			promptHtml+= '<div class="prompcon"><p>';
			promptHtml+= msg;
			promptHtml+= '</p></div></div>';

		var p = jQuery(obj).position();
		
		var content = $('<div class="msg_error"></div>');
		jQuery(obj).parents('td').append(content.html(promptHtml));
		jQuery(obj).parents('td:first').find('.msg_error').attr('style',"z-index:100");
		jQuery(obj).parents('td:first').find('.msg_error').css('top',p.top+$(obj).height());
		jQuery(obj).parents('td:first').find('.msg_error').css('left',p.left);
	} else {
		jQuery(obj).parents('td:first').find('.msg_error').css('display','');
	}
	
}

/**
 * 显示下方错误提示
 * @param obj
 * @return
 */
function hideDownError(obj){
	//jQuery(obj).parents('td:first').find('.msg_error').css('display','none');
	jQuery(obj).parents('td:first').find('.msg_error').remove();
}


/**
 * 检测是否有错误消息显示
 * @return
 */
function inputResult(){
	var result = true;
	var errorDiv = jQuery('.msg_error');
	var errorSpan = jQuery('.msg_error2');
	
	if (errorSpan.length > 0){
		return false;
	}
	
	for (var i = 0 ; i < errorDiv.length; i++){
		var display = jQuery(errorDiv.eq(i)).css('display');
		if (display != 'none'){
			result = false;
			break;
		}
	}
	return result;
}

/**
 * 验证表单数据
 * @param obj
 * @return
 */
function validate(obj){
	var prop = $(obj).closest("td").data("prop");
	if($.trim($(obj).val()).length==0){
		var msg = "该元素数据不能为空";
		if(prop != null){
			msg = prop.description+"不能为空";
		}
		showDownError(obj,msg);
	}
}

function tip(obj){
	var prop = $(obj).closest("td").data("prop");
	var msg = "请输入该元素数据";
	if(prop != null){
		msg = "请输入"+prop.description;
	}
	showDownPrompt(obj,msg);
}
