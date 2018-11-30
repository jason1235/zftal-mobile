<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<link href="<%=systemPath %>/css/globalweb/page.css" rel="stylesheet" type="text/css" />
<script src="<%=systemPath %>/js/jquery/jquery-1.6.js" type="text/javascript"></script>
<script src="<%=systemPath %>/js/jquery/jquery.myPagination.js" type="text/javascript"></script>
<style type="text/css">
.demo_data2 {
    border: 1px solid #DEDEDE;
	display: inline;
    float: left;
    height: 75px;
    margin: 4px 5px 0;
    padding: 8px;
    width: 155px;
}
</style>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
</head>	
<s:set name="njdms" value="@com.zfsoft.service.common.engine.CommonSqlEngine@getAllNj()"/>
<s:set name="njdmsJson" value="@com.zfsoft.service.common.engine.CommonSqlEngine@getAllNjJson()"/>
<s:set name="bmdms" value="@com.zfsoft.service.common.engine.CommonSqlEngine@getAllXy()"/>
<s:set name="zydms" value="@com.zfsoft.service.common.engine.CommonSqlEngine@getAllZy()"/>	  
<script type="text/javascript">
 jQuery(document).ready(function(){
    var bmdmData  = jQuery("#ZFTAL_XTGL_BMDMB");
    var zydmData  = jQuery("#ZFTAL_XTGL_ZYDMB");
    var bjdmData  = jQuery("#ZFTAL_XTGL_BJDMB");
    var shoolDate  = jQuery("#SCHOOL");
    zydmData.hide();
    bjdmData.hide();
    shoolDate.hide();
    asynXydm();
    band();
 }); 

//根据表名获取字段代码、字段名称信息
function changeDisplay(bm){
	var array = jQuery("div[name='sDiv']");
	jQuery.each(array,function(i,n) {
		if (jQuery(n)) {
			jQuery(n).hide();
		}
	});
	if(jQuery("#"+bm).attr("id")=='ZFTAL_XTGL_ZYDMB'){
		asynZydm();
	}
	else if(jQuery("#"+bm).attr("id")=='ZFTAL_XTGL_BJDMB'){
		var map = {};
		map["on"] = true;
		map["queryModel.showCount"] = 5;    //每页记录数
		map["zddm"] = 'bjdm_id';			//班级Id
		map["zdmc"] = 'bjmc';				//班级名称
		map["bm"] = 'zftal_xtgl_bjdmb';		//查询数据库表	
		asynBjdm(map);
	}
	jQuery("#"+bm).show();
}

//学院代码异步分页
function asynXydm(){
   var map = {};
   map["on"] = true;
   map["queryModel.showCount"] = 5;          //每页记录数
   map["zddm"] = 'bmdm_id';				     //学院Id
   map["zdmc"] = 'bmmc';				     //学院名称
   map["bm"] = 'zftal_xtgl_bmdmb';		     //查询数据库表
   jQuery("#xydm_pages").myPagination({
   ajax: {
	  on: true,                              //开启状态
	  callback: 'callBack_xydm',             //回调函数，注，此 ajaxCallBack 函数，必须定义在 $(function() {}); 外面
	  url: _path+'/xtgl/sjdx_cxSjfwdx.html', //访问服务器地址
	  dataType: 'json',                      //返回类型
	  param:map                              //参数列表，其中  on 必须开启，page 参数必须存在，其他的都是自定义参数，如果是多条件查询，可以序列化表单，然后增加 page 参数
	}
   });
}

