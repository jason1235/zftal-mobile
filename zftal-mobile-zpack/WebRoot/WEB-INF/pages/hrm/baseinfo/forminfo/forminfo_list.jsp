<%@page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<%@include file="/commons/hrm/head.ini"%>

		<script type="text/javascript">
			$(function(){
				var fixedWin = true;
				$(".mes_add2").click(function(){
					showWindow("增加","<%=request.getContextPath()%>/baseinfo/forminfo_edit.html", 480, 120);
				});
				//信息类选择
				$(".mes_list_con ul li").click(function(){
					var name = $(this).attr("name");
					currentF(name);
				});
				//信息类监听
				$(".mes_list_con li").hover(function(){
					//return;
					if( $(this).attr("name") == null ) return;
					var $mes_list_tools=$("#mes_list_tools").html();
		
					$(this).append($mes_list_tools).css("position","relative").children(".mes_list_tools").css("display","block");
		
					//信息类修改
					$(".ico_edit_mes").click(function(){
						var guid = $(this).closest("li").attr("name");
						showWindow("修改","<%=request.getContextPath()%>/baseinfo/forminfo_edit.html?guid="+guid, 480, 120);
					});
		
					//信息类删除
					$(".ico_delete_mes").click(function(){
						var guid = $(this).closest("li").attr("name");
		
						showConfirm("确定要删除吗？");
		
						$("#why_cancel").click(function(){
							divClose();
						});
		
						$("#why_sure").click(function(){
							$.post('<%=request.getContextPath()%>/baseinfo/forminfo_remove.html', 'guid='+guid, function(data){
								var callback = function(){
									$("form:first").submit();
									window.location.reload();
								};
								processDataCall(data,callback);
							},"json");
						});
						return false;
					});
				},function(){
					//return;
					$(this).remove(".mes_list_tools").css("position","").children(".mes_list_tools").css("display","none");
				});
				
				$(".mes_list_con ul li").eq(0).click();
				
			});
			var current = null;
			
			function currentF(name){
				if( name == null || name == "" || name == current )
				{
					return false;
				}
				
				$("li[name='" + current + "']").removeClass("current");
				current = name;
				$("input[name='guid']").val(name);
				
				$("li[name='" + name + "']").attr("class", "current");
				
				var url = "<%=request.getContextPath()%>/baseinfo/forminfo_class_list.html?guid=" + name;
				var iframe = document.getElementById("property_con"); 
				window.frames["property_con"].location.href = url;
			}
		</script>
	</head>

	<body>
		<div id="mes_list_tools">
			<div class="mes_list_tools" style="display: none;">
				<span class="ico_edit_mes"></span>
				<span class="ico_delete_mes"></span>
			</div>
		</div>
		<div class="mes_list" id="message_list2" style="height:30px">
			<div class="mes_list_con" style="height:30px">
				<ul>
					<c:forEach items="${list}" var="bean">
					<li name="${bean.guid }">
						<a href="#">${bean.name }</a>
					</li>
					</c:forEach>
					<li class="mes_add"><a id="btn_zjxxl" href="#"><span class="mes_add2" title="增加登记表">+ 添加</span></a></li>
					<div class="clear"></div>
				</ul>
			</div>
		</div>
		<iframe name="property_con" id="frame_content"
			allowTransparency="true" src="#" width="100%" height="800px" frameborder="0"
			marginwidth="0" marginheight="0" ></iframe>
	</script>

	</body>
</html>