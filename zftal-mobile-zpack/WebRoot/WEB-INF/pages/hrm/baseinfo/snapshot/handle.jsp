<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
		$("#action").click(function(){
			var param = $("#form1 input").serialize();
			$.post('<%=request.getContextPath()%>/baseinfo/snapshot.html',param,function(data){
				//tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");

				//$("#window-sure").click(function() {
				//	divClose();
				//	if( data.success ) {
				//		window.location.reload();
				//	}
				//})
			},"json");
			divClose();
			updateProgress(_path+'/baseinfo/snapshot_progress.html',200);
		});
    });
    </script>
  </head>
  <body>
<div id="testID" >    
  <div class="tab">
	<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="4">
					<span>手动备份<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th width="30%"><span class="red">*</span>备份时间</th>
				<td width="70%"><input type="text" name="snapTime" class="Wdate" style="width:200px" value="${snapTime}" onclick="WdatePicker({dateFmt:'yyyy-MM'})"/></td>
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
