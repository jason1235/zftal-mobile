var KlwhGrid = Class.create(BaseJqGrid,{  
				caption : "学生列表",
				pager: "pager", //分页工具栏  
		        url: _path+'/xtgl/klwh_cxXsxx.html?doType=query', //这是Action的请求地址  
		        colModel:[
		        	 {label:'学号',name:'xh', index: 'xh',key:true,align:'center'},
				     {label:'姓名',name:'xm', index: 'xm',align:'center'},
				     {label:'年级',name:'nj', index: 'nj',align:'center'},
			      	 {label:'学院 ',name:'xy', index: 'xy',align:'center'},
			      	 {label:'专业',name:'zy', index: 'zy',align:'center'},
			      	 {label:'班级',name:'bj', index: 'bj',align:'center'}
				],
				sortname: 'xh', //首次加载要进行排序的字段 
	         	sortorder: "desc"
	    	});

//查询
function searchResult(){
	var map = {};
	map["nj"] = jQuery('#nj').val();
	map["xy"] = jQuery('#xy').val();
	map["zy"] = jQuery('#zy').val();
	map["bj"] = jQuery('#bj').val();

	search('tabGrid',map);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	var btncsh=jQuery("#btn_csh");//按钮ID  命名规则  btn_xx（xx:操作）
	var btnplcsh=jQuery("#btn_plcsh");
	//绑定全部初始化事件
	if(btncsh!=null){
		btncsh.click(function () {
			qbcsh('klwh_cshgz.html?type=qb',350,220);
		});
	}
	
	//绑定批量初始化事件
	if(btnplcsh!=null){
		btnplcsh.click(function () {
			plcsh('klwh_cshgz.html?type=pl&pkValue=',350,220);
		});
	}

}



/** 
 *  批量初始化时触发事件
 * @param cbvname  复选框名称，只能选择一条记录进行修改
 * @param url 要弹出的页面的路径
 * @return
 */
function plcsh(url,w,h) {
	var ids = getChecked();
	if(ids.length==0){
		alert("请选择要操作的数据");
	}else{
		showWindow('批量初始化',w,h,url+ids);
	}
	}
/** 
 *  全部初始化时触发事件
 * @param cbvname  复选框名称，只能选择一条记录进行修改
 * @param url 要弹出的页面的路径
 * @return
 */
function qbcsh(url,w,h) {
	
	showConfirmDivLayer('确定要全部进行初始化吗？',{'okFun':function(){
		showWindow('全部初始化',w,h,url)
	}})
		
	}






