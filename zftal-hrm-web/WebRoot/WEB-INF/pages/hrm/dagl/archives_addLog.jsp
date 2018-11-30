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
        
        $("#save_btn").click(function(){
           if("${status}"=="SAVE"){
        	   insertLogData();
               }
           else{
        	   showConfirm("你确定要${status.text}该档案？");
	           $("#why_cancel").click(function(){
	                $("#windown-close").click();
	               });
	           $("#why_sure").click(function(){
	        	   insertLogData();
	               return false;
	           });
           }
        });
        
        $("#cancel").click(function(){
            divClose();
        });
    });

    function insertLogData(){
    	 if(!check()){
             return false;
        }
        var param=$("#form_edit").serialize();
        $.post("<%=request.getContextPath()%>/dagl/archives_change.html",
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
    }

    function check()
    {
        var statusTime=$("input[name='archives.changeStatusTime']").val();
        if(statusTime==null || statusTime==''){
            showWarning("操作时间不能为空");
            return false;
        }
        var message=$("textarea[name='dataMessage']").val();
        if(message==null || message==''){
            showWarning("操作说明不能为空");
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
                            <input type="hidden" name ="archives.status" value="${status }"/>
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
                        <th width="20%">
                            <span class="red"></span>职工号
                        </th>
                        <td width="30%">
                            ${archives.gh }
                        </td>
                        <th width="20%">
                            <span class="red"></span>姓名
                        </th>
                        <td width="30%">
                            ${archives.xm }
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>档案编号
                        </th>
                        <td>
                            ${archives.bh }
                        </td>
                        <th>
                            <span class="red"></span>档案描述
                        </th>
                        <td>
                            ${archives.detail }
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>当前状态
                        </th>
                        <td>
                           ${archives.statusText }
                        </td>
                        <th>
                            <span class="red"></span>存放位置
                        </th>
                        <td>
                           ${archives.savePoint }
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>操作时间
                        </th>
                        <td>
                            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="archives.changeStatusTime" 
                            value="<s:date name='nowTime' format='yyyy-MM-dd'/>"/>
                        </td>
                        <th></th><td></td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>操作说明<br/> </th>
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