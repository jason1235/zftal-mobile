<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/commons/hrm/head_v5.ini" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/plugins/code/prettify.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>

       <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/kindeditor.js"></script>
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/plugins/code/prettify.js"></script>
        
   <style type="text/css">
		.ui-autocomplete{
			z-index:12001;
			width: 500px
		}
		<c:if test="${view=='view'}">
		#beanview td{
		   width:240px;
		}
		</c:if>
		.formlist td,.formlist th {
			padding-left:10px;
		}
		.form-control {
			color:#000;
		}
	
	</style>
	<script type="text/javascript">
			
			var catalogList = ${catalog};
				
			KindEditor.ready(function(K) {
				editPlugin(K);
			});
			function editPlugin(K){
			var editor1 = K.create('textarea[name="query.content"]', {
					themeType: 'simple',
					resizeType: 1,
					minHeight:430,
					cssPath :  _path+'/kindeditor/plugins/code/prettify.css',
					uploadJson : _path+'/kindeditor/uploadJson.html',
					fileManagerJson : _path+'/kindeditor/fileManagerJson.html',
					allowFileManager : true,
					afterCreate : function() {
						var self = this;
						K.ctrl(document, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
						K.ctrl(self.edit.doc, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
					},
					afterBlur: function(){this.sync();}
					});
					prettyPrint();
		}
		var isPic = false; 
		$(function(){
		var titleTrHtml = $('#titleTr').html();
		var titleSpanHtml = $('#titleSpan').html();
		var picHtml = $('#picContainer').html();
			editPlugin(KindEditor);
			function showType(ca) {
				for (var i =0; i < catalogList.length;i++) {
					if (ca == catalogList[i]['catalogCode']) {
						var st = catalogList[i]['showType'];
						
						$('#showType').html(st);
						//$('input[name="query.showType"]').val(st);
						if ($('#titleTr') && $('#titleTr').html() != "") {
							titleTrHtml = $('#titleTr').html();
						}
						if (st == '图片') {
							isPic = true;
							$('#titleInput').attr('name','query.xxx');
							$('#newsContent').css('display','none');
							$('#titleTr').css('display','none');
							//$('#titleTr').html('');
							$('#titleSpan').html(titleSpanHtml);
							if ('modify' == '${op}') {
								var logochoice = $("input[name='query.isremotelogo']:checked").val();
								if(logochoice.trim() == '0'){
									$(".detaillogo").show();
									$("#logoremote").hide();
								}
								if(logochoice.trim() == '1'){
									$(".detaillogo").hide();
									$("#logoremote").show();
								}
								$('#picAddContainer').css('display','none');
							} else {
								$("#logochoice").show();
								$(".detaillogo").show();
								$("#logoremote").hide();
								$("input[name='query.isremotelogo']").get(0).checked = true;
								$('#picAddContainer').css('display','');
							}
							
							
						} else if(st == '视频'){
							$("#file").show();
							$("#intro").hide();
							$("#newsContent").hide();
							$("#logochoice").hide();
							$(".detaillogo").hide();
							$("#logoremote").hide();
						} else {
							if(st == '详情'){
								$("#logochoice").hide();
								$(".detaillogo").hide();
								$("#logoremote").hide();
							}else{
								$("#file").hide();
								if ('modify' == '${op}') {
									$("#logochoice").show();
									var logochoice = $("input[name='query.isremotelogo']:checked").val();
									if(logochoice.trim() == '0'){
										$(".detaillogo").show();
										$("#logoremote").hide();
									}
									if(logochoice.trim() == '1'){
										$(".detaillogo").hide();
										$("#logoremote").show();
									}
								}
								if ('add' == '${op}') {
										$("#logochoice").show();
										$(".detaillogo").show();
										$("#logoremote").hide();
										$("input[name='query.isremotelogo']").get(0).checked = true;
								}
							}
							isPic = false;
							$('#titleInput').attr('name','query.title');
							$('#newsContent').css('display','');
							$('#titleTr').css('display','');
							//$('#titleTr').html(titleTrHtml);
							//$('#picAddContainer').css('display','none');
							$('#picContainer').html(picHtml);
							$('#titleSpan').html('');
						}
						return;
					}
				}
			};
			showType($("#catalog").val());
			$("#catalog").bind('change',function(){
				showType($(this).val());
			});
			
			$("input[name='query.isremotelogo']").bind('change',function(){
				var logochoice = $(this).val();
				if(logochoice.trim() == '0'){
					$(".detaillogo").show();
					$("#logoremote").hide();
					//var len = $(".picDiv").length;
					
					//$.post("<%=request.getContextPath()%>/zxgl/news_image.html",
					//		null,function(data){
							//$('#picContainer').append('<div class="picDiv" style="float:left;margin:5px 10px;">' + data +'<br/><span id="titleSpan">标题：<input type="text" class="form-control" style="width:200px;display:inline;" name="query.title" /></span><br></div>' );
					//		$("#logotd").append("<div class='picDiv' style='margin:5px 10px;'>" + data +"<div style='clear:both;'><button  type='button' class='btn btn-primary picDelete'>删除</button></div></div><br>" );
					//		if(len == '2'){
					//			$("#picAdd").hide();
					//		}
					//		$(".picDelete").click(function(){
					//			$(this).closest(".picDiv").remove();
					//		});
					//},"json");
				}
				if(logochoice.trim() == '1'){
					//$(".picDiv").remove();
					$(".detaillogo").hide();
					$("#logoremote").show();
				}
			});
			
			$("#cancel").click(function(){
				//location.href=_path+"/zxgl/news_list.html";
				//modalClose();
				//return false;
				history.go(-1);
			});
			//$(".close").click(function(){
            //    modalClose();
            //    return false;
            //});
			
			$("#save_btn").click(function(){
			console.log("*********************");
				//var title=$("#form_edit").find("input[name='query.title']").val();
				var title = $("#titleInput");
				var source=$("#form_edit").find("input[name='query.source']").val();
				var author=$("#form_edit").find("input[name='query.author']").val();
				var intro=$("#form_edit").find("input[name='query.intro']").val();
				var ca = $("#catalog").val();
				
				if(!isPic && title != null && title.val()==''){
					errorAlert("请填写标题");
					window.parent.scroll(0,0);
					return false;
				} else {
				 title = title.val();
				}
				if(source==''){
					//errorAlert("请填写来源");
					//return false;
				}
				if(author==''){
					//errorAlert("请填写作者");
					//return false;
				}

				if(intro==''){
					//errorAlert("请填写简介");
					//return false;
				}
				
				
				for (var i =0; i < catalogList.length;i++) {
					if (ca == catalogList[i]['catalogCode']) {
						var st = catalogList[i]['showType'];
						if (st == '图文') {
							//if (isPic) {
							    var reg = /.*\?fileGuid=(.*)/;
								var $img = $('.picDiv img').first();
								if ($img != null) {
									var imgPath = $img.attr('src');
									var ret = reg.exec(imgPath);
									if (ret != null && ret.length > 1) {
										var guid = ret[1];
										//if (guid == null || guid == '') {
										//	errorAlert("请上传图片");
										//	window.parent.scroll(0,0);
										//	return false;
										//}
									}
								}
							//}
						}
						if(st == '视频'){
							var vedioVal = $("input[name='vedioId']").val();
							if(vedioVal == null || vedioVal == ""){
								errorAlert("请上传视频");
								window.parent.scroll(0,0);
								return false;
							}
						}
					}
				}
				
				
				
				
				$.post("<%=request.getContextPath()%>/zxgl/news_save.html",
						$("#form_edit").serialize(),function(data){
						var callback = function(){
							//reflashPage();
							//location.reload();
							location.href = _path + "/zxgl/news_list.html";
							//top.xg_rightFrame.$('#form_list').submit();
						};
						if(data.success){
							window.parent.scroll(0,0);
							openAlert("保存成功", "s","auto", callback);
						}else{
							errorAlert(data.text);
							window.parent.scroll(0,0);
						}						
				},"json");
				return false;
			});
			
			$('#back').click(function(){
				location.href = _path + "/zxgl/news_list.html";
			});
			if ('modify' == '${op}'){
				if ('true' == '${flag}'){
					$("#picAdd").hide();
				}
			}
			
			$(".picDelete").click(function(){
				$(this).closest(".picDiv").remove();
				var len = $(".picDiv").length;
				if(len < 3){
					$("#picAdd").show();
				}
				
			});
			
			$("#picAdd").click(function(){
				var len = $(".picDiv").length;
				
				$.post("<%=request.getContextPath()%>/zxgl/news_image.html",
						null,function(data){
						//$('#picContainer').append('<div class="picDiv" style="float:left;margin:5px 10px;">' + data +'<br/><span id="titleSpan">标题：<input type="text" class="form-control" style="width:200px;display:inline;" name="query.title" /></span><br></div>' );
						$("#logotd").append("<div class='picDiv' style='margin:5px 10px;'>" + data +"<div style='clear:both;'><button  type='button' class='btn btn-primary picDelete'>删除</button>&nbsp;&nbsp;&nbsp;</div></div>" );
						if(len == '2'){
							$("#picAdd").hide();
						}
						$(".picDelete").click(function(){
							$(this).closest(".picDiv").remove();
							var len = $(".picDiv").length;
							if(len < 3){
								$("#picAdd").show();
							}
						});
				},"json");
			})
			
		})	
	</script>
</head>

<body>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <input type="hidden" name="op" value="${op }"/>
        <input type="hidden" name="query.id" value="${model.id}"/>
        <input type="hidden" name="query.business"  value="ydmh" />
        <div class="sl_mod modal-content" style="border:none">
            <div class="modal-header">
<!--                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>-->
                <button type="button" class="close" onclick="history.go(-1)"><span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span><span class="sr-only">Close</span></button>              
                <h4 class="modal-title text-left" id="myModalLabel">资讯维护</h4>      
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
                
                <tbody>
                    <tr id="titleTr">
                        
                        <th style="width:15%">
                            <span class="red">*</span>标题
                        </th>
                        <td>
                        <input id="titleInput" type="text" name="query.title" maxlength="100" placeholder="不要超过100个字符哦(^_^)" class="form-control" value="${model.title }" />
                        </td>
                    </tr>
                    
                    <tr>
                        <th style="width:15%">
                            <span class="red"></span>来源
                        </th>
                        <td>
                        <input type="text" name="query.source" maxlength="50" placeholder="不要超过50个字符哦(^_^)" class="form-control" value="${model.source }" />
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red"></span>作者
                        </th>
                        <td>
                        <input type="text" name="query.author" maxlength="50" placeholder="不要超过50个字符哦(^_^)" class="form-control" value="${model.author }" />
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red"></span>时间
                        </th>
                        <td>
                        <input type="text" name="query.newsTime" maxlength="50" placeholder="例如：2018-09-09" class="form-control" value="${model.newsTime }" />
                        </td>
                    </tr>
                    
                   <tr>
                        <th>
                            <span class="red">*</span>资讯类别
                        </th>
                        <td>
                            <s:if test="catalogList != null">
                                <s:select id="catalog" name="query.catalogCode" list="catalogList" listKey="catalogCode" listValue="catalogName" value="#request.model.catalogCode"  theme="simple" cssClass="form-control"></s:select>
                            </s:if>
                            <s:else>
                                <select id="catalog" name="query.catalogCode" class="form-control"></select>
                            </s:else>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>展示形式
                        </th>
                        <td>
                            <span id="showType"></span>
                        </td>
                    </tr>
                    
                    <tr id="intro">
                        <th>
                            <span class="red"></span>资讯简介
                        </th>
                        <td>
                        	<textarea name="query.intro" maxlength="250" placeholder="不要超过250个字符哦(^_^)" class="form-control" rows="3" cols="10">${model.intro }</textarea>
                            <!-- <input type="text" id="intro" name="query.intro" class="form-control" value="${model.intro }" /> -->
                        </td>
                    </tr>
                    
                    <tr id="newsContent">
                        <th style="width:90px;">
                            <span class="red"></span>资讯内容
                        </th>
                        <td>
                            <textarea id="kcontent" name="query.content" rows="3" style="width:100%;">${model.content }</textarea>
                        </td>
                    </tr>
                    
                    <tr id="logochoice" >
                        <th style="width:90px;">
                            <span class="red"></span>logo图片选择方式
                        </th>
                        <td>
                            <input type="radio" value="0" name="query.isremotelogo" <c:if test = "${model.isremotelogo == '0'}"> checked="checked"</c:if> />本地上传&nbsp;&nbsp;
                      		<input type="radio" value="1" name="query.isremotelogo" <c:if test = "${model.isremotelogo == '1'}"> checked="checked"</c:if> />远程访问
                        </td>
                    </tr>
                    
                    <c:if test="${op eq 'add' }">
                    <tr id="detaillogo" class="detaillogo"> 
                    <th style="width:90px;" >
                            资讯logo图片
                        </th>
                        <td id="logotd">
                        <div id="picAddContainer" style="clear:both;"><button id="picAdd" type="button" class="btn btn-primary">增加</button>&nbsp;&nbsp;&nbsp;<font color="red">最多上传3张图片</font></div>
                        <div id="picContainer">
                            <div class="picDiv" style="margin:5px 10px;">
                            ${imageHtml }<div style="clear:both;"><button  type="button" class="btn btn-primary picDelete">删除</button></div><br/>
                            <span id="titleSpan">标题：<input type="text" maxlength="50" class="form-control" placeholder="不要超过50个字符哦(^_^)" style="width:200px;display:inline;" name="query.title" value="${model.title }" /></span>
                            </div>
                        </div>
                       
                         
                        </td>
                    
                        
                        
                    </tr>
                    </c:if>
                    <c:if test="${op eq 'modify' }">
	                    <tr class="detaillogo"> 
	                    <th style="width:90px;" >
	                            资讯logo图片
	                        </th>
	                        <td id="logotd">
	                        <div id="picAddContainer" style="clear:both;"><button id="picAdd" type="button" class="btn btn-primary">增加</button></div>
                      <c:forEach var="imageitem" items="${imageHtmlArr}" varStatus="st">
                     
	                            <div class="picDiv" style="margin:5px 10px;">
	                            ${imageitem }<div style="clear:both;"><button type="button" class="btn btn-primary picDelete">删除</button></div>
	                            </div>
	                       
	                    
	                        
	                        
                      </c:forEach>
                      </td>
	                    </tr>
                    </c:if>
                    
                    
                    
                    <tr id="logoremote">
                    <th style="width:90px;" >
                            logo图片远程地址
                        </th>
                        <td>
                        	<input type="text" name="query.remotelogourl" maxlength="200" placeholder="不要超过200个字符哦(^_^)" class="form-control" value="${model.remotelogourl }" />
                        </td>
                    
                        
                        
                    </tr>
                    
                    <tr id="file"  class="androidmobile">
                        <th >
                            <span class="red">*</span>视频上传
                        </th>
                         <td >
                         
                         ${fileHtml }
                             
                        </td>
             		</tr>
                    
                </tbody>
            </table>
            </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>
<%-- 
	<form id="form_edit">
		<input type="hidden" name="op" value="${op }"/>
		<input type="hidden" name="query.id" value="${model.id}"/>
<!--		<input type="hidden"  name="query.showType" value=""/>    -->
		<table class="formlist">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">资讯维护</font></span>
                            
                        </th>
                    </tr>
                </thead>
        </table>
        <div class="buttonbox"><a id="back" class="btn_fh_rs" style="cursor: pointer">返回</a></div>
		<div class="tab" style="clear: both;max-height: 800px;overflow: auto;">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
				
				<tbody>
					<tr id="titleTr">
						
						<th style="width:90px;">
							<span class="red">*</span>标题
						</th>
						<td>
						<input id="titleInput" type="text" name="query.title" class="text_nor" style="width:200px;" value="${model.title }" />
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>来源
						</th>
						<td>
						<input type="text" name="query.source" class="text_nor" style="width:200px;" value="${model.source }" />
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>作者
						</th>
						<td><input type="text" name="query.author" class="text_nor" style="width:200px;" value="${model.author }" /></td>
					</tr>
					
						<tr>
						<th>
							<span class="red">*</span>所属业务系统
						</th>
						<td>
							<s:if test="businessList != null">
								<s:select id="business" name="query.business" list="businessList" listKey="classXtbm" listValue="classXtmc" value="#request.model.business"  theme="simple" cssStyle="width:100px;"></s:select>
							</s:if>
							<s:else>
								<select id="business" name="query.catalogCode" style="width:100px;"></select>
							</s:else>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>资讯类别
						</th>
						<td>
							<s:if test="catalogList != null">
								<s:select id="catalog" name="query.catalogCode" list="catalogList" listKey="catalogCode" listValue="catalogName" value="#request.model.catalogCode"  theme="simple" cssStyle="width:100px;"></s:select>
							</s:if>
							<s:else>
								<select id="catalog" name="query.catalogCode" style="width:100px;"></select>
							</s:else>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>展示形式
						</th>
						<td>
							<span id="showType"></span>
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>资讯简介
						</th>
						<td>
							<input type="text" id="intro" name="query.intro" value="${model.intro }" />
						</td>
					</tr>  
					
					<tr id="newsContent">
						<th style="width:90px;">
							<span class="red"></span>资讯内容
						</th>
						<td>
							<textarea id="kcontent" name="query.content" rows="3" style="width:100%;">${model.content }</textarea>
						</td>
					</tr>
					
					<tr>
					<th style="width:90px;">
							<span class="red"></span>资讯logo图片
						</th>
						<td>
						<div id="picContainer">
							<div class="picDiv" style="float:left;margin:5px 10px;">
							${imageHtml }<br/>
							<span id="titleSpan">标题：<input type="text" style="width:200px;" name="query.title" value="${model.title }" /></span><br>
							</div>
						</div>
						<div id="picAddContainer" style="clear:both;display:none;"><button id="picAdd" type="button">增加</button></div>
						</td>
					
						
						
					</tr>
					
				</tbody>
			</table>
			</div>
			<table class="formlist" >
			<tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                            <c:if test="${view !='view'}">
                                <button id="save_btn">保 存</button>
                            </c:if>
                                <button type="button" id="cancel">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
			</table>
	</form>--%>
</body>
</html>
