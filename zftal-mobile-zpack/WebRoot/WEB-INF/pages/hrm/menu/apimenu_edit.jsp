<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/themes/default/default.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/kindeditor/plugins/code/prettify.css" />
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/kindeditor.js"></script>
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/lang/zh_CN.js"></script>
        <script charset="utf-8" type="text/javascript" src="<%=request.getContextPath()%>/kindeditor/plugins/code/prettify.js"></script>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head_v5.ini" %>
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
    
    KindEditor.ready(function(K) {
		editPlugin(K);
	});
	function editPlugin(K){
		var editor1 = K.create('textarea[name="model.content"]', {
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
    var link = '';
    $(function(){
    	
    	editPlugin(KindEditor);
    	var fjgndm = '${model.fjgndm }';
        if(fjgndm == 'N'){
        	$("#content").hide();
        }
        if(fjgndm.length>2 ){
        	$("#content").show();
        }
        
        $("#save_btn").click(function(){
            
            if(!check()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/menu/apiMenu_save.html",
                $("#form_edit").serialize(),function(data){
                    if(data.success){
                        window.location.href="<%=request.getContextPath()%>/menu/apiMenu_page.html?query.fjgndm="+$("input[name='model.fjgndm']").val();
                    }else{
                        errorAlert(data.text);
                    }
                            
            },"json");
            return false;
        });
        
        $("#fjgn").bind('input propertychange', function() {
        	var fjgndm = $(this).val();
        	if(fjgndm.length>4 ){
        		$("#content").show();
        	}else{
        		$("#content").hide();
        	}
        });
        
    });

    function check()
    {
        var name=$("input[name='model.gnmkdm']").val();
        if(name==null || name==''){
            showWarning("功能代码不能为空");
            return false;
        }
        return true;
        var xssx=$("input[name='model.xssx']").val();
        if(xssx==null || xssx==''){
            showWarning("显示顺序不能为空");
            return false;
        }
        return true;
    }

    function closeWin(){
        history.go(-1);
     }

    </script>
</head>
<body>
    <form id="form_edit">
        <div class="sl_mod modal-content" style="border:none">
        	<div class="modal-header">
<!--                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>-->
                <button type="button" class="close" onclick="history.go(-1)"><span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span><span class="sr-only">Close</span></button>              
                <h4 class="modal-title text-left" id="myModalLabel">api菜单信息</h4>      
            </div>
            <div class="modal-body">
			     <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
            <div class="tab" style="clear: both;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                
                <tbody>
                    <tr>
                        <th width="30%">
                            <span class="red">*</span>功能代码
                        </th>
                        <td>
                            <c:if test="${op=='add'}">
                                <input name="model.gnmkdm" type="text" class="form-control"/>
                            </c:if>
                            <c:if test="${op!='add'}">
                                ${model.gnmkdm }
                                <input value="${model.gnmkdm }" name="model.gnmkdm" type="hidden" />
                            </c:if>
                            <input name="op" type="hidden" value="${op }"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>父级功能代码
                        </th>
                        <td>
                            <c:if test="${op=='add'}">
                                <input id="fjgn" value="${model.fjgndm }" name="model.fjgndm" type="text" class="form-control"/>
                            </c:if>
                            <c:if test="${op!='add'}">
                                ${model.fjgndm }
                                <input id="fjgn" value="${model.fjgndm }" name="model.fjgndm" type="hidden"/>
                            </c:if>
                            
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>菜单名称
                        </th>
                        <td>
                            <input name="model.gnmkmc" type="text" value="${model.gnmkmc }" class="form-control"/>
                        </td>
                    </tr>
                    <tr id="content">
                        <th>
                            <span class="red"></span>菜单内容
                        </th>
                        <td>
                            <textarea id="kcontent" name="model.content" rows="3" style="width:100%;">${model.content }</textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>显示顺序
                        </th>
                        <td>
                            <input type="text" name="model.xssx" value="${model.xssx }" class="form-control"/>
                        </td>
                    </tr>
                </tbody>
            </table>
            </div>
            </div>
             <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel" onclick="closeWin()">取 消</button>
            </div>
        </div>
    </form>
</body>
</html>