<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="view" uri="/WEB-INF/code.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>

	<script type="text/javascript">
		$(function(){
			var current = null;

			//选择行监听
			$("#list_body > tr").click( function(){
				var classId = $(this).find("input[name='classId']").val();

				if( classId == null ) {
					return;
				}

				if( current != null ) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");
				current = $(this);
			});

			//双击行监听
			$("#list_body > tr").dblclick( function(){
				var classId = $(this).find("input[name='classId']").val();

				if( classId == null ) {
					return;
				}
				
				modify( classId );
			});

			//操作菜单监听
			$(".current_item").hover(function(){
				$(this).next("div.select_tools").show();
				$(this).parent().css("position","relative")
			},function(){
				$(this).next("div.select_tools").show().hide();
				$(this).parent().css("position","")
			})
			
			$(".select_tools").hover(function(){
				$(this).show();
				$(this).parent().css("position","relative")
			},function(){
				$(this).hide();
				$(this).parent().css("position","")
			})

			//复选框选择监听-修改信息类
			$("input[name='checkItem']").click( function(){
				var classid = $(this).siblings("input[name='classId']").val();
				var open = false;

				if( $(this).attr("checked") ) {
					open = true;
				}

				$.post("<%=request.getContextPath() %>/baseinfo/formInfoMember_open.html?batch=0", "name=${name}&classId=" + classid + "&open=" + open, function(data){});
			});

			//修改监听
			$("a[name='modify']").click( function() {
				var classid = $(this).closest("tr").find("input[name='classId']").val();
				modify( classid );
			});

			//属性维护监听
			$("a[name='property']").click( function(){
				var classid = $(this).closest("tr").find("input[name='classId']").val();
				property( classid );
			});

			//上移监听
			$(".btn_sy").click( function(){
				if( current == null ) {
					return showWarning("请选需要移动记录！");
				}

				var prev = current.prev();
				var prevClassId = prev.find("input[name='classId']").val();

				if( prevClassId == null ) {
					return showWarning("已经在第一行，无法上移！");
				}

				swap( prev, current );
			});

			//下移监听
			$(".btn_xy").click( function(){
				if( current == null ) {
					return showWarning("请选需要移动记录！");
				}

				var next = current.next();
				var nextClassId = next.find("input[name='classId']").val();

				if( nextClassId == null ) {
					return showWarning("已经在最后一行，无法下移！");
				}

				swap( current, next);
			});
		});

		//属性维护
		function property( classid ) {
			$("form:first").attr("action", "<%=request.getContextPath() %>/baseinfo/formInfoMemberProperty_list.html?batch=0");
			$("form > input[name='member.classId']").val(classid);

			$("form:first").submit();
		}
		
		//修改
		function modify( classid ) {
			
			showWindow("修改","<%=request.getContextPath() %>/baseinfo/formInfoMember_edit.html?batch=0&name=${name}&classId="+classid, 480, 180);
		}

		//行移动
		function swap( prev, next ) {

			var classId1 = prev.find("input[name='classId']").val();
			var classId2 = next.find("input[name='classId']").val();

			var params = "name=${name}&classIds=" + classId1 + "&classIds=" + classId2;

			$.post('<%=request.getContextPath() %>/baseinfo/formInfoMember_swap.html?batch=0', params, function(data){
				if( data.success ) {
					prev.before(next);
				}
			}, "json")
		}
	</script>
</head>

<body>
	<form action="<%=request.getContextPath() %>/baseinfo/formInfoMember_list.html" method="post">
		<input type="hidden" name="name" value="${name }" />
		<input type="hidden" name="member.name" value="${name }" />
		<input type="hidden" name="member.classId" value="" />
		<input type="hidden" name="member.batch" value="0" />
	</form>
	
	<div class="toolbox" style="z-index: 10;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_sy" href="#" class="btn_sy">上移</a></li>
				<li><a id="btn_xy" href="#" class="btn_xy">下移</a></li>
			</ul>
			<a href="#" class="btn_fh_rs" onclick="window.location.href='../xtgl/jsgl_cxJsxx.html';return false;">返回</a>
		</div>
	</div>
	<div class="formbox">
		<h3 class="datetitle_01">
			<span>成员列表<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以修改）</font></span>
		</h3>
		
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<th width="40px">序号</th>
					<th>标题</th>
					<td>信息类标识</td>
					<th>注释</th>
					
					<!-- <th>提示信息</th>
					<th>授权编辑</th> -->
					<th width="100px">操作</th>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${members}" var="member" varStatus="i">
				<tr>
					<td width="40px">
						<!-- <input type="checkbox" name="checkItem" <c:if test="${member.open}">checked="checked"</c:if> /> -->
						${i.index +1 } 
						<input type="hidden" name="classId" value="${member.classId }"/>
					</td>
					<td>${member.title }</td>
					<td>${member.infoClass.identityName }</td>
					<td>${member.comment }</td>
					<!-- <td>${member.tooltip }</td>
					<td><view:SingleParser value="${member.editable }" /></td> -->
					
					<td>
						<div>
				      		<div class="current_item">
				        		<span class="item_text">属性维护</span>
				        	</div>
				        	<div class="select_tools" id="select_tools1" style=" width:80px; display:none">
				           		<ul>
				           			<li><a name="property" href="#" class="first1">属性维护</a></li>
				           			<!-- <li><a name="modify" href="#" class="last1">修改</a></li> -->
				           		</ul>
				           	</div>
				        </div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<script type="text/javascript">
		fillRows(20, '', '', true);
	</script>
</body>
</html>