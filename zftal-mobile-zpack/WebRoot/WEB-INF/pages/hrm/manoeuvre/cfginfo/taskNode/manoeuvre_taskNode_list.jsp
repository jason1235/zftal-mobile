<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" defer="" src="/zftal-hrm/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		
	<script type="text/javascript">
		var current = null;
		$(function(){
			$(".current_item").hover(function(){
				$(this).next("div.select_tools").show();
				$(this).parent().css("position","relative");
			},function(){
				$(this).next("div.select_tools").show().hide();
				$(this).parent().css("position","");
			})
			$(".select_tools").hover(function(){
				$(this).show();
				$(this).parent().css("position","relative");		
			},function(){
				$(this).hide();
				$(this).parent().css("position","");
			})
					
			$(".select_tools a").css("cursor","pointer");

			$("tbody > tr[name='tr']").click(function(){
				var id = $(this).attr("id");
				if( id == null ){
					return;
				}
				if(current != null){
					current.removeClass("current");
				}
				$(this).attr("class", "current");
				current = $(this);
			})
			
			$("tbody > tr[name='tr']").dblclick(function(){
				var id = $(this).attr("id");
				modify(id);
			});
			$("#btn_zj").click(function(){
				add();
			}); 
			$("a[name='modify']").click(function(){
				var id = $(this).closest("tr").attr("id");
				modify(id);
			});
			$("a[name='modify']").each(function(){
				$(this).attr("title","双击数据行可以进入修改");
			});
			
			$("a[name='del']").click(function(){
				var id = $(this).closest("tr").attr("id");
				remove(id);
			});

			$("a[name='setAudit']").click(function(){
				var id = $(this).closest("tr").attr("id");
				setAudit(id);
			});
			
			$("#btn_sy").click(function(){
				moveUp();
			}); 

			$("#btn_xy").click(function(){
				moveDown();
			}); 

			$(".select_tools a").css("width","73px");
			

			fillRows("20", "", "", false);//填充空行
		});

		function moveUp(){
			if( current == null ){
				showWarning("请选需要移动记录！");
				return;
			}
			var prev = current.prev();
			var prevIndex = prev.find("td:first").text();
			if( prevIndex == "" ){
				showWarning("已经在第一行，无法上移！");
				return;
			}
			var nid1 = current.attr("id");
			var nid2 = prev.attr("id");
			$.post("<%=request.getContextPath() %>/manoeuvre/TaskNode_exchange.html", {nid1:nid1,nid2:nid2}, function(data){
				if( data.success ) {
					prev.find("td:first").text( current.find("td:first").text() );
					current.find("td:first").text(prevIndex)
					current.after(prev);
				}
			}, "json")
		}

		function moveDown(){
			if( current == null ){
				showWarning("请选需要移动记录！");
				return;
			}
			var next = current.next();
			var nextIndex = next.find("td:first").text();
			if( $.trim(nextIndex) == "" ){
				showWarning("已经在最后一行，无法下移！");
				return;
			}
			var nid1 = current.attr("id");
			var nid2 = next.attr("id");

			$.post("<%=request.getContextPath() %>/manoeuvre/TaskNode_exchange.html", {nid1:nid1,nid2:nid2}, function(data){
				if( data.success ) {
					next.find("td:first").text( current.find("td:first").text() );
					current.find("td:first").text(nextIndex)
					current.before(next);
				}
			}, "json")
		}

		function add(){
			showWindow("增加审核环节节点信息","<%=request.getContextPath()%>/manoeuvre/TaskNode_edit.html", 510, 220);
		}

		function modify(id){
			showWindow("修改审核环节节点信息","<%=request.getContextPath()%>/manoeuvre/TaskNode_edit.html?info.nid="+id, 510, 220);
		}

		function remove(id){
			showConfirm("确定要删除吗？");
			
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}

		function delEntity(id){
			$.post('<%=request.getContextPath()%>/manoeuvre/TaskNode_remove.html?query.nid='+id,'',function(data){
				var callback = function(){
					$("#searchForm").submit();
				};
				processDataCall(data, callback);
			},"json");
		}

		function setAudit(id){
			if(id == null || id == ''){
				showWarning("未选定任何记录");
				return false;
			}
			window.location.href = "<%=request.getContextPath()%>/manoeuvre/AuditConfiguration_list.html?query.taskNode.nid="+id;
		}
		
	</script>
	
  </head>
  
  <body>
  	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" class="btn_zj">增 加</a></li>
				<li><a id="btn_sy" class="btn_sy">上移</a></li>
				<li><a id="btn_xy" class="btn_xy">下移</a></li>
			</ul>
		</div>	
	</div>
    <form action="<%=request.getContextPath()%>/manoeuvre/TaskNode_list.html" name="searchForm" id="searchForm" method="post">
    	
    	<div class="formbox">
    	<!--标题start-->
		    <h3 class="datetitle_01">
		    	<span>审核环节信息<font color="#0457A7" style="font-weight:normal;"></font></span>
		    </h3>
		<!--标题end-->	
			<table width="100%" class="dateline" id="tab">
				<thead id="list_head">
					<tr>
						<td>序号</td>
						<td>环节名称</td>
						<td>备注</td>
						<td width="120px">操作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${infoList}" var="bean" varStatus="sta">
						<tr name="tr" id="${bean.nid }">
							<td>${bean.order }</td>
							<td>${bean.nodeName }</td>
							<td>${bean.remark }</td>
							<td>
								<div>
							      	<div class="current_item">
							        	<span class="item_text">修改</span>
							        </div>
							        <div class="select_tools" id="select_tools1" style=" width:100px; display:none">
							            <ul>
							                <li><a name="modify" class="first1">修改</a></li>
							                <li><a name="setAudit" class="tools_list">编辑审核设置</a></li>
							                <li><a name="del" class="last1">删除</a></li>
							            </ul>
							        </div>
							    </div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
    	</div>
    </form>
  </body>
</html>
