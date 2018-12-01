<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>

	<script type="text/javascript">
		var current = null;

		$(function(){
			$(".btn_zj").click(function(){
				showWindow("编辑","<%=request.getContextPath() %>/baseinfo/infogroup_edit.html", 720, 200);
			});

			$("tbody > tr").click( function(){
				var guid = $(this).find("input[name='guid']").val();

				if( guid == null ) {
					return;
				}

				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});

			$("tbody > tr").dblclick( function(){
				var guid = $(this).find("input[name='guid']").val();

				if( guid == null ) {
					return;
				}

				modify( guid );
			});
			
			operationList();//初始化操作栏目

			fillRows("15", "", "", false);//填充空行
		});
		
	  	function preDel(id){//删除前操作
			showConfirm("确定要删除吗？");
	
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}
	  	
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/baseinfo/infogroup_delete.html',"guid="+id,function(data){
				var callback = function(){
					$("form:first").submit();
				};
				
				processDataCall(data, callback);
			},"json");
		}

		function modify( id ) {//修改
			if( id == null ) {
				return;
			}
			
			showWindow( "修改", "<%=request.getContextPath()%>/baseinfo/infogroup_edit.html?guid=" + id, 720, 200 );
		}
		
		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("input[name='guid']").val();

				modify( id );
			});
			
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("input[name='guid']").val();

				preDel(id);
			});
			
			$(".select_tools a").css("cursor","pointer");
			
			operationHover();
		}
	</script>
</head>

<body>
	<form action="<%=request.getContextPath() %>/baseinfo/infogroup_list.html" method="post" >
	</form>
	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li>
					<a id="btn_zj" class="btn_zj">增 加</a>
				</li>
			</ul>
		</div>	
	</div>
	
	<h3 class="datetitle_01">
    	<span>组合查询条件配置<font color="#0457A7" style="font-weight:normal;">（提示：双击行可以修改）</font></span>
    </h3>
	
	<div class="formbox">
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<td>条件名称</td>
					<td>所属目录</td>
					<td>字段</td>
					<td>引用代码表</td>
					<td style="width:100px">操作</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${list }" var="bean" varStatus="i">
				<tr>
					<td>
						${i.index+1 }
						<input type="hidden" name="guid" value="${bean.guid}" />
					</td>
					<td>${bean.name }</td>
					<td>${bean.catalogName }</td>
					<td>${bean.fieldName}</td>
					<td>${bean.codeTableName }</td>
					<td>
						 <div>
					      	<div class="current_item">
					        	<span class="item_text">修改</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="modify" class="tools_list">修改</a></li>
					                <li><a name="del" class="last1">删除</a></li>
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