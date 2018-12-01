<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	fillRows("5", "list_head2", "list_body2", false);//填充空行
	$("span[name='title']").each(function(){
		tooltips(this);
	});
});
</script>
</head>
<body>
<div id="testID" >    
  <div class="tab" id="defineInfo">
	<table align="center" class="formlist">
		<thead id="list_head2">
			<tr>
				<th width="25%">角色</th>
				<th width="15%">操作人</th>
				<th width="15%">状态</th>
				<th width="35%">审核时间</th>
				<th width="10%">意见</th>
			</tr>
		</thead>
		<tbody id="list_body2">
			<s:iterator value="infoList" var="infor">
			<tr>
			   <td width="25%"><ct:RoleParse code="${infor.roleId }"/></td>
			   <td width="15%"><ct:PersonParse code="${infor.operator }" /></td>
			   <td width="15%">
			   		<c:if test="${infor.status == -1}">重置</c:if>
			   		<c:if test="${infor.status == 0}">拒绝</c:if>
			   		<c:if test="${infor.status == 1}">同意</c:if>
			   </td>
			   <td width="35%"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			   <td><span name="title" title="${infor.info }" style="overflow: hidden;display:block;text-overflow: ellipsis;white-space: nowrap;width: 50px;">${infor.info }</span></td>
			</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>
