<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
	    var current = null;
		$(function(){

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
			
			//增加表单
			function addItem()
			{
				showWindow("增加","<%=request.getContextPath() %>/billgrade/config_edit_config.html?oper=add", 350, 230);
			}
	
			//修改表单
			function modifyItem(guid)
			{
				showWindow("修改","<%=request.getContextPath() %>/billgrade/config_edit_config.html?oper=modify&config.id="+guid, 350, 230);
			}
			
			//配置表单
			function configItem(guid)
			{
				location.href="<%=request.getContextPath() %>/billgrade/config_list_property.html?conditionQuery.configId="+guid;
			}
	
			//删除表单
			function removeItem(guid)
			{
				showConfirm("确定要删除吗？");
	
				$("#why_cancel").click(function(){
					alertDivClose();
				});
	
				$("#why_sure").click(function(){
					$.post('<%=request.getContextPath() %>/billgrade/config_remove_config.html', 'config.id='+guid, function(data){
						var callback = function(){
							window.location.reload();
						};
						
						processDataCall(data, callback);
					}, "json");
				});
			}
	
			function operationList(){
				$("a[name='config']").click(function(){//行数据配置
					var guid = $(this).closest("tr").attr("id");
					configItem(guid);
				});
				$("a[name='modify']").click(function(){//行数据修改链接
					var guid = $(this).closest("tr").attr("id");
					modifyItem(guid);
				});
				$("a[name='del']").click(function(){//行数据删除链接
					var guid = $(this).closest("tr").attr("id");
					removeItem(guid);
				});
				$("a[name='level']").click(function(){
                    var guid = $(this).closest("tr").attr("id");
                    showWindow("等第设置","<%=request.getContextPath() %>/billgrade/config_level_config.html?config.id="+guid, 350, 400);
                });
				$(".select_tools a").css("cursor","pointer");
				operationHover();
			}
			
			operationList();
			
			fillRows(15, '', '', false);

			$("button[name='close']").click(function(){
                $("#tips").fadeOut("slow",function(){
                    $("#scroll").empty();
                });
            });
		});

	</script>
</head>

<body>
	<div class="toolbox" style="z-index:0;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" href="#" class="btn_zj">增加</a></li>
			</ul>
		</div>
	</div>
	 <form action="billgrade/config_list_config.html" name="search" id="search" method="post">
	<div class="formbox">
		<h3 class="datetitle_01">
			<span>评分事件列表</span>
		</h3>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<td>事件名称</td>
					<td>表单名称</td>
					<td>业务模块</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${configPageList}" var="config" varStatus="i">
				<tr id="${config.id }">
					<td>${i.index+configQuery.startRow }</td>
					<td>${config.name }</td>
					<td>${billConfigMap[config.billConfigId] }</td>
					<td>${config.businessCodeText}</td>
					<td>
                      <div>
                        <div class="current_item">
                            <span class="item_text">配置规则</span>
                        </div>
                        <div class="select_tools" style=" width:80px; display:none">
                            <ul>
                                <li><a name="config" href="#" class="last1">配置规则</a></li>
                                <li><a name="level" href="#" class="last1">等第设置</a></li>
                                <li><a name="modify" href="#" class="last1">修改</a></li>
                                <li><a name="del" href="#" class="last1">删除</a></li>
                            </ul>
                        </div>
                      </div>
                    </td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<ct:page pageList="${configPageList }" query="${configQuery}" queryName="configQuery" />

	</div>
	</form>
</body>

</html>