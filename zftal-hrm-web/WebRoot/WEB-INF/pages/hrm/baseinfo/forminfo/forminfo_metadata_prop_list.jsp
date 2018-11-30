<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>

	<script type="text/javascript">
		$(function(){
			var current = null;
			//编辑
			$(".item_text").click( function(){
				var guid = $(this).closest("td").attr("id");
				var checked = $(this).closest("tr").find("#checkbox_id").attr("checked");
				if(guid==""&&checked=="checked"){
					showWarning("请先保存选中行！");
					return false;
				}
				if(guid==""&&checked==null){
					showWarning("无法修改的行，如要修改请先选中并保存！");
					return false;
				}
				if(guid!=""&&checked==null){
					showWarning("该行被撤销选择，无法修改！");
					return false;
				}
				
				
				showWindow("编辑","<%=request.getContextPath() %>/baseinfo/forminfometadata_edit.html?guid="+guid, 720, 200);
			});
			//保存
			$("#btn_ccg").click(function(){
				$.post("<%=request.getContextPath() %>/baseinfo/forminfometadata_save_list.html", $("#bform").serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					processDataCall(data,callback);
				},"json");
				
				return false;
			});
			//返回
			$("#back").click(function(){
				location.href="<%=request.getContextPath()%>/baseinfo/forminfo_class_list.html?guid=${typeId}"; 
			});
		})
	
	</script>
</head>

<body>
	<div id="positionbox" style="position:relative; float:left;">
		<div class="toolbox_fd" id="div_tools" style="display:none;position:absolute;">
			<div class="buttonbox">
				<ul>
					<li><a id="btn_xg" href="#" class="btn_xg">编辑</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_ccg" href="#" class="btn_ccg">保存</a></li>
			</ul>
			<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
		</div>
	</div>
	<form id="bform" action="<%=request.getContextPath() %>/baseinfo/forminfometadata_prop_list.html" method="post">
		<input type="hidden" name="typeId" value="${typeId}"/>
		<input type="hidden" name="classId" value="${classId}"/>
		<div class="formbox">
			<table summary="" class="dateline" align="" width="100%">
				<thead id="list_head">
					<tr>
						<td>选择</td>
						<td>序号</td>
						<td>属性名称</td>
						<td>字段名称</td>
						<td>字段类型</td>
						<td>字段长度</td>
						<td>可显示</td>
						<td>可编辑</td>
						<td>必填</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${metadata_prop_list }" var="bean" varStatus="i">
					<c:if test="${bean.infoProperty.fieldName!='globalid' and bean.infoProperty.fieldName!='lastModifyTime'}">
					<tr id="${bean.guid }">
						<td>
							<input type="checkbox" id="checkbox_id" name="propNames" value="${bean.infoProperty.fieldName}"
								 <c:if test="${bean.checked eq true}">checked="checked"</c:if>/>
						</td>
						<td>${i.index+1 }</td>
						<td>${bean.infoProperty.name }</td>
						<td>${bean.infoProperty.fieldName }</td>
						<td>${bean.infoProperty.typeInfo.text }</td>
						<td>${bean.infoProperty.fieldLen }</td>
						<td>
							<c:if test="${bean.infoProperty.viewable eq true}">是</c:if>
							<c:if test="${bean.infoProperty.viewable eq false}"><font class="red">否</font></c:if>
						</td>
						<td>
							<c:if test="${bean.infoProperty.editable eq true}">是</c:if>
							<c:if test="${bean.infoProperty.editable eq false}"><font class="red">否</font></c:if>
						</td>
						<td>
							<c:if test="${bean.infoProperty.need eq true}">是</c:if>
							<c:if test="${bean.infoProperty.need eq false}"><font class="red">否</font></c:if></td>
						<td id="${bean.guid}"><div>
					      	<div class="current_item">
					        	<span class="item_text">修改</span>
					        </div>
					      </div></td>
					</tr>
					</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
	
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
		fillRows(15, '', '', true);
	</script>
</body>

</html>