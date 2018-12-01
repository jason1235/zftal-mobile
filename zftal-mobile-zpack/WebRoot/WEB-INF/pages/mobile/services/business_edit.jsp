<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
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
        $("#dwName").val("${model.classXtms}");
        $("#save_btn").click(function(){
            if(!check()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/serviceManager/business_save.html",
                $("#form_edit").serialize()  ,function(data){
                    var callback = function(){
							//reflashPage();
							//location.reload();
						};
						if(data.success){
							//processDataCall(data, callback);
							$(".ymPrompt_close",window.parent.document).click();
                        	$("#search",window.parent.document).submit();
						}else{
							errorAlert(data.text);
						}
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
            modalClose();
            return false;
        });
        
        $(".close").click(function(){
            modalClose();
            return false;
        });
        
    });
    
     function check()
    {
        
        var xtbm=$("input[name='model.classXtbm']").val();
        var xtmc=$("input[name='model.classXtmc']").val();
        var xtdz=$("input[name='model.classXtdz']").val();
        var procode = $("input[name='model.procode']").val();
        
        var chinese = /^([a-zA-Z0-9]+)$/;
		var flag = (chinese.test(xtbm));
        if(xtbm==null || xtbm==''){
            //showWarning("系统编码不能为空");
            errorAlert("系统编码不能为空");
            //$("#myModal_toji",window.parent.document).find(".modal-content").height(612);
            return false;
        }
		if(!flag){
		    //showWarning("系统编码只能为英文和字母的组合");
		    errorAlert("系统编码只能为数字和字母的组合");
		    //$("#myModal_toji",window.parent.document).find(".modal-content").height(612);
            return false;
		}
        if(xtmc==null || xtmc==''){
            //showWarning("系统名称不能为空");
            errorAlert("系统名称不能为空");
            //$("#myModal_toji",window.parent.document).find(".modal-content").height(612);
            return false;
        }
        //if(xtdz==null || xtdz=='' ){
            //showWarning("系统地址不能为空");
        //    errorAlert("系统地址不能为空");
        //    $("#myModal_toji",window.parent.document).find(".modal-content").height(612);
        //    return false;
        //}
        if(procode==null || procode=='' ){
            //showWarning("系统地址不能为空");
            errorAlert("procode编码不能为空");
            //$("#myModal_toji",window.parent.document).find(".modal-content").height(612);
            return false;
        }
        return true;
    }
     </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/serviceManager/business_save.html" name="search" id="form_edit" method="post">
            <div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">系统信息维护</h4>
            </div>
            <div class="modal-body" style="background-color: #fff;">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>系统编码
                        </th>
                         <td  >
                            <c:if test="${op!='add'}">
                            ${model.classXtbm }
                                <input value="${model.classXtbm }" name="model.classXtbm"  type="hidden" />
                                <input value="${model.classId }" name="model.classId" type="hidden"/>
                            </c:if>
                            <c:if test="${op == 'add'}">
                                <input id="xtbm" maxlength="30" placeholder="不要超过30个字符哦(^_^)" name="model.classXtbm" type="text" value="${model.classXtbm }" class="form-control"/>
                            </c:if>
                        </td>
              </tr>
              <tr>
                        <th  >
                            <span class="red">*</span>系统名称
                        </th>
                         <td>
                            <c:if test="${model.procode == '999' }">
                            ${model.classXtmc }
                            <input id="xtmc" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.classXtmc" type="hidden" value="${model.classXtmc }"/>
                            </c:if>
                            <c:if test="${model.procode != '999' }">
                            <input id="xtmc" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.classXtmc" type="text" value="${model.classXtmc }" class="form-control"/>
                            </c:if>
                        </td>
             </tr>
             <tr>
                        <th >
                                                                                 系统描述
                        </th>
                         <td >
                         <textarea maxlength="250" placeholder="不要超过250个字符哦(^_^)" rows="4" cols="20" id="dwName" name="model.classXtms" value="${model.classXtms }" class="form-control">${model.classXtms }</textarea>
                        </td>
             </tr>
             <tr>
                        <th><span class="red">*</span>PROCODE 编码</th>
                        <td >
                            <c:if test="${model.procode == '999' }">
                            ${model.procode }
                            <input maxlength="15" placeholder="不要超过15个字符哦(^_^)" id="xtmc" name="model.procode" type="hidden" value="${model.procode }"/>
                            </c:if>
                            <c:if test="${model.procode != '999' }">
                            <input maxlength="15" placeholder="不要超过15个字符哦(^_^)" id="xtmc" name="model.procode" type="text" value="${model.procode }" class="form-control"/>
                            </c:if>
                        </td>
             </tr>
             <tr>
                        <th >
                            系统地址
                        </th>
                         <td >
                            <input id="xtdz" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.classXtdz" type="text" value="${model.classXtdz }" class="form-control"/>
                        </td>
             </tr>
             <tr>
                        <th>单点系统编码</th>
                        <td >
                            <input maxlength="20" placeholder="不要超过20个字符哦(^_^)"  name="model.signalXtbm" type="text" value="${model.signalXtbm }" class="form-control"/>
                        </td>
             </tr>
             <tr >
             <th >
                            <span class="red">*</span>是否第三方系统
                        </th>
                         <td >
                         <select name="model.otherFlag"  value="${model.otherFlag }"  class="form-control">
                              <option value="0" <c:if test="${model.otherFlag == '0' }"> selected="selected"</c:if>>否</option>
                              <option value="1" <c:if test="${model.otherFlag == '1' }"> selected="selected"</c:if>>是</option>
                             </select>
                        </td>
             </tbody>
			 </tr>
             </tbody>
            </table>
       </div> 
            </div>
            <div class="modal-footer" style="background-color: #fff;">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
    </form>
