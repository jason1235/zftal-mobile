<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<script type="text/javascript">
		$(function(){
			var infoClassId="${config.infoClassId}";
			$("#save").click(function(){
				if(infoClassId==""){
					if( $("#localPropertyId").val() == "" ) {
						alert("数据库字段不得为空，请重新输入！");
						return false;
					}
				}
				
				$.post('<%=request.getContextPath()%>/bill/pushdata_save_property.html', $('#form_bill').serialize(), function(data){
					var callback = function(){
						location.href="<%=request.getContextPath()%>/bill/pushdata_list_property.html?config.id=${config.id}";
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
	<form id="form_bill">
		<input type="hidden" name="property.id" value="${property.id }" />
		<input type="hidden" name="property.eventConfigId" value="${config.id }" />
		<input type="hidden" name="oper" value="${oper}" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>属性绑定<font color="#0f5dc2" style="font-weight:normal;"></font></span>
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
				<tbody id="tbody_bill">
					<tr>
						<th>
							<span class="red">*</span>表单属性
						</th>
						<td>
							<select id="billPropertySelect" name="property.billPropertyId" >
								<c:forEach items="${billProperties }" var="billProperty">
								<option value="${billProperty.id }" 
								<c:if test="${billProperty.id eq property.billPropertyId }" >selected="selected"</c:if> >
								${billProperty.name }</option>
								</c:forEach>
							</select>
						</td>
						<c:if test="${!empty(config.infoClassId)}">
							<th>
								<span class="red">*</span>信息类属性
							</th>
							<td>
								<select id="infoPropertySelect" name="property.localPropertyId" >
									<c:forEach items="${infoProperties }" var="infoProperty">
									<option value="${infoProperty.guid }" 
									<c:if test="${infoProperty.guid eq property.localPropertyId }" >selected="selected"</c:if> >
									${infoProperty.name }</option>
									</c:forEach>
								</select>
							</td>
						</c:if>
						<c:if test="${empty(config.infoClassId)}">
							<th>
								<span class="red">*</span>数据库字段
							</th>
							<td>
								<input type="text" id="localPropertyId" name="property.localPropertyId" value="${property.localPropertyId }"/>
							</td>
						</c:if>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>