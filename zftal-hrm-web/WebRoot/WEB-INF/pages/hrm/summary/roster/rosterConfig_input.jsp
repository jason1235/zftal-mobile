<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="ic" uri="/info-class"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	var id = $("#form1 input[name='config.guid']").val();//判断增加还是修改
	if(id==""){
		$("#action").click(function(){
			addEntity();
		});
	}else{
		var callback = function(){
			initRadio('config.queryType','${config.queryType}');
		};
		linkedColumnEvent(callback);
		$("#action").click(function(){
			updateEntity();
		});
	}
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post(_path+'/summary/rosterConfig_save.html',$("#form1 input").serialize(),function(data){
		var callback = function(){
			window.location.reload();
		};
		processDataCall(data, callback);
	},"json");
}

function updateEntity(){//修改
	if(!validate()){
		return false;
	}
	var param = $("#form1 input").serialize();
	var queryType = $("input[name='config.queryType']:checked").next().text();
	$.post(_path+'/summary/rosterConfig_update.html',param,function(data){
			var callback = function(){
				$(_tr).find("td[name='queryType']").text(queryType);
			};
			processDataCall(data, callback);
		},"json");
}

function validate(){
	var v = $("#form1 input[name='config.guid']");
	if($.trim(v.val()).length==0){
		alert("字段不能为空");
		return false;
	}
	v = $("#form1 input[name='config.queryType']");
	if($.trim(v.val()).length==0){
		alert("查询类型不能为空");
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
					<span id="title">花名册配置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<input type="hidden" name="config.classid"  value="${config.classid }"/>
			<tr>
				<th width="30%"><span class="red">*</span>字段名称</th>
				<td width="70%">
					<%--<input type="text" class="text_nor" style="width:180px" onclick="propertyPicker(this,'${config.classid}')"/>
					<input type="hidden" name="config.guid" class="text_nor" style="width:180px" value="${config.guid }"/>
				--%>
					<s:if test="config.guid == null">
					<ic:propertyPicker name="config.guid" propId="${config.guid }" classId="${config.classid }" />
					</s:if>
					<s:else>
					<ic:propertyPicker name="config.guid" propId="${config.guid }" classId="${config.classid }" editable="false"/>
					</s:else>
				</td>
			</tr>
			<tr >
				<th >查询类型</th>
				<td id="queryType">
					<span>请先选择字段</span>
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
