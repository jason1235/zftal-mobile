<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
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
			$.post('<%=request.getContextPath()%>/message/messageSetup_update.html',param,function(data){
				var callback = function(){
					location.href = _path+"/message/messageSetup_list.html";
				}
				processDataCall(data,callback);
			},"json");
		});
		$("input[name='config.sftzbr']").change(function(){
            changeTzbr();
        });
        changeTzbr();
    });
    
    function validate(){
    	if($("input[name='config.xxlx']").val()==''){
    		alert("消息类型不能为空！");
    		return false;
    	}
    	if($("textarea[name='config.sql']").val()==''){
    		alert("数据sql不能为空！");
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

    function changeTzbr(){
        var tzbr=$("input[name='config.sftzbr']:checked").val();
        if(tzbr=="1"){
            $(".brtz").show();
        }else{
            $(".brtz").hide();
        }
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
					<span>${config.xxlx }设置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<input type="hidden" name="config.id" value="${config.id }"/>
			<tr>
				<th width="20%"><span class="red">*</span>消息类型</th>
				<td width="80%">
					<input type="text" name="config.xxlx" value="${config.xxlx }" style="width: 100%;"/>
				</td>
			</tr>
			<tr>
				<th><span class="red">*</span>数据sql</th>
				<td>
					<textarea name="config.sql" style="width: 100%;height: 200px;">${config.sql}</textarea>
				</td>
			</tr>
			<tr>
				<th><span class="red">*</span>状态</th>
				<td>
					<input type="radio" name="config.zt" value="1"/>开
					<input type="radio" name="config.zt" value="0"/>关
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
					<input type="radio" name="config.sftzbr" value="0"/>否
				</td>
			</tr>
			<tr class="brtz">
				<th>本人消息标题</th>
				<td>
					<input type="text" name="config.xxbtmb_br" value="${config.xxbtmb_br}" style="width: 100%;"/>
				</td>
			</tr>
			<tr class="brtz">
				<th>本人消息内容</th>
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
