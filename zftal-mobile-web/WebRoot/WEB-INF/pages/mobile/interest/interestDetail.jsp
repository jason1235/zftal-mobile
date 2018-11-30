<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
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
        $("#save_btn").click(function(){
        	var url = "<%=request.getContextPath()%>/interest/interest_checkInterest.html";
        	$.post(url,{
        		interestId:$("#interid").val(),
        		status:1
        	} ,function(data){
                    if(data.success){
                    	window.location.href="<%=request.getContextPath()%>/interest/interest_htInterestList.html";
                    }else{
                        errorAlert(data.text);
                        window.parent.scroll(0,0);
                    }
                            
            },"json");
        });
        
        $("#cancel").click(function(){
           var url = "<%=request.getContextPath()%>/interest/interest_checkInterest.html";
        	$.post(url,{
        		interestId:$("#interid").val(),
        		status:2
        	} ,function(data){
                    if(data.success){
                    	window.location.href="<%=request.getContextPath()%>/interest/interest_htInterestList.html";
                    }else{
                        errorAlert(data.text);
                        window.parent.scroll(0,0);
                    }
                            
            },"json");
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
	 <input type="hidden" id="interid" value="${interestEntity.id}"/>
    <div class="sl_mod modal-content">
        <div class="modal-header">
            <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title text-left" id="myModalLabel">圈子信息</h4>
        </div>
        <div class="modal-body">
        <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             	<tbody>
              
            	<tr>
                   	<th width="30%">
                       	圈子名称
                    </th>
                    <td><input id="name" type="text" class="form-control" value="${interestEntity.name }"  readonly="readonly"/></td>
                </tr>
                
                <tr  style="max-height: 180px;">
	                <th>
	                  	圈子图片<br />
	                </th>
	                <td>
					<div  style="height:180px; " id="baseImgPath">
	                	<img style="height:180px; " src="<%=request.getContextPath() %>/${interestEntity.img}">
	                </div>
	                </td>
	            </tr>
                
                <tr>
                    <th>
                      	 圈子描述
                    </th>
                    <td>
                    	<textarea readonly="readonly" class="form-control" rows="3" cols="10">${interestEntity.content}</textarea>
                    </td>
                </tr>
                
                <tr  style="max-height: 180px;">
	                <th>
	                  	学生证<br />
	                </th>
	                <td>
					<div  style="height:180px; " id="baseImgPath">
	                  	<img style="height:180px; " src="<%=request.getContextPath() %>/${interestEntity.idCard}">
	                </div>
	                </td>
	            </tr>
                
                <tr>
                   	<th width="30%">
                       	成员头衔
                    </th>
                    <td><input id="name" type="text" class="form-control" value="${interestEntity.title }"  readonly="readonly"/></td>
                </tr>
                
                
                
                
             
              	</tbody>
            </table>
        </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="save_btn">通过</button>
            <button type="button" class="btn btn-primary" id="cancel">不通过</button>
        </div>
</form>


</body>
</html>