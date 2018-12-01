
var $path="/xsgzgl/";
//load object by on page action
function flashQueryResultOnPage(queryResultId) {
	//alert('a'); 
	var urlStr = $path+"query/query_flashQueryResult.html"; 
	var arrayId = new Array("curPage", "rowCount", "orderBy", "param", "code");
	var arrayValue = getQueryEnvValue(queryResultId, arrayId);
	var curPage = arrayValue[0];
	var rowCount = arrayValue[1];
	var orderBy = arrayValue[2];
	var param = arrayValue[3];
	var code = arrayValue[4];
	jQuery.post(urlStr, {
		QUERY_RESULT_ID : queryResultId,  
		QUERY_RESULT_CODE : code,
		CUR_PAGE : curPage,
		ROW_COUNT : rowCount,
		PARAM : param,
		ORDER_BY : orderBy
	}, function(result) {
		// alert(result);
			jQuery('#_' + queryResultId).html(result);
		}, 'html');
}
//load object by on page action
function flashQueryResultByPage(queryResultId,targetPage) {
	//alert(targetPage);    
	if(targetPage<1){
		alert('非法页['+targetPage+']！');
		return;
	}
	var urlStr = $path+"query/query_flashQueryResult.html"; 
	var arrayId = new Array("curPage", "rowCount", "orderBy", "param", "code");
	var arrayValue = getQueryEnvValue(queryResultId, arrayId);
	var curPage = targetPage;
	var rowCount = arrayValue[1];
	var orderBy = arrayValue[2];
	var param = arrayValue[3];
	var code = arrayValue[4];
	//if the target page equal the curent page ,just return.
	if(arrayValue[0]==targetPage){
		return;
	}
	//check if the curPage is the last page,if so return alert error!
	var dd=document.getElementById('lastPage_'+ queryResultId);
	//alert(dd);
	if(dd!=null)
	{
		
		var lastPage=dd.innerHTML;
		//alert(arrayValue[0]+" "+targetPage+" "+lastPage);
		if(targetPage>lastPage){
			//alert('已经是最后一页了！');
			return ; 
		}
	}
	//do the target for the cur page,is using the ajax for asynchronism!
	setQueryEnvValue(queryResultId, new Array("curPage"),new Array(targetPage+""));
	jQuery.post(urlStr, {
		QUERY_RESULT_ID : queryResultId,
		QUERY_RESULT_CODE : code,
		CUR_PAGE : curPage,
		ROW_COUNT : rowCount,
		PARAM : param,
		ORDER_BY : orderBy
	}, function(result) {
		// alert(result);
			jQuery('#_' + queryResultId).html(result);
		}, 'html');
}
// get head value from object
function getQueryEnvValue(queryResultId, arrayId) {
	var arrayValue = new Array();
	var pNode = document.getElementById(queryResultId).parentNode;
	var nodes = pNode.getElementsByTagName("input");
	for ( var i = 0; i < arrayId.length; i++) {
		for ( var j = 0; j < nodes.length; j++) {
			if (arrayId[i] == nodes[j].id) {
				arrayValue[i] = nodes[j].value;
				break;
			}
		}
	}
	return arrayValue;
}

// get head value from object
function setQueryEnvValue(queryResultId, arrayId, arrayValue) {

	var pNode = document.getElementById(queryResultId).parentNode;
	var nodes = pNode.getElementsByTagName("input");
	for ( var i = 0; i < arrayId.length; i++) {
		for ( var j = 0; j < nodes.length; j++) {
			if (arrayId[i] == nodes[j].id) {
				//alert(nodes[j].id+" "+arrayValue[i]);
				nodes[j].value = arrayValue[i];
				break;
			}
		}
	}

}

