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
                zj_createGroup($("#form_edit").serialize());
            return false;
        });
    });
    function check(){
        var m = $("#groupname").val();
        var d = $("input[id='model.level']").val();
        if(m==null||m==""){
            alert("组名不能为空");return false;
        }
        if(d==null||d==""){
            alert("审核类型不能为空");return false;
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
                            <span><font color="#0f5dc2" style="font-weight:normal;">增加信息</font></span>
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
                        <th style="width:120px">组名</th>
		                <td>
		                    <input id="groupname" name = "model.name" value = '${model.name }'/>
		                    <input type="hidden" name = "model.id" value = '${model.id }'/>
		                </td>
                    </tr>
	                <tr>
		                <th style="width:120px">专家类型</th>
		                <td>
		                    <ct:codePicker name="model.type" catalog="DM_DEF_ZJLX" code="${model.type}"/>
		                </td>
                    </tr>
                    <tr>
                         <th style="width:120px"><span class="red">*</span>审核级别</th>
		                <td>
		                    <ct:codePicker name="model.level" catalog="DM_SYS_ZJPSJB" code="${model.level}"/>
		                 </td>
                    </tr>
                    <tr >
                        <td colspan="4">
                            <span class="red">组名与审核级别为必填项</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>