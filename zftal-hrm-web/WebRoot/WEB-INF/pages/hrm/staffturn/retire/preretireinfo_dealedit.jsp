<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page import="com.zfsoft.hrm.staffturn.config.IStatusUpdateConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery/jquery.ui.core.js"></script>
	
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/jquery.ui.all.css" type="text/css" media="all" />
	<script type="text/javascript">
		$(function(){			
			$("#cancel").click(function(){
				divClose();
				return false;
			});
		});
		
		function validate(op){
			var error="";
			if($("#retireTime_edit").val()==""&&op=='01'){
				error=error+"退休日期不得为空";
			}
			if($("input[name='model.retireType']").val()==""&&op=='01'){
				error=error+"退休类别不得为空";
			}
			if($("input[name='model.employTime']").val()==""&&op=='12'){
				error=error+"延聘时间不得为空";
			}
			if(error!=""){
				showWarning(error);
				return false;
			}
			return true;
		}
		
		function operation(op){
			if(!validate(op)){
				return false;
			}
			var param = $('form:last').serialize();
			param +="&op="+op;
			$.post('<%=request.getContextPath() %>/retire/preretire_retireDealSave.html', param, function(data){
				var callback = function(){
					$("form:first").submit();
				};
				processDataCall(data,callback);
			},"json");
		}
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
							<span>退休处理<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button type="button" onclick="operation('<%=IStatusUpdateConfig.RETIRE_REENGAGE%>');">返 聘</button>
								<button type="button" onclick="operation('<%=IStatusUpdateConfig.RETIRE_ISEMPLOY%>');">延 聘</button>
								<button type="button" onclick="operation('<%=IStatusUpdateConfig.RETIRE_STATUS%>');">退 休</button>
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
					<!-- 20140424 add start -->
                    <tr>
                        <th>原退休日期</th>
                        <td>
							${empty model.oldRetireTime ? model.retireTimeString : model.oldRetireTimeString }
					        <input type="hidden" id="oldRetireTime" name="model.oldRetireTime" value="${empty model.oldRetireTime ? model.retireTimeString : model.oldRetireTimeString }" />
                        </td>
                        <th>
							<span class="red">*</span>退休日期
						</th>
						<td>
							<input type="text" id="retireTime_edit" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 110px;"
								name="model.retireTime" value="${empty model.retireTime ? model.oldRetireTimeString : model.retireTimeString}" />
						</td>
                       </tr>
                    <!-- 20140424 add end -->
					<tr>
						<th>
							<span class="red">*</span>退休类别
						</th>
						<td>
							<ct:codePicker name="model.retireType"
										catalog="<%=ICodeConstants.RETIRE_TYPE %>"
										code="${model.retireType }" />
						</td>
							<th>
							退休工人等级
						</th>
						<td>
							<ct:codePicker name="model.grdj"
										catalog="<%=ICodeConstants.DM_GB_A_GJZYZGDM %>"
										code="${model.grdj }" />
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
					 <input type="hidden" id="lonely2" name="model.lonely" value="false"/>
					 <input type="hidden" id="turnout2" name="model.turnout" value="false"/>
					<tr>
					
						<!--<th>
							<span class="red">*</span>退休基本费
						</th>
						<td>
							<input type="text" class="text_nor" id="retireBaseFee" name="model.retireBaseFee" style="width:150px" size="35" value="${model.retireBaseFee }"/>
						</td>
					--></tr>
					<tr>
						<!--<th>
							<span class="red">*</span>退休补贴
						</th>
						<td>
							<input type="text" class="text_nor" id="retireSubsidy" name="model.retireSubsidy" style="width:150px" size="35" value="${model.retireSubsidy }"/>
						</td>
						--><th>
							<span class="red"></span>退休证编号
						</th>
						<td>
							<input type="text" class="text_nor" id="identifier" name="model.identifier" style="width:150px" size="35" value="${model.identifier }" />
						</td>
						<th>
							<span class="red"></span>退休文号
						</th>
						<td>
							<input type="text" class="text_nor" id="num" name="model.num" style="width:150px" size="35" value="${model.num }" />
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
						<th>延聘时间</th>
						<td>
							<input type="text" id="employTime_edit" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 110px;"
								name="model.employTime" value="${model.employTimeString}" />
						</td>
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
