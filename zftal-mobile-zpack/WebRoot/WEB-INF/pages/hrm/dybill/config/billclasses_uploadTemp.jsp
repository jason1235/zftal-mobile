<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
<script type="text/javascript">
function callback(data,status){//显示上传操作反馈信息
    tipsWindown("提示信息","text:"+data,"340","120","true","","true","id");
    $("#window-sure").click(function() {
        alertDivClose();
        if( status ) {
            $(".ymPrompt_close").click();
        }
    });
}
</script>
  </head>
  <body>
<div class="tab">
    <form action="bill/config_saveTemplate.html" enctype="multipart/form-data" method="post" target="frame">
    <table class="formlist" width="100%">
      <tfoot>
        <tr>
          <td colspan="2">
            <div class="btn">
              <button name="">上传</button>
            </div>
          </td>
        </tr>
      </tfoot>
      <tbody>
        <tr>
          <th colspan="2" class="title_02">模板上传</th>
        </tr>
        <tr>
          <td colspan="2">
            <p class="input_file">选择上传的模板文件 <input name="file" type="file"></input>
            <input type="hidden" name = 'spBillConfig.id' value="${spBillConfig.id }"/>
            <c:if test="${hasModel}">
            <a style="text-decoration: underline;color: blue;" 
             href="bill/config_downloadTemplate.html?spBillConfig.id=${spBillConfig.id}">模板下载</a>
            </c:if>
            </p>
          </td>
        </tr>
      </tbody>
    </table>
    </form>
  </div>
  <iframe name="frame" src="about:blank" style="display:none"></iframe>
  </body>
</html>
