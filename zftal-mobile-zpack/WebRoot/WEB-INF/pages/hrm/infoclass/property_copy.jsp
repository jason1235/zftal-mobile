<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	
	<script type="text/javascript">
		$(function(){
			
			//取消
			$("#cancel").click(function(){
				$("#sform").submit();
				return false;
			})
			
			//保存
			$("#save").click(function(){
				$.post("<%=request.getContextPath() %>/infoclass/infoproperty_copy.html", $("#bform").serialize(), function(data){
					tipsWindown("提示信息", "text:"+data.html, "340", "120", "true", "", "true", "id");
					
					$("#window-sure").click(function() {
						divClose();
						
						$("#sform").submit();
					})
				}, "json");
				
				return false;
			})
			
			//信息类变更
			$("#classId").change(function(){
				$("#bform").submit();
			})
			
		})
	</script>
	
</head>
<body>
	<form id="sform" action="<%=request.getContextPath() %>/infoclass/infoproperty_list.html" method="post">
		<input type="hidden" name="classId" value="${model.classId }"/>
	</form>
	
	<form id="bform" action="<%=request.getContextPath() %>/infoclass/infoproperty_toCopy.html" method="post">
		<input type="hidden" name="classId" value="${model.classId }"/>
	<div class="tab">
		<table width="100%"  border="0" class="formlist">
			<thead>
				<tr>
					<th colspan="2"><span>信息类属性复制</span></th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<td colspan="2">
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
						信息类
					</th>
					<td>
						<select id="classId" name="classId" style="width: 185px;">
							<c:forEach items="${classes}" var="clasz">
							<c:if test="${(model.classId eq clasz.guid) eq false}">
							<option value="${clasz.guid }" <c:if test="${clasz.guid eq classId }">selected="selected"</c:if>>${clasz.name }</option>
							</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="tdbox">
						<table width="100%" class="dateline">
							<thead id="list_head">
								<tr>
									<td><input disabled="disabled" type="checkbox" /></td>
									<td>属性名称</td>
									<td>字段名称</td>
									<td>字段类型</td>
									<td>字段长度</td>
									<td>可显示</td>
									<td>引用代码表</td>
								</tr>
							</thead>
							<tbody id="list_body">
								<c:forEach items="${classes}" var="clasz">
									<c:if test="${clasz.guid eq classId}">
										<c:forEach items="${clasz.properties }" var="bean" varStatus="i">
								<tr>
									<td><input name="items" type="checkbox" value="${bean.guid }"/></td>
									<td>${bean.name }</td>
									<td>${bean.fieldName }</td>
									<td>${bean.typeInfo.text }</td>
									<td>${bean.fieldLen }</td>
									<td>
										<c:if test="${bean.viewable eq true}">是</c:if>
										<c:if test="${bean.viewable eq false}"><font class="red">否</font></c:if>
									</td>
									<td>${bean.codeName }</td>
								</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</form>
	
	<script type="text/javascript">
		fillRows("15", "list_head", "list_body", true);
	</script>
</body>
</html>
