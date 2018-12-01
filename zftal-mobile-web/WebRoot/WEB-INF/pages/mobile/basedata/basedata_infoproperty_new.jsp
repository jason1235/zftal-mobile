<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/infoclass/property.js"></script>
    <script type="text/javascript">
        $(function(){
            initSaveEvent();
            initVirtualColumnEvent();
            loadVirtualColumnInfo();
        });
    </script>
</head>

<body>
    <form id="form2">
    	<div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">信息类属性维护</h4>
            </div>
        <input type="hidden" name="guid" value="${model.guid }" />
        <input type="hidden" name="classId" value="${model.classId }" />
        <div class="modal-body" style="background-color: #fff;">
        <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span>信息类属性维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr name="step1">
                        <th >
                            <span class="red">*</span>属性名称
                        </th>
                        <td>
                            <input type="text" class="form-control" id="name" name="name" size="25" maxlength="16" value="${model.name }" />
                        </td>
                        <td colspan="2"></td>
                    </tr>
                    <tr name="step1">
                        <th>
                            <span class="red">*</span>字段名称
                        </th>
                        <td>
                            <input type="text" class="form-control" id="fieldName" name="fieldName" size="25"  maxlength="15" value="${model.fieldName }"
                                    style="ime-mode:disabled;" <c:if test="${!empty model.guid}">disabled="disabled"</c:if> />
                        </td>
                        <th>
                            <span class="red"></span>字段注释
                        </th>
                        <td>
                            <input type="text" class="form-control" id="description" name="description" size="25" maxlength="32" value="${model.description }" />
                        </td><%--
                        <th>
                            <span class="red">*</span>虚拟字段
                        </th>
                        <td>否
                            <input name="virtual" value="false" type="hidden"/>
                        </td>
                    --%></tr>
                    <%--<tr name="step1">
                        <th>
                            <span class="red">*</span>显示宽度
                        </th>
                        <td>
                            <input type="text" class="form-control" id="width" name="width" size="25"  maxlength="16" value="${model.width }"/>
                        </td>
                        <th>
                            <span class="red">*</span>显示高度
                        </th>
                        <td>
                            <input type="text" class="form-control" id="height" name="height" size="25"  maxlength="16" value="${model.height }"/>
                        </td>
                    </tr>
                --%></tbody>
            </table>
       </div>
       <div class="modal-footer" style="background-color: #fff;">
                <button type="button" class="btn btn-primary" id="save">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
    </form>
</div>
</html>