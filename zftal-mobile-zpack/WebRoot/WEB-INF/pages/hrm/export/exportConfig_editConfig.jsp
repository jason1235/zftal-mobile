<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
        $(function(){
            $("#save").click(function(){
            	
                if( $("#name").val() == "" ) {
                    alert("目录名称不得为空，请重新输入！");
                    return false;
                }

                if( $("input[name='file']").val() == "" ) {
                    alert("模板文件不能为空，请选择要上传的模板");
                    return false;
                }
                var form = $("#configForm");
                form.attr("action",_path+"/export/config_saveConfig.html");
                form.attr("target","frame");
                //alert(form.attr("enctype"));
                form.submit();
                return false;
            });

            $("#cancel").click(function(){
                divClose();
                return false;
            });

            if("${hasFile}"!="true"){
            	filemodify();
            	$("#backFileView").remove();
            }else{
            	fileview();
            }
        });

        function fileview(){
            $(".fileview").show();
            $(".filemodify").hide();
            $("#fileUpload").removeAttr("name");
        }
        function filemodify(){
            $(".filemodify").show();
            $(".fileview").hide();
            $("#fileUpload").attr("name","file");
        }

        function callback(data,status){//显示上传操作反馈信息
            tipsWindown("提示信息","text:"+data,"340","120","true","","true","id");
            $("#window-sure").click(function() {
                alertDivClose();
                if( status ) {
                    $(".ymPrompt_close").click();
                    window.location.reload();
                }
            });
        }

        function downloadTemp(){
            window.open(_path+"/export/config_downloadTemp.html?exportConfig.id=${exportConfig.id}");
        }
    </script>
</head>

<body>
    <form method="post" enctype="multipart/form-data" id="configForm" target="frame">
        <input type="hidden" name="exportConfig.id" value="${exportConfig.id }" />
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="2">
                            <span>模板维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save">保 存</button>
                                <button id="cancel">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <th width="100px"><span class="red">*</span>模板名称</th>
                        <td>
                            <input type="text" class="text_nor" id="name" name="exportConfig.name" size="25"  maxlength="16" value="${exportConfig.name }" />
                        </td>
                    </tr>
                    <tr>
                        <th>所属目录</th>
                        <td>
                            <select disabled="disabled">
                                <option>${exportType.name }</option>
                            </select>
                            <input type="hidden" name="exportConfig.type" value="${exportType.id }" />
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>是否开放</th>
                        <td>
                            <input type="radio" value="Y" name="exportConfig.open" <c:if test="${exportConfig.open == 'Y' }">checked</c:if> />开放
                            <input type="radio" value="N" name="exportConfig.open" <c:if test="${exportConfig.open != 'Y' }">checked</c:if>/>暂不开放
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>数据来源</th>
                        <td>
                            <input type="radio" value="0" name="exportConfig.origin" <c:if test="${exportConfig.origin != '1' }">checked</c:if> />默认
                            <input type="radio" value="1" name="exportConfig.origin" <c:if test="${exportConfig.origin == '1' }">checked</c:if>/>自抓取
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>模板文件</th>
                        <td><a class="fileview" onclick="downloadTemp()" style='text-decoration: underline;color: blue;'>模板下载</a>
                            <a class="fileview" onclick="filemodify()" style='text-decoration: underline;color: blue;'>模板变更</a>
                            <input class="filemodify" id="fileUpload" type="file"/>
                            <a class="filemodify" id="backFileView" onclick="fileview()" style='text-decoration: underline;color: blue;'>撤销变更</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
    <iframe id="frame" name="frame" src="about:blank" style="display:none"></iframe>
</body>
</html>