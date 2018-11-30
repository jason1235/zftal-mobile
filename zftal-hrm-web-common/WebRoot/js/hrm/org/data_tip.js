/**
 * overall数据行提示
 * @param obj
 */
function datatips(obj){
	var x = 0;  //设置偏移量
	var y = 20;
	var padding_right = 0;
	
	var t = $(obj);
	var l = 100;
	t.mouseover(function(e){
	    var datatip = "<div id=\"datatip\" style=\"z-index:9999;display:none;position:absolute;padding:10px;border:1px solid #999; color:#0457A7; background: #F2F2F2;\"></div>"; //创建 div 元素
	    var tip = $(datatip);
	    $(tip).append("工号:"+$(obj).find("[name='gh']").text());
	    $(tip).append("<br/>姓名:"+$(obj).find("[name='xm']").text());
	    $(tip).append("<br/>部门:"+$(obj).find("[name='dwm']").text());
		$("body").append(tip);	//把它追加到文档中
		l = $(tip).outerWidth();
		$("#datatip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": checkX(e.pageX)  + "px"
			}).show("fast");	  //设置x坐标和y坐标，并且显示
    });
	t.mouseout(function(){		
		$("#datatip").remove();   //移除 
    });
    t.mousemove(function(e){
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