<%--
<form action="<%=request.getContextPath()%>/serviceManager/business_save.html" name="search" id="form_edit" method="post">
       <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            <thead>
                    <tr>
                        <th colspan="2">
                            <span><font color="#0f5dc2" style="font-weight:normal;">服务信息</font></span>
                        </th>
                    </tr>
            </thead>
                
             <tbody>
             <tr>
                        <c:if test="${op!='add'}">
                                <input value="${model.classId }" name="model.classId" type="hidden"/>
                        </c:if>
             </tr>
              <tr>
                        <th  >
                            <span class="red">*</span>系统编码
                        </th>
                         <td  >
                            <c:if test="${op!='add'}">
                            ${model.classXtbm }
                                <input value="${model.classXtbm }" name="model.classXtbm"  type="hidden" />
                            </c:if>
                            <c:if test="${op == 'add'}">
                                <input id="xtbm" name="model.classXtbm" type="text" value="${model.classXtbm }" style="width:255px"/>
                            </c:if>
                        </td>
              </tr>
              <tr>
                        <th  >
                            <span class="red">*</span>系统名称
                        </th>
                         <td>
                         	<c:if test="${model.procode == '999' }">
                         	${model.classXtmc }
                            <input id="xtmc" name="model.classXtmc" type="hidden" value="${model.classXtmc }" style="width:255px"/>
                         	</c:if>
                         	<c:if test="${model.procode != '999' }">
                            <input id="xtmc" name="model.classXtmc" type="text" value="${model.classXtmc }" style="width:255px"/>
                         	</c:if>
                        </td>
             </tr>
             <tr>
                        <th >
                                                                                 系统描述
                        </th>
                         <td >
                         <textarea rows="3" cols="30" id="dwName" name="model.classXtms" value="${model.classXtms }" style="width:255px">
						 </textarea>
                        </td>
             </tr>
             <tr>
                        <th>PROCODE 编码</th>
                        <td >
                        	<c:if test="${model.procode == '999' }">
                         	${model.procode }
                            <input id="xtmc" name="model.procode" type="hidden" value="${model.procode }" style="width:255px"/>
                         	</c:if>
                         	<c:if test="${model.procode != '999' }">
                            <input id="xtmc" name="model.procode" type="text" value="${model.procode }" style="width:255px"/>
                         	</c:if>
                        </td>
             </tr>
             <tr>
                        <th >
                            <span class="red">*</span>系统地址
                        </th>
                         <td >
                            <input id="xtdz" name="model.classXtdz" type="text" value="${model.classXtdz }" style="width:255px"/>
                        </td>
             </tr>
             </tbody>
             <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn" type="button">保 存</button>
                                <button id="cancel" type="button" >取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
       </div> 
</form>
 --%>

</body>
</html>