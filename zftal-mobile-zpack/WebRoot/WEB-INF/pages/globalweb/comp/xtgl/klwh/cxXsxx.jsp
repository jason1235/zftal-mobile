<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtwh/klwh.js"></script>
		<script type="text/javascript">
			jQuery(function(){
				var klwhGrid = new KlwhGrid();
				loadJqGrid("#tabGrid","#pager",klwhGrid);
				bdan();
			})
		</script>
	</head>

	<body>
			<!-- 按钮 -->
			
			<div class="toolbox">
					<!-- 加载当前菜单栏目下操作     -->
						<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
					<!-- 加载当前菜单栏目下操作 -->
	        </div> 

			<div class="searchtab">
				<s:form name="form" method="post" action="/xtgl/klwh_cxXsxx.html" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							
							<th>年级</th>
							<td>
								<input type="text" name="nj" id="nj" size="12px;"/>
							</td>
							<th>学院</th>
							<td>
								<input type="text" name="xy" id="xy" size="12px;"/>
							</td>
							<th>专业</th>
							<td>
								<input type="text" name="zy" id="zy" size="12px;"/>
							</td>
							<th>班级</th>
							<td>
								<input type="text" name="bj" id="bj" size="12px;"/>
							</td>
							<td>
								<div class="btn">
									<button class="btn_cx" id="search_go"
										onclick="searchResult();">
										查 询
									</button>
								</div>
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
	</body>
</html>
