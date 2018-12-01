<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
	$(function(){
		$("#save_btn").click(function(){
            $.post("<%=request.getContextPath()%>/billgrade/config_save_property.html?condition.id=${condition.id}",
                $("#selectedConds :input,#condition_num,#condition_score,#condition_classId,#condition_configId").serialize()
                ,function(data){
                    var callback = function(){
                    	location.href="<%=request.getContextPath() %>/billgrade/config_list_property.html?conditionQuery.configId=${condition.configId}";
                    };
                    if(data.success){
                        processDataCall(data, callback);
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
        });
        $("#condition_classId").val("${condition.billClassId}");
        assembleCondition();
		getPropertyList();
	});

    function addCondition(ele){
        var trObj=$(ele).closest("tr");
        /*
        if(trObj.find("input[name='fieldValue']").val()==''){
            alert("条件值不能为空！");
            return false;
        }
        */
        var tr2=$("#select-con-template tbody").html();
        var trObj2=$(tr2);
        trObj2.find("input[name='classId']").val(trObj.attr("classId"));
        trObj2.find("input[name='configId']").val(trObj.attr("configId"));
        trObj2.find("input[name='fieldValue']").val(trObj.find("input[name='fieldValue']").val());
        trObj2.find("td[name='propertyName']").text(trObj.attr("propertyName"));
        if(trObj.attr("fieldType")=='CODE'){
            trObj2.find("td[name='fieldValue_view']").text(trObj.find("input[name='fieldValue']").next().val());
        }else{
            trObj2.find("td[name='fieldValue_view']").text(trObj.find("input[name='fieldValue']").val());
        }
        trObj2.find("select[name='operator']").val(trObj.find("select[name='operator']").val());
        trObj2.find("select[name='logicalRel']").val(trObj.find("select[name='logicalRel']").val());
        $("#selectedConds>table>tbody").append(trObj2);
        assembleCondition();
    }
    function removeCondition(ele){
        var trObj=$(ele).closest("tr");
        trObj.remove();
        assembleCondition();
    }
    function assembleCondition(){
        var assembledConds_view="";
        var len=$("#selectedConds>table>tbody tr").length;
        $("#selectedConds>table>tbody tr").each(function(i){
            var obj=$(this);
            //拼接显示条件
            assembledConds_view+="<font color=\"red\">"+obj.find("select[name^='parenthesisBefore']").val()+"</font>";
            assembledConds_view+=obj.find("td[name='propertyName']").text();
            assembledConds_view+="&nbsp;<font color=\"blue\">"+obj.find("select[name='operator'] option:selected").text()+"</font>&nbsp;";
            if("NUMBER"==obj.data("fieldType")){
                assembledConds_view+=obj.find("td[name='fieldValue_view']").text();
            }else{
                assembledConds_view+="'"+obj.find("td[name='fieldValue_view']").text()+"'";
            }
            assembledConds_view+="<font color=\"red\">"+obj.find("select[name='parenthesisAfter']").val()+"</font>";
            if(i<len-1){
                assembledConds_view+="<font color=\"red\">&nbsp;"+obj.find("select[name='logicalRel'] option:selected").text()+"</font>&nbsp;";
            }
        });
        $(".selected-attr2 h4").html(assembledConds_view);
    }

    function chooseBillClass(){
    	$("#selectedConds>table>tbody").html("");
    	$(".selected-attr2 h4").html("");
    	getPropertyList();
    }

    function getPropertyList(){
        $.post("<%=request.getContextPath()%>/billgrade/config_findPropertyList.html",
                $("#condition_classId,#config_billConfigId").serialize(),function(data){
        	$("#propList>table>tbody").html("");
             if(data.propertyList!=null&&data.propertyList.length>0){
                 var propertyList = data.propertyList;
                  for(var i=0;i<propertyList.length;i++){
                      var trHtml = "<tr configId='"+propertyList[i].id+"' propertyName='"+propertyList[i].name+"' fieldType='"+propertyList[i].fieldType+"'>";
                      trHtml+="<th>"+propertyList[i].name +"</th>";
                      trHtml+="<td>"+$("#select-con-template tbody").find("td[guid='operator']").html()+"</td>";
                      if(propertyList[i].fieldType=="CODE"){
                    	  trHtml+="<td><div><input type='hidden' id='fieldValue' name='fieldValue' value='' />"+
                    	  "<input type='text'  class='text_nor text_sel' value='' style='width: 180px;'  readonly='readonly' onclick=\"codePicker(this, '"+propertyList[i].codeId+"')\" /></div></td>";
                          }
                      else if(propertyList[i].fieldType=='DATE' ||propertyList[i].fieldType=='MONTH' ||propertyList[i].fieldType=='YEAR'){
                          trHtml+="<td> <input type=\"text\" name=\"fieldValue\" style=\"width: 181px;\" class=\"Wdate\" onfocus=\"WdatePicker({dateFmt:'"+propertyList[i].infoProperty.typeInfo.format+"'})\"/></td>";
                          }
                      else if(propertyList[i].fieldType=='SIGLE_SEL'){
                    	  trHtml+="<td><select name='fieldValue' style='width: 110px;'><option value=''></option><option value='1'>是</option><option value='0'>否</option></select></td>";
                          } 
                      else{
                    	  trHtml+="<td><input type='text' name='fieldValue' style='width: 181px;'/></td>";
                          }
                      trHtml+="<td class='buttonbox'><ul><li><a title='加入搜索条件列表' onclick='addCondition(this);' class='btn_zj' href='#'></a></li></ul></td>";
  
                      trHtml+="</tr>";
                      $("#propList>table>tbody").append(trHtml);
                  }
             }
        },"json");
        return false;
    }
	</script>
	<style type="text/css">
	    #form_bill .prop-item li{
	        float:left;
	    }
	    #form_bill .selected-attr2 { background:#fcf7d9; border:1px solid #a7bdd3; float:left; width:99.81%; width:99.73%\9; *width:99.81%;
	        margin-top:0; }
	    #form_bill .selected-attr2 h3 { margin-left:20px; display:inline; float:left; line-height:25px; padding:5px 0; font-weight:normal; }
	    #form_bill .selected-attr2 h4 { margin-left:5px; float:left; width:375px; line-height:25px; padding:5px 0; font-weight:normal; }
    </style>
</head>

<body>
	<form id="form_bill">
		<div class="selected-attr2">
		    <div style="float: right;width: 100%;">
		      <h3> 表单类：</h3>
		      <div style="padding-top: 5px">
		      <select id="condition_classId" name="condition.billClassId" style="width: 350px;" onchange="chooseBillClass()">
		          <c:forEach items="${classes}" var="clazz">
		              <option value="${clazz.id }">${clazz.name }</option>
		          </c:forEach>
		      </select>
		      </div>
		    </div>
		    <div style="float: right;width: 100%">
	            <h3>条件描述：</h3>
	            <h4>
	            </h4>
            </div>
            <div style="float: right;width: 100%">
	            <h3> 分值：</h3>
	            <div style="padding-top: 5px">
	            <input id="condition_score" name="condition.score" style="width: 25px;" maxlength="2" value="${condition.score }"/>
	            <button style="float: right;" type="submit" id="save_btn"> 确定</button>
	            </div> 
            </div>
        </div>
        <div class="selected-attr2">
            
        </div>
        <div id="total">
            <input type="hidden" id="config_billConfigId" name="config.billConfigId" value="${config.billConfigId }"/>
            <input type="hidden" id="condition_configId" name="condition.configId" value="${config.id }"/>
            <div id="query-con2">
                <div id="selectedConds" class="tab" style="clear: both;max-height: 150px;overflow: auto;">
                    <table align="center" class="formlist"> 
                        <tbody>
                        <c:forEach items="${condition.properties }" var="p">
                                <tr>
                                    <input type="hidden" name="classId"/>
                                    <input type="hidden" name="configId" value="${p.propertyId }"/>
                                    <input type="hidden" name="fieldValue" value="${p.fieldValue }"/>
                                    <td>
                                        <select name="parenthesisBefore" onchange="assembleCondition()"> 
                                            <option value=" " <c:if test="${p.parenthesisBefore == ' ' }">selected</c:if>></option> 
                                            <option value="(" <c:if test="${p.parenthesisBefore == '(' }">selected</c:if>>&nbsp;(&nbsp;</option> 
                                            <option value="((" <c:if test="${p.parenthesisBefore == '((' }">selected</c:if>>&nbsp;((&nbsp;</option> 
                                            <option value="(((" <c:if test="${p.parenthesisBefore == '(((' }">selected</c:if>>&nbsp;(((&nbsp;</option> 
                                            <option value="((((" <c:if test="${p.parenthesisBefore == '((((' }">selected</c:if>>&nbsp;((((&nbsp;</option> 
                                            <option value="(((((" <c:if test="${p.parenthesisBefore == ')))))' }">selected</c:if>>&nbsp;(((((&nbsp;</option>
                                        </select>
                                    </td>
                                    <td name="propertyName">${p.billProperty.name}</td>
                                    <td guid="operator">
                                        <select name="operator" onchange="assembleCondition()">
                                             <c:forEach items="${operatorList}" var="op">
                                                <option value="${op.key }" <c:if test="${p.operator == op.key }">selected</c:if>>${op.text }</option> 
                                            </c:forEach>
                                        </select>       
                                    </td>
                                    <td name="fieldValue_view" align="left">${p.fieldValueString }</td>
                                    <td>
                                        <select name="parenthesisAfter" onchange="assembleCondition()">
                                            <option value=" " <c:if test="${p.parenthesisAfter == '' }">selected</c:if>></option> 
                                            <option value=")" <c:if test="${p.parenthesisAfter == ')' }">selected</c:if>>&nbsp;)&nbsp;</option> 
                                            <option value="))" <c:if test="${p.parenthesisAfter == '))' }">selected</c:if>>&nbsp;))&nbsp;</option> 
                                            <option value=")))" <c:if test="${p.parenthesisAfter == ')))' }">selected</c:if>>&nbsp;)))&nbsp;</option> 
                                            <option value="))))" <c:if test="${p.parenthesisAfter == '))))' }">selected</c:if>>&nbsp;))))&nbsp;</option> 
                                            <option value=")))))" <c:if test="${p.parenthesisAfter == ')))))' }">selected</c:if>>&nbsp;)))))&nbsp;</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="logicalRel" onchange="assembleCondition()">
                                            <c:forEach items="${logincalList}" var="l">
                                                <option value="${l.key }" <c:if test="${p.logicalRel == l.key }">selected</c:if>>${l.text }</option> 
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="buttonbox">
                                        <ul>
                                            <li>
                                                <a class="btn_sc" href="#" onclick="removeCondition(this)" title="删除这个条件项"></a>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div id="propList" class="tab" style="max-height: 250px;overflow: auto;">
                    <table align="center" class="formlist"> 
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
        
	</form>
	<table id="select-con-template" style="display: none;">
    <tbody>
    <tr>
        <input type="hidden" name="classId"/>
        <input type="hidden" name="configId"/>
        <input type="hidden" name="fieldValue"/>
        <td>
            <select name="parenthesisBefore" onchange="assembleCondition()"> 
                <option value=" "></option> 
                <option value="(">&nbsp;(&nbsp;</option> 
                <option value="((">&nbsp;((&nbsp;</option> 
                <option value="(((">&nbsp;(((&nbsp;</option> 
                <option value="((((">&nbsp;((((&nbsp;</option> 
                <option value="(((((">&nbsp;(((((&nbsp;</option>
            </select>
        </td>
        <td name="propertyName"></td>
        <td guid="operator">
            <select name="operator" onchange="assembleCondition()">
                 <c:forEach items="${operatorList}" var="op">
                    <option value="${op.key }">${op.text }</option> 
                </c:forEach>
                <!-- 
                <option value="not null">not null</option>
                <option value="null">null</option>
                 -->
            </select>       
        </td>
        <td name="fieldValue_view" align="left"></td>
        <td>
            <select name="parenthesisAfter" onchange="assembleCondition()">
                <option value=" "></option> 
                <option value=")">&nbsp;)&nbsp;</option> 
                <option value="))">&nbsp;))&nbsp;</option> 
                <option value=")))">&nbsp;)))&nbsp;</option> 
                <option value="))))">&nbsp;))))&nbsp;</option> 
                <option value=")))))">&nbsp;)))))&nbsp;</option>
            </select>
        </td>
        <td>
            <select name="logicalRel" onchange="assembleCondition()">
                <c:forEach items="${logincalList}" var="l">
                    <option value="${l.key }">${l.text }</option> 
                </c:forEach>
            </select>
        </td>
        <td class="buttonbox">
            <ul>
                <li>
                    <a class="btn_sc" href="#" onclick="removeCondition(this)" title="删除这个条件项"></a>
                </li>
            </ul>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>