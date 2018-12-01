<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript">
    	var tr;//选中列的全局变量
    	$(function(){
			$("#add").click(function(){//功能条增加按钮
				showWindow("增加","<%=request.getContextPath()%>/code/codeItem_input.html?catalogId=${query.catalogId}",720,270);
				//tipsWindown("增加","url:post?<%=request.getContextPath()%>/code/codeItem_input.html?catalogId=${query.catalogId}","720","250","true","","true","id");
			});
			
			$("#back").click(function(){//功能条返回按钮
				location.href="<%=request.getContextPath()%>/code/codeCatalog_list.html";
			});
			
			$("#delete").click(function(){//功能条删除功能
				var checkes =$("tbody > tr").find("input:checked");
				if(checkes.length==0){
					showWarning("请先选择要删除的行数据");
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
				}
				showConfirm("确定要删除吗？");
				$("#why_cancel").click(function(){
					divClose();
				});
				$("#why_sure").click(function(){
					$.post('<%=request.getContextPath()%>/code/codeItem_delete.html',checkes.serialize()+"&catalogId=${query.catalogId}",function(data){
						var callback = function(){
							window.location.reload();
						};
						processDataCall(data, callback);
					},"json");
				});
			});
			
			$("#refresh").click(function(){//功能条重启按钮
				$.post('<%=request.getContextPath()%>/code/codeItem_reload.html',"query.catalogId=${query.catalogId}",function(data){
					var callback = function(){
						window.location.reload();
					};
					processDataCall(data, callback);
				},"json");
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
						var tr_id = tr.find("td[name='guid'] span").text();
						var tr_child = $("tbody > tr[name*="+tr_id+"]");
						if(tr_child!=null&&tr_child.length>0){
							tr.after(tr_child);
						}
						var prv_id = prv.find("td[name='guid'] span").text();
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
						var next_id = next.find("td[name='guid'] span").text();
						var next_child = $("tbody > tr[name*="+next_id+"]");
						if(next_child!=null&&next_child.length>0){
							next.after(next_child);
						}
						var tr_id = tr.find("td[name='guid'] span").text();
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
			
			$('#import').click(function(){//功能条导入
				showWindow("导入条目","<%=request.getContextPath()%>/code/codeItem_toImport.html?catalogId=${query.catalogId}",480, 140);
				//tipsWindown("导入条目","url:post?<%=request.getContextPath()%>/code/codeItem_toImport.html?catalogId=${query.catalogId}","480","140","true","","true","id");
			});
			
			$('#export').click(function(){//功能条导出
				location.href="<%=request.getContextPath()%>/code/codeItem_exportData.html?query.catalogId=${query.catalogId}";
			});
			
			$('#download').click(function(){//功能条下载模板
				location.href="<%=request.getContextPath()%>/files/template/code/item.xls";
			});

			var current = null;
			
			$("tbody > tr").click(function(){//行数据单击修改事件
				if( current != null ) {
					current.removeClass("current");
				}
				$(this).attr("class", "current");
				current = $(this);
			});
			
			$("tbody > tr").dblclick(function(){//行数据双击修改事件
				var id = $(this).find("td[name='guid'] span").text();
				_tr = $(this);
				queryEntity(id);
			});
		
			$("tbody > tr").find("a[class*='list']").click(function(){//行数据树节点点击折叠事件
				loadNode($(this));
			});
			
			$("tbody > tr").click(function(){//行数据点击高亮及更新全局变量
				if(tr != null) {
					tr.removeClass("current");
				}
				$(this).attr("class", "current");
				tr = $(this);
				});
			$("thead input[type='checkbox']").click(function(){//复选框全选
				if($(this).attr("checked")!=""){
					$(this).attr("checked","true");
					$("tbody > tr").find("input[name='guid']").attr("checked","true");
				}else{
					$(this).attr("checked","");
					$("tbody > tr").find("input[name='guid']").attr("checked","");
				}
			});
			searchCol();//编目搜索层控件
			operationList();//操作栏初始化
			fillRows("15", "", "", false);//填充空行数据
		});

		function preDel(id){//删除前确认
			showConfirm("确定要删除吗？");
			$("#why_cancel").click(function(){
				divClose();
			});
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}
		
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/code/codeItem_delete.html',"guid="+id+"&catalogId=${query.catalogId}",function(data){
				var callback = function(){
					$(_tr).remove();
				};
				processDataCall(data, callback);
			},"json");
		}

		function updateOrder(tr){//更新行数据排序
			var tr_name = tr.attr("name");
			var next = tr.parent().find("tr[name='"+tr_name+"']");
			var radios = next.find("input[type='checkbox']");
			radios.attr("checked","checked");
			var param = next.find("input").serialize();
			radios.removeAttr("checked");
			$.post('<%=request.getContextPath()%>/code/codeItem_updateOrder.html',param+"&catalogId=${query.catalogId}",function(data){
					if( !data.success ) {
						tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
						$("#window-sure").click(function() {
							divClose();
						});
					}
				},"json");
		}
		
		function queryEntity(id){//查询
			showWindow("修改","<%=request.getContextPath()%>/code/codeItem_query.html?guid="+id+"&catalogId=${query.catalogId}",720, 250);
			//tipsWindown("修改","url:post?<%=request.getContextPath()%>/code/codeItem_query.html?guid="+id+"&catalogId=${query.catalogId}","720","250","true","","true","id");
		}

		function loadNode(obj){
			var tr = $(obj).closest("tr");
			
			var catalogId = "${query.catalogId}";
			var parentId = $(tr).find("td[name='guid'] span").text();
			$.post('<%=request.getContextPath()%>/code/codeItem_getChildren.html',"catalogId="+catalogId+"&parentId="+parentId,function(data){
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
						$(clone).find("[class^='list_ico']").text(this.description);
						$(clone).find("td[name='guid'] span").text(this.guid);
						$(clone).find("input[name='guid']").val(this.guid);
						$(clone).find("input[name='order']").val(i+1);
						if(this.hasChild){
							$(clone).find("[class^='list_ico']").attr("class","list_ico_up"+(level-1));
						}else{
							$(clone).find("[class^='list_ico']").unbind("click");
							$(clone).find("[class^='list_ico']").attr("class","list_ico"+(level-1));
						}
						$(clone).find("td[name='visible']").text(this.visible==0?"否":"是");
						$(clone).find("td[name='checked']").text(this.checked==0?"否":"是");
						$(clone).insertAfter(last);
						//last = $(clone);
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
			}else{
				sty = sty.replace("up","down");
				var tr = $(obj).closest("tr");
				var id = tr.find("td[name='guid'] span").text();
				$("tbody > tr[name="+id+"]").show();
			}
			$(obj).attr("class",sty);
		}

		function hideNode(tr){//隐藏节点及修改节点样式（递归）
			var id = tr.find("td[name='guid'] span").text();
			var children = $("tbody > tr[name="+id+"]");
			if(children.length>0){
			children.each(function(){
				hideNode($(this));
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

		function searchCol(){//编目搜索层控件初始化
			$(".datetitle_01 li").hover(function(){
				$(this).parent().next().show();
				$(this).attr("class","sel_dropdown");
			},function(){
				$(this).parent().next().hide();
				$(this).attr("class","ico_xl");
			});
			$(".tosel").hover(function(){
				$(this).show();
				$(this).find(":hidden").show();
				$(this).parent().find(".datetitle_01 li").attr("class","sel_dropdown");
			},function(){
				$(this).hide();
				$(this).parent().find(".datetitle_01 li").attr("class","ico_xl");
			});

			$("#search").click(function(){//搜索按钮点击
				var search = $(this).prev().val();
					searchCatalog(search);
				});
			function searchCatalog(key){
				$("#sel_con > li").each(function(){
					var v = $(this).find("a").text();
					if(v!=null&&v.indexOf(key)<0)
						$(this).hide();
					});
			}
			$("#search").prev().focus(function(){//输入框获取焦点初始化
				$(this).val("");
				});
			$("#search").prev().keydown(function(event){//数据框回车事件
				if(event.keyCode=='13'){
					searchCatalog($(this).val());
				}
				});
			$("#sel_con > li").each(function(){//搜索层过滤
				var li = $(this);
				li.find("a").click(function(){
					var param=li.find("input").val();
					location.href="<%=request.getContextPath()%>/code/codeItem_list.html?query.catalogId="+param;
					});
				});
		}
		var _tr;
		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='guid'] span").text();
				_tr = $(this).closest("tr");
				queryEntity(id);
			});
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='guid'] span").text();
				_tr = $(this).closest("tr");
				preDel(id);
			});
			$('a[name="maintain"]').click(function(){//行数据条目维护链接
				var id = $(this).closest("tr").find("td[name='guid'] span").text();
				location.href="<%=request.getContextPath()%>/code/codeItem_list.html?query.catalogId="+id;
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
			operationPermission();
		}

		function operationPermission(){//操作链接开放判断
			$("a[name='del']").each(function(){
				var tr = $(this).closest("tr");
				var source = "${catalog.source}";
				if(source=='0'){
					$(this).unbind("click");
					$(this).css("cursor","default");
					$(this).parent().prev().find("a").removeClass().addClass("last1");
					$(this).parent().hide();
					//$(this).closest("tr").find(".item_text").css("background",null);
				}
			});
			$("a[name='modify']").each(function(){
				$(this).attr("title","双击数据行可以进入修改");
			});
		}
    </script>
  </head>
  <body>
  <!-- From内容 start-->
  
		<div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<c:if test="${catalog.source == '1'}">
						<li>
							<a id="add" class="btn_zj">增 加</a>
						</li>
						<li>
							<a id="delete" class="btn_sc" onclick="">删 除</a>
						</li>
						</c:if>
						<%--<li>
							<a id="import" class="btn_dr" onclick="">导 入</a>
						</li>
						<li>
							<a id="export" class="btn_dc" onclick="">导 出</a>
						</li>
						<li>
							<a id="download" class="btn_down" onclick="">模板下载</a>
						</li>--%>
						<li>
							<a id="mv_up" class="btn_sy" onclick="">上 移</a>
						</li>
						<li>
							<a id="mv_down" class="btn_xy" onclick="">下 移</a>
						</li>
						<%--<li>
							<a id="refresh" class="btn_qd" onclick="">重 启</a>
						</li>--%>
					</ul>
					<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
				</div>
			<!-- 按钮 -->
			<!-- 过滤条件开始 -->
			<!-- 过滤条件结束 -->
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<div class="formbox">
<!--标题下拉框start-->
<div class="selectbox" style="position:relative;" >
    	<ul class="datetitle_01">
        	<li class="ico_xl">${catalog.name }</li>
        	<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以修改）</font>
        </ul>
        <div class="tosel" id="tosel" style="display:none;">
        	<ul class="sel_search">
    			<li>支持编目名称、拼音模糊搜索</li>
            	<li>
                	<input style="width:300px;" class="text_nor text_nor_rs" name="" type="text" value="请输入关键字" />
    				<button class="btn_common" id="search">搜索</button>
                </li>
            </ul>
            <ul class="sel_tab">
            </ul>
            <ul class="sel_con" id="sel_con">
            	<c:forEach items="${map}" var="ce">
            		<li><a href="#">${ce.value.name }</a><input type="hidden" value="${ce.value.guid }"/></li>
            	</c:forEach>
            </ul>
        </div>
    </div>
<!--标题下拉框end-->
  <table width="100%" class="datelist" id="tiptab">
    <thead id="list_head">
      <tr>
        <td>条目名称</td>
        <td>条目编号</td>
        <td>显　示</td>
        <td>可选中</td>
        <td>已废弃</td>
        <td>包含父节点</td>
        <td> 操　作</td>
      </tr>
    </thead>
    <tbody id="list_body">
      <c:forEach items="${list}" var="level1" varStatus="vs">
      <tr name="root" alt="1">
        <c:if test="${level1.hasChild}">
        <td name="desc"><a id="ico_updown_${level1.guid }" class="list_ico_up">${level1.description }</a></td>
        </c:if>
        <c:if test="${!level1.hasChild}">
        <td name="desc"><span class="list_ico">${level1.description }</span></td>
        </c:if>
        <td name="guid"><span>${level1.guid }</span></td>
        <td name="visible"><c:if test="${level1.visible == 1}">是</c:if><c:if test="${level1.visible == 0}"><font color="red">否</font></c:if></td>
        <td name="checked"><c:if test="${level1.checked == 1}">是</c:if><c:if test="${level1.checked == 0}"><font color="red">否</font></c:if></td>
        <td name="dumped"><c:if test="${level1.dumped == 1}">是</c:if><c:if test="${level1.dumped == 0}"><font color="red">否</font></c:if></td>
        <td name="hasParentNodeInfo"><c:if test="${level1.hasParentNodeInfo == 1}">是</c:if><c:if test="${level1.hasParentNodeInfo == 0}"><font color="red">否</font></c:if></td>
        <td>
          <input type="hidden" name="guid" value="${level1.guid }"/>
          <input type="hidden" name="order" value="${vs.index+1 }">
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
<!--分页显示-->
<!-- <div class="pagination">
                      <div class="pageleft">
                       <p class="pagenum">第<input type="text" value="1" style="color:red; text-align:center;width:20px;" />页 / 共<span class="red">10</span>页， 每页显示
            <input type="text" style="width:20px;" />
            条 /  共<span class="red">20</span>条记录 </p>
                      </div>
                      
                      <div class="pageright">
                      <div id="pagediv" class="paging"> <span id="pagelist" class="pagelist"></span> <a id="first" href="javascript:submitFirstPage()" class="first" title="首页">首　页</a> <a id="pre" href="javascript:submitPrePage()" class="prev" title="上一页">上一页</a> <a id="disonclick" href="javascript:submitNextPage()" class="next" title="下一页">下一页</a> <a id="last" href="javascript:submitLastPage()" class="last" title="末页">末　页</a>
<script>window.document.getElementById('disonclick').disabled="disabled";document.getElementById('disonclick').onclick="";--%></script>
          </div></div>
 
  </div>
                    
</div> -->

  </body>
</html>
