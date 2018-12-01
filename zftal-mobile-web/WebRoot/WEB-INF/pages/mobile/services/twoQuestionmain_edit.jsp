<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
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
        	var name 		= $("input[name='model.papermainname']").val();
            if(name==null || name==''){
                errorAlert("问卷名称不能为空");
                return false;
            }
            $.post(_path+"/serviceManager/twoQuestion_save.html",$("#form_edit").serialize(),function(data){
                if(data.success){
                	
                	parent.location.href = _path+"/serviceManager/twoQuestion_toModifyQuestion.html?query.papermainid="+data.id+"&op=add";
                }else{
                    errorAlert(data.text);
                }
            },"json");
        });
        
        $("#cancel").click(function(){
        	window.parent.$('#myModal_toji').modal('hide');
        	return false;
        });
        
        $(".close").click(function(){
        	window.parent.$('#myModal_toji').modal('hide');
        	return false;
        });
    });
    
     </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/serviceManager/twoQuestion_save.html" name="search" id="form_edit" method="post">
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">问卷信息</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
            	<tr>
                        <th width="30%" >
                            <span class="red">*</span>问卷名称
                        </th>
                         <td >
                              <input id="papermainname" maxlength="50" placeholder="不要超过50个字符哦(^_^)" name="model.papermainname" type="text" value="${model.papermainname }" class="form-control"/>
                        </td>
              </tr>
              <tr>
                        <th width="30%" >
                           				 问卷介绍
                        </th>
                         <td >
                              <textarea maxlength="250" placeholder="不要超过250个字符哦(^_^)" rows="4" cols="30" id="instruction" name="model.instruction" value="${model.instruction}" class="form-control"></textarea>
                        </td>
              </tr>
              <tr>
                        <th width="30%" >
                            			问卷备注
                        </th>
                         <td >
                              <input id="remark" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.remark" type="text" value="${model.remark }" class="form-control"/>
                        </td>
              </tr>
              <tr>
                        <th width="30%" >
                            			仅发起人可查看结果
                        </th>
                         <td >
                            <select name="model.qn_owner"  class="form-control">
	                              <option value="1" <c:if test="${model.qn_owner == '1' }"> selected="selected"</c:if>>是</option>
	                             <option value="0" <c:if test="${model.qn_owner == '0' }"> selected="selected"</c:if>>否</option>
                    		</select>
                        </td>
              </tr>
             </tbody>
            </table>
       </div> 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">下一步</button>
            </div>
        </div>
    </form>

</body>
</html>