<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    
<head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript">
        $(function(){
            var current = null;
            operationList();
            fillRows("15", "", "", false);//填充空行
            $("select[name='classId']").val('${classId}');
            $("select[name='type']").val('${type}');
            //requestSnapData();
        });
        
        function operationList(){

            $("a[name='detail']").click(function(){
                var logId = $(this).closest("tr").find("input[id='logId']").val();
                var classId = $("select[name='classId']").val();
                window.location.href=_path+"/baseinfo/dynalog_detail.html?logId="+logId+"&classId="+classId;
            });
            
            $(".select_tools a").css("cursor","pointer");

            operationHover();
        }

        function findResult(id)
        {
        	var classId = "${classId}";
            $("#declareTR_"+id).show();
            function successCall(data){
                if(data==null||data==""){
                    data="未找到满足项";
                }
                $("#declareTD_"+id).html(data);
                $("#declareTD_"+id).find(".toolbox").remove();
                $("#declareTD_"+id).find(".title_xxxx").remove();
            }
            $("#declareTD_"+id).unbind("ajaxStart");
            $.ajax({
                url:_path+"/baseinfo/dynalog_detail.html?logId="+id+"&classId="+classId,
                type:"post",
                data:"word="+id,
                cache:false,
                dataType:"html",
                success:successCall
            });
        }
        function a(id,obj){
            var hrmStr = $(obj).html();
            if(hrmStr == '查看详情'){
                $(obj).html("隐藏详情");
                findResult(id);
            }else{
                $(obj).html("查看详情");
                $("#declareTD_"+id).html("");
                $("#declareTR_"+id).hide();
            }
        }
    </script>
</head>
<body>
<form theme="simple" action="<%=request.getContextPath()%>/normal/staffBatch_showLog.html?classId=${classId}&instanceId=${instanceId}" method="post" id="log">
        <div class="formbox" id="snap_content">
            <h3 class="datetitle_01">
                <span>日志数据<font color="#0457A7" style="font-weight:normal;"></font></span>
            </h3>
            <div style="overflow-x:auto;">
            <table width="100%" class="dateline tablenowrap" id="tiptab" >
                <thead id="list_head">
                    <tr>
                    <td>日志时间</td>
                    <td>类型</td>
                    <td>操作人</td>
                    <td>操作</td>
                    </tr>
                </thead>
                <tbody id="list_body">
                <s:iterator value="logList" var="info">
                <tr>
                <td><s:date name="values['operation_time_']" format="yyyy-MM-dd HH:mm:ss"/><input type="hidden" id="logId" value="${info.values['logid'] }"/></td>
                <td>
                    <s:if test="values['operation_'] == 'add'">新增</s:if>
                    <s:if test="values['operation_'] == 'modify'">修改</s:if>
                    <s:if test="values['operation_'] == 'remove'">删除</s:if>
                </td>
                <td><ct:PersonParse code="${info.values['operator_'] }" />(${info.values['operator_'] })</td>
                <td>
                  <div>
                    <div class="current_item">
                        <span class="item_text" onclick="a('${info.values['logid'] }',this)">查看详情</span>
                    </div>
                  </div>
                </td>
                </tr>
                <tr id="declareTR_${info.values['logid'] }" style="display: none;">
                   <td id="declareTD_${info.values['logid'] }" colspan="4"></td>
                </tr>
                </s:iterator>
                </tbody>
            </table>
            </div>
        <ct:page pageList="${logList }"  />
        </div>
</form>
</body>
</html>
