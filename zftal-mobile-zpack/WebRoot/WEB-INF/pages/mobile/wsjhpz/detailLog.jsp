<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 1.0 Transitional//EN">
<html>
  <head>
    <title></title>
	<jsp:include page="/common/ini/head.ini"/>
	<script language="javascript">
	$(document).ready(function(){
		var doc = $(window.parent.document);
		//所有的关闭按钮点击事件
		$("button[id='']").click(function(){
			doc.find("#windownbg").remove();
			doc.find("#windown-box").fadeOut("fast",function(){
				$(this).remove();
			});
		})
	});

</script> 
  </head>
<body  bgcolor="#F8F8D8">
<div class="listbox">
	<div class="title">
	<h3>日志详情</h3>
  </div>
  <div class="con" style="height:300;" align="left" >
  	<%=request.getAttribute("detailLog")%>
  </div>
</div>

</body>
</html>
