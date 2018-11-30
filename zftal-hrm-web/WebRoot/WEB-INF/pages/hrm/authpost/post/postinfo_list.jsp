<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		<script type="text/javascript">
			var _tr;
			var _uptr;
			var _downtr;
			var current = null;
			$(function(){
				$("input[name='model.typeCode']").next().change(function(){
					var type=$(this).prev().val();
					location.href="<%=request.getContextPath()%>/post/postinfo_list.html?typeCode="+type;
				});
				$("tr").click(function(){
					
					if(current != null) {
						current.removeClass("current");
					}
	 
					$(this).attr("class", "current");
	 
					current = $(this);
				});
				
				$("tr").dblclick(function(){
					
					if(current != null) {
						current.removeClass("current");
					}
	 
					$(this).attr("class", "current");
	 
					current = $(this);
					
					var id = $(this).find("td[name='id']").html();
					var type=$("input[name='model.typeCode']").val();
					showWindow("修改","<%=request.getContextPath()%>/post/postinfo_modify.html?id="+id+"&typeCode="+type,480, 200);
				});
				
				$("#btn_zj").click(function(){
					var type=$("input[name='model.typeCode']").val();
					showWindow("增加","<%=request.getContextPath()%>/post/postinfo_add.html?typeCode="+type, 480, 200);
				});
				$("#btn_sy").click(function(){
					if($(current)==null)return false;
					var id = $(current).find("td[name='id']").html();
					_tr = $(current);
					_uptr=$(_tr).prev();
					if(_uptr==null)return false;
					$.post('<%=request.getContextPath()%>/post/postinfo_up.html',"id="+id,function(data){
						if( data.success ) {
							$(_uptr).before($(_tr));
						}
					},"json");
					
				});
				$("#btn_xy").click(function(){
					if($(current)==null)return false;
					var id = $(current).find("td[name='id']").html();
					_tr = $(current);
					_downtr=$(_tr).next();
					if(_downtr==null){
						return false;
					}else{
						var _index=$(_downtr).find("td:first").html();
						if(_index=="&nbsp;"){
							return false;
						}
					}
						
					$.post('<%=request.getContextPath()%>/post/postinfo_down.html',"id="+id,function(data){
						if( data.success ) {
							$(_downtr).after($(_tr));
						}
					},"json");
				});
				$(".btn_cx").click(function(){
					var type=$("input[name='model.typeCode']").val();
					location.href="<%=request.getContextPath()%>/post/postinfo_list.html?typeCode="+type;
				});
				operationList();
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
				$.post('<%=request.getContextPath()%>/post/postinfo_delete.html',"id="+id,function(data){
					var callback = function(){
						$("form:last").submit();
					};
					processDataCall(data,callback);
				},"json");
			}
			function operationList(){
				$("a[name='modify']").click(function(){//行数据修改链接
					var id = $(this).closest("tr").find("td[name='id']").html();
					_tr = $(this).closest("tr");
					var type=$("input[name='model.typeCode']").val();
					showWindow("修改","<%=request.getContextPath()%>/post/postinfo_modify.html?id="+id+"&typeCode="+type,480, 200);
				});
				$("a[name='modify']").each(function(){
					$(this).attr("title","双击数据行可以进入修改");
				});
				$("a[name='del']").click(function(){//行数据修改链接
					var id = $(this).closest("tr").find("td[name='id']").html();
					_tr = $(this).closest("tr");
					preDel(id);
				});
				$(".select_tools a").css("cursor","pointer");
				operationHover();
				
			}
		</script>
	</head>

	<body>
	<form>
	<div class="formbox">
		<div class="toolbox">
			<div class="buttonbox">
				<ul>
					<li>
						<a id="btn_zj" class="btn_zj">增 加</a>
						<a id="btn_sy" class="btn_sy">上移</a>
						<a id="btn_xy" class="btn_xy">下移</a>
					</li>
				</ul>
			</div>	
		</div>
		<div class="searchtab">
			<table  width="100%" border="0">
				<tbody>
					<tr>
						<th>
						  所属类别
						</th>
						<td>
							 <ct:codePicker name="model.typeCode" catalog="<%=ICodeConstants.DM_DEF_WORKPOST %>" code="${model.typeCode }" />
						</td>
					</tr>
				</tbody>
				<tfoot>
			        <tr>
			          <td colspan="6">
			            <div class="btn">
			              <button class="btn_cx" name="search" type="button">查 询</button>
			            </div></td>
			        </tr>
			      </tfoot>
			</table>
		</div>
		
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<td>岗位编码</td>
					<td>所属类别</td>
					<td>岗位名称</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${authInfoList}" var="bean" varStatus="i">
				<tr>
					<td>${i.index+1 }</td>
					<td name="id">${bean.id}</td>
					<td>${bean.typeName}</td>
					<td>${bean.name}</td>
					<td>
						 <div>
					      	<div class="current_item">
					        	<span class="item_text">修改</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					                <li><a name="modify" class="tools_list">修改</a></li>
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