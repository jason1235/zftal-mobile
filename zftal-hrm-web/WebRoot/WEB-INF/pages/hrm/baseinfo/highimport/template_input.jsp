<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@ include file="/commons/hrm/head.ini" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />

<script>
	$(function(){
		$("#btn_bc").click(function(){
			saveRequest();
		});
	});

	function saveRequest(){
		if(!validate()){
			return false;
		}
		
		var param = $("#form1").serialize();
		$.post(_path+'/baseinfo/highImport_save.html',param,function(data){
			var callback = function(){
				$("#search").submit();
			};
			processDataCall(data,callback);
		},"json");
	}
	
	function validate(){
		if($.trim($("#mbmc").val()).length == 0){
			alert("模版名称不能为空！");
			$("#mbmc").focus();
			return false;
		}
		return true;
	}
</script>
 
<body>
	<form id="form1" name="form1" method="post">
	<input type="hidden" name="oper" value="${oper }"/>
	<input type="hidden" name="template.id" value="${template.id}"/>
	 
		<div id="testID">
		  <div class="tab">
			<table align="center" class="formlist" width="1240" height="270">
				<thead>
					<tr>
						<th colspan="4">
							<span id="title">新增导入模版</span>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
				   		<th width="15%"><span style="color:red">*</span>模版名称</th>
						<td colspan="3">
			                	<input id="mbmc" name="template.mbmc" value="${template.mbmc }" type="text"  style="width: 180px;"/>			                	
						</td>					
					</tr>
					
					<tr>
						<th width="25%">备注
						</th>
						<td colspan="3">
							<textarea rows="4" cols="50" name="template.bz">${template.bz }</textarea>
						</td>
					</tr>
				</tbody>
		    <tfoot>
		      <tr>
		        <td colspan="4">
		          	<div class="btn">
		            	<button type="button" id="btn_bc" name="action" >保 存</button>
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