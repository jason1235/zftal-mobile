<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	
	<script type="text/javascript">
		$(function(){
			$("#btn_fh").click(function(){
				location.href='<%=request.getContextPath() %>/sp/spprocedure_list.html';
			});
			
			$("#btn_ck2").click(function(){
				var pid = $("input[name='spProcedure.pid']").val();
				showWindow("查看",_path+"/sp/spprocedure_detail.html?pid="+pid,720,400);
			});
		});
	</script>
</head>

<body>
	<div class="toolbox" style="z-index: 10;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_fh" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a></li>				 
				<li><a onclick="return false;" id="btn_ck2" class="btn_ck" href="#">流程预览</a></li>
			</ul>
		</div>
	</div>
	<form>
		<input type="hidden" id="pid" name="spProcedure.pid" value="${spProcedure.pid }"/> 
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>流程设置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
<%--				<tfoot>--%>
<%--					<tr>--%>
<%--						<td colspan="4">--%>
<%--							<div class="bz">"<span class="red">*</span>"为必填项</div>--%>
<%--							<div class="btn">--%>
<%--								<button id="save">保 存</button>--%>
<%--								<button id="cancel">返回</button>--%>
<%--							</div>--%>
<%--						</td>--%>
<%--					</tr>--%>
<%--				</tfoot>--%>
				<tbody>
					<tr>
						<th>
							<span class="red"></span>流程类型
						</th>
						<td>
							${spProcedure.ptypeStr}
						</td>
						<th>
							<span class="red"></span>流程名称
						</th>
						<td>
							${spProcedure.pname}
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>业务模块
						</th>
						<td>
							${spProcedure.belongToSysName}
						</td>
						<th>
							<span class="red"></span>流程描述
						</th>
						<td>
							${spProcedure.pdesc}
						</td>
					</tr>
					<tr>
						<td colspan="4">
						<div>
						<%@include file="/WEB-INF/pages/sp/node/node_list.jsp" %>
						</div>
						</td>
					</tr>
					<tr>
						<td colspan="4">
						<div>
						<%@include file="/WEB-INF/pages/sp/line/line_list.jsp" %>
						</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
