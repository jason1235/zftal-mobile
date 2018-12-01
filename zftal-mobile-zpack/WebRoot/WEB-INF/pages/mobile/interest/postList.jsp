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
	          <button type="button" class="btn btn-default" id="btn_xg">查看评论</button>
	          <button type="button" class="btn btn-default" id="btn_sc">删除</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	   
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/interest/interest_htInterestPostList.html" method="post">
		            	
        <table class="table table-striped sl_all_bg table-bordered text-left" style="table-layout: fixed;">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>帖子内容</th>
                    <th>帖子位置信息</th>
                    <th>帖子点赞数</th>
                    <th>帖子评论数</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    	<s:iterator value="list" var="p" status="st">
	                <tr class="pxtr">
	                    <td>
	                    	<input name="idckb" type="checkbox" id="id" value="${p.id }"/>
	                    </td>
	                    <td style="white-space:nowrap;overflow: hidden;text-overflow:ellipsis" title="${p.content }">${p.content }</td>
	                    <td>${p.address }</td>
	                    <td>${p.giveNum }</td>
	                    <td>${p.commentNum }</td>
	                </tr>
                </s:iterator>
		    </tbody>
        </table>
        <ct:page pageList="${list }" query="${interestPostQuery }" />
	    </form>
	    </div>
		<script type="text/javascript">
			
			
			//修改按钮
			$("#btn_xg").click(function(){
			 	if($("input[name='idckb']:checked").length==0){
	              	openAlert("请先选中操作行！", "e");
	              	return false;
	          	}
	      	 	if($("input[name='idckb']:checked").length>1){
	              	openAlert("不能选中多行！", "e");
	              	return false;
	          	}	
	      	 
	      		var id = $("input[name='idckb']:checked").val();
	    	 	var url = _path+"/interest/interest_htCommentList.html?interestCommentQuery.interPostId="+id;
	    	 	
	    	 	$("#myModal_toji").find(".modal-content").width(640);
		        $("#myModal_toji").find(".modal-content").height(520);
		        $("#myFrame").attr("src", url);
		        $("#myFrame").height(520);
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
						$.post("<%=request.getContextPath() %>/interest/interest_delInterestPost.html?interestId="+id, null, function(data){
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
