<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

  <head>
    <script type="text/javascript">
      $(function(){
        $("#cancel").click(function() {
          divClose();
          return false;
        });
        
        $("#save").click(function() {
          if( $("#name").val() == "" ) {
            alert("名称不得为空，请重新输入！");
            return false;
          }
          if( $("#identityName").val() == "" ) {
            alert("标识名不得为空，请重新输入！");
            return false;
          }
          
          var p = new RegExp("^[a-zA-Z][\\w]*$");
          var res = p.test($("#identityName").val());
          if(!res){
            alert("标识名只能以字母开头，由字母数字及_组成");
            return false;
          }
          
          var recordCnt = $("#recordCnt").val();
          if ($.trim(recordCnt).length == 0) {
            alert("记录数不能为空");
            return false;
          }
          
          var rep1 = /^\+?[1-9][0-9]*$/; //正整数
          if(!rep1.test(recordCnt)){
            alert("记录数只能输入正整数");
            return false;
          }
          
          if(Number(recordCnt) < 1){
            alert("记录数至少1条");
            return false;
          }
          
          $.post("<%=request.getContextPath() %>/dataorigin/dataorigin_saveXml.html", $("#form_bill").serialize() + "&ywId=" + $("#ywId").val(), function(data){
            var callback = function(){
              location.href="<%=request.getContextPath() %>/dataorigin/dataorigin_page.html?ywId=" + $("#ywId").val();
            };
            processDataCall(data, callback);
          }, "json");
          return false;
        });
        var jumpId_ = "${jumpId}";
        
        if (jumpId_ == "add") {
          $("#version").closest("tr").hide();
        }
        
        $("#originType").change(function() {
          var selType = $(this).children("option:selected").val();
          if(selType != "" && selType != "workflow") {
            $("input[name='dataorigin.identityName']").attr("readonly", "readonly");
            $.post("<%=request.getContextPath() %>/dataorigin/dataorigin_getInfoClassList.html", "dataorigin.originType=" + selType,
              function(data){
                if(data.message.success) {
                  var html = '';
                  var htmlv = '';
                  for (var i = 0; i < data.classList.length; i++) {
                    if (selType == "teacher" || selType == "business") {
                      html += "<option value='" + data.classList[i].guid + "'>" + data.classList[i].name + "</option>";
                    } else if (selType == "bill") {
                      html += "<option value='" + data.classList[i].id + "'>" + data.classList[i].name + "</option>";
                    }
                  }
                  for (var j = 0; j < data.versionList.length; j++) {
                    htmlv += "<option value='" + data.versionList[j] + "'>" + data.versionList[j] + "</option>";
                  }
                  htmlv += "<option value='0'>即时</option>"
                  $("#classSelect").html(html);
                  if (selType == "teacher" || selType == "business") {
                    $("#version").closest("tr").hide();
                    $("input[name='dataorigin.name']").val(data.classList[0].name);
                    $("input[name='dataorigin.identityName']").val(data.classList[0].identityName);
                  } else if (selType == "bill") {
                    $("#version").closest("tr").show();
                    $("#version").html(htmlv);
                    $("input[name='dataorigin.name']").val(data.classList[0].name);
                    $("input[name='dataorigin.identityName']").removeAttr("readonly");
                  $("input[name='dataorigin.identityName']").val("");
                  }
                }
              }, "json");
          } else {
            $("#classSelect").html("<option value=''>--无--</option>");
            $("input[name='dataorigin.name']").removeAttr("readonly");
            $("input[name='dataorigin.identityName']").removeAttr("readonly");
            $("input[name='dataorigin.name']").val("");
            $("input[name='dataorigin.identityName']").val("");
            $("#version").closest("tr").hide();
          }
        });
        
        $("#classSelect").change(function() {
          var selType = $("#originType").children("option:selected").val();
          if ($(this).children("option:selected").val() != "") {
            $("input[name='dataorigin.identityName']").attr("readonly","readonly");
            $.post("<%=request.getContextPath() %>/dataorigin/dataorigin_getInfoClass.html", "dataorigin.originType=" + selType + "&dataorigin.classId=" + $(this).children("option:selected").val(),
            function(data) {
              if(data.message.success){
                var infoClass = data.infoClass;
                if (selType == "teacher" || selType == "business") {
                  $("input[name='dataorigin.name']").val(infoClass.name);
                  $("input[name='dataorigin.identityName']").val(infoClass.identityName);
                } else if (selType == "bill") {
                  var html = ""
                  for (var j = 0; j < data.versionList.length; j++) {
                    html += "<option value='" + data.versionList[j] + "'>" + data.versionList[j] + "</option>";
                  }
                  html += "<option value='0'>即时</option>";
                  $("#version").html(html);
                  $("input[name='dataorigin.name']").val(infoClass.name);
                  $("input[name='dataorigin.identityName']").removeAttr("readonly");
                  $("input[name='dataorigin.identityName']").val("");
                }

              }
            }, "json");
          } else {
            $("input[name='dataorigin.name']").removeAttr("readonly");
            $("input[name='dataorigin.identityName']").removeAttr("readonly");
            $("input[name='dataorigin.name']").val("");
            $("input[name='dataorigin.identityName']").val("");
          }
        });
      });
    </script>
  </head>

  <body>
    <form id="form_bill">
      <input type="hidden" name="jumpId" value="${jumpId }" />
      <div class="tab">
        <table width="100%" border="0" class="formlist" cellpadding="0" cellspacing="0">
          <thead>
            <tr>
              <th colspan="2">
                <span>数据源维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
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
          <tbody id="tbody_bill">
            <c:if test="${jumpId eq 'add'}">
            <tr>
              <th>
                <span class="red">*</span>信息来源
              </th>
              <td>
                <select id="originType" name="dataorigin.originType" style="width:206px">
                  <option value="">--无--</option>
                  <option value="teacher">人员信息类</option>
                  <option value="business">业务信息类</option>
                  <option value="bill">表单</option>
                  <option value="workflow">工作流</option>
                </select>
              </td>
            </tr>
            <tr>
              <th>
                <span class="red">*</span>具体信息类
              </th>
              <td>
                <select id="classSelect" name="dataorigin.classId" style="width:206px">
                  <option value="">--无--</option>
                </select>
              </td>
            </tr>
            </c:if>
            <tr>
              <th>
                <span class="red">*</span>名称
              </th>
              <td>
                <input type="text" class="text_nor" id="name" name="dataorigin.name" style="width:200px" value="${dataorigin.name }"  maxlength="16"/>
              </td>
            </tr>
            <tr>
              <th>
                <span class="red">*</span>标识名
              </th>
              <td>
                <input type="text" class="text_nor" id="identityName" name="dataorigin.identityName" style="width:200px" maxlength="16"
                <c:if test="${jumpId eq 'modify'}"> readonly="readonly"</c:if> value="${dataorigin.identityName }" />
              </td>
            </tr>
            <c:if test="${jumpId eq 'modify'}">
              <input type="hidden" name="dataorigin.id" value="${dataorigin.id }" />
              <input type="hidden" name="dataorigin.classId" value="${dataorigin.classId }" />
              <input type="hidden" name="dataorigin.originType" value="${dataorigin.originType }" />
            </c:if>
            <tr>
              <th>
                <span class="red">*</span>版本号
              </th>
              <td>
                <select id="version" name="dataorigin.version" style="width:206px">
                  <c:forEach items="${versionList }" var="version">
                  <option value="${version }" <c:if test="${version eq dataorigin.version }" >selected="selected"</c:if>>
                  ${version }</option>
                  </c:forEach>
                  <option value="0" <c:if test="${'0' eq dataorigin.version }" >selected="selected"</c:if>>即时</option>
                </select>
              </td>
            </tr>
            <tr>
              <th>
                <span class="red">*</span>记录数
              </th>
              <td>
                <input type="text" class="text_nor" id="recordCnt" name="dataorigin.recordCnt" style="width:200px" value="${dataorigin.recordCnt }" />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </form>
  </body>
</html>