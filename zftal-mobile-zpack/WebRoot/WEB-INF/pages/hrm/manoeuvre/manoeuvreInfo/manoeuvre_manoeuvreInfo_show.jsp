<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" defer="" src="/zftal-hrm/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />	
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

		$(".college_title .up").toggle(function(){
			conId = "con_"+$(this)[0].id.split('_')[1];
			$("#"+conId).hide();
			$(this).attr("class","down");
			$(this).text("展开");
		return false;
		},function(){
			conId = "con_"+$(this)[0].id.split('_')[1];
			$("#"+conId).show();
			$(this).attr("class","up");
			$(this).text("收起");
		});

		$(".current_item").hover(function(){
			$(this).next("div.select_tools").show();
			$(this).parent().css("position","relative");
		},function(){
			$(this).next("div.select_tools").show().hide();
			$(this).parent().css("position","");
		})
		$(".select_tools").hover(function(){
			$(this).show();
			$(this).parent().css("position","relative");		
		},function(){
			$(this).hide();
			$(this).parent().css("position","");
		})
				
		$(".select_tools a").css("cursor","pointer");
		
		$("a[name='show1']").click(function(){
			var id = $(this).closest("tr").attr("id");
			showAuditStatusDtl(id);
		});
		$(".select_tools a").css("width","73px");
	})

	function buttonDisable(){
		$("#pass").attr("disabled","true");
		$("#unpass").attr("disabled","true");
		$("#retreat").attr("disabled","true");
	}
	
	function doAudit(res){
		$("#auditResult").val(res);
		$.post('<%=request.getContextPath() %>/manoeuvre/manoeuvre_audit.html', $('#auditForm').serialize(), function(data){
			var callback = function(){
				backForm.submit();
			};
			processDataCall(data, callback);
		}, "json");
	}

	function showAuditStatusDtl(id){
		showWindow("查看详细",_path+"/manoeuvre/AuditStatus_show.html?sid="+id+"&query.manoeuvreInfo.guid=${model.guid}&showType=1", "700", "250");
	}
	
	function goBack(){
		backForm.submit();
	}
	</script>
	
  </head>
  
  <body>
  	
  	<div class="toolbox">
		<div class="buttonbox">
			<a class="btn_fh_rs" style="cursor: pointer" onclick="goBack();return false;">返 回</a>
		</div>	
	</div>
	
  	<form action="manoeuvre/manoeuvre_list.html" name="backForm" id="backForm" method="post">
			
			<input type="hidden" id="listType" name="listType" value="${listType }"/>
			<input type="hidden" id="query.toPage" name="query.toPage" value="${query.toPage }"/>
			<c:if test="${listType != 'declare'}">
				<input type="hidden" id="query.currentOrg" name="query.currentOrg" value="${query.currentOrg }"/>
				<input type="hidden" id="query.planOrg" name="query.planOrg" value="${query.planOrg }"/>
				<%--<input type="hidden" id="query.applyTimeMin" name="query.applyTimeMin" value="${query.applyTimeMin}"/>
				<input type="hidden" id="query.applyTimeMax" name="query.applyTimeMax" value="${query.applyTimeMax}"/>
				--%>
				<input type="hidden" id="query.manoeuvreType" name="query.manoeuvreType" value='${query.manoeuvreType}'/>
				<input type="hidden" id="query.wideStaffid" name="query.wideStaffid" value='${query.wideStaffid}'/>
			</c:if>
	</form>	
	<div>
			<div>
				<h3 class="college_title" id="basicInfo">
					<span class="title_name" >
						<a href="#" style="text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;">申报信息</a>
					</span>
					<a class="up" id="up_basicInfo" href="#">收起</a>
				</h3>
			</div>
			
			<div id="con_basicInfo">
				<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
					<%--<thead>
						<tr>
							<th colspan="4">
								<span>出国进修申报信息<font color="#0f5dc2" style="font-weight:normal;"></font></span>
							</th>
						</tr>
					</thead>--%>
					
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
							<span class="red"></span>调动类型
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
					
					<tr>
						<th width="25%">
							<span class="red"></span>当前状态
						</th>
						<td>
							<c:if test="${model.finishAudit && model.executeStatus != null && model.executeStatus != ''}">
								${model.execuStatusText }
							</c:if>
							<c:if test="${model.finishAudit && (model.executeStatus == null || model.executeStatus == '')}">
								${model.auditResultText }
							</c:if>
							<c:if test="${!model.finishAudit && model.currentNode.nodeName != null && model.currentNode.nodeName != ''}">
								审核中 ： ${model.currentNode.nodeName }
							</c:if>
							<c:if test="${!model.finishAudit && (model.currentNode.nodeName == null || model.currentNode.nodeName == '')}">
								未提交
							</c:if>
							
						</td>	
						<th width="25%">
							<span class="red"></span>执行时间
						</th>
						<td>
							${model.excuteTimeText }
						</td>	
					</tr>
				</table>
			</div>
			<c:if test="${(model.currentNode.nodeName != null && model.currentNode.nodeName != '' ) || model.finishAudit || fn:length(auditStatusList) > 0}">
			<div>
				<h3 class="college_title" id="auditInfo">
					<span class="title_name" >
						<a href="#" style="text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;">审核信息</a>
					</span>
					<a class="up" id="up_auditInfo" href="#">收起</a>
				</h3>
			</div>
			
			<div id="con_auditInfo">
				<table width="100%" class="dateline" id="tab">
					<thead id="list_head1" name="list_head1">
						<tr>
							<td>序号</td>
							<td>审核时间</td>
							<td>审核环节</td>
							<td>审核结果</td>
							<td>审核人</td>
							<td width="120px">操作</td>
						</tr>
					</thead>
					<tbody id="list_body1" name="list_body1">
						<c:forEach items="${auditStatusList}" var="bean" varStatus="sta">
							<tr name="tr" id="${bean.sid }">
								<td>${sta.index + 1 }</td>
								<td>${bean.auditTimeText }</td>
								<td>${bean.taskNodeName }</td>
								<td>${bean.resultText }</td>
								<td>${bean.personInfo.viewHtml['xm'] }</td>
								<td>
									<div>
								      	<div class="current_item">
								        	<span class="item_text">查看详细</span>
								        </div>
								        <div class="select_tools" id="select_tools1" style=" width:100px; display:none">
								            <ul>
								                <li><a name="show1" class="first1">查看详细</a></li>
								            </ul>
								        </div>
								    </div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<c:if test="${listType=='audit' && (model.auditResult==null || model.auditResult=='')}">
					<form name="auditForm" id="auditForm">
					<input type="hidden" name="auditStatus.result" id="auditResult"/>
					<input type="hidden" name="guid" value="${query.guid }"/>
						<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
							<tfoot>
								<tr>
									<td colspan="2">
										
										<div class="btn">
											<button id="pass" type="button" >通过</button>
											<button id="unpass" type="button" >否决</button>
											<button id="retreat" type="button" >退回</button>
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
										<textarea rows="4" cols="50" name="auditStatus.opinion" ></textarea>
									</td>	
								</tr>
								
							</tbody>
						</table>
					</form>
				</c:if>
			</div>
			</c:if>
			
			<c:if test="${model.finishAudit}">
			<div>
				<h3 class="college_title" id="passInfo">
					<span class="title_name" >
						<a href="#" style="text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;">执行信息</a>
					</span>
					<a class="up" id="up_passInfo" href="#">收起</a>
				</h3>
			</div>
			
			<div id="con_passInfo">
				<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
					<%--<thead>
						<tr>
							<th colspan="4">
								<span>出国进修执行信息<font color="#0f5dc2" style="font-weight:normal;"></font></span>
							</th>
						</tr>
					</thead>
						
						--%><tr>
							<th width="25%">
								<span class="red"></span>申请人
							</th>
							<td>
								${model.personInfo.viewHtml['xm'] }
							</td>	
							<th width="25%">
								<span class="red"></span>执行状态
							</th>
							<td>
								${model.execuStatusText }
							</td>	
						</tr>
						
						<tr>
							<th width="25%">
								<span class="red"></span>变更执行时间
							</th>
							<td colspan="3">
								${model.excuteTimeText }
							</td>	
						</tr>
						
				</table>
			</div>
			
			</c:if>
			
		</div>
	
  </body>
</html>
