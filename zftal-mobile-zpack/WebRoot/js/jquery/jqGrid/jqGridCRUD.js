/*
 * jqGrid 封装jqGrid基类,依赖prototype.js
 */
BaseJqGrid = Class.create({
	datatype : "json", // 将这里改为使用JSON数据
	mtype : 'POST',
	// autoheight:true,
	height : 235,
	// scroll: true,
	autowidth : true, // 自动调整宽度
	// pager: "pager1", //分页工具栏
	rowNum : 10, // 每页显示记录数
	viewrecords : true, // 是否显示行数
	rowList : [10, 20, 30, 50, 100], // 可调整每页显示的记录数
	multiselect : true, // 是否支持多选
	// loadonce:true, //一次加载所有数据
	onSortCol : function(index, colindex, sortorder) {
		// 列排序事件
		// alert('onSortCol index=>'+index +" colindex=>"+colindex +"
		// sortorder=>"+sortorder);
	},
	gridview : true,
	jsonReader: {      
		root: "items",
		page: "currentPage",
		total: "totalPage",
		records: "totalResult",    
		repeatitems : false      
	},
	prmNames : {
		page : "queryModel.currentPage",
		rows : "queryModel.showCount",
		order : "queryModel.sortOrder",
		sort : "queryModel.sortName"
	},
	userDataOnFooter : false, // 总计
	//altRows : true, //
	footerrow : false
	/*loadError: function(xhr,status,error){  
       alert("数据加载异常,请重试!");    
	}*/
});


/**
 * 加载JqGrid表格
 * @param tableID : jqGrid列表id
 * @param pagerId : jqGrid列表pager分页导航id
 * @param obj : jqGrid列表对象,如userGrid
 * @param percent : jqGrid列表宽度显示的百分比,非必填
 * @param width : jqGrid列表宽度减去的宽度,非必填
 */
function loadJqGrid(tableID, pagerId, obj, percent, width) {
	jQuery(tableID).jqGrid(obj).navGrid(pagerId, {
		del : false,
		add : false,
		edit : false,
		search : false,
		refresh : true
	}, {}, {}, {}, {
		multipleSearch : true
	});
}
function loadYhfpJqGrid(tableID, pagerId, obj, percent, width) {
	jQuery(tableID).jqGrid(obj).navGrid(pagerId, {
		del : false,
		add : false,
		edit : false,
		search : false,
		refresh : false
	}, {}, {}, {}, {
		multipleSearch : true
	});
}



/**
 * 刷新结果集
 * @param tabId
 */
function refershGrid(tabId) {
	jQuery("#"+tabId).jqGrid().trigger('reloadGrid');
}

/**
* 通用查询脚本
* @param tabId
* @param jsonMap
*/
function search(tabId,jsonMap) {
	
	//jQuery.ajaxSettings.traditional = true;
	jQuery("#"+tabId).jqGrid('setGridParam',{  
	     postData : jsonMap
	 }).trigger('reloadGrid');
}

/**
 * 重置查询条件
 */
function searchReset(){
	
}


/**
 * 获取jqGrid中的选中行
 */
function getChecked() {
	return jQuery("#tabGrid").jqGrid('getGridParam', 'selarrrow');
}


/**
 * 批量操作
 * @param url
 * @param msg
 */
function plcz(url,msg){
	var ids = getChecked();

	if (ids.length == 0){
		alert('请选择您要'+msg+'的记录！');
	} else {
		
		var _do = function(){
			jQuery.ajaxSetup({async:false});
			jQuery.post(url,{ids:ids.toString()},function(data){
				alert(data.toString());
				refershGrid('tabGrid');
			},'json');
			jQuery.ajaxSetup({async:true});
		}
		
		showConfirmDivLayer('您确定要'+msg+'选择的记录吗？',{'okFun':_do})
	}
}


/*
 * jqGrid 封装jqGrid增删改查操作,依赖commanFunction.js
 * 暂未使用
 */
initButtonFn = function() {
	// 增加
	jQuery("#add").click(function() {
		if (_target == "_self") {
			window.location = _addUrl;
		} else {
			showTopWin(_addUrl, _topWinW, _topWinH, 'no');
		}
	});

	// 修改
	jQuery("#edit").click(function() {
		var id = getChecked();
		if (id.length > 0) {
			if (id.length > 1) {
				alert("只能对一条数据进行修改!");
			} else {
				try {
					if (typeof(eval('allowEdit')) == "function") {
						if (!allowEdit(id))
							return;
					}
				} catch (e) {
					// alert("not function");
				}
				
				var ret = jQuery("#myTab").jqGrid('getRowData', id);
				var url = _editUrl + id;
				if (_target == "_self") {
					window.location = url;
				} else {
					showTopWin(url, _topWinW, _topWinH, "Y");
				}

			}
		} else {
			alert("请选择一条数据进行修改!");
		}
	});

	// 查看
	jQuery("#view").click(function() {
		var id = getChecked();
		if (id.length > 0) {
			if (id.length > 1) {
				alert("只能对一条数据进行查看!");
			} else {
				var stage = "";
				if (jQuery("#stage") != null) {
					stage = jQuery("#stage").val();
				}
				var ret = jQuery("#myTab").jqGrid('getRowData', id);
				var url = _viewUrl + id + "&stage=" + stage;
				if (_target == "_self") {
					window.location = url;
				} else {
					showTopWin(url, _topWinW, _topWinH, "Y");
				}
			}
		} else {
			alert("请选择一条数据进行查看!");
		}
	});
	
	// 删除
	jQuery("#delete").click(function() {
		var ids = getChecked();
		if (ids.length > 0) {
			var params = {
				"ids" : ids.toString()
			};

			try {
				if (typeof(eval('allowDelete')) == "function") {
					if (!allowDelete(ids))
						return;
				}
			} catch (e) {
				// alert("not function");
			}

			showConfirmDivLayer("是否删除选中记录?", {
				okFun : function() {
					jQuery.post(_deleteUrl, params, function(data) {
							alert(data);
							jQuery("#myTab").jqGrid().trigger('reloadGrid');
					}, 'text');
				}
			});
		} else {
			alert("请至少选择一条数据进行操作!");
		}
	});

	// 查询
	jQuery("#query").click(function() {
		showTopWin(_queryUrl, _topWinW, _topWinH, 'no');
	});
}


