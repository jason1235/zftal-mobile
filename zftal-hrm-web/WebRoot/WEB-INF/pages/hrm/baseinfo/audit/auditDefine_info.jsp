<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table align="center" class="formlist" id="defineList">
	<thead id="list_head2">
		<tr>
			<th>序号</th>
			<th>角色</th>
			<th>范围</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="list_body2">
		<c:forEach items="${defineList }" var="define" varStatus="vs">
		<tr>
		   <input type="hidden" id="guid" value="${define.guid }"/>
		   <td>第${define.order }步</td>
		   <td>${define.roleName }</td>
		   <td>
		   	<c:if test="${define.scope == 'depart' }">部门</c:if>
		   	<c:if test="${define.scope == 'all' }">全部</c:if>
		   </td>
		   <td>
			  <div>
		      	<div class="current_item">
		        	<span class="item_text">删除</span>
		        </div>
		        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
		            <ul>
		                <li><a name="del" href="#" class="tools_list">删除</a></li>
		            </ul>
		        </div>
		      </div>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
