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
            if( $("#name").val() == "" ) {
            	errorAlert("目录名称不得为空，请重新输入！");
                return false;
            }

            $.post("<%=request.getContextPath() %>/basedata/infocatalog_save.html",$("form:last").serialize(),function(data){
               parent.document.location.reload();
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
    <form action="<%=request.getContextPath()%>/serviceManager/business_save.html" name="search" id="form_edit" method="post">
            <input type="hidden" name="guid" value="${model.guid }" />
	        <input type="hidden" name="menuId" value="${model.menuId }" />
	        <input type="hidden" name="type" value="${type}" />
            <div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">信息类目录维护</h4>
            </div>
            <div class="modal-body" style="background-color: #fff;">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>目录名称
                        </th>
                         <td  >
                         	<input type="hidden" name="catalog.index" value="1" />
                         	<input type="text" id="name" name="name"   placeholder="不要超过16个字符哦(^_^)" maxlength="16" value="${model.name }" class="form-control"/>
                         	
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