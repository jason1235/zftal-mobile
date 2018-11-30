<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>图片剪切</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/demos.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery.Jcrop.css">
		<script type="text/javascript" language="javascript" src="<%=basePath%>js/jquery.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=basePath%>js/jquery.Jcrop.js"></script>
		<script type="text/javascript">
		var firstFlag = false;
	jQuery(document).ready(
		function() {
			jQuery('#imgCrop').Jcrop( {
				aspectRatio:1,
				allowResize:false,
				allowSelect:false,
				onChange : showCoords,			// 选框改变时的事件
				onSelect : showCoords			// 选框选定时的事件
			}, function(){this.animateTo([0,0,120,120])});
			jQuery('#cropButton').click(function() {
				var x = jQuery("#x").val();
				var y = jQuery("#y").val();
				var w = jQuery("#w").val();
				var h = jQuery("#h").val();
				if (w == 0 || h == 0) {
					alert("您还没有选择图片的剪切区域,不能进行剪切图片!");
					return;
				}
				document.form1.submit();
			});
		});
		function showCoords(c) {
			jQuery('#x').val(c.x);
			jQuery('#y').val(c.y);
			jQuery('#x2').val(c.x2);
			jQuery('#y2').val(c.y2);
			jQuery('#w').val(c.w);
			jQuery('#h').val(c.h);
			if(!firstFlag){
				exchange();
			}
			showPreview(c);
		}
		
		function exchange(){
			firstFlag = true;
			jQuery('#preview').attr("src", jQuery('#imgCrop').attr("src"));
		}
		function showPreview(coords) {
			if (parseInt(coords.w) > 0) {
				var rx = 120 / coords.w;
				var ry = 120 / coords.h;
				jQuery('#preview').css({
					marginLeft : '-' + Math.round(rx * coords.x) + 'px',
					marginTop : '-' + Math.round(ry * coords.y) + 'px'
				});
			}
		}
</script>
	</head>
	<body>
		<form name="form1" method=post action="<%=path%>/imgCrop">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td id="imgTd" style="width:${param.width}px;height:${param.height}px;"
						align="center" style="padding-top:5px;">
						<img src="<%=path%>${param.oldImgPath}" id="imgCrop" name="imgCrop" border="0" />
					</td>
					<td width="5">&nbsp;</td>
					<td style="vertical-align:top;">
						<div style="width:120px; height:120px; overflow: hidden;ma">
							<c:choose>
								<c:when test="${param.tag eq 0}">
									<img src="<%=path%>${param.oldImgPath}" id="preview" />
								</c:when>
							</c:choose>
						</div>
					</td>
				</tr>
			</table>
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td id="cropTd">
						<input type="button" value="上传头像" id="cropButton" />
					</td>
					<td>
						<a href="index.jsp">返回上传图片</a>
					</td>
				</tr>
			</table>
			<input type="hidden" id="x" name="x" />
			<input type="hidden" id="y" name="y" />
			<input type="hidden" id="x2" name="x2" />
			<input type="hidden" id="y2" name="y2" />
			<input type="hidden" id="w" name="w" />
			<input type="hidden" id="h" name="h" />
			<!-- 源图片宽度和高度 -->
			<input type="hidden" id="width" name="width" value="${param.width}" />
			<input type="hidden" id="height" name="height" value="${param.height}" />
			<input type="hidden" id="oldImgPath" name="oldImgPath" value="${param.oldImgPath}" />
			<input type="hidden" id="imgRoot" name="imgRoot" value="${param.imgRoot}" />
			<input type="hidden" id="imgFileExt" name="imgFileExt" value="${param.imgFileExt}" />
		</form>
	</body>
</html>