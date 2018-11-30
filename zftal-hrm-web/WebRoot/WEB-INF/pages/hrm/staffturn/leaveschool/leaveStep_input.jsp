<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
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
	var id = $("#form1 input[name='step.guid']").val();//判断增加还是修改
	if(id==""){
		$("#action").click(function(){
			addEntity();
		});
	}else{
		$("#action").click(function(){
			updateEntity();
		});
	}
	//$("input[name='step.handler']").click(function(){
	//	var val = selectPersonSingle("teacher");
	//	if(val != null)
	//		$(this).val(val[0]);
	//});
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('leaveSchool/leaveStep_save.html',$("#form1 input").serialize(),function(data){
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
	var param = $("#form1 input").serialize();
	var handler = $("input[name='step.handler']").val();
	$.post('leaveSchool/leaveStep_update.html',param,function(data){
		var callback = function(){
			window.location.reload();
			//$(_tr).find("td[name='handler']").text(handler);
		};
		processDataCall(data, callback);
	},"json");
}

function validate(){
	var v = $("#form1 input[name='step.deptId']");
	if( $.trim(v.val()).length == 0 ){
		alert("处理部门不能为空");
		return false;
	}
	v = $( "#form1 input[name='step.handler']" );

	if( $.trim(v.val()).length == 0 ){
		alert("处理人不能为空");
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
					<span id="title">离校环节维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
		   <input type="hidden" name="step.guid" value="${step.guid }"/>
			<tr>
				<th width="30%"><span class="red">*</span>处理部门</th>
				<td width="70%">
					<c:if test="${step.guid != null }">
						<ct:codePicker name="step.deptId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${step.deptId }" editable="false"/>
					</c:if>
					<c:if test="${step.guid == null }">
						<ct:codePicker name="step.deptId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${step.deptId }" />
					</c:if>
				</td>
			</tr>
			<tr>
				<th width="15%"><span class="red">*</span>处理人</th>
				<td width="35%">
					<ct:selectPerson name="step.handler" id="step.handler" single="false" value="${step.handler }" width="180px"/>
				</td>
			</tr>
		</tbody>
    <tfoot>
      <tr>
        <td colspan="4">
            <div class="bz">"<span class="red">*</span>"为必填项</div>
          	<div class="btn">
            	<button id="action" name="action" type="button" >保 存</button>
            	<button name="cancel" onclick="divClose();" type="button">取 消</button>
          	</div>
        </td>
      </tr>
	</tfoot>
	</table>
	</div>
</div>
</body>
</html>
