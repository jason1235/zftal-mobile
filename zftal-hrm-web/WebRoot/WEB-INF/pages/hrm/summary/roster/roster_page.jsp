<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants,com.zfsoft.common.factory.SessionFactory" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript">
    $(function(){
        	var _tr;
			$("#btn_zj").click(function(){//功能条增加按钮
				showWindow("增加","<%=request.getContextPath()%>/summary/roster_input.html","480","230");
			});
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity(id);
			});
			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/summary/roster_page.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			operationList();//初始化操作栏目
			
			fillRows($("#pageSize").val(), "", "", false);//填充空行
		});
		function queryEntity(id){//查询
			showWindow("处理情况","<%=request.getContextPath()%>/summary/roster_query.html?roster.guid="+id,"480","230");
		}
		
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/summary/roster_delete.html',"roster.guid="+id,function(data){
				var callback = function(){
					$("button[name='search']").click();
				};
				processDataCall(data, callback);
			},"json");
		}

		function preDel(id){//删除前操作
			showConfirm("确定要删除吗？");
			$("#why_cancel").click(function(){
				divClose();
			});
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}
		
		function operationList(){
			$("a[name='modify']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity(id);
				_tr = $(this).closest("tr");
			});
			$("a[name='delete']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				//_tr = $(this).closest("tr");
				preDel(id);
			});
			$("a[name='use']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				window.location.href = _path+"/summary/rosterData_page.html?guid="+id;
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
			$("a[name='modify']").each(function(){
				$(this).attr("title","双击数据行可以进入修改");
			});
		}
		
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
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">增 加</a>
						</li>
					</ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form action=""<%=request.getContextPath()%>/summary/roster_page.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
		  <th width="10%">名称</th>
          <td width="24%">
          	<input type="text" name="query.name" class="text_nor" style="width:180px" value="${query.name }"/>
          </td>
          <td colspan="6">
            <div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>

        </tr>
      </tbody>
    </table>
  </div>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>花名册管理<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<c:if test="${'BT' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="BT">名称</td>
						</c:if>
						<c:if test="${'BT' != sortFieldName}">
							<td class="sort_title" id="BT">名称</td>
						</c:if>
						<c:if test="${'ZS' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="ZS">描述 </td>
						</c:if>
						<c:if test="${'ZS' != sortFieldName}">
							<td class="sort_title" id="ZS">描述</td>
						</c:if>
						<c:if test="${'LX' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="LX">类型 </td>
						</c:if>
						<c:if test="${'LX' != sortFieldName}">
							<td class="sort_title" id="LX">类型</td>
						</c:if>
						<c:if test="${'CREATOR' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="CREATOR">创建人</td>
						</c:if>
						<c:if test="${'CREATOR' != sortFieldName}">
							<td class="sort_title" id="CREATOR">创建人</td>
						</c:if>
						<c:if test="${'CREATETIME' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="CREATETIME">创建时间</td>
						</c:if>
						<c:if test="${'CREATETIME' != sortFieldName}">
							<td class="sort_title" id="CREATETIME">创建时间</td>
						</c:if>
						<td width="100px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="roster">
						<tr>
						<td name="name">${roster.name}</td>
						<td name="description">${roster.description}</td>
						<td name="rosterType">
							<c:if test="${roster.rosterType=='PERSONAL'}">个人</c:if>
							<c:if test="${roster.rosterType=='PUBLIC'}">公共</c:if></td>
						<td><ct:PersonParse code="${roster.creator}" /></td>
						<td><s:date name="createtime" format="yyyy-MM-dd HH:mm:ss"/><input type="hidden" id="guid" value="${roster.guid}"/></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">使用</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="use" href="#" class="first1">使用</a></li>
					                <c:if test="${roster.creator==currentUserYhm}">
						                <li><a name="modify" href="#" class="tools_list">修改</a></li>
						                <li><a name="delete" href="#" class="last1">删除</a></li>
						           	</c:if>
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
