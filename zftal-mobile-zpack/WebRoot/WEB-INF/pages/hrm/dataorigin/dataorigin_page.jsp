<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini"%>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    
    <script type="text/javascript">
      $(function() {
        // 返回
        $("#back").click(function() {
          var pgId = $("#pgId").val();
          var content = "";
          if (pgId == "pt") {
            content += "<form id='temp' method='post' action='<%=request.getContextPath() %>/print/printTemplate_page.html'>";
            content += "</form>";
          } else if (pgId == "ecs") {
            content += "<form id='temp' method='post' action='<%=request.getContextPath() %>/export/config_page.html'>";
            content += "</form>";
          } else if (pgId == "ypsh") {
            content += "<form id='temp' method='post' action='<%=request.getContextPath() %>/introduce/exportTemplate_list.html'>";
            content += "</form>";
          }
          
          if (content != "") {
            $("body").append(content);
            $('#temp').submit();
            $('#temp').remove();
          }
        });
        
        // 增加数据源
        $("#btn_zjsjy").click(function() {
          showWindow("增加数据源", "<%=request.getContextPath()%>/dataorigin/dataorigin_modify.html?jumpId=add", 400, 300);
        });
        
        // 删除数据源
        $(".btn_scbd").click(function() {
          var xmlId = $("#xmlId").val();
          var classId = $(this).closest(".demo_xxxx").attr("id");
          //删除
          showConfirm("确定要删除吗？");

          $("#why_cancel").click(function() {
            alertDivClose();
          });

          $("#why_sure").click(function() {
            $.post("<%=request.getContextPath() %>/dataorigin/dataorigin_remove.html", "xmlId=" + xmlId + "&dataorigin.id=" + classId,function(data) {
              tipsWindown("提示信息","text:" + data.html, "340", "120", "true", "", "true", "id");
              
              $("#window-sure").click(function() {
                alertDivClose();
                if( data.success ) {
                  window.location.reload();
                }
              });
            }, "json");

            return false;
          });
        });
        
        // 修改数据源
        $(".btn_xgbd").click(function() {
          var xmlId = $("#xmlId").val();
          var classId = $(this).closest(".demo_xxxx").attr("id");
          showWindow("修改数据源","<%=request.getContextPath() %>/dataorigin/dataorigin_modify.html?jumpId=modify&xmlId=" + xmlId + "&dataorigin.id=" + classId, 400, 300);
        });
        
        // 自定义抓取配置
        $(".btn_zqpz").click(function() {
          var xmlId = $("#xmlId").val();
          var classId = $(this).closest(".demo_xxxx").attr("id");
          showWindow("自定义抓取配置","<%=request.getContextPath() %>/dataorigin/dataorigin_userdefined.html?xmlId=" + xmlId + "&dataorigin.id=" + classId, 380, 450);
        });
        
        // 增加属性
        $(".btn_zjsx").click(function() {
          var xmlId = $("#xmlId").val();
          var classId = $(this).closest(".demo_xxxx").attr("id");
          showWindow("增加属性","<%=request.getContextPath() %>/dataorigin/dataorigin_modifyXmlBillProperty.html?xmlId=" + xmlId + "&dataorigin.id=" + classId, 620, 250);
        });
        
        // 选择属性
        $(".btn_sxxz").click(function() {
          var xmlId = $("#xmlId").val();
          var classId = $(this).closest(".demo_xxxx").attr("id");
          showWindow("选择属性","<%=request.getContextPath() %>/dataorigin/dataorigin_choiceproperty.html?xmlId=" + xmlId + "&dataorigin.id=" + classId, 500, 400);
        });
        
        // 下移
        $(".btn_sybd").click(function(event){
          var xmlId = $("#xmlId").val();
          var classId = $(this).closest(".demo_xxxx").attr("id");
          location.href="<%=request.getContextPath() %>/dataorigin/dataorigin_xmlBillClassMoveUp.html?xmlId=" + xmlId + "&dataorigin.id=" + classId;
        });
        
        // 上移
        $(".btn_xybd").click(function(){
          var xmlId = $("#xmlId").val();
          var classId = $(this).closest(".demo_xxxx").attr("id");
          location.href="<%=request.getContextPath() %>/dataorigin/dataorigin_xmlBillClassMoveDown.html?xmlId=" + xmlId + "&dataorigin.id=" + classId;
        });
        
        // 设置
        $(".tab_szcd").hover(function() {
          var classInfoId = $(this).attr("id");
          if (classInfoId != '' && classInfoId != null) {
            $(".btn_list").find(".btn_xg").css("display", "none");
            $(".btn_list").find(".btn_sc").css("display", "none");
            $(".btn_list").css("height", "50px");
          } else {
            $(".btn_list").find(".btn_xg").css("display", "block");
            $(".btn_list").find(".btn_sc").css("display", "block");
            $(".btn_list").css("height","50px");
          }
          var btnlisthtml = $("#btn_list_item").html();
          $(this).append(btnlisthtml);
          var btnlist = $(this).find(".btn_list");
          var xmlId = $("#xmlId").val();
          var classId = $(this).closest(".demo_xxxx").attr("id");
          var propertyId = $(this).closest("th").attr("id");
          var param = "xmlId=" + xmlId + "&dataorigin.id=" + classId + "&xmlBillProperty.id=" + propertyId;
          // 修改属性
          btnlist.find(".btn_xg").click(function() {
            showWindow("修改属性", "<%=request.getContextPath() %>/dataorigin/dataorigin_modifyXmlBillProperty.html?" + param, 620, 200);
          });
          // 删除属性
          btnlist.find(".btn_sc").click(function() {
            showConfirm("确定要删除吗？");

            $("#why_cancel").click(function() {
              alertDivClose();
            });

            $("#why_sure").click(function() {
              $.post("<%=request.getContextPath() %>/dataorigin/dataorigin_removeXmlBillProperty.html", param, function(data) {
                tipsWindown("提示信息","text:" + data.html, "340", "120", "true", "", "true", "id");
                $("#window-sure").click(function() {
                  alertDivClose();
                  
                  if( data.success ) {
                    window.location.reload();
                  }
                });
              }, "json");

              return false;
            });
          });
          $(this).find(".btn_list").css("display","block");
        }, function() {$(this).find(".btn_list").remove();
        });
      });
    </script>
  </head>
  <body>
    <input type="hidden" id="ywId" name="ywId" value="${ywId }"/>
    <input type="hidden" id="pgId" name="pgId" value="${pgId }"/>
    <div id="btn_list_item">
      <div class="btn_list" style="display:none; width:50px;height:50px;">
        <ul>
          <li><a href="#" class="btn_xg">修改</a></li>
          <li><a href="#" class="btn_sc">删除</a></li>
        </ul>
      </div>
    </div>
    <div class="toolbox">
      <!-- 按钮 -->
      <div class="buttonbox">
        <ul>
          <li><a id="btn_zjsjy" href="#" class="btn_zj">增加数据源</a></li>
        </ul>
        <a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返回</a>
      </div>
      <p class="toolbox_fot">
        <em></em>
      </p>
    </div>
    <div id="infoContent">
      <c:if test="${xmlBean eq null }">
        <div class="page_prompt">
          <div class="page_promptcon">
            <h3>温馨提示：</h3>
            <p><font color="#0f5dc2">还没有配置数据源</font></p>
          </div>
          <p>&nbsp;</p>
        </div>
      </c:if>
      <c:if test="${xmlBean != null }">
        <input id="xmlId" type="hidden" name="xmlId" value="${xmlId }"/>
        <c:forEach items="${xmlBean }" var="xmlBean">
          <div class="demo_xxxx" id="${xmlBean.id }">
            <h3 class="college_title" style="cursor: pointer;">
              <a id="btn_scbd" class="btn_scbd" href="#">删除数据源</a>
              <a id="btn_xgbd" class="btn_xgbd" href="#">修改数据源</a>
              <a id="btn_xybd" class="btn_xybd" href="#">下移</a>
              <a id="btn_sybd" class="btn_sybd" href="#">上移</a>
              <span class="title_name">${xmlBean.name }
              <c:if test="${xmlBean.originType eq 'teacher'}">(人员信息类)</c:if>
              <c:if test="${xmlBean.originType eq 'business'}">(业务信息类)</c:if>
              <c:if test="${xmlBean.originType eq 'bill'}">(表单)</c:if>
              <c:if test="${xmlBean.originType eq 'workflow'}">(工作流)</c:if>
              </span>
              <c:if test="${xmlBean.originType eq 'teacher' or xmlBean.originType eq 'business'}">
              <a id="btn_sxxz" class="btn_sxxz" style="cursor: pointer;" href="#">选择属性</a>
              </c:if>
              <c:if test="${xmlBean.originType eq ''}">
              <a id="btn_zqpz" class="btn_zqpz" style="cursor: pointer;" href="#">自定义抓取配置</a>
              <a id="btn_zjsx" class="btn_zjsx" style="cursor: pointer;" href="#">增加属性</a>
              </c:if>
            </h3>
            <table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
              <tbody>
                <c:set var="comRow" value="0"/>
                <c:set var="cIndex" value="0"/>
                <c:set var="colNum" value="2"/>
                <c:forEach items="${xmlBean.billPropertys }" step="${colNum }">
                <c:set var="rIndex" value="0"/>
                <c:if test="${fn:length(xmlBean.billPropertys) > cIndex}">
                  <tr>
                    <c:forEach items="${xmlBean.billPropertys }" begin="${cIndex }" end="${cIndex + colNum - 1}" var="p">
                      <th id="${p.id }" style="text-align: right;">
                        <c:if test="${xmlBean.classId != null and xmlBean.classId != ''}">
                        <a class="ico_sz" href="#">${p.name }</a>
                        </c:if>
                        <c:if test="${xmlBean.classId eq null or xmlBean.classId eq ''}">
                        <div id="${xmlBean.classId}" class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
                          <a class="ico_sz" href="#">${p.name }</a>
                        </div>
                        </c:if>
                      </th>
                      <td>#######</td>
                      <c:set var="rIndex" value="${rIndex + 1 }"/>
                    </c:forEach>
                    <!--填充空行-->
                    <c:if test="${rIndex < colNum}">
                      <c:forEach begin="${rIndex + 1 }" end="${colNum }" >
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                      </c:forEach>
                    </c:if>
                  </tr>
                </c:if>
                <c:set var="cIndex" value="${cIndex + colNum}"/>
                <c:set var="comRow" value="${comRow + 1}"/>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:forEach>
      </c:if>
    </div>
  </body>
</html>
