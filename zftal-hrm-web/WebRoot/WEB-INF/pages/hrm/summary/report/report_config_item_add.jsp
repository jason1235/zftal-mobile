<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="code" uri="/WEB-INF/code.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
        
        $("#save").click(function(){
            if($("#name").val()==null||$("#name").val()==""){
                showWarning("名称不能为空，请重新输入！");
                return false;
            }
            if($("#condition").val()==null||$("#condition").val()==""){
                showWarning("条件不能为空，请重新输入！");
                return false;
            }
            
            $.post("<%=request.getContextPath() %>/summary/reportconfig_save_${itemType}.html",$("#itemAddForm").serialize(),function(data){
                var callback = function(){
                    $("form:first").submit();
                };
                processDataCall(data,callback);
            },"json");

            return false;
        });
        $("cancel").click(function(){
            divClose();
            return false;
        });

        $("#typeChoose").change(function(){
            typeChoose();
        });
        typeChoose();
    });

    function typeChoose(){
        $(".typeChoose").hide();
        var showTR = $("#typeChoose").val();
        $("#"+showTR).show();
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
    <form id="itemAddForm">
        <input type="hidden" name="id" value="${id }" />
        <input type="hidden" name="item.fieldName" value="${item.fieldName }" />
        <input type="hidden" name="index" value="${index }" />
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="2">
                            <span>报表维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th><span class="red">*</span>名称</th>
                        <td>
                            <input type="text" class="text_nor" id="name" name="item.name" size="25" value="${item.name }" />
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>类型</th>
                        <td>
                            <select id="typeChoose">
                                <option value="fieldChoose">信息类字段选择</option>
                                <option value="sqlEdit">SQL语句配置</option>
                            </select>
                        </td>
                    </tr>
                    <tr id="sqlEdit" class="typeChoose">
                        <th><span class="red">*</span>条件语句</th>
                        <td>
                            <textarea style="width:400px;height:200px" id="condition" name="item.condition" size="25">${item.condition }</textarea> 
                        </td>
                    </tr>
                </tbody>
            </table>
            
        </div>
    </form>
    <div id="fieldChoose" class="typeChoose">
                    <script type="text/javascript">
                    $(function(){
                        $("#infoClass").change(function(){
                            var classId = $(this).val();
                            changeClass(classId);
                        });
                        $("#property").change(function(e){
                            var propertyId = $(this).val();
                            changeProperty(propertyId);
                       });
                    });
                        changeClass($("#infoClass").val());
                    function changeClass(classId){
                        $.post("<%=request.getContextPath()%>/baseinfo/infogroupsearch_findProperty.html",
                                "classId="+classId,function(data){
                                if(data.success){
                                    var strHtml="";
                                    for(var i = 0;i<data.properties.length;i++){
                                        if(data.properties[i].fieldType!='PHOTO'
                                            &&data.properties[i].fieldType!='IMAGE'
                                                &&data.properties[i].fieldType!='FILE')
                                        var option = "<option type='"+data.properties[i].fieldType
                                                +"' value='"+data.properties[i].guid
                                                +"' fieldName='"+data.properties[i].fieldName
                                                +"' id='"+data.properties[i].guid;
                                        if(data.properties[i].virtual){
                                        	option+="' referFunc='"+data.properties[i].referFunc
                                                   +"' refer='"+data.properties[i].refer;
                                        }
                                        if(data.properties[i].fieldType=='CODE'){
                                            option+="' codeId='"+data.properties[i].codeId;
                                        }
                                        strHtml+=option+"'>"+data.properties[i].name+"</option>";
                                    }
                                    $("#property").html(strHtml);
                                    changeProperty($("#property").val());
                                }else{
                                    alert(data.text);
                                }
                            },"json");
                    }
                    function changeProperty(propertyId){
                        var select =$("#"+propertyId);
                        var type = select.attr("type");
                        var trHtml="";
                         if(type=="CODE"){
                             trHtml+="<input type='hidden' id='fieldValue' name='fieldValue' value='' />"+
                             "<input type='text' class='text_nor text_sel' value='' style='width: 180px;'  readonly='readonly' onclick=\"codePicker(this, '"+select.attr("codeId")+"')\" />";
                             }
                         else if(type=='DATE' ||type=='MONTH' ||type=='YEAR'){
                             trHtml+="<input type=\"text\" id='fieldValue'  style=\"width: 120px;\" class=\"Wdate\" onfocus=\"WdatePicker({dateFmt:'"+returnFormat(type)+"'})\"/>";
                             }
                         else if(type=='SIGLE_SEL'){
                             trHtml+="<select id='fieldValue' style='width: 110px;'><option value=''></option><option value='1'>是</option><option value='0'>否</option></select>";
                             } 
                         else{
                             trHtml+="<input type='text' id='fieldValue' style='width: 181px;'/>";
                             }
                         $("#searchStyle").html(trHtml);
                    }


                    function addCondition(ele){
                        var tr2=$("#select-con-template tbody").html();
                        var trObj2=$(tr2);
                        var propertyId = $("#property").val();
                        var classId = $("#infoClass").val();
                        trObj2.find("input[name='tableName']").val($("#"+classId).attr("tableName"));
                        trObj2.find("input[name='fieldType']").val($("#"+propertyId).attr("type"));
                        trObj2.find("input[name='fieldName']").val($("#"+propertyId).attr("fieldName"));
                        trObj2.find("input[name='fieldValue']").val($("#fieldValue").val());
                        trObj2.find("td[name='propertyName']").text($("#"+propertyId).html()+"("+$("#"+classId).attr("tableName")+")");
           
                        if($("#"+propertyId).attr('referFunc')!=null){
                        	trObj2.find("input[name='referFunc']").val($("#"+propertyId).attr('referFunc'));
                        	trObj2.find("input[name='refer']").val($("#"+propertyId).attr('refer'));
                        }
                        if($("#"+propertyId).attr("type")=='CODE'){
                            trObj2.find("td[name='fieldValue_view']").text($("#fieldValue").next().val());
                        }else{
                            trObj2.find("td[name='fieldValue_view']").text($("#fieldValue").val());
                        }
                        trObj2.find("select[name='operator']").val($("#operator").val());
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
                            assembledConds_view+="<font color=\"red\">"+obj.find("select[name='parenthesisBefore']").val()+"</font>";
                            assembledConds_view+=obj.find("td[name='propertyName']").text();
                            assembledConds_view+="&nbsp;<font color=\"blue\">"+obj.find("select[name='operator'] option:selected").text()+"</font>&nbsp;";
                            if("NUMBER"== $(this).find("input[name='fieldType']").val()){
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
                        assembleSQL();
                    }

                    function assembleSQL(){
                        var sql="";
                        var sql2 = null;
                        var classList = "";
                        var len=$("#selectedConds>table>tbody tr").length;
                        $("#selectedConds>table>tbody tr").each(function(i){
                            var obj=$(this);
                            var type = $(this).find("input[name='fieldType']").val();
                            //拼接显示条件
                            sql+=obj.find("select[name='parenthesisBefore']").val();
                            var tableName = obj.find("input[name='tableName']").val();
                            if(tableName != "OVERALL"){
                                if(classList.indexOf("#"+tableName+"#")==-1){
                                    classList+="#"+tableName+"#";
                                    if(sql2 == null)
                                        sql2 = " select 1 from overall o";
                                    sql2 +=" left join "+ tableName +" on ( o.gh = "+tableName+".gh and "+tableName+".deleted = '0')";
                                }
                            }else{
                            	tableName= "report"
                            }
                            var referFunc = $(this).find("input[name='referFunc']").val();
                            if(referFunc!=null&&referFunc!=''){
                            	sql+=referFunc+"("+replaceTableName(tableName,$(this).find("input[name='refer']").val())+")";
                            }else{
                            	sql+=tableName+"."+obj.find("input[name='fieldName']").val();
                            }
                            var operator =obj.find("select[name='operator']").val();
                            sql+=" "+operator+" ";
                            
                            if("NUMBER"==type){
                                sql+=obj.find("input[name='fieldValue']").val();
                            }
                            else if(type=='DATE'||type=='MONTH'||type=='YEAR'){
                                sql+= "to_data('"+obj.find("input[name='fieldValue']").val()+"','"+returnFormat(type)+"')";
                            }else{
                                if(operator=='NOT LIKE'||operator=='LIKE')
                                    sql+="'%"+obj.find("input[name='fieldValue']").val()+"%'";
                                else
                                    sql+="'"+obj.find("input[name='fieldValue']").val()+"'";
                            }
                            sql+=obj.find("select[name='parenthesisAfter']").val();
                            if(i<len-1){
                                sql+=" "+obj.find("select[name='logicalRel']").val()+" ";
                            }
                        });
                        if(sql2!=null){
                            sql=" exists ("+sql2+" WHERE report.gh=o.gh and "+sql+")";
                        }
                        $("#condition").html(sql);
                    }

                    function returnFormat(type){
                         if(type=='DATE'){
                             return 'yyyy-MM-dd';
                         }
                         else if(type=='MONTH'){
                             return 'yyyy-MM';
                         }
                         else if(type=='YEAR'){
                             return 'yyyy';
                         }
                         return 'yyyy-MM-dd';
                    }

                    function replaceTableName(tableName,refer){
                        var ref = /[a-zA-Z][a-zA-Z0-9_]*/g;
                        var word = refer.match(ref);
                        var substr= refer;
                        var result = "";
                        for ( var i = 0; i < word.length; i++) {
                            var length = word[i].length+substr.indexOf(word[i]);
                            result +=substr.substring(0,length).replace(word[i],tableName+'.'+word[i]);
                            substr = substr.substring(length);
						}
                        return result+substr;
                    }
                    </script>
                       
                    <form id="form_bill">
                        <div class="selected-attr2">
                            <div style="float: right;width: 100%">
                                <h3>条件描述：</h3>
                                <h4>
                                </h4>
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
                                        </tbody>
                                    </table>
                                </div>
                                <div id="propList" class="tab" style="max-height: 250px;overflow: auto;">
                                    <table align="center" class="formlist"> 
                                       <tbody>
                                        <tr>
                                            <th>
                                                                            字段
                                           </th>
                                            <td >
                                               <select style="width: 160px" id="infoClass">
                                               <c:forEach items="${infoClasses}" var="c">
                                                   <option tableName="${c.identityName }" value="${c.guid }" id="${c.guid }">${c.name }</option>
                                               </c:forEach>
                                               </select>
                                            </td>
                                            <td>
                                                <select style="width: 160px" id="property">
                                                </select>
                                            </td>
                                            </tr>
                                            <tr>
                                            <th>
                                                                            取值
                                            </th>
                                            <td colspan="2">
                                            <select id="operator"> 
                                                <option value="=">=</option>
                                                <option value="!=">!=</option>
                                                <option value=">">></option>
                                                <option value=">=">>=</option>
                                                <option value='<'><</option>
                                                <option value='<='><=</option>
                                                <option value="LIKE">包含</option>
                                                <option value="NOT LIKE">不包含</option>
                                            </select>       
                                            <span style="width: 200px" id = "searchStyle">
                                            </span>
                                            <button type="button" id="addField" onclick="addCondition(this)">条件添加</button>
                                            </td>
                                        </tr>
                                    </tbody> 
                                    </table>
                                </div>
                            </div>
                        </div>
                    </form>
            </div>
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
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
           
           </tfoot>
           
<table id="select-con-template" style="display: none;">
    <tbody>
    <tr>
        <td>
            <input type="hidden" name="classId"/>
            <input type="hidden" name="tableName"/>
            <input type="hidden" name="fieldType"/>
            <input type="hidden" name="fieldName"/>
            <input type="hidden" name="fieldValue"/>
            <input type="hidden" name="referFunc"/>
            <input type="hidden" name="refer"/>
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
                <option value="=">=</option>
                <option value="!=">!=</option>
                <option value=">">></option>
                <option value=">=">>=</option>
                <option value='<'><</option>
                <option value='<='><=</option>
                <option value="LIKE">包含</option>
                <option value="NOT LIKE">不包含</option>
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
                <option value="and">并且</option>
                <option value="or">或者</option>
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