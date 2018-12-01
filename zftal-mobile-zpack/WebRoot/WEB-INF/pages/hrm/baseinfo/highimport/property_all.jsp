<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

	<script type="text/javascript">
		$(function(){
		})		
	</script>
</head>

<body>
	<div class="formbox">
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head" width="100%">
				<tr>
					<td width="10%"><input type="checkbox" disabled/></td>
					<td width="10%">序号</td>
					<td width="20%">属性名称</td>
					<td width="20%">字段名称</td>
					<td width="20%">字段类型</td>
					<td width="40%">字段长度</td>
				</tr>
			</thead>
			<tbody id="list_body" width="100%">
				<c:forEach items="${infoPropertyList }" var="bean" varStatus="i">
				<tr id="${bean.guid }">
					<td><input name="items" type="checkbox" value="${bean.guid }"/></td>
					<td>${i.index+1 }</td>
					<td>${bean.name }</td>
					<td>${bean.fieldName }</td>
					<td>${bean.typeInfo.text }</td>
					<td>${bean.fieldLen }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>