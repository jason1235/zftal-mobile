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
		var codes=new Array("","1","2","3"); 
		if (queryResultIsRowSelected(trNode)) {	
			for(var i=0;i<codes.length;i++){
				//alert("bnt"+i);    
				if(codes[i]==""){    
					document.getElementById('bnt'+i+"a").disabled="disabled";
				}else{
					document.getElementById('bnt'+i+"a").disabled="";
				}   
				
			}    
		} else {
			for(var i=0;i<codes.length;i++){
				if(codes[i]==""){
					document.getElementById('bnt'+i+"a").disabled="";  
				}else{
					document.getElementById('bnt'+i+"a").disabled="disabled";
				}
				
			}
		}

	}   
	//上传
	function onUpload() {
	 
		var url="<%=jsPath %>/inout/inout_addImport.html";  
		//alert(url);    
		document.URL=url;      

	}
	//下载
	function onDownload() {
		 var rowid = getSelectedQueryResultRowData("importManageResult");
		if (rowid != null) {
			var url="<%=jsPath %>/inout/inout_downloadImport.html";  
			url+="?IMPORT_ID="+rowid.BH;
			//alert(url);  
			document.URL=url;  
		}
	
	}
	//执行导入
	function onImport() {
		 var rowid = getSelectedQueryResultRowData("importManageResult");
		if (rowid != null) {
			if(rowid.ZT==17){
				var url="<%=jsPath %>/inout/op_inout_doImport.html";  
				url+="?IMPORT_ID="+rowid.BH;
				//alert(url);  
				document.URL=url;  
			}
			
		}
	
	}
	//更新
	function onReDoImport() {
	 	alert('正在开发...');
		//var url="<%=jsPath %>/inout/inout_addImport.html";  
		//alert(url);    
		//document.URL=url;      

	}
	//下载模板
	function onDownloadClass(){
		var url="<%=jsPath %>/inout/inout_downloadClass.html";  
		//alert(url);    
		url+="?GZZBH=1";
		document.URL=url;     
	
		//alert('正在开发...');
	}
	//导入数据
	function onImportData() {
	 
		var url="<%=jsPath %>/inout/inout_importData.html";  
		//alert(url);   
		url+="?gzzbh=1"; 
		document.URL=url;      

	}
</script>
	</head>


	<body>
		<div class="toolbox">
			<!-- 按钮 -->
			<div class="buttonbox">
				<ul>
					<li>
						<a href="#" id="bnt0a" onclick="onDownloadClass();" class="btn_zj">
							下载模板
						</a>
						
					</li>
					<li>
						<a href="#" id="bnt1a" onclick="onImportData();" class="btn_zj">
							上传文件
						</a>
						
					</li>
					
				</ul>
			</div>
		</div>
	
<table>

			<tr>
				<td>
					<button id="bnt0a" onclick="onUpload();">
						<span>配置</span>
					</button>
					<button id="bnt1a" disabled="disabled" onclick="onDownload();">
						<span>下载</span>
					</button>
					<button id="bnt2a" disabled="disabled" onclick="onImport();">
						<span>导入</span> 
					</button>
					<button id="bnt3a" disabled="disabled" onclick="onReDoImport();">
						<span>更新</span>
					</button>
					
				</td>

			</tr>

		</table>
		<table>

			<tr>
				<td>
					<zf:query id="operateLogQuery" rowSize="3"
						code="operateLogManageQuery"></zf:query>

				</td>
			</tr>
			
			<tr>
				<td>  

					<zf:queryResult id="importManageResult"
						code="importManage" rowCount="0" curPage="0" param="{1=1}"
						orderBy="" head="导入信息" ></zf:queryResult>   
				</td>
			</tr>
			<tr>
				<td>

				

				</td>
			</tr>
		</table>


	</body>
</html>
