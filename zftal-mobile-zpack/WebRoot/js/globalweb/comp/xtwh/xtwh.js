/* 此JS提供系统维护功能下面公用操作的工具库.  */

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
 * 打开一个新的窗口 弹出无模式对话框
 * @param url  可以为空,默认500
 * @param w    可以为空,默认400
 * @param h    可以为空
 * @param scrollbar 可以为空
 * @return
 */
var ie = document.all ? 1 : 0;
function showTopWin(url, w, h, scro) {
	var info = "";
	if(scro == null){
		info = "Status:YES;dialogWidth:" + w + "px;dialogHeight:" + h + "px;help:no;scroll:no";
	}else{
		info = "Status:YES;dialogWidth:" + w + "px;dialogHeight:" + h + "px;help:no;scroll:yes";
	}
	if(ie){
		showModalDialog(url, window, info);
	}else{
		window.open(url,"","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=yes,width="+w+",height="+h+",left=100,top=100,screenX=0,screenY=0");
	}
}

/**
 * 检测表单输入值是否为空
 * @param dataid
 * @return
 */
function isEmpty(dataid){
	if($('#' + dataid).val() != null && $('#' + dataid).val() != ''){
		return false;
	}else{
		return true;
	}
}

/**
 * 提交并刷新当前页面
 * @param url
 * @return
 */
function submitForm(url){
	if (url != null && url != '') {
		document.forms[0].action = url;
	}
	document.forms[0].submit();
}

/**
 * 去除空格
 * @param str
 * @return
 */
function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 用户多项选择查询页面中行首的复选框
 * 参数:chkallid :全选ID, chkoneid:行首ID
 */
function selectAll(chkallid, chkoneid){
	var checkBoxArr = document.getElementsByName(chkoneid);
	var selall = document.getElementById(chkallid);
	for(var i=0;i<checkBoxArr.length;i++){
		checkBoxArr[i].checked = selall.checked;
	}
}

/**
 * 关闭页面
 * @return
 */
function Close(){
	window.close();
}

/**
 * 验证必填信息是否全部填写完整
 * @param inputStr
 * @return
 */
function checkAllInput(inputStr){
	var input = inputStr.split("!!");
	for(var i=0;i<input.length;i++){
		var temp = document.getElementById(input[i]);
		var tempvalue = trim(document.getElementById(input[i]).value);
		if(temp==null || tempvalue==null || tempvalue==""){
			alert("请将必填信息，填写完整！");
			return false;
		}
	}
	return true;
}

/**
 * 弹出窗关闭后刷新父页(父页查询按钮id需为search_go)
 * @param msg
 * @return
 */
function alertAndrefresh(msg) {
	alert(msg);
	Close();
	if (window.dialogArguments && window.dialogArguments.document.getElementById("search_go")) {
		window.dialogArguments.document.getElementById("search_go").click();
	}
}

/** 
 *  修改单条记录时触发事件
 * @param cbvname  复选框名称，只能选择一条记录进行修改
 * @param url 要弹出的页面的路径
 * @return
 */
function update(cbvname,url,w,h,scroll) {
	if ($("input[name='"+cbvname+"']")) {
		var array = $("input[name='"+cbvname+"']");
		var count = 0;
		var pkValue = "";
		$.each(array,function(i,n) {
			if ($(array[i]).attr("checked")) {
				count++;
				pkValue = $(array[i]).val();
			}
		}); 
		if (count > 1) {
			alert("只能选择单条记录进行操作！");
			return;
		} else if (count <= 0) {
			alert("请选择要操作的记录！");
			return;
		} else {
			if (w == undefined || w == null) {
				w==500;
			}
			if (h == undefined || h == null) {
				h==400;
			}
			//如果url中已经存在参数，需判断
			if(url.indexOf("?") > 0)
				url += "&pkValue=" + pkValue;
			else
				url += "?pkValue=" + pkValue;
			showTopWin(url,w,h,scroll);
		}
	}
}

/**
 * 删除所选择的数据
 * @param cbvname 复选框名
 * @param url 执行的路径
 * @param mes 弹出的提示语
 * @return
 */
function delet(cbvname,url,mes) {
	if ($("input[name='"+cbvname+"']")) {
		var array = $("input[name='"+cbvname+"']");
		var count = 0;
		$.each(array,function(i,n) {
			if ($(array[i]).attr("checked")) {
				count++;
			}
		}); 
		if (count <= 0) {
			alert("请选择要删除的记录！");
			return;
		} else {
			if(mes!=null&&mes!=''){
				if(confirm(mes)){
					submitForm(url);
				}
			}else{
				if(confirm('确定要删除吗？')){
					submitForm(url);
				}
	        }
		}
	}
}