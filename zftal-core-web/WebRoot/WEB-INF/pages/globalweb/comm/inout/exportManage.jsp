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
		flashQueryResultCondition("exportManageResult", param);
	}
	function onSuperQueryAction() {
		var param = superQueryGetSelectedValueTexts("operateLogSuperQuery");
		//alert(param);
		flashQueryResultCondition("exportManageResult", param);
	}
	//选择用户
	function onSelectUserAction(user) {
		user.value = "0";
	}

	function onClick(trNode) { 
		var codes=new Array("","1"); 
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
	//配置
	function onExport() {
	 
		var url="<%=jsPath %>/inout/inout_addExport.html";  
		//alert(url);    
		document.URL=url;      

	}
	//下载
	function onDownload() {
		 var rowid = getSelectedQueryResultRowData("exportManageResult");
		if (rowid != null) {
			var url="<%=jsPath %>/inout/inout_downloadExport.html";  
			url+="?EXPORT_ID="+rowid.BH;
			//alert(url);  
			document.URL=url;  
		}
	
	} 


</script>
	</head>


	<body>
<table>

			<tr>
				<td>
					<button id="bnt0a" onclick="onExport();">
						<span>配置</span>
					</button>
					<button id="bnt1a" disabled="disabled" onclick="onDownload();">
						<span>导出</span>
					</button>  
					
					
				</td>

			</tr>

		</table>
		<table>

			<tr>
				<td>
					<zf:query id="operateLogQuery" rowSize="3"
						code="operateManageQuery"></zf:query>

				</td>
			</tr>
			
			<tr>
				<td>  

					<zf:queryResult id="exportManageResult"
						code="exportManage" rowCount="0" curPage="0" param="{1=1}"
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
