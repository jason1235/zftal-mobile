<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
String systemPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=systemPath %>/css/jquery/easyui.css">
<%@ taglib prefix="s" uri="/struts-tags"%>
    <table class="easyui-datagrid" fit="true">
	   <thead>
		<tr style="align:center">					
		 <th field="f1" width="160" >学院名称</th>
		</tr>
	   </thead>
	    <tbody>
		   <s:if test="jskcList !=null">
			<s:iterator value="jskcList" id="list" status="s">
				   
					 <tr><td>${bmmc}</td></tr>
			</s:iterator>
		   </s:if>
		   <s:else>
		   
		   </s:else>	   
	    </tbody>

	</table>