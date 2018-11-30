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
		$("#pass").click(function(){
			buttonDisable();
			doAudit("1");
		});
		$("#unpass").click(function(){
			buttonDisable();
			doAudit("0");
		});
		$("#retreat").click(function(){
			buttonDisable();
			doAudit("2");
		});

		$("textarea").keydown(function(){
			if(this.value.length > 49 && event.keyCode != '8') 
	            event.returnValue=false; 
		});
		$("textarea").keyup(function(){
			if(this.value.length > 49 ){
				$(this).val(this.value.substr(0,50));
			}
		});
	})
	
	function buttonDisable(){
		$("#pass").attr("disabled","true");
		$("#unpass").attr("disabled","true");
		$("#retreat").attr("disabled","true");
	}

	function doAudit(res){
		$("#auditResult").val(res);
		$.post('<%=request.getContextPath() %>/manoeuvre/manoeuvre_audit.html', $('form:last').serialize(), function(data){
			var callback = function(){
				searchForm.submit();
			};
			processDataCall(data, callback);
		}, "json");
	}
	</script>
	
  </head>
  
  <body>
  	<form>
		<div class="tab">
			<input type="hidden" id="guid" name="guid" value="${guid }"/>
			<input type="hidden" id="auditResult" name="auditStatus.result"/> 
			
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				
				<thead>
					<tr>
						<th colspan="4">
							<span>人员变更信息审核<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
					
					<tr>
						<th width="25%">
							<span class="red"></span>申请人
						</th>
						<td>
							${model.personInfo.viewHtml['xm'] }
						</td>	
						<th width="25%">
							<span class="red"></span>职工号
						</th>
						<td>
							${model.staffid }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>性别
						</th>
						<td>
							${model.personInfo.viewHtml['xbm'] }
						</td>	
						<th width="25%">
							<span class="red"></span>申请时间
						</th>
						<td>
							${model.applyTimeText }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>调出部门
						</th>
						<td>
							${model.currentOrgText }
						</td>	
						<th width="25%">
							<span class="red"></span>调入部门
						</th>
						<td>
							${model.planOrgText }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>当前岗位
						</th>
						<td>
							${model.currentPostText }
						</td>	
						<th width="25%">
							<span class="red"></span>调任岗位
						</th>
						<td>
							${model.planPostText }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>当前岗位类别
						</th>
						<td>
							${model.currentPostTypeText }
						</td>	
						<th width="25%">
							<span class="red"></span>调任岗位类别
						</th>
						<td>
							${model.planPostTypeText }
						</td>	
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red">*</span>变更类别
						</th>
						<td colspan="3">
							${model.manoeuvreTypeText }
						</td>
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red">*</span>调动类型
						</th>
						<td colspan="3">
							${model.ddlxText }
						</td>
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>变更原因
						</th>
						<td colspan="3">
							${model.reason }
						</td>		
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>备注
						</th>
						<td colspan="3">
							${model.remark }
						</td>		
					</tr>
			</table>
			
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				
				<tfoot>
					<tr>
						<td colspan="2">
							
							<div class="btn">
								<button id="pass" type="button" >通过</button>
								<button id="unpass" type="button" >否决</button>
								<button id="retreat" type="button" >退回</button>
								<button id="cancel" type="button" onclick="divClose();">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					
					<tr>
						<th width="25%">
							<span class="red"></span>审核意见
						</th>
						<td>
							<textarea rows="4" cols="50" name="auditStatus.opinion" />
						</td>	
					</tr>
				</tbody>
			</table>
		</div>
	</form>
  </body>
</html>
