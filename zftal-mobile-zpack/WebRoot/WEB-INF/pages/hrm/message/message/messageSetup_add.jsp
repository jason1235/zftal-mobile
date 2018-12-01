<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <script type="text/javascript">
    $(function(){
    	initRadio('config.zt',"${config.zt}");
    	initRadio('config.sftzbr',"${config.sftzbr}");
		$("#action").click(function(){
			if(!validate()){
				return false;
			}
			var param = $("#form1 :input").serialize();
			var op = $("#op").val();
			if(op == "add"){
				$.post('<%=request.getContextPath()%>/message/messageSetup_add.html',param,function(data){
					var callback = function(){
						location.href = _path+"/message/messageSetup_list.html";
					}
					processDataCall(data,callback);
				},"json");
			}else{
			    $.post('<%=request.getContextPath()%>/message/messageSetup_update.html',param,function(data){
					var callback = function(){
						location.href = _path+"/message/messageSetup_list.html";
					}
					processDataCall(data,callback);
				},"json");
			}
		});
		
		//$("#typeChoose").val("${config.ywlx}");
		$("#infoclass").val("${config.tablename}");
		$("input[name='config.sftzbr']").change(function(){
			changeTzbr();
		});
		changeTzbr();
		var temp = "${config.sfjsts}";
        sfjsts(temp);
		$("input[name='config.sfjsts']").change(function(){
		    var temp = $("input[name='config.sfjsts']:checked").val();
            sfjsts(temp);
        });
		$("#typeChoose").change(function(){
            typeChoose();
        });
        typeChoose();
        
        $("#infoClass").change(function(){
            assembleSQL();
        });
        assembleSQL();
        
    });
    
    
    function sfjsts(temp){
        
     
        if(temp=="1"){
           $("input[name='config.sfjsts'][value=1]").attr("checked",true);
           $("input[name='config.xxlzq']").val("");
           //$("#xgzq").attr("value","");
           $("#xgzq").hide();
        }else{
           $("input[name='config.sfjsts'][value=0]").attr("checked",true);
           $("#xgzq").show();
        }   
    }
    
    function assembleSQL(){
           var tablename = $("#infoClass").val();
           var sql = "select a.gh,o.xm from " + tablename + " a left join overall o on o.gh = a.gh "
           $("#condition").html(sql);
           //alert(sql);
           
    }
    
    function typeChoose(){
	        $(".typeChoose").hide();
	        $("#sqlEdit").hide();
	        var showTR = $("#typeChoose").val();
	        if(showTR == "0")
	           $(".typeChoose").show();
	        else{
	           $("#sqlEdit").show();
	        }
	    }
	    
    function changeTzbr(){
    	var tzbr=$("input[name='config.sftzbr']:checked").val();
        if(tzbr=="1"){
        	$(".brtz").show();
        }else{
            $(".brtz").hide();
        }
    }
    
    function validate(){
    	if($("input[name='config.xxlx']").val()==''){
    		alert("消息类型不能为空！");
    		return false;
    	}
    	if($("input[name='config.sfjsts']:checked").val()==undefined)
    	     $("input[name='config.sfjsts']:checked").val(0);

    	if($("input[name='config.sfjsts']:checked").val() == 0 && $("input[name='config.xxlzq']").val()=='')
    	{
    		alert("修改周期不能为空！");
    		return false;
    	}
    	var tzbr=$("input[name='config.sftzbr']:checked").val();
        if(tzbr=="1"){
            if($("input[name='config.xxbtmb_br']").val()==""){
                alert("本人消息标题不能为空！");
                return false;
            }
            if($("textarea[name='config.xxnrmb_br']").val()==''){
                alert("本人消息内容不能为空！");
                return false;
            }
        }
    	var num=$("input[name='config.zjxsgs']").val();
    	var numRegex=/^([0-9]|[1-9][0-9])$/;
    	num=trim(num);
   		if(num!=""&&!numRegex.test(num)){
   			alert("显示人数只能输入小于99的数字！");
   			return false;
   		}
   		return true;
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
					<span>新增消息定时配置</span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<input type="hidden" name="config.id" value="${config.id }"/>
			<input type="hidden" name="op" value="${op }" id="op"/>
			<tr>
				<th width="20%"><span class="red">*</span>业务类型</th>
				<td width="80%">
					<select id="typeChoose" name="config.ywlx" >
					<option value="0" <c:if test="${config.ywlx == '0' }"> selected </c:if> >
					信息类
					</option> 
					<option value="1" <c:if test="${config.ywlx == '1' || config.ywlx == null }"> selected </c:if> >
					SQL语句配置
					</option>
                    </select>
				</td>
			</tr>
			<tr>
				<th width="20%" class="typeChoose" ><span class="red">*</span>信息类</th>
                <td width="80%" class="typeChoose">
                   <select style="width: 160px" id="infoClass" name="config.tableName">
                   <c:forEach items="${infoClasses}" var="c">
                       <option tableName="${c.identityName }" value="${c.identityName }_log" id="${c.guid }" 
                       <c:if test="${c.identityName == 'overall'}"> style="display: none;" </c:if>
                       >
                        ${c.name }
                        </option>
                   </c:forEach>
                   </select>
                </td>
            </tr>
            <tr>
				<th><span class="red">*</span>是否及时推送</th>
				<td>
					<input type="radio" name="config.sfjsts" value="1" <c:if test="${config.sfjsts == '1' }"> selected </c:if> />是
					<input type="radio" name="config.sfjsts" value="0" <c:if test="${config.sfjsts == '0' }"> selected </c:if> />否
				</td>
			</tr>
            <tr id="xgzq">
			    <th width="20%" ><span class="red">*</span>修改周期</th>
			    <td width="80%" >
                     <input type="text" id="zhouqi" name="config.xxlzq" value="${config.xxlzq }">天(备注：最少一天)
                </td>
			</tr>
			<tr>
				<th width="20%"><span class="red">*</span>消息类型</th>
				<td width="80%">
					<input type="text" name="config.xxlx" value="${config.xxlx }" style="width: 100%;"/>
				</td>
			</tr>
			<tr id="sqlEdit" >
				<th><span class="red">*</span>数据sql</th>
				<td>
					<textarea id="condition"  name="config.sql" style="width: 100%;height: 200px;">${config.sql}</textarea>
				</td>
			</tr>
			<tr>
				<th><span class="red">*</span>状态</th>
				<td>
					<input type="radio" name="config.zt" value="1" <c:if test="${config.zt == '1' }"> selected </c:if> />开
					<input type="radio" name="config.zt" value="0" <c:if test="${config.zt == '0' }"> selected </c:if> />关
				</td>
			</tr>
			<tr>
				<th>接收人</th>
				<td>
					<ct:selectPerson id="jobName" name="config.jsr" value="${config.jsr }" single="false" width="100%"/>
				</td>
			</tr>
			<tr>
				<th>消息标题</th>
				<td>
					<input type="text" name="config.xxbtmb_jsr" value="${config.xxbtmb_jsr}"  style="width: 100%;"/>
				</td>
			</tr>
			<tr>
				<th>消息内容</th>
				<td>
					<textarea name="config.xxnrmb_jsr" style="width: 100%;">${config.xxnrmb_jsr}</textarea>
				</td>
			</tr>
			<tr>
				<th>显示人数</th>
				<td>
					<input type="text" name="config.zjxsgs" value="${config.zjxsgs}"/>
				</td>
			</tr>
			<tr>
				<th><span class="red">*</span>是否通知本人</th>
				<td>
					<input type="radio" name="config.sftzbr" value="1"/>是
					<input type="radio" name="config.sftzbr" value="0" checked="checked"/>否
				</td>
			</tr>
			<tr class="brtz">
				<th><span class="red">*</span>本人消息标题</th>
				<td>
					<input type="text" name="config.xxbtmb_br" value="${config.xxbtmb_br}" style="width: 100%;"/>
				</td>
			</tr>
			<tr class="brtz">
				<th><span class="red">*</span>本人消息内容</th>
				<td>
					<textarea name="config.xxnrmb_br" style="width: 100%;">${config.xxnrmb_br}</textarea>
				</td>
			</tr>
			
		</tbody>
    <tfoot>
      <tr>
        <td colspan="4">
            <div class="bz">"<span class="red">*</span>"为必填项</div>
          	<div class="btn">
            	<button id="action" name="action" >保 存</button>
            	<button name="cancel" onclick='divClose();'>取 消</button>
          	</div>
        </td>
      </tr>
	</tfoot>
	</table>
	</div>
</div>
  </body>
</html>
