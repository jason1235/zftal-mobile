/* 此JS提供功能页面如增加,修改,删除,审核等操作的工具库.*/

// JS方法注释格式须包含以下几点说明：
// 1.方法的使用说明
// 2.参数说明(可选)
// 3.方法返回类型说明 (可选)
// 4.作者与时间
// 例如：

/**
 * 验证输入必须为数字
 * 
 * @param id为主键值
 * @return 返回TRUE或FALSE lt 2012.3.28
 */


var ie = document.all ? 1 : 0;

/** JavaScript版本检测 */
if(ie){
	var ver = Number(ScriptEngineMajorVersion() + "." + ScriptEngineMinorVersion());
	if(ver < 5.5){
		alert('IE 版本过低，请更新至6.0及以上版本，以免影响您的使用效果！');
		// location.href = "errMsg.do?errMsg=asdfasdf";
	}
} 

/**
 * 刷新并提交当前表单
 * 
 * @param url为当前表单路径,必填
 * @param str为页面要判断必填字符串ID,可选项，如
 *            xh!!xm!!xb，多字之间必须用!!分隔
 * 
 * lt 2012.3.29
 */
function subForm(url) {
		if (url != null && url != '') {
			document.forms[0].action = url;
		}
		document.forms[0].submit();
}

/**
 * 局部表单提交
 * @param url
 */
function ajaxSubForm(id,url){
	jQuery("#"+id).ajaxSubmit({
		target:"#rightContent",
		url:url,
		type:'POST'
	});
}

/**
 * 打开一个新的窗口 弹出无模式对话框
 * 
 * @param url
 *            可以为空,默认500
 * @param w
 *            可以为空,默认400
 * @param h
 *            可以为空
 * @param scrollbar
 *            可以为空 lt 2012.3.29
 */
