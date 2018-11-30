<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
</script>
</head>
<body>
<div id="testID" >    
  <div class="tab">
	<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="4">
					<span id="title">消息信息<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th>标题</th>
				<td>${msg.title }</td>
				
			</tr>
			<tr>
				<th>发送时间</th>
				<td><s:date name="msg.sendTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<th>发送人</th>
		   		<td><ct:PersonParse code="${msg.sender }"/></td>
			</tr>
			<tr>
			    <th>接收人类型</th>
			    <td>${msg.receiverTypeMc }</td>
			</tr>
		   	<tr>
		   		<th>接收人</th>
		   		<td>
		   		<c:if test="${msg.receiverType eq '1' }">
		   		${msg.roleMc }
		   		</c:if>
		   		<c:if test="${msg.receiverType != '1' }">
		   		<ct:PersonParse code="${msg.receiver }"/>
		   		</c:if>
		   		</td>
		   	</tr>
		   	<tr>
		   		<th>内容</th>
				<td><ct:ContentExplain content="${msg.content }" /></td>
		   	</tr>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>
