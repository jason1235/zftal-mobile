/* 此JS提供数字,字符等基础数据的格式验证的工具库.*/

// JS方法注释格式须包含以下几点说明：
// 1.方法的使用说明
// 2.参数说明(可选)
// 3.方法返回类型说明 (可选)
// 4.作者与时间
// 例如：


/**
 * 验证输入必须为数字
 * @param id为主键值
 * @return 返回TRUE或FALSE
 * lt 2011.12.15
 */

/**
 * 验证输入必须为数字或还小数点的数字
 * @param obj为输入的标签对象
 * @return 返回TRUE
 * lt 2011.12.15
 */
function onlyNumber(obj){
	obj.value = obj.value.replace(/[^(\d|\.)]/g,'');
	return true;
}

/**
 * 验证输入必须为整数
 * @param obj为输入的标签对象
 * @return TRUE
 * lt 2011.12.15
 */
function onyInt(obj){
	obj.value = obj.value.replace(/[^(\d]/g,'');
	return true;
}

/**
 * 验证输入不能为中文
 * @param obj为输入的标签对象
 * @return TRUE
 * lt 2011.12.15
 */
function isNotChar(obj){
	obj.value = obj.value.replace(/[^\u0000-\u00ff]/g,'');
	return true;
}


/*
 *限制只能输入数字或字母 
 */
function onlyNumWords(obj){
	obj.value = obj.value.replace(/[^\da-zA-Z]/g,"");
	return true;
}


/**
 * 验证email的录入格式
 * @param sEmail
 * @return
 */
function isEmail(sEmail){
    var p = /^[_\.0-9a-z-]+@([0-9a-z][0-9a-z-]+\.){1,4}[a-z]{2,3}$/i;
    if (!p.test(sEmail)) {
    	return false;
    }
    return true;
}

/**
 * 验证输入必须为email
 * @param obj为输入的标签对象
 * @return 验证成功为TRUE,反之为FALSE
 * lt 2011.12.15
 */
function onlyEmail(obj) {
	if (obj.value != null && obj.value != "") {
		var b = isEmail(obj.value);
		if (!b) {
			alert("e-mail格式不正确!");
			obj.value = "";
			return false;
		}
	}
	return true;
}

//检测文本框的最大长度
function checkTextareaLength(fieldId, fieldExplain, length){
	var fildV = jQuery("#" + fieldId).val();
	if(fildV != null && fildV != ""){
     	if(fildV.length > length/2){
     		showRightError(jQuery('#'+fieldId+''),fieldExplain + "不能大于" + parseInt(length/2) + "个字");
      		return false;
   		 }
	}
	return true;
}