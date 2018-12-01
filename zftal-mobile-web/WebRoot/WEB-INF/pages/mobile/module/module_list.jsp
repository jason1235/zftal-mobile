<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/hrm/head_v5.ini" %>
	</head>
	<body>
	    <div class="container_ydht sl_all_bg">
	        <div class="toolbox">
                <div class="btn-group" id="but_ancd">
                    <button type="button" class="btn btn-default" id="btn_zj">增加</button>
                    <button type="button" class="btn btn-default" id="btn_xg">修改</button>
                    <button type="button" class="btn btn-default" id="btn_sc" onclick="doControl('delete')">删除</button>
                    <button type="button" class="btn btn-default" id="btn_qy" onclick="doControl('query.status=1')">启用</button>
                    <button type="button" class="btn btn-default" id="btn_ty" onclick="doControl('ty')">停用</button>
                    </div>
            </div>
            <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	        
	        <form id="form_list" action="<%=request.getContextPath() %>/mkgl/module_list.html" method="post">
	            <div class="form-horizontal sl_all_form">
		            <input type="hidden" id="sortFieldName" name="sortFieldName" value=""/>
		            <input type="hidden" id="asc" name="asc" value=""/>
		            <div class="row">
		                <div class="col-xs-4">
		                    <div class="form-group">
		                        <label for="" class="col-xs-5 control-label">名称</label>
		                        <div class="col-xs-7">
		                            <s:textfield name="query.name" label="" cssClass="form-control" theme="simple"></s:textfield>
		                        </div>
		                    </div>
		                </div>
		                <div class="col-xs-4">
		                    <div class="form-group">
		                        <label for="" class="col-xs-5 control-label">使用状态</label>
		                        <div class="col-xs-7">
		                            <s:select id="status" name="query.status" cssClass="form-control" list="#{'1':'启用','0':'停用' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" ></s:select>
		                        </div>
		                    </div>
		                </div>
		                <div class="col-xs-4">
		                    <div class="form-group">
		                        
		                    </div>
		                </div>
		            </div>
		        </div>
		        <div class="row sl_aff_btn">
		            <div class="col-xs-12">
		                <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
		            </div>
		        </div>
		        
		        <table class="table table-striped sl_all_bg table-bordered text-left">
		            <thead id="list_head">
		                <tr>
		                    <th style="width:64px;">选择</th>
		                    <th>模块名称</th>
		                    <th>模块ID</th>
		                    <th>模块类地址</th>
		                    <th>模块地址</th>
		                    <th>使用状态</th>
		                    <th>模块图标地址</th>
		                </tr>
		            </thead>
		            <tbody id="list_body" >
			            <s:iterator value="pageList" var="p" status="st">
	                    <tr>
	                        <td>
	                            <input name="idckb" type="checkbox" id="id" value="${p.id }"/>
	                        </td>
	                        <td>${p.name}</td>
	                        
	                        <td>${p.moduleId}</td>
	                        
	                        <td>${p.classPath }</td>
	                        <td>${p.url }</td>
	                        
	                        <td id="tdstatus">
	                        <s:if test="%{#p.status==1 }">启用</s:if>
	                        <s:if test="%{#p.status==0 }">停用</s:if>
	                        </td>
	                        <td>${p.iconUrl}</td>
	                        </tr>
	                    </s:iterator>
		            </tbody>
		        </table>
		        <ct:page pageList="${pageList }" query="${query }" />
	        </form>
	    </div>
		<%--<div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">增加</a>
						
                            <a id="btn_xg" class="btn_xg">修改</a>
                        
							<a id="btn_sc" onclick="doControl('delete')" class="btn_sc">删除</a>
						
							<a id="btn_qy" onclick="doControl('query.status=1')" class="btn_qd">启用</a>
						
							<a id="btn_ty" onclick="doControl('ty')" class="btn_zant">停用</a>
						
						</li>
					</ul>
				</div>
		  
		</div>
		<form id="form_list" action="<%=request.getContextPath() %>/mkgl/module_list.html" method="post">
			<input type="hidden" id="sortFieldName" name="sortFieldName" value=""/>
			<input type="hidden" id="asc" name="asc" value=""/>
    <div class="searchtab">
            		<table border="0">
						<tbody>
							<tr>
								
								<td style="width:5%">名称</td>
								<td style="width:10%">
									<s:textfield name="query.name" label="" theme="simple"></s:textfield> 
								
								</td>
								
								
								<td style="width:8%">
									使用状态
								</td>
								<td>
									<s:select id="status" name="query.status" list="#{'1':'启用','0':'停用' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
								</td>
								
								</tr>
								
						</tbody>
					</table>
					<table width="100%" border="0">
						<tfoot>
				          <tr>
				            <td colspan="8">
				              <div class="btn">
				                <button class="btn_cx" type="submit" id="search_go">查 询</button>
				              </div>
				           </td>
				          </tr>
				       </tfoot>
					</table>
					
            	</div>
  <div class="formbox">
  	<!--标题start-->
    <h3 class="datetitle_01">
    	<span>模块管理<font color="#0457A7" style="font-weight:normal;"> </font></span>
    </h3>
