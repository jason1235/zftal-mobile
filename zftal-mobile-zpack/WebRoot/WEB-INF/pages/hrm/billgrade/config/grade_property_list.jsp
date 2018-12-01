<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
     <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
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
			
			//增加表单
			function addItem()
			{
				showWindow("增加","<%=request.getContextPath() %>/billgrade/config_edit_property.html?oper=add&condition.configId=${conditionQuery.configId}", 550, 400);
			}
	
			//修改表单
			function modifyItem(guid)
			{
				showWindow("修改","<%=request.getContextPath() %>/billgrade/config_edit_property.html?oper=modify&condition.configId=${conditionQuery.configId}&condition.id="+guid, 550, 400);
			}
			
			//删除表单
			function removeItem(guid)
			{
				showConfirm("确定要删除吗？");
	
				$("#why_cancel").click(function(){
					alertDivClose();
				});
	
				$("#why_sure").click(function(){
					$.post('<%=request.getContextPath() %>/billgrade/config_remove_property.html', 'condition.id='+guid, function(data){
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
				$(".select_tools a").css("cursor","pointer");
				operationHover();
			}
			
			operationList();
			
			fillRows(15, '', '', false);
		});
	</script>
</head>

<body>
	<div class="toolbox" style="z-index:0;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" href="#" class="btn_zj">增加</a></li>
			</ul>
			<a style="cursor: pointer" class="btn_fh_rs" href="<%=request.getContextPath() %>/billgrade/config_list_config.html" id="back">返回</a>
		</div>
	</div>
	 <form action="billgrade/config_list_property.html?conditionQuery.configId=${conditionQuery.configId}" name="search" id="search" method="post">
    <div class="formbox">
        <h3 class="datetitle_01">
            <span>${config.name } 评分规则列表</span>
        </h3>
        <table summary="" class="dateline" align="" width="100%">
            <thead id="list_head">
                <tr>
                    <td>序号</td>
                    <td>表单类</td>
                    <td>分值</td>
                    <td>条件描述</td>
                    <td>操作</td>
                </tr>
            </thead>
            <tbody id="list_body">
                <c:forEach items="${conditionPageList}" var="condition" varStatus="i">
                <tr id="${condition.id }">
                    <td>${i.index+conditionQuery.startRow }</td>
                    <td>${classNameMap[condition.billClassId] }</td>
                    <td>${condition.score}</td>
                    <td>${condition.text}</td>
                    <td>
                      <div>
                        <div class="current_item">
                            <span class="item_text">修改</span>
                        </div>
                        <div class="select_tools" style=" width:80px; display:none">
                            <ul>
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
        <ct:page pageList="${conditionPageList }" query="${conditionQuery}" queryName="conditionQuery" />
    </div>
    </form>
	
    
</body>

</html>