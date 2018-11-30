<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
    	initRadio('open',"${open}");
    	$(":radio[name='notifySelf'][value='${notifySelf}']").attr("checked","checked");
		$("#action").click(function(){
			if(!validate()){
				return false;
			}
			var param = $("#form1 input,#form1 select").serialize();
			$.post('<%=request.getContextPath()%>/retire/retireScan_timing.html',param,function(data){
				var callback = function(){
					$("form:first").submit();
				};
				processDataCall(data,callback);
			},"json");
		});
		
	   	$("#manualExe").click(function(){
    		var param = $("#form1 input,#form1 select").serialize();
			showWindow("筛选时间","<%=request.getContextPath()%>/retire/retireScan_period.html?"+param,400,300);
		});
		
		$("#rule-div").load("<%=request.getContextPath()%>/retire/retireScan_config.html");
    });
    function validate(){
    	var obj = $("input[name='receiver']");
    	if($.trim(obj.val()).length==0){
			//showDownError(obj,"任务起始时间不能为空");
			alert("接收人不能为空");
			return false;
    	}
    	return true;
    }
    function initRadio(name,value){
		$("input[type='radio'][name='"+name+"']").each(function(){
			if($(this).val()==value){
				$(this).attr("checked","checked");
			}
		});
	}
    </script>
  </head>
 <body>
	<div id="testID" >    
	  <div class="tab">
		<table align="center" class="formlist">
			<thead>
				<tr>
					<th colspan="4">
						<span>定时设置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
					</th>
				</tr>
			</thead>
			<tbody id="form1">
				<tr>
					<th width="30%"><span class="red">*</span>通知方式</th>
					<td width="70%">
						<select name="communicateType" style="width:200px">
						<option value="1" selected>站内短信</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width="30%"><span class="red">*</span>执行周期</th>
					<td width="70%">
						<select name="period" style="width:200px">
							<c:forEach items="${retireScheduleTypeEnums }" var="item">
								<option value="${item }" ${item==period ? "selected=\"selected\"" :"" }>${item.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th width="30%"><span class="red">*</span>定时开关</th>
					<td width="70%">
						<input type="radio" name="open" value="1" checked/>开
						<input type="radio" name="open" value="0"/>关
					</td>
				</tr>
				<tr>
					<th width="30%"><span class="red">*</span>通知处理人</th>
					<td width="70%">
						<%--<input type="text" class="text_nor" id="receiver" name="receiver" size="35" value="${receiver }" />
						--%><ct:selectPerson name="receiver" id="receiver" value="${receiver }" single="false"/>
					</td>
				</tr>
				<tr>
					<th width="30%"><span class="red">*</span>是否通知退休人</th>
					<td width="70%">
						<input type="radio" name="notifySelf" value="1" checked/>是
						<input type="radio" name="notifySelf" value="0"/>否
					</td>
				</tr>
			</tbody>
	    <tfoot>
	      <tr>
	        <td colspan="4">
	            <div class="bz">"<span class="red">*</span>"为必填项</div>
	          	<div class="btn">
	          		<button id="action" name="action" >保 存</button>
	            	<button id="manualExe">手工执行预退休</button>	            	
	          	</div>
	        </td>
	      </tr>
		</tfoot>
		</table>
		</div>
	</div>

	<div id="rule-div"></div>
</body>
</html>
