var LogGrid = Class.create(BaseJqGrid,{  
	caption : "日志列表",
	pager: "pager", //分页工具栏  
	height : 400,
	rowNum : 15, // 每页显示记录数
	rowList : [15, 30, 50, 100], // 可调整每页显示的记录数
    url: _path+'/xtgl/rzgl_cxRz.html?doType=query', //这是Action的请求地址  
    colNames: ['ID','业务模块','操作类型','操作用户','操作日期','IP地址','主机名','操作描述'],
	colModel:[
	     {name:'czbh', index:'czbh', key:true, hidden:true},
	     {name:'czmk', index: 'czmk'},
	     {name:'czlx', index: 'czlx'},
      	 {name:'czr', index: 'czr'},
	     {name:'czrq', index: 'czrq'},
	     {name:'ipdz', index: 'ipdz'},
	     {name:'zjm', index: 'zjm'},
	     {name:'czms', index: 'czms'}
	],
	sortname: 'czrq', //首次加载要进行排序的字段 
 	sortorder: "desc"
});
//查询
function searchResult(){
	var map = {};
	map["czr"] = jQuery('#czr').val(); 
	map["czkssj"] = jQuery('#czkssj').val(); 
	map["czjssj"] = jQuery('#czjssj').val(); 
	map["czmk"] = jQuery('#czmk').val(); 
	map["czlx"] = jQuery('#czlx').val();
	if(jQuery('#czkssj').val()!='' && jQuery('#czjssj').val()!=''){
		var czkssj_date=new Date((jQuery("#czkssj").val()).replace(/-/g,"/"));
		var czjssj_date=new Date((jQuery("#czjssj").val()).replace(/-/g,"/"));
		if(czkssj_date>czjssj_date){
			alert("开始时间不能大于结束时间！");
			return false;
		}
	}
	search('tabGrid',map);
}
//查看
function onView(){
	var id = getChecked();

	if(id.length != 1){
		alert('请选择一条您要查看的记录!');
		return;
	}
	var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	var url= _path+"/xtgl/rzgl_ckRzxx.html"; 
	url+="?";
	url+="czbh="+row.czbh;
	
	showWindow('查看日志',490,260,url);
}

function bdan(){
	var btn_ck=jQuery("#btn_ck");//取消置顶
	//绑定增加点击事件
	if(btn_ck!=null){
		btn_ck.click(function () {
			onView();
		});
	}
}