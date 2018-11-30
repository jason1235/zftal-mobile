<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

  <head>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/select.js"></script>
    <script type="text/javascript">
      $(function() {
        //保存
        $("#save").click(function() {
          if( $("#name").val() == "" ) {
            alert("属性名称不得为空，请重新输入！");
            return false;
          }
          
          if( $("#fieldName").val() == "" ) {
            alert("字段名不得为空，请重新输入！");
            return false;
          }
          
          $("#windown-content").unbind("ajaxStart");
          
          $.post(_path + '/dataorigin/dataorigin_saveXmlBillProperty.html', $("form[id='form2']").serialize(), function(data) {
            var callback = function() {
              location.href = "<%=request.getContextPath() %>/dataorigin/dataorigin_page.html?ywId=" + $("#ywId").val();
            };
            
            processDataCall(data, callback);
          }, "json");
          
          return false;
        });
        
        $("#cancel").click(function() {
          divClose();
        });
        
        function dealType(type) {
          if (type == "CODE") {
            $("#tbody").append(
              "<tr class=\"fieldType\" id=\"CODE\">" +
              "<th>" +
              "<span class=\"red\">*</span>引用代码库" +
              "</th>" +
              "<td>" +
              "<input id=\"codeId\" name=\"xmlBillProperty.codeId\" " +
              "value='${xmlBillProperty.codeId}'" +
              "type=\"hidden\">" +
              "<input class=\"text_nor text_sel\" " +
              "value='${xmlBillProperty.codeStr}' " +
              "onmouseover=\"initSelectConsole(this, '/code/codeCatalog_load.html')\" style=\"width: 200px;\" readonly=\"readonly\" type=\"text\">" +
              "</td>" +
              "<th>&nbsp;</th>" +
              "<td>&nbsp;</td>" +
              "</tr>");
          }
        }
        
        $("#fieldType").change(function() {
          var type=$(this).val();
          $("#tbody").find(".fieldType").remove();
          dealType(type);
        });
        
        dealType("${xmlBillProperty.fieldType}");
        getDefInputStyle();
      });
      
      function getDefInputStyle() {
        $.post(_path + '/dataorigin/dataorigin_getDefInputStyle.html', $("form[id='form2']").serialize(), function(data) {
          if (data.success) {
            $("#defaultStyle").html(data.result);
          } else {
            alert(data.text);
          }
        }, "json");
      }
    </script>
  </head>

  <body>
    <form id="form2">
      <input type="hidden" name="xmlId" value="${xmlId }" />
      <input type="hidden" name="dataorigin.id" value="${dataorigin.id }" />
      <input type="hidden" name="xmlBillProperty.id" value="${xmlBillProperty.id }" />
      <div class="tab">
        <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
          <thead>
            <tr>
              <th colspan="4">
                <span>属性维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
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
          <tbody id="tbody">
            <tr>
              <th>
                <span class="red">*</span>属性名称
              </th>
              <td>
                <input type="text" class="text_nor" id="name" name="xmlBillProperty.name" size="25" maxlength="16" value="${xmlBillProperty.name }" />
              </td>
              <th>
                <span class="red">*</span>字段名称
              </th>
              <td>
                <input type="text" class="text_nor" id="fieldName" name="xmlBillProperty.fieldName" size="25"  maxlength="16" value="${xmlBillProperty.fieldName }" <c:if test="${!empty xmlBillProperty.id}">readonly="readonly"</c:if> />
              </td>
            </tr>
            <tr>
              <th>
                <span class="red"></span>字段类型
              </th>
              <td>
                <select id="fieldType" name="xmlBillProperty.fieldType" class="text_nor" style="width: 185px;">
                  <c:forEach items="${types }" var="type">
                  <option value="${type.name }" <c:if test="${type.name eq xmlBillProperty.fieldType}">selected="selected"</c:if>>${type.text }</option>
                  </c:forEach> 
                </select>
              </td>
              <th>&nbsp;</th>
              <td>
               <button type="button" onclick="getDefInputStyle()">获取样式</button>
              </td>
            </tr>
            <tr>
              <th>
                <span class="red"></span>默认值
              </th>
              <td colspan="3" id="defaultStyle">
                <input type="hidden" name="xmlBillProperty.defaultValue" value="${xmlBillProperty.defaultValue }"/>
              </td>
              
            </tr>
          </tbody>
        </table>
      </div>
    </form>
  </body>
</html>