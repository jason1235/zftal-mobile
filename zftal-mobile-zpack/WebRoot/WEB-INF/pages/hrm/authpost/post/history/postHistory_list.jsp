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
        	$("#back").click(function(){
				location.href = _path + "/post/postHistoryLog_list.html";
            });
        	$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/post/postHistory_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
				});
        	$("#search input[name='query.deptId']").prev().click(function(){
				codePicker(this,"<%=ICodeConstants.DM_DEF_ORG%>");
			});
			fillRows("20", "", "", false);//填充空行数据
		});
		
    </script>
  </head>
  <body>
<div class="toolbox">
<!-- 按钮 -->
		<div class="buttonbox">
			<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
		</div>
  <p class="toolbox_fot">
		<em></em>
	</p>
</div>
<form id="search" method="post" action="post/postHistory_list.html">
<div class="searchtab">
    <table width="100%" border="0">
      <tfoot>
        <tr>
          <td colspan="6">
            <div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div></td>
        </tr>
      </tfoot>
      <tbody>
        <tr>
          <input type="hidden" name="snapTime" value="${snapTime }"/>
          <td>部门</td>
          <td>
          	<ct:codePicker name="query.deptId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.deptId }" />
		  </td>
          <td></td>
          <td></td>
        </tr>
      </tbody>
    </table>
  </div>
<div class="formbox">
  <h3 class="datetitle_01">
    <span>历史岗位列表<font color="#0457A7" style="font-weight:normal;">快照时间:${snapTime }</font></span>
  </h3>
  <table width="100%" class="dateline" id="tiptab">
    <thead id="list_head">
      <tr>
        <td>序号</td>
		<td>岗位编号</td>
		<td>岗位名称</td>
		<td>岗位类别</td>
		<td>岗位等级</td>
		<td>上级岗位</td>
		<td>计划人数</td>
      </tr>
    </thead>
    <tbody id="list_body">
      <c:forEach items="${pageList}" var="post" varStatus="st">
		<tr name="tr">
		<td>${st.count }</td>
		<td name="guid">${post.guid }</td>
		<td>${post.postValue }</td>
		<td>${post.postTypeValue }</td>
		<td>${post.levelValue }</td>
		<td>${post.superiorValue }</td>
		<td>${post.planNumber }</td>
		</tr>
	  </c:forEach>
    </tbody>
  </table>
  <ct:page pageList="${pageList }" />
</div>
</form>

  </body>
</html>
