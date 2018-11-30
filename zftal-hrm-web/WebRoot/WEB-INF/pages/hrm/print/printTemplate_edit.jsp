<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <%@include file="/commons/hrm/head.ini"%>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
  <script type="text/javascript">
    $(function() {
      var jumpId_ = "${jumpId}";
      if (jumpId_ == "edit") {
        $(".btn_xxxx_bj").show();
        $(".btn_xxxx_bc").hide();
        $("#save").closest("ul").hide();
      }
      // 保存
      $("#save").click(function() {
        var err = true;
        $("input[name='mbmc']").each(function() {
          if($(this).val() == "" ) {
            alert("名称不得为空，请重新输入！");
            err = false;
            return;
          }
        });
        
        if (!err) {
          return false;
        }
        
        if( $("#creatIdentityName").val() == "" ) {
          alert("业务名不得为空，请重新输入！");
          return false;
        }
        var p = new RegExp("^[a-zA-Z][\\w]*$");
        var res = p.test($("#creatIdentityName").val());
        if(!res){
          alert("业务名只能以字母开头，由字母数字及_组成");
          return false;
        }

        $.post(_path + "/print/printTemplate_saveTemplate.html", $("#form_edit").serialize(), function(data) {
          if(data.success) {
            location.href = "<%=request.getContextPath()%>/print/printTemplate_page.html";
          } else {
            alert(data.text);
          }
        }, "json");
        return false;
      });
      
      $(".btn_xxxx_sc").live("click", function(){
        $.ajax({
          url:"<%=request.getContextPath()%>/print/printTemplate_remove.html",
          type:"post",
          dataType:"json",
          data:"model.id=" + $(this).closest("div").find("input[name='recordId']").val(),
          success:function(data) {
            var content = '<form id="temp" method="post" action="<%=request.getContextPath() %>/print/printTemplate_edit.html">';
            content += '<input type="hidden" name="model.mbbzm" value="' + $("#creatIdentityName").val() + '"/>';
            content += '<input type="hidden" name="jumpId" value="edit"/>';
            content += '</form>';
            $('body').append(content);
            $('#temp').submit();
            $('#temp').remove();
          }
        });
      });
      
      $(".btn_xxxx_bj").live("click", function(){
        var obj = $(this).closest("div").find("#" + $(this).closest("div").find("input[name='recordId']").val());
        var bjobj = $(this);
        var bcobj = $(this).closest("ul").find(".btn_xxxx_bc");
        var param;
        
        $.ajax({
          url:"<%=request.getContextPath()%>/print/printTemplate_editImage.html",
          type:"post",
          dataType:"json",
          data:"imageId=" + $(this).closest("div").find("input[name='imageId']").val(),
          success:function(data) {
            obj.html(data);
            bjobj.hide();
            bcobj.show();
          }
        });
      });
      
      $(".btn_xxxx_bc").live("click", function(){
        var obj = null;
        var bcobj = $(this);
        var bjobj = $(this).closest("ul").find(".btn_xxxx_bj");
        var isAdd = false;
        var btnobj = $(this).closest("ul");
        var thisDiv = $(this).closest("div");
        var imid;
        
        var param = "&model.mbbzm=" + $("#creatIdentityName").val();
        param += "&model.mbmc=" + $(this).closest("div").find("input[name='mbmc']").val();
        
        if ($(this).closest("div").find("input[name='recordId']").length > 0) {
            param += "&model.id=" + $(this).closest("div").find("input[name='recordId']").val();
            param += "&model.bjid=" + $(this).closest("div").find("input[name='imageId']").val();
            obj = $(this).closest("div").find("#" + $(this).closest("div").find("input[name='recordId']").val());
        } else {
            param += "&model.bjid=" + $(this).closest("div").find("input[name='bjid']").val();
            obj = $(this).closest("div").find("input[name='bjid']").closest("td");
            imid = $(this).closest("div").find("input[name='bjid']").val();
            isAdd = true;
        }
        
        $.ajax({
          url:"<%=request.getContextPath()%>/print/printTemplate_saveImage.html",
          type:"post",
          dataType:"json",
          data:param,
          success:function(data) {
            if (data.error == "yes") {
              alert(data.msg);
            } else {
              obj.html(data.vhtml);
              bcobj.hide();
              if (!isAdd) {
                bjobj.show();
              } else {
                btnobj.append("<li class='btn_xxxx_bj'><a style='cursor: pointer;'>编辑</a></li>");
                var hdnHtml = "<input type= 'hidden' name='recordId' value='" + data.recordId + "'/>";
                hdnHtml += "<input type='hidden' name='imageId' value='" + imid + "'/>";
                thisDiv.append(hdnHtml);
                obj.attr("id", data.recordId);
              }
            }
          }
        });
      });
      
      $("#back").click(function() {
        var content = '<form id="temp" method="post" action="<%=request.getContextPath() %>/print/printTemplate_page.html">';
        content += '</form>';
        $('body').append(content);
        $('#temp').submit();
        $('#temp').remove();
      });
      
      $("#btn_new_add").click(function() {
        $.ajax({
          url:"<%=request.getContextPath()%>/print/printTemplate_addImage.html",
          type:"post",
          dataType:"json",
          data:null,
          success:function(data) {
            var html = "<div>";
            if (jumpId_ == "add") {
                html += "<ul class='btn_xxxx'><li class='btn_xxxx_sc'><a style='cursor: pointer;'>删除</a></li></ul>";
            } else {
                html += "<ul class='btn_xxxx'><li class='btn_xxxx_sc'><a style='cursor: pointer;'>删除</a></li>";
                //html += "<li class='btn_xxxx_bj'><a style='cursor: pointer;'>编辑</a></li>";
                html += "<li class='btn_xxxx_bc'><a style='cursor: pointer;'>保存</a></li></ul>";
            }
            
            html += "<table width='100%' border='0' class='formlist' cellpadding='0' cellspacing='0'><tbody><tr><th><span class='red'>*</span>名称</th><td>";
            html += "<input type='text' class='text_nor' name='mbmc' style='width:200px' value='' maxlength='16'/></td></tr>";
            html += "<tr><th><span class='red'>*</span>背景图片</th><td colspan='3'>" + data + "</td></tr></tbody></table></div>";
            
            $("#bjarea").append(html);
          }
        });
      });
      
    });

  </script>
