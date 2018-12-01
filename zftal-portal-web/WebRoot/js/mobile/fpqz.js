
var param="";
var wfpGrid ;
var yfpGrid;

/*
 * jqGrid 封装jqGrid基类,依赖prototype.js
 */
BaseJqGrid_mobile_fpyh = Class.create({
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
		page : "member.queryModel.currentPage",
		rows : "member.queryModel.showCount",
		order : "member.queryModel.sortOrder",
		sort : "member.queryModel.sortName"
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
    jQuery.post(_path+"/pushMsg/group_saveFp.html",{"member.userId":userId,"member.groupId":modelID},function(data){
    	var arrIds = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery('#yfpTabGrid').jqGrid('addRowData', i, data.member);
	},"json");
}

var url = _path+'/pushMsg/group_fpyhWfpYhxx.html?member.groupId='+modelID;
var WfpGrid = Class.create(BaseJqGrid_mobile_fpyh,{  
				caption : "未分配用户列表",
				pager: "pager", //分页工具栏  
		        url:  url, //这是Action的请求地址  
		        colModel:[
		        	 {label:'用户名',name:'userId', index: 'user_id',key:true,align:'center'},
				     {label:'姓 名',name:'userName', index: 'user_name',align:'center'}//,
				],
				sortname: 'user_id', //首次加载要进行排序的字段 
	         	sortorder: "desc",
	         	ondblClickRow:wfp
	         	});

//查询
function searchResult(){
	var map = {};
	map["member.userId"] = jQuery('#userId').val();
	map["member.userName"] = jQuery('#userName').val();

	search('tabGrid',map);
}

function yfp(rowId,states){
	var userId=jQuery("#yfpTabGrid").jqGrid("getCell", rowId,"userId"); 
	jQuery("#yfpTabGrid").jqGrid("delRowData", rowId); 
    jQuery.post(_path+"/pushMsg/group_removeFp.html",{"member.userId":userId,"member.groupId":modelID},function(data){
    	var arrIds = jQuery("#tabGrid").jqGrid('getDataIDs');
        var i = arrIds.length + 1; 
        jQuery('#tabGrid').jqGrid('addRowData', i, data.member);
	},"json");
    removeFp(userId);
}

var YfpGrid = Class.create(BaseJqGrid_mobile_fpyh,{  
	caption : "已分配用户列表",
    url: _path+'/pushMsg/group_fpyhYfpYhxx.html?member.groupId='+modelID, //这是Action的请求地址  
    rowNum : 1000,
    rowList : [1000],
    colModel:[
    	 {label:'用户名',name:'userId', index: 'user_id',key:true,align:'center'},
		 {label:'姓 名',name:'userName', index: 'user_name',align:'center'}//,
	],
	sortname: 'user_id', //首次加载要进行排序的字段 
 	sortorder: "desc",
 	ondblClickRow:yfp
});


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