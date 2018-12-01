<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	var id = $("#form1 input[name='guid']").val();//判断增加还是修改
	if(id==""){
		$("input[id='parentName']").val("");
		$("#action").click(function(){
			addEntity();
		});
	}else{
		$("#form1 input[id='parentName']").removeClass().addClass("text_nobor").focus(function(){
			$(this).blur();
		});
		$("#action").click(function(){
			updateEntity();
		});
	}
	$("input[name='title']").focus(function(){showDownPrompt(this,"输入条件标题");});
	//$("input[name='parentId']").focus(function(){showDownPrompt(this,"输入父节点编号(暂未实现选择控件)")});
	$("textarea[name='express']").focus(function(){showDownPrompt(this,"输入条件表达式(暂未实现输入检测)");});
	$("input[id='parentName']").click(function(){
		selectConsle(this,'condition');
	});
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('<%=request.getContextPath()%>/baseinfo/conditionDefined_save.html',$("#form1 input,#form1 textarea").serialize(),function(data){
		var callback = function(){
			window.location.reload();
		};
		processDataCall(data, callback);
	},"json");
}

function updateEntity(){//修改
	if(!validate()){
		return false;
	}
	var dis = $("input:disabled");//获取disabled控件的值
	dis.attr("disabled","");
	var param = $("#form1 input,#form1 textarea").serialize();
	dis.attr("disabled","true");
	var title = $("input[name='title']").val();
	var text = $("input[name='text']").val();
	var express = $("textarea[name='express']").val();
	$.post('<%=request.getContextPath()%>/baseinfo/conditionDefined_update.html',param,function(data){
		var callback = function(){
			$(tr).find("[class^='list_ico']").text(title);
			$(tr).find("td[name='text']>span").text(text);
			$(tr).find("td[name='express']>span").text(express);
		};
		processDataCall(data, callback);
	},"json");
}

function validate(){
	var v = $("#form1 input[name='title']");
	if(v.val().length==0){
		//showDownError(v,"条件标题不能为空");
		alert("条件标题不能为空");
		return false;
	}
	v = $("#form1 input[name='parentId']");
	//if(v.val().length==0){
	//	showDownError(v,"父节点编号不能为空");
	//	return false;
	//}
	v = $("#form1 textarea[name='express']");
	if(v.val().length==0){
		//showDownError(v,"条件表达式不能为空");
		alert("条件表达式不能为空");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="testID" >    
  <div class="tab">
	<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="4">
					<span>条件维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<input type="hidden" name="guid" value="${model.guid }"/>
			<input type="hidden" name="query.parentId" value="${root.guid }"/>
			<input type="hidden" name="query.type" value="${root.type }"/>
			<input type="hidden" id="root.title" value="${root.title }"/>
			<tr>
				<th width="15%"><span class="red">*</span>条件标题</th>
				<td width="35%"><input type="text" name="title" class="text_nor" style="width:180px" value="${model.title }"/></td>
				<th width="15%">描述信息</th>
				<td width="35%"><input type="text" name="text" class="text_nor" style="width:180px" value="${model.text }"/></td>
			</tr>
			<tr>
				<th>父节点</th>
				<td colspan="3" id="tag">
					<input type="text" id="parentName" class="text_nor" value="${root.title }" style="width:180px" readOnly/>
					<input type="hidden" name="parentId" class="text_nor" value="${model.parentId }" style="width:180px"/>
				</td>
			</tr>
			<tr>
				<th><span class="red">*</span>表达式</th>
				<td colspan="3"><textarea name="express" rows="4" style="width:540px">${model.express }</textarea></td>
			</tr>
		</tbody>
    <tfoot>
      <tr>
        <td colspan="4">
            <div class="bz">"<span class="red">*</span>"为必填项</div>
          	<div class="btn">
            	<button id="action" name="action" >保 存</button>
            	<button name="cancel" onclick='divClose();'>取 消</button>
          	</div>
        </td>
      </tr>
	</tfoot>
	</table>
	</div>
</div>
</body>
</html>