<!--标题end-->
<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td><input type="checkbox" disabled/></td>
						<td>模块名称</td>
						<td>模块ID</td>
						<td>模块类地址</td>
						<td>模块地址</td>
						<td>使用状态</td>
						<td>模块图标地址</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="p" status="st">
					<tr>
						<td>
							<input name="idckb" type="checkbox" id="id" value="${p.id }"/>
						</td>
						<td>${p.name}</td>
						
						<td>${p.moduleId}</td>
						
						<td>${p.classPath }</td>
						<td>${p.url }</td>
						
						<td id="tdstatus">
						<s:if test="%{#p.status==1 }">启用</s:if>
						<s:if test="%{#p.status==0 }">停用</s:if>
						</td>
						
						<td>${p.iconUrl}</td>
				
						
						</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" query="${query }" />
  </div>
		</form> --%>
		<script type="text/javascript">
		var heightOffset= 500;
			$(function(){
			var enableMsg = '${enableMsg}';
			if (enableMsg != '') {
				alert(enableMsg);
			}
				$('#list_body tr').find('input').bind('click',function(e){
					e.stopPropagation();
					var tdstatus = $(this).parent().parent().find('#tdstatus').html();
					
					tdstatus = (tdstatus==null?'':trim(tdstatus));
					if (tdstatus == '启用') {
						$('#btn_qy').css('display','none');
						$('#btn_sc').css('display','none');
						$('#btn_ty').css('display','');
					} else if (tdstatus == '停用') {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','none');
					} else {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
					}
					if ($(this).prop("checked")) {
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
				$('#list_body tr').bind('click',function(e){
				
				var tdstatus = $(this).find('#tdstatus').html();
					tdstatus = (tdstatus==null?'':trim(tdstatus));
					
					if (tdstatus == '启用') {
						$('#btn_qy').css('display','none');
						$('#btn_sc').css('display','none');
						$('#btn_ty').css('display','');
					} else if (tdstatus == '停用') {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','none');
					} else {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
					}
				
				var status = $(this).find("input[type='checkbox']").prop('checked');
				if (status) {
					$(this).find("input[type='checkbox']").removeAttr('checked');
					$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
				} else {
					$(this).find("input[type='checkbox']").attr('checked',true);
				}
				
				//$(this).find("input[type='checkbox']").attr('checked','checked');
					$(this).siblings().find('input[id="id"]').removeAttr('checked');
					$(this).toggleClass('current').siblings().removeClass('current');
					
					
					
				});
				$('#list_body tr').bind('dblclick',function(){
					
                    var id = $(this).find("input[id='id']").val();
                    showWindow("修改","<%=request.getContextPath()%>/mkgl/module_modify.html?query.id=" + id, 750,heightOffset);
				});
			});
		    //heightOffset+=230;
		    $("#btn_zj").click(function(){
		    	var url = "<%=request.getContextPath()%>/mkgl/module_add.html";
                var data = {};
                $("#myModal_toji").find(".modal-content").width(750);
                $("#myModal_toji").find(".modal-content").height(heightOffset);
                $("#myFrame").attr("src", url);
                $("#myModal_toji").modal("show");
					//showWindow("增加","<%=request.getContextPath()%>/mkgl/module_add.html", 750,heightOffset);
			});
			 $("#btn_xg").click(function(){
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
                    var url = "<%=request.getContextPath()%>/mkgl/module_modify.html?query.id=" + id;
                    $("#myModal_toji").find(".modal-content").width(750);
                    $("#myModal_toji").find(".modal-content").height(heightOffset);
                    $("#myFrame").attr("src", url);
                    $("#myModal_toji").modal("show");
					//showWindow("修改","<%=request.getContextPath()%>/mkgl/module_modify.html?query.id=" + id, 750,heightOffset);
				});
				function doUpdate(obj) {
					var id = obj;
					showWindow("修改","<%=request.getContextPath()%>/mkgl/module_modify.html?query.id=" + id, 750,heightOffset);
				}
				function doDelete(obj) {
					var id = obj;
					showConfirm("确定要删除吗？");
					$("#why_cancel").click(function(){
						alertDivClose();
					});
					

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/mkgl/module_delete.html?query.id="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							processDataCall(data, callback);
						}, "json");
					});
				}
		     
				
				function doControl(arg) {
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
					
					if (arg == "ty") {
					arg = 'query.status=0';
						//showConfirm("确定要停用吗？");
						openAlert("确定要停用吗？", "w");
					}
					else if (arg == "delete") {
					arg = "query.deleted=1";
						//showConfirm("确定要删除吗？");
						openAlert("确定要删除吗？", "w");
					} else {
						$.post("<%=request.getContextPath() %>/mkgl/module_control.html?"+arg+"&query.id="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							//processDataCall(data, callback);
							processCall(data, callback);
						}, "json");
					}
					//$("#why_cancel").click(function(){
					//	alertDivClose();
					//});
					

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/mkgl/module_control.html?"+arg+"&query.id="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							//processDataCall(data, callback);
							processCall(data, callback);
						}, "json");
					});
					
					}
					
				
				
				fillRows("15", "", "", false);//填充空行
		</script>
	</body>
</html>
