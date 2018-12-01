<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		$(function(){
			$('#action').click(function(){
				
				var time = $('input[name="deferDate"]').val();
				if(time.length == 0){
					alert('延期日期不能为空！');
					return;
				}
				operate('/contractNew_changeList');
			});
		});
		</script>
	</head>
	<body>
	    <div class="tab" id="tab">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="formlist">
			    <thead>
			    	<tr>
			        	<th colspan="4"><span>延期设置<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
			        </tr>
			    </thead>
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
			    <tbody>
			      <tr>
			        <th width="15%"><span class="red">*</span>延期日期</th>
			        <td width="35%">
			        	<input name="deferDate" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
			        </td>
			      </tr>
			      <tr>
			        <th width="15%">延期原因</th>
			        <td width="35%">
			        	<textarea  name="deferResult" style="width:88%;height:85px;"></textarea>
			        </td>
			      </tr>
			    </tbody>
		    </table>
	    </div>
	</body>
</html>
