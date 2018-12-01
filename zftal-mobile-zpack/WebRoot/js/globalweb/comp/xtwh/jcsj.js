var JcsjGrid = Class.create(BaseJqGrid,{  
				caption : "基础数据列表",
				pager: "pager", //分页工具栏  
		        url:_path+'/xtgl/jcsj_cxJcsj.html?doType=query', //这是Action的请求地址  
		        colModel:[
		        	 {label:'主键',name:'pkValue', index: 'pkValue',key:true,hidden:true,align:'center'},
				     {label:'类型',name:'lxmc', index: 'lxmc',align:'center'},
				     {label:'代码',name:'dm', index: 'dm',align:'center'},
			      	 {label:'名称',name:'mc', index: 'mc',align:'center'}
				],
				sortname: 'lxmc,dm,mc', //首次加载要进行排序的字段 
	         	sortorder: "asc"
	    	});

//查询
function searchResult(){
	var map = {};
	map["lxdm"] = jQuery('#lxdm').val();
	map["dm"] = jQuery('#dm').val();
	map["mc"] = jQuery('#mc').val();

	search('tabGrid',map);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	var btnzj=jQuery("#btn_zj");//按钮ID  命名规则  btn_xx（xx:操作）
	var btnsc=jQuery("#btn_sc");
	var btnxg=jQuery("#btn_xg");
	var btndc=jQuery("#btn_dc");
	var btndcsz=jQuery("#btn_dcsz");
	//绑定增加点击事件
	if(btnzj!=null){
		btnzj.click(function () {
			showWindow('增加基础数据',400,250,'jcsj_zjJcsj.html');
		});
	}
	
	//绑定删除事件
	if(btnsc!=null){
		btnsc.click(function () {
			plcz('jcsj_scJcsj.html','删除');
		});
	}

	//绑定修改事件
	if(btnxg!=null){
		btnxg.click(function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	
		var url="jcsj_xgJcsj.html?pkValue="; 
		url+=id;
		showWindow('修改基础数据',400,250,url);
		});
	}
	
	//绑定导出设置事件
	if(btndcsz!=null){
		btndcsz.click(function () {
		showWindow('导出设置',550,350,_path+'/inout/dc_szDczd.html?dcclbh=01');
	});
	}
	
	//绑定导出事件
	if(btndc!=null){
		btndc.click(function () {
		subForm('jcsj_dcJcsj.html?dcclbh=01');
		return false;
		});
	}
}