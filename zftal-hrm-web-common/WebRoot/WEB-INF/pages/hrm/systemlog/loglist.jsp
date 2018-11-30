<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <%@include file="/commons/hrm/head.ini" %>
        <script type="text/javascript">
            $(function(){
                        $("#list_body tr").dblclick(function(){
		                    var fileName = $(this).find("input[name='model.fileName']");
		                    window.open(_path+"//systemlog/systemLog_download.html?"+fileName.serialize());
	                    });
            });

            function download(id){
            	var fileName = $("#"+id);
                window.open(_path+"/systemlog/systemLog_download.html?"+fileName.serialize());
            }
                
            
        </script>
    </head>
    <body>
        <!--div class="toolbox">
            <div class="buttonbox">
                <ul>
                    <li>
                        <a onclick="return false;" class="btn_zj" href="#">
                          打包下载
                        </a>
                    </li>
                </ul>
            </div>  
        </div-->
        <div class="formbox">
            <!--标题start-->
            <h3 class="datetitle_01">
                <span>系统日志下载<font color="#0f5dc2" style="font-weight:normal;">（提示：双击可以下载选定日志）</font></span>
            </h3> 
            <div class="con_overlfow">
                <!--标题end-->
                <table width="100%" class="dateline nowrap" id="tiptab" >
                    <thead id="list_head">
                        <tr>
                            <%--<input style="display: none;" type="checkbox" onclick="selectAllOrCancel(this,'ids');" id="allCheckBoxDel"/>--%>
                            <td>文件名</td>
                            <td>文件大小（字节）</td>
                            <td>最后修改时间</td>
                            <td>操作</td>
                        </tr>
                    </thead>
                    <tbody id="list_body">
                        <c:forEach items="${files}" var="f" varStatus="st">
                          <tr name="tr">
                            <td>${f.fileName}
                            <input id="download_${st.index }" name="model.fileName" value="${f.fileName}" type="hidden" />
                            </td>
                            <td>${f.fileSize }</td>
                            <td>${f.modifyDateView }</td>
                            <td>
                                <a style="text-decoration: underline;color: blue;" href="javascript:void(0);" onclick="download('download_${st.index }')">下载</a>
                            </td>
                          </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
