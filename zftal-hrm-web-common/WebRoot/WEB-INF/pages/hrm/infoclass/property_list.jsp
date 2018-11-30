<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

	<script type="text/javascript">
		$(function(){
			initCheckBox();           
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
			})
			
			//监听双击行
			$("tbody > tr").dblclick(function(){
				var guid = $(this).attr("id");
				
				if( guid == null )
				{
					return;
				}
				
				modifyItem( guid );
			})

			//监听增加
			$(".btn_zj").click( function(){
				addItem();
			})
			$("#btn_sync").click(function(){
				showWindow("同步字段选择","<%=request.getContextPath() %>/infoclass/infoproperty_doSyncView.html?classId=${model.guid}", 480, 170);
			});
			//导出
			$(".btn_dc").click( function(){
				exportData();
			})

			//上移
			$(".btn_sy").click( function(){
				if( current == null )
				{
					showWarning("请选需要移动记录！");
					return;
				}
				
				var prev = current.prev();
				var prevIndex = prev.find("td:first").text();

				if( prevIndex == "" )
				{
					showWarning("已经在第一行，无法上移！");
					return;
				}

				var guid = current.attr("id")
						+ ","
						+ prev.attr("id");

				$.post('<%=request.getContextPath() %>/infoclass/infoproperty_swap.html', 'guid='+guid, function(data){
					if( data.success ) {
						prev.find("td:first").text( current.find("td:first").text() );
						current.find("td:first").text(prevIndex)
						
						current.after(prev);
					}
				}, "json")
				
			})

			//下移
			$(".btn_xy").click( function(){
				if( current == null )
				{
					showWarning("请选需要移动记录！");
					return;
				}
				
				var next = current.next();
				var nextIndex = next.find("td:first").text();

				if( $.trim(nextIndex) == "" )
				{
					showWarning("已经在最后一行，无法下移！");
					return;
				}

				var guid = current.attr("id")
						+ ","
						+ next.attr("id");

				$.post('<%=request.getContextPath() %>/infoclass/infoproperty_swap.html', 'guid='+guid, function(data){
					if( data.success ) {
						next.find("td:first").text( current.find("td:first").text() );
						current.find("td:first").text(nextIndex)
						
						current.before(next);
					}
				}, "json")

			})
		})
		
		function initCheckBox(){
			var op = '${init}';			
			var objs = op.split(",");
            for ( var i = 0; i < objs.length; i++) {
            	var temp=objs[i];
		    	$("input[name='checkboxSel'][value='"+temp+"']").attr("checked",true);
            }
		}
		
		//增加信息类属性
		function addItem()
		{
			showWindow("增加","<%=request.getContextPath() %>/infoclass/infoproperty_edit.html?classId=${model.guid}", 720, 360);
		}

		//修改信息类属性
		function modifyItem(guid)
		{
			showWindow("修改","<%=request.getContextPath() %>/infoclass/infoproperty_edit.html?guid="+guid, 720, 360);
		}
		
		//修改信息类属性
		function syncItem(guid)
		{
			showWindow("同步","<%=request.getContextPath() %>/infoclass/infoproperty_sync.html?guid="+guid, 480, 170);
		}

		//删除信息类属性
		function removeItem(guid)
		{
			showConfirm("确定要删除吗？");

			$("#why_cancel").click(function(){
				divClose();
			})

			$("#why_sure").click(function(){
				$.post('<%=request.getContextPath() %>/infoclass/infoproperty_remove.html', 'guid='+guid, function(data){
					var callback = function(){
						$("form:first").submit();
						//window.location.reload();
					};
					
					processDataCall(data, callback);
				}, "json");
			})
		}

		//导出数据字典
		function exportData()
		{
			window.open("<%=request.getContextPath() %>/infoclass/infoclass_expDate.html");
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
			$("a[name='sync']").click(function(){//行数据删除链接
				var guid = $(this).closest("tr").attr("id");
				syncItem(guid);
			});
			//$(".select_tools a").css("cursor","pointer");
			//operationHover();
		}
	</script>
	<style type="text/css">
	.link{
	color: #1c69b6 !important;
	text-decoration: underline;
	}
	</style>
