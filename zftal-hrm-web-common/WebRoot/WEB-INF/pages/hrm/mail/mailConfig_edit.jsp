<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    $(function(){
        
        $("#save_btn").click(function(){
            
            if(!check()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/mail/mailTemplate_saveConfig.html",
                $("#form_edit").serialize(),function(data){
                    var callback = function(){
                    	$(".ymPrompt_close").click();
                    };
                    if(data.success){
                        processDataCall(data, callback);
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
        });
        
        $('#cancel').click(function(){
            $(".ymPrompt_close").click();
        });
        
        $("#testMail").click(function(){
        	//alert($("#form_edit,#form_test").serialize());
            if(!checkSend()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/mail/mailTemplate_sendTestMail.html",
            		$("#form_edit,#form_test").serialize(),function(data){
                    if(data.success){
                    	alert("发送成功");
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
        });
    });

    function check()
    {
    	var host = $('#host').val();
    	if(host==null || host==''){
            showWarning("邮件服务器地址不能为空");
            return false;
        }
    	var port = $('#port').val();
        if(port==null || port==''){
            showWarning("邮件服务器端口号不能为空");
            return false;
        }
    	var send = $('#send').val();
        if(send==null || send==''){
            showWarning("系统发送邮箱不能为空");
            return false;
        }
        var user = $('#user').val();
        if(user==null || user==''){
            showWarning("鉴权账号不能为空");
            return false;
        }
        var pwd = $('#pwd').val();
        if(pwd==null || pwd==''){
            showWarning("请输入鉴权密码");
            return false;
        }
        return true;
    }

    function checkSend(){
        if(!check()){
            return false;
        }
        var toMail = $('#toMail').val();
        if(toMail==null || toMail==''){
            showWarning("收件人不能为空");
            return false;
        }
        var content = $('#content').val();
        if(content==null || content==''){
            showWarning("测试邮件内容不能为空");
            return false;
        }
        return true;
    }
    
    </script>
</head>
<body>
    <form id="form_edit">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">邮件服务信息</font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn" type="button">保 存</button>
                                <button id="cancel" type="button">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <th>
                            <span class="red">*</span>邮件服务器地址（IP/域名 ：端口）
                        </th>
                        <td>
                            <input id="host" name="config.host" value="${config.host }" />：
                            <input id="port" name="config.port" style="width: 60px" value="${config.port }" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>系统发送邮箱
                        </th>
                        <td>
                            <input id="send" name="config.send" value="${config.send }" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>鉴权账号
                        </th>
                        <td>
                            <input id="user" name="config.user" value="${config.user }" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>鉴权密码
                        </th>
                        <td>
                            <input id="pwd" name="config.pwd" type="password" /><span class="red">说明：为了安全，每次编辑都要重新输入密码！</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
    
    <form id="form_test">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">发送测试邮件</font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">以目前填入的邮件信息发送测试邮件到指定邮箱</div>
                            <div class="btn">
                                <button id="testMail" type="button">发送邮件</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <th>
                            <span class="red">*</span>收件人
                        </th>
                        <td>
                            <input id="toMail" name="toMail" style="width: 360px"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>邮件内容
                        </th>
                        <td>
                            <textarea id="content" name="mailCont" cols="50" rows="3" ></textarea>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>