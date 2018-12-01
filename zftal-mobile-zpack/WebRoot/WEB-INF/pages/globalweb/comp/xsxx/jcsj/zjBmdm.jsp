<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
</head>
<script type="text/javascript">
	//添加成功部门代码
	function bcBmdm(){
		var bmdm_id=jQuery("#bmdm_id");
		var bmmc=jQuery("#bmmc");
		var bmlx=jQuery("#bmlx");
		if(bmdm_id.val()==""){
			alert("请填写部门代码!");
			bmdm_id.focus();
			return false;
		}
		if(bmmc.val()==""){
			alert("请填写部门名称!");
			bmmc.focus();
			return false;
		}
		if(bmlx.val()==""){
			alert("请选择部门类型!");
			bmmc.focus();
			return false;
		}
		//jQuery("form [name=bmdm]").submit();
		jQuery('form[name=bmdmForm]').submit();
	}
	
</script>

<s:form name="bmdmForm" action="/xsxx/bmdm_bczjBmdm.html" method="post" theme="simple">
	<body>
            <div class="tab_cur">
				<p class="location">
					<em>您的当前位置:</em><a>学生信息  - 基础数据初始化 - 部门代码添加  </a>
				</p>
			</div>
     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="2"><span>增加部门代码</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="2"><div class="bz">"<span class="red">*</span>"为必填项</div>
	          <div class="btn">
	            <button name="btn_tj" onclick="bcBmdm();return false;">保 存</button>
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	     <tr>
	        <th width="20%"><span class="red">*</span>部门代码</th>
			<td>
				<s:textfield maxlength="15" name="bmdm_id" id="bmdm_id" cssStyle="width:154px" ></s:textfield>
			</td>
	   	  </tr>
	      <tr>
	        <th width="20%"><span class="red">*</span>部门名称</th>
	        <td width="30%"><s:textfield maxlength="50" name="bmmc" id="bmmc" cssStyle="width:154px" ></s:textfield> </td>
	     </tr>
	      <tr>
	     	<th width="20%"><span class="red">*</span>部门类型</th>
	        <td width="30%">
				<select name="bmlx" id="bmlx">
					<option value=""></option>
					<option value="1">学校</option>
					<option value="5">学院</option>
				</select>
			 </td>
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