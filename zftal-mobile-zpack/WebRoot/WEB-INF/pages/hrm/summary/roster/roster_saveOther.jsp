<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	$("#action").click(function(){
		addEntity();
	});
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	var param = $("#form1 input,#guid,#query-con1 input,#conditionArea1 input,#dataArea input").serialize();
	$.post(_path+'/summary/rosterData_saveOther.html',param,function(data){
			var callback = function(){
				//do something
			};
			processDataCall(data, callback);
		},"json");
}

function validate(){
	var v = $("#form1 input[name='name']");
	if($.trim(v.val()).length==0){
		alert("名称不能为空");
		return false;
	}
	return true;
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
					<span id="title">花名册<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th width="30%"><span class="red">*</span>名称</th>
				<td width="70%">
					<input type="text" name="name" class="text_nor" style="width:180px" value="" maxlength="12"/>
				</td>
			</tr>
			<tr>
				<th width="30%"><span class="red">*</span>类型</th>
				<td width="70%">
					<input type="radio" name="rosterType" value="PERSONAL" checked="true" /> 个人 
					<input type="radio" name="rosterType" value="PUBLIC" /> 公共
				</td>
			</tr>
			<tr>
				<th >注释</th>
				<td >
					<input type="text" name="description" class="text_nor" style="width:180px" value="" maxlength="16"/>
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
</body>
</html>
