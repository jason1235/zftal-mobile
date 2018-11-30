<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/hrm/head_v5.ini" %>

<style type="text/css">
		 .td-control {
		        width:7%;
		        height: 34px;
			    font-size: 14px;
			    line-height: 1.42857;
			    color: rgb(85, 85, 85);
			    background-color: rgb(255, 255, 255);
			    background-image: none;
			    box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px inset;
			    padding: 6px 6px;
			    border-width: 1px;
			    border-style: solid;
			    border-color: rgb(204, 204, 204);
			    border-image: initial;
			    border-radius: 4px;
			    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
		  }
		
			
			.formlist td,.formlist th {
				padding-left:10px;
			}
			.formlist th {
				width:20%;
			}
			
			.form-control {
				color:#000;
			}
			#container{
				width:80%;
				height:300px;
				
			}
			.search_tip{
				position:relative;
			}
			#tip {
	            background-color: #ddf;
	            color: #333;
	            border: 1px solid silver;
	            box-shadow: 3px 4px 3px 0px silver;
	            position: absolute;
	            top: 10px;
	            left: 10px;
	            border-radius: 5px;
	            overflow: hidden;
	            line-height: 20px;
	        }
	        #tip input[type="text"] {
	            height: 25px;
	            border: 0;
	            padding-left: 5px;
	            width: 280px;
	            border-radius: 3px;
	            outline: none;
	        }
		</style>
</head>


	 
	<body>
		<input type="hidden" name="zgh" id="zgh" value="${model.zgh}"/>
		
		 <jsp:include page="../../../modal.jsp" flush="true"></jsp:include>
        <div class="sl_mod modal-content" style="border:none">
            <div class="modal-header">
                <button type="button" class="close" onclick="history.go(-1)">
	                <span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span>
	                <span class="sr-only">Close</span>
                </button>              
                <h4 class="modal-title text-left" id="myModalLabel">绑定设备号</h4>      
            </div>
            <div class="modal-body">
            	<jsp:include page="../../../alert.jsp" flush="true"></jsp:include>
            	<div class="tab" style="clear: both;">
            		<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            			<tbody>
		                   <tr id="titleTr">
		                        <th><span class="red">*</span>设备号:</th>
		                        <td>
		                         <input type="text" name="deviceid" id="deviceid" maxlength="32"  class="form-control" />
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
        </div>   
		
		
  
  <script type="text/javascript">
$(function(){
	$("#cancel").click(function(){
		window.parent.$('#myModal_toji').modal('hide');
        	return false;
	});

	$("#save_btn").click(function(){
		var id = $("#zgh").val();
		var deviceid = $("#deviceid").val();
		
		if( id==null || id=="" ){
			openAlert("用户职工号不能为空！", "e");
			return false;
		}
		if( deviceid==null || deviceid=="" ){
			openAlert("设备号不能为空！", "e");
			return false;
		}
		
		var url = "<%=request.getContextPath()%>/xtgl/yhgl_bcsbh.html?model.zgh="+id+"&model.deviceid="+deviceid;
		$.ajax({
			url:url,
			type:"post",
			dataType:"json",
			data:{},
			success:function(data){
				if(data.status=='success'){
					openAlert(data.msg, "s","auto", function(){
					  	window.parent.$('#myModal_toji').modal('hide');
	        			return false;
				    });
        		}else{
        			openAlert(data.msg, "e");
        		}
		    }
		});
	});

});

</script>
  
</body>
</html>