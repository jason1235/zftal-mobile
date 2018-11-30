<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.zfsoft.zfca.tp.cas.client.ZfssoConfig,com.zfsoft.common.system.SubSystemHolder" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>
	<script type="text/javascript">
	$(function() {
		//$.post(_path+"/message/message_listData.html","",function(data){
        //    if(data.success){
        //    	$("#messageNum").html("[新消息(<font color='red'>"+data.sumcount+"</font>)]");
        //         if(data.sumcount > 0){
        //        	 $("#messageNum").click(function(){
        //        		 showMsg();
        //            });
        //         }
        //    }else{
        //        alert(data.text);
        //    }
        //},"json");
		
	//});
	
	function logout(){

		showConfirmDivLayer('确定注销？',{'okFun':function(){
			//document.forms[0].action="login_logout.html";
			//document.forms[0].submit();
			var tag=$("<form method=\"post\"></form>");
			tag.attr("action","login_logout.html");
			tag.appendTo("body");
			tag.submit();
		 }
		})
	}
	
	//修改密码
	function xgMm(){
		showWindowV2('修改密码','<%=jsPath %>/xtgl/yhgl_xgMm.html',400,240);
	}
	function showPassword(oString){
		var obj = document.getElementById('downmenu');
		if(obj){
			obj.style.display=oString;
		}
	}

	function showMsg(){
		//showWindow("我的消息","<%=request.getContextPath() %>/message/message_page.html",500,400);
		window.open("<%=jsPath %>/message/message_page.html","aaa");
	}
</script>
</head>
	<body >
		<!-- 一级菜单选中标志 -->
    <input type="hidden" name="classbz" id="classbz" value='<c:out value="${gnmkdm }"/>'/>
    <input type="hidden" name="quickId" id="quickId" value='<c:out value="${quickId }"/>'/>
      <div class="head">
        <div class="logo">
          <h2 class="floatleft"><img src="<%=stylePath%>/logo/logo_school.png"/></h2>
          <h3 class="floatleft"><img src="<%=stylePath%>/logo/logo_smp.png" /></h3>
        </div>
        <div class="info">
        <div class="welcome">
        <div class="tool"  
        	onmouseover="javascript:showPassword('block')" 
        	onmouseout="javascript:showPassword('none')">
        	
       	 	<a class="tool_btn" href="#" onclick="${(empty sessionScope.subsystem || sessionScope.subsystem.sysCode =='hrm_system') ? "showhid('downmenu');" : ""}" >${user.xm }</a>
       	 	
       	 	<%
        	if( ZfssoConfig.usezfca == null || "0".equals( ZfssoConfig.usezfca ) ) {
        	%>
	        	<div class="downmenu" id="downmenu" style="display:none;">
	        		<a href="#" class="passw" onclick="xgMm()">修改密码</a>
	        	</div>
        	<%
        	}
        	%>
         </div>
		<span>您好！</span>
		<span> <a class="news" id="messageNum" href="#">[新消息(0)]</a></span>
		<%
		if( ZfssoConfig.usezfca == null || "0".equals( ZfssoConfig.usezfca ) ) {
		%>
			<a href="#" onclick="logout();" id="tologin" title="注销" class="logout"></a></div> 
		<%
		} else {
		%>
		<a href="#" onclick="javascript:window.close();" id="tologin" title="退出" class="sign_out"></a></div>
		<%	
		}
		%>
        
      </div>
    </div>
      
    </body>
    </html>