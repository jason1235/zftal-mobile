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
		
		<!--<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.2&key=4930900b24b94bcebfdc1e20693c5317"></script>
		--><style type="text/css">
		 .td-control {
		        width:7%;
		        height: 34px;
			    font-size: 14px;
			    line-height: 1.42857;
			    color: rgb(85, 85, 85);
			    background-color: rgb(255, 255, 255);
			    background-image: none;
			    box-shadow: rgba(0, 0, 0, 0.0745098) 0px 1px 1px inset;
			    padding: 6px 6px;
			    border-width: 1px;
			    border-style: solid;
			    border-color: rgb(204, 204, 204);
			    border-image: initial;
			    border-radius: 4px;
			    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
		  }
		
			
			.formlist td,.formlist th {
				padding-left:10px;
			}
			.formlist th {
				width:20%;
			}
			
			.form-control {
				color:#000;
			}
			#container{
				width:80%;
				height:300px;
				
			}
			.search_tip{
				position:relative;
			}
			#tip {
	            background-color: #ddf;
	            color: #333;
	            border: 1px solid silver;
	            box-shadow: 3px 4px 3px 0px silver;
	            position: absolute;
	            top: 10px;
	            left: 10px;
	            border-radius: 5px;
	            overflow: hidden;
	            line-height: 20px;
	        }
	        #tip input[type="text"] {
	            height: 25px;
	            border: 0;
	            padding-left: 5px;
	            width: 280px;
	            border-radius: 3px;
	            outline: none;
	        }
		</style>
		
  </head>
  
  <body>
    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <input type="hidden" name="op" value="${op }"/>
        <input type="hidden" name="query.canteenId" value="${model.canteenId}"/>
        
        <div class="sl_mod modal-content" style="border:none">
            <div class="modal-header">
                <button type="button" class="close" onclick="history.go(-1)">
	                <span aria-hidden="true" class="glyphicon glyphicon-share-alt"></span>
	                <span class="sr-only">Close</span>
                </button>              
                <h4 class="modal-title text-left" id="myModalLabel">商家管理</h4>      
            </div>
            <div class="modal-body">
            	<jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
            	<div class="tab" style="clear: both;">
            		<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            			<tbody>
		                   <tr id="titleTr">
		                        <th><span class="red">*</span>商家名称:</th>
		                        <td>
		                         <input id="titleInput" type="text" name="query.canteenName" maxlength="100" placeholder="不要超过100个字符哦(^_^)" 
		                         	class="form-control" value="${model.canteenName}" />
		                        </td>
		                    </tr>
		                    <tr>
		                        <th><span class="red">*</span>商家电话:</th>
		                        <td>
		                          <input type="text" id="telephone"  name="query.telephone" maxlength="20" placeholder="手机号码或固定电话" 
		                          	class="form-control" value="${model.telephone}" />
		                        </td>
		                    </tr>
		                     
							<tr>
		                        <th><span class="red">*</span>状&nbsp;态: </th>
		                        <td>
		                            <select name="query.isactive"  value="${model.isactive}" class="form-control">
		                               <option value="1" <c:if test = "${model.isactive eq 1}"> selected="selected"</c:if>>启用</option>
			                           <option value="0" <c:if test = "${model.isactive eq 0}"> selected="selected"</c:if>>禁用</option>
		                            </select>
		                        </td>
		                    </tr>
    
							<tr id="detaillogo" class="detaillogo"> 
			                    <th style="width:90px;"><span class="red">*</span>商家图片：</th>
			                    <td id="logotd">
				                        <div id="picAddContainer" style="clear:both;">
					                       <font color="red">请上传1张图片,文件大小不要超过1024KB， 格式为[jpg, gif, png, bmp]</font>
				                        </div>
				                        <div id="picContainer">
										  <c:if test="${op eq 'add' }">
				                            <div class="picDiv" style="margin:5px 10px;">
					                            ${imageHtml}
					                         </div>
										  </c:if>
										  <c:if test="${op eq 'modify' }">
										   <div class="picDiv" style="margin:5px 10px;">
						                        ${imageHtmlArr}
						                   </div>
										  </c:if>
				                        </div>
			                     </td>
			                </tr>
		                    <tr id="intro">
		                        <th>商家描述:</th>
		                        <td>
		                        	<textarea name="query.description" maxlength="250" placeholder="不要超过250个字符哦(^_^)" 
		                        	class="form-control" rows="3" cols="10">${model.description}</textarea>
		                        </td>
		                    </tr> 
		                    <tr>
		                    <th >距离测量地址:</th><td><a href="http://lbs.amap.com/console/show/picker" target="_blank">http://lbs.amap.com/console/show/picker</a></td>
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
        <script type="text/javascript">
        	/*
			var map = new AMap.Map('container', {
	            resizeEnable: true,
	            zoom: 13,//地图显示的缩放级别
	            keyboardEnable: false
		    });
		    */
			var regexp=/^[0-9]+(.[0-9]{0,2})?$/;
			var regexpM=/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
			var reg = /.*\?fileGuid=(.*)/;
			var regexlo=/^[0-9]+(.[0-9]{0,6})?$/;
			var regexla=/^[0-9]+(.[0-9]{0,6})?$/;
			var discount="";
			
			$(function(){
				/*
				 AMap.plugin(['AMap.Autocomplete','AMap.PlaceSearch'],function(){
				      var autoOptions = {
				        city: "北京", //城市，默认全国
				        input: "keyword"//使用联想输入的input的id
				      };
				      autocomplete= new AMap.Autocomplete(autoOptions);
				      var placeSearch = new AMap.PlaceSearch({
				            city:'北京',
				            map:map
				      })
				      AMap.event.addListener(autocomplete, "select", function(e){
				         //TODO 针对选中的poi实现自己的功能
				         placeSearch.setCity(e.poi.adcode);
				         placeSearch.search(e.poi.name)
				      });
			    });
				*/

				$("input").focus(function(){
					$(this).val("");
				});
				
				
				$("#cancel").click(function(){
					history.go(-1);
				});
				

				$("#save_btn").click(function(){
					var canteenName = $("#titleInput");
					if(canteenName == null || canteenName.val()==''){
						errorAlert("请填写商家名称");
						window.parent.scroll(0,0);
						return false;
					} else {
					   canteenName = canteenName.val();
					}
					
					var telephone = $("#telephone").val();
					if(telephone==null||telephone==""){
					    errorAlert("请输入正确联系电话");
						window.parent.scroll(0,0);
						return false;
					}else if(!regexpM.test(telephone)){			
						errorAlert(regexpM.test(telephone)+"请输入正确手机号码");
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
								errorAlert("请上传商家图片");
								window.parent.scroll(0,0);
								return false;
							}
						}
					}
					$.post("<%=request.getContextPath()%>/canteenfood/canteen_saveOrUpdate.html",
						$("#form_edit").serialize(),function(data){
							if(data.success){
								window.parent.scroll(0,0);
								openAlert(data.text, "s","auto", function(){
								  location.href = _path + "/canteenfood/canteen_list.html";
							    });
							}else{
								errorAlert(data.text);
								window.parent.scroll(0,0);
							}
					 },"json");
					return false;
				});
				
				$('#back').click(function(){
					location.href = _path + "/canteenfood/canteen_list.html";
				});
			});	
			
			function removeClk(obj){
			 len=$("#infos").children().length;
			  len--;
			  $(obj).parent().children("input").val("");
			  $(obj).parent().remove();
			}
		</script>
    </form>
  </body>
</html>
