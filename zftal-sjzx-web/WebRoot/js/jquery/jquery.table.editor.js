/**
 * @author 沈鲁威 2013-09-11
 * @依懒 jquery,样式文件table.editor.css
 * @功能说明，该插件提供了一个能自由拆分、合并、插入行、插入列如excel般模式的html表格编辑，是自定义模板的必备利器
 * @api
 * ==========================实例化==========================
 * @demo var tableEditor=$("#editContainer").tableEditor();//editContainer是div
 * ==========================动作==========================
 * @action insertTable	插入表格 		@param rowNum行数 @param colNum列数
 * @action joinTd		合并单元格		鼠标动作会选中要合并的单元格
 * @action splitTd		拆分单元格		鼠标动作会选中要拆分的单元格
 * @action insertCol	插入列			鼠标动作会选中要插入的位置
 * @action insertRow	插入行			鼠标动作会选中要插入的位置
 * @action removeCell	删除单元格		鼠标动作会选中要删除的单元格
 * @action removeTable	删除表格			@param tableId表id
 * @action saveHistory	保存历史			无
 * ==========================获取==========================
 * @query getSelecteds	获取选中单元格	无
 * @query getTableById 获取指定id的表	@param tableId表id
 * @query getTables	获取所有表		无
 * ==========================事件==========================
 * @event ctrlzComplete 				撤销完成事件		无
 * @event ctrlyComplete 				重做完成事件		无
 * @event tableInsertComplete 			表格插入事件		@return @param table
 * @event splitComplete 				拆分完成事件		无
 * @event joinComplete 					合并完成事件		无
 * @event addColComplete 				增加列完成事件	无
 * @event addRowComplete 				增加行完成事件	无
 * @event removeCellComplete 			删除单元格事件	无
 * @event removeTableComplete			删除表事件 		@return @param table
 * ==========================键盘动作 ==========================
 * @key ctrl+z 撤销
 * @key 按ctrl选中多行
 * @key 按shift连选
 */
