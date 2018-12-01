<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ page language="java" import="com.zfsoft.hrm.core.util.DownloadFilenameUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String useragent = request.getHeader("user-agent");
	String disposition = DownloadFilenameUtil.fileDisposition(useragent, "人员变更信息一览表.xls");
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
			   	  	  人员变更信息一览表
			   	  </span>
			    </h3>
				<!--标题end-->
				<table width="100%" class="dateline tablenowrap" id="tiptab" >
				<thead id="list_head">
					<tr>
						<th>职工号</th>
						<th>姓名</th>
						<th>原部门</th>
						<th>调入部门</th>
						<th>原岗位类别</th>
                        <th>调入岗位类别</th>
                        <th>原编制类别</th>
                        <th>调入编制类别</th>
                        <th>原岗位</th>
                        <th>调入岗位</th>
                        <th>变更类型</th>
                        <th>执行时间</th>
                        <th>变更时间</th>
                        <th>执行状态</th>
					</tr>
				</thead>
				<tbody id="list_body">
				<s:iterator value="pageList" var="info">
				    <tr name="tr">
				        <td>${info.staffid }</td>
                        <td>${info.personInfo.viewHtml['xm'] }</td>
                        <td>${info.currentOrgText }</td>
                        <td>${info.planOrgText }</td>
                        <td>${info.currentPostTypeText }</td>
                        <td>${info.planPostTypeText }</td>
                        <td>${info.oldFormationTypeText }</td>
                        <td>${info.formationTypeText }</td>
                        <td>${info.currentPostText }</td>
                        <td>${info.planPostText }</td>
                        <td>${info.manoeuvreTypeText }</td>
                        <td>${info.excuteTimeText }</td>
                        <td>${info.changeTimeText }</td>
                        <td>${info.execuStatusText }</td>
				</tr>
				</s:iterator>
				</tbody>
			</table>
			</div>
		</div>
	</body>
</html>
