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
         var perPageSize=$("input[name=dyQuery\\.perPageSize]").val();
         fillRows(perPageSize, "", "", false);//填充空行
         $("#btn_zj").click(function(){
        	 createDeclare();
         });
    });

    function createDeclare(){
        $.post(_path+"/bnsinfo/infochange_create.html?classId=${classId}","",function(data){
            if(data.success){
                location.href=_path+"/bnsinfo/infochange_detail.html?classId=${classId}&businessInfoChange.id="+data.businessInfoChange.id;
            }else{
                alert(data.text);
            }
        },"json");
    }

    </script>
  </head>
  <body>
    <div class="toolbox">
	    <div class="buttonbox">
              <ul>
                <c:if test="${hasBusiness}">
                  <li>
                      <a id="btn_zj" class="btn_zj">新增申请</a>
                  </li>
                  </c:if>
              </ul>
         </div>
         <p class="toolbox_fot">
                <em></em>
            </p>
    </div>
     <form name="search" id="search" method="post" onsubmit="query();return false;" >
        <div class="formbox">
        <!--标题start-->
            <h3 class="datetitle_01">
                <span>${clazz.name }<font color="red" style="font-weight:normal;">（提示：只显示审核通过的成果信息）</font></span>
                <input type="hidden" name=classId value="${classId }"/>
                <input type="hidden" name=gh value="${gh }"/>
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