</head>

<body>
	<div class="toolbox" style="z-index:0;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" href="#" class="btn_zj">增加</a></li>
<%--				<li><a id="btn_dc" href="#" class="btn_dc">导出</a></li>--%>
				<li><a id="btn_sy" href="#" class="btn_sy">上移</a></li>
				<li><a id="btn_xy" href="#" class="btn_xy">下移</a></li>
				<li style="width: 215px;"><a id="btn_sync" href="#" class="btn_sx">同步数据</a></li>
				<li><input type="checkbox" name="checkboxSel" value="viewable"/>显示</li>
				<li><input type="checkbox" name="checkboxSel" value="editable"/>编辑</li>
				<li><input type="checkbox" name="checkboxSel" value="need"/>必填</li>
				<li><input type="checkbox" name="checkboxSel" value="syncToField"/>同步</li>
				<li><input type="checkbox" name="checkboxSel" value="virtual"/>虚拟</li>
			</ul>
		</div>
	</div>
	<div class="formbox">
		<h3 class="datetitle_01">
			<span>${model.name }属性信息</span>
		</h3>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<td>属性名称</td>
					<td>字段名称</td>
					<td>字段类型</td>
					<td>字段长度</td>
					<td>可显示</td>
					<td>可编辑</td>
					<td>必填</td>
					<td>同步</td>
					<td>虚拟</td>
					<td width="90px">操作</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${model.properties }" var="bean" varStatus="i">
				<tr id="${bean.guid }">
					<td>${i.index+1 }</td>
					<td>${bean.name }</td>
					<td>${bean.fieldName }</td>
					<td>${bean.typeInfo.text }</td>
					<td>${bean.fieldLen }</td>
					<td><c:if test="${bean.viewable eq true}">是</c:if><c:if test="${!bean.viewable eq true}"><font class="red">否</font></c:if></td>
					<td><c:if test="${bean.editable eq true}">是</c:if><c:if test="${!bean.editable eq true}"><font class="red">否</font></c:if></td>
					<td><c:if test="${bean.need eq true}">是</c:if><c:if test="${!bean.need eq true}"><font class="red">否</font></c:if></td>
					<td><c:if test="${!empty bean.syncToField}">是</c:if><c:if test="${empty bean.syncToField}"><font class="red">否</font></c:if></td>
					<td><c:if test="${bean.virtual eq true}">是</c:if><c:if test="${!bean.virtual eq true}"><font class="red">否</font></c:if></td>
					<td>
					  <div>
				      	<%--<div class="current_item">
				        	<span class="item_text">修改</span>
				        </div>
				        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
				            <ul>
				                <li><a name="modify" href="#" class="first1">修改</a></li>
				                <li><a name="sync" href="#" class="tools_list">同步</a></li>
				                <li><a name="del" href="#" class="last1">删除</a></li>
				            </ul>
				        </div>--%>
				        <div>
				            
					        <a name="modify" href="#" class="link">修改</a>
	                        <a name="sync" href="#" style="padding-left: 1px;" class="link">同步</a>
	                        <c:if test="${bean.sourceInit eq false}">
	                        <a name="del" href="#" style="padding-left: 1px;" class="link">删除</a>
	                        </c:if>
                        </div>
				      </div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<script type="text/javascript">
		operationList();
		fillRows(15, '', '', false);
		<c:if test="${model.typeInfo.editable eq false }">
		//$(".btn_zj").parent().hide();
		//$("a[name='sync']").parent().hide();
		//$("a[name='del']").parent().hide();
		$("a[name='sync']").hide();
		</c:if>
	</script>
</body>

</html>