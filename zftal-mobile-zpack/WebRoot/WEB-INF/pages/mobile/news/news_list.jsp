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
              <button type="button" class="btn btn-default" id="btn_top" onclick="doControl('query.top=1')">置顶</button>
              <button type="button" class="btn btn-default" id="btn_ctop" onclick="doControl('query.top=0')">撤销置顶</button>
              <button type="button" class="btn btn-default" id="btn_recmd" onclick="doControl('query.recommend=1')">推荐</button>
              <button type="button" class="btn btn-default" id="btn_crecmd" onclick="doControl('query.recommend=0')">撤销推荐</button>
              <button type="button" class="btn btn-default" id="btn_head" onclick="doControl('query.headline=1')">广告位</button>
              <button type="button" class="btn btn-default" id="btn_chead" onclick="doControl('query.headline=0')">撤销广告位</button>
            </div>
        </div>
        <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    
	    <form id="form_list" action="<%=request.getContextPath() %>/zxgl/news_list.html" method="post">
	    <div class="form-horizontal sl_all_form">
            <input type="hidden" id="sortFieldName" name="sortFieldName" value=""/>
            <input type="hidden" id="asc" name="asc" value=""/>
            <div class="row">
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">资讯标题</label>
                        <div class="col-xs-7">
                            <s:textfield maxlength="100" placeholder="最多100个字符" name="query.title" cssClass="form-control" theme="simple"></s:textfield>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">资讯类别</label>
                        <div class="col-xs-7">
                            <s:select id="newsCatalog" name="query.catalogCode" list="catalogList" cssClass="form-control" listKey="catalogCode" listValue="catalogName" headerKey=" " headerValue=" " theme="simple"></s:select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">使用状态</label>
                        <div class="col-xs-7">
                            <s:select id="status" name="query.status" list="#{'1':'启用','0':'停用' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
                        </div>
                    </div>
                </div>
                <%--
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">业务系统</label>
                        <div class="col-xs-7">
                            <s:if test="businessList != null">
                                <s:select id="business" name="query.business" cssClass="form-control" list="businessList" listKey="classXtbm" listValue="classXtmc" headerKey="" headerValue="" theme="simple"></s:select>
                            </s:if>
                            <s:else>
                                <select style="width:100px;" id="business" cssClass="form-control" name="query.business"></select>
                            </s:else>
                        </div>
                    </div>
                </div>
            	--%>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">是否置顶</label>
                        <div class="col-xs-7">
                            <s:select id="top" name="query.top" list="#{'1':'是','0':'否' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">是否推荐</label>
                        <div class="col-xs-7">
                            <s:select id="recommend" name="query.recommend" list="#{'1':'是','0':'否' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">是否头条</label>
                        <div class="col-xs-7">
                            <s:select id="headline" name="query.headline" list="#{'1':'是','0':'否' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" ></s:select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">展现形式</label>
                        <div class="col-xs-7">
                            <s:select id="showType" name="query.showType" list="showTypeEnum" cssClass="form-control" listKey="key" listValue="text" headerKey="" headerValue="" theme="simple"></s:select>
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
        
        <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:5%;">选择</th>
                    <th style="width:8%;">资讯标题</th>
                    <th style="width:11%;">创建时间</th>
                    <th style="width:11%;">资讯类别</th>
                    <th style="width:7%;">展现形式</th>
                    <%--<th>业务系统</th>
                    --%>
                    <th style="width:12%;">资讯简介</th>
                    <th style="width:7%;">使用状态</th>
                    <th style="width:7%;">是否置顶</th>
                    <th style="width:7%;">是否推荐</th>
                    <th style="width:7%;">是否广告咨询</th>
                    <th style="width:16%;">资讯logo图片地址</th>
                </tr>
            </thead>
            <tbody id="list_body" >
            <s:iterator value="pageList" var="p" status="st">
                    <s:if test="%{#p.grabMethod==1}">
                        <tr style="color:red">
                    </s:if>
                    <s:else>
                        <tr>
                    </s:else>
                    <input type="hidden" value="${p.catalogCode}" />
                        <td>
                            <input name="idckb" type="checkbox" id="id" value="${p.id }"/>
                        </td>
                        <td width="10%">${p.title}</td>
                        <td width="10%"><s:date name="#p.createTime" format="yyyy-MM-dd" /></td>
                        <td id="catalogName" width="10%">${p.catalogName}</td>
                        
                        <td width="10%">
                        <s:if test="%{#p.showType=='TELETEXT_SHOW' }">图文</s:if>
                        <s:if test="%{#p.showType=='IMAGE_SHOW' }">图片</s:if>
                        <s:if test="%{#p.showType=='DETAIL_SHOW' }">详情</s:if>
                        <s:if test="%{#p.showType=='VEDIO_SHOW' }">视频</s:if>
                        </td>
                        
                        <%--<td width="10%">${p.businessName }</td>
                        
                        
                        --%><td width="10%" class="discription">${p.intro }</td>
                        
                        <td width="10%">
                        <s:if test="%{#p.status==1 }"><span id="tdstatus" style="color:green">启用</span></s:if>
                        <s:if test="%{#p.status==0 }"><span id="tdstatus" style="color:red">停用</span></s:if>
                        </td>
                        
                        
                        <td id="tdtop" width="10%">
                        <s:if test="%{#p.top==1 }">置顶</s:if>
                        </td>
                        
                        <td id="tdrecommend" width="10%">
                        <s:if test="%{#p.recommend==1 }">推荐</s:if>
                        </td>
                        <td id="tdheadline" width="10%">
                        <s:if test="%{#p.headline==1 }">广告</s:if>
                        </td>
                        <td width="10%">
                        	<s:if test="%{#p.isremotelogo==1 }">${p.remotelogourl}</s:if>
                        	<s:if test="%{#p.isremotelogo==0 }">${p.picUrl}</s:if>
                        </td>
                
                        
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
						
							<a id="btn_top" onclick="doControl('query.top=1')" class="btn_qd">置顶</a>
						
							<a id="btn_ctop" onclick="doControl('query.top=0')" class="btn_zant">撤销置顶</a>
						
							<a id="btn_recmd" onclick="doControl('query.recommend=1')" class="btn_qd">推荐</a>
						
							<a id="btn_crecmd" onclick="doControl('query.recommend=0')" class="btn_zant">撤销推荐</a>
							
							<a id="btn_head" onclick="doControl('query.headline=1')" class="btn_qd">头条</a>
						
							<a id="btn_chead" onclick="doControl('query.headline=0')" class="btn_zant">撤销头条</a>
						</li>
					</ul>
				</div>
		  
		</div>
		<form id="form_list" action="<%=request.getContextPath() %>/zxgl/news_list.html" method="post">
			<input type="hidden" id="sortFieldName" name="sortFieldName" value=""/>
			<input type="hidden" id="asc" name="asc" value=""/>
    <div class="searchtab">
            		<table width="100%" border="0">
						<tbody>
							<tr>
								
								<td>关键字</td>
								<td>
									<s:textfield name="query.title" label="" theme="simple"></s:textfield> 
								
								</td>
								<td>
									新闻类别
								</td>
								<td>
									<s:select id="newsCatalog" name="query.catalogCode" list="catalogList" listKey="catalogCode" listValue="catalogName" headerKey=" " headerValue=" " theme="simple" cssStyle="width:100px;"></s:select>
								</td>
								
								<td>
									使用状态
								</td>
								<td>
									<s:select id="status" name="query.status" list="#{'1':'启用','0':'停用' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
								</td>
								
								<td>
									所属业务系统
								</td>
								<td>
								<s:if test="businessList != null">
									<s:select id="business" name="query.business" list="businessList" listKey="classXtbm" listValue="classXtmc" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
								</s:if>
								<s:else>
									<select style="width:100px;" id="business" name="query.business"></select>
								</s:else>
									
								</td>
								</tr>
							<tr>
								<td>
									是否置顶
								</td>
								<td>
									<s:select id="top" name="query.top" list="#{'1':'是','0':'否' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" cssStyle="width:152px;"></s:select>
								</td>
								
								<td>
									是否推荐
								</td>
								<td>
									<s:select id="recommend" name="query.recommend" list="#{'1':'是','0':'否' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
								</td>
								
								<td>
									是否头条
								</td>
								<td>
									<s:select id="headline" name="query.headline" list="#{'1':'是','0':'否' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
								</td>
								
								<td>
									展现形式
								</td>
								<td>
									<s:select id="showType" name="query.showType" list="showTypeEnum" listKey="key" listValue="text" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
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
    	<span>资讯管理<font color="#0457A7" style="font-weight:normal;"> </font></span>
    </h3>
