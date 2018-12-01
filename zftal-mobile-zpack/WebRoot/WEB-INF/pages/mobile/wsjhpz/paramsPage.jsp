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

	<script type="text/javascript">
		$(function(){
			var types = '${types}';
			$("#cancel").click(function(){
	            window.parent.$('#myModal_toji').modal('hide');
	            return false;
	        });
	        
	        $(".close").click(function(){
	            window.parent.$('#myModal_toji').modal('hide');
	            return false;
	        });
	        
	        $("#save_btn").click(function(){
			var url = window.parent.$('#wsjhConfig_url').val();
	    	var namespace=window.parent.$('#wsjhConfig_namespace').val();
	    	var operate = window.parent.$("select[name='wsjhConfig.operate']").val();  
	    	var isType=window.parent.$('input[name="wsjhConfig.isType"]:checked').val();
	        var gys = window.parent.$('#wsjhConfig_gys').val();
	    	var zys = window.parent.$('#wsjhConfig_zys').val();
			var params="";
			var sign="";
			$('input[id="params_value"]').each(function(){
				var name = $(this).attr('name');
				var value = $(this).val();
				params+="&"+name+"="+value;
				if("SIGN"==name)sign=value;
			});
			if(url==null||url.length==0){
				errorAlert("WebService URL地址为空!");
				return false;
			}			
			if(namespace==null||namespace.length==0){
				errorAlert("WebService 命名空间为空!");
				return false;
			}
			if(operate==null||operate.length==0){
				errorAlert("WebService 操作方法为空!");
				return false;
			}
	     	
			$.post("webservice/wsjhconfig_getFhbl.html",{"url":url,"namespace":namespace,"operate":operate,"params":params,"isType":isType,"gys":gys,"zys":zys,"types":types},function(data){
				if(data.success){
					var params = data.params;
					//alert(params);
					window.parent.$('#wsjhConfig_fhbl').val(params);
					window.parent.$('#wsjhConfig_sign').val(sign);
					window.parent.$('#myModal_toji').modal('hide');
				}
			},"json");
			//$("#hideWin").trigger("click");
		});	
			
		})	
	</script>
</head>

<body>
	<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <div class="sl_mod modal-content" style="border:none">
            <div class="modal-header">
<!--                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>-->
                <button type="button" class="close" ><span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span><span class="sr-only">Close</span></button>              
                <h4 class="modal-title text-left" id="myModalLabel">获取返回变量</h4>      
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
			<tbody>
				<s:iterator value="#paramsList" var="o">
		          <tr width="20%">
		             <th width="30%"><s:property value="#o"/></th>
		             <td>
		    		   <input type="text" id="params_value" maxlength="40" placeholder="不要超过40个字符哦(^_^)" class="form-control" name="<s:property value="#o"/>" />
		    		</td>
		    	  </tr>				
		     	</s:iterator>
          	</tbody>
   		  </table>	
            </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>
</body>
</html>
