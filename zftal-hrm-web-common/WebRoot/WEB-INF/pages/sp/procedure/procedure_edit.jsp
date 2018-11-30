<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	
	<script type="text/javascript">
		$(function(){
			
			$("#save").click(function(){
				var pname=$("input[name='spProcedure.pname']").val();
				if(pname==null||pname==""){
					showWarning("流程名称不能为空!");
					return false;
				}
				$.post('<%=request.getContextPath() %>/sp/spprocedure_saveProcedure.html', $('form:last').serialize(), function(data){
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					
					$("#window-sure").click(function() {
						divClose();
						
						if( data.success ) {
							window.location.reload();
						}
					});
				}, "json");

				return false;
			});
			
			$("#cancel").click(function(){
				divClose();
				return false;
			});
		});
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="spProcedure.pid" value="${spProcedure.pid }"/> 
		<input type="hidden" name="spProcedure.pstatus" value="${spProcedure.pstatus }"/> 
		<input type="hidden" name="op" value="${op }"/>
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>流程设置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">保 存</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th>
							<span class="red">*</span>流程名称
						</th>
						<td>
							<input type="text" name="spProcedure.pname" value="${spProcedure.pname }"/>
						</td>
						<th>
							<span class="red">*</span>流程类型
						</th>
						<td>
							<select name="spProcedure.ptype">
								<c:forEach items="${ptypes}" var="ptype">
									<option value="${ptype.key }" <c:if test="${ptype.key eq spProcedure.ptype}"> selected="selected"</c:if>>${ptype.text }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>申报表单
						</th>
						<td>
							<select name="commitBillIds">
							<option name="commitBillIds" value="">无</option>
								<c:forEach items="${commitBillList}" var="commitBill">
									<option name="commitBillIds" value="${commitBill.billId }" <c:if test="${commitBill.checked eq true }"> selected="selected"</c:if>>${commitBill.billName}</option>
								</c:forEach>
							</select>
						</td>
						<th>
							<span class="red"></span>审核表单
						</th>
						<td>
							<select name="approveBillIds">
							<option name="approveBillIds" value="">无</option>
								<c:forEach items="${approveBillList}" var="approveBill">
										<option name="approveBillIds" value="${approveBill.billId }" <c:if test="${approveBill.checked eq true }"> selected="selected"</c:if>>${approveBill.billName}</option>
								</c:forEach>
							</select>
						</td>
						
					</tr>
					<tr>
						<th>
							<span class="red"></span>审核页面链接
						</th>
						<td>
							<input type="text" name="spProcedure.link" value="${spProcedure.link }" /><span class="red">说明：代办事宜跳转使用</span>
						</td>
						<th>
							<span class="red">*</span>业务模块
						</th>
						<td>
							<select name="spProcedure.belongToSys">
								<c:forEach items="${belongToSyses}" var="belongToSys">
									<option value="${belongToSys.gnmkdm }" <c:if test="${belongToSys.gnmkdm  eq spProcedure.belongToSys}"> selected="selected"</c:if>>${belongToSys.gnmkmc }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>流程描述
						</th>
						<td colspan="3"> 
							<textarea rows="3" cols="70" name="spProcedure.pdesc">${spProcedure.pdesc }</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
