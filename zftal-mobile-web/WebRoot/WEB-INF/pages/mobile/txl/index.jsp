<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %> 
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/hrm/head_v5.ini" %>
<meta name="format-detection" content="telephone=yes">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="telephone=no" name="format-detection" />

<title>黄页通讯录</title>
<link href="<%=request.getContextPath() %>/assets/css/global.css" type="text/css" rel="stylesheet" />

<style type="text/css">
	.f_size14 {
		font-size:14px;
	}
</style>
</head>
<body>
<form action="<%=request.getContextPath() %>/txl/txl_index.html" method="post">
<%--<header class="header">
    	<a id="backbtnid" onclick="goBack()" class="back">返回</a>
        <div class="header_title">学校黄页</div>
</header>    
	--%><div class="search po_ab" style="position: fixed!important;z-index: 1010;-webkit-transform-origin: 0 0;-ms-transform-origin: 0 0;transform-origin: 0 0;">
    	
		<div class="input-group" style="margin-left: 5px;margin-right: 5px;margin-top: 5px;">
					<input type="text" id="bmmc" class="form-control" placeholder="请输入${commentsList[0].theVal },${commentsList[2].theVal }或${commentsList[2].theVal }名称" name="query.bm1" value="${query.bm1 }">
					<span class="input-group-btn">
						
						<button type="submit" id="search" class="btn btn-primary">
						<span class="glyphicon glyphicon-search"> </span>
							
						</button>
					</span>
				</div>
		
	</div>
		<div class="mainframe1" style="top:40px;">
			<div id="list">
				
				<c:set var="sbm" value=""/>
				<div class="title1 text_a_c f_size14" style="margin-left: 10px;" >总共${listSize }条</div>
				<c:forEach var="s" items="${pageList }" varStatus="vs">
						<c:if test="${vs.index==0 }">
							<div class="clearfloat"></div>
							<div class="title1 text_a_c f_size14"></div>
							<div class="list margin_b_0">
								<ul>
							<c:set var="sbm" value="${s.bm }" />
						</c:if>
						
							<c:if test="${s.dh != '' && s.dh != null}">
						<li style="margin: 0px;" >
						<a href="<%=request.getContextPath() %>/txl/txl_detail.html?query.id=${s.id}">
						<span style="display:inline; ">
						<input type="hidden" value="${s.id}" name="id">
								<img name="image" onclick="CallThePhone('${s.dh}');" alt="" 
								src="<%=request.getContextPath() %>/assets/images/reico_tel.png" 
								style="width: 40px;height: 40px;float: left;margin-top:0px;padding-top:0px;border-top: 0px;" />
								<p style="margin-left: 55px;border: 0px;padding: 0px;color:black;">
								<font size="5px" style="font-size:14px;font-family: !important; font-weight:normal;">${s.bm }-${s.ks }</font><br>
								<font size="2px" style="font-weight: normal;">${s.dh }</font></p>
							
						</span>
						</a>
						</li>
							</c:if>	
						
						
				</c:forEach>
					</ul>
					</div>
				 
			</div>
    </div>
</form>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/iscroll.js"></script>
</body>
</html>