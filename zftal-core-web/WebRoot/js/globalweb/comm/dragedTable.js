/************************************ dragedTableData.js *******************************/
/*
 * 可以实现表格内容的内部拖动 确保中间过度层的存在，id为指定
 */
/*--------全局变量-----------*/
var dragedTable_x0, dragedTable_y0, dragedTable_x1, dragedTable_y1;
var dragedTable_movable = false;
var dragedTable_preCell = null;
var dragedTable_normalColor = null;  
// 起始单元格的颜色
var dragedTable_preColor = "lavender";
// 目标单元格的颜色
var dragedTable_endColor = "#ffdead";
var dragedTable_movedDiv = "dragedTable_movedDiv";
var dragedTable_tableId = "";

var tempHTML = "<font color=blue>可填充此处</font>";
/*--------全局变量-----------*/
function DragedTable(tableId) {     
	dragedTable_tableId = tableId;
	var oTempDiv = document.createElement("div");
	oTempDiv.id = dragedTable_movedDiv;
	oTempDiv.onselectstart = function() {
		return false
	};
	oTempDiv.style.cursor = "hand";
	oTempDiv.style.position = "absolute";
	oTempDiv.style.border = "1px solid black";
	oTempDiv.style.backgroundColor = dragedTable_endColor;
	oTempDiv.style.display = "none";
	document.body.appendChild(oTempDiv);
	for(var i=0; i<document.all(tableId).tBodies.length; i++){ 
		document.all(tableId).tBodies[i].onmousedown = showDiv;
	}
}
// 得到控件的绝对位置
function getPos(cell) {
	var pos = new Array();
	var t = cell.offsetTop;
	var l = cell.offsetLeft;
	while (cell = cell.offsetParent) {
		t += cell.offsetTop;
		l += cell.offsetLeft;
	}
	pos[0] = t;
	pos[1] = l;
	return pos;
}
// 显示图层
function showDiv() {
	var obj = event.srcElement;
	var pos = new Array();
	// 获取过度图层
	var oDiv = document.getElementById(dragedTable_movedDiv);
	if (obj.tagName.toLowerCase() == "td") {
		obj.style.cursor = "hand";
		pos = getPos(obj);
		// 计算中间过度层位置，赋值
		oDiv.style.width = obj.offsetWidth;
		oDiv.style.height = obj.offsetHeight;
		oDiv.style.top = pos[0];
		oDiv.style.left = pos[1];
		oDiv.innerHTML = obj.innerHTML;
		oDiv.style.display = "";
		dragedTable_x0 = pos[1];
		dragedTable_y0 = pos[0];
		dragedTable_x1 = event.clientX;
		dragedTable_y1 = event.clientY;
		// 记住原td
		dragedTable_normalColor = obj.style.backgroundColor;
		obj.style.backgroundColor = dragedTable_preColor;
		dragedTable_preCell = obj;
		dragedTable_movable = true;
		
		var rows = document.all(dragedTable_tableId).rows;
		for(var i=1;i<rows.length-1;i++){
			var row = rows[i];
			var rowCells = row.cells;
			for(var j=0;j<rowCells.length;j++){
				if(j%2 == 1){
					cell = row.insertCell(j);
					cell.innerHTML = tempHTML;
				}
			}
		}
		rows[0].cells[0].colSpan=rows[1].cells.length;
		rows[rows.length-1].cells[0].colSpan=rows[1].cells.length;
	}
}
function dragDiv() {
	if (dragedTable_movable) {
		var oDiv = document.getElementById(dragedTable_movedDiv);
		var pos = new Array();
		oDiv.style.top = event.clientY - dragedTable_y1 + dragedTable_y0;
		oDiv.style.left = event.clientX - dragedTable_x1 + dragedTable_x0;
		var oTable = document.all(dragedTable_tableId);
		for ( var i = 0; i < oTable.cells.length; i++) {
			if (oTable.cells[i].tagName.toLowerCase() == "td" && oTable.cells[i].className != "noMove") {
				pos = getPos(oTable.cells[i]);
				if (event.x > pos[1]
						&& event.x < pos[1] + oTable.cells[i].offsetWidth
						&& event.y > pos[0]
						&& event.y < pos[0] + oTable.cells[i].offsetHeight) {
					if (oTable.cells[i] != dragedTable_preCell)
						oTable.cells[i].style.backgroundColor = dragedTable_endColor;
				} else {
					if (oTable.cells[i] != dragedTable_preCell)
						oTable.cells[i].style.backgroundColor = dragedTable_normalColor;
				}
			}
		}
	}
}
function hideDiv() {
	if (dragedTable_movable) {
		var oTable = document.all(dragedTable_tableId);
		var pos = new Array();
		if (dragedTable_preCell != null) {
			for ( var i = 0; i < oTable.cells.length; i++) {
				pos = getPos(oTable.cells[i]);
				// 计算鼠标位置，是否在某个单元格的范围之内
				if (event.x > pos[1]
						&& event.x < pos[1] + oTable.cells[i].offsetWidth
						&& event.y > pos[0]
						&& event.y < pos[0] + oTable.cells[i].offsetHeight) {
					if (oTable.cells[i].tagName.toLowerCase() == "td" && oTable.cells[i].className != "noMove") {
						//交换文本
						dragedTable_preCell.innerHTML = oTable.cells[i].innerHTML;
						oTable.cells[i].innerHTML = document.getElementById(dragedTable_movedDiv).innerHTML;
						//清除原单元格和目标单元格的样式
						dragedTable_preCell.style.backgroundColor = dragedTable_normalColor;
						oTable.cells[i].style.backgroundColor = dragedTable_normalColor;
						oTable.cells[i].style.cursor = "";
						dragedTable_preCell.style.cursor = "";
						dragedTable_preCell.style.backgroundColor = dragedTable_normalColor;
					}
				}
			}
		}
		dragedTable_movable = false;
		
		// 清除提示图层
		document.getElementById(dragedTable_movedDiv).style.display = "none";
		
		resetTable();
	}
}
document.onmouseup = function() {
	hideDiv();
	var oTable = document.all(dragedTable_tableId);
	for ( var i = 0; i < oTable.cells.length; i++)
		oTable.cells[i].style.backgroundColor = dragedTable_normalColor;
}
document.onmousemove = function() {
	dragDiv();
}


// 重置table的形状
function resetTable(){
	var oTable = document.all(dragedTable_tableId);
	var cells = oTable.cells;
	var rows = oTable.rows;
	var temp = new Array();
	var countRow = oTable.rows.length;
	var oTbody = document.all("tbodyId");

	var index = 0;
	for(var i=0;i<cells.length;i++){
		if(cells[i].innerHTML.toLowerCase() != tempHTML){
			temp[index++] = cells[i].innerHTML;
		}
	}
	
	for(var i=1;i<countRow-1;i++){
		oTable.deleteRow(1);
	}
	
	var tempRow = oTbody.insertRow();
	
	for(var i=1;i<temp.length-1;i++){
		var tempCell = tempRow.insertCell();
		tempCell.innerHTML = temp[i];
		
		if(tempRow.cells.length == 4){
			tempRow = oTbody.insertRow();
		}
	}
	
	rows[0].cells[0].colSpan=rows[1].cells.length;
	rows[rows.length-1].cells[0].colSpan=rows[1].cells.length;
}

/************************************ dragedTableData.js 结束 *******************************/

