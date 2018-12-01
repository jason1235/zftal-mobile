<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/expertvote/audit.js"></script>
    <script type="text/javascript">
    $(function(){
			$("#back").click(function(){//功能条增加按钮
				location.href = _path+"/expertmanage/audit_page.html";
			});
    		loadView();
		});
    
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
					</ul>
					<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<div class="formbox">
	<input type="hidden" name="spBillConfig.id" value="${expertDeclare.config_id }"/>
	<input type="hidden" name="spBillInstance.id" value="${expertDeclare.instance_id }"/>
	<input type="hidden" name="privilegeExpression" value="${privilegeExpression }"/>
	<input type="hidden" name="expertDeclare.id" value="${expertDeclare.id }"/>
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>专家推荐审核<font color="#0457A7" style="font-weight:normal;" id="tip"></font></span>
    </h3>
	<div id="content" style="padding-top:20px;">
	</div>
	<div id="content2" style="display:none;">
		<form id="form1">
		<s:iterator value="excutedList" var="node">
		<div class="title-nav">
			<h3><span id="yuef">环节：${node.nodeName }</span></h3>
		</div>
		<div class="yuezj-list yuezj-list-cur" style="height:60px;">
			<div style="float:left;width:50%" class="yuezj-list-title">
				<strong>审核人：</strong><ct:PersonParse code="${node.auditorId }"/>
			</div>
			<div style="float:left;width:50%" class="yuezj-list-title">
				<strong>审核时间：</strong><s:date name="auditTime" format="yyyy-MM-dd HH:mm:ss"/>
			</div>
			<div style="float:left;width:50%" class="yuezj-list-title">
				<strong>审核意见：</strong>${node.suggestion }
			</div>
			<div style="float:left;width:50%" class="yuezj-list-title">
				<strong>审核状态：</strong><s:property value="%{@com.zfsoft.workflow.enumobject.WorkNodeStatusEnum@valueOf(status).getText()}"/>
			</div>
		</div>
		</s:iterator>
		<br>
		<div class="por-rz">
			<div class="por-rz-tool"><a id="logButton" href="#">查看审核日志</a></div>
			<div id="logContent" style="display:none;" class="por-rz-con">
				<table class="por-rz-tab">
					<tbody>
				<s:if test="logList.size()>0">
				<s:iterator value="logList" var="log">
					<tr><th style="width: 150px;"><s:date name="logTime" format="yyyy-MM-dd HH:mm" /></th><td>${log.ocontent }</td></tr>
				</s:iterator>
				</s:if>
				<s:else>
					<tr><th>-</th><td>暂无</td></tr>
				</s:else>
					</tbody>
				</table>
			</div>
		</div>
		<br>
		<div class="formbox">
			<div class="con_overlfow">
			<table width="100%" id="tiptab" class="formlist">
			<thead id="list_head2">
				<tr>
					<th colspan="4">${currentNode.nodeName }</th>
				</tr>
			</thead>
			<tbody id="list_body2">
				<tr>
					<th><span class="red">*</span>审核意见
						<input type="hidden" value="${currentNode.nodeId }" name="node.nodeId">
					</th>
					<td colspan="3"><textarea style="font-size:12px;width:400px" name="node.suggestion" row="3">${currentNode.suggestion }</textarea></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4">
					<div class="btn">
						<button type="button" name="save" id="input">保存</button>
						<button type="button" name="pass" id="input">通过</button>
						<button type="button" name="reject" id="input">不通过</button>
						<button type="button" name="back" id="input">退回</button>
					</div>
					</td>
				</tr>
			</tfoot>
			</table>
			</div>
		</div>
		<div style="position: absolute;display:none; z-index: 1; width: 110px;" class="btn" id="menu">
		<button style="margin-top:4px;margin-right:3px" type="button" name="backButton" id="-1">退回上报人</button>
			<s:iterator value="excutedList" var="node">
			<button style="margin-top:4px;margin-right:3px" type="button" name="backButton" id="${node.nodeId }">${node.nodeName }</button>
			</s:iterator>
		</div>
		</form>
	</div>
  </body>
</html>
