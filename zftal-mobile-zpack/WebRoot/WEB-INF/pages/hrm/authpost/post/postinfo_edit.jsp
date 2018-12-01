<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					alert("岗位名称不得为空，请重新输入！");
					return false;
				}
				
				if( $("#id").val() == "" ) {
					alert("岗位编码不得为空，请重新输入！");
					return false;
				}
				
				$.post('<%=request.getContextPath() %>/post/postinfo_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					processDataCall(data,callback);
				},"json");

				return false;
			})
			
			$("#cancel").click(function(){
				divClose();
				return false;
			})
		})
	</script>
</head>

<body>
	<form>
		<div class="tab">
			<input type="hidden" id="opState" name="opState" value="${opState }"/>
			<input type="hidden" id="sort" name="sort" value="${sort }"/>
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>岗位信息维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
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
							<span class="red"></span>所属类别
						</th>
						<td>
						 	<ct:codePicker name="model.typeCode" catalog="<%=ICodeConstants.DM_DEF_WORKPOST %>" code="${model.typeCode }" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>岗位编码
						</th>
						<td>
							<input type="text" <c:if test="${opState eq 'modify'}"> readonly="readonly" class="text_nobor" </c:if>
											   <c:if test="${opState eq 'add'}"> class="text_nor" </c:if>  id="id" name="id" size="35" maxlength="10"  value="${model.id }" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>岗位名称
						</th>
						<td>
							<input type="text" class="text_nor" id="name" name="name" size="35" maxlength="32" value="${model.name }" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>