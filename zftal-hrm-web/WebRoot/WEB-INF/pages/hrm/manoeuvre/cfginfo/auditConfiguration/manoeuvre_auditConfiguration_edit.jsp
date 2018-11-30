<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" defer="" src="/zftal-hrm/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		
	<script type="text/javascript">
	$(function(){

		$("#save").click(function(){
			save();
		});

		$("#extensionTypeSel").change(function(){
			var opt = $("#extensionTypeSel")[0].options[$("#extensionTypeSel")[0].selectedIndex].value;
			$("input[name='info.extensionType']").val(opt);
			if(opt == '0' || opt == '1'){
				$("input[name='info.extension']").val('0');
			}
			else{
				$("input[name='info.extension']").val('1');
			}
		});
		
		if ('${aid }' == null || '${aid }' == "") {
		    if ($("input[name='audittype']:checked").val() == "2") {
		        $("#assesorView").show();
		        $("#assesorRoleView").hide();
                $("#hidaudittype").val($("input[name='audittype']:checked").val());
		    } else {
		        $("#assesorView").hide();
		        $("#assesorRoleView").show();
                $("#hidaudittype").val($("input[name='audittype']:checked").val());
		    }
		} else {
		    $("#assesorView").attr("colspan", "2");
            $("#assesorFill").hide();
            $("#assesorRoleView").hide();
		}
		
		$("input[name='audittype']").click(function() {
		    $("#hidaudittype").val($(this).val());
		    if ($(this).val() == "2") {
                $("#assesorView").show();
                $("#assesorRoleView").hide();
		    } else {
		        $("#assesorView").hide();
		        $("#assesorRoleView").show();
		    }
		});
		
	})
	
	function buttonDisable(){
		$("#save").attr("disabled","true");
		$("#cancel").attr("disabled","true");
	}
	
	function validate(){
		var assessor = $("input[name='info.assessor']").val();
		var extensionType = $("input[name='info.extensionType']").val();
		var audittype = $("input[name='audittype']:checked").val();
		if(audittype == '2' && (assessor == null || assessor == '')){
			return alert('请选择审核人');
		}
		if(extensionType == null || extensionType == ''){
			return alert('请选择审核类型');
		}
		return true;
	}

	function save(){
		if(!validate()){
			return;
		}
		buttonDisable();
		$.post('<%=request.getContextPath() %>/manoeuvre/AuditConfiguration_save.html', $('form:last').serialize(), function(data){
			var callback = function(){
				$("#searchForm").submit();
			};
			processDataCall(data, callback);
		}, "json");
	}
	</script>
	
  </head>
  
  <body>
  	<form>
		<div class="tab">
			<input type="hidden" id="aid" name="info.aid" value="${aid }"/>
			<input type="hidden" id="nid" name="info.taskNode.nid" value="${taskNode.nid }"/>
			<input type="hidden" name="info.extensionType" value="${info.extensionType }" />
			<input type="hidden" id="hidaudittype" name="hidaudittype" value="" />
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="6">
							<span>编辑审核设置信息<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="3">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save" type="button" >保 存</button>
								<button id="cancel" type="button" onclick="divClose();">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					
					<tr>
						<th width="25%">
							<span class="red">*</span>
							<c:if test="${aid == null or aid eq '' }">审核人/角色</c:if>
							<c:if test="${aid != null and aid != '' }">审核人</c:if>
						</th>
						<td id="assesorFill">
                            <input type="radio" name="audittype" value="1"/>角色
                            <input type="radio" name="audittype" value="2" checked="checked"/>个人
                        </td>
						<td id="assesorView">
							<ct:selectPerson name="info.assessor" id="assessor" value="${info.assessor}" />
						</td>
						<td id="assesorRoleView">
						    <select name="info.role" id="role">
						        <c:forEach items="${roles}" var="role">
                                <option value="${role.JSDM }">
                                    ${role.JSMC }
                                </option>
                                </c:forEach>
                            </select>
                            
                        </td>
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red">*</span>审核类型
						</th>
						<td colspan="2">
							<select id="extensionTypeSel" name="extensionTypeSel">
			            		<option value="">--- 清空 ---</option>
			            		<option value="0" <c:if test="${info.extensionType == '0'}"> selected="selected" </c:if> > 调入审核 </option>
			            		<option value="1" <c:if test="${info.extensionType == '1'}"> selected="selected" </c:if> > 调出审核 </option>
			            		<option value="2" <c:if test="${info.extensionType == '2'}"> selected="selected" </c:if> > 全部审核 </option>
			            		
			            	</select>	
						</td>
					</tr>
					
					<%--<tr>
						<th>
							<span class="red">*</span>审核范围
						</th>
						<td>
							本部门:<input type="radio" name="info.extension" <c:if test="${info.extension == '0'}"> checked="true" </c:if> value="0" />
							全部:<input type="radio" name="info.extension" <c:if test="${info.extension == '1'}"> checked="true" </c:if> value="1" />
						</td>
					</tr>
					
					--%><tr>
						<th>
							<span class="red"></span>备&nbsp;&nbsp;&nbsp;&nbsp;注
						</th>
						<td colspan="2">
							<input type="text" class="text_nor" name="info.remark" size="35" value="${info.remark }" maxLength="45" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
  </body>
</html>
