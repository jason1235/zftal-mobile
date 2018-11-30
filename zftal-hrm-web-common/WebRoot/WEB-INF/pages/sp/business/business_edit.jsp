<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$("#save").click(function(){
				$.post('<%=request.getContextPath() %>/sp/spbusiness_saveBusiness.html', $('form:last').serialize(), function(data){
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
			
			$("input[name='classIds']").click(function(e){
				if(this.checked){
					var html=
							"<h2>权限<select name=\"classPrivilege\">"
							+ "<option value=\"SEARCH\">查询</option>"
							+ "<option value=\"SEARCH_EDIT\">查询-编辑</option>"
							+ "<option value=\"SEARCH_ADD_DELETE\">查询-增加-删除</option>"
							+ "<option value=\"SEARCH_ADD_DELETE_EDIT\" selected=\"selected\">查询-增加-删除-编辑</option>"
							+ "</select></h2>";
					$(this).closest("div").append(html);
				}else{
					$(this).next().remove();
				}
			});
			
			$("#cancel").click(function(){
				divClose();
				return false;
			});
		});
		
		function searchBillClass(obj,id,postApplyId,oldNum){
			if(validate(obj,oldNum)){
				var param = '?postApply.id=' + postApplyId + '&id=' + id + '&score=' + $(obj).val();
				$.post('<%=request.getContextPath()%>/introduce/admission_setScore.html' + param,null,function(data){
				},"json");
			}
		}
	</script>
</head>

<body>
	<form>
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>业务流程编辑<font color="#0f5dc2" style="font-weight:normal;"></font></span>
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
<%--						<th>--%>
<%--							<span class="red"></span>编号--%>
<%--						</th>--%>
<%--						<td>--%>
<%--							${spBusiness.bid }--%>
<%--							<input type="hidden" name="spBusiness.bid" value="${spBusiness.bid }"/> --%>
<%--						</td>--%>
						<th>
							<span class="red"></span>业务名称
						</th>
						<td>
							${spBusiness.bname}
							<input type="hidden" name="spBusiness.bid" value="${spBusiness.bid }"/>
							<input type="hidden" name="spBusiness.bname" value="${spBusiness.bname }"/> 
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>流程名称
						</th>
						<td>
							<select name="spBusiness.pid">
								<option value="">无 </option>
								<c:forEach items="${procedureList}" var="procedure">
									<option value="${procedure.pid}" <c:if test="${procedure.pid eq spBusiness.pid}"> selected='selected'</c:if>>${procedure.pname}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>初始化申报表单
						</th>
						<td>
						<!--  
							<select name="spBusiness.billId" disabled="disabled">
								<option value="">无 </option>
								<c:forEach items="${spBillConfigs}" var="billConfig">
									<option value="${billConfig.id}" <c:if test="${billConfig.id eq spBusiness.billId}"> selected='selected'</c:if>>${billConfig.name}</option>
								</c:forEach>
							</select>
						-->
							<c:forEach items="${spBillConfigs}" var="billConfig">
								<c:if test="${billConfig.id eq spBusiness.billId}">
								${billConfig.name}
								<input name="spBusiness.billId" type="hidden" value="${billConfig.id}" />
								</c:if>
							</c:forEach>
						</td>
					</tr>
					
					<c:if test="${fn:length(billClassList) > 0}">
					<tr>
						<th>
							<span class="red"></span>表单内容
						</th>
						<td>
							<div style="overflow-y:auto;height: 130px;width: 100%">
								<c:forEach items="${billClassList}" var="billClass">
								<div>
									<input type="checkbox" name="classIds" value="${billClass.classId}" <c:if test="${billClass.checked eq true }"> checked="checked"</c:if>/>${billClass.className}
									<c:if test="${billClass.checked eq true  }">
										<h2>
											权限
											<select name="classPrivilege">
												<option value="SEARCH" <c:if test="${billClass.classesPrivilege eq 'SEARCH' }"> selected="selected"</c:if>>查询</option>
												<option value="SEARCH_EDIT" <c:if test="${billClass.classesPrivilege eq 'SEARCH_EDIT' }"> selected="selected"</c:if>>查询-编辑</option>
												<option value="SEARCH_ADD_DELETE" <c:if test="${billClass.classesPrivilege eq 'SEARCH_ADD_DELETE' }"> selected="selected"</c:if>>查询-增加-删除</option>
												<option value="SEARCH_ADD_DELETE_EDIT" <c:if test="${billClass.classesPrivilege eq 'SEARCH_ADD_DELETE_EDIT' }"> selected="selected"</c:if>>查询-增加-删除-编辑</option>
											</select>
										</h2>
									</c:if>
								</div>
								</c:forEach>
							</div>
						</td>
					</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
