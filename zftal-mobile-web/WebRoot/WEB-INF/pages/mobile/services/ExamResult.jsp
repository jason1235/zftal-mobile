<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head_v5.ini" %>
	<style type="text/css">
        table tbody td{
            overflow: visible;
            white-space:normal;
        }
    </style>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/echarts.min.js"></script>
     <script type="text/javascript">
   	 $(function(){
   	 	
   	 
   	 });
   	 
    
    </script>
    </head>
  <body>
  <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
 		  
  	<div>
  		<c:forEach var="questionitem" items="${questionListlength}" varStatus="st">
  		<c:if test="${questionitem.type == '2' }">
  			<div id="main${st.index }" style="width: 100%;height:130px;margin-top:15px;text-align:center;padding-top:5px;background-color: white;" >
  			<font size="4px">
  				Q${st.index+1 }&nbsp;${questionitem.title}(简答题)</font>
  			</div>
	  		<div  style="width: 100%;height:auto;" >
	  			<table class="table table-striped sl_all_bg table-bordered text-left">
	  				<tbody>
	  				<tr name="tr" class="data">
	                    <td width="70%" >答案选项</td>
	                    <td class="autocut">回复情况</td>
	                    
	                	</tr>
                	<tr name="tr" class="data">
	                    <td width="30%" class="autocut" colspan="2">简答题不显示统计结果</td>
	                    
	                	</tr>
	  				</tbody>
	  			</table>
	  		</div>
  		</c:if>
  		<c:if test="${questionitem.type != '2' }">
  			<div style="width: 100%;height:auto;text-align:center;background-color: white;">
  			<font size="4px">
  				Q${st.index+1 }&nbsp;${questionitem.title}</font>
  			</div>
  			<div id="main${st.index }" style="width: 100%;height:260px;" ></div>
	  		<div  style="width: 100%;height:auto;" >
	  			<table class="table table-striped sl_all_bg table-bordered text-left">
	  				<tbody>
	  				<tr name="tr" class="data">
	                    <td width="70%" >答案选项</td>
	                    <td class="autocut">回复情况</td>
	                    
	                	</tr>
	  				<c:forEach var="valueitem" items="${questionitem.itemValue}" varStatus="st">
	  					<tr name="tr" class="data">
	                    <td width="70%" >${valueitem.name } </td>
	                    <td class="autocut">${valueitem.value }</td>
	                    
	                	</tr>
                	</c:forEach>
                	<tr name="tr" class="data">
	                    <td width="70%"  colspan="2">受访人数&nbsp;&nbsp;&nbsp;${questionitem.totalpeople }</td>
	                    
	                	</tr>
	  				</tbody>
	  			</table>
	  		</div>
	  		</c:if>
  		</c:forEach>
	</div>
  <script type="text/javascript">
    $(function(){
     var questionList = ${questionList};
     for(var i = 0; i<questionList.length; i++){
    	 if(questionList[i].type == '2') continue;
    	 var itemNameList = questionList[i].item;
    	 //for(var j = 0; j < itemNameList.length; j++){
    	 //	 if(itemNameList[j].length > 18){
	     //		 itemNameList[j] = itemNameList[j].substring(0,18)+"...";
    	 //	 }
    	 //}
    	 var itemValueList = questionList[i].itemValue;
	     var myChart = echarts.init(document.getElementById('main'+i));
	     var j = i+1;
	     var titleText = "Q"+j+"  "+questionList[i].title;
	     var titleNewText;
	     
	    	// 指定图表的配置项和数据
	   	 var option = {
	   			backgroundColor: '#FFFFFF',//背景色
	   		    
	   		    tooltip : {
	   		    	trigger: 'item',
	   		        formatter: "{a} <br/>{b} : {c} ({d}%)"
	   		    },
	   		    legend: {
	   		    	 top : 180,
		             orient: 'horizontal' ,   // legend 横向布局。
	   		         data: itemNameList
	   		    },
	   		 	calculable : true,
	   		    series : [
	   		        {
	   		        	top: 0,
	   		            name: '访问来源',
	   		            type: 'pie',
	   		            radius : '35%',
	   		            center: ['50%', '30%'],
	   		            data:itemValueList,
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
	
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
    	 
     }

    
   	 
    });
    
    
    
    </script>
	</body>
</html>