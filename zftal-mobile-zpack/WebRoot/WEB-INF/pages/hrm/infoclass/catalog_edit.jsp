<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
		$(function(){
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					alert("目录名称不得为空，请重新输入！");
					return false;
				}

				$.post("baseinfo/infocatalog_save.html",$("form:last").serialize(),function(data){

					var callback = function(){
						location.href = "<%=request.getContextPath() %>/infoclass/infoclass_list.html?catalog.type=${type}";
					};
					processDataCall(data, callback);
				},"json");
				
				return false;
			});

			$("#cancel").click(function(){
				divClose();
				return false;
			});
		});
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="guid" value="${model.guid }" />
		<input type="hidden" name="menuId" value="${model.menuId }" />
		<input type="hidden" name="type" value="${type}" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>目录维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">保 存</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th><span class="red">*</span>目录名称</th>
						<td>
							<input type="text" class="text_nor" id="name" name="name" size="25"  maxlength="16" value="${model.name }" />
							<input type="hidden" name="catalog.index" value="1" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>