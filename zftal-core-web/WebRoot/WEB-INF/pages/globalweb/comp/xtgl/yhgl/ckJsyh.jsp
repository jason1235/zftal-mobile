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
	        	<th colspan="4"><span>查看角色所属用户</span></th>
	        </tr>
	    </thead>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	          <div class="btn">
	            <button name="btn_gb" onclick="closeWin()">关 闭</button>
	          </div></td>
	      </tr>
	    </tfoot>
	    <tbody>
	      <tr>
	        <td width="20%">角色名称</td>
	       <td width="80%">${model.jsmc}</td>
	      </tr>
	      
	       <tr>
		       <td width="20%" rowspan="${col}">所属用户</td>
		       
				 <td>
				 <s:iterator value="yhList" id="s" status="substa">
									<div style="width:100px;float: left;">
										<s:if test="zgh != null && zgh!=''">
											${xm }(${zgh })
										</s:if>
									</div>
									<s:if test="#substa.index != 0 && (#substa.index+1)%4==0">
										</td>
										</tr>
										<tr>
										<td>
									</s:if>
								</s:iterator>
			    			  </td>
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