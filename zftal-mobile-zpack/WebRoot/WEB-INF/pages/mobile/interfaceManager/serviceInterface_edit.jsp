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
        $("#dwName").val("${model.classJkffms}");
        var qbssjk   = $("#jklm").val();
        var qbjkffm  = $("#jkffm").val();
        getParamList(qbssjk,qbjkffm);
        
        //alert(jkffm);
        
       
        $("#jklm").change(function(){
            var ssjk = $("#jklm").val();
            
            $.post("<%=request.getContextPath()%>/interfaceManager/serviceInterface_getMethodList.html",
                "ssjk="+ ssjk ,function(data){
                    if(data.success){
                            $("select").remove(".jkffm");
                   			var optionStr = "<select id='jkffm' name='model.classJkffm'  value='${model.classJkffm }' style='width:250px;' class='jkffm'> ";
                   			for(var i = 0; i<data.methodList.length;i++){
                   			   optionStr = optionStr + " <option value='" + data.methodList[i] + "'>" + data.methodList[i] + "</option> "
                   			}
                   			optionStr = optionStr + " </select> ";
                   			$("#jkffmtd").append(optionStr); 
                   			var jkffmStr = data.methodList[0];
                   			getParamList(ssjk,jkffmStr);
                   			
                   			
                   			$("#jkffm").change(function(){
				            var qjssjk   = $("#jklm").val();
					        var qjjkffm  = $("#jkffm").val();
					        getParamList(qjssjk,qjjkffm);
				        });
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json")
            
        });
        
         
        $("#jkffm").change(function(){
				            var qjssjk   = $("#jklm").val();
					        var qjjkffm  = $("#jkffm").val();
					        getParamList(qjssjk,qjjkffm);
				        });
        $("#save_btn").click(function(){
             var ssjk   = $("#jklm").val();
           	 var jkffm  = $("#jkffm").val();
           
             $.post("<%=request.getContextPath()%>/interfaceManager/serviceInterface_test.html",
                "ssjk=" + ssjk + "&jkffm=" + jkffm,function(data){
                    if(data.success){
                       	$.post("<%=request.getContextPath()%>/interfaceManager/serviceInterface_save.html",
		                $("#form_edit").serialize()  ,function(data){
				                    if(data.success){
				                        $(".ymPrompt_close",window.parent.document).click();
				                        $("#search",window.parent.document).submit();
				                    }else{
				                        showWarning(data.text);
				                    }
			                            
			                    },"json");
                    }else{
                        alert(data.text);
                    }
                            
            },"json");
            return false;
        });
        
        
        
        $("#cancel").click(function(){
           $(".ymPrompt_close",window.parent.document).click();
        });
        
        $("#test").click(function(){
        
           //if(!check()) return false;
           var object = $("input[class='paraminput']");
           var arrayStr = "";
           $("input[class='paraminput']").each(function(i){
                //	alert($(this).val());
			   arrayStr = arrayStr  + $(this).val() + ",";
		       //alert(arrayObj[0]);
		   });
		   
           //var arrayObj = new Array([length]);
           //alert(arrayStr); 
           //for(var i = 0 ; i < length ; i++){
           //}
           var ssjk   = $("#jklm").val();
           var jkffm  = $("#jkffm").val();
           
             $.post("<%=request.getContextPath()%>/interfaceManager/serviceInterface_test.html",
                "ssjk=" + ssjk + "&jkffm=" + jkffm + "&arrayStr=" + arrayStr,function(data){
                    if(data.success){
                        alert("接口方法测试成功！");
                        $("#resultType").val(data.result);
                    }else{
                        alert(data.text);
                    }
                            
            },"json");
        });
        
    });
    
    
    
    function getParamList(qjssjk,qjjkffm){
         $.post("<%=request.getContextPath()%>/interfaceManager/serviceInterface_getParamList.html",
                "qjssjk=" + qjssjk + "&qjjkffm=" + qjjkffm,function(data){
                    if(data.success){
                            $("tr").remove(".param");
                            var tdStr = "";
                   			for(var i = 0; i<data.parametersList.length;i++){
                   			 tdStr =tdStr+ " <tr class='param'> " +
							               "        <th width='4%' > " +
							               "             <span class='red'>*</span>参数 " + i +
							               "         </th>    "  +
							               "          <td width='8%' >  "  +
							               "             <input   type='input' id='param" + i + "' class='paraminput' style='width:250px;'/>   ";
							 if(data.parametersList[i].type == "string"){
							 tdStr =tdStr+ "(请输入字符)";
							 }if(data.parametersList[i].type == "int"){
							 tdStr =tdStr+ "(请输入数值)";
							 }if(data.parametersList[i].type == "boolean"){
							 tdStr =tdStr+ "(请输入布尔类型)";
							 }
							 tdStr =tdStr+ "           <input   type='hidden' id='type" + i + "' value='" + data.parametersList[i].type +"' class='paramtype'/>" +
							               "          </td>    "  +
				             			   " </tr>  ";
                   			}
                   			$("#resultTypetr").before(tdStr); 
                    }else{
                        alert(data.text);
                    }
                            
            },"json");
    }
    
    //function check(){
        // $("input[class='paraminput']").each(function(i){
         //     var type = $(this).closest("tr").find("input[class='paramtype']").val();
         //     if(type == "string"){
         //          reg=/^([a-zA-Z0-9]+)$/;   
        //		   if(!reg.test($(this).val())){
        //		   alert("有字符型参数输入非法！");
        //		   return false;   
		//		   }
        //      }
         //     if(type == "int"){
         //          reg=/^-?d+$/;   
        //		   if(!reg.test($(this).val())){
        //		   alert("有数值型参数输入非法！");
        //		   return false;
		//		   }
         //     }
        //      alert(type);
         //     if(type != "string" && type != "string"){
         //          if($(this).val() != "0" && $(this).val() != "1")
                   //alert("有布尔型参数输入非法，只能输入0(false)或者1(true)！");
        //		   return false;
         //     }
        // });
   // }
                   			
     </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/interfaceManager/serviceInterface_save.html" name="search" id="form_edit" method="post">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            <thead>
                    <tr>
                        <th colspan="2">
                            <span><font color="#0f5dc2" style="font-weight:normal;">维护服务接口类</font></span>
                        </th>
                    </tr>
            </thead>
                
             <tbody>
             <tr>
                        <c:if test="${op!='add'}">
                                <input value="${model.classFwjkid }" name="model.classFwjkid" type="hidden"/>
                        </c:if>
             </tr>
              <tr>
                        <th width="4%" >
                            <span class="red">*</span>所属服务
                        </th>
                         <td width="8%" id="ssywxttd">
	                         <select id="ssywxt" name="model.classSsfw"  value="${model.classSsfw }" style="width:250px;">
	                       			 <c:forEach var="menu" items="${ServiceManagerList}" varStatus="st">
	                         		    <option value="${menu.classId} "  <c:if test = "${model.classSsfw eq menu.classId}"> selected="selected"</c:if>>${menu.classFwmc}</option>
	                                 </c:forEach>
	          				 </select>
                        </td>
              </tr>
              <tr>
                        <th width="4%" >
                            <span class="red">*</span>接口类名
                        </th>
                         <td width="8%" >
	                         <select id="jklm" name="model.classSsjk"   style="width:250px;">
	                       			 <c:forEach var="menu" items="${interfacemanagerList}" varStatus="st">
	                         		    <option value="${menu.classJkid} "  <c:if test = "${model.classSsjk	 eq menu.classJkid}"> selected="selected"</c:if>>${menu.classJklm}</option>
	                                 </c:forEach>
	          				 </select>
                        </td>
             </tr>
             <tr >
                        <th width="4%">
                                                                                接口方法描述
                        </th>
                         <td width="8%" >
                         <textarea rows="3" cols="30" id="dwName" name="model.classJkffms" value="${model.classJkffms}" style="width:250px">
						 </textarea>
                        </td>
             </tr>
             <tr id="jkffmtr">
                        <th width="4%" >
                            <span class="red">*</span>接口方法名
                        </th>
                         <td width="8%" id="jkffmtd">
                            <!--<input id="jkffm" name="model.classJkffm" type="text" value="${model.classJkffm }" style="width:250px"/>
                        -->
                            <select id="jkffm" name="model.classJkffm"   style="width:250px;" class="jkffm">
	                       			 <c:forEach var="menu" items="${methodList}" varStatus="st">
	                         		    <option value="${menu} "  <c:if test = "${model.classJkffm	 eq menu}"> selected="selected"</c:if>>${menu}</option>
	                                 </c:forEach>
	          				 </select>
                        </td>
             </tr>
             <tr id="resultTypetr">
                        <th width="4%">
                                                                               返回数据
                        </th>
                         <td width="8%" >
                         <textarea rows="10" cols="60" id="resultType"  style="width:450px">
						 </textarea>
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