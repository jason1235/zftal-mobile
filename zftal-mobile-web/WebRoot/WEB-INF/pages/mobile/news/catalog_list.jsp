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
		<%--<div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">增加</a>
						
                            <a id="btn_xg" class="btn_xg">修改</a>
                        
							<a id="btn_sc" class="btn_sc">删除</a>
						
							<a id="btn_qy" class="btn_qd">启用</a>
						
							<a id="btn_ty" class="btn_zant">停用</a>
						</li>
					</ul>
				</div>
		  
		</div>
		 --%>
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
	    
	    <%--
	    <div class="modal fade" id="myModal" tabindex="-1">
	       <div class="modal-dialog">
	           <div class="modal-content">
	           <div class="modal-header">

<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

<h4 class="modal-title" id="myModalLabel">Modal title</h4>

</div>

<div class="modal-body"> One fine body&hellip; </div>

<div class="modal-footer">

<button type="button" class="btn btn-default" id="close">Close</button>

<button type="button" class="btn btn-primary" id="save">Save changes</button>

</div>
	           </div>
	       </div>
	    </div>
	     --%>
	    
	    <form id="form_list" role="form" action="<%=request.getContextPath() %>/zxgl/newsCatalog_list.html" method="post">
	        <div class="form-horizontal sl_all_form ">
	        <input type="hidden" id="sortFieldName" name="sortFieldName" value=""/>
	        <input type="hidden" id="asc" name="asc" value=""/>
	        <div class="row">
		        <div class="col-xs-3">
		            <div class="form-group">
		                <label for="" class="col-xs-5 control-label">类别名称</label>
		                <div class="col-xs-7">
	                        <s:textfield name="query.catalogName" cssClass="form-control" theme="simple" maxlength="10" placeholder="最多10个字符"></s:textfield>
	                    </div>
		            </div>
		        </div>
		        <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">使用状态</label>
                        <div class="col-xs-7">
                            <s:select id="status" name="query.status" cssClass="form-control" list="#{'1':'启用','0':'停用' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" ></s:select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">展现形式</label>
                        <div class="col-xs-7">
                            <s:select id="showType" name="query.showType" cssClass="form-control" list="showTypeEnum" listKey="key" listValue="text" headerKey="" headerValue="" theme="simple" ></s:select>
                        </div>
                    </div>
                </div>
                <%-- 
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">来源</label>
                        <div class="col-xs-7">
                            <s:select id="source" name="query.source" cssClass="form-control" list="#{'0':'系统','1':'自定义' }" listKey="key"  listValue="value" headerKey=" " headerValue="" theme="simple" ></s:select>
                        </div>
                    </div>
                </div>
                --%>
	        </div>
	    </div>
	    <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
            </div>
        </div>
        <input name="minIndex" id="minIndex" type="hidden" value=""/>
		<input name="maxIndex" id="maxIndex" type="hidden" value=""/>
		            	<font color="red">(^_^)提示：</font>	拖动条目并保存顺序，可变更移动app端首页各资讯栏目的显示次序，头条栏目不可移动
        <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                   <%-- <th>排序</th> --%>
                    <th>类别编码</th>
                    <th>类别名称</th>
                    <th>创建时间</th>
                    <th>使用状态</th>
                    <th>展现形式</th>
                    <th>来源</th>
                    <th>资讯数量</th>
                   <%-- <th>类别logo地址</th>--%>
                </tr>
			    <tr class="virtal-catalog" title="提示：请切换到“设置”->“资讯设置”中设置头条名称">
			    	<td></td>
			    	<td>TT</td>
			    	<td>${headline.value }</td>
			    	<td></td>
			    	<td><span style="color:green">启用</span></td>
			    	<td>图文</td>
			    	<td>系统</td>
			    	<td>${hc }</td>
			    </tr>
            </thead>
		    <tbody id="list_body" >
		    <s:iterator value="pageList" var="p" status="st">
                <tr class="pxtr">
                    <td>
                    
                    <input name="idckb" type="checkbox" id="id" value="${p.catalogId }"/>
                    <input type="hidden" value="${p.source }" />
                     <input name="ids" type="hidden" value="${p.catalogId }"/>
	                 <input name="pxm" type="hidden" value="${p.pxm}"/>   
                    </td>
                   <%--  <td>${st.index+1 }</td>--%>
                    <td>${p.catalogCode}</td>
                    <td>${p.catalogName}</td>
                    <td>
                    <s:date name="#p.createTime" format="yyyy-MM-dd" /></td>
                    
                    <td>
                        <s:if test="%{#p.status==1 }"><span id="cstatus" style="color:green">启用</span></s:if>
                        <s:if test="%{#p.status==0 }"><span id="cstatus" style="color:red">停用</span></s:if>
                    </td>
                    
                    
                    <td>
                    <s:if test="%{#p.showType=='TELETEXT_SHOW' }">图文</s:if>
                    <s:if test="%{#p.showType=='IMAGE_SHOW' }">图片</s:if>
                    <s:if test="%{#p.showType=='DETAIL_SHOW' }">详情</s:if>
                    <s:if test="%{#p.showType=='VEDIO_SHOW' }">视频</s:if>
                    </td>
                    
                    <td>
                        <s:if test="#p.source==0">系统</s:if>
                        <s:else>自定义</s:else>
                    </td>
                    <td>
                    <s:if test="#p.ct == null">
                        0
                    </s:if>
                    <s:else>
                        ${p.ct}
                    </s:else>
                    </td>
                    <%--<td>${p.logoUrl}</td>--%>
            
                    
                    </tr>
                </s:iterator>
		    </tbody>
        </table>
        <ct:page pageList="${pageList }" query="${query }" />
	    </form>
	    </div>
	    <%--
		<form id="form_list" action="<%=request.getContextPath() %>/zxgl/newsCatalog_list.html" method="post">
			<input type="hidden" id="sortFieldName" name="sortFieldName" value=""/>
			<input type="hidden" id="asc" name="asc" value=""/>
    <div class="searchtab">
            		<table width="100%" border="0">
						<tbody>
							<tr>
								
								<td>
									类别名称
								</td>
								<td>
									<s:textfield name="query.catalogName" theme="simple"></s:textfield>
