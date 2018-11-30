<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript">
    	$(function(){
        	var type=$("#type").val();

			$("#btn_zj").click(function(){//功能条增加按钮
				showWindow("增加", "<%=request.getContextPath()%>/leaveSchool/leaveStep_input.html", 480, 170 );
			});

			var current = null;
			
			$("#list_body tr").click( function(){
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			} );

			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[name='step.guid']").val();
				queryEntity(id);
			});

			operationList();//初始化操作栏目

			fillRows("20", "", "", false);//填充空行
		});
		
	    function preDel(id){//删除前操作
			showConfirm("确定要删除吗？");
	
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}
		
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/leaveSchool/leaveStep_remove.html',"step.guid="+id,function(data){
				var callback = function(){
					window.location.reload();
				};
				processDataCall(data, callback);
			},"json");
		}
		
		function queryEntity(id){//查询
			showWindow("修改", "<%=request.getContextPath()%>/leaveSchool/leaveStep_edit.html?step.guid=" + id, 480, 170 );
		}

		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("input[name='step.guid']").val();
				queryEntity(id);
			});

			$("a[name='del']").click(function(){//行数据删除链接
				var id = $(this).closest("tr").find("input[name='step.guid']").val();
				preDel(id);
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
			<!-- 按钮 -->
			<!-- 过滤条件开始 -->
			<!-- 过滤条件结束 -->
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form action="leaveSchool/leaveStep_list.html" name="searchform" id="searchform" method="post">
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>离校环节列表<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以修改）</font></span>
    </h3>
<!--标题end-->
		<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
						<td>序号</td>
						<td>处理部门</td>
						<td>处理人</td>
						<td width="80px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${list}" var="step" varStatus="st">
						<tr name="tr">
						<input type="hidden" name="step.guid" value="${step.guid }"/>
						<td>${st.count }</td>
						<td><ct:codeParse catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${step.deptId }" /></td>
						<td name="handler"><ct:PersonParse code="${step.handler }" /></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">修改</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="modify" href="#" class="first1">修改</a></li>
					                <li><a name="del" href="#" class="last1">删除</a></li>
					            </ul>
					        </div>
					      </div>
						</td>
					</tr>
					</c:forEach>
				</tbody>
  	</table>
  	<ct:page pageList="${list }" />
	</div>
  	  </form>
  </body>
</html>
