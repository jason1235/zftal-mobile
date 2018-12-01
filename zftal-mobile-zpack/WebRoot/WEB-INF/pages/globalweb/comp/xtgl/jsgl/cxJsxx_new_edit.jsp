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
            $.post("<%=request.getContextPath()%>/xtgl/jsgl_save.html",
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
							errorAlert(data.text);
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
    
     function check()
    {
        
        var jsdm=$("input[name='NewJsglQuery.jsdm']").val();
        var jsmc=$("input[name='NewJsglQuery.jsmc']").val();
        
        var chinese = /^([a-zA-Z0-9]+)$/;
		var flag = (chinese.test(jsdm));
        if(jsdm==null || jsdm==''){
            //showWarning("系统编码不能为空");
            errorAlert("角色代码不能为空");
            //$("#myModal_toji",window.parent.document).find(".modal-content").height(612);
            return false;
        }
		if(!flag){
		    //showWarning("系统编码只能为英文和字母的组合");
		    errorAlert("角色代码只能为英文和字母的组合");
		    //$("#myModal_toji",window.parent.document).find(".modal-content").height(612);
            return false;
		}
        if(jsmc==null || jsmc==''){
            //showWarning("系统名称不能为空");
            errorAlert("系统名称不能为空");
            //$("#myModal_toji",window.parent.document).find(".modal-content").height(612);
            return false;
        }
        //if(xtdz==null || xtdz=='' ){
            //showWarning("系统地址不能为空");
        //    errorAlert("系统地址不能为空");
        //    $("#myModal_toji",window.parent.document).find(".modal-content").height(612);
        //    return false;
        //}
        return true;
    }
     </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/xtgl/jsgl_save.html" name="search" id="form_edit" method="post">
            <div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">角色信息维护</h4>
            </div>
            <div class="modal-body" style="background-color: #fff;">
            <jsp:include page="/WEB-INF/pages/globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
             	<input name="op" type="hidden" value="${op }"/>
             <c:if test="${op == 'modify' }">
	              <tr>
	                        <th width="30%" >
	                            <span class="red">*</span>角色代码
	                        </th>
	                         <td  >
	                           ${NewJsglQuery.jsdm }
	                           <input id="xtmc" maxlength="20" placeholder="不要超过20个字符哦(^_^)" name="NewJsglQuery.jsdm" type="hidden" class="form-control" value="${NewJsglQuery.jsdm }"/>
	                        </td>
	              </tr>
              </c:if>
              <c:if test="${op == 'add' }">
	              <tr>
	                        <th width="30%" >
	                            <span class="red">*</span>角色代码
	                        </th>
	                         <td  >
	                           <input id="xtmc" maxlength="20" placeholder="不要超过20个字符哦(^_^)" name="NewJsglQuery.jsdm" type="text" class="form-control" value="${NewJsglQuery.jsdm }"/>
	                        </td>
	              </tr>
              </c:if>
              <tr>
                        <th  >
                            <span class="red">*</span>角色名称
                        </th>
                         <td>
                            <input id="xtmc" maxlength="20" placeholder="不要超过20个字符哦(^_^)" name="NewJsglQuery.jsmc" type="text" class="form-control" value="${NewJsglQuery.jsmc }"/>
                        </td>
             </tr>
             </tbody>
            </table>
       </div> 
            </div>
            <div class="modal-footer" style="background-color: #fff;">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
    </form>

</body>
</html>