<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ page language="java" import="com.zfsoft.hrm.core.util.DownloadFilenameUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String useragent = request.getHeader("user-agent");
	String disposition = DownloadFilenameUtil.fileDisposition(useragent, "快照信息一览表.xls");
	response.setHeader("Content-Disposition", disposition);
	response.setContentType("application/vnd.ms-excel"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:w="urn:schemas-microsoft-com:office:excel">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<head>
		<style type="text/css">
		
			h3{
				font:10.5pt;
			}
			tr{
				height:22;
			}
			th{
				border:1px solid rgb(0, 0, 0);
				font:10.5pt;
			}
			td{
				border-color:-moz-use-text-color black black -moz-use-text-color;
				border-style:solid;
				border-width:1pt;
				font:10pt;
				text-align: left;
				padding:0 5.4pt;
								
			}
			p{
				font:10pt;
			}
			table{
				width:100%;
				border-collapse: collapse; 
				empty-cells: show;
				border:1px solid rgb(0, 0, 0);
			}
			@page
		    {
		    	mso-page-border-surround-header:no;
		   		mso-page-border-surround-footer:no;
		   	}
			@page Section1
			{
			 	size:21.5cm 14.00cm;
				margin:52.0pt 72.0pt 52.0pt 72.0pt;
				mso-header-margin:0.5cm;
				mso-footer-margin:0.5cm;
				layout-grid:0.5pt;
			}
			div.Section1
			{
				page:Section1;
			}
		</style>
	</head>
	<body>
		<div class="Section1" >
		    <!-- 查询条件 -->
			<div class="formbox">
				<!--标题start-->
			     <h3 style="margin-top: 7px; margin-bottom: 0px; text-align: center;">
			   	  <span>
			   	  	  快照信息一览表
			   	  </span>
			    </h3>
				<!--标题end-->
				<table width="100%" class="dateline tablenowrap" id="tiptab" >
				<thead id="list_head">
					<tr>
					<s:iterator value="query.clazz.viewables" var="p">
					<s:if test="fieldName != 'zp'">
						<th>${p.name }</th>
					</s:if>
					</s:iterator>
					</tr>
				</thead>
				<tbody id="list_body">
				<s:iterator value="pageList" var="overall">
				<tr name="tr">
				<s:iterator value="clazz.viewables" var="p">
					<s:if test="fieldName != 'zp'">
						<td name="${p.fieldName }">${overall.viewHtml[p.fieldName]}&nbsp;</td>
					</s:if>
				</s:iterator>
				</tr>
				</s:iterator>
				</tbody>
			</table>
			</div>
		</div>
	</body>
</html>
