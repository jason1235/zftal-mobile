<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body>
		<s:form id="operateLogForm" name="operateLogForm" action=""
			theme="simple">
			<div class="tab">
				<table width="100%" border="0" class=" formlist" cellpadding="0"
					cellspacing="0">
					<thead>
						<tr>
							<th colspan="4">
								<span>操作日志信息<font color="#0f5dc2"
									style="font-weight: normal;"> </font> </span>
							</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4">
								<div class="btn">
									<button id="bnt0a" onclick="iFClose();return false;">
										<span>关闭</span>
									</button>
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<tr>
							<th width="15%">
								操作用户
							</th>
							<td colspan="3">
								<s:label name="czr"></s:label>
							</td>
						</tr>
						<tr>
							<th width="15%">
								操作类型
							</th>
							<td width="35%">
								<s:label name="czlx"></s:label>
							</td>
						</tr>
						<tr>
							<th width="15%">
								操作日期
							</th>
							<td width="35%">
								<s:label name="czrq"></s:label>
							</td>
							<th width="15%">
								操作模块
							</th>
							<td width="35%">
								<s:label name="czmk"></s:label>
							</td>
						</tr>
						<tr>
							<th width="15%">
								主机名
							</th>
							<td width="35%">
								<s:label name="zjm"></s:label>
							</td>
							<th width="15%">
								IP地址
							</th>
							<td width="35%">
								<s:label name="ipdz"></s:label>
							</td>
						</tr>
						<tr>
							<th width="15%">
								业务名称
							</th>
							<td width="85%" colspan="3">
								<s:label name="ywmc"></s:label>
							</td>
						</tr>
						<tr>
							<th width="15%">
								操作描述
							</th>
							<td width="85%" colspan="3">
								<s:label name="czms"></s:label>
							</td>
						</tr>
					</tbody>
				</table>
		</s:form>


	</body>
</html>
