
/**
 * 填充行
 * <p>
 * 对表格进行行的填充，在最后进行调用
 * </p>
 * @param totleLength 总行数
 * @param thead 默认为：list_head
 * @param tbody 默认为：list_body
 * @param hasBox 是否含有复选框
 * @return
 */
function fillRows(totleLength, thead, tbody, hasBox) {
	thead = thead || 'list_head';
	tbodyname = tbody || 'list_body';
	
	hasBox=hasBox!=null?hasBox:true;

	var tbody = document.getElementById(tbodyname);
	var rowLength = $("#"+tbodyname+" tr:visible").length;
	var addLength = totleLength - rowLength;
	
	var columns = 0; //列数(不包含checkbox)
	try {
		columns = $("#"+thead+" > tr").children().length-1;
	} catch(e) {}
	
	for (var i = 0; i < addLength; i++) { //添加空行
		var tr = document.createElement("tr");
		
		var td = document.createElement("td");
		if(hasBox){
			td.innerHTML = "<input type='checkbox' disabled='disabled'>";
		}else{
			td.innerHTML="&nbsp;";
		}
		tr.appendChild(td);
		
		for (var j = 0; j < columns; j++) { //添加空列
			var td = document.createElement("td");
			td.innerHTML = "&nbsp;";
			tr.appendChild(td);
		}
		tbody.appendChild(tr);
	}
	
}

