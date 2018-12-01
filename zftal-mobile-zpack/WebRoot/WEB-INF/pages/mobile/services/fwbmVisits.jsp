<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/echarts.min.js"></script>
    <link type="text/css" href="<%=systemPath %>/js/My97DatePicker/skin/WdatePicker.css"/>
	<script type="text/javascript" src="<%=systemPath %>/js/My97DatePicker/WdatePicker.js" defer="defer"></script>
     <script type="text/javascript">
   	 $(function(){
   	 	$("#search_go").click(function(){
  	 		  var fwbmKey = $("#fwcode").val();
		      var czkssj = $("#czkssj").val();
			  var czjssj = $("#czjssj").val();
   	 		if(czkssj > czjssj){
		   	 		errorAlert("开始日期不能大于结束日期");
		   	 		return false;
		   	 }
		   	 if(czjssj - czkssj > 30){
		   	 		errorAlert("日期相差不能超过一个月");
		   	 		return false;
		   	 }
			  url = "<%=request.getContextPath()%>/serviceManager/serviceManager_getVisitsByFwbm.html?startdate=" 
		        + czkssj + "&enddate=" + czjssj + "&fwbm=" + fwbmKey;
			  window.location.href = url;
   	 	});
   	 
   	 });
   	 
    
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
  <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
 		  <div class="form-horizontal sl_all_form">
            <div class="row">
            	<div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">服务编码:</label>
		                <div class="col-xs-7">
	                        <select name="fwbm" class="form-control" id="fwcode" onchange="searchChange();">
	                        	<c:forEach var="fwbmunit" items="${fwbmList}">
	                        	 <option value="${fwbmunit.key }" <c:if test="${fwbmunit.key == queryFwbm }"> selected="selected" </c:if>>${fwbmunit.key }&nbsp;&nbsp;${fwbmunit.text }</option>
	                        	</c:forEach>
	                        </select></div>
		            	</div>
		        </div>
            	<div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">操作日期:</label>
		                <div class="col-xs-7">
	                        <input class="form-control" id="czkssj" type="text" name="czkssj" value="${startdate }" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d'})"/> 
						</div>
		            </div>
		        </div>
		        <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-1 control-label">至</label>
                        <div class="col-xs-7">
                            <input class="form-control" id="czjssj" type="text" name="czjssj" value="${enddate }" onchange="searchChange();" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
                        </div>
                    </div>
                    
                </div>
               <div class="col-xs-2">
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
                    </div>
                </div>
               
                
            </div>
          </div>
  	
  		<div id="main" style="width: 1000px;height:700px;"></div>
  
  </div>
  <script type="text/javascript">
    $(function(){
     var actualjson = ${actualjson};
     var cumulatejson = ${cumulatejson};
     var dayJson = ${dayJson};
     
     var myChart = echarts.init(document.getElementById('main'));

    	// 指定图表的配置项和数据
   	 var option = {
        title : {
            text: 'app访问量统计分析',

        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['累计访问量','当日访问量']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                magicType : {show: true, type: ['line', 'bar']},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : dayJson
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} '
                },
                name : '访问量(单位:次数)'
            }
        ],
        series : [
            {
                name:'累计访问量',
                type:'line',
                data:cumulatejson,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },

            },
            {
                name:'当日访问量',
                type:'line',
                data:actualjson,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
                }
            }
        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    
   	 
    });
    
    
    
    </script>
	</body>
</html>