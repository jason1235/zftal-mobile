<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script>
</head>
<script type="text/javascript">
	function save(){
		if (inputResult() && checkInputNotNull('zgh!!xm!!mm!!nmm')){
			 subForm('yhgl_zjBcYhxx.html')
		}
	}
	
	function checkZgh(obj){
		var isExists = true;
		var pkValue = $('#zgh').val();
		if (pkValue != ''){
			$.ajax({
					url:"yhgl_valideZgh.html",
					type:"post",
					dataType:"json",
					data:{pkValue:pkValue},
					success:function(data){
					if(data!=null){
						showDownError(obj,'职工号"'+pkValue+'"已存在，不能使用！');
						isExists =  false;
					}
				}
				});
			}
				return isExists;
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

	function checkNmm(obj){
		var isNotnulll = true;
		var mm = $('#mm').val();
		var nmm = $('#nmm').val();
		 if(mm!=nmm){
			showDownError(obj,'两次输入密码不一致！');
			isNotnulll =  false;	
		}
			return isNotnulll;
	}
	
	
	function checkEmail(obj){
		var isRight = true;
		var email = $('#dzyx').val();
		if (email!=''&&!isEmail(email)){
			showDownError(obj,'邮箱格式不正确');
			isRight =  false;
		}else{
			hideDownError(obj);
			}
		return isRight;
	}
	
	function popuWindow(){
		var url="yhgl_cxJgdms.html"; 
		window.open ("yhgl_cxJgdms.html", "newwindow", "height=500, width=610, top:280px; left:50%, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
		//showWindow('增加用户',500,610,'yhgl_cxJgdms.html');
	}
	
</script>

<s:form action="/xtgl/yhgl_zjBcYhxx.html" method="post" theme="simple">
	<body>
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>增加用户</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="save();return false;">保 存</button>
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="20%"><span class="red">*</span>职工号</th>
	        <td width="30%">
	       
	        <s:textfield maxlength="20" name="zgh" id="zgh" onkeyup="isNotChar(this);" cssStyle="width:154px" onblur="checkZgh(this);"
	         onfocus="showDownPrompt(this,'只能输入字母或数字');"></s:textfield> 
	       </td>
	     
	     	<th width="20%"><span class="red">*</span>姓名</th>
	        <td width="30%">
	        <s:textfield maxlength="20" name="xm" id="xm" cssStyle="width:154px" 
	      
	       	>
	        </s:textfield>
	         </td>
	      </tr>
	      

	       <tr>
	        <th width="20%">联系电话</th>
	        <td width="30%"><s:textfield maxlength="20" onfocus="showDownPrompt(this,'只能输入数字');" onkeyup="onyInt(this);" name="lxdh" id="lxdh" cssStyle="width:154px" ></s:textfield> </td>
	    
	   		<th width="20%">Email</th>
	        <td width="30%">
	        <s:textfield maxlength="40" name="dzyx" id="dzyx" onblur="checkEmail(this);" 
	        cssStyle="width:154px" ></s:textfield>

	        </div>
	         </td>
	      </tr>
	      
	       <tr>
	        <th width="20%"><span class="red">*</span>登录密码</th>
	        <td width="30%">
	        <s:password maxlength="20" name="mm" id="mm" cssStyle="width:154px"  onblur="checkMm(this)"
	         onfocus="showDownPrompt(this,'密码不能为空,并且长度不能小于6位');"></s:password> 
	        
	        </td>
	        
	         <th width="20%"><span class="red">*</span>重复密码</th>
	        <td width="30%">
	        <s:password maxlength="20" name="nmm" id="nmm" cssStyle="width:154px"  onblur="checkNmm(this)"
	         onfocus="showDownPrompt(this,'密码不能为空,并且长度不能小于6位');"></s:password> 
	        
	        </td>
	        </tr>
	        <s:hidden name="jgdm" id="jgdm" value="0"></s:hidden>
	        <s:hidden name="cydm_id_bmlb" id="cydm_id_bmlb"></s:hidden>
	        <s:hidden name="jgmc" id="jgmc" ></s:hidden>
<%--	        <tr>--%>
<%--		    	<th width="20%"><span class="red">*</span>所属机构</th>--%>
<%--		        <td width="80%" colspan="3">--%>
<%--		         <s:hidden name="jgdm" id="jgdm" value="0"></s:hidden>--%>
<%--		         <s:hidden name="cydm_id_bmlb" id="cydm_id_bmlb"></s:hidden>--%>
<%--		         <s:textfield maxlength="20" name="jgmc" id="jgmc" cssStyle="width:130px"></s:textfield>--%>
<%--		         <button onclick="popuWindow();return false;">选择</button>--%>
<%--				</td>--%>
<%--		    </tr>--%>
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