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
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/echarts/echarts.js"></script>
        <script type="text/javascript">
                
			$(function(){ 
				//var chart = new FusionCharts("MSColumn3D","id",780,400);
                //chart.setDataXML("${xmlData}");
                //chart.render("report_view");
				//fillRows("20", "", "", false);//填充空行
				$("#cx_btn").click(function(e){
					$("#selectForm").attr("action","<%=request.getContextPath() %>/summary/report_list.html?id=${id}");
					$("#selectForm").submit();
					e.preventDefault();//阻止默认的按钮事件，防止多次请求
				});
				$("#dc_btn").click(function(e){
					$("#selectForm").attr("action","<%=request.getContextPath() %>/summary/report_exportData.html?id=${id}");
					$("#selectForm").submit();
					e.preventDefault();//阻止默认的按钮事件，防止多次请求
				});
				$("#list_body").find("a").click(function(){
					var rowcondition=encodeURIComponent($(this).next().val());
					var colcondition=encodeURIComponent($(this).next().next().val());
					var id = $(this).attr("id");
					var href="<%=request.getContextPath() %>/summary/report_list_rev.html?id="+id+"&rquery.rowCondition="
						+rowcondition+"&rquery.columnCondition="+colcondition+"&snapTime=${snapTime}";
					location.href=href;
					//showWindow("人员详情","<%=request.getContextPath()%>/summary/report_list_rev.html?id="+id+"&rquery.rowCondition="+rowcondition+"&rquery.columnCondition="+colcondition, 640, 330);
				});
			});

			require.config({
	            paths: {
	                echarts: '<%=request.getContextPath() %>/js/echarts/'
	            }
	        });
	        require(
	            [
	                'echarts',
	                'echarts/chart/line',   // 按需加载所需图表，如需动态类型切换功能，别忘了同时加载相应图表
	                'echarts/chart/bar',
	                'echarts/chart/pie'
	            ],
	            function (ec) {
	                var myChart = ec.init(document.getElementById('report1_view'));
	                option = ${echarts_obj};
	                myChart.setOption(option);
	            }
	        );
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
			<LI><A id="dc_btn" class="btn_shuc">导出报表</A> </LI>
			</UL>
			</DIV><!-- 按钮 --><!-- 过滤条件开始 --><!-- 过滤条件结束 -->
			<P class=toolbox_fot><EM></EM></P>
		</div>
		<form id="selectForm" method="post">
		
		<div class="searchtab">
			<table width="100%" border="0" class="formlist">
				<tbody>
					<tr>
						<th width="30%">快照日期</th>
						<td>
							<select name="snapTime">
								<option value="" <c:if test="${'' eq snapTime||null eq snapTime}"> selected='true' </c:if>>当前</option>
								<c:forEach items="${snapTimeList}" var="stime">
									<option value="${stime }" <c:if test="${stime eq snapTime}"> selected='true' </c:if>>${stime }</option>
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
		
		<div id="report_view" ></div>
		
		<div id="report1_view" style="height: 400px"></div>
		
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<c:forEach items="${reportView.titles }" var="title" varStatus="i">
						<td>
						${title.name }
						</td>
					</c:forEach>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${reportView.keySet}" var="row" >
				<tr>
					<td>
						${row.name }
		            </td>
					<c:forEach items="${reportView.itemValueMaps[row] }" var="col">
						<td>
							<c:if test="${col.value =='0' }">
								<span style="color: gray;">${col.value }</span>
							</c:if>
							<c:if test="${col.value !='0' }">
								<a href="#" id="${id }"><span style="color: blue;text-decoration: underline;">${col.value }</span></a>								
							</c:if>
							<input id="condition1" type="hidden" value="${row.condition }"/>
							<input id="condition2" type="hidden" value="${col.condition }"/>
						</td>
					</c:forEach>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="flashcontent"></div>
	</body>
</html>