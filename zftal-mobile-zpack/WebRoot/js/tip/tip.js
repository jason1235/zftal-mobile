/**
 * overall数据行提示
 * @param obj
 */
function tip(target,content){
	var x = 0;  //设置偏移量
	var y = 20;
	var padding_right = 0;
	
	var l = 100;
	target=$(target);
	target.mouseover(function(e){
	    var datatip = "<div id=\"datatip\" style=\"z-index:9999;display:none;position:absolute;padding:10px;border:1px solid #999; color:#0457A7; background: #F2F2F2;\"></div>"; //创建 div 元素
	    var tip = $(datatip);
	    $(tip).append(content);
		$("body").append(tip);	//把它追加到文档中
		l = $(tip).outerWidth();
		$("#datatip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": checkX(e.pageX)  + "px"
			}).show("fast");	  //设置x坐标和y坐标，并且显示
    });
	target.mouseout(function(){		
		$("#datatip").remove();   //移除 
    });
	target.mousemove(function(e){
		$("#datatip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": checkX(e.pageX)  + "px"
			});
	});

    function checkX(mouseX){
    	var width = $(document).width();
    	//var w = $("#datatip").width();
    	var border = width-l-x-padding_right;
    	if(mouseX+x<border){
    		return mouseX+x;
    	}else{
    		return mouseX-l;
    	}
    }
}

/**
 * overall数据行提示
 * @param obj
 */
function tips(target,content){
	var x = 0;  //设置偏移量
	var y = 20;
	var padding_right = 0;
	
	var l = 100;
	target=$(target);
	target.mouseover(function(e){
	    var datatip = "<div id=\"datatip\" style=\"z-index:9999;display:none;position:absolute;padding:10px;border:1px solid #999; color:#0457A7; background: #F2F2F2;\"></div>"; //创建 div 元素
	    var tip = $(datatip);
	    $(tip).append(content);
		$("body").append(tip);	//把它追加到文档中
		l = $(tip).outerWidth();
		$("#datatip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": (e.pageX+x)  + "px"
			}).show("fast");	  //设置x坐标和y坐标，并且显示
    });
	target.mouseout(function(){		
		$("#datatip").remove();   //移除 
    });
	target.mousemove(function(e){
		$("#datatip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": (e.pageX+x)  + "px"
			});
	});
}