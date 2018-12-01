<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var msg='${requestScope.success}';
			if(msg==''){
				msg='操作成功！';
			}
			var parentForm=parent.document.forms['bform'];
			if(parentForm==null)parentForm=parent.document.getElementById('bform');
			parent.tipsMsg('提示消息',msg,'',function(){
				if(parentForm!=null)parentForm.submit();
			});
		});
	</script>
</head>
<body>
</body>
</html>