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
							<td>
								职工号
							</td>
							<td>
								姓名
							</td>
							<td>
								部门
							</td>
							<td>
								岗位名称
							</td>
							<td>
								岗位类别
							</td>
							<td>
								岗位等级
							</td>
							<td>
								在岗状态
							</td>
							<td>
								编制类型
							</td>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${personPostInfoList}" var="bean">
							<tr>
								<td>
									${bean.userId}
								</td>
								<td>
									${bean.overall.viewHtml['xm']}
								</td>
								<td>
									${bean.overall.viewHtml['dwm']}
								</td>
								<td>
									${bean.postInfo.name}
								</td>
								<td>
									${bean.postInfo.typeName}
								</td>
								<td>
									${bean.levelValue}
								</td>
								<td>
									${bean.overall.viewHtml['dqztm']}
								</td>
								<td>
									${bean.overall.viewHtml['bzlbm']}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
</html>
