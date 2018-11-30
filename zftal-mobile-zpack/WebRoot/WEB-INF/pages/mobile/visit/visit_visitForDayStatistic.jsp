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
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
 		 <ul id="mytab" class="nav nav-tabs sl_nav_tabs" role="tablist">
            <li class="active"><a id="appdaytop" href="#" data-toggle="tab">APP使用人次（按天）top10</a></li>
            <li><a id="appday" href="#" data-toggle="tab">APP使用人次（按天）</a></li>
			<li><a id="apphourtop" href="#" data-toggle="tab">在线人数（按时为单位）top10</a></li>
			<li><a id="apphour"href="#" data-toggle="tab">在线人数（按时为单位）</a></li>
         </ul>
         
         
  <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
 		  <div class="form-horizontal sl_all_form">
            <div class="row">
            
            	
            	<div class="col-xs-3" id="type2">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">选择时间:</label>
		                <div class="col-xs-7">
	                        <input class="form-control" type="text" name="chooesDate" value="${chooesDate }" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',skin:'whyGreen',maxDate:'%y-%M'})"/> 
						</div>
		            </div>
		        </div>
		        <div class="col-xs-3">
		            <div class="form-group" id="type4">
		                <label for="" class="col-xs-5 control-label">选择时间:</label>
		                <div class="col-xs-7">
	                        <input class="form-control" type="text" name="chooesHourDate" value="${chooesHourDate }" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d'})"/> 
						</div>
		            </div>
		        </div>
               <div class="col-xs-2" id="searchDiv">
                    <div class="form-group">
                        <button type="button" class="btn btn-primary btn-sm" id="search_go">查询</button>
                    </div>
                </div>
               
                
            </div>
          </div>
  	
	        <div class="row">
	            <div id="main" style="width: 80%;height:300px;margin-right: 20%;">
	            	
	            </div>
	        </div>


        <div class="row sl_aff_btn" style="padding: 0px 0; "></div>
  
  		
  </div>
  <script type="text/javascript">
    $(function(){
    	$("#type2").hide();
    	$("#type4").hide();
    	$("#searchDiv").hide();
    	var topUserVisitCountList = ${topUserVisitCountList};
    	var topVisitTimeList = ${topVisitTimeList};
    	var len = topVisitTimeList.length;
    	if(len > 0){
    		$("#main").empty();
	    	// 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('main'));
			
	        // 指定图表的配置项和数据
	        var option = {
	            title: {
	                text: '日累计访问量统计top10(数据来自app端访问)',
	                left: 20
	            },
	            tooltip: {
	                trigger: 'axis',
	                axisPointer: {
	                    type: 'shadow'
	                }
	            },
	            legend: {
	                data: [ '日累计访问量'],
	                right:20,
	                top:5
	            },
	            grid: {
	                left: '3%',
	                right: '4%',
	                bottom: '3%',
	                containLabel: true
	            },
	            xAxis: {
	                type: 'value',
	                boundaryGap: [0, 0.01]
	            },
	            yAxis: {
	            	name: '访问日期',
	                type: 'category',
	                data: topVisitTimeList
	            },
	            series: [
	                {
	                    name: '日累计访问量',
	                    type: 'bar',
	                    data: topUserVisitCountList,
	                    name : '日累计访问量(单位:累计访问数)'
	                }
	            ]
	        };

	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
    	}else{
    		$("#main").append("<div style='height:300px;width:100%;margin-top: 100px;padding-top: 100px;padding-left: 35%;'>"+
    		"<font color='red' size='5px'>没有可以显示的数据</font></div>");
    	}
    	
    	var type = 1;
    	$("#search_go").click(function(){
    		switch (type) {
			case 2:
				$("#appday").trigger("click");
				break;
			case 4:
				$("#apphour").trigger("click");
				break;

			default:
				break;
			}
    	})
    	
    	$("#appdaytop").click(function(){
    		$("#type2").hide();
        	$("#type4").hide();
        	$("#searchDiv").hide();
    		$("#main").empty();
    		type = 1;
    		$.post("<%=request.getContextPath()%>/visit/visit_visitStatistic.html",
                    "type="+type,function(data){
    			var topUserVisitCountList = data.topUserVisitCountList;
    	    	var topVisitTimeList = data.topVisitTimeList;
    	    	var len = topVisitTimeList.length;
    	    	if(len > 0){
    	    		$("#main").empty();
    		    	// 基于准备好的dom，初始化echarts实例
    		        var myChart = echarts.init(document.getElementById('main'));
    				
    		        // 指定图表的配置项和数据
    		        var option = {
    		            title: {
    		                text: '日累计访问量统计top10(数据来自app端访问)',
    		                left: 20
    		            },
    		            tooltip: {
    		                trigger: 'axis',
    		                axisPointer: {
    		                    type: 'shadow'
    		                }
    		            },
    		            legend: {
    		                data: [ '日累计访问量'],
    		                right:20,
    		                top:5
    		            },
    		            grid: {
    		                left: '3%',
    		                right: '4%',
    		                bottom: '3%',
    		                containLabel: true
    		            },
    		            xAxis: {
    		                type: 'value',
    		                boundaryGap: [0, 0.01]
    		            },
    		            yAxis: {
    		            	name: '访问日期',
    		                type: 'category',
    		                data: topVisitTimeList
    		            },
    		            series: [
    		                {
    		                    name: '日累计访问量',
    		                    type: 'bar',
    		                    data: topUserVisitCountList,
    		                    name : '日累计访问量(单位:累计访问数)'
    		                }
    		            ]
    		        };

    		        // 使用刚指定的配置项和数据显示图表。
    		        myChart.setOption(option);
    	    	}else{
    	    		$("#main").append("<div style='height:300px;width:100%;margin-top: 100px;padding-top: 100px;padding-left: 35%;'>"+
    	    		"<font color='red' size='5px'>没有可以显示的数据</font></div>");
    	    	}
    		 },"json");
    	})
    	
    	$("#apphourtop").click(function(){
    		$("#type2").hide();
        	$("#type4").hide();
        	$("#searchDiv").hide();
    		$("#main").empty();
    		type = 3;
    		$.post("<%=request.getContextPath()%>/visit/visit_visitStatistic.html",
                    "type="+type,function(data){
    			var topUserVisitHourCountList = data.topUserVisitHourCountList;
    	    	var topVisitTimeHourList = data.topVisitTimeHourList;
    	    	var len = topVisitTimeHourList.length;
    	    	if(len > 0){
    	    		$("#main").empty();
    		    	// 基于准备好的dom，初始化echarts实例
    		        var myChart = echarts.init(document.getElementById('main'));
    				
    		        // 指定图表的配置项和数据
    		        var option = {
    		            title: {
    		                text: '小时累计访问量统计top10(数据来自app端访问)',
    		                left: 20
    		            },
    		            tooltip: {
    		                trigger: 'axis',
    		                axisPointer: {
    		                    type: 'shadow'
    		                }
    		            },
    		            legend: {
    		                data: [ '小时累计访问量'],
    		                right:20,
    		                top:5
    		            },
    		            grid: {
    		                left: '3%',
    		                right: '4%',
    		                bottom: '3%',
    		                containLabel: true
    		            },
    		            xAxis: {
    		                type: 'value',
    		                boundaryGap: [0, 0.01]
    		            },
    		            yAxis: {
    		            	name: '访问小时',
    		                type: 'category',
    		                data: topVisitTimeHourList
    		            },
    		            series: [
    		                {
    		                    name: '小时累计访问量',
    		                    type: 'bar',
    		                    data: topUserVisitHourCountList,
    		                    name : '小时累计访问量(单位:累计访问数)'
    		                }
    		            ]
    		        };

    		        // 使用刚指定的配置项和数据显示图表。
    		        myChart.setOption(option);
    	    	}else{
    	    		$("#main").append("<div style='height:300px;width:100%;margin-top: 100px;padding-top: 100px;padding-left: 35%;'>"+
    	    		"<font color='red' size='5px'>您选择的时间内无数据显示,或没有数据</font></div>");
    	    	}
    		 },"json");
    	})
    	
    	$("#appday").click(function(){//第二个选项卡
    		$("#main").empty();
    		var chooesDate = $("input[name='chooesDate']").val();
    		type = 2;
    		$.post("<%=request.getContextPath()%>/visit/visit_visitStatistic.html",
                    "type="+type+"&chooesDate="+chooesDate,function(data){
    						if(data.success){
    							$("input[name='chooesDate']").val(data.chooesDate);
    							$("#type2").show();
    				    		$("#type4").hide();
    				    		$("#searchDiv").show();
    							var userVisitCountList = data.userVisitCountList;
    							var visitTimeList = data.visitTimeList;
    							len = visitTimeList.length;
    							if(len > 0){
    								// 基于准备好的dom，初始化echarts实例
        					        var myChart = echarts.init(document.getElementById('main'));
        					        
        					        option = {
        					        	    title : {
        					        	        text: 'APP使用人次（按天），默认显示当前月每天人访问量'
        					        	    },
        					        	    tooltip : {
        					        	        trigger: 'axis'
        					        	    },
        					        	    toolbox: {
        					        	        show : false
        					        	    },
        					        	    calculable : true,
        					        	    xAxis : [
        					        	        {
        					        	            type : 'category',
        					        	            boundaryGap : false,
        					        	            data : visitTimeList,
        					                        name : '日期(单位:日)'
        					        	        }
        					        	    ],
        					        	    yAxis : [
        					        	        {
        					        	            type : 'value',
        					        	            axisLabel : {
        					        	                formatter: '{value}'
        					        	            }
        					        	        }
        					        	    ],
        					        	    series : [
        					        	        
        					        	        {
        					        	            name:'日累计访问量',
        					        	            type:'line',
        					        	            data:userVisitCountList
        					        	        }
        					        	    ]
        					        	};
        					        	                    
        					     // 使用刚指定的配置项和数据显示图表。
        					        myChart.setOption(option);
    							}else{
    								$("#main").append("<div style='height:300px;width:100%;margin-top: 100px;padding-top: 100px;padding-left: 35%;'>"+
    					    		"<font color='red' size='5px'>您选择的时间内无数据显示,或没有数据</font></div>");
    							}
    							
    							
    						}else{
    							errorAlert(data.text);
    						}
                },"json");
    	})
    	
    $("#apphour").click(function(){//第二个选项卡
    		$("#main").empty();
    		var chooesHourDate = $("input[name='chooesHourDate']").val();
    		type = 4;
    		$.post("<%=request.getContextPath()%>/visit/visit_visitStatistic.html",
                    "type="+type+"&chooesHourDate="+chooesHourDate,function(data){
    						if(data.success){
    							$("input[name='chooesHourDate']").val(data.chooesHourDate);
    							$("#type2").hide();
    				    		$("#type4").show();
    				    		$("#searchDiv").show();
    							var userVisitHourCountList = data.userVisitHourCountList;
    							var visitTimeHourList = data.visitTimeHourList;
    							len = visitTimeHourList.length;
    							if(len > 0){
    								// 基于准备好的dom，初始化echarts实例
        					        var myChart = echarts.init(document.getElementById('main'));
        					        
        					        option = {
        					        	    title : {
        					        	        text: 'APP使用人次（按天），默认显示当前天每小时人访问量'
        					        	    },
        					        	    tooltip : {
        					        	        trigger: 'axis'
        					        	    },
        					        	    toolbox: {
        					        	        show : false
        					        	    },
        					        	    calculable : true,
        					        	    xAxis : [
        					        	        {
        					        	            type : 'category',
        					        	            boundaryGap : false,
        					        	            data : visitTimeHourList,
        					                        name : '小时(单位:时)'
        					        	        }
        					        	    ],
        					        	    yAxis : [
        					        	        {
        					        	            type : 'value',
        					        	            axisLabel : {
        					        	                formatter: '{value}'
        					        	            }
        					        	        }
        					        	    ],
        					        	    series : [
        					        	        
        					        	        {
        					        	            name:'日累计访问量',
        					        	            type:'line',
        					        	            data:userVisitHourCountList
        					        	        }
        					        	    ]
        					        	};
        					        	                    
        					     // 使用刚指定的配置项和数据显示图表。
        					        myChart.setOption(option);
    							}else{
    								$("#main").append("<div style='height:300px;width:100%;margin-top: 100px;padding-top: 100px;padding-left: 35%;'>"+
    					    		"<font color='red' size='5px'>您选择的时间内无数据显示,或没有数据</font></div>");
    							}
    							
    							
    						}else{
    							errorAlert(data.text);
    						}
                },"json");
    	})
    });
    
    
    
    </script>
	</body>
</html>