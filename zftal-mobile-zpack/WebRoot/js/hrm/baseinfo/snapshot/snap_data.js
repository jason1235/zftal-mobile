/**
 * 请求快照分页数据
 */
function requestSnapData(){
	var successCall = function(d){
		try{
    		var data = $.parseJSON(d);
    		if(data.success==false){
    			tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
    			$("#window-sure").click(function() {
					divClose();
				});
    		}
		}catch(e){
			$("#snap_content").empty();
			$("#snap_content").append(d);
    		var pageSize = $("#pageSize","#snap").val();
    		fillRows(pageSize, "list_head2", "list_body2", false);//填充空行
		}
	};
	$("#windown-content").unbind("ajaxStart");
	var param = $("#snap").serialize();
	$.ajax({
		url:_path+"/baseinfo/snapshotData_page.html",
		type:"post",
		data:param,
		cache:false,
		dataType:"html",
		success:successCall
	});
	
}

function initTag(){
	tagRoll();
	$("#catalogs li:first").addClass("ha");
	$("#catalogs a").click(function(){
		$("input[name='classId']").val($(this).attr("alt"));
		$("#catalogs li").removeClass("ha");
		$(this).closest("li").addClass("ha");
		requestSnapData();
	});
}

function tagRoll(){
	var i=0;
	$(".btn_up").attr('class','btn_up_disable');
	$(".btn_up_down .btn_down").live('click',function(){
		var ulHeight=$("#catalogs ul").height();
		var len=Math.ceil(ulHeight/26);
		if(!$(this).parents("#catalogs").children("ul").is(":animated")){
		if(i<len-1){
		i++;
		$(this).siblings("span").attr("class","btn_up");
		$(this).parents("#catalogs").children("ul").animate({marginTop:(-26*i)+"px"},200);
		}
		if(i==len-1){
			$(this).attr('class','btn_down_disable');
		}
		}
	});
	$(".btn_up_down .btn_up").live('click',function(){
		var ulHeight=$("#catalogs ul").height();
		var len=Math.ceil(ulHeight/26);
		if(!$(this).parents("#catalogs").children("ul").is(":animated")){
		if(i>0){
		i--;
		$(this).siblings("span").attr("class","btn_down");
		$(this).parents("#catalogs").children("ul").animate({marginTop:(-26*i)+"px"},200);
		}
		if(i==0){
			$(this).attr('class','btn_up_disable');
		}
		}
	});
}