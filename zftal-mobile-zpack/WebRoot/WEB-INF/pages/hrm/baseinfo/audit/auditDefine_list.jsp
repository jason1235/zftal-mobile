<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/baseinfo/auditDefine/config.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/baseinfo/role/role.js"></script>
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
				var id = $(this).find("input[id='classId']").val();
				preConfig(id);
			});

			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function configEntity(id){//配置
			var callback = function(){
				window.location.href = _path+"/baseinfo/auditDefine_list.html";
			};
			showWindow("配置", "<%=request.getContextPath()%>/baseinfo/auditDefine_edit.html?define.classId="+id, 480, 320,callback );
		}
		
		function preConfig(id){//删除前操作
			showConfirm("改动审核配置，将会重置待审核信息的审核步骤，确认修改配置？");
			$("#why_cancel").click(function(){
				alertDivClose();
			});
			$("#why_sure").click(function(){
				alertDivClose();
				configEntity(id);
			});
		}
		
		function operationList(){

			$("a[name='config']").click(function(){
				var id = $(this).closest("tr").find("input[id='classId']").val();
				preConfig(id);
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
					<ul>
						<%--<li>
							<a id="btn_zj" class="btn_zj">增 加</a>
						</li>
					--%></ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form name="search" id="search" method="post">
<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>审核定义列表<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以查看信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="20%">信息类</td>
						<td width="65%">审核定义</td>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="infoList" var="info">
						<tr name="tr">
						<input type="hidden" id="classId" value="${info.classId }"/>
						<td>${info.className }</td>
						<td>
						<c:forEach items="${info.list }" var="ad" varStatus="st">
						${ad.order }. ${ad.roleName }
						</c:forEach>
						</td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">配置</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="config" href="#" class="tools_list">配置</a></li>
					            </ul>
					        </div>
					      </div>
						</td>
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
	</div>
  	  </form>
  </body>
</html>
