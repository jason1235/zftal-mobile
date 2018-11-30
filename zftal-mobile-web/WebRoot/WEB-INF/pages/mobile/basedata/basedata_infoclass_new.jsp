<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="com.zfsoft.hrm.dybill.enums.ScanStyleType"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/commons/hrm/head_v5.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    var childHeight = 0;
    $(function(){
    	//IFrameResize1();//初始化页面大小
        $("#save_btn").click(function(){
                if( $("#name").val() == "" ) {
                	errorAlert("名称不得为空，请重新输入！");
                    return false;
                }
                
                if( $("#identityName").val() == "" ) {
                	errorAlert("标识名不得为空，请重新输入！");
                    return false;
                }
                var p = new RegExp("^[a-zA-Z][\\w]*$");
                var res = p.test($("#identityName").val());
                if(!res){
                	errorAlert("标识名只能以字母开头，由字母数字及_组成");
                    return false;
                }
                
                if( $("#type").val() == "" ) {
                	errorAlert("类型不得为空，请重新输入！");
                    return false;
                }
                if( $("#index").val() == "" ) {
                	errorAlert("顺序码不得为空，请重新输入！");
                    return false;
                }
                var num = new RegExp("^[0-9]{1,2}$");
                if(!num.test($("#index").val())){
                	errorAlert("顺序码必须是0-99的数字");
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
                	errorAlert("存在排序字段名为空的行！");
                    return false;
                }
               	
        		$.post('<%=request.getContextPath() %>/infoclass/infoclass_save.html', $('form:last').serialize(), function(data){
        			window.parent.location.reload();
                }, "json");
                
                

                return false;
        });
        

        $("#displayNum").val('${model.displayNum}');
        $("#scanStyle").val('${model.scanStyle}');
        $("#pxsx").val('${model.pxsx}');
        $("#pxfs").val('${model.pxfs}');
        var pxsx = '${model.pxsx}';
        var pxfs = '${model.pxfs}';
        var pxsxList = pxsx.split(",");
        var pxfsList = pxfs.split(",");
        var selectHtml = "";
        var temp = $("input[name='guid']").val();
        if(temp != null && temp != ''){
            var properties =  ${model.propertiesList };
            if(pxsx != null && pxsx != ''){
	            for(var i = 0; i<pxsxList.length; i++){
	            	selectHtml += "<tr><th><span class='red'></span>排序</th><td>";
	            	selectHtml +=             "<select name='pxsx' id='pxsx' class='form-control' >";
	            	selectHtml +=                 "<option value=''>--请选择--</option>";
	            	for(var j = 0; j<properties.length; j++){
	            		if(properties[j].fieldName.trim() == pxsxList[i].trim()){
	            			selectHtml +=  "  <option value='"+properties[j].fieldName+"' selected='selected'>"+properties[j].name+"</option>";
	            		}else{
	            			selectHtml +=  "  <option value='"+properties[j].fieldName+"'>"+properties[j].name+"</option>";
	            		}
	            	}
	            	selectHtml +=         " </select></td>";
	            	selectHtml +=        " <td><select name='pxfsList' id='pxfs' class='form-control'>";
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
	            	selectHtml +=        " </select></td><td><button type='button' class='btn btn-default delete' id='delete'>删除</button></td></tr>";
	            }
	           $("#listbody").append(selectHtml);
	           //IFrameResize();
            }
        }
        $(".delete").click(function(){
        	$(this).closest("tr").empty();
        	IFrameResize();
        })
        $(".add").click(function(){
            	var Htmlselect = "";
            	Htmlselect += "<tr><th><span class='red'></span>排序</th><td> ";
            	Htmlselect +=             "<select name='pxsx' id='pxsx' class='form-control' >";
            	Htmlselect +=                 "<option value=''>--请选择--</option>";
            	for(var j = 0; j<properties.length; j++){
            		Htmlselect +=  "  <option value='"+properties[j].fieldName+"'>"+properties[j].name+"</option>";
            	}
            	Htmlselect +=         " </select></td>";
            	Htmlselect +=        " <td><select name='pxfsList' id='pxfs' class='form-control' >";
            	Htmlselect +=        "<option value='asc'";
            	Htmlselect += ">顺序 </option>";
            	Htmlselect +=       "<option value='desc'";
            	Htmlselect += ">倒序</option>";
            	Htmlselect +=        " </select></td><td><button type='button' class='btn btn-default delete' id='delete'>删除</button></td></tr>";
            	$("#listbody").append(Htmlselect);
            	IFrameResize();
            	$(".delete").click(function(){
                	$(this).closest("tr").empty();
                	IFrameResize();
                })
         })
        
        $("#cancel").click(function(){
            modalClose();
        });
        
        $(".close").click(function(){
            modalClose();
        });
    });
    
    
     </script>
