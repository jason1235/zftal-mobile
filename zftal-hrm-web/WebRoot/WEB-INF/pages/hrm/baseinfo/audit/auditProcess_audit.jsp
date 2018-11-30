<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/commons/hrm/head.ini" %>
<script type="text/javascript">
$(function(){
	$("#pass").click(function(){
		passAudit();
	});
	$("#reject").click(function(){
		rejectAudit();
	});
	$("#back").click(function(){
		window.location.href = _path + "/baseinfo/auditProcess_auditPage.html";
	});
	preview();
	fillRows("5", "list_head2", "list_body2", false);//填充空行
});
function passAudit(){//审核通过
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/baseinfo/auditProcess_pass.html',$("#form1 input,#form1 textarea").serialize(),function(data){
		var callback = function(){
			window.location.href = _path + "/baseinfo/auditProcess_auditPage.html";
		};
		processDataCall(data,callback);
	},"json");
}
function rejectAudit(){//审核拒绝
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/baseinfo/auditProcess_reject.html',$("#form1 input,#form1 textarea").serialize(),function(data){
		var callback = function(){
			window.location.href = _path + "/baseinfo/auditProcess_auditPage.html";
		};
		processDataCall(data,callback);
	},"json");
}
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
	<br/>
	<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="2">
					<span>审核意见<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th width="30%"><span class="red"></span>审核意见</th>
				<td width="70%">
					<textarea name="info.info" rows="3" style="width:280px;font-size:12px"></textarea>
				</td>
				<input type="hidden" name="info.classId" value="${process.classId }"/>
				<input type="hidden" name="info.guid" value="${process.guid }"/>
			</tr>
		</tbody>
	    <tfoot>
	      <tr>
	        <td colspan="2">
	            <div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button id="pass">通过</button>
	            <button id="reject">拒绝</button>
	          </div></td>
	      </tr>
		</tfoot>
	</table>
	</div>
</div>
</body>
</html>
