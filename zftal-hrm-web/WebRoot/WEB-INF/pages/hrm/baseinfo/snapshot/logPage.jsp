<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
   	<%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/baseinfo/snapshot/progress.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript">
		$(function(){

			var current = null;

			// 行单击选定事件
			$("#list_body tr").click( function(){
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});

			// 行双击事件
			$("#list_body tr").dblclick( function(){
				var id = $(this).find("td[name='snapTime'] span").text();
				viewShotDetail( id );
			});

			// 功能条增加按钮
			$("#auto").click(function(){
				tipsWindown("维护","url:post?<%=request.getContextPath()%>/baseinfo/snapshot_setup.html","480","180","true","","true","id");
			});

			// 功能条返回按钮
			$("#handle").click(function(){
				tipsWindown("维护","url:post?<%=request.getContextPath()%>/baseinfo/snapshot_handle.html","480","160","true","","true","id");
			});

			operationList();//操作栏初始化

			fillRows("20", "", "", false);//填充空行数据
		});

		// 删除前确认
		function preDel(id){
			showConfirm("确定要删除吗？");
			$("#why_cancel").click(function(){
				divClose();
			});
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}

		// 删除
		function delEntity(id){
			$.post('<%=request.getContextPath()%>/baseinfo/snapshot_delete.html',"snapTime="+id,function(data){
				var callback = function(){
					window.location.reload();
				};
				processDataCall(data, callback);
			}, "json");
		}

		// 查看详细
		function viewShotDetail( id ) {
			location.href = "<%=request.getContextPath()%>/baseinfo/snapshot_detailPage.html?snapTime="+id;
		}

		// 操作列表
		function operationList() {
			
			// 行数据修改链接
			$("a[name='view']").click( function(){
				var id = $(this).closest("tr").find("td[name='snapTime'] span").text();
				viewShotDetail( id );
			});

			// 行数据修改链接
			$("a[name='del']").click(function(){
				var id = $(this).closest("tr").find("td[name='snapTime'] span").text();
				preDel(id);
			});
			
			$(".select_tools a").css("cursor","pointer");

			operationHover();
		}

		function initSelect(name,value){
			$("select[name='"+name+"'] > option").each(function(){
				if($(this).val()==value){
					$(this).attr("selected","selected");
				}
			});
		}
		
		function initRadio(name,value){
			$("input[type='radio'][name='"+name+"']").each(function(){
				if($(this).val()==value){
					$(this).attr("checked","checked");
				}
			});
		}
	</script>
</head>

<body>
	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li><a id="auto" class="btn_cs">定 时</a></li>
				<li><a id="handle" class="btn_sz" onclick="">手 动</a></li>
			</ul>
		</div>
	</div>
	
	<form id="page" name="page" action="snapshotLog_page.html" method="post">
		<input type="hidden" name="snapTime" value="" />
		<div class="formbox">
			<h3 class="datetitle_01">
				<span>快照列表<font color="#0457A7" style="font-weight:normal;">（提示：双击行可以查看明细）</font></span>
			</h3>
			<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
						<td>快照时间</td>
						<td>操作人</td>
						<td>记录时间</td>
						<td width="80px">操　作</td>
					</tr>
				</thead>
				
				<tbody id="list_body">
					<s:iterator value="pageList" var="log">
					<tr>
						<td name="snapTime"><span><s:date name="snapTime" format="yyyy-MM" /></span></td>
						<td><span>${log.operator }</span></td>
						<td><span><s:date name="operateTime" format="yyyy-MM-dd" /></span></td>
						<td width="80px">
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