<!--标题end-->
<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td><input type="checkbox" disabled/></td>
						<td width="10%">资讯标题</td>
						<td width="10%">创建时间</td>
						<td width="10%">资讯类别</td>
						<td width="10%">展现形式</td>
						<td width="10%">所属业务系统</td>
						<td width="10%">资讯简介</td>
						<td width="10%">使用状态</td>
						<td width="10%">是否置顶</td>
						<td width="10%">是否推荐</td>
						<td width="10%">是否头条</td>
						<td width="10%">资讯logo图片地址</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="p" status="st">
					<s:if test="%{#p.grabMethod==1}">
						<tr style="color:red">
					</s:if>
					<s:else>
						<tr>
					</s:else>
					<input type="hidden" value="${p.catalogCode}" />
						<td>
							<input name="idckb" type="checkbox" id="id" value="${p.id }"/>
						</td>
						<td width="10%">${p.title}</td>
						<td width="10%"><s:date name="#p.createTime" format="yyyy-MM-dd" /></td>
						<td id="catalogName" width="10%">${p.catalogName}</td>
						
						<td width="10%">
						<s:if test="%{#p.showType=='TELETEXT_SHOW' }">图文</s:if>
						<s:if test="%{#p.showType=='IMAGE_SHOW' }">图片</s:if>
						<s:if test="%{#p.showType=='DETAIL_SHOW' }">详情</s:if>
						</td>
						
						<td width="10%">${p.businessName }</td>
						
						
						<td width="10%">${p.intro }</td>
						
						<td id="tdstatus" width="10%">
						<s:if test="%{#p.status==1 }">启用</s:if>
						<s:if test="%{#p.status==0 }">停用</s:if>
						</td>
						
						
						<td id="tdtop" width="10%">
						<s:if test="%{#p.top==1 }">置顶</s:if>
						</td>
						
						<td id="tdrecommend" width="10%">
						<s:if test="%{#p.recommend==1 }">推荐</s:if>
						</td>
						<td id="tdheadline" width="10%">
						<s:if test="%{#p.headline==1 }">头条</s:if>
						</td>
						<td width="10%">${p.picUrl}</td>
				
						
						</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" query="${query }" />
  </div>
		</form> --%>
		<script type="text/javascript">
		var heightOffset= 200;
			$(function(){
			var enableMsg = '${enableMsg}';
			if (enableMsg != '') {
				alert(enableMsg);
			}
				$('#list_body tr').find('input').bind('click',function(e){
					e.stopPropagation();
					var $that = $(this);
					showMenu($(this).parent().parent());
					
					var headline = $(this).closest("tr").find("input[name='headline']").val();
					
					if (!$(this).prop("checked")) {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
						$('#btn_top').css('display','');
						$('#btn_ctop').css('display','');
						$('#btn_recmd').css('display','');
						$('#btn_crecmd').css('display','');
						$('#btn_head').css('display','');
						$('#btn_chead').css('display','');
					}
					$("#list_body").find('input[type="checkbox"]').each(function(){
					
						if ($that.val() != $(this).val()) {
							$(this).removeAttr('checked');
							$(this).parent().parent().removeClass('current');
						}
					});
					
					$(this).parent().parent().toggleClass('current');
				});
				$('#list_body tr').bind('click',function(e){
				var catalogCode = $(this).find("input[type='hidden']").val();
				var catalogName = $(this).find("td[id='catalogName']").html();
				
				showMenu($(this));
				
				if ($(this).find("input[type='checkbox']").length < 1) {
					$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
						$('#btn_top').css('display','');
						$('#btn_ctop').css('display','');
						$('#btn_recmd').css('display','');
						$('#btn_crecmd').css('display','');
						$('#btn_head').css('display','');
						$('#btn_chead').css('display','');
				}
				var status = $(this).find("input[type='checkbox']").prop('checked');
				if (status) {
					$(this).find("input[type='checkbox']").prop('checked',false);
					$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
						$('#btn_top').css('display','');
						$('#btn_ctop').css('display','');
						$('#btn_recmd').css('display','');
						$('#btn_crecmd').css('display','');
						$('#btn_head').css('display','');
						$('#btn_chead').css('display','');
				} else {
					$(this).find("input[type='checkbox']").prop('checked',true);
				}
				
				//$(this).find("input[type='checkbox']").attr('checked','checked');
					$(this).siblings().find('input[id="id"]').prop('checked',false);
					//$(this).toggleClass('current').siblings().removeClass('current');
				
					
				});
				
				function showMenu(obj) {
					var tdstatus = obj.find('#tdstatus').html();
					tdstatus = (tdstatus==null?'':trim(tdstatus));
					var tdtop = obj.find('#tdtop').html();
					tdtop = (tdtop==null?'':trim(tdtop));
					var tdrecommend = obj.find('#tdrecommend').html();
					tdrecommend = (tdrecommend==null?'':trim(tdrecommend));
					var tdheadline = obj.find('#tdheadline').html();
					tdheadline = (tdheadline==null?'':trim(tdheadline));
					if (tdstatus == '启用') {
						$('#btn_qy').css('display','none');
						$('#btn_sc').css('display','none');
						$('#btn_ty').css('display','');
					} else {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','none');
					}
					if (tdtop == '置顶') {
						$('#btn_top').css('display','none');
						$('#btn_ctop').css('display','');
					} else {
						$('#btn_top').css('display','');
						$('#btn_ctop').css('display','none');
					}
					if (tdrecommend == '推荐') {
						$('#btn_recmd').css('display','none');
						$('#btn_crecmd').css('display','');
					} else {
						$('#btn_recmd').css('display','');
						$('#btn_crecmd').css('display','none');
					}
					if (tdheadline == '广告') {
						$('#btn_head').css('display','none');
						$('#btn_chead').css('display','');
					} else {
						$('#btn_head').css('display','');
						$('#btn_chead').css('display','none');
					}
				}
				
				$('#list_body tr').bind('dblclick',function(){
					
                    var id = $(this).find("input[id='id']").val();
                    location.href="<%=request.getContextPath()%>/zxgl/news_modify.html?query.id=" + id;
                    //var url = "<%=request.getContextPath()%>/zxgl/news_modify.html?query.id=" + id;
                    // $("#myModal_toji").find(".modal-content").width(750);
                    //$("#myModal_toji").find(".modal-content").height(heightOffset+665);
                    //$("#myFrame").attr("src", url);
                    //$("#myModal_toji").modal("show");
				});
			});
		    //heightOffset+=230;
		    $("#btn_zj").click(function(){
		    	location.href="<%=request.getContextPath()%>/zxgl/news_add.html";
					//showWindow("增加","<%=request.getContextPath()%>/zxgl/news_add.html", 750,heightOffset+300);
					
				//var url = "<%=request.getContextPath()%>/zxgl/news_add.html";
                //var data = {};
                //$("#myModal_toji").find(".modal-content").width(750);
                //$("#myModal_toji").find(".modal-content").height(heightOffset+665);
                //$("#myFrame").attr("src", url);
                //$("#myModal_toji").modal("show");
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
                    location.href="<%=request.getContextPath()%>/zxgl/news_modify.html?query.id=" + id;
					//showWindow("修改","<%=request.getContextPath()%>/zxgl/news_modify.html?query.id=" + id, 750,heightOffset+300);
					//var url = "<%=request.getContextPath()%>/zxgl/news_modify.html?query.id=" + id;
                    //$("#myModal_toji").find(".modal-content").width(750);
                    //$("#myModal_toji").find(".modal-content").height(heightOffset + 665);
                    //$("#myFrame").attr("src", url);
                    //$("#myModal_toji").modal("show");
				});
				function doUpdate(obj) {
					var id = obj;
					showWindow("修改","<%=request.getContextPath()%>/zxgl/news_modify.html?query.id=" + id, 750,heightOffset+300);
				}
				function doDelete(obj) {
					var id = obj;
					showConfirm("确定要删除吗？");
					$("#why_cancel").click(function(){
						alertDivClose();
					});
					

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/zxgl/news_delete.html?query.id="+id, null, function(data){
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
					var selectCatalog = checked.parent().parent().find("input[type='hidden']").val();
					if (arg == "ty") {
					arg = 'query.status=0';
						//showConfirm("确定要停用吗？停用同时会取消置顶和推荐状态");
						openAlert("确定要停用吗？停用同时会取消置顶和推荐状态", "w");
					}
					else if (arg == "delete") {
					arg = "query.deleted=1";
						//showConfirm("确定要删除吗？");
						openAlert("确定要删除吗？", "w");
					} else {
						$.post("<%=request.getContextPath() %>/zxgl/news_control.html?"+arg+"&query.id="+id+ "&query.catalogCode=" + selectCatalog, null, function(data){
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
						$.post("<%=request.getContextPath() %>/zxgl/news_control.html?"+arg+"&query.id="+id+ "&query.catalogCode=" + selectCatalog, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							//processDataCall(data, callback);
							processCall(data, callback);
						}, "json");
					});
					
					}
					
				
				
				//fillRows("15", "", "", false);//填充空行
		</script>
	</body>
</html>
