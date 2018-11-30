<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <%@include file="/commons/hrm/head.ini"%>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
  <style type="text/css">
  .hrea{
    position: relative;
    display: inline-block;
    background: #D0EEFF;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.hrea:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
.image {
    position: absolute;
    font-size: 100px;
	padding: 4px 12px;
	line-height: 20px;
    right: 0;
    top: 0;
    opacity: 0;
}
</style>
  <script type="text/javascript">
    $(function() {
   
      // 执行
      $("#save").click(function() {

        //if ($.trim($("#guid").val()).length == 0) {
        //  if ($.trim($("#fwbm").val()).length == 0) {
        //    alert("服务编码不能为空");
        //    return false;
        //  }
          
        //  var p = /^[0-9]*$/g;
        //  var res = p.test($.trim($("#fwbm").val()));
        //  if(!res){
        //    alert("服务编码只能由数字组成");
        //    return false;
        //  }
        //}
        
        if ($.trim($("#fwmc").val()).length == 0) {
          alert("服务名称不能为空");
          return false;
        }
        
        $.ajax({
          url:"<%=request.getContextPath() %>/basedata/basedata_savePublish.html",
          type:"post",
          dataType:"json",
          data:$("#form_edit").serialize() + "&classId=" + $("#classId").val(),
          success:function(data) {
            divClose();
            $("form:first").submit();
          }
        });
        return false;
      });
      
      // 取消
      $("#cancel").click(function() {
        divClose();
        return false;
      });
      
      $("input[name='switch']").click(function() {
        $("#regularSwitch").val($(this).val());
      });
      
      if ("${model.regularSwitch}" == "") {
        $("#regularSwitch").val("off");
      }
      
    });
  </script>
</head>

<body>
  <form id="form_edit">
    <input type="hidden" id="guid" name="icsem.guid" value="${icsem.guid }">
    <div class="tab">
      <table width="100%" border="0" class="formlist" cellpadding="0" cellspacing="0">
        <thead>
          <tr>
            <th colspan="2">
              <span>发布服务</span>
            </th>
          </tr>
        </thead>
        <tfoot>
          <tr>
            <td colspan="2">
              <div class="bz">"<span class="red">*</span>"为必填项</div>
              <div class="btn">
                <button type="button" id="save">保存</button>
                <button type="button" id="cancel">取消</button>
              </div>
            </td>
          </tr>
        </tfoot>
        <tbody>
          <!--
          <tr>
            <th width="30%">
              <span class="red">*</span>服务编码
            </th>
            <td width="70%">
              <c:if test="${icsem.guid == null or icsem.guid eq '' }">
              <input type="text" class="text_nor" id="fwbm" name="icsem.fwbm" size="35" value="${icsem.fwbm }" maxLength="45" />
              </c:if>
              <c:if test="${icsem.guid != null and icsem.guid != '' }">
              ${icsem.fwbm }
              </c:if>
            </td>
          </tr>
          -->
          <tr>
            <th width="30%">
              <span class="red">*</span>服务名称
            </th>
            <td width="70%">
            	<input type="hidden" class="text_nor" id="fwbm" name="icsem.fwbm" size="35" value="${icsem.fwbm }" />
            	<c:if test="${icsem.fwmc != '' && icsem.fwmc != null}">
            		${icsem.fwmc }
            		<input type="hidden" class="text_nor" id="fwmc" name="icsem.fwmc" size="35" value="${icsem.fwmc }" maxLength="45" />
            	</c:if>
            	<c:if test="${icsem.fwmc eq null || icsem.fwmc eq ''}">
              		<input type="text" class="text_nor" id="fwmc" name="icsem.fwmc" size="35" value="${icsem.fwmc }" maxLength="45" />
            		
            	</c:if>
            </td>
          </tr>
          <tr>
            <th width="30%">
              <span class="red"></span>服务Logo图片
            </th>
            <td width="70%">
              ${imageHtml }
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </form>
</body>
<script type="text/javascript">
	$(function() {
		var fwmc = '${icsem.fwmc}';
		if(fwmc != null && fwmc != '') {
			$("div[name='image']").find("input").attr("disabled","disabled");
		}
	});
</script>
</html>