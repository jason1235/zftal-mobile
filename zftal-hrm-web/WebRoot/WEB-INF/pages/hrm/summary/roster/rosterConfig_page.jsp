<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/roster/property.js"></script>
    <script type="text/javascript">
    $(function(){
        	var _tr;
			//$("#btn_zj").click(function(){//功能条增加按钮
			//	tipsWindown("增加","url:post?<%=request.getContextPath()%>/summary/rosterConfig_input.html","480","170","true","","true","id");
			//});
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity(id);
			});
			$("span.list_ico_up").click(function(){
				var tr = $(this).closest("tr");
				if($(tr).data("flag")==null)
					openConfigList(tr);
				else{
					var cls = $(tr).find("span[class^=list_ico]").attr("class");
					var id = $(tr).find("input[id='guid']").val();
					var configs = $("tr[id='"+id+"']");
					if(cls == "list_ico_down"){
						configs.hide();
						$(tr).find("span.list_ico_down").removeClass().addClass("list_ico_up");
					}else{
						configs.show();
						$(tr).find("span.list_ico_up").removeClass().addClass("list_ico_down");
					}
				}
			});
			$("tbody > tr").click(function(){
				if(_tr != null) {
					_tr.removeClass("current");
				}
				$(this).attr("class", "current");
				_tr = $(this);
			});
			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	    function newEntity(id){//查询
	    	showWindow("增加","<%=request.getContextPath()%>/summary/rosterConfig_input.html?config.classid="+id,"480","170");
		}
    
		function queryEntity(tr){//查询
			var classid = $(tr).attr("id");
			var id = $(tr).find("input[id='guid']").val();
			showWindow("维护","<%=request.getContextPath()%>/summary/rosterConfig_query.html?config.classid="+classid+"&config.guid="+id,"480","170");
		}
		
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/summary/rosterConfig_delete.html',"config.guid="+id,function(data){
				var callback = function(){
					window.location.reload();
				};
				processDataCall(data, callback);
			},"json");
		}

		function preDel(id){//删除前操作
			showConfirm("确定要删除吗？");
			$("#why_cancel").click(function(){
				divClose();
			});
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}
		
		function operationList(){
			$("a[name='add']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				newEntity(id);
				_tr = $(this).closest("tr");
			});
			$("a[name='modify']").click(function(){
				//var id = $(this).closest("tr").find("input[id='guid']").val();
				queryEntity($(this).closest("tr"));
				_tr = $(this).closest("tr");
			});
			$("a[name='delete']").click(function(){
				var id = $(this).closest("tr").find("input[id='guid']").val();
				//_tr = $(this).closest("tr");
				preDel(id);
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
			$("a[name='modify']").each(function(){
				$(this).attr("title","双击数据行可以进入修改");
			});
		}
		
		function openConfigList(tr){
			var id = $(tr).find("#guid").val();
			$.post('<%=request.getContextPath()%>/summary/rosterConfig_children.html',"query.classid="+id,function(data){
				if(data.success){
					$(data.result).each(function(){
						var config = this;
						var queryText = "";
						if(config.queryType == "1"){
							queryText = "精确查询";
						}else if(config.queryType == "2"){
							queryText = "模糊查询";
						}else if(config.queryType == "3"){
							queryText = "范围查询";
						}
						
						var name = "";
						if(config.infoProperty != null){
							name = config.infoProperty.name;
						}
						var rowHtml = "<tr id=\""+id+"\"><input type='hidden' id='guid' value='"+config.guid+"'>"
						+"<td><span class='list_ico1'>"+name+"</span></td>"
						+"<td name='queryType'>"+queryText+"</td>"
						+"<td>"
						+"	<div>"
						+"      <div class=\"current_item\">"
					    +"   	<span class=\"item_text\">修改</span>"
					    +"  </div>"
					    +"  <div class=\"select_tools\" id=\"select_tools1\" style=\" width:80px; display:none\">"
					    +"      <ul>"
					    +"          <li><a name=\"modify\" href=\"#\" class=\"first1\">修改</a></li>"
					    +"          <li><a name=\"delete\" href=\"#\" class=\"last1\">删除</a></li>"
					    +"      </ul>"
					    +"  </div>"
					    +"</div></td>"
						+"</tr>";
						$(tr).after(rowHtml);
					});
					//操作悬停事件重新监听
					$(".current_item").unbind("mouseenter mouseleave");
					$(".select_tools").unbind("mouseenter mouseleave");
					$(".select_tools").find("a").unbind("click");
					operationList();
					$(tr).find("span.list_ico_up").removeClass().addClass("list_ico_down");
					$(tr).data("flag","1");//数据已加载
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						divClose();
					});
				}
			},"json");
		}
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
					</ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>花名册配置<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="25%">名称</td>
						<td width="60%">查询类型</td>
						<td width="15%">操作</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="classList" var="clazz">
						<tr>
						<c:if test='${cntMap[clazz.guid] =="0" }'>
						<td ><span class="list_ico">${clazz.name}</span> - (<span>0</span>)</td>
						</c:if>
						<c:if test='${cntMap[clazz.guid] !="0" }'>
						<td ><span class="list_ico_up">${clazz.name}</span> - (<span>${cntMap[clazz.guid] }</span>)</td>
						</c:if>
						<td >&nbsp;<input type="hidden" id="guid" value="${clazz.guid}"/></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">新增字段</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="add" href="#" class="tools_list">新增字段</a></li>
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
