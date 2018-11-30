<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<html>
<head>
<%@ include file="/commons/hrm/head.ini" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
<script type="text/javascript">
$(function(){
	$("#saveBtn").click(function(e){
		if(!checkTime()){
			return false;
		}
	    alertDivClose();
	    $("#manualExe").attr("disabled","disabled");
	    var param = $("#form2 input,#form2 select").serialize();
		$.post('<%=request.getContextPath()%>/retire/retireScan_manualExeByDate.html',param,function(data){
			$("#manualExe").removeAttr("disabled");
	        if(data.success){
	            processDataCall(data);
	        }else{
	            showWarning(data.text);
	        }
		},"json");
		e.preventDefault();
	});
	
	$("#cancel").click(function(){
		divClose();
		return false;
	});
});
		
function checkTime(){
	var obj = $("input[name='model.manual_time']");
 	if($.trim(obj.val()).length==0){
		alert("筛选时间不能为空");
		return false;
 	}
 	return true;
}
</script>
</head>
<body>
<div id="testID">    
  <div class="tab">
		<table align="left" class="formlist" style="font-size: 12px;">
		<thead>
			<tr>
				<th colspan="4">
					<span>筛选预退休人员时间<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form2">
			<tr>
				<th><span class="red">*</span>退休时间</th>
				<input type="hidden" id="communicateType" name="communicateType" value="${communicateType}" />
				<input type="hidden" id="open" name="open" value="${open}" />
				<input type="hidden" id="receiver" name="receiver" value="${receiver}" />
				<input type="hidden" id="notifySelf" name="notifySelf" value="${notifySelf}" />
				<input type="hidden" id="period" name="period" value="${period}" />
				<td>
				<c:if test="${period=='MONTH'}">
			    	<input name="model.manual_time" value="${model.manual_time }" type="text" style="width: 130px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate"/>
			    </c:if>
			    <c:if test="${period!='MONTH'}">
			  		<input name="model.manual_time" value="${model.manual_time }" type="text" style="width: 130px;" onfocus="WdatePicker({dateFmt:'yyyy'})" class="Wdate"/>
			    </c:if>
				</td>
			</tr>
		</tbody>
	    <tfoot>
			<tr>
			  <td colspan="4">
			    <div class="btn">
			      <button id="saveBtn">确 定</button>
			      <button id="cancel">取 消</button>
			    </div>
			  </td>
			</tr>
		</tfoot>
	</table>
  </div>
</div>
</body>
</html>