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
            var reportId = "";
            var snapTime = "";
            var hasModel=false;
            $(function(){ 
                
                //fillRows("20", "", "", false);//填充空行
                $("#cx_btn").click(function(e){
                    if($("#reportList").val()==null||$("#reportList").val()==''){
                        alert("请选择要查询的报表！")
                        return false;
                    }
                	queryReportData();
                });
                $("#dc_btn").click(function(e){
                    if(hasModel){
                    	window.open("<%=request.getContextPath() %>/summary/seniorreport_exportData.html?id="+reportId+"&snapTime="+snapTime);
                    }else{
                        alert("未找到相应的导出模板，无法导出Excel");
                    }
                });
                $("#btn_save").click(function(e){
                	$("#btn_list").hide();
                	$("#load_btn").show();
                	 $.post("<%=request.getContextPath() %>/summary/seniorreport_saveHistroy.html","id="+reportId,function(data){
                		 $("#btn_list").show();
                         $("#load_btn").hide();
                         $("#reportList").val(reportId);
                		   queryHistroy(reportId);
                		   alert(data.text);
                     });
                });
                $("#type").change(function(){
                	queryReportList();
                });
                $("#reportList").change(function(){
                	queryHistroy($(this).val());
                })
                queryReportList();
            });

            function queryReportList(){
                $.post("<%=request.getContextPath() %>/summary/seniorreport_getList.html",$("#type").serialize(),function(data){
                	var str="<option value='' >--请选择--</option>";
                    for (var i=0;i<data.reportList.length;i++){
                        str+="<option value='"+data.reportList[i].reportId+"'>"+data.reportList[i].reportName+"</option>";
                    }
                    $("#reportList").html(str);
                    if(data.reportList.length!=0){
                    	$("#reportList").val(data.reportList[0].reportId);
                        queryHistroy(data.reportList[0].reportId);
                    }
                })
            }

            function queryHistroy(id){
                if(id==null||id=='')return;
                $.post("<%=request.getContextPath() %>/summary/seniorreport_queryReport.html","id="+id,function(data){
                	 var str="<option value='' >即时数据</option>";
                    for (var i=0;i<data.list.length;i++){
                        str+="<option value='"+data.list[i].id+"'>"+data.list[i].histroyTimeStr+"_"+data.list[i].gh+"</option>";
                    }
                    $("#snapTime").html(str);
                })
            }

            function queryReportData(){

            	$("#btn_save").hide();
            	if($('#snapTime').val()==''){
            		$("#btn_save").show();
                }
            	reportId = $("#reportList").val();
            	snapTime = $("#snapTime").val();
            	$("#loading").show();
                $("#data").hide();
                $.post("<%=request.getContextPath() %>/summary/seniorreport_reportDetail.html",$("#selectForm").serialize(),function(data){
                    $("#list_head").html(data.Title);
                    $("#list_body").html(data.Body);
                    $("#loading").hide();
                    $("#data").show();
                    $(".reporttitle").html(data.reportView.reportTitle);
                    $("#list_body").find("a").click(function(){
                        var rowcondition=encodeURIComponent($(this).next().val());
                        var colcondition=encodeURIComponent($(this).next().next().val());
                        var id = $(this).attr("id");
                        var href="<%=request.getContextPath() %>/summary/seniorreport_list_rev.html?id=${id}&rquery.rowCondition="
                            +rowcondition+"&rquery.columnCondition="+colcondition;
                        location.href=href;
                        //showWindow("人员详情","<%=request.getContextPath()%>/summary/report_list_rev.html?id="+id+"&rquery.rowCondition="+rowcondition+"&rquery.columnCondition="+colcondition, 640, 330);
                    });
                    if(data.hasModel){
                        hasModel=true;
                    }
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
            </UL>
            </DIV><!-- 按钮 --><!-- 过滤条件开始 --><!-- 过滤条件结束 -->
            <P class=toolbox_fot><EM></EM></P>
        </div>
        <form id="selectForm" method="post">
        
        <div class="searchtab">
            <table width="100%" border="0">
                <tbody>
                    <tr>
                        
                        <th width="10%">所属类别</th>
                        <td width="20%">
                            <c:if test="${type == null}">
                            <select id="type" name="type">
                                <c:forEach items="${types}" var="t">
                                    <option value="${t.key }">${t.text }</option>
                                </c:forEach>
                            </select>
                            </c:if>
                            <c:if test="${type != null}">
                            <span>${type.text }</span>
                            <input type="hidden" name ="type" id="type" value="${type.key}"/>
                            </c:if>
                        </td>
                        
                        <th width="10%">报表名称</th>
                        <td width="20%">
                            <select name="id" id="reportList">
                            </select>
                        </td>
                        
                       </tr>
                       <tr>
                        <th width="10%">归档日期</th>
                        <td width="20%">
                            <select id='snapTime' name="snapTime">
                                <option value="" <c:if test="${'' eq snapTime||null eq snapTime}"> selected='true' </c:if>>即时数据</option>
                                <c:forEach items="${snapTimeList}" var="stime">
                                    <option value="${stime }" <c:if test="${stime eq snapTime}"> selected='true' </c:if>>${stime }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td width="16%" colspan="2">
                            <div class="btn">
                              <button class="btn_cx" id="cx_btn" type="button">查 询</button>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        </form>
        
        <div id="loading" class="page_loading" style="display: none;">
            <div class="load_con">
                <div class="pic"></div>
            </div>
          <p>数据正在加载中，请稍候。。。</p>
        </div>
       <div id="data" style="overflow-x:auto;overflow-y:hidden;display: none;">
        <div class="reporttitle">${reportView.reportTitle }</div>
        <div class="toolbox" id = "btnList">
	        <div class="buttonbox">
	        <span id="load_btn" class='formlist' style="display:none;"><div class='loading'>归档保存中</div></span>
	            <ul id = "btn_list">
	                <li>
	                    <A id="dc_btn" class="btn_shuc">导出报表</a>
	                    <a></a>
	                    
	                    <a id="btn_save" class="btn_down">归档保存</a>
	                </li>
	            </ul>
	        </div>
	    </div>
        <table id="data" summary="" class="dateline" align="" style="width:789px;">
            <thead id="list_head">
            </thead>
            <tbody id="list_body">
            </tbody>
        </table>
    </div>
    <div id="flashcontent"></div>
    </body>
</html>