<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
        <link rel="stylesheet" href="<%=request.getContextPath() %>/kindeditor/themes/default/default.css" />
        <link rel="stylesheet" href="<%=request.getContextPath() %>/kindeditor/plugins/code/prettify.css" />
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath() %>/kindeditor/kindeditor.js"></script>
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath() %>/kindeditor/lang/zh_CN.js"></script>
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath() %>/kindeditor/plugins/code/prettify.js"></script>
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath() %>/kindeditor/kindeditor.js"></script>
    <script type="text/javascript">
    $(function(){
			$("#btn_zj").click(function(){//功能条增加按钮
				showWindow("增加", "<%=request.getContextPath()%>/message/notice_input.html", 660, 470 );
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

			$("a[name='allotObj']").click(function(){
                var id = $(this).closest("tr").find("input[id='guid']").val();
                showWindow("设置对象", "<%=request.getContextPath()%>/message/allot_page.html?messageAllot.type=NOTICE&messageAllot.mainId="+id, 500, 240 );
            });

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/message/notice_page.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});

			initSelect("query.top","${query.top}");
			initSelect("query.status","${query.status}");
			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function queryEntity(id){//查询
			showWindow("修改", "<%=request.getContextPath()%>/message/notice_edit.html?notice.guid="+id, 660, 470 );
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
		
		function delEntity(id){//查询
			$.post('<%=request.getContextPath()%>/message/notice_delete.html',"notice.guid="+id,function(data){
				var callback = function(){
					$("button[name='search']").click();
				};
				processDataCall(data, callback);
			},"json");
		}

		function operationList(){

			$("a[name='edit']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity(id);
			});
			
			$("a[name='manager']").click(function(){
				//var id = $(this).closest("tr").find("input[id='guid']").val();
				managerNotice(this);
			});

			$("a[name='del']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				preDel(id);
			});

			initManagerView();
			$(".select_tools a").css("cursor","pointer");

			operationHover();
		}
		
		function initManagerView(){
			$("a[name='manager']").each(function(){
				var status = $(this).closest("tr").find("#status").val();
				if(status == "0"){
					$(this).text("发布");
				}else{
					$(this).text("取消");
				}
			});
		}
		
		function managerNotice(a){
			var status = $(a).closest("tr").find("#status").val();
			var id = $(a).closest("tr").find("input[id='guid']").val();
			var param = "notice.guid="+id+"&notice.status="+0;
			if(status == "0"){
				param = "notice.guid="+id+"&notice.status="+1;
			}
			$.post('<%=request.getContextPath()%>/message/notice_updateStatus.html',param,function(data){
				var callback = function(){
					$("button[name='search']").click();
				};
				processDataCall(data, callback);
			},"json");
		}

		function tipsRefresh(msg,suc){
			var callback = function(){
				$("#search").submit();
			};
			if(suc == 'true'){
				tipsWindown("提示信息","text:"+msg,"340","120","true","1000","true","id",callback);
			}else{
				tipsWindown("提示信息","text:"+msg,"340","120","true","","true","id");
			}
			
			$("#window-sure").click(function() {
				divClose();
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
 <form action="<%=request.getContextPath()%>/message/notice_page.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">置顶状态</th>
		  <td width="20%">
          	<select name="query.top" style="width:180px">
          		<option value="" selected>全部</option>
          		<option value="0">未置顶</option>
          		<option value="1">已置顶</option>
          	</select>
		  </td>
		  <th width="10%">发布状态</th>
		  <td width="20%">
          	<select name="query.status" style="width:180px">
          		<option value="" selected>全部</option>
          		<option value="0">未发布</option>
          		<option value="1">已发布</option>
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
    	<span>最新通知管理<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以修改信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<c:if test="${'BT' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="BT"  width="40%">标题</td>
						</c:if>
						<c:if test="${'BT' != sortFieldName}">
						<td class="sort_title" id="BT" width="40%">标题</td>
						</c:if>
						<c:if test="${'FBR' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="FBR"  width="15%">发布人</td>
						</c:if>
						<c:if test="${'FBR' != sortFieldName}">
						<td class="sort_title" id="FBR" width="15%">发布人</td>
						</c:if>
						<c:if test="${'FBSJ' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="FBSJ"  width="15%">发布时间</td>
						</c:if>
						<c:if test="${'FBSJ' != sortFieldName}">
						<td class="sort_title" id="FBSJ" width="15%">发布时间</td>
						</c:if>
						<c:if test="${'ZDZT' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="ZDZT"  width="8%">置顶</td>
						</c:if>
						<c:if test="${'ZDZT' != sortFieldName}">
						<td class="sort_title" id="ZDZT" width="8%">置顶</td>
						</c:if>
						<c:if test="${'FBZT' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="FBZT"  width="8%">发布</td>
						</c:if>
						<c:if test="${'FBZT' != sortFieldName}">
						<td class="sort_title" id="FBZT" width="8%">发布</td>
						</c:if>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="notice">
						<tr name="tr">
						<input type="hidden" id="guid" value="${notice.guid }"/>
						<input type="hidden" id="status" value="${notice.status }"/>
						<td>${notice.title }</td>
						<td><ct:PersonParse code="${notice.author }"/></td>
						<td><s:date name="createTime" format="yyyy-MM-dd" /></td>
						<td><c:if test="${notice.top == 0 }" >否</c:if><c:if test="${notice.top == 1 }">是</c:if></td>
						<td><c:if test="${notice.status == 0 }" >否</c:if><c:if test="${notice.status == 1 }">是</c:if></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">修改</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="edit" href="#" class="first1">修改</a></li>
					                <li><a name="allotObj" href="#" class="tools_list">设置</a></li>
					                <li><a name="manager" href="#" class="tools_list">发布</a></li>
					                <li><a name="del" href="#" class="last1">删除</a></li>
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
  	 <iframe name="frame" src="about:blank" style="display:none"></iframe>
  </body>
</html>
