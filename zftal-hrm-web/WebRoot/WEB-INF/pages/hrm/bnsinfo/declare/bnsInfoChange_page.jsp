<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="code" uri="/WEB-INF/code.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/lockTableTitle.js"></script>
    <script type="text/javascript">
        $(function(){
            initTag(); 
        });
        
        function initTag(){
        	$("#${initTagId}").closest("li").addClass("ha");
            if($(".ha").length==0)
                $("#comp_title li:first").addClass("ha");
            var id = $(".ha a").attr("id");
            requestData(id,"classId=${classId}");
            $("#comp_title a").click(function(){
                $("#comp_title li").removeClass("ha");
                $(this).closest("li").addClass("ha");
                var path = $(this).attr("id");
                requestData(path,"classId=${classId}");
            });
        }
        
        function requestData(path,param){
            var successCall = function(d){
                try{
                    var data = $.parseJSON(d);
                    if(data.success==false){
                        tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
                        $("#window-sure").click(function() {
                            divClose();
                        });
                    }
                }catch(e){
                    $("#compId").empty();
                    $("#compId").append(d);
                }
            };
            $("#windown-content").unbind("ajaxStart");
            $.ajax({
                url:_path + "/bnsinfo/infochange_" + path + ".html",
                type:"post",
                data:param,
                cache:false,
                dataType:"html",
                success:successCall
            });
        }

        function query(){
            var id = $(".ha a").attr("id");
            requestData(id,$("#search").serialize());
            return false;
        }
        </script>
    </head>
    <body>
        <input type="hidden" name="idCard" value="${classId }"/>
        <div class="compTab">
            <div class="comp_title" id="comp_title">
              <ul style="width:90%">
                <li><a href="#" id="bnsinfoList"><span>我的【 ${clazz.name }】</span></a></li>
                <c:if test="${hasBusiness}">
                    <li><a href="#" id="declarePage"><span>【 ${clazz.name }】申报</span></a></li>
                </c:if>
              </ul>
            </div>
            <div class="comp_con" id="compId">
            </div>
        </div>
    </body>
</html>
