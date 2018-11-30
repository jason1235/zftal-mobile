var YhglGrid = Class.create(BaseJqGrid,{  
				caption : "用户列表",
				pager: "pager", //分页工具栏  
				height : 400,
				rowNum : 10000, // 每页显示记录数
				rowList : [], // 可调整每页显示的记录数	
		        url: _path+'/xtgl/yhgl_cxPlsjsq.html?doType=query', //这是Action的请求地址  
		        colModel:[
		        	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
				     {label:'姓 名',name:'xm', index: 'xm',align:'center'},
				     {label:'所属机构',name:'jgdm', index: 'jgdm',align:'center'},
			      	 {label:'联系电话',name:'lxdh', index: 'lxdh',align:'center'},
			      	 {label:'邮箱',name:'dzyx', index: 'dzyx',align:'center'},
			      	 {label:'是否启用',name:'sfqy', index: 'sfqy',align:'center'},
			      	 {label:'拥有角色数',name:'jss', index: 'jss',align:'center',formatter:viewYh}
				],
				sortname: 'zgh', //首次加载要进行排序的字段 
	         	sortorder: "desc"
	    	});

function searchResult(){
	var map = {};
	map["jsdm"] = jQuery('#jsdm').val();
	map["jgdm"] = jQuery('#jgdm').val();
	search('tabGrid',map);
}

function viewYh(cellvalue, options, rowObject){
	var zgh = rowObject.zgh;
	var html = "<a href='javascript:ckYh(\""+zgh+"\")' >"+cellvalue+"</a>";
	return  html;
}

function ckYh(obj){
	var url="yhgl_ckYhxx.html?zgh="+obj; 
	showWindow('查看用户',650,360,url);
}

function selectJsdm(){
	var map = {};
	map["jsdm"] = jQuery('#jsdm').val();
	search('tabGrid',map);
}

function selectJgdm(){
	var map = {};
	map["jgdm"] = jQuery('#jgdm').val();
	search('tabGrid',map);
}

function sjsq(){
	var url = 'sjfw_szSjsq.html';
	var jsdm = jQuery('#jsdm').val();
	if(jsdm==''){
		alert('请选择角色！');
		return false;
	}
	var id = getChecked();
	if (id.length < 1) {
		alert('请选定记录!');
		return false;
	}
	var url="sjfw_szSjsq.html?yh_id="+id+"&js_id="+jsdm; 
	showWindow('设置数据授权',750,610,url);
	//var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	
}
 