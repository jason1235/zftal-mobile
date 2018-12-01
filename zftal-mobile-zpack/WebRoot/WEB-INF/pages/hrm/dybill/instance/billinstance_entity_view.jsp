<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="entity_${valueEntity.id}">
<script type="text/javascript">
	$(function() {
		var div_billClass=$("#billClass_${xmlBillClassBean.id}");
		var btn_sc=$("#entity_${valueEntity.id}").find(".btn_xxxx_sc");
		var btn_bj=$("#entity_${valueEntity.id}").find(".btn_xxxx_bj");
		
		$(btn_sc).click(function(){
			showConfirm("确定要删除吗？");
			
			$("#why_cancel").click(function(){
				alertDivClose();
			});

			$("#why_sure").click(function(){
				$.post("<%=request.getContextPath() %>/bill/instance_remove.html",
						"spBillInstance.id=${spBillInstance.id}&valueEntity.id=${valueEntity.id}&spBillConfig.id=${spBillConfig.id }&xmlBillClassBean.id=${xmlBillClassBean.id}"+
						"&xmlBillClassBean.privilegeType=${xmlBillClassBean.privilegeType}&localEdit=${localEdit}&saveLog=${saveLog}",
					function(data){ 
						if(data.success){
							$("#entity_${valueEntity.id}").remove();
							div_billClass.find(".demo_add_02").css("display","block");
							alertDivClose();
						}else{
							tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
							$("#why_sure").click(function(){
								alertDivClose();
							});
						}
					}
				,"json");
			});
		});
		
		$(btn_bj).click(function(){
			$.post("<%=request.getContextPath() %>/bill/instance_modify.html",
					"spBillInstance.id=${spBillInstance.id}&valueEntity.id=${valueEntity.id}&spBillConfig.id=${spBillConfig.id }&xmlBillClassBean.id=${xmlBillClassBean.id}"+
					"&xmlBillClassBean.privilegeType=${xmlBillClassBean.privilegeType}&localEdit=${localEdit}&saveLog=${saveLog}",
				function(data){
					$("#entity_${valueEntity.id}").replaceWith(data);
				}
			);
		});
		
		if('${localEdit}'=='true'&&'${innerClick}'=='false'){
			$(btn_bj).click();
		}
		
		$(".changeField").closest("td").mouseover(function(){
			var div=$(this).find("div");
			tip($(this),div.html());
		});
	});
</script>
<style>
.formlist th{
width:20%;
}
.formlist td{
width:200px;
}
</style>
<ul class="btn_xxxx">
	<c:if test="${xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE' || xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE_EDIT'}">
	<li class="btn_xxxx_sc"><a style="cursor: pointer;">删除</a></li>
	</c:if>
	<c:if test="${xmlBillClassBean.privilegeType eq 'SEARCH_EDIT' || xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE_EDIT'}">
	<c:if test="${empty(valueEntity.infoEntityId)||localEdit }">
	<li class="btn_xxxx_bj"><a style="cursor: pointer;">编辑</a></li>
	</c:if>
	</c:if>
</ul>
<table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
	<tbody>
	<c:set var="photoRow" value="5"/>
	<c:set var="usePhoto" value="0"/>
    <c:if test="${fn:length(xmlBillClassBean.photoBillPropertys) >0}">
        <c:set var="usePhoto" value="1"/>
    </c:if>
    <c:set var="comRow" value="0"/>
		<c:set var="cIndex" value="0"/>
		<c:forEach items="${xmlBillClassBean.commonBillPropertys }" step="${xmlBillClassBean.colNum - usePhoto}" >
		<c:set var="rIndex" value="0"/>
		<c:if test="${fn:length(xmlBillClassBean.commonBillPropertys)>cIndex}">
		<!--按colNum换行-->
			<tr>
				<c:forEach items="${xmlBillClassBean.commonBillPropertys }" begin="${cIndex }" end="${cIndex+xmlBillClassBean.colNum-1 - usePhoto }"
					 var="billProperty">
				<th id="${billProperty.id }" >
					${billProperty.name }
				</th>
				<td>
					${valueEntity.newViewMap[billProperty.id] }
					<div class="history" style="display: none;">${valueEntity.viewMap[billProperty.id] }</div>
				</td>
				<c:set var="rIndex" value="${rIndex+1 }"/>
				</c:forEach>
				<c:if test="${cIndex==0}">
					<c:forEach items="${xmlBillClassBean.photoBillPropertys }" var="billProperty">
			            <th style="width: 30px" id="${billProperty.id }" rowspan="${photoRow }">
			                ${billProperty.name }
			            </th>
			            <td rowspan="${photoRow }" >
			                ${valueEntity.newViewMap[billProperty.id] }
			                <div class="history" style="display: none;">${valueEntity.viewMap[billProperty.id] }</div>
			            </td>
		            </c:forEach>
	            </c:if>
				<!--填充空行-->
				<c:if test="${rIndex < xmlBillClassBean.colNum  - usePhoto}">
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
		<c:if test="${fn:length(xmlBillClassBean.commonBillPropertys)==0}">
                <c:forEach items="${xmlBillClassBean.photoBillPropertys }" var="billProperty">
                    <tr>
                        <th id="${billProperty.id }" >
                            ${billProperty.name }
                        </th>
                        <td colspan="${xmlBillClassBean.colNum * 2 -1}" >
                            ${valueEntity.editMap[billProperty.id] }
                            <div class="history" style="display: none;">${valueEntity.viewMap[billProperty.id] }</div>
                        </td>
                    </tr>
                </c:forEach>
                <c:set var="setPhoto" value="${setPhoto+1}"/>
            </c:if>
		<c:forEach items="${xmlBillClassBean.imageBillPropertys }" var="billProperty">
		<tr>
			<th id="${billProperty.id }" >
				${billProperty.name }
			</th>
			<td colspan="${xmlBillClassBean.colNum * 2 -1}" >
				${valueEntity.newViewMap[billProperty.id] }
				<div class="history" style="display: none;">${valueEntity.viewMap[billProperty.id] }</div>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</div>
