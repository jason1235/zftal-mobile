<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript">
    $(function(){
        
        var timeToLive = "${pushMsg.timeToLive}";
        if($("option[value='"+timeToLive+"']").length==0){
            $("#id_time_to_live_input").show();
            $("#id_time_to_live_handler").val("-1");
        }else{
            $("#id_time_to_live_input").hide();
            $("#id_time_to_live_handler").val(timeToLive);
        }
        $("#id_time_to_live_handler").change(function(){
        	var time=$("#id_time_to_live_handler").val();
            if(time=='-1'){
            	$("#id_time_to_live_input").show();
            	$("input[name='pushMsg.timeToLive']").val(timeToLive);
            }else{
            	$("#id_time_to_live_input").hide();
                $("input[name='pushMsg.timeToLive']").val(time);
            }
        });
        $(".tsdx_tr").hide();
        $("#ALL_tr").show();
        $("input[name='pushMsg.tsdxlx']").change(function(){
            var tsdxlx=$("input[name='pushMsg.tsdxlx']:checked").val();
            $(".tsdx_tr").hide();
            $("#"+tsdxlx+"_tr").show();
        });
        
		$("#action").click(function(){
			
			if(!validate()){
				return false;
			}
			
			var param = $("#form1 :input").serialize();
			//console.log(param);
			$.post('<%=request.getContextPath()%>/pushMsg/pushMsg_add.html',param,function(data){
				var callback = function(){
					//reflashPage();
					location.reload();
				};
                if(data.success){
                	openAlert("操作成功", "s","auto", callback);
                }else{
                    showWarning(data.text);
                }
			},"json");
		});

    });
    
    function validate(){
    	var appType=$("select[name='pushMsg.appType']").val();
        if(appType==null |appType==''){
            showWarning("推送APP不能为空");
            return false;
        }
    	var tsnr=$("input[name='pushMsg.tsnr']").val();
        if(tsnr==null || tsnr==''){
            showWarning("推送内容不能为空");
            return false;
        }
        getddval();
        var tsdxlx=$("input[name='pushMsg.tsdxlx']:checked").val();
        var type=$("input[name='pushMsg.tsdx']").val();
        if(tsdxlx!='ALL'&&tsdxlx!='ALL_USER'&&tsdxlx!='ALL_NOT_USER'&&(type==null || type=='')){
            showWarning("推送对象不能为空");
            return false;
        }
   		return true;
    }
   
    function groupView(val){
    	//var result = showTopWin( _path + "/pushMsg/pushMsg_" + val+".html", 440, 520, null );
    	var url = _path + "/pushMsg/pushMsg_" + val+".html";
    	$("#myModal_toji").find(".modal-content").width(440);
        $("#myModal_toji").find(".modal-content").height(520);
        $("#myFrame").attr("src", url);
        $("#myFrame").height(520);
        $("#myModal_toji").modal("show");
        
    }
    function showResult(result,val){
        if( result == null ) return;
        var dl = $("#dl_"+val);
        for(var i=0;i<result.length;i++)
            addItem(result[i][0],result[i][1],dl);
    }
    function addItem(value, title,obj){
        if($("a[id=\"selected_"+value+"\"]").length>0){
            return;
        }
        var content="<dd> <a id=\"selected_"+value+"\" href=\"#\"><h5>"+title+"</h5>"
        +"<span class=\"close-icon\" title=\"取消\"></span></a> "
        +"<input type=\"hidden\" class=\"fsdxList\" value=\""+value+"\"></dd>";
        content = $(content);
        $(content).appendTo($(obj));
        $(content).find(".close-icon").click(function(){
            $(this).closest("dd").remove();
        });
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
  <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
       <div class="sl_mod modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-left" id="myModalLabel">通知发送</h4>
            </div>
            <div class="modal-body">
                <div class="tab">
    <table align="center" class="formlist">
        <tbody id="form1">
            <input type="hidden" name="pushMsg.tsbt" value=""/> 
            <tr>
                <th width="20%"><span class="red">*</span>推送APP</th>
                <td width="80%">
                    <select name="pushMsg.appType" class="form-control">
                        <c:forEach items="${appList}" var="app">
                        <option value="${app.appType }">${app.detail }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th width="20%"><span class="red">*</span>推送内容</th>
                <td width="80%">
                    <input type="text" maxlength="1000" placeholder="不要超过1000个字符哦(^_^)" name="pushMsg.tsnr" value="${pushMsg.tsnr}" class="form-control"/>
                </td>
            </tr>
            <tr>
                <th width="20%"><span class="red">*</span>推送类型</th>
                <td width="80%">
                    <input type="radio" name="pushMsg.tsfs" value="1" checked="checked" />通知
                    <input type="radio" name="pushMsg.tsfs" value="0" />消息
                </td>
            </tr>
            <tr>
                <th width="20%"><span class="red">*</span>推送平台</th>
                <td width="80%">
                    <input type="radio" name="pushMsg.tspt" value="ALL" checked="checked">所有平台</input>
                    <input type="radio" name="pushMsg.tspt" value="Android">Android</input>
                    <input type="radio" name="pushMsg.tspt" value="IOS">IOS</input>
                    <input type="radio" name="pushMsg.tspt" value="WinPhone">WinPhone</input>
                </td>
            </tr>
            <tr>
                <th width="20%">离线消息保留时长</th>
                <td width="80%">
                    <select id="id_time_to_live_handler" class="form-control">
                           <option value="0">不保留</option>
                           <option value="60">1分钟</option>
                           <option value="600">10 分钟</option>
                           <option value="3600">1小时</option>
                           <option value="10800">3小时</option>
                           <option value="43200">12 小时</option>
                           <option value="86400">1天</option>
                           <option value="259200">3 天</option>
                           <option value="864000">10 天</option>
                           <option value="-1">自定义</option>
                    </select>
                    <span id="id_time_to_live_input">
                    <input name='pushMsg.timeToLive' style="width: 50px" value="${pushMsg.timeToLive}"/>秒
                    </span>
                </td>
            </tr>
            <tr>
                <th width="20%"><span class="red">*</span>推送对象</th>
                <td width="80%">
                    <input type="radio" name="pushMsg.tsdxlx" value="ALL" checked="checked">广播(所有人)</input>
                    <input type="radio" name="pushMsg.tsdxlx" value="ALL_USER">系统中用户</input>
                    <input type="radio" name="pushMsg.tsdxlx" value="ALL_NOT_USER">非系统中用户</input>
                    <input type="radio" name="pushMsg.tsdxlx" value="TAG">设备标签(Tag)</input>
                    <input type="radio" name="pushMsg.tsdxlx" value="ALIAS">设备别名(Alias)</input>
                    <input type="radio" name="pushMsg.tsdxlx" value="REGISTRATION_ID">Registration ID</input>
                </td>
            </tr>
            <tr id='TAG_tr' class='tsdx_tr'>
                <th width="20%"><span class="red">*</span></th>
                <td width="80%">
                     <div class="search_advanced" id="myTbody3">  
                       <div class="selected-attr after" style="float:none;min-height:30px;_height:30px">
                        <dl id ="dl_groupList">
                        </dl>
                       </div>
                        <button onclick="groupView('groupList')" class="btn btn-primary" type="button">添加分组</button>
                    </div>
                </td>
            </tr>
            <tr id='ALIAS_tr' class='tsdx_tr'>
                <th width="20%"><span class="red">*</span></th>
                <td width="80%">
                     <div class="search_advanced" id="myTbody3">  
                       <div class="selected-attr after" style="float:none;min-height:30px;_height:30px">
                        <dl id ="dl_personList">
                        </dl>
                       </div>
                        <button onclick="groupView('personList')" class="btn btn-primary" type="button">添加人员</button>
                    </div>
                </td>
            </tr>
            <tr id='REGISTRATION_ID_tr' class='tsdx_tr'>
                <th width="20%"><span class="red">*</span></th>
                <td width="80%">
                    <input type="text" name="pushMsg.tsdx" value="" style="width: 100%;"/>
                </td>
            </tr>
            
        </tbody>
    </table>
    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="action" name="action">发送</button>
            </div>
       </div>
<%--
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
		<input type="hidden" name="pushMsg.tsbt" value=""/> 
			<!-- <input type="hidden" name="pushMsg.tsid" value="1"/> -->
			<!-- <input type="hidden" name="pushMsg.tssj" value="1"/> -->
			
			
			<tr>
                <th width="20%"><span class="red">*</span>推送APP</th>
                <td width="80%">
                    <select name="pushMsg.appType">
                        <c:forEach items="${appList}" var="app">
                        <option value="${app.appType }">${app.detail }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
			<tr>
				<th width="20%"><span class="red">*</span>推送内容</th>
				<td width="80%">
					<input type="text" name="pushMsg.tsnr" value="${pushMsg.tsnr}" style="width: 100%;"/>
				</td>
			</tr>
			<tr>
                <th width="20%"><span class="red">*</span>推送类型</th>
                <td width="80%">
                    <input type="radio" name="pushMsg.tsfs" value="1" checked="checked" />通知
                    <input type="radio" name="pushMsg.tsfs" value="0" />消息
                </td>
            </tr>
			<tr>
				<th width="20%"><span class="red">*</span>推送平台</th>
				<td width="80%">
					<input type="radio" name="pushMsg.tspt" value="ALL" checked="checked">所有平台</input>
					<input type="radio" name="pushMsg.tspt" value="Android">Android</input>
					<input type="radio" name="pushMsg.tspt" value="IOS">IOS</input>
					<input type="radio" name="pushMsg.tspt" value="WinPhone">WinPhone</input>
				</td>
			</tr>
			<tr>
                <th width="20%">离线消息保留时长</th>
                <td width="80%">
                    <select id="id_time_to_live_handler">
                           <option value="0">不保留</option>
                           <option value="60">1分钟</option>
                           <option value="600">10 分钟</option>
                           <option value="3600">1小时</option>
                           <option value="10800">3小时</option>
                           <option value="43200">12 小时</option>
                           <option value="86400">1天</option>
                           <option value="259200">3 天</option>
                           <option value="864000">10 天</option>
                           <option value="-1">自定义</option>
                    </select>
                    <span id="id_time_to_live_input">
                    <input name='pushMsg.timeToLive' style="width: 50px" value="${pushMsg.timeToLive}"/>秒
                    </span>
                </td>
            </tr>
			<tr>
				<th width="20%"><span class="red">*</span>推送对象</th>
				<td width="80%">
					<input type="radio" name="pushMsg.tsdxlx" value="ALL" checked="checked">广播(所有人)</input>
					<input type="radio" name="pushMsg.tsdxlx" value="ALL_USER">系统中用户</input>
					<input type="radio" name="pushMsg.tsdxlx" value="ALL_NOT_USER">非系统中用户</input>
					<input type="radio" name="pushMsg.tsdxlx" value="TAG">设备标签(Tag)</input>
					<input type="radio" name="pushMsg.tsdxlx" value="ALIAS">设备别名(Alias)</input>
					<input type="radio" name="pushMsg.tsdxlx" value="REGISTRATION_ID">Registration ID</input>
				</td>
			</tr>
			<tr id='TAG_tr' class='tsdx_tr'>
				<th width="20%"><span class="red">*</span></th>
                <td width="80%">
					 <div class="search_advanced" id="myTbody3">  
					   <div class="selected-attr after" style="float:none;min-height:30px;_height:30px">
					    <dl id ="dl_groupList">
		                </dl>
		               </div>
		                <button onclick="groupView('groupList')" type="button">添加分组</button>
					</div>
				</td>
			</tr>
			<tr id='ALIAS_tr' class='tsdx_tr'>
                <th width="20%"><span class="red">*</span></th>
                <td width="80%">
                     <div class="search_advanced" id="myTbody3">  
                       <div class="selected-attr after" style="float:none;min-height:30px;_height:30px">
                        <dl id ="dl_personList">
                        </dl>
                       </div>
                        <button onclick="groupView('personList')" type="button">添加人员</button>
                    </div>
                </td>
            </tr>
            <tr id='REGISTRATION_ID_tr' class='tsdx_tr'>
                <th width="20%"><span class="red">*</span></th>
                <td width="80%">
                    <input type="text" name="pushMsg.tsdx" value="" style="width: 100%;"/>
                </td>
            </tr>
			
		</tbody>
    <tfoot>
      <tr>
        <td colspan="4">
            <div class="bz">"<span class="red">*</span>"为必填项</div>
          	<div class="btn">
            	<button id="action" name="action" >发送</button>
          	</div>
        </td>
      </tr>
	</tfoot>
	</table>
	</div>
</div> --%>
  </body>
</html>
