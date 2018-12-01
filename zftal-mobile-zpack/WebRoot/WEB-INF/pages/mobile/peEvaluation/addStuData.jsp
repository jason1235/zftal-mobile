<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/laydate/theme/default/laydate.css" type="text/css" />
		<%@ include file="/commons/hrm/head_v5.ini" %>
		<style>
			.ui-autocomplete {
				z-index: 12001;
				width: 500px
			}
			
			.demo-input {
				padding-left: 10px;
				height: 38px;
				min-width: 262px;
				line-height: 38px;
				border: 1px solid #e6e6e6;
				background-color: #fff;
				border-radius: 2px;
			}
		</style>

		<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/laydate/laydate.js"></script>

		<script type="text/javascript">
			$(function() {
				$("#save_btn").click(function() {
					$.post("<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_htSubmitData.html", $("#form_edit").serialize(), function(data) {
						if(data.success) {
							window.parent.$('#myModal_toji').modal('hide');
							parent.location.reload();
						} else {
							errorAlert(data.msg);
						}
					}, "json");
				});

				$("#cancel").click(function() {
					window.parent.$('#myModal_toji').modal('hide');
					return false;
				});

				$(".close").click(function() {
					window.parent.$('#myModal_toji').modal('hide');
					return false;
				});
			});
		</script>
	</head>

	<body>
		<form action="<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_htSubmitData.html" name="search" id="form_edit" method="post">
			<div class="sl_mod modal-content">
				<div class="modal-header">
					<button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title text-left" id="myModalLabel">测评信息</h4>
				</div>
				<div class="modal-body">
					<jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
					<div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
						<table width="100%" border="0" class="formlist" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<th width="30%">
										<span class="red"></span>肺活量
									</th>
									<td>
										<input id="schoolNumber" name="peDataQuery.schoolNumber" type="hidden" value="${peDataQuery.schoolNumber}" />
										<input id="gender" name="peDataQuery.gender" type="hidden" value="${peDataQuery.gender}" />
										<input id="dataType" name="peDataQuery.dataType" type="hidden" value="${peDataQuery.dataType}" />
										<input id="pulmonary" maxlength="50"  name="peDataQuery.pulmonary" value="${studentScore.pulmonary }" type="text" class="form-control" />
									</td>
								</tr>
								
								<tr>
									<th width="30%">
										<span class="red"></span>50米跑
									</th>
									<td>
										<input id="fiftyRun" maxlength="50" name="peDataQuery.fiftyRun" value="${studentScore.fiftyRun }" type="text" class="form-control" />
									</td>
								</tr>
								
								<tr>
									<th width="30%">
										<span class="red"></span>立定跳远
									</th>
									<td>
										<input id="jump" maxlength="50" name="peDataQuery.jump" value="${studentScore.jump }" type="text" class="form-control" />
									</td>
								</tr>
								
								<tr>
									<th width="30%">
										<span class="red"></span>坐位体前屈
									</th>
									<td>
										<input id="antexion" maxlength="50" name="peDataQuery.antexion" value="${studentScore.antexion }" type="text" class="form-control" />
									</td>
								</tr>
								
								<tr>
									<th width="30%">
										<span class="red"></span>800米跑
									</th>
									<td>
										<input id="eightHundredRun" maxlength="50" name="peDataQuery.eightHundredRun" value="${studentScore.eightHundredRun }" type="text" class="form-control" />
									</td>
								</tr>
								
								<tr>
									<th width="30%">
										<span class="red"></span>1000米跑
									</th>
									<td>
										<input id="oneThousandRun" maxlength="50" name="peDataQuery.oneThousandRun" value="${studentScore.oneThousandRun }" type="text" class="form-control" />
									</td>
								</tr>
								
								<tr>
									<th width="30%">
										<span class="red"></span>一分钟仰卧起坐
									</th>
									<td>
										<input id="sitUp" maxlength="50" name="peDataQuery.sitUp" value="${studentScore.sitUp }" type="text" class="form-control" />
									</td>
								</tr>
								
								<tr>
									<th width="30%">
										<span class="red"></span>引体向上
									</th>
									<td>
										<input id="pullUp" maxlength="50" name="peDataQuery.pullUp" value="${studentScore.pullUp }" type="text" class="form-control" />
									</td>
								</tr>
								

							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="save_btn">保存</button>
				</div>
			</div>
		</form>

	</body>

</html>