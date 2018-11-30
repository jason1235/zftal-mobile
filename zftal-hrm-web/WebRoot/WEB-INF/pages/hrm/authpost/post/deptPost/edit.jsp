<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	var id = $("#form1 input[name='post.guid']").val();//判断增加还是修改
	if(id==""){
		$("#action").click(function(){
			addEntity();
		});

	}else{
		$("#action").click(function(){
			updateEntity();
		});
		$("#form1 input[name='post.guid']").attr("readonly","readonly").removeClass("text_nor").addClass("text_nobor");
	}

});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('post/deptPost_save.html',$("#form1 input,#form1 textarea").serialize(),function(data){
			var callback = function(){
				$("button[name='search']").click();
			};
			processDataCall(data, callback);
		},"json");
}

function updateEntity(){//修改
	if(!validate()){
		return false;
	}
	var param = $("#form1 input,#form1 textarea").serialize();
	$.post('post/deptPost_update.html',param,function(data){
			var callback = function(){
				$("button[name='search']").click();
			};
			processDataCall(data, callback);
			
		},"json");
}

function validate(){
	var v;
	v = $("#form1 input[name='post.guid']");
	if($.trim(v.val()).length==0){
		alert("岗位编号不能为空");
		return false;
	}
	v = $("#form1 input[name='post.planNumber']");
	if($.trim(v.val()).length==0){
		alert("计划编制数不能为空");
		return false;
	}
	
	var regexp=/^[1-9][0-9]*$/;
	if(!regexp.test(v.val())){
		alert("计划编制数需为正整数！");
		return false;
	}
	
	v = $("#form1 input[name='post.deptId']");
	if($.trim(v.val()).length==0){
		alert("所属部门不能为空");
		return false;
	}
	v = $("#form1 input[name='post.postId']");
	if($.trim(v.val()).length==0){
		alert("岗位名称不能为空");
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
					<span id="title">部门岗位维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
		    <tr>
				<th width="15%" ><span class="red">*</span>岗位编号</th>
				<td width="35%" ><input type="text" name="post.guid" class="text_nor" style="width:180px" value="${post.guid }" maxlength="10"/></td>
				<th width="15%"><span class="red">*</span>计划编制数</th>
				<td width="35%"><input type="text" name="post.planNumber" class="text_nor" style="width:180px" value="${post.planNumber }" maxlength="8"/></td>
			</tr>
			<tr>
				<th width="15%"><span class="red">*</span>所属部门</th>
				<td width="35%">
					<c:if test="${post.guid == null }">
					<ct:codePicker name="post.deptId" catalog="${code['deptId']}" code="${post.deptId }" />
					</c:if>
					<c:if test="${post.guid != null }">
					<ct:codePicker name="post.deptId" catalog="${code['deptId']}" code="${post.deptId }" editable="false"/>
					</c:if>
				</td>
				<th width="15%"><span class="red">*</span>岗位名称</th>
				<td width="35%">
					<c:if test="${post.guid == null }">
					<ct:codePicker name="post.postId" catalog="${code['postId']}" code="${post.postId }" />
					</c:if>
					<c:if test="${post.guid != null }">
					<ct:codePicker name="post.postId" catalog="${code['postId']}" code="${post.postId }" editable="false"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th width="15%"><span class="red"></span>上级岗位</th>
				<td width="35%">
					<c:if test="${post.guid == null }">
					<ct:codePicker name="post.superiorId" catalog="${code['superiorId']}" code="${post.superiorId }" />
					</c:if>
					<c:if test="${post.guid != null }">
					<ct:codePicker name="post.superiorId" catalog="${code['superiorId']}" code="${post.superiorId }" editable="false"/>
					</c:if>
				</td>
				<th width="15%">岗位等级</th>
				<td width="35%">
					<ct:codePicker name="post.level" catalog="${code['level']}" code="${post.level }" />
				</td>
			</tr>
			<tr>
				<th width="15%">
					<span>岗位职责</span>
				</th>
				<td width="85%" colspan="3">
					<textarea name="post.duty" rows="4" style="width:530px;">${post.duty }</textarea>
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
