<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
	$(function(){
		initDetailEvent();
	});
	
	function initDetailEvent(){
		$("span[name='count']").each(function(){
			if(parseInt($(this).text())>=0){
				$(this).closest("li").find("a").bind("mouseenter",function(){
					$("#detail").hide();
					$("#detail tbody").empty().append("<tr><td>数据请求中...</td></tr>");
					var index = parseInt($(this).siblings("div").find("span[name='index']").text());
					var set = $(this).offset();
					$("#detail").css("top",set.top+38);
					var width = $("div.demo_college").width();
					var eWidth = $(this).closest("li").width();
					var size = Math.floor(width/eWidth);
					if((index+1)%size<2){//右侧2个显示切换样式
						$("#detail").removeClass().addClass("explain_right01");
						//$("#detail").css("left",set.left+eWidth-$("#detail").width());
						$("#detail").css("left","auto");
						$("#detail").css("right",width-set.left-eWidth);
					}else{
						$("#detail").removeClass().addClass("explain_left01");
						$("#detail").css("left",set.left);
						$("#detail").css("right","auto");
					}
					var conIdTag = $(this).siblings("div").find("span[name='conId']");
					requestInfo(conIdTag);
				});
			}
		});
	}
	
	function requestInfo(idTag){
		var conditionId = idTag.text();
		var data = idTag.data("data");
		if(data != null){
			createDetail(data.result);
			return;
		}
		$.post(_path+'/baseinfo/commonSearch_subs.html',"conditionId="+conditionId,function(data){
			if(data.success){
				idTag.data("data",data);
				createDetail(data.result);
			}else{
				$("#detail tbody").empty().append("<tr><td>数据请求出错</td></tr>");
			}
		},"json");
	}
	
	function createDetail(list){
		$("#detail tbody").remove();
		var content = $("<tbody></tbody>");
		if(list.length==0){
			//$(content).append("<tr><td>暂无子数据</td></tr>");
			$("#detail table").append($(content));
			//$("#detail").hide();
		}else{
			var columnNumber = 2;
			var n = 0;//有效数据计数
			for(var i=0;i<list.length;i++){
				if(i%columnNumber==0){
					$(content).append("<tr></tr>");
				}
				var obj = list[i];
				$(content).find("tr:last").append("<td><a href='#' style='cursor:default;'><span style='color:#155FBE;'>"+obj.title+"</span></a></td><td>(总计："+obj.result.count+"人)</td>");
				if(obj.result.count>0){
					$(content).find("a:last").attr("href",_path + "/normal/overallInfo_list.html?conditionId="+obj.guid);
					$(content).find("a:last").css("cursor","pointer");
					//n++;
				}
			}
			//if(n==0){//处理子查询但是没有有效数据（大于0）的显示情况
			//	$(content).find("tr:last").append("<td>暂无子数据</td><td>&nbsp;</td>");
			//}
			var endRow = $(content).find("tr:last td").length;
			if(endRow/2<columnNumber){
				for(var n=0;n<columnNumber-endRow/2;n++){
					$(content).find("tr:last").append("<td>&nbsp;</td><td>&nbsp;</td>");
				}
			}
			$("#detail table").append($(content));
			$("#detail").slideDown("fast",function(){
				var max = 450;
				var divWidth = $("#detail table").width();
				if(divWidth>max){
					divWidth = max;
				}
				
				$("#detail").css("width",max);
				//$("#detail").animate({width:divWidth},"fast");
			});
		}
	}
	</script>
</head>
<body>
	<div class="prompt">
  <p>当前（在职）教工总人数：<font color="#f06600"><b>${model.data }</b></font>人</p>
  </div>
	<!---------------------展示 start------------------->
	<div class="demo_college">
	
		<c:forEach items="${list}" var="con">
		<h3 class="college_title">
			<span class="title_name" >
				<a href="#" style="text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;">${con.title }</a>
			</span>
			<span>总计：${con.result.count}人</span>
		</h3>
		<div class="con after">
	    	<ul>
	    	<c:forEach items="${con.children}" var="var2" varStatus="st2">
	    		<li>
	    		<c:if test="${var2.result.count > 0}">
	        	<a class="college_li" href="<%=request.getContextPath() %>/normal/overallInfo_list.html?conditionId=${var2.guid}">${var2.title }<span>(总计：${var2.result.count}人)</span></a>
	        	</c:if>
	        	<c:if test="${var2.result.count == 0}">
	        	<a class="college_li" href="#">${var2.title }<span>(总计：${var2.result.count}人)</span></a>
	        	</c:if>
	        	<div style="display:none;">
	        		<span name="count">${var2.result.count }</span>
	        		<span name="index">${st2.count }</span>
	        		<span name="conId">${var2.guid }</span>
	        	</div>
	        	</li>
	        </c:forEach>
			</ul>
		</div>
		</c:forEach>
	</div>
		
	<!---------------------展示 end------------------->
	<div class="explain_left01" style="display:none;postion:absolute;" id="detail">
       <div class="explain_con">
        <table width="100%" border="0" class="explain_tab" style="white-space:nowrap;">
		<tbody>
            <tr>
            <td name="value"></td>
            </tr>
		</tbody>
      </table>
	   </div>
	</div>
  </body>
</html>
