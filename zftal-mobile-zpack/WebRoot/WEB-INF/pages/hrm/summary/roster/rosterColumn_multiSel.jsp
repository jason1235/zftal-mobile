<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	initFoldingEvent();
	initColumnEvent();
	$("#action").click(function(){
		divClose();
		$("#preview").click();
	});
});
</script>
<div style="width:400px;margin:5px auto;">
	<table width="380" class="fieldlist">
    <thead>
      <tr>
        <th >字段列表</th>
      </tr>
     </thead>
      <tbody>
      <tr>
        <td>
            <div style="height:280px;overflow:scroll;overflow-y:scroll;overflow-x:hidden" class="tab_box">
		        <input type="hidden" id="column.rosterId" value="${rosterId }"/>
            	<div class="titlelist" id="columnList">
                  ${html }
                </div>
            </div>
		</td>
      </tr>
      </tbody>
      <tfoot>
	      <tr>
	        <td colspan="4">
	          	<div class="btn">
	            	<button id="action" name="action" >确定</button>
	          	</div>
	        </td>
	      </tr>
		</tfoot>
    </table>
</div>
