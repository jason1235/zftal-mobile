<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/hrm/head_v5.ini" %>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
		<style type="text/css">
			.ui-autocomplete{
				z-index:12001;
				width: 500px
			}
			.formlist td,.formlist th {
				padding-left:10px;
			}
			.form-control {
				color:#000;
			}
		</style>
		<script type="text/javascript">
			var regexp=/^[0-9]+(.[0-9]{0,2})?$/;
			var reg = /.*\?fileGuid=(.*)/;
			$(function(){
				$("#cancel").click(function(){
					history.go(-1);
				});
				
				$("#save_btn").click(function(){
					var foodName = $("#titleInput");
					if(foodName == null || foodName.val()==''){
						errorAlert("请填写食品名称");
						window.parent.scroll(0,0);
						return false;
					} else {
					   foodName = foodName.val();
					}
					
					/*var storage = $("#storage").val();
					if(storage==null||storage==""||storage==0||isNaN(storage)){
						errorAlert("请输入正确库存数量");
						window.parent.scroll(0,0);
						return false;
					}*/
					
					var foodCataid = $("#foodcata").val();
					if(foodCataid==null||foodCataid==""){
					    errorAlert("请选择所属类别");
						window.parent.scroll(0,0);
						return false;
					}
					
					var price = $("#price").val();
					if(price==null||price==""||price==0||!regexp.test(price)){
					    errorAlert("请输入含有0-2位小数的价格,如10、10.5、10.50");
						window.parent.scroll(0,0);
						return false;
					}
					
					
					var $img = $('.picDiv img').first();
					if ($img != null) {
						var imgPath = $img.attr('src');
						var ret = reg.exec(imgPath);
						if (ret != null && ret.length > 1) {
							var guid = ret[1];
							if(guid==null||guid==""){
								errorAlert("请上传商品图片");
								window.parent.scroll(0,0);
								return false;
							}
						}
					}
					$.post("<%=request.getContextPath()%>/canteenfood/food_saveOrUpdate.html",
						$("#form_edit").serialize(),function(data){
							if(data.success){
								window.parent.scroll(0,0);
								openAlert(data.text, "s","auto", function(){
								  location.href = _path + "/canteenfood/food_list.html";
							    });
							}else{
								errorAlert(data.text);
								window.parent.scroll(0,0);
							}
					 },"json");
					return false;
				});
				
				$('#back').click(function(){
					location.href = _path + "/canteenfood/food_list.html";
				});
				
			});	
		</script>
  </head>
  
  <body>
    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <input type="hidden" name="op" value="${op }"/>
        <input type="hidden" name="query.foodId" value="${model.foodId}"/>
        <div class="sl_mod modal-content" style="border:none">
            <div class="modal-header">
                <button type="button" class="close" onclick="history.go(-1)">
	                <span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span>
	                <span class="sr-only">Close</span>
                </button>              
                <h4 class="modal-title text-left" id="myModalLabel">食品管理</h4>      
            </div>
            <div class="modal-body">
            	<jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
            	<div class="tab" style="clear: both;">
            		<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            			<tbody>
		                   <tr id="titleTr">
		                        <th style="width:15%"><span class="red">*</span>食品名称:</th>
		                        <td>
		                         <input id="titleInput" type="text" name="query.foodName" maxlength="100" placeholder="不要超过100个字符哦(^_^)" 
		                         	class="form-control" value="${model.foodName}" />
		                        </td>
		                    </tr>
		                    <!--<tr>
		                        <th><span class="red">*</span>食品库存:</th>
		                        <td>
		                          <input type="text" id="storage"  name="query.storage" maxlength="10" placeholder="" 
		                          	class="form-control" value="${model.storage}" />
		                        </td>
		                    </tr>-->
		                    <tr>
		                        <th><span class="red">*</span>所属食品类别:</th>
		                        <td>
		                            <s:if test="foodcataList!= null">
		                                <s:select id="foodcata" name="query.foodCataid" list="foodcataList" listKey="foodcataId" 
		                                  listValue="foodcataName" value="#request.model.foodCataid" theme="simple" cssClass="form-control"></s:select>
		                            </s:if>
		                            <s:else>
		                                <select id="foodcata" name="query.foodCataid" class="form-control"></select>
		                            </s:else>
		                        </td>
		                    </tr>
		                    <tr>
		                        <th style="width:15%"><span class="red">*</span>价格:</th>
		                        <td>
		                        <input type="text" id="price" name="query.price"  placeholder="请输入数字" 
		                        	class="form-control" value="${model.price}" />
		                        </td>
		                    </tr>
		                    <tr>
		                        <th><span class="red">*</span>状态: </th>
		                        <td>
		                            <select name="query.isactive"  value="${model.isactive}" class="form-control">
		                               <option value="1" <c:if test = "${model.isactive eq 1}"> selected="selected"</c:if>>启用</option>
			                           <option value="0" <c:if test = "${model.isactive eq 0}"> selected="selected"</c:if>>禁用</option>
		                            </select>
		                        </td>
		                    </tr>
		                    <tr id="detaillogo" class="detaillogo"> 
			                    <th style="width:90px;"><span class="red">*</span>食品图片：</th>
			                    <td id="logotd">
				                        <div id="picAddContainer" style="clear:both;">
					                       <font color="red">请上传1张图片,文件大小不要超过1024KB， 格式为[jpg, gif, png, bmp]</font>
				                        </div>
				                        <div id="picContainer">
				                            <div class="picDiv" style="margin:5px 10px;">
											  <c:if test="${op eq 'add' }">
						                            ${imageHtml}
											  </c:if>
											  <c:if test="${op eq 'modify' }">
							                        ${imageHtmlArr}
											  </c:if>
						                   </div>
				                        </div>
			                     </td>
			                </tr>
			                <tr id="intro">
		                        <th><span class="red"></span>食品描述:</th>
		                        <td>
		                        	<textarea name="query.description" maxlength="250" placeholder="不要超过250个字符哦(^_^)" 
		                        	class="form-control" rows="3" cols="10">${model.description}</textarea>
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
