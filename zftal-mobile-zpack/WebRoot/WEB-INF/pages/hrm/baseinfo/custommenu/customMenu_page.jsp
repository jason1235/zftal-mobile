<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/baseinfo/custommenu/custom.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    	var _tr;
    	$(function(){

            $("#btn_zj").click(function(){//功能条增加按钮
            	showWindow("增加",_path+"/baseinfo/customMenu_input.html","580","510");
			});
            
            $("#btn_sy").click(function(){
            	moveUp();
			});
            
            $("#btn_xy").click(function(){
            	moveDown();
			});

            $("tbody > tr[name^='tr']").click( function(){
            	if(_tr != null) {
            		_tr.removeClass("current");
				}
				$(this).attr("class", "current");
				_tr = $(this);
            });
            
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[name='menuId']").val();
				queryEntity(id);
			});
			
			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("<%=request.getContextPath()%>/baseinfo/conditionDefined_rootList.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
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
		function delEntity(id){//删除
			$.post(_path+'/baseinfo/customMenu_delete.html',"custom.menuId="+id,function(data){
				var callback = function(){
					$(_tr).remove();
				};
				processDataCall(data, callback);
			},"json");
		}
		function queryEntity(id){//查询
			showWindow("修改",_path+"/baseinfo/customMenu_edit.html?custom.menuId="+id,"580","510");
		}
		
		function moveUp(){
			var id = getRowId();
			if(id == null){
				alert("请先点击要操作的数据行");
				return;
			}
			$.post(_path+'/baseinfo/customMenu_moveUp.html',"custom.menuId="+id,function(data){
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
			$.post(_path+'/baseinfo/customMenu_moveDown.html',"custom.menuId="+id,function(data){
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
				return $(_tr).find("input[name='menuId']").val();
			}
		}

		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("input[name='menuId']").val();
				_tr = $(this).closest("tr");
				queryEntity(id);
			});
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("input[name='menuId']").val();
				_tr = $(this).closest("tr");
				preDel(id);
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
		}
		
		function validate(){
			var v = $("#form1 input[name='custom.name']");
			if(v.val().length==0){
				alert("分类查询名称不能为空");
				return false;
			}
			var str = checkValue();
			if(str != null){
				alert(str);
				return false;
			}
			
			return true;
		}
		
		function checkValue(){
			var bol = null;
			$("#ul input").each(function(){
				var value = $(this).val();
				var i = $(this).attr("seq");
				if($(this).is(":checked")){
					if($("input[name='custom.conditions[" + i + "].value']").val().length == 0){
						bol = $(this).next().html() + "不能为空";
						return bol;
					};
				}
			});
			return bol;
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
					</ul>
			
				</div>
			<!-- 按钮 -->
			<!-- 过滤条件开始 -->
			<!-- 过滤条件结束 -->
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<form id="search" method="post" action="baseinfo/customMenu_page.html">
<!-- 
  <div class="searchbox_rs">
<div class="search_rs" id="search_rs">
    <input style="width:340px;" class="text_nor text_nor_rs" type="text" value="" />
    <button class="" name="search" >搜索一下</button>
    <h1>说明： 可以按照分类查询名称进行模糊搜索</h1>
    <a class="ico_close03" href="#" title="关闭搜索" onmouseover="this.className='ico_close02'" onmouseout="this.className='ico_close03'" onclick="javascript:document.getElementById('search_rs').style.display='none';javascript:document.getElementById('search_rs_btn').style.display='block'; return false;" ></a>
</div>
<div class="search_rs_btn" style="display:none;" title="打开搜索" id="search_rs_btn" onclick="javascript:document.getElementById('search_rs').style.display='block';javascript:document.getElementById('search_rs_btn').style.display='none'">搜索</div>
</div>
 -->
		
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>分类查询定义<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以修改）</font></span>
    </h3>
<!--标题end-->
		<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="25%">分类查询名称</td>
						<td width="55%">查询参数</td>
						<td width="15%">操　作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${pageList}" var="custom" varStatus="st">
						<tr name="tr">
						<td name="name">${custom.name }</td>
						<td>
							<span name="param">
								<c:forEach items="${custom.conditions }" var="condition">
								<ct:codeParse catalog="${condition.codeId }" code="${condition.value }" />&nbsp;
								</c:forEach>
							</span>
							<input type="hidden" name="menuId" value="${custom.menuId}">
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
  </body>
</html>
