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
		$("#action").click(function(){
			addEntity();
		});
		//新增可选择代码
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('leaveSchool/leaveFlow_save.html',$("#form1 input, #form1 select").serialize(),function(data){
		var callback = function(){
			window.location.reload();
		};
		processDataCall(data, callback);
	},"json");
}

function validate(){
	var v = $("#form1 input[name='flow.accountId']");
	if($.trim(v.val()).length==0){
		//showDownError(v,"所属部门不能为空");
		alert("职工号不能为空");
		return false;
	}
	<%--
	v = $("#form1 input[name='flow.type']");
	if($.trim(v.val()).length==0){
		//showDownError(v,"岗位名称不能为空");
		alert("离校类型不能为空");
		return false;
	}
	 --%>
	v = $("#form1 input[name='flow.processDept']:checked");
	if($.trim(v.val()).length==0){
		//showDownError(v,"岗位名称不能为空");
		alert("处理部门不能为空");
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
					<span id="title">离校人员新增<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
		   <tr>
				<th width="30%"><span class="red">*</span>职工号</th>
				<td width="70%">
					<ct:selectPerson name="flow.accountId" id="flow.accountId" single="true" value="${step.handler }" width="180px"/>
				</td>
			</tr>
			<tr>
				<th width="15%"><span class="red"></span>处理部门</th>
				<td width="35%">
					<c:forEach items="${stepList }" var="step">
					<input type="checkbox" name="flow.processDept" value="${step.deptId }"/>${step.deptValue }<br />
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th width="30%"><span class="red"></span>离校类型</th>
				<td width="70%">
					<ct:codePicker name="flow.type" catalog="<%=ICodeConstants.DM_RETIRD_REASON%>" code="" />
				</td>
			</tr>
			<!-- 20140422 add start -->
			<tr>
                <th width="15%"><span class="red"></span>编制状况</th>
                <td width="70%">
                    <ct:codePicker name="flow.bzzkCd" width="100" catalog="<%=ICodeConstants.DM_GB_BZZKDMB %>" code="" />
                </td>
            </tr>
            <tr>
            	<th width="15%"><span class="red"></span>离校去向</th>
            	<td>
            		<select name="flow.lxqx" style="width:100px">
            			<option value="ENTERPRISE">事业单位</option>
            			<option value="SCHOOL">学校</option>
            			<option value="CORPORATION">企业</option>
            			<option value="OTHER">其他</option>
            		</select>
            	</td>
            </tr>
			<!-- 20140422 add end -->
			<tr>
				<th width="30%">备注</th>
				<td width="70%">
					<input type="text" name="flow.remark" id="flow.remark" class="text_nor" value="${flow.remark }" style="width: 280px"/>
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
