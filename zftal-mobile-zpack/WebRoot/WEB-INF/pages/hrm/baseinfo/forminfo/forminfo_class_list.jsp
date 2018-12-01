<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>

	<script type="text/javascript">
		$(function(){
			$("span[name='modify']").click(function(){
				var guid=$(this).closest("div").attr("id");
				location.href="<%=request.getContextPath() %>/baseinfo/forminfometadata_prop_list.html?typeId=${formInfoClass.formInfoTypeId}&classId="+guid;
			});
			
		});
	</script>
</head>

<body>
	<div class="formbox">
		<table class="dateline" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<td>信息类名称</td>
					<td>已选字段</td>
					<td style="width:100px">操作</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${formInfoClass.infoClazzes }" var="bean" varStatus="i">
				<tr>
					<td>${i.index+1 }</td>
					<td>${bean.name }</td>
					<td>${bean.propString }</td>
					<td>
						 <div>
					      	<div class="current_item" id="${bean.guid}">
					        	<span name="modify" class="item_text">条目维护</span>
					        </div>
					      </div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div id="testID" style="display:none">
		<div class="open_prompt">
			<table width="100%" border="0" class="table01">
				<tr>
					<td width="109"><div class="img img_why01"></div></td>
					<th><p>确定要删除吗？</p></th>
				</tr>
				<tr>
					<td colspan="2" align="center" class="btn01">
						<input type="button" id="" class="button" value="确 定" />
						<input type="button" id="confirmcancel" class="button" value="取 消"  />
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<script type="text/javascript">
	</script>
</body>

</html>