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
        $("#save_btn").click(function(){
            if(!check()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/qrcode/qrcode_save.html",
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
        	window.parent.$('#myModal_toji').modal('hide');
            return false;
        });
        
        $(".close").click(function(){
        	window.parent.$('#myModal_toji').modal('hide');
            return false;
        });
    });
    
     function check()
    {
        
        var xtbm=$("input[name='model.codename']").val();
        
        if(xtbm==null || xtbm==''){
            //showWarning("系统编码不能为空");
            errorAlert("微信名称不能为空");
            return false;
        }
        return true;
    }
     
    
     </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/qrcode/qrcode_save.html" name="search" id="form_edit" method="post">
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">微信信息</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>微信名称
                        </th>
                         <td  >
                            <c:if test="${op!='add'}">
                                <input value="${model.codename }" name="model.codename"  type="text" />
                                <input value="${model.codeid }" name="model.codeid" type="hidden"/>
                            </c:if>
                            <c:if test="${op == 'add'}">
                                <input id="codename" maxlength="30" placeholder="不要超过30个字符哦(^_^)" name="model.codename" type="text" value="${model.codename }" class="form-control"/>
                            </c:if>
                        </td>
              </tr>
             <tr>
                        <th >
                                                                                 主办单位
                        </th>
                         <td >
                         <input id="codename" maxlength="30" placeholder="不要超过30个字符哦(^_^)" name="model.codeintroduction" type="text" value="${model.codeintroduction }" class="form-control"/>
                        </td>
             </tr>
             <tr>
                        <th >
                            <span class="red">*</span>二维码图片
                        </th>
                         <td >
                            <div style="height:220px; overflow:auto;">
		                     	 ${imageHtml }
	                        </div>
                        </td>
             </tr>
             <tr>
                        <th >
                            <span class="red">*</span>logo图片
                        </th>
                         <td >
                            <div style="height:220px; overflow:auto;">
		                     	 ${logoHtml }
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