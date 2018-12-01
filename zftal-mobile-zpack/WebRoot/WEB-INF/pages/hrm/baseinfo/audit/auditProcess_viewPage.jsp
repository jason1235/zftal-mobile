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
				$("#search").attr("action","<%=request.getContextPath()%>/baseinfo/auditProcess_viewPage.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});

			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function queryEntity(id){//查询
			showWindow("查看", "<%=request.getContextPath()%>/baseinfo/auditProcess_view.html?process.guid="+id, 480, 220 );
		}
		
		function operationList(){

			$("a[name='view']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity(id);
			});
			$("a[name='viewMore']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				window.location.href = _path+"/baseinfo/auditProcess_viewMore.html?process.guid="+id;
			});
			
			$(".select_tools a").css("cursor","pointer");
			$("select[name='query.stepType']").val("${query.stepType}");
			operationHover();
		}
		
    </script>
  </head>
  <body>
 <form action="baseinfo/auditProcess_viewPage.html" name="search" id="search" method="post">
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
		  <th width="10%">审核状态</th>
		  <td width="20%">
          	<select name="query.stepType" style="width:180px">
          		<option value="" selected>全部</option>
          		<option value="-1">拒绝通过</option>
          		<option value="2">待审核</option>
          		<option value="1">审核中</option>
          		<option value="99">审核通过</option>
          	</select>
		  </td>
		  <th width="10%">&nbsp;</th>
		  <td width="20%">&nbsp;</td>
		  <th width="10%">&nbsp;</th>
		  <td width="20%">&nbsp;</td>
        </tr>
      </tbody>
      <tfoot>
      	<tr>
      	  <td colspan="6">
		  	<div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>
      	</tr>
      </tfoot>
    </table>
  </div>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>审核信息申请列表<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以查看信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="50%">信息类</td>
						<td width="15%">申请时间</td>
						<td width="16%">审核状态</td>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="process">
						<tr name="tr">
						<input type="hidden" id="guid" value="${process.guid }"/>
						<td><ic:class classId="${process.classId }" /></td>
						<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
						<s:if test="step == -1"><font color="red">拒绝通过</font></s:if>
						<s:elseif test="step==0"><font color="grey">待审核</font></s:elseif>
						<s:elseif test="step>0 and step<99"><font color="blue">审核中</font></s:elseif>
						<s:elseif test="step==99"><font color="green">审核通过</font></s:elseif>
						<s:else>未知状态(${process.step })</s:else>
						</td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<%--<span class="item_text">查看</span>
					        --%>
					        <span>详情</span></div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <%--<li><a name="view" href="#" class="tools_list">查看</a></li>
					                --%><li><a name="viewMore" href="#" class="tools_list">详情</a></li>
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
