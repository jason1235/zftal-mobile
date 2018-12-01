<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%> 
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dragedTable.js"></script>
		<script type="text/javascript">
		function showTbody(obj,objTbody,className1,className2,html1,html2){
			if(obj.className==className1){
				obj.className=className2;
				obj.innerHTML=html2;
				//obj.parentNode.parentNode.className="cur-tr";
				document.getElementById(objTbody).style.display="none";
			}else{
				obj.className=className1;
				obj.innerHTML=html1;
				//obj.parentNode.parentNode.className="";
				document.getElementById(objTbody).style.display="";
			}
		}
	
		//应用
		function yy(zdmc){
			var yy_id = "yy_" + zdmc;		
			var li_id = "li_" + zdmc;
			var sp_id = "sp_" + zdmc;
			var ra_id = "ra_" + zdmc;

			var zdsm = $(sp_id).innerHTML;
			
			var node = document.getElementById("ul_mkms");    

			if($(yy_id).checked){
		    	var li = document.createElement("li");   
		    	var value = "<input name='chyh' type='radio' id="+ra_id+" value='"+zdmc+"'/><label>&nbsp;"+zdsm+"</label>";
		    	value += "<input name='zds' value='" + zdmc + "' type='hidden'/>";
		    	value += "<input name='zdsms' value='" + zdsm + "' type='hidden'/>";
		    	li.innerHTML = value;
		    	li.id = li_id;
		        node.appendChild(li);
			}else {
				if($(li_id)){
					node.removeChild($(li_id));
				}
			} 
       }

	   function delDczd(){
		 var lis = document.getElementsByName('chyh');
		 var flag = false;
		 var zdmc = "";
		 var node = document.getElementById('ul_mkms');
		 
		 if(lis != null && lis.length>0){
			for(var i=0;i<lis.length;i++){
				if(lis[i].checked){
					flag = true;
					zdmc = lis[i].value;
				}
			}
		 }

		 if(!flag){
			alertInfo("没有选择要删除的导出字段！");
		 }else {

			if(confirm("确认删除该导出字段？")){
				var li_id = "li_" + zdmc;
				var yy_id = "yy_" + zdmc;
	
				$(yy_id).checked = "";
				node.removeChild($(li_id));
			}
		 }
	   }

	 //向上
	 function yhup(){
		var node = document.getElementById('ul_mkms');
		var lis = node.getElementsByTagName("li");
		var ras = document.getElementsByName('chyh');

		var index = -1;
		var temp = new Array();
		
		for(var i=0; i<lis.length; i++){
			temp[i] = lis[i];
		}

		for(var i=0; i<ras.length; i++){
			if(ras[i].checked){
				index = i;
				break;
			}
		}
		
		if(index>0){
			for(var i=0; i<temp.length; i++){
				node.removeChild(temp[i]);
			}
			
			var obj = temp[index];
			temp[index] = temp[index-1];
			temp[index-1] = obj;

			for(var i=0; i<temp.length; i++){
				node.appendChild(temp[i]);
			}

			ras[index-1].checked = "checked";
		}else if(index == -1){
			alertInfo('请选择要上移的字段！');
		}else{
			alertInfo('以处在第一项，不可再进行上移操作！');
		}

		
     }

     function yhdown(){
    	var node = document.getElementById('ul_mkms');
 		var lis = node.getElementsByTagName("li");
 		var ras = document.getElementsByName('chyh');

 		var index = -1;
 		var temp = new Array();
 		
 		for(var i=0; i<lis.length; i++){
 			temp[i] = lis[i];
 		}

 		for(var i=0; i<ras.length; i++){
 			if(ras[i].checked){
 				index = i;
 				break;
 			}
 		}
 		
 		if(index<lis.length-1 && index != -1){
 			for(var i=0; i<temp.length; i++){
 				node.removeChild(temp[i]);
 			}
 			
 			var obj = temp[index];
 			temp[index] = temp[index+1];
 			temp[index+1] = obj;

 			for(var i=0; i<temp.length; i++){
 				node.appendChild(temp[i]);
 			}

 			ras[index+1].checked = "checked";
 		}else if(index == -1){
 			alertInfo('请选择要下移的字段！');
 		}else{
 			alertInfo('以处在最后一项，不可再进行下移操作！');
 		}
     }

 	 function checAll(ch){
		var checs = document.getElementsByName('ch_zdmc');
		//var ch = $('che');

		if(ch.checked){
			for(var i=0; i<checs.length; i++){
				if(!checs[i].checked){
					checs[i].checked = "checked"
<%--					yy(checs[i].value);--%>
				}
			}
			$('rev').checked="";
		}else {
			for(var i=0; i<checs.length; i++){
				if(checs[i].checked){
					checs[i].checked = ""
<%--					yy(checs[i].value);--%>
				}
			}
		}
 	 }

 	 function revAll(che){
 		var checs = document.getElementsByName('ch_zdmc');
 		//$('che').checked="";
		for(var i=0; i<checs.length; i++){
			checs[i].checked = (checs[i].checked ? "" : "checked");
<%--			yy(checs[i].value);--%>
		}
 	 }

 	function init(){
 	//onSave();
 		//注册可拖拽表格
 		new DragedTable("tableId");   
 		//alert(document.all("tableId").rows.length);
 		
 	}
 	function onSave(){
 		var cells=document.getElementById("tableId").cells;
 		for(var i=0 ;i<cells.length;i++){
 			var cell=cells[i];
 			alert(cell.innerHTML);  
 		}
 	}
	</script>
	
	</head>
	<body onload="init();">
		<s:form action="op_inout_saveExportConfig" method="post"  theme="simple">
		<input type="hidden" name="tableName" value="${tableName }"/>
		
			
		<div class="prompt">
	       <h3><span>系统提示：</span></h3>
	       <p>对需要导出的字段可以进行排序操作，即鼠标点击某个需要调整顺序的字段，直接拖动该字段所在的单元格至需要放的位置,导出Excel时将以排好的顺序导出！<br/></p>
	<%--       <a class="close" title="隐藏" onclick="hidParent(this);"></a>--%>
	   	</div>
			<!-- 伸缩按钮 -->
			<div class="opencon" id="myTbody">
				<table border="0" class="formlist" id="tableId" width="95%">
					<thead>
						<tr>
							<th colspan="4">
								<span>数据字段</span>
								
							</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4" class="noMove" id="footId">
								<div class="choose">    
									<input name="全选" id="che" type="checkbox" value="全选" onclick="checAll(this);"/>
									全  选
									<input name="反选" id="rev" type="checkbox" value="反选" onclick="revAll(this);" />
									反  选
								</div>
								<div align="right">    
								<button name="保存" class="" type="submit">
									保  存
								</button>     
								<button name="返回" class="" onclick="window.history.back();" >
									返  回
								</button>
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody id="tbodyId">
				
				<s:iterator value="fields" status="rowstatus">
				  
				    <s:if test="#rowstatus.index % 4 == 0">
				      <tr>
				    </s:if>
				     <td >
				     	<input name="ch_zdmc" type="checkbox" value="aaa"/>
				     	
				     	<s:property value="key"/>
				     </td>
				     
				    
					 <s:if test="#rowstatus.index % 4 == 3">
				      </tr>
				    </s:if>
				  
				</s:iterator>


					</tbody>
				</table>
			</div>

		</s:form>

	</body>
</html>

