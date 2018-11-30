<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <style type="text/css">
    .form-control {
	    display: block;
	    width: 100%;
	    height: 34px;
	    padding: 6px 12px;
	    font-size: 14px;
	    line-height: 1.42857143;
	    color: #555;
	    background-color: #fff;
	    background-image: none;
	    border: 1px solid #ccc;
	    border-radius: 4px;
	    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
	    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
	    -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
	    -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
	}
</style>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/skin/WdatePicker.css"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>


    <script type="text/javascript">
    $(function() {
        $("#btn_tb").click(function() {
          var procedureId = $("#procedureId").val();
          if (procedureId.length == 0) {
            alert("没有设置存储过程！");
            return false;
          }
          
          $.ajax({
            url:"<%=request.getContextPath() %>/basedata/basedata_doSynchronized.html",
            type:"post",
            dataType:"json",
            data:"procedureId=" + $("#procedureId").val(),
            success:function(data) {
              if (data["out_code"] != "0") {
                alert(data["out_message"]);
              } else {
                $("form:first").submit();
              }
            }
          });
          return false;
        });
        
        if ("${showMore}" == "1") {
          $("div.more--item_bottom a").removeClass().addClass("up");
          $("div.more--item_bottom a").text("收起");
          $("#total").css("display","block");
          $("#showMore").val("1");
          $("div.more--item_bottom a").toggle(
            hideMore,showMore
          );
        }else{
          $("div.more--item_bottom a").toggle(
            showMore,hideMore
          );
        }
        
        $("#selectBtn").click(function(){
          $("#selectForm").submit();
        });
        
        //fillRows("15", "", "", false);
      });
      function showMore(){
        $(this).removeClass().addClass("up");
        $(this).text("收起");
        $("#total").slideDown();
        $("#showMore").val("1");
      }
      function hideMore(){
        $(this).removeClass().addClass("down");
        $(this).text("更多");
        $("#total").slideUp();
        $("#showMore").val("-1");
      }
   
      /*
      *排序回调函数
      */
      function callBackForSort(sortFieldName,asc){
        $("#sortFieldName").val(sortFieldName);
        $("#asc").val(asc);
        $("#selectForm").submit();
      }
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath()%>/basedata/basedata_synBaseData.html" name="selectForm" id="selectForm" method="post">
      
      	<c:if test="${viewables == null || fn:length(viewables) == 0}">
              <h3>温馨提示：</h3>
              <p><font color="#0f5dc2">信息类【${query.clazz.name }】没有设置可显示的属性</font></p>
        </c:if>
      
      
      	<input type="hidden" id="classId" name="classId" value="${classId}"/>
        <input type="hidden" id="procedureId" name="procedureId" value="${procedureId}"/>
        <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
        <input type="hidden" id="asc" name="asc" value="${asc}"/>
        <input type="hidden" id="showMore" name='showMore' value="${showMore }" />
        
         <c:if test="${fn:length(viewables) > 0}">
         
         <Div class="row">

		<div class="col-sm-12">

			

			<div class="panel panel-default">

				<div class="panel-heading">

					<h3 class="panel-title">${query.clazz.name }</h3>

					<a href="javascript:;" class="expand_close expand1">展开关闭</a>

				</div>

				<div class="panel-body">

					

	<form class="form-horizontal" role="form">

		<div class="row">
		
			<c:forEach items="${conditions }" step="2" varStatus="vs">
				<c:forEach begin="${vs.index}" end="${vs.index + 1}" var="idx">
					<c:if test="${fn:length(conditions) > idx }">
	                    <c:set var="item" value="${conditions[idx] }"/>
	                    	<c:choose>
	                    		<c:when test="${item.fieldType=='DATE' || item.fieldType=='MONTH' || item.fieldType=='YEAR' }">
	                    		<div class="col-md-12 col-sm-12" style="margin-top: 5px;margin-bottom: 5px;">
	                    			<div class="form-group form-inline">
										<label for="" class="col-sm-2 control-label">${item.name }</label>
										<div class="col-sm-6">
											<input type="text" id="${item.fieldName }1" name="${item.fieldName }" value="${valuesMap[item.fieldName][0] }" 
											<c:if test="${item.fieldType=='MONTH'}">
												onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'${item.fieldName }2\')}'})"  
											 </c:if>
											 <c:if test="${item.fieldType=='YEAR'}">
												onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'#F{$dp.$D(\'${item.fieldName }2\')}'})"  
											 </c:if>
											 <c:if test="${item.fieldType=='DATE'}">
												onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'${item.fieldName }2\')}'})"  
											 </c:if>
											class="form-control startDate" placeholder="" style="float:left;">　
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">至</font>　
											<input type="text" id="${item.fieldName }2" name="${item.fieldName }" value="${valuesMap[item.fieldName][1] }" 
											<c:if test="${item.fieldType=='MONTH'}">
												onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'${item.fieldName }1\')}'})"  
											 </c:if>
											 <c:if test="${item.fieldType=='YEAR'}">
												onfocus="WdatePicker({dateFmt:'yyyy',minDate:'#F{$dp.$D(\'${item.fieldName }1\')}'})"  
											 </c:if>
											 <c:if test="${item.fieldType=='DATE'}">
												onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'${item.fieldName }1\')}'})"  
											 </c:if>
											class="form-control endDate" placeholder="" style="float:right;">
										</div>
									</div>
		                    	</div>	
		                        </c:when>
		                        <c:when test="${item.fieldType=='SIGLE_SEL'}">
		                        		<div class="col-md-6 col-sm-6" style="margin-top: 5px;margin-bottom: 5px;">
										<div class="form-group">
											<label for="" class="col-sm-4 control-label">${item.name }</label>
											<div class="col-sm-8">
												<select name="${item.fieldName }"  class="form-control">
													<option value=""></option>
						                            <option value="1" ${valuesMap[item.fieldName]=="1"?"selected=\"selected\"":""}>是</option>
						                            <option value="0" ${valuesMap[item.fieldName]=="0"?"selected=\"selected\"":""}>否</option>
												</select>
											</div>
										</div>
									</div>
		                        </c:when>
		                        <c:otherwise>
		                        	<div class="col-md-6 col-sm-6" style="margin-top: 5px;margin-bottom: 5px;">
										<div class="form-group">
											<label for="" class="col-sm-4 control-label">${item.name }</label>
											<div class="col-sm-8">
												<c:if test="${item.fieldLen > 1000}">
													<input maxlength="1000" placeholder="最多1000个字符" type="text" fieldType="${item.fieldType }" name="${item.fieldName }" value="${valuesMap[item.fieldName] }" class="form-control" placeholder="">
												</c:if>
												<c:if test="${item.fieldLen < 1000}">
													<input maxlength="${item.fieldLen }" placeholder="最多${item.fieldLen }个字符" type="text" fieldType="${item.fieldType }" name="${item.fieldName }" value="${valuesMap[item.fieldName] }" class="form-control" placeholder="">
												</c:if>
											</div>
										</div>
									</div>
		                        </c:otherwise>
	                    	</c:choose>
	                    </c:if>
                    <c:if test="${fn:length(conditions) eq idx }">
                    <div class="col-md-3 col-sm-3">
										<div class="form-group">
											
										</div>
                    </c:if>
				</c:forEach>
			</c:forEach>
		
		


			
		</div>
	</form>
				</div>
				<div class="panel-footer text-right">
					<button type="button" class="btn btn-primary btn-sm" id="selectBtn">查询</button>
				</div>
			</div>
			<script type="text/javascript">
				$(document).ready(function(e) {
					$(".panel-heading .expand_close").eq(0).removeClass('expand1').addClass('close1');
					$(".panel-body").eq(0).show();
					$(".panel-footer").eq(0).show();
					$(".panel-heading").click(function(e) {
						if($(this).children(".expand_close").attr("class").indexOf("expand1")>0){ 
							$(this).children(".expand_close").removeClass('expand1').addClass('close1');
							$(this).parent().children(".panel-body").slideDown();
							$(this).parent().children(".panel-footer").slideDown();
						}else
						{

							$(this).children(".expand_close").removeClass('close1').addClass('expand1');

							$(this).parent().children(".panel-body").slideUp();

							$(this).parent().children(".panel-footer").slideUp();

						}
					});

				});

			</script>
		</div>
		
		
		<table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
	                <c:forEach items="${viewables}" var="p">
	                  <c:if test="${p.fieldName eq sortFieldName}">
	                    <td class="sort_title_current_${asc }" id="${p.fieldName }">${p.name }</td>
	                  </c:if>
	                  <c:if test="${p.fieldName != sortFieldName}">
	                    <td class="sort_title" id="${p.fieldName }">${p.name }</td>
	                  </c:if>
	                </c:forEach>
                    
                </tr>
            </thead>
            <tbody id="list_body" >
            	<c:forEach items="${pageList}" var="entity">
                <tr name="list_tr" id="${entity.values['globalid']}" class="data pxtr">
                  <c:forEach items="${viewables}" var="infoProperty">
                    <td class="autocut">${entity.viewHtml[infoProperty.fieldName]}</td>
                  </c:forEach>
                </tr>
              </c:forEach>
            </tbody>
        </table>
        <ct:page pageList="${pageList }" />
        
        
        
	</Div>
         	
         
         </c:if>
         
        
      </form>
  </div>
	</body>
</html>