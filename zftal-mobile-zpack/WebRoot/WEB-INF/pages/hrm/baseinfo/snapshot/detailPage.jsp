<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
	
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
			
			$("#back").click(function(){
				location.href = _path + "/baseinfo/snapshotLog_page.html";
			});

			// 填充空行数据
			fillRows("20", "", "", false);
		});
    </script>
</head>

<body>
	<div class="toolbox">
		<div class="buttonbox">
			<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
		</div>
	</div>
	
	<form id="page" name="page" action="baseinfo/snapshot_detailPage.html" method="post">
		<input type="hidden" name="snapTime" value="${snapTime}" />
		<div class="formbox">
			<h3 class="datetitle_01">
				<span>${snapTime}快照明细列表<font color="#0457A7" style="font-weight:normal;"></font></span>
			</h3>
			<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
						<td>快照时间</td>
						<td>操作表</td>
						<td>数据量</td>
						<td>记录时间</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<s:iterator value="pageList" var="log">
					<tr>
						<td name="snapTime"><span><s:date name="snapTime" format="yyyy-MM" /></span></td>
						<td><span>${log.clazz.name }</span></td>
						<td><span>${log.tableSize }</span></td>
						<td><span><s:date name="operateTime" format="yyyy-MM-dd" /></span></td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
			<ct:page pageList="${pageList }" />
		</div>
	</form>
</body>
</html>
