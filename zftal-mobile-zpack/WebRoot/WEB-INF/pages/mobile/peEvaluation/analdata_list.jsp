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
      			<button type="button" class="btn btn-default" id="btn_jshfs">计算后分数</button>
      			<button type="button" class="btn btn-default" id="btn_xsxx">学生信息</button>
      			
      			<button type="button" class="btn btn-default" id="btn_exportCou">导出国家EXCEL</button>
      			<button type="button" class="btn btn-default" id="btn_exportPro">导出省EXCEL</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/htPeEvaluation/htPeEvaluation_dataAnal.html" method="post">
	        <div class="form-horizontal sl_all_form ">
	        <div class="row">
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">级别</label>
		                <div class="col-xs-7">
	                        <select name="dataType" class="form-control">
	                        	<option value="0"   <s:if test="%{dataType == 0}">selected="selected"</s:if> >-平时测评-</option>
	                        	<option value="1" <s:if test="%{dataType == 1}">selected="selected"</s:if> >-国家级-</option>
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
                    <th>学院</th>
                    <th>人数</th>
                    <th>合格人数</th>
                    <th>合格率</th>
                    <th>优良人数</th>
                    <th>优良率</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    <s:iterator value="list" var="p" status="st">
                <tr class="pxtr">
                    <td>${p.name}</td>
                    <td>${p.xyrs}</td>
                    <td>${p.xyhgrs}</td>
                    <td>${p.qualified} %</td>
                    <td>${p.xyylrs}</td>
                    <td>${p.excellent} %</td>
                </tr>
                </s:iterator>
		    </tbody>
        </table>
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
		
		$("#btn_jshfs").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_jshfsList.html";
            window.location.href=url;
		});	
		
		$("#btn_xsxx").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_htStuList.html";
            window.location.href=url;
		});	
		
		$("#btn_exportCou").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_exportCouExcel.html";
            window.location.href=url;
		});	
		
		$("#btn_exportPro").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_exportProExcel.html";
            window.location.href=url;
		});	
				
		</script>
	</body>
</html>
