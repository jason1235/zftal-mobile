var alert1=window.alert;


/**
 * 弹出指定内容
 * @param title
 * @param content
 * @param width
 * @param height
 */
function tipsWindown(title, url, width, height, handler) {
	
//	if (content.indexOf("id:") > -1){
//		var id = "#"+content.split("id:")[1];
//		content = jQuery(id).html();
//	}
	
	ymPrompt.win({message:url,
		  width:width,
		  height:height,
		  title:title,
		  maxBtn:true,
		  minBtn:true,
		  iframe:true,
		  showShadow:false,
		  useSlide:true,
		  maskAlphaColor:"#FFFFFF",
		  maskAlpha:0.3,
		  handler:handler
		}
	);
	
}

/**
 * 弹出页面内容层关闭
 */
function divLayerClose(){
	ymPrompt.close();
}


/**
 * 弹出询问框
 */
function showConfirmDivLayer() {
	
	var argumentsArr = Array.prototype.slice.call(arguments);
	var _fun = null;
	
	ymPrompt.confirmInfo({
			title:"系统提示",
			message:argumentsArr[0],
			width:340,
			height:160,
			maskAlphaColor:"#FFFFFF",
			maskAlpha:0.3,
			useSlide:true,
			handler:function(type){
				if (type=="ok"){
					_fun = argumentsArr[1]["okFun"];
				} else if (type == "cancel"){
					_fun = argumentsArr[1]["cancelFun"];
				}
				
				if (_fun != null){
					_fun();
				}
				
			}
		}
	);
}

/**
 * 弹出警告框
 */
function showAlertDivLayer() {
	var argumentsArr = Array.prototype.slice.call(arguments);
	if(argumentsArr[0] == null) return;
	
	var clickFun = null;
	
	if (argumentsArr.length == 3){
		clickFun = argumentsArr[2]["clkFun"];
	}
	ymPrompt.alert({
		title:"系统提示",
		useSlide:true,
		maskAlphaColor:"#FFFFFF",
		maskAlpha:0.3,
		message:argumentsArr[0],
		width:340,
		height:160,
		//showMask:false,
		handler:clickFun
	});
	//setTimeout(function(){ymPrompt.doHandler();},3000);
}


/**
 * 弹出iframe窗口关闭
 */
function iFClose(){
	parent.window.ymPrompt.close();
}


/**
 * 在弹出iframe窗口中刷新父页面，并关闭窗口
 */
function refershParent(){
	jQuery(parent.window.document).find('#search_go').click();
	iFClose();
}
var locationAlert=window.alert;
window.alert = showAlertDivLayer;

/**
 * 把原有的弹出窗口转成ymPormpt式弹出
 * @param title
 * @param width
 * @param height
 * @param url
 */
function showWindow(title,width,height,url,handler){
	
	ymPrompt.win({message:url,
				  width:width,
				  height:height,
				  title:title,
				  maxBtn:true,
				  minBtn:true,
				  iframe:true,
				  showShadow:false,
				  useSlide:true,
				  maskAlphaColor:"#FFFFFF",
				  maskAlpha:0.3,
				  handler:handler
			}
	);
}

/**
 * 把原有的弹出窗口转成ymPormpt式弹出
 * @param title
 * @param width
 * @param height
 * @param url
 */
function showWindowV1(title,url,width,height,handler){
	
	ymPrompt.win({message:url,
				  width:width,
				  height:height,
				  title:title,
				  maxBtn:true,
				  minBtn:true,
				  iframe:true,
				  showShadow:false,
				  useSlide:true,
				  maskAlphaColor:"#FFFFFF",
				  maskAlpha:0.3,
				  handler:handler
			}
	);
}

/**
 * 把原有的弹出窗口转成ymPormpt式弹出
 * @param title
 * @param width
 * @param height
 * @param url
 */
function showWindowV2(title,url,width,height,handler){
	jQuery.post(url,{},function(data){
		ymPrompt.win({message:data,
					  width:width,
					  height:height,
					  title:title,
					  maxBtn:true,
					  minBtn:true,
					  showShadow:false,
					  useSlide:true,
					  maskAlphaColor:"#FFFFFF",
					  maskAlpha:0.3,
					  handler:handler
				}
		);
		//$("#cancel").click(function(){iFClose();});
	});
}

/**
 * 弹出窗口，非iframe
 * @param title
 * @param url
 * @param width
 * @param height
 */
function showDivWindow(title,width,height,url){
	
	jQuery.post(url,{},function(data){
		ymPrompt.win({
			message:data,
			title:title,
			width:width,
			height:height,
			maxBtn:true,
			minBtn:true,
			useSlide:true,
			maskAlphaColor:"#FFFFFF",
			maskAlpha:0.3
		});
	});
}

