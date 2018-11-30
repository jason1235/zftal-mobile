<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.zfsoft.util.date.TimeUtil"%>
<%@page import="com.zfsoft.common.system.SubSystemHolder" %>
<!doctype html>
<html>
<head>
  <%@ include file="/commons/head.ini" %>
  
  <script type="text/javascript">
  			var strong = false;
  		$(function(){
             $("#password").keyup(function(e){
                 pwStrength(this.value);
                 if(e.keyCode==13){
                	var username = $("#username").val();
       				var password = $("#password").val();
       				var name 	 = $("#name").val();
                    login(username,password,name);
                 }
             });
                
  			
  			
  			$("#login").click(function(){
  				var username = $("#username").val();
  				var password = $("#password").val();
  				var name 	 = $("#name").val();
  				login(username,password,name);
  			});
  			$(".am-modal-btn").click(function(){
  				location.href="<%=request.getContextPath() %>";
  			});
  			$("#getBack").click(function(){
  				location.href="<%=request.getContextPath() %>";
  			});
  		});
  		
  		function login(username,password,name){
  			if(username==null || username==''){
  				$("#error").html("");
				$("#error").append("用户名不能为空");
				return false;
  	        }
  			if(password==null || password==''){
  				$("#error").html("");
				$("#error").append("密码不能为空");
				return false;
  	        }
  			if(password.indexOf(" ") >=0){
  				$("#error").html("");
				$("#error").append("密码不能包含空格");
				return false;
  			}
  			if(name==null || name==''){
  				$("#error").html("");
				$("#error").append("昵称不能为空");
				return false;
  	        }
  			var chinese = /[^\u0000-\u00ff]/g;
			var flag = (chinese.test(username));
			if(flag){
				$("#error").html("");
				$("#error").append("用户名不能包含汉字");
				return false;
			}else{
				strong = true;
				$("#error").html("");
			}
  			if(strong){
  				$.post("<%=request.getContextPath()%>/guest/user_zhuce.html",
                "username="+username+"&password="+password+"&name="+name,function(data){
                    if(data.success){
                    	errorAlert("注册成功！");
					}else{
						$("#error").html("");
						$("#error").append(data.text);
					}  
           		 });
				}
  		}
  		
  		function pwStrength(pwd){ 
            if (pwd==null||pwd==''){  
               hideMMshow();
             }  
            else{  
              S_level=checkStrong(pwd); 
              switch(S_level) {  
              case 0:
            	   $("#error").html("");
  				   $("#error").append("密码太短,请及时修改!");
              	   strong = false;
              	   break;   
              case 1: 
            	  $("#error").html("");
  	         	   //showErrMsg("密码强度太弱,请及时修改!");
                     strong = true;
              	   break;
              default:
  					strong = true;
              		$("#error").html("");
             }  
          }     
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
				$("#error").html("");
				$("#error").append("用户名不能包含汉字");
			}else{
				$("#error").html("");
			}
        	//return true;
        }
        //return true;
  </script>
</head>
<body>
<header>
<jsp:include page="/commons/modal.jsp" flush="true"></jsp:include>
  <div class="log-header">
      <h1><a href="/">正方软件</a> </h1>
  </div>    
  <div class="log-re">
    <button type="button" class="am-btn am-btn-default am-radius log-button" id="getBack">返回</button>
  </div> 
</header>

<div class="log"> 
  <div class="am-g">
  <div class="am-u-lg-3 am-u-md-6 am-u-sm-8 am-u-sm-centered log-content" >
    <h1 class="log-title am-animation-slide-top">正方软件</h1>
    <br>
    <form class="am-form" id="log-form" >
      <div class="am-input-group am-radius am-animation-slide-left">       
        <input type="text" id="username" class="am-radius"  placeholder="用户名,不超过20个字符" onchange="isOrNotChar(this);" maxlength="20"/>
        <span class="am-input-group-label log-icon am-radius"><i class="am-icon-user am-icon-sm am-icon-fw"></i></span> 
      </div>      
      <br>
      <div class="am-input-group am-animation-slide-left log-animation-delay">       
        <input type="text" id="name" class="am-form-field am-radius log-input"  placeholder="昵称,不超过15个字" maxlength="15">
        <span class="am-input-group-label log-icon am-radius"><i class="am-icon-user am-icon-sm am-icon-fw"></i></span>
      </div>   
      <br> 
      <div class="am-input-group am-animation-slide-left log-animation-delay">       
        <input type="password" id="password" class="am-form-field am-radius log-input"  placeholder="密码,不超过20个字符"  maxlength="20">
        <span class="am-input-group-label log-icon am-radius"><i class="am-icon-lock am-icon-sm am-icon-fw"></i></span>
      </div>  
    	<div class="" id="error" style="color: red;"></div>
      <br>
      <button id="login" type="button" class="am-btn am-btn-primary am-btn-block am-btn-lg am-radius am-animation-slide-bottom log-animation-delay">注 册</button>
            
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
    © 1999-<%=TimeUtil.getYear() %> 正方软件股份有限公司 版权所有<a class="ver" href="#"><%=SubSystemHolder.getPropertiesValue("System.Version") %></a>
  </footer>
</div>




</body>
</html>