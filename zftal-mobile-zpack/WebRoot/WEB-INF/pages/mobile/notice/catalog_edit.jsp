<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

	<style>
		.ui-autocomplete{
			z-index:12001;
			width: 500px
		}
		<c:if test="${view=='view'}">
		#beanview td{
		   width:240px;
		}
		</c:if>
	</style>
	<script type="text/javascript">
	
		
			$("#cancel").click(function(){
				divClose();
				return false;
			});
		
			
			
			$("#save_btn").click(function(){
				var name=$("#form_edit").find("input[name='query.name']").val();
				var ssywxt=$("#ssywxt").val();
				var grabOrNot=$("#grab").val();
				
				if(name==''){
					showWarning("种类名称为必填项");
					return false;
				}	
				if(ssywxt==''){
					showWarning("业务系统为必选项");
					return false;
				}
				if(grabOrNot==''){
					showWarning("是否抓取数据为必选项");
					return false;
				}	
				
				
				$.post("<%=request.getContextPath()%>/gggl/catalog_save.html",
						$("#form_edit").serialize(),function(data){
						var callback = function(){
							//reflashPage();
							location.reload();
						};
						if(data.success){
							processDataCall(data, callback);
						}else{
							showWarning(data.text);
						}
						
				},"json");
				return false;
			});
				
		
	</script>
</head>

<body>
	<form id="form_edit">
		<input type="hidden" name="op" value="${op }"/>
		<input type="hidden" name="query.id" value="${model.id}"/>  
		<table class="formlist">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">待办消息管理维护</font></span>
                        </th>
                    </tr>
                </thead>
        </table>
		<div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
				
				<tbody>
					<tr>
						
						
						<th>
							<span class="red">*</span>种类名称
						</th>
						<td>
						<input type="text" name="query.name" class="text_nor" style="width:200px;" value="${model.name }" />
						</td>
						
						
					</tr>
					
					<tr>
						<th >
                            <span class="red">*</span>所属业务系统
                        </th>
                         <td >
	                         <select id="ssywxt" name="query.businessSystem"  value="${model.businessSystem }" style="width:200px;">
	                                    <option value="" ></option>
	                       			 <c:forEach var="menu" items="${businessList}" varStatus="st">
	                         		    <option value="${menu.classId} "  <c:if test = "${model.businessSystem eq menu.classId}"> selected="selected"</c:if>>${menu.classXtmc}</option>
	                                 </c:forEach>
	          				 </select>
                        </td>
					
					</tr>
					
					<tr>
						<th >
                            <span class="red">*</span>是否抓取
                        </th>
                         <td >
	                         <select id="grab" name="query.grabOrNot"  value="${model.grabOrNot }" style="width:200px;">
	                         		    <option value="1 "  <c:if test = "${model.grabOrNot eq 1}"> selected="selected"</c:if>>是</option>
	                         		    <option value="0 "  <c:if test = "${model.grabOrNot eq 0}"> selected="selected"</c:if>>否</option>
	          				 </select>
                        </td>
					
					</tr>
					
				</tbody>
			</table>
			</div>
			<table class="formlist" >
			<tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                            <c:if test="${view !='view'}">
                                <button id="save_btn">保 存</button>
                            </c:if>
                                <button id="cancel">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
			</table>
	</form>
</body>
</html>
