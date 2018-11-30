$(function(){
	initProgress();
});

function initProgress(){
//	var html = ""
//		+"<div style=\"position:absolute;width:400px;\" >"
//		+"<div class=\"page_loading\" id=\"_progress\" style=\"display:none;left:0px;top:0px;background:white;\">"
//		+"<div  class=\"load_con\" >"
//		+"	<div class=\"pic\"></div>"
//		+"</div>"
//		+"<p>页面正在加载中，请稍后。。。</p>"
//		+"</div>"
//		+"</div>";
	var html = html();
	var content = $(html);
	var height = $(content).height();
	var width = $(content).width();
	$(content).css("top",($(window).height()-height)/2);
	$(content).css("left",($(window).width()-width)/2);
	$("body").append($(content));
	
	function html(){
		var text = ""
			+"<div style=\"position:absolute;width:400px;height:160px;\">"
			+"	<div id=\"_progress\" class=\"open_win\" style=\"display:none;\">"
			+"	<table class=\"formlist\" width=\"100%\">"
			+"		<tbody>"
			+"		<tr>"
			+"			<th class=\"title_02\">提示信息</th>"
			+"		</tr>"
			+"		<tr>"
			+"			<td>"
			+"			<p class=\"loading\">正在处理中，请稍后...</p>"
			+"			</td>"
			+"		</tr>"
			+"		</tbody>"
			+"	</table>"
			+"	</div>"
			+"</div>";
		return text;
	}
}

function updateProgress(url,interval){
	if(interval==null){
		interval = 500;
	}
	if($("#_progress").is(":hidden"))
		$("#_progress").show();
/*	$.post(url,"",function(data){
		paint(data,interval,url);
	},"json");*/
	
	$.ajax({type:"post",
		url:url,
		success:function(data){
			paint(data,interval,url);
		},
		datatType:"json",
		global:false
	});
}



/**
 * data.success 请求是否成功
 * 		progress 进度条值
 * 		progress.finish  进度条是否完成
 * @param data
 * @return
 */
function paint(data,interval,url){
	if(data.success){
		if(!data.progress.finish){
			$("#_progress").find("p").text("完成进度:"+data.progress.progress);
			setTimeout("updateProgress('"+url+"',"+interval+")", interval);
		}else{
			$("#_progress").find("p").text("已完成");
			$("#_progress").fadeOut("slow",function(){
				window.location.reload();
			});
		}
	}else{
		$("#_progress").find("p").text(data.progress.description+",操作已终止");
		$("#_progress").fadeOut(2000);
	}
}