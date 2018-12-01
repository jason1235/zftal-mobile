<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="view" uri="/WEB-INF/code.tld" %>
<%@taglib prefix="class" uri="/WEB-INF/infoclasstag.tld" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>

	<script type="text/javascript">
		$(function(){
			var current = null;

			//选择行监听
			$(".createdPro").click( function(){
				var pName = $(this).find("input[name='pName']").val();

				if( pName == null ) {
					return;
				}

				if( current != null ) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");
				current = $(this);
			});

			//双击行监听
			$(".createdPro").dblclick( function(){
				var pName = $(this).find("input[name='pName']").val();

				if( pName == null ) {
					return;
				}
				
				modify( pName );
			});

			//操作菜单监听
			$(".current_item").hover(function(){
				$(this).next("div.select_tools").show();
				$(this).parent().css("position","relative")
			},function(){
				$(this).next("div.select_tools").show().hide();
				$(this).parent().css("position","")
			});
			
			$(".select_tools").hover(function(){
				$(this).show();
				$(this).parent().css("position","relative")
			},function(){
				$(this).hide();
				$(this).parent().css("position","")
			});

			//复选框选择监听-修改信息类
			$("#btn_bc").click( function(){
				saveProList();
			});

			//修改监听
			$("a[name='modify']").click( function() {
				var pName = $(this).closest("tr").find("input[name='pName']").val();
				modify( pName );
			});

			//返回监听
			$(".btn_fh_rs").click( function(){
				black();
			});

			//上移监听
			$(".btn_sy").click( function(){
				if( current == null ) {
					return showWarning("请选需要移动记录！");
				}

				var prev = current.prev();
				var prevpName = prev.find("input[name='pName']").val();

				if( prevpName == null ) {
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
				var nextpName = next.find("input[name='pName']").val();

				if( nextpName == null ) {
					return showWarning("已经在最后一行，无法下移！");
				}

				swap( current, next);
			});
		});

		//返回操作
		function black() {
			window.location.href='<%=request.getContextPath() %>/baseinfo/formInfoMember_list.html?name=${member.name}';
		}

		//修改操作
		function modify( pName ) {
			var params = "member.name=${member.name}&member.classId=${member.classId}&pname=" + pName;
			showWindow("修改","<%=request.getContextPath() %>/baseinfo/formInfoMemberProperty_edit.html?" + params, 720, 260);
		}

		//属性是否显示
		function viewable( pName, viewable ) {
			$("form > input[name='pname']").val( pName );
			var params = $("form").serialize() + "&viewable=" + viewable;
			$.post("<%=request.getContextPath() %>/baseinfo/formInfoMemberProperty_view.html", params, function(data){});
		}

		//保存勾选
        function saveProList() {
            var params = $("form").serialize() + "&" + $(".check_pname").serialize();
            $.post("<%=request.getContextPath() %>/baseinfo/formInfoMemberProperty_saveProList.html", params, function(data){
            	$("form").submit();
            });
        }

		//行移动
		function swap( prev, next ) {

			var pName1 = prev.find("input[name='pName']").val();
			var pName2 = next.find("input[name='pName']").val();

			var params = $("form").serialize() + "&pnames=" + pName1 + "&pnames=" + pName2;

			$.post('<%=request.getContextPath() %>/baseinfo/formInfoMemberProperty_swap.html', params, function(data){
				if( data.success ) {
					prev.before(next);
				}
			}, "json")
		}
	</script>
</head>

<body>
	
	<form action="<%=request.getContextPath() %>/baseinfo/formInfoMemberProperty_list.html" method="post">
		<input type="hidden" name="name" value="${member.name }" />
		<input type="hidden" name="member.name" value="${member.name }" />
		<input type="hidden" name="member.classId" value="${member.classId }" />
		<input type="hidden" name="pname" value="" />
	</form>
	
	<div class="toolbox" style="z-index: 10;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_sy" href="#" class="btn_sy">上移</a></li>
				<li><a id="btn_xy" href="#" class="btn_xy">下移</a></li>
				<li><a id="btn_bc" href="#" class="btn_ccg">保存</a></li>
			</ul>
			<a href="#" class="btn_fh_rs">返 回</a>
		</div>
	</div>
	<div class="formbox">
		<h3 class="datetitle_01">
			<span>成员属性列表<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以修改）</font></span>
		</h3>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<th width="40px">配置</th>
					<th>属性名</th>
					<!-- <th>高亮显示</th> -->
					<th>显示</th>
					<th>编辑</th>
					<th>必填</th>
					<!-- <th>最小代码长度</th> -->
					<th>默认值</th>
					<!-- <th>警告信息</th> -->
					<th width="80px">操作</th>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${properties}" var="property">
				<c:if test="${property.pName != 'gh' && property.pName != 'globalid' && property.pName != 'lastModifyTime'}">
				<tr <c:if test="${property.created}">class="createdPro"</c:if>>
					<td width="40px">
						<input type="checkbox" class="check_pname" name="pnames" value="${property.pName }" <c:if test="${property.created}">checked="checked"</c:if> />
						<input type="hidden" name="pName" value="${property.pName }" />
					</td>
					<td><class:property name="${property.pName }" classId="${member.classId }"/></td>
					<!--<td><view:SingleParser value="${property.highlight }" /></td> -->
					<td><view:SingleParser value="${property.viewable }" falseText="<font class='red'>未授权</font>" trueText="授权" /></td>
					<td><view:SingleParser value="${property.editable }" falseText="<font class='red'>未授权</font>" trueText="授权" /></td>
					<td><view:SingleParser value="${property.need }" /></td>
					<!-- <td>${property.minLength }</td> -->
					<td>${property.defaultValueView }</td>
					<!-- <td>${property.alert }</td> -->
					<td width="80px">
						<div>
						  <c:if test="${property.created}">
				      		<div class="current_item">
				        		<span class="item_text">修改</span>
				        	</div>
				        	<div class="select_tools" id="select_tools1" style=" width:80px; display:none">
				           		<ul>
				           			<li><a name="modify" href="#" class="first1">修改</a></li>
				           		</ul>
				           	</div>
				           	</c:if>
				        </div>
					</td>
				</tr>
				</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script type="text/javascript">
		fillRows(20, '', '', true);
	</script>
</body>
</html>