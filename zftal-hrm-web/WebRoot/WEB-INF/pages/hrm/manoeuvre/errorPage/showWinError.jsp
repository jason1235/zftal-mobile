<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<html>
	<head>
		<title>Error Page</title>
		<script type="text/javascript">
			$(function(){
				var msg='${requestScope.message}';
				if(msg==''){
					msg='操作失败！';
				}
				showWarning(msg);
			})
		</script>
	</head>
	<body>
		
	</body>
</html>