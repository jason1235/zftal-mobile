<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/message.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script></head>
		<script type="text/javascript">
		
			//根据职工号加载用户已有角色信息,并显示选中状态
			function loadJsxx(){
				var cbvjsxxArray = $("input[name='cbvjsxx']");
				var jsdm = document.getElementById("jsdm").value;
				var data = jsdm.split(",");
					$.each(cbvjsxxArray,function(j,n) {
						for(var i=0;i<data.length;i++){
							if(data[i]==$(cbvjsxxArray[j]).attr("value")){
								cbvjsxxArray[j].checked = true;
							}
						}
					});
			}	

			function save(){
				if (checkEmail($('#dzyx')) && checkInputNotNull('xm')){ 
		 			 var jgdm = $('#jgdm').val();
		 			 var jgmc = $('#jgmc').val();
		 			 if(jgdm==''){
		 			 	alert('请选择所属机构！');
		 			 	return false;
		 			 }
		 			 if(jgmc==''){
		 			 	alert('请选择所属机构！');
		 			 	return false;
		 			 }
					 subForm('yhgl_xgBcYhxx.html');
				}
			}

			function checkEmail(obj){
				
				var isRight = true;
				var email = $('#dzyx').val();
				if (email!=''&&!isEmail(email)){
					showDownError(obj,'邮箱格式不正确');
					isRight =  false;
				}
				else
				{
					jQuery('.msg_error').html('');
				}
				return isRight;
			}
			function popuWindow(){
				var url="yhgl_cxJgdms.html"; 
				window.open ("yhgl_cxJgdms.html", "newwindow", "height=500, width=610, top:280px; left:50%, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
			}

		</script>

<s:form action="/xtgl/yhgl_bcxgYhgl.html" method="post" theme="simple">
	<body onload="loadJsxx();">
     <input type="hidden" name="doType" value="save"/>   
      <input type="hidden" id="jsdm" name="jsdm" value="${model.jsdm}"/>  
       <input type="hidden" id="jsmc" name="jsmc" value="${model.jsmc}"/>    
       <input type="hidden" id="zgh" name="zgh" value="${model.zgh}"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>修改用户</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick=" save();return false;">保 存</button>
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="20%">职工号</th>
	        <td>
	        ${model.zgh }
	         </td>
	     
	     	<th width="20%"><span class="red">*</span>姓名</th>
	        <td width="30%">
	        <div class="pos" style="z-index:2">
	        <s:textfield maxlength="20" name="xm" id="xm" onblur="checkNotNull('xm')" 
	         onfocus="showMessage('xmMessage','hide');" cssStyle="width:154px" >
	        </s:textfield> 
	        
	        <div id="xmMessage" class="hide">
							<div class="prompcon" >
								<p>
									<p>
									<s:text name="W99001">
										<s:param>姓名</s:param>
									</s:text>
								</p>
								</p>
							</div>
			</div>
	        </div>
	        </td>
	      </tr>

	       <tr>
	        <th width="20%">联系电话</th>
	        <td width="30%"><s:textfield maxlength="20" onfocus="showDownPrompt(this,'只能输入数字');" onkeyup="onyInt(this);" name="lxdh" id="lxdh" cssStyle="width:154px" ></s:textfield> </td>
	    
	   		<th width="20%">Email</th>
	        <td width="30%"><s:textfield maxlength="40" name="dzyx" id="dzyx" onblur="checkEmail(this);"  cssStyle="width:154px" ></s:textfield> </td>
	      </tr>
	      <tr>
	    	<th width="20%"><span class="red">*</span>所属机构</th>
	        <td width="80%" colspan="3">
	         <s:hidden name="jgdm" id="jgdm"></s:hidden>
	         <s:hidden name="cydm_id_bmlb" id="cydm_id_bmlb"></s:hidden>
	         <s:textfield maxlength="20" name="jgmc" id="jgmc" cssStyle="width:130px"></s:textfield>
	         <button onclick="popuWindow();return false;">选择</button>
			</td>
	      </tr>
	      
	      <tr>
	    	<th width="20%">是否启用</th>
	        <td width="80%" colspan="3">
				<s:radio list="#{'0':'否','1':'是'}" id="sfqy" name="sfqy"></s:radio>
			</td>
	      </tr>
	       <tr>
	        <th width="20%" rowspan="${col}">所属角色</th>
	   		<td width="80%" colspan="3">
	   		  <div style="height:100px; overflow-y:auto;">
						<s:iterator value="jsxxList" id="s" status="substa">
									<div style="width:120px;float: left;">
										<input type="checkbox" class="cbvclass" style="cursor: pointer;" id="cbvjsxx" name="cbvjsxx" value="${s.jsdm}" />
										${s.jsmc}
									</div>
						</s:iterator>
			  </div>
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