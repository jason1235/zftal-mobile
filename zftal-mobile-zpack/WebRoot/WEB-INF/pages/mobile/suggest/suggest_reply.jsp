<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/kindeditor.js"></script>
<script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/plugins/code/prettify.js"></script>
<%@ include file="/commons/hrm/head_v5.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    KindEditor.ready(function(K) {
		editPlugin(K);
	});
	function editPlugin(K){
	var editor1 = K.create('textarea[name="query.replyContent"]', {
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
        $("#save_btn").click(function(){
            var replyConetnt = $("textarea[name='query.replyContent']").val();
            if(replyConetnt == null || replyConetnt ==''){
				errorAlert("请填写回复内容");
				window.parent.scroll(0,0);
				return false;
			}
            $.post("<%=request.getContextPath()%>/suggest/suggest_reply.html",
                $("#form_edit").serialize()  ,function(data){
	            	var callback = function(){
	  					location.href = "<%=request.getContextPath() %>/suggest/suggest_list.html"
	  				};
                 	openAlert("操作成功!", "s", "auto", callback);
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
            history.back();
            return false;
        });
        
        $(".close").click(function(){
        	history.back();
            return false;
        });
        
    });
     </script>
</head>
<body>
 <jsp:include page="/WEB-INF/pages/globalweb/modal.jsp" flush="true"></jsp:include>
    <form action="<%=request.getContextPath()%>/serviceManager/business_save.html" name="search" id="form_edit" method="post">
            <input name="query.id" value="${query.id }" type="hidden"/>
            <div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">意见反馈回复信息</h4>
            </div>
            <div class="modal-body" style="background-color: #fff;">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
             <tr>
                        <th style="width:120px;">
                                                                                 回复内容
                        </th>
                         <td>
                            <textarea name="query.replyContent" rows="10" style="width:100%;" >${query.replyContent }</textarea>
                        </td>
             </tr>
             </tbody>
            </table>
       </div> 
            </div>
            <div class="modal-footer" style="background-color: #fff;">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
    </form>

</body>
</html>