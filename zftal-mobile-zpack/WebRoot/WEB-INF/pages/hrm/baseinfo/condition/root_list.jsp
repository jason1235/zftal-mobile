<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript">
    var type;
    var _tr;
    	$(function(){
            type=$("#type").val();

            $("#btn_zj").click(function(){//功能条增加按钮
				tipsWindown("增加","url:post?<%=request.getContextPath()%>/baseinfo/conditionDefined_rootInput.html?type="+type,"480","190","true","","true","id");
			});
            
            $("#back").click(function(){//功能条返回按钮
				location.href=_path+"/baseinfo/commonSearch_list.html?type=teacher";
			});

            var current = null;
            $("tbody > tr[name^='tr']").click( function(){
            	if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
            });
            
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[name='id']").val();
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
			$.post('<%=request.getContextPath()%>/baseinfo/conditionDefined_delete.html',"guid="+id,function(data){
				var callback = function(){
					$(_tr).remove();
				};
				processDataCall(data, callback);
			},"json");
		}
		function queryEntity(id){//查询
			tipsWindown("修改","url:post?<%=request.getContextPath()%>/baseinfo/conditionDefined_rootEdit.html?guid="+id,"480","190","true","","true","id");
		}

		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='guid'] input").val();
				_tr = $(this).closest("tr");
				queryEntity(id);
			});
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='guid'] input").val();
				_tr = $(this).closest("tr");
				preDel(id);
			});
			$('a[name="maintain"]').click(function(){//行数据维护链接
				var id = $(this).closest("tr").find("input[name='id']").val();
				_tr = $(this);
				location.href="<%=request.getContextPath()%>/baseinfo/conditionDefined_itemList.html?query.parentId="+id+"&type="+type;
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
					<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
				</div>
			<!-- 按钮 -->
			<!-- 过滤条件开始 -->
			<!-- 过滤条件结束 -->
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<form id="search" method="post" action="baseinfo/conditionDefined_rootList.html">
   <input type="hidden"  id="type" name="type" value="${type}"/>
  <div class="searchbox_rs">
<div class="search_rs" id="search_rs">
    <input style="width:340px;" class="text_nor text_nor_rs" name="query.title" type="text" value="${query.title }" />
    <button class="" name="search" >查询</button>
    <h1>说明： 可以按照条件系列名称进行模糊搜索</h1>
    <a class="ico_close03" href="#" title="关闭搜索" onmouseover="this.className='ico_close02'" onmouseout="this.className='ico_close03'" onclick="javascript:document.getElementById('search_rs').style.display='none';javascript:document.getElementById('search_rs_btn').style.display='block'; return false;" ></a>
</div>
<div class="search_rs_btn" style="display:none;" title="打开搜索" id="search_rs_btn" onclick="javascript:document.getElementById('search_rs').style.display='block';javascript:document.getElementById('search_rs_btn').style.display='none'">搜索</div>
</div>
		
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>条件系列定义<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以修改）</font></span>
    </h3>
<!--标题end-->
		<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
						<%--<th>
							<input type="checkbox" />
						</th>
						--%><%--<td width="5%">序号</td>
						--%><td width="25%">系列名称</td>
						<td width="55%">描述信息</td>
						<td width="15%">操　作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${list}" var="catalog" varStatus="st">
						<tr name="tr">
						<%--<th><input type="checkbox" name="guid"/></th>--%>
						<%--<td>${st.index+1 }</td>
						--%><td name="title">${catalog.title }</td>
						<td name="guid"><span name="text">${catalog.text }</span><input type="hidden" name="id" value="${catalog.guid}"></td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">条目维护</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					            	<li><a name="maintain" href="#" class="first1">条目维护</a></li>
					                <li><a name="modify" href="#" class="tools_list">修改</a></li>
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
