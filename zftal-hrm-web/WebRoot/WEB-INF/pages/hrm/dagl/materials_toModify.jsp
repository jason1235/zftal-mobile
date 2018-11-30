<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib  
    prefix="fmt"  
    uri="http://java.sun.com/jsp/jstl/fmt"  
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
<script type="text/javascript">
$(function(){
	
    		
    		$("#save_btn").click(function(){
    		
            var op = $("#op").val();
            if(!check()){
                 return false;
            }
            var param=$("#form_edit").serialize();
            
            if(op == "modify"){
            $.post("<%=request.getContextPath()%>/dagl/materials_modifyData.html",
                $("#form_edit").serialize(),function(data){
                    if(data.success){
                        $(".ymPrompt_close",window.parent.document).click();
                        $("#search",window.parent.document).submit();
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
            }
        });
        
        $("#cancel").click(function(){
            divClose();
        });
      });
      
      function check()
    {
        
        var type=$("input[name='materials.classGhsj']").val();
        if(type==null || type==''){
            showWarning("请填写归还时间！");
            return false;
        }
        
        return true;
    }
</script>
</head>
<body>

<form  action="<%=request.getContextPath()%>/dagl/materials_modifyData.html" name="search" id="form_edit" method="post">
<input type="hidden" id="op" value="${op }">
<input type="hidden" id="materials.classId" name="materials.classId" value="${materials.classId }">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">归还登记</font></span>
                        </th>
                    </tr>
     		</thead>
                <tbody>
                    <tr>
                        <th>
                            <span class="red">*</span>借阅人职工号
                        </th>
                        <td>
                            <label id="ghLabel">${materials.classGh }</label>
                            <input id="ghInput" type="hidden" name="materials.classGh" value="${materials.classGh }"/>
                        </td>
                        <th>
                            <span class="red">*</span>借阅人姓名
                        </th>
                        <td>
                            <label id="xmLabel">${materials.classXm }</label>
                            <input id="xmInput" type="hidden" name="materials.classXm" value="${materials.classXm }"/>
                        </td>
                    </tr>
                    <tr>
                       <th>
                            <span class="red">*</span>借阅时间
                        </th>
                        <td>
                            <input  name="materials.classJysj"
                            value="<fmt:formatDate value="${materials.classJysj }" />" id="jysjInput" readonly="true"/>
                            
                        </td>
                        <th>
                            <span class="red"></span>归还时间
                        </th>
                        <td>
                            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="materials.classGhsj"
                            value="<fmt:formatDate value="${materials.classGhsj }" />" id="ghsjInput"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>借阅材料名称<br/>  </th>
                        <td >
                            <label id="clmcLabel">
                            ${materials.classClmc }
                            </label>
                            <input id="clmcInput" type="hidden" name="materials.classClmc" value="${materials.classClmc }"/>
                        </td>
                        <th><span class="red">*</span>借阅材料编号<br/>  </th>
                        <td >
                            <label id="clbhLabel">
                            ${materials.classDescribe }
                            </label>
                            <input id="clbhInput" type="hidden" name="materials.classDescribe" value="${materials.classDescribe }"/>
                        </td>
                        
                            <input id="clidInput" type="hidden" name="materials.classClid"   value="${materials.classClid }" />
                        
                    </tr>
                </tbody>
                <tfoot>
        			<tr>
                        <th colspan="4">
                            <span><span class="red">*</span>"为必填项</span>
                            <button id="save_btn" type="button">提交归还登记时间</button>
                                <button id="cancel" type="button">取 消</button>
                        </th>
                    </tr>
      			</tfoot>
                </table>
        
       
            
        </div>
    </form>

</body>
</html>