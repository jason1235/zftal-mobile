<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head_v5.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    $(function(){
        $("#save_btn").click(function(){
            if(!check()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/version/version_save.html",
                $("#form_edit").serialize()  ,function(data){
                    var callback = function(){
							//reflashPage();
							//location.reload();
						};
						if(data.success){
							$(".ymPrompt_close",window.parent.document).click();
                        	$("#search",window.parent.document).submit();
						}else{
							showWarning(data.text);
						}
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
        	$("#search",window.parent.document).submit();
            return false;
        });
        
        $(".close").click(function(){
        	$("#search",window.parent.document).submit();
            return false;
        });
    });
    
     function check()
    {
        
        var terrace=$("#terrace").val();
        var versionnum=$("input[name='query.versionnum']").val();
        var upgradeprompt=$("#upgradeprompt").val();
        
        if(terrace==null || terrace==''){
            errorAlert("平台类型不能为空");
            window.parent.scroll(0,0);
            return false;
        }
        if(versionnum==null || versionnum==''){
            errorAlert("版本号不能为空");
            window.parent.scroll(0,0);
            return false;
        }
        if(upgradeprompt==null || upgradeprompt==''){
            errorAlert("升级提示不能为空");
            window.parent.scroll(0,0);
            return false;
        }
        var apkfile = $("#fileHref").find("a").text();
		  if(apkfile == ""){
	  		errorAlert("请上传安卓apk包");
	  		window.parent.scroll(0,0);
      		return false;
		  }
        return true;
    }
     </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/version/version_save.html" name="search" id="form_edit" method="post">
    	<input name="query.id" type="hidden" value="${query.id }"/>
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">版本校验维护</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>平台类型
                        </th>
                         <td>
                            <select id="terrace" name="query.terrace"  value="${query.terrace }"  class="form-control">
                              <option value="0" <c:if test = "${query.terrace eq '0'}"> selected="selected"</c:if>>android</option>
                              <option value="1" <c:if test = "${query.terrace eq '1'}"> selected="selected"</c:if>>iphone</option>
                              <option value="2" <c:if test = "${query.terrace eq '2'}"> selected="selected"</c:if>>ipad</option>
                             </select>
                        </td>
              </tr>
              <tr>
                        <th  >
                            <span class="red">*</span>版本号
                        </th>
                         <td>
                            <input maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="query.versionnum" type="text" value="${query.versionnum }" class="form-control"/>
                        </td>
             </tr>
             <tr>
                        <th >
                            <span class="red">*</span>安卓apk文件上传
                        </th>
                         <td >
                         
                         ${fileHtml }
                             
                        </td>
             </tr>
             <tr>
                        <th >
                               <span class="red">*</span> 升级提示
                        </th>
                         <td >
                         <textarea maxlength="500" placeholder="不要超过500个字符哦(^_^)" rows="3" cols="30" id="upgradeprompt" name="query.upgradeprompt" value="${query.upgradeprompt }" class="form-control">${query.upgradeprompt }</textarea>
                        </td>
             </tr>
             <tr>
                        <th >
                           			 备注
                        </th>
                         <td>
                            <input maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="query.remark" type="text" value="${query.remark }" class="form-control"/>
                        </td>
             </tr>
             </tbody>
            </table>
       </div> 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>

</body>
</html>