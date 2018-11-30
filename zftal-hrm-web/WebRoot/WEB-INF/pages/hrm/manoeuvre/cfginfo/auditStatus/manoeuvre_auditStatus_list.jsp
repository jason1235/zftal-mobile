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
				showAuditStatusDtl(id);
			});
			
			$("a[name='show']").click(function(){
				var id = $(this).closest("tr").attr("id");
				showAuditStatusDtl(id);
			});
			$("a[name='show']").each(function(){
				$(this).attr("title","双击数据行可以查看详情");
			});

			$(".select_tools a").css("width","73px");
			
			fillRows("10", "list_head1", "list_body1", false);//填充空行
		});

		function showAuditStatusDtl(id){
			showWindow("查看详细","<%=request.getContextPath()%>/manoeuvre/AuditStatus_show.html?sid="+id+"&query.manoeuvreInfo.guid=${query.manoeuvreInfo.guid}&query.toPage=${query.toPage}", 700, 350);
		}

		function showWinPagin(perPageSize, toPage){
			showWindow("审核记录","<%=request.getContextPath()%>/manoeuvre/AuditStatus_list.html?query.manoeuvreInfo.guid=${query.manoeuvreInfo.guid }&query.perPageSize="+perPageSize+"&query.toPage="+toPage, 720, 350);
		}
		
		function goBack(){
			searchForm.submit();
		}
		
	</script>
	
  </head>
  
  <body>
  	<div class="toolbox">
		<div class="buttonbox">
			<a class="btn_fh_rs" style="cursor: pointer" onclick="history.go(-1);return false;">返 回</a>
		</div>	
	</div>
	
    <form action="<%=request.getContextPath()%>/manoeuvre/AuditStatus_list.html" name="searchForm" id="searchForm" method="post">
    	<input type="hidden" name="query.manoeuvreInfo.guid" value="${query.manoeuvreInfo.guid }"/>
    	
    	<div class="formbox">
    	<!--标题start-->
		    <h3 class="datetitle_01">
		    	<span>审核历史信息<font color="#0457A7" style="font-weight:normal;"></font></span>
		    </h3>
		<!--标题end-->	
			<table width="100%" class="dateline" id="tab">
				<thead id="list_head1" name="list_head1">
					<tr>
						<td>序号</td>
						<td>审核时间</td>
						<td>审核环节</td>
						<td>审核结果</td>
						<td>审核人</td>
						<td width="120px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body1" name="list_body1">
					<c:forEach items="${page}" var="bean" varStatus="sta">
						<tr name="tr" id="${bean.sid }">
							<td>${sta.index + 1 }</td>
							<td>${bean.auditTimeText }</td>
							<td>${bean.taskNodeName }</td>
							<td>${bean.resultText }</td>
							<td>${bean.personInfo.viewHtml['xm'] }</td>
							<td>
								<div>
							      	<div class="current_item">
							        	<span class="item_text">查看详细</span>
							        </div>
							        <div class="select_tools" id="select_tools1" style=" width:100px; display:none">
							            <ul>
							                <li><a name="show" class="first1">查看详细</a></li>
							            </ul>
							        </div>
							    </div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<%@ include file="/WEB-INF/pages/hrm/manoeuvre/pagination/page.jsp" %>
    	</div>
    </form>
  </body>
</html>
