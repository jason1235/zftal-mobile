<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<script type="text/javascript">
		$(function(){
			
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					alert("事件名称不得为空，请重新输入！");
					return false;
				}
				
				$.post('<%=request.getContextPath()%>/billgrade/config_save_config.html', $('#form_bill').serialize(), function(data){
					var callback = function(){
						location.href="<%=request.getContextPath()%>/billgrade/config_list_config.html";
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
		<input type="hidden" name="config.id" value="${config.id }" />
		<input type="hidden" name="oper" value="${oper }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>表单事件维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
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
				<tbody id="tbody_bill">
					<tr>
						<th width="80px">
							<span class="red">*</span>事件名称
						</th>
						<td width="120px">
							<input type="text" class="text_nor" id="name" name="config.name" value="${config.name }"  maxlength="255"/>
						</td>
						</tr>
						<tr>
						<th>
                            <span class="red">*</span>表单
                        </th>
                        <td>
                            <select id="billConfigSelect" name="config.billConfigId" >
                                <c:forEach items="${billConfigs }" var="billConfig">
                                <option value="${billConfig.id }" 
                                <c:if test="${billConfig.id eq config.billConfigId }" >selected="selected"</c:if> >
                                ${billConfig.name }</option>
                                </c:forEach>
                            </select>
                        </td>
					</tr>
					<tr>
						<th width="100px">
							<font id="business" style="float: right;">所属业务</font><span style="float: right;" class="red">*</span>
						</th>
						<td>
							<select id="infoClassSelect" name="config.businessCode">
								<c:forEach items="${types }" var="t">
								<option value="${t.key }" >${t.text }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>