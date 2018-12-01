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
    	
    	$("input[name='menuModel.menuName']").keydown(function(event) {  
    		if (event.keyCode == 13) { 
	    		return false;
    		}
        //    if (event.keyCode == 13) {    
        //    	var menuName = $(this).val();
        //    	if(menuName==null || menuName==''){
        //            errorAlert("菜单名称不能为空");
        //            return false;
        //        }
        //    	$.post("<%=request.getContextPath()%>/basedata/basedata_saveMenuItem.html",
        //                $("#form_edit").serialize()  ,function(data){
    	//			if(data.success){
    	//				var callback = function(){
    	//					//reflashPage();
    	//					window.parent.location.reload();
    	//					event.stopImmediatePropagation(); 
    	//				};
    	//				window.parent.openAlert("保存成功", "s","auto", callback);
    	//			}else{
    	//				errorAlert(data.text);
    	//			}
                                    
        //            },"json");
        //        return false;  
        //    }    
        })   
        
        
        $("#save_btn").click(function(){
        	$(this).attr("disabled",true);
        	var menuName = $("input[name='menuModel.menuName']").val();
        	if(menuName==null || menuName==''){
                errorAlert("菜单名称不能为空");
                return false;
            }
        	$.post("<%=request.getContextPath()%>/basedata/basedata_saveMenuItem.html",
                    $("#form_edit").serialize()  ,function(data){
				if(data.success){
					var callback = function(){
						//reflashPage();
						window.parent.location.reload();
					};
					window.parent.openAlert("保存成功", "s","auto", callback);
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
     </script>
</head>
<body>
    <form  name="search" id="form_edit" method="post">
    <input type="hidden"  value="${menuModel.infoClassId }" name="menuModel.infoClassId"/>
						<c:if test="${menuModel.firstMenuId != ''}">
                                <input type="hidden"  value="${menuModel.firstMenuId }" name="menuModel.firstMenuId"/>
                        </c:if>
                        <c:if test="${menuModel.classCdid != ''}">
                                <input type="hidden"  value="${menuModel.classCdid }" name="menuModel.classCdid"/>
                        </c:if>
                        
            <div class="modal-header" style="background: #2587de;">
                <button type="button" class="close" style="color:#FFFFFF;"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel" style="color:#FFFFFF;">信息类移动端展示效果菜单信息维护</h4>
            </div>
            <div class="modal-body" style="background-color: #fff;">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>菜单名称
                        </th>
                         <td  >
                         <input id="cdmc" name="menuModel.menuName" type="text" value="${menuModel.menuName }" maxlength="30" placeholder="不要超过30个字符哦(^_^)" class="form-control"/>
                            
                        </td>
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

</body>
</html>