<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<script type="text/javascript">
$(function(){
	$("#save").click(function(){		
		save();
	});
	
	$("#savedo").click(function(){		
		saveAndExecute();
	});

	$("#cancel").click(function(){
		divClose();
		return false;
	});

	$("textarea").keydown(function(){
		if(this.value.length > 49 && event.keyCode != '8') 
            event.returnValue=false; 
	});
	$("textarea").keyup(function(){
		if(this.value.length > 49 ){
			$(this).val(this.value.substr(0,50));
		}
	});

});

function buttonDisable(){
	$("#save").attr("disabled","true");
	$("#cancel").attr("disabled","true");
}

function createTokenId(){
	var date = new Date();
	var val = $("input[name='tokenId']").val() + '-' + date.valueOf();
	$("input[name='tokenId']").val(val);
}
function save(){
	if(!validate()){
		return false;
	}
	buttonDisable();
	$.post('<%=request.getContextPath()%>/manoeuvre/manoeuvre_save.html',$("#form1 input,#form1 textarea").serialize(),function(data){
		var callback = function(){
			searchForm.submit();
		};
		processDataCall(data, callback);
		
	},"json");
}

function saveAndExecute(){
	if(!validate()){
		return false;
	}
	buttonDisable();
	$.post('<%=request.getContextPath()%>/manoeuvre/manoeuvre_saveAndExecute.html',$("#form1 input,#form1 textarea").serialize(),function(data){
		var callback = function(){
			searchForm.submit();
		};
		processDataCall(data, callback);
		
	},"json");
}


function validate(){
	if($.trim($("#staffid").val()).length == 0){
		alert("申报人不能为空！");
		return false;
	}
	if($.trim($("#planOrg").val()).length == 0){
		alert("调入部门不能为空！");
		return false;
	}
	if($.trim($("#manoeuvreType").val()).length == 0){
		alert("变更类型不能为空！");
		return false;
	}
	if($.trim($("input[name='ddlx']").val()).length == 0){
		alert("调动类型不能为空！");
		return false;
	}
	
	return true;
}


createTokenId();
 

</script>
</head>

