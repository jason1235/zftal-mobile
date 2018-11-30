<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	var id = $("#form1 input[name='guid']").val();//判断增加还是修改
	if(id==""){
		$("#action").click(function(){
			addEntity();
			});
	}else{
		$("#action").click(function(){
			updateEntity();
			});
	}
	$("input[name='title']").focus(function(){showDownPrompt(this,"输入条件系列标题")});
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('baseinfo/conditionDefined_save.html',$("#form1 input").serialize(),function(data){
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
	var title = $("input[name='title']").val();
	var text = $("input[name='text']").val();
	$.post('baseinfo/conditionDefined_update.html',param,function(data){
		var callback = function(){
			$(_tr).find("td[name='title']").text(title);
			$(_tr).find("span[name='text']").text(text);
		};
		processDataCall(data, callback);
	},"json");
}

function validate(){
	var v = $("#form1 input[name='title']");
	if(v.val().length==0){
		showDownError(v,"条件系列标题不能为空");
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
				<th colspan="2">
					<span>条件系列维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<input type="hidden" name="parentId" value="root"/>
			<input type="hidden" name="guid" value="${model.guid }"/>
			<input type="hidden" name="index" value="${model.index }"/>
			<input type="hidden" name="query.type" value="${model.type }"/>
			<tr>
				<th width="30%"><span class="red">*</span>系列名称</th>
				<td width="70%"><input type="text" name="title" class="text_nor" style="width:180px" value="${model.title }"/></td>
			</tr>
			<tr>
				<th width="30%">描述信息</th>
				<td width="70%"><input type="text" name="text" class="text_nor" style="width:180px" value="${model.text }"/></td>
			</tr>
			<tr>
				<th width="30%">过滤条件</th>
				<td width="70%"><input type="text" name="express" class="text_nor" style="width:180px" value="${model.express }"/></td>
			</tr>
		</tbody>
    <tfoot>
      <tr>
        <td colspan="2">
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
