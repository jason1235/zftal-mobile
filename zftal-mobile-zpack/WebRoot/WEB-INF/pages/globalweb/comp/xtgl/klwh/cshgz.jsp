<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script>
</head>
<script type="text/javascript">
	function save(){
		var flag = document.getElementById("sdsr").checked;
			 if(flag&&null==$("#mm").val()&&$("#mm").val().length <6){
					alert("手动输入密码不能为空！");
					$("#mm").focus();
			}else{
				if(inputResult() && null!=$("#type").val()&&$("#type").val()=='qb'){
				showConfirmDivLayer('该操作会初始化所有学生密码，确定要全部初始化吗？',
						{'okFun':function(){
				 	subForm('klwh_qbCsh.html')
				 }
				 })
						 
				}else if(inputResult() && null!=$("#type").val()&&$("#type").val()=='pl'){
				showConfirmDivLayer('确定要初始化吗？',
						{'okFun': function(){
					subForm('klwh_plCsh.html')
				}
				})
					
				}
			}
			
	}

	function changeCheck(){
		$('#sdsr').attr("checked","checked");
	}

	function checkMm(obj){
		var mm = $('#mm').val();
		if(mm.length <6){
			showDownError(obj,'密码长度不能小于6位！');
		}
	}


	
</script>

<s:form action="" method="post" theme="simple">
	<body>
      <input type="hidden" id="pkValue" name="pkValue" value="${pkValue}"/>   
       <input type="hidden" id="type" name="type" value="${type}"/>    
	 <div class="tab">
	  <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	  <thead>
	    	<tr>
	        	<th colspan="4"><span>规则选择</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	          <div class="btn">
	            <button name="btn_tj" onclick="save();return false;">保 存</button>
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
		    <s:if test="type == 'qb'">
		      <tr align="center">
			      <td colspan="3" > 
			    	 <font color="red">您选择了全部初始化，请慎重操作！</font>
			      </td>
		     </tr>
		    </s:if>
	      <tr  >
	      <td width = "10%" align="center">
	      <input type="radio" id="sfz" name="gzlx" value="0" />
	      
	      </td>
	      <td width = "80%" align="left" colspan="2">
	    		  按身份证后6位，无身份证按6个0
	      </td>
	     </tr>
	     
	     <tr>
	     	<td  align="center"><input type="radio" id="sdsr" name="gzlx" value="1" checked="checked"/></td>
	     	<td align="left" width="10%">
	     	手动输入
	     	</td>
	     	<td>
	     	<s:textfield maxlength="20" name="mm" id="mm" cssStyle="width:120px" onblur="checkMm(this);" onfocus="changeCheck();showDownPrompt(this,'密码长度不能小于6位');"></s:textfield> 
	     	</td>
	      </tr>
	      <tr>
	      <td colspan="3" >
	      &nbsp;
	      </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
  <s:if test="result != null && result != ''">
  	<script>
  		alert('${result}','',{'clkFun':function(){refershParent()}});
  	</script>
  </s:if>
</body>
</s:form>
</html>