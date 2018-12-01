<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	$(function(){
		var div=$("#infoChoice_${xmlBillClassBean.classId}");
		var selfNum=parseInt("${selfNum}");
		var choiceNum=parseInt("${choiceNum}");
		var maxLength=parseInt("${xmlBillClassBean.maxLength}");
		maxLength=maxLength-selfNum;
		div.find(".checkedbox").click(function(){
			var checkedNum=0;
			var checkedBox;
			for(var i=0 ;i < div.find(".checkedbox").length;i++){
				checkedBox=div.find(".checkedbox")[i];
				if($(checkedBox).attr("checked")=="checked"){
					checkedNum++;
				}
			}
			if(maxLength==checkedNum){
				$("#billClass_${xmlBillClassBean.id}").find(".demo_add_02").css("display","none");
			}
			if(maxLength>checkedNum){
				$("#billClass_${xmlBillClassBean.id}").find(".demo_add_02").css("display","block");
			}
			if(maxLength<checkedNum){
				showWarning("超出最大数量限制");
				$(this).find("checked").removeAttr("checked");
				return false;
			}
			var infoEntityId=$(this).closest("tr").attr("id");
			if($(this)[0].checked){
				$.post("<%=request.getContextPath() %>/bill/instance_addChoice.html",div.find("form").serialize()+"&valueEntity.infoEntityId="+infoEntityId,
					function(data){
						if("${xmlBillClassBean.scanStyle}"=="LIST"){
							$("#billClass_${xmlBillClassBean.id}").find("#list_${xmlBillClassBean.id}").replaceWith(data);
						}else{
							$("#billClass_${xmlBillClassBean.id}").find("#entity_container").append(data);
						}
					}
				);
			}else{
				$.post("<%=request.getContextPath() %>/bill/instance_removeChoice.html",div.find("form").serialize()+"&valueEntity.infoEntityId="+infoEntityId,
					function(data){
						if(data.message.success){
							if("${xmlBillClassBean.scanStyle}"=="LIST"){
								$("#billClass_${xmlBillClassBean.id}").find("tr[name='"+data.valueEntityId+"']").remove();
							}else{
								$("#billClass_${xmlBillClassBean.id}").find("#entity_container").find("#entity_"+data.valueEntityId).remove();
							}
						}else{
							tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
							$("#why_sure").click(function(){
								alertDivClose();
							});
						}
					},"json"
				);
			}
			
		});
	});
</script>
<div id="infoChoice_${xmlBillClassBean.classId}" style="overflow:visible;">
	<form>
		<input type="hidden" name="spBillConfig.id" value="${spBillConfig.id }"/>
		<input type="hidden" name="xmlBillClassBean.id" value="${xmlBillClassBean.id }"/>
		<input type="hidden" name="spBillInstance.id" value="${spBillInstance.id }"/>
		<input type="hidden" name="saveLog" value="${saveLog }"/>
		<div class="formbox">
			<h3 class="datetitle_01"> 
				<span>${xmlBillClassBean.name}信息列表，限选${xmlBillClassBean.maxLength-selfNum }条</span>
			</h3>
			<div class="con_overlfow" style="width: 598px; height:345px; overflow-y:auto; ">
				<table summary="" class="dateline tablenowrap"" align="" width="100%">
					<thead id="list_head">
						<tr>
							<th>选择</th>
							<th>序号</th>
							<c:forEach items="${xmlBillClassBean.billPropertys }" var="billProperty">
							<th id="${billProperty.id }" >
								${billProperty.name }
							</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${dynaBeanList}" var="bean" varStatus="i">
						<tr id="${bean.values['globalid']}">
							<td><input class="checkedbox" type="checkbox"  
								<c:if test="${choiceEntities[bean.values['globalid']] }">checked="checked"</c:if>
							 	/> </td>
							<td>${i.index+1 }</td>
							<c:forEach items="${xmlBillClassBean.billPropertys }" var="billProperty">
							<td> ${bean.viewHtml[billProperty.fieldName] } </td>
							</c:forEach>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</form>
</div>