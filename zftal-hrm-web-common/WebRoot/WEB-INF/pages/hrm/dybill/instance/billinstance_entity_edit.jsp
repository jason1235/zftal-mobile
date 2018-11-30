<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="entity_${valueEntity.id}">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		var btn_cx=$("#entity_${valueEntity.id}").find(".btn_xxxx_cx");
		var btn_bc=$("#entity_${valueEntity.id}").find(".btn_xxxx_bc");
		var op=$("#entity_${valueEntity.id}").find("input[name='op']").val();
		
		function requestCancel(){
			$.post("<%=request.getContextPath() %>/bill/instance_cancel.html",
					"spBillInstance.id=${spBillInstance.id}&valueEntity.id=${valueEntity.id}&spBillConfig.id=${spBillConfig.id }&xmlBillClassBean.id=${billClass.id}"+
					"&xmlBillClassBean.privilegeType=${xmlBillClassBean.privilegeType}&localEdit=${localEdit}&innerClick=${true}&saveLog=${saveLog}",
					function(data){ 
						if("${xmlBillClassBean.scanStyle}"=="LIST"){
							$("#entity_${valueEntity.id}").remove();
							$("#billClass_${xmlBillClassBean.id}").find("#entity_container").append(data);
						}else{
							$("#entity_${valueEntity.id}").replaceWith(data);
						}
						
					}
			);
		}
		
		$(btn_cx).click(function(){
			if(op=="add"){
				if("${xmlBillClassBean.scanStyle}"=="LIST"){
					requestCancel();
				}else{
					$("#billClass_${xmlBillClassBean.id}").find(".demo_add_02").css("display","block");
					$("#entity_${valueEntity.id}").remove();
				}
			}else{
				requestCancel();
			}
			
		});
		
		$(btn_bc).click(function(){
			$.post("<%=request.getContextPath() %>/bill/instance_save.html",$("form[id='form_${valueEntity.id}']").serialize(),
				function(data){
					if(data.success!=null&&!data.success){
						tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
						
						$("#window-sure").click(function() {
							alertDivClose();
						});
					}else{
						if("${xmlBillClassBean.scanStyle}"=="LIST"){
							$("#entity_${valueEntity.id}").remove();
							$("#billClass_${xmlBillClassBean.id}").find("#entity_container").append(data);
						}else{
							$("#entity_${valueEntity.id}").replaceWith(data);
						}
						
					}
					
				}
			);
		});
	});
</script>
<form id="form_${valueEntity.id}" enctype="multipart/form-data" method="post">
<input type="hidden" name="op" value="${op }"/>
<input type="hidden" name="saveLog" value="${saveLog}"/>
<input type="hidden" name="localEdit" value="${localEdit}"/>
<input type="hidden" name="innerClick" value="true"/>
<input type="hidden" name="valueEntity.id" value="${valueEntity.id }"/>
<input type="hidden" name="spBillConfig.id" value="${spBillConfig.id }"/>
<input type="hidden" name="xmlBillClassBean.id" value="${xmlBillClassBean.id }"/>
<input type="hidden" name="spBillInstance.id" value="${spBillInstance.id }"/>
<input type="hidden" name="valueEntity.entityType" value="${valueEntity.entityType }"/>
<input type="hidden" name="valueEntity.infoEntityId" value="${valueEntity.infoEntityId }"/>
<input type="hidden" name="xmlBillClassBean.privilegeType" value="${xmlBillClassBean.privilegeType }"/>
<ul class="btn_xxxx">
	<li class="btn_xxxx_cx"><a style="cursor: pointer;">撤销</a></li>
	<li class="btn_xxxx_bc"><a style="cursor: pointer;">保存</a></li>
</ul>
<table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
	<tbody>
	    <c:if test="${fn:length(xmlBillClassBean.commonBillPropertys)==0}">
		<c:forEach items="${xmlBillClassBean.photoBillPropertys }" var="billProperty">
		<tr>
			<th id="${billProperty.id }" >
				<c:if test="${billProperty.required}"><span class="red">*</span></c:if>
				${billProperty.name }
			</th>
			<td colspan="${xmlBillClassBean.colNum * 2 -1}" >
				${valueEntity.editMap[billProperty.id] }
			</td>
		</tr>
		</c:forEach>
		</c:if>
		<c:set var="photoRow" value="5"/>
	    <c:set var="usePhoto" value="0"/>
	    <c:if test="${fn:length(xmlBillClassBean.photoBillPropertys) >0}">
	        <c:set var="usePhoto" value="1"/>
	    </c:if>
	    <c:set var="comRow" value="0"/>
		<c:set var="cIndex" value="0"/>
		<c:forEach items="${xmlBillClassBean.commonBillPropertys }" step="${xmlBillClassBean.colNum - usePhoto}">
		<c:set var="rIndex" value="0"/>
		<c:if test="${fn:length(xmlBillClassBean.commonBillPropertys)>cIndex}">
		<tr>
			<c:forEach items="${xmlBillClassBean.commonBillPropertys }" begin="${cIndex }" end="${cIndex+xmlBillClassBean.colNum-1 - usePhoto}"
				 var="billProperty">
			<th id="${billProperty.id }" >
				<c:if test="${billProperty.required}"><span class="red">*</span></c:if>
				${billProperty.name }
			</th>
			<td>
				<c:if test="${billProperty.editable}">
					${valueEntity.editMap[billProperty.id] }
				</c:if>
				<c:if test="${!billProperty.editable}">
					<input name="${billProperty.fieldName }" type="hidden" value="${valueEntity.valueMap[billProperty.id]}"/>
					${valueEntity.editMap[billProperty.id] }
				</c:if>
			</td>
			<c:set var="rIndex" value="${rIndex+1 }"/>
			</c:forEach>
			<c:if test="${cIndex==0}">
				<c:forEach items="${xmlBillClassBean.photoBillPropertys }" var="billProperty">
	                 <th id="${billProperty.id }" rowspan="${photoRow }">
		                <c:if test="${billProperty.required}"><span class="red">*</span></c:if>
		                ${billProperty.name }
		            </th>
		            <td rowspan="${photoRow }">
		                ${valueEntity.editMap[billProperty.id] }
		            </td>
		        </c:forEach>
            </c:if>
			<c:if test="${rIndex < xmlBillClassBean.colNum - usePhoto}">
				<c:forEach begin="${rIndex+1 }" end="${xmlBillClassBean.colNum }" >
				<th> </th> <td> </td>
				</c:forEach>
			</c:if>
		</tr>
			<c:set var="cIndex" value="${cIndex+xmlBillClassBean.colNum  - usePhoto}"/>
			<c:set var="comRow" value="${comRow+1}"/>
			<c:if test="${comRow>=photoRow}">
                 <c:set var="usePhoto" value="0"/>
            </c:if>
         </c:if>
		</c:forEach>
		<c:forEach items="${xmlBillClassBean.imageBillPropertys }" var="billProperty">
		<tr>
			<th id="${billProperty.id }" >
				<c:if test="${billProperty.required}"><span class="red">*</span></c:if>
				${billProperty.name }
			</th>
			<td colspan="${xmlBillClassBean.colNum * 2 -1}" date="">
				${valueEntity.editMap[billProperty.id] }
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</form>
</div>