// load object by on page action
function flashQueryResultOnNextPage(queryResultId, isBefore) {
	var urlStr = $path+"query/query_flashQueryResult.html";
	var arrayId = new Array("curPage", "rowCount", "orderBy", "param", "code");
	var arrayValue = getQueryEnvValue(queryResultId, arrayId);
	var curPage = arrayValue[0];
	var rowCount = arrayValue[1];
	var orderBy = arrayValue[2];
	var param = arrayValue[3];
	var code = arrayValue[4];
	//alert(isBefore);
	if (isBefore) {
		if (curPage <= 1) {
			alert('已经是第一页了!');
			return;
		} else {
			curPage--;
		}
	} else {
		curPage=Number(curPage)+1;
		//check if the curPage is the last page,if so return alert error!
		var dd=document.getElementById('lastPage_'+ queryResultId);
		//alert(dd);
		if(dd!=null)
		{
			var lastPage=dd.innerHTML;
			if(curPage>lastPage){
				alert('已经是最后一页了！');
				return ;
			}
		}
		
	}
	setQueryEnvValue(queryResultId, new Array("curPage"),new Array(curPage+""));
	jQuery.post(urlStr, {
		QUERY_RESULT_ID : queryResultId,
		QUERY_RESULT_CODE : code,
		CUR_PAGE : curPage,
		ROW_COUNT : rowCount,
		PARAM : param,
		ORDER_BY : orderBy
	}, function(result) {
		// alert(result);
			jQuery('#_' + queryResultId).html(result);
		}, 'html');

}
//刷新查询结果：标签编号，json查询字串
function flashQueryResultCondition(queryResultId, param) {     
	var urlStr = $path+"query/query_flashQueryResult.html";
	var arrayId = new Array("curPage", "rowCount", "orderBy", "param", "code");
	var arrayValue = getQueryEnvValue(queryResultId, arrayId);
	var curPage = arrayValue[0];
	var rowCount = arrayValue[1];
	var orderBy = arrayValue[2];

	var oParam = arrayValue[3];
	var code = arrayValue[4];

	if (oParam == param) {
		alert('重复查询!');  
		return;
	}

	setQueryEnvValue(queryResultId, new Array("param"), new Array(param));
	jQuery.post(urlStr, {
		QUERY_RESULT_ID : queryResultId,
		QUERY_RESULT_CODE : code,
		CUR_PAGE : curPage,
		ROW_COUNT : rowCount,
		PARAM : param,
		ORDER_BY : orderBy
	}, function(result) {
		// alert(result);
			jQuery('#_' + queryResultId).html(result);
			
			document.getElementById("lastPage"+queryResultId).innerHTML=document.getElementById("lastPage_"+queryResultId).innerHTML;
			document.getElementById("allRowSize"+queryResultId).innerHTML=document.getElementById("allRowSize_"+queryResultId).innerHTML;			
		}, 'html');

}
// load object by the order by
function flashQueryResultOrderBy(queryResultId, itemCode,aItem) {
	var urlStr = $path+"query/query_flashQueryResult.html";
	var arrayId = new Array("curPage", "rowCount", "orderBy", "param", "code",
			"isASC");
	var arrayValue = getQueryEnvValue(queryResultId, arrayId);
	var curPage = arrayValue[0];
	var rowCount = arrayValue[1];
	var orderBy = arrayValue[2];
	var param = arrayValue[3];
	var code = arrayValue[4];
	var isASC = arrayValue[5];
	
	var aNodes = aItem.parentNode.parentNode.getElementsByTagName("a"); 		
	for(var i=0;i<aNodes.length;i++){
		var aNode=aNodes[i];
		aNode.className="Sort_down";
	}

	if ("true" == isASC) {

		orderBy = itemCode + " DESC";
		setQueryEnvValue(queryResultId, new Array("isASC", "orderBy"),
				new Array("false", orderBy));
		aItem.className="Sort_down";
	} else {
		orderBy = itemCode + " ASC";
		setQueryEnvValue(queryResultId, new Array("isASC", "orderBy"),
				new Array("true", orderBy));
		aItem.className="Sort_up";
	}

	jQuery.post(urlStr, {
		QUERY_RESULT_ID : queryResultId,
		QUERY_RESULT_CODE : code,
		CUR_PAGE : curPage,
		ROW_COUNT : rowCount,
		PARAM : param,
		ORDER_BY : orderBy
	}, function(result) {
		// alert(result);
			jQuery('#_' + queryResultId).html(result);
		}, 'html'); 

}

/**
 * 取得所有非空的值:[允许外部调用]
 */
