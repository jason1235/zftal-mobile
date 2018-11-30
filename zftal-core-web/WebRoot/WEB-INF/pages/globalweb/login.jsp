<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.zfsoft.util.date.TimeUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript"
			src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript">
			 $(document).ready(function(){
                 refreshCode();
                 $("#yzm").keyup(function(e){
                	 if(e.keyCode==13){
                		dl();
                	 }
                 });
                 $("#btn_dl").click(function(e){
                	 dl();
                 });
                 
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
			//登录
			function dl() {
				if(isEmpty('yhm')){
					alert('用户名不能为空！');
					return false;
				}
				if(isEmpty('mm')){
					alert('密码不能为空！');
					return false;
				}
				if(isEmpty('yzm')){
					alert('验证码不能为空！');
					return false;
				}
				$.post('<%=request.getContextPath() %>/xtgl/login_login.html', $('#form').serialize(), function(data){
					if(data.success==false){
						$(".errors").html("<span class=\"red\">"+data.message+"</span>");
						//$("#errors").css("display","");
					}else{
						$(".errors").html("");
						location.href="<%=request.getContextPath() %>/xtgl/index_initMenu.html";
					}
				});
				//document.forms[0].submit();
			}

						
		</script>
	</head>
	<body class="login_bg">

		<s:form id="form" action="/xtgl/login_login.html" theme="simple">
			<div class="login_main">
				<div class="login_logo">
					<h2>
						<img src="<%=stylePath%>logo/logo_school.png" />
					</h2>
					<h3>
						<img src="<%=stylePath%>logo/logo_xg.png" />
					</h3>
				</div>
				<div class="login_left">
					<img class="login_pic" src="<%=stylePath%>logo/login_pic.png" />
				</div>
				<div class="login_right">
					<div class="login">
						<div class="user">
							<label for="">
								用户名：
							</label>
							<input name="yhm" id="yhm" type="text" class="text_nor"
								maxlength="20" style="width: 120px" 
								 />
						</div>
						<div class="passw">
							<label for="">
								密&nbsp;&nbsp;&nbsp;码：
							</label>
							<input name="mm" id="mm" type="password" value=""
								class="text_nor" maxlength="20" style="width: 120px" />
						</div>
						<div class="yzm">
							<label>
								验证码：
							</label>
							<input name="yzm" type="text" id="yzm" class="text_nor"
								style="width: 45px;" maxlength="4" value=""
								/>
							<img border="0" align="absmiddle" id="yzmPic" style="cursor:hand;" onclick="javascript:refreshCode();" name="yzmPic"
								width="70px" height="19px" />
						</div>
						<div class="errors" >
						</div>
						<div class="login_notice">
							<p>
								热心提示：为了您的帐号安全，请及时修改您的初始密码。
							</p>
						</div>
					</div>
				</div>
				<!-- 版权信息 -->
				<%@include file="/WEB-INF/pages/globalweb/login_bottom.jsp" %>
			</div>
		</s:form>

		<script type="text/javascript">
			if('${message}' != null && '${message}' != ''){
				alert('${message}');
			}
		</script>
	</body>
</html>

