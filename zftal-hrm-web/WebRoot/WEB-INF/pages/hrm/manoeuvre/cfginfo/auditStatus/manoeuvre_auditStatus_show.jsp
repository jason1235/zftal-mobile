<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" defer="" src="/zftal-hrm/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		
	<script type="text/javascript">
	$(function(){
		$("#back").click(function(){
			goBackToAuditStatusList();
		});
	})
	
	function goBackToAuditStatusList(){
		showWindow("审核","<%=request.getContextPath()%>/manoeuvre/AuditStatus_list.html?query.manoeuvreInfo.guid=${pquery.manoeuvreInfo.guid}&query.toPage=${pquery.toPage}", 720, 350);
	}
	
	</script>
	
  </head>
  
  <body>
  	<form>
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="6">
							<span>详细信息<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="btn">
							
								<button id="closeWin" type="button" onclick="divClose();return false;">关闭</button>
							
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					
					<tr>
						<th width="25%">
							<span class="red"></span>审核时间
						</th>
						<td>
							${info.auditTimeText }
						</td>	
						
						<th width="25%">
							<span class="red"></span>审核环节
						</th>
						<td>
							${info.taskNodeName }
						</td>
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>审核结果
						</th>
						<td>
							${info.resultText }
						</td>	
						
						<th width="25%">
							<span class="red"></span>审核人
						</th>
						<td>
							${info.personInfo.viewHtml['xm'] }
						</td>
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>审核意见
						</th>
						<td colspan="3">
							${info.opinion }
						</td>
					</tr>
					
					<%--<tr>
						<th width="25%">
							<span class="red"></span>备&nbsp;&nbsp;&nbsp;&nbsp;注
						</th>
						<td colspan="3">
							${info.remark }
						</td>
					</tr>
				--%></tbody>
			</table>
		</div>
	</form>
  </body>
</html>