function getQueryValues(queryId) {

	var query = document.getElementById(queryId);

	var ret = "";
	var inputNode = query.getElementsByTagName("input");
	for ( var i = 0; i < inputNode.length; i++) {
		var nodeItem = inputNode[i];
		var value = nodeItem.value;
		var name = nodeItem.id;
		// alert(nodeItem.type);
		if (nodeItem.type == "button") {
			continue;
		}
		if (value != null && value != "") {
			if (ret == "") {
				ret += name + ":'" + value + "'";
			} else {
				ret += "," + name + ":'" + value + "'";
			}
			//alert(value);
		}
	}
	var selectNode = query.getElementsByTagName("select");
	for ( var i = 0; i < selectNode.length; i++) {
		var nodeItem = selectNode[i];

		var name = nodeItem.id;
		var value = nodeItem.options[nodeItem.selectedIndex].value;
		if (value != null && value != "") {
			if (ret == "") {
				ret += name + ":'" + value + "'";
			} else {
				ret += "," + name + ":'" + value + "'";
			}
		}
	}
  	
	
	ret = "{" + ret + "}";
	return ret;
}
//取得选择的记录行号:返回值是个数组:取元素:list[0],list[1]
function getSelectedQueryResultRows(queryResultId){
	var list =new Array();
	var queryResult = document.getElementById(queryResultId);

	var inputNodes = queryResult.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}

		//alert(nodeItem.checked);
		if (!nodeItem.checked) {
			continue;
		}	
		if (value != null && value != "") {
			list[list.length]=value;
		}
	}
	
	return list;
}
//取得选择的单行记录行号
function getSelectedQueryResultRow(queryResultId){
	var list =new Array();
	var queryResult = document.getElementById(queryResultId);

	var inputNodes = queryResult.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}
		if (nodeItem.id == "all") {
			continue;
		}
		//alert(nodeItem.checked);
		if (!nodeItem.checked) {
			continue;
		}	
		//if (value != null && value != "") {
		//	return value;
		//}
		//(tmp.substring(tmp.length-3,tmp.length)
		var id=nodeItem.id+"";
		//alert(id.substring("row".length,id.length));
		return id.substring("row".length,id.length);
	}
	return -1;
}
//全选：
function queryResultRowAllSelect(queryResultId,checkItem){

	var queryResult = document.getElementById(queryResultId);

	var inputNodes = queryResult.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}


		nodeItem.checked=checkItem.checked;
			
	}
}
//反选：
function queryResultRowRejectSelect(queryResultId){

	var queryResult = document.getElementById(queryResultId);

	var inputNodes = queryResult.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}
		if (nodeItem.id == "all") {
			continue;
		}
		if (nodeItem.checked) {
			nodeItem.checked=false;
		}else{
			nodeItem.checked=true;
		}				
	}
}
//取得指定记录数据:返回值是个JSON对象:json.BH ,json.BM
function getQueryResultData(queryResultId,rowId){
	var ret ="";
	var queryRowResult = document.getElementById(queryResultId+rowId);
	//alert(queryRowResult +" "+queryResultId);
	var labelNodes = queryRowResult.getElementsByTagName("label");
	for ( var i = 0; i < labelNodes.length; i++) {
		var nodeItem = labelNodes[i];
		var value = nodeItem.innerHTML;
		var name = nodeItem.id;

		
		if (name != null&&name!="") {
			if(ret==""){
				ret=name+":'"+value+"'";
			}else{
				ret+=","+name+":'"+value+"'";
			}
		}
		
	}  
	var inputNodes = queryRowResult.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (name != null&&name!="") {
			if(ret==""){
				ret=name+":'"+value+"'";
			}else{
				ret+=","+name+":'"+value+"'";
			}
		}
		
	}
	ret="{"+ret+"}";
	
	return strToJson(ret);
} 
//行操作：
function onQueryResultRowSelect(queryResultRow){



	var inputNodes = queryResultRow.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}
		if (nodeItem.id == "all") {
			continue;
		}
		if (nodeItem.checked) { 
			nodeItem.checked=false;
		}else{
			nodeItem.checked=true;
		}				
	}
}
//把JSON字串转化为JSON对象  
function strToJson(str){    
    var json = eval('(' + str + ')');    
    return json;    
} 
 

//取得选择的单行记录数据
function getSelectedQueryResultRowData(queryResultId){
	var rowId=getSelectedQueryResultRow(queryResultId);
	//alert(rowId);
	if(rowId==-1){
		return strToJson("{}");
	}else{
	return getQueryResultData(queryResultId,rowId); }
}

