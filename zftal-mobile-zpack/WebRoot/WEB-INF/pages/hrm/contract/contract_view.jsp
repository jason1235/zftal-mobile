<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<script type="text/javascript">
		$(function(){
			initTag();

			requestData1('${path}',$("#viewId input,#search1 input").serialize());		
		});
		
		function initTag(){
			var li = '${path}'.split("_")[1];
			if(li == null){
				$("#comp_title li:first").addClass("ha");
			}else{
				$("#" + li).parent().addClass("ha");
			}
			
			$("#comp_title a").click(function(){
				$("#comp_title li").removeClass("ha");
				$(this).closest("li").addClass("ha");
				var path = $(this).attr("id");
				var param = $("#viewId input").serialize();
				requestData1('/contractNew_' + path,param);
			});
		}
		
		function requestData1(path,param){
			if(path == null || path ==''){
				path = "/contractNew_signatureList";
			}
			$(".page_loading").show();
			var successCall = function(d){
				try{
		    		var data = $.parseJSON(d);
		    		if(data.success==false){
		    			tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
		    			$("#window-sure").click(function() {
							divClose();
						});
		    		}
				}catch(e){
					$("#viewId").empty();
					$("#viewId").append(d);
					$(".page_loading").hide();
				}
			};
			$("#windown-content").unbind("ajaxStart");
			$.ajax({
				url:_path + "/contract" + path + ".html",
				type:"post",
				data:param,
				cache:false,
				dataType:"html",
				success:successCall
			});
		}
		</script>
	</head>
	<body>
		<div id="search1">
			<input type="hidden" name="workNum" class="text_nor" style="width:130px" value="${model.workNum }" />
			<input type="hidden" name="fullName" class="text_nor" style="width:130px" value="${model.fullName }" />
			<input type="hidden" name="type" class="text_nor" style="width:130px" value="${model.type }" />
			<input type="hidden" name="startDate" value="${model.startDate}" />
			<input type="hidden" name="endDate" value="${model.endDate}"/>
			<input type="hidden" name="number" class="text_nor" style="width:130px" value="${model.number }" />
			<input type="hidden" name="status" class="text_nor" style="width:130px" value="${model.status }" />
			<input type="hidden" name="disuse" value="${model.disuse }" />
			<input type="hidden" name="showCount" value="${model.showCount }"/>
			<input type="hidden" name="currentPage" value="${model.currentPage }"/>
			<input type="hidden" name="totalResult" value="${model.totalResult }"/>
		</div>
		<div class="compTab">
			<div class="comp_title" id="comp_title">
		      <ul>
		        <li><a href="#" id="signatureList"><span>在签合同</span></a></li>
		        <li><a href="#" id="willList"><span>即将到期合同</span></a></li>
		        <li><a href="#" id="overList"><span>过期合同</span></a></li>
		        <li><a href="#" id="historyList"><span>历史合同</span></a></li>
		        <li><a href="#" id="allList"><span>所有合同</span></a></li>
		      </ul>
		    </div>
			<!--页面加载start-->
			<div class="page_loading" style="display: none;">
				<div class="load_con">
			  		<div class="pic"></div>
			    </div>
			  <p>页面正在加载中，请稍候。。。</p>
			</div>
			<!--友情提醒end-->
			<div class="comp_con" id="viewId">
			</div>
		</div>
	</body>
</html>
