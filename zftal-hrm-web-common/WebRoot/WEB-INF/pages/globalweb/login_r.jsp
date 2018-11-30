<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	</head>
	<body></body>
</html>
<script type="text/javascript">
	var aTag=$("<form method=\"post\"></form>");
	aTag.attr("action","<%=request.getContextPath() %>/xtgl/login_loginpage.html");
	aTag.appendTo("body");
	//aTag.submit();
	var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        var s;
        (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
        (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
        (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
        (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
 
        
        if (Sys.ie) {
        	var ver = parseInt(Sys.ie);
        	if (ver < 8) {
        		
        		aTag.attr("action","<%=request.getContextPath() %>/xtgl/login_upgrade.html");
        		aTag.submit();
        	} else {
        		aTag.submit();
        	}
        } else {
        	aTag.submit();
        }
</script>
