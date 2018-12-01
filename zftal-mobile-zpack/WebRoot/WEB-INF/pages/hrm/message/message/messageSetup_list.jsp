<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript">
    $(function(){

			var current = null;

			$("tbody > tr[name^='tr']").click(function(){	//监听单击行
				if(current != null) {
					current.removeClass("current");
				}
				$(this).attr("class", "current");
				current = $(this);
			});
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[id='jobName']").val();
				queryEntity(id);
			});

			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function queryEntity(id){//查询
			showWindow("消息定时配置","<%=request.getContextPath()%>/message/messageSetup_edit.html?config.id="+id,"600","500");
		}

		function operationList(){

			$("#btn_zj").click(function(){
				showWindow("消息定时配置","<%=request.getContextPath()%>/message/messageSetup_addInit.html","600","500");
			});
			
			$("a[name='setup']").click(function(){
				var id = $(this).closest("tr").find("input[id='jobName']").val();
				queryEntity(id);
			});
			
			$("a[name='execute']").click(function(){
				var id = $(this).closest("tr").find("input[id='jobName']").val();
				$.post('<%=request.getContextPath()%>/message/messageSetup_execute.html',"config.id="+id,function(data){
					var callback = function(){
						location.href = _path+"/message/messageSetup_list.html";
					}
					processDataCall(data,callback);
				},"json");
			});
			
			$("a[name='delete']").click(function(){
				showConfirm("确定要删除该记录吗？");
    			
    			$("#why_cancel").click(function(){
    				divClose();
    			});
    	
   				var id = $(this).closest("tr").find("input[id='jobName']").val();
    			$("#why_sure").click(function(){
    				alertDivClose();
    				$.post('<%=request.getContextPath()%>/message/messageSetup_delete.html',"config.id="+id,function(data){
    					var callback = function(){
    						location.href = _path+"/message/messageSetup_list.html";
    					}
    					processDataCall(data,callback);
    				},"json");
    			});
			});

			$(".select_tools a").css("cursor","pointer");
			operationHover();
		}
    </script>
  </head>
  <body>
  <div class="toolbox">    
		<!-- 按钮 -->
		<div class="buttonbox">
			<ul>
				<li>
					<a id="btn_zj" class="btn_zj">增 加</a>
				</li>
			</ul>
	
		</div>
	</div>
 <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>消息提醒管理<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以修改信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td>消息类型</td>
						<td>是否通知本人</td>
						<td>运行状态</td>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="list" var="config">
					<tr name="tr">
						<input type="hidden" id="jobName" value="${config.id }"/>
						<td>${config.xxlx }</td>
						<td>${config.sftzbr=="1"?"是":"否" }</td>
						<td>${config.zt=="1"?"运行":"停止" }</td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">配置</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="setup" href="#" class="tools_list">配置</a></li>
					                <li><a name="execute" href="#" class="tools_list">手动执行</a></li>
					                <li><a name="delete" href="#" class="tools_list">删除</a></li>
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
  </body>
</html>
