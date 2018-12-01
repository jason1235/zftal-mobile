<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ page language="java" import="com.zfsoft.hrm.core.util.DownloadFilenameUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String useragent = request.getHeader("user-agent");
	String disposition = DownloadFilenameUtil.fileDisposition(useragent, "数据字典信息一览表.xls");
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
			   	  	  数据字典信息一览表
			   	  </span>
			    </h3>
				<!--标题end-->
				<table width="100%" class="dateline" id="tiptab" >
					<thead id="list_head">
						<tr>
							<th>序号</th>
							<th>英文名</th>
							<th>中文名</th>
							<th>字段名</th>
							<th>注释</th>
							<th>数据类型</th>
							<th>数据长度</th>
							<th>是否为空</th>
							<th>默认值</th>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${pageList}" var="ddSelect" varStatus="st">
						  <tr name="tr">
							<td rowspan="${fn:length(ddSelect.fields)}">${(st.index+1) + (model.currentPage - 1) * model.showCount }</td>
							<td rowspan="${fn:length(ddSelect.fields)}">${ddSelect.name }</td>
							<td rowspan="${fn:length(ddSelect.fields)}">${ddSelect.chineseName }</td>
							<c:forEach items="${ddSelect.fields}" var="field" varStatus="i">
								<c:if test="${i.index > 0 }">
									<tr>
								</c:if>
									<td>${field.fieldName }</td>
									<td>${field.fieldChineseName}</td>
									<td>${field.fieldtype }</td>
									<td>${field.fieldLength }</td>
									<td>${field.vacant.text }</td>
									<td>${field.fieldDefalutValue }</td>
								<c:if test="${i.index > 0 }">
									</tr>
								</c:if>
							</c:forEach>
						  </tr>
						</c:forEach>
					</tbody>
			 	</table>
			</div>
		</div>
	</body>
</html>
