<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.zfsoft.common.system.SubSystemHolder" %>
<%-- <%@ include file="/commons/hrm/head.ini"%>--%>
<% String stylePath = SubSystemHolder.getStyleUrl(); %>
<script type="text/javascript">
	$(function(){
		$("a[id^='usual_']").click(function(){
			quickMenu(this);
		});
	});

</script>
<c:forEach items="${userCommons}" var="userComm"
	varStatus="index">
	<c:if test="${index.index < 9 }">
		<li class="col-md-6 col-sm-6 col-xs-6">
			<a id="usual_${userComm.resourceId }" href="#"><img src="<%=stylePath %>images/ico/ico_but${index.index +1 }.png" alt="${userComm.resourceName }"/><h5>${userComm.resourceName }</h5></a>
		</li>
	</c:if>
	<c:if test="${index.index == 9 }">
		<li class="col-md-6 col-sm-6 col-xs-6">
			<a id="usual_${userComm.resourceId }" href="#"><img src="<%=stylePath %>images/ico/ico_but10.png" alt="${userComm.resourceName }"/><h5>${userComm.resourceName }</h5></a>
		</li>
	</c:if>
</c:forEach>
	