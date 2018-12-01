<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<style>
		<c:if test="${view=='view'}">
		#beanview td{
		   width:240px;
		}
		</c:if>
	</style>
	<script type="text/javascript">

	function after(item) {
        $("#ghLabel").html(item.userId);
        $("#ghInput").val(item.userId);
        $("#dwmcLabel").html(item.departmentName);
        $("#dwmInput").val(item.departmentId);
        
        if($("#dwh").length!=0){
           $("#xm").val(item.userName);                         
           $("#dwh").val(item.departmentId);
           $("#dwh").next().val(item.departmentName);                           
           $("#dqztm").val(item.status);
           $("#dqztm").next().val(item.statusName);
        }
        return false;
    }
	
		$(function(){
			var caches = {};
			$("#cancel").click(function(){
				divClose();
				return false;
			});
			
			$("#save_btn").click(function(){
				var name=$("#form_edit").find("input[name=gh]").val();
				if(name==null || name==''){
					showWarning("姓名为必填项");
					return false;
				}
				$.post("<%=request.getContextPath()%>/normal/staffBatch_save.html",
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
			
			
			//但对个人概况进行处理
			if(${clazz.identityName !='JBXXB'}){
				$("[name='isShowTd']").show();
			}
		});
	</script>
</head>

<body>
	<form id="form_edit">
		<input type="hidden" name="classId" value="${classId }"/> 
		<input type="hidden" name="instanceId" value="${instanceId }"/> 
		<input type="hidden" name="op" value="${op }"/> 
		<table class="formlist">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">信息维护</font></span>
                        </th>
                    </tr>
                </thead>
        </table>
		<div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
				
				<tbody>
					<c:if test="${op eq 'add' }">
					<tr>
						<th>
							<!--<span class="red">*</span>
						-->人员查询</th>
						<td colspan="3">
						<c:if test="${!clazz.typeInfo.moreThanOne}">
							<ct:personThink  id="userNameInput" selectFunction="after" />
							<span class="red">输入职工号、姓名、部门代码、部门名称和状态查找</span>
						</c:if>
						<c:if test="${clazz.typeInfo.moreThanOne}">
                            <ct:selectPerson name="gh" id="gh" single="false" width="300px" />
                        </c:if>
						</td>
					</tr>
					<c:if test="${!clazz.typeInfo.moreThanOne}">
						<tr>
							<th>
								<span class="red">*</span>职工号
							</th>
							<td>
								<label id="ghLabel"></label>
								<input id="ghInput" type="hidden" name="gh"/>
							</td>
							<th name="isShowTd" style="display:none">
								<span class="red"></span>所在部门
							</th>
							<td name="isShowTd" style="display:none">
								<label id="dwmcLabel"></label>
								<input id="dwmInput" type="hidden" name="dwm" />
							</td>
							<c:if test="${rowspan == 4}">
	                        <th rowspan="6">
	                            <span class="red"><c:if test="${zpInfo.need }">*</c:if></span>${zpInfo.name}
	                        </th>
	                        <td rowspan="6">
	                            <c:if test="${view=='view'}">
	                                <span>${model.viewHtml[zpInfo.fieldName]}</span>
	                            </c:if>
	                            <c:if test="${view!='view'}">
	                                <c:if test="${zpInfo.editable}">
	                                  <span>${model.editHtml[zpInfo.fieldName]}</span>
	                                </c:if>
	                                <c:if test="${zpInfo.viewable&&!zpInfo.editable}">
	                                  <span>${model.viewHtml[zpInfo.fieldName]}</span>
	                                </c:if>
	                            </c:if>
	                        </td>
	                        </c:if>
						</tr>
					</c:if>
					</c:if>
					<c:if test="${op eq 'modify' }">
					<tr name="isShowTd" style="display:none">
						<th name="isShowTd" style="display:none">
							<span class="red">*</span>姓名
						</th>
						<td colspan="3" name="isShowTd" style="display:none">
							<label id="ghLabel">${xm }</label>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>职工号
						</th>
						<td>
							<label id="ghLabel">${gh }</label>
							<input type="hidden" name="gh" value="${gh }"/>
						</td>
						<th name="isShowTd" style="display:none">
							<span class="red"></span>所在部门
						</th>
						<td name="isShowTd" style="display:none">
							<label id="dwmcLabel">${dwmc }</label>
						</td>
						<c:if test="${rowspan == 4}">
                        <th rowspan="6">
                            <span class="red"><c:if test="${zpInfo.need }">*</c:if></span>${zpInfo.name}
                        </th>
                        <td rowspan="6">
                            <c:if test="${view=='view'}">
                                <span>${model.viewHtml[zpInfo.fieldName]}</span>
                            </c:if>
                            <c:if test="${view!='view'}">
                                <c:if test="${zpInfo.editable}">
                                  <span>${model.editHtml[zpInfo.fieldName]}</span>
                                </c:if>
                                <c:if test="${zpInfo.viewable&&!zpInfo.editable}">
                                  <span>${model.viewHtml[zpInfo.fieldName]}</span>
                                </c:if>
                            </c:if>
                        </td>
                        </c:if>
					</tr>
					</c:if>
					
					<c:if test="${zpInfo == null }">
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
					</c:if>
					
					<c:if test="${zpInfo != null }">
					<c:set var="mstep" value="2"/>
					<c:set var="cIndex" value="0"/>
					<c:set var="allCount" value="${fn:length(editables) }"/>
					<c:forEach items="${editables }" var="prop" begin="${cIndex }" end="${rowspan }">
					<tr>
					    <th >
					        <span class="red"><c:if test="${prop.need }">*</c:if></span>${prop.name}
					    </th>
					    <td>
					        <c:if test="${view=='view'}">
					            <span>${model.viewHtml[prop.fieldName]}</span>
					        </c:if>
					        <c:if test="${view!='view'}">
					            <c:if test="${prop.editable}">
					              <span>${model.editHtml[prop.fieldName]}</span>
					            </c:if>
					            <c:if test="${prop.viewable&&!prop.editable}">
					              <span>${model.viewHtml[prop.fieldName]}</span>
					            </c:if>
					        </c:if>
					    </td>
					    <c:if test="${cIndex == 0 && rowspan == 5}">
					    <th rowspan="6">
					        <span class="red"><c:if test="${zpInfo.need }">*</c:if></span>${zpInfo.name}
					    </th>
					    <td rowspan="6">
					        <c:if test="${view=='view'}">
					            <span>${model.viewHtml[zpInfo.fieldName]}</span>
					        </c:if>
					        <c:if test="${view!='view'}">
					            <c:if test="${zpInfo.editable}">
					              <span>${model.editHtml[zpInfo.fieldName]}</span>
					            </c:if>
					            <c:if test="${zpInfo.viewable&&!zpInfo.editable}">
					              <span>${model.viewHtml[zpInfo.fieldName]}</span>
					            </c:if>
					        </c:if>
					    </td>
					    </c:if>
					</tr>
					<c:set var="cIndex" value="${cIndex+1 }"/>
					</c:forEach>
					<c:if test="${cIndex < rowspan}">
					    <c:forEach begin="${cIndex }" end="5" >
					    <tr><th>&nbsp;</th><td>&nbsp;</td></tr>
					    </c:forEach>
					</c:if>
					
					<c:forEach items="${editables }" var="prop" begin="${cIndex }" step="${mstep }">
					<c:set var="rIndex" value="0"/>
					<tr>
					    <c:forEach items="${editables }" begin="${cIndex }" end="${cIndex+mstep-1}"
					         var="property">
					    <th>
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
					</c:if>
				</tbody>
			</table>
			</div>
			<table class="formlist" >
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
			</table>
	</form>
</body>
</html>
