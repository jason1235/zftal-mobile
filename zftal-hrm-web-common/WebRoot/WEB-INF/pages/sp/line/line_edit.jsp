<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	
	<script type="text/javascript">
		$(function(){
			
			$("#save").click(function(){
				if(!check()) return false;
				$.post('<%=request.getContextPath() %>/sp/spline_saveLine.html', $('form:last').serialize(), function(data){
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					
					$("#window-sure").click(function() {
						divClose();
						
						if( data.success ) {
							window.location.reload();
						}
					});
				}, "json");

				return false;
			});
			
			$("#cancel").click(function(){
				divClose();
				return false;
			});
		});

		function check(){
			var startNode=$("select[name='spLine.unodeId']").val();
            if(startNode==null||startNode==""){
                showWarning("开始节点不能为空!");
                return false;
            }
			var endNode=$("select[name='spLine.dnodeId']").val();
            if(endNode==null||endNode==""){
                showWarning("结束节点不能为空!");
                return false;
            }
            return true;
		}
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="spLine.pid" value="${spLine.pid }"/> 
		<input type="hidden" name="spLine.lineId" value="${spLine.lineId }"/> 
		<input type="hidden" name="op" value="${op }"/> 
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
							<span class="red">*</span>起始节点
						</th>
						<td>
							<select name="spLine.unodeId">
								<c:forEach items="${nodeList }" var="node">
									<c:if test="${node.nodeType != 'END_NODE'}">
										<option value="${node.nodeId }" <c:if test="${node.nodeId eq spLine.unodeId }"> selected="selected" </c:if>>
											${node.nodeName }
										</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<th>
							<span class="red">*</span>结束节点
						</th>
						<td>
							<select name="spLine.dnodeId">
								<c:forEach items="${nodeList }" var="node">
									<c:if test="${node.nodeType != 'START_NODE'}">
										<option value="${node.nodeId }" <c:if test="${node.nodeId eq spLine.dnodeId }"> selected="selected" </c:if>>
											${node.nodeName }
										</option>
								    </c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>是否必须经过
						</th>
						<td colspan="3">
							<input type="radio" name="spLine.isMustPass" <c:if test="${spLine.isMustPass != '0'}"> checked="true" </c:if> value="1" />是
							<input type="radio" name="spLine.isMustPass" <c:if test="${spLine.isMustPass == '0'}"> checked="true" </c:if> value="0" />否
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>走向描述
						</th>
						<td colspan="3">
							<textarea rows="5" cols="50" name="spLine.lineDesc">${spLine.lineDesc}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>
