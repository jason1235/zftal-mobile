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
				var name=$("#form_edit").find("input[name='fixTypeQuery.name']").val();
				
				$.post("<%=request.getContextPath()%>/reportFix/reportFix_saveOrUpdate.html",
						$("#form_edit").serialize(),function(data){
						var callback = function(){
							//reflashPage();
							top.xg_rightFrame.$('#form_list').submit();
						};
						if(data.success){
						    modalClose();
						    //callback();
						    window.parent.location.reload();
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
        <input type="hidden" name="fixTypeQuery.id" value="${fixType.id}"/>
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">报修类别维护</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
                
                <tbody>
                    <tr>
                        <th>
                            <span class="red">*</span>类别名称
                        </th>
                        <td><input type="text" name="fixTypeQuery.name" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  class="form-control" value="${fixType.name }" /></td>
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
