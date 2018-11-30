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
	          <button type="button" class="btn btn-default" id="btn_zj">增加</button>
	          <button type="button" class="btn btn-default" id="btn_xg">修改</button>
	          <button type="button" class="btn btn-default" id="btn_sc">删除</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	   
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/zxgl/newsCatalog_list.html" method="post">
		            	<font color="red">(^_^)提示：</font>	拖动条目并保存顺序，可变更移动app端首页各资讯栏目的显示次序，头条栏目不可移动
        <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>类别</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    	<s:iterator value="list" var="p" status="st">
	                <tr class="pxtr">
	                    <td>
	                    	<input name="idckb" type="checkbox" id="id" value="${p.id }"/>
	                    </td>
	                    <td>${p.name }</td>
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
				new Sortable(document.getElementById("list_body"),{group:"items",animation:150});
				$('.pxtr').bind('dblclick',function(){
					var id = $(this).find('input').val();
					var url = "<%=request.getContextPath()%>/reportFix/reportFix_toUpdateType.html?fixTypeQuery.id=" + id;
                    $("#myModal_toji").find(".modal-content").width(750);
                    $("#myModal_toji").find(".modal-content").height(heightOffset+70);
                    $("#myFrame").attr("src", url);
                        $("#myModal_toji").modal("show");
				});
			});
			
		    //增加按钮
		    $("#btn_zj").click(function(){
		        var url = "<%=request.getContextPath()%>/reportFix/reportFix_toAdd.html";
		        var data = {};
		        $("#myModal_toji").find(".modal-content").width(750);
		        $("#myModal_toji").find(".modal-content").height(200);
		        $("#myFrame").attr("src", url);
		        $("#myFrame").height(200);
		        $("#myModal_toji").modal("show");
			});
			//修改按钮
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
                    var url = "<%=request.getContextPath()%>/reportFix/reportFix_toUpdateType.html?fixTypeQuery.id=" + id;
                    $("#myModal_toji").find(".modal-content").width(750);
                    $("#myModal_toji").find(".modal-content").height(200);
                    $("#myFrame").attr("src", url);
                    $("#myFrame").height(200);
                        $("#myModal_toji").modal("show");
				});
				
				//删除按钮
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
						$.post("<%=request.getContextPath() %>/reportFix/reportFix_deleteFixType.html?fixTypeQuery.id="+id, null, function(data){
							var callback = function(){
								location.reload();
							};
							processCall(data, callback);
						}, "json");
					});
				});
		</script>
	</body>
</html>
