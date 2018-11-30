<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<script type="text/javascript" src="/zfstyle_v5/js/jquery-1.11.0.min.js"></script>
<title></title>
<style type="text/css">
   #main {
   	padding:1rem;
   }

    </style>
</head>
<body>
<div class="am-article" id="main">
    <div style="display:block;text-align:center;font-size:15pt;font-family:Microsoft Yahei;">${entity.title}</div>
	<div style="width:100%;min-height:25pt;font-size:12pt;color:#B5B5B5;padding:1pt;clear:both;">
		<p style="float:left;border-bottom:1px solid #B5B5B5;width:100%"> 
			发布人：${entity.name}&nbsp; &nbsp;发布时间：${entity.timecreatestr}&nbsp; &nbsp;
			<c:if test="${not empty entity.telephone}">
			发布人手机：${entity.telephone}
			</c:if>
		</p>
	</div>
	<div class="am-article-bd" style="margin-top:10pt;font-size:15pt;" id="newcontent">&nbsp;&nbsp;${entity.content}
	<br>
	<c:forEach var="menu" items="${lossObjectPictureList}" >
			<img src="<%=request.getContextPath() %>/${menu.picturePath}">
	  </c:forEach>
	  </div>
	<div style="width:100%;min-height:25pt;font-size:12pt;color:#B5B5B5;padding:1pt;clear:both;">
		<p style="float:left;width:100%"> 
		<c:if test="${entity.flag eq '0'}">
			拾金不昧者：${entity.lossuser}&nbsp; &nbsp;${entity.overtimestr}
		</c:if>
		<c:if test="${entity.flag eq '1'}">
			物件原主人：${entity.lossuser}&nbsp; &nbsp;${entity.overtimestr}
		</c:if>
		</p>
	</div>
</div>


<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script type="text/javascript"> 
	$(function(){
			
	})
</script>	
</body>
</html>