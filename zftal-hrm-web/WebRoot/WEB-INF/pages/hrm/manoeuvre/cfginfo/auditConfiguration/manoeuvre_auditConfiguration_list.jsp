<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" defer="" src="/zftal-hrm/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		
	<script type="text/javascript">
		$(function(){
			$(".current_item").hover(function(){
				$(this).next("div.select_tools").show();
				$(this).parent().css("position","relative");
			},function(){
				$(this).next("div.select_tools").show().hide();
				$(this).parent().css("position","");
			})
			$(".select_tools").hover(function(){
				$(this).show();
				$(this).parent().css("position","relative");		
			},function(){
				$(this).hide();
				$(this).parent().css("position","");
			})
					
			$(".select_tools a").css("cursor","pointer");
			$("tbody > tr[name='tr']").dblclick(function(){
				var id = $(this).attr("id");
				modify(id);
			});
			$("#btn_zj").click(function(){
				add();
			}); 
			$("a[name='modify']").click(function(){
				var id = $(this).closest("tr").attr("id");
				modify(id);
			});
			$("a[name='modify']").each(function(){
				$(this).attr("title","双击数据行可以进入修改");
			});

			$("a[name='chooseOrg']").click(function(){
				var id = $(this).closest("tr").attr("id");
				chooseOrg(id);
			});
			
			$("a[name='del']").click(function(){
				var id = $(this).closest("tr").attr("id");
				remove(id);
			});
			$("button[name='search']").click(function(e){//搜索按钮
				$("#searchForm").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			$("#extensionTypeQuerySel").change(function(){
				var opt = $("#extensionTypeQuerySel")[0].options[$("#extensionTypeQuerySel")[0].selectedIndex].value;
				$("input[name='query.extensionType']").val(opt);
				
				
			});

			$(".select_tools a").css("width","73px");
			

			fillRows("20", "", "", false);//填充空行
		});

		function add(){
			showWindow("增加审核设置信息","<%=request.getContextPath()%>/manoeuvre/AuditConfiguration_edit.html?taskNode.nid=${taskNode.nid}", 510, 220);
		}

		function modify(id){
			showWindow("修改审核设置信息","<%=request.getContextPath()%>/manoeuvre/AuditConfiguration_edit.html?taskNode.nid=${taskNode.nid}&aid="+id, 510, 220);
		}

		function chooseOrg(id){
			showWindow("选择部门","<%=request.getContextPath()%>/manoeuvre/AuditConfigOrgInfo_load.html?query.aid="+id, 420, 260);
		}

		function remove(id){
			showConfirm("确定要删除吗？");
			
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}

		function delEntity(id){
			$.post('<%=request.getContextPath()%>/manoeuvre/AuditConfiguration_delete.html',{aid:id},function(data){
				var callback = function(){
					$("#searchForm").submit();
				};
				processDataCall(data, callback);
			},"json");
		}

		function goBack(){
			window.location.href = "<%=request.getContextPath()%>/manoeuvre/TaskNode_list.html";
		}
		
	</script>
	
  </head>
  
  <body>
  	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li>
					<a id="btn_zj" class="btn_zj">增 加</a>
				</li>
			</ul>
			<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="goBack();return false;">返 回</a>
		</div>	
	</div>
    <form action="<%=request.getContextPath()%>/manoeuvre/AuditConfiguration_list.html" name="searchForm" id="searchForm" method="post">
    	<input type="hidden" name="query.taskNode.nid" value="${query.taskNode.nid }"/>
    	<div class="searchtab">
    		<table width="100%" border="0">
    			<tfoot>
    				<tr>
			          <td colspan="4">
			            <div class="btn">
			              <button type="button" class="btn_cx" name="search" >查 询</button>
			            </div>
			           </td>
			        </tr>
    			</tfoot>
    			<tbody>
    				<tr>
    					<th width="15%">审核人姓名</th>
			            <td >
			            	<input name="query.assessorName" id="assessorName" value="${query.assessorName}" />
			            </td>
					    <th width="15%">审核类型</th>
			            <td >
			            	<select id="extensionTypeQuerySel" name="extensionTypeQuerySel">
			            		<option value="">------ 清空 ------</option>
			            		<option value="0" <c:if test="${query.extensionType == '0'}"> selected="selected" </c:if> > 调入审核 </option>
			            		<option value="1" <c:if test="${query.extensionType == '1'}"> selected="selected" </c:if> > 调出审核 </option>
			            		<option value="2" <c:if test="${query.extensionType == '2'}"> selected="selected" </c:if> > 全部审核 </option>
			            	</select>	
					  		<input type="hidden" name="query.extensionType" value="${query.extensionType }" />
					    </td>
    				</tr>
    			</tbody>
    		</table>
    	</div>
    	<div class="formbox">
    	<!--标题start-->
		    <h3 class="datetitle_01">
		    	<span>${taskNode.nodeName }&nbsp;&nbsp;审核设置信息<font color="#0457A7" style="font-weight:normal;"></font></span>
		    </h3>
		<!--标题end-->	
			<table width="100%" class="dateline" id="tab">
				<thead id="list_head">
					<tr>
						<td>序号</td>
						<td>审核人</td>
						<td>审核人部门</td>
						<td>审核类型</td>
						<td>可审核部门</td>
						<td>备注</td>
						<td width="120px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${page}" var="bean" varStatus="sta">
						<tr name="tr" id="${bean.aid }">
							<td>${sta.index + 1 }</td>
							<td>${bean.userName }</td>
							<td>${bean.assessorInfo.viewHtml['dwm'] }</td>
							<td>${bean.extensionTypeText }</td>
							<td>${bean.auditConfigOrgListText }</td>
							<td>${bean.remark }</td>
							<td>
								<div>
							      	<div class="current_item">
							        	<span class="item_text">修改</span>
							        </div>
							        <div class="select_tools" id="select_tools1" style=" width:100px; display:none">
							            <ul>
							                <li><a name="chooseOrg" class="last1">设定可审部门</a></li>
							                <!-- 
							                <li><a name="modify" class="first1">修改</a></li>
							                 -->
							                <li><a name="del" class="last1">删除</a></li>
							            </ul>
							        </div>
							    </div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<ct:page pageList="${page }" />
    	</div>
    </form>
  </body>
</html>
