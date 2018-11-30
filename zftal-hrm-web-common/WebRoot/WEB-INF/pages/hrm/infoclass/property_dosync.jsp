<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    
    <script type="text/javascript">
        var sycnProps = [];
        var sycnIndex = 0*1;
        
        $(function(){
            
        	//保存
            $("#save").click(function(){
                if( $("#sync_model").val() == "" ) {
                    alert("同步字段不得为空 请选择！");
                    return false;
                }
                
                $.post('<%=request.getContextPath() %>/infoclass/infoproperty_syncInfo.html', $('form:last').serialize(), function(data){
                    var callback = function(){
                        $("form:first").submit();
                        //window.location.reload();
                    };
                    
                    processDataCall(data, callback);
                }, "json");
                
                return false;
            });
            
            //取消
            $("#cancel").click(function(){
                divClose();
                return false;
            });
            
        });
    </script>
</head>

<body>
    <form>
        <input type="hidden" name="classId" value="${model.classId }" />
        
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="2">
                            <span>选择同步字段<font color="#0f5dc2" style="font-weight:normal;"></font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save">保 存</button>
                                <button id="cancel">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <th>
                            <span class="red"></span>同步字段
                        </th>
                        <td>
                            <select id="sync_model" name="model.guid" class="text_nor" style="width: 185px;">
                                <option value="">--请选择--</option>
                                <c:forEach items="${properties}" var="prop" varStatus="unicSt">
	                                <c:if test="${!empty prop.syncToField}">
	                                <option value="${prop.guid }">${prop.name }</option>
	                                </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>