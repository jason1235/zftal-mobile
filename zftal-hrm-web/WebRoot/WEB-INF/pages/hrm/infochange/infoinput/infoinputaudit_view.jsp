<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/bill/billview.js"></script>
    <script type="text/javascript">
    $(function(){
            $("#back").click(function(){//功能条增加按钮
                location.href = _path+"/infochange/inputaudit_page.html";
            });
            load($("#content"),'${spBusiness.billId }',
                    '${infoChange.billInstanceId }','ALL_SEARCH');
            $("#logViewBtn").toggle(
                    function(){
                        $("#billLogs").slideDown();
                    },
                    function(){
                        $("#billLogs").slideUp();
                    }
                );

            $("#dyBeanBtn").toggle(
                    function(){
                        loadDybeanList("classId=${infoChange.classId}&gh=${infoChange.userId}&dyQuery.perPageSize=5");
                    },
                    function(){
                        $("#dyBeanList").html("");
                    }
                );
            
            $("#billLogs").slideUp();
        });
    function openLogView(id){
    	showWindow("查看",_path+"/bill/instance_logView.html?"+
    	    	"spBillConfig.id=${spBusiness.billId }&spBillInstance.id="+id,720,400);
        }
    function loadDybeanList(inputs){
        function successCall(data){
            try{
                $.parseJSON(data);
                alert(data.text);
            }catch(e){
                $("#dyBeanList").html(data);
            }
        }
        $.ajax({
            url:_path+"/infochange/infoinput_beanlist.html",
            type:"post",
            data:inputs,
            cache:false,
            dataType:"html",
            success:successCall
        });
    }
    
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                    </ul>
                    <a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
<div class="formbox">
    <input type="hidden" name="infoChange.id" value="${infoChange.id }"/>
<!--标题start-->
    <h3 class="datetitle_01">
        <span>信息修改审核<font color="#0457A7" style="font-weight:normal;" id="tip"></font></span>
    </h3>
    <div id="content" style="padding-top:20px;">
    </div>
    <div id="content2" >
    <div class="por-rz-tool"><a id="dyBeanBtn" href="#">查看历史数据</a></div>
            <div id="dyBeanList" class="por-rz-con"></div>
    </div>
    <%--<div id="content2" >
            <div class="por-rz">
            <div class="por-rz-tool"><a id="logViewBtn" href="#">查看表单修改日志</a></div>
            <div id="billLogs" style="" class="por-rz-con">
                <table class="por-rz-tab">
                    <tbody>
                <s:if test="billLogs.size()>0">
                <s:iterator value="billLogs" var="billLog">
                    <tr><th style="width: 150px;"><s:date name="modifyDate" format="yyyy-MM-dd HH:mm" /></th><td width="200px">修改人：<ct:PersonParse code="${billLog.operator }"/>(${billLog.operator })</td>
                    <td>修改后表单：<a style="color: blue;" onclick="openLogView('${billLog.id}')">点击查看</a></td></tr>
                </s:iterator>
                </s:if>
                <s:else>
                    <tr><th>-</th><td>暂无</td></tr>
                </s:else>
                    </tbody>
                </table>
            </div>
        </div>
        <form id="form1">
        <s:iterator value="excutedList" var="node">
        <div class="title-nav">
            <h3><span id="yuef">环节：${node.nodeName }</span></h3>
        </div>
        <div class="yuezj-list yuezj-list-cur" style="height:60px;">
            <div style="float:left;width:50%" class="yuezj-list-title">
                <strong>审核人：</strong><ct:PersonParse code="${node.auditorId }"/>
            </div>
            <div style="float:left;width:50%" class="yuezj-list-title">
                <strong>审核时间：</strong><s:date name="auditTime" format="yyyy-MM-dd HH:mm:ss"/>
            </div>
            <div style="float:left;width:50%" class="yuezj-list-title">
                <strong>审核意见：</strong>${node.suggestion }
            </div>
            <div style="float:left;width:50%" class="yuezj-list-title">
                <strong>审核状态：</strong><s:property value="%{@com.zfsoft.workflow.enumobject.WorkNodeStatusEnum@valueOf(status).getText()}"/>
            </div>
        </div>
        </s:iterator>
        <br>
        <div class="por-rz">
            <div class="por-rz-tool"><a id="logButton" href="#">查看审核日志</a></div>
            <div id="logContent" style="" class="por-rz-con">
                <table class="por-rz-tab">
                    <tbody>
                <s:if test="logList.size()>0">
                <s:iterator value="logList" var="log">
                    <tr><th style="width: 150px;"><s:date name="logTime" format="yyyy-MM-dd HH:mm" /></th><td>${log.ocontent }</td></tr>
                </s:iterator>
                </s:if>
                <s:else>
                    <tr><th>-</th><td>暂无</td></tr>
                </s:else>
                    </tbody>
                </table>
            </div>
        </div>
        <br>
        </form>
    </div> --%>
  </body>
</html>
