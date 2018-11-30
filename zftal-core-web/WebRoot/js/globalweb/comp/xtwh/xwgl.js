var NewsGrid = Class.create(BaseJqGrid,{  
	caption : "新闻列表",
	pager: "pager", //分页工具栏  
    url: _path+'/xtgl/xwgl_cxXw.html?doType=query', //这是Action的请求地址  
    colModel:[
	     {label:'ID',name:'xwbh', index:'xwbh', key:true, hidden:true},
	     {label:'新闻标题',name:'xwbt', index: 'xwbt'},
	     {label:'发布时间',name:'fbsj', index: 'fbsj'},
      	 {label:'发布人',name:'fbr', index: 'fbr'},
	     {label:'发布对象',name:'fbdx', index: 'fbdx'},
	     {label:'是否发布',name:'sffb', index: 'sffb'},
	     {label:'是否置顶',name:'sfzd', index: 'sfzd'}
	],
	sortname: 'fbsj', //首次加载要进行排序的字段 
 	sortorder: "desc"
});
//查询
function searchResult(){
	var map = {};
	map["xwbt"] = jQuery('#xwbt').val();
	map["sffb"] = jQuery('#sffb').val();
	map["sfzd"] = jQuery('#sfzd').val();

	search('tabGrid',map);
}
//增加
function onNewNews(){
	//showWindow('增加新闻',700,450,'')
	var url = _path+'/xtgl/xwgl_zjXw.html';
	document.location.href=url;      
}
//修改新闻 
function onModifyNews(){    
	var id = getChecked();
	if(id.length != 1){
		alert('请先选定一条记录!');
		return;
	}
	
	var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	//业务判断
	if(row.sffb=='是'){
		alert('不能对已发布的记录进行修改！');
		return ;
	}
	var url= _path+"/xtgl/xwgl_xgXw.html"; 
	url+="?";
	
	url+="xwbh="+row.xwbh;
	document.location.href = url;
}
//查看
function onView(){
	var id = getChecked();

	if(id.length != 1){
		alert('请选择一条您要查看的记录!');
		return;
	}
	var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	var url= _path+"/xtgl/xwgl_ckXw.html"; 
	url+="?";
	
	url+="xwbh="+row.xwbh;
	window.open(url);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	var btn_zj=jQuery("#btn_zj");//增加
	var btn_xg=jQuery("#btn_xg");//修改
	var btn_sc=jQuery("#btn_sc");//删除
	var btn_ck=jQuery("#btn_ck");//查看
	var btn_fb=jQuery("#btn_fb");//发布
	var btn_qxfb=jQuery("#btn_qxfb");//取消发布
	var btn_zd=jQuery("#btn_zd");//置顶
	var btn_qxzd=jQuery("#btn_qxzd");//取消置顶
	//绑定增加点击事件
	if(btn_zj!=null){
		btn_zj.click(function () {
			onNewNews();
		});
	}

	if(btn_xg!=null){
		btn_xg.click(function () {
			onModifyNews();
		});
	}

	if(btn_sc!=null){
		btn_sc.click(function () {
			plcz(_path+'/xtgl/xwgl_scXw.html','删除');
		});
	}
	
	if(btn_ck!=null){
		btn_ck.click(function () {
			onView();
		});
	}
	
	if(btn_fb!=null){
		btn_fb.click(function () {
			plcz(_path+'/xtgl/xwgl_fbXw.html','发布');
		});
	}
	if(btn_qxfb!=null){
		btn_qxfb.click(function () {
			plcz(_path+'/xtgl/xwgl_qxfbXw.html','取消发布');
		});
	}
	if(btn_zd!=null){
		btn_zd.click(function () {
			plcz(_path+'/xtgl/xwgl_zdXw.html','置顶');
		});
	}
	if(btn_qxzd!=null){
		btn_qxzd.click(function () {
			plcz(_path+'/xtgl/xwgl_qxzdXw.html','取消置顶');
		});
	}
}