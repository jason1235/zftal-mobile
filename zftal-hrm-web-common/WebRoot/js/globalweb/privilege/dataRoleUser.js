
var param="";
var wfpGrid ;
var yfpGrid;

/*
 * jqGrid 封装jqGrid基类,依赖prototype.js
 */
BasePrivilegeJqGrid = Class.create({
	datatype : "json", // 将这里改为使用JSON数据
	mtype : 'POST',
	height : 235,
	autowidth : true, // 自动调整宽度
	rowNum : 10, // 每页显示记录数
	viewrecords : true, // 是否显示行数
	rowList : [10, 20, 30, 50, 100], // 可调整每页显示的记录数
	multiselect : true, // 是否支持多选
	onSortCol : function(index, colindex, sortorder) {
		
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
	var userId=jQuery("#tabGrid").jqGrid("getCell", rowId,"userId"); 
	jQuery("#tabGrid").jqGrid("delRowData", rowId); 
	
    jQuery.getJSON( _path+"/privilege/dataroleuser_getTheUser.html",{"model.userId":userId,"model.dataRoleId":sjjsdm},function(data){
	    var arrIds = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
	    var i = arrIds.length + 1; 
	    jQuery('#yfpTabGrid').jqGrid('addRowData', i, data);  
    }); 
} 

var WfpGrid = Class.create(BaseJqGrid,{  
				caption : "未分配用户列表",
				pager: "pager", //分页工具栏  
		        url: _path+'/privilege/dataroleuser_unallotList.html?model.dataRoleId='+sjjsdm, //这是Action的请求地址  
		        colModel:[
		        	 {label:'用户名',name:'userId', index: 'userId',key:true,align:'center'},
				     {label:'姓 名',name:'userName', index: 'userName',align:'center'}
				],
				//sortname: 'userName', //首次加载要进行排序的字段 
	         	//sortorder: "desc",
	         	onSelectRow:wfp});

//查询
function searchResult(){
	var map = {};
	map["userId"] = jQuery('#userId').val();
	map["userName"] = jQuery('#userName').val();
	//map["sfqy"] = jQuery('#sfqy').val();

	search('tabGrid',map);
}

/*function viewYh(cellvalue, options, rowObject){
	var zgh = rowObject.userId;
	var html = "<a href='javascript:ckYh(\""+zgh+"\")' >"+cellvalue+"</a>";
	return  html;
}*/ 

function yfp(rowId,states){
	var userId=jQuery("#yfpTabGrid").jqGrid("getCell", rowId,"userId"); 
	jQuery("#yfpTabGrid").jqGrid("delRowData", rowId); 
	
    jQuery.getJSON( _path+"/privilege/dataroleuser_getTheUser.html",{"model.userId":userId,"model.dataRoleId":sjjsdm},function(data){
    	var arrIds = jQuery("#tabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery('#tabGrid').jqGrid('addRowData', i, data);
    });
}

var YfpGrid = Class.create(BaseJqGrid,{  
	caption : "已分配用户列表",
    url: _path+'/privilege/dataroleuser_allotList.html?model.dataRoleId='+sjjsdm, //这是Action的请求地址  
    rowNum : 1000,
    rowList : [1000], 
    colModel:[
	    {label:'用户名',name:'userId', index: 'userId',key:true,align:'center'},
		{label:'姓 名',name:'userName', index: 'userName',align:'center'}
	],
	//sortname: 'userName', //首次加载要进行排序的字段  
    //sortorder: "desc",
 	onSelectRow:yfp
});

//保存角色分配用户信息
function savefpyhxx() {
	var array = jQuery("#yfpTabGrid input[type='checkbox']");
	var param="";
	if (array != null && array.length > 0) {
		var j=1;
		jQuery.each(array,function(i,n) {
			if(!jQuery(n).parent().parent().is("th")){
				if(j==1){
					param="ids="+jQuery(n).parent().parent().children('td').eq(1).html();
				}else{
					param=param+"&"+"ids="+jQuery(n).parent().parent().children('td').eq(1).html();
				
				}
				j=j+1;
			}else{
				j=1; 
			}
		});
	} 

	//subForm(_path+'/privilege/dataroleuser_allotUserDataRole.html?query.dataRoleId='+sjjsdm+'&'+param);	
	subForm(_path+'/privilege/dataroleuser_allotUserDataRole.html?'+param);	
	
}

