<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.infochange.action.InfoInputAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/tip/tip.js");
    $(function(){
            $("#btn_jump").click(function(){//
                window.open("<%=InfoInputAction.getPropertiesValue("kyxtlink") %>");
            });
            var current = null;

            var classId = $("#classId").val();
            $("#search").attr("action","<%=request.getContextPath()%>/infochange/infoinput_kylist.html?classId="+classId);

            var perPageSize=$("input[name=dyQuery\\.perPageSize]").val();
            fillRows(perPageSize, "", "", false);//填充空行
    });
    
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <li>
                            <a id="btn_jump" class="btn_up">进入科研系统</a>
                        </li>
                        <!--
                        <li>
                            <a id="btn_ck2" class="btn_ck">流程跟踪</a>
                        </li>
                         -->
                    </ul>
            
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
     <form name="search" id="search" method="post">
        <div class="formbox">
        <!--标题start-->
            <h3 class="datetitle_01">
                <span>${clazz.name }信息查看<font color="red" style="font-weight:normal;">  （提示：此处信息由科研系统提供，只显示审核通过的信息，如需编辑请进入科研系统）</font></span>
                <input type="hidden" id=classId value="${classId }"/>
            </h3>
        <!--标题end-->
            <div class="con_overlfow">
                <table summary="" class="dateline tablenowrap" align="" width="100%">
                    <thead id="list_head">
                        <tr>
                            <td width=2%>序号</td>
                            <c:forEach items="${clazz.viewables}" var="infoProperty">
                            <c:if test="${infoProperty.fieldName =='pzrq'}">
                                <td width=10%>${infoProperty.name}</td>
                            </c:if>
                            <c:if test="${infoProperty.fieldName != 'gh' && infoProperty.fieldType!='PHOTO' && infoProperty.fieldType!='IMAGE' && infoProperty.fieldName!='pzrq'}">
                                <td>${infoProperty.name}</td>
                            </c:if>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody id="list_body">
                        <c:forEach items="${dyBeans}" var="d" varStatus="st">
                            <tr name="tr" id="${d.values['globalid']}">
                                <td>
		                        ${st.index + beginIndex}
		                        </td>
                                <c:forEach items="${clazz.viewables}" var="infoProperty">
                                <c:if test="${infoProperty.fieldName != 'gh' && infoProperty.fieldType!='PHOTO' && infoProperty.fieldType!='IMAGE'}">
                                 <td style="white-space: normal;">${d.viewHtml[infoProperty.fieldName]}</td>
                                </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
             </div>
             <ct:page pageList="${dyBeans }" query="${dyQuery }" queryName="dyQuery"/>
        </div>
      </form>
  </body>
</html>