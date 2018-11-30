<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <script type="text/javascript">
        $(function() {
          function getBean(src) {
            var tr = $(src).closest("tr");
            var o = {
                    propertyIdInput : tr.find("input[name='xmlBillProperty.id']"),
                    serialize :"" 
                    };

            var serialize = "xmlId=" + $("input[name='xmlId']").val();
            serialize += "&dataorigin.id=" + $("input[name='dataorigin.id']").val();
            serialize += "&xmlBillProperty.id=" + $(tr).find("input[name='xmlBillProperty.id']").val();
            serialize += "&xmlBillProperty.propertyId=" + $(tr).find("input[name='xmlBillProperty.propertyId']").val();
            o.serialize = serialize;
            return o;
          }
          
          $(".checkbox").click(function() {
            var b = getBean(this);
            var bool = true;
            if($(this).val() == "true"){
              $(this).val("false");
              bool = false;
            }else{
              $(this).val("true");
              bool = true;
            }
            
            if (bool) {
              $.post('<%=request.getContextPath() %>/dataorigin/dataorigin_addChoiceXmlBillProperty.html', b.serialize, function(data){
                if(data.message.success){
                  b.propertyIdInput.val(data.xmlBillProperty.id);
                }
              },"json");
            } else {
              $.post('<%=request.getContextPath() %>/dataorigin/dataorigin_removeChoiceXmlBillProperty.html', b.serialize, function(data){
                if(data.success){
                  b.propertyIdInput.val("");
                }
              },"json");
            }
          });
          
          $(".ymPrompt_close").click(function() {
            window.location.reload();
          });
        });
    </script>
</head>

<body>
    <div class="formbox">
        <input type="hidden" name="xmlId" value="${xmlId}"/>
        <input type="hidden" name="dataorigin.id" value="${dataorigin.id}"/>
        <table summary="" class="dateline" align="" width="100%">
            <thead id="list_head">
                <tr>
                    <td>选择</td>
                    <td>序号</td>
                    <td>属性名称</td>
                    <td>字段名称</td>
                    <td>字段类型</td>
                </tr>
            </thead>
            <tbody id="list_body">
                <c:forEach items="${choicePropertyList }" var="bean" varStatus="i">
                <tr>
                    <input type="hidden" name="xmlBillProperty.id" value="${bean.billProperty.id }"/>
                    <input type="hidden" name="xmlBillProperty.propertyId" value="${bean.infoProperty.guid }"/>
                    <td>
                        <input class="checkbox" type="checkbox" name="propNames" <c:if test="${bean.checked eq true}">checked="true"</c:if>
                        <c:if test="${bean.checked eq true}">value="true"</c:if>
                        <c:if test="${bean.checked eq false}">value="false"</c:if>/>
                    </td>
                    <td>${i.index + 1 }</td>
                    <td>${bean.infoProperty.name }</td>
                    <td>${bean.infoProperty.fieldName }</td>
                    <td>${bean.infoProperty.typeInfo.text }</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>

</html>