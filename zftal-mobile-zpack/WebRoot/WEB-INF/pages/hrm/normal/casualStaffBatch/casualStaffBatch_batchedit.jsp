<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript">
        $(function(){
            $('#action').click(function(){
            	$.post("<%=request.getContextPath()%>/normal/casualStaffBatch_batchModify.html",
                        $("#batchEdit").serialize(),function(data){
		            		var callback = function(){
		                        reflashPage();
		                    };
		                    if(data.success){
		                        processDataCall(data, callback);
		                    }else{
		                        showWarning(data.text);
		                    }
                        },"json");
            });
            $("#cancel").click(function(){
                $(".ymPrompt_close").click();
            });
            findProperties();
        });
        function findProperties(){
            $.post("<%=request.getContextPath()%>/normal/casualStaffBatch_findProperties.html",
                $("#batchEdit").serialize(),function(data){
                    $("#edithtml").html(data.edithtml);
                },"json");
           }
        </script>
    </head>
    <body>
        <div class="tab" id="tab">
        <form id="batchEdit" action="/normal/casualStaffBatch_batchModify.html">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="formlist">
                <thead>
                    <tr>
                        <th colspan="4"><span>批量修改<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
                    </tr>
                </thead>
                <tfoot>
                  <tr>
                    <td colspan="4">
                        <div class="btn">
                            <button id="action" type="button" >保 存</button>
                            <button id="cancel" type="button">取 消</button>
                        </div>
                    </td>
                  </tr>
                </tfoot>
                <tbody>
                  <tr>
                    <th width="15px">属性</th>
                    <td width="35%">
                        <select name="batchFieldName" onchange="findProperties()">
                            <c:forEach items="${plist }"  var="property">
                                <option value="${property.fieldName }">${property.name }</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="classId" value="${classId }"/>
                        <input type="hidden" name="ids" value="${ids }"/>
                    </td>
                    <th width="28px">修改值</th>
                    <td width="35%" id="edithtml">
                        
                    </td>
                  </tr>
                </tbody>
            </table>
            </form>
        </div>
    </body>
</html>
