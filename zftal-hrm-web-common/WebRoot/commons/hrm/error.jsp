<%@page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@page import="org.apache.commons.logging.LogFactory" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	
	<script type="text/javascript">
	$(document).ready(function() {
		var msg='${requestScope.message}';
		var url='${requestScope.back}';
		if(msg==''){
			msg='操作失败！';
		}
		var parentForm=parent.document.forms['bform'];
		var parentWindow=parent.window;
		if(parentForm==null)parentForm=parent.document.getElementById('bform');
		parent.tipsMsg('提示消息',msg,'error',function(){
			if(url!=''){
				parentWindow.location.replace( '<%=request.getContextPath() %>' + url );
			}
			if(parentForm!=null)parentForm.submit();
		});
	});
	</script>	
</head>

<body>
</body>
</html>