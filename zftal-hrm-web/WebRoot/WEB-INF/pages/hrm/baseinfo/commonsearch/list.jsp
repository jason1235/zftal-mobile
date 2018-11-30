<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtwh/jsgl.js"></script>
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
    <script type="text/javascript">
        	var _tr;
    $(function(){
        	var type=$("#type").val();

			$("#btn_zj").click(function(){//功能条增加按钮
				tipsWindown("增加","url:post?<%=request.getContextPath()%>/baseinfo/commonSearch_input.html?type="+type,"480","200","true","","true","id");
			});
			$("#btn_csh").click(function(){//条件定义按钮
				location.href = _path+"/baseinfo/conditionDefined_rootList.html?type=teacher";
			});
			
			$("#btn_sy").click(function(){
            	moveUp();
			});
            
            $("#btn_xy").click(function(){
            	moveDown();
			});

			$("tbody > tr[name^='tr']").click(function(){//行数据双击事件
				if(_tr != null) {
					_tr.removeClass("current");
				}
				$(this).attr("class", "current");
				_tr = $(this);
			});
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[name='id']").val();
				_tr = $(this);
				queryEntity(id);
			});

			$('a[name="maintain"]').click(function(){//行数据维护链接
				var id = $(this).closest("tr").find("input[name='id']").val();
				location.href="<%=request.getContextPath()%>/baseinfo/conditionDefined_itemList.html?query.parentId="+id;
			});

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("<%=request.getContextPath()%>/baseinfo/commonSearch_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});

			initSelectDiv();
			initConditionColumn();
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
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/baseinfo/commonSearch_remove.html',"guid="+id,function(data){
				var callback = function(){
					window.location.href = _path+"/baseinfo/commonSearch_list.html?type=teacher";
				};
				processDataCall(data, callback);
			},"json");
		}
		function queryEntity(id){//查询
			tipsWindown("修改","url:post?<%=request.getContextPath()%>/baseinfo/commonSearch_query.html?guid="+id,"480","200","true","","true","id");
		}

		function initCheck(data){//初始化选择层中的checkbox状态及弹出层中中文标题
			var ids = data.split(",");
			$("input[name='conditionId']").removeAttr("checked");
			for(var i=0;i<ids.length;i++){
				$("input[name='conditionId']").each(function(){
					if(ids[i] == $(this).val()){
						$(this).attr("checked","checked");
					}
				});
			}
			jointConditionTitle();
		}

		function showSelectDiv(obj){//显示选择层及定位
			var o = $(obj).offset();
			$("#_conditionSel").css("top",o.top+$(obj).height());
			$("#_conditionSel").css("left",o.left);
			$("#inputsel").slideDown("fast");
		}

		function initSelectDiv(){//初始化选择层事件
			$("body").click(function(){
				$("#inputsel").hide();
			});
			$("#inputsel").click(function(e){
				e.stopPropagation();
			});
			$("#inputsel").find(":checkbox").change(function(){
				jointConditionTitle();
			});
		}

		function jointConditionTitle(){//获得已选择条件标题
			var view = "";
			$("input[name='conditionId']:checked").each(function(){
				if(view.length>0){
					view += ", ";
				}
				view += $(this).next().text();
			});
			if(view.length>0){
				$("input[id='conditionView']").val(view);
			}else{
				$("input[id='conditionView']").val("(无)");
			}
		}
		function initConditionColumn(){//页面加载时初始化条件配置的中文显示
			$("td[name='conditions']").each(function(){
				var ids = $(this).find("span").text().split(",");
				var view = "";
				for(var i=0;i<ids.length;i++){
					$("input[name='conditionId']").each(function(){
						if($.trim(ids[i]) == $(this).val()){
							if(view.length>0){
								view += ", ";
							}
							view += $(this).next().text();
						}
					});
					if(view.length>0){
						$(this).find("span").text(view);
					}else{
						$(this).find("span").text("(无)");
					}
				}
			});
		}

		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("input[name='id']").val();
				current = $(this).closest("tr");
				queryEntity(id);
			});

			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("input[name='id']").val();
				current = $(this).closest("tr");
				preDel(id);
			});

			$(".select_tools a").css("cursor","pointer");

			operationHover();
		}
		
		function moveUp(){
			var id = getRowId();
			if(id == null){
				alert("请先点击要操作的数据行");
				return;
			}
			$.post(_path+'/baseinfo/commonSearch_moveUp.html',"guid="+id,function(data){
				var callback = function(){
					var target = $(_tr).prev("[name='tr']");
					if($(target).length>0){
						$(target).before($(_tr));
					}else{
						$("button[name='search']").click();
					}
				};
				if(data.success){
					callback();
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","1000","true","id");
					$("#window-sure").click(function() {
						divClose();
					});
				}
			},"json");
		}
		
		function moveDown(){
			var id = getRowId();
			if(id == null){
				alert("请先点击要操作的数据行");
				return;
			}
			$.post(_path+'/baseinfo/commonSearch_moveDown.html',"guid="+id,function(data){
				var callback = function(){
					var target = $(_tr).next("[name='tr']");
					if($(target).length>0){
						$(target).after($(_tr));
					}else{
						$("button[name='search']").click();
					}
				};
				if(data.success){
					callback();
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						divClose();
					});
				}
			},"json");
		}
		
		function getRowId(){
			if(_tr != null){
				return $(_tr).find("input[name='id']").val();
			}
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
							<a id="btn_sy" class="btn_sy">上 移</a>
						</li>
						<li>
							<a id="btn_xy" class="btn_xy">下 移</a>
						</li>
						<li>
							<a id="btn_csh" class="btn_csh">条件定义</a>
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
<form id="search" method="post" action="baseinfo/commonSearch_list.html">
<!-- 
<div class="searchbox_rs">
<div class="search_rs" id="search_rs">
    <input style="width:340px;" class="text_nor text_nor_rs" name="query.title" type="text" value="${query.title }" />
    <button class="" name="search" >搜索一下</button>
    <h1>说明： 可以按照查询名称进行模糊搜索</h1>
    <a class="ico_close03" href="#" title="关闭搜索" onmouseover="this.className='ico_close02'" onmouseout="this.className='ico_close03'" onclick="javascript:document.getElementById('search_rs').style.display='none';javascript:document.getElementById('search_rs_btn').style.display='block'; return false;" ></a>
</div>
<div class="search_rs_btn" style="display:none;" title="打开搜索" id="search_rs_btn" onclick="javascript:document.getElementById('search_rs').style.display='block';javascript:document.getElementById('search_rs_btn').style.display='none'">搜索</div>
</div>
 -->
<!------------鼠标移上去显示的工具栏-start------------>
<div id="positionbox" style="position:relative; float:left;">
<div class="toolbox_fd" id="div_tools" style="display:none;position:absolute;">
<div class="buttonbox">
  <ul>
    <li><a id="btn_xg" href="#" class="btn_xg" name="tip_modify"> 修改 </a> </li>
    <li><a id="btn_sc" href="#" class="btn_sc" name="tip_del"> 删除 </a> </li>
  </ul>
</div>
</div>
</div>
		
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>常用查询定义<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以修改）</font></span>
    </h3>
<!--标题end-->
        <input type="hidden"  id="type" name="type" value="${query.type}"/>
		<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="25%">查询名称</td>
						<td width="60%">配置条件</td>
						<td width="15%">操作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${pageList}" var="search" varStatus="st">
						<tr name="tr">
						<td name="title"><span>${search.title }</span><input type="hidden" name="id" value="${search.guid }"/></td>
						<td name="conditions">
							<span><c:forEach items="${search.conditions}" var="con" varStatus="st2"><c:if test="${st2.index>0}">,</c:if>${con.guid }</c:forEach></span>
						</td>
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
 <ct:page pageList="${pageList }" />
</div>
</form>
<div id="_conditionSel" class="selectbox" style="position:absolute;z-index:2000;">
    <div class="tosel" id="inputsel" style="display:none;left:0px;top:0px;width:400px;">
        <ul class="sel_con" id="input_sel_con">
        <c:forEach items="${list}" var="condition">
			<input type="checkbox" name="conditionId" value="${condition.guid }" /><span>${condition.title }</span>
		</c:forEach>
        </ul>
    </div>
</div>

  </body>
</html>
