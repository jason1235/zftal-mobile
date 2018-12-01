<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.zfsoft.hrm.core.util.DownloadFilenameUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String info = (String)request.getAttribute("userName");
	String name = info+"离校信息一览表.xls";
	String useragent = request.getHeader("user-agent");
	String disposition = DownloadFilenameUtil.fileDisposition(useragent, name);
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
			   	  	 ${leaveName }的离校处理单
			   	  </span>
			    </h3>
				<table width="60%" class="dateline nowrap" id="tiptab" >
						<tr>
							<th>职工号</th>
							<td>${info.accountId }</td>
							<th>姓名</th>
							<td>${info.dynaBean.viewHtml['xm'] }</td>
						</tr>
						<tr>
							<th>部门</th>
							<td>${info.dynaBean.viewHtml['dwm'] }</td>
							<th>离校类型</th>
							<td><ct:codeParse catalog="<%=ICodeConstants.DM_RETIRD_REASON %>" code="${info.type }"/></td>
						</tr>	
							<th>现任职务</th>
							<td>${info.duty }</td>
							<th>离校状态</th>
							<td><s:if test='leaveStatus == "1"'>已处理</s:if><s:else>未处理</s:else></td>
						<tr>
							<th>离校时间</th>
							<td><s:date name="leaveDate" format="yyyy-MM-dd"></s:date></td>
							<th>备注</th>
							<td>${info.remark }</td>
						</tr>
						<tr>
							<th>工资结算</th>
							<td>
							<s:if test='flow.salaryStatus == "1"'>已结算</s:if><s:else><font class="red">未结算</font></s:else>
							</td>
							<th>处理结果</th>
							<td>${flow.comment }</td>
						<tr>
							<th>离校处理流程</th>
						</tr>
						<thead id="list_head1">
								<tr>
									<th>处理部门</th>
									<th>处理人</th>
									<th>处理时间</th>
									<th>处理状态</th>
								</tr>
						</thead>
						<tbody id="list_body1">
								<s:iterator value="processList" var="process">
								<tr>
									<td><ct:codeParse catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${process.deptId }" /></td>
							   		<td><ct:PersonParse code="${process.operator }"/></td>
							   		<td><s:date name="operateDate" format="yyyy-MM-dd"/></td>
							   		<td><s:if test='status =="0"'><font color="red">未处理</font></s:if><s:else><font color="green">已处理</font></s:else></td>
								</tr>
								</s:iterator>
						</tbody>
					</tbody>
			 	</table>
			</div>
		</div>
	</body>
</html>
