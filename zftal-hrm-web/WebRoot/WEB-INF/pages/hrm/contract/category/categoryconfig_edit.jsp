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
		initRadio('categoryConfig.sfqy',"${categoryConfig.sfqy}");
		$(":radio[name='categoryConfig.sfzdtx'][value='${categoryConfig.sfzdtx}']").attr("checked","checked");
		var id = $("#form1 input[name='categoryConfig.htzldm']").val();//判断增加还是修改

		if(id==""){
			$("#action").click(function(){
				var htzldm=$("#htzldm").val();
				$.post('<%=request.getContextPath() %>/contract/categoryConfig_check.html?categoryConfig.htzldm='+htzldm,null, function(data){
					if(data.success){
						saveRequest();						
					}else{
						alert("合同种类代码重复，请重新输入!");
					}
				});
				return false;
			});
		}else{
			$("#action").click(function(){
				updateRequest();
			});
		}
	});
	
	function saveRequest(){
		if(!validate()){
			return false;
		}
		var param = $("#form1").serialize();
		$.post(_path+'/contract/categoryConfig_save.html',param,function(data){
			var callback = function(){
				$("#search").submit();
			};
			processDataCall(data,callback);
		},"json");
	}
		
	function updateRequest(){
		if(!validate()){
			return false;
		}
		var param = $("#form1").serialize();
		$.post(_path+'/contract/categoryConfig_update.html',param,function(data){
			var callback = function(){
				$("#search").submit();
			};
			processDataCall(data,callback);
		},"json");
	}
	
	function check(input,oldNum){
		var v = $(input).val();
		var p = new RegExp("^([1-9][0-9]*|0)$");
		var res = p.test(v);
		if(!res){
			alert("提醒天数请输入非负整数！");
			$(input).val(oldNum);
			$(input).focus();
			return false;
		}
	}	
	
	function validate(){
		if($.trim($("#htzldm").val()).length == 0){
			alert("合同种类代码不能为空");
			$("#htzldm").focus();
			return false;
		}
		
		if($.trim($("#htzlmc").val()).length == 0){
			alert("合同种类名称不能为空");
			$("#htzlmc").focus();
			return false;
		}
				
		if($.trim($("#glry").val()).length == 0){
			alert("管理人员不能为空");
			$("#glry").focus();
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
						<th colspan="4">
							<span id="title">合同种类设置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tbody>
				   <tr>
						<th width="25%"><span class="red">*</span>合同种类代码</th>
						<td width="30%">
							<input id="htzldm" type="text" name="categoryConfig.htzldm" value="${categoryConfig.htzldm }"/>
						</td>
						<th width="25%"><span class="red">*</span>合同种类名称</th>
						<td width="30%">
							<input id="htzlmc" type="text" name="categoryConfig.htzlmc" value="${categoryConfig.htzlmc }"/>
						</td>
					</tr>
					<tr>
						<th width="25%"><span class="red">*</span>到期提醒天数</th>
						<td width="30%">
							<input id="dqtxts" type="text" name="categoryConfig.dqtxts" value="${categoryConfig.dqtxts }" onchange="check(this,'${categoryConfig.dqtxts }');"/>
						</td>
						<th width="25%"><span class="red">*</span>续签提醒天数</th>
						<td width="30%">
							<input id="xqtxts" type="text" name="categoryConfig.xqtxts" value="${categoryConfig.xqtxts }" onchange="check(this,'${categoryConfig.dqtxts }');"/>
						</td>
					</tr>
					<tr>
						<th width="25%"><span class="red">*</span>试用期提醒天数</th>
						<td width="30%">
							<input id="syqtxts" type="text" name="categoryConfig.syqtxts" value="${categoryConfig.syqtxts }" onchange="check(this,'${categoryConfig.dqtxts }');"/>
						</td>
						<th width="25%"><span class="red">*</span>管理人员</th>
						<td width="30%">
							<ct:selectPerson name="categoryConfig.glry" id="glry" value="${categoryConfig.glry }" single="false" />
						</td>
					</tr>
					<tr>
						<th width="25%"><span class="red">*</span>是否启用</th>
						<td width="30%">
							<input type="radio" name="categoryConfig.sfqy" class="text_nor" value="1" checked/>是
							<input type="radio" name="categoryConfig.sfqy" class="text_nor" value="0"/>否
						</td>
						<th width="25%"><span class="red">*</span>是否自动提醒</th>
						<td width="30%">
							<input type="radio" name="categoryConfig.sfzdtx" class="text_nor" value="1" checked/>是
							<input type="radio" name="categoryConfig.sfzdtx" class="text_nor" value="0"/>否
						</td>
					</tr>
					<tr>
						<th width="20%">备注</th>
						<td colspan="3">
							<textarea rows="3" cols="59" name="categoryConfig.bz">${categoryConfig.bz }</textarea>
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