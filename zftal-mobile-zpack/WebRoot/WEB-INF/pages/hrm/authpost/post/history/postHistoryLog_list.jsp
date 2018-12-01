<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript">
    	$(function(){
			$("#auto").click(function(){//功能条增加按钮
				showWindow("维护","<%=request.getContextPath()%>/post/postHistory_setup.html","480","180");
			});
			$("#handle").click(function(){//功能条返回按钮
				showWindow("维护","<%=request.getContextPath()%>/post/postHistory_handle.html","480","160");
			});
			operationList();//操作栏初始化
			fillRows($("#pageSize").val(), "", "", false);//填充空行数据
		});

		function preDel(id){//删除前确认
			showConfirm("确定要删除吗？");
			$("#why_cancel").click(function(){
				divClose();
			});
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}
		
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/post/postHistory_delete.html',"snapTime="+id,function(data){
					var callback = function(){
						$("#page").submit();
					};
					processDataCall(data, callback);
				},"json");
		}

		function operationList(){
			$("a[name='view']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='snapTime'] span").text();
				location.href = "<%=request.getContextPath()%>/post/postHistory_list.html?snapTime="+id;
			});
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='snapTime'] span").text();
				preDel(id);
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
			operationPermission();
		}

		function operationPermission(){//操作链接开放判断
			$("a[name='del']").each(function(){
				var tr = $(this).closest("tr");
			});
			$("a[name='view']").each(function(){
				$(this).attr("title","双击数据行可以进入修改");
			});
		}
		
		/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#page").submit();
		}
    </script>
  </head>
  <body>
  <!-- From内容 start-->
  
		<div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="auto" class="btn_cs">定时</a>
						</li>
						<li>
							<a id="handle" class="btn_sz" onclick="">手动</a>
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
<form id="page" name="page" method="post" action="<%=request.getContextPath()%>/post/postHistoryLog_list.html">
<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="formbox">
  <table width="100%" class="dateline" id="tiptab">
    <thead id="list_head">
      <tr>
        <c:if test="${'snap_time' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="snap_time">快照时间</td>
		</c:if>
		<c:if test="${'snap_time' != sortFieldName}">
			<td class="sort_title" id="snap_time">快照时间</td>
		</c:if>
        <c:if test="${'operator' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="operator">操作人</td>
		</c:if>
		<c:if test="${'operator' != sortFieldName}">
			<td class="sort_title" id="operator">操作人</td>
		</c:if>
        <c:if test="${'operate_time' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="operate_time"> 记录时间</td>
		</c:if>
		<c:if test="${'operate_time' != sortFieldName}">
			<td class="sort_title" id="operate_time">记录时间</td>
		</c:if>
        <td> 操　作</td>
      </tr>
    </thead>
    <tbody id="list_body">
      <s:iterator value="pageList" var="log">
      <tr>
        <td name="snapTime"><span><s:date name="snapTime" format="yyyy-MM" /></span></td>
        <td><span>${log.operator }</span></td>
        <td><span><s:date name="operateTime" format="yyyy-MM-dd" /></span></td>
        <td>
		  <div>
	      	<div class="current_item">
	        	<span class="item_text">查看明细</span>
	        </div>
	        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
	            <ul>
	                <li><a name="view" href="#" class="first1">查看明细</a></li>
	                <li><a name="del" href="#" class="last1">删除</a></li>
	            </ul>
	        </div>
	      </div>
		</td>
      </tr>
      </s:iterator>
    </tbody>
  </table>
  <ct:page pageList="${pageList }" />
</div>
  </form>

  </body>
</html>
