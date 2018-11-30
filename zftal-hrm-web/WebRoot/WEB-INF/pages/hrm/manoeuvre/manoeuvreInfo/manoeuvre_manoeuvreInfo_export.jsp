<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String name = "人员调动信息表_" + request.getAttribute("model.staffid") + "_" + request.getAttribute("model.personInfo.value['xm']") + ".doc";
	response.setContentType("application/msword;charset=UTF-8"); 
	response.setHeader( "Content-Disposition", "attachment;filename=" + new String(name.getBytes(), "ISO8859-1"));
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:w="urn:schemas-microsoft-com:office:word">
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
			
			<!--[if gte mso 9]><xml>
			 <w:WordDocument>
			  <w:View>Print</w:View>
			  <w:Zoom>100</w:Zoom>
			  <w:DrawingGridVerticalSpacing>7.8 磅</w:DrawingGridVerticalSpacing>
			  <w:Compatibility>
			   <w:UseFELayout/>
			  </w:Compatibility>
			  <w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel>
			 </w:WordDocument>
			 </xml><![endif]--> 
	</head>
	<body>
		<div class="Section1" >
			<center>
				<h2>
					人员调动信息表 —— ${model.staffid }&nbsp;&nbsp;&nbsp;&nbsp;${model.personInfo.viewHtml['xm'] }
				</h2>
			</center>
			<div>
				<h3>
					人员调动信息 —— ${model.personInfo.viewHtml['xm'] }
				</h3>
				<table>
		         	<tbody id="form1">
		         		<tr>
							<th width="25%">
								<span class="red"></span>申请人
							</th>
							<td>
								${model.personInfo.viewHtml['xm'] }
							</td>	
							<th width="25%">
								<span class="red"></span>职工号
							</th>
							<td>
								${model.staffid }
							</td>	
						</tr>
						
						<tr>
							<th width="25%">
								<span class="red"></span>性别
							</th>
							<td>
								${model.personInfo.viewHtml['xbm'] }
							</td>	
							<th width="25%">
								<span class="red"></span>申请时间
							</th>
							<td>
								${model.applyTimeText }
							</td>	
						</tr>
						
						<tr>
							<th width="25%">
								<span class="red"></span>调出部门
							</th>
							<td>
								${model.currentOrgText }
							</td>	
							<th width="25%">
								<span class="red"></span>调入部门
							</th>
							<td>
								${model.planOrgText }
							</td>	
						</tr>
						
						<tr>
							<th width="25%">
								<span class="red"></span>当前岗位
							</th>
							<td>
								${model.currentPostText }
							</td>	
							<th width="25%">
								<span class="red"></span>调任岗位
							</th>
							<td>
								${model.planPostText }
							</td>	
						</tr>
						
						<tr>
							<th width="25%">
								<span class="red"></span>当前岗位类别
							</th>
							<td>
								${model.currentPostTypeText }
							</td>	
							<th width="25%">
								<span class="red"></span>调任岗位类别
							</th>
							<td>
								${model.planPostTypeText }
							</td>	
						</tr>
						
						<tr>
							<th width="25%">
								<span class="red">*</span>变更类别
							</th>
							<td colspan="3">
								${model.manoeuvreTypeText }
							</td>
						</tr>
						<tr>
							<th width="25%">
								<span class="red">*</span>调动类型
							</th>
							<td colspan="3">
								${model.ddlxText }
							</td>
						</tr>
					
						<tr>
							<th width="25%">
								<span class="red"></span>变更原因
							</th>
							<td colspan="3">
								${model.reason }
							</td>		
						</tr>
						
						<tr>
							<th width="25%">
								<span class="red"></span>备注
							</th>
							<td colspan="3">
								${model.remark }
							</td>		
						</tr>
					</tbody>
				</table>
			</div>
			
			<div>
				<h3>
					审核情况
				</h3>
				<div>
					<c:forEach items="${auditStatusList}" var="bean" varStatus="sta">
						审核时间 ： ${bean.auditTimeText }
						<table>
							
							<tr>
								<th width="20%">审核环节</th>
								<td>${bean.taskNodeName }</td>
							</tr>
							<tr>
								<th width="20%">审核人</th>
								<td>${bean.personInfo.viewHtml['xm'] }</td>
							</tr>
							
							<tr>
								<th width="20%">审核意见</th>
								<td>${bean.opinion }</td>
							</tr>
							
							<tr>
								<th width="20%">审核结果</th>
								<td>${bean.resultText }</td>
							</tr>
							
						</table>
					</c:forEach>
				</div>
			</div>
			
			<div>
				<h3>
					审核情况
				</h3>
				<div>
					<table>
						<tr>
							<th width="20%">
								<span class="red"></span>执行状态
							</th>
							<td>
								${model.execuStatusText }
							</td>	
						</tr>
						
						<tr>
							<th width="20%">
								<span class="red"></span>变更执行时间
							</th>
							<td >
								${model.excuteTimeText }
							</td>	
						</tr>
					</table>
				</div>
			</div>
			
	</body>
</html>