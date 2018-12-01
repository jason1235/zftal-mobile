<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/select.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/bill/fieldThink.js"></script>
    <script type="text/javascript">
    $("button[name='push']").click(function(e){
        var form = $("#data_pushInfo");
        form.attr("action",_path+"/baseinfo/pushInfo_${btn_action}.html");
        //form.attr("target","frame");
        //alert(form.attr("enctype"));
        form.submit();
        e.preventDefault();//阻止默认的按钮事件，防止多次请求
        startProcess();
    });
    function startProcess(){
        divClose();
        showProgress();
        requestProgress();
        //showWindow("导入信息提示", "<%=request.getContextPath()%>/baseinfo/infoClassData_info.html", 600, 400 );
    }
    $(function(){
        thinkField("tableField",$("#tableName"));
    });
   
    </script>
</head>

<body>
    <form id="data_pushInfo" enctype="multipart/form-data" method="post" target="frame">
        <input type="hidden" name="config.id" value="${config.id }" />
        <div style="overflow-y: auto;height: 200px">
        <table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
        <thead>
            <th colspan="2">
             <span class="title_name">属性映射关系</span>
            </th>
            <tr>
                <th> 属性</th>
                <th> 对应字段</th>
            </tr>
        </thead>
        <tbody>
                     <tr>
                            <th style="text-align: right;">
                                <div class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
                                    工号
                                </div>
                                <input name="defineCatch.catchFields[0].billProperty.fieldName" value="gh" type = "hidden"/>
                            </th>
                            <td>
                               <input class="tableField" name="defineCatch.catchFields[0].fieldName" value="${field.fieldName }" />
                            </td>
                     <tr>
                       <tr>
                            <th style="text-align: right;">
                                <div class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
                                    表单实例ID
                                </div>
                                <input name="defineCatch.catchFields[1].billProperty.fieldName" value="instanceId" type = "hidden"/>
                            </th>
                            <td>
                               <input class="tableField" name="defineCatch.catchFields[1].fieldName" value="${field.fieldName }" />
                            </td>
                     <tr>
         </tbody>
         </table>
         </div>
         <div>
        <table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
        <thead>
            <th colspan="2">
             <span class="title_name">其他配置</span>
            </th>
        </thead>
        <tbody>
            <tr>
                <th>
                                   <span class="red">*</span>数据来源（表名）
                </th>
                <td>
                <input id="tableName" name="defineCatch.tableName" value="${defineCatch.tableName }" />
                </td>
            </tr>
           <tr>
                 <th>
                                        <span class="red">*</span>筛选条件
                </th>
                <td>
                    <textarea rows="3" cols="30" name="defineCatch.expression">${defineCatch.expression }</textarea>
                </td>
            </tr>
            </tbody>
            <tfoot>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save" name="push">执 行</button>
                                <button id="cancel" type="button" onclick="divClose();">取 消</button>
                            </div>
                        </td>
            </tfoot>
        </table>
        </div>
    </form>
    <iframe name="frame" src="about:blank" style="display:none"></iframe>
</body>
</html>