<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<link rel="stylesheet" href="../../css/public.css" type="text/css" media="all" />
	<link rel="stylesheet" href="../../css/module.css" type="text/css" media="all" />
	<link rel="stylesheet" href="../../css/skin_blue.css" type="text/css" media="all" />
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript">
	   var opname;
		$(function(){
			$("#save_prop").click(function(){
				if( $("#name").val() == "" )
				{
					return alert("组合查询条件名称不得为空，请重新输入！");
				}

				if( $("#fieldName").val() == "" )
				{
					return alert("字段不得为空，请重新输入！");
				}
				
				if( $("#codeTableName").val() == "" )
				{
					return alert("引用代码表不得为空，请重新输入！");
				}
				
				$.post('<%=request.getContextPath() %>/baseinfo/infogroup_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					
					processDataCall(data, callback);
				}, "json");
			});
			
			$("#cancel").click(function(){
				divClose();
			});

			$("#fieldSelect").val($("#fieldName").val());
			opname = $("#fieldSelect").find("option:selected").attr("name");
		});
		function selectField(){
			var o = $("#fieldSelect").find("option:selected");
			var name = o.attr("name");
			var code = o.attr("code");
			var fieldName = o.attr("value");
			if( $("#name").val() == "" || $("#name").val()==opname)
            {
				$("#name").val(name);
				opname = name;
            }
			$("#fieldName").val(fieldName);
			$("#codeTableName").val(code);
		}
	</script>
	<title>信息类组合查询配置页面</title>
</head>
<body>
	<form>
		<input type="hidden" name="guid" value="${guid }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>信息类组合查询配置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save_prop" type="button">保 存</button>
								<button id="cancel" type="button">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th>
							<span class="red">*</span>条件名称
						</th>
						<td>
							<input type="text" id="name" name="name" class="text_nor" style="width: 180px;" value="${model.name }" />
						</td>
						<th>
							<span class="red">*</span>所属目录
						</th>
						<td  colspan="2">
							<select name="model.catalogName" style="width: 180px;" >
								 <option <c:if test="${model.catalogName eq '基本类' }">selected</c:if> value="基本类">基本类</option>
								 <option <c:if test="${model.catalogName eq '资格类' }">selected</c:if> value="资格类">资格类</option>
								 <option <c:if test="${model.catalogName eq '任职类' }">selected</c:if> value="任职类">任职类</option>
								 <option <c:if test="${model.catalogName eq '其他类' }">selected</c:if> value="其他类">其他类</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>字段
						</th>
						<td>
						    <select id="fieldSelect" onchange="selectField()">
						      <option value="" code="" name="">--请选择--</option>
						      <c:forEach items="${list}" var="p">
						          <option value="${p.fieldName }" code="${p.codeId }" name="${p.name }">${p.name }</option>
						      </c:forEach>
						    </select>
							<input type="hidden" id="fieldName" name="fieldName" class="text_nor" style="width: 180px;" value="${model.fieldName }" />
						</td>
						<th>
							<span class="red">*</span>引用代码表
						</th>
						<td>
							<input type="text" id="codeTableName" name="codeTableName" class="text_nor" style="width: 180px;" value="${model.codeTableName }" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>