</head>

<body>
  <div class="formbox">
    <div class="toolbox">
      <div class="buttonbox">
          <ul>
              <li><a id="save" class="btn_zj">保 存</a></li>
          </ul>
          <a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
      </div>
    </div>
  
  
  
  <form id="form_edit">
    <div class="tab">
      <table width="100%" border="0" class="formlist" cellpadding="0" cellspacing="0">
        <tbody>
          <tr>
            <th>
              <span class="red" >*</span>业务名
            </th>
            <td>
              <c:if test="${jumpId eq 'add' }">
              <input type="text" class="text_nor" id="creatIdentityName" name="model.mbbzm" style="width:200px" maxlength="16"
                      style="ime-mode:disabled" title="请输入英文字符或下划线" value="${model.mbbzm }" />
              </c:if>
              <c:if test="${jumpId != 'add' }">
              <input type="text" class="text_nobor" id="creatIdentityName" name="model.mbbzm" style="width:200px" maxlength="16"
                      readonly="readonly" value="${model.mbbzm }" />
              </c:if>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="demo_xxxx">
      <h3 class="college_title"><span class="title_name">背景图片上传</span></h3>
      <div id="bjarea">
        <c:if test="${jumpId != 'edit' }">
        <div>
          <ul class="btn_xxxx">
            <li class="btn_xxxx_sc"><a style="cursor: pointer;">删除</a></li>
          </ul>
          <table width="100%" border="0" class="formlist" cellpadding="0" cellspacing="0">
            <tbody>
              <tr>
                <th>
                  <span class="red">*</span>名称
                </th>
                <td >
                  <input type="text" class="text_nor" name="mbmc" style="width:200px" value=""  maxlength="16"/>
                </td>
              </tr>
              <tr>
                <th>
                  <span class="red">*</span>背景图片
                </th>
                <td colspan="3">
                  ${imageHtml }
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        </c:if>
        <c:if test="${jumpId eq 'edit' }">
          <c:forEach items="${models }" var="m" varStatus="i">
          <div>
            <ul class="btn_xxxx">
              <li class="btn_xxxx_sc"><a style="cursor: pointer;">删除</a></li>
              <li class="btn_xxxx_bj"><a style="cursor: pointer;">编辑</a></li>
              <li class="btn_xxxx_bc"><a style="cursor: pointer;">保存</a></li>
            </ul>
            <input type="hidden" name="recordId" value="${m.id }" />
            <input type="hidden" name="imageId" value="${m.bjid }" />
            <table width="100%" border="0" class="formlist" cellpadding="0" cellspacing="0">
              <tbody>
                <tr>
                  <th>
                    <span class="red">*</span>名称
                  </th>
                  <td >
                    <input type="text" class="text_nor" name="mbmc" style="width:200px" value="${m.mbmc }"  maxlength="16"/>
                  </td>
                </tr>
                <tr>
                  <th>
                    <span class="red">*</span>背景图片
                  </th>
                  <td colspan="3" id="${m.id }">
                    ${m.imageHtml }
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          </c:forEach>
        </c:if>
      </div>
      
      <div class="demo_add_02" style="display:block;">
        <a id="btn_new_add" style="cursor:pointer;">添加</a>
      </div>
      </div>
    </div>
  </form>
  </div>
</body>
</html>