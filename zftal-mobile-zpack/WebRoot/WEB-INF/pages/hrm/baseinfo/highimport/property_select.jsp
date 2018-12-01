<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
		$(function(){
			$("#cancel").click(function(){
				window.close();
			});
			
			$("#save").click(function(){
				saveRequest();
			});
			
			$("#classSelect").val(classId.value);
			fillRows("15","","",false);
		});
		
		function choiceProperty(obj){
			var classId=obj.value;
			$("#classId").val(classId);
			$("form:first").submit();
		}
		
		function saveRequest(){
			var guid=$("#list_body input").serialize();
			var xxlid=$("#classSelect").val();
			$.post('<%=request.getContextPath()%>/baseinfo/property_save.html?property.xxlid=' + xxlid + '&property.mbid=${property.mbid }',guid,function(data){
				var callback = function(){
					$("form:first").submit();
				};
				processDataCall(data,callback);
			},"json");
		}
	</script>
</head>

<body>
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>信息类字段维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="btn">
								<button id="save">保 存</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody id="tbody_bill">
				 	<form action="<%=request.getContextPath()%>/baseinfo/property_select.html"  method="post">
						<input type="hidden" id="classId" name="classId" value="${classId }" />
						<input type="hidden" name="property.mbid" value="${property.mbid }" />
					</form>
					 <tr>
						<th>
							<span class="red">*</span>信息来源
						</th>
						<td>
							<select id="classSelect" name="classId" style="width:206px" onChange="choiceProperty(this)">
								<option value="${classId }"></option>
								<c:forEach items="${infoClasses }" var="infoClass">
								<c:if test="${infoClass.type != 'OVERALL' }">
								<option value="${infoClass.guid }" id="infoClassId" name="infoClassId"
								<c:if test="${infoClass.guid eq xmlBillClass.classId }" >selected="selected"</c:if> >
								${infoClass.name }</option></c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<jsp:include page="property_all.jsp" flush="true"></jsp:include>
				</tbody>
			</table>
		</div>
</body>
</html>