<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib prefix="zf" uri="/zjTag"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/query/query.js"></script>

		<script type="text/javascript">
	function onQueryAction() {
		var param = getQueryValues("operateLogQuery");
		//alert(param);
		flashQueryResultCondition("importManageResult", param);
	}
	function onSuperQueryAction() {
		var param = superQueryGetSelectedValueTexts("operateLogSuperQuery");
		//alert(param);
		flashQueryResultCondition("importManageResult", param);
	}
	//选择用户
	function onSelectUserAction(user) {
		user.value = "0";
	}

	function onClick(trNode) {
		var codes = new Array("", "1", "2", "3");
		if (queryResultIsRowSelected(trNode)) {
			for ( var i = 0; i < codes.length; i++) {
				//alert("bnt"+i);    
				if (codes[i] == "") {
					document.getElementById('bnt' + i + "a").disabled = "disabled";
				} else {
					document.getElementById('bnt' + i + "a").disabled = "";
				}

			}
		} else {
			for ( var i = 0; i < codes.length; i++) {
				if (codes[i] == "") {
					document.getElementById('bnt' + i + "a").disabled = "";
				} else {
					document.getElementById('bnt' + i + "a").disabled = "disabled";
				}

			}
		}

	}

	function onUpload() {
		 
		var url="<%=jsPath %>/inout/op_inout_addExport.html";
		//alert(url);    
		document.URL = url;
	} 
</script>
	</head>


	<body>

		<s:form id="exportForm" action="/inout/op_inout_addExport.html"
			enctype="multipart/form-data"  theme="simple">

			<thead>
				<tr>
					<td>
					
						<button id="bnt0a" onclick=
	"exportForm.submit();"
>
							<span>保存</span>
						</button>


					</td>

				</tr>
			</thead>

			<tbody>
				<tr>
					<td>
						编号
					</td>
					<td>


						<s:textfield name="id" value="" readonly="true"></s:textfield>
					</td>
					<td>
						编码
					</td>
					<td>
						<s:textfield name="code" value="test"></s:textfield>

					</td>

				</tr>
				<tr>

					<td>
						名称
					</td>
					<td>
						<s:textfield name="name" value="测试"></s:textfield>

					</td>
					<td>
						描述
					</td>
					<td>
						<s:textfield name="note" value="测试"></s:textfield>
					</td>

				</tr>
				<tr>

					<td>
						数据转换规则
					</td>
					<td>
						<s:select name="dataChangeId" list="dataChangeSelect" listKey="BH"
							listValue="MC" >
						</s:select>

					</td>
					<td>
						导出表
					</td>
					<td>
						<s:textfield name="target" value=""></s:textfield>
					</td>

				</tr>

			</tbody>

		</s:form>


	</body>
</html>
