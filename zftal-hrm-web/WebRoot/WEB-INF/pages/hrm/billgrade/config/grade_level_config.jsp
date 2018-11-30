<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    $(function(){
        $("#add_level").click(function(){
            var content="<tr class='LevelTR'><td><input type='text' class= 'desc' style='width:100px' />"+
            "</td><td><input type='text' class = 'scorePoint' style='width:50px'/> "+
            "分及以上 </td>"+
            "<td><a style='text-decoration: underline;color: blue;' onclick='removeLevel(this);return false;' href='#'>删除</a></td></tr>";
            content = $(content);
            $(content).appendTo($("#levelPage"));
        });
        $("#save_btn").click(function(){
	        if(!check()){
	            return false;
	        }
	        var param=$("#form_edit").serialize();
	        $.post("<%=request.getContextPath()%>/billgrade/config_level_save.html",
	        		param,function(data){
	                var callback = function(){
	                	divClose();
	                };
	                if(data.success){
	                    processDataCall(data, callback);
	                }else{
	                    showWarning(data.text);
	                }
	                        
	        },"json");
	        return false;
	    });
	    
	    $("#cancel").click(function(){
	        divClose();
	    });
	});

    function removeLevel(obj){
           $(obj).closest("tr").remove();
    }

    function check(){
        var levelArray = $(".LevelTR");
        for(var i=0;i<levelArray.length;i++){
            var desc = $(levelArray[i]).find(".desc");
            var scorePoint = $(levelArray[i]).find(".scorePoint");
            var levelId = $(levelArray[i]).find(".levelId");
            if(trim($(desc).val())!='')
            {
	            if(scorePoint.length>0){
	                if(trim($(scorePoint).val())=='')
	                    {alert("分值范围不能为空！"); return false};
	            }
            }
            $(desc).attr("name","config.levelConfig.levelList["+i+"].desc");
            $(scorePoint).attr("name","config.levelConfig.levelList["+i+"].scorePoint");
            $(levelId).attr("name","config.levelConfig.levelList["+i+"].id");
        }
        return true;
        
    }
    </script>
</head>
<body>
    <form id="form_edit">
        <div class="tab">
            <table width="98%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <tfoot>
                    <tr>
                        <td colspan="3">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="add_level" type="button">增加等第</button>
                                <button id="save_btn">保 存</button>
                                <button id="cancel">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                   <tr>
                        <th>
                                                                            最大分值
                        </th>
                        <td  colspan="2" >
                            <input name="config.levelConfig.maxScore" value="${config.levelConfig.maxScore }"/>
                            <input type="hidden" name="config.id" value="${config.id }"/>
                        </td>
                    </tr>
                    <tr>
                        <th>等第描述</th><th>分值范围</th><th>操作</th>
                    </tr>
                </tbody>
                <tbody  id="levelPage">
	                <c:forEach items="${config.levelConfig.innerLevelList}" var="l">
	                    <tr class='LevelTR'>
	                        <td><input type='text' class='desc' style='width:100px' value="${l.desc }" />
	                        <input type="hidden" class='levelId' value="${l.id }" /></td>
	                        <td><input type='text' class='scorePoint' style='width:50px' value="${l.scorePoint }" />
	                                                                    分及以上</td>
	                        <td><a style='text-decoration: underline;color: blue;' onclick='removeLevel(this);return false;' href='#'>删除</a></td>
	                    </tr>
	                </c:forEach>
                </tbody>
                <tbody>
                    <tr class='LevelTR'>
                        <td><input type='text' class = 'desc' style='width:100px' 
	                        value='${config.levelConfig.lastLevel.desc}' />
                            <input type="hidden" class = 'levelId'
	                        value='${config.levelConfig.lastLevel.id}' />
                        </td>
                        <td>默认最低等次<input type="hidden" class = 'scorePoint' style='width:50px' 
                        value='0'/></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>
