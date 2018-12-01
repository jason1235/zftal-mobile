<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    
  <script type="text/javascript">
    $(function() {
      // 执行
      $("#execute").click(function() {
        if ($("select[name='model.procedureName']").val() == null || $("select[name='model.procedureName']").val() == "") {
          alert("请选择存储过程");
          return false;
        }
        
        $.ajax({
          url:"<%=request.getContextPath() %>/basedata/basedata_doRegular.html",
          type:"post",
          dataType:"json",
          data:$("#form_edit").serialize(),
          success:function(data) {
            divClose();
            $("form:first").submit();
          }
        });
        return false;
      });
      
      // 取消
      $("#cancel").click(function() {
        divClose();
        return false;
      });
      
      $("input[name='switch']").click(function() {
        $("#regularSwitch").val($(this).val());
      });
      
      if ("${model.regularSwitch}" == "") {
        $("#regularSwitch").val("off");
      }
      
    });
  </script>
</head>

<body>
  <form id="form_edit">
     <input type="hidden" id="classId" name="model.classId" value="${model.classId }">
    <input type="hidden" id="procedureId" name="model.procedureId" value="${model.procedureId }">
    <div class="tab">
      <table width="100%" border="0" class="formlist" cellpadding="0" cellspacing="0">
        <thead>
          <tr>
            <th colspan="2">
              <span>设置</span>
            </th>
          </tr>
        </thead>
        <tfoot>
          <tr>
            <td colspan="2">
              <div class="bz">"<span class="red">*</span>"为必填项</div>
              <div class="btn">
                <button type="button" id="execute">执行</button>
                <button type="button" id="cancel">取消</button>
              </div>
            </td>
          </tr>
        </tfoot>
        <tbody>
          <tr>
            <th width="30%">
              <span class="red">*</span>存储过程
            </th>
            <td width="70%">
              <select name="model.procedureName">
                <c:forEach items="${dbprocedures}" var="dp">
                <option value="${dp }" <c:if test="${model.procedureName eq dp }">selected</c:if>>${dp }</option>
                </c:forEach>
              </select>
            </td>
          </tr>
          <tr>
            <th width="30%">
              <span class="red">*</span>执行周期
            </th>
            <td width="70%">
              <select name="model.executeCyc" style="width:100px;">
                <c:forEach items="${executeCycs}" var="cyc">
                <option value="${cyc.key }" <c:if test="${model.executeCyc eq cyc.key }">selected</c:if>>${cyc.text }</option>
                </c:forEach>
              </select>
            </td>
          </tr>
          <tr>
            <th width="30%">
              <span class="red">*</span>定时开关
            </th>
            <td width="70%">
              <input type="hidden" id="regularSwitch" name="model.regularSwitch" value="${model.regularSwitch }"/>
              <input type="radio" name="switch" value="on" <c:if test="${model.regularSwitch eq 'on' }">checked</c:if>/>开
              <input type="radio" name="switch" value="off" <c:if test="${model.regularSwitch != 'on' }">checked</c:if>/>关
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </form>
</body>
</html>