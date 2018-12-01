<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.zfsoft.util.date.TimeUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0039)http://www.sojump.com/mobile/login.aspx -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/WEB-INF/pages/wechat/mobilePage.ini"%>
        <script type="text/javascript"
            src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=systemPath %>/css/wechat/mobile.css"/>
    <title>
            用户绑定
    </title>
<style>
#getYzm, .getYzm
{
    background:none;
    padding:0;
}
</style>
<script type="text/javascript">
function isEmpty(dataid){
    if($('#' + dataid).val() != null && $('#' + dataid).val() != ''){
        return false;
    }else{
        return true;
    }
}
function showErrMsg(message){
    $(".errors").html("<span style='color:red'>"+message+"</span>");
}
//登录
function dl() {
	if(isEmpty('yhm')){
        showErrMsg('用户名不能为空！');
        return false;
    }
    if(isEmpty('mm')){
        showErrMsg('密码不能为空！');
        return false;
    }
                
    $("#btn_dl").attr("disabled","disabled");
    $(".errors").html("<span style='color:blue'>账号绑定中，请稍候......</span>");
    $.post('<%=systemPath %>/wechat/login_binding.html', $('#form :input').serialize(), function(data){
        $("#btn_dl").removeAttr("disabled");
        if(data.success==false){
            showErrMsg(data.text);
            $("#count").val(data.logincount);
             if(data.logincount>=3){
                 $("#yzmdiv").css("display","block");
                 $("#mm").val("");
             }else{
                 $("#yzmdiv").css("display","none");
                 $("#mm").val("");
             }
        }else{
        	$(".errors").html("<span style='color:green'>绑定成功，<font color='red'><span id='time'>3</span></font>秒后系统将自动返回列表页面</span>");
        	minusTime(2);
        }
    });
}

function minusTime( time){
    
    var interval = setInterval(function(){
            $("#time").html(time);
            if(time == 0){
                clearInterval(interval);
                location.href="${wechat_jump_url}";
            }
            time--;
        },1000);
    return ;
}
</script>
</head>
<body style="">
    <div class="i_header">
        <div class="header_c">
            <a href="#" id="hrefLogo" class="logo"></a>
        </div>
    </div>
    <div class="content">
        <form name="form" method="post" id="form">
            <div>
                <input type="hidden" name="model.bindId" value="${openId }">
            </div>
            <ul class="unstyled matrix_ul">
                <li>
                    <div class="topic_input">
                        <div class="topic_bel">
                            <i class="ico_phone"></i>
                        </div>
                        <div class="topic_ipt">
                            <input name="model.yhm" type="text" id="yhm" placeholder="用户名" class="btx">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="topic_input">
                        <div class="topic_bel">
                            <i class="ico_p"></i>
                        </div>
                        <div class="topic_ipt">
                            <input name="model.mm" type="password" id="mm" placeholder="密码" class="btx">
                        </div>
                    </div>
                </li>
            </ul>
            <div style="margin-top: 20px;">
                <a id="btn_dl" class="button orange" onclick="dl()">绑定</a>
            </div>
            <div class="errors" ></div>
        </form>
    </div>


</body></html>