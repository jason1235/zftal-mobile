<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.zfsoft.utils.StringUtil"%>
<%@page import="com.opensymphony.xwork2.util.ValueStack"%>

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

	
	//下载模板
	function onDownloadClass(){
		var url="<%=jsPath %>/inout/inout_downloadClass.html";
		//alert(url);    
		url += "?GZZBH=1";
		document.URL = url;

		//alert('正在开发...');
	}
	//提交表单
	function onSubmitForm() {
		//alert('aa');
		importForm.submit();
		//window.confirm("是否执行导入！", "是", "否");
		if (window.comfirm("是否执行导入！")) {
			importForm.submit();

		}
	}
</script>
	</head>


	<body>
		<table>
			<tbody>
				<tr>
					<td rowspan="4" align="right">
						系统提示
					</td>
					<td>
						XXXXXXXXX
					</td>
				</tr>
				<tr>
					<td>
						XXXXXXXXXXXXX
					</td>
				</tr>
				<tr>
					<td>
						XXXXXXXXXXXXX
					</td>
				</tr>
				<tr>
					<td>
						XXXXXXXXXXXXX
					</td>
				</tr>
			</tbody>
		</table>
		<br />
		<s:form id="importForm" action="/inout/op_inout_uploadImport.html"
			enctype="multipart/form-data" theme="simple">
			<table>


				<tbody>
					<tr>

						<td align="right">
							数据模板下载
						</td>
						<td>

							<button id="bnt0a" onclick="onDownloadClass();">
								<span>下载</span>
							</button>
						</td>
						<td>

						</td>
						<td>

						</td>

					</tr>
					<tr>
						
						<td width="200px;" align="right">
							上传文件
						</td>
						<td>
							<s:hidden name="gzzbh" ></s:hidden>
							<s:file name="uploadFile" accept="*.xls" />
							<button id="bnt0a" onclick="onSubmitForm();">
								<span>导入</span>
							</button>
						</td>


					</tr>
				</tbody>


			</table>
		</s:form>

		<table>
			<tbody>
				<tr>
					<td>
						${message}
					</td>
				</tr>
				<!--  
				<tr>  
					<td>信息导入成功。</td>
				</tr>
				
				<tr>  
					
					<td>信息导入失败，失败记录XX条，<a>请点击下载</a></td>
				</tr>
				-->
			</tbody>
		</table>
		<table>
			<tbody>
				<tr align="right">
					<td align="right">
					
							<button id="bnt0a" onclick=window.close();;>
								<span>关闭</span>
							</button>
					</td>
				</tr>


			</tbody>
		</table>
	</body>
</html>
