<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
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
		$("#action").click(function(){
			saveRequest();
		});
	});
	
	function saveRequest(){
		if(!validate()){
			return false;
		}
		var param = $("#form1").serialize();
		$.post(_path+'/expertmanage/declare_save.html',param,function(data){
			var callback = function(){
				$("#search").submit();
			};
			processDataCall(data,callback);
		},"json");
	}
		
	function validate(){
		if($.trim($("#tjrgh").val()).length == 0){
			alert("专家姓名不能为空!");
			return false;
		}
		
		var zydm = $("#form1 input[name='expertDeclare.type']").val();
		if(zydm == null || zydm == ''){
			alert("专家类型不能为空!");
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
	<form id="form1" name="form1" method="post">
		<div id="testID">    
		  <div class="tab">
			<table align="center" class="formlist">
				<thead>
					<tr>
						<th colspan="2">
							<span id="title">被推荐人信息<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th width="25%"><span class="red">*</span>专家姓名</th>
						<td>
							<ct:selectPerson name="expertDeclare.tjrgh" id="tjrgh" value="${expertDeclare.tjrgh }" single="false"/>
						</td>
					</tr>
					<tr>
						<th width="25%"><span class="red">*</span>专家类别</th>
						<td id="zydm">
							<ct:codePicker name="expertDeclare.type" catalog="DM_DEF_ZJLX" code="${expertDeclare.type }" width="200"/>
						</td>
					</tr>
					
				</tbody>
		    <tfoot>
		      <tr>
		        <td colspan="4">
		            <div class="bz">"<span class="red">*</span>"为必填项</div>
		          	<div class="btn">
		            	<button type="button" id="action" name="action" >保 存</button>
		            	<button type="button" name="cancel" onclick='divClose();'>取 消</button>
		          	</div>
		        </td>
		      </tr>
			</tfoot>
			</table>
		  </div>
		</div>
	</form>
</body>
</html>