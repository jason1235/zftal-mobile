<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>

<meta charset="utf-8">

<title>
</title>

<script type="text/javascript" src="<%=stylePath %>js/jquery-1.11.0.min.js"></script>

<link rel="stylesheet" href="<%=stylePath %>css/bootstrap.min.css" type="text/css" media="all" />

<script type="text/javascript" src="<%=stylePath %>js/modal.js"></script>

<script type="text/javascript" src="<%=stylePath %>js/bootstrap.min.js"></script>

<!--[if lt IE 9]> 
	<script src="<%=stylePath %>/js/html5shiv.min.js"></script>
	<script src="<%=stylePath %>/js/respond.min.js"></script> 
<![endif]-->
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>

	<link rel="stylesheet" href="<%=stylePath %>css/rebase.css">

	<script type="text/javascript">
		$(function(){
			$(".nav-slidedown li").click(function(){
				$(".nav-slidedown").find("li").removeClass("nav-slidedownclick");
				$(this).addClass("nav-slidedownclick");
			})
			$(".nav-a").click(function(){
				var has = $(this).find(".panel-title").hasClass("active");
				$(".panel-title").removeClass("active");
				if(!has){
					$(this).find(".panel-title").addClass("active");
				}
			})
			$(".tocontent").click(function(){
				var contentHtml = $(this).closest("li").find("#content").html();
				$("#title").html($(this).html());
				$("#nr").empty();
				$("#nr").append(contentHtml);
			})
		})
		
		

	</script>

</head>



<body>



<!-- top -->

<%--<div class="navbar navbar-default navbar-static-top top1">

	<div class="container_1170">

		<div class="navbar-header">
			<a class="navbar-brand logo_2" href="#"><img src="<%=request.getContextPath()%>/img/logo.png" class="margin-r10"/>移动校园平台开发者文档</a>

		</div>

	</div>

</div>

--%><!-- top-end -->









<div class="container_1170 index_main padding_t40">

<div class="col-sm-3 table-bordered" style="padding:0; border-right: none;">
	<div id="accordion" role="tablist" aria-multiselectable="true">
	
	<c:forEach var="apiMenu" items="${listOne}" varStatus="so">
           <div class="panel" style="margin-bottom:0; border:none;">
	<c:if test = "${apiMenu.fjgndm eq 'N'}">
					<a class="nav-a" data-toggle="collapse" data-parent="#accordion" href="#${so.index }"  aria-controls="collapseFour" id="headingFour"> 
			<div class="nav-title" role="tab" >
				<h4 class="panel-title">
					${apiMenu.gnmkmc}
					
				</h4>
			</div>
					</a>
			<div id="${so.index }" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour" aria-expanded="true">

				<ul class="nav-slidedown ">
				<c:forEach var="apiMenuTwo" items="${listTwo}" varStatus="st">
					<c:if test = "${apiMenuTwo.fjgndm eq apiMenu.gnmkdm}">
						<li>
							<a href="#" class="tocontent">${apiMenuTwo.gnmkmc}</a>
							<div style="display: none;" id="content">${apiMenuTwo.content }</div>
						</li>
					</c:if>
				</c:forEach>
					
				</ul>

			</div>
	
	</c:if>
		</div>                         
   	</c:forEach>


		


	</div>



</div>

<div class="col-sm-9 table-bordered">
		<div class="panel-body" id="zw">
			  <h3><img src="<%=request.getContextPath()%>/img/ico-h3.png"><span id="title">移动校园平台开发者文档</span></h3>
			  <div id="nr" style=" min-height:1000px;">
				  <p style="margin-left:10px;color:#333333;font-family:'Microsoft Yahei', 宋体, Tahoma, Arial;font-size:14px;background-color:#FFFFFF;">
	开发者进行接入开发时，除了需要满足每个接口的规范限制外，还需特别注意用户数据等敏感信息的使用规范。
</p><br>
<p style="margin-left:10px;color:#333333;font-family:'Microsoft Yahei', 宋体, Tahoma, Arial;font-size:14px;background-color:#FFFFFF;">
	<b>涉及用户数据时：</b>
</p><br>
<ul style="color:#333333;font-family:'Microsoft Yahei', 宋体, Tahoma, Arial;font-size:14px;background-color:#FFFFFF;">
	<li>
		您的服务需要收集用户任何数据的，必须事先获得用户的明确同意， 同时应当告知用户相关数据收集的目的、范围及使用方式等，保障用户知情权。
	</li>
	<li>
		您收集用户的数据后，必须采取必要的保护措施，防止用户数据被盗、泄漏等。
	</li>
	<li>
		您在用户数据使用时，不得将其使用在其他不经过用户同意的条件下进行任何目的进行使用，也不得以任何方式将其提供给他人。
	</li>
	<li>
		如果我们认为您使用用户数据的方式，可能损害用户权益，有权要求您删除相关数据并不得再以该方式收集、使用用户数据。
	</li>
	<li>
		一旦您停止使用本服务，或我们基于任何原因终止您使用本服务，您必须立即删除全部因使用本服务而获得的数据（包括各种备份）， 且不得再以任何方式进行使用。
	</li>
</ul><br>
<p style="margin-left:10px;color:#333333;font-family:'Microsoft Yahei', 宋体, Tahoma, Arial;font-size:14px;background-color:#FFFFFF;">
	<b>其他规范：</b>
</p><br>
<ul style="color:#333333;font-family:'Microsoft Yahei', 宋体, Tahoma, Arial;font-size:14px;background-color:#FFFFFF;">
	<li>
		请勿为任何用户自动登录到平台提供代理身份验证凭据。
	</li>
	<li>
		请勿提供跟踪功能，包括但不限于识别其他用户在个人主页上查看、点击等操作行为。
	</li>
	<li>
		请勿自动将浏览器窗口定向到其他网页。
	</li>
	<li>
		请勿设置或发布任何违反相关法规、公序良俗、社会公德等的玩法、内容等。
	</li>
	<li>
		请勿公开表达或暗示，您与平台之间存在合作关系，包括但不限于相互持股、商业往来或合作关系等。
	</li>
</ul><br>
<p style="margin-left:10px;color:#333333;font-family:'Microsoft Yahei', 宋体, Tahoma, Arial;font-size:14px;background-color:#FFFFFF;">
	完整的开发者规范和接口限制，请详见开发者接口文档，以及平台开发者协议。
</p>
       				


			  </div>
		</div>
</div>

</div>



<!-- footer -->

<%--<div class="footer">

	<p>版权所有© Copyright 1999-2014 正方软件股份有限公司　　中国·杭州文二西路1号 元茂大厦8F</p>

</div>

--%><!-- footer-end -->


<script type="text/javascript">
	$(function () {
		
	});
</script>



</body>

</html>
