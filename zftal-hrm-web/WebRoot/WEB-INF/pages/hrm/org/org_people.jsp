<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
	    $(function(){
	
			$("#save").click(function(){
				save();
			});

			$("#checkAll").click(function(){
				checkAll();
			})
		})
		
		function validate(){
			var num = $("input:checkbox[name='staffIds'][checked='true']").size();
			var planOrg = $("input[name='orgPeople.planOrg']").val();
			
			if(num == null || num == 0){
				return alert('请选择人员');
			}
			if(planOrg == null || planOrg == ''){
				return alert('请选择目标部门');
			}
			return true;
		}
	
		function save(){
			if(!validate()){
				return;
			}
			$.post('<%=request.getContextPath()%>/org/orgPeople_peopleMove.html', $('#form1').serialize(), function(data){
				var callback = function(){
					
				};
				processDataCall(data, callback);
			}, "json");
		}

		function checkAll(){
			if($("#checkAll").is(":checked")){
        		$("input:checkbox[name='staffIds']").attr("checked","true");
        	}else{
        		$("input:checkbox[name='staffIds']").removeAttr("checked");
        	}
		}

    </script>
  </head>
	<body>
		<form id="form1" method="post" action="<%=request.getContextPath()%>/org/org_peopleMove2.html">
			<div class="formbox" >
				<div class="tab">
					<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th colspan="2">
									<span>选择部门<font color="#0f5dc2" style="font-weight:normal;"></font></span>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th width="25%">
									<span class="red">*</span>目标部门
								</th>
								<td>
									<ct:codePicker name="orgPeople.planOrg" catalog="<%=ICodeConstants.DM_DEF_ORG%>" code="" width="200" />
								</td>	
							</tr>
						</tbody>
					</table>
				</div>
				<div class="tab">
					<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
						<thead id="list_head1">
							<tr>
								<td align="center" width="16%">
									<input type="checkbox" id="checkAll" /> 全部
								</td>
								<td align="center" width="42%">职工号</td>
								<td align="center" width="42%">姓名</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${peopleList}" var="bean" varStatus="sta">
								<tr name="tr">
									<td align="center">
										<input type="checkbox" name="staffIds" value="${bean.values['gh'] }" />
									</td>
									<td>${bean.values['gh']}</td>
									<td>${bean.viewHtml['xm']}</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="3">
									<div class="bz">"<span class="red">*</span>"为必填项</div>
									<div class="btn">
										<button id="save" type="button" >保 存</button>
										<button id="cancel" type="button" onclick="divClose();">取 消</button>
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
