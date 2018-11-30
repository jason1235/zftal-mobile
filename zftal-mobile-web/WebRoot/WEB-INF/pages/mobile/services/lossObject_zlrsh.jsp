<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head_v5.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    $(function(){
    	var flag = '${query.flag}';
        $("#save_btn").click(function(){
        	var lossuser = $("input[name='query.lossuser']").val();
        	if(lossuser==null || lossuser==''){
        		if(flag == '1'){
	                errorAlert("丢失者不能为空");
        		}else{
        			errorAlert("寻回者不能为空");
        		}
                
                return false;
            }
        	
            $.post("<%=request.getContextPath()%>/serviceManager/loss_control.html",
                $("#form_edit").serialize()  ,function(data){
                    var callback = function(){
                    		
						};
						if(data.success){
							$(".ymPrompt_close",window.parent.document).click();
                        	$("#search",window.parent.document).submit();
						}else{
							showWarning(data.text);
						}
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
            modalClose();
            return false;
        });
        
        $(".close").click(function(){
            modalClose();
            return false;
        });
    });
    
     </script>
</head>
<body>
    <form action="" name="search" id="form_edit" method="post">
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">失物招领审核</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
             <tr>
                        <th width="30%">
                        <span class="red">*</span>
                        <input value="1" name="query.isover" type="hidden"/>
                        	<c:if test="${query.flag eq '1' }">
                        	丢失者
                        	</c:if>
                        	<c:if test="${query.flag eq '0' }">
                        	寻回者
                        	</c:if>
                            <input value="${query.id }" name="query.id" type="hidden"/>
                                                                               
                        </th>
                         <td >
                         <input value="${query.lossuser }" name="query.lossuser" type="text" class="form-control" maxlength="10" placeholder="不要超过10个字符哦(^_^)"/>
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