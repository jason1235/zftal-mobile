<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants" %>
<%@page import="com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<script type="text/javascript">
$(function(){
	$("#save").click(function(){
		save();
	});

	$("#cancel").click(function(){
		divClose();
		return false;
	});

	initSelect("executeStatus", "${model.executeStatus}");
});

function buttonDisable(){
	$("#save").attr("disabled","true");
	$("#cancel").attr("disabled","true");
}

function initSelect(name,value){
	$("select[name='"+name+"'] > option").each(function(){
		if($(this).val()==value){
			$(this).attr("selected","selected");
		}
	});
}

function save(){
	buttonDisable();
	$.post('manoeuvre/manoeuvre_execute.html',$("#form1 input,#form1 textarea,#form1 select").serialize(),function(data){
		var callback = function(){
			searchForm.submit();
		};
		processDataCall(data, callback);
	},"json");
}

</script>
</head>

<body>
	<form id="form1" onsubmit="return false;">
		<input type="hidden" id="createdByHR" name="createdByHR" value="${model.createdByHR }" />
		<input type="hidden" id="finishAudit" name="finishAudit" value="${model.finishAudit }" />
		<input type="hidden" id="beenDeclared" name="beenDeclared" value="${model.beenDeclared }" />
		<input type="hidden" id="guid" name="guid" value="${guid }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>人员变更信息维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">调动执行</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th width="25%">
							<span class="red"></span>申请人
						</th>
						<td>
							${model.personInfo.viewHtml['xm'] }
						</td>	
						<th width="25%">
							<span class="red"></span>职工号
						</th>
						<td>
							${model.staffid }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>性别
						</th>
						<td>
							${model.personInfo.viewHtml['xbm'] }
						</td>	
						<th width="25%">
							<span class="red"></span>申请时间
						</th>
						<td>
							${model.applyTimeText }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>调出部门
						</th>
						<td>
							${model.currentOrgText }
						</td>	
						<th width="25%">
							<span class="red"></span>调入部门
						</th>
						<td>
							${model.planOrgText }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>当前岗位
						</th>
						<td>
							${model.currentPostText }
						</td>	
						<th width="25%">
							<span class="red"></span>调任岗位
						</th>
						<td>
							${model.planPostText }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>当前岗位类别
						</th>
						<td>
							${model.currentPostTypeText }
						</td>	
						<th width="25%">
							<span class="red"></span>调任岗位类别
						</th>
						<td>
							${model.planPostTypeText }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red">*</span>变更类别
						</th>
						<td colspan="3">
							${model.manoeuvreTypeText }
						</td>
					</tr>
					<tr>
						<th width="25%">
							<span class="red">*</span>调动类型
						</th>
						<td colspan="3">
							${model.ddlxText }
						</td>
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>变更原因
						</th>
						<td colspan="3">
							${model.reason }
						</td>		
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>备注
						</th>
						<td colspan="3">
							${model.remark }
						</td>		
					</tr>
					
					<c:choose>
						<c:when test="${editType eq 'status'}">
							<tr>
								<th width="25%">
									执行状态
								</th>
								<td colspan="3">
									<select id="executeStatus" name="executeStatus" style="width:100px;">
										<option value="">--请选择--</option>
										<option value="<%=ManoeuvreInfo.EXCUTESTATUS_WAITING %>">待执行</option>
										<option value="<%=ManoeuvreInfo.EXCUTESTATUS_EXCUTING %>">执行中</option>
										<option value="<%=ManoeuvreInfo.EXCUTESTATUS_FINISHED %>">执行完成</option>
									</select>
									
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th width="25%">
									实际执行时间
								</th>
								<td colspan="3">
									<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" id="excuteTime" name="excuteTime" value="${model.excuteTimeText }" />
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>