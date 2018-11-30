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
	          <button type="button" class="btn btn-default" id="btn_save_index" onclick="doUpdateIndex()">保存顺序</button>
	        </div>
	    </div>
	    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/zxgl/schoolScenery_list.html" method="post">
	        <div class="form-horizontal sl_all_form ">
	        <div class="row">
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">名称</label>
		                <div class="col-xs-7">
	                        <s:textfield name="query.sceneryName" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
	                    </div>
		            </div>
		        </div>
		        <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">使用状态</label>
                        <div class="col-xs-7">
                            <s:select  name="query.isActive" cssClass="form-control" list="#{'1':'启用','0':'停用' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" ></s:select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">风景类别</label>
                        <div class="col-xs-7">
                        	<select id="status" name="query.sceneryCatalogId" class="form-control" value="${query.sceneryCatalogId }" >
                                    <option value="" ></option>
                                 <c:forEach var="catalogItem" items="${sceneryCatalogList}" varStatus="st">
                                    <option value="${catalogItem.sceneryCatalogId}" <c:if test = "${query.sceneryCatalogId eq catalogItem.sceneryCatalogId}"> selected="selected"</c:if>>${catalogItem.sceneryCatalogName}</option>
                                 </c:forEach>
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
		            	<font color="red">(^_^)提示：</font>	拖动条目并保存顺序，可变更移动学校风景列表栏目的显示次序
        <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>名称</th>
                    <th>所属类别</th>
                    <th>创建时间</th>
                    <th>使用状态</th>
                </tr>
            </thead>
		    <tbody id="list_body" >
		    <s:iterator value="list" var="p" status="st">
                <tr class="pxtr">
                    <td>
                    
                    <input name="sceneryId" type="checkbox" id="id" value="${p.sceneryId }"/>
                    <input name="status" type="hidden" value="${p.isActive }" />
                     <input name="ids" type="hidden" value="${p.sceneryId }"/>
	                 <input name="sortNumber" type="hidden" value="${p.sortNumber}"/>   
                    </td>
                    <td>${p.sceneryName}</td>
                    <td>
                   	  <c:forEach items="${sceneryCatalogList}" var="catalogItem"> 
                            <c:if test="${p.sceneryCatalogId eq catalogItem.sceneryCatalogId }">${catalogItem.sceneryCatalogName}</c:if>
                      </c:forEach> 
                    </td>
                    <td>
                    <s:date name="#p.createTime" format="yyyy-MM-dd" />
                    </td>
                    
                    <td>
                        <s:if test="%{#p.isActive==1 }"><span id="cstatus" style="color:green">启用</span></s:if>
                        <s:if test="%{#p.isActive==0 }"><span id="cstatus" style="color:red">停用</span></s:if>
                    </td>
                    
            
                    
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
			$('#minIndex').val($('.pxtr:first').find('[name=sortNumber]').val());
			$('#maxIndex').val($('.pxtr:last').find('[name=sortNumber]').val());
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
					//$(this).toggleClass('current').siblings().removeClass('current');	
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
			});
		    //heightOffset+=230;
		    $("#btn_zj").click(function(){
		        var url = "<%=request.getContextPath()%>/zxgl/schoolScenery_edit.html";
		        $("#myModal_toji").find(".modal-content").width(760);
		        $("#myModal_toji").find(".modal-content").height(555);
		        $("#myFrame").attr("src", url);
		        $("#myFrame").height(555);
	            $("#myModal_toji").modal("show");
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
                    var url = "<%=request.getContextPath()%>/zxgl/schoolScenery_edit.html?query.sceneryId=" + id;
                    $("#myModal_toji").find(".modal-content").width(760);
                    $("#myModal_toji").find(".modal-content").height(555);
                    $("#myFrame").attr("src", url);
                    $("#myFrame").height(555);
                    $("#myModal_toji").modal("show");
				});
				
				function doUpdateIndex() {
					var action = $('#form_list').attr('action');
					$('#form_list').attr('action','<%=request.getContextPath()%>/zxgl/schoolScenery_updateIndex.html');
					$('#form_list').submit();
					$('#form_list').attr('action',action);
				}
				
		     $("#btn_sc").click(function(){
					var checked=$("input[id='id']:checked");
                    if(checked.length==0){
                        openAlert("请选择行", "e");
                        return false;
                    }else if(checked.length>1){
                        openAlert("只能对单行进行操作", "e");
                        return false;
                    }
                    
                    var status = checked.parent().find('input[name="status"]').val();
                    if(status == "1"){
                    	openAlert("该记录已经启用，请先停用记录再删除", "e");
                        return false;
                    }
					var id = checked.val();
                    openAlert("确定要删除吗？", "w");
					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/zxgl/schoolScenery_delete.html?query.sceneryId="+id , null, function(data){
							var callback = function(){
								location.reload();
							};
							processCall(data, callback);
						}, "json");
					});
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
                    var status = checked.parent().find('input[name="status"]').val();
                    if(status == "1"){
                    	openAlert("该记录已经启用", "e");
                        return false;
                    }
                    
					var id = checked.val();
					$.post("<%=request.getContextPath() %>/zxgl/schoolScenery_updateActive.html?query.sceneryId="+id+"&query.isActive=1", null, function(data){
							var callback = function(){
								location.reload();
							};
							processCall(data, callback);
					}, "json");
				})
				$('#btn_ty').click(function(){
				var checked=$("input[id='id']:checked");
                    if(checked.length==0){
                        openAlert("请选择行", "e");
                        return false;
                    }else if(checked.length>1){
                        openAlert("只能对单行进行操作", "e");
                        return false;
                    }
                    var status = checked.parent().find('input[name="status"]').val();
                    if(status == "0"){
                    	openAlert("该记录已经停用", "e");
                        return false;
                    }
                    
					var id = checked.val();
					$.post("<%=request.getContextPath() %>/zxgl/schoolScenery_updateActive.html?query.sceneryId="+id+"&query.isActive=0", null, function(data){
							var callback = function(){
								location.reload();
							};
							processCall(data, callback);
					}, "json");
				})
				
		</script>
	</body>
</html>