<body>
	<form id="form1" onsubmit="return false;">
		<input type="hidden" id="guid" name="guid" value="${guid }"/>
		<input type="hidden" id="createdByHR" name="createdByHR" value="${model.createdByHR }" />
		<input type="hidden" id="finishAudit" name="finishAudit" value="${model.finishAudit }" />
		<input type="hidden" id="beenDeclared" name="beenDeclared" value="${model.beenDeclared }" />
		<input type="hidden" name="tokenId" value="manoeuvre/manoeuvre_save.html"/>
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>人员变更信息申报<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">保 存</button>
								<button id="savedo">保存并调动</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:if test="${model.createdByHR}">
						<tr>
							<th width="25%">
								<span class="red">*</span>申报人
							</th>
							<td>
							<c:if test="${model.staffid==null||model.staffid==''}">
								<ct:selectPerson name="staffid" id="staffid"  value="${model.staffid}"  />
							</c:if>
							<c:if test="${model.staffid!=null&&model.staffid!=''}">
							     <input name="staffid" id="staffid" type="hidden" value="${model.staffid}"/>
							     ${model.staffid}
							</c:if>
							</td>
							
							<th width="25%">
								<span class="red">*</span>调入部门
							</th>
							<td>
							    <input type="hidden" id="planOrg_sub" value="off" />
								<ct:codePicker name="planOrg" catalog="<%=ICodeConstants.DM_DEF_ORG%>" code="${model.planOrg }" />
							</td>
						</tr>
						
						<tr>
							<th width="25%">
								调入岗位
							</th>
							<td>
								<ct:codePicker name="planPost" catalog="<%=ICodeConstants.DM_POSTINFO%>" code="${model.planPost }" />
							</td>
							
							<th width="25%">
								调入岗位类别
							</th>
							<td>
								<ct:codePicker name="planPostType" catalog="<%=ICodeConstants.DM_DEF_WORKPOST%>" code="${model.planPostType }" />
							</td>
						</tr>
						
						<tr>
							
							<th width="25%">
								调入编制类型
							</th>
							<td>
								<ct:codePicker name="formationType" catalog="<%=ICodeConstants.AUTH_TYPE%>" code="${model.formationType }" />
							</td>
						</tr>
					
					</c:if>
					<c:if test="${!model.createdByHR}">
						<tr>
							<th width="25%">
								职工号
							</th>
							<td>
							 <input name="staffid" id="staffid" type="hidden" value="${model.staffid}"/>
								${model.staffid }
							</td>
						
							<th width="25%">
								姓名
							</th>
							<td>
								${model.personInfo.viewHtml['xm'] }
							</td>
						</tr>
					
					
						<tr>
							<th width="25%">
								调出部门
							</th>
							<td>
								${model.personInfo.viewHtml['dwm'] }
							</td>
						
							<th width="25%">
								<span class="red">*</span>调入部门
							</th>
							<td>
							    <input type="hidden" id="planOrg_sub" value="off" />
								<ct:codePicker name="planOrg" catalog="<%=ICodeConstants.DM_DEF_ORG%>" code="${model.planOrg }" />
							</td>
						</tr>
						
						<tr>
							<th width="25%">
								当前岗位
							</th>
							<td>
								${model.personInfo.viewHtml['rzgwm'] }
							</td>
						
							<th width="25%">
								调入岗位
							</th>
							<td>
								<ct:codePicker name="planPost" catalog="<%=ICodeConstants.DM_POSTINFO%>" code="${model.planPost }" />
							</td>
						</tr>
						
						<tr>
							<th width="25%">
								当前岗位类别
							</th>
							<td>
								${model.personInfo.viewHtml['gwlb'] }
							</td>
						
							<th width="25%">
								调入岗位类别
							</th>
							<td>
								<ct:codePicker name="planPostType" catalog="<%=ICodeConstants.DM_DEF_WORKPOST%>" code="${model.planPostType }" />
							</td>
						</tr>
					    	<tr>
							<th width="25%">
								当前编制类别
							</th>
						
							<td>  
								  ${model.oldFormationTypeText }
							      <input id="oldFormationType"  type="hidden" name="model.oldFormationType" value="${model.oldFormationType }" />
							     
							</td>
							
							<th width="25%">
								调入编制类型
							</th>
							<td>
								<ct:codePicker name="formationType" catalog="<%=ICodeConstants.AUTH_TYPE%>" code="${model.formationType }" />
							</td>
						</tr>
					</c:if>
					
					<tr>
						<th width="25%">
							<span class="red">*</span>变更类别
						</th>
						<td >
							<ct:codePicker name="manoeuvreType" catalog="<%=ICodeConstants.DM_XB_RYDPLB%>" code="${model.manoeuvreType }" />
						</td>
						
						<th width="25%">
								<span class="red"></span>变更时间
							</th>
							<td>
							      <input type="text" name="model.changeTime" onclick="hideDownError(this);" style="width:160px" 
									value="<fmt:formatDate value="${model.changeTime}" pattern="yyyy-MM-dd"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
					</tr>
					<tr>
						<th width="25%">
							<span class="red">*</span>调动类型
						</th>
						<td colspan="3">
							<ct:codePicker name="ddlx" catalog="<%=ICodeConstants.DM_DEF_DDLX%>" code="${model.ddlx }" />
						</td>
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>变更原因
						</th>
						<td colspan="3">
							<textarea rows="4" cols="50" name="reason">${model.reason }</textarea>
						</td>
					</tr>
					<tr>
						<th width="25%">
							<span class="red"></span>备注
						</th>
						<td colspan="3">
							<textarea rows="4" cols="50" name="remark">${model.remark }</textarea>
						</td>
					</tr>
					
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>