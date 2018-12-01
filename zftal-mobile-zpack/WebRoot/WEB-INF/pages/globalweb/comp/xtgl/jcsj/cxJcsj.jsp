<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtwh/jcsj.js"></script>
		<script type="text/javascript">
			jQuery(function(){
				var jcsjGrid = new JcsjGrid();
				loadJqGrid("#tabGrid","#pager",jcsjGrid);
				bdan();
			})
			
		</script>
	</head>

	<body>
			<div class="toolbox">
					<!-- 加载当前菜单栏目下操作     -->
						<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
					<!-- 加载当前菜单栏目下操作 -->
	        </div> 

			<div class="searchtab">
				<s:form name="form" method="post" action="/xtgl/jcsj_cxJcsj.html" theme="simple">
				<table width="100%" border="0" id="searchTab">
					<tbody>
						<tr>
							<th>类型</th>
							<td>
							<s:select name="lxdm" list="lxdmList" listKey="lxdm"
									listValue="lxmc" id="lxdm" headerKey="" headerValue="">
							</s:select>
								
							</td>
							<th>代码</th>
							<td>
								<input type="text" name="dm" id="dm"/>
							</td>
							<th>名称</th>
							<td>
								<input type="text" name="mc" id="mc"/>
							</td>
							<td>
								<div class="btn">
									<button class="btn_cx" id="search_go"
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

		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</body>
</html>
