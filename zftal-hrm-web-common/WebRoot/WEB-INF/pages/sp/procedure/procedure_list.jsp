<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
		$(function(){
			var current = null;

			//监听单击行
			$("tbody > tr").click(function(){
				var guid = $(this).attr("id");
				
				if( guid == null )
				{
					return;
				}
				
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			
			//监听双击行
			$("tbody > tr").dblclick(function(){
				var guid = $(this).attr("id");
				
				if( guid == null )
				{
					return;
				}
				
				modifyItem( guid );
			});

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/sp/spprocedure_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});

			//监听增加
			$(".btn_zj").click( function(){
				addItem();
			});
	
		
		//增加信息类属性
		function addItem()
		{
			showWindow("增加","<%=request.getContextPath() %>/sp/spprocedure_addProcedure.html", 720, 330);
		}

		//修改信息类属性
		function modifyItem(guid)
		{
			showWindow("修改","<%=request.getContextPath() %>/sp/spprocedure_modifyProcedure.html?spQuery.pid="+guid, 720, 330);
		}
		
		//流程配置
		function configItem(guid)
		{
			location.href="<%=request.getContextPath() %>/sp/spprocedure_configProcedure.html?spQuery.pid="+guid;
		}		
		//流程预览
		function previewItem(guid)
		{
			showWindow("预览","<%=request.getContextPath() %>/sp/spprocedure_detail.html?pid="+guid, 720, 400);
		}
		
		//删除信息类属性
		function removeItem(guid)
		{
			showConfirm("确定要删除吗？");

			$("#why_cancel").click(function(){
				divClose();
			});

			$("#why_sure").click(function(){
				$.post('<%=request.getContextPath() %>/sp/spprocedure_removeProcedure.html', 'spQuery.pid='+guid, function(data){
					var callback = function(){
						window.location.reload();
					};
					
					processDataCall(data, callback);
				}, "json");
			});
		}
		//启用
		function enabledItem(guid)
		{
			showConfirm("确定要启用吗？");

			$("#why_cancel").click(function(){
				divClose();
			});

			$("#why_sure").click(function(){
				$.post('<%=request.getContextPath() %>/sp/spprocedure_enabledProcedure.html', 'spQuery.pid='+guid, function(data){
					var callback = function(){
						window.location.reload();
					};
					
					processDataCall(data, callback);
				}, "json");
			});
		}
		//不启用
		function disabledItem(guid)
		{
			showConfirm("确定要停用吗？");

			$("#why_cancel").click(function(){
				divClose();
			});

			$("#why_sure").click(function(){
				$.post('<%=request.getContextPath() %>/sp/spprocedure_disabledProcedure.html', 'spQuery.pid='+guid, function(data){
					var callback = function(){
						window.location.reload();
					};
					
					processDataCall(data, callback);
				}, "json");
			});
		}

		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var guid = $(this).closest("tr").attr("id");
				modifyItem(guid);
			});
			$("a[name='del']").click(function(){//行数据删除链接
				var guid = $(this).closest("tr").attr("id");
				removeItem(guid);
			});
			$("a[name='enabled']").click(function(){//行数据启用链接
				var guid = $(this).closest("tr").attr("id");
				enabledItem(guid);
			});
			$("a[name='disabled']").click(function(){//行数据停用链接
				var guid = $(this).closest("tr").attr("id");
				disabledItem(guid);
			});
			$("a[name='config']").click(function(){//行数据配置
				var guid = $(this).closest("tr").attr("id");
				configItem(guid);
			});
			$("a[name='preview']").click(function(){//行数据预览
				var guid = $(this).closest("tr").attr("id");
				previewItem(guid);
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
		}
		operationList();
		$("#pbelongToSysName").val("${query.pbelongToSysName}");
		$("#pstatus").val("${query.pstatus}");
		fillRows(20, '', '', false);
	});
	
	/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#search").submit();
		}
	</script>
</head>

