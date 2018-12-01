<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%@include file="/commons/hrm/head.ini" %>
  <script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/message.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/globalweb/privilege/deptFilter.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#list_body1 input:checkbox[id!='self'][id!='all']").click(function(){
				$("input[name='deptFilter.dataType']").val("in");
    			checkedOperation($(this));
        	});
    		$("#self").click(function(){
    			$("input[name='deptFilter.dataType']").val("self");
    			selfCleckdOperation($(this));
        	});
    		$("#all").click(function(){
    			$("input[name='deptFilter.dataType']").val("all");
    			allCleckdOperation($(this));
        	});
    		//$("#checkAll").click(function(){
    		//	$("input[name='deptFilter.dataType']").val("in");
    		//	checkAll();
    		//});
    		$("#save").click(function(){
        		if("${viewType}" == "user"){
        			save("${orgUrl}","${deptFilter.userId}","${deptFilter.roleId}");
            	}
        		else{
        			saveWithRoleId("${orgUrl}","${deptFilter.userId}","${deptFilter.roleId}");
            	}
    		});
    		$("#clear").click(function(){
    			clear();
    		});
    		$("span[name='list_ico']").click(function(){
    			expandOperaton($(this));
        	})
        	$("span[name='choose_ico']").click(function(){
        		chooseExpandOperaton($(this));
            })
        	$("span").css("cursor","pointer");

    		initColor();
    		initE();
    	});

        
    </script>
  </head>
  <body>
  	<input type="hidden" id="propertyName" name="propertyName"  value="dwm"/>
	<div class="formbox">
		<form id="form1">
			<input type="hidden" name="deptFilter.dataType" value="${deptFilter.dataType}" />
		</form>
		<table width="100%" class="formlist" id="configs">
			<thead id="list_head1">
				<tr>
					<td width="100%">部门名称 </td>
				</tr>
			</thead>
			<tbody id="list_body1" style="text-align:left;">
				${orghtml }
			</tbody>
			<tfoot>
		      <tr>
		        <td colspan="2">
		          	<div class="btn">
		            	<button id="save" name="save" >保 存</button>
		            	<button id="clear" name="clear">清空</button>
		            	<button name="cancel" onclick='iFClose();return false;'>取 消</button>
		          	</div>
		        </td>
		      </tr>
			</tfoot>
  		</table>
	</div>
</body>
</html>
