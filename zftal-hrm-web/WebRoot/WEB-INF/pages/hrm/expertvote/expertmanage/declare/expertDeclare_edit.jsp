<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<script type="text/javascript">
		$(function(){
			
			$('a[class="btn_up"]').click(function(){
	    		push();
			});
			
			$('a[class="btn_fh"]').click(function(){
				back();
			});
			
			requestData('${path}','${privileges}');	
			
		});
		
		function requestData(path,param){
			if( path.length == 0 && param.length == 0){
				$("#demo_xxxx").css("display","block");
				return;
			}
			var successCall = function(d){
				try{
		    		var data = $.parseJSON(d);
		    		if(data.success==false){
		    			$(".page_loading").remove();
		    			tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
		    			$("#window-sure").click(function() {
							divClose();
						});
		    		}
				}catch(e){
					$("#compId").empty();
					$("#compId").append(d);
					$(".toolbox").css("display","block");
					$("#demo_xxxx").css("display","block");
					$(".page_loading").remove();
				}
			};
			var params = $("input[name='spBillConfig.id']").serialize(); 
			params += '&' + $("input[name='spBillInstance.id']").serialize();
			params += '&privilegeExpression=' + param;
			$("#windown-content").unbind("ajaxStart");
			$.ajax({
				url:_path + "/bill/instance_" + path + ".html",
				type:"post",
				data:params,
				cache:false,
				dataType:"html",
				success:successCall
			});
		}
		
		function initRadio(name,value){
			$("input[type='radio'][name='"+name+"']").each(function(){
				if($(this).val()==value){
					$(this).attr("checked","checked");
				}
			});
		}
		
		function push(){
			$.post('<%=request.getContextPath()%>/expertmanage/declare_push.html',$("#demo_xxxx input, #demo_xxxx select").serialize(),function(data){
				var callback=function(){
					back();
				}
				if( data.success ) {
					processDataCall(data,callback);
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						alertDivClose();
					});
				}
			},"json");
		}
		
		function back(){
			var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/expertmanage/declare_page.html">';
			content += getInputHtml();
			content +='    </form>';
			$('body').append(content);
			$('#form').submit();
			$('#form').remove();
		}		
		
		function getInputHtml(){
			var inputHTML = "";
			$("#demo_xxxx input").each(function(){
				inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
			});
			
			return inputHTML;
		}

		</script>
	</head>
	<body>
		<!--页面加载start-->
		<div class="page_loading">
			<div class="load_con">
		  		<div class="pic"></div>
		    </div>
		  <p>页面正在加载中，请稍候。。。</p>
		</div>
		<!--友情提醒end-->
		<div class="toolbox" style="display: none;">
		    <!-- 按钮 -->
		    <div class="buttonbox">
		        <ul class="btn_xxxx" style="background:none;border-top:none;">
		        <c:if test="${expertDeclare.status == 'INITAIL' || expertDeclare.status == code.NEW_AUDITING }">
		            <li>
		                <a onclick="return false;" class="btn_up" href="#">
		                  上报
		                </a>
		            </li>
		        </c:if>
		            <li>
		                <a onclick="return false;" class="btn_fh" href="#">
		                   返回
		                </a>
		            </li>
		        </ul>
			</div>	
		</div>
		<div style="margin: 0px auto; clear:both;width:100% ">
			<input type="hidden" name="spBillConfig.id" value="${expertDeclare.config_id }"/>
   			<input type="hidden" name="spBillInstance.id" value="${expertDeclare.instance_id }"/>
   			<input type="hidden" name="status" value="${expertDeclare.status }"/>
			<div class="comp_con" id="compId">
			</div>
	   		<div class="demo_xxxx" id="demo_xxxx" style="display: none;">
	   			<!--<input type="hidden" name="spAuditBillConfigId" value="${model.spAuditBillConfigId }"/>
	   			--><input type="hidden" name="id" value="${expertDeclare.id }"/>
	   			<input type="hidden" name="spBillConfigId" value="${expertDeclare.config_id }"/>
	   			<input type="hidden" name="spBillInstanceId" value="${expertDeclare.instance_id }"/>
	   			<input type="hidden" name="expertDeclare.config_id" value="${expertDeclare.config_id }"/>
   				<input type="hidden" name="expertDeclare.instance_id" value="${expertDeclare.instance_id }"/>
   				<input type="hidden" name="expertDeclare.id" value="${expertDeclare.id }"/>
		    </div>
		</div>
	</body>
</html>