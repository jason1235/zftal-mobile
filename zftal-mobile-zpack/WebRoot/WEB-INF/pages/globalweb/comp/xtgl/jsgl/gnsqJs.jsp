<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	 <script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>		
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script> 
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtwh/jsgl.js"></script>
	<script type="text/javascript">
		function back(){
            var doType  = jQuery('#doType').val();
            var sffpyh  = jQuery('#sffpyh_qry').val();
			var jsmc  = jQuery('#jsmc_qry').val();
			var gnmkdm = jQuery('#gnmkdm_qry').val();
            var content = '<form id="form3" method="post" action="jsgl_cxJsxx.html">';
            content +='        <input type="hidden" name="doType" value="" />';
            content +='        <input type="hidden" name="sffpyh" value="' + sffpyh + '" />';
            content +='        <input type="hidden" name="jsmc" value="' + jsmc + '" />';
            content +='        <input type="hidden" name="gnmkdm" value="' + gnmkdm + '" />';
            content +='   </form>';
            jQuery('body').append(content);
            jQuery('#form3').submit();
            jQuery('#form3').remove();
        } 	
	</script>
	</head>
	
	<body >
		<input type="hidden" name="doType" id="doType" value="${model.doType }"/>
		<input type="hidden" name="sffpyh_qry" id="sffpyh_qry" value="${sffpyh_qry }"/>
		<input type="hidden" name="jsmc_qry" id="jsmc_qry" value="${jsmc_qry }"/>
		<input type="hidden" name="gnmkdm_qry" id="gnmkdm_qry" value="${gnmkdm_qry }"/>
			<div class="toolbox">
				<div class="buttonbox">	
					<ul>
						<s:if test="sfejsq==true">
							<li><a href="#" class="btn_zj" id="btn_zj" onclick="saveData();return false;">保存</a></li>
							<li><a href="#" class="btn_qx" id="btn_qx" onclick="selectAll();return false;">全选</a></li>
							<li><a href="#" class="btn_sx" id="btn_sx" onclick="czBtn();return false;">重置</a></li>
						</s:if>
						<li><a href="#" class="btn_fh" id="btn_fh" onclick="back();">返回</a></li>
					</ul>
				</div>
			</div>	
				
		    <s:if test="sfejsq==true">
			 	<table style="width: 795px" border="0" class="formlist">
					
						<tbody>
							<tr>
								<th width="15%">角色名称</th>
								<td width="30%">
									${model.jsmc}
								</td>
								<th width="15%">已分配用户数</th>
								<td>
									${model.yhnum}
								</td>
							</tr>
						</tbody>		
				</table>
			</s:if>
 		
 		<s:if test="sfejsq==true">
			<s:include value="/WEB-INF/pages/globalweb/comp/xtgl/jsgl/gnCd.jsp"></s:include>
		</s:if>
		<div id="tmpdiv1"></div>
		
	</body>
</html>
