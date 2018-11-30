<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/baseinfo/condition/batch.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#propertyName").change(function(){
    			loadBatchConfig();
    		});
    		$("#checkAll").click(function(){
    			checkAll();
    		});
    		$("#action").click(function(){
    			commitBatchConfig();
    		});
    		fillRows("10", "list_head1", "list_body1", false);//填充空行
    	});
    </script>
    <style type="text/css">
    .formlist td{
    	padding: 3px 12px 3px 6px;
    }
    </style>
  </head>
  <body>
  <div class="searchbox">
	  <p class="search_con">
        <label>字段：</label>
		<select name="propertyName" id="propertyName">
		<option value=''>请选择</option>
		<c:forEach items="${codeList }" var="p">
		<option value='${p.fieldName }'>${p.name }</option>
		</c:forEach>
		</select>
		<font color="#0457A7" style="font-weight:normal;">（提示：选择要进行条件定义的字段，系统会自动加载可定义参数）</font>
	  </p>
	</div>
<div class="formbox">
	<table width="100%" class="formlist" id="configs">
		<thead id="list_head1">
			<tr>
				<td width="40%">全选<input type="checkbox" id="checkAll"/>    参数名称 <input type="hidden" name="rootId" value="${rootId }"/></td>
			    <td width="50%">参数代码</td>
			</tr>
		</thead>
		<tbody id="list_body1" style="text-align:left;">
		</tbody>
		<tfoot>
	      <tr>
	        <td colspan="2">
	            <div class="bz"><span class="red">注:</span>父节点若不选中，则子节点条件位置上升一层</div>
	          	<div class="btn">
	            	<button id="action" name="action" >保 存</button>
	            	<button name="cancel" onclick='divClose();'>取 消</button>
	          	</div>
	        </td>
	      </tr>
		</tfoot>
  	</table>
</div>
</body>
</html>
