<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
<script type="text/javascript">
$(function(){
    $("button[name='dataImport']").click(function(e){
        var form = $("#uploadForm");
        form.attr("action",_path+"/baseinfo/infoClassCheck_dataImport.html");
        //form.attr("target","frame");
        //alert(form.attr("enctype"));
        form.submit();
        e.preventDefault();//阻止默认的按钮事件，防止多次请求
        startProcess();
    });
});

function startProcess(){
    divClose();
    showProgress();
    requestProgress();
    //showWindow("导入信息提示", "<%=request.getContextPath()%>/baseinfo/infoClassData_info.html", 600, 400 );
}
</script>
  </head>
  <body>
<div class="tab">
    <form id="uploadForm" enctype="multipart/form-data" method="post" target="frame">
    <table class="formlist" width="100%">
      <tfoot>
        <tr>
          <td>
            <div class="btn">
              <button name="dataImport">导 入</button>
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
          </td>
        </tr>
      </tbody>
    </table>
    </form>
  </div>
  <iframe name="frame" src="about:blank" style="display:none"></iframe>
  </body>
</html>
