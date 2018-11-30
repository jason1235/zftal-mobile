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
			var editor1;
			KindEditor.ready(function(K) {
				editPlugin(K);
			});
			function editPlugin(K){
			   editor1 = K.create('textarea[name="query.content"]', {
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
		 
		$(function(){
			editPlugin(KindEditor);
			$("#cancel").click(function(){
				history.go(-1);
			});
			$("#save_btn").click(function(){
				var title = $("#titleInput");
				if(title != null && title.val()==''){
					errorAlert("请填写标题");
					window.parent.scroll(0,0);
					return false;
				} else {
				   title = title.val();
				}
				if(editor1.isEmpty()){
					errorAlert("请填写帮助说明");
					window.parent.scroll(0,0);
					return false;
				}
				
				$.post("<%=request.getContextPath()%>/helpguide/helpguide_save.html",
					$("#form_edit").serialize(),function(data){
						if(data.success){
							window.parent.scroll(0,0);
							openAlert(data.text, "s","auto", function(){
							  location.href = _path + "/helpguide/helpguide_list.html";
						    });
						}else{
							errorAlert(data.text);
							window.parent.scroll(0,0);
						}
				},"json");
				
				return false;
			});
			
			$('#back').click(function(){
				location.href = _path + "/helpguide/helpguide_list.html";
			});
			
	
			
		});
	</script>
</head>

<body>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <input type="hidden" name="op" value="${op }"/>
        <input type="hidden" name="query.id" value="${model.id}"/>
        <div class="sl_mod modal-content" style="border:none">
            <div class="modal-header">
<!--                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>-->
                <button type="button" class="close" onclick="history.go(-1)"><span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span><span class="sr-only">Close</span></button>              
                <h4 class="modal-title text-left" id="myModalLabel">帮助说明维护</h4>
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
                        <input id="titleInput" type="text" name="query.title" maxlength="50" placeholder="不要超过50个字符哦(^_^)" class="form-control" value="${model.title }" />
                        </td>
                    </tr>
                    <tr>
                        <th width="30%" >
                            <span class="red">*</span>是否热门
                        </th>
                        <td>
	                        <select name="query.isHot"  value="${model.isHot}" class="form-control">
	                           <option value="0" <c:if test = "${model.isHot eq 0}"> selected="selected"</c:if>>否</option>
                               <!--<option value="1" <c:if test = "${model.isHot eq 1}"> selected="selected"</c:if>>是</option>
                            --></select>
                        </td>
              		</tr>
                    <tr>
                        <th width="30%" >
                            <span class="red">*</span>是否启用
                        </th>
                        <td>
	                        <select name="query.isPlayed"  value="${model.isPlayed }" class="form-control">
                               <option value="1" <c:if test = "${model.isPlayed eq 1}"> selected="selected"</c:if>>是</option>
	                           <option value="0" <c:if test = "${model.isPlayed eq 0}"> selected="selected"</c:if>>否</option>
                            </select>
                        </td>
              		</tr>
                    <tr id="content">
                        <th style="width:90px;">
                            <span class="red"></span>帮助说明
                        </th>
                        <td>
                            <textarea id="kcontent" name="query.content" rows="3" style="width:100%;">${model.content }</textarea>
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
</body>
</html>
