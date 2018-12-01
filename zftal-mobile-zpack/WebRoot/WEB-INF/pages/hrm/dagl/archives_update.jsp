<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
        var caches = {};
        
        $("#save_btn").click(function(){
            
            if(!check()){
                 return false;
            }
            var param=$("#form_edit").serialize();
            $.post("<%=request.getContextPath()%>/dagl/archives_updateArchives.html",
                $("#form_edit").serialize(),function(data){
                    var callback = function(){
                        reload();
                    };
                    if(data.success){
                        processDataCall(data, callback);
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
            divClose();
        });
    });

    function check()
    {
    	var bh=$("input[name='archives.bh']").val();
        if(bh==null || bh==''){
            showWarning("档案编号不能为空");
            return false;
        }
        var type=$("input[name='archives.savePoint']").val();
        if(type==null || type==''){
            showWarning("存放位置不能为空");
            return false;
        }
        var type=$("input[name='archives.changeStatusTime']").val();
        if(type==null || type==''){
            showWarning("修改时间不能为空");
            return false;
        }
        var type=$("textarea[name='dataMessage']").val();
        if(type==null || type==''){
            showWarning("修改说明不能为空");
            return false;
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
                            <span><font color="#0f5dc2" style="font-weight:normal;">档案信息</font></span>
                            <input type="hidden" name ="archives.id" value="${archives.id }"/>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn" type="button">保 存</button>
                                <button id="cancel" type="button">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <th>
                            <span class="red"></span>职工号
                        </th>
                        <td>
                            ${archives.gh }
                        </td>
                        <th>
                            <span class="red"></span>姓名
                        </th>
                        <td>
                            ${archives.xm }
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>档案编号
                        </th>
                        <td>
                            <input name="archives.bh" value="${archives.bh }" />
                        </td>
                        <th>
                            <span class="red"></span>档案描述
                        </th>
                        <td>
                            <input name="archives.detail" value="${archives.detail }" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>存放位置
                        </th>
                        <td>
                            <input name="archives.savePoint" value="${archives.savePoint }" />
                        </td>
                        <th>
                            <span class="red">*</span>修改时间
                        </th>
                        <td>
                            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="archives.changeStatusTime" 
                            value="<s:date name='nowTime' format='yyyy-MM-dd'/>" />
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>修改说明<br/> </th>
                        <td colspan="3">
                            <textarea name="dataMessage" cols="50"></textarea>
                        </td>
                    </tr>
                </tbody>
                </table>
        </div>
    </form>
</body>
</html>