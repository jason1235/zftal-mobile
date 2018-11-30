<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib uri="/WEB-INF/infoclasstag.tld" prefix="clazz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	
	<script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript" src="<%=stylePath %>/js/lockTableTitle.js"></script>
	
	<script type="text/javascript">
		window.returnValue = null;

		$(function(){
			//监听【查询】按钮点击事件
			$("#btn_cx").click( function() {
				$("form").submit();
			});

			//监听【取消】按钮点击事件
			$("#btn_qx").click( function() {
				window.close();
			});
			
			//监听双击行
			$("tbody > tr").dblclick( function() {
				var guid = $(this).find("input[name='guid']").val();

				if( guid == null ) {
					return;
				}

				try {
					window.returnValue = [guid, $(this).find("input[name='name']").val()];
					if(window.opener){
                      window.opener.returnValue = [guid, $(this).find("input[name='name']").val()];
                    }
					window.close();
				} catch(e) {
					alert('select person error !');
				}
			});
			
			FixTable("MyTable", 0, 840, 300); 
		});
	</script>
</head>
  
<body>
	<form action="<%=request.getContextPath() %>/tools/selectPerson_single.html?type=${type}" method="post">
		<div class="searchtab">
			<table width="100%">
				<tfoot>
					<tr>
						<td colspan="6">
							<div class="bz">
								<label>
								</label>
							</div>
							<div class="btn">
								<button id="btn_cx" class="button" type="button" name="查询">查 询</button>
								<button id="btn_qx" class="button" type="button" name="取消">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				
				<tbody>
					<tr>
						<c:forEach items="${query.config.conditions}" var="condition" varStatus="i">
						<th>${condition.title }</th>
						<td><clazz:search type="${query.config.type.name}" condition="${condition}" value="${query.values[condition.name]}" /></td>
						<c:if test="${( i.index + 1 ) % 3 == 0 }">
					</tr>
					<tr>
						</c:if>
						</c:forEach>
						<c:forEach begin="0" end="${3-(fn:length(query.config.conditions))/3 }" step="1">
						<th>&nbsp;</th>
						<td>&nbsp;</td>
						</c:forEach>
					</tr>
					
				</tbody>
			</table>
		</div>
	
	<div id="lockTable">
		<h3 class="datetitle_01">
			<span>人员信息<font color="red" style="font-weight:normal;">（提示：双击一行可以选定并返回）</font></span>
		</h3>
		<table id="MyTable" cellpadding="0" cellspacing="0" class="dateline nowrap" style="width: 823px;">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<c:forEach items="${query.config.propertyInfos}" var="property">
					<td>${property.name }</td>
					</c:forEach>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${beans}" var="bean" varStatus="i">
				<tr>
					<td>
						${i.index+1 }
						<input type="hidden" name="guid" value="${bean.viewHtml[query.config.type.primaryFileName] }" />
						<input type="hidden" name="name" value="${bean.viewHtml['xm']}" />
					</td>
					<c:forEach items="${query.config.properties}" var="pName">
					<td>${bean.viewHtml[pName] }</td>
					</c:forEach>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<ct:page pageList="${beans }" />
	</div>
	</form>
	<script type="text/javascript">
		fillRows(10, '', '', false);
	</script>
</body>
</html>
