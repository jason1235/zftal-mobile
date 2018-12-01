<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.zfsoft.hrm.core.util.DownloadFilenameUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String name = "离校人员一览表.xls";
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
			   	  	 离校人员一览表
			   	  </span>
			    </h3>
				<table width="100%" class="dateline nowrap" id="tiptab" >
					<thead id="list_head">
						<tr>
							<th>职工号</th>
							<th>姓名</th>
							<th>部门</th>
							<th>职称</th>
							<th>职称级别</th>
							<th>离校类型</th>
							<th>现任职务</th>
							<th>离校状态</th>
							<th>离校时间</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody id="list_body">
						<s:iterator var="info" value="list">
						  <tr name="tr">
							<td>${info.accountId }</td>
							<td>${info.dynaBean.viewHtml['xm'] }</td>
							<td>${info.dynaBean.viewHtml['dwm'] }</td>
							<td>${info.dynaBean.viewHtml['zc'] }</td>
							<td>${info.dynaBean.viewHtml['zyjszj'] }</td>
							<td><ct:codeParse catalog="<%=ICodeConstants.DM_RETIRD_REASON %>" code="${info.type }"/></td>
							<td>${info.duty }</td>
							<td><s:if test='leaveStatus == "1"'>已处理</s:if><s:else>未处理</s:else></td>
							<td><s:date name="leaveDate" format="yyyy-MM-dd"></s:date></td>
							<td>${info.remark }</td>
						  </tr>
						</s:iterator>
					</tbody>
			 	</table>
			</div>
		</div>
	</body>
</html>
