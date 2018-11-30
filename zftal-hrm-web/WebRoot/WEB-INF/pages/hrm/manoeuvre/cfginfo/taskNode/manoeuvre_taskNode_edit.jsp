<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" defer="" src="/zftal-hrm/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		
	<script type="text/javascript">
	$(function(){
		$("#save").click(function(){
			save();
		});

		$("#theNodeName").focus(function(){
			showDownPrompt(this,"在此处定义审核环节的名称");
		})
	})
	
	function buttonDisable(){
		$("#save").attr("disabled","true");
		$("#cancel").attr("disabled","true");
	}
	
	function validate(){
		
		if($("#theNodeName").val() == null || $("#theNodeName").val() == ''){
			showWarning("请填写环节名称");
			return false;
		}
		return true;
	}

	function save(){
		if(!validate()){
			return;
		}
		buttonDisable();
		$.post('<%=request.getContextPath() %>/manoeuvre/TaskNode_save.html', $('#form1').serialize(), function(data){
			var callback = function(){
				$("#searchForm").submit();
			};
			processDataCall(data, callback);
		}, "json");
	}
	</script>
	
  </head>
  
  <body>
  	<form id="form1" name="form1">
		<div class="tab">
			<input type="hidden" id="nid" name="info.nid" value="${info.nid }"/>
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>编辑审核环节信息<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save" type="button" >保 存</button>
								<button id="cancel" type="button" onclick="divClose();">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					
					<tr>
						<th>
							<span class="red">*</span>环节名称
						</th>
						<td>
							<input type="text" class="text_nor" id="theNodeName" name="info.nodeName" size="35" value="${info.nodeName }" maxLength="45" />
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>备&nbsp;&nbsp;&nbsp;&nbsp;注
						</th>
						<td>
							<input type="text" class="text_nor" name="info.remark" size="35" value="${info.remark }" maxLength="45" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
  </body>
</html>