</head>
<body style="background: #FFFFFF;">
    <form action="<%=request.getContextPath()%>/infoclass/infoclass_save.html" name="search" id="form_edit" method="post">
    	<input type="hidden" name="guid" value="${model.guid }" />
        <input type="hidden" name="catalog.guid" value="${model.catalog.guid }" />
        <input type="hidden" name="menuId" value="${model.menuId }" />
        <input type="hidden" name="lessThanOne" value="true" />
        <input type="hidden" name="type"  value="MULTIPLE" />
        <input type="hidden" name="displayNum"  value="2" />
        <input type="hidden" name="scanStyle"  value="<%=ScanStyleType.LIST %>" />
        
            <div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">信息类维护</h4>
            </div>
            <div class="modal-body" style="background-color: #fff;">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" style="overflow-x: hidden;">
             <tbody id="listbody">
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>名称
                        </th>
                         <td  colspan="3">
                            <input id="name" name="name" value="${model.name }" class="form-control" maxlength="16" placeholder="不要超过16个字符哦(^_^)" />
                        </td>
              </tr>
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>标识名
                        </th>
                         <td  colspan="3">
                         	<c:if test="${empty model.guid }">
                            <input type="text"  id="identityName" name="identityName" class="form-control" placeholder="不要超过16个字符哦(^_^)" maxlength="16"
                                    style="ime-mode:disabled" title="请输入英文字符或下划线" value="${model.identityName }" />
                            </c:if>
                            <c:if test="${!empty model.guid }">
                            <input type="text" id="identityName" name="identityName" class="form-control" placeholder="不要超过16个字符哦(^_^)" maxlength="16"
                                    readonly="readonly" value="${model.identityName }" />
                            </c:if>
                        </td>
              </tr>
              
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>信息来源
                        </th>
                         <td  colspan="3">
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
                        <th width="30%" >
                            <span class="red">*</span>顺序码
                        </th>
                         <td  colspan="3">
                         	<input id="index" name="index" value="${model.index }" class="form-control" maxlength="2" placeholder="请填写0-99的数字" />
                        </td>
              </tr>
              
              <tr>
                        <th colspan="4">
                        	<input type="checkbox" name="isOrNotLike" value="y" <c:if test="${isOrNotLike eq 'y'}">checked="checked"</c:if> />
                            <span class="red">*</span>模糊查询(说明：是否支持移动端列表页面模糊条件检索,默认选择不勾选,即默认不支持模糊查询)
                        </th>
              </tr>
              
              <tr>
                        <th colspan="4">
                        	<input type="checkbox" name="xxlall" value="y" <c:if test="${xxlall eq 'y'}">checked="checked"</c:if> />
                            <span class="red">*</span>全部查看(说明：是否支持移动端列表页面查看所有人的记录,默认不勾选，仅支持根据gh字段查询)
                        </th>
              </tr>
              
             </tbody>
            </table>
       </div> 
            </div>
            <div class="modal-footer" style="background-color: #fff;">
            	<c:if test="${!empty model.guid }">
            		<button type="button" class="btn btn-primary add" id="add">增加排序</button>
            	</c:if>
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
    </form>

</body>
</html>