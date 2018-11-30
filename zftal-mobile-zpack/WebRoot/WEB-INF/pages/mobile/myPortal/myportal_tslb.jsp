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
	        	window.parent.$('#myModal_toji').modal('hide');
	        	return false;
	        });
	        
	        $(".close").click(function(){
	        	window.parent.$('#myModal_toji').modal('hide');
	        	return false;
	        });
		 	
		});		
		
		
        //保存特色类别
		function doSaveTslb(){
			var url = "<%=request.getContextPath()%>/myportal/myportal_saveTslb.html";
			
			var lbmc = $("input[name='lbmc']").val();
            if(lbmc==null || lbmc==''){
                errorAlert("类别名称不能为空");
                return false;
            }
            
            $.post(url,$("#form_edit").serialize(),function(data){
            	if(data.data=='success'){
	               if(data.code=='1'){
	            	   if(confirm("新增类别成功")){
	            	      window.parent.$('#myModal_toji').modal('hide');
			              parent.location.reload();
	            	   }
	               }else if(data.code=='2'){
	            	   errorAlert(data.msg);
	               }
            	}else{
            	   errorAlert(data.msg);
            	}
            },"json");
		}
	</script>
</head>

<body>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">我的门户特色类别</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height:300px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
                <tbody>
                    <tr>
                   <th width="30%" >
                       <span class="red">*</span>类别名称
                   </th>
                    <td >
                         <input id="lbmc" maxlength="50" placeholder="不要超过50个字符哦(^_^)" name="lbmc" type="text" class="form-control"/>
                   </td>
	              </tr>
	              <tr>
	                  <th width="30%" >
	                     	类别描述
	                  </th>
	                   <td >
	                        <textarea maxlength="200" placeholder="不要超过200个字符哦(^_^)" rows="4" cols="30" id="lbms" name="lbms" class="form-control"></textarea>
	                  </td>
	              </tr>
                </tbody>
            </table>
            </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn" onclick="doSaveTslb();">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>
</body>
</html>
