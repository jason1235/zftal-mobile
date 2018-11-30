<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ct" uri="/custom-code"%>

<style>
	.tableheight .liheight{
		height:28px;line-height:28px
	}
.tableheight .marginlength{
	margin-left:18px
}
	
	.tableheight ul li a {
    	color: #ccff00;
}	
</style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript">
	
	function staffInfoShow(){
		$('#usual_N110101').click();
	}

	</script>
	<div class="col-md-12">
		<div class="index_grxx">		
			<div class="col-md-5">
				<div class="media" id="staffinfo">
					
				</div>
			</div>
		</div>
	</div>
	<%--
	<div class="col-md-6">
					<div class="index_rctx">
						<h5 class="index_title"><span class="title">最新通知</span><a href="#" id="usual_N702020"></a></h5>
						<div class="list-group" id="notice"></div>
					</div>
	</div>
	
	<div class="col-md-6">
					<div class="index_rctx">
						<h5 class="index_title"><span class="title">待办事宜</span><a href="#" id="usual_N703010"></a></h5>
						<div class="list-group" id="todo"></div>
					</div>
	</div>	
	
	<div class="col-md-6">
					<div class="index_rctx">
						<h5 class="index_title"><span class="title">文件下载</span></h5>
						<div class="list-group" id="download"></div>
					</div>
	</div>	
		 --%>
		
		 
<!--		<div class="newsnotice" style="margin-top:5px;" id="todo">-->
<!--			<h3 class="head_03"><span>待办事宜</span><a href="#" id="usual_N703010"></a></h3>-->
<!--			<div class="newscon01">有图新闻此处class命名改为：newscon-->
<!--				<ul>-->
<!--				</ul>-->
<!--			</div>-->
<!--		</div>-->
		
		<!--
		<div class="remindtoday" style="margin-top:5px;" id="download">
			<h3><span>文件下载</span><a href="#" id="usual_N704020"></a></h3>
			<ul>
				</ul>
		</div>
		 -->
		<jsp:include page="pendingtask.jsp"></jsp:include>
