<%@page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/commons/hrm/head.ini" %>
   </head>  
   <body>  
       <div class="img">  
          <form id="uploadfile_form" name="uploadfile_form" enctype="multipart/form-data"    
                method="post" target="uploadfile_iframe"> 
                <div class="img_content">
               		<img id="image1"> 
               		<button onclick="startImageUp()">提交</button>
                </div>
               <div id="p_out" style=" width:200px; height:6px; margin:10px 0 0 0; padding:1px; font-size:6px; border:solid #6b8e23 1px;">
               		<div id="p_in" style=" width:0%;  height:100%;  background-color:#6b8e23;  margin:0; padding:0; "></div>
               </div>    
               <input name="file" type="file" id="fileBtn" style="width:200px;"/>
          </form>    
          <iframe frameborder="0" style="border:none; width:0; height:0;" id="uploadfile_iframe" name="uploadfile_iframe" src="javascript:void(0)" ></iframe>    
       </div>  
         
   </body>  
     
    <script type="text/javascript">
    	$(function(){
    		$('#image1')[0].src='<%=request.getContextPath() %>/file/file_image.html?fileName=' + parent.document.getElementById('zp').value;
    	});
	  	//获取文件上传进度  
	    function startImageUp(){  
	    	if($(".fileBtnDiv input").value==''||$(".fileBtnDiv input:first").value==null){
	    		showWarning("请选择上传的图片");
				$("#window-sure").click(function() {
					divClose();
				});
				return false;
	    	}
	    	$.post('<%=request.getContextPath() %>/file/file_startImageUp.html',null, function(data){
	    		if(data.success){
	    			$("#p_in").css("width","0%");
	    			uploadimage(data.key);
	    			readPrograss(data.key);
	    		}
	    	});
	    }  
	    //上传文件  
	    function uploadimage(key){
	    	$("#uploadfile_form")[0].action="<%=request.getContextPath() %>/file/file_uploadimage.html?key="+key+"&fileName="+ parent.document.getElementById('zp').value;
	    	$("#uploadfile_form")[0].submit();
	    }  
	    function readPrograss(key){
	    	$.post('<%=request.getContextPath() %>/file/file_readProgress.html?key='+key,null, function(data){
	    		if(data.success){
	    			updateView(data.key,data.len,data.total,data.src,data.isComplete);
	    		}
	    	});
	    }
	    //更新进度  
	    function updateView(key,len, total,src,isComplete){  
	    	if(total==0){
	    		readPrograss(key);
	    		return;
	    	}
	        $("#p_in").css("width",(Math.round(len/total*100))+'%');
	        if(isComplete) {  
	        	if(len >= total) { 
		            $("#image1")[0].src="<%=request.getContextPath() %>/file/file_image.html?fileName="+src;
		            parent.document.getElementById('zp').value=src;
	        	}
	        } else{
	        	readPrograss(key);
	        } 
	    }      
   </script>  
</html>
