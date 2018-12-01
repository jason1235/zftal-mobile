
var param="";
var wfpGrid ;
var yfpGrid;

function wfp(rowId,states){
	var zgh=jQuery("#tabGrid").jqGrid("getCell", rowId,"zgh"); 
	jQuery("#tabGrid").jqGrid("delRowData", rowId); 
	
    jQuery.getJSON( _path+"/xtgl/jsgl_fpyhCxYhxx.html",{"zgh":zgh},function(data){
    	var arrIds = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery('#yfpTabGrid').jqGrid('addRowData', i, data);
        
        var array = jQuery("#yfpTabGrid input[type='checkbox']");
		if (array != null && array.length > 0) {
			var j=1;
			jQuery.each(array,function(i,n) {
				if(!jQuery(n).parent().parent().is("th")){
					if(j==1){
						param=jQuery(n).parent().parent().children('td').eq(1).html();
					}else{
						param=param+","+jQuery(n).parent().parent().children('td').eq(1).html();
					
					}
					j++
				}else{
					j=1;
				}
			});
		} 

		jQuery("#tabGrid").jqGrid("setGridParam",{"url": url + "&yhids="+param}); 
        
    });
}

var url = _path+'/xtgl/jsgl_fpyhWfpYhxx.html?jsdm='+jsdm;
//yhids
var WfpGrid = Class.create(BaseJqGrid,{  
				caption : "未分配用户列表",
				multiselect : false, // 是否支持多选
				pager: "pager", //分页工具栏  
		        url:  url, //这是Action的请求地址  
		        colModel:[
		        	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
				     {label:'姓 名',name:'xm', index: 'xm',align:'center'},
			      	 {label:'联系电话',name:'lxdh', index: 'lxdh',align:'center'},
			      	 {label:'邮箱',name:'dzyx', index: 'dzyx',align:'center'}
				],
				sortname: 'zgh', //首次加载要进行排序的字段 
	         	sortorder: "desc",
	         	ondblClickRow:wfp});

//查询
function searchResult(){
	var array = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
	var param = "";
	if (array != null && array.length > 0) {
		var j=1;
		jQuery.each(array,function(i,n) {
			param = param + jQuery("#yfpTabGrid").jqGrid("getCell", n,"zgh") + ",";
		});
	} 
	var map = {};
	map["zgh"] = jQuery('#zgh').val();
	map["xm"] = jQuery('#xm').val();
	map["param"] = param;
	search('tabGrid',map);
}

function viewYh(cellvalue, options, rowObject){
	var zgh = rowObject.zgh;
	var html = "<a href='javascript:ckYh(\""+zgh+"\")' >"+cellvalue+"</a>";
	return  html;
}

function yfp(rowId,states){
	var zgh=jQuery("#yfpTabGrid").jqGrid("getCell", rowId,"zgh"); 
	jQuery("#yfpTabGrid").jqGrid("delRowData", rowId); 
	
	var array = jQuery("#yfpTabGrid input[type='checkbox']");
	if (array != null && array.length > 0) {
		var j=1;
		jQuery.each(array,function(i,n) {
			if(!jQuery(n).parent().parent().is("th")){
				if(j==1){
					param=jQuery(n).parent().parent().children('td').eq(1).html();
				}else{
					param=param+","+jQuery(n).parent().parent().children('td').eq(1).html();
				
				}
				j++
			}else{
				j=1;
			}
		});
	} 

	jQuery("#tabGrid").jqGrid("setGridParam",{"url": url + "&yhids="+param});
	
    jQuery.getJSON( _path+"/xtgl/jsgl_fpyhCxYhxx.html",{"zgh":zgh},function(data){
    	var arrIds = jQuery("#tabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery('#tabGrid').jqGrid('addRowData', i, data);
        
    });
}

var YfpGrid = Class.create(BaseJqGrid,{  
	caption : "已分配用户列表",
	rowNum : 10000, // 每页显示记录数
	multiselect : false, // 是否支持多选
	pgbuttons:false,//是否显示用于翻页的按钮
	pginput:false,
	rowList : [], // 可调整每页显示的记录数	
    pager: "pagerYfp", //分页工具栏  
    url: _path+'/xtgl/jsgl_fpyhYfpYhxx.html?jsdm='+jsdm, //这是Action的请求地址  
    colModel:[
    	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
	     {label:'姓 名',name:'xm', index: 'xm',align:'center'}
	],
	sortname: 'zgh', //首次加载要进行排序的字段 
 	sortorder: "desc",
 	ondblClickRow:yfp
});