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
		
		<style type="text/css">
			.ui-autocomplete{
				z-index:12001;
				width: 500px
			}
			.formlist td,.formlist th {
				padding-left:10px;
			}
			.form-control {
				color:#000;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				$("#cancel").click(function(){
					history.go(-1);
				});
				
				$("#save_btn").click(function(){
					var auditReason = $("#titleInput").val();				
					$.post("<%=request.getContextPath()%>/webcast/audit_audit.html",
						$("#form_edit").serialize(),function(data){
							if(data.success){
								window.parent.scroll(0,0);
								openAlert(data.text, "s","auto", function(){
								  location.href = _path + "/webcast/audit_list.html";
							    });
							}else{
								errorAlert(data.text);
								window.parent.scroll(0,0);
							}
					 },"json");
					return false;
				});
				
				$('#back').click(function(){
					location.href = _path + "/webcast/audit_list.html";
				});
				
			});	
		</script>
  </head>
  
  <body>
    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <input type="hidden" name="op" value="${op }"/>
        <input type="hidden" name="query.appid" value="${model.appid}"/>
        <div class="sl_mod modal-content" style="border:none">
            <div class="modal-header">
                <button type="button" class="close" onclick="history.go(-1)">
	                <span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span>
	                <span class="sr-only">Close</span>
                </button>              
                <h4 class="modal-title text-left" id="myModalLabel">审核管理</h4>      
            </div>
            <div class="modal-body">
            	<jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
            	<div class="tab" style="clear: both;">
            		<table width="80%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            			<tbody>
		                    <tr>
		                        <th width="15%"><span class="red"></span>审核:</th>
		                        <td>
		                            <select name="query.isaudit"  value="${model.isaudit}" class="form-control">
		                               <option value="1" <c:if test = "${model.isaudit eq 1}"> selected="selected"</c:if>>通过</option>
			                           <option value="2" <c:if test = "${model.isaudit eq 2}"> selected="selected"</c:if>>驳回</option>
		                            </select>
		                        </td>
		                    </tr>     	                    
		                   <tr>
		                        <th width="15%"><span class="red">*</span>通过/驳回意见:</th>
		                        <td>
		                         	<textarea name="query.auditReason" id="titleInput" maxlength="100" placeholder="不要超过100个字符哦(^_^)" 
		                        	class="form-control" rows="3" cols="10">${model.auditReason}</textarea>
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
  </body>
</html>
