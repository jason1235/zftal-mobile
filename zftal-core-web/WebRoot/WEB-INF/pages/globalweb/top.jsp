<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.zfsoft.common.log.User"%>
<%@page import="com.zfsoft.dao.entities.JsglModel"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	<script type="text/javascript">
	$(function() {
		viewMenu();
	});
	
	function logout(){

		showConfirmDivLayer('确定注销？',{'okFun':function(){
			document.forms[0].action="login_logout.html";
			document.forms[0].submit();
		 }
		})
	}
	//将当前选中的菜单样式突出
	function viewMenu() {
		$('ul.ul_find > li').each(function (i,n) {
			$(n).removeClass();
		});
		var classbz = $('#classbz').val();
		if (classbz != null && classbz != "") {
			classbz = "li_"+classbz;
			$('#'+classbz).parent().addClass('current');
		} else {
			$('#li_page').parent().addClass('current');
		}
	}
	
	//修改密码
	function xgMm(){
		showWindowV2('修改密码','<%=jsPath %>/xtgl/yhgl_xgMm.html',400,240);
	}
	

</script>
</head>
	<body >
		<s:form action="#"  theme="simple">
		<!-- 一级菜单选中标志 -->
    <input type="hidden" name="classbz" id="classbz" value="${gnmkdm }"/>
      <div class="head">
        <div class="logo">
          <h2 class="floatleft"><img src="<%=stylePath%>/logo/logo_school.png"/></h2>
          <h3 class="floatleft"><img src="<%=stylePath%>/logo/logo_xg.png" /></h3>
        </div>
        <div class="info">
        <div class="welcome">
        <div class="tool"  onmouseover="javascript:document.getElementById('downmenu').style.display='block'" onmouseout="javascript:document.getElementById('downmenu').style.display='none'">
       	 	<a class="tool_btn" href="#" onclick="showhid('downmenu');" >${user.xm }</a>
        	<div class="downmenu" id="downmenu" style="display:none;">
        	<a href="javascript:void(0);" class="passw" onclick="xgMm()">修改密码</a>
        	</div>
         </div>
		<span>您好！</span><a href="#" onclick="logout();" id="tologin" title="注销" class="logout"></a></div> 
        
      </div>
    </div>
      <div class="menu">
    	<div class="nav">
 			<ul class="ul_find">
						<li>
								<a href="#" onclick="viewMenu();subForm('index_initMenu.html')" id="li_page">首页</a>
						</li>
						<s:if test="topMenuList != null && topMenuList.size() > 0">
							<s:iterator id="cdlist" value="topMenuList">
								<li>
									<a style="cursor: pointer;" onclick="viewMenu();subForm('index_initMenu.html?gnmkdm=${GNMKDM }')" id="li_${GNMKDM }">${GNMKMC}</a>
								</li>
							</s:iterator>
						</s:if>
						<s:else>
							<div>
								<b><font color="red" size="2">该用户尚未开放任何功能模块权限，请联系管理员！</font>
								</b>
							</div>
						</s:else>
						
			</ul>
      </div>
    </div>
    </s:form>
    </body>
    </html>