<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/org/orgShow.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#list_body1 input:checkbox").click(function(){
    			checkedOperation($(this));
        	});
    		$("#checkAll").click(function(){
    			checkAll();
    		});
    		$("#save").click(function(){
    			save("${orgUrl}");
    		});
    		$("span[name='list_ico']").click(function(){
    			expandOperaton($(this));
        	})
        	$("span").css("cursor","pointer");
    	});
        initColor();
        if(isAll()){
			$("input:checkbox[id='checkAll']:not(:disabled)").attr("checked","true");
		}
    </script>
  </head>
  <body>
  	<input type="hidden" id="propertyName" name="propertyName"  value="dwm"/>
	<div class="formbox">
		<table width="100%" class="formlist" id="configs">
			<thead id="list_head1">
				<tr>
					<td width="60%">全选<input type="checkbox" id="checkAll"/>    部门名称 </td>
				    <td width="30%">部门代码</td>
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
		            	<button name="cancel" onclick='divClose();'>取 消</button>
		          	</div>
		        </td>
		      </tr>
			</tfoot>
  		</table>
	</div>
</body>
</html>
