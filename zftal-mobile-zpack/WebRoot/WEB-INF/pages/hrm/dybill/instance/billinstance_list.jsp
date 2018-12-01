<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini"%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
		<style type="text/css">
		    span.history{
		    	padding:0 30px;
		    	float:right;
		    	margin-top:-1px;
		    	cursor:pointer;
		    	background:#ECF5FF;
		    	color:#2672d6;
		    	border-left:#CBE4F8 1px solid;
		    	border-top:#CBE4F8 1px solid;
		    }
		    span.on{
		    	background:#4381D1;
		    	color:#ffffff;
		    	border-left:#4169E1 1px solid;
		    	border-top:#4169E1 1px solid;
		    }
		    li a.selected{
		    	border:1px solid #adc6e7;
		    	background:#ffffff;
		    	color:#333333;
		    	height: 22px;
			    line-height: 22px;
			    padding: 0 9px;
		    }
		</style>
		<script type="text/javascript">
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js");
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js");
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/hrm/code.js");
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/hrm/normal/resume/resume.js");
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/hrm/inputPrompt.js");
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/hrm/imageUpload.js");
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/hrm/fileUpload.js");
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/hrm/date.js");
			jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/tip/tip.js");
		</script>
		<script type="text/javascript">
		    window.onbeforeunload=function(e){
		    	var saves=$("#billContent").find(".btn_xxxx_bc");
	            if(saves.length>0){
	            	return "离开页面后将会丢失尚未保存的信息";
	            }
			}
		</script>
	</head>
	<body>
		<div id="billContent" name="${spBillInstance.id}">
			<input id="billConfigId" type="hidden" name="spBillConfig.id" value="${spBillConfig.id }"/>
			<c:forEach items="${billClasses }" var="xmlBillClassBean">
			<c:set var="valueEntities" value="${classValueEntityMap[xmlBillClassBean.id] }"></c:set>
			<div class="demo_xxxx" id="${xmlBillClassBean.id }">
				<h3 class="college_title" style="cursor: pointer;">
					<span class="title_name">${xmlBillClassBean.name }</span>
				</h3>
				
				<script type="text/javascript">
					$(function() {
						var btn_new_add=$("#billClass_${xmlBillClassBean.id}").find("#btn_new_add");
						var btn_new_choice=$("#billClass_${xmlBillClassBean.id}").find("#btn_new_choice");
						var currentEntityNum=parseInt("${fn:length(valueEntities)}");
						var append="${xmlBillClassBean.append}";
						var maxLength=parseInt("${xmlBillClassBean.maxLength}");
						
						if(currentEntityNum<maxLength){
							$("#billClass_${xmlBillClassBean.id}").find(".demo_add_02").css("display","block");
						}else{
							$("#billClass_${xmlBillClassBean.id}").find(".demo_add_02").css("display","none");
						}
						function tile_add(){
							$.post("<%=request.getContextPath() %>/bill/instance_add.html",
									"spBillInstance.id=${spBillInstance.id}&spBillConfig.id=${spBillConfig.id }&xmlBillClassBean.id=${xmlBillClassBean.id}"+
									"&xmlBillClassBean.privilegeType=${xmlBillClassBean.privilegeType}&localEdit=${localEdit}&innerClick=${true}&saveLog=${saveLog}",
								function(data){
									$("#billClass_${xmlBillClassBean.id}").find("#entity_container").append(data);
									currentEntityNum++;
									if(append=="true"&&currentEntityNum<maxLength){
										$("#billClass_${xmlBillClassBean.id}").find(".demo_add_02").css("display","block");
									}else{
										$("#billClass_${xmlBillClassBean.id}").find(".demo_add_02").css("display","none");
									}
								}
							);
						}
						function list_add(){
							$.post("<%=request.getContextPath() %>/bill/instance_add.html",
									"spBillInstance.id=${spBillInstance.id}&spBillConfig.id=${spBillConfig.id }&xmlBillClassBean.id=${xmlBillClassBean.id}"+
									"&xmlBillClassBean.privilegeType=${xmlBillClassBean.privilegeType}&localEdit=${localEdit}&innerClick=${true}&saveLog=${saveLog}",
								function(data){
									$("#billClass_${xmlBillClassBean.id}").find("#entity_container").children().remove();
									$("#billClass_${xmlBillClassBean.id}").find("#entity_container").append(data);
									currentEntityNum++;
									$("#billClass_${xmlBillClassBean.id}").find(".demo_add_02").css("display","none");
								}
							);
						}
						
						$(btn_new_add).click(function(){
							if("${xmlBillClassBean.scanStyle}"=="LIST"){
								list_add();
							}else{
								tile_add();
							}
						});
						
						$(btn_new_choice).click(function(){
							showWindow("选择信息","<%=request.getContextPath() %>/bill/instance_infoChoice.html?"+
									"spBillInstance.id=${spBillInstance.id}&spBillConfig.id=${spBillConfig.id }&xmlBillClassBean.id=${xmlBillClassBean.id}"+
									"&xmlBillClassBean.privilegeType=${xmlBillClassBean.privilegeType}&staffId=${staffId}&saveLog=${saveLog}",
									600, 400);
						});
						
					});
					
					
				</script>
				
				<!--列表模式 -->
				<c:if test="${xmlBillClassBean.scanStyle eq 'LIST'}">
				<div id="billClass_${xmlBillClassBean.id }">
					<div id="entity_container">
						<%@include file="billinstance_entity_list_view.jsp" %>
					</div>
					<c:if test="${xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE'||xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE_EDIT'}">
					<div class="demo_add_02" >
						<c:if test="${xmlBillClassBean.append}">
						<a id="btn_new_add" style="cursor:pointer;">添 加</a>
						</c:if>
						<c:if test="${xmlBillClassBean.choice}">
						<a id="btn_new_choice" style="cursor:pointer;">选择</a>
						</c:if>
					</div>
					</c:if>
				</div>
				</c:if>
					
				<!--平铺模式 -->
				<c:if test="${xmlBillClassBean.scanStyle eq 'TILE'}">
				<div id="billClass_${xmlBillClassBean.id }">
					<div id="entity_container">
					<c:forEach items="${valueEntities}" var="valueEntity" varStatus="tms">
						<%@include file="billinstance_entity_view.jsp" %>
					</c:forEach>
					</div>
					<c:if test="${xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE'||xmlBillClassBean.privilegeType eq 'SEARCH_ADD_DELETE_EDIT'}">
					<div class="demo_add_02" >
						<c:if test="${xmlBillClassBean.append}">
						<a id="btn_new_add" style="cursor:pointer;">添 加</a>
						</c:if>
						<c:if test="${xmlBillClassBean.choice}">
						<a id="btn_new_choice" style="cursor:pointer;">选择</a>
						</c:if>
					</div>
					</c:if>
				</div>
				</c:if>
			</div>
		</c:forEach>
		</div>
	</body>
</html>
