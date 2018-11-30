<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
        $("#save_btn").click(function(){
            if(!check()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/weibo/weibo_save.html",
                $("#form_edit").serialize()  ,function(data){
                    var callback = function(){
							//reflashPage();
							//location.reload();
						};
						if(data.success){
							//processDataCall(data, callback);
							$(".ymPrompt_close",window.parent.document).click();
                        	$("#search",window.parent.document).submit();
						}else{
							showWarning(data.text);
						}
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
           $(".ymPrompt_close",window.parent.document).click();
           $("#search",window.parent.document).submit();
            return false;
        });
        
        $(".close").click(function(){
            //modalClose();
            $(".ymPrompt_close",window.parent.document).click();
            $("#search",window.parent.document).submit();
            return false; 
        });
    });
     function check()
    {
        
        var wbmc=$("input[name='model.wbmc']").val();
        var client_id=$("input[name='model.client_id']").val();
        var client_sercret=$("input[name='model.client_sercret']").val();
        
        if(wbmc==null || wbmc==''){
            //showWarning("系统编码不能为空");
            errorAlert("微博名称不能为空");
            return false;
        }
        if(client_id==null || client_id==''){
            //showWarning("系统名称不能为空");
            errorAlert("微博ID不能为空");
            return false;
        }
        if(client_sercret==null || client_sercret=='' ){
            //showWarning("系统地址不能为空");
            errorAlert("微博sercret不能为空");
            return false;
        }
        return true;
    }
     
     </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/weibo/weibo_save.html" name="search" id="form_edit" method="post">
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">微博信息</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>微博名称
                        </th>
                         <td  >
                            <c:if test="${op!='add'}">
                                <input value="${model.wbbh }" name="model.wbbh" type="hidden"/>
                            </c:if>
                                <input id="wbmc" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.wbmc"   type="text" value="${model.wbmc }" class="form-control"/>
                        </td>
              </tr>
              <tr>
                        <th  >
                            <span class="red">*</span>微博ID
                        </th>
                         <td>
                            <input id="client_id" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.client_id" type="text" value="${model.client_id }" class="form-control"/>
                        </td>
             </tr>
             <tr>
                        <th >
                            <span class="red">*</span>微博sercret
                        </th>
                         <td >
                         <input id="client_sercret" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.client_sercret" type="text" value="${model.client_sercret }" class="form-control"/>
                            
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