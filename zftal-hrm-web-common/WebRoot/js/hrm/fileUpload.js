//function fileMouseover(content,name) {
//	var fileDiv = $(content).closest(".file_"+name);
//	var form=$(fileDiv).find('#fileCommitBtn_'+name).closest("form");
//	form.attr("target","uploadfile_iframe_"+name);
//}

function getFile(content,name) {
	return getFile(content,name,5*1024*1024) ;
}
var path = "";
var filetype = "";
function getFile(content,name,size,webpath,fileType) {
	path = webpath;
	filetype = fileType;
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	var fileInput=$(content)[0];
	var fileDiv = $(content).closest(".file_"+name);
	var fileHref = $("#fileHref");
	var input = $("#"+name);
	var form=$(fileDiv).find('#fileCommitBtn_'+name).closest("form");
//	fileMouseover(content,name);
	if (isIE && !fileInput.files) {
		//var filePath = $(fileInput).val();
		//var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		//var file = fileSystem.GetFile(filePath);
		//fileSize = file.Size;
	} else {
		fileSize =fileInput.files[0].size;
		console.log(fileSize);
	}
	
	fileSize = fileSize / 1024 / 1024;
	
	if (fileSize > size) {
		showWarning("附件不能大于"+size+"M");
		$('#window-sure').click(function() {
			divClose();
		});
		return;
	}
	
	startFileUp();

	// 获取文件上传key
	function startFileUp() {
		if ($(fileDiv).find('#fileCommitBtn_'+name).val() == ''
				|| $(fileDiv).find('#fileCommitBtn_'+name).val() == null) {
			showWarning('请选择上传的图片');
			$('#window-sure').click(function() {
				divClose();
			});
			return false;
		}
		$.post(_path + '/file/file_startFileUp.html', "fileName="+$(fileDiv).find('#fileCommitBtn_'+name).val()+"&filetype="+filetype, function(data) {
			if (data.success) {
				$("#fileError").css('display','none'); 
				$(data).find('#p_in_'+name).css('width', '0%');
				uploadFile(data.key);
				readFilePrograss(data.key);
			} else {
				//fileHref.empty();
				//fileHref.append("无");
				$("#fileError").append(data.text+"，请重新上传！");
				$("#fileError").css('display','block'); 
				//alert(data.text);
				//showWarning(data.text);
				$('#window-sure').click(function() {
					divClose();
				});
			}
		});
	}
	// 上传文件
	function uploadFile(key) {
		var fileName=$(fileDiv).find('#fileCommitBtn_'+name).val();
		var id=$(fileDiv).find('#fileCommitBtn_'+name).attr("id");
		var url=_path +'/file/file_uploadattachement.html?fileProp.currentName=fileCommit_'+name+'&fileProp.maxSize='+size+'&key=' + key + '&fileName='
			+fileName+'&fileGuid=' + input.val()+'&newSession=false';
		jQuery.ajaxFileUpload({
			  url:url,//服务器端程序
			  secureuri:false,
			  fileElementId:id,
			  success:function(data,type){
			  }
		});
//		form.attr( "action",
//			_path +'/file/file_uploadattachement.html?fileProp.currentName=fileCommit_'+name+'&fileProp.maxSize='+size+'&key=' + key + '&fileName='
//			+$(fileDiv).find('#fileCommitBtn_'+name).val()+'&fileGuid='
//			+ input.val()+'&newSession=false');
//		form.submit();
//		form.removeAttr("target");
	}
	// 读取文件进度
	function readFilePrograss(key) {
		$.post(_path + '/file/file_readProgress.html?key=' + key, null,
				function(data) {
					if (data.success) {
						if(data.exceptionMaxSize){
							showWarning(data.errorMessage);
							
							$('#window-sure').click(function() {
								divClose();
							});
							return;
						}
						updateFileProcess(data.key, data.len, data.total, data.guid,
								data.isComplete,data.fileName);
					}
				});
	}
	
	// 更新进度
	function updateFileProcess(key, len, total, guid, isComplete,fileName) {
		if (total == 0) {
			readFilePrograss(key);
			return;
		}
		$(fileDiv).find('#p_in_'+name)
				.css('width', (Math.round(len / total * 100)) + '%');
		if (isComplete) {
			if (len >= total) {
				input.val(guid);
				fileHref.empty();
				fileName = fileName.substring(1,fileName.length)
				fileHref.append("<a href='" + path +"/file/attachement_download.html?model.guId="+guid+"' id='fileHref'>"+fileName+"</a>");
				$('#fileCommitBtn_'+name).closest("div").find("font[name='filetypename']").empty();
				$('#fileCommitBtn_'+name).closest("div").find("font[name='filetypename']").append("上传成功");
				//document.getElementById("filetypename").innerHTML="上传成功";
				//$("#filetypename").empty();
				//$("#filetypename").html("上传成功");
				//alert("上传成功");
			}
		} else {
			readFilePrograss(key);
		}
	}
}