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
        	var _tr;
        	var type=$("#type").val();

			$("#btn_zj").click(function(){//功能条增加按钮
				tipsWindown("增加","url:post?<%=request.getContextPath()%>/leaveSchool/leaveStep_input.html","480","170","true","","true","id");
			});

			var current = null;
			
			$("tbody> tr[name='tr']").click(function(){
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[id='guid']").val();
				if($(this).attr("status")=="0"){
				    preProcess(id);
				}
			});
			
			$("button[name='searchform']").click(function(e){//搜索按钮
				$("#searchform").attr("action","<%=request.getContextPath()%>/leaveSchool/leaveProcess_list.html");
				$("#searchform").attr("method","post");
				$("#searchform").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			
			initSelect('query.status','${query.status}');
			operationList();//初始化操作栏目
		     var xmtds=$("td[name=xm]");
			 for ( var i=0; i<xmtds.length; i++){
				var xmtd = xmtds[i];
				var id = $(xmtd).closest("tr").find("input[id='globalid']").val();
				var html="<a style=\"color:#074695\" href=\"<%=request.getContextPath()%>/normal/staffResume_list.html?globalid="+id+"&type=teacher\" >"+$(xmtd).html()+ "</a>";
				$(xmtd).html(html);
				
			}
			fillRows("20", "", "", false);//填充空行
		});
		
	    function preProcess(id){
			showConfirm("确定该员工已完成此流程处理吗？");
	
			$("#why_cancel").click(function(){
				divClose();
			});
			
			$("#why_sure").click(function(){
				processEntity(id);
			});
		}
		
		function processEntity(id){//删除
			$.post('<%=request.getContextPath()%>/leaveSchool/leaveProcess_update.html',"process.guid="+id,function(data){
				var callback = function(){
					//window.location.reload();
					location.href = _path+"/leaveSchool/leaveProcess_list.html";
				};
				processDataCall(data, callback);
			},"json");
		}
		
		function operationList(){
			$("a[name='modify']").click(function(){//行数据处理链接
				var id = $(this).closest("tr").find("input[id='guid']").val();
				preProcess(id);
			});
			
			$(".select_tools a").css("cursor","pointer");
			
			operationHover();
		}
    </script>
  </head>
  <body>
 <form action="leaveSchool/leaveProcess_list.html" name="searchform" id="searchform" method="post">
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th>部　门</th>
          <td>
		  	<ct:codePicker name="query.deptId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.deptId }" />
		  </td>
          <th>姓　名</th>
          <td>
          	<input type="text" id="query.name" name="query.name" class="text_nor" style="width:100px" value="${query.name }"/>
		  </td>
          <th>状　态</th>
          <td>
          	<select name="query.status" style="width:80px">
          		<option value="0">未处理</option>
          		<option value="1">已处理</option>
          	</select>
		  </td>
		  <td colspan="6">
          	<div class="btn">
              <button class="btn_cx" type="button" name="searchform" >查 询</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>离校处理列表<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以处理）</font></span>
		<%-- (环节所属处理部门有浏览权限，负责人有处理权限)--%>
    </h3>
<!--标题end-->
		<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
					    <td width="80px">操作</td>
						<td>职工号</td>
						<td>姓名</td>
						<td>部门</td>
						<td>现任职务</td>
						<td>处理部门</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<s:iterator value="list" var="process">
					<tr name="tr" status="${status }" >
					<s:if test='status == "0"'>
                        <td>
                          <div>
                            <div class="current_item">
                                <span class="item_text">处理</span>
                            </div>
                            <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
                                <ul>
                                    <li><a name="modify" href="#" class="tools_list">处理</a></li>
                                </ul>
                            </div>
                          </div>
                        </td>
                        </s:if>
                        <s:else>
                        <td>已处理</td>
                        </s:else>
						<td name="accountId">${process.accountId }<input type="hidden" id="guid" value="${process.guid }"/></td>
						<td>${process.dynaBean.viewHtml['xm'] }</td>
						<td>${process.dynaBean.viewHtml['dwm'] }</td>
						<td>${process.duty }</td>
						<td><ct:codeParse catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${process.deptId }" /></td>
						
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	<ct:page pageList="${list }" />
	</div>
  </form>
  </body>
</html>
