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
			
			//拷贝表单
			$(".btn_fz").click(function()
			{
				if(current==null){
					showWarning("请选择行");
					return false;
				}
				var guid = $(current).attr("id");
				
				showWindow("复制","<%=request.getContextPath() %>/bill/config_copy.html?spBillConfig.id="+guid, 320, 260);
			});
		
			//增加表单
			function addItem()
			{
				showWindow("增加","<%=request.getContextPath() %>/bill/config_add.html", 320, 260);
			}
	
			//修改表单
			function modifyItem(guid)
			{
				showWindow("修改","<%=request.getContextPath() %>/bill/config_modify.html?spBillConfig.id="+guid, 320, 260);
			}
			
			//配置表单
			function configItem(guid)
			{
				location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassList.html?spBillConfig.id="+guid;
			}
	
			//删除表单
			function removeItem(guid)
			{
				showConfirm("确定要删除吗？");
	
				$("#why_cancel").click(function(){
					alertDivClose();
				});
	
				$("#why_sure").click(function(){
					$.post('<%=request.getContextPath() %>/bill/config_remove.html', 'spBillConfig.id='+guid, function(data){
						var callback = function(){
							window.location.reload();
						};
						
						processDataCall(data, callback);
					}, "json");
				});
			}
			
			//删除表单
			function publishItem(guid)
			{
				$.post('<%=request.getContextPath() %>/bill/config_check.html', 'spBillConfig.id='+guid, function(data){
                    showConfirm(data.text+"确定要发布吗？");

                    $("#why_cancel").click(function(){
                        alertDivClose();
                    });
        
                    $("#why_sure").click(function(){
                        $.post('<%=request.getContextPath() %>/bill/config_publish.html', 'spBillConfig.id='+guid, function(data){
                        	var callback = function(){
                                window.location.reload();
                            };
                            
                            processDataCall(data, callback);
                        }, "json");
                    });
                }, "json");
			}
			
			function operationList(){
				$("a[name='config']").click(function(){//行数据配置
					var guid = $(this).closest("tr").attr("id");
					configItem(guid);
				})
				$("a[name='modify']").click(function(){//行数据修改链接
					var guid = $(this).closest("tr").attr("id");
					modifyItem(guid);
				});
				$("a[name='del']").click(function(){//行数据删除链接
					var guid = $(this).closest("tr").attr("id");
					removeItem(guid);
				});
				$("a[name='publish']").click(function(){//行数据删除链接
					var guid = $(this).closest("tr").attr("id");
					publishItem(guid);
				});
				$("a[name='exportTemp']").click(function(){//行数据删除链接
                    var guid = $(this).closest("tr").attr("id");
                    showWindow("修改","<%=request.getContextPath() %>/bill/config_toUpload.html?spBillConfig.id="+guid, 340, 180);
                });
				$(".select_tools a").css("cursor","pointer");
				operationHover();
			}
			
			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/bill/config_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			
			operationList();
			$("#bill_type").val("${query.bill_type}");
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
	<div class="toolbox" style="z-index:0;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" href="#" class="btn_zj">增加</a></li>
				<li><a id="btn_zj" href="#" class="btn_fz">复制</a></li>
			</ul>
		</div>
	</div>
	<form id="search" action="" method="post">
	<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 	<input type="hidden" id="asc" name="asc" value="${asc}"/>
	<div class="searchtab" id="searchtab">
		<table width="100%" border="0">
			<tbody>
				<tr>
					<th>表单名称</th>
					<td>
						<input type="text" name="query.name" class="text_nor" style="width: 130px;" value="${query.name }" />
					</td>
					<th>
						表单类型
					</th>
					<td>
						<select name="query.bill_type" style="width:150px" id="bill_type">
							<option value="">全部</option>
							<option value="COMMIT">上报</option>
							<option value="APPROVE">审批</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>数据表名</th>
					<td>
						<input type="text" name="query.id_name" class="text_nor" style="width: 130px;" value="${query.id_name }" />
					</td>
					<th>使用状态</th>
					<td>
						<select name="query.status" style="width:150px" id="status">
							<option value="">全部</option>
							<option value="INITIALIZE">初始化</option>
							<option value="USING">使用中</option>
							<option value="UNUSE">未使用</option>
						</select>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4">
						<div class="btn">
							<button class="btn_cx" type="button" name="search">查 询</button>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
	<div class="formbox">
		<h3 class="datetitle_01">
			<span>表单列表</span>
		</h3>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<c:if test="${'NAME' eq sortFieldName}">
						<td class="sort_title_current_${asc }" id="NAME">表单名称</td>
					</c:if>
					<c:if test="${'NAME' != sortFieldName}">
						<td class="sort_title" id="NAME">表单名称</td>
					</c:if>
					<c:if test="${'BILL_TYPE' eq sortFieldName}">
						<td class="sort_title_current_${asc }" id="BILL_TYPE">表单类型</td>
					</c:if>
					<c:if test="${'BILL_TYPE' != sortFieldName}">
						<td class="sort_title" id="BILL_TYPE">表单类型</td>
					</c:if>
					<c:if test="${'ID_NAME' eq sortFieldName}">
						<td class="sort_title_current_${asc }" id="ID_NAME">数据表名</td>
					</c:if>
					<c:if test="${'ID_NAME' != sortFieldName}">
						<td class="sort_title" id="ID_NAME">数据表名</td>
					</c:if>
					<c:if test="${'STATUS' eq sortFieldName}">
						<td class="sort_title_current_${asc }" id="STATUS">表单状态</td>
					</c:if>
					<c:if test="${'STATUS' != sortFieldName}">
						<td class="sort_title" id="STATUS">表单状态</td>
					</c:if>
					<td>已发版本</td>
					<td width="12%">操作</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${spBillConfigList}" var="bean" varStatus="i">
				<tr id="${bean.id }">
					<td>${i.index+beginIndex }</td>
					<td>${bean.name }</td>
					<td>
						<c:if test="${bean.bill_type=='COMMIT'}">上报</c:if>
						<c:if test="${bean.bill_type=='APPROVE'}">审批</c:if>
					</td>
					<td>${bean.id_name }</td>
					<td>
						<c:if test="${bean.status=='INITIALIZE'}">初始化</c:if>
						<c:if test="${bean.status=='USING'}">使用中</c:if>
						<c:if test="${bean.status=='UNUSE'}">未使用</c:if>
					</td>
					<td>${bean.versions }</td>
					<td>
					  <div>
				      	<div class="current_item">
				        	<span class="item_text">表单配置</span>
				        </div>
				        <div class="select_tools" style=" width:80px; display:none">
				            <ul>
				            	<li><a name="config" href="#" class="first1">表单配置</a></li>
				                <li><a name="modify" href="#" class="last1">修改</a></li>
				                <li><a name="del" href="#" class="last1">废弃</a></li>
				                <li><a name="publish" href="#" class="last1">发布</a></li>
				                <li><a name="exportTemp" href="#" class="last1">导出模板</a></li>
				            </ul>
				        </div>
				      </div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			 <ct:page pageList="${spBillConfigList }" />				
		</div>
		</form>
	</div>
</body>

</html>