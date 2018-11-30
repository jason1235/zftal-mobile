<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="class" uri="/WEB-INF/infoclasstag.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#save").click( function(){
				$.post('<%=request.getContextPath() %>/baseinfo/formInfoMemberProperty_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					
					processDataCall(data, callback);
				}, "json");
			});

			$("#cancel").click( function(){
				divClose();
			});

			$("#need").val("${model.need}");
		});
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="member.name" value="${member.name }" />
		<input type="hidden" name="member.classId" value="${member.classId }" />
		<input type="hidden" name="pname" value="${model.pName }"/>
		
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>成员属性信息维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
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
						<th><span class="red"></span>属性名</th>
						<td>
							<class:property name="${model.pName }" classId="${member.classId }"/>
						</td>
						<th><span class="red"></span>默认值</th>
                        <td>
                            ${defStyle }
                        </td>
					</tr>
					<tr>
					   <th><span class="red"></span>显示</th>
                        <td>
                            <input type="radio" name="viewable" value="true" <c:if test="${model.viewable == true}">checked="checked"</c:if> /><font class="">授权</font>
                            <input type="radio" name="viewable" value="false" <c:if test="${model.viewable == false}">checked="checked"</c:if> /><font class="red">不授权</font>
                        </td>
						<th><span class="red"></span>编辑</th>
						<td>
						    <input type="radio" name="editable" value="false" <c:if test="${model.editable == false}">checked="checked"</c:if> /><font class="red">不授权</font>
							<input type="radio" name="editable" value="true" <c:if test="${model.editable == true}">checked="checked"</c:if> /><font class="">授权</font>
							<select id="need" name="model.need">
								<option value="false">非必填</option>
								<option value="true">必填</option>
							</select>
						</td>
					</tr>
					<tr style = 'display: none;'>
						<th><span class="red"></span>最小代码长度</th>
						<td>
							<input type="text" class="text_nor" size="25" name="minLength" style="ime-mode:disabled" onkeypress="check_int_dot(this);" value="${model.minLength }" />
						</td>
						<th><span class="red"></span>高亮显示</th>
                        <td>
                            <input type="radio" name="highlight" value="true" <c:if test="${model.highlight == true}">checked="checked"</c:if> /><font class="">是</font>
                            <input type="radio" name="highlight" value="false" <c:if test="${model.highlight == false}">checked="checked"</c:if> /><font class="red">否</font>
                        </td>
					</tr>
					<tr style = 'display: none;'>
						<th><span class="red"></span>警告信息</th>
						<td>
							<input type="text" class="text_nor" size="25" name="alert" value="${model.alert }" />
						</td>
						<th><span class="red"></span>&nbsp;</th>
						<td>&nbsp;</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>