<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/commons/hrm/head.ini" %>
	</head>
	 <script type="text/javascript">
 	 $(document).ready(function(){
		//$("#right").append($('#bode').html());
	 });
	 $(function(){
		 $("#windown-close").click(function() {
			$("#windownbg").remove();
				$("#windown-box").fadeOut("slow",function(){
					$(this).remove();
				});
		 });
		var	cw = document.documentElement.clientWidth;
		var ch = document.documentElement.clientHeight;
		var est = document.documentElement.scrollTop;
		var Drag_ID = document.getElementById("windown-box");
		var DragHead = document.getElementById("windown-title");
		var _version = $.browser.version;
		var drag = "true";
		var moveX = 0,moveY = 0,moveTop,moveLeft = 0,moveable = false;
			if ( _version == 6.0 ) {
				moveTop = est;
			}else {
				moveTop = 0;
			}
		var	sw = Drag_ID.scrollWidth,sh = Drag_ID.scrollHeight;
			DragHead.onmousedown = function(e) {
			if(drag == "true"){moveable = true;}else{moveable = false;}
			e = window.event?window.event:e;
			var ol = Drag_ID.offsetLeft, ot = Drag_ID.offsetTop-moveTop;
			moveX = e.clientX-ol;
			moveY = e.clientY-ot;
			document.onmousemove = function(e) {
					if (moveable) {
					e = window.event?window.event:e;
					var x = e.clientX - moveX;
					var y = e.clientY - moveY;
						if ( x > 0 &&( x + sw < cw) && y > 0 && (y + sh < ch) ) {
							Drag_ID.style.left = x + "px";
							Drag_ID.style.top = parseInt(y+moveTop) + "px";
							Drag_ID.style.margin = "auto";
							}
						}
					}
			document.onmouseup = function () {moveable = false;};
			Drag_ID.onselectstart = function(e){return false;}
		}
	 });
 	 </script>
	<body id="bode">
	
		<div style="margin: auto; z-index: 998; top: 120px; left: 28%;" id="windown-box" class="windown-box">
			<div style="width: 350px; cursor: move;" id="windown-title" class="windown-title">
				<h2>提示信息</h2>
				<span id="windown-close" class="windown-close">关闭</span>
			</div>
			<div id="windown-content-border" class="windown-content-border">
				<div style="width: 340px; height: 120px;" id="windown-content" class="windown-id">
					<div class="open_prompt">  
						<table width="100%" border="0" class="table01">    
							<tbody>
								<tr>      
									<td width="109">
										<div class="img img_why01"></div>
									</td>      
									<th width="197"><p><font color="red">当前用户不存在部门，无法审核！</font></p></th>    
								</tr>    
								<tr>      
									<td align="center" colspan="2" class="btn01">        
										<input type="button" id="window-sure" name="确定 " class="button" onclick="divClose();" value="确 定">      
									</td>    
								</tr>  
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>	
	</body>
</html>
