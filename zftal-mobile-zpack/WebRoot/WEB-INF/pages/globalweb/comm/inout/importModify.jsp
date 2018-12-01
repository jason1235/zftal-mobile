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
		 
		var url="<%=jsPath %>/inout/op_inout_modifyImport.html"; 
		//alert(url);    
		document.URL=url;      

	}
</script>   
	</head>


	<body>
		<table>

			<tr>
				<td>
					
					

				</td>

			</tr>

		</table>
		<table>

			<tr>
				<td>
	正在开发

				</td>
			</tr>


		</table>


	</body>
</html>
