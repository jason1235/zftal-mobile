<%@ page language="java"
	import="java.util.*,com.zfsoft.hrm.config.ICodeConstants"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini"%>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
		<script type="text/javascript">
    $(function(){
        $("button[name='search']").click(function(){
       		$("#search").attr("action","<%=request.getContextPath()%>/expertvote/expertgroup_select.html");
			$("#search").attr("method","post");
			$("#search").submit();
			e.preventDefault();//阻止默认的按钮事件，防止多次请求	
        });
        
        $("button[name='add']").click(function(){
			if($("input[id='id']:checked").length==0){
				alert("请选择要添加的记录！");
				return false;
			}
			var param=$("#search input[name='groupMember.zjz_id'],#search input[name='ids']").serialize();
			$.post('<%=request.getContextPath()%>/expertvote/config/expertgroup_addExpert.html',
											param, function(data) {
												var callback = function() {
													parent.refreshList();
													parent.ymPrompt.close();
												};
												if (data.success) {
													processDataCall(data,
															callback);
												} else {
													showWarning(data.text);
												}
											}, "json");
							return false;
						});

		$("tbody > tr[name^='tr']").click(function() { //监听单击行
			var current = $(this).attr("class");
			if (current != null && current != '') {
				$(this).removeClass("current");
				$(this).find("input[name='ids']").removeAttr("checked");
			} else {
				$(this).attr("class", "current");
				$(this).find("input[name='ids']").attr("checked", "checked");
			}
		});

		fillRows("10", "", "", false);//填充空行
	});

	function selectAllOrCancel(obj, name) {//全选选择框操作
		var checks = document.getElementsByName(name);
		var body = document.getElementById("list_body");
		var tr = body.getElementsByTagName("tr");
		if (obj.checked) {
			for ( var i = 0; i < checks.length; i++) {
				tr[i].className = 'current';
				checks[i].checked = true;
			}
		} else {
			for ( var i = 0; i < checks.length; i++) {
				tr[i].className = '';
				checks[i].checked = false;
			}
		}
	}
	/*
	 *排序回调函数
	 */
	function callBackForSort(sortFieldName, asc) {
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
			</div>
		</div>
		<form
			action="<%=request.getContextPath()%>/expertvote/expertgroup_select.html"
			name="search" id="search" method="post">
			<input type="hidden" id="sortFieldName" name="sortFieldName"
				value="${sortFieldName}" />
			<input type="hidden" id="asc" name="asc" value="${asc}" />
			<input type="hidden" name="groupMember.zjz_id" value="${zjz_id}" />
			<div class="searchtab">
				<table width="100%" border="0">
					<tbody>
						<tr>
							<th width="10%">
								工号
							</th>
							<td>
								<input name="expertInfo.gh" value="${expertInfo.gh }"
									type="text" />
							</td>
							<th width="10%">
								姓名
							</th>
							<td>
								<input name="expertInfo.xm" value="${expertInfo.xm }"
									type="text" />
							</td>
							<th width="10%">
								类型
							</th>
							<td width="20%">
								<ct:codePicker name="expertInfo.type"
									catalog="DM_DEF_ZJLX"
									code="${expertInfo.type }" />
							</td>
						</tr>
						<tr>
							<td colspan="6" align="right">
								<button class="btn_cx" name="search" type="button">
									查 询
								</button>
								<button class="btn_tj" name="add" type="button">
									添 加
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="formbox">
				<!--标题start-->
				<h3 class="datetitle_01">
					<span>专家库管理</span>
				</h3>
				<!--标题end-->
				<div class="con_overlfow">
					<table width="100%" class="dateline tablenowrap" id="tiptab">
						<thead id="list_head">
							<tr>
								<td width="5%">
									<input type="checkbox" onclick="selectAllOrCancel(this,'ids');" />
								</td>
								<td width="5%">
									序号
								</td>
								<td class="sort_titlem_m" id="zjxm" width="20%">
									专家姓名
								</td>
								<td class="sort_titlem_m" id="sszy" width="20%">
									专家类型
								</td>
								<td class="sort_titlem_m" id="sbz" width="20%">
									上报者
								</td>
								<td class="sort_titlem_m" id="rksj" width="20%">
									入库时间
								</td>
							</tr>
						</thead>
						<tbody id="list_body">
							<s:iterator value="expertList" var="p" status="st">
								<tr name="tr">
									<td>
										<input type="checkbox" name="ids" id="id" value="${p.id }" />
									</td>
									<td>
										${st.index + 1 }
									</td>
									<td>
										<ct:PersonParse code="${p.gh }" />
									</td>
									<td>
										<ct:codeParse code="${p.type }"
											catalog="DM_DEF_ZJLX" />
									</td>
									<td>
										<ct:PersonParse code="${p.sbz }" />
									</td>
									<td>
										<s:date name="dedate" format="yyyy-MM-dd" />
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<ct:page pageList="${expertList }" query="${expertInfo }"
					queryName="expertInfo" />
			</div>
		</form>
	</body>
</html>