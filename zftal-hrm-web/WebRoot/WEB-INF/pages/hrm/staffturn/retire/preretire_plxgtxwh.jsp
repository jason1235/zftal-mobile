<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<script type="text/javascript">
$(function(){
	$("#action").click(function(){
		var ops = $("input[name='ops']").val();
		if(ops == 'modify'){
			modifyBatch2();
		}else{
			modifyBatch();
		}
	});
});
function modifyBatch(){
	var params=$("#list_body input[name='ids']").serialize();
	params+="&"+$("#form1 :input").serialize();
	$.post('<%=request.getContextPath()%>/retire/preretire_plxgtxwh.html',params,function(data){
        var callback = function(){
            $("#search_btn").click();
        };
        if(data.success){
            processDataCall(data, callback);
        }else{
            showWarning(data.text);
        }
	},"json");
}

function modifyBatch2(){
	var params=$("#list_body input[name='ids']").serialize();
	params+="&"+$("#form1 :input").serialize();
	$.post('<%=request.getContextPath()%>/retire/preretire_plxgtxwh2.html',params,function(data){
        var callback = function(){
            $("#search_btn").click();
        };
        if(data.success){
            processDataCall(data, callback);
        }else{
            showWarning(data.text);
        }
	},"json");
}

</script>
<input name="ops" type="hidden" value="${ops}"/>
<div id="testID">    
  <div class="tab">
		<table align="center" class="formlist"> 
        <thead>
            <tr>
                <th colspan="4">
                    <span><font color="#0f5dc2" style="font-weight:normal;">批量修改退休文号</font></span>
                </th>
            </tr>
        </thead>
		<tbody id="form1">
			<tr>
				<th><span class="red">*</span>退休文号</th>
				<td>
					<input name="model.num" type="text" style="width: 65px;"/>
				</td>
			</tr>
		</tbody>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	            <div class="bz">"<span class="red">*</span>"为必填项</div>
	          	<div class="btn">
		            <button id="action" name="action" >保 存</button>
		            <button name="cancel" onclick='divClose();'>取 消</button>
	          </div>
	        </td>
	      </tr>
		</tfoot>
	</table>
  </div>
</div>
