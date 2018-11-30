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
                
                //fillRows("20", "", "", false);//填充空行
                $("#cx_btn").click(function(e){
                    $("#selectForm").attr("action","<%=request.getContextPath() %>/summary/reportview_list.html?id=${id}");
                    $("#selectForm").submit();
                    e.preventDefault();//阻止默认的按钮事件，防止多次请求
                });
                //initTag();
                changeView("Column3D");
                $("#year").val("${num}");
                $("#cfield").val("${col.fieldName}");
                $("#rfield").val("${row.fieldName}");
            });

            function changeView(id){
            	var chart = new FusionCharts(id,new Date().getTime(),780,400);
                chart.setDataXML("${xmlData}");
                chart.render("report_view");
                return false;
            }
            function initTag(){
                $("#comp_title li:first").addClass("ha");
                $("#comp_title a").click(function(){
                    $("#comp_title li").removeClass("ha");
                    $(this).closest("li").addClass("ha");
                    var id = $(this).attr("id");
                    changeView(id);
                });
            }

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
                    var myChart = ec.init(document.getElementById('report_view1'));
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
            <!--<LI><A id="cx_btn" class="btn_cx" >查询</A> </LI>-->
            </UL>
            </DIV><!-- 按钮 --><!-- 过滤条件开始 --><!-- 过滤条件结束 -->
            <P class=toolbox_fot><EM></EM></P>
        </div>
        <form id="selectForm" method="post">
        
        <div class="searchtab">
            <table width="100%" border="0" class="formlist">
                <tbody>
                    <tr>
                        <th width="20%">时间范围</th>
                        <td>
                            <select id="year" name="num">
                                <option value="5">近5年</option>
                                <option value="10">近10年</option>
                            </select>
                        </td> 
                        <th>展示数据</th>
                        <td>
                            <select id="rfield" name="row.fieldName">
                                <c:forEach items="${reportContent.rows}" var="r">
                                    <option value="${r.fieldName }">${r.name }</option>
                                </c:forEach>
                            </select>
                            <select id="cfield" name="col.fieldName">
	                            <c:forEach items="${reportContent.columns}" var="c">
	                                <option value="${c.fieldName }">${c.name }</option>
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
        
        
        <div class="compTab">
            <!-- <div class="comp_title" id="comp_title">
              <ul style="width:90%">
                <li><a href="#" id="Column3D"><span>柱状图</span></a></li>
                <li><a href="#" id="Pie2D"><span>饼图</span></a></li>
                <li><a href="#" id="Line"><span>折线图</span></a></li>
              </ul>
            </div>
            <div id="report_view" ></div>-->
            <div id="report_view1" style="height: 400px"></div>
        </div>
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