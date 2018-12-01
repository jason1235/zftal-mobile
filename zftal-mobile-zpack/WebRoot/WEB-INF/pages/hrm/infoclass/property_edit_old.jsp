<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	
	<script type="text/javascript">
		var sycnProps = [];
		var sycnIndex = 0*1;
		
		$(function(){
			
			//保存
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					alert("属性名称不得为空，请重新输入！");
					return false;
				}
				
				if( $("#fieldName").val() == "" ) {
					alert("字段名不得为空，请重新输入！");
					return false;
				}
				
				if( $("#fieldLen").val() == "" ) {
					alert("字段长度不得为空，请重新输入！");
					return false;
				}
				
				$.post('<%=request.getContextPath() %>/infoclass/infoproperty_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
						//window.location.reload();
					};
					
					processDataCall(data, callback);
				}, "json");
				
				return false;
			});
			
			//取消
			$("#cancel").click(function(){
				divClose();
				return false;
			})
			
			$("input[name='syncToField_name']").hover(function(){
				var set = $(this).offset();
				$("#_syscnSelector").css("left",set.left);
				$("#_syscnSelector").css("top",set.top+$(this).height());
				$("#inputsel").slideDown("fast");
			},function(){
				$(parent.document).find("#inputsel").hide();
			})
			initSyscn();
			function initSyscn(){
				if($("input[name='syncToField']").val()!=''){
					var v = $("input[name='syncToField']").val();
					$.each(parent.sycnProps,function(){
						if(this.guid==v){
							$("input[name='syncToField_name']").val(this.name);
						}
					});
				}
			}
		})
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="guid" value="${model.guid }" />
		<input type="hidden" name="classId" value="${model.classId }" />
		
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>信息类属性维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
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
						<th>
							<span class="red">*</span>属性名称
						</th>
						<td>
							<input type="text" class="text_nor" id="name" name="name" size="25" value="${model.name }" />
						</td>
						<th>
							<span class="red"></span>属性描述
						</th>
						<td>
							<input type="text" class="text_nor" id="description" name="description" size="25" value="${model.description }" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>字段名称
						</th>
						<td>
							<input type="text" class="text_nor" id="fieldName" name="fieldName" size="25" value="${model.fieldName }"
									style="ime-mode:disabled;" <c:if test="${!empty model.guid}">disabled="disabled"</c:if> />
						</td>
						<th>
							<span class="red">*</span>字段类型
						</th>
						<td>
							<select id="fieldType" name="fieldType" class="text_nor" style="width: 185px;">
								<c:forEach items="${types }" var="type">
								<option value="${type.name }" <c:if test="${type.name eq model.fieldType}">selected="selected"</c:if>>${type.text }</option>
								</c:forEach> 
							</select>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>可显示
						</th>
						<td>
							<input type="radio" class="text_nor" name="viewable" value="true" <c:if test="${model.viewable eq true }">checked="checked"</c:if> />是
							<input type="radio" class="text_nor" name="viewable" value="false" <c:if test="${!model.viewable eq true }">checked="checked"</c:if> /><font class="red">否</font>
						</td>
						<th>
							<span class="red"></span>可编辑
						</th>
						<td>
							<input type="radio" class="text_nor" name="editable" value="true" <c:if test="${model.editable eq true }">checked="checked"</c:if> />是
							<input type="radio" class="text_nor" name="editable" value="false" <c:if test="${!model.editable eq true }">checked="checked"</c:if> /><font class="red">否</font>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>必填
						</th>
						<td>
							<input type="radio" class="text_nor" name="need" value="true" <c:if test="${model.need eq true }">checked="checked"</c:if> />是
							<input type="radio" class="text_nor" name="need" value="false" <c:if test="${!model.need eq true }">checked="checked"</c:if> /><font class="red">否</font>
						</td>
						<th>
							<span class="red"></span>默认值
						</th>
						<td>
							<input type="text" class="text_nor" id="" name="defaultValue" size="25" value="${model.defaultValue }" />
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>显示表达式
						</th>
						<td colspan="3">
							<input type="text" class="text_nor" name="displayFormula" size="25" value="${model.displayFormula }" />
							<button class="btn_common" onclick="return false;">定义公式</button>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>字段长度
						</th>
						<td>
							<input type="text" class="text_nor" id="fieldLen" name="fieldLen" size="25" value="${model.fieldLen }" 
									style="ime-mode:disabled" onkeypress="check_int_dot(this);" />
						</td>
						<th>
							<span class="red"></span>小数位数
						</th>
						<td>
							<input type="text" class="text_nor" id=digits name="digits" size="25" value="${model.digits }" 
									style="ime-mode:disabled" onkeypress="check_int_dot(this);" />
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>引用代码库
						</th>
						<td>
							<input type="text" class="text_nor" id="codeId" name="codeId" size="25" value="${model.codeId }" />
						</td>
						<th>
							<span class="red"></span>唯一性标识
						</th>
						<td>
							<input type="radio" class="text_nor" name="unique" value="true" <c:if test="${model.unique eq true }">checked="checked"</c:if> />是
							<input type="radio" class="text_nor" name="unique" value="false" <c:if test="${model.unique eq false }">checked="checked"</c:if> /><font class="red">否</font>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>同步字段
						</th>
						<td>
							<input type="text" class="text_nor" name="syncToField_name" size="25" value="${model.syncToField }" readOnly="true" editable="false" />
							<input type="hidden" name="syncToField" value="${model.syncToField }" />
							
						</td>
						<th>
							<span class="red"></span>同步条件
						</th>
						<td>
							<!--<input type="text" class="text_nor" name="syncCondition" size="25" value="${model.syncCondition }" />-->
							<select id="uniq_sel" name="syncCondition" class="text_nor" style="width: 185px;">
								<option value="">--请选择--</option>
								<c:forEach items="${uniques}" var="prop" varStatus="unicSt">
								<option value="${prop.fieldName }" <c:if test="${prop.fieldName eq model.syncCondition}">selected="selected"</c:if>>${prop.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>验证表达式
						</th>
						<td colspan="3">
							<input type="text" class="text_nor" name="verifyStr" size="25" value="${model.verifyStr}" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>