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
		$("#form1 input[name='parentId']").click(function(){
			codePicker(this,'${model.catalogId}');
			});
		$("#action").click(function(){
			addEntity();
			});
		$("input[name='guid']").focus(function(){showDownPrompt(this,"输入条目编号")});
		$("input[name='description']").focus(function(){showDownPrompt(this,"输入条目信息")});
	}else{
		//codeSelector('tag','${model.catalogId}','parentName','parentId','${query.name}','${model.parentId}',false);
		$("#form1 input[name='guid']").attr("readonly","readonly").removeClass().addClass("text_nobor");
		$("input[name='description']").focus(function(){showDownPrompt(this,"输入条目信息")});
		//$("#form1 input[name='description']").attr("readonly","readonly").removeClass().addClass("text_nobor");
		var view = $("#form1 input[name='parentId']");
		var valHtml = "<input type=\"hidden\" name=\"parentId\" value=\"${model.parentId }\" />";
		var val = $(valHtml);
		view.removeAttr("name");
		view.val('${query.name}');
		val.insertAfter(view);
		$(view).click(function(){
			//codePicker(this,'${model.catalogId}');
			});
		$("#form1 input[name='parentId']").prev().attr("readonly","readonly").removeClass().addClass("text_nobor");
		$("#form1 input.text_nobor").focus(function(){
			$(this).blur();
			});
		$("#action").click(function(){
			updateEntity();
			});
	}
	initRadio("visible","${model.visible}");
	initRadio("dumped","${model.dumped}");
	initRadio("checked","${model.checked}");
	initRadio("hasParentNodeInfo","${model.hasParentNodeInfo}");

});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('<%=request.getContextPath() %>/code/codeItem_insert.html',$("#form1 input").serialize(),function(data){
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
	var param = $("#form1 input").serialize();
	dis.attr("disabled","true");
	var visible = $("input[name='visible']:checked").val();
	var checked = $("input[name='checked']:checked").val();
	var dumped = $("input[name='dumped']:checked").val();
	var hasParentNodeInfo = $("input[name='hasParentNodeInfo']:checked").val();
	var description = $("input[name='description']").val();
	$.post('<%=request.getContextPath() %>/code/codeItem_update.html',param,function(data){
		var callback = function(){
			$(_tr).find("td[name='visible']").html(visible=="1"?"是":"<font color=\"red\">否</font>");
			$(_tr).find("td[name='checked']").html(checked=="1"?"是":"<font color=\"red\">否</font>");
			$(_tr).find("td[name='dumped']").html(dumped=="1"?"是":"<font color=\"red\">否</font>");
			$(_tr).find("td[name='hasParentNodeInfo']").html(hasParentNodeInfo=="1"?"是":"<font color=\"red\">否</font>");
			$(_tr).find("td[name='desc']").find(":first-child").text(description);
		};
		processDataCall(data, callback);
	},"json");
}

function initRadio(name,value){
	$("input[type='radio'][name='"+name+"']").each(function(){
		if($(this).val()==value){
			$(this).attr("checked","checked");
		}
		});
}

function validate(){
	var v = $("#form1 input[name='guid']");
	if($.trim(v.val()).length==0){
		//showDownError(v,"代码条目不能为空");
		alert("代码条目不能为空");
		return false;
	}
	v = $("#form1 input[name='description']");
	if($.trim(v.val()).length==0){
		//showDownError(v,"条目信息不能为空");
		alert("条目信息不能为空");
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
					<span>条目维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th width="15%"><span class="red">*</span>代码条目</th>
				<td width="35%"><input type="text" name="guid" class="text_nor" style="width:180px" value="${model.guid }"/></td>
				<th width="15%"><span class="red">*</span>条目信息</th>
				<td width="35%"><input type="text" name="description" class="text_nor" style="width:180px" value="${model.description }"/></td>
			</tr>
			<tr>
				<th>条目父节点编号</th>
				<td>
					<input type="text" name="parentId" class="text_nor" style="width:180px" value="${model.parentId }" />
				</td>
				<th><span class="red">*</span>是否包含父节点信息</th>
				<td><input type="radio" value="1" name="hasParentNodeInfo" checked>是</input><input type="radio" value="0" name="hasParentNodeInfo"><font color="red">否</font></input></td>
			</tr>
			<tr>
				<th><span class="red">*</span>是否弃用</th>
				<td><input type="radio" value="1" name="dumped">是</input><input type="radio" value="0" name="dumped" checked><font color="red">否</font></input></td>
				<th><span class="red">*</span>是否显示</th>
				<td><input type="radio" value="1" name="visible" checked>是</input><input type="radio" value="0" name="visible"><font color="red">否</font></input></td>
			</tr>
			<tr>
					<th><span class="red">*</span>是否可选中</th>
				<td><input type="radio" value="1" name="checked" checked>是</input><input type="radio" value="0" name="checked"><font color="red">否</font></input></td>
				<th>条目注释</th>
				<td><input type="text" name="comment" class="text_nor" style="width:180px" value="${model.comment }"/></td>
			</tr>
			<tr>
				<th>提示信息</th>
				<td><input type="text" name="tip" class="text_nor" style="width:180px" value="${model.tip }"/></td>
				<th><!-- <span class="red">*</span>所属编目编号 --></th>
				<td><input type="hidden" name="catalogId" class="text_nor" style="width:180px" value="${model.catalogId }"/>
					<input type="hidden" name="order" class="text_nor" style="width:180px" value="${model.order }"/>
				</td>
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
