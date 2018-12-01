//设置弹出框，需要引进modal.jsp
function setIfram(left,top,height,width,iframesrc){
	$("#myFrame").attr("src",iframesrc);//设置连接
	$('#my-modal').css("left",left);//设置距离左边的比例
	$('#my-modal').css("top",top);//设置距离顶部的比例
	$("#myFrame").height(height);//设置iframe高度
	$("#my-modal").width(width);//设置iframe宽度
	//$("#title").empty();
	//$("#title").text(title);
};

//设置错误提示信息弹出框，message代码需要弹出的信息，需要引进modal.jsp
function errorAlert(message){
	var $alert = $("#my-alert");
	$("#alertContent").empty();
	$("#alertContent").text(message);
	$alert.modal();
};

//增加编辑页面中的错误提示，message代表需要提示的警告或者错误，需要引进alert.jsp
function dangerAlert(message){
	$("#confirmmessage").empty();
	$("#confirmmessage").text(message);
	$('#dangerdiv').show();
};
//关闭alert.jsp
function dangerAlertclose(){
	$('#dangerdiv').hide();
}