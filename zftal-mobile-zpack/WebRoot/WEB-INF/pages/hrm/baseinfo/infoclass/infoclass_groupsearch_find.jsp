<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/org/data_tip.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript">
        var defStr='输入工号、姓名或者姓名拼音进行模糊查询';
        var notInputKey=false;
        $(function(){
            // 照片单元格合并行事件
            var size = $("table.formlist tbody > tr").length/$("table.formlist tbody").length;
            $("tbody img").closest("td").attr("rowspan",size);
            
            //监听条目点击事件
            $(".btn_zj").click(function(e){
                itemClick(e);
            });
            
            //查询按钮点击事件
            $(".brn_cx").click(function(e){
            	if(notInputKey){
                    $("#search_key_word").val(''); 
                }
                $("form").attr("method","POST");
                $("form").attr("action","<%=request.getContextPath()%>/baseinfo/infogroupsearch_list.html");
                $("form").submit();
            });
            //导出按钮点击事件
            $(".brn_dc").click(function(e){
            	if(notInputKey){
                    $("#search_key_word").val(''); 
                }
                $("form").attr("method","POST");
                $("form").attr("action","<%=request.getContextPath()%>/baseinfo/infogroupsearch_export.html");
                $("form").submit();
                $("form").attr("action","<%=request.getContextPath()%>/baseinfo/infogroupsearch_list.html");
                if($("#search_key_word").val() == ''){
                    $("#search_key_word").val(defStr);
                    $("#search_key_word").css('color','#AAAAAA');
                    notInputKey = true;
                }
                
            });
            $(".demo_xxxx_title a").click(function(){
                var globalid = $(this).prev().val();
                //location.href="<%=request.getContextPath() %>/normal/staffResume_list.html?globalid="+globalid+"&type=teacher";
                goUrl(_path+"/normal/staffResume_list.html?globalid="+globalid+"&type=teacher");
            });
            
            $("#list_body>tr").dblclick(function(){
                var id = $(this).find("input[id='globalid']").val();
                goUrl(_path+"/normal/staffResume_list.html?globalid="+id+"&type=teacher");
            });
            $("#btn_sz").click(function(){
                showWindow("显示字段设置", "<%=request.getContextPath()%>/infoclass/infoPropertyView_list.html?classId=${classId}", 480, 300 );
            });
            var xmtds=$("td[name=xm]");
            for ( var i=0; i<xmtds.length; i++){
                var xmtd = xmtds[i];
                var id = $(xmtd).closest("tr").find("input[id='globalid']").val();
                var html="<a style=\"color:#074695\" href=\"<%=request.getContextPath()%>/normal/staffResume_list.html?globalid="+id+"&type=teacher\" >"+$(xmtd).html()+ "</a>";
                $(xmtd).html(html);
                
            }
            //监听关闭按钮事件
            $(".close-icon").click(function(e){
                closeClick(e);
            });

            $("#search_key_word").focusin(function(){
            	$("#search_key_word").css('color','#000000');
                var textVal = $(this).val();
                if(textVal == '' ||notInputKey){
                    $(this).val('');
                }
            });
            if($("#search_key_word").val() == ''){
            	$("#search_key_word").val(defStr);
            	$("#search_key_word").css('color','#AAAAAA');
            	notInputKey = true;
            }
            $("#search_key_word").focusout(function(){
                var textVal = $(this).val();
                if($(this).val() == ''){
                    $(this).val(defStr);
                    $(this).css('color','#777777');
                    notInputKey = true;
                }else{
                	notInputKey = false;
                }
            });
            fillRows($("#pageSize").val(), "", "", false);//填充空行
            
            $("#infoClass").change(function(){
                var classId = $(this).val();
                changeClass(classId);
            });
            $("#property").change(function(e){
            	var propertyId = $(this).val();
            	changeProperty(propertyId);
           });
            changeClass($("#infoClass").val());
            
            
        });

        function changeClass(classId){
        	$.post("<%=request.getContextPath()%>/baseinfo/infogroupsearch_findProperty.html",
                    "classId="+classId,function(data){
                    if(data.success){
                        var strHtml="";
                        for(var i = 0;i<data.properties.length;i++){
                            if(data.properties[i].fieldType!='PHOTO'
                                &&data.properties[i].fieldType!='IMAGE'
                                    &&data.properties[i].fieldType!='FILE')
                            strHtml += "<option type='"+data.properties[i].fieldType
                                    +"' value='"+data.properties[i].guid
                                    +"' codeId='"+data.properties[i].codeId
                                    +"' format='"+data.properties[i].typeInfo.format
                                    +"' id='"+data.properties[i].guid+"'>"
                                    +data.properties[i].name+"</option>";
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
                 trHtml+="<div><input type='hidden' id='fieldValue' name='fieldValue' value='' />"+
                 "<input type='text' class='text_nor text_sel' value='' style='width: 180px;'  readonly='readonly' onclick=\"codePicker(this, '"+select.attr("codeId")+"')\" /></div>";
                 }
             else if(type=='DATE' ||type=='MONTH' ||type=='YEAR'){
                 trHtml+="<input type=\"text\" id='fieldValue1'  style=\"width: 120px;\" class=\"Wdate\" onfocus=\"WdatePicker({dateFmt:'"+select.attr("format")+"'})\"/> 至  "
                        +"<input type=\"text\" id='fieldValue2'  style=\"width: 120px;\" class=\"Wdate\" onfocus=\"WdatePicker({dateFmt:'"+select.attr("format")+"'})\"/>";
                 }
             else if(type=='SIGLE_SEL'){
                 trHtml+="<select id='fieldValue' style='width: 110px;'><option value=''></option><option value='1'>是</option><option value='0'>否</option></select>";
                 } 
             else{
                 trHtml+="<input type='text' id='fieldValue' style='width: 181px;'/>";
                 }
             $("#searchStyle").html(trHtml);
        }
        
        //关闭按钮事件
        function closeClick(e){
            var currentTarget=$(e.currentTarget);
            $(currentTarget).closest("dd").remove();
        }
        var i=0;
        //条目点击事件
        function itemClick(e){

        	var classId = $("#infoClass").val();
        	var propertyId = $("#property").val();
        	var title =$("#"+propertyId).html()+"("+ $("#"+classId).html()+")";
        	var type = $("#"+propertyId).attr("type");
        	var  value = $("#fieldValue").val();
        	var content = value;
        	if(type=="CODE"){
        		content =$("#fieldValue").next().val();
                }
            else if(type=='DATE' ||type=='MONTH' ||type=='YEAR'){
                var startDate = $("#fieldValue1").val();
                var endDate = $("#fieldValue2").val();
                if(trim(startDate) == "" && trim(endDate) == ""){
                	alert("请先输入查询条件再点击添加！");
                    return;
                }
            	value = startDate+"TO"+endDate;
            	content = startDate+"至"+endDate;
                }
            else if(type=='SIGLE_SEL'){
                if(value == '0'){
                	content ="否";
                }else{
                	content = "是";
                }
            }
        	if(value == null||trim(value)==''){
            	alert("请先输入查询条件再点击添加！");
        		return;
            };
        	var content="<dd> <a id=\"selected_"+i+"\" href=\"#\"><h5>"+title+"</h5>"+content
                +"<span class=\"close-icon\" title=\"取消\"></span></a> "
                +"<input type=\"hidden\" name=\"conditionItems\" value=\""+classId+"&"+propertyId+"&"+value+"\"> </dd>";
            i++;
            content = $(content);
            $(content).appendTo($(".selected-attr dl"));
            $(content).find(".close-icon").click(function(e){
                closeClick(e);
            });
        }
        function showTbody(obj,objTbody,className1,className2,html1,html2){
            if(obj.className==className1){
                obj.className=className2;
                obj.innerHTML=html2;
                document.getElementById(objTbody).style.display="none";
            }else{
                obj.className=className1;
                obj.innerHTML=html1;
                document.getElementById(objTbody).style.display="";
            }
        }
        
        /*
        *排序回调函数
        */
        function callBackForSort(sortFieldName,asc){
            $("#sortFieldName").val(sortFieldName);
            $("#asc").val(asc);
            $("#search").submit();
        }

        function beforSubmit(){
        	if(notInputKey){
                $("#search_key_word").val(''); 
            }
        }
    </script>
</head>

<body>
    <form id="search" method="post" onsubmit="beforSubmit();" action="<%=request.getContextPath() %>/baseinfo/infogroupsearch_list.html">
        <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
        <input type="hidden" id="asc" name="asc" value="${asc}"/>
        <input  type="hidden" name="resourceId" value="sys_user_findAllUser1"/>
        <div class="search_advanced" id="myTbody3">
            <div class="selected-attr after" style="float:none;min-height:30px;_height:30px">
                <h3>已选条件:</h3>
                <dl>
                <c:if test="${conditionItems != null}">
                <c:forEach items="${conditionItems}" var="conditionItem">
                            <dd><a href="#">
                            <h5>${viewMap[conditionItem][1] }(${viewMap[conditionItem][0]})</h5>
                            ${viewMap[conditionItem][2] }
		                    <span class="close-icon" title="取消"></span></a>
		                    <input type="hidden" name="conditionItems" value="${conditionItem }"> </dd>
	            </c:forEach>
	            </c:if>
                </dl>
            </div>
         <div class="adv_filter">
                <table border="0" width="780px">
                    <tbody>
                        <tr>
                            <td style="padding-left:68px;">
                            条件选择:
                            </td>
                            <td >
                               <select style="width: 160px" id="infoClass">
                               <c:forEach items="${infoClasses}" var="c">
                                   <option value="${c.guid }" id="${c.guid }">${c.name }</option>
                               </c:forEach>
                               </select>
                            </td>
                            <td>
                                <select style="width: 160px" id="property">
                                </select>
                            </td>
                            <td >
                            <span style="width: 380px" id = "searchStyle">
                            </span>   
                            </td>
                            <td class='buttonbox' style="padding-right:30px;">
                            <ul><li><a title='加入搜索条件列表' class='btn_zj' href='#'></a></li></ul>
                            </td>
                        </tr>
                    </tbody>
                </table>    
            </div>
            <div class="adv_filter">
                 <table border="0" width="92%">
                    <tbody>
                      <tr>
                        <td style="padding-left:68px;">
                           输入关键字: <input id="search_key_word" name="query.fuzzyValue" value="${query.fuzzyValue }" type="text" size="50" />
                            <button type="button" class="brn_cx"  name="查询" >查 询</button>
                            <button type="button" class="brn_dc"  name="导出" >导出</button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
            </div>
            
        </div>
    <div class="toolbox">
<!-- 按钮 -->
    <div class="buttonbox">
        <ul>
            <li><a id="btn_sz" class="btn_sz">显示字段设置</a></li>
        </ul>
    </div>
    <p class="toolbox_fot">
        <em></em>
    </p>
    </div>
    <div class="formbox" >
        <h3 class="datetitle_01">
            <span>组合查询结果<font color="#0457A7" style="font-weight:normal;"> (双击查看详细)</font></span>
        </h3>
        <div style="overflow-x:auto;width:795px;">
        <table width="100%" class="dateline tablenowrap" id="tiptab" >
            <thead id="list_head">
                <tr>
                <s:iterator value="query.clazz.viewables" var="p">
                <s:if test="fieldName != 'zp'">
                <c:if test="${p.fieldName eq sortFieldName}">
                    <td class="sort_title_current_${asc }" id="${p.fieldName }">${p.name }
                    </td>
                </c:if>
                <c:if test="${p.fieldName != sortFieldName}">
                    <td class="sort_title" id="${p.fieldName }">${p.name }
                    </td>
                </c:if>
                </s:if>
                </s:iterator>
                </tr>
            </thead>
            <tbody id="list_body">
            <s:iterator value="pageList" var="overall">
            <tr name="tr">
            <td style="display:none;"><input type='hidden' id='globalid' value="${overall.values['globalid'] }"/></td>
            <s:iterator value="query.clazz.viewables" var="p">
                <s:if test="fieldName != 'zp'">
                    <td name="${p.fieldName }">${overall.viewHtml[p.fieldName]}</td>
                </s:if>
            </s:iterator>
            </tr>
            </s:iterator>
            </tbody>
        </table>
        </div>
        <ct:page pageList="${pageList }"/>
    </div>
    </form>
</body>

</html>
