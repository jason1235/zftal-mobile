<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
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
        	var type=$("#type").val();
			$("#btn_zj").click(function(){//功能条增加按钮
				showWindow("增加","<%=request.getContextPath()%>/post/deptPost_input.html","720","320");
			});
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("td[name='guid']").text();
				queryEntity(id);
			});
			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/post/deptPost_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
				});
			operationList();//初始化操作栏目
			fillRows($("#pageSize").val(), "", "", false);//填充空行
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
			$.post('<%=request.getContextPath()%>/post/deptPost_remove.html',"post.guid="+id,function(data){
				var callback = function(){
					$("button[name='search']").click();
				};
				processDataCall(data, callback);
				},"json");
		}
		function queryEntity(id){//查询
			showWindow("修改","<%=request.getContextPath()%>/post/deptPost_edit.html?post.guid="+id,"720","320");
		}

		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='guid']").text();
				queryEntity(id);
			});
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='guid']").text();
				preDel(id);
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
			<!-- 按钮 -->
			<!-- 过滤条件开始 -->
			<!-- 过滤条件结束 -->
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<form id="search" method="post" action="<%=request.getContextPath()%>/post/deptPost_list.html">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchtab">
    <table width="100%" border="0">
      <tfoot>
        <tr>
          <td colspan="6">
            <div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div></td>
        </tr>
      </tfoot>
      <tbody>
        <tr>
		  <th width="10%">部门</th>
          <td width="24%">
          	<ct:codePicker name="query.deptId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.deptId }" />
          </td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
        </tr>
      </tbody>
    </table>
  </div>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>部门岗位列表<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td>序号</td>
						<c:if test="${'guid' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="guid">岗位编号</td>
						</c:if>
						<c:if test="${'guid' != sortFieldName}">
						<td class="sort_title" id="guid">岗位编号</td>
						</c:if>
						<c:if test="${'GWMC' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="GWMC">岗位名称</td>
						</c:if>
						<c:if test="${'GWMC' != sortFieldName}">
						<td class="sort_title" id="GWMC">岗位名称</td>
						</c:if>
<%--						<td>所属部门</td>--%>
						<c:if test="${'gwlb' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="gwlb">岗位类别</td>
						</c:if>
						<c:if test="${'gwlb' != sortFieldName}">
						<td class="sort_title" id="gwlb">岗位类别</td>
						</c:if>
						<c:if test="${'GWDJ' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="GWDJ">岗位等级</td>
						</c:if>
						<c:if test="${'GWDJ' != sortFieldName}">
						<td class="sort_title" id="GWDJ">岗位等级</td>
						</c:if>
						<c:if test="${'sjgwbh' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="sjgwbh">上级岗位</td>
						</c:if>
						<c:if test="${'sjgwbh' != sortFieldName}">
						<td class="sort_title" id="sjgwbh">上级岗位</td>
						</c:if>
						<c:if test="${'jhbzs' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="jhbzs">计划编制人数</td>
						</c:if>
						<c:if test="${'jhbzs' != sortFieldName}">
						<td class="sort_title" id="jhbzs">计划编制人数</td>
						</c:if>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${pageList}" var="post" varStatus="st">
						<tr name="tr">
						<%--<th><input type="checkbox" name="guid"/></th>--%>
						<td>${st.count }</td>
						<td name="guid">${post.guid }</td>
						<td>${post.postValue }</td>
<%--						<td>${post.deptValue }</td>--%>
						<td>${post.postTypeValue }</td>
						<td>${post.levelValue }</td>
						<td>${post.superiorValue }</td>
						<td>${post.planNumber }</td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">修改</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="modify" href="#" class="first1">修改</a></li>
					                <li><a name="del" href="#" class="last1">删除</a></li>
					            </ul>
					        </div>
					      </div>
						</td>
					</tr>
					</c:forEach>
				</tbody>
  	</table>
  	</div>
  <ct:page pageList="${pageList }" />
</div>
</form>
  </body>
</html>