<body>
	<div class="toolbox" style="z-index: 10;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" href="#" class="btn_zj">增加</a></li>
			</ul>
		</div>
	</div>
	
	<div class="formbox">
		<form id="search" action="<%=request.getContextPath()%>/sp/spprocedure_list.html" method="post">
			<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 			<input type="hidden" id="asc" name="asc" value="${asc}"/>
			<div class="searchtab" id="searchtab">
				<table width="100%" border="0">
					<tbody>
						<tr>
							<th>流程名称</th>
							<td>
								<input type="text" name="query.pname" class="text_nor" style="width: 130px;" value="${query.pname }" />
							</td>
							<th>
								业务模块
							</th>
							<td>
								<select name="query.pbelongToSysName" id="pbelongToSysName">
									<option value="">全部</option>
									<c:forEach items="${belongToSyses}" var="belongToSys">
										<option value="${belongToSys.gnmkdm }" >${belongToSys.gnmkmc }</option>
									</c:forEach>
								</select>
							</td>
							<th>状	   态</th>
							<td>
								<select name="query.pstatus" id="pstatus">
									<option value="">全部</option>
									<option value="1">启用</option>
									<option value="0">停用</option>
								</select>
							</td>
							<td>
								<div class="btn">
									<button class="btn_cx" type="button" name="search">查 询</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<h3 class="datetitle_01">
				<span>流程信息（提示：双击可以查看选定行）</span>
			</h3>
			<table summary="" class="dateline" align="" width="100%">
					<thead id="list_head">
						<tr>
							<td>序号</td>
							<c:if test="${'P_NAME' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="P_NAME">流程名称</td>
							</c:if>
							<c:if test="${'P_NAME' != sortFieldName}">
								<td class="sort_title" id="P_NAME">流程名称</td>
							</c:if>
							<c:if test="${'P_TYPE' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="P_TYPE">流程类型</td>
							</c:if>
							<c:if test="${'P_TYPE' != sortFieldName}">
								<td class="sort_title" id="P_TYPE">流程类型</td>
							</c:if>
							<c:if test="${'BELONG_TO_SYS_NAME' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="BELONG_TO_SYS_NAME">业务模块</td>
							</c:if>
							<c:if test="${'BELONG_TO_SYS_NAME' != sortFieldName}">
								<td class="sort_title" id="BELONG_TO_SYS_NAME">业务模块</td>
							</c:if>
							<c:if test="${'P_DESC' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="P_DESC">流程说明</td>
							</c:if>
							<c:if test="${'P_DESC' != sortFieldName}">
								<td class="sort_title" id="P_DESC">流程说明</td>
							</c:if>
							<c:if test="${'P_STATUS' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="P_STATUS">状态</td>
							</c:if>
							<c:if test="${'P_STATUS' != sortFieldName}">
								<td class="sort_title" id="P_STATUS">状态</td>
							</c:if>
							<td width="12%">操作</td>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${procedureList}" var="bean" varStatus="i">
						<tr id="${bean.pid }">
							<td>${i.index+beginIndex }</td>
							<td>${bean.pname }</td>
							<td>${bean.ptypeStr }</td>
							<td>${bean.belongToSysName}</td>
							<td>${bean.pdesc }</td>
							<td><c:if test="${bean.pstatus eq '1' }">启用</c:if><c:if test="${bean.pstatus eq '0' }">停用</c:if></td>
							<td>
							  <div>
						      	<div class="current_item">
						        	<span class="item_text">修改</span>
						        </div>
						        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
						            <ul>
						                <li><a name="modify" href="#" class="first1">修改</a></li>
						                <li><a name="config" href="#" class="last1">配置</a></li>
						                <li><a name="preview" href="#" class="last1">预览</a></li>
						                <li><a name="enabled" href="#" class="last1">启用</a></li>
						                <li><a name="disabled" href="#" class="last1">停用</a></li>
						                <li><a name="del" href="#" class="last1">删除</a></li>
						            </ul>
						        </div>
						      </div>
							</td>
						</tr>
						</c:forEach>
					</tbody>
			</table>
			<div>
				<ct:page pageList="${procedureList }" />
			</div>
		</form>
	</div>
</body>
</html>