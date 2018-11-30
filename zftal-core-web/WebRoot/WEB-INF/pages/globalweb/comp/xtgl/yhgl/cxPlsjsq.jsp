<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtwh/plsjsq.js"></script>
		<script type="text/javascript">
			jQuery(function(){
				var yhglGrid = new YhglGrid();
				loadJqGrid("#tabGrid","#pager",yhglGrid);
			})
		</script>
	</head>

	<body>
			<div class="searchtab">
				<s:form name="form" method="post" action="/xtgl/yhgl_xxx.html" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>角&nbsp;色</th>
							<td>
 								<s:select list="jsxxList" listKey="jsdm" listValue="jsmc" headerKey="" headerValue="全部" id="jsdm" name="jsdm" onchange="selectJsdm();return false;" cssStyle="width:150px;"></s:select>
							</td>
							<th>所属机构</th>
							<td>
								<s:select list="jgdmsList" listKey="bmdm_id" listValue="bmmc" headerKey="" headerValue="全部" id="jgdm" name="jgdm" onchange="selectJgdm();return false;" cssStyle="width:150px;"></s:select>
							</td>
						</tr>
					</tbody>
				</table>
				</s:form>
			</div>
		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
		<div style="text-align: center;padding-top: 20px;">
		 <button onclick="sjsq();return false;">
					  数据授权
		 </button>
		 </div>
	</body>
</html>
