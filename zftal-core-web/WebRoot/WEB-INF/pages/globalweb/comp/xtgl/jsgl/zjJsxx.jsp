<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>	
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js//globalweb/comp/xtwh/jsgl.js"></script>
		<script type="text/javascript">
			$(function(){
				var r=$(":radio[name='sfejsq']");
				if(r.length>0){
					r.filter("[value='0']").get(0).checked=true;
				}
			});
			function validateEjsq(){
				return true;
			}
		</script>
		
</head>

<s:form action="/xtgl/jsgl_zjJsxx.html" method="post" theme="simple">
	<body>
     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>增加角色</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="if (inputResult() &&checkInputNotNull('jsmc') && validateEjsq()) subForm('jsgl_zjBcJsxx.html');return false;">保 存</button>
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th><span class="red">*</span>角色名称</th>
	        <td><s:textfield maxlength="20" name="jsmc" id="jsmc" cssStyle="width:145px" ></s:textfield> 
	        </td>
	      </tr>
	      <tr>
	        <th>角色说明</th>
	        <td>
	        <s:textarea name="jssm" id="jssm" rows="4" onfocus="showRightPrompt(this,'对角色进行描述，最大不能超过1000字符');" 
	        onblur="checkTextareaLength('jssm','角色说明',2000);" cssStyle="width:220px;height:80px;">
	        </s:textarea> 
	        <br/>
			&nbsp;
	        </td>
	      </tr>
	      <tr style="display: none;">
	        <th>二级授权</th>
	        <td>
				<s:radio list="#{'0':'否','1':'是'}" id="sfejsq" name="sfejsq"></s:radio>
			</td>
	      </tr>
	    </tbody>
	  </table>
  </div>
 <s:if test="result != null && result != ''">
    <script>
	  alert('${result}', '', {
		'clkFun' : function() {
			refershParent();
	  }
	});
   </script>
</s:if>
</body>
</s:form>
</html>