//自定义 回调函数
function callBack_xydm(data) {
   var html = "";
   var objects = jQuery('#objects').val();
   var data2 = objects.split("|");
  if(data.items != null && (data.items).length>0){
  	var xydms = data.items;
  	var njdms = ${njdmsJson};
  	for(var i=0;i<xydms.length;i++){
  		var checkState = "";
  		var checkValue = xydms[i]["zddm"]+"&"+xydms[i]["zdmc"];
	 	//判断是否选中checkbox
	 	for(var k=0;k<data2.length;k++){
	 		if(data2[k]==checkValue){
	 			checkState = " checked='true'";
	 			break;
	 		}
	 	}  		
  		html = html + "<div class='demo_data2'><h4><input name='bmdm_' type='checkbox' value="+ checkValue + checkState +" />"+ xydms[i]["zdmc"] +"</h4><p name='p_bmdm_njdm'>";
  		for(var j = 0 ;j<njdms.length;j++){
	  		var checkState = "";
	  		var checkValue = xydms[i]["zddm"]+"&"+xydms[i]["zdmc"]+"_"+ njdms[j]["njdm_id"]+"&"+njdms[j]["njmc"];
		 	//判断是否选中checkbox
		 	for(var k=0;k<data2.length;k++){
		 		if(data2[k]==checkValue){
		 			checkState = " checked='true'";
		 			break;
		 		}
		 	} 
  			html = html + "<input name='bmdm_njdm' onclick=selectParent('bmdm_','"+xydms[i]["zddm"]+"&"+xydms[i]["zdmc"]+"') type='checkbox' value="+checkValue + checkState +" />"+njdms[j]["njmc"];
  		}
  		html = html + "</p></div>";
  	}  	 
  }else{
  	 html = "暂无数据";
  }
  jQuery("#bmdm_njdm_id").val("全部");
  jQuery('#xydm_div').html(html);
  document.getElementById("checkAll_bmdm").checked=false;
}

//专业代码异步分页
function  asynZydm(){
   var map = {};
   map["on"] = true;
   map["queryModel.showCount"] = 3;          //每页记录数
   //map["queryModel.sortName"] = 'zydm_id'; //排序字段
   //map["queryModel.sortOrder"] = 'desc';   //排序顺序
   map["zddm"] = 'zydm_id';				     //专业Id
   map["zdmc"] = 'zymc';				     //专业名称
   map["bm"] = 'zftal_xtgl_zydmb';		     //查询数据库表
   jQuery("#zydm_pages").myPagination({
   ajax: {
	  on: true,                            
	  callback: 'callBack_zydm',             
	  url: _path+'/xtgl/sjdx_cxSjfwdx.html',  
	  dataType: 'json',                     
	  param:map                            
	}
   });
}	
//自定义 回调函数
function callBack_zydm(data) {
   var html = "";
   var objects = jQuery('#objects').val();
   var data2 = objects.split("|");
  if(data.items != null && (data.items).length>0){
  	var zydms = data.items;
  	var njdms = ${njdmsJson};
  	for(var i=0;i<zydms.length;i++){
  		var checkState = "";
  		var checkValue = zydms[i]["zddm"]+"&"+zydms[i]["zdmc"];
	 	//判断是否选中checkbox
	 	for(var k=0;k<data2.length;k++){
	 		if(data2[k]==checkValue){
	 			checkState = " checked='true'";
	 			break;
	 		}
	 	}  		
  		html = html + "<div class='demo_data2'><h4><input name='zydm_' type='checkbox' value="+ checkValue + checkState +" />"+ zydms[i]["zdmc"] +"</h4><p name='p_zydm_njdm'>";
  		for(var j = 0 ;j<njdms.length;j++){
	  		var checkState = "";
	  		var checkValue = zydms[i]["zddm"]+"&"+zydms[i]["zdmc"]+"_"+ njdms[j]["njdm_id"]+"&"+njdms[j]["njmc"];
		 	//判断是否选中checkbox
		 	for(var k=0;k<data2.length;k++){
		 		if(data2[k]==checkValue){
		 			checkState = " checked='true'";
		 			break;
		 		}
		 	} 
  			html = html + "<input name='zydm_njdm' onclick=selectParent('zydm_','"+zydms[i]["zddm"]+"&"+zydms[i]["zdmc"]+"') type='checkbox' value="+checkValue + checkState +" />"+njdms[j]["njmc"];
  		}
  		html = html + "</p></div>";
  	}  	 
  }else{
  	 html = "暂无数据";
  }
  jQuery("#njdm_zydm_id").val("全部");
  jQuery('#zydm_div').html(html);
  document.getElementById("checkAll_zydm").checked=false;
}	

//班级代码异步分页
function asynBjdm(map){
   jQuery("#bjdm_pages").myPagination({
   ajax: {
	  on: true,                            //开启状态
	  callback: 'callBack_bjdm',           //回调函数，注，此 ajaxCallBack 函数，必须定义在 $(function() {}); 外面
	  url: _path+'/xtgl/sjdx_cxSjfwdx.html', //访问服务器地址
	  dataType: 'json',                    //返回类型
	  param:map                            ////参数列表，其中  on 必须开启，page 参数必须存在，其他的都是自定义参数，如果是多条件查询，可以序列化表单，然后增加 page 参数
	}
   });
}

