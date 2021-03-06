<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.zfsoft.util.date.TimeUtil"%>
<%@ page import="com.zfsoft.zfca.tp.cas.client.ZfssoConfig" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
        <script type="text/javascript"
            src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
        <script type="text/javascript"
            src="<%=systemPath %>/js/globalweb/password.js"></script>
            
        <% String usezfca = ZfssoConfig.usezfca;
            String goUrl = request.getContextPath() + "/xtgl/index_initMenu.html";
        %>    
        <% if(null !=usezfca && "1".equals(usezfca)){ %>
        <script language="javascript">
            window.location.href = "<%=ZfssoConfig.casurl%>/login?service=<%=java.net.URLEncoder.encode("http://"+ZfssoConfig.ywxtservername+goUrl, "utf-8")%>";
        </script>
        <%}%>
            
            
        <script type="text/javascript">
                
             $(document).ready(function(){
                 $("#mm").passwordStrength();
                 refreshCode();
                 $("#mm").keyup(function(e){
                     pwStrength(this.value);
                     if(e.keyCode==13){
                        dl();
                     }
                 });
                 $("#yzm").keyup(function(e){
                     if(e.keyCode==13){
                        dl();
                     }
                 });
                 $("#btn_dl").click(function(e){
                     dl();
                 });
                 if("${logincount>=3}"=="true"){
                     $("#yzmdiv").css("display","block");
                     $("#mm").val("");
                 }
                 
             });
            function refreshCode(){
                document.getElementById("yzmPic").src = _path + '/xtgl/login_code.html?time=' + new Date().getTime();
            }
            function isEmpty(dataid){
                if($('#' + dataid).val() != null && $('#' + dataid).val() != ''){
                    return false;
                }else{
                    return true;
                }
            }
            function showErrMsg(message){
                $(".errors").html("<p class='bg-danger sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>" + message);
            } 
            function hideMMshow(){
                $(".errors").html("");
            } 
            //登录
            function dl() {
                if(isEmpty('yhm')){
                    $(".errors").html("<p class='bg-danger sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>用户名不能为空！");
                    return false;
                }
                if(isEmpty('mm')){
                    $(".errors").html("<p class='bg-danger sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>密码不能为空！");
                    return false;
                }
                
                if(isEmpty('yzm') && $("#count").val()>=3){
                    $(".errors").html("<p class='bg-danger sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>验证码不能为空！");
                    return false;
                }
                $("#btn_dl").attr("disabled","disabled");
                $(".errors").html("<p class='bg-info sl_info'><span class='glyphicon glyphicon-minus-sign'></span>正在登录中，请稍候......");
                
                var enterWay = $("#enterWay").val();
                if(enterWay == "xitong"){
                	$.post('<%=request.getContextPath() %>/xtgl/login_login.html', $('#form :input').serialize(), function(data){
                        $("#btn_dl").removeAttr("disabled");
                        if(data.success==false){
                            $(".errors").html("<p class='bg-danger sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>" + data.message);
                            if (data.logincount == null) {
                                data.logincount=$("#count").val();
                            }
                            $("#count").val(data.logincount);
                            if(data.logincount>=3){
                                $("#yzmdiv").css("display","block");
                                $("#mm").val("");
                            }else{
                                $("#yzmdiv").css("display","none");
                                $("#mm").val("");
                            }
                        }else{
                            $(".errors").html("");
                            location.href="<%=request.getContextPath() %>/xtgl/index_initMenu.html";
                        }
                    });
                }else{
                	var username = $("#yhm").val();
      				var password = $("#mm").val();
      				$.post("<%=request.getContextPath()%>/guest/user_login.html",
                    "username="+username+"&password="+password,function(data){
      					$("#btn_dl").removeAttr("disabled");
                        if(data.success){
    						$("#error").html("");
    						location.href="<%=request.getContextPath() %>/guest/user_index.html";
    					}else{
    						//$("#error").html("");
    						//$("#error").append(data.message);
    						$(".errors").html("<p class='bg-danger sl_danger'><span class='glyphicon glyphicon-minus-sign'></span>" + data.message);
    						$("#mm").val("");
    					}  
                	});
                }
                
                //document.forms[0].submit();
            }
            
            function openGuest(){
            	window.location.href = "<%=request.getContextPath() %>/guest/user_toadd.html";
            }

            function toRecruit(){
                var tag=$("<form method=\"post\"></form>");
                tag.attr("action","<%=request.getContextPath() %>/iframeweb/login_loginPage.html");
                tag.appendTo("body");
                tag.submit();
            }
            
            /**
             * 验证输入不能为中文
             * @param obj为输入的标签对象
             * @return TRUE
             * lt 2011.12.15
             */
            function isOrNotChar(obj){
            	var chinese = /[^\u0000-\u00ff]/g;
    			var flag = (chinese.test(obj.value));
    			if(flag){
	            	showErrMsg("用户名不能包含汉字");
    	            return false;
    			}else{
    				hideMMshow
    			}
            	//return true;
            }
        </script>
        <style type="text/css">
            .is0{margin-top:3px;}
            .is0>span{float:left; font-size:12px;}
            .is0 .strengthResult_pic{float:left; border:1px solid #ddd; margin-top:4px;}
            .is0 .strengthResult_pic span{background:#ddd; margin:1px 2px; float:left; width:50px; height:5px;}
            .is0 .strengthResult_tip{float:left; font-size:12px;}
            .is0 .strengthResult_tip span{display:none;}
        </style>
    </head>
    <body class="background:#fafafa;">
        <div class="container container_1170">
        <%@ include file="modal.jsp" %>
            <div class="row sl_log_top">
                <div class="col-sm-10 logo_1"><img src="<%= stylePath %>/logo/logo_jw_d.png" style="margin-top:-3px" />
                	移动信息服务管理平台
                	<%--
                	<a style="font-size:15px;font-family: inherit;font-weight: bolder;" href="<%=request.getContextPath() %>/menu/apiMenu_list.html">平台接入指南</a>
               		--%>
               	</div>
            </div>
            
            <div class="row sl_log_bor4" >
                <div class="col-sm-8 hidden-xs sl_log_lf">
                    <img class="img-responsive" src="<%= stylePath %>/img/login_bg.jpg" width="657" height="463" />
                </div>
                <div class="col-sm-4 sl_log_rt" >
                    <form class="form-horizontal" role="form" id="form">
                        <input type="hidden" name="count" id="count" />
                        <div style="font-size: 12pt;">
                        	<font>用户登录</font>&nbsp;
                        	<a href="#" onclick="openGuest();">游客注册</a>
                        </div>
                            		
                        </br>
                        <div class="form-group" >
                            <div class="input-group">
                                <div class="input-group-addon"><img src="<%= stylePath %>/images/pjlc_ic03_gray.png" width="16" height="16"/></div>
                                <select id="enterWay" name="enterWay"  class="form-control">
                                        <option value="xitong" >系统用户</option>
                                        <option value="guest" >游客登录</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon"><img src="<%= stylePath %>/images/log_ic01.png" width="16" height="16"/></div>
                                <input name="yhm" id="yhm" type="text" class="form-control" placeholder="用户名" maxlength="20" value="" onchange="isOrNotChar(this);" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon"><img src="<%= stylePath %>/images/log_ic02.png" width="16" height="16"/></div>
                                <input name="mm" id="mm" type="password" value="" class="form-control" placeholder="密码" maxlength="20" />
                            </div>
                            <div id="passwordStrengthDiv" class="pull-right is0" style="width:100%;">
                            	
                            	<div style="float:right;">
                                <span style="float:left;">密码强度：</span>
                                <div class="strengthResult_pic">
                                    <span class="strengthResult_pic1" id="strengthResult_span1"></span>
                                    <span class="strengthResult_pic2" id="strengthResult_span2"></span>
                                    <span class="strengthResult_pic3" id="strengthResult_span3"></span>
                                </div>
                                <div class="strengthResult_tip">
                                    <span class="strengthResult_1" id="strengthResult_tip1">弱</span>
                                    <span class="strengthResult_2" id="strengthResult_tip2">中</span>
                                    <span class="strengthResult_3" id="strengthResult_tip3">强</span>
                                </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="yzmdiv" style="display:none;">
                            <div class="input-group col-xs-4 pull-left">
                                <input name="yzm" type="text" id="yzm" class="form-control" placeholder="验证码" maxlength="4" value="" />
                            </div>
                            <div class="input-group col-xs-4 col-xs-offset-5">
                                <img border="0" align="absmiddle" id="yzmPic" style="cursor:hand;" onclick="javascript:refreshCode();" name="yzmPic" width="108px" height="34px" />
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <button type="button" id="btn_dl" class="btn btn-primary btn-block btn-lg">登 录</button>
                        </div>
                        <div class="errors" >
                        </div>
                        <div>
                            <p class="bg-warning sl_warning"><span class="glyphicon glyphicon-minus-sign"></span>温馨提示：为了您的帐号安全，请及时修改您的初始密码。</p>
                        </div>
                        <div class="sl_log_ewm hidden-xs" style="padding: 0px;"><img src="<%=request.getContextPath() %>/upload/qrcode.png" width="140" height="140"/>&nbsp;&nbsp;<span>用手机扫一扫下载app,<br />

				安全、便捷登陆</span></div>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/pages/globalweb/login_bottom.jsp" %>

        <script type="text/javascript">
            if('${message}' != null && '${message}' != ''){
                alert('${message}');
            }
        </script>
    </body>
</html>

