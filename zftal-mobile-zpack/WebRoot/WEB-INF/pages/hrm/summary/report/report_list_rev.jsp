<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript">
			$(function(){ 
				fillRows("20", "", "", false);//填充空行
				
				$(".btn_fh_rs").click(function(e){
					$("#selectForm").attr("action","<%=request.getContextPath() %>/summary/report_list.html?id=${id}");
					$("#selectForm").append("<input name='snapTime' value='${snapTime }'/>");
					$("#selectForm").submit();
					e.preventDefault();//阻止默认的按钮事件，防止多次请求
				});
			});
		</script>
	</head>

	<body>
	<form id="selectForm" method="post" >
		
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
					   <c:forEach items="${fieldConfig}" var="field">
					       <td>${field.fieldDesc }</td>
					   </c:forEach>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${pageList}" var="bean">
						<tr name="list_tr">
							<c:forEach items="${fieldConfig}" var="field">
							<td>
							   <c:if test="${field.fieldType == 'CODE'}">
							     <ct:codeParse catalog="${field.fieldFormat}" code="${bean[field.fieldName]}"/>
							   </c:if>
							   <c:if test="${field.fieldType == 'TIME'}">
                                 <fmt:formatDate value="${bean[field.fieldName]}" pattern="${field.fieldFormat}"/>
                               </c:if>
                               <c:if test="${field.fieldType != 'CODE' &&field.fieldType != 'TIME'}">
                                 ${bean[field.fieldName]}
                               </c:if>
                               </td>
	                       </c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<ct:page pageList="${pageList }" queryName="rquery"/>
		<div id="flashcontent"></div>
	</form>
	</body>
</html>