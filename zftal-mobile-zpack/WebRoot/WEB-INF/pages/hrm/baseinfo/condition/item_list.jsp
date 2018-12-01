<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/baseinfo/condition/selectConsle.js"></script>
    <script type="text/javascript">
    	var tr = null;//选中列的全局变量
       	//var _tr;//当前操作行,用于更新操作
    	$(function(){
        	var type=$("#htype").val();

			$("#btn_zj").click(function(){//功能条增加按钮
				tipsWindown("增加","url:post?<%=request.getContextPath()%>/baseinfo/conditionDefined_itemInput.html?type="+type+"&query.parentId=${query.parentId}","720","250","true","","true","id");
			});
			
			$("#btn_dr").click(function(){//功能条增加按钮
				tipsWindown("批量增加","url:post?<%=request.getContextPath()%>/baseinfo/conditionBatch_setup.html?type="+type+"&rootId=${query.parentId}","720","400","true","","true","id");
			});
			
			$("#btn_sc").click(function(){//功能条增加按钮
				preClear('${query.parentId}');
			});
			
			$("tbody > tr").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[name='id']").val();
				queryEntity(id);
			});
			
			$("#back").click(function(){//功能条返回按钮
				location.href="<%=request.getContextPath()%>/baseinfo/conditionDefined_rootList.html?type="+type;
			});
			
			$("tbody > tr").click(function(){//行数据点击高亮及更新全局变量
				if(tr != null) {
					tr.removeClass("current");
				}
				$(this).attr("class", "current");
				tr = $(this);
			});
			
			$("#mv_up").click(function(){//功能条上移功能
				if(tr!=null&&tr.length>0){
					var tr_name = tr.attr("name");
					var prv = tr.prevAll("[name='"+tr_name+"']:first");
					if(prv.length>0){
						tr.after(prv);//父节点交换
						var tr_order = tr.find("input[name='order']").val();
						var prv_order = prv.find("input[name='order']").val();
						tr.find("input[name='order']").val(prv_order);
						prv.find("input[name='order']").val(tr_order);
						var tr_id = tr.find("input[name='id']").val();
						var tr_child = $("tbody > tr[name*="+tr_id+"]");
						if(tr_child!=null&&tr_child.length>0){
							tr.after(tr_child);
						}
						var prv_id = prv.find("input[name='id']").val();
						var prv_child = $("tbody > tr[name*="+prv_id+"]");
						if(prv_child!=null&&prv_child.length>0){
							//prv.after(prv_child);
						}
						updateOrder(tr);//调整排序
					}else{
						showWarning("不能上移了");
						$("#war_sure").click(function(){
							divClose();
						});
					}
				}else{
					showWarning("请先点击目标行");
					$("#war_sure").click(function(){
						divClose();
					});
				}
			});
			$("#mv_down").click(function(){//功能条下移功能
				if(tr!=null&&tr.length>0){
					var tr_name = tr.attr("name");
					var next = tr.nextAll("[name='"+tr_name+"']:first");
					if(next.length>0){
						//next.after(tr);//父节点交换
						tr.before(next);
						var tr_order = tr.find("input[name='order']").val();
						var next_order = next.find("input[name='order']").val();
						tr.find("input[name='order']").val(next_order);
						next.find("input[name='order']").val(tr_order);
						var next_id = next.find("input[name='id']").val();
						var next_child = $("tbody > tr[name*="+next_id+"]");
						if(next_child!=null&&next_child.length>0){
							next.after(next_child);
						}
						var tr_id = tr.find("input[name='id']").val();
						var tr_child = $("tbody > tr[name*="+tr_id+"]");
						if(tr_child!=null&&tr_child.length>0){
							//tr.after(tr_child);
						}
						updateOrder(tr);//调整排序
					}else{
						showWarning("不能下移了");
						$("#war_sure").click(function(){
							divClose();
						});
					}
				}else{
					showWarning("请先点击目标行");
					$("#war_sure").click(function(){
						divClose();
					});
				}
			});
			$("tbody > tr").find("a[class*='list']").click(function(){//行数据树节点点击折叠事件
				loadNode($(this));
			});
			operationList();//初始化操作栏目
			fillRows("15", "", "", false);//填充空行
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
	    function preClear(id){//删除前操作
			showConfirm("确定要清空当前条件吗？");
	
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				clearEntity(id);
			});
		}
	    
	    function clearEntity(id){
	    	$.post('<%=request.getContextPath()%>/baseinfo/conditionBatch_delete.html',"rootId="+id,function(data){
	    		var callback = function(){
	    			location.href = location.href;
	    		};
	    		processDataCall(data, callback);
			},"json");
	    }
	    
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/baseinfo/conditionDefined_delete.html',"guid="+id,function(data){
				var callback = function(){
					$(tr).remove();
				};
				processDataCall(data, callback);
			},"json");
		}
		function queryEntity(id){//查询
			tipsWindown("修改","url:post?<%=request.getContextPath()%>/baseinfo/conditionDefined_itemEdit.html?guid="+id,"720","250","true","","true","id");
		}

		function updateOrder(tr){//更新行数据排序
			var tr_name = tr.attr("name");
			var next = tr.parent().find("tr[name='"+tr_name+"']");
			var param = next.find("input").serialize();
			$.post('<%=request.getContextPath()%>/baseinfo/conditionDefined_updateOrder.html',param,function(data){
				if( !data.success ) {
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						divClose();
					})
				}
			},"json");
		}

		function loadNode(obj){
			var tr = $(obj).closest("tr");
			
			var parentId = $(tr).find("input[name='id']").val();
			$.post('<%=request.getContextPath()%>/baseinfo/conditionDefined_getChildren.html',"query.parentId="+parentId,function(data){
				if(data.success&&data.result.length>0){
					var sty = $(obj).attr("class");
					sty = sty.replace("up","down");
					$(obj).attr("class",sty);
					var last = tr;
					var level = parseInt(last.attr("alt"))+1;
					$(data.result).each(function(i){
						var clone = last.clone(true);//行复制
						$(clone).attr("alt",level);
						$(clone).attr("name",this.parentId);
						$(clone).find("#positionbox").remove();//剔除浮动动作条
						$(clone).removeClass("current");//剔除鼠标悬停高亮
						$(clone).find("[class^='list_ico']").text(this.title);
						$(clone).find("td[name='text'] span").text(this.text);
						$(clone).find("input[name='id']").val(this.guid);
						$(clone).find("td[name='express'] span").text(this.express);
						$(clone).find("input[name='order']").val(i+1);
						if(this.hasChild){
							$(clone).find("[class^='list_ico']").attr("class","list_ico_up"+(level-1));
						}else{
							$(clone).find("[class^='list_ico']").attr("class","list_ico"+(level-1));
						}
						$(clone).insertAfter(last);
						last = $(clone);
						});
					tr.find("a[class*='list']").unbind("click");//父节点点击事件更新
					tr.find("a[class*='list']").click(function(){
						foldTr($(this));
						});
				}
			},"json");
		}

		function foldTr(obj){
			var sty = $(obj).attr("class");
			if(sty.indexOf("down")>0){
				sty = sty.replace("down","up");
				var tr = $(obj).closest("tr");
				hideNode(tr);
				//var id = tr.find("td[name='guid']").text();
				//$("tbody > tr[name="+id+"]").hide();
				//alert(tr.attr("alt"));
			}else{
				sty = sty.replace("up","down");
				var tr = $(obj).closest("tr");
				var id = tr.find("input[name='id']").val();
				$("tbody > tr[name="+id+"]").show();
				//alert(tr.attr("alt"));
			}
			$(obj).attr("class",sty);
		}

		function hideNode(tr){//隐藏节点及修改节点样式（递归）
			var id = tr.find("input[name='id']").val();
			var children = $("tbody > tr[name="+id+"]");
			if(children.length>0){
				children.each(function(){
					hideNode($(this));
					//alert($(this).find("a[class*='list']").length);
					var node = $(this).find("a[class*='list']");
					if(node.length>0){
						var sty = node.attr("class");
						sty = sty.replace("down","up");
						node.attr("class",sty);
					}
					$(this).hide();
				});
			}
		}

		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				tr = $(this).closest("tr");
				var id = $(this).closest("tr").find("input[name='id']").val();
				queryEntity(id);
			});
			$("a[name='del']").click(function(){//行数据修改链接
				tr = $(this).closest("tr");
				var id = $(this).closest("tr").find("input[name='id']").val();
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
						<li>
							<a id="mv_up" class="btn_sy" onclick="">上 移</a>
						</li>
						<li>
							<a id="mv_down" class="btn_xy" onclick="">下 移</a>
						</li>
						<li>
							<a id="btn_dr" class="btn_dr" onclick="">批量增加</a>
						</li>
						<li>
							<a id="btn_sc" class="btn_sc" onclick="">清空</a>
						</li>
					</ul>
					<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
		
<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>条件管理<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以修改）</font></span>
    </h3>
<!--标题end-->
	<table width="100%" class="dateline" id="tiptab">
		<thead id="list_head">
			<tr>
				<%--<th width="5%">
					<input type="checkbox" />
				</th>
				--%><td width="15%">条件名称</td>
			    <td width="45%">表达式</td>
				<td width="25%">描述信息</td>
				<td width="15%">操作</td>
			</tr>
				<input type="hidden"  id="htype" name="htype" value="${type}"/>
		</thead>
		<tbody id="list_body">
			<c:forEach items="${list}" var="obj" varStatus="vs">
			<tr name="${obj.parentId }" alt="1">
				<%--<th><input type="checkbox" name="id" value="${obj.guid }"/><input type="hidden" name="order" value="${vs.index+1 }"></th>
				--%><c:if test="${obj.hasChild}">
		        <td><a id="ico_updown_${obj.guid }" class="list_ico_up">${obj.title }</a></td>
		        </c:if>
		        <c:if test="${!obj.hasChild}">
		        <td name="title"><span class="list_ico">${obj.title }</span></td>
		        </c:if>
				<td name="express"><span>${obj.express }</span><input type="hidden" name="id" value="${obj.guid}"><input type="hidden" name="order" value="${vs.index+1 }"></td>
				<td name="text"><span>${obj.text }</span></td>
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
</div>
</body>
</html>
