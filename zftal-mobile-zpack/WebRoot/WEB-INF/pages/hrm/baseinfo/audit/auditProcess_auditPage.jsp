<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="ic" uri="/info-class"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
			var current = null;

			$("tbody > tr[name^='tr']").click(function(){	//监听单击行
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");
				current = $(this);
			});
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[id='guid']").val();
				queryEntity(id);
			});

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/baseinfo/auditProcess_auditPage.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});

			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function queryEntity(id){//查询
			//showWindow("查看", "<%=request.getContextPath()%>/baseinfo/auditProcess_audit.html?process.guid="+id, 480, 300 );
			window.location.href = _path + "/baseinfo/auditProcess_audit.html?process.guid="+id;
		}
		
		function operationList(){

			$("a[name='view']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity(id);
			});
			
			$(".select_tools a").css("cursor","pointer");
			operationHover();
		}
		
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form action="baseinfo/auditProcess_viewPage.html" name="search" id="search" method="post">
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>待审核信息列表<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以审核信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="10%">申请人</td>
						<td width="35%">信息类</td>
						<td width="15%">申请时间</td>
						<td width="20%">审核步骤</td>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="process">
						<tr name="tr">
						<input type="hidden" id="guid" value="${process.guid }"/>
						<td><ct:PersonParse code="${process.gh }"/></td>
						<td><ic:class classId="${process.classId }" /></td>
						<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
							步骤(${process.step+1 }/${process.stepSize })：<ct:RoleParse code="${process.roleId }"/>
						</td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">审核</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="view" href="#" class="tools_list">审核</a></li>
					            </ul>
					        </div>
					      </div>
						</td>
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" />
	</div>
  	  </form>
  </body>
</html>
