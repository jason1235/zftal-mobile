<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="code" uri="/WEB-INF/code.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>

	<script type="text/javascript">
		$(function(){ 
			$("#save").click(function(){
				if($("#name").val()==null||$("#name").val()==""){
					showWarning("报表名不能为空，请重新输入！");
					return false;
				}
				if($("#sql").val()==null||$("#sql").val()==""){
					showWarning("报表查询条件不能为空，请重新输入！");
					return false;
				}
				if($("#menuId").val()==null||$("#menuId").val()==""){
                    showWarning("所属类别不能为空，请重新输入！");
                    return false;
                }
				$.post("<%=request.getContextPath() %>/summary/seniorreportconfig_save_report.html",$("form:last").serialize(),function(data){
				var callback = function(){
					$("form:first").submit();
				};
				processDataCall(data,callback);
		},"json");
				return false;
			});
			$("cancel").click(function(){
				divClose();
				return false;
			});

			$("#menuId").val("${config.menuId}");
		});
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="config.reportId" value="${config.reportId }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>报表维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
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
						<th><span class="red">*</span>报表名称</th>
						<td>
							<input type="text" class="text_nor" id="name" name="config.reportName" size="25" value="${config.reportName }" />
						</td>
					</tr>
					<tr>
                        <th><span class="red">*</span>所属类别</th>
                        <td>
                            <select id="menuId" name="config.menuId">
                                <c:forEach items="${types}" var="t">
                                    <option value="${t.key }">${t.text }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
					<tr>
						<th><span class="red">*</span>查询语句</th>
						<td>
							<textarea style="width:180px;height:60px" id="sql" name="config.sql" size="25" >${config.sql }</textarea> 
						</td>
					</tr>
					<tr>
						<th><span class="red">*</span>历史查询语句</th>
						<td>
							<textarea style="width:180px;height:60px" id="sql" name="config.historySql" size="25" >${config.historySql }</textarea> 
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>