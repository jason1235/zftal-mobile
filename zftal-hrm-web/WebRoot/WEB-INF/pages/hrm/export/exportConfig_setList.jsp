<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

    <script type="text/javascript">
        //修改信息类属性
        function modifyItem(guid) {
            location.href = "<%=request.getContextPath() %>/dataorigin/dataorigin_page.html?pgId=ecs&ywId=" + guid;
        }
        
        // 行数据修改链接
        function operationList() {
            $("a[name='modify']").click(function(){
                var guid = $(this).closest("tr").attr("id");
                modifyItem(guid);
            });
        }
    </script>
    <style type="text/css">
    .link{
        color: #1c69b6 !important;
        text-decoration: underline;
    }
    </style>
</head>

<body>
    <div class="formbox">
        <h3 class="datetitle_01">
            <span>${model.name }属性信息</span>
        </h3>
        <table summary="" class="dateline" align="" width="100%">
            <thead id="list_head">
                <tr>
                    <td>序号</td>
                    <td>目录名称</td>
                    <td>模板名称</td>
                    <td width="90px">操作</td>
                </tr>
            </thead>
            <tbody id="list_body">
                <c:forEach items="${configList }" var="config" varStatus="i">
                <tr id="${config.id }">
                    <td>${i.index + 1 }</td>
                    <c:forEach items="${typeList }" var="type">
                    <c:if test="${config.type==type.id}">
                    <td>${type.name }</td>
                    </c:if>
                    </c:forEach>
                    <td>${config.name }</td>
                    <td>
                    <c:if test="${config.origin != '0' }">
                    <a name="modify" href="#" class="link">设置数据源</a>
                    </c:if>
                    <c:if test="${config.origin eq '0' }">&nbsp;</c:if>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <script type="text/javascript">
        operationList();
        fillRows(15, '', '', false);
    </script>
</body>

</html>