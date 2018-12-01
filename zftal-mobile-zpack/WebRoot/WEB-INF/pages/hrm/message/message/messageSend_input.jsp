<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	
     $("input[name='audittype']").each(function() {
         if ($(this).val() == "2") {
             $(this).attr("checked", "checked");
             $("#hidaudittype").val($(this).val());
             $("#assesorView").show();
             $("#assesorRoleView").hide();
         }
     });

	$("input[name='audittype']").click(function() {
        $("#hidaudittype").val($(this).val());
        if ($(this).val() == "2") {
            $("#assesorView").show();
            $("#assesorRoleView").hide();
        } else {
            $("#assesorView").hide();
            $("#assesorRoleView").show();
        }
    });
});
function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post(_path+'/message/messageSend_save.html',$("#form1 input,#form1 textarea,#form1 select").serialize(),function(data){
		var callback = function(){
			location.href = _path+"/message/messageSend_page.html";
		};
		processDataCall(data, callback);
	},"json");
}

function validate(){
	var v = $("#form1 input[name='msg.title']");
	if($.trim(v.val()).length==0){
		alert("标题不能为空");
		return false;
	}
	var sendType = $("input[name='audittype']:checked").val();
	
	if (sendType == "2") {
    	v = $("#form1 input[name='msg.receiver']");
    	if($.trim(v.val()).length==0){
    		alert("接收人不能为空");
    		return false;
    	}
	} else {
		v = $("#form1 select[name='msg.role']");
	    if($.trim(v.val()).length==0){
	        alert("接收角色不能为空");
	        return false;
	    }
    }
	v = $("#form1 textarea[name='msg.content']");
	if($.trim(v.val()).length==0){
		alert("消息内容不能为空");
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
				<th colspan="3">
					<span id="title">消息信息<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th width="100px"><span class="red">*</span>标题</th>
				<td colspan="2"><input type="text" name="msg.title" class="text_nor" style="width:300px" maxlength="32"/></td>
			</tr>
		   	<tr>
		   		<th><span class="red">*</span>接收人/角色</th>
		   		<td id="assesorFill">
		   		    <input type="hidden" id="hidaudittype" name="hidaudittype" value="" />
		   		    <input type="radio" name="audittype" value="1"/>角色
		   		    <input type="radio" name="audittype" value="2"/>个人
		   		</td>
		   		<td id="assesorView"><ct:selectPerson id="msg.receiver" name="msg.receiver" single="false" width="170px"/></td>
		   		<td id="assesorRoleView">
                    <select name="msg.role" style="width:170px;">
                        <c:forEach items="${roles}" var="role">
                        <option value="${role.JSDM }">
                            ${role.JSMC }
                        </option>
                        </c:forEach>
                    </select>
                </td>
		   	</tr>
		   	<tr>
		   		<th><span class="red">*</span>内容</th>
				<td colspan="2"><textarea name="msg.content" rows="3" style="width:300px;font-size:12px"></textarea></td>
		   	</tr>
		</tbody>
		<tfoot>
	      <tr>
	        <td colspan="3">
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
