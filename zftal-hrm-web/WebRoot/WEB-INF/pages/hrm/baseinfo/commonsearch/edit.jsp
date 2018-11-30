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
		$("input[name='guid']").attr("readonly","readonly").removeClass().addClass("text_nobor");
	}
	var data = '${conditionId}';
	initCheck(data);
	
	$("input#conditionView").focus(function(){
		$(this).blur();
		});
	$("input#conditionView").click(function(e){
		showSelectDiv(this);
		e.stopPropagation();
	});
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('baseinfo/commonSearch_save.html',$("#form1 input,input[name='conditionId']").serialize(),function(data){
		var callback = function(){
			window.location.href = _path+"/baseinfo/commonSearch_list.html?type=teacher";
		};
		processDataCall(data, callback);
	},"json");
}

function updateEntity(){//修改
	if(!validate()){
		return false;
	}
	var param = $("#form1 input,input[name='conditionId']").serialize();
	var title = $("input[name='title']").val();
	var view = $("input[id='conditionView']").val();
	$.post('baseinfo/commonSearch_update.html',param,function(data){
		var callback = function(){
			//var _tr = $(current);
			$(_tr).find("td[name='title']>span").text(title);
			$(_tr).find("td[name='conditions']>span").text(view);
		};
		processDataCall(data, callback);
	},"json");
}

function validate(){
	var v = $("#form1 input[name='title']");
	if($.trim(v.val()).length==0){
		alert("常用查询标题不能为空");
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
		   <input type="hidden" name="query.type" value="${type }"/>
		   <input type="hidden" name="guid" class="text_nor" style="width:180px" value="${model.guid }"/>
			<tr>
				<th width="30%"><span class="red">*</span>查询名称</th>
				<td width="70%"><input type="text" name="title" class="text_nor" style="width:180px" value="${model.title }"/></td>
			</tr>
			<tr>
				<th width="30%">条件配置</th>
				<td width="70%"><input type="text" id="conditionView" class="text_nor text_sel" style="width:180px"/></td>
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