function TableEditor(container){
	var editor=this;
	var editContainer=$(container);
	//历史队列对象
	function CancelQueue(){
		this.hisqueue=[];
		this.prequeue=[];
		this.i=0;
		this.undo=function() {
			if(this.hisqueue.length<=0){
				return "";
			}
			var html=this.hisqueue.pop();
			//editContainer.find("td.currentSelect").removeClass("currentSelect");
			this.prequeue.push(editContainer.html());
			return html;
		};
		
		this.redo=function push(html){
			if(this.prequeue.length<=0){
				return "";
			}
			var html=this.prequeue.pop();
			//editContainer.find("td.currentSelect").removeClass("currentSelect");
			this.hisqueue.push(editContainer.html());
			return html;
		};
		
		this.saveHistory=function(html){
			this.hisqueue.push(html);
		};
	}
	//历史队列
	var cancelQueue=new CancelQueue();
	//保存历史，内部方法
	var saveHistory = function (){
		cancelQueue.saveHistory(editContainer.html());
	};
	//撤销--内部方法
	var undo = function (){
		var html=cancelQueue.undo();
		if(html!=""){
			editContainer.html(html);
		}
	};
	//重做--内部方法
	var redo = function (){
		var html=cancelQueue.redo();
		if(html!=""){
			editContainer.html(html);
		}
	};
	//键盘事件监听
	$(document).keydown(function(event){
		 if(event.ctrlKey&&event.keyCode==90) {//ctrl+z
			 undo();
			 if(editor.ctrlzComplete!=null){editor.ctrlzComplete();};
		 }
		 if(event.ctrlKey&&event.keyCode==89) {//ctrl+y
			 redo();
			 if(editor.ctrlyComplete!=null){editor.ctrlyComplete();};
		 }
	});
	//全局变量
	var startTd=null;
	var tableIndex=0;
	//单元格选取动作
	editContainer.find("td").live("mousedown",function (e){
		if(!$(e.target).is("td")){
			return;
		}
		var table=$(this).closest("table");
		var otherTables=editContainer.find(".currentSelect").closest("table");
		for ( var i = 0; i < otherTables.length; i++) {
			if(table[0]==otherTables[i]){
				continue;
			}
			$(otherTables[i]).find("td").removeClass("currentSelect");
		}
		if(!e.shiftKey){
			if(!e.ctrlKey){
				table.find("td").removeClass("currentSelect");
				startTd=$(this);
				$(this).addClass("currentSelect");
			}else{
				if(!$(this).hasClass("currentSelect")){
					$(this).addClass("currentSelect");
				}else{
					$(this).removeClass("currentSelect");
				}
			}
		}if(e.shiftKey){
			table.find("td").removeClass("currentSelect");
			if(startTd==null){
				return;
			}
			var endTd=$(this);
			var startRow=parseInt(startTd.attr("rowIndex"));
			var endRow=parseInt(endTd.attr("rowIndex"));
			var startCol=parseInt(startTd.attr("colIndex"));
			var endCol=parseInt(endTd.attr("colIndex"));
			
			if(startRow>endRow){
				var row=endRow;
				endRow=startRow;
				startRow=row;
			}
			
			if(startCol>endCol){
				var col=endCol;
				endCol=startCol;
				startCol=col;
			}
			
			for(var row=startRow;row<=endRow;row++){
				for(var col=startCol;col<=endCol;col++){
					$(table).find("td[rowIndex="+row+"][colIndex="+col+"]").addClass("currentSelect");
				}
			}
		}
	});
	
	//重置行索引，辅助方法
	function tableReRowIndex(table){
		var trs=table.find("tr");
		var rowIndex=0;
		for(var i=0; i<trs.length; i++){
			$(trs[i]).attr("rowIndex",rowIndex);
			var tds=$(trs[i]).find("td");
			if(tds.length==0){
				$(trs[i]).remove();
			}else{
				for(var j=0; j<tds.length; j++){
					var td=$(tds[j]);
					td.attr("rowIndex",rowIndex);
					//td.attr("colIndex",j);
				}
				rowIndex++;
			}
		}
	}
	//重设跨行，辅助方法
	function tableReRowSpan(table){
		var trs=table.find("tr");
		var tds=null;
		var minRowspan=null;
		for(var i=0; i<trs.length; i++){
			tds=$(trs[i]).find("td");
			for(var j=0; j<tds.length; j++){
				var td=$(tds[j]);
				var rowspan=td.attr("rowspan")==null?1:parseInt(td.attr("rowspan"));
				if(minRowspan==null){
					minRowspan=rowspan;
				}
				if(rowspan<minRowspan){
					minRowspan=rowspan;
				}
			}
			
			for(var j=0; j<tds.length; j++){
				var td=$(tds[j]);
				var rowspan=td.attr("rowspan")==null?1:parseInt(td.attr("rowspan"));
				td.attr("rowspan",rowspan-minRowspan+1);
			}
		}
	}
	//===================回调函数====================//
	this.ctrlzComplete=null;				//撤销完成事件
	this.ctrlyComplete=null;				//重做完成事件
	this.tableInsertComplete=null;			//表格插入事件		 回参 table
	this.splitComplete=null;				//拆分完成事件		 
	this.joinComplete=null;					//合并完成事件		 
	this.addColComplete=null;				//增加列完成事件
	this.addRowComplete=null;				//增加行完成事件	
	this.removeCellComplete=null;			//删除单元格事件
	this.removeTableComplete=null;			//删除表事件 			回参 table
	//===================取元素====================//
	this.getSelecteds = function (){		//获取选中
		return editContainer.find(".currentSelect");
	};
	this.getTableById = function (tableId){	//获取指定表
		return editContainer.find("table[id="+tableId+"]");
	};
	this.getTables = function (){			//获取所有表
		return editContainer.find("table");
	};
	//===================动作====================//
	this.saveHistory=saveHistory;			//保存历史
	this.undo=undo;							//撤销
	this.insertTable = function (){			//插入表格
		var insertTableWidowHtml=
			"<table width=\"100%\" class=\"formlist\" border=\"0\" cellspacing=\"0\""+
			"	cellpadding=\"0\">"+
			"	<tbody>"+
			"		<tr>"+
			"			<th width=\"12%\">行数</th>"+
			"			<td>"+
			"			<input id=\"rowNum\" value=\"10\">"+
			"			</td>"+
			"		</tr>"+
			"		<tr>"+
			"			<th width=\"12%\">列数</th><td><input id=\"colNum\" value=\"4\"/></td>"+
			"		</tr>"+
			"	</tbody>"+
			"	<tfoot>"+
			"		<tr>"+
			"			<td colspan=\"2\" align=\"right\">"+
			"				<a id=\"save_button\">确定</a>"+
			"			</td>"+
			"		</tr>"+
			"	</tfoot>"+
			"</table>";
		var insertTableWindow = editShowWindow("新增表",$(insertTableWidowHtml),300,200,function(){});
		$(insertTableWindow).find("#save_button").button();
		$(insertTableWindow).find("#save_button").click(function(){
			var rowNum = parseInt($(insertTableWindow).find("#rowNum").val());
			var colNum = parseInt($(insertTableWindow).find("#colNum").val());
			saveHistory();
			var table=$("<table id=\""+tableIndex+"\" style=\"width: 100%;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"formlist\"></table>");
			var tbody=$("<tbody></tbody>");
			tableIndex++;
			var tr;
			for(var i=0;i<rowNum;i++){
				tr=$("<tr rowIndex=\""+i+"\"></tr>");
				for(var j=0;j<colNum;j++){
					tr.append("<td rowIndex=\""+i+"\" colIndex=\""+j+"\">&nbsp;</td>");
				}	
				tbody.append(tr);
			}
			table.append(tbody);
			editContainer.append(table);
			$(insertTableWindow).dialog("close");
			if(editor.tableInsertComplete!=null){
				editor.tableInsertComplete(table);
			}
		});
	};
	this.removeTable = function (tableId){	//删除表
		editContainer.find("table[id="+tableId+"]").remove();
		if(editor.removeTableComplete!=null){
			editor.removeTableComplete(table);
		}
	};
	this.removeCell = function (){			//删除单元格
		var table=editContainer.find(".currentSelect").closest("table");
		if(table.length>1){
			edtitorShowWarning("请不要夸表操作!");
			return;
		}
		var tds=table.find(".currentSelect");
		if(tds.length<1){
			edtitorShowWarning("请至少选择一个单元格!");
			return ;
		}
		saveHistory();
		tds.remove();
		if(editor.removeCellComplete!=null){
			editor.removeCellComplete();
		}
	};
	
	this.joinTd = function (){				//合并单元格
		var table=editContainer.find(".currentSelect").closest("table");
		if(table.length>1){
			edtitorShowWarning("请不要夸表操作!");
			return;
		}
		var tds=$(table).find(".currentSelect");
		var minRow=null;
		var maxRow=null;
		var minCol=null;
		var maxCol=null;
		
		for(var i=0; i<tds.length; i++){
			var row=parseInt($(tds[i]).attr("rowIndex"));
			var col=parseInt($(tds[i]).attr("colIndex"));
			var rowspan=$(tds[i]).attr("rowspan")==null?1:parseInt($(tds[i]).attr("rowspan"));
			var colspan=$(tds[i]).attr("colspan")==null?1:parseInt($(tds[i]).attr("colspan"));
			if(minRow==null){
				minRow=row;
			}else{
				if(minRow>row){
					minRow=row;
				}
			}
			if(minCol==null){
				minCol=col;
			}else{
				if(minCol>col){
					minCol=col;
				}
			}
			
			if(maxRow==null){
				maxRow=row+rowspan;
			}else{
				if(maxRow<row+rowspan){
					maxRow=row+rowspan;
				}
			}
			
			if(maxCol==null){
				maxCol=col+colspan;
			}else{
				if(maxCol<col+colspan){
					maxCol=col+colspan;
				}
			}
		}
		
		if(minRow!=null &&maxRow!=null&& minCol!=null&& maxCol!=null){
			saveHistory();
			var minTd=table.find("td[rowIndex="+minRow+"][colIndex="+minCol+"]");
			if(minTd.length==0){
				return;
			}
			minTd.attr("rowspan",maxRow-minRow);
			minTd.attr("colspan",maxCol-minCol);
			
			for(var i=0; i<tds.length; i++){
				if($(tds[i]).attr("rowIndex")==minTd.attr("rowIndex")&&
						$(tds[i]).attr("colIndex")==minTd.attr("colIndex")){
					continue;
				}else{
					$(tds[i]).remove();
				}
			}
			tableReRowIndex(table);
			tableReRowSpan(table);
		}
		if(editor.joinComplete!=null){
			editor.joinComplete();
		}
	};
	
	//拆分单元格
	this.splitTd = function (){					//合并单元格
		var tds=editContainer.find(".currentSelect");
		var trs=null;
		if(tds.length!=1){
			edtitorShowWarning("请选择一个单元格!");
			return ;
		}
		var table=tds.closest("table");
		var td=$(tds[0]);
		var splitWidowHtml=
			"<table width=\"100%\" class=\"formlist\" border=\"0\" cellspacing=\"0\""+
			"	cellpadding=\"0\">"+
			"	<tbody>"+
			"		<tr>"+
			"			<th width=\"12%\">方向</th>"+
			"			<td>"+
			"			<select id=\"direction\">"+
			"				<option value=\"horizontal\">横向</option>"+
			"				<option value=\"vertical\">纵向</option></select>"+
			"			</td>"+
			"		</tr>"+
			"		<tr>"+
			"			<th width=\"12%\">拆分数</th><td><input id=\"splitNum\" value=\"2\"/></td>"+
			"		</tr>"+
			"	</tbody>"+
			"	<tfoot>"+
			"		<tr>"+
			"			<td colspan=\"2\" align=\"right\">"+
			"				<a id=\"save_button\">确定</a>"+
			"			</td>"+
			"		</tr>"+
			"	</tfoot>"+
			"</table>";
		var splitParamWindow = editShowWindow("拆分单元格",$(splitWidowHtml),300,200,function(){});
		$(splitParamWindow).find("#save_button").button();
		$(splitParamWindow).find("#save_button").click(function(){
			saveHistory();
			var direction = $(splitParamWindow).find("#direction").find(":selected").val();
			var splitNum = parseInt($(splitParamWindow).find("#splitNum").val());
			
			if(direction=="horizontal"){
				var tr=td.closest("tr");
				tds=$(tr).find("td");
				var strColIndex=parseInt(td.attr("colIndex"));
				var strRowspan=td.attr("rowspan")==null?1:parseInt(td.attr("rowspan"));
				var strColspan=td.attr("colspan")==null?1:parseInt(td.attr("colspan"));
				for(var i = 0; i< tds.length; i++ ){
					if(tds[i]==td[0]){
						td.attr("colspan",1);
						for(var y=0;y<splitNum-1;y++){
							if(y+1==splitNum-1&&splitNum<=strColspan){
								td.after("<td rowIndex=\""+td.attr("rowIndex")+"\" colIndex=\""+(strColIndex+y+1)+"\" rowspan=\""+strRowspan+"\">&nbsp;</td>");
								td.next().attr("colspan",strColspan-(splitNum-1));
							}else{
								td.after("<td rowIndex=\""+td.attr("rowIndex")+"\" colIndex=\""+(strColIndex+y+1)+"\" rowspan=\""+strRowspan+"\">&nbsp;</td>");
							}
						}
						if(splitNum>strColspan){
							//自推移
							for(var j = i+1; j<tds.length; j++ ){
 								var colIndex=parseInt($(tds[j]).attr("colIndex"));
	 							$(tds[j]).attr("colIndex",colIndex+(splitNum-strColspan));
							}
							//其他行推移
							trs=table.find("tr");
							for(var k = 0; k < trs.length; k++ ){
								if(trs[k]==tr[0]){//同行跳过
									continue;
								}else{
									var targetTds=$(trs[k]).find("td");
									for(var x = 0; x < targetTds.length; x++ ){
										var targetTd=$(targetTds[x]);
										var colspan=targetTd.attr("colspan")==null?1:parseInt(targetTd.attr("colspan"));
										var colIndex=targetTd.attr("colIndex")==null?0:parseInt(targetTd.attr("colIndex"));
										if((colIndex<=strColIndex&&(colspan+colIndex)>=(strColIndex+strColspan))||//包含
												(colIndex>=strColIndex&&(colspan+colIndex)<(strColIndex+strColspan))||//右交叉
												(colIndex<strColIndex&&(colspan+colIndex)>=(strColIndex+strColspan))||//左交叉
												(colIndex>strColIndex&&(colspan+colIndex)<(strColIndex+strColspan))){//被包含//找相同偏移量的的单元格放大splitNum-1格
											targetTd.attr("colspan",colspan+splitNum-1);
											for(var y=x+1;y<targetTds.length;y++){
												var colIndex=parseInt($(targetTds[y]).attr("colIndex"));
					 							$(targetTds[y]).attr("colIndex",colIndex+splitNum-1);
			  								}
											break;
										}
									}
								}
							}
						}
						break;
					}
				}
			}else{
				var strColIndex=parseInt(td.attr("colIndex"));
				var strColspan=td.attr("colspan")==null?1:parseInt(td.attr("colspan"));
// 							var strrowIndex=parseInt(td.attr("rowIndex"));
				var strRowspan=td.attr("rowspan")==null?1:parseInt(td.attr("rowspan"));
				var currentColIndex=strColIndex;
				var tr=td.closest("tr");
				
				td.attr("rowspan",1);
				var currentTr=tr;
				var nextTr=null;
				var nextTds=null;
				var lastTargetTd=null;
				for(var y=0;y<splitNum-1;y++){
					nextTr=currentTr.next();
					if(nextTr.length==0){
						currentTr.after("<tr><td colIndex=\""+currentColIndex+"\" colspan=\""+strColspan+"\">&nbsp;</td></tr>");
						currentTr = currentTr.next();
						lastTargetTd=currentTr.find("td");
					}else{
						//查找是否存在相同偏移量的单元格
						nextTds=nextTr.find("td");
  						var flag=false;
  						for(var w=0;w<nextTds.length;w++){
  							var targetTd=$(nextTds[w]);
  							var colspan=targetTd.attr("colspan")==null?1:parseInt(targetTd.attr("colspan"));
							var colIndex=targetTd.attr("colIndex")==null?0:parseInt(targetTd.attr("colIndex"));
							if((colIndex<=strColIndex&&(colspan+colIndex)>=(strColIndex+strColspan))||//包含
									(colIndex>=strColIndex&&(colspan+colIndex)<(strColIndex+strColspan))||//右交叉
									(colIndex<strColIndex&&(colspan+colIndex)>=(strColIndex+strColspan))||//左交叉
									(colIndex>strColIndex&&(colspan+colIndex)<(strColIndex+strColspan))){//被包含
								flag=true;
								break;
							}
  						}
  						//存在相同偏移量的单元格
  						if(flag){
  							//在本行后再增加一行
  							currentTr.after("<tr><td colIndex=\""+currentColIndex+"\" colspan=\""+strColspan+"\">&nbsp;</td></tr>");
  							currentTr = currentTr.next();
  							lastTargetTd=currentTr.find("td");
  						}else{//不存在
  							var targetTds=nextTr.find("td");
  							var targetTd=null;
  							if(currentColIndex==0){
  								targetTd=targetTds[0];
  								$(targetTd).before("<td colIndex=\""+currentColIndex+"\" colspan=\""+strColspan+"\">&nbsp;</td>");
  								lastTargetTd=$(targetTd).prev();
  							}else{
  								lastTargetTd=null;
  								for(var i=1; i<targetTds.length;i++){
	  								var targetColIndex=parseInt($(targetTds[i]).attr("colIndex"));
	  								if(currentColIndex<targetColIndex){
	  									targetTd=targetTds[i-1];
		  								$(targetTd).after("<td colIndex=\""+currentColIndex+"\" colspan=\""+strColspan+"\">&nbsp;</td>");
		  								lastTargetTd=$(targetTd).next();
	  									break;
	  								}
	  							}
  								if(lastTargetTd==null){
	  								targetTd=targetTds[targetTds.length-1];
	  								$(targetTd).after("<td colIndex=\""+currentColIndex+"\" colspan=\""+strColspan+"\">&nbsp;</td>");
	  								lastTargetTd=$(targetTd).next();
	  							}
  							}
  							currentTr = nextTr ;
  						}
					}
					if(y+1==splitNum-1&&splitNum<=strRowspan){
						lastTargetTd.attr("rowspan",strRowspan-(splitNum-1));
					}
				}
				tableReRowIndex(table);
				
				if(splitNum>strRowspan){
					//所有和当前单元格行偏移量相同的rowspan+splitNum-1
					var targetTds=$(tr).find("td");
					var targetTd=null;
					for(var j = 0; j<targetTds.length; j++ ){
						if(td[0]==targetTds[j]){
							continue;
						}
						targetTd=$(targetTds[j]);
						var rowspan=targetTd.attr("rowspan")==null?1:parseInt(targetTd.attr("rowspan"));
						targetTd.attr("rowspan",rowspan+(splitNum-strRowspan));
					}
				}
				
				tableReRowSpan(table);
			}
			
			$(splitParamWindow).dialog("close");
			if(editor.splitComplete!=null){
				editor.splitComplete();
			}
		});
	};
	
	this.insertCol = function (){			//插入列
		saveHistory();
		var tds=editContainer.find(".currentSelect");
		if(tds.length!=1){
			edtitorShowWarning("请选择一个单元格!");
			return ;
		}
		
		var table=tds.closest("table");
		var trs=table.find("tr");
		var td=$(tds[0]);
		var strColIndex=parseInt(td.attr("colIndex"));
		for ( var i = 0; i < trs.length; i++) {
			var targetTd=$(trs[i]).find("td[colIndex="+strColIndex+"]");
			if(targetTd.length<=0){
				var currentTds = $(trs[i]).find("td");
				for ( var j = 0; j < currentTds.length; j++) {
					var currentTd = $(currentTds[j]);
					var currentColIndex=parseInt(currentTd.attr("colIndex"));
					if(currentColIndex>strColIndex){
						currentTd.attr("colIndex",currentColIndex+1);
					}
				}
				continue;
			}
			var strRowspan=targetTd.attr("rowspan")==null?1:parseInt(targetTd.attr("rowspan"));
			targetTd.before($("<td rowIndex=\""+targetTd.attr("rowIndex")+"\" colIndex=\""+targetTd.attr("colIndex")+"\""+
					" rowspan=\""+strRowspan+"\">&nbsp;</td>"));
			while(targetTd.length>0){
				var thisColIndex=parseInt(targetTd.attr("colIndex"));
				targetTd.attr("colIndex",thisColIndex+1);
				targetTd=targetTd.next();
			}
		}
		if(editor.addRowComplete!=null){
			editor.addRowComplete();
		}
	};
	
	this.insertRow = function (){				//插入行
		saveHistory();
		var tds=editContainer.find(".currentSelect");
		if(tds.length!=1){
			edtitorShowWarning("请选择一个单元格!");
			return ;
		}
		//var table=tds.closest("table");
		var td=$(tds[0]);
		var currentTr=td.closest("tr");
		currentTr.before(currentTr[0].outerHTML);
		
		var thisRowIndex=parseInt(currentTr.attr("rowIndex"));
		while(currentTr.length){
			var targetTds=currentTr.find("td");
			thisRowIndex=parseInt(currentTr.attr("rowIndex"));
			currentTr.attr("rowIndex",thisRowIndex+1);
			for ( var i = 0; i < targetTds.length; i++) {
				$(targetTds[i]).attr("rowIndex",thisRowIndex+1);
			}
			currentTr=currentTr.next();
		}
		if(editor.addColComplete!=null){
			editor.addColComplete();
		}
	};
}


