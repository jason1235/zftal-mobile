<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
        $("input[name='startDate']").click(function(){
			showDownPrompt(this,"选择任务起始日期，默认为午夜整点执行");
        });
		$("#action").click(function(){
			if(!validate()){
				return false;
			}
			var param = $("#form1 input,#form1 select").serialize();
			$.post('<%=request.getContextPath()%>/post/postHistory_timing.html',param,function(data){
				var callback = function(){
					window.location.reload();
				};
				processDataCall(data, callback);
			},"json");
		});
		//$("input[name='day']").focus(function(){
			//showDownPrompt(this,"该月份中的日期号,请输入1-28的数字");
		//});
    });
    function validate(){
    	var obj = $("input[name='startDate']");
    	if(obj==''){
			showDownError(obj,"任务起始时间不能为空");
			return false;
    	}
    	var date = new Date();
    	var d = date.getFullYear()+"-"+(date.getMonth()+1>9?date.getMonth()+1:("0"+(date.getMonth()+1)))+"-"+(date.getDate()>9?date.getDate():("0"+date.getDate()));
    	if(d>=obj.val()){
    		showDownError(obj,"任务起始时间不能在今天之前");
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
					<span>定时设置<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th width="30%"><span class="red">*</span>执行周期</th>
				<td width="70%">
					<select name="period" style="width:200px">
					<option value="month" selected>每个月</option>
					<option value="quarter_year">每三月</option>
					<option value="half_year">每半年</option>
					</select>
				</td>
			</tr>
			<tr>
				<th width="30%"><span class="red">*</span>执行日期</th>
				<td width="70%">
					<input type="text" name="startDate" class="Wdate" style="width:200px" value="${startDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</td>
			</tr>
			<tr>
				<th width="30%"><span class="red">*</span>定时开关</th>
				<td width="70%">
					<input type="radio" name="trigger" value="1" checked/>开
					<input type="radio" name="trigger" value="0"/>关
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
