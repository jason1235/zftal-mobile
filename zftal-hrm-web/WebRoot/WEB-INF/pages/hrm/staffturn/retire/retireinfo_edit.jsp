<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
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
	
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery.ui.all.css" type="text/css" media="all" />
	<script type="text/javascript">
		$(function(){
			
			$("#save").click(function(){
				var error="";
				if($("#retireTime_edit").val()==""){
					error=error+"退休日期不得为空；\n";
				}
				if($("input[name='model.retireType']").val()==""){
					error=error+"退休类别不得为空；\n";
				}
				/*
				if($("input[name='model.retirePost']").val()==""){
					error=error+"退休行政级别不得为空；\n";
				}
				
				if($("input[name='model.retirePostLevel']").val()==""){
					error=error+"退休专业级别不得为空；\n";
				}
				*/
				/*function isPrice(s){
					s=trim(s);
					var p=/^[1-9](\d+(\.\d{1,2})?)?$/;
					var p1=/^[0-9](\.\d{1,2})?$/;
					return p.test(s)||p1.test(s);
				}
				
				if($("#retireBaseFee").val() != ''){
					if(!isPrice($("#retireBaseFee").val())){
						error=error+"退休基本费，格式是最多保留两位小数的数值；\n";
					}
				}
				
				if($("#retireSubsidy").val() != ''){
					if(!isPrice($("#retireSubsidy").val())){
						error=error+"退休补贴，格式是最多保留两位小数的数值。";
					}
				}
*/
/*				if(error!=""){
					showWarning(error);
					return false;
				}*/
				
				$.post('<%=request.getContextPath() %>/retire/retire_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					processDataCall(data,callback);
				},"json");

				return false;
			});
			
			$("#cancel").click(function(){
				divClose();
			});
		});
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="query.opType" value="${query.opType }"/> 
		<input type="hidden" name="model.state" value="${model.state }"/> 
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>退休编辑<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">保 存</button>
								<button id="cancel">取 消</button>
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
							<input type="hidden" name="model.userId" value="${model.userId }"/> 
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
							<span class="red"></span>出生年月
						</th>
						<td>
							${model.overall.viewHtml['csrq']}
						</td>
						<th>
							<span class="red"></span>参加工作时间
						</th>
						<td>
							${model.overall.viewHtml['rzrq'] }
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>政治面貌
						</th>
						<td>
							${model.overall.viewHtml['zzmmm'] }
						</td>
						<th>
							<span class="red"></span>目前职务级别
						</th>
						<td>
							${model.overall.viewHtml['zj'] }
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>退休日期
						</th>
						<td>
							<input type="text" id="retireTime_edit" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 110px;"  name="model.retireTime" value="${model.retireTimeString }" />
						</td>
						<th>
							<span class="red">*</span>退休类别
						</th>
						<td>
							<ct:codePicker name="model.retireType"
										catalog="<%=ICodeConstants.RETIRE_TYPE %>"
										code="${model.retireType }" />
						</td>
					</tr>
					<tr>
						<th>
							退休行政级别
						</th>
						<td>
							<ct:codePicker name="model.retirePost"
										catalog="<%=ICodeConstants.ADMIN_DUTY_LEVEL %>"
										code="${model.retirePost }" />
						</td>
						<th>
							退休专业级别
						</th>
						<td>
							<ct:codePicker name="model.retirePostLevel"
										catalog="<%=ICodeConstants.POST_LEVEL %>"
										code="${model.retirePostLevel }" />
						</td>
					</tr>
					<%--
					<tr>
						<th>
							<span class="red">*</span>孤老
						</th>
						<td>
							<input type="radio" id="lonely1" name="model.lonely" <c:if test="${model.lonely == true }"> checked="checked" </c:if> value="true"/>是
							<input type="radio" id="lonely2" name="model.lonely" <c:if test="${model.lonely == false }"> checked="checked" </c:if> value="false"/>否
						</td>
						<th>
							<span class="red">*</span>返聘
						</th>
						<td>
							<input type="radio" id="reEngage1" name="model.reEngage" <c:if test="${model.reEngage == true }"> checked="checked" </c:if> value="true"/>是
							<input type="radio" id="reEngage1" name="model.reEngage" <c:if test="${model.reEngage == false }"> checked="checked" </c:if> value="false"/>否
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>转出
						</th>
						<td>
							<input type="radio" id="turnout1" name="model.turnout" <c:if test="${model.turnout == true }"> checked="checked" </c:if> value="true"/>是
							<input type="radio" id="turnout2" name="model.turnout" <c:if test="${model.turnout == false }"> checked="checked" </c:if> value="false"/>否
						</td>
						
					</tr>
					 --%>
					 <input type="hidden" id="lonely2" name="model.lonely" value="false"/><!--
					 <input type="hidden" id="reEngage1" name="model.reEngage" value="false"/>
					 --><input type="hidden" id="turnout2" name="model.turnout" value="false"/>
					<tr>
						<th>
							退休工人等级
						</th>
						<td>
							<ct:codePicker name="model.grdj"
										catalog="<%=ICodeConstants.DM_GB_A_GJZYZGDM %>"
										code="${model.grdj }" />
						</td>
						<th>
							<span class="red"></span>退休证编号
						</th>
						<td>
							<input type="text" class="text_nor" id="identifier" name="model.identifier" style="width:150px" size="35" value="${model.identifier }" />
						</td>
					</tr><!--
					
					<tr>
						<th>
							<span class="red"></span>退休基本费
						</th>
						<td>
							<input type="text" class="text_nor" id="retireBaseFee" name="model.retireBaseFee" style="width:150px" size="35" value="${model.retireBaseFee }"/>
						</td>
						<th>
							<span class="red"></span>退休补贴
						</th>
						<td>
							<input type="text" class="text_nor" id="retireSubsidy" name="model.retireSubsidy" style="width:150px" size="35" value="${model.retireSubsidy }"/>
						</td>
					</tr>
					--><tr>
						<th>
							<span class="red"></span>退休文号
						</th>
						<td>
							<input type="text" class="text_nor" id="num" name="model.num" style="width:150px" size="35" value="${model.num }" />
						</td>
						<th>
							<span class="red"></span>是否返聘
						</th>
						<td colspan="3">
							<select name="model.doResult">
								<option value="01" ${model.doResult!='04'?"selected=\"selected\"":"" }>否</option>
								<option value="04" ${model.doResult=='04'?"selected=\"selected\"":"" }>是</option>
							</select>
						</td>
						</tr>
					<tr>
						<th>离退后享受级别</th>
						<td>
							<ct:codePicker name="model.lthxsjbm"
										catalog="DM_GB_A_LTXGBXXSDYLBDM"
										code="${model.lthxsjbm }" />
						</td>
						<th>
							<span class="red"></span>离退休费支付单位
						</th>
						<td colspan="3">
							<input type="text" class="text_nor" id="ltxfzfdw" name="model.ltxfzfdw" style="width:150px" size="35" value="${model.ltxfzfdw }" />
						</td>
					</tr>
					<tr>
						<th>离退后管理单位</th>
						<td>
							<input type="text" class="text_nor" id="lthgldw" name="model.lthgldw" style="width:150px" size="35" value="${model.lthgldw }" />
						</td>
						<th>
							<span class="red"></span>异地安置地点
						</th>
						<td colspan="3">
							<input type="text" class="text_nor" id="ydazdd" name="model.ydazdd" style="width:150px" size="35" value="${model.ydazdd }" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>备注
						</th>
						<td colspan="3">
							<input type="text" class="text_nor" id="remark" name="model.remark" style="width:150px" size="35" value="${model.remark }" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