//自定义 回调函数
function callBack_bjdm(data) {
  var html = "";
  var objects = jQuery('#objects').val();
  var data2 = objects.split("|");  
  if(data.items != null && (data.items).length>0){
  	var bjdms = data.items;
  	html = "<div id='p_bjdm'>";
  	for(var i=0;i<bjdms.length;i++){
	   var checkState = "";
	   var checkValue = bjdms[i]["zddm"]+"&"+bjdms[i]["zdmc"];
       //判断是否选中checkbox
	   for(var k=0;k<data2.length;k++){
	 	 if(data2[k]==checkValue){
	 		 checkState = " checked='true'";
	 	 }
	   }  	
  	   html = html + "<div class='demo_data2'><h4><input name='bjdm_' type='checkbox' value="+ checkValue + checkState +" />"+bjdms[i]["zdmc"]+"</h4></div>";
  	}  	 
  	html = html + "</div>";
  }else{
  	 html = "暂无数据";
  }
  jQuery('#bjdm_div').html(html);
  document.getElementById("checkAll_bjdm").checked=false;
}

//选中父复选框
function selectParent(name,value){
  var inputNames = jQuery("input[name='"+name+"']");
  jQuery.each(inputNames,function(i,n){
   if(jQuery(n)){
	 if(jQuery(n).val()==value){
	   jQuery(n).attr('checked',true);
	 }
   }	 
  }); 
}

