<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/commons/hrm/head.ini" %>
<script type="text/javascript">
$(function(){
	$("#back").click(function(){
		window.location.href = _path + "/baseinfo/auditProcess_viewPage.html";
	});
	preview();
	fillRows("5", "list_head2", "list_body2", false);//填充空行
});

function preview(){//审核信息预览
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/baseinfo/auditProcess_detail.html',"process.guid=${process.guid}",function(data){
		if(data.success){
			$("#preview").append(data.result);
			$("span[name='changed']").each(function(){
				tooltips(this);
			});
			$("span[name='tips']").each(function(){
				tooltips(this);
			});
			tooltips($("#picChanged"));
		}else{
			$("#preview").append("加载审核信息失败");
		}
	},"json");
}
</script>
</head>
<body>
<div id="testID" >
  <div class="toolbox">
		<!-- 按钮 -->
			<div class="buttonbox">
				<a id="back" class="btn_fh_rs" style="cursor: pointer">返 回</a>
			</div>
	  <p class="toolbox_fot">
			<em></em>
		</p>
	</div>
  <div id="preview">
  </div>
  <div class="tab">
	<table align="center" class="formlist">
		<thead id="list_head2">
			<tr>
				<th width="20%">角色</th>
				<th width="20%">操作人</th>
				<th width="15%">状态</th>
				<th>意见</th>
			</tr>
		</thead>
		<tbody id="list_body2">
			<c:forEach items="${infoList }" var="info" varStatus="vs">
			<tr>
			   <td><ct:RoleParse code="${info.roleId }"/></td>
			   <td><ct:PersonParse code="${info.operator }" /></td>
			   <td>
			   		<c:if test="${info.status == -1}">重置</c:if>
			   		<c:if test="${info.status == 0}">拒绝</c:if>
			   		<c:if test="${info.status == 1}">同意</c:if>
			   </td>
			   <td><span name="tips" title="${info.info}" style="overflow: hidden;display:block;text-overflow: ellipsis;white-space: nowrap;width: 200px;">${info.info }</span></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>
