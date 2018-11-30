<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script type="text/javascript">
		$(function(){
			function getBean(src){
				var tr=$(src).closest("tr");
				var o = {
						//visableSelect : $(src).closest("tr").find("select[name='xmlBillProperty.visable']"), 
						editableSelect : $(src).closest("tr").find("select[name='xmlBillProperty.editable']"), 
						requiredSelect : $(src).closest("tr").find("select[name='xmlBillProperty.required']"), 
						//visableFont : tr.find(".visable"), 
						editableFont : tr.find(".editable"), 
						requiredFont : tr.find(".required"), 
						propertyIdInput : tr.find("input[name='xmlBillProperty.id']"),
						serialize :"" 
						};

				var serialize="spBillConfig.id="+$("input[name='spBillConfig.id']").val();
				serialize+="&xmlBillClassBean.id="+$("input[name='xmlBillClassBean.id']").val();
				serialize+="&xmlBillProperty.id="+$(tr).find("input[name='xmlBillProperty.id']").val();
				serialize+="&xmlBillProperty.propertyId="+$(tr).find("input[name='xmlBillProperty.propertyId']").val();
				//serialize+="&xmlBillProperty.visable="+o.visableSelect.children("option:selected").val();
				serialize+="&xmlBillProperty.editable="+o.editableSelect.children("option:selected").val();
				serialize+="&xmlBillProperty.required="+o.requiredSelect.children("option:selected").val();
				o.serialize=serialize;
				return o;
			}
			
			$(".checkbox").click(function(){
				var b=getBean(this);
				var bool=true;
				if($(this).val()=="true"){
					$(this).val("false");
					bool=false;
				}else{
					$(this).val("true");
					bool=true;
				}
				
				if(bool){
					//if(b.visableFont.html()=="是"){
					//	b.visableSelect.css("display","block");
					//	b.visableFont.css("display","none");
					//}
					
					if(b.editableFont.html()=="是"){
						b.editableSelect.css("display","block");
						b.editableFont.css("display","none");
					}
					
					b.requiredSelect.css("display","block");
					b.requiredFont.css("display","none");
					
					$.post('<%=request.getContextPath() %>/bill/config_addChoiceXmlBillProperty.html', 
							b.serialize, function(data){
						if(data.message.success){
							b.propertyIdInput.val(data.xmlBillProperty.id);
						}
					},"json");
				}else{
					//b.visableSelect.css("display","none");
					//b.visableFont.css("display","block");
					b.editableSelect.css("display","none");
					b.editableFont.css("display","block");
					b.requiredSelect.css("display","none");
					b.requiredFont.css("display","block");
					$.post('<%=request.getContextPath() %>/bill/config_removeChoiceXmlBillProperty.html',
							b.serialize, function(data){
						if(data.success){
							b.propertyIdInput.val("");
						}
					},"json");
				}
				
			});
			
			$(".select").change(function(){
				var b=getBean(this);
				$.post('<%=request.getContextPath() %>/bill/config_modifyChoiceXmlBillProperty.html', 
						b.serialize, function(data){
					if(data.success){
					}
				},"json");
			});
			$(".ymPrompt_close").click(function(){
				window.location.reload();
			});
		});
	</script>
</head>

<body>
	<div class="formbox">
		<input type="hidden" name="spBillConfig.id" value="${spBillConfig.id}"/>
		<input type="hidden" name="xmlBillClassBean.id" value="${xmlBillClassBean.id}"/>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>选择</td>
					<td>序号</td>
					<td>属性名称</td>
					<td>字段名称</td>
					<td>字段类型</td>
<%--					<td>可显示</td>--%>
					<td>可编辑</td>
					<td>必填</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${choicePropertyList }" var="bean" varStatus="i">
				<tr>
					<input type="hidden" name="xmlBillProperty.id" value="${bean.billProperty.id }"/>
					<input type="hidden" name="xmlBillProperty.propertyId" value="${bean.infoProperty.guid }"/>
					<td>
						<input class="checkbox" type="checkbox" name="propNames" <c:if test="${bean.checked eq true}">checked="true"</c:if>
						<c:if test="${bean.checked eq true}">value="true"</c:if>
						<c:if test="${bean.checked eq false}">value="false"</c:if>/>
					</td>
					<td>${i.index+1 }</td>
					<td>${bean.infoProperty.name }</td>
					<td>${bean.infoProperty.fieldName }</td>
					<td>${bean.infoProperty.typeInfo.text }</td>
					<td>
						<input type="hidden" name="infoProperty.editable" value="${bean.infoProperty.editable }"/>
						<c:if test="${bean.checked eq false}">
							<c:if test="${bean.infoProperty.editable eq true}"><font class="black editable">是</font></c:if>
							<c:if test="${bean.infoProperty.editable eq false}"><font class="red editable">否</font></c:if>
							<select class="select" name="xmlBillProperty.editable" style="display:none ">
								<option value="true">是</option>
								<option value="false">否</option>
							</select>
						</c:if>
						<c:if test="${bean.checked eq true}">
							<c:if test="${bean.infoProperty.editable eq true}">
							<font class="black editable" style="display:none;">是</font>
							<select class="select" name="xmlBillProperty.editable">
								<option value="true">是</option>
								<option value="false" <c:if test="${bean.billProperty.editable eq false}">selected="selected"</c:if>>否</option>
							</select>
							</c:if>
							<c:if test="${bean.infoProperty.editable eq false}">
							<font class="red editable" >否</font>
							<select class="select" name="xmlBillProperty.editable" style="display:none;">
								<option value="true">是</option>
								<option value="false" <c:if test="${bean.billProperty.editable eq false}">selected="selected"</c:if>>否</option>
							</select>
							</c:if>
						</c:if>
					</td>
					<td>
						<input type="hidden" name="infoProperty.need" value="${bean.infoProperty.need }"/>
						<c:if test="${bean.checked eq false}">
							<c:if test="${bean.infoProperty.need eq true}"><font class="black required">是</font></c:if>
							<c:if test="${bean.infoProperty.need eq false}"><font class="red required">否</font></c:if>
							<select class="select" name="xmlBillProperty.required" style="display:none ">
								<option value="true">是</option>
								<option value="false" <c:if test="${bean.infoProperty.need eq false}">selected="selected"</c:if>>否</option>
							</select>
						</c:if>
						<c:if test="${bean.checked eq true}">
							<c:if test="${bean.infoProperty.need eq true}"><font class="black required" style="display:none ">是</font></c:if>
							<c:if test="${bean.infoProperty.need eq false}"><font class="red required" style="display:none ">否</font></c:if>
							<select class="select" name="xmlBillProperty.required">
								<option value="true" <c:if test="${bean.billProperty.required eq true}">selected="selected"</c:if>>是</option>
								<option value="false" <c:if test="${bean.billProperty.required eq false}">selected="selected"</c:if>>否</option>
							</select>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>

</html>