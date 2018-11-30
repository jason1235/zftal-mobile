<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
        $(".close-icon").click(function(){
            $(this).closest("dd").remove();
            fillDL();
        });
        $("#action").click(function(){
        	var ddList = $("#dl").find(".selected_dd");
            for(var i=0;i<ddList.length;i++){
                var classname="list["+i+"]";
                $(ddList[i]).find(".selectId").attr("name",classname+".allotValue");
                $(ddList[i]).find(".type").attr("name",classname+".messageAllotType");
            }
            
            var param = $("#form1 :input").serialize();
            //console.log(param);
            $.post('<%=request.getContextPath()%>/message/allot_save.html',param,function(data){
                var callback = function(){
                };
                if(data.success){
                    processDataCall(data, callback);
                }else{
                    showWarning(data.text);
                }
            },"json");
        });
        fillDL();
    });
    
    function addItem(value, title,obj,typeName,type){
        if($("a[id=\"selected_"+type+"_"+value+"\"]").length>0){
            return;
        }
        var content="<dd class='selected_dd'> <a href=\"#\" id=\"selected_"+type+"_"+value+"\" >"
        +"<h5><span style='color:blue' >"+typeName+":</span>"+title+"</h5>"
        +"<span class=\"close-icon\" title=\"取消\"></span></a> "
        +"<input type=\"hidden\" class=\"type\" value=\""+type+"\">"
        +"<input type=\"hidden\" class=\"selectId\" value=\""+value+"\"></dd>";
        content = $(content);
        $(content).appendTo($(obj));
        $(content).find(".close-icon").click(function(){
            $(this).closest("dd").remove();
            fillDL();
        });
    }
    function chooseView(val,typeName,type){
        var result = showTopWin( _path + "/message/allot_" + val+".html", 440, 520, null );
        if(!result){
            result=window.returnValue;
        }
        if( result == null ) return;
        var dl = $("#dl");
        for(var i=0;i<result.length;i++)
            addItem(result[i][0],result[i][1],dl,typeName,type);
        fillDL();
    }

    function fillDL(){
        var l = $("#dl").find(".selected_dd").length;
        if(l==0){
            $("#dl").append("<dd id='default_dl'> <a href=\"#\"><h5><span style='color:blue' >默认所有用户</span></h5></a><dd>");
        }
        else{
        	$("#default_dl").remove();
        }
    }

    function getddval(){
        var tsdxlx=$("input[name='pushMsg.tsdxlx']:checked").val();
        dlObj=$("#"+tsdxlx+"_tr").find("dl");
        if(dlObj.length==0) return;
        var $checks = $(dlObj).find(".fsdxList");
        var values = "";
        for( var i = 0; i < $checks.length; i++ ) {
            $check = $checks.get(i);
            values += $check.value + ",";
        }
        $("input[name='pushMsg.tsdx']").val(values);
    }
    </script>
  </head>
  <body>
<div id="testID" >    
  <div class="tab">
    <table align="center" class="formlist">
        <thead>
            <tr>
                <th colspan="4">
                    <span>通知发送</span>
                </th>
            </tr>
        </thead>
        <tbody id="form1">
        <input type="hidden" name="messageAllot.mainId" value="${messageAllot.mainId }"/>
        <input type="hidden" name="messageAllot.type" value="${messageAllot.type }"/> 
            <!-- <input type="hidden" name="pushMsg.tsid" value="1"/> -->
            <!-- <input type="hidden" name="pushMsg.tssj" value="1"/> -->
            <tr>
                <td>
                     <div class="search_advanced" id="myTbody3">  
                       <div class="selected-attr after" style="float:none;min-height:30px;_height:30px">
                        <dl id ="dl" style="width: 450px">
                            <c:forEach items="${list}" var="allot">
                                <dd class='selected_dd'>
                                    <a href="#" id="selected_${allot.messageAllotType.key}_${allot.allotValue}" >
                                        <h5><span style='color:blue' >${allot.messageAllotType.name }:</span>${allot.allotValueText }</h5>
                                        <span class="close-icon" title="取消"></span>
                                    </a>
								        <input type="hidden" class="type" value="${allot.messageAllotType.key }" />
								        <input type="hidden" class="selectId" value="${allot.allotValue }" /></dd>
                            </c:forEach>
                            
                        </dl>
                       </div>
                        <button onclick="chooseView('personList','用户','USER')" type="button">添加人员</button>
                        <button onclick="chooseView('jsglList','角色','ROLE')" type="button">添加角色</button>
                    </div>
                </td>
            </tr>
        </tbody>
    <tfoot>
      <tr>
        <td colspan="4">
            <div class="bz">"<span class="red">*</span>"为必填项</div>
            <div class="btn">
                <button id="action" name="action" >保存</button>
            </div>
        </td>
      </tr>
    </tfoot>
    </table>
    </div>
</div>
  </body>
</html>
