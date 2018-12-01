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
					var foodcataName = $("#titleInput");
					if(foodcataName == null || foodcataName.val()==''){
						errorAlert("请填写类别名称");
						window.parent.scroll(0,0);
						return false;
					} else {
					   foodcataName = foodcataName.val();
					}
					
					var canteenId = $("#canteen1").val();
					if(canteenId==null||canteenId==""){
					    errorAlert("请选择所属商家");
						window.parent.scroll(0,0);
						return false;
					}
					$.post("<%=request.getContextPath()%>/canteenfood/foodcata_saveOrUpdate.html",
						$("#form_edit").serialize(),function(data){
							if(data.success){
								window.parent.scroll(0,0);
								openAlert(data.text, "s","auto", function(){
								  location.href = _path + "/canteenfood/foodcata_list.html";
							    });
							}else{
								errorAlert(data.text);
								window.parent.scroll(0,0);
							}
					 },"json");
					return false;
				});
				
				$('#back').click(function(){
					location.href = _path + "/canteenfood/foodcata_list.html";
				});
				
			});	
		</script>
  </head>
  
  <body>
    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <input type="hidden" name="op" value="${op }"/>
        <input type="hidden" name="query.foodcataId" value="${model.foodcataId}"/>
        <div class="sl_mod modal-content" style="border:none">
            <div class="modal-header">
                <button type="button" class="close" onclick="history.go(-1)">
	                <span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span>
	                <span class="sr-only">Close</span>
                </button>              
                <h4 class="modal-title text-left" id="myModalLabel">商家管理</h4>      
            </div>
            <div class="modal-body">
            	<jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
            	<div class="tab" style="clear: both;">
            		<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            			<tbody>
		                   <tr id="titleTr">
		                        <th style="width:15%"><span class="red">*</span>类别名称:</th>
		                        <td>
		                         <input id="titleInput" type="text" name="query.foodcataName" maxlength="100" placeholder="不要超过100个字符哦(^_^)" 
		                         	class="form-control" value="${model.foodcataName}" />
		                        </td>
		                    </tr>
		                   <tr>
		                        <th><span class="red">*</span>所属商家:</th>
		                        <td>
		                            <s:if test="canteenList!= null">
		                                <s:select id="canteen1" name="query.canteenId" list="canteenList" listKey="canteenId" 
		                                  listValue="canteenName" value="#request.model.canteenId" theme="simple" cssClass="form-control"></s:select>
		                            </s:if>
		                            <s:else>
		                                <select id="canteen1" name="query.canteenId" class="form-control"></select>
		                            </s:else>
		                        </td>
		                    </tr>
		                    <tr>
		                        <th><span class="red"></span>状态: </th>
		                        <td>
		                            <select name="query.isactive"  value="${model.isactive}" class="form-control">
		                               <option value="1" <c:if test = "${model.isactive eq 1}"> selected="selected"</c:if>>启用</option>
			                           <option value="0" <c:if test = "${model.isactive eq 0}"> selected="selected"</c:if>>禁用</option>
		                            </select>
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
