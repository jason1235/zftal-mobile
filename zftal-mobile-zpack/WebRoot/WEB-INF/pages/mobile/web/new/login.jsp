<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<title></title>
<style type="text/css">
        
    </style>
</head>
<body>
	<script type="text/x-handlebars-template" id="amz-tpl">
    {{>header header}}

	<form action="<%=request.getContextPath()%>/mobile/web_handler.html" method="post" class="am-form" style="padding:2rem">
	<input type="hidden" id="module" name="module" value="${module}">
    <div class="am-form-group am-form-icon">
        <i class="am-icon-user am-icon-sm am-icon-fw" style="color:#ccc;position:relative;top:2.8rem"></i>
        <input name="username" type="text" class="am-form-field am-input-lg am-radius" style="background-color:#EBEBEB;border:none" placeholder="用户名">
    </div>

    <div class="am-form-group am-form-icon">
        <i class="am-icon-lock am-icon-sm am-icon-fw" style="color:#ccc;position:relative;top:2.8rem"></i>
        <input name="password" type="password" class="am-form-field am-input-lg am-radius" style="background-color:#EBEBEB;border:none" placeholder="密码">
    </div>
	<div id="errorMsg" style="color:red">${errorMsg}</div>
    <button type="submit" class="am-btn am-btn-xl am-btn-primary am-radius am-btn-block" style="background-color:#1157AA">登录</button>
    </form>	
</script>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script type="text/javascript"> 
	
    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    var title = data.header.content.title;
    data.header.content.left = [{link:"javascript:history.go(-1)",icon:"chevron-left"}];
    data.header.content.title = "登录";
    var html = template(data);
    $tpl.before(html);
    data.header.content.title = title;
    data.header.content.left = [];
    $(function(){
    	
    })
</script>	
</body>
</html>