/**
 * 警告框
 * 
 * @param text
 */
function edtitorShowWarning(text) {
	var html = "<div title=\"警告\">"
			+ "<p><span class=\"ui-icon ui-icon-alert\" "
			+ " style=\"float:left; margin:0 7px 20px 0;\"></span>" + text
			+ "</p></div>";

	$(html).dialog({
		resizable : true,
		modal : true,
		buttons : {
			"确定" : function() {
				$(this).dialog("close");
			}
		}
	});
}

/**
 * 弹窗
 * 
 * @param content
 */
function editShowWindow(title, content, width, height, callBack,buttons) {
	var chtml = $("<div title=\"" + title + "\"></div>");
	chtml.html(content);
	if (width == null) {
		width = 400;
	}
	if (height == null) {
		height = 300;
	}
	var params={};
	var windowResult=$(chtml);
	windowResult.dialog({
		resizable : true,
		bgiframe : true,
		modal : true,
		width : width,
		height : height,
		close : function(event, ui) {
			if (callBack == null) {
				return false;
			}
			params.event=event;
			params.ui=ui;
			callBack(params);
		},
		buttons:buttons
	});
	return windowResult;
}

$.fn.extend({tableEditor : function(){
		var tableEditor=new TableEditor(this);
		return tableEditor;
	}
});