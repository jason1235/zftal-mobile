<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>投票结果统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/amazeui.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/app.css">
	<style type="text/css">
        table tbody td{
            overflow: visible;
            white-space:normal;
        }
    </style>
	<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/echarts.min.js"></script>
    <script type="text/javascript">
     //加载数据
     function getResultData(){
    	 //1、加载所有统计数据
   		 //2、echarts展示统计信息
   		 //3、表格显示参与情况
   		 //4、表格显示投票详情
    	 var voteId = $("#voteIdDiv").text().trim();
    	 $.get("<%=request.getContextPath()%>/vote/vote_hdVoteResultCount.html",{"voteId":voteId},function(data){
           	if(data.code==1){
				console.log(data);   
				var voteMain = {};//投票信息
				var resultCount = [];//投票统计
				var resultDetail = [];//投票结果详情
				var partInMaps = {};//投票参与情况
				if(!!data.data){
					var resultDatas = data.data;
					voteMain = resultDatas.voteMain;
					resultCount = resultDatas.resultCount;
					resultDetail = resultDatas.resultDetail;
					partInMaps = resultDatas.partInMaps;
					//console.log(voteMain); 
					//console.log(resultCount); 
					//console.log(resultDetail); 
					//console.log(partInMaps); 
					
					initBarCharts(voteMain,resultCount);
					
					initVoteDetailDatas(resultDetail);
					
					initPartInDatas(partInMaps);
				}
           	}else{
           	    if(confirm("加载数据出错！")){
           	    	window.close();
           	    }
           	}
         },"json");
     }
     
     //统计数据图表化
     function initBarCharts(voteMain,resultCount){
    	 if(!!voteMain){
    		 var option = {};
    		 
    		 if(voteMain.voteType=='0'){//文字类型，饼图
    			 //封装饼图数据
    			 var legendData = [];
    			 var seriesData = [];
    			 
    			 for(var i=0;i<resultCount.length;i++){
    				 legendData.push(resultCount[i].description);
    				 seriesData.push({value:resultCount[i].amount,name:resultCount[i].description});
    			 }
    			 
    			 //指定图表的配置项和数据,饼图option
    	         option = {
    	        		    title : {
    	        		        text: voteMain.voteTitle,
    	        		        subtext: '',
    	        		        x:'center'
    	        		    },
    	        		    tooltip : {
    	        		        trigger: 'item',
    	        		        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	        		    },
    	        		    legend: {
    	        		        orient: 'vertical',
    	        		        left: 'left',
    	        		        data: legendData
    	        		    },
    	        		    series : [
    	        		        {
    	        		            name: '比重',
    	        		            type: 'pie',
    	        		            radius : '55%',
    	        		            center: ['50%', '60%'],
    	        		            data:seriesData,
    	        		            itemStyle: {
    	        		                emphasis: {
    	        		                    shadowBlur: 10,
    	        		                    shadowOffsetX: 0,
    	        		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
    	        		                }
    	        		            }
    	        		        }
    	        		    ]
    	        		};
    		 }else if(voteMain.voteType=='1'){//打分类型，柱状图
    			 //封装柱状图数据
    			 var xAxisData = [];
    			 var seriesData = [];
    			 
    			 if(voteMain.voteScoreMethod==0){
	    			 for(var i=0;i<resultCount.length;i++){
	    				 xAxisData.push(resultCount[i].description);
	    				 seriesData.push((resultCount[i].score/resultCount[i].amount).toFixed(2));
	    			 }
    			 }else if(voteMain.voteScoreMethod==1){
    				 for(var i=0;i<resultCount.length;i++){
	    				 xAxisData.push(resultCount[i].description);
	    				 seriesData.push(resultCount[i].score);
	    			 }
    			 }
    			 
    			 // 指定图表的配置项和数据,柱状图option
    	         option = {
    	             title: {
    	                 text: '分数'
    	             },
    	             tooltip: {},
    	             legend: {
    	                 data:['投票统计']
    	             },
    	             xAxis: {
    	                 data:xAxisData
    	             },
    	             yAxis: {},
    	             series: [{
    	                 name: voteMain.voteScoreMethod==0? '平均分':'总分',
    	                 type: 'bar',
    	                 data: seriesData
    	             }]
    	         };
    		 }
    	 }
    	 
    	 // 基于准备好的dom，初始化echarts实例
         var myChart = echarts.init(document.getElementById('main'));
         // 使用刚指定的配置项和数据显示图表。
         myChart.setOption(option);
     }
     
   //表格展示投票情况
     function initVoteDetailDatas(resultDetail){
    	 if(!!resultDetail){
    		 var detailStr = "<table class=\"am-table am-table-bordered am-table-radius am-table-striped\"><tr><th>账号</th><th>姓名</th><th>投票次数</th></tr>";
    		 for(var i=0;i<resultDetail.length;i++){
    			 detailStr += ("<tr><td>"+resultDetail[i].userId+"</td><td>"+resultDetail[i].xm+"</td><td>"+resultDetail[i].times+"</td><td>"+resultDetail[i].lastVoteTime.substring(0,resultDetail[i].lastVoteTime.length-2+"</td></tr>"));
    		 }
    		 detailStr += "</table>";
    		 $("#voteDetailDataTable").append(detailStr);
    	 }
     }
     
     //表格展示参与情况
     function initPartInDatas(partInMaps){
    	 if(!!partInMaps){
	    	 var partIns = partInMaps.partIns;
	    	 var notPartIns = partInMaps.notPartIns;
	    	 var partInsStr = "";
	    	 var notPartInsStr = "";
	    	 for(var i=0;i<partIns.length;i++){
	    		 partInsStr += (partIns[i].userId+"-"+partIns[i].xm+"&nbsp;&nbsp;&nbsp;");
	    		 if(i%8==0){
	    			 partInsStr += "\r";
	    		 }
	    	 }
	    	 for(var i=0;i<notPartIns.length;i++){
	    		 notPartInsStr += (notPartIns[i].userId+"-"+notPartIns[i].xm+"&nbsp;&nbsp;&nbsp;");
	    		 if(i%8==0){
	    			 partInsStr += "\r";
	    		 }
	    	 }
	    	 $("#voteCountDataTable").append("<span style='color:green;'>已参与</span>:"+partInsStr+"</br>");
	    	 $("#voteCountDataTable").append("<span style='color:red;'>未参与</span>:"+notPartInsStr);
    	 }
     }
     
   	 $(function(){
   		 
         getResultData();
   	 });
   	 </script>
  </head>
  <body>
       <div id="voteCountData">
           <div style="text-align:center;">
           	    <h3>投票统计</h3>
	           <div id="main" style="width: 600px;height:400px;margin:auto;"></div>
           </div>
       </div>
       </br>
       <div id="voteDetail">
           <div style="text-align:center;"><h3>投票详情</h3></div>
           <div id="voteDetailDataTable" style=""></div>
       </div>
        </br>
       <div id="votePartIns">
           <div style="text-align:center;"><h3>投票参与情况</h3></div>
           <div id="voteCountDataTable"  style=""></div>
       </div>
        </br> </br>
       <div id="voteIdDiv" style="display:none;">${voteIdForResultCount}</div>
  </body>
</html>