
var param="";
var wfpGrid ;
var yfpGrid;

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
	var zgh=jQuery("#tabGrid").jqGrid("getCell", rowId,"zgh"); 
	jQuery("#tabGrid").jqGrid("delRowData", rowId); 
    jQuery.getJSON( _path+"/xtgl/jsgl_fpyhCxYhxx.html",{"zgh":zgh},function(data){
    	var arrIds = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery('#yfpTabGrid').jqGrid('addRowData', i, data);
    });
    saveFp(zgh);
}

var url = _path+'/xtgl/jsgl_fpyhWfpYhxx.html?jsdm='+jsdm;
var WfpGrid = Class.create(BaseJqGrid_jsgl_fpyh,{  
				caption : "未分配用户列表",
				pager: "pager", //分页工具栏  
		        url:  url, //这是Action的请求地址  
		        colModel:[
		        	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
				     {label:'姓 名',name:'xm', index: 'xm',align:'center'}//,
				],
				sortname: 'zgh', //首次加载要进行排序的字段 
	         	sortorder: "desc",
	         	ondblClickRow:wfp
	         	});

//查询
function searchResult(){
	var map = {};
	map["zgh"] = jQuery('#zgh').val();
	map["xm"] = jQuery('#xm').val();
	map["sfqy"] = jQuery('#sfqy').val();

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
    jQuery.getJSON( _path+"/xtgl/jsgl_fpyhCxYhxx.html",{"zgh":zgh},function(data){
    	var arrIds = jQuery("#tabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery('#tabGrid').jqGrid('addRowData', i, data);
        
    });
    removeFp(zgh);
}

var YfpGrid = Class.create(BaseJqGrid_jsgl_fpyh,{  
	caption : "已分配用户列表",
    url: _path+'/xtgl/jsgl_fpyhYfpYhxx.html?jsdm='+jsdm, //这是Action的请求地址  
    rowNum : 1000,
    rowList : [1000],
    colModel:[
    	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
	     {label:'姓 名',name:'xm', index: 'xm',align:'center'},
	     {label:'数据范围',name:'sjfw', index: 'sjfw',align:'center',
	    	 formatter:function(cellvalue, options, rowObject){
	    	 	var html="<span id='sjfw_" + rowObject.zgh + "'></span>";
	    	 	
	    	 	jQuery.post( _path+"/dataprivilege/deptFilter_getOrgsText.html",{"deptFilter.userId":rowObject.zgh,"deptFilter.roleId":jsdm},function(data){
	    	 		jQuery("#sjfw_"+rowObject.zgh).html(data.simple);
	    	 		jQuery("#sjfw_"+rowObject.zgh).attr("name",data.whole);
	    	 		jQuery("#sjfw_"+rowObject.zgh).mousemove(function(){
						datatips(jQuery(this));
					})
	    	    });
	    	 	
	    		return html;
	    	}},
	     {label:'数据授权',name:'privillege',align:'center',
				formatter:function(cellvalue, options, rowObject){
	 	    		return "<a href=\"#\" id='" + rowObject.zgh + "' name='privillege' style='color:blue;text-decoration:underline;'>数据授权</a>";
	 	    	}}
	],
	sortname: 'zgh', //首次加载要进行排序的字段 
 	sortorder: "desc",
 	ondblClickRow:yfp
});

function saveFp(userId){
	  jQuery.post(_path+"/xtgl/jsgl_saveFp.html",{"yhCbv":userId,"jsdm":jsdm},function(data){
				jQuery.post(_path+"/dataprivilege/deptFilter_save.html",{"deptFilter.userId":userId,"deptFilter.roleId":jsdm,"selectId":"all","deptFilter.dataType":"all"},function(data1){
					jQuery.post( _path+"/dataprivilege/deptFilter_getOrgsText.html",{"deptFilter.userId":userId,"deptFilter.roleId":jsdm},function(data2){
						jQuery("#sjfw_"+userId).html(data2.simple);
						jQuery("#sjfw_"+userId).attr("name",data2.whole);
					 },"json");
				},"json");
	  },"json");
}

function removeFp(userId){
	jQuery.post(_path+"/dataprivilege/deptFilter_remove.html",{"deptFilter.userId":userId,"deptFilter.roleId":jsdm},function(data){
		if(data.success){
			jQuery.post(_path+"/xtgl/jsgl_removeFp.html",{"zgh":userId,"jsdm":jsdm},function(data){
				  
			},"json");
		}
		else{
			alert(data.text,'',{
			  'clkFun' : function() {
			  iFClose();
			  }
		  });
		}
	});
}

/**
 * 数据行提示
 * @param obj
 */
function datatips(obj){
	var x = 0;  //设置偏移量
	var y = 20;
	var padding_right = 0;
	var t = jQuery(obj);
	var l = 100;
	t.mouseover(function(e){
	    var datatip = "<div id=\"datatip\" style=\"z-index:9999;display:none;position:absolute;padding:10px;border:1px solid #999; color:#0457A7; background: #F2F2F2;\"></div>"; //创建 div 元素
	    var tip = jQuery(datatip);
	    var data = jQuery(obj).attr("name");
	    jQuery(tip).append(data);
	    jQuery("body").append(tip);	//把它追加到文档中
		l = jQuery(tip).outerWidth();
		jQuery("#datatip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": checkX(e.pageX)  + "px"
			}).show("fast");	  //设置x坐标和y坐标，并且显示
    });
	t.mouseout(function(){		
		jQuery("#datatip").remove();   //移除 
    });
    t.mousemove(function(e){
    	jQuery("#datatip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": checkX(e.pageX)  + "px"
			});
	});
    
    function checkX(mouseX){
    	var width = jQuery(document).width();
    	var border = width-l-x-padding_right;
    	if(mouseX+x<border){
    		return mouseX+x;
    	}else{
    		return mouseX-l;
    	}
    }
    
}


