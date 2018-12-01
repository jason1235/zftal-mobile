<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <style type="text/css">
      .autocut {
          overflow:hidden;
          white-space:normal;
          text-overflow:ellipsis;
          -o-text-overflow:ellipsis;
          -icab-text-overflow:ellipsis;
          -khtml-text-overflow:ellipsis;
          -moz-text-overflow:ellipsis;
          -webkit-text-overflow:ellipsis;
      }

      .autocut:hover {
          overflow:visible;
          white-space:normal;
          word-wrap:break-word;
      }
    </style>
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
				$("#search").attr("action","<%=request.getContextPath()%>/message/message_page.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});

			initSelect("query.status","${query.status}");
			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function queryEntity(id){//查询
			var callback = function(){
				$("button[name='search']").click();
			};
			//tipsWindown("今日提醒","url:post?<%=request.getContextPath()%>/message/message_info.html?msg.guid="+id,"480","250","true","","true","id",callback);
			showWindow("今日提醒", "<%=request.getContextPath()%>/message/message_info.html?msg.guid="+id, 480, 250,callback);
		}
		
		function deleteEntiry(id) {//删除
			$.post(_path+'/message/message_del.html','msg.guid='+id,function(data){
				var callback = function (){
					$("#search").submit();	
				};
				processDataCall(data,callback);
			},"json");
		}

		function operationList(){

			$("a[name='info']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity(id);
			});

			$("a[name='delete']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				deleteEntiry(id);
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
 <form action="<%=request.getContextPath()%>/message/message_page.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">标题名称</th>
          <td width="20%">
          	<input type="text" name="query.name" class="text_nor" style="width:180px" value="${query.name }"/>
		  </td>
		  <th width="10%">状  态</th>
		  <td width="20%">
          	<select name="query.status" style="width:180px">
          		<option value="">全部</option>
          		<option value="0">未读</option>
          		<option value="1">已读</option>
          	</select>
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
    	<span>今日提醒管理<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以查看信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" style="table-layout:fixed;"  id="tiptab">
				<thead id="list_head">
					<tr>
						<c:if test="${'XXBT' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="XXBT">消息标题</td>
						</c:if>
						<c:if test="${'XXBT' != sortFieldName}">
						<td class="sort_title" id="XXBT">消息标题</td>
						</c:if>
						<!-- 增加消息内容 20141104 start -->
						<c:if test="${'NR' eq sortFieldName}">
						<td width="300px" class="sort_title_current_${asc }" id="NR">消息内容</td>
						</c:if>
						<c:if test="${'NR' != sortFieldName}">
						<td width="300px" class="sort_title" id="NR">消息内容</td>
						</c:if>
						<!-- 增加消息内容 20141104 end -->
						<c:if test="${'FSR' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="FSR">发送人</td>
						</c:if>
						<c:if test="${'FSR' != sortFieldName}">
						<td class="sort_title" id="FSR">发送人</td>
						</c:if>
						<c:if test="${'FSSJ' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="FSSJ">发送时间</td>
						</c:if>
						<c:if test="${'FSSJ' != sortFieldName}">
						<td class="sort_title" id="FSSJ">发送时间</td>
						</c:if>
						<c:if test="${'YDSJ' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="YDSJ">阅读时间</td>
						</c:if>
						<c:if test="${'YDSJ' != sortFieldName}">
						<td class="sort_title" id="YDSJ">阅读时间</td>
						</c:if>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="msg">
						<tr name="tr">
						<td class="autocut">${msg.title }</td>
						<!-- 增加消息内容 20141104 start -->
						<td class="autocut"><ct:ContentExplain content="${msg.content }" /></td>
						<!-- 增加消息内容 20141104 end -->
						<td><ct:PersonParse code="${msg.sender }"/></td>
						<td><s:date name="sendTime" format="yyyy-MM-dd"></s:date><input type="hidden" id="guid" value="${msg.guid }"/></td>
						<td><s:date name="readTime" format="yyyy-MM-dd"></s:date></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">查看</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="info" href="#" class="tools_list">查看</a></li>
					                <%--<li><a name="delete" href="#" class="tools_list">删除</a></li>--%>
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
