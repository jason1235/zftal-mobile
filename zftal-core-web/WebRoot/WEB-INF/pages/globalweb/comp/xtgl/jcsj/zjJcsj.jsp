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
	var lx = $('#lxdm').val();
	var dm = $('#dm').val();
	var mc = $('#mc').val();
	var pkValue = lx+dm;
	 if (inputResult() && checkInputNotNull('lxdm!!dm!!mc')){ 
			$.ajax({
					url:"jcsj_valideDm.html",
					type:"post",
					dataType:"json",
					data:{pkValue:pkValue},
					success:function(data){
					if(data!=null){
						showDownError(jQuery('#dm'),'该类型下的代码"'+dm+'"已存在，不能使用！');
						return false;
					}else{
						subForm('jcsj_zjBcJcsj.html')
					}
					}
			});
	}
}

</script>

<s:form action="/xtgl/jcsj_bczjJcsj.html" method="post" theme="simple">
	<body>
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>增加基础数据</span></th>
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
	        <th width="30%"><span class="red">*</span>类型</th>
			<td width="70%">
				<s:select list="lxdmList" listKey="lxdm" listValue="lxmc" name="lxdm"
				id="lxdm" headerKey="" headerValue="" cssStyle="width:150px"></s:select>
			</td>
	   	  </tr>
	      <tr>
	        <th width="30%"><span class="red">*</span>代码</th>
	        <td width="70%">
	       
	        <s:textfield maxlength="20" name="dm" id="dm" onkeyup="onlyNumWords(this);" 
	        onfocus="showDownPrompt(this,'只能输入字母或数字');" cssStyle="width:154px" >
	        </s:textfield>
	         </td>
	     </tr>
	      <tr>
	     	<th width="30%"><span class="red">*</span>名称</th>
	        <td width="70%">
	       
	        <s:textfield maxlength="20" name="mc" id="mc" cssStyle="width:154px">
	        </s:textfield> 
	      
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