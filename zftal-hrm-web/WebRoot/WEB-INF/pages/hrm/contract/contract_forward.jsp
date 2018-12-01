<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>
	<s:i18n name="struts" >
	<s:text name="struts.i18n.title" /></s:i18n>
</title>
<%@ include file="/commons/hrm/head.ini" %>
<script type="text/javascript">
	$(document).ready(function() {
		var msg='${requestScope.forward.html}';
		var suc='${requestScope.forward.success}';
		parent.tipsRefresh(msg,suc);
	});
	
</script>
</head>
<body>
</body>
</html>