function showTbody(obj,objTbody,className1,className2,html1,html2){
	if(obj.className==className1){
		obj.className=className2;
		obj.innerHTML=html2;
		document.getElementById(objTbody).style.display="none";
	}else{
		obj.className=className1;
		obj.innerHTML=html1;
		document.getElementById(objTbody).style.display="";
	}
   
}     
//取得选择的单行记录数据
function superQuerySelectAll(superQueryId,allItem){
	var moreQuery=document.getElementById("_"+superQueryId);
	var inputNodes = moreQuery.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}
		if (nodeItem.id == "all") {
			continue;
		}
		if (nodeItem.id == "reject") {
			continue;
		}
		nodeItem.checked=allItem.checked;
						
	}
}
//取得选择的单行记录数据
function superQueryRejectSelect(superQueryId,rejectItem){
	var moreQuery=document.getElementById("_"+superQueryId);
	var inputNodes = moreQuery.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}
		if (nodeItem.id == "all") {
			continue;
		}
		if (nodeItem.id == "reject") {
			continue;
		}
		if (nodeItem.checked) {
			nodeItem.checked=false;
		}else{
			nodeItem.checked=true;
		}				
		
		
		
	}
}
//选定目标
function superQuerySelectItem(superQueryId,selectItem,itemCode,itemName,value,valueText){
	//alert('ok');
	var moreQuery=document.getElementById("_"+superQueryId);  
	var selectResult=document.getElementById(superQueryId+"_");   
	var superQuery=document.getElementById(superQueryId);    
	
	if(selectItem.className=="selectedValue"){     
		return ;
	}else{
		selectItem.className="selectedValue";    
	}
                 	
	var html=selectResult.innerHTML;  
	
	//var aNodes = moreQuery.getElementsByTagName("A")
	
	
	var newHtml = "<dd><a id=\""+itemCode+"\" href=\"javascript:\" onClick=\"superQueryCancelSelected('"+superQueryId+"',this,'"+itemCode+"','"+itemName+"','"+value+"','"+valueText+"');\" >";     
	newHtml+=	"<h5>"+itemName+"</h5>";     
	newHtml+=	"<span id=\"text\" >"+valueText+"</span>";   
	newHtml+=	"<span class='close-icon' title='取消'></span>";       
	newHtml+=	"<input id=\"value\" type='hidden' value='"+value+"'/></a></dd>";  
	selectResult.innerHTML = html+newHtml;        
	//alert(selectResult.parentNode.innerHTML);    
	//document.createElement(newHtml);
	//var newElement=document.createTextNode(newHtml);

	//selectResult.appendChild(newElement);
	//alert(selectItem.innerHTML);        

}
//取选择值JSON串 
function superQueryGetSelectedValueTexts(superQueryId){

	var ret="";
	var selectResult=document.getElementById(superQueryId+"_");
	var superQuery=document.getElementById(superQueryId);    
	var moreQuery=document.getElementById("_"+superQueryId);    
	
	//处理公共内容
	   
	var allValue= document.getElementById("input_"+superQueryId).value;
		
	if(allValue==null||allValue==""){  
		//alert('');	
	}else{
		var checkBoxInputs = moreQuery.getElementsByTagName("input"); 
		
		for(var i=0;i<checkBoxInputs.length;i++){
			var checkBoxInput=checkBoxInputs[i];
			//alert(checkBoxInput.checked); 
			if(checkBoxInput.type=="checkbox"){
				if (checkBoxInput.id == "all") {
					continue;
				}
				if (checkBoxInput.id == "reject") {
					continue;
				}
				if (checkBoxInput.checked) {	
					if(ret!=""){
						ret+=",";
					}		
						ret+=checkBoxInput.id+":'"+allValue+"'";	 
					}
				}
			
		}
	}
	//处理多选内容	
	var aNodes = selectResult.getElementsByTagName("A");      
	for(var i=0;i<aNodes.length;i++){
		var aNode=aNodes[i];
		var value=superQueryGetSelectedValue(aNode);
		var text=superQueryGetSelectedText(aNode); 
		if(ret!=""){
			ret+=",";
		}		
		ret+=aNode.id+":'"+value+"'";	
	}
	
	//处理普通内容
	var inputNodes = superQuery.getElementsByTagName("input");
	for(var i=0;i<inputNodes.length;i++){
		var inputNode=inputNodes[i];
		if(inputNode.id=="value"||inputNode.id=="text"){
			continue;
		}
		//alert(inputNode.id);   
		var value=inputNode.value;
		if(value==null||value==""){
			continue;
		}
		var key=inputNode.id;

		if(ret!=""){
			ret+=",";
		}	
		//if(key.substring(0,1)=="_"){
		//	key=key.substring(1,key.length);
		//}	
		ret+=key+":'"+value+"'";	
	}
	
	//处理单选内容   
	var selectNodes = superQuery.getElementsByTagName("select");
	for(var i=0;i<selectNodes.length;i++){
		var selectNode=selectNodes[i];
		
		var value = selectNode.options[selectNode.selectedIndex].value;   
		
		if(value==null||value==""){  
			continue;
		}
		
		if(ret!=""){
			ret+=",";
		}		
		ret+=selectNode.id+":'"+value+"'";  	     
		
		
		
	}
  	if(ret!=""){
  		var queryCode= document.getElementById("code_"+superQueryId).value;
		ret+=",_code:'"+queryCode+"'"; 
	}	
	return "{"+ret+"}"; 
}

