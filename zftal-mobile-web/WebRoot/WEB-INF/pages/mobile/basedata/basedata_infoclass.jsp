<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="com.zfsoft.hrm.dybill.enums.ScanStyleType"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
        $(function(){
        	
            $("#save").click(function(){
                if( $("#name").val() == "" ) {
                    alert("名称不得为空，请重新输入！");
                    return false;
                }
                
                if( $("#identityName").val() == "" ) {
                    alert("标识名不得为空，请重新输入！");
                    return false;
                }
                var p = new RegExp("^[a-zA-Z][\\w]*$");
                var res = p.test($("#identityName").val());
                if(!res){
                    alert("标识名只能以字母开头，由字母数字及_组成");
                    return false;
                }
                
                if( $("#type").val() == "" ) {
                    alert("类型不得为空，请重新输入！");
                    return false;
                }
                if( $("#index").val() == "" ) {
                    alert("顺序码不得为空，请重新输入！");
                    return false;
                }
                var num = new RegExp("^[0-9]{1,2}$");
                if(!num.test($("#index").val())){
                    alert("顺序码必须是0-99的数字");
                    return false;
                }
                var leng = $("select[name='pxsx']").length;
                var flag = false;
                $("select[name='pxsx']").each(function(){
                	var pxsx = $(this).val();
                	 if(pxsx==null || pxsx==''){
                		 flag = true;
                		 //alert("存在排序字段名为空的行！");
                         return false;
                	}
                });	
                if(flag){
                	alert("存在排序字段名为空的行！");
                    return false;
                }
               	
        		$.post('<%=request.getContextPath() %>/infoclass/infoclass_save.html', $('form:last').serialize(), function(data){
                    var callback = function(){
                    	//$(".ymPrompt_close").trigger();
                    	parent.location.reload();
                    };
                    	processDataCall(data, callback);
                    
                }, "json");
                
                

                return false;
            })
            
            
            
            $("#cancel").click(function(){
            	parent.location.reload();
            })
            
            
            $("#displayNum").val('${model.displayNum}');
            $("#scanStyle").val('${model.scanStyle}');
            $("#pxsx").val('${model.pxsx}');
            $("#pxfs").val('${model.pxfs}');
            var pxsx = '${model.pxsx}';
            var pxfs = '${model.pxfs}';
            var pxsxList = pxsx.split(",");
            var pxfsList = pxfs.split(",");
            //alert(pxsxList);
            //alert(pxfsList.length);
            var selectHtml = "";
            var temp = $("input[name='guid']").val();
            if(temp != null && temp != ''){
	            var properties =  ${model.propertiesList };
	            if(pxsx != null && pxsx != ''){
		            for(var i = 0; i<pxsxList.length; i++){
		            	selectHtml += "<tr><th><span class='red'></span>排序</th><td>";
		            	selectHtml +=             "<select name='pxsx' id='pxsx'>";
		            	selectHtml +=                 "<option value=''>--请选择--</option>";
		            	for(var j = 0; j<properties.length; j++){
		            		if(properties[j].fieldName.trim() == pxsxList[i].trim()){
		            			selectHtml +=  "  <option value='"+properties[j].fieldName+"' selected='selected'>"+properties[j].name+"</option>";
		            		}else{
		            			selectHtml +=  "  <option value='"+properties[j].fieldName+"'>"+properties[j].name+"</option>";
		            		}
		            	}
		            	selectHtml +=         " </select>";
		            	selectHtml +=        " <select name='pxfsList' id='pxfs'>";
		            	selectHtml +=        "<option value='asc'";
		            	
		            	if(pxfsList[i].trim() == "asc"){
		            		selectHtml +=  "selected='selected'";
		            	}	
		            	selectHtml += ">顺序 </option>";
		            	selectHtml +=       "<option value='desc'";
		           		if(pxfsList[i].trim() == "desc"){
		               		selectHtml +=  "selected='selected'";
		               	}	
		            	selectHtml += ">倒序</option>";
		            	selectHtml +=        " </select><input type='button' id='delete' class='delete' value='删除'/></td></tr>";
		            }
		           $("#listbody").append(selectHtml);
	            }
            	
            }
            $(".delete").click(function(){
            	$(this).closest("tr").empty();
            	return false;
            })
            
            $("#add").click(function(){
            	var Htmlselect = "";
            	Htmlselect += "<tr><th><span class='red'></span>排序</th><td>";
            	Htmlselect +=             "<select name='pxsx' id='pxsx'>";
            	Htmlselect +=                 "<option value=''>--请选择--</option>";
            	for(var j = 0; j<properties.length; j++){
            		Htmlselect +=  "  <option value='"+properties[j].fieldName+"'>"+properties[j].name+"</option>";
            	}
            	Htmlselect +=         " </select>";
            	Htmlselect +=        " <select name='pxfsList' id='pxfs'>";
            	Htmlselect +=        "<option value='asc'";
            	Htmlselect += ">顺序 </option>";
            	Htmlselect +=       "<option value='desc'";
            	Htmlselect += ">倒序</option>";
            	Htmlselect +=        " </select> <input type='button' id='delete' class='delete' value='删除'/></td></tr>";
            	$("#listbody").append(Htmlselect);
            	
            	$(".delete").click(function(){
                	$(this).closest("tr").empty();
                	return false;
                })
            	return false;
            })
            return false;
        })
    </script>
