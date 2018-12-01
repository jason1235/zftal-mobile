<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="http://www.zfsoft.com:82/zfstyle_v4/js/uicomm.js"></script>
	<script type="text/javascript" src="http://www.zfsoft.com:82/zfstyle_v4/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/application.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>
	<div class="prompt">
  <p>当前学生总人数：<font color="#f06600"><b>${model.data }</b></font>人</p>
  </div>
	<!---------------------展示 start------------------->
	<div class="demo_college">
	
		<c:forEach items="${list}" var="con">
		<h3 class="college_title">
			<span class="title_name" >
				<a href="#" style="text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;">${con.title }</a>
			</span>
			<span>总计:${con.result.count}</span>
		</h3>
		<div class="con after">
	    	<ul>
	    	<c:forEach items="${con.children}" var="var2">
	        	<li><a class="college_li" href="<%=request.getContextPath() %>/normal/overallInfo_stulist.html?conditionId=${var2.guid}">${var2.title }<span>(总计:${var2.result.count})</span></a></li>
	        </c:forEach>
			</ul>
		</div>
		</c:forEach>
	</div>
		
	<!---------------------展示 end------------------->
	
	<%@ include file="/WEB-INF/pages/hrm/buttonFilter.jsp" %>
  </body>
</html>
