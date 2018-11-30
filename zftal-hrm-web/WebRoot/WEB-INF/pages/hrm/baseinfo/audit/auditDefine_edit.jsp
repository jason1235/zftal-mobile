<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	$("#action").click(function(){
		addEntity();
	});
	loadDefineList();
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/baseinfo/auditDefine_save.html',$("#form1 input").serialize(),function(data){
		if(data.success){
			loadDefineList();
			$("#form1 input[name='define.roleId']").val("");
			$("#form1 input[name='define.roleId']").siblings().val("");
		}else{
			alert("操作异常");
		}
	},"json");
}

function validate(){
	var v = $("#form1 input[name='define.roleId']");
	if($.trim(v.val()).length==0){
		//showDownError(v,"所属部门不能为空");
		alert("审核角色不能为空");
		return false;
	}
	if($("#list_body2").find("input[id='guid']").length>=5){
		alert("审核流程长度暂限制为5步");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="testID" >    
  <div class="tab" id="defineInfo">
	<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="4">
					<span id="title">审核定义配置<font color="#0f5dc2" style="font-weight:normal;">（信息类：${clazz.name }）</font></span>
					<input type="hidden" name="query.classId" value="${define.classId }"/>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
		   <tr>
				
				<th width="15%"><span class="red">*</span>角色<input type="hidden" name="define.classId" value="${define.classId }"/></th>
				<td width="35%">
					<%--<input type="text" name="define.roleId" class="text_nor" value="${define.roleId }"/>
				--%>
					<ct:RolePicker name="define.roleId" code="${define.roleId }" />
				</td>
				<th width="15%"><span class="red"></span>范围</th>
				<td width="35%">
					<input type="radio" name="define.scope" value="all" checked/><span>全部</span>
					<input type="radio" name="define.scope" value="depart"/><span>部门</span>
				</td>
			</tr>
			<tr>
				
			</tr>
			
		</tbody>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	            <div class="bz">"<span class="red">*</span>"为必填项</div>
	          	<div class="btn">
	            	<button id="action" name="action" >添加</button>
	          	</div>
	        </td>
	      </tr>
		</tfoot>
	</table>
	<br />
	
	</div>
</div>
</body>
</html>
