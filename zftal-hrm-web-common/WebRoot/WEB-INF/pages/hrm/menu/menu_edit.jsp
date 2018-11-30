<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">

    var link = '';
    $(function(){
        $("#linkType").change(function(){
        	changeLinkType();
        });
        $("#businessInfoClasses").change(function(){
        	fillLink();
        });
        changeLinkType();
        $("#save_btn").click(function(){
            
            if(!check()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/menu/menu_save.html",
                $("#form_edit").serialize(),function(data){
                    var callback = function(){
                    	refulsh();
                    };
                    if(data.success){
                        processDataCall(data, callback);
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
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
    }

    function closeWin(){
        $(".ymPrompt_close").click();
     }
    function changeLinkType(){
        var v = $("#linkType").val();
        if(v == 'bnsDeclare'){
            link = '/bnsinfo/infochange_list.html?classId=';
        }
        if(v == 'bnsAudit'){
            link = '/bnsinfo/infoaudit_page.html?query.status=WAIT_AUDITING&query.classId=';
        }
        if(v == 'bnsManage'){
            link = '/normal/staffBatch_list.html?modeType=right&classId=';
        }
        if(v == "default"){
            $("#menuLink").show();
            $("#businessInfoClasses").hide();
        }else{
            $("#menuLink").hide();
            $("#businessInfoClasses").show();
            fillLink();
        }
    }

    function fillLink(){
    	 var v = $("#businessInfoClasses").val();
    	 $("#menuLink").val(link+v);
    }
    </script>
</head>
<body>
    <form id="form_edit">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">菜单信息</font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn">保 存</button>
                                <button id="cancel" type="button" onclick="closeWin()">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <th width="30%">
                            <span class="red">*</span>功能代码
                        </th>
                        <td>
                            <c:if test="${op=='add'}">
                                <input name="model.gnmkdm" type="text"/>
                            </c:if>
                            <c:if test="${op!='add'}">
                                ${model.gnmkdm }
                                <input value="${model.gnmkdm }" name="model.gnmkdm" type="hidden"/>
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
                                <input value="${model.fjgndm }" name="model.fjgndm" type="text"/>
                            </c:if>
                            <c:if test="${op!='add'}">
                                ${model.fjgndm }
                                <input value="${model.fjgndm }" name="model.fjgndm" type="hidden"/>
                            </c:if>
                            
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>菜单名称
                        </th>
                        <td>
                            <input name="model.gnmkmc" type="text" value="${model.gnmkmc }"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>菜单链接
                        </th>
                        <td>
                            <select id="linkType">
                                <option value="default">自定义链接</option>
                                <option value="bnsDeclare">通用业务信息类申报</option>
                                <option value="bnsAudit">通用业务信息类审核</option>
                                <option value="bnsManage">通用业务信息类汇总管理</option>
                            </select>
                            <input id="menuLink" type="text" name="model.dyym" value="${model.dyym }"/>
                            <select id="businessInfoClasses" >
                                    <c:forEach items="${businessInfoClasses }" var="infoClass">
                                    <c:if test="${infoClass.type != 'OVERALL' }">
                                    <option value="${infoClass.guid }" 
                                    <c:if test="${infoClass.guid eq config.infoClassId }" >selected="selected"</c:if> >
                                    ${infoClass.name }</option></c:if>
                                    </c:forEach>
                                </select>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>显示顺序
                        </th>
                        <td>
                            <input type="model.xssx" name="model.xssx" value="${model.xssx }"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>