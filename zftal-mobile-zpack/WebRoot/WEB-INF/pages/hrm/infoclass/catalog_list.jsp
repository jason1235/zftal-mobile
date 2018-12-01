<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	
	<script type="text/javascript">
		function addItem()
		{
			bform.action = "<%=request.getContextPath() %>/infoclass/infoclasscatalog_edit.html";
			bform.submit();
		}

		function modifyItem(guid)
		{
			bform.guid.value = guid;
			bform.action = "<%=request.getContextPath() %>/infoclass/infoclasscatalog_edit.html";
			bform.submit();
		}

		function removeItem(guid)
		{
			bform.guid.value = guid;
			bform.action = "<%=request.getContextPath() %>/infoclass/infoclasscatalog_remove.html";
			bform.submit();
		}
		
		function refresh()
		{
			bfrom.submit();
		}
	</script>
</head>

<body>
	<form name="bform" action="<%=request.getContextPath() %>/infoclass/infoclasscatalog_list.html" method="post">
		<input type="hidden" id="guid" name="model.guid" value="" />
	</form>
	
	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li><a onclick="addItem();" class="btn_zj">增加</a></li>
				<li><a onclick="refresh();" class="btn_sx">刷新</a></li>
			</ul>
		</div>
	</div>
	
	<div class="formbox">
		<h3 class="datetitle_01">
			<span>目录信息<font color="#0457A7" style="font-weight: normal">（提示：信息类目录列表）</font></span>
		</h3>
		<table width="100%" class="dateline">
			<thead>
				<tr>
					<td>索引</td>
					<td>目录名称</td>
					<td>显示顺序</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="bean" varStatus="i">
				<tr>
					<td>${i.index + 1 }</td>
					<td>${bean.name }</td>
					<td>${bean.index }</td>
					<td>
						<a href="#" onclick="modifyItem('${bean.guid }');">修改</a>
						<a href="#" onclick="removeItem('${bean.guid }');">删除</a>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>