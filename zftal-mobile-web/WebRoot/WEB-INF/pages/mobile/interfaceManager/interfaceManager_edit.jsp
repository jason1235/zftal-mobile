<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    $(function(){
        $("#dwName").val("${model.classJkms}");
        
        
        $("#save_btn").click(function(){
            if(!check()){
                 return false;
            }
            
            qbjkdz = $("#jkdz").val();
             $.post("<%=request.getContextPath()%>/interfaceManager/interfaceManager_test.html",
                "qbjkdz="+ qbjkdz ,function(data){
                    if(data.success){
                        $.post("<%=request.getContextPath()%>/interfaceManager/interfaceManager_save.html",
			                $("#form_edit").serialize()  ,function(data){
			                    var callback = function(){
								//reflashPage();
								location.reload();
								};
								if(data.success){
									processDataCall(data, callback);
								}else{
									showWarning(data.text);
								}
			                            
			            },"json");
                    }else{
                    
                    
                    	showConfirm("测试不通过，是否仍然保存？");

		                $("#why_cancel").click(function(){
		                    divClose();
		                })
		                
		                $("#why_sure").click(function(){
		                    $.post("<%=request.getContextPath()%>/interfaceManager/interfaceManager_save.html",
			                $("#form_edit").serialize()  ,function(data){
			                    var callback = function(){
							//reflashPage();
							location.reload();
							};
							if(data.success){
								processDataCall(data, callback);
							}else{
								showWarning(data.text);
							}
			                            
			            	},"json");
		                })
                    }
                            
            },"json");
            
            
            return false;
        });
        
        $("#cancel").click(function(){
           divClose();
		   return false;
        });
        
        $("#test").click(function(){
            if(!check()){
                 return false;
            }
           qbjkdz = $("#jkdz").val();
             $.post("<%=request.getContextPath()%>/interfaceManager/interfaceManager_test.html",
                "qbjkdz="+ qbjkdz ,function(data){
                    if(data.success){
                        alert(data.text);
                    }else{
                        alert(data.text);
                    }
                            
            },"json");
        });
        
        $("#jkdzmc").change(function(){ 
            var jkdz = $("#jkdzmc").val();
            var zjdz = jkdz.lastIndexOf("/");
            var lastdz = jkdz.indexOf("?");
            var length = jkdz.length;
            var jklm = "";
            if(jkdz.indexOf("/") < 0)
              showWarning("接口地址输入错误，应包含'/'!");
            if (jkdz.indexOf("?") >= 0) {
				jklm = jkdz.substring(zjdz+1,lastdz);
			} else {
				jklm = jkdz.substring(zjdz+1,length);
			}
			$("#jklmmc").val(jklm);
            
        });
        
    
        
    });
    
     function check()
    {
        
        var jklm=$("input[name='model.classJklm']").val();
        var jkbm=$("input[name='model.classJkbm']").val();
        var chinese = /^([a-zA-Z0-9]+)$/;
		var flag = (chinese.test(jkbm));
        if(jkbm==null || jkbm==''){
            showWarning("接口编码不能为空");
            return false;
        }
		if(!flag){
		    showWarning("接口编码只能为英文和字母的组合");
            return false;
		}
        if(jklm==null || jklm==''){
            showWarning("接口类名不能为空");
            return false;
        }
        
        return true;
    }
     </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/interfaceManager/interfaceManager_save.html" name="search" id="form_edit" method="post">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            <thead>
                    <tr>
                        <th colspan="2">
                            <span><font color="#0f5dc2" style="font-weight:normal;">维护接口类</font></span>
                        </th>
                    </tr>
            </thead>
                
             <tbody>
             <tr>
                        <c:if test="${op!='add'}">
                                <input value="${model.classJkid }" name="model.classJkid" type="hidden"/>
                        </c:if>
             </tr>
              <tr>
                        <th width="4%" >
                            <span class="red">*</span>接口编码
                        </th>
                         <td width="8%" >
                            <input id="jkbm" name="model.classJkbm" type="text" value="${model.classJkbm }" style="width:300px;" />
                        </td>
             </tr>
              <tr>
                        <th width="4%" >
                            <span class="red">*</span>接口类名
                        </th>
                         <td width="8%" >
                            <input id="jklmmc" name="model.classJklm" type="text" value="${model.classJklm }" style="width:300px;" readonly="readonly"/>
                        </td>
             </tr>
             <tr>
                        <th width="4%">
                                                                                接口描述
                        </th>
                         <td width="8%" >
                         <textarea rows="4" cols="30" id="dwName" name="model.classJkms" value="${model.classJkms }" style="width:300px">
						 </textarea>
                        </td>
             </tr>
             <tr>
                        <th width="4%" >
                            <span class="red">*</span>接口地址
                        </th>
                         <td width="8%" >
                            <input id="jkdzmc" name="model.classJkdz" type="text" value="${model.classJkdz }" style="width:300px"/>
                        </td>
             </tr>
             </tbody>
             <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="test" type="button">测 试</button>
                                <button id="save_btn" type="button">保 存</button>
                                <button id="cancel" type="button" >取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
</form>


</body>
</html>