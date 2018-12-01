<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="list_${xmlBillClassBean.id}" >
<script type="text/javascript">
	$(function() {
		var div_billClass=$("#billClass_${xmlBillClassBean.id}");
		var tr=$("#list_${xmlBillClassBean.id}").find("tr");
		var btn_bj=$("#list_${xmlBillClassBean.id}").find(".btn_xxxx_bj");
		var btn_sc=$("#list_${xmlBillClassBean.id}").find(".btn_xxxx_sc");
		
		var div_billClass=$("#billClass_${xmlBillClassBean.id}");
		var currentEntityNum=parseInt("${fn:length(valueEntities)}");
		var append="${xmlBillClassBean.append}";
		var maxLength=parseInt("${xmlBillClassBean.maxLength}");
		if(currentEntityNum<maxLength){
			div_billClass.find(".demo_add_02").css("display","block");
		}else{
			div_billClass.find(".demo_add_02").css("display","none");
		}
		
		tr.click(function(){
			$(tr).removeClass("current");
			$(this).addClass("current");
			if($(this).attr("type")=="INFOCLASS"&&"${localEdit}"!="true"){
				btn_bj.css("display","none");
			}else{
				btn_bj.css("display","block");
			}
		});
		
		$(btn_sc).click(function(){
			var currentItem=div_billClass.find(".current");
			if(currentItem.length<=0){
				showWarning("请选择行");
				return false;
			}
			showConfirm("确定要删除吗？");
			
			$("#why_cancel").click(function(){
				alertDivClose();
			});
			var valueEntityId=currentItem.attr("name");
			$("#why_sure").click(function(){
				$.post("<%=request.getContextPath() %>/bill/instance_remove.html",
						"spBillInstance.id=${spBillInstance.id}&spBillConfig.id=${spBillConfig.id }"+
						"&xmlBillClassBean.id=${xmlBillClassBean.id}&valueEntity.id="+valueEntityId+
						"&xmlBillClassBean.privilegeType=${xmlBillClassBean.privilegeType}&localEdit=${localEdit}&saveLog=${saveLog}",
					function(data){ 
						if(data.success){
							$("#entity_"+valueEntityId).remove();
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
			var currentItem=div_billClass.find(".current");
			if(currentItem.length<=0){
				showWarning("请选择行");
				return false;
			}
			var valueEntityId=currentItem.attr("name");
			$.post("<%=request.getContextPath() %>/bill/instance_modify.html",
					"spBillInstance.id=${spBillInstance.id}&spBillConfig.id=${spBillConfig.id }"+
					"&xmlBillClassBean.id=${xmlBillClassBean.id}&valueEntity.id="+valueEntityId+
					"&xmlBillClassBean.privilegeType=${xmlBillClassBean.privilegeType}&localEdit=${localEdit}&saveLog=${saveLog}",
				function(data){
					$("#list_${xmlBillClassBean.id}").replaceWith(data);
					div_billClass.find(".demo_add_02").css("display","none");
				}
			);
		});
		$(".changeField").closest("td").mouseover(function(){
			var div=$(this).find("div");
			tip($(this),div.html());
		});
	});
</script>
<%--抓取不为0的时候不显示删除按钮可能会造成抓取到多条数据后无法删除让用户重新选择的情况
故而屏蔽之
	<c:if test="${xmlBillClassBean.catchRecordNum eq 0 && !(xmlBillClassBean.privilegeType eq 'SEARCH')}"> --%>
	<c:if test="${!(xmlBillClassBean.privilegeType eq 'SEARCH')}">
	<ul class="btn_xxxx"><input type="hidden" value="2012-09-05 11:25:57.734 00012" name="globalid">
		<c:if test="${xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE' || xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE_EDIT'}">
		<li class="btn_xxxx_sc"><a style="cursor: pointer;">删除</a></li>
		</c:if>
		<c:if test="${xmlBillClassBean.privilegeType eq 'SEARCH_EDIT' || xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE_EDIT'}">
		<c:if test="${empty(xmlBillClassBean.classId)}">
			<li class="btn_xxxx_bj" style="display: none;"><a style="cursor: pointer;">编辑</a></li>
		</c:if>
		<c:if test="${!empty(xmlBillClassBean.classId)}">
			<li class="btn_xxxx_bj" style="display: none;"><a style="cursor: pointer;">编辑</a></li>
		</c:if>
		</c:if>
	</ul>
	</c:if>
	<div class="con_overlfow">
	<table summary="" class="dateline tablenowrap" align="" width="100%">

	<thead>
		<tr>
		<!--<th>来源</th>-->
		<c:forEach items="${xmlBillClassBean.commonBillPropertys }" var="billProperty">
			<th id="${billProperty.id }" style="text-align: right;" >
				${billProperty.name }
			</th>
		</c:forEach>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${valueEntities}" var="valueEntity">
		<tr id="entity_${valueEntity.id}" name="${valueEntity.id}" type="${valueEntity.entityType}">
		<!-- <td>${valueEntity.entityType.desc}</td> -->
		<c:forEach items="${xmlBillClassBean.commonBillPropertys }" var="billProperty">
			<td> ${valueEntity.newViewMap[billProperty.id] }
				<div class="history" style="display: none;">${valueEntity.viewMap[billProperty.id] }</div></td>
		</c:forEach>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	</div>
</div>

