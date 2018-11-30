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
		tipsRefresh(msg,suc);
	});
	
	function tipsRefresh(msg,suc){
		if(suc == 'false'){
			tipsWindown("提示信息","text:"+msg,"340","120","true","","true","id");
		}else{
			add();
		}
		
		$("#window-sure").click(function() {
			divClose();
		});
	}	
	
	function add(){
		var tjrgh = '${expertDeclare.tjrgh }';
		var spBillConfigId = '${expertDeclare.config_id }';
		var id = '${expertDeclare.id }';			
		var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/expertmanage/declare_add.html">';
		content +=' 	<input type="hidden" name="expertDeclare.config_id" value="' + spBillConfigId + '" />';
		content +=' 	<input type="hidden" name="expertDeclare.tjrgh" value="' + tjrgh + '" />';
		content +=' 	<input type="hidden" name="expertDeclare.id" value="' + id + '" />';
		content +='    </form>';
		$('body').append(content);
		$('#form').submit();
		$('#form').remove();
	}
</script>
</head>
<body>
</body>
</html>