function showWin(url, w, h, scro) {
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
 * 关闭窗口 lt 2012.3.29
 */
function closeWin() {
	window.close();
}

/**
 * 去除字符串空格 lt 2011-3-7
 */
function trim(str) { 
	return str.replace(/(^\s*)|(\s*$)/g, "");   
}

/**
 * 验证输入是否为空
 * 
 * @param inputStr
 *            格式：!! 多个字符之间必须用 !!
 * @return
 */
function checkInputNotNull(inputStr){
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
 * 删除所选择的数据
 * 
 * @param cbvname
 *            复选框名
 * @param url
 *            执行的路径
 * @param mes
 *            弹出的提示语
 * @return
 */
function deleted(cbvname,url,mes) {
	if (jQuery("input[name='"+cbvname+"']")) {
		var array = jQuery("input[name='"+cbvname+"']");
		var count = 0;
		var pkValue="";
		jQuery.each(array,function(i,n) {
			if (jQuery(array[i]).attr("checked")) {
				count++;
				pkValue += jQuery(array[i]).val()+",";
			}
		}); 
		if(""!=pkValue){
			pkValue = trim(pkValue);
			pkValue = pkValue.substring(0, pkValue.length-1);
		}
		if (count <= 0) {
			alert("请选择要删除的记录！");
			return;
		} else {
			if(mes!=null&&mes!=''){
				if(confirm(mes)){
					subForm(url+pkValue);
				}
			}else{
				if(confirm('确定要删除吗？')){
					subForm(url+pkValue);
				}
	        }
		}
	}
}

/**
 * 修改单条记录时触发事件
 * 
 * @param cbvname
 *            复选框名称，只能选择一条记录进行修改
 * @param url
 *            要弹出的页面的路径
 * @return
 */
function update(cbvname,url,w,h,scroll) {
	if (jQuery("input[name='"+cbvname+"']")) {
		var array = jQuery("input[name='"+cbvname+"']");
		var count = 0;
		var pkValue = "";
		jQuery.each(array,function(i,n) {
			if (jQuery(array[i]).attr("checked")) {
				count++;
				pkValue = jQuery(array[i]).val();
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
			showWin(url+pkValue,w,h,scroll);
		}
	}
}

/**
 * 查看单条记录时触发事件
 * 
 * @param cbvname
 *            复选框名称，只能选择一条记录进行查看
 * @param url
 *            要弹出的页面的路径
 * @return
 */
function view(cbvname,url,w,h,scroll) {
	if (jQuery("input[name='"+cbvname+"']")) {
		var array = jQuery("input[name='"+cbvname+"']");
		var count = 0;
		var pkValue = "";
		jQuery.each(array,function(i,n) {
			if (jQuery(array[i]).attr("checked")) {
				count++;
				pkValue = jQuery(array[i]).val();
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
			showWin(url+pkValue,w,h,scroll);
		}
	}
}

/**
 * 保存完成后提示操作信息，关闭当前页面并刷新父页面
 * 
 * @param msg
 * @return
 */
function refreshParent(msg) {
	alert(msg);
	closeWin();
	if (window.dialogArguments && window.dialogArguments.document.getElementById("search_go")) {
		window.dialogArguments.document.getElementById("search_go").click();
	}
}

// 单行选择
var curr_row = null;
var cur_bgc = "#ffdead";// 选中行背景（字符串）
var obj_bgc = "#FFFFFF";
var Rows=new Array();	// 所有选中的行对象
function rowMoreClick(objTr,tag) {
	curr_row = objTr;
	var iRow=window.event.srcElement;
	do{
		iRow=iRow.parentElement;
	}while(iRow && iRow.tagName!= undefined && iRow.tagName!='TR')
	

		if (Rows.length!=0){
			for (i=0; i<Rows.length; i++){	
				if (Rows[i]!=null && Rows[i].tagName.toLowerCase() == "tr") {
					Rows[i].style.backgroundColor = obj_bgc;
	    		}
			}
		}
		if(document.all("ycxh")){
			document.getElementById("ycxh").value=curr_row.cells[1].innerText.trim();
		}
		obj_bgc = curr_row.style.backgroundColor;
		curr_row.style.backgroundColor = cur_bgc;
		Rows.length=1;
		Rows[0]=iRow;

	changeColor(iRow);
}

// 选中行变色
function changeColor(E){
	for(i=0;i<Rows.length;i++){
		if(Rows[i]){
			Rows[i].style.backgroundColor=cur_bgc;
		}
	}
}


function searchReset() {

var input = document.getElementsByTagName('input');
var select = document.getElementsByTagName('select');
			
for (var i = 0;i<input.length;i++) {
	if (input[i].type != 'hidden' && input[i].disabled != true 
		&& input[i].type != 'checkbox')
		input[i].value="";
}
for (var i = 0;i<select.length;i++) {
	if (select[i].disabled != true && select[i].options.length > 0 && select[i].options(0).value == "")
	select[i].value="";
}
}

/**
 * 补全输入信息
 * 
 * @param id
 *            输入字段对应id
 * @param divid
 *            加载提示信息的层id
 * @param text
 *            提示信息
 * @return
 */
function setTextValue(id,divid,text){
	if (text!=""){
		document.getElementById(id).value=text;
		document.getElementById(divid).style.visibility="hidden";
		moveAtCaret(document.getElementById(id),text.length);
	}
}

/**
 * 将obj中光标向右移动n个字符
 * 
 * @param obj
 * @param n
 * @return
 */
function moveAtCaret(obj,n){    
	jQuery(obj).focus();    
	var rng=document.selection.createRange();    
	rng.moveStart("character",n);    
	rng.select();    
}  

/**
 * 获取模糊匹配提示信息
 * 
 * @param id
 *            输入框对应id
 * @param divid
 *            加载提示信息层对应id
 * @param tablename
 *            表名
 * @param zdm
 *            获取字段名，需要获取的输出字段
 * @return
 */
function loadSuggestion(id,divid,tablename,zdm){
	var value = document.getElementById(id).value;
	if(value!=null && value!=""){    
		jQuery.ajax({
			url: _path+'/mhpp/mhpp_mhpp.html',
			type: "post",
			dataType:"json",
			data:{srz:value,bm:tablename,zdm:zdm},
			success:function(data){	
				if(data!=""&&data!=null){
					var arr=data.split(",");
					var Msg=document.getElementById(divid);
					
					Msg.innerHTML="";
					Msg.style.visibility="visible";
					for(var i=0;i<arr.length;i++){
						var suggest="<div onclick=\"setTextValue('" + id + "','" + divid + "',this.innerHTML);\" style='cursor: hand;'>"+arr[i]+"</div>";
						Msg.innerHTML+=suggest;
					}
				}else{
					document.getElementById(divid).style.visibility="hidden"; 
				} 
			}
		});
	}else{
		document.getElementById(divid).style.visibility="hidden";
	}

	document.onclick = function (){
		document.getElementById(divid).style.visibility='hidden';
	}
}

// 简单密码判断
function checkJdmm(str){ 
	var b = true;
	var c = str.split("");
		for (var i = 0; i < c.length - 1; i++) {
			if (c[i + 1] - c[i] != 0) {
				b = false;
				break;
			}
		}
		if (!b) {
			b = true;
			for (var i = 0; i < c.length - 1; i++) {
				if (c[i + 1] - c[i] != 1) {
					b = false;
					break;
				}
			}
		}
	return b;
}


//为当前页面select option 增加title属性,用于内容提示
function addOptionTitle(){
	var optionObj=jQuery("select option");
	if(optionObj == null){
		optionObj=jQuery("select option");
	}else{
		optionObj.each(function(){
			jQuery(this).attr("title",jQuery(this).html());
		});
	}
	if(optionObj != null){
		optionObj.each(function(){
			jQuery(this).attr("title",jQuery(this).html());
		});
	}
	
}

