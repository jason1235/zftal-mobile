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
<script type="text/javascript">
$(function(){
	$("#action").click(function(){
		updateEntity();
	});
});

function updateEntity(){//修改
	var param = $("#form1 input,#form1 textarea").serialize();
	//var handler = $("input[name='flow.handler']").val();
	$.post('leaveSchool/leaveFlow_confirm.html',param,function(data){
		var callback = function(){
			//window.location.reload();
			location.href = _path+"/leaveSchool/leaveFlow_list.html";
		};
		processDataCall(data, callback);
	},"json");
}

function validate(){
	var v = $("#form1 input[name='step.deptId']");
	if($.trim(v.val()).length==0){
		//showDownError(v,"所属部门不能为空");
		alert("处理部门不能为空");
		return false;
	}
	v = $("#form1 input[name='step.handler']");
	if($.trim(v.val()).length==0){
		//showDownError(v,"岗位名称不能为空");
		alert("处理人不能为空");
		return false;
	}
	return true;
}
</script>
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
		    				<button type="button" id="action" >保 存</button>
		    				<button type="button" onclick="divClose();" >取 消</button>
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
						</table>
					</td>
				</tr>
				<tr>
					<th width="30%"><span class="red">*</span>工资结算</th>
					<td width="70%">
						<input type="radio" name="flow.salaryStatus" value="0" checked/>未结
						<input type="radio" name="flow.salaryStatus" value="1"/>已结
					</td>
				</tr>
				<tr>
					<th width="30%"><span class="red"></span>离校时间</th>
					<td width="70%">
						<input type="text"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"
							name="flow.leaveDate"
							value="${flow.leaveDateString }" />
					</td>
				</tr>
				<tr>
					<th><span class="red"></span>处理结果</th>
					<td>
						<textarea name="flow.comment" rows="3" style="width:300px"></textarea>
						<input type="hidden" name="flow.accountId" value="${flow.accountId }"/>
						<input type="hidden" name="flow.leaveStatus" value="1"/>
						<input type="hidden" name="flow.type" value="${flow.type }"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

<%--<div id="testID" >    --%>
<%--  <div class="tab">--%>
<%--	<table align="center" class="formlist">--%>
<%--		<thead>--%>
<%--			<tr>--%>
<%--				<th colspan="4">--%>
<%--					<span id="title">离校流程管理<font color="#0f5dc2" style="font-weight:normal;"></font></span>--%>
<%--				</th>--%>
<%--			</tr>--%>
<%--		</thead>--%>
<%--		<tbody >--%>
<%--			<tr>--%>
<%--				<th width="25%">处理部门</th>--%>
<%--				<th width="25%">处理人</th>--%>
<%--				<th width="25%">处理时间</th>--%>
<%--				<th width="25%">处理状态</th>--%>
<%--			</tr>--%>
<%--		   <s:iterator value="processList" var="process">--%>
<%--		   	<tr>--%>
<%--		   		<td><ct:codeParse catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${process.deptId }" /></td>--%>
<%--		   		<td><ct:PersonParse code="${process.operator }"/></td>--%>
<%--		   		<td><s:date name="operateDate" format="yyyy-MM-dd"/></td>--%>
<%--		   		<td><s:if test='status =="0"'><font color="red">未处理</font></s:if><s:else><font color="green">已处理</font></s:else></td>--%>
<%--		   	</tr>--%>
<%--		   </s:iterator>--%>
<%--		</tbody>--%>
<%--	</table>--%>
<%--	<br />--%>
<%--	<table align="center" class="formlist">--%>
<%--		<tbody id="form1">--%>
<%--			<tr>--%>
<%--				<th ><span class="red">*</span>工资结算</th>--%>
<%--				<td>--%>
<%--					<input type="radio" name="flow.salaryStatus" class="text_nor" value="0" checked/>未结--%>
<%--					<input type="radio" name="flow.salaryStatus" class="text_nor" value="1"/>已结--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<th ><span class="red"></span>处理结果</th>--%>
<%--				<td >--%>
<%--					<textarea name="flow.comment" rows="2" style="width:300px"></textarea>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--		   <input type="hidden" name="flow.accountId" value="${flow.accountId }"/>--%>
<%--		   <input type="hidden" name="flow.leaveStatus" value="1"/>--%>
<%--		   <input type="hidden" name="flow.type" value="${flow.type }"/>--%>
<%--		</tbody>--%>
<%--		<tfoot>--%>
<%--	      <tr>--%>
<%--	        <td colspan="2">--%>
<%--	            <div class="bz">"<span class="red">*</span>"为必填项</div>--%>
<%--	          	<div class="btn">--%>
<%--	            	<button id="action" name="action" >保 存</button>--%>
<%--	            	<button name="cancel" onclick='divClose();'>取 消</button>--%>
<%--	          	</div>--%>
<%--	        </td>--%>
<%--	      </tr>--%>
<%--		</tfoot>--%>
<%--	</table>--%>
<%--	</div>--%>
<%--</div>--%>
	<script type="text/javascript">
		fillRows( 5, 'list_head1', 'list_body1', false );
	</script>
</body>
</html>
