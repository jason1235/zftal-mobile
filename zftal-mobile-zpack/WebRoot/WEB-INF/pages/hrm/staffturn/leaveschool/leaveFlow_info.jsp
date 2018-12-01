<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
 	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
</head>
<body>
	<div class="tab">
		<table width="100%"  border="0" class="formlist">
			<thead>
		    	<tr>
		        	<th colspan="2">
		        		<span>离校流程管理<font color="#0f5dc2" style="font-weight:normal;"></font></span>
		        	</th>
		        </tr>
		    </thead>
		    <tfoot>
		    	<tr>
		    		<td colspan="4">
		    			<div class="btn">
		    				<button type="button" onclick="divClose();" >确 定</button>
		    			</div>
		    		</td>
		    	</tr>
		    </tfoot>
			<tbody id="form1">
				<tr>
					<td colspan="2" class="tdbox">
						<table summary="" class="dateline" align="" width="100%">
							<thead id="list_head1">
								<tr>
									<td>处理部门</td>
									<td>处理人</td>
									<td>处理时间</td>
									<td>处理状态</td>
								</tr>
							</thead>
							<tbody id="list_body1" >
								<s:iterator value="processList" var="process">
								<tr>
									<td><ct:codeParse catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${process.deptId }" /></td>
							   		<td><ct:PersonParse code="${process.operator }"/></td>
							   		<td><s:date name="operateDate" format="yyyy-MM-dd"/></td>
							   		<td><s:if test='status =="0"'><font color="red">未处理</font></s:if><s:else><font color="green">已处理</font></s:else></td>
								</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<th width="30%"><span class="red">*</span>工资结算</th>
					<td width="70%">
						<s:if test='flow.salaryStatus == "1"'>已结算</s:if><s:else><font class="red">未结算</font></s:else>
					</td>
				</tr>
				<tr>
					<th><span class="red"></span>处理结果</th>
					<td>${flow.comment }</td>
				</tr>
			</tbody>
		</table>
	</div>
	<script type="text/javascript">
	   var num =$("#list_body1 tr").length;
	   for(var i=5;i>num;i--){
		    $("#list_body1").append("<tr><td></td><td></td><td></td><td></td></tr>")
	   }
	</script>
</body>
</html>
