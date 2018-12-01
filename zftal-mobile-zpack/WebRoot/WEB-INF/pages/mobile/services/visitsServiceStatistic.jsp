<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
  <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
  		<form class="form-horizontal sl_all_form" role="form" action="<%=request.getContextPath()%>/serviceManager/serviceManager_visitsServiceStatistic.html">
 		  <div class="form-horizontal sl_all_form">
            <div class="row">
            
            	<c:if test="${isPortal eq 'yes'}">
	            	<div class="col-xs-3">
			            <div class="form-group">
			                <label for="" class="col-xs-5 control-label">学校编码:</label>
			                <div class="col-xs-7">
		                        <input maxlength="15" placeholder="最多15个字符" class="form-control" type="text" name="visitsQuery.schoolCode" value="${visitsQuery.schoolCode }" /> 
							</div>
			            </div>
			        </div>
            	</c:if>
            	
            	<div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">服务名称:</label>
		                <div class="col-xs-7">
	                        <input maxlength="15" placeholder="最多15个字符" class="form-control" type="text" name="visitsQuery.fwmc" value="${visitsQuery.fwmc }" /> 
						</div>
		            </div>
		        </div>
            	
            	<div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">操作日期:</label>
		                <div class="col-xs-7">
	                        <input class="form-control" type="text" name="visitsQuery.startDate" value="${visitsQuery.startDate }" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d'})"/> 
						</div>
		            </div>
		        </div>
		        <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-1 control-label">至</label>
                        <div class="col-xs-7">
                            <input class="form-control" type="text" name="visitsQuery.endDate" value="${visitsQuery.endDate }" onchange="searchChange();" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
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
  	
	        <div class="row">
	            <div id="main" style="width: 70%;height:300px;margin-right: 20%;">
	            	
	            </div>
	        </div>


        <div class="row sl_aff_btn" style="padding: 0px 0; "></div>
  
  		<table class="table table-striped sl_all_bg table-bordered text-left">
			<thead>
	            <tr>
	                <th colspan="6" style="background: #EBEBEB;">数据明细</th>
	            </tr>
			</thead>
            <thead id="list_head">
	            <tr>
	                <th>排名</th>
	                <th>服务编码</th>
	                <th>服务名称</th>
	                
	                <th>累计访问量</th>
	                <th>总计天数</th>
	                <th>日均访问量</th>
	            </tr>
            </thead>
            <tbody id="list_body" >
            	<s:iterator value="statisticList" var="menu" status="st">
                <tr name="tr" class="data">
                    <td class="autocut" ><s:property value='#st.index+1+total'/></td>
                    <td class="autocut" >${menu.fwbm }</td>
                    <td class="autocut" >${menu.fwmc }</td>
                    <td class="autocut" >${menu.visits}</td>
                    <td class="autocut endtr" >${dayBetween }</td>
                    <td class="autocut" >${menu.avenge} </td>
                    
                </tr>
                </s:iterator>
  			</tbody>
        </table>
        <ct:page pageList="${statisticList }" query="${visitsQuery }" queryName="visitsQuery" />
	    </form>
  </div>
  <script type="text/javascript">
    $(function(){
    	fillRows("10", "", "", false);//填充空行
    	$("#pageSize").attr("disabled",true);//此处是让每页显示条数固定化不可编辑
    	topList = ${topList};
    	var serviceName = new Array(topList.length);//获取服务名称数组
    	var totalVisits = new Array(topList.length);//获取累计访问量数组
    	var avengeVisits = new Array(topList.length);//获取平均访问量数组
    	var len = topList.length;
    	if(len > 0){
    		$("#main").empty();
	    	for ( var i = len - 1; i >= 0; i--) {
	    		serviceName[len - 1 - i] = topList[i].fwmc;
	    		totalVisits[len - 1 - i] = topList[i].visits;
	    		avengeVisits[len - 1 - i] = topList[i].avenge;
			}
	    	// 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('main'));

	        // 指定图表的配置项和数据
	        var option = {
	            title: {
	                text: '服务访问量统计top10(数据来自app端访问)',
	                left: 20
	            },
	            tooltip: {
	                trigger: 'axis',
	                axisPointer: {
	                    type: 'shadow'
	                }
	            },
	            legend: {
	                data: [ '日均访问量','累计访问量'],
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
	                type: 'category',
	                data: serviceName
	            },
	            series: [
	                {
	                    name: '累计访问量',
	                    type: 'bar',
	                    data: totalVisits
	                }
	                ,
	                {
	                    name: '日均访问量',
	                    type: 'bar',
	                    data: avengeVisits
	                }
	            ]
	        };

	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
    	}else{
    		$("#main").append("<div style='height:300px;width:100%;margin-top: 100px;padding-top: 100px;padding-left: 35%;'>"+
    		"<font color='red' size='5px'>您选择的日期内无数据显示,无法显示统计图</font></div>");
    	}
    	
    
    });
    
    
    
    </script>
	</body>
</html>