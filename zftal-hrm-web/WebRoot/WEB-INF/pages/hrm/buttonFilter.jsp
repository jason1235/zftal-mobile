<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="button_permission" style="display:none;">
<c:forEach items="${ancdModelList}" var="ancd" >
	<div>${ancd.czdm }</div>
</c:forEach>
</div>
<script type="text/javascript">
$(function(){
	$("[id*='btn_']").each(function(){
		$(this).hide();
		});
	$("#button_permission > div").each(function(){
		var id = "btn_"+$(this).text();
		$("[id='"+id+"']").show();
	});
});
</script>
