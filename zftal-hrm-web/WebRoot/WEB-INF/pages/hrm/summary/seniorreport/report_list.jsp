<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="java.util.Date"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/FusionCharts/assets/prettify/prettify.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/FusionCharts/assets/ui/js/json2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/FusionCharts/FusionCharts.js"></script>
        <script type="text/javascript">
                
			$(function(){ 
                
				//fillRows("20", "", "", false);//填充空行
				$("#cx_btn").click(function(e){
					$("#selectForm").attr("action","<%=request.getContextPath() %>/summary/seniorreport_list.html?id=${id}");
					$("#selectForm").submit();
					e.preventDefault();//阻止默认的按钮事件，防止多次请求
				});
				$("#dc_btn").click(function(e){
					window.open("<%=request.getContextPath() %>/summary/seniorreport_exportData.html?id=${id}&"+$("#selectForm").serialize());
				});

				queryReportData();
			});


			function queryReportData(){
				$.post("<%=request.getContextPath() %>/summary/seniorreport_reportDetail.html?id=${id}",$("#selectForm").serialize(),function(data){
				    $("#list_head").html(data.Title);
				    $("#list_body").html(data.Body);
					$("#loading").hide();
				    $("#data").show();
				    $("#list_body").find("a").click(function(){
	                    var rowcondition=encodeURIComponent($(this).next().val());
	                    var colcondition=encodeURIComponent($(this).next().next().val());
	                    var id = $(this).attr("id");
	                    
	                    var href="<%=request.getContextPath() %>/summary/seniorreport_list_rev.html?id=${id}&rquery.rowCondition="
	                        +rowcondition+"&rquery.columnCondition="+colcondition;
	                    location.href=href;
	                    //showWindow("人员详情","<%=request.getContextPath()%>/summary/report_list_rev.html?id="+id+"&rquery.rowCondition="+rowcondition+"&rquery.columnCondition="+colcondition, 640, 330);
	                });
				})
			}
		</script>
	</head>

	<body>
	<div class="formbox">
		<%-- <div class="reporttitle">${reportView.reportTitle }报表</div> --%>
		<div class="toolbox">
			<!-- 按钮 -->
			<DIV class=buttonbox>
			<UL>
			<!-- <LI><A id="cx_btn" class="btn_cx" >查询</A> </LI>--> 
			<c:if test="${hasModel}">
			<LI><A id="dc_btn" class="btn_shuc">导出报表</A> </LI>
			</c:if>
			</UL>
			</DIV><!-- 按钮 --><!-- 过滤条件开始 --><!-- 过滤条件结束 -->
			<P class=toolbox_fot><EM></EM></P>
		</div>
		<form id="selectForm" method="post">
		
		<div class="searchtab">
			<table width="100%" border="0" class="formlist">
				<tbody>
					<tr>
						<th width="30%">存档日期</th>
						<td>
							<select name="snapTime">
								<option value="" <c:if test="${'' eq snapTime||null eq snapTime}"> selected='true' </c:if>>即时数据</option>
								<c:forEach items="${snapList}" var="snap">
									<option value="${snap.id }" <c:if test="${snap.id eq snapTime}"> selected='true' </c:if>>${snap.histroyTimeStr }_${snap.gh }</option>
								</c:forEach>
							</select>
							<div class="btn">
				              <button class="btn_cx" id="cx_btn" type="button">查 询</button>
				            </div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		</form>
		
		<div id="loading" class="page_loading" >
            <div class="load_con">
                <div class="pic"></div>
            </div>
          <p>数据正在加载中，请稍候。。。</p>
        </div>
        <div style="overflow-x:auto;overflow-y:hidden;">
		<table id="data" summary="" class="dateline" align="" style="width:789px; display: none;">
			<thead id="list_head">
			</thead>
			<tbody id="list_body">
			</tbody>
		</table>
		<div>
	</div>
	<div id="flashcontent"></div>
	</body>
</html>