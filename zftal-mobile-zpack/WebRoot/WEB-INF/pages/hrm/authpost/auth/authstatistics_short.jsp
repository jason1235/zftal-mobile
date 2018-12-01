<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript">
			$(function(){
				$("a[name='detail']").click(function(){//行数据修改链接
					var globalid=$(this).closest("li").attr("name");
					//location.href="<%=request.getContextPath()%>/normal/staffResume_list.html?globalid="+globalid+"&type=teacher";
					goUrl(_path+"/normal/staffResume_list.html?globalid="+globalid+"&type=teacher");
				});
				
				fillRows("20", "", "", false);//填充空行
			});
			
		</script>
	</head>

	<body>
	<div class="formbox">
			<div class="con_overlfow">
				<table summary="" class="dateline tablenowrap" align="" width="100%">
					<thead id="list_head">
						<tr>
							<td>岗位名称</td>
							<td>岗位类别</td>
							<td>岗位级别</td>
							<td>计划编制数</td>
							<td>现有编制数</td>
							<td>缺编数</td>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${deptPostList}" var="bean">
							<tr>
								<td>
									${bean.deptPostName}
								</td>
								<td>
									${bean.postTypeName}
								</td>
								<td>
									${bean.levelName}
								</td>
								
								<td>
									${bean.planAuthNum}
								</td>
								<td>
									${bean.currentAuthNum}
								</td>
								<td>
									${bean.shortNum}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
</html>
