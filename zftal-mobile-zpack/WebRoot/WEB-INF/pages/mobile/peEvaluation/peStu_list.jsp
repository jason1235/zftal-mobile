<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	          	<!--<button type="button" class="btn btn-default" id="btn_zj">增加</button>-->
	          	<button type="button" class="btn btn-default" id="btn_xg">修改</button>
	          	<button type="button" class="btn btn-default" id="btn_sc">删除</button>
	          	
          		<button type="button" class="btn btn-default" id="btn_tycp">体育测评</button>
      			<button type="button" class="btn btn-default" id="btn_jshfs">计算后分数</button>
      			<button type="button" class="btn btn-default" id="btn_sjfx">数据分析</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/htPeEvaluation/htPeEvaluation_htStuList.html" method="post">
	        <div class="form-horizontal sl_all_form ">
	        <div class="row">
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">学生姓名</label>
		                <div class="col-xs-7">
	                        <s:textfield name="studentInfoQuery.name" value="" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
	                    </div>
		            </div>
		        </div>
		        
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">学籍号</label>
		                <div class="col-xs-7">
	                        <s:textfield name="studentInfoQuery.schoolNumber"  cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
	                    </div>
		            </div>
		        </div>
		        
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">性别</label>
		                <div class="col-xs-7">
	                        <!--<s:textfield name="studentInfoQuery.gender" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>-->
	                        <select name="studentInfoQuery.gender" class="form-control">
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
	                        <s:textfield name="studentInfoQuery.sclassname"  cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
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
                    <th style="width:64px;">选择</th>
                    <th>学籍号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>出生日期</th>
                    <th>家庭住址</th>
                    <th>毕业高中</th>
                    <th>身高</th>
                    <th>体重</th>
                    <th>班级名称</th>
                    <th>学院名称</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    <s:iterator value="list" var="p" status="st">
                <tr class="pxtr">
                    <td><input name="idckb" type="checkbox" id="id" value="${p.schoolNumber }"/></td>
                    <td>${p.schoolNumber}</td>
                    <td>${p.name}</td>
                    <td>
                        <s:if test="%{#p.gender == 1}">男</s:if>
                        <s:if test="%{#p.gender == 2}">女</s:if>
                    </td>
                    <td>${p.birthday}</td>
                    <td>${p.homeAddr}</td>
                    <td>${p.highSchoolName}</td>
                    <td>${p.height}</td>
                    <td>${p.weight}</td>
                    <td>${p.sclassname}</td>
                    <td>${p.sschoolname}</td>
                </tr>
                </s:iterator>
		    </tbody>
        </table>
        
        <ct:page pageList="${list }" query="${query }" />
        
	    </form>
	    </div>
		<script type="text/javascript">
		var heightOffset= 200;
			$(function(){
				console.log(11111)
				
				new Sortable(document.getElementById("list_body"),{group:"items",animation:150});
				$('#minIndex').val($('.pxtr:first').find('[name=pxm]').val());
				$('#maxIndex').val($('.pxtr:last').find('[name=pxm]').val());
				var enableMsg = '${enableMsg}';
				if (enableMsg != '') {
					alert(enableMsg);
				}
			});
			
			$("#btn_xg").click(function(){
			var checked=$("input[id='id']:checked");
				if(checked.length==0){
					openAlert("请选择行", "e");
					return false;
					
				}else if(checked.length>1){
                    openAlert("只能对单行进行操作", "e");
                    return false;
                }
                var id = checked.val();
                var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_edit.html?studentInfoQuery.schoolNumber=" + id;
                 window.location.href=url;
			});
				
				
		     $("#btn_sc").click(function(){
				var checked=$("input[id='id']:checked");
                if(checked.length==0){
                    openAlert("请选择行", "e");
                    return false;
                }else if(checked.length>1){
                    openAlert("只能对单行进行操作", "e");
                    return false;
                }
                
				var id = checked.val();
				var source = checked.parent().find('input[type="hidden"]').val();
                openAlert("确定要删除吗？", "w");

				$("#why_sure").click(function(){
					$.post("<%=request.getContextPath() %>/htPeEvaluation/htPeEvaluation_delStu.html?schoolNumber="+id, null, function(data){
						var callback = function(){
							location.reload();
						};
						
						processCall(data, callback);
					}, "json");
				});
			});
			
		$("#btn_tycp").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_tycpdataList.html";
            window.location.href=url;
		});	
		
		$("#btn_jshfs").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_jshfsList.html";
            window.location.href=url;
		});	
		
		$("#btn_sjfx").click(function(){
            var url = "<%=request.getContextPath()%>/htPeEvaluation/htPeEvaluation_dataAnal.html";
            window.location.href=url;
		});	
			
		
		</script>
	</body>
</html>
