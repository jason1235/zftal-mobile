<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
	
	<script type="text/javascript">
		$(function(){
			
			operationList();
			fillRows("20", "", "", false);//填充空行
		})
		
		function qm(mn){
			var frame = $(parent.frames['topframe']);
			frame.find("input[name='quickId']").val(mn);
			var topCode = mn.substring(0,3);
			frame.find("a[id='li_"+topCode+"']").click();
		}
		
		function operationList(){
			$("a[name='modify']").click(function(){//行数据处理链接
				var id = $(this).attr("id");
				if(id == null || id == ''){
					return alert("ID缺失");
				}
				qm(id);
			});
			
			$(".select_tools a").css("cursor","pointer");
			
			operationHover();
		}
	</script>
  </head>
  
  <body>
 <form action="<%=request.getContextPath()%>/pendingAffair/pendingAffair_list.html" name="searchForm" id="searchForm" method="post">
	<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>待办事宜列表<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td>消息</td>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="list" var="info">
						<tr name="tr">
							<td style="padding-left:20px">${info.affairName }(${info.sumNumber })条消息</td>
							<td>
							  <div>
						      	<div class="current_item">
						        	<span class="item_text" style="background:none;">处理</span>
						        </div>
						        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
						            <ul>
						                <li><a id="${info.menu}" name="modify" href="#" class="tools_list">处理</a></li>
						            </ul>
						        </div>
						      </div>
						    </td>
						</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  
	</div>
  	  </form>
  </body>
</html>
