<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    	$(function(){
    		var current = null;
    		
    		$(".textlink > ul > li").click(function(){
       			var subs = "-<a href=\"#\">" + $(this).parent().prev().find("span").text() + "</a>";
       			subs += "-<a href=\"#\">" + $(this).find("span").text() + "</a>";
    			parent.syncronizedMenu(subs);
    		});
    		var firstColumnList = $("body h3").filter(":first");
    		firstColumnList.click();
    		var firstMenu = $(firstColumnList).next().find("li:first");
    		$(firstMenu).click();
    		var url = $(firstMenu).find("a").attr("href");
    		$(parent.document).find("[name='framecon']").attr("src",url);
    	})
    </script>
  </head>
<body>
	<s:if test="menuList != null && menuList.size() > 0">
		<s:iterator id="menu" value="menuList" status="sta">
			 <div class="textlink" >
		 		<h3 onclick="showhidediv(this);" class="close"><span>${menu.GNMKMC}</span></h3>
		 		<ul style="display:none;">
		 			<s:iterator id="zmenu" value="#menu.sjMenu">
		 				 <li>
		 				 	<a href="<%=systemPath %>${zmenu.DYYM}" 
		 				 	   target="framecon">
		 				 	   <span>${zmenu.GNMKMC}</span>
		 				 	</a>
			 			</li>
		 			</s:iterator>
				</ul>
		      </div>
		</s:iterator>
	</s:if>
	<s:else>
		<div class="textlink" id="">
			<br/>
			<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;暂无任何功能模块信息！</font>
		</div>
	</s:else>
</body>