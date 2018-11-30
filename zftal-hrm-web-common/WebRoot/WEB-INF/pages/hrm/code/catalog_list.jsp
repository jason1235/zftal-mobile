<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
        	var _tr = null;
    $(function(){
        	
			$("#btn_zj").click(function(){//功能条增加按钮
				showWindow("增加","<%=request.getContextPath()%>/code/codeCatalog_input.html",720,270);
			});

			$('#btn_dr').click(function(){//功能条导入按钮
				showWindow("导入编目","<%=request.getContextPath()%>/code/codeCatalog_toImport.html",480,140);
			});

			$('#btn_dc').click(function(){//功能条导出按钮
				location.href="<%=request.getContextPath()%>/code/codeCatalog_exportData.html";
			});

			$('#btn_mbxz').click(function(){//功能条下载按钮
				location.href="<%=request.getContextPath()%>/files/template/code/catalog.xls";
			});

			$("tbody > tr[name^='tr']").click(function(){//行数据单击事件
				if(_tr != null) {
					_tr.removeClass("current");
				}
				$(this).attr("class", "current");
				_tr = $(this);
			});
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("td[name='guid'] span").text();
				queryEntity(id);
			});

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/code/codeCatalog_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});

			initSelect("query.type","${query.type}");
			initSelect("query.source","${query.source}");
			operationList();//操作栏初始化
			fillRows("20", "", "", false);//填充空行
		});
	
	    function preDel(id){//删除前操作
			showConfirm("此操作将会删除该编目及所属条目，确定要删除吗？");
	
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				alertDivClose();
				delEntity(id);
			});
		}
		
		function delEntity(id){//删除
			$.post('<%=request.getContextPath()%>/code/codeCatalog_delete.html',"guid="+id,function(data){
				var callback = function(){
					window.location.reload();
				};
				processDataCall(data, callback);
			},"json");
		}
		
		function queryEntity(id){//查询
			showWindow("修改","<%=request.getContextPath()%>/code/codeCatalog_query.html?guid="+id,720,270);
			//tipsWindown("修改","url:post?<%=request.getContextPath()%>/code/codeCatalog_query.html?guid="+id,"720","270","true","","true","id");
		}

		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='guid'] span").text();
				queryEntity(id);
			});
			
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").find("td[name='guid'] span").text();
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
				var source = $(tr).find("td[name='source'] input").val();
				$(this).attr("title","编目类型来源于数据中心时，删除将操作被禁止");
				if(source=='0'){
					$(this).unbind("click");
					$(this).css("cursor","default");
					$(this).parent().prev().find("a").removeClass().addClass("last1");
					$(this).parent().hide();
				}
			});
		}

		function initSelect(name,value){
			$("select[name='"+name+"'] > option").each(function(){
				if($(this).val()==value){
					$(this).attr("selected","selected");
				}
			});
		}

		function initRadio(name,value){
			$("input[type='radio'][name='"+name+"']").each(function(){
				if($(this).val()==value){
					$(this).attr("checked","checked");
				}
			});
		}
		
		/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#search").submit();
		}
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li><a id="btn_zj" class="btn_zj">增 加</a></li>
						<%--<li>
							<a id="btn_dr" class="btn_dr" onclick="">导 入</a>
						</li>
						<li>
							<a id="btn_dc" class="btn_dc" onclick="">导 出</a>
						</li>
						<li>
							<a id="btn_xzmb" class="btn_down" onclick="">模板下载</a>
						</li>
					--%></ul>
			
				</div>
			<!-- 按钮 -->
			<!-- 过滤条件开始 -->
			<!-- 过滤条件结束 -->
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<form id="search" method="post" action="<%=request.getContextPath()%>/code/codeCatalog_list.html">
<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
<input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchbox">
    <p class="search_con">
	    <label>编目类型</label>
		<select name="query.type">
			<option value="">请选择</option>
			<option value="0">国标</option>
			<option value="1">校标</option>
			<option value="2">系统</option>
		</select>
	                <label>代码来源</label>
		<select name="query.source">
			<option value="">请选择</option>
			<option value="0">数据中心</option>
			<option value="1">自己新增</option>
		</select>
		<label>编目名称或编号</label><input type="text" class="text_nor" name="query.name" value="${query.name }"/>
		<button class="btn_cx" name="search" >查 询</button>
	</p>
	<p class="search_title">
		<em></em>
	</p>
    
</div>
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
    	<span>编目管理<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以修改）</font></span>
    </h3>
<!--标题end-->
		<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
						<td>序号</td>
						<c:if test="${'BMMC' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="BMMC">编目名称</td>
						</c:if>
						<c:if test="${'BMMC' != sortFieldName}">
							<td class="sort_title" id="BMMC">编目名称</td>
						</c:if>
						<c:if test="${'BMID' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="BMID">编目编号</td>
						</c:if>
						<c:if test="${'BMID' != sortFieldName}">
							<td class="sort_title" id="BMID">编目编号</td>
						</c:if>
						<c:if test="${'DMLX' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="DMLX">编目类型</td>
						</c:if>
						<c:if test="${'DMLX' != sortFieldName}">
							<td class="sort_title" id="DMLX">编目类型</td>
						</c:if>
						<c:if test="${'DMSJLY' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="DMSJLY">代码来源</td>
						</c:if>
						<c:if test="${'DMSJLY' != sortFieldName}">
							<td class="sort_title" id="DMSJLY">代码来源</td>
						</c:if>
						<td>操　作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${pageList}" var="catalog" varStatus="st">
						<tr name="tr">
						<td>${st.index+1 }</td>
						<td name="name">${catalog.name }</td>
						<td name="guid"><span>${catalog.guid }</span></td>
						<td name="type">
							<c:if test="${catalog.type==0}">国标</c:if>
							<c:if test="${catalog.type==1}">校标</c:if>
							<c:if test="${catalog.type==2}">系统</c:if>
							<input type="hidden" value="${catalog.type }">
						</td>
						<td name="source"><c:if test="${catalog.source==0}">数据中心</c:if><c:if test="${catalog.source==1}">自己新增</c:if><input type="hidden" value="${catalog.source }"></td>
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
  	<ct:page pageList="${pageList }" />
  </div>
  </form>
  </body>
</html>
