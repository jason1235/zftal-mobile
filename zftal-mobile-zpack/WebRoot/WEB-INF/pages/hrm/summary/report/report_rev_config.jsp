<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/select.js"></script>
    <style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
        $(function(){
        	updateIndex();
             $("#save").click(function(){
            	 updateIndex();
                     $.post('<%=request.getContextPath() %>/summary/reportconfig_revConfig_save.html?id=${id}', $('#fieldList').find('input').serialize()
                    		  +"&"+ $('#fieldList').find('select').serialize(), function(data){
                          var callback = function(){
                            	 divClose();
                            	 location.reload();
                             };
                             if(data.success){
                                 processDataCall(data, callback);
                             }else{
                                 showWarning(data.text);
                             }
                     }, "json");
                     return false;
            });
            
            $("#cancel").click(function(){
                divClose();
            });

            

            thinkField();
        });

        var caches ={};
        function thinkField(fieldClass){
            $(".fieldName").each(function(i){
                    $(this).autocomplete({
                    source: function(request,response){
                        var key=$.trim(request.term);
                        if(key!=""){
                           if(key in caches){
                               response(caches[key]);
                               }
                           $.ajax({
                               type:'post',
                               url:_path+'/summary/reportconfig_thinkFieldList.html',
                               dataType:'json',
                               data:"id=${id}&fieldName="+key,
                               cache:true,
                               success:function(data){
                                   caches[key]=data;
                                   response(data);
                                   }
                               });
                        }
                    },
                    minLength: 1, //触发条件，达到两个字符时，才进行联想
                    select: function( event, ui ) {
                        $(this).val(ui.item.fieldName);
                        return false;
                    }
               }).data("ui-autocomplete")._renderItem = function( ul, item ) {
                     return $("<li>")
                       .append( "<a>" + item.fieldName + "," +item.fieldType +"("+item.fieldLen +")</a>" )
                       .appendTo( ul );
               };
            });
        }

        function addField(){
            $("#fieldList").append("<tr class='field'>"
                    +"<td><input style='width: 100px;' class='fieldDesc' /></td>"
                    +"<td><select class='fieldType' onchange=\"changeFieldType(this)\"><option value='TEXT'>文本</option><option value='CODE'>代码</option><option value='TIME'>时间</option></select></td>"
                    +"<td><input style='width: 100px;' class='fieldName' /></td>"
                    +"<td><a class='opt_sy' onclick='move_up(this)'>上移</a> " 
                    +"<a class='opt_xy' onclick='move_down(this)'>下移</a> " 
                    +"<a class='opt_sc' onclick='removeField(this)'>删除</a> </td></tr>");
            updateIndex();
        }

        function changeFieldType(obj){
        	var td=$(obj).closest("td");
        	var type = $(obj).val();
        	$(td).find(".format").remove();
        	if(type=="CODE"){
        	td.append("<span class = 'format'>"
                	+"<input class='text_nor text_sel' style='width: 100px;' onmouseover=\"initSelectConsole(this, '/code/codeCatalog_load.html');\" type=\"text\" readOnly=\"readonly\" value=''/>"
                    +"<input type='hidden' class='fieldFormat' />"
                	+"</span>");
        	}
        	else if(type=="TIME"){
        		td.append("<span class = 'format'><select class='fieldFormat'>"
                        +"<option value='yyyy-MM'>月份</option>"
                        +"<option value='yyyy-MM-dd'>日期</option>"
                        +"<option value='yyyy-MM-dd HH:mm'>时间(到分)</option>"
                        +"</select></span>");
            }
        }
        
        function move_down(obj){
            var tr=$(obj).closest("tr");
            var preTr=tr.nextAll("tr[class='field']:first");
            var trHtml=tr.html();
            tr.html(preTr.html());
            preTr.html(trHtml);
            updateIndex();
        }
        function move_up(obj){
            var tr=$(obj).closest("tr");
            var preTr=tr.prevAll("tr[class='field']:first");
            var trHtml=tr.html();
            tr.html(preTr.html());
            preTr.html(trHtml);
            updateIndex();
        }
        function removeField(obj){
            var tr = $(obj).closest("tr");
            tr.remove();
            updateIndex();
        }
        function updateIndex(){
            var nodeTRs = $("#fieldList").find("tr");
            for(var index=0;index<nodeTRs.length;index++){
                var tr =$(nodeTRs[index]);
                tr.find(".fieldDesc").attr("name","fieldList["+index+"].fieldDesc");
                tr.find(".fieldType").attr("name","fieldList["+index+"].fieldType");
                tr.find(".fieldName").attr("name","fieldList["+index+"].fieldName");
                tr.find(".fieldFormat").attr("name","fieldList["+index+"].fieldFormat");
                if(index==0){
                    tr.find(".opt_sy").hide();
                }else{
                    tr.find(".opt_sy").show();
                }
                if(index==nodeTRs.length-1){
                    tr.find(".opt_xy").hide();
                }else{
                    tr.find(".opt_xy").show();
                }
            }
        }
            
    </script>