//取出值
function superQueryGetSelectedValue(aNode){

		var inputNode = aNode.getElementsByTagName("input")[0]; 		
		//alert(inputNode);
		return inputNode.value;
		
		var inputNodes = aNode.getElementsByTagName("span"); 		
		for(var i=0;i<inputNodes.length;i++){
			var inputNode=inputNodes[i];
			if(inputNode.id=="value"){
				return inputNode.value; 
			}
		}  
		return "";

}
//取出显示值
function superQueryGetSelectedText(aNode){

		var spanNodes = aNode.getElementsByTagName("span"); 		
		for(var i=0;i<spanNodes.length;i++){
			var spanNode=spanNodes[i];
			if(spanNode.id=="text"){
				return spanNode.innerHTML; 
			}
		}
		return "";
		

}

//取消目标
function superQueryCancelSelected(superQueryId,selectItem,itemCode,itemName,value,valueText){
	
	var moreQuery=document.getElementById("_"+superQueryId);  
	var selectResult=document.getElementById(superQueryId+"_");   
	var superQuery=document.getElementById(superQueryId);    
	
	//目标处理
	var aNodes =superQuery.getElementsByTagName("A");
	for(var i=0;i<aNodes.length;i++){ 
		var aNode=aNodes[i];  
		var aCode=aNode.id;     
		//alert(aCode+"-"+itemCode);      
		if(aCode != itemCode){ 
			continue;
		}
		var aValue=superQueryGetItemValue(aNode);
		  
		//alert(aCode+" "+itemCode +"---"+aValue+" "+value+"+++"+aNode.className);    
		
		if(itemCode==aCode&&aValue==value){
			aNode.className="";
		}		
	}
	//处理已选条件：

	selectResult.removeChild(selectItem.parentNode);   
	
}
//取出值
function superQueryGetItemValue(aNode){

		var inputNode = aNode.getElementsByTagName("input")[0]; 		
		//alert(inputNode);
		return inputNode.value;
		
		var inputNodes = aNode.getElementsByTagName("span"); 		
		for(var i=0;i<inputNodes.length;i++){
			var inputNode=inputNodes[i];
			if(inputNode.id=="value"){
				return inputNode.value; 
			}
		}
		return "";

}
//取出显示值
function superQueryGetItemText(aNode){

		var spanNodes = aNode.getElementsByTagName("span"); 		
		for(var i=0;i<spanNodes.length;i++){
			var spanNode=spanNodes[i];
			if(spanNode.id=="text"){
				return spanNode.innerHTML; 
			}
		}
		return "";
		
}

//是否是数字
function queryIsNumber(str){
	for(var i=0;i<str.length;i++){
		if("0123456789".indexOf(str.charAt(i))==-1){
			return false;
		}
	}
	
	return true;
}
//按回车键查询
function queryByEnterKey(event){
	if(event.keyCode==13){
		alert('');
	}
}

//判断行是否选中:trNode行对象
function queryResultIsRowSelected(trNode){
	return trNode.firstChild.firstChild.checked;  
}
//判断是否有行被选定
function queryResultHasRowSelected(queryResultId){
	var queryResult = document.getElementById(queryResultId);

	var inputNodes = queryResult.getElementsByTagName("input");
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;  

		
		if (nodeItem.type != "checkbox") {
			continue;
		}

		//alert(nodeItem.id+" "+nodeItem.checked);
		if (nodeItem.checked) {
			return true;
		}	
		
	}
	return false;
}
//if single row is selected return true
function isSingleSelected(queryResultId){
	var list =new Array();
	var queryResult = document.getElementById(queryResultId);
	var inputNodes = queryResult.getElementsByTagName("input");
	var isSingleSelected=false;
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}
		if (nodeItem.id == "all") {
			continue;
		} 
		//alert(nodeItem.checked);
		if (!nodeItem.checked) {
			continue;
		}	
		if (isSingleSelected) {
			return false;
		}
		isSingleSelected=true;
		
	}
	
	return isSingleSelected;
}
//if one row is selected return true
function isSelected(queryResultId){
	var list =new Array();
	var queryResult = document.getElementById(queryResultId);
	var inputNodes = queryResult.getElementsByTagName("input");
	var isSelected=false;
	for ( var i = 0; i < inputNodes.length; i++) {
		var nodeItem = inputNodes[i];
		var value = nodeItem.value;
		var name = nodeItem.id;

		
		if (nodeItem.type != "checkbox") {
			continue;
		}
		if (nodeItem.id == "all") {
			continue;
		} 
		//alert(nodeItem.checked);
		if (!nodeItem.checked) {
			continue;
		}	
		isSelected=true;
		
	}
	
	return isSelected;
}