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
  <script type="text/javascript" src="<%=systemPath %>/js/salary.js"></script>
  <script type="text/javascript">
  $(function() {
    
    //信息类选择
    $(".mes_list_con ul li").click(function(){
      if($(this).attr("name") == null) {
        return;
      }

      $("#catalogId").val($(this).attr("name"));
      $("form:first").submit();
    });
    
    // 设置
    $("a[name='regular']").click(function() {
      var param = "?model.classId=" + $(this).closest("tr").find("input[name='classId']").val();
      param += "&model.procedureId=" + $(this).closest("tr").find("input[name='procedureId']").val();
      showWindow("设置", "<%=request.getContextPath()%>/basedata/basedata_regular.html" + param, 400, 220);
    });
    
  });
  
  var current = null;
  
  function currentF(name){
    if( name == null || name == "" || name == current ) {
      return false;
    }

    $("li[name='" + current + "']").removeClass("current");
    current = name;
    $("#catalogId").val(name);
    
    $("li[name='" + name + "']").attr("class", "current");
  }
  
  </script>
</head>

<body>
  <form action="<%=request.getContextPath() %>/basedata/basedata_setProcedure.html" method="post">
    <input type="hidden" id="catalogId" name="catalogId" value="${catalogId }" />
  </form>
  
  <div class="selectbox" style="position:relative; z-index: 1;" >
    <ul class="datetitle_01">
      <li class="">基础数据</li>
    </ul>
    <div class="mes_list" id="message_list2" style="display: block;">
      <div class="mes_list_con">
        <h2><a href="#">基础数据</a></h2>
        <ul>
          <c:forEach items="${catalogs}" var="cl">
          <li name="${cl.guid }"><a href="#">${cl.name }</a></li>
          </c:forEach>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
  </div>
  
  <div class="formbox">
    <h3 class="datetitle_01">
      <span>存储过程设置</span>
    </h3>
    <table summary="" class="dateline tablenowrap" align="" width="100%">
      <thead id="list_head">
        <tr>
          <td width="20%">信息类名称</td>
          <td width="20%">存储过程名称</td>
          <td>定时执行周期</td>
          <td>定时开关</td>
          <td width="20%">操作</td>
        </tr>
      </thead>
      <tbody id="list_body">
        <c:forEach items="${procedures }" var="p" varStatus="i">
          <tr name="tr">
            <td width="20px">
              <input type="hidden" name="procedureId" value="${p.procedureId }"/>
              <input type="hidden" name="classId" value="${p.classId }"/>
              ${p.className }
            </td>
            <td>${p.procedureName }</td>
            <td>${p.executeCyc.text }</td>
            <td>
              <c:if test="${p.regularSwitch eq 'on' }">开</c:if>
              <c:if test="${p.regularSwitch eq 'off' }">关</c:if>
            </td>
            <td>
              <a style="color:#074695" name="regular" href="#">设置</a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
  <script type="text/javascript">
    currentF("${catalogId}");
  </script>
</body>
</html>