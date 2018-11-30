<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
	    	$("#btn_zj").click(function(){
	    		showWindow("写消息","<%=request.getContextPath()%>/message/messageSend_input.html","480","250");
			});
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
				$("#search").attr("action","<%=request.getContextPath()%>/message/messageSend_page.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});

			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function queryEntity(id){//查询
			showWindow("查看","<%=request.getContextPath()%>/message/messageSend_info.html?msg.guid="+id,"480","250");
		}
		
		function deleteEntity(id) {
			$.post(_path+'/message/messageSend_delete.html',"msg.guid="+id,function(data){
				var callback=function(){
					location.href = _path+"/message/messageSend_page.html";
					//$("#search").submit();						
				}
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
				deleteEntity(id);
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
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">写消息</a>
						</li>
					</ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form action="<%=request.getContextPath()%>/message/messageSend_page.html" name="search" id="search" method="post">
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
    	<span>已发送消息列表<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以查看信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<c:if test="${'XXBT' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="XXBT">消息标题</td>
						</c:if>
						<c:if test="${'XXBT' != sortFieldName}">
						<td class="sort_title" id="XXBT">消息标题</td>
						</c:if>
						<td>接收人类型</td>
						<c:if test="${'JSR' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="JSR">接收人</td>
						</c:if>
						<c:if test="${'JSR' != sortFieldName}">
						<td class="sort_title" id="JSR">接收人</td>
						</c:if>
						<c:if test="${'FSSJ' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="FSSJ">发送时间</td>
						</c:if>
						<c:if test="${'FSSJ' != sortFieldName}">
						<td class="sort_title" id="FSSJ">发送时间</td>
						</c:if>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="msg">
						<tr name="tr">
						<td title="${msg.title }">${fn:substring(msg.title,0,22) }${fn:length(msg.title)>22?"...":"" }</td>
						<td>${msg.receiverTypeMc }</td>
						<c:if test="${msg.receiverType eq '1' }">
						<td>${msg.roleMc }</td>
						</c:if>
						<c:if test="${msg.receiverType != '1' }">
						<td title="<ct:PersonParse code="${msg.receiver }"/>"><ct:PersonParse code="${msg.threeReceivers }"/>${fn:length(fn:split(msg.receiver,";"))>4?"...":"" }</td>
						</c:if>
						<td><s:date name="sendTime" format="yyyy-MM-dd"></s:date><input type="hidden" id="guid" value="${msg.guid }"/></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">查看</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="info" href="#" class="tools_list">查看</a></li>
					            	<li><a name="delete" href="#" class="tools_list">删除</a></li>
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
