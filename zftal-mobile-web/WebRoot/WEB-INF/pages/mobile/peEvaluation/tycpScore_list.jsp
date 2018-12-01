<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/hrm/head_v5.ini" %>
		<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
		<style type="text/css">
			.virtal-catalog td {
				font-weight:bold;
			}
		</style>
	</head>
	<body>
	    <div class="container_ydht sl_all_bg">
	    <div class="toolbox">
	        <div class="btn-group" id="but_ancd">
	          	<button type="button" class="btn btn-default" id="btn_tycp">体育测评</button>
      			<button type="button" class="btn btn-default" id="btn_xsxx">学生信息</button>
      			<button type="button" class="btn btn-default" id="btn_sjfx">数据分析</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/htPeEvaluation/htPeEvaluation_jshfsList.html" method="post">
	        <div class="form-horizontal sl_all_form ">
	        <div class="row">
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">学生姓名</label>
		                <div class="col-xs-7">
	                        <s:textfield name="peDataQuery.stuName" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
	                    </div>
		            </div>
		        </div>
		        
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">学籍号</label>
		                <div class="col-xs-7">
	                        <s:textfield name="peDataQuery.schoolNumber" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
	                    </div>
		            </div>
		        </div>
		        
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">性别</label>
		                <div class="col-xs-7">
	                       <!-- <s:textfield name="studentInfoQuery.name" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>-->
	                        <select name="peDataQuery.gender" class="form-control">
	                        	<option value="">---</option>
	                        	<option value="1">-男-</option>
	                        	<option value="2">-女-</option>
	                        </select>
	                    </div>
		            </div>
		        </div>
		        
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">班级名称</label>
		                <div class="col-xs-7">
	                        <s:textfield name="peDataQuery.className" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
	                    </div>
		            </div>
		        </div>
		        
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">级别</label>
		                <div class="col-xs-7">
	                        <select name="peDataQuery.dataType" class="form-control">
	                        	<option value="0"   <s:if test="%{peDataQuery.dataType == 0}">selected="selected"</s:if> >-平时测评-</option>
	                        	<option value="1" <s:if test="%{peDataQuery.dataType == 1}">selected="selected"</s:if> >-国家级-</option>
	                        </select>
	                    </div>
		            </div>
		        </div>
		        
	        </div>
	    </div>
	    <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
            </div>
        </div>
        <input name="minIndex" id="minIndex" type="hidden" value=""/>
		<input name="maxIndex" id="maxIndex" type="hidden" value=""/>
        <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                   <!-- <th style="width:64px;">选择</th>-->
                    <th>姓名</th>
                    <th>班级</th>
                    <th>BMI</th>
                    <th>肺活量</th>
                    <th>50米跑</th>
                    <th>立定跳远</th>
                    <th>坐位体前屈</th>
                    <th>800米跑</th>
                    <th>1000米跑</th>
                    <th>1分钟仰卧起坐</th>
                    <th>引体向上</th>
                    <th>综合评定</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    <s:iterator value="list" var="p" status="st">
                <tr class="pxtr">
                    <!--<td><input name="idckb" type="checkbox" id="id" value="${p.schoolNumber }"/></td>-->
                    <td>${p.stuName}</td>
                    <td>${p.className}</td>
                    <td>${p.BMI}</td>
                    <td>${p.pulmonaryScore}</td>
                    <td>${p.fiftyRunScore}</td>
                    <td>${p.jumpScore}</td>
                    <td>${p.antexionScore}</td>
                    <td>${p.eightHundredRunScore}</td>
                    <td>${p.oneThousandRunScore}</td>
                    <td>${p.sitUpScore}</td>
                    <td>${p.pullUpScore}</td>
                    <td>${p.generalScore}</td>
                </tr>
                </s:iterator>
		    </tbody>
        </table>
        
        <ct:page pageList="${list }" query="${peDataQuery }" queryName="peDataQuery" />
        
	    </form>
	    </div>
		<script type="text/javascript">
		var heightOffset= 200;
			$(function(){
				new Sortable(document.getElementById("list_body"),{group:"items",animation:150});
				$('#minIndex').val($('.pxtr:first').find('[name=pxm]').val());
				$('#maxIndex').val($('.pxtr:last').find('[name=pxm]').val());
				var enableMsg = '${enableMsg}';
				if (enableMsg != '') {
					alert(enableMsg);
				}
			});
			
		$("#btn_tycp").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_tycpdataList.html";
            window.location.href=url;
		});	
		
		$("#btn_xsxx").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_htStuList.html";
            window.location.href=url;
		});	
		
		$("#btn_sjfx").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_dataAnal.html";
            window.location.href=url;
		});	
				
		</script>
	</body>
</html>
