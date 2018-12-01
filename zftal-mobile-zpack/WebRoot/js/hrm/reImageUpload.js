//function imageMouseover(content,name) {
//	var fileDiv = $(content).closest(".img_"+name);
//	var form=$(fileDiv).find('#imageCommitBtn_'+name).closest("form");
//	form.attr("target","uploadimage_iframe_"+name);
//}

function getImage(content,name) {
	return getImage(content,name,100,200) ;
}

function getImage(content,name, size,width) {
	
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	var fileInput=$(content)[0];
	var imageDiv = $(content).closest(".img_"+name);
	
	var type=imageDiv.attr("name");
	var input = $("#"+name);
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
		//showWarning("�������ܴ���"+size+"k");
		$("#imageError").append("�������ܴ���"+size+"k"+"���������ϴ���");
		$("#imageError").css('display','block'); 
		$('#window-sure').click(function() {
			divClose();
		});
		return;
	}
	
	startImageUp();

	// ��ȡ�ļ��ϴ�key
	function startImageUp() {
		if ($(imageDiv).find('#imageCommitBtn_'+name).val() == ''
				|| $(imageDiv).find('#imageCommitBtn_'+name).val() == null) {
			showWarning('��ѡ���ϴ���ͼƬ');
			$('#window-sure').click(function() {
				divClose();
			});
			return false;
		}
		$.post(_path + '/file/file_startImageUp.html', "fileName="+$(imageDiv).find('#imageCommitBtn_'+name).val(), function(data) {
			if (data.success) {
				$("#imageError").css('display','none'); 
				$(data).find('#p_in_'+name).css('width', '0%');
				uploadImage(data.key);
				readImagePrograss(data.key);
			} else {
				$("#imageError").append(data.text+"���������ϴ���");
				$("#imageError").css('display','block'); 
				//showWarning(data.text);
				$('#window-sure').click(function() {
					divClose();
				});
			}
		});
	}
	// �ϴ��ļ�
	function uploadImage(key) {
		var fileName=$(imageDiv).find('#imageCommitBtn_'+name).val();
		var id=$(imageDiv).find('#imageCommitBtn_'+name).attr("id");
		var url=_path +'/file/file_uploadimage.html?fileProp.width='+width+'&fileProp.currentName=imageCommit_'+name+'&fileProp.maxSize='+size+'&key=' + key + '&fileName='
			+fileName+'&fileGuid=' + input.val()+'&newSession=false';
		jQuery.ajaxFileUpload({
			  url:url,//�������˳���
			  secureuri:false,
			  fileElementId:id,
			  success:function(data,type){
				  
				//if (type=='success'){
				//	alert("�ϴ��ɹ�");
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
	// ��ȡ�ļ����
	function readImagePrograss(key) {
		$.post(_path + '/file/file_readProgress.html?key=' + key, null,
				function(data) {
					if (data.success) {
						if(data.exceptionMaxSize){
							showWarning(data.errorMessage);
							
							$('#window-sure').click(function() {
								divClose();
							});
							return false;
						}
						updateImageProcess(data.key, data.len, data.total, data.guid,
								data.isComplete);
					}
				});
	}
	
	// ���½��
	function updateImageProcess(key, len, total, guid, isComplete) {
		if (total == 0) {
			readImagePrograss(key);
			return;
		}
		$(imageDiv).find('#p_in_'+name)
				.css('width', (Math.round(len / total * 100)) + '%');
		if (isComplete) {
			if (len >= total) {
				$(imageDiv).find('#image_'+name).attr("src",_path
						+ '/file/file_'+type+'.html?fileGuid=' + guid+"&date="+new Date().getTime());;
				input.val(guid);
				$('#imageCommitBtn_'+name).closest("div").find("font[name='imagetypename']").empty();
				$('#imageCommitBtn_'+name).closest("div").find("font[name='imagetypename']").append("上传成功");
				//document.getElementById("imagetypename").innerHTML="�ϴ��ɹ�";
				//$("#filetypename").empty();
				//$("#filetypename").html("�ϴ��ɹ�");
			}
		} else {
			readImagePrograss(key);
		}
	}
}