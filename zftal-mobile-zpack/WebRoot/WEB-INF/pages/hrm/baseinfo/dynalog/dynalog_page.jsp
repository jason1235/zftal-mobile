<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	
<head>
	<%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
	<script type="text/javascript">
		$(function(){
			var current = null;

			// 行单击选定事件
			$("#list_body tr").click( function(){
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			
			$("button[name='search']").click(function(e){//搜索按钮
				$("#log").attr("action","<%=request.getContextPath()%>/baseinfo/dynalog_page.html");
				$("#log").attr("method","post");
				$("#log").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			
			operationList();
			fillRows("20", "", "", false);//填充空行
			$("select[name='classId']").val('${classId}');
			$("select[name='type']").val('${type}');
			//requestSnapData();
		});
		
		function operationList(){

			$("a[name='detail']").click(function(){
				var logId = $(this).closest("tr").find("input[id='logId']").val();
				var classId = $("select[name='classId']").val();
				window.location.href=_path+"/baseinfo/dynalog_detail.html?logId="+logId+"&classId="+classId;
			});
			
			$(".select_tools a").css("cursor","pointer");

			operationHover();
		}
		
    </script>
</head>
<body>
<form action="baseinfo/dynalog_page.html" method="post" id="log">
	<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
		  <th>信息类</th>
          <td>
          	<select style="width:156px" name="classId">
          	<s:iterator value="classList" var="clazz">
          	<option value="${clazz.guid }">${clazz.name }</option>
          	</s:iterator>
          	</select>
          </td>
          <th>工　号</th>
          <td><input type="text" name="gh" class="text_nor" style="width:150px" value="${gh }"/></td>
		  <th>姓     名</th>
          <td><input type="text" name="username" class="text_nor" style="width:150px" value="${username }"/></td>
        </tr>
        <tr>
		  <th>日志时间起始于</th>
          <td>
          	<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" type="text" name="before" style="width:150px" value="${before }"/>
          </td>
          <th>日志时间截至到</th>
          <td><input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" type="text" name="end" style="width:150px" value="${end }"/></td>
		  <th>类型</th>
          <td>
          	<select name="type" style="width:156px">
          	<option value="">全部</option>
          	<option value="add">新增</option>
          	<option value="modify">修改</option>
          	<option value="remove">删除</option>
          	</select>
          </td>
        </tr>
        <tr>
		  <th>操作人职工号</th>
          <td>
          	<input type="text" name="operator" class="text_nor" style="width:150px" value="${operator }"/>
          </td>
          <th>&nbsp;</th>
          <td>&nbsp;</td>
		  <th>&nbsp;</th>
          <td>
          	&nbsp;
          </td>
        </tr>
      </tbody>
      <tfoot>
      	<tr>
      	  <td colspan="6">
		  	<div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>
      	</tr>
      </tfoot>
    </table>
  </div>
		<div class="formbox" id="snap_content">
			<h3 class="datetitle_01">
				<span>日志数据<font color="#0457A7" style="font-weight:normal;"></font></span>
			</h3>
			<div style="overflow-x:auto;">
			<table width="100%" class="dateline tablenowrap" id="tiptab" >
				<thead id="list_head">
					<tr>
					<td>日志时间</td>
					<td>职工号</td>
					<td>姓名</td>
					<td>类型</td>
					<td>操作人</td>
					<td>操作人职工号</td>
					<td>操作</td>
					</tr>
				</thead>
				<tbody id="list_body">
				<s:iterator value="pageList" var="info">
				<tr>
				<td><s:date name="values['operation_time_']" format="yyyy-MM-dd HH:mm:ss"/><input type="hidden" id="logId" value="${info.values['logid'] }"/></td>
				<td>${info.values['gh'] }</td>
				<td>${info.values['xm2'] }</td>
				<td>
					<s:if test="values['operation_'] == 'add'">新增</s:if>
					<s:if test="values['operation_'] == 'modify'">修改</s:if>
					<s:if test="values['operation_'] == 'remove'">删除</s:if>
				</td>
				<td><ct:PersonParse code="${info.values['operator_'] }" /></td>
				<td>${info.values['operator_'] }</td>
				<td>
				  <div>
			      	<div class="current_item">
			        	<span class="item_text">详情</span>
			        </div>
			        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
			            <ul>
			                <li><a name="detail" href="#" class="tools_list">详情</a></li>
			            </ul>
			        </div>
			      </div>
				</td>
				</tr>
				</s:iterator>
				</tbody>
			</table>
			</div>
		<ct:page pageList="${pageList }"/>
		</div>
</form>
</body>
</html>
