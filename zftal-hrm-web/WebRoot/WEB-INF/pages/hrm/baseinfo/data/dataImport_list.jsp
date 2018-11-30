<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <style>
		.progressbar{background:repeat-x scroll #87CEFA;height:20px;}
	</style>
    <script type="text/javascript">
    $(function(){

			var current = null;

			$("tbody > tr[name^='tr']").click(function(){	//监听单击行
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			
			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
			$("button[name='close']").click(function(){
				$("#tips").fadeOut("slow",function(){
					$("#scroll").empty();
				});
			});
		});
	
		function uploadEntity(id){//数据导入
			showWindow("数据导入", "<%=request.getContextPath()%>/baseinfo/infoClassData_upload.html?classId="+id, 480, 150 );
		}
		
		function downloadEntity(id){//模板下载
			window.location.href = _path + "/baseinfo/infoClassData_downloadTemplate.html?classId="+id;
		}
		
		function highImport(){ //高级导入
			window.location.href = _path + "/baseinfo/highImport_list.html";
		}
		
		function operationList(){
			$("a[name='upload']").click(function(){
				var id = $(this).closest("td").find("input[id='classId']").val();
				uploadEntity(id);
			});
			$("a[name='download']").click(function(){
				var id = $(this).closest("td").find("input[id='classId']").val();
				downloadEntity(id);
			});
			
			$(".select_tools a").css("cursor","pointer");

			operationHover();
		}
		
		function requestProgress(){
			$("#windown-content").unbind("ajaxStart");
			$.ajax({type:"post",
				url:"<%=request.getContextPath()%>/baseinfo/infoClassData_process.html",
				success:function(data){
					if(data.success){
						$("#scroll").append(data.result.description);
						//var li = $("#scroll").find("li:last");
						$("#scroll").parent().scrollTop($("#scroll").height());
						if(data.progress != null){
							$("#progressTitle").text(data.progress.msg);
							$("#progressInfo").text(data.progress.percent);
							$("#progressbar").css("width",data.progress.percent);
						} 
						if(data.result.finish){//操作结束，出现关闭按钮
							$("#viewInfoWindow").fadeOut("normal",function(){
								$("#closeInfoWindow").fadeIn("normal");
							});
						}else{
							setTimeout("requestProgress()",200);//请求间隔200ms
						}
					}else{
						$("#scroll").append("<li><font color='red'>请求失败</font></li>");
					}						
				},
				datatType:"json",
				global:false
			});
		}
		
		function showProgress(){
			$("#scroll").empty();
			$("#progressTitle").empty();
			$("#progressInfo").empty();
			$("#viewInfoWindow").show();
			$("#closeInfoWindow").hide();
			$("#progressbar").css("width","0px");
			var left = ($(document).width()-600)/2;
			$("#tips").css({"left":left+"px","top":40+"px"});
			$("#tips").fadeIn("slow");
		}
    </script>
  </head>
  <body>
<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>信息类导入列表<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
	<div>
		<button type="button" id="highImport" onclick="highImport();">高级导入</button>
	</div>
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<c:forEach end="2" items="${subList}" var="list" varStatus="st">
						<td>信息类</td>
						<td>操作</td>
						</c:forEach>
					</tr>
				</thead>
				<tbody id="list_body" >
						<s:iterator value="subList" var="p" status="st">
							<tr name="tr">
										<c:forEach begin="0" end="2" var="idx" varStatus="topSt">
										<c:set value="${p[topSt.index]}" var="model" />
										<td><c:if test="${!empty model}">${model.name }</c:if></td>
										<td><c:if test="${!empty model}"><input type="hidden" id="classId" value="${model.guid }"/>
											  <div>
										      	<div class="current_item">
										        	<span class="item_text">模板下载</span>
										        </div>
										        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
										            <ul>
										                <li><a name="download" href="#" class="first1">模板下载</a></li>
										                <li><a name="upload" href="#" class="last1">数据导入</a></li>
										            </ul>
										        </div>
										      </div></c:if>
										</td>
										</c:forEach>
							</tr>
						</s:iterator>
				</tbody>	
  	</table>
  	</div>
	</div>
	<div id="tips" style="position:absolute;z-index:2000;display:none;background: none repeat scroll 0 0 #ffffff">
	<div class="readme" style="margin:0px;" style="width:640px">
	  <h2>操作信息</h2>
	  <div style="height:370px;width:640px;overflow-x:hidden;overflow-y:auto;">
	  <ul id="scroll">
	  </ul>
	  </div>
	  <table style="width:640px;">
		  <tr style="background:#E8F0FB;">
		  <td width="70px">
		  <div style="float:left;margin:5px">
			<font id="progressTitle"></font>
		  </div>
		  </td>
		  <td width="400px">
		  <div style="border: 1px solid #AAAAAA;background:#ffffff;height:20px">
		  	<div id="progressbar" class="progressbar" style="width:0px"></div>
		  </div>
		  </td>
		  <td width="70px">
		  <div style="float:left;margin:5px">
			<font id="progressInfo"></font>
		  </div>
		  </td>
		  <td>
		  <div style="float:right;margin:5px">
			<button id="closeInfoWindow" name="close">关     闭</button>
			<button id="viewInfoWindow" >处理中</button>
		  </div>
		  </td>
		  </tr>
	  </table>
	</div>
	</div>
  </body>
</html>
