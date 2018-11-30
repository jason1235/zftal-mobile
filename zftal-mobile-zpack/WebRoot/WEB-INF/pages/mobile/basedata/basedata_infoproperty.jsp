<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    
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
        <input type="hidden" name="guid" value="${model.guid }" />
        <input type="hidden" name="classId" value="${model.classId }" />
        
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span>信息类属性维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save" type="button">保 存</button>
                                <button id="cancel" type="button">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr name="step1">
                        <th>
                            <span class="red">*</span>属性名称
                        </th>
                        <td>
                            <input type="text" class="text_nor" id="name" name="name" size="25" maxlength="16" value="${model.name }" />
                        </td>
                        <td colspan="2"></td>
                    </tr>
                    <tr name="step1">
                        <th>
                            <span class="red">*</span>字段名称
                        </th>
                        <td>
                            <input type="text" class="text_nor" id="fieldName" name="fieldName" size="25"  maxlength="16" value="${model.fieldName }"
                                    style="ime-mode:disabled;" <c:if test="${!empty model.guid}">disabled="disabled"</c:if> />
                        </td>
                        <th>
                            <span class="red"></span>字段注释
                        </th>
                        <td>
                            <input type="text" class="text_nor" id="description" name="description" size="25" maxlength="32" value="${model.description }" />
                        </td>
                        <%--<th>
                            <span class="red">*</span>虚拟字段
                        </th>
                        <td>否
                            <input name="virtual" value="false" type="hidden"/>
                        </td>
                    	--%>
                    	</tr>
                    <tr name="step1">
                        <th>
                            <span class="red">*</span>显示宽度
                        </th>
                        <td>
                            <input type="text" class="text_nor" id="width" name="width" size="25"  maxlength="16" value="${model.width }"/>
                        </td>
                        <th>
                            <span class="red">*</span>显示高度
                        </th>
                        <td>
                            <input type="text" class="text_nor" id="height" name="height" size="25"  maxlength="16" value="${model.height }"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>