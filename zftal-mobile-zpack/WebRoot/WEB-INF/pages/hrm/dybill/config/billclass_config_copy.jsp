<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<script type="text/javascript">
		$(function(){
			$("#choice").change(function(){
				var spBillConfigId=$(this).children("option:selected").val();
				$.post('<%=request.getContextPath() %>/bill/config_getXmlBillClasses.html?spBillConfig.id='+spBillConfigId, null, 
					function(data){
						var html="";
						for(var i=0 ;i<data.length;i++){
							if(i==0){
								html+="<input type=\"radio\" name=\"copyBillClassId\" value=\""+data[i].id+"\" checked=\"checked\">"+data[i].name;
							}else{
								html+="<input type=\"radio\" name=\"copyBillClassId\" value=\""+data[i].id+"\">"+data[i].name;
							}
						}
						$("#classDiv").html(html);
					}
				,"json");
			});
			$("#save").click(function(){
				if($("#identityName").val()==''||$("#identityName").val()==null){
					alert("新标示名不能为空");
					return false;
				}
				$.post('<%=request.getContextPath() %>/bill/config_saveCopyXmlBillClass.html', $('form:last').serialize(), function(data){
					var callback = function(){
						location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassList.html?spBillConfig.id=${spBillConfig.id }";
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
		<input type="hidden" name="spBillConfig.id" value="${spBillConfig.id }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>引用表单选择<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">确认</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody id="tbody_bill">
					<tr>
						<th>
							<span class="red">*</span>新标示名
						</th>
						<td>
							<input type="text" name="xmlBillClassBean.identityName" id="identityName">
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>表单选择
						</th>
						<td>
							<select id="choice" name="copyBillConfigId" style="width:206px;">
							<c:forEach items="${spbillconfigs}" var="spbillconfig">
								<option value="${spbillconfig.id}" >${spbillconfig.name}</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					<tr id="xchoice">
						<th>
							<span class="red"></span>类选择
						</th>
						<td>
							<div style="height:200px" id="classDiv">
							<c:forEach items="${billClasses}" var="billClass" varStatus="i">
								<input type="radio" name="copyBillClassId" value="${billClass.id}" <c:if test="${i.index eq 0 }">checked="checked"</c:if>>${billClass.name}
							</c:forEach>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>