<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/laydate/laydate.js"></script>
<%@ include file="/commons/hrm/head_v5.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    $(function(){
    	
    	laydate.render({
         	elem: '#birthday', //指定元素
       	});
    	
        $("#save_btn").click(function(){
			var name = $("#name").val();
			var weight = $("#weight").val();
			var height = $("#height").val();
			var sclassname = $("#sclassname").val();
			
			if(name==''){
				errorAlert("姓名为必填项");
				return false;
			}
			if(height=='' || weight == ''){
				errorAlert("请输入正确的身高和体重");
				return false;
			}
			if(sclassname==''){
				errorAlert("请选择班级");
				return false;
			}
        	
        	var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_saveStu.html";
        	$.post(url,$("#form_edit").serialize() ,function(data){
                    if(data.success){
                    	var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_htStuList.html";
                    	window.location.href=url;
                    }else{
                        errorAlert(data.text);
                        window.parent.scroll(0,0);
                    }
                            
            },"json");
        });
        
        $("#cancel").click(function(){
            history.go(-1);
        });
        
        $(".close").click(function(){
            history.go(-1);
        });
        
        
        
    });
    
     
     </script>
</head>
<body>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
<form id="form_edit">
	<input type="hidden" name="studentInfoQuery.schoolNumber" value="${studentInfoQuery.schoolNumber}"/>
    <div class="sl_mod modal-content">
        <div class="modal-header">
            <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title text-left" id="myModalLabel">学生信息维护</h4>
        </div>
        <div class="modal-body">
        <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             	<tbody>
              
            	<tr>
                   	<th width="30%">
                        <span class="red">*</span>姓名
                    </th>
                    <td><input id="name" type="text" name="studentInfoQuery.name" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                    	class="form-control" value="${studentInfoQuery.name }" /></td>
                </tr>
                
                
                <tr>
                    <th>
                        <span class="red">*</span>性别
                    </th>
                    <td>
                    	<select id="gender" name="studentInfoQuery.gender" class="form-control">
                    		<option value="1">男</option>
                    		<option value="2">女</option>
                    	</select>
                    </td>
                </tr>
                
                <tr>
                   	<th width="30%"> 出生日期
                    </th>
                    <td><input id="birthday" type="text" name="studentInfoQuery.birthday" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                    	class="form-control" value="${studentInfoQuery.birthday }" /></td>
                </tr>
                
                <tr>
                   	<th width="30%">家庭住址
                    </th>
                    <td><input id="homeAddr" type="text" name="studentInfoQuery.homeAddr" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                    	class="form-control" value="${studentInfoQuery.homeAddr }" /></td>
                </tr>
                
                <tr>
                   	<th width="30%">毕业高中
                    </th>
                    <td><input id="highSchoolName" type="text" name="studentInfoQuery.highSchoolName" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                    	class="form-control" value="${studentInfoQuery.highSchoolName }" /></td>
                </tr>
                
                <tr>
                   	<th width="30%">
                        <span class="red">*</span>身高
                    </th>
                    <td><input id="height" type="text" name="studentInfoQuery.height" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                    	class="form-control" value="${studentInfoQuery.height }" /></td>
                </tr>
                
                <tr>
                   	<th width="30%">
                        <span class="red">*</span>体重
                    </th>
                    <td><input id="weight" type="text" name="studentInfoQuery.weight" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                    	class="form-control" value="${studentInfoQuery.weight }" /></td>
                </tr>
                
                <tr>
                   	<th width="30%">
                        <span class="red">*</span>学院
                    </th>
                    <td>
                    	<select id="sschoolname" name="studentInfoQuery.instituteId" class="form-control">
                    		<option value="">--请选择学院--</option>
                    		<s:iterator value="schools" var="p" status="st">
                    			<s:if test="%{#p.id == studentInfoQuery.instituteId }">
                    				<option value="${p.id}" selected="selected">${p.name}</option>
                    			</s:if>
                    			<s:if test="%{#p.id != studentInfoQuery.instituteId }">
                    				<option value="${p.id}">${p.name}</option>
                    			</s:if>
                    		</s:iterator>
                    	</select>
                    </td>
                </tr>
                
                <tr>
                   	<th width="30%">
                        <span class="red">*</span>班级
                    </th>
                    <td>
                    	<select id="sclassname" name="studentInfoQuery.classId" class="form-control">
                    		<option value="">--请选择班级--</option>
                    		<s:iterator value="classes" var="p">
                    			<s:if test="%{#p.id == studentInfoQuery.classId }">
                    				<option value="${p.id}" selected="selected">${p.name}</option>
                    			</s:if>
                    			<s:if test="%{#p.id != studentInfoQuery.classId }">
                    				<option value="${p.id}">${p.name}</option>
                    			</s:if>
                    		</s:iterator>
                    	</select>
                    </td>
                </tr>
                
	            
	            
              	</tbody>
            </table>
        </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
            <button type="button" class="btn btn-primary" id="cancel">取 消</button>
        </div>
</form>


</body>
</html>