<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script type="text/javascript">
		$(function(){
			var current = null;

			//监听单击行
			$("tbody[name='node_table']> tr").click(function(){
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
			$("tbody[name='node_table']> tr").dblclick(function(){
				var guid = $(this).attr("id");
				
				if( guid == null )
				{
					return;
				}
				
				modifyItem( guid );
			});

			//监听增加
			$("#btn_zjjd").click( function(){
				addItem();
			});
			
		
		//增加信息类属性
		function addItem()
		{
			showWindow("增加","<%=request.getContextPath() %>/sp/spnode_addNode.html?spNode.pid=${spProcedure.pid}", 720, 550);
		}

		//修改信息类属性
		function modifyItem(guid)
		{
			showWindow("修改","<%=request.getContextPath() %>/sp/spnode_modifyNode.html?spNode.pid=${spProcedure.pid}&spNode.nodeId="+guid, 720, 550);
		}
		
		//删除信息类属性
		function removeItem(guid)
		{
			showConfirm("确定要删除吗？");

			$("#why_cancel").click(function(){
				divClose();
			});

			$("#why_sure").click(function(){
				$.post('<%=request.getContextPath() %>/sp/spnode_removeNode.html', 'spNode.nodeId='+guid, function(data){
					var callback = function(){
						window.location.reload();
					};
					
					processDataCall(data, callback);
				}, "json");
			});
		}

		function operationList(){
			$("a[name='modify_node']").click(function(){//行数据修改链接
				var guid = $(this).closest("tr").attr("id");
				modifyItem(guid);
			});
			$("a[name='del_node']").click(function(){//行数据删除链接
				var guid = $(this).closest("tr").attr("id");
				removeItem(guid);
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
		}
		operationList();
		});
	</script>
</head>

<body>
	<div class="toolbox" style="z-index: 10;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zjjd" href="#" class="btn_zj">增加节点</a></li>
			</ul>
		</div>
	</div>
	
	<div class="formbox">
		
		<h3 class="datetitle_01">
			<span>流程节点信息（提示：双击可以查看选定行）</span>
		</h3>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<td>节点名称</td>
					<td>相关角色</td>
					<td>节点类型</td>
					<td>节点任务</td>
					<td width="12%">操作</td>
				</tr>
			</thead>
			<tbody name="node_table" id="list_body">
				<c:forEach items="${spProcedure.spNodeList}" var="bean" varStatus="i">
				<tr id="${bean.nodeId}">
					<td>${i.index+1 }</td>
					<td>${bean.nodeName }</td>
					<td>${bean.roleName }</td>
					<td>${bean.nodeTypeStr }</td>
					<td><c:forEach items="${bean.spTaskList}" var="task">
							${task.taskName}&nbsp;
						</c:forEach>
					</td>
					<td>
					  <div>
				      	<div class="current_item">
				        	<span class="item_text">修改</span>
				        </div>
				        <div class="select_tools" id="select_tools_node" style=" width:80px; display:none">
				            <ul>
				                <li><a name="modify_node" href="#" class="first1">修改</a></li>
				                <li><a name="del_node" href="#" class="last1">删除</a></li>
				            </ul>
				        </div>
				      </div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>

</html>