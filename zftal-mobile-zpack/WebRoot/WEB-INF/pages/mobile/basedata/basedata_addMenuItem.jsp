<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    $(function(){
        $("#save_btn").click(function(){
            $.post("<%=request.getContextPath()%>/basedata/basedata_saveMenuItem.html",
                $("#form_edit").serialize()  ,function(data){
                    var callback = function(){
							//reflashPage();
							location.reload();
						};
						if(data.success){
							processDataCall(data, callback);
						}else{
							showWarning(data.text);
						}
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
        	divClose();
		    return false;
        });
    });
    
     </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/basedata/basedata_saveMenuItem.html" name="search" id="form_edit" method="post">

						<input type="hidden"  value="${menuModel.infoClassId }" name="menuModel.infoClassId"/>
						<c:if test="${menuModel.firstMenuId != ''}">
                                <input type="hidden"  value="${menuModel.firstMenuId }" name="menuModel.firstMenuId"/>
                        </c:if>
                        <c:if test="${menuModel.classCdid != ''}">
                                <input type="hidden"  value="${menuModel.classCdid }" name="menuModel.classCdid"/>
                        </c:if>

       <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            <thead>
                    <tr>
                        <th colspan="2">
                            <span><font color="#0f5dc2" style="font-weight:normal;">菜单信息</font></span>
                        </th>
                    </tr>
            </thead>
                
             <tbody>
              <tr>
                        <th  >
                            <span class="red">*</span>菜单名称
                        </th>
                         <td>
                            <input id="cdmc" name="menuModel.menuName" type="text" value="${menuModel.menuName }" style="width:250px"/>
                        </td>
             </tr>
             </tbody>
             <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn" type="button">保 存</button>
                                <button id="cancel" type="button" >取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
       </div> 
</form>


</body>
</html>