</head>

<body>
    <form >
        <input type="hidden" name="guid" value="${model.guid }" />
        <input type="hidden" name="catalog.guid" value="${model.catalog.guid }" />
        <input type="hidden" name="menuId" value="${model.menuId }" />
        <input type="hidden" name="lessThanOne" value="true" />
        <input type="hidden" name="type"  value="MULTIPLE" />
        <input type="hidden" name="displayNum"  value="2" />
        <input type="hidden" name="scanStyle"  value="<%=ScanStyleType.LIST %>" />
        
        
        
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="2">
                            <span>信息类维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                            	<button id="add" type="button">增加排序</button>
                                <button id="save" type="button">保 存</button>
                                <button id="cancel" type="button">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody id="listbody">
                    <c:if test="${!empty model.guid }">
                    <tr>
                        <th>
                            <span class="red">*</span>信息类id
                        </th>
                        <td>
                            ${model.guid }
                        </td>
                    </tr>
                    </c:if>
                    <tr>
                        <th>
                            <span class="red">*</span>名称
                        </th>
                        <td>
                            <input type="text" class="text_nor" id="name" name="name" style="width:200px" value="${model.name }"  maxlength="16"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>标识名
                        </th>
                        <td>
                            <c:if test="${empty model.guid }">
                            <input type="text" class="text_nor" id="identityName" name="identityName" style="width:200px" maxlength="16"
                                    style="ime-mode:disabled" title="请输入英文字符或下划线" value="${model.identityName }" />
                            </c:if>
                            <c:if test="${!empty model.guid }">
                            <input type="text" class="text_nobor" id="identityName" name="identityName" style="width:200px" maxlength="16"
                                    readonly="readonly" value="${model.identityName }" />
                            </c:if>
                        </td>
                    </tr>
                    
                    
                    <tr>
                        <th>
                            <span class="red"></span>信息来源
                        </th>
                        <td>
                            <input type="hidden" name="xxly" value="${model.xxly}" />
                            <c:if test="${model.xxly == 'system'}">
                            系统初始化
                            </c:if>
                            <c:if test="${model.xxly != 'system'}">
                            用户自定义
                            </c:if>
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red">*</span>顺序码
                        </th>
                        <td>
                            <input type="text" class="text_nor" id="index" name="index" style="width:200px" maxlength="30" value="${model.index}" />
                        </td>
                    </tr>
                    
                    
                   
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>