function band(){
	var btn_ccg  = jQuery("#btn_ccg");
	var bmdm_njdm_id = jQuery("#bmdm_njdm_id");
	var njdm_zydm_id = jQuery("#njdm_zydm_id");
	var bjdm_bmdm_id = jQuery("#bjdm_bmdm_id");
	var bjdm_zydm_id = jQuery("#bjdm_zydm_id");
 	var bmdms_ = jQuery("input[name='bmdm_']");
 	var zydms_ = jQuery("input[name='zydm_']");
 	var bjdms_ = jQuery("input[name='bjdm_']");
 	var xxdms_ = jQuery("input[name='xxdm_']");
 	var bmdm_njdm_ = jQuery("input[name='bmdm_njdm']");
 	var zydm_njdm_ = jQuery("input[name='zydm_njdm']");	
	var checkAll_bmdm = jQuery("#checkAll_bmdm");
	var checkAll_zydm = jQuery("#checkAll_zydm");
	var checkAll_bjdm = jQuery("#checkAll_bjdm");
	var p_bjdm = jQuery("#p_bjdm");
	
	// 绑定学院代码、下拉年级代码事件
	if (bmdm_njdm_id != null) {
		bmdm_njdm_id.change(function() {
		     var checkVal = jQuery("#bmdm_njdm_id").val();
		     var checkText = jQuery("#bmdm_njdm_id").find("option:selected").text();
			 jQuery.ajax({
					url:"comm_cxAllXydm.html",
					type: "post",
					dataType:"json",
					success:function(bmdms){
					 if(bmdms.length>0){
					 	var array = jQuery("p[name='p_bmdm_njdm']");
					    for(var i=0;i<bmdms.length;i++){
							var html = "";
							if(checkVal=='全部'){
								var data = ${njdmsJson};
								jQuery.each(data,function(n,value) { 
	 								var checkState = "";
	 								var checkValue = bmdms[i]["bmdm_id"]+"&"+bmdms[i]["bmmc"]+"_" + value.njdm_id+"&" + value.njmc;
									var objects = jQuery('#objects').val();
	 								var data2 = objects.split("|");
	 								//判断是否选中checkbox
	 								for(var j=0;j<data2.length;j++){
	 									if(data2[j]==checkValue){
	 										checkState = " checked='true'";
	 									}
	 								}
	 								html = html + "<input name='bmdm_njdm' onclick=selectParent('bmdm_','"+bmdms[i]["bmdm_id"]+"&"+bmdms[i]["bmmc"]+"') type='checkbox' value="+ checkValue + checkState + " />"+value.njmc+"";
								});
							}else{
								    var checkState = "";
								    var checkValue = bmdms[i]["bmdm_id"]+"&"+bmdms[i]["bmmc"]+"_"+ checkVal +"&"+checkText;
									var objects = jQuery('#objects').val();
	 								var data2 = objects.split("|");
	 								//判断是否选中checkbox
	 								for(var j=0;j<data2.length;j++){
	 									if(data2[j]==checkValue){
	 										checkState = " checked='true'";
	 									}
	 								}
		    	 					html = "<input name='bmdm_njdm' onclick=selectParent('bmdm_','"+bmdms[i]["bmdm_id"]+"&"+bmdms[i]["bmmc"]+"') type='checkbox' value=" + checkValue + checkState + " />"+checkText+"";
		     				}
					        jQuery(array[i]).html(html);
						}
					 }
					}
			    });
		 });
	}
	
	// 绑定专业代码、下拉年级代码事件
	if (njdm_zydm_id != null) {
		njdm_zydm_id.change(function() {
		     var checkVal = jQuery("#njdm_zydm_id").val();
		     var checkText = jQuery("#njdm_zydm_id").find("option:selected").text();
			 jQuery.ajax({
					url:"comm_cxZydm.html",
					type: "post",
					dataType:"json",
					success:function(zydms){
					 if(zydms.length>0){
					 	var array = jQuery("p[name='p_zydm_njdm']");
					    for(var i=0;i<zydms.length;i++){
							var html = "";
							if(checkVal=='全部'){
								var data = ${njdmsJson};
								jQuery.each(data,function(n,value) { 
	 								var checkState = "";
	 								var checkValue = zydms[i]["zydm_id"]+"&"+zydms[i]["zymc"]+ "_" + value.njdm_id+"&" + value.njmc;
									var objects = jQuery('#objects').val();
	 								var data2 = objects.split("|");
	 								//判断是否选中checkbox
	 								for(var j=0;j<data2.length;j++){
	 									if(data2[j]==checkValue){
	 										checkState = " checked='true'";
	 									}
	 								}								
	 								html = html + "<input name='zydm_njdm' onclick=selectParent('zydm_','"+zydms[i]["zydm_id"]+"&"+zydms[i]["zymc"]+"') type='checkbox' value="+ checkValue + checkState  + " />"+value.njmc+"";
								});
							}else{
								    var checkState = "";
								    var checkValue = zydms[i]["zydm_id"]+"&"+zydms[i]["zymc"]+"_"+ checkVal +"&"+checkText;
									var objects = jQuery('#objects').val();
	 								var data2 = objects.split("|");
	 								//判断是否选中checkbox
	 								for(var j=0;j<data2.length;j++){
	 									if(data2[j]==checkValue){
	 										checkState = " checked='true'";
	 									}
	 								}
		    	 					html = "<input name='zydm_njdm' onclick=selectParent('zydm_','"+zydms[i]["zydm_id"]+"&"+zydms[i]["zymc"]+"') type='checkbox' value=" +  checkValue + checkState +" />"+checkText+"";
		     				}
					        jQuery(array[i]).html(html);
						}
					 }
					}
			    });			  			 
		});
	}
	
	// 绑定学院下拉查询专业列表、班级列表事件
	if (bjdm_bmdm_id != null) {
		bjdm_bmdm_id.change(function() {
		     var bmdm_id = jQuery("#bjdm_bmdm_id").val();
		     var bjdm_zydm_id = jQuery("#bjdm_zydm_id");
				jQuery.ajax({
					url:"comm_cxZydm.html",
					type: "post",
					dataType:"json",
					data:{"bmdm_id":bmdm_id},
					success:function(zydms){
					 var html = "<option value='全部'>全部</option>";
					 if(zydms.length>0){
					   jQuery(bjdm_zydm_id).html("");
					    for(var i=0;i<zydms.length;i++){
							html = html + "<option value="+zydms[i]["zydm_id"]+">"+zydms[i]["zymc"]+"</option>";
						}
					 } 
					  	jQuery(bjdm_zydm_id).html(html);
					}
				});	
				// 按部门代码分页查询班级代码列表
		 		var map = {};
				if(bmdm_id!='全部'){
				  map["ls_bmdm"] = bmdm_id;              //所属部门
				}
				map["on"] = true;
				map["queryModel.showCount"] = 5;         //每页记录数
				map["zddm"] = 'bjdm_id';				 //班级Id
				map["zdmc"] = 'bjmc';				     //班级名称
				map["bm"] = 'zftal_xtgl_bjdmb';		     //查询数据库表
				asynBjdm(map);
		});
	}
	
	// 绑定下拉专业代码、查询班级代码列表事件
	if (bjdm_zydm_id != null) {
		bjdm_zydm_id.change(function() {
		 	var bmdm_id = jQuery("#bjdm_bmdm_id").val();
		    var zydm_id = jQuery("#bjdm_zydm_id").val();
				// 按部门代码分页查询班级代码列表
		 		var map = {};
				if(bmdm_id!='全部'){
				  map["ls_bmdm"] = bmdm_id;             //所属部门
				}
				if(zydm_id!='全部'){
				  map["ls_zydm"] = zydm_id;             //所属专业
				}
				map["on"] = true;
				map["queryModel.showCount"] = 5;         //每页记录数
				map["zddm"] = 'bjdm_id';				 //班级Id
				map["zdmc"] = 'bjmc';				     //班级名称
				map["bm"] = 'zftal_xtgl_bjdmb';		     //查询数据库表
				asynBjdm(map);
		});
	}	
	
	// 绑定学院全选按钮
	if(checkAll_bmdm !=null){
		checkAll_bmdm.change(function() {
			var bmdms_ = jQuery("input[name='bmdm_']");
			var bmdm_njdm_ = jQuery("input[name='bmdm_njdm']");
			var t = document.getElementById("checkAll_bmdm");
			if(t.checked){	
 				jQuery(bmdms_).attr('checked',true);
 				jQuery(bmdm_njdm_).attr('checked',true);
			}else{
 				jQuery(bmdms_).attr('checked',false); 
 				jQuery(bmdm_njdm_).attr('checked',false);
			}
		});
	}
	
	// 绑定专业全选按钮
	if(checkAll_zydm !=null){
		checkAll_zydm.change(function() {
			var zydms_ = jQuery("input[name='zydm_']");
			var zydm_njdm_ = jQuery("input[name='zydm_njdm']");	
			var t = document.getElementById("checkAll_zydm");	
			if(t.checked){
 				jQuery(zydms_).attr('checked',true);
 				jQuery(zydm_njdm_).attr('checked',true);
			}else{
 				jQuery(zydms_).attr('checked',false); 
 				jQuery(zydm_njdm_).attr('checked',false);
			}
		});
	}	
	
	// 绑定年级全选按钮
	if(checkAll_bjdm !=null){
		checkAll_bjdm.change(function() {
			var bjdms_ = jQuery("input[name='bjdm_']");
			var t = document.getElementById("checkAll_bjdm");
			if(t.checked){
 				jQuery(bjdms_).attr('checked',true);
			}else{
 				jQuery(bjdms_).attr('checked',false); 
			}
		});
	}	
	
	// 绑定已授权数据范围复选框(学院)
	var objects = jQuery('#objects').val();
	var data = objects.split("|");
	jQuery.each(bmdms_,function(j,n) {
	 for(var i=0;i<data.length;i++){
	   if(data[i]==jQuery(bmdms_[j]).attr("value")){
		 jQuery(bmdms_[j]).attr('checked',true);
	   }
	 }
	});
	// 绑定已授权数据范围复选框(学院年级)
	jQuery.each(bmdm_njdm_,function(j,n) {
	 for(var i=0;i<data.length;i++){
	   if(data[i]==jQuery(bmdm_njdm_[j]).attr("value")){
		 jQuery(bmdm_njdm_[j]).attr('checked',true);
	   }
	 }
	});
	// 绑定已授权数据范围复选框(全校)
	jQuery.each(xxdms_,function(j,n) {
	 for(var i=0;i<data.length;i++){
	   var temp = data[i].split("&")[0];
	   if(temp==jQuery(xxdms_[j]).attr("value")){
		 jQuery(xxdms_[j]).attr('checked',true);
	   }
	 }
	});	
	
	
	// 绑定保存功能
	if(btn_ccg != null){
 		btn_ccg.click(function(){
 			 var bmdm_ids = ""; 
 			 var zydm_ids = "";
 			 var bjdm_ids = "";
			 var bmdm_njdm_ids = "";
 			 var zydm_njdm_ids = "";
 			 var xxdm_ids = "";
 			 
 			 var bmdm_ids_not =""; 			 
 			 var zydm_ids_not = "";
 			 var bjdm_ids_not = "";
 			 var bmdm_njdm_ids_not = "";
 			 var zydm_njdm_ids_not = "";

 			 var map = {};
 			 var bmdms_ = jQuery("input[name='bmdm_']");
			 jQuery.each(bmdms_,function(i,n){
				 if(jQuery(n)){
				 	if(jQuery(n).is(':checked')){
				 	  bmdm_ids = bmdm_ids + jQuery(n).val() + ",";
				 	}
				 	else{
				 	  bmdm_ids_not = bmdm_ids_not + jQuery(n).val() + ",";
				 	}
				 }	 
			 });
			 var zydms_ = jQuery("input[name='zydm_']");
			 jQuery.each(zydms_,function(i,n){
				 if(jQuery(n)){
				 	if(jQuery(n).is(':checked')){
				 	  zydm_ids = zydm_ids + jQuery(n).val() + ",";
				 	}else{
				 	  zydm_ids_not = zydm_ids_not + jQuery(n).val() + ",";
				 	}
				 }	 
			 });
			 var bjdms_ = jQuery("input[name='bjdm_']");
			 jQuery.each(bjdms_,function(i,n){
				 if(jQuery(n)){
				 	if(jQuery(n).is(':checked')){
				 	  bjdm_ids = bjdm_ids + jQuery(n).val() + ",";
				 	}else{
				 	  bjdm_ids_not = bjdm_ids_not + jQuery(n).val() + ",";
				 	}
				 }	 
			 });
			 var bmdm_njdm_ = jQuery("input[name='bmdm_njdm']");
			 jQuery.each(bmdm_njdm_,function(i,n){
				 if(jQuery(n)){
				 	if(jQuery(n).is(':checked')){
				 	  bmdm_njdm_ids = bmdm_njdm_ids + jQuery(n).val() + ",";
				 	}else{
				 	  bmdm_njdm_ids_not = bmdm_njdm_ids_not + jQuery(n).val() + ",";
				 	}
				 }	 
			 });
			 var zydm_njdm_ = jQuery("input[name='zydm_njdm']");
			 jQuery.each(zydm_njdm_,function(i,n){
				 if(jQuery(n)){
				 	if(jQuery(n).is(':checked')){
				 	  zydm_njdm_ids = zydm_njdm_ids + jQuery(n).val() + ",";
				 	}else{
				 	  zydm_njdm_ids_not = zydm_njdm_ids_not + jQuery(n).val() + ",";
				 	}
				 }	 
			 });
			var xxdms_ = jQuery("input[name='xxdm_']");
			jQuery.each(xxdms_,function(i,n){
				 if(jQuery(n)){
				 	if(jQuery(n).is(':checked')){
				 	  xxdm_ids = xxdm_ids + jQuery(n).val();
				 	}else{
 
				 	}
				 }	 
			 });
			
			map["bmdm_ids"]   = bmdm_ids;
			map["zydm_ids"] = zydm_ids;
			map["bjdm_ids"] = bjdm_ids;
			map["xxdm_ids"] = xxdm_ids;				
			map["bmdm_njdm_ids"] = bmdm_njdm_ids;  
			map["zydm_njdm_ids"] = zydm_njdm_ids; 
			
			map["bmdm_ids_not"] = bmdm_ids_not;
			map["zydm_ids_not"] = zydm_ids_not;
			map["bjdm_ids_not"] = bjdm_ids_not;
			map["bmdm_njdm_ids_not"] = bmdm_njdm_ids_not;
			map["zydm_njdm_ids_not"] = zydm_njdm_ids_not;
			
			map["yh_id"] = '${yh_id}';
			map["js_id"] = '${js_id}';
		    jQuery.ajax({
			 url:"sjfw_bcSjfw.html",
			 type:"post",	 
			 dataType:"html",
			 data:map,
			 success:function(data){
			 	data = data.replace('"','');
			 	data = data.replace('"','');
		 		var	url = "yhgl_szssjsYh.html?zgh=${yh_id}";
		 		alert(data,'',{'clkFun':function(){iFClose();parent.location.reload();}});
		     },
		     error:function(){
		     	alert("数据保存异常！");
		     }
		 });
		 //console.log("bmdm_ids = " + bmdm_ids +"zydm_ids = " +zydm_ids +"bjdm_ids = " +bjdm_ids +"bmdm_njdm_ids = " +bmdm_njdm_ids+"zydm_njdm_ids = " + zydm_njdm_ids);
 		});
	}	
}

