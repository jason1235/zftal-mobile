<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="code" uri="/WEB-INF/code.tld" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <%@include file="/commons/hrm/head.ini" %>

  <script type="text/javascript">

    $(function() {
      var res = false;
      // 粗体
      $("input[name='ckbold']").click(function () {
        var obj = $(this).closest("td").find("input[name='bold']");
        if (obj.val() == "1") {
          obj.val("0");
        } else {
          obj.val("1");
        }
        refreshImage();
      });
    
      // 斜体
      $("input[name='ckitalic']").click(function () {
        var obj = $(this).closest("td").find("input[name='italic']");
        if (obj.val() == "1") {
          obj.val("0");
        } else {
          obj.val("1");
        }
        refreshImage();
      });
      
      // 下划线
      $("input[name='ckunderline']").click(function () {
        var obj = $(this).closest("td").find("input[name='underline']");
        if (obj.val() == "1") {
          obj.val("0");
        } else {
          obj.val("1");
        }
        refreshImage();
      });
      
      // 字体位置
      $("input[name^='fl']").click(function () {
        $(this).closest("td").find("input[name='fontLocation']").val($(this).closest("td").find("input[name^='fl']:checked").val());
        refreshImage();
      });
      
      // 监听单击行
      $("tbody > tr[name^='tr']").click(function() {
        var current = $(this).attr("class");
        if(current != null && current != '') {
          $(this).removeClass("current");
          $(this).find("input[name='ids']").removeAttr("checked");
        }else{
          $(this).attr("class", "current");
          $(this).find("input[name='ids']").attr("checked","checked");
        }
      });
      
      //信息类监听
      $(".mes_list_con li").hover(function() {
        if( $(this).attr("name") == null ) {
          return;
        }

        var $mes_list_tools = $("#mes_list_tools").html();
        
        res = false;

        $(this).append($mes_list_tools).css("position", "relative").children(".mes_list_tools").show();
        if($(this).attr("xxly") == "system") {
          $(this).find("#btn_scxxl").css("height","0px");
        }

        //信息类修改
        $(".ico_edit_mes").click(function() {
          res = true;
          var guid = $(this).closest("li").attr("name");
          location.href = "<%=request.getContextPath() %>/print/printTemplate_edit.html?model.id=" + guid + "&jumpId=edit";
        });

        //信息类删除
        $(".ico_delete_mes").click(function() {
          var guid = $(this).closest("li").attr("name");

          showConfirm("确定要删除吗？");

          $("#why_cancel").click(function(){
            divClose();
          })
          
          $("#why_sure").click(function() {
            $.post("<%=request.getContextPath() %>/print/printTemplate_remove.html", 'model.id=' + guid, function(data){
                var callback = function(){
                    $("#id").val("");
                    $("form:first").submit();
                };
                
                processDataCall(data, callback);

            }, "json");
          });

          return false;
        });
      }, function() {$(this).find(".mes_list_tools").remove();});
      
      // 添加
      $("#btn_zjxxl").click(function() {
        location.href = "<%=request.getContextPath() %>/print/printTemplate_edit.html?jumpId=add";
      });
      
      //信息类选择
      $(".mes_list_con ul li").click(function(){
        if($(this).attr("name") == null) {
          return;
        }
        if(res) {
          return;
        }
        $("#id").val($(this).attr("name"));
        $("#ywId").val($(this).attr("ywId"));
        $("form:first").submit();
      });
      
      // 增加
      $("#btn_zj").click(function() {
        showWindow("增加", "<%=request.getContextPath()%>/print/printTemplate_editProperty.html?model.id=" + $("#id").val() + "&model.mbbzm=" + $("#ywId").val(), 500, 300);
      });
      
      // 切换项目类型
      $("select[name='xmType']").change(function () {
        // 常量
        if ($(this).val() == "0") {
          $(this).closest("tr").find("select[name='xm']").attr("disabled", "disabled");
          $(this).closest("tr").find("select[name='xm']").hide();
          $(this).closest("tr").find("input[name='xm']").show();
          $(this).closest("tr").find("input[name='xm']").removeAttr("disabled");
          $(this).closest("tr").find("input[name='mark']").hide();
        } else {
          $(this).closest("tr").find("input[name='xm']").attr("disabled", "disabled");
          $(this).closest("tr").find("input[name='xm']").hide();
          $(this).closest("tr").find("select[name='xm']").show();
          $(this).closest("tr").find("select[name='xm']").removeAttr("disabled");
          $(this).closest("tr").find("input[name='mark']").show();
        }
      });
      
      // 刷新画面触发源_内容
      $("input[name='nr']").change(function () {
        refreshImage();
      });
      
      // 刷新画面触发源_横坐标
      $("input[name='xzb']").change(function () {
        // 正整数
        var rep = /^\+?[0-9][0-9]*$/;
        // 横坐标
        if ($.trim($(this).val()).length > 0) {
          if(!rep.test($.trim($(this).val()))){
            alert("横坐标只能输入正整数");
            return false;
          }
        }

        refreshImage();
      });

      // 刷新画面触发源_纵坐标
      $("input[name='yzb']").change(function () {
        // 正整数
        var rep = /^\+?[0-9][0-9]*$/;
        // 纵坐标
        if ($.trim($(this).val()).length > 0) {
          if(!rep.test($.trim($(this).val()))){
            alert("纵坐标只能输入正整数");
            return false;
          }
        }

        refreshImage();
      });
      
      // 刷新画面触发源_项目宽
      $("input[name='xmk']").change(function () {
        // 正整数
        var rep = /^\+?[0-9][0-9]*$/;
        // 项目宽度
        if ($.trim($(this).val()).length > 0) {
          if(!rep.test($.trim($(this).val()))){
            alert("项目宽度只能输入正整数");
            return false;
          }
        }

        refreshImage();
      });
      
      // 刷新画面触发源_项目高
      $("input[name='xmg']").change(function () {
        // 正整数
        var rep = /^\+?[0-9][0-9]*$/;
        // 项目高度
        if ($.trim($(this).val()).length > 0) {
          if(!rep.test($.trim($(this).val()))){
            alert("项目高度只能输入正整数");
            return false;
          }
        }

        refreshImage();
      });
      
      // 刷新画面触发源_字体
      $("select[name='font']").change(function () {
        refreshImage();
      });
      
      // 刷新画面触发源_字体大小
      $("input[name='fontSize']").change(function () {
        // 正整数
        var rep = /^\+?[0-9][0-9]*$/;
        // 字体大小
        if ($.trim($(this).val()).length > 0) {
          if(!rep.test($.trim($(this).val()))){
            alert("字体大小只能输入正整数");
            return false;
          }
        }
        
        refreshImage();
      });
      
      // 保存
      $("#btn_bc").click(function () {
        var error = check();
        if ($.trim(error).length > 0) {
          alert(error);
          return false;
        }
        
        var param = "model.id=" + $("#id").val();
        param += "&" + $("#search").serialize();
        $.post('<%=request.getContextPath()%>/print/printTemplate_saveAll.html',param,function(data) {
          if(data.success) {
            refreshList();
          } else {
            alert(data.text);
          }
        }, "json");
      });
      
      // 删除
      $("#btn_sc").click(function () {
        var checked = $("input[id='ids']:checked");
        if(checked.length == 0){
          alert("请先选中操作行");
          return false;
        }
        
        $("input[id='ids']:checked").each(function () {
          $(this).closest("tr").remove();
        });
        
        var param = "model.id=" + $("#id").val();
        param += "&" + $("#search").serialize();
        $.post('<%=request.getContextPath()%>/print/printTemplate_saveAll.html',param,function(data) {
          if(data.success) {
            refreshList();
          } else {
            alert(data.text);
          }
        }, "json");
      });
      
      // 设置数据源
      $("#btn_szsjy").click(function() {
        location.href = "<%=request.getContextPath() %>/dataorigin/dataorigin_page.html?pgId=pt&ywId=" + $("#ywId").val();
      });

      showBj();
      
      initList();
      
      var obj = null;
      var tr = null;
      var offsetTop = $("#bg").offset().top;
      var offsetLeft = 0;
      var offsetRight = parseInt($("#bg").css("width"));
      var offsetBottom = parseInt($("#bg").css("height")) + offsetTop;
      $("div[name='psp']").live("mousedown", function() {
        obj = $(this);
        $("#list_body tr").each(function (index, domEle) {
          if ($(domEle).attr("id") == ("tr" + obj.attr("id"))) {
            tr = $(domEle);
          }
        });
      });
      
      $("#bg").mousemove(function(e) {
        if (obj != null) {
          var pw = parseInt(obj.css("width"));
          var ph = parseInt(obj.css("height"));
          var mx = e.pageX;
          var my = e.pageY;
          var px;
          var py;
          if (my < offsetTop) {
            py = 0;
          } else if (my > (offsetBottom - ph)) {
            py = offsetBottom - ph - offsetTop;
          } else {
            py = my - offsetTop;
          }
          
          if (mx < offsetLeft) {
            px = 0;
          } else if (mx > (offsetRight - pw)) {
            px = offsetRight - pw;
          } else {
            px = mx;
          }
          
          obj.css("margin-left",  px+ "px");
          obj.css("margin-top", py + "px");
          tr.find("input[name='xzb']").val(px);
          tr.find("input[name='yzb']").val(py);
        }
      });
      
      $("div[name='psp']").live("mouseup", function() {
        obj = null;
        tr = null;
      });
      
      fillRows(15, '', '', false);
    });
    
    function initList() {
      $("#list_body tr").each(function() {
        var type = $(this).find("select[name='xmType']").val();
        
        if (type == "0") {
          $(this).find("select[name='xm']").attr("disabled", "disabled");
          $(this).find("select[name='xm']").hide();
          $(this).find("input[name='xm']").show();
          $(this).find("input[name='xm']").removeAttr("disabled");
          $(this).find("input[name='mark']").hide();
        } else {
          $(this).find("input[name='xm']").attr("disabled", "disabled");
          $(this).find("input[name='xm']").hide();
          $(this).find("select[name='xm']").show();
          $(this).find("select[name='xm']").removeAttr("disabled");
          $(this).find("input[name='mark']").show();
        }
      });
    }
    
    function check() {
      // 正整数
      var rep = /^\+?[0-9][0-9]*$/;
      var rep1 = /^\+?[1-9][0-9]*$/;
      var p = new RegExp("^[a-zA-Z][\\w]*$");
      var zIndex = 0;
      var error = "";
      var cnt = $("#cnt").val();
      
      $("#list_body tr").each(function () {
        zIndex += 1;
        var xmval;
        var mark;
        if (Number(zIndex) <= Number(cnt)) {
          var type = $(this).find("select[name='xmType']").val();
          
          // 常量
          if (type == "1") {
            xmval = $(this).find("select[name='xm']").val();
            mark = $(this).find("input[name='mark']").val();
          } else {
            xmval = $(this).find("input[name='xm']").val();
          }
        
          if ($.trim(xmval).length == 0) {
            error += "第" + zIndex + "行，项目不能为空。";
          }
          
          var res = p.test(xmval);
          if(type != "1" && !res){
            alert("项目只能以字母开头，由字母数字及_组成");
            return false;
          }
          
          if ($.trim(mark).length > 0) {
            if(!rep1.test($.trim($(this).find("input[name='mark']").val()))){
              error += "第" + zIndex + "行，下标只能输入正整数。";
            }
          }
        
          // 横坐标
          if ($.trim($(this).find("input[name='xzb']").val()).length > 0) {
            if(!rep.test($.trim($(this).find("input[name='xzb']").val()))){
              error += "第" + zIndex + "行，横坐标只能输入正整数。";
            }
          }
        
          // 纵坐标
          if ($.trim($(this).find("input[name='yzb']").val()).length > 0) {
            if(!rep.test($.trim($(this).find("input[name='yzb']").val()))){
              error += "第" + zIndex + "行，纵坐标只能输入正整数。";
            }
          }
        
          // 项目宽度
          if ($.trim($(this).find("input[name='xmk']").val()).length > 0) {
            if(!rep.test($.trim($(this).find("input[name='xmk']").val()))){
              error += "第" + zIndex + "行，项目宽度只能输入正整数。";
            }
          }
        
          // 项目高度
          if ($.trim($(this).find("input[name='xmg']").val()).length > 0) {
            if(!rep.test($.trim($(this).find("input[name='xmg']").val()))){
              error += "第" + zIndex + "行，项目高度只能输入正整数。";
            }
          }
        
          // 字体大小
          if ($.trim($(this).find("input[name='fontSize']").val()).length > 0) {
            if(!rep.test($.trim($(this).find("input[name='fontSize']").val()))){
              error += "第" + zIndex + "行，字体大小只能输入正整数。";
            }
          }
        }
      });
      return error;
    }
    
    function showBj() {
      var path = "${template.bjlj}";
      var width = "${template.bjkd}";
      var height = "${template.bjgd}";
      var style = "background:url(" + path + ");";
      style += "width:" + width + "px;";
      style += "height:" + height + "px;";
      $("#bg").attr("style", style);
      
      refreshImage();
      
    }
    
    function refreshImage() {
      var html = "";
      var zIndex = 0;
      var cnt = $("#cnt").val();
      $("#list_body tr").each(function () {
        zIndex += 1;
        if (Number(zIndex) <= Number(cnt)) {
          var zx = "";
          if ($(this).find("input[name='bold']").val() == "1") {
            zx += " bold ";
          }
          if ($(this).find("input[name='italic']").val() == "1") {
            zx += " italic ";
          }
          
          var align = $(this).find("input[name='fontLocation']").val();

          var ds = 'style="border-style:solid;border-width:1px;position:absolute;z-index:' + zIndex + ';';
          ds += "width:" + $(this).find("input[name='xmk']").val() + "px;";
          ds += "height:" + $(this).find("input[name='xmg']").val() + "px;";
          ds += "font:" + zx + $(this).find("input[name='fontSize']").val() + "pt ";
          ds += " '" + $(this).find("select[name='font']").val() + "';";
          ds += "margin-left:" + $(this).find("input[name='xzb']").val() + "px;";
          ds += "margin-top:" + $(this).find("input[name='yzb']").val() + "px;";
          ds += '"';
          html += "<div name='psp' id='" + $(this).find("input[name='xm']").val() + "'" + ds + "align='" + align + "'>";
          if ($(this).find("input[name='underline']").val() == "1") {
            html += "<u>";
          }
          
          html += $(this).find("input[name='nr']").val();
          
          if ($(this).find("input[name='underline']").val() == "1") {
            html += "</u>";
          }
          html += "</div>";
        }
      });
      
      $("#bg").html(html);
    }
    
    // 创建属性
    function save_callback_p(type) {
      $.post(_path + "/print/printTemplate_saveProperty.html", type, function(data) {
        if(data.success) {
          location.href = "<%=request.getContextPath()%>/print/printTemplate_page.html?query.id=" + $("#id").val();
        } else {
          alert(data.text);
        }
      }, "json");
    }

    // 刷新
    function refreshList() {
        $("form:first").submit();
    }
    
    var current = null;
    
    function currentF(name){
      if( name == null || name == "" || name == current ) {
        return false;
      }

      $("li[name='" + current + "']").removeClass("current");
      current = name;
      $("#id").val(name);
      
      $("li[name='" + name + "']").attr("class", "current");
    }
    
    // 全选选择框操作
    function selectAllOrCancel(obj, name) {
      var checks = document.getElementsByName(name);
      var body = document.getElementById("list_body");
      var tr = body.getElementsByTagName("tr");
      if (obj.checked) {
        for ( var i = 0; i < checks.length; i++) {
          tr[i].className='current';
          if (!checks[i].disabled) {
            checks[i].checked = true;
          }
        }
      } else {
        for ( var i = 0; i < checks.length; i++) {
          tr[i].className='';
          checks[i].checked = false;
        }
      }
    }
    
    </script>
