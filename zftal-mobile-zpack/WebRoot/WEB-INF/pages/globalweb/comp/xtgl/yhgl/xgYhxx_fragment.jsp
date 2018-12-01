<%@ page language="java" contentType="text/html; charset=UTF-8"%>
	      <tr>
	        <th width="20%">职工号</th>
	        <td>
	        ${model.zgh }
	         </td>
	     
	     	<th width="20%"><!--<span class="red">*</span>-->姓名</th>
	        <td width="30%"><!--
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
	        -->
	        ${model.xm }
	        </td>
	      </tr>

	       <tr>
	        <th width="20%">联系电话</th>
	        <td width="30%"><s:textfield maxlength="20" onfocus="showDownPrompt(this,'只能输入数字');" onkeyup="onyInt(this);" name="lxdh" id="lxdh" cssStyle="width:154px" ></s:textfield> </td>
	    
	   		<th width="20%">Email</th>
	        <td width="30%"><s:textfield maxlength="40" name="dzyx" id="dzyx" onblur="checkEmail(this);"  cssStyle="width:154px" ></s:textfield> </td>
	      </tr>
	      <s:hidden name="jgdm" id="jgdm" value="0"></s:hidden>
	      <s:hidden name="cydm_id_bmlb" id="cydm_id_bmlb"></s:hidden>
	      <s:hidden name="jgmc" id="jgmc" ></s:hidden>
<%--	      <tr>--%>
<%--	    	<th width="20%"><span class="red">*</span>所属机构</th>--%>
<%--	        <td width="80%" colspan="3">--%>
<%--	         <s:hidden name="jgdm" id="jgdm"></s:hidden>--%>
<%--	         <s:hidden name="cydm_id_bmlb" id="cydm_id_bmlb"></s:hidden>--%>
<%--	         <s:textfield maxlength="20" name="jgmc" id="jgmc" cssStyle="width:130px"></s:textfield>--%>
<%--	         <button onclick="popuWindow();return false;">选择</button>--%>
<%--			</td>--%>
<%--	      </tr>--%>
	      
	      <tr>
	    	<th width="20%">是否启用</th>
	        <td width="80%" colspan="3">
				<s:radio list="#{'0':'否','1':'是'}" id="sfqy" name="sfqy"></s:radio>
			</td>
	      </tr>