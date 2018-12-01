<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<style>
		.ui-autocomplete{
			z-index:12001;
			width: 500px
		}
		<c:if test="${view=='view'}">
		#beanview td{
		   width:240px;
		}
		</c:if>
	</style>
	<script type="text/javascript">
	
		$(function(){
			$("#cancel").click(function(){
				divClose();
				return false;
			});
			$("#save_btn").click(function(){
				$.post("<%=request.getContextPath()%>/normal/casualStaffBatch_save.html",
						$("#form_edit").serialize(),function(data){
						var callback = function(){
							reflashPage();
						};
						if(data.success){
							processDataCall(data, callback);
						}else{
							showWarning(data.text);
						}
						
				},"json");
				return false;
			});
		});
	</script>
</head>

<body>
	<form id="form_edit">
		<input type="hidden" name="classId" value="${classId }"/> 
		<input type="hidden" name="instanceId" value="${instanceId }"/> 
		<input type="hidden" name="op" value="${op }"/> 
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
				<thead>
					<tr>
						<th colspan="4">
							<span><font color="#0f5dc2" style="font-weight:normal;">信息维护</font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
							<c:if test="${view !='view'}">
								<button id="save_btn">保 存</button>
							</c:if>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:set var="mstep" value="2"/>
					<c:set var="cIndex" value="0"/>
					<c:forEach items="${editables }" var="prop" step="${mstep }">
					<c:set var="rIndex" value="0"/>
					<tr>
						<c:forEach items="${editables }" begin="${cIndex }" end="${cIndex+mstep-1}"
							 var="property">
						<th id="${billProperty.id }" >
							<span class="red"><c:if test="${property.need }">*</c:if></span>${property.name}
						</th>
						<td>
							<c:if test="${view=='view'}">
							    <span>${model.viewHtml[property.fieldName]}</span>
							</c:if>
							<c:if test="${view!='view'}">
                                <c:if test="${property.editable}">
                                  <span>${model.editHtml[property.fieldName]}</span>
                                </c:if>
                                <c:if test="${property.viewable&&!property.editable}">
                                  <span>${model.viewHtml[property.fieldName]}</span>
                                </c:if>
							</c:if>
						</td>
						<c:set var="rIndex" value="${rIndex+1 }"/>
						</c:forEach>
						<c:if test="${rIndex < mstep}">
							<c:forEach begin="${rIndex+1 }" end="${mstep }" >
							<th> </th> <td> </td>
							</c:forEach>
						</c:if>
					</tr>
					<c:set var="cIndex" value="${cIndex+mstep }"/>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