<!--									<input type="text" id="name" name="query.catalogName" value="${query.catalogName }" maxlength="20" style="width:135px" /> -->
								</td>
								
								<td>
									使用状态
								</td>
								<td>
									<s:select id="status" name="query.status" list="#{'1':'启用','0':'停用' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
								</td>
								
								<td>
									展现形式
								</td>
								<td>
									<s:select id="showType" name="query.showType" list="showTypeEnum" listKey="key" listValue="text" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
								</td>
								
								<td>
									来源
								</td>
								<td>
								<s:select id="source" name="query.source" list="#{'0':'系统','1':'自定义' }" listKey="key"  listValue="value" headerKey=" " headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
									
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
    	<span>资讯类别管理<font color="#0457A7" style="font-weight:normal;"> </font></span>
    </h3>
<!--标题end-->
<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td><input type="checkbox" disabled/></td>
						<td width="10%">排序</td>
						<td width="10%">类别编码</td>
						<td width="10%">类别名称</td>
						<td width="10%">创建时间</td>
						<td width="10%">使用状态</td>
						<td width="10%">展现形式</td>
						<td width="10%">来源</td>
						<td width="10%">资讯数量</td>
						<td width="10%">类别logo地址</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="p" status="st">
					<tr>
						<td>
						
						<input name="idckb" type="checkbox" id="id" value="${p.catalogId }"/>
						<input type="hidden" value="${p.source }" />
							
						</td>
						<td width="10%">${st.index+1 }</td>
						<td width="10%">${p.catalogCode}</td>
						<td width="10%">${p.catalogName}</td>
						<td width="10%">
						<s:date name="#p.createTime" format="yyyy-MM-dd" /></td>
						
						<td id="cstatus" width="10%">
							<s:if test="%{#p.status==1 }">启用</s:if>
							<s:if test="%{#p.status==0 }">停用</s:if>
						</td>
						
						
						<td width="10%">
						<s:if test="%{#p.showType=='TELETEXT_SHOW' }">图文</s:if>
						<s:if test="%{#p.showType=='IMAGE_SHOW' }">图片</s:if>
						<s:if test="%{#p.showType=='DETAIL_SHOW' }">详情</s:if>
						</td>
						
						<td width="10%">
							<s:if test="#p.source==0">系统</s:if>
							<s:else>自定义</s:else>
						</td>
						<td width="10%">
						<s:if test="#p.ct == null">
							0
						</s:if>
						<s:else>
							${p.ct}
						</s:else>
						</td>
						<td width="10%">${p.logoUrl}</td>
				
						
						</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" query="${query }" />
  </div>
  </div>
		</form> --%>
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
				//$(this).find('input').attr('checked','checked');
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
				$('.pxtr').bind('dblclick',function(){
					var id = $(this).find('input').val();
					var url = "<%=request.getContextPath()%>/zxgl/newsCatalog_modify.html?query.catalogId=" + id;
                    //var data = {"query.catalogId": id};
                    $("#myModal_toji").find(".modal-content").width(750);
                    $("#myModal_toji").find(".modal-content").height(heightOffset+70);
                    $("#myFrame").attr("src", url);
                    //$.post(url, data, function(res) {
                    //    $("#myFrame").html(res);
                        $("#myModal_toji").modal("show");
					//showWindow("修改","<%=request.getContextPath()%>/zxgl/newsCatalog_modify.html?query.catalogId=" + id, 750,heightOffset+300);
				});
			});
		    //heightOffset+=230;
		    $("#btn_zj").click(function(){
		        var url = "<%=request.getContextPath()%>/zxgl/newsCatalog_add.html";
		        var data = {};
		        $("#myModal_toji").find(".modal-content").width(750);
		        $("#myModal_toji").find(".modal-content").height(290);
		        $("#myFrame").attr("src", url);
		        $("#myFrame").height(290);
		        //$.post(url, data, function(res) {
		        //    $("#myFrame").html(res);
		            $("#myModal_toji").modal("show");
		        //});
					//showWindow("增加","<%=request.getContextPath()%>/zxgl/newsCatalog_add.html", 750,heightOffset+300);
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
                    var url = "<%=request.getContextPath()%>/zxgl/newsCatalog_modify.html?query.catalogId=" + id;
                    //var data = {"query.catalogId": id};
                    $("#myModal_toji").find(".modal-content").width(750);
                    $("#myModal_toji").find(".modal-content").height(270);
                    $("#myFrame").attr("src", url);
                    $("#myFrame").height(270);
                    //$.post(url, data, function(res) {
                    //    $("#myFrame").html(res);
                        $("#myModal_toji").modal("show");
                    //});
					//showWindow("修改","<%=request.getContextPath()%>/zxgl/newsCatalog_modify.html?query.catalogId=" + id, 750,heightOffset+300);
				});
				
				function doUpdateIndex() {
					
					var action = $('#form_list').attr('action');
					$('#form_list').attr('action','<%=request.getContextPath()%>/zxgl/newsCatalog_updateIndex.html');
					$('#form_list').submit();
					$('#form_list').attr('action',action);
				}
				
				function doUpdate(obj) {
					var id = obj;
					  var url = "<%=request.getContextPath()%>/zxgl/newsCatalog_modify.html?query.catalogId=" + id;
                    //var data = {"query.catalogId": id};
                    $("#myModal_toji").find(".modal-content").width(750);
                    $("#myModal_toji").find(".modal-content").height(heightOffset+90);
                    $("#myFrame").attr("src", url);
                    //$.post(url, data, function(res) {
                    //    $("#myFrame").html(res);
                        $("#myModal_toji").modal("show");
					//showWindow("修改","<%=request.getContextPath()%>/zxgl/newsCatalog_modify.html?query.catalogId=" + id, 750,heightOffset+300);
				}
				function doDelete(obj) {
					var id = obj;
					showConfirm("确定要删除吗？");
					$("#why_cancel").click(function(){
						alertDivClose();
					});
					

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/zxgl/newsCatalog_delete.html?query.catalogId="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							processDataCall(data, callback);
						}, "json");
					});
				}
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
                    //showConfirm("确定要删除吗？");
                    openAlert("确定要删除吗？", "w");

					//$("#why_cancel").click(function(){
					//	alertDivClose();
					//});
					

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/zxgl/newsCatalog_delete.html?query.catalogId="+id + "&query.source=" + source, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							//processDataCall(data, callback);
							processCall(data, callback);
						}, "json");
					});
				});
				$("#cz_btn").click(function(){
					$("#code").val('');
					$("#name").val('');
					$("#status").val('');
					//$("#status").find("option[text='全部']").attr("selected",true);
				});
				
				$('#btn_qy').click(function(){
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
					$.post("<%=request.getContextPath() %>/zxgl/newsCatalog_enable.html?query.catalogId="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							//processDataCall(data, callback);
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
					$.post("<%=request.getContextPath() %>/zxgl/newsCatalog_disable.html?query.catalogId="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							//processDataCall(data, callback);
							processCall(data, callback);
						}, "json");
				})
				
				//fillRows("15", "", "", false);//填充空行
		</script>
	</body>
</html>
