<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/commons/hrm/head_v5.ini" %>
  <title>图片裁剪</title>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8" />

<script src="../js/jquery.min.js"></script>
<script src="../js/jquery.Jcrop.js"></script>
<script type="text/javascript">
  jQuery(function($){
	  var proportion = $("#proportion").val();
	  if(proportion == "2"){
		  $("#preview-pane").find(".preview-container").css("height","130px");
		  $("#preview-pane").find(".preview-container").css("width","260px");
	  }else{
		  $("#preview-pane").find(".preview-container").css("height","200px");
		  $("#preview-pane").find(".preview-container").css("width","200px");
	  }

    // Create variables (in this scope) to hold the API and image size
    var jcrop_api,
        boundx,
        boundy,

        // Grab some information about the preview pane
        $preview = $('#preview-pane'),
        $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),

        xsize = $pcnt.width(),
        ysize = $pcnt.height();
    
    //console.log('init',[xsize,ysize]);
    $('#target').Jcrop({
      boxWidth: 500,
      createBorders:['1','1','1','1'],
      allowResize: true,
      keySupport: false,
      aspectRatio:  proportion,
      onChange: updatePreview,
      onSelect: updatePreview,
      onRelease:  clearCoords
      //aspectRatio: xsize / ysize
    },function(){
      // Use the API to get the real image size
      var bounds = this.getBounds();
      boundx = bounds[0];
      boundy = bounds[1];
      // Store the API in the jcrop_api variable
      jcrop_api = this;

      // Move the preview into the jcrop container for css positioning
      $preview.appendTo(jcrop_api.ui.holder);
    });

    $('#coords').on('change','input',function(e){
      var x = $('#x').val(),
              x2 = $('#x2').val(),
              y = $('#y').val(),
              y2 = $('#y2').val();
      jcrop_api.setSelect([x,y,x2,y2]);
    });

    function updatePreview(c)
    {
      $('#x').val(c.x);
      $('#y').val(c.y);
      $('#x2').val(c.x2);
      $('#y2').val(c.y2);
      $('#w').val(c.w);
      $('#h').val(c.h);
      if (parseInt(c.w) > 0)
      {
        var rx = xsize / c.w;
        var ry = ysize / c.h;

        $pimg.css({
          width: Math.round(rx * boundx) + 'px',
          height: Math.round(ry * boundy) + 'px',
          marginLeft: '-' + Math.round(rx * c.x) + 'px',
          marginTop: '-' + Math.round(ry * c.y) + 'px'
        });
      }
    };

    function clearCoords()
    {
      $('#coords input').val('');
    };
    
    jQuery('#cancel').click(function() {
    	window.parent.cutcancel(); 
    	//window.parent.$('#myModal_toji').modal('hide');
    });
    jQuery('.close').click(function() {
    	window.parent.cutcancel(); 
    	//window.parent.$('#myModal_toji').modal('hide');
    });
    jQuery('#cropButton').click(function() {
		var x = jQuery("#x").val();
		var y = jQuery("#y").val();
		var w = jQuery("#w").val();
		var h = jQuery("#h").val();
		var oldImgPath = jQuery("#oldImgPath").val();
		var imgRoot = jQuery("#imgRoot").val();
		var imgFileExt = jQuery("#imgFileExt").val();
		var width = jQuery("#width").val();
		var height = jQuery("#height").val();
		var imageName = jQuery("#imageName").val();
		var guid = jQuery("#guid").val();
		if (w == 0 || h == 0) {
			$("#error").html("<p><b>错误：</b>您还没有选择图片的剪切区域,不能进行剪切图片!</p>");
			return;
		}
		$.post("<%=request.getContextPath() %>/file/file_imgCut.html",
				"x="+x+"&y="+y+"&w="+w+"&h="+h+"&oldImgPath="+oldImgPath+"&imgRoot="+imgRoot+"&imgFileExt="+imgFileExt+
				"&width="+width+"&height="+height+"&imageName="+imageName+"&guid="+guid,
				function(data){
					//window.parent.$('#myModal_toji').modal('hide');
					window.parent.cutsuccess();
        },"json");
		//document.form1.submit();
	});

  });


</script>
<link rel="stylesheet" href="../css/demos.css" type="text/css" />
<link rel="stylesheet" href="../css/jquery.Jcrop.css" type="text/css" />
<style type="text/css">

/* Apply these styles only when #preview-pane has
   been placed within the Jcrop widget */
.jcrop-holder{
	margin-left: 15px;
	background-color: white;
}

.jcrop-holder #preview-pane {
  display: block;
  position: absolute;
  z-index: 2000;
  top: 10px;
  right: -300px;
  padding: 6px;
  background-color: white;
  

  -webkit-border-radius: 6px;
  -moz-border-radius: 6px;
  border-radius: 6px;

  -webkit-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
  box-shadow: 1px 1px 5px 2px rgba(0, 0, 0, 0.2);
}

.jcrop-tracker{
	    border: 3px rgba(0,0,0,.4) solid;
	    padding: 5px 5px 5px 5px;
}

/* The Javascript code will set the aspect ratio of the crop
   area based on the size of the thumbnail preview,
   specified here */
#preview-pane .preview-container {
  border: 1px rgba(0,0,0,.4) solid;
  background-color: white;
  overflow: hidden;
}

</style>

</head>
<body style="background-color: #fff;">

<div>
            <div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">图片裁剪</h4>
            </div>
            
  <div id="error" class="description" style="color: red;margin-left: 15px;background-color: #fff;">
                        </div>
<div class="description" style="margin-left: 15px;background-color: #fff;">
  <p>
    <b>提示：</b>
     请裁剪图片
  </p>
  </div>

      

	  <img src="<%=path%>${param.oldImgPath}" id="target" alt="" />
  <div id="preview-pane" >
    <div class="preview-container">
      <img src="<%=path%>${param.oldImgPath}" class="jcrop-preview" alt="Preview" />
    </div>
  </div>

  <form id="coords"
        class="coords"
        onsubmit="return false;"
        action="">

    <div class="inline-labels" style="display: none;">
      <label>X <input type="text" size="4" id="x" name="x" /></label>
      <label>Y <input type="text" size="4" id="y" name="y" /></label>
      <label>X2 <input type="text" size="4" id="x2" name="x2" /></label>
      <label>Y2 <input type="text" size="4" id="y2" name="y2" /></label>
      <label>W <input type="text" size="4" id="w" name="w" /></label>
      <label>H <input type="text" size="4" id="h" name="h" /></label>
      <input type="hidden" id="width" name="width" value="${param.width}" />
	  <input type="hidden" id="height" name="height" value="${param.height}" />
	  <input type="hidden" id="oldImgPath" name="oldImgPath" value="${param.oldImgPath}" />
	  <input type="hidden" id="imgRoot" name="imgRoot" value="${param.imgRoot}" />
	  <input type="hidden" id="imgFileExt" name="imgFileExt" value="${param.imgFileExt}" />
	  <input type="hidden" id="imageName" name="imageName" value="${param.imageName}" />
	  <input type="hidden" id="guid" name="guid" value="${param.guid}" />
	  <input type="hidden" id="proportion" name="proportion" value="${param.proportion}" />
    </div>
  </form>


<div class="modal-footer" style="background-color: #fff;">
                <button type="button" class="btn btn-primary" id="cropButton" >保 存</button>
                <button type="button" class="btn btn-primary" id="cancel" >取 消</button>
            </div>

</div>
</body>
</html>

