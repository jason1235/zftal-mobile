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
    	
    	laydate.render({
         	elem: '#startTime', //指定元素
         	type: 'datetime',
         	format: 'yyyy-MM-dd HH:mm:ss'
       	});
       	
       	laydate.render({
         	elem: '#endTime', //指定元素
         	type: 'datetime',
         	format: 'yyyy-MM-dd HH:mm:ss'
       	});
    	
        $("#save_btn").click(function(){
        	
        	var name=$("#form_edit").find("input[name='ballot.name']").val();
			var ballotNum=$("#form_edit").find("input[name='ballot.ballotNum']").val();
			var startTime=$("#form_edit").find("input[name='ballot.startTime']").val();
			var endTime=$("#form_edit").find("input[name='ballot.endTime']").val();
			if(name==''){
				errorAlert("投票的标题为必填项");
				return false;
			}
			
			if(ballotNum==''){
				errorAlert("每人可投票数为必填项");
				return false;
			}
			if(startTime=='' || endTime ==''){
				errorAlert("请选择时间");
				return false;
			}
			var reg = /^[0-9]+$/ ;
   		   	if(!reg.test(ballotNum)){
       		   	errorAlert("每人可投票数必须是正整数！");
       		   	return false; 
   		   	}
        	
        	var url = "<%=request.getContextPath()%>/htballot/htballot_addBallot.html";
        	$.post(url,$("#form_edit").serialize() ,function(data){
                    if(data.success){
                    	window.location.href="<%=request.getContextPath()%>/htballot/htballot_htballotList.html";
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
	 <input type="hidden" name="ballot.id" value="${ballot.id}"/>
    <div class="sl_mod modal-content">
        <div class="modal-header">
            <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title text-left" id="myModalLabel">文艺投票信息维护</h4>
        </div>
        <div class="modal-body">
        <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             	<tbody>
              
            	<tr>
                   	<th width="30%">
                        <span class="red">*</span>投票的标题
                    </th>
                    <td><input id="name" type="text" name="ballot.name" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                    	class="form-control" value="${ballot.name }" /></td>
                </tr>
                
                <tr>
                    <th>
                        <span class="red">*</span>开始时间
                    </th>
                    <td>
                    	<input type="text" id="startTime" name="ballot.startTime" class="form-control" value="${ballot.startTime}"   placeholder="请选择日期" readonly="readonly"/>  
                    </td>
                    	
                </tr>
                
                <tr>
                    <th>
                        <span class="red">*</span>结束时间
                    </th>
                    <td>
                    	<input type="text" id="endTime" name="ballot.endTime" class="form-control" value="${ballot.endTime}"   placeholder="请选择日期" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <span class="red">*</span>投票规则
                    </th>
                    <td>
                   		<select id="ruleType" name="ballot.ruleType">
                   			<option value="1">每天可投票</option>
                   			<option value="2">每人固定票数</option>>
                   		</select>
                    </td>
                </tr>
                
                <tr>
                    <th>
                        <span class="red">*</span>每人可投票数
                    </th>
                    <td>
                    <input id="ballotNum" type="text" name="ballot.ballotNum" maxlength="10" placeholder="请输入数字"  
                    	class="form-control" value="${ballot.ballotNum}" /></td>
                </tr>
                
                <tr>
                    <th>
                        <span class="red"></span>投票介绍
                    </th>
                    <td>
                    	<textarea name="ballot.content" maxlength="250" placeholder="不要超过250个字符哦(^_^)" 
                    		class="form-control" rows="3" cols="10">${ballot.content}</textarea>
                    </td>
                </tr>
             
	            <tr  style="max-height: 180px;">
	                <th>
	                  	<span class="red"></span>列表缩略图<br />
	                </th>
	                <td>
	                <input type="hidden" name="imageName" value="${ballot.baseImgPath }"/>
					<div  style="height:180px; " id="baseImgPath">
	                  	${baseImgPath }
	                </div>
	                </td>
	            </tr>
	            
	            <tr  style="max-height: 180px;">
	                <th>
	                  	<span class="red"></span>详情主图<br />
	                </th>
	                <td>
	                <input type="hidden" name="imageName" value="${ballot.bigImgPath }"/>
					<div  style="height:180px; " id="bigImgPath">
	                  	${bigImgPath }
	                </div>
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