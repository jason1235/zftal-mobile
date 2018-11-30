<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
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

			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function queryEntity(id){//查询
			showWindowV2("查看", "<%=request.getContextPath()%>/message/noticeView_info.html?notice.guid="+id, 720, 400 );
		}
		
		function operationList(){

			$("a[name='info']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity(id);
			});
			
			$(".select_tools a").css("cursor","pointer");

			operationHover();
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
 <form action="<%=request.getContextPath()%>/message/noticeView_page.html" name="search" id="search" method="post">
 	 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
	 <input type="hidden" id="asc" name="asc" value="${asc}"/>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>最新通知列表<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以查看信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<c:if test="${'BT' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="BT"  width="70%">标题</td>
						</c:if>
						<c:if test="${'BT' != sortFieldName}">
						<td class="sort_title" id="BT" width="70%">标题</td>
						</c:if>
						<c:if test="${'FBSJ' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="FBSJ"  width="15%">发布时间</td>
						</c:if>
						<c:if test="${'FBSJ' != sortFieldName}">
						<td class="sort_title" id="FBSJ" width="15%">发布时间</td>
						</c:if>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="notice">
						<tr name="tr">
						<input type="hidden" id="guid" value="${notice.guid }"/>
						<td><c:if test="${notice.top==1 }"><font color='red'>【置顶】</font></c:if>${notice.title }</td>
						<td><s:date name="createTime" format="yyyy-MM-dd" /></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">查看</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="info" href="#" class="tools_list">查看</a></li>
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
