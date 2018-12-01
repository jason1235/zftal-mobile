<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
  <%@ include file="/commons/head.ini" %>
  <script type="text/javascript">
  		$.post("<%=request.getContextPath()%>/guest/user_login.html",
          "sessionName=1",function(data){
              if(data.success){
					$("#error").html("");
					location.href="<%=request.getContextPath() %>/guest/user_index.html";
				}else{
					$("#error").html("");
					$("#error").append(data.message);
				}  
      	});
  
  		$(function(){
  			$("#login").click(function(){
  				var username = $("#username").val();
  				var password = $("#password").val();
  				$.post("<%=request.getContextPath()%>/guest/user_login.html",
                "username="+username+"&password="+password,function(data){
                    if(data.success){
						$("#error").html("");
						location.href="<%=request.getContextPath() %>/guest/user_index.html";
					}else{
						$("#error").html("");
						$("#error").append(data.message);
					}  
            	});
  			});
  		});
  		
  		
  </script>
</head>
<body>
<header>
  <div class="log-header">
      <h1><a href="/">Amaze UI</a> </h1>
  </div>    
  <div class="log-re">
    <button type="button" class="am-btn am-btn-default am-radius log-button">注册</button>
  </div> 
</header>

<div class="log"> 
  <div class="am-g">
  <div class="am-u-lg-3 am-u-md-6 am-u-sm-8 am-u-sm-centered log-content" >
    <h1 class="log-title am-animation-slide-top">AmazeUI</h1>
    <br>
    <form class="am-form" id="log-form" >
      <div class="am-input-group am-radius am-animation-slide-left">       
        <input type="text" id="username" class="am-radius"  placeholder="用户名" />
        <span class="am-input-group-label log-icon am-radius"><i class="am-icon-user am-icon-sm am-icon-fw"></i></span> 
      </div>      
      <br>
      <div class="am-input-group am-animation-slide-left log-animation-delay">       
        <input type="text" id="password" class="am-form-field am-radius log-input"  placeholder="密码" >
        <span class="am-input-group-label log-icon am-radius"><i class="am-icon-lock am-icon-sm am-icon-fw"></i></span>
      </div>      
    	<div class="" id="error"></div>
      <br>
      <button id="login" type="button" class="am-btn am-btn-primary am-btn-block am-btn-lg am-radius am-animation-slide-bottom log-animation-delay">登 录</button>
            <p class="am-animation-slide-bottom log-animation-delay"><a href="#">忘记密码?</a></p>
	  <!--
      <div class="am-btn-group  am-animation-slide-bottom log-animation-delay-b">
      <p>使用第三方登录</p>
      <a href="#" class="am-btn am-btn-secondary am-btn-sm"><i class="am-icon-github am-icon-sm"></i> Github</a>
      <a href="#" class="am-btn am-btn-success am-btn-sm"><i class="am-icon-google-plus-square am-icon-sm"></i> Google+</a>
      <a href="#" class="am-btn am-btn-primary am-btn-sm"><i class="am-icon-stack-overflow am-icon-sm"></i> stackOverflow</a>
      </div>
	  -->
    </form>
  </div>
  </div>
  <footer class="log-footer">   
    © 2014 AllMobilize, Inc. Licensed under MIT license.
  </footer>
</div>




</body>
</html>