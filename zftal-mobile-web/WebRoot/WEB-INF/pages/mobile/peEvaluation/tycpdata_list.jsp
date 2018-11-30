<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>

	<head>
		<%@ include file="/commons/hrm/head_v5.ini" %>

		<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/amazeui.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/app.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>

		<style type="text/css">
			.virtal-catalog td {
				font-weight: bold;
			}
			
			.am-form-file {
				position: relative;
				overflow: hidden;
			}
			
			.am-form-group {
				margin-bottom: 1.5rem;
			}
		</style>
	</head>

	<body>
		<div class="container_ydht sl_all_bg">

		<form id="uploadForm" class="am-form am-margin"  target="resultFrame" method="post" enctype="multipart/form-data">

			<div id="file-list"></div>

			<div class="toolbox">
				<div class="btn-group" id="but_ancd">
					<button type="button" class="btn btn-default" id="btn_xsxx">学生信息</button>
					<button type="button" class="btn btn-default" id="btn_jshfs">计算后分数</button>
					<button type="button" class="btn btn-default" id="btn_sjfx">数据分析</button>

					<button type="button" class="btn btn-default" id="btn_lr">成绩录入</button>

					<button type="button" class="btn btn-default" id="btn_exportCou">导出国家EXCEL</button>
					<button type="button" class="btn btn-default" id="btn_exportPro">导出省EXCEL</button>
					
					<div style="width: 150px;;float: left;margin-left: 1px;">
						<div class="am-form-group am-form-file" style="padding-left: 1px;">
							<button type="button" class="btn btn-default" style="border-radius: 0;margin-left: -1px;">
			    		<i class="am-icon-cloud-upload"></i> 选择要上传的文件</button>
							<input id="doc-form-file" name="file" type="file">
						</div>
					</div>
					<button class="btn btn-default" type="button" value="上传成绩" id="submit" />上传成绩</button>
					<input type="hidden" id="filename" name="filename" />
					
				</div>
			</div>
		</form>
			<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>

			<form id="form_list" role="form" action="<%=request.getContextPath() %>/htPeEvaluation/htPeEvaluation_tycpdataList.html" method="post">
				<div class="form-horizontal sl_all_form ">
					<div class="row">
						<div class="col-xs-3">
							<div class="form-group">
								<label for="" class="col-xs-5 control-label">学生姓名</label>
								<div class="col-xs-7">
									<s:textfield name="peDataQuery.stuName" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
								</div>
							</div>
						</div>

						<div class="col-xs-3">
							<div class="form-group">
								<label for="" class="col-xs-5 control-label">学籍号</label>
								<div class="col-xs-7">
									<s:textfield name="peDataQuery.schoolNumber" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
								</div>
							</div>
						</div>

						<div class="col-xs-3">
							<div class="form-group">
								<label for="" class="col-xs-5 control-label">性别</label>
								<div class="col-xs-7">
									<!-- <s:textfield name="studentInfoQuery.name" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>-->
									<select name="peDataQuery.gender" class="form-control">
										<option value="">---</option>
										<option value="1">-男-</option>
										<option value="2">-女-</option>
									</select>
								</div>
							</div>
						</div>

						<div class="col-xs-3">
							<div class="form-group">
								<label for="" class="col-xs-5 control-label">班级名称</label>
								<div class="col-xs-7">
									<s:textfield name="peDataQuery.className" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
								</div>
							</div>
						</div>

						<div class="col-xs-3">
							<div class="form-group">
								<label for="" class="col-xs-5 control-label">级别</label>
								<div class="col-xs-7">
									<select id="dataType" name="peDataQuery.dataType" class="form-control">
										<option value="0" <s:if test="%{peDataQuery.dataType == 0}">selected="selected"</s:if> >-平时测评-</option>
										<option value="1" <s:if test="%{peDataQuery.dataType == 1}">selected="selected"</s:if> >-国家级-</option>
									</select>
								</div>
							</div>
						</div>

					</div>
				</div>
				<div class="row sl_aff_btn">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
					</div>
				</div>
				<input name="minIndex" id="minIndex" type="hidden" value="" />
				<input name="maxIndex" id="maxIndex" type="hidden" value="" />
				<table class="table table-striped sl_all_bg table-bordered text-left">
					<thead id="list_head">
						<tr>
							<th style="width:64px;">选择</th>
							<th>姓名</th>
							<th>班级</th>
							<th>肺活量</th>
							<th>50米跑</th>
							<th>立定跳远</th>
							<th>坐位体前屈</th>
							<th>800米跑</th>
							<th>1000米跑</th>
							<th>1分钟仰卧起坐</th>
							<th>引体向上</th>
							<th>任课教师</th>
						</tr>
					</thead>
					<tbody id="list_body">
						<s:iterator value="list" var="p" status="st">
							<tr class="pxtr">
								<td><input name="idckb" type="checkbox" id="id" value="${p.schoolNumber }" /></td>
								<td>${p.stuName}</td>
								<td>${p.className}</td>
								<td>${p.pulmonary}</td>
								<td>${p.fiftyRun}</td>
								<td>${p.jump}</td>
								<td>${p.antexion}</td>
								<td>${p.eightHundredRun}</td>
								<td>${p.oneThousandRun}</td>
								<td>${p.sitUp}</td>
								<td>${p.pullUp}</td>
								<td>${p.teacherName}</td>

							</tr>
						</s:iterator>
					</tbody>
				</table>

				<ct:page pageList="${list }" query="${peDataQuery }" queryName="peDataQuery" />

			</form>
		</div>
		<script type="text/javascript">
			var heightOffset = 200;
			$(function() {
				new Sortable(document.getElementById("list_body"), {
					group: "items",
					animation: 150
				});
				$('#minIndex').val($('.pxtr:first').find('[name=pxm]').val());
				$('#maxIndex').val($('.pxtr:last').find('[name=pxm]').val());
				var enableMsg = '${enableMsg}';
				if(enableMsg != '') {
					alert(enableMsg);
				}
			});

			$('#doc-form-file').on('change', function() {
				var fileNames = '';
				var fname = $(this).val();
				var lindex = fname.lastIndexOf("/");
				var windex = fname.lastIndexOf("\\");
				var index = lindex > windex ? lindex : windex;
				fname = fname.substring(index + 1);
				//$.each(this.files, function() {
				fileNames += '<span class="am-badge" style="color:#000000;background-color:white;">' + fname + '</span> ';
				//});
				$('#file-list').html(fileNames);
			});

			$("#submit").click(function() {
				var filename = $('.am-badge').html();
				$("#filename").val(filename);
				if(filename == null || filename == "" || filename == undefined) {
					openAlert("请上传文件再点击上传！", "e");
					return false;
				} else if(/.*\.xls(x)?/.test(filename)) {
					//return true;
				} else {
					openAlert("上传文件格式不对，您导入的不是excel文件，请导入excel文件！", "e");
					//alert("上传文件格式不对！");
					return false;
				}
//				var afile = document.getElementById("doc-form-file").files[0];
//				var filename = $("#filename").val();
				var fd = new FormData($('#uploadForm')[0])
//				fd.append("file", document.getElementById("doc-form-file").files[0]);  
//				fd.append(fd.get("filename"));
				console.log(fd.get("filename"));
				console.log(fd.get("file"));
				$.ajax({
					type:"post",
					url:"<%=request.getContextPath() %>/htPeEvaluation/htPeEvaluation_impExcel.html",
//					url: '<%=request.getContextPath() %>/txl/txl_impExcel.html',
					cache: false,
					data:new FormData($('#uploadForm')[0]),
                	processData: false,
                	contentType: false,
                	success:function(data){
                		var callback = function(){
								location.reload();
							};
						processCall(data, callback);
                	}
				});

			});

			$("#btn_xsxx").click(function() {
				var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_htStuList.html";
				window.location.href = url;
			});

			$("#btn_jshfs").click(function() {
				var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_jshfsList.html";
				window.location.href = url;
			});

			$("#btn_sjfx").click(function() {
				var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_dataAnal.html";
				window.location.href = url;
			});

			$("#btn_exportCou").click(function() {
				var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_exportCouExcel.html";
				window.location.href = url;
			});

			$("#btn_exportPro").click(function() {
				var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_exportProExcel.html";
				window.location.href = url;
			});

			$("#btn_lr").click(function() {
				var checked = $("input[id='id']:checked");
				if(checked.length == 0) {
					openAlert("请选择行", "e");
					return false;
				} else if(checked.length > 1) {
					openAlert("只能对单行进行操作", "e");
					return false;
				}

				var id = checked.val();
				var dataType = $("#dataType").val();

				var url = _path + "/htPeEvaluation/htPeEvaluation_addStuData.html?id=" + id + "&dataType=" + dataType;
				$("#myModal_toji").find(".modal-content").width(800);
				$("#myModal_toji").find(".modal-content").height(600);
				$("#myFrame").height(600);
				$("#myFrame").attr("src", url);
				$("#myModal_toji").modal("show");
			});
		</script>
	</body>

</html>