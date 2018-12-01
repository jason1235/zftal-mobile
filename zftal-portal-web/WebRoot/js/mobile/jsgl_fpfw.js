
var param="";
var wfpGrid ;
var yfpGrid;
//var jsid=document.getElementById("jsid").value;
/*
 * jqGrid 封装jqGrid基类,依赖prototype.js
 */
BaseJqGrid_jsgl_fpyh = Class.create({
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
	multiselect : false, // 是否支持多选
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

function wfp(rowId,states){
	
	var fwbm=jQuery("#tabGrid").jqGrid("getCell", rowId,"classFwbm"); 
	var isOrNot = "no";
	jQuery("#tabGrid").jqGrid("delRowData", rowId); 
    jQuery.post( _path+"/serviceManager/fwdyjs_fenpei.html",{"fwbm":fwbm,"jsid":jsid,"isOrNot":isOrNot},function(data){
    	//alert(111);
    	var arrIds = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery('#yfpTabGrid').jqGrid('addRowData', i, data);
    });
    //alert(jsid+"----"+fwbm);
    saveFp(fwbm);
}

function saveFp(fwbm){
	//alert(jsid+"----"+fwbm);	
	  jQuery.post(_path+"/serviceManager/fwdyjs_add.html",{"fwbm":fwbm,"jsid":jsid},function(data){
	  },"json");
}

var url = _path+'/serviceManager/fwdyjs_getWfp.html?jsid='+jsid;
var WfpGrid = Class.create(BaseJqGrid_jsgl_fpyh,{  
				caption : "未分配用户列表",
				pager: "pager", //分页工具栏  
		        url:  url, //这是Action的请求地址  
		        colModel:[
		        	 {label:'服务编码',name:'classFwbm', index: 'fwbm',key:true,align:'center'},
				     {label:'服务名称',name:'classFwmc', index: 'fwmc',align:'center'}//,
				],
				sortname: 'fwbm', //首次加载要进行排序的字段 
	         	sortorder: "desc",
	         	ondblClickRow:wfp
	         	});

//查询
function searchResult(){
	var map = {};
	map["classFwbm"] = jQuery('#fwbm').val();
	map["classFwmc"] = jQuery('#fwmc').val();

	search('tabGrid',map);
}

function yfp(rowId,states){
	var fwbm=jQuery("#yfpTabGrid").jqGrid("getCell", rowId,"classFwbm"); 
	var isOrNot = "yes";
	jQuery("#yfpTabGrid").jqGrid("delRowData", rowId); 
    jQuery.post( _path+"/serviceManager/fwdyjs_fenpei.html",{"fwbm":fwbm,"jsid":jsid,"isOrNot":isOrNot},function(data){
    	//alert(111);
    	var arrIds = jQuery("#tabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery("#tabGrid").jqGrid("addRowData", i, data);
    });
    //alert(jsid+"----"+fwbm);
    removeFp(fwbm);
}

function removeFp(fwbm){
	//alert(jsid+"----"+fwbm);	
	  jQuery.post(_path+"/serviceManager/fwdyjs_remove.html",{"fwbm":fwbm,"jsid":jsid},function(data){
	  },"json");
}

var YfpGrid = Class.create(BaseJqGrid_jsgl_fpyh,{  
	caption : "已分配用户列表",
    url: _path+'/serviceManager/fwdyjs_getYfp.html?jsid='+jsid, //这是Action的请求地址  
    rowNum : 1000,
    rowList : [1000],
    colModel:[
    	 {label:'服务编码',name:'classFwbm', index: 'fwbm',key:true,align:'center'},
		 {label:'服务名称',name:'classFwmc', index: 'fwmc',align:'center'}//,
	],
	sortname: 'fwbm', //首次加载要进行排序的字段 
 	sortorder: "desc",
 	ondblClickRow:yfp
});



