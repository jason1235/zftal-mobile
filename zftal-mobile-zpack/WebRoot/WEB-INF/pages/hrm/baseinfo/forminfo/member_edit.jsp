<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="view" uri="/WEB-INF/code.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	
	<script type="text/javascript">
		$(function(){
			$("#save").click( function(){
				if( $("#title").val() == null ) {
					return alert("标题不得为空，请重新输入！");
				}
				
				$.post('<%=request.getContextPath() %>/baseinfo/formInfoMember_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					
					processDataCall(data, callback);
				}, "json");
			});

			$("#cancel").click( function(){
				divClose();
			});
		});
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="name" value="${name }" />
		<input type="hidden" name="classId" value="${classId }" />
		<input type="hidden" name="text" value="" />
		
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>成员信息维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button type="button" id="save">保 存</button>
								<button type="button" id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th><span class="red">*</span>标题</th>
						<td>${model.title }
							<input type="hidden" class="text_nor" id="title" size="35" name="title" value="${model.title }" />
						</td>
					</tr>
					<tr>
						<th><span class="red"></span>注释</th>
						<td>
							<input type="text" class="text_nor" size="35" name="comment" value="${model.comment }" />
						</td>
					</tr>
					<tr style="display: none;">
						<th><span class="red"></span>提示信息</th>
						<td>
							<input type="text" class="text_nor" size="35" name="tooltip" value="${model.tooltip }" />
						</td>
					</tr>
					<tr style="display: none;">
						<th><span class="red"></span>编辑</th>
						<td>
							<input type="radio"  name="editable" value="true" <c:if test="${model.editable == true }"> checked="checked" </c:if> />授权
							<input type="radio"  name="editable" value="false" <c:if test="${model.editable == false }"> checked="checked" </c:if> />不授权
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>