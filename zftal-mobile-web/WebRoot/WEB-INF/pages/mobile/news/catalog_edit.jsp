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
		body{margin:0 0 0 0;}
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
		
			var showType = $('#sshowType').val();
			if (showType == 'TELETEXT_SHOW') {
				$('#listtype_tr').css('display','');
			} else {
				$('#listtype_tr').css('display','none');
			}
			
			$('#sshowType').bind('change',function(){
				var type = $(this).val();
				if (type == 'TELETEXT_SHOW') {
				$('#listtype_tr').css('display','');
			} else {
				$('#listtype_tr').css('display','none');
			}
			});
			
			$("#save_btn").click(function(){
			initErrorAlert();
				var name=$("#form_edit").find("input[name='query.catalogName']").val();
				var code=$("#form_edit").find("input[name='query.catalogCode']").val();
				
				var showType = $("#form_edit").find("select[name='query.showType']").val();
				var status = $("#form_edit").find("input[name='query.status']:checked").val();
				//var toPage = $("#form_edit").find("input[name='query.toPage']").val();
				//var perPageSize = $("#form_edit").find("input[name='query.perPageSize']").val();
				
				if(code==''){
					//showWarning("类别编码为必填项");
					errorAlert("类别编码为必填项");
					return false;
				}
				reg=/^([a-zA-Z0-9]+)$/;   
       		   	if(!reg.test(code)){
	       		   	//alert("类别编码必须只包含字母或数字！");
	       		   	errorAlert("类别编码必须只包含字母或数字！");
	       		   	return false; 
       		   	}
				if(name==''){
					//showWarning("类别名称为必填项");
					errorAlert("类别名称为必填项");
					return false;
				}
				
				
				$.post("<%=request.getContextPath()%>/zxgl/newsCatalog_save.html",
						$("#form_edit").serialize(),function(data){
						var callback = function(){
							//reflashPage();
							top.xg_rightFrame.$('#form_list').submit();
						};
						if(data.success){
						    modalClose();
						    callback();
							//processDataCall(data, callback);
						}else{
							//showWarning(data.text);
							errorAlert(data.text);
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
        <input type="hidden" name="query.catalogId" value="${model.catalogId}"/>
        <input type="hidden" name="originalCatalog" value="${model.catalogName }"/>
        <input type="hidden" name="query.listType" cssClass="form-control" value="1"/>
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">资讯类别维护</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
                
                <tbody>
                    <tr>
                        <c:if test="${op=='modify'}">
                        <th width="30%">
                            <span class="red"></span>类别编码
                        </th>
                        <td>
                        <span style="color:gray">${model.catalogCode }</span>
                        </td>
                        </c:if>
                        <c:if test="${op=='add'}">
                        <th width="30%">
                            <span class="red">*</span>类别编码
                        </th>
                        <td>
                        <input type="text" name="query.catalogCode" maxlength="30" placeholder="不要超过30个字符哦(^_^)"  class="form-control" value="${model.catalogCode }" />
                        </td>
                        </c:if>
                        
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>类别名称
                        </th>
                        <td><input type="text" name="query.catalogName" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  class="form-control" value="${model.catalogName }" /></td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>展现形式
                        </th>
                        <td>
                        <s:select id="sshowType" name="query.showType" cssClass="form-control" list="showTypeEnum" value="#request.model.showType" listKey="key" listValue="text" headerKey="" theme="simple" ></s:select>
                        </td>
                    </tr>
                    
                    <!--<tr id="listtype_tr">
                        <th>
                            <span class="red">*</span>列表形式
                        </th>
                        <td>
                        
                        <s:select id="listType" name="query.listType" cssClass="form-control" list="#{1:'列表',2:'平铺' }" value="#request.model.listType" listKey="key" listValue="value" headerKey="" theme="simple" ></s:select>
                        
                        </td>
                    </tr>
                    
                    -->
                    <!--<tr>
                    <th>
                            <span class="red"></span>类别图片
                        </th>
                        <td>
                        ${imageHtml }
                        </td>
                    
                        
                        
                    </tr>
                    
                --></tbody>
            </table>
            </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>
<%-- 
	<form id="form_edit">
		<input type="hidden" name="op" value="${op }"/>
		<input type="hidden" name="query.catalogId" value="${model.catalogId}"/>
		<input type="hidden" name="originalCatalog" value="${model.catalogName }"/>  
		<table class="formlist">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">资讯类别维护</font></span>
                        </th>
                    </tr>
                </thead>
        </table>
		<div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
				
				<tbody>
					<tr>
						
						<c:if test="${op=='modify'}">
						<th>
							<span class="red"></span>类别编码
						</th>
						<td>
						<span style="color:gray">${model.catalogCode }</span>
						</td>
						</c:if>
						<c:if test="${op=='add'}">
						<th>
							<span class="red">*</span>类别编码
						</th>
						<td>
						<input type="text" name="query.catalogCode" class="text_nor" style="width:200px;" value="${model.catalogCode }" />
						</td>
						</c:if>
						
					</tr>
					<tr>
						<th>
							<span class="red">*</span>类别名称
						</th>
						<td><input type="text" name="query.catalogName" class="text_nor" style="width:200px;" value="${model.catalogName }" /></td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>展现形式
						</th>
						<td>
						<s:select id="sshowType" name="query.showType" list="showTypeEnum" value="#request.model.showType" listKey="key" listValue="text" headerKey="" theme="simple" cssStyle="width:100px;"></s:select>
						</td>
					</tr>
					
					<tr id="listtype_tr">
						<th>
							<span class="red">*</span>列表形式
						</th>
						<td>
						<s:select id="listType" name="query.listType" list="#{1:'列表',2:'平铺' }" value="#request.model.listType" listKey="key" listValue="value" headerKey="" theme="simple" cssStyle="width:100px;"></s:select>
						</td>
					</tr>
					
					<tr>
					<th>
							<span class="red"></span>类别图片
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
	</form>
	--%>
</body>
</html>
