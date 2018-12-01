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
				if(code==''){
					//showWarning("类别编码为必填项");
					errorAlert("类别编码为必填项");
					return false;
				}
				reg=/^([a-zA-Z0-9]+)$/;   
				if(name==''){
					//showWarning("类别名称为必填项");
					errorAlert("类别名称为必填项");
					return false;
				}
				
				$.post("<%=request.getContextPath()%>/zxgl/newsCatalog_save.html",
						$("#form_edit").serialize(),function(data){
						var callback = function(){
							top.xg_rightFrame.$('#form_list').submit();
						};
						if(data.success){
						    modalClose();
						    callback();
						}else{
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
                <h4 class="modal-title text-left" id="myModalLabel">文艺投票信息维护</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
                
                <tbody>
                    <tr>
                        <th width="30%">
                            <span class="red"></span>投票的标题
                        </th>
                        <td><input id="name" type="text" name="ballot.name" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                        	class="form-control" value="${ballot.name }" /></td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>开始时间
                        </th>
                        <td><input id="startTime" type="text" name="ballot.startTime" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                        	class="form-control" value="${ballot.startTime}" /></td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>结束时间
                        </th>
                        <td><input id="endTime" type="text" name="ballot.endTime" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                        	class="form-control" value="${ballot.endTime}" /></td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>投票规则
                        </th>
                        <td>
                       		<select id="ruleType" name="ballot.ruleType">
                       			
                       		</select>
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>每人可投票数
                        </th>
                        <td>
                        <input id="ballotNum" type="text" name="ballot.ballotNum" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                        	class="form-control" value="${ballot.ballotNum}" /></td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red"></span>投票介绍
                        </th>
                        <td>
                        	<textarea name="ballot.content" maxlength="250" placeholder="不要超过250个字符哦(^_^)" 
                        		class="form-control" rows="3" cols="10">${ballot.ballotNum}</textarea>
                        </td>
                    </tr>
                    
                    <tr style="max-height: 300px;">
                    <th>
                      	<span class="red"></span>缩略图片<br />
                    </th>
                    <td >
						<div  style="height:300px; " id="myselfimage">
                      		${imageHtml }
                      	</div>
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
