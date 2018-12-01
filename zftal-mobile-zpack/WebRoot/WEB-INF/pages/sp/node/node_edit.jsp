<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script type="text/javascript">
		$(function(){
			
			$("#save").click(function(){
				var nodeName=$("input[name='spNode.nodeName']").val();
				if(nodeName==null||nodeName==""){
					showWarning("节点名称不能为空!");
					return false;
				}
				$.post('<%=request.getContextPath() %>/sp/spnode_saveNode.html', $('form:last').serialize(), function(data){
					var callback = function(){
			            window.location.reload();
			        };
			        if(data.success){
			            processDataCall(data, callback);
			        }else{
			            showWarning(data.text);
			        }
				}, "json");

				return false;
			});
			$("input[name='taskIds']").click(function(e){
				//var checked=$(this).attr("checked");
				if(this.checked){
					var html=
							"<h2>必需<select name=\"taskIsMusts\"><option value=\"Y\">是</option><option value=\"N\">否</option></select>"
								+"自动<select name=\"taskIsAutos\"><option value=\"Y\">是</option><option value=\"N\">否</option></select></h2>";
					$(this).closest("div").append(html);
				}else{
					$(this).next().remove();
				}
			});
			$("input[name='billClasses']").click(function(e){
				//var checked=$(this).attr("checked");
				if(this.checked){
					var html=
							"<h2>编辑<select name=\"classIsEdit\"><option value=\"N\">否</option><option value=\"Y\">是</option></select></h2>";
					$(this).closest("div").append(html);
				}else{
					$(this).next().remove();
				}
			});
			
			$("input[name='commitBillClassIds']").click(function(e){
				//var checked=$(this).attr("checked");
				if(this.checked){
					var html=
							"<h2>权限<select name=\"commitClassPrivilege\">"
							+ "<option value=\"SEARCH\" selected=\"selected\">查询</option>"
							+ "<option value=\"SEARCH_EDIT\">查询-编辑</option>"
							+ "<option value=\"SEARCH_ADD_DELETE\">查询-增加-删除</option>"
							+ "<option value=\"SEARCH_ADD_DELETE_EDIT\">查询-增加-删除-编辑</option>"
							+ "</select></h2>";
					$(this).closest("div").append(html);
				}else{
					$(this).next().remove();
				}
			});
			
			$("input[name='approveBillClassIds']").click(function(e){
				//var checked=$(this).attr("checked");
				if(this.checked){
					var html=
							"<h2>权限<select name=\"approveClassPrivilege\">"
							+ "<option value=\"SEARCH\">查询</option>"
							+ "<option value=\"SEARCH_EDIT\">查询-编辑</option>"
							+ "<option value=\"SEARCH_ADD_DELETE\">查询-增加-删除</option>"
							+ "<option value=\"SEARCH_ADD_DELETE_EDIT\" selected=\"selected\">查询-增加-删除-编辑</option>"
							+ "</select></h2>";
					$(this).closest("div").append(html);
				}else{
					$(this).next().remove();
				}
			});
			
			$("#cancel").click(function(){
				divClose();
				return false;
			});
		});
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="op" value="${op }"/>
		<input type="hidden" name="spNode.nodeId" value="${spNode.nodeId }"/> 
		<input type="hidden" name="spNode.pid" value="${spNode.pid }"/> 
		<input type="hidden" name="spNode.nodeStatus" value="${spNode.nodeStatus }"/> 
		<input type="hidden" name="spNode.inType" value="${spNode.inType }"/> 
		<input type="hidden" name="spNode.outType" value="${spNode.outType }"/> 
		<input type="hidden" name="spNode.btype" value="${spNode.btype }"/> 
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>流程设置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
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
							<span class="red">*</span>节点名称
						</th>
						<td>
							<input type="text" name="spNode.nodeName" value="${spNode.nodeName }"/>
						</td>
						<th>
							<span class="red">*</span>节点类型
						</th>
						<td>
							<select name="spNode.nodeType">
								<c:forEach items="${nodeTypes}" var="nodetype">
									<option value="${nodetype.key }" <c:if test="${nodetype.key eq spNode.nodeType}"> selected="selected"</c:if>>${nodetype.text }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>选择角色
						</th>
						<td colspan="3">
							<c:if test="${op eq 'add' }">
								<select name="spNode.roleId">
									<c:forEach items="${roleList}" var="role" varStatus="i">
										<option value="${role.jsdm }" <c:if test="${i.index eq 0 }"> selected="selected""</c:if>>${role.jsmc }</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${op eq 'modify' }">
								<select name="spNode.roleId">
									<c:forEach items="${roleList}" var="role" varStatus="i">
										<option value="${role.jsdm }" <c:if test="${role.jsdm eq spNode.roleId }"> selected="selected""</c:if>>${role.jsmc }</option>
									</c:forEach>
								</select>
							</c:if>
							
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>申报表单内容
						</th>
						<td>
							<div style="overflow-y:auto;height: 130px;width: 100%">
								<c:forEach items="${commitNodeClassMap}" var="commitNodeClass">	
									<!--  <h2><c:out value="${commitNodeClass.key}"/><h2/> -->
									<c:forEach items="${commitNodeClass.value}" var="nodeBill">	
										<div>							
											<input type="checkbox" name="commitBillClassIds" value="${nodeBill.classId }" <c:if test="${nodeBill.checked eq true }"> checked="checked"</c:if>/>${nodeBill.className }
											<c:if test="${nodeBill.checked eq true  }">
												<h2>
													权限
													<select name="commitClassPrivilege">
														<option value="SEARCH" <c:if test="${nodeBill.classesPrivilege eq 'SEARCH' }"> selected="selected"</c:if>>查询</option>
														<option value="SEARCH_EDIT" <c:if test="${nodeBill.classesPrivilege eq 'SEARCH_EDIT' }"> selected="selected"</c:if>>查询-编辑</option>
														<option value="SEARCH_ADD_DELETE" <c:if test="${nodeBill.classesPrivilege eq 'SEARCH_ADD_DELETE' }"> selected="selected"</c:if>>查询-增加-删除</option>
														<option value="SEARCH_ADD_DELETE_EDIT" <c:if test="${nodeBill.classesPrivilege eq 'SEARCH_ADD_DELETE_EDIT' }"> selected="selected"</c:if>>查询-增加-删除-编辑</option>
													</select>
												</h2>
											</c:if>
										</div>
									</c:forEach>
								</c:forEach>
							</div>
							<!--  
							<c:forEach items="${commitNodeClassMap}" var="commitNodeClass">	
								<h2><c:out value="${commitNodeClass.key}"/><h2/>
								<br/>
								<c:forEach items="${commitNodeClass.value}" var="nodeBill">								
									<input type="checkbox" name="commitBillClassIds" value="${nodeBill.classId }" <c:if test="${nodeBill.checked eq true }"> checked="checked"</c:if>/>${nodeBill.className }
								</c:forEach>
							</c:forEach>
							-->
						</td>

						<th>
							<span class="red"></span>审核表单内容
						</th>
						<td >
							<div style="overflow-y:auto;height: 130px;width:100%">
								<c:forEach items="${approveNodeClassMap}" var="approveNodeClass">
								<!--  <h2><c:out value="${commitNodeClass.key}"/><h2/> -->
								
									<c:forEach items="${approveNodeClass.value}" var="nodeBill">	
										<div>							
										<input type="checkbox" name="approveBillClassIds" value="${nodeBill.classId }" <c:if test="${nodeBill.checked eq true }"> checked="checked"</c:if>/>${nodeBill.className }
										<c:if test="${nodeBill.checked eq true  }">
											<h2>
												权限
												<select name="approveClassPrivilege">
													<option value="SEARCH" <c:if test="${nodeBill.classesPrivilege eq 'SEARCH' }"> selected="selected"</c:if>>查询</option>
													<option value="SEARCH_EDIT" <c:if test="${nodeBill.classesPrivilege eq 'SEARCH_EDIT' }"> selected="selected"</c:if>>查询-编辑</option>
													<option value="SEARCH_ADD_DELETE" <c:if test="${nodeBill.classesPrivilege eq 'SEARCH_ADD_DELETE' }"> selected="selected"</c:if>>查询-增加-删除</option>
													<option value="SEARCH_ADD_DELETE_EDIT" <c:if test="${nodeBill.classesPrivilege eq 'SEARCH_ADD_DELETE_EDIT' }"> selected="selected"</c:if>>查询-增加-删除-编辑</option>
												</select>
											</h2>
										</c:if>
										</div>
									</c:forEach>
								</c:forEach>
							</div>
							<!-- 
							<c:forEach items="${approveNodeClassMap}" var="approveNodeClass">
								<h2><c:out value="${approveNodeClass.key}"/><h2/>
								<br/>	
								<c:forEach items="${approveNodeClass.value}" var="nodeBill">								
									<input type="checkbox" name="approveBillClassIds" value="${nodeBill.classId }" <c:if test="${nodeBill.checked eq true }"> checked="checked"</c:if>/>${nodeBill.className }
								</c:forEach>
							</c:forEach>
							-->
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>节点任务
						</th>
						<td>
							<div style="overflow-y:auto;height: 160px;width: 100%">
							<c:forEach items="${taskList}" var="nodeTask">
								<div>
									<input type="checkbox" name="taskIds" value="${nodeTask.spTask.taskId }" <c:if test="${nodeTask.checked eq true }"> checked="checked"</c:if>/>${nodeTask.spTask.taskName }
									<c:if test="${nodeTask.checked eq true  }">
										<h2>
											必需
											<select name="taskIsMusts">
												<option value="Y" <c:if test="${nodeTask.need eq 'Y' }"> selected="selected"</c:if>>是</option>
												<option value="N" <c:if test="${nodeTask.need eq 'N' }"> selected="selected"</c:if>>否</option>
											</select>
											自动
											<select name="taskIsAutos">
											<option value="Y" <c:if test="${nodeTask.auto eq 'Y' }"> selected="selected"</c:if>>是</option>
												<option value="N" <c:if test="${nodeTask.auto eq 'N' }"> selected="selected"</c:if>>否</option>
											</select>
										</h2>
									</c:if>
								</div>
								<br/>
							</c:forEach>
							</div>
						</td>
						<th>
                            <span class="red"></span>表单任务
                        </th>
                        <td>
                            <div style="overflow-y:auto;height: 160px;width: 100%">
                            <c:forEach items="${pushTasks}" var="pushTask">
                                <div>
                                    <input type="checkbox" name="pushTaskIds" value="${pushTask.spTask.taskId }" <c:if test="${pushTask.checked eq true }"> checked="checked"</c:if>/>${pushTask.spTask.taskName }
                                </div>
                                <br/>
                            </c:forEach>
                            </div>
                        </td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>节点描述
						</th>
						<td colspan="3">
							<textarea rows="5" cols="50" name="spNode.nodeDesc">${spNode.nodeDesc }</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