</script>
	<body>
	<!-- 隐藏域 已授权数据范围 -->
     <input type="hidden" id="objects" value="${objects}"/>  
	  <div class="position_xxxx after">
			<h3>数据范围控制：</h3>
			<s:if test="lists!=null">
			    <ul class="list_xxxx">
			    <s:iterator value="lists" status="st">
			    	<li <s:if test="#st.last">class="last"</s:if>><a href="javascript:void(0);" onclick="changeDisplay('${bm }')">按${zwmc }</a></li>
				</s:iterator>
			    </ul>
		    </s:if>
	  </div>
  
	  <!-- 全校 -->
	  <div id="SCHOOL" name="sDiv">
		 <div class="Financial_data" style="height:457px;">
		  <h3><span>学校名称</span> </h3>
			 <div class="demo_data2"><h4><input type="checkbox" value="-2" name="xxdm_">全校</h4></div>
		 </div>	
	  </div>
	   
	  <!-- 学院/部门列表 -->
	  <div id="ZFTAL_XTGL_BMDMB" name="sDiv">
       <div class="searchbox">
			  <p class="search_con">
                	<label>年级：</label>
					<s:select name="bmdm_njdm_id" id="bmdm_njdm_id" list="njdms" listKey="njdm_id"
						listValue="njmc" headerKey="全部" headerValue="全部" cssStyle="width:150px">
					</s:select>					
				</p>
	 </div>
	 <div class="Financial_data" style="height:430px;">
	  <h3><span>学院名称</span><em><input id="checkAll_bmdm" name="checkAll_bmdm" type="checkbox" /> 全选</em></h3>
		<div class="con" id="xydm_div"></div>
	 </div>	
		 <div id="xydm_pages" style="margin-left:10px;float:left; padding-top:0px"></div> 
	  </div>
	  
	  <!-- 专业列表 -->
	  <div id="ZFTAL_XTGL_ZYDMB" name="sDiv">
       <div class="searchbox">
		 <p class="search_con">
           <label>年级：</label>
		    <s:select name="njdm_zydm_id" id="njdm_zydm_id" list="njdms" listKey="njdm_id"
					listValue="njmc" headerKey="全部" headerValue="全部" cssStyle="width:150px">
			</s:select>					
		 </p>
	 </div>       
	 <div class="Financial_data" style="height:430px;">
	  <h3><span>专业名称</span><em><input id="checkAll_zydm" name="checkAll_zydm" type="checkbox" /> 全选</em></h3>
		<div class="con" id="zydm_div"></div>
	 </div>      
	 <div id="zydm_pages" style="margin-left:10px;float:left; padding-top:0px"></div> 
	 </div>
	  
	  <!-- 班级列表 -->
	  <div id="ZFTAL_XTGL_BJDMB" name="sDiv">
       <div class="searchbox">
			  <p class="search_con">
                	<label>学院：</label>
					<s:select name="bjdm_bmdm_id" id="bjdm_bmdm_id" list="bmdms" listKey="bmdm_id"
						listValue="bmmc" headerKey="全部" headerValue="全部" cssStyle="width:150px">
					</s:select>	
                	<label>专业：</label>
					<s:select name="bjdm_zydm_id" id="bjdm_zydm_id" list="zydms" listKey="zydm_id"
						listValue="zymc" headerKey="全部" headerValue="全部" cssStyle="width:150px">
					</s:select>		
			 </p>
	 </div>
	 <div class="Financial_data" style="height:430px;">
	  <h3><span>班级名称</span><em><input id="checkAll_bjdm" name="checkAll_bjdm" type="checkbox" /> 全选</em></h3>
		<div class="con" id="bjdm_div"></div>
	 </div>
		<div id="bjdm_pages" style="margin-left:10px;float:left; padding-top:0px"></div> 
	  </div>
      <div style="float: right; padding-right: 10px;">
	       <button id="btn_ccg" >保 存</button>
	       <button id="btn_gb" onclick="iFClose();return false;">关 闭</button>
	  </div>  
	</body>
</html>
