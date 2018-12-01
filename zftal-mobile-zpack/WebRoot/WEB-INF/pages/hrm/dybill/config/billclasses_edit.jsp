<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script type="text/javascript">
		$(function(){
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					showWaring("名称不得为空，请重新输入！");
					return false;
				}
				if( $("#idName").val() == "" ) {
					showWaring("表名不得为空，请重新输入！");
					return false;
				}
				
				$.post('<%=request.getContextPath() %>/bill/config_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					
					processDataCall(data, callback);
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
		<input type="hidden" name="spBillConfig.id" value="${spBillConfig.id }" />
		<input type="hidden" name="op" value="${op }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>表单维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
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
							<span class="red">*</span>表单名称
						</th>
						<td>
							<input type="text" class="text_nor" id="name" name="spBillConfig.name" style="width:200px" value="${spBillConfig.name }"  maxlength="16"/>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>表单类型
						</th>
						<td>
							<select name="spBillConfig.billType" style="width:206px">
								<c:forEach items="${spBillConfig.billTypes }" var="billType">
								<option value="${billType }" 
								<c:if test="${billType eq spBillConfig.billType }" >selected="selected"</c:if> >
								${billType.desc }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							数据表名
						</th>
						<td>
							<input type="text" class="text_nor" id="name" name="spBillConfig.idName" style="width:200px"  value="${spBillConfig.idName }"  maxlength="16"
								<c:if test="${!(op eq 'add')}">
								    disabled="disabled"
								</c:if>
							/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>