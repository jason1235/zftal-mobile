<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/normal/staffinfo/staffinfo.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript"> 
   
    $(function(){
    	hasBusiness=${hasBusiness};
    	buttonInitialize();//简历页面按钮事件初始化
    	menuLayerInitialize();//菜单层事件初始化
    	allInfoLayerDatafill();//所有信息层数据填充，即请求数据
    	
    	$("#log").click(function(e){
    		var gh = $("input[name='gh']").val();
    		goUrl(_path+"/baseinfo/dynalog_userPage.html?classId=${clazz.guid}&gh="+gh);
    		e.stopPropagation();
    	});
    	
    	 $(".btn_ck").click(function(){
    		 location.href = _path+"/infochange/infochange_page.html?query.classId=${clazz.guid}&query.status=INITAIL";
    	 });
    });

    </script>
  </head>
  <body>
  <div id="position-fixed" style="top:0; background:#fff;">
 	 <div class="title_xxxx">
		<span class="people_xx">${bean.values.xm } （职工号： ${bean.values.gh }）<input type="hidden" name="gh" value="${bean.values.gh }"/><input type="hidden" name="globalid" value="${bean.values.globalid }"/></span>
		<c:if test="${hasBusiness }">
			&emsp;<a class="btn_ck">审核查看</a>
		</c:if>
	</div>
  </div>
    
	<div class="demo_xxxx" id="${clazz.guid }" >
	<h3 class="college_title" style="cursor: pointer;">
		<span class="title_name">${clazz.name }</span>
		<!-- <a id="log" style="cursor:pointer;">日志查询</a> -->
		<a id="open" class="down" style="cursor:pointer;">展开</a>
		<div style="display:none;">
		<c:forEach items="${ancdModelList }" var="btn">
		<span>${btn.czdm }</span>
		</c:forEach>
		</div>
	</h3>
	<div name="conDiv" style="display:none;"></div>
	</div>
  </body>
</html>
