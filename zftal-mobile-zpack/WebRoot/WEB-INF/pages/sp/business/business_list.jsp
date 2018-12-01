<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
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

			//监听增加
			$(".btn_zj").click( function(){
				addItem();
			});
			
		//修改信息类属性
		function modifyItem(guid)
		{
			showWindow("修改","<%=request.getContextPath() %>/sp/spbusiness_modifyBusiness.html?spBusiness.bid="+guid, 720, 330);
		}

		//修改信息类属性
        function addItem()
        {
            showWindow("增加","<%=request.getContextPath() %>/sp/spbusiness_addBusiness.html", 720, 330);
        }
		
		$("button[name='search']").click(function(e){
			$("#search").attr("action","<%=request.getContextPath()%>/sp/spbusiness_list.html");
			$("#search").attr("method","post");
			$("#search").submit();
			e.preventDefault();//阻止默认的按钮事件，防止多次请求
		});
		
		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var guid = $(this).closest("tr").attr("id");
				modifyItem(guid);
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
		}
		
		operationList();
		$("#link").val("${query.link}");
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
	<form id="search" method="post" action="<%=request.getContextPath()%>/sp/spbusiness_list.html">
	<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 	<input type="hidden" id="asc" name="asc" value="${asc}"/>
	<div class="searchtab" id="searchtab">
		<table width="100%" border="0">
			<tbody>
				<tr>
					<th>业务流程</th>
					<td>
						<input type="text" name="query.b_name" class="text_nor" style="width: 130px;" value="${query.b_name }" />
					</td>
					<th>
						业务模块
					</th>
					<td>
						<select name="query.link" id="link">
							<option value="">全部</option>
							<c:forEach items="${belongToSyses}" var="belongToSys">
								<option value="${belongToSys.gnmkdm }" <c:if test="${belongToSys.gnmkdm  eq spProcedure.belongToSys}"> selected="selected"</c:if>>${belongToSys.gnmkmc }</option>
							</c:forEach>
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
	
	<div class="formbox">
		<h3 class="datetitle_01">
			<span>业务流程信息（提示：双击可以查看选定行）</span>
		</h3>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<c:if test="${'B_NAME' eq sortFieldName}">
						<td class="sort_title_current_${asc }" id="B_NAME">业务流程名</td>
					</c:if>
					<c:if test="${'B_NAME' != sortFieldName}">
						<td class="sort_title" id="B_NAME">业务流程名</td>
					</c:if>
					<c:if test="${'BELONG_TO_SYS_NAME' eq sortFieldName}">
						<td class="sort_title_current_${asc }" id="BELONG_TO_SYS_NAME">业务模块</td>
					</c:if>
					<c:if test="${'BELONG_TO_SYS_NAME' != sortFieldName}">
						<td class="sort_title" id="BELONG_TO_SYS_NAME">业务模块</td>
					</c:if>
					<c:if test="${'BELONG_TO_SYS_NAME' eq sortFieldName}">
						<td class="sort_title_current_${asc }" id="BELONG_TO_SYS_NAME">使用流程名称</td>
					</c:if>
					<c:if test="${'BELONG_TO_SYS_NAME' != sortFieldName}">
						<td class="sort_title" id="BELONG_TO_SYS_NAME">使用流程名称</td>
					</c:if>
					<td width="12%">操作</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${businessList}" var="bean" varStatus="i">
					<tr id="${bean.bid }">
						<td>${i.index+beginIndex }</td>
						<td>${bean.bname }</td>
						<td>${bean.belongToSysName}</td>
						<td>${bean.procedure.pname }</td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">修改</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="modify" href="#" class="first1">修改</a></li>
<%--					                <li><a name="del" href="#" class="last1">删除</a></li>--%>
					            </ul>
					        </div>
					      </div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div>
		<ct:page pageList="${businessList }" />		
	</div>
	</form>
</body>
</html>