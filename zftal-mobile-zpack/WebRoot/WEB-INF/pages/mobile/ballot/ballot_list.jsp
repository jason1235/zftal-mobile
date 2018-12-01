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
	          <button type="button" class="btn btn-default" id="btn_qy">启用</button>
	          <button type="button" class="btn btn-default" id="btn_ty">停用</button>
	          <button type="button" class="btn btn-default" id="btn_ck">查看候选项</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/htballot/htballot_htballotList.html" method="post">
	        <div class="form-horizontal sl_all_form ">
	        <div class="row">
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">投票标题</label>
		                <div class="col-xs-7">
	                        <s:textfield name="ballotQuery.name" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
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
                    <th>投票标题</th>
                    <th>缩略图</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>投票规则</th>
                    <th>每人可投票数</th>
                    <th>状态</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    <s:iterator value="list" var="p" status="st">
                <tr class="pxtr">
                    <td>
                    
                    <input name="idckb" type="checkbox" id="id" value="${p.id }"/>
                    </td>
                    <td>${p.name}</td>
                    <td><img src="<%=request.getContextPath() %>/${p.baseImgPath}" style="height: 30px;width: 30px;"/></td>
                    <td>${p.startTime}</td>
                    <td>${p.endTime}</td>
                    <td>
                        <s:if test="%{#p.ruleType == 1}">每人每天可投票</s:if>
                        <s:if test="%{#p.ruleType == 2}">每人固定票数</s:if>
                    </td>
                    <td>${p.ballotNum}</td>
                    <td>
                        <s:if test="%{#p.enable==1 }"><span id="cstatus" style="color:green">启用</span></s:if>
                        <s:if test="%{#p.enable==0 }"><span id="cstatus" style="color:red">停用</span></s:if>
                    </td>
                    </tr>
                </s:iterator>
		    </tbody>
        </table>
        
        <ct:page pageList="${list }" query="${ballotQuery }" queryName="ballotQuery" />
        
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
				$('.pxtr').find('input[type="checkbox"]').bind('click',function(e){
					e.stopPropagation();
					showMenu($(this).parent().parent());
					if (!$(this).prop("checked")) {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
					}
					var $that = $(this);
					$("#list_body").find('input[type="checkbox"]').each(function(){
					
						if ($that.val() != $(this).val()) {
							$(this).removeAttr('checked');
							$(this).parent().parent().removeClass('current');
						}
					});
					
					
					$(this).parent().parent().toggleClass('current');
				});
				$('.pxtr').bind('click',function(e){
				showMenu($(this));
				if ($(this).find('input').length < 1) {
					$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
				}
				var status = $(this).find('input[type="checkbox"]').prop('checked');
				
				if (status) {
					$(this).find('input[type="checkbox"]').prop('checked',false);
					$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
				} else {
					$(this).find('input[type="checkbox"]').prop('checked',true);
					
				}
				$(this).siblings().find('input[type="checkbox"]').prop('checked',false);
				});
				
				function showMenu(obj) {
					var cstatus = obj.find('#cstatus').text();
					cstatus = (cstatus==null?'':trim(cstatus));
					if (cstatus == '停用') {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','none');
					} else {
						$('#btn_qy').css('display','none');
						$('#btn_sc').css('display','none');
						$('#btn_ty').css('display','');
					}
				};
				$('.pxtr').bind('dblclick',function(){
					var id = $(this).find('input').val();
					var url = "<%=request.getContextPath()%>/zxgl/newsCatalog_modify.html?query.catalogId=" + id;
				});
			});
			
			
			
		    $("#btn_zj").click(function(){
		        var url = "<%=request.getContextPath()%>/htballot/htballot_toAdd.html";
		        window.location.href=url;
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
                    var url = "<%=request.getContextPath()%>/htballot/htballot_updateBallot.html?id=" + id;
                     window.location.href=url;
				});
				
				
		     $("#btn_sc").click(function(){
					var checked=$("input[id='id']:checked");
                    if(checked.length==0){
                        //alert("请选择行");
                        openAlert("请选择行", "e");
                        return false;
                    }else if(checked.length>1){
                        //alert("只能对单行进行操作");
                        openAlert("只能对单行进行操作", "e");
                        return false;
                    }
                    
					var id = checked.val();
					var source = checked.parent().find('input[type="hidden"]').val();
                    openAlert("确定要删除吗？", "w");

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/htballot/htballot_deleteBallot.html?id="+id, null, function(data){
							var callback = function(){
								location.reload();
							};
							
							processCall(data, callback);
						}, "json");
					});
				});
				$("#cz_btn").click(function(){
					$("#code").val('');
					$("#name").val('');
					$("#status").val('');
				});
				
				$('#btn_ck').click(function(){
					var checked=$("input[id='id']:checked");
                    if(checked.length==0){
                        openAlert("请选择行", "e");
                        return false;
                    }else if(checked.length>1){
                        openAlert("只能对单行进行操作", "e");
                        return false;
                    }
                    var id = checked.val();
                    var url = "<%=request.getContextPath()%>/htballot/htballot_ballotDetailList.html?ballotDetailQuery.ballotId=" + id;
                    window.location.href=url;
                    
				});
				
				$('#btn_qy').click(function(){
				var checked=$("input[id='id']:checked");
                    if(checked.length==0){
                        openAlert("请选择行", "e");
                        return false;
                    }else if(checked.length>1){
                        openAlert("只能对单行进行操作", "e");
                        return false;
                    }
                    
					var id = checked.val();
					$.post("<%=request.getContextPath() %>/htballot/htballot_enable.html?ballot.id="+id+"&ballot.enable="+1, null, function(data){
							var callback = function(){
								location.reload();
							};
							processCall(data, callback);
						}, "json");
				})
				$('#btn_ty').click(function(){
				var checked=$("input[id='id']:checked");
                    if(checked.length==0){
                        //alert("请选择行");
                        openAlert("请选择行", "e");
                        return false;
                    }else if(checked.length>1){
                        //alert("只能对单行进行操作");	
                        openAlert("只能对单行进行操作", "e");
                        return false;
                    }
                    
					var id = checked.val();
					$.post("<%=request.getContextPath() %>/htballot/htballot_enable.html?ballot.id="+id+"&ballot.enable="+0, null, function(data){
							var callback = function(){
								location.reload();
							};
							processCall(data, callback);
						}, "json");
				})
				
		</script>
	</body>
</html>
