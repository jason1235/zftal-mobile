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
        	var identifier=$("#form_edit").find("input[name='ballotDetail.identifier']").val();
			var name=$("#form_edit").find("input[name='ballotDetail.name']").val();
			if(identifier==''){
				errorAlert("候选的标题为必填项");
				return false;
			}
			
			if(name==''){
				errorAlert("候选的名称为必填项");
				return false;
			}
        	
        	var url = "<%=request.getContextPath()%>/htballot/htballot_saveBallotDetail.html";
        	$.post(url,$("#form_edit").serialize() ,function(data){
                    if(data.success){
                    	var id = $("#ballotId").val();
                    	var url = "<%=request.getContextPath()%>/htballot/htballot_ballotDetailList.html?ballotDetailQuery.ballotId=" + id;
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
	<input type="hidden" name="ballotDetail.id" value="${ballotDetail.id}"/>
	<input type="hidden" id="ballotId" name="ballotDetail.ballotId" value="${ballotId}"/>
    <div class="sl_mod modal-content">
        <div class="modal-header">
            <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title text-left" id="myModalLabel">候选信息维护</h4>
        </div>
        <div class="modal-body">
        <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             	<tbody>
              
            	<tr>
                   	<th width="30%">
                        <span class="red">*</span>候选的编号
                    </th>
                    <td><input id="identifier" type="text" name="ballotDetail.identifier" maxlength="10" placeholder="不要超过10个字符哦(^_^)"  
                    	class="form-control" value="${ballotDetail.identifier }" /></td>
                </tr>
                
                
                <tr>
                    <th>
                        <span class="red">*</span>候选的名称
                    </th>
                    <td>
                    <input id="name" type="text" name="ballotDetail.name" maxlength="10" placeholder="请输入候选的名称"  
                    	class="form-control" value="${ballotDetail.name}" /></td>
                </tr>
                
	            <tr  style="max-height: 180px;">
	                <th>
	                  	<span class="red"></span>候选图片<br />
	                </th>
	                <td>
	                <input type="hidden" name="imageName" value="${ballotDetail.baseImgPath }"/>
					<div  style="height:180px; " id="baseImgPath">
	                  	${baseImgPath }
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