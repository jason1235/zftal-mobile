<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>    
    <title>初始化密码</title>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
    	<script type="text/javascript" src="<%=systemPath %>/js/hrm/code.js"></script>
	<script type="text/javascript">	   
	function formSubmit(){
		var param = $("#form1").serialize();
	
		$.post(_path+'/xtgl/yhgl_mmcsh.html',param,function(data){
			if(data.success){
				var text ="<span style='color:red;font-size:18px'>"
				+data.html
				+"</span>"
    			$("#remind").html(text);
    			setTimeout("iFClose()",800);
			}
		},"json");
	}

	function checkMm(obj){
		var isNotnulll = true;
		var mm = $('#mm').val();
		 if(mm.length <6){
			showDownError(obj,'密码长度不能为空并且不小于6位！');
			isNotnulll =  false;	
		}
			return isNotnulll;
	}

	function selectGz(){
		var cshgz=$("#cshgz").val();
		if(cshgz=="zdmm"){
			addText();
		}else{
			removeText();
		}
	}
	
	function addText(){
	    var text="";
    	text += "<tr>"
    	+"<th><span style='color:red'>*</span>指定初始化密码</th>"
        +"<td>"
        +"<input type='text' name='zdmm' id='mm' onblur='checkMm(this);' onfocus='showDownPrompt(this,\"密码不能为空,并且长度不能小于6位\");'/>"
    	+"</td>"
    	+"</tr>"
    	$("#zdcshmm").html(text);
    }
    
    function removeText(){
    	$("#zdcshmm").html("");
    }
	</script>
  </head>
  
  <body>
	<s:form method="post" action="yhgl_mmcsh.html" theme="simple" id="form1">
	 <input type="hidden" name="pkValue" value="${pkValue}" />
  	<div class="tab">
  	<table width="100%" border="0" class="formlist">	
		<thead>
			<tr>
				<th colspan="2"><span>密码初始化</span></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th align="right">
					<span class="red">*</span>请选择密码初始规则
				</th>
				<td>
					<select id="cshgz" name="cshgz" onChange="selectGz()" style="width:153px">
						<option value="zjhm">初始化身份证后6位</option>
						<option value="zdmm">指定初始化密码</option>
					</select>
				</td>
			</tr>
			<tr></tr>
			<tbody id="zdcshmm">
			</tbody>
			<div id="remind"></div>
	    </tbody>
	    <tfoot>
	    </tfoot>
	    <tfoot>
    		<tr>
		   		<td colspan="2">
		   			<div class="btn">
						<button type="button" class="" onclick="formSubmit();return false;">
								确定
						</button>
						<button type="button" class="" onclick="iFClose();return false;">
								关闭
						</button>
		   			</div>
		   		</td>
    		</tr>
	    </tfoot>
	    </table>
    </div>
   </s:form> 
  </body>
</html>