<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<script type="text/javascript">
		$(function(){
			var current = null;

			// 行单击选定事件
			$("#list_body tr").click( function(){
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			initTag();
			requestSnapData();
		});
		
    </script>
</head>
<body>
	<form action="baseinfo/snapshotData_page.html" method="post" id="snap">
		<input type="hidden" name="classId" value="${classId }"/>
		<input type="hidden" name="gh" value="${gh }"/>
		<div class="comp_title" id="catalogs">
		    <ul style="width:85%">
		      <s:iterator value="infoList" var="clazz">
		      <li name="tag"><a href="#" alt="${clazz.guid }"><span>${clazz.name }</span></a></li>
		      </s:iterator>
		    </ul>
		    <div class="btn_up_down"><span class="btn_up"></span><span class="btn_down"></span></div>
	    </div>
		<div class="formbox" id="snap_content">
		</div>
	</form>
</body>
</html>
