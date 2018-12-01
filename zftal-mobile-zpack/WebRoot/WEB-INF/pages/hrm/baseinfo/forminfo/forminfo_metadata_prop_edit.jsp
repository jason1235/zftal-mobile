<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	
	<script type="text/javascript">
		$(function(){
			//保存
			$("#save_prop").click(function(){
				$.post('<%=request.getContextPath() %>/baseinfo/forminfometadata_save.html', $('form').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					processDataCall(data,callback);
				},"json");
				
				return false;
			})
			
			//取消
			$("#cancel").click(function(){
				divClose();
				return false;
			})
		})
	</script>
</head>

<body>
	<form>

		<input type="hidden" name="guid" value="${guid }" />
		<input type="hidden" name="formInfoTypeId" value="${model.formInfoTypeId }" />
		<input type="hidden" name="infoProperty.guid" value="${model.infoProperty.guid }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>信息类属性维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save_prop">保 存</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th>
							<span class="red">*</span>属性名称
						</th>
						<td>
							<input type="text" class="text_nor" id="name" name="infoProperty.name" size="25" value="${model.infoProperty.name }" />
						</td>
						<th>
							<span class="red"></span>属性描述
						</th>
						<td>
							<input type="text" class="text_nor" id="description" name="infoProperty.description" size="25" value="${model.infoProperty.description }" />
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>可显示
						</th>
						<td>
							<input type="radio" class="text_nor" name="viewable" value="true" <c:if test="${model.infoProperty.viewable eq true }">checked="checked"</c:if> />是
							<input type="radio" class="text_nor" name="viewable" value="false" <c:if test="${model.infoProperty.viewable eq false }">checked="checked"</c:if> /><font class="red">否</font>
						</td>
						<th>
							<span class="red"></span>可编辑
						</th>
						<td>
							<input type="radio" class="text_nor" name="editable" value="true" <c:if test="${model.infoProperty.editable eq true }">checked="checked"</c:if> />是
							<input type="radio" class="text_nor" name="editable" value="false" <c:if test="${model.infoProperty.editable eq false }">checked="checked"</c:if> /><font class="red">否</font>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>必填
						</th>
						<td>
							<input type="radio" class="text_nor" name="need" value="true" <c:if test="${model.infoProperty.need eq true }">checked="checked"</c:if> />是
							<input type="radio" class="text_nor" name="need" value="false" <c:if test="${model.infoProperty.need eq false }">checked="checked"</c:if> /><font class="red">否</font>
						</td>
						<th>
							<span class="red"></span>默认值
						</th>
						<td>
							${defStyle }
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>