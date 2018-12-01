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
	    
	    <form id="form_list"   role="form" action="<%=request.getContextPath() %>/htballot/htballot_ballotDetailList.html" method="post">
	    	<input type="hidden" id="ballotId" name="ballotDetailQuery.ballotId" value="${ballotId}"/>
	        <div class="form-horizontal sl_all_form ">
	        <div class="row">
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">候选名称</label>
		                <div class="col-xs-7">
	                        <s:textfield name="ballotDetailQuery.name" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
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
                    <th>候选编号</th>
                    <th>候选名称</th>
                    <th>候选图片</th>
                    <th>获得票数</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    <s:iterator value="list" var="p" status="st">
                <tr class="pxtr">
                    <td>
                    <input name="idckb" type="checkbox" id="id" value="${p.id }"/>
                    </td>
                    <td>${p.identifier}</td>
                    <td>${p.name}</td>
                    <td><img src="<%=request.getContextPath() %>/${p.baseImgPath}" style="height: 30px;width: 30px;"/></td>
                    <td>${p.count}</td>
                    </tr>
                </s:iterator>
		    </tbody>
        </table>
        
        <ct:page pageList="${list }" query="${ballotDetailQuery }" queryName="ballotDetailQuery" />
        
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
			
			
			
		    $("#btn_zj").click(function(){
		        var id = $("#ballotId").val();
		        var url = "<%=request.getContextPath()%>/htballot/htballot_toAddDetail.html?id="+id;
		        window.location.href=url;
		       /* var data = {};
		        $("#myModal_toji").find(".modal-content").width(750);
		        $("#myModal_toji").find(".modal-content").height(435);
		        $("#myFrame").attr("src", url);
		        $("#myFrame").height(435);
		        $("#myModal_toji").modal("show");*/
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
                var url = "<%=request.getContextPath()%>/htballot/htballot_updateBallotDetail.html?detailId=" + id;
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
					$.post("<%=request.getContextPath() %>/htballot/htballot_deleteBallotDetail.html?detailId="+id, null, function(data){
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
