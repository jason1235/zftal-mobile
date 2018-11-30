<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    <style>
		.ui-autocomplete{
			z-index:12001;
			width: 360px
		}
	</style>
    <script type="text/javascript">
        $(function(){
            $("#save").click(function(){
                if( $("#staffId").val() == "" ) {
                    alert("请选择要导出的人员！");
                    return false;
                }

                var staffId = $("#staffId").val();
                var url = _path+"/export/export_export.html?exportConfig.id=${exportConfig.id}&staffId="+staffId;
                window.open( url, "400", "300", true);
                divClose();
                return false;
            });

            $("#cancel").click(function(){
                divClose();
                return false;
            });
            
            var caches = {};
		$("#staffName" ).autocomplete({
				     source: function(request,response){
		     var key=$.trim(request.term);
		     if(key!=""){
		    	    if(key in caches){
		    	        response(caches[key]);
			    	    }
		    	    $.ajax({
			    	    type:'post',
			    	    url:'<%=request.getContextPath() %>/normal/overallInfo_userListScopeThink.html',
			    	    dataType:'json',
			    	    data:'deptType=self&term='+key,
			    	    cache:true,
			    	    success:function(data){
			    	        caches[key]=data;
			    	        response(data);
				    	    }
			    	    });
			     }
			  },
				     minLength: 1, //触发条件，达到两个字符时，才进行联想
				     select: function( event, ui ) {
				    	 $("#staffId").val(ui.item.userId);
				    	 $("#staffName").val(ui.item.userName);
				    	 return false;
				     }
				}).data("ui-autocomplete")._renderItem = function( ul, item ) {
				      return $("<li>")
				        .append( "<a>" + item.userName+"("+item.userId+")    "+item.departmentName+"("
				        		+item.departmentId+") </a>" )
				        .appendTo( ul );
				};
        });
    </script>
</head>

<body>
    <form>
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="2">
                            <span>人员选择<font color="#0f5dc2" style="font-weight:normal;"></font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save" type="button" >导 出</button>
                                <button id="cancel" type="button" >取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <th><span class="red">*</span>人员选择</th>
                        <td>
                            <!--<ct:selectPerson name="staffId" id="staffId" value="${staffId}" width="150px" />
                        -->
                        	<input type="hidden" id="staffId" />
                        	<input type="text" id="staffName" />
                        	<span class="red">输入职工号、姓名查找</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
</body>
</html>