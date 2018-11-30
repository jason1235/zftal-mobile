<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

  <head>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/select.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/bill/fieldThink.js"></script>
    <style>
      .ui-autocomplete{
        z-index:12001;
        width: 500px
      }
    </style>
    <script type="text/javascript">
      $(function(){
        $("#save").click(function(){
          $("#windown-content").unbind("ajaxStart");
          
          $.post("<%=request.getContextPath() %>/dataorigin/dataorigin_saveDefineCatch.html", $("#form2").serialize() + "&ywId=" + $("#ywId").val(), function(data){
              var callback = function(){
                location.href = "<%=request.getContextPath() %>/dataorigin/dataorigin_page.html?ywId=" + $("#ywId").val();
              };
              processDataCall(data, callback);
          }, "json");
          
          return false;
        });
          
        $("#cancel").click(function(){
          divClose();
        });

        thinkField("tableField",$("#tableName"));
      });
      
    </script>
  </head>

  <body>
    <form id="form2">
      <input type="hidden" name="xmlId" value="${xmlId }" />
      <input type="hidden" name="dataorigin.id" value="${dataorigin.id }" />
      <div style="overflow-y: auto;height: 200px">
        <table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
          <thead>
            <th colspan="2">
              <span class="title_name">属性映射关系</span>
            </th>
            <tr>
              <th> 表单属性</th>
              <th> 对应字段</th>
            </tr>
          </thead>
          <tbody>
          <c:forEach items="${dataorigin.defineCatch.catchFields }" var="field" varStatus="st">
            <tr>
              <th id="${field.billProperty.id }" style="text-align: right;">
                <div id="${billClass.classId}" class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
                  ${field.billProperty.name }
                </div>
                <input name="dataorigin.defineCatch.catchFields[${st.index }].billPropertyId" value="${field.billPropertyId}" type = "hidden"/>
              </th>
              <td>
                <input class="tableField" name="dataorigin.defineCatch.catchFields[${st.index }].fieldName" value="${field.fieldName }" />
              </td>
            <tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
      <div>
        <table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
          <thead>
            <th colspan="2">
              <span class="title_name">其他配置</span>
            </th>
          </thead>
          <tbody>
            <tr>
              <th>数据来源（表名）</th>
              <td>
                <input id="tableName" name="dataorigin.defineCatch.tableName" value="${dataorigin.defineCatch.tableName }" />
              </td>
            </tr>
            <tr>
              <th>抓取条件</th>
              <td>
                <textarea rows="3" cols="30" name="dataorigin.defineCatch.expression">${dataorigin.defineCatch.expression }</textarea>
              </td>
            </tr>
          </tbody>
          <tfoot>
            <td colspan="2">
              <div class="btn">
                <button id="save" type="button">保 存</button>
                <button id="cancel" type="button">取 消</button>
              </div>
            </td>
          </tfoot>
        </table>
      </div>
    </form>
  </body>
</html>