<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
</head>
<script type="text/javascript">
//保存完成后提示操作信息，关闭当前页面并刷新父页面
function refreshParent(msg,searchId) {
	alert(msg);
	closeWin();
	if (window.dialogArguments && window.dialogArguments.document.getElementById(searchId)) {
		window.dialogArguments.document.getElementById(searchId).click();
	}
}

//添加专业代码
function bcZydm(){
	var bmdm_id=jQuery("#bmdm_id");
	var zymc=jQuery("#zymc");
	var xz=jQuery("#xz");
	if(bmdm_id.val()==""){
		alert("请选择部门代码!");
		bmdm_id.focus();
		return false;
	}
	if(zymc.val()==""){
		alert("请填写专业名称!");
		zymc.focus();
		return false;
	}
	if(xz.val()==""){
		alert("请填写学制!");
		xz.focus();
		return false;
	}
	jQuery("form[name='zydmForm']").submit();
}
</script>

<s:form name="zydmForm" action="/xsxx/zydm_bcxgZydm.html" method="post" theme="simple">
	<body>
            <div class="tab_cur">
				<p class="location">
					<em>您的当前位置:</em><a>学生信息   - 基础数据初始化 - 专业代码修改  </a>
				</p>
			</div>
     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="2"><span>修改专业代码</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="2"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="bcZydm();return false;">保 存</button>
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	     <tr>
	     	<th width="20%"><span class="red">*</span>部门名称</th>
	        <td width="30%">
	        	<s:select name="bmdm_id" id="bmdm_id" list="bmdmModelList" listKey="bmdm_id" listValue="bmmc" cssStyle="width:154px"  headerKey="" headerValue=""></s:select>
	        </td>
	      </tr>
	     <tr>
	        <th width="20%"><span class="red">*</span>专业代码</th>
			<td>
				${model.zydm}
				<input type="hidden" name="zydm" id="zydm" value="${model.zydm}"/>
			</td>
	   	  </tr>
	      <tr>
	        <th width="20%"><span class="red">*</span>专业名称</th>
	        <td width="30%"><s:textfield maxlength="50" name="zymc"  id="zymc" cssStyle="width:154px" ></s:textfield> </td>
	     </tr>
	      <tr>
	     	<th width="20%"><span class="red">*</span>学制</th>
	        <td width="30%"><s:textfield maxlength="1" name="xz"  id="xz" cssStyle="width:154px" ></s:textfield> </td>
	      </tr>
	    </tbody>
	  </table>
  </div>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
  	<script>
  	alert('${result}','',{'clkFun':function(){refershParent()}});
  	</script>
  </s:if>
</body>
</s:form>
</html>