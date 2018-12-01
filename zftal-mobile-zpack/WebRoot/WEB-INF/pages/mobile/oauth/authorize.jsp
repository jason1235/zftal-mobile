<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>Insert title here</title>
<%@ include file="/commons/hrm/head_v5.ini" %>
<style type="text/css">
html,body{ margin:0px; height:100%; background:white;}
</style>
<script type="text/javascript">
		$(function(){
		var urlinfo = window.location.href;//獲取url
		var businessname = decodeURI(urlinfo.substring(urlinfo.lastIndexOf("=")+1,urlinfo.length));//拆分url得到”=”後面的參數
		$("#businessname").append("-该网页由"+businessname+"开发，向其提供以下权限即可继续操作");
			$("#save_btn").click(function(){
				$.post("<%=request.getContextPath()%>/oauth/oauth_authorizeForYh.html",
	                $("#form_edit").serialize(),function(data){
							if(data.success){
								window.location.href=data.redirect_uri;
							}
	            	},"json");
				 return false;
			});
			
		})
		
	
</script>
</head>
<body>
<form action="<%=request.getContextPath()%>/oauth/oauth_authorizeForYh.html" name="search" id="form_edit" method="post">
	<div style="width: 100%; background: #ececec;" style="background: white;">

            <img src="<%=request.getContextPath()%>/img/access.gif" alt="" style="width: 100%;height: auto;;">

    </div>
    <div style="width: 100%; "style="background: white;">

        <div  style="background: white; width: 100%; padding-top: 10px">

            <div style="width: 98%; font-size: 12px; color: #222; padding: 10px;" id="businessname">
                <i style="font-style: normal; color: #ff5656; font-weight: bold;">说明</i> 
                
                </div>
            <div style="width: 98%; font-size: 12px; color: #222; padding: 10px;">
            <input type="hidden" value="${param.access_token }" name="access_token">
            <input type="hidden" value="${param.procode }" name="procode">
            <input type="hidden" value="${param.timeout }" name="timeout">
            <input type="hidden" value="${param.redirect_uri }" name="redirect_uri">
               <input type="checkbox" disabled="true" checked="checked">
                获得你的公开信息（用户名、姓名、等第三方需要信息）
            </div>
        </div>
    </div>

    <div style=" width: 100%; padding-top: 10px;">
        <button id="save_btn" type="button"" style="border: none; color:#fff; background:#2a70cc;width:95%;margin-left:2%;font-size: 14px; text-align: center; height: 40px;">
       		<font color="#FFFFFFFF" >确认登录</font> 
        </button>
    </div>
</form>
</body>
</html>