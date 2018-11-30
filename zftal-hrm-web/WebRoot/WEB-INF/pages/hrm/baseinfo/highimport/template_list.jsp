<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
    <%@ include file="/commons/hrm/head.ini" %>
    <script>
    $(function(){
    	$("#btn_zj").click(function(){
    		showWindow("增加","<%=request.getContextPath()%>/baseinfo/highImport_input.html",550,300);
    	});
    	    	
    	operationList();//操作栏初始化
    	fillRows("20","","",false);
    });
    
	function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").attr("trKey");
				modifyEntity(id);
			});
			
			$("a[name='del']").click(function(){//行数据删除链接
				var id = $(this).closest("tr").attr("trKey");
				delEntiry(id);
			});
			
			$('a[name="maintain"]').click(function(){//配置条目维护链接
				var id = $(this).closest("tr").attr("trKey");
				location.href="<%=request.getContextPath()%>/baseinfo/property_list.html?template.id="+id;
			});
			
			$(".select_tools a").css("cursor","pointer");
			operationHover();
	}
	
	function modifyEntity(id){
		showWindow("增加","<%=request.getContextPath()%>/baseinfo/highImport_modify.html?template.id="+id,550,300);
	}
	
	function delEntiry(id){
		$.post(_path+'/baseinfo/highImport_delete.html',"template.id="+id,function(data){
			var callback = function(){
				$("#search").submit();
			};
			processDataCall(data,callback);
		},"json");
	}
   </script>
  </head>
  <body>
	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" class="btn_zj">增 加</a></li>
			</ul>
		</div>

		<form id="search" method="post" action="<%=request.getContextPath()%>/baseinfo/highImport_list.html">
    	<h3 class="datetitle_01">
    		<span>高级导入模版列表</span>
    	</h3>
		<table width="100%" class="dateline" id="tiptab">
			<thead id="list_head">
				<tr>
					<td width="10%"><p align="center">序号</p></td>
					<td width="40%"><p align="center">模版名称</p></td>
					<td width="40%"><p align="center">备注</p></td>
					<td width="10%"><p align="center">操作</p></td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${pageList}" var="template" varStatus="st">
				<tr name="tr" trKey="${template.id }">
					<td>${st.index+1 }</td>
					<td name="mbmc">${template.mbmc }</td>
					<td name="bz">${template.bz }</td>
					<td>
					  <div>
				      	<div class="current_item">
				        	<span class="item_text">配置</span>
				        </div>
				        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
				            <ul>
				            	<li><a name="maintain" href="#" class="first1">配置</a></li>
				                <li><a name="modify" href="#" class="tools_list">修改</a></li>
				                <li><a name="del" href="#" class="tools_list">删除</a></li>
				            </ul>
				        </div>
				      </div>
					</td>
				</tr>
				</c:forEach>
			</tbody>				
	  	</table>
	  	<div>
	  		<ct:page pageList="${pageList }" query="${templete }" queryName="templete"/>
	  	</div>
  		</form>
  	</div>
  </body>
</html>