</head>

<body>
    <form id="form2">
        <div style="overflow-y: auto;height: 300px">
        <table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
        <thead>
            <th colspan="4">
             <span class="title_name">属性映射关系</span>
            </th>
            <tr>
                <th> 列名</th>
                <th> 类型</th>
                <th> 对应字段</th>
                <th> 操作</th>
            </tr>
        </thead>
        <tbody id="fieldList">
              <c:forEach items="${fieldList}" var="field">
                <tr class='field'>
	                <td><input style='width: 100px;' class='fieldDesc' value="${field.fieldDesc}" /></td>
	                <td>
	                    <select class='fieldType' onchange="changeFieldType(this)">
		                    <option value='TEXT' <c:if test="${field.fieldType=='TEXT' }">selected='selected'</c:if>>文本</option>
		                    <option value='CODE' <c:if test="${field.fieldType=='CODE' }">selected='selected'</c:if>>代码</option>
		                    <option value='TIME' <c:if test="${field.fieldType=='TIME' }">selected='selected'</c:if>>时间</option>
	                    </select>
	                    <span class="format">
	                       <c:if test="${field.fieldType == 'CODE'}">
		                       <input class='text_nor text_sel' style='width: 100px;' value="${field.fieldFormatText }" onmouseover="initSelectConsole(this, '/code/codeCatalog_load.html');" type="text" readOnly="readonly" value=''/>
	                           <input type='hidden' class='fieldFormat' value="${field.fieldFormat }"/>
	                       </c:if>
	                       <c:if test="${field.fieldType == 'TIME'}">
	                           <select class='fieldFormat'>
			                       <option value='yyyy-MM' <c:if test="${field.fieldFormat=='yyyy-MM' }">selected='selected'</c:if>>月份</option>
			                       <option value='yyyy-MM-dd' <c:if test="${field.fieldFormat=='yyyy-MM-dd' }">selected='selected'</c:if>>日期</option>
			                       <option value='yyyy-MM-dd HH:mm' <c:if test="${field.fieldFormat=='yyyy-MM-dd HH:mm' }">selected='selected'</c:if>>时间(到分)</option>
		                       </select>
                           </c:if>
	                    </span>
	                </td>
	                <td><input style='width: 100px;' class='fieldName' value="${field.fieldName}"/></td>
	                <td>
	                    <a class='opt_sy' onclick='move_up(this)'>上移</a> 
	                    <a class='opt_xy' onclick='move_down(this)'>下移</a> 
	                    <a class='opt_sc' onclick='removeField(this)'>删除</a> 
	                </td>
                </tr>
              </c:forEach>
         </tbody>
         </table>
         </div>
         <div>
            <table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
                <tfoot>
                    <tr>
                        <td>
                            <div class="btn">
	                            <button type="button" onclick="addField()">增加</button>
	                            <button type="button" id="save">保存</button>
	                            <button type="button">取消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
         </div>
    </form>
</body>
</html>