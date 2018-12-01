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
        var regexp=/^\s*$/;
        $(function(){
            $('#confirm_action').click(function(){
            	if(regexp.test($("#formEdit input[name='gh']").val())){
            		alert("工号不能为空！");
            		return false;
            	}
            	$.post("<%=request.getContextPath()%>/normal/casualStaffBatch_fpgh.html",
                       $("#formEdit").serialize(),function(data){
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
        });
        </script>
    </head>
    <body>
        <div class="tab" id="tab">
        <form id="formEdit" action="/normal/casualStaffBatch_fpgh.html">
        	<input type="hidden" name="classId" value="${classId }"/> 
			<input type="hidden" name="instanceId" value="${instanceId }"/>
            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="formlist">
                <thead>
                    <tr>
                        <th colspan="4">
                        	<span>分配工号<font color="red" style="font-weight:normal;">（请确认工号无误）</font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                  <tr>
                    <td colspan="4">
                        <div class="btn">
                            <button id="confirm_action" type="button" >确认</button>
                            <button id="cancel" type="button">取 消</button>
                        </div>
                    </td>
                  </tr>
                </tfoot>
                <tbody>
                  <tr>
                    <th><span class="red">*</span>工号</th>
                    <td>
                        <input type="text" name="gh" value="${model.values['gh'] }"/>
                    </td>
                  </tr>
                </tbody>
            </table>
            </form>
        </div>
    </body>
</html>
