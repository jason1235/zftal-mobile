<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
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
    <script type="text/javascript">
    $(function(){
        $("#save_btn").click(function(){
            if(check())
            	zj_createTask($("#form_edit").serialize());
            return false;
        });
    });
    function check(){
        var m = $("#taskname").val();
        var d = $("input[id='model.level']").val();
        if(m==null||m==""){
            alert("任务名称不能为空");return false;
        }
        if(d==null||d==""){
            alert("审核类型不能为空");return false;
        }
        var p = $("#passPoint").val();
        if(p==null||p==""){
            alert("投票比例不能为空");return false;
        }
        var reg = new RegExp("^[1-9]?[0-9]$");
        if(!reg.test(p)){
            alert("投票比例必须是1-100之间的整数");return false;
        }
        return true;
    }
    </script>
</head>
<body>
    <form id="form_edit">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">任务信息</font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn" type="button">保 存</button>
                                <button id="cancel">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <th style="width:120px"><span class="red">*</span>任务名</th>
                        <td>
                            <input id="taskname" name = "model.name" value = '${model.name }'/>
                            <input type="hidden" name = "model.id" value = '${model.id }'/>
                        </td>
                    </tr>
                    <tr>
	                    <th>
	                       <span class="red"></span>业务模块
                        </th>
                        <td>
                            <c:if test="${model.id == null ||model.id == '' }">
                            <select name="model.belongToSys">
                                <c:forEach items="${belongToSyses}" var="belongToSys">
                                    <option value="${belongToSys.gnmkdm }" >${belongToSys.gnmkmc }</option>
                                </c:forEach>
                            </select>
                            </c:if>
                            <c:if test="${model.id != null && model.id != '' }">
                                ${model.belongToSysName }
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                         <th style="width:120px"><span class="red">*</span>审核级别</th>
                        <td>
                            <ct:codePicker name="model.level" catalog="DM_SYS_ZJPSJB" code="${model.level}"/>
                         </td>
                    </tr>
                    
                    <tr>
                         <th style="width:120px"><span class="red">*</span>投票比例(1-100)</th>
                        <td>
                            <input id="passPoint" name="model.passPoint" value="${model.passPoint }" />
                         </td>
                    </tr>
                    <tr>
                         <th style="width:120px"><span class="red">*</span>投票人数计算方式</th>
                        <td>
                            <select name="model.pointType" value="${model.pointType }">
                                <option value="0">进一法</option>
                            </select>
                         </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>