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
        	if(!check()){
                return false;
           }
           var param=$("#form_edit").serialize();
           $.post("<%=request.getContextPath()%>/dagl/archives_addArchiveItem.html",
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
        });
        
        $("#cancel").click(function(){
            divClose();
        });
    });

    function check()
    {
        var name=$("input[name='archiveItem.name']").val();
        if(name==null || name==''){
            showWarning("入档时间不能为空");
            return false;
        }
        var createTime=$("input[name='archiveItem.createTime']").val();
        if(createTime==null || createTime==''){
            showWarning("入档时间不能为空");
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
                            <span><font color="#0f5dc2" style="font-weight:normal;">材料信息</font></span>
                            <input type="hidden" name ="archiveItem.archiveId" value="${archives.id }"/>
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
                            <span class="red"></span>归属档案编号
                        </th>
                        <td>
                            ${archives.bh }
                        </td>
                        <th>
                            <span class="red"></span>归属档案
                        </th>
                        <td>
                            ${archives.detail }
                        </td>
                    </tr>
                    <tr>
                        
                        <th>
                            <span class="red">*</span>材料名称
                        </th>
                        <td>
                           <input name="archiveItem.name"/>
                        </td>
                        <th>
                            <span class="red">*</span>制成时间
                        </th>
                        <td>
                            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="archiveItem.createTime" 
                            value="<s:date name='nowTime' format='yyyy-MM-dd'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>材料编号
                        </th>
                        <td>
                           <input name="archiveItem.bh"/>
                        </td>
                        <th>
                            <span class="red"></span>材料描述
                        </th>
                        <td>
                           <input name="archiveItem.desc"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>入档说明<br/> </th>
                        <td colspan="3">
                            <textarea name="dataMessage" cols="50"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>类号
                        </th>
                        <td>
                           <input name="archiveItem.lh"/>
                        </td>
                        <th>
                            <span class="red"></span>序号
                        </th>
                        <td>
                           <input name="archiveItem.xh"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>页码
                        </th>
                        <td>
                           <input name="archiveItem.ym"/>
                        </td>
                        <th>
                            <span class="red"></span>份数
                        </th>
                        <td>
                           <input name="archiveItem.fs"/>
                        </td>
                    </tr>
                </tbody>
                </table>
        </div>
    </form>
</body>
</html>