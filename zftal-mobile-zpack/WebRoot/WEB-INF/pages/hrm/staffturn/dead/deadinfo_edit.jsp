<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery/jquery.ui.core.js"></script>
	
	<script type="text/javascript">
		$(function(){			
			$("#save").click(function(){
				if( $("#userId1").val() == "" ) {
					return alert("职工号不得为空，请重新输入！");
				}

				if( $("#deadTime1").val() == "" ) {
					return alert("离世日期不得为空，请重新输入！");
				}

				if(!isPrice($("#deadSubsidy1").val())){
					alert("离世抚恤金，格式是最多保留两位小数的数值！");
					return false;
				}
				
				if( $("#deadSubsidy1").val() == "" ) {
					return alert("离世抚恤金不得为空，请重新输入！");
				}

				if( $("#receiver1").val() == "" ) {
					return alert("签收人不得为空，请重新输入！");
				}

				if( $("#receiveDate1").val() == "" ) {
					return alert("签收日期不得为空，请重新输入！");
				}

				$.post('<%=request.getContextPath() %>/dead/deadinfo_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					processDataCall(data,callback);
				},"json");

				return false;
			});
			
			$("#cancel").click(function(){
				divClose();
				return false;
			});
		});
		
		function isPrice(s){
			s=trim(s);
			var p=/^[1-9](\d+(\.\d{1,2})?)?$/;
			var p1=/^[0-9](\.\d{1,2})?$/;
			return p.test(s)||p1.test(s);
		}
	</script>
</head>

<body>
	<form id="edit">
		<input type="hidden" name="query.opType" value="${query.opType }"/> 
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>人员选择<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save" type="button">保 存</button>
								<button id="cancel" type="button">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th>
							<span class="red"></span>职工号
						</th>
						<td>
							${model.userId}
							<input type="hidden" id="userId1" name="model.userId" value="${model.userId }"/> 
						</td>
						<th>
							<span class="red"></span>姓名
						</th>
						<td>
							${model.overall.viewHtml['xm']}
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>离世日期
						</th>
						<td>
							<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 110px;" size="20" id="deadTime1" name="model.deadTime" value="${model.deadTimeString }"/>
						</td>
						<th>
							<span class="red">*</span>离世抚恤金
						</th>
						<td>
							<input type="text" class="text_nor" size="20" id="deadSubsidy1" name="model.deadSubsidy" value="${model.deadSubsidy }"/>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>签收人
						</th>
						<td>
							<input type="text" class="text_nor" size="20" id="receiver1" name="model.receiver" value="${model.receiver }"/>
						</td>
						<th>
							<span class="red">*</span>签收日期
						</th>
						<td>
							<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 110px;" size="20" id="receiveDate1" name="model.receiveDate" value="${model.receiveDateString }" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
