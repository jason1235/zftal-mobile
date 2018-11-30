<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		
		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/hrm/table.css" type="text/css" media="all" />
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtwh/yhgl.js"></script>
		<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtwh/code.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script>
		<script type="text/javascript">
			jQuery(function(){
				var yhglGrid = new YhglGrid();
				loadJqGrid("#tabGrid","#pager",yhglGrid);
				bdan();
				jQuery("input[name='jgdm']").prev().click(function(){
					codePicker(jQuery(this),"DM_DEF_ORG");
				});
			});
		</script>
	</head>

	<body>
		<div class="toolbox">
				<!-- 加载当前菜单栏目下操作     -->
			<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
				<!-- 加载当前菜单栏目下操作 -->
	      </div> 

			<div class="searchtab">
				<s:form name="form" method="post" action="/xtgl/yhgl_cxYhxx.html" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>用户名</th>
							<td>
								<input type="text" name="zgh" id="zgh"/>
							</td>
							<th>姓名</th>
							<td>
								<input type="text" name="xm" id="xm"/>
							</td>
							<th>是否启用</th>
							<td>
								<select id="sfqy" name="sfqy">
									<option></option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>
							<td>
								<div class="btn">
									<button class="btn_cx" id="search_go"
										onclick="searchResult();return false;">
										查 询
									</button>
								</div>
							</td>
						</tr>
 						<tr>
							<!--<th>所属机构</th>
							<td>
								<%--<s:select list="jgdmsList" id="jgdm" name="jgdm" listKey="bmdm_id" listValue="bmmc" headerKey="" headerValue="全部"></s:select>--%>
								<input type="text" style="width:130px;" class="text_nor text_sel" value="${model.jgmc }" readOnly/>
						  		<input type="hidden" id="jgdm" name="jgdm" class="text_nor" value="${model.jgdm }"/>
							</td>
							-->
							
						</tr>
					</tbody>
				</table>
				</s:form>
			</div>
		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</body>
</html>
