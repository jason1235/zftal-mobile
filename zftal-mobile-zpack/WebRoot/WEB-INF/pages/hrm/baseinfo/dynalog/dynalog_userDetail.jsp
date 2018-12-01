<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/commons/hrm/head.ini" %>
<script type="text/javascript">
$(function(){
	$("#back").click(function(){
		window.location.href = _path + "/baseinfo/dynalog_userPage.html?classId=${classId}&gh=${gh}";
	});
	$("span[name='changed']").each(function(){
		tooltips(this);
	});
});
</script>
</head>
<body>
<div id="testID" >
  <div class="toolbox">
		<!-- 按钮 -->
			<div class="buttonbox">
				<a id="back" class="btn_fh_rs" style="cursor: pointer">返 回</a>
			</div>
	  <p class="toolbox_fot">
			<em></em>
		</p>
	</div>
  <div id="preview">
  ${html }
  </div>
</div>
</body>
</html>
