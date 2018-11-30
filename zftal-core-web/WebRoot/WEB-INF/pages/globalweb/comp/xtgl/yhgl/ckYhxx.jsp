<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
</head>

<s:form action="/xtgl/yhgl_add.html" method="post" theme="simple">
	<body>
     <input type="hidden" name="doType" value="save"/>       
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>查看用户</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	          <div class="btn">
	            <button name="btn_gb" onclick="iFClose();return false;">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <th width="20%"><span class="red">*</span>职工号</th>
	       <td width="30%">${model.zgh}</td>
	     	<th width="20%"><span class="red">*</span>姓名</th>
	        <td width="30%">${model.xm}</td>
	      </tr>

	       <tr>
	        <th width="20%">联系电话</th>
	        <td width="30%">${model.lxdh}</td>
	   		<th width="20%">Email</th>
	   		<td width="30%">${model.dzyx}</td>
	       </tr>
	      
	       <tr>
	    	 <th width="20%">是否启用</th>
	        <td colspan="3">
				<s:radio list="#{'0':'否','1':'是'}" id="sfqy" name="sfqy"></s:radio>
			</td>
	      </tr>
	      
	       <tr>
		       <th width="20%"><span class="red">*</span>所属角色</th>
		       <td colspan="3">${model.jsmc}</td>
			</tr>
		</table>
	  </div>
	</td>
	        
	</tr>
 </tbody>
</table>
  </div>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
  	<script>
  		refreshParent($('#result').val());
  	</script>
  </s:if>
</body>
</s:form>
</html>