</head>

<body>
  <form action="<%=request.getContextPath() %>/print/printTemplate_page.html" method="post">
    <input type="hidden" id="id" name="query.id" value="${model.id}" />
    <input type="hidden" id="ywId" name="query.mbbzm" value="${model.mbbzm}" />
  </form>

  <div id="mes_list_tools">
    <div class="mes_list_tools" style="display:none;">
      <span id="btn_xgxxl" class="ico_edit_mes"></span>
      <span id="btn_scxxl" class="ico_delete_mes"></span>
    </div>
  </div>
  <input type="hidden" id="cnt" value="${fn:length(properties) }" />
  <div class="selectbox" style="position:relative; z-index: 1;" >
    <ul class="datetitle_01">
      <li class="">打印模板管理</li>
    </ul>
    <div class="mes_list" id="message_list2" style="display: block;">
      <div class="mes_list_con">
        <h2><a href="#">打印模板</a></h2>
        <ul>
          <c:forEach items="${templates}" var="template">
          <li name="${template.id }" ywId="${template.mbbzm }"><a href="#">${template.mbmc }</a></li>
          </c:forEach>
          <li class="mes_add"><a id="btn_zjxxl" href="#"><span class="mes_add2" title="添加信息类">+ 添加</span></a></li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
  </div>
  
  <c:if test="${templates != null and fn:length(templates) > 0}">
  <div>
    <div id="bg">
    </div>
    <div class="toolbox" style="z-index:0;">
      <div class="buttonbox">
        <ul>
          <li><a id="btn_zj" href="#" class="btn_zj">增加</a></li>
          <li><a id="btn_bc" href="#" class="btn_dr">保存</a></li>
          <li><a id="btn_sc" href="#" class="btn_sc">删除</a></li>
          <li><a id="btn_szsjy" href="#" class="btn_sz">设置数据源</a></li>
        </ul>
      </div>
    </div>
    <form action="" name="search" id="search" method="post">
      <div class="formbox">
        <h3 class="datetitle_01">
          <span>打印模板设置</span>
        </h3>
        <div class="con_overlfow">
        <table summary="" class="dateline tablenowrap" align="" width="100%">
          <thead id="list_head">
            <tr>
              <td width="4%">
                <input type="checkbox" onclick="selectAllOrCancel(this,'ids');"/>
              </td>
              <td>项目类别</td>
              <td>项目</td>
              <td>预览内容</td>
              <td>坐标</td>
              <td>项目大小</td>
              <td>字体</td>
            </tr>
          </thead>
          <tbody id="list_body">
            <c:forEach items="${properties }" var="bean" varStatus="i">
              <tr name="tr" id="tr${bean.xm }">
                <td>
                  <input type="checkbox" name="ids" id="ids"/>
                </td>
                <td>
                  <select name="xmType">
                    <option value="0" <c:if test="${bean.xmType eq '0' }">selected</c:if>>常量</option>
                    <option value="1" <c:if test="${bean.xmType eq '1' }">selected</c:if>>变量</option>
                  </select>
                </td>
                <td>
                  <select name="xm" style="width:80px;">
                    <c:forEach items="${items}" var="item">
                    <option value="${item.fieldName }" <c:if test="${bean.xm eq item.fieldName}">selected</c:if>>${item.name }</option>
                    </c:forEach>
                  </select>
                  <input type="text" name="xm" value="${bean.xm }" class="text_nor" style="width:100px"/>
                  <input type="text" class="text_nor" name="mark" style="width:20px" value="${bean.mark }" />
                </td>
                <td><input type="text" name="nr" value="${bean.nr }" class="text_nor" style="width:100px"/></td>
                <td>横：&nbsp;<input type="text" name="xzb" value="${bean.xzb }" class="text_nor" style="width:30px"/>&nbsp;纵：&nbsp;<input type="text" name="yzb" value="${bean.yzb }" class="text_nor" style="width:30px"/></td>
                <td>宽：&nbsp;<input type="text" name="xmk" value="${bean.xmk }" class="text_nor" style="width:30px"/>&nbsp;高：&nbsp;<input type="text" name="xmg" value="${bean.xmg }" class="text_nor" style="width:30px"/></td>
                <td>字体：&nbsp;
                  <select name="font" style="width:80px">
                    <c:forEach items="${fonts}" var="f">
                    <option value="${f }" <c:if test="${bean.font eq f }">selected</c:if>>${f }</option>
                    </c:forEach>
                  </select>
                  &nbsp;大小：&nbsp;<input type="text" name="fontSize" value="${bean.fontSize }" class="text_nor" style="width:20px"/>
                  &nbsp;字形：&nbsp;<input type="checkbox" name="ckbold" <c:if test="${bean.bold eq '1' }">checked</c:if>/>粗体
                                   <input type="checkbox" name="ckitalic" <c:if test="${bean.italic eq '1' }">checked</c:if>/>斜体
                                   <input type="checkbox" name="ckunderline" <c:if test="${bean.underline eq '1' }">checked</c:if>/>下划线
                                   <input type="hidden" name="bold" value="${bean.bold }" />
                                   <input type="hidden" name="italic" value="${bean.italic }" />
                                   <input type="hidden" name="underline" value="${bean.underline }" />
                 &nbsp;位置：&nbsp;<input type="radio" name="fl_${i.index }" value="left" <c:if test="${bean.fontLocation eq 'left' }">checked</c:if>/>居左
                                   <input type="radio" name="fl_${i.index }" value="center" <c:if test="${bean.fontLocation eq 'center' }">checked</c:if>/>居中
                                   <input type="radio" name="fl_${i.index }" value="right" <c:if test="${bean.fontLocation eq 'right' }">checked</c:if>/>居右
                                   <input type="hidden" name="fontLocation" value="${bean.fontLocation }" />
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
        </div>
      </div>
    </form>
</div>
</c:if>
  <script type="text/javascript">
    currentF("${model.id}");
  </script>
</body>
</html>