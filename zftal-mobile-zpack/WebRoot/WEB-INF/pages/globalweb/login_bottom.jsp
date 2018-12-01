<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.zfsoft.util.date.TimeUtil"%>
<%@page import="com.zfsoft.common.system.SubSystemHolder" %>
<div class="footer">
	<span>&copy;1999-<%=TimeUtil.getYear() %>  <a href="http://www.zfsoft.com"
		target="_blank">正方软件股份有限公司</a> <span>版权所有</span></span>
		<a class="ver" href="#"><%=SubSystemHolder.getPropertiesValue("System.Version") %></a>
</div>