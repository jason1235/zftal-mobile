<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/expertvote/audit.js"></script>
    <script type="text/javascript">
    $(function(){
            loadView();
        });
    
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
<div class="formbox">
    <input type="hidden" name="spBillConfig.id" value="${declare.configId }"/>
    <input type="hidden" name="spBillInstance.id" value="${declare.instanceId }"/>
    <input type="hidden" name="privilegeExpression" value="ALL_SEARCH"/>
    <input type="hidden" name="declare.id" value="${declare.id }"/>
<!--标题start-->
    <h3 class="datetitle_01">
        <span>申报信息<font color="#0457A7" style="font-weight:normal;" id="tip"></font></span>
    </h3>
    <div id="content" style="padding-top:20px;">
    </div>
    
  </body>
</html>
