<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    
  <script type="text/javascript">
    $(function() {
      initPage();
      // 粗体
      $("#bold").click(function () {
        if ($(this).attr("checked") == "checked") {
          $("input[name='property.bold']").val("1");
        } else {
          $("input[name='property.bold']").val("0");
        }
      });
    
      // 斜体
      $("#italic").click(function () {
        if ($(this).attr("checked") == "checked") {
          $("input[name='property.italic']").val("1");
        } else {
          $("input[name='property.italic']").val("0");
        }
      });
      
      // 下划线
      $("#underline").click(function () {
        if ($(this).attr("checked") == "checked") {
          $("input[name='property.underline']").val("1");
        } else {
          $("input[name='property.underline']").val("0");
        }
      });
      
      // 字体位置
      $("input[name='fl']").click(function () {
        $("input[name='property.fontLocation']").val($("input[name='fl']:checked").val());
      });
      
      // 切换项目类型
      $("select[name='property.xmType']").change(function () {
        // 常量
        if ($(this).val() == "0") {
          $("select[name='property.xm']").attr("disabled", "disabled");
          $("select[name='property.xm']").hide();
          $("input[name='property.xm']").show();
          $("input[name='property.xm']").removeAttr("disabled");
          $("input[name='property.mark']").attr("disabled", "disabled");
          $("input[name='property.mark']").hide();
        } else {
          $("input[name='property.xm']").attr("disabled", "disabled");
          $("input[name='property.xm']").hide();
          $("select[name='property.xm']").show();
          $("select[name='property.xm']").removeAttr("disabled");
          $("input[name='property.mark']").show();
          $("input[name='property.mark']").removeAttr("disabled");
        }
      });
    
      // 保存
      $("#save").click(function() {
        var xmval;
        var mark;
        var type = $("select[name='property.xmType']").val();
        // 常量
        if (type == "1") {
          xmval = $("select[name='property.xm']").val();
          mark = $("#mark").val();
        } else {
          xmval = $("#xm").val();
        }
        if(xmval == "") {
          alert("项目不得为空，请重新输入！");
          return false;
        }
        
        var p = new RegExp("^[a-zA-Z][\\w]*$");
        var res = p.test(xmval);
        if(type != "1" && !res){
          alert("项目只能以字母开头，由字母数字及_组成");
          return false;
        }
        
        // 正整数
        var rep = /^\+?[0-9][0-9]*$/;
        var rep1 = /^\+?[1-9][0-9]*$/;
        
        // 项目宽度
        if ($.trim(mark).length > 0) {
          if(!rep1.test($.trim(mark))){
            alert("下标只能输入正整数");
            return false;
          }
        }
        
        // 项目宽度
        if ($.trim($("input[name='property.xmk']").val()).length > 0) {
          if(!rep.test($.trim($("input[name='property.xmk']").val()))){
            alert("项目宽度只能输入正整数");
            return false;
          }
        }
        
        // 项目高度
        if ($.trim($("input[name='property.xmg']").val()).length > 0) {
          if(!rep.test($.trim($("input[name='property.xmg']").val()))){
            alert("项目高度只能输入正整数");
            return false;
          }
        }
        
        // 横坐标
        if ($.trim($("input[name='property.xzb']").val()).length > 0) {
          if(!rep.test($.trim($("input[name='property.xzb']").val()))){
            alert("横坐标只能输入正整数");
            return false;
          }
        }
        
        // 纵坐标
        if ($.trim($("input[name='property.yzb']").val()).length > 0) {
          if(!rep.test($.trim($("input[name='property.yzb']").val()))){
            alert("纵坐标只能输入正整数");
            return false;
          }
        }
        
        // 字体大小
        if ($.trim($("input[name='property.fontSize']").val()).length > 0) {
          if(!rep.test($.trim($("input[name='property.fontSize']").val()))){
            alert("字体大小只能输入正整数");
            return false;
          }
        }

        save_callback_p($("#form_edit").serialize());
        return false;
      });
      
      // 取消
      $("#cancel").click(function() {
        divClose();
        return false;
      });
      
    });
    
    function initPage() {
      
      $("select[name='property.xmType']").val("0");
      $("select[name='property.xm']").attr("disabled", "disabled");
      $("select[name='property.xm']").hide();
      $("input[name='property.xm']").show();
      $("input[name='property.xm']").removeAttr("disabled");
      $("input[name='property.mark']").attr("disabled", "disabled");
      $("input[name='property.mark']").hide();
      
      // 粗体
      if ($("#bold").attr("checked") == "checked") {
        $("input[name='property.bold']").val("1");
      } else {
        $("input[name='property.bold']").val("0");
      }
      
      // 斜体
      if ($("#italic").attr("checked") == "checked") {
        $("input[name='property.italic']").val("1");
      } else {
        $("input[name='property.italic']").val("0");
      }
      
      // 下划线
      if ($("#underline").attr("checked") == "checked") {
        $("input[name='property.underline']").val("1");
      } else {
        $("input[name='property.underline']").val("0");
      }
      
      // 字体位置
      $("input[name='property.fontLocation']").val($("input[name='fl']:checked").val());
      
    }

  </script>
</head>

<body>
  <form id="form_edit">
    <input type="hidden" name="model.id" value="${model.id }" />
    <div class="tab">
      <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
        <thead>
          <tr>
            <th colspan="4">
              <span>打印模板属性维护</span>
            </th>
          </tr>
        </thead>
        <tfoot>
          <tr>
            <td colspan="4">
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
              <span class="red">*</span>项目类型
            </th>
            <td colspan="3">
              <select name="property.xmType" style="width:120px;">
                <option value="0">常量</option>
                <option value="1">变量</option>
              </select>
            </td>
          </tr>
          <tr>
            <th>
              <span class="red">*</span>项目(下标)
            </th>
            <td>
              <select name="property.xm" style="width:90px;">
              <c:forEach items="${items}" var="item">
                <option value="${item.fieldName }">${item.name }</option>
              </c:forEach>
              </select>
              <input type="text" class="text_nor" id="xm" name="property.xm" style="width:120px" maxlength="16" value="${property.xm }" />
              <input type="text" class="text_nor" id="mark" name="property.mark" style="width:20px" value="${property.mark }" />
            </td>
            <th>预览内容</th>
            <td>
              <input type="text" class="text_nor" name="property.nr" style="width:120px" maxlength="16" value="${property.nr }" />
            </td>
          </tr>
          <tr>
            <th>项目宽度</th>
            <td>
              <input type="text" class="text_nor" name="property.xmk" style="width:120px" maxlength="16" value="${property.xmk }" />
            </td>
            <th>项目高度</th>
            <td>
              <input type="text" class="text_nor" name="property.xmg" style="width:120px" maxlength="16" value="${property.xmg }" />
            </td>
          </tr>
          <tr>
            <th>横坐标</th>
            <td>
              <input type="text" class="text_nor" name="property.xzb" style="width:120px" maxlength="16" value="${property.xzb }" />
            </td>
            <th>纵坐标</th>
            <td>
              <input type="text" class="text_nor" name="property.yzb" style="width:120px" maxlength="16" value="${property.yzb }" />
            </td>
          </tr>
          <tr>
            <th>字体</th>
            <td>
              <select name="property.font" style="width:120px">
              <c:forEach items="${fonts}" var="f">
                <option value="${f }">${f }</option>
              </c:forEach>
              </select>
            </td>
            <th>字体大小</th>
            <td>
              <input type="text" class="text_nor" name="property.fontSize" style="width:120px" maxlength="16" value="${property.fontSize }" />
            </td>
          </tr>
          <tr>
            <th>字形</th>
            <td>
              <input type="checkbox" id="bold" />粗体
              <input type="checkbox" id="italic" />斜体
              <input type="checkbox" id="underline" />下划线
              <input type="hidden" name="property.bold" value="${property.bold }" />
              <input type="hidden" name="property.italic" value="${property.italic }" />
              <input type="hidden" name="property.underline" value="${property.underline }" />
            </td>
            <th>字体位置</th>
            <td>
              <input type="radio" name="fl" value="left" checked/>居左
              <input type="radio" name="fl" value="center"/>居中
              <input type="radio" name="fl" value="right"/>居右
              <input type="hidden" name="property.fontLocation" value="${property.fontLocation }" />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </form>
</body>
</html>