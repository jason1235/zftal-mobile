<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
<script type="text/javascript">
function callback(data,status){//显示上传操作反馈信息
	tipsWindown("提示信息","text:"+data,"340","120","true","","true","id");
	$("#window-sure").click(function() {
		if( status ) {
			window.location.reload();
		}
		divClose();
	})
}
</script>
  </head>
  <body>
<div class="tab">
	<form action="${url }" enctype="multipart/form-data" method="post" target="frame">
    <table class="formlist" width="100%">
      <tfoot>
        <tr>
          <td>
          	<div class="btn">
              <button name="">导 入</button>
            </div>
          </td>
        </tr>
      </tfoot>
      <tbody>
        <tr>
          <th class="title_02">导入数据</th>
        </tr>
		<tr>
          <td>
			<p class="input_file">选择导入数据文件 <input name="file" type="file"></input></p>
			<input name="guid" type="hidden" value="${model.guid }"></input>
		  </td>
        </tr>
      </tbody>
    </table>
    </form>
  </div>
  <iframe name="frame" src="about:blank" style="display:none"></iframe>
  </body>
</html>
