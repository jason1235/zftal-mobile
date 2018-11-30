<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/commons/hrm/head_v5.ini" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
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
				modalClose();
				return false;
			});
            
            $(".close").click(function(){
                modalClose();
                return false;
            });
			
			$("#save_btn").click(function(){
				var name=$("#form_edit").find("input[name='query.name']").val();
				var moduleId=$("#form_edit").find("input[name='query.moduleId']").val();
				
				var classPath = $("#form_edit").find("input[name='query.classPath']").val();
				var url = $("#form_edit").find("input[name='query.url']").val();
				//var toPage = $("#form_edit").find("input[name='query.toPage']").val();
				//var perPageSize = $("#form_edit").find("input[name='query.perPageSize']").val();
				
				if(name==''){
					showWarning("模块名称为必填项");
					return false;
				}
				if(moduleId==''){
					showWarning("模块ID为必填项");
					return false;
				}
				if(classPath==''){
					showWarning("模块类地址为必填项");
					return false;
				}
				if(url==''){
					showWarning("模块地址为必填项");
					return false;
				}
				
				
				$.post("<%=request.getContextPath()%>/mkgl/module_save.html",
						$("#form_edit").serialize(),function(data){
						var callback = function(){
							//reflashPage();
							location.reload();
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
        <input type="hidden" name="op" value="${op }"/>
        <input type="hidden" name="query.id" value="${model.id}"/>
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">模块维护</h4>
            </div>
            <div class="modal-body">
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
                
                <tbody>
                    
                    <tr>
                    
                        <th width="30%">
                            <span class="red">*</span>模块名称
                        </th>
                        <td><input type="text" name="query.name" class="form-control" value="${model.name }" /></td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>模块ID
                        </th>
                        <td>
                        <input type="text" name="query.moduleId" class="form-control" value="${model.moduleId }" />
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>模块类地址
                        </th>
                        <td>
                        <input type="text" name="query.classPath" class="form-control" value="${model.classPath}" />
                        <span style="color:red">（格式：“Android类地址;IOS Controller地址;Windows Phone类地址”）</span>
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>模块地址
                        </th>
                        <td>
                        <input type="text" name="query.url" class="form-control" value="${model.url}" />
                        </td>
                    </tr>
                    
                
                    
                    <tr>
                    <th>
                            <span class="red"></span>模块图标
                        </th>
                        <td>
                        ${imageHtml }
                        </td>
                    
                        
                        
                    </tr>
                    
                </tbody>
            </table>
            </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>
<%--	<form id="form_edit">
		<input type="hidden" name="op" value="${op }"/>
		<input type="hidden" name="query.id" value="${model.id}"/>
		  
		<table class="formlist">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">模块维护</font></span>
                        </th>
                    </tr>
                </thead>
        </table>
		<div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
				
				<tbody>
					
					<tr>
					
						<th>
							<span class="red">*</span>模块名称
						</th>
						<td><input type="text" name="query.name" class="text_nor" style="width:200px;" value="${model.name }" /></td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>模块ID
						</th>
						<td>
						<input type="text" name="query.moduleId" class="text_nor" style="width:200px;" value="${model.moduleId }" />
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>模块类地址
						</th>
						<td>
						<input type="text" name="query.classPath" class="text_nor" style="width:200px;" value="${model.classPath}" />
						<span style="color:red">（格式：“Android类地址;IOS Controller地址;Windows Phone类地址”）</span>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>模块地址
						</th>
						<td>
						<input type="text" name="query.url" class="text_nor" style="width:200px;" value="${model.url}" />
						</td>
					</tr>
					
				
					
					<tr>
					<th>
							<span class="red"></span>模块图标
						</th>
						<td>
						${imageHtml }
						</td>
					
						
						
					</tr>
					
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
	</form> --%>
</body>
</html>
