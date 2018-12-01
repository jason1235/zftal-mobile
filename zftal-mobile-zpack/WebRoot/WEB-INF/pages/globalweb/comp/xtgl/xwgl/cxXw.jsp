<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtwh/xwgl.js"></script>
		<script type="text/javascript">
			jQuery(function(){
				var newsGrid = new NewsGrid();
				loadJqGrid("#tabGrid","#pager",newsGrid);
				bdan();
			})
		</script>
	</head>


	<body>
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
				<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
			<!-- 加载当前菜单栏目下操作 -->

			<div class="searchtab">
				<s:form name="form" method="post" action="/xtgl/xwgl_cxXw.html" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>标题</th>
							<td>
								<input type="text" name="xwbt" id="xwbt"/>
							</td>
							<th>是否发布</th>
							<td>
								<s:select name="sffb" list="sfList" listKey="key"
									listValue="value" id="sffb" headerKey="" headerValue="">
								</s:select>
							</td>
							<th>是否置顶</th>
							<td>
								<s:select name="sfzd" list="sfList" listKey="key"
									listValue="value" id="sfzd" headerKey="" headerValue="">
								</s:select>
							</td>
							<td>
								<div class="btn">
									<button id="search_go"
										onclick="searchResult();return false;">
										查 询
									</button>
									<button onclick="searchReset();">
										重 置
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				</s:form>
			</div>
		</div>

		<div class="formbox">
			<!--<h3 class="datetitle_01">
				<span> 查询结果&nbsp;&nbsp; 
					<logic:notEmpty name="rs">
						<font color="blue">提示：单击表头可以排序</font>
					</logic:notEmpty> 
				</span>
			</h3>
			-->
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</body>
</html>
