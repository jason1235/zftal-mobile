<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
			<h3 class="datetitle_01">
				<span>快照数据<font color="#0457A7" style="font-weight:normal;"></font></span>
			</h3>
			<div style="overflow-x:auto;">
			<table width="100%" class="dateline tablenowrap" id="tiptab" >
				<thead id="list_head2">
					<tr>
					<td>快照时间</td>
					<s:iterator value="query.clazz.viewables" var="p">
					<s:if test="fieldName != 'zp'">
						<td>${p.name }</td>
					</s:if>
					</s:iterator>
					</tr>
				</thead>
				<tbody id="list_body2">
				<s:iterator value="pageList" var="overall">
				<tr>
				<td><s:date name="snapTime" format="yyyy-MM"/></td>
				<s:iterator value="clazz.viewables" var="p">
					<s:if test="fieldName != 'zp'">
						<td>${overall.viewHtml[p.fieldName]}</td>
					</s:if>
				</s:iterator>
				</tr>
				</s:iterator>
				</tbody>
			</table>
			</div>
<ct:page pageList="${pageList }" function="openWindowCallback"/>
