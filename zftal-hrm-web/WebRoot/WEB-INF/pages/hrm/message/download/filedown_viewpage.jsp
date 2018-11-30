<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
			operationList();//初始化操作栏目
			$("button[name='search']").click(function(){
				 $("#search").attr("action", "<%=request.getContextPath()%>/message/file_viewPage.html");
	             $("#search").attr("method", "post");
	             $("#search").submit();
	             e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			$("#type").val("${query.type}");
			fillRows("20", "", "", false);//填充空行
	});
    
    function operationList(){
		$("a[name='download']").click(function(){
			var id = $(this).closest("tr").find("input[id='fileId']").val();
			var url = "<%=request.getContextPath() %>/file/attachement_download.html?guId=" + id;
			window.open( url, "400", "300", true);
		});
		operationHover();
	}
	
	/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#search").submit();
		}
    </script>
  </head>
  <body>
 <form action="<%=request.getContextPath() %>/message/file_viewPage.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
 	<div class="searchtab">
				<table width="100%" border="0">
					<tbody>
						<tr>
							<th>
								文件标题
							</th>
							<td>
								<input type="text" name="query.name" class="text_nor" style="width: 180px" value="${query.name }" />
							</td>
							<th>
								文件类型
							</th>
							<td>
								<select name="query.type" id="type">
								<option value="">全部</option>
								<c:forEach items="${types }" var="item">
									<option value="${item.guid }">${item.description }</option>
								</c:forEach>
								</select>
							</td>
							<td colspan="2">
								<div class="btn">
									<button name="search" class="brn_cx">
										查 询
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
 
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>文件下载<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<c:if test="${'NAME' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="NAME" width="40%">文件标题</td>
						</c:if>
						<c:if test="${'NAME' != sortFieldName}">
						<td class="sort_title" id="NAME" width="40%">文件标题</td>
						</c:if>
						<c:if test="${'TYPE' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="TYPE" width="40%">文件类型</td>
						</c:if>
						<c:if test="${'TYPE' != sortFieldName}">
						<td class="sort_title" id="TYPE" width="40%">文件类型</td>
						</c:if>  
						<c:if test="${'CREATOR' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="CREATOR" width="15%">发布人</td>
						</c:if>
						<c:if test="${'CREATOR' != sortFieldName}">
						<td class="sort_title" id="CREATOR" width="15%">发布人</td>
						</c:if> 
						<c:if test="${'CREATETIME' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="CREATETIME" width="15%">发布时间</td>
						</c:if>
						<c:if test="${'CREATETIME' != sortFieldName}">
						<td class="sort_title" id="CREATETIME" width="15%">发布时间</td>
						</c:if>
						<td width="15%">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="file">
						<tr name="tr">
						<input type="hidden" id="guid" value="${file.guid }"/>
						<input type="hidden" id="status" value="${file.status }"/>
						<input type="hidden" id="fileId" value="${file.fileId }"/>
						<td>${file.name }</td>
						<td><ct:codeParse catalog="DM_FILE_TYPE" code="${file.type }" /></td>
						<td><ct:PersonParse code="${file.creator }"/></td>
						<td><s:date name="createTime" format="yyyy-MM-dd" /></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">下载</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="download" href="#" class="tools_list">下载</a></li>
					            </ul>
					        </div>
					      </div>
						</td>
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" />
	</div>
  	  </form>
  </body>
</html>
