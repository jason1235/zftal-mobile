<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    	$(function(){
        	$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/auth/authDetail_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
				});
			fillRows("20", "", "", false);//填充空行数据
		});
    	function initSelect(name,value){
			$("select[name='"+name+"'] > option").each(function(){
				if($(this).val()==value){
					$(this).attr("selected","selected");
				}
			});
		}
		
		/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#page").submit();
		}
    </script>
  </head>
  <body>
<div class="searchtab">
	<form id="search">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th>部门</th>
          <td>
          	<ct:codePicker name="query.deptId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.deptId }" />
		  </td>
          <th>编制类别</th>
          <td>
          	<ct:codePicker name="query.authType" catalog="<%=ICodeConstants.AUTH_TYPE %>" code="${query.authType }" />
		  </td>
          <th>岗位等级</th>
          <td>
          	<ct:codePicker name="query.level" catalog="<%=ICodeConstants.DM_DEF_POST_LEVEL %>" code="${query.level }" />
          </td>
        </tr>
        <tr>
          <th>岗位类别</th>
          <td>
          	<ct:codePicker name="query.postType" catalog="<%=ICodeConstants.DM_DEF_WORKPOST %>" code="${query.postType }" />
          </td>
<td colspan="4">
            <div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div></td>
        </tr>
      </tbody>
    </table>
    </form>
  </div>
<div class="formbox">
<form id="page" name="page" method="post" action="<%=request.getContextPath()%>/auth/authDetail_list.html">
<input type="hidden" name="query.deptId" class="text_nor" style="width:180px" value="${query.deptId }"/>
<input type="hidden" name="query.authType" class="text_nor" style="width:180px" value="${query.authType }"/>
<input type="hidden" name="query.level" class="text_nor" style="width:180px" value="${query.level }"/>
<input type="hidden" name="query.postType" class="text_nor" style="width:180px" value="${query.postType }"/>
<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
<input type="hidden" id="asc" name="asc" value="${asc}"/>
  <table width="100%" class="dateline" id="tiptab">
    <thead id="list_head">
      <tr>
        <td>序号</td>
        <c:if test="${'zgh' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="zgh">职工号</td>
		</c:if>
		<c:if test="${'zgh' != sortFieldName}">
			<td class="sort_title" id="zgh">职工号</td>
		</c:if>
		<c:if test="${'xm' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="xm">姓名</td>
		</c:if>
		<c:if test="${'xm' != sortFieldName}">
			<td class="sort_title" id="xm">姓名</td>
		</c:if>
		<c:if test="${'dwm' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="dwm">部门</td>
		</c:if>
		<c:if test="${'dwm' != sortFieldName}">
			<td class="sort_title" id="dwm">部门</td>
		</c:if>
		<c:if test="${'bzlb' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="bzlb">编制类别</td>
		</c:if>
		<c:if test="${'bzlb' != sortFieldName}">
			<td class="sort_title" id="bzlb">编制类别</td>
		</c:if>
		<c:if test="${'gwlb' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="gwlb">岗位类别</td>
		</c:if>
		<c:if test="${'gwlb' != sortFieldName}">
			<td class="sort_title" id="gwlb">岗位类别</td>
		</c:if>
		<c:if test="${'gwdj' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="gwdj">岗位等级</td>
		</c:if>
		<c:if test="${'gwdj' != sortFieldName}">
			<td class="sort_title" id="gwdj">岗位等级</td>
		</c:if>
		<c:if test="${'gwid' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="gwid">岗位名称</td>
		</c:if>
		<c:if test="${'gwid' != sortFieldName}">
			<td class="sort_title" id="gwid">岗位名称</td>
		</c:if>
		<c:if test="${'gwzt' eq sortFieldName}">
			<td class="sort_title_current_${asc }" id="gwzt">岗位状态</td>
		</c:if>
		<c:if test="${'gwzt' != sortFieldName}">
			<td class="sort_title" id="gwzt">岗位状态</td>
		</c:if>
      </tr>
    </thead>
    <tbody id="list_body">
      <c:forEach items="${pageList}" var="detail" varStatus="st">
		<tr name="tr">
		<td>${st.count }</td>
		<td>${detail.accountId }</td>
		<td>${detail.name }</td>
		<td>${detail.deptValue }</td>
		<td>${detail.authTypeValue }</td>
		<td>${detail.postTypeValue }</td>
		<td>${detail.levelValue }</td>
		<td>${detail.postValue }</td>
		<td>
			<ct:codeParse catalog="<%=ICodeConstants.STATUS %>" code="${detail.postStatus }" />
		</td>
		</tr>
	  </c:forEach>
    </tbody>
  </table>
  <ct:page pageList="${pageList }" />
</form>
</div>

  </body>
</html>
