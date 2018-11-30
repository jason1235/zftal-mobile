<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				//if (checkEmail($('#dzyx')) && checkInputNotNull('xm')){ 
				if (checkEmail()){ 
					 subForm('yhgl_xgBcYhxx.html');
				}
			}

			function checkEmail(){
				
				var isRight = true;
				var obj = $('#dzyx');
				if(obj.length==0){
					return true;
				}
				var email=obj.val();
				if (email!='' && !isEmail(email)){
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
			<c:choose>
				<c:when test="${ (empty sessionScope.platform_deploy) || sessionScope.subsystem.sysCode=='hrm_system'}">
					<%@ include file="xgYhxx_fragment.jsp" %>
				</c:when>
				<c:otherwise>
				      <tr>
			      	      <s:hidden name="jgdm" id="jgdm" value="0"></s:hidden>
					      <s:hidden name="cydm_id_bmlb" id="cydm_id_bmlb"></s:hidden>
					      <s:hidden name="jgmc" id="jgmc" ></s:hidden>
				        <th width="20%">职工号</th>
				        <td>${model.zgh }</td>
				     	<th width="20%">姓名</th>
				        <td width="30%">${model.xm }</td>
				      </tr>					
				</c:otherwise>
			</c:choose>
	       <tr>
	        <th width="20%" rowspan="${col}">所属角色</th>
	   		<td width="80%" colspan="3">
	   		  <div style="height:100px; overflow-y:auto;">
						<s:iterator value="jsxxList" id="s" status="substa">
									<div style="width:240px;float: left;">
										<input type="checkbox" class="cbvclass" style="cursor: pointer;" id="cbvjsxx" name="cbvjsxx" value="${s.jsdm}" />
										<%--<c:choose>
											<c:when test="${fn:length(s.jsmc)>7 }">
												<span title="${s.jsmc }">${fn:substring(s.jsmc,0,7)}...</span>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
									--%>
												${s.jsmc }
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