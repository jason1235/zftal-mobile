//function imageMouseover(content,name) {
//	var fileDiv = $(content).closest(".img_"+name);
//	var form=$(fileDiv).find('#imageCommitBtn_'+name).closest("form");
//	form.attr("target","uploadimage_iframe_"+name);
//}
var imagename;
var imagecontent;
var imageinput;
var guid;
var imageName;
var proportion;
var parentImangeDiv;
function getImage(content,name) {
	return getImage(content,name,100,200) ;
}

function cutsuccess(){
	//var imageDiv = $(imagecontent).closest(".img_"+imagename);
	
	$('#image_'+imagename).attr("src",_path
			+ '/file/file_image.html?fileGuid=' + guid+"&date="+new Date().getTime());;
	imageinput.val(guid);
	$('#imageCommitBtn_'+imagename).closest("div").find("font[name='imagetypename']").empty();
	$('#imageCommitBtn_'+imagename).closest("div").find("font[name='imagetypename']").append("上传成功");
	$('#myModal_toji').modal('hide');
}

function cutcancel(){
	$('#myModal_toji').modal('hide');
}

function getImage(content,name, size,width) {
	imagecontent = content;
	imagename = name;
	proportion = width;
	var suffix;
	var imagewidth;
	var imageheight;
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	var fileInput=$(content)[0];
	var imageDiv = $(content).closest(".img_"+name);
	parentImangeDiv = $(content).closest(".img_"+name);
	
	var type=imageDiv.attr("name");
	var input = $("#"+name);
	imageinput   = $("#"+name);
	var form=$(imageDiv).find('#imageCommitBtn_'+name).closest("form");
//	imageMouseover(content,name);
	if (isIE && !fileInput.files) {
		//var filePath = $(fileInput).val();
		//var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		//var file = fileSystem.GetFile(filePath);
		//fileSize = file.Size;
	} else {
		fileSize =fileInput.files[0].size;
		console.log(fileSize);
	}
	
	fileSize = fileSize / 1024;
	
	if (fileSize > size) {
		$('#imageCommitBtn_'+name).closest("div[name='image']").find(".errorspan").empty();
		//showWarning("附件不能大于"+size+"k");
		$('#imageCommitBtn_'+name).closest("div[name='image']").find(".errorspan").append("附件不能大于"+size+"k"+"，请重新上传！");
		$('#imageCommitBtn_'+name).closest("div[name='image']").find(".errorspan").css('display','block'); 
		//$("#imageError").append("附件不能大于"+size+"k"+"，请重新上传！");
		//$("#imageError").css('display','block'); 
		$('#window-sure').click(function() {
			divClose();
		});
		return;
	}
	
	startImageUp();

	// 获取文件上传key
	function startImageUp() {
		if ($(imageDiv).find('#imageCommitBtn_'+name).val() == ''
				|| $(imageDiv).find('#imageCommitBtn_'+name).val() == null) {
			showWarning('请选择上传的图片');
			$('#window-sure').click(function() {
				divClose();
			});
			return false;
		}
		$.post(_path + '/file/file_startImageUp.html', "fileName="+$(imageDiv).find('#imageCommitBtn_'+name).val(), function(data) {
			if (data.success) {
				suffix    = data.suffix;
				imageName = data.imageName;
				$('#imageCommitBtn_'+name).closest("div[name='image']").find(".errorspan").empty();
				$('#imageCommitBtn_'+name).closest("div").find(".errorspan").css('display','none'); 
				//$("#imageError").css('display','none'); 
				$(data).find('#p_in_'+name).css('width', '0%');
				uploadImage(data.key);
				readImagePrograss(data.key);
				
			} else {
				$('#imageCommitBtn_'+name).closest("div[name='image']").find(".errorspan").empty();
				$('#imageCommitBtn_'+name).closest("div[name='image']").find(".errorspan").append(data.text+"，请重新上传！");
				$('#imageCommitBtn_'+name).closest("div[name='image']").find(".errorspan").css('display','block'); 
				//$("#imageError").append(data.text+"，请重新上传！");
				//$("#imageError").css('display','block'); 
				//showWarning(data.text);
				$('#window-sure').click(function() {
					divClose();
				});
			}
		});
	}
	// 上传文件
	function uploadImage(key) {
		var fileName=$(imageDiv).find('#imageCommitBtn_'+name).val();
		if(!imageName.endWith("gif")){
			fileName = (new Date()).valueOf()+fileName.substring(fileName.lastIndexOf("\\")+1,fileName.length);
		}
		imageName = fileName;
		var id=$(imageDiv).find('#imageCommitBtn_'+name).attr("id");
		var url=_path +'/file/file_uploadimage.html?fileProp.width='+width+'&fileProp.currentName=imageCommit_'+name+'&fileProp.maxSize='+size+'&key=' + key + '&fileName='
			+fileName+'&fileGuid=' + input.val()+'&newSession=false';
		jQuery.ajaxFileUpload({
			  url:url,//服务器端程序
			  secureuri:false,
			  fileElementId:id,
			  success:function(data,type){
				  console.log(data);
				//if (type=='success'){
				//	alert("上传成功");
				//}
			  }
		});
//		var fileName=$(imageDiv).find('#imageCommitBtn_'+name).val();
//		form.attr( "action",
//				_path +'/file/file_uploadimage.html?fileProp.currentName=imageCommit_'+name+'&fileProp.maxSize='+size+'&key=' + key + '&fileName='
//				+fileName+'&fileGuid=' + input.val()+'&newSession=false');
//		form.submit();
//
//		form.removeAttr("target");
	}
	// 读取文件进度
	function readImagePrograss(key) {
		$.post(_path + '/file/file_readProgress.html?key=' + key+'&fileName='+imageName, null,
				function(data) {
					if (data.success) {
						if(data.exceptionMaxSize){
							showWarning(data.errorMessage);
							
							$('#window-sure').click(function() {
								divClose();
							});
							return false;
						}
						if(data.isComplete){
							guid      = data.guid;
							if(!imageName.endWith("gif")){
								//imagewidth     = data.width;
								imageheight    = data.height;
								//console.log(suffix);
								//console.log(imageName);
								//console.log(imagewidth);
								//console.log(imageheight);
								var url = _path+"/file/file_imgcrop.html?tag=0&oldImgPath=/homeimages/"+imageName+"&imgFileExt="+suffix+"&imgRoot=/homeimages/&imageName="+imageName+"&guid="+guid+"&proportion="+proportion;
								//window.location.href=url;
								$("#myModal_toji").find(".modal-content").width(900);
								$("#myModal_toji").find(".modal-content").height(imageheight+230);
								$("#myFrame").attr("src", url);
								$("#myFrame").height(imageheight+230);
								$(".modal-dialog").css("margin-left", "100px");
								$("#myModal_toji").modal("show");
								window.parent.scroll(0,0);
							}else{
								cutsuccess();
							}
						};
						updateImageProcess(data.key, data.len, data.total, data.guid,
								data.isComplete);
					}
				});
	}
	
	String.prototype.endWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  return false;
		if(this.substring(this.length-str.length)==str)
		  return true;
		else
		  return false;
		return true;
		}
	
	
	
	// 更新进度
	function updateImageProcess(key, len, total, guid, isComplete) {
		if (total == 0) {
			readImagePrograss(key);
			return;
		}
		//$(imageDiv).find('#p_in_'+name).css('width', (Math.round(len / total * 100)) + '%');
		if (isComplete) {
			if (len >= total) {
//				$(imageDiv).find('#image_'+name).attr("src",_path
//						+ '/file/file_'+type+'.html?fileGuid=' + guid+"&date="+new Date().getTime());;
//				input.val(guid);
//				$('#imageCommitBtn_'+name).closest("div").find("font[name='imagetypename']").empty();
//				$('#imageCommitBtn_'+name).closest("div").find("font[name='imagetypename']").append("上传成功");
				//document.getElementById("imagetypename").innerHTML="上传成功";
				//$("#filetypename").empty();
				//$("#filetypename").html("上传成功");
			}
		} else {
			readImagePrograss(key);
		}
	}
}