<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript">
			$(function(){ 
				fillRows("20", "", "", false);//填充空行
				
				$(".btn_fh_rs").click(function(e){
					$("#selectForm").attr("action","<%=request.getContextPath() %>/summary/seniorreport_list.html?id=${id}");
					$("#selectForm").submit();
					e.preventDefault();//阻止默认的按钮事件，防止多次请求
				});
			});
		</script>
	</head>

	<body>
	<form id="selectForm" method="post" >
		<input type="hidden" name="snapTime" value="${snapTime }"/>
		<div class="toolbox">
			<!-- 按钮 -->
			<div class="buttonbox">
				<a onclick="" style="cursor: pointer" class="btn_fh_rs" id="back">返 回</a>
			</div>
		</div>
		<div class="con_overlfow">
			<table summary="" class="dateline tablenowrap" align="" width="100%">
				<thead id="list_head">
					<tr>
						<td>职工号</td>
						<td>姓名</td>
						<td>性别</td>
						<td>出生年月</td>
						<td>部门</td>
						<td>参加工作时间</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${pageList}" var="bean">
						<tr name="list_tr">
							<td>${bean.viewHtml['gh']}</td>
							<td>${bean.viewHtml['xm']}</td>
							<td>${bean.viewHtml['xbm']}</td>
							<td>${bean.viewHtml['csrq']}</td>
							<td>${bean.viewHtml['dwm']}</td>
							<td>${bean.viewHtml['rzrq']}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<ct:page pageList="${pageList }"/>
		<div id="flashcontent"></div>
	</form>
	</body>
</html>