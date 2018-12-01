<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	$.post(_path+'/sp/spworkflow_info.html',$("input[name='workId']").serialize(),function(data){
		if(data.success){
			$("#content").html(data.html);
		}else{
			alert("数据加载失败:"+data.text);
		}
	},"json");
});
</script>
</head>
<body>
<input type="hidden" name="workId" value="${workId }"/>
<div id="content"></div>
</body>
</html>
