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
	var AppFwList = ${AppFwList};
		$(function(){
			var initialtype = $("#type").val();
			var initialfwbm = '${model.code}';
	    	if(initialtype == '0'){
				var fwbmhtml = "<select name=\"query.code\" class=\"form-control\" id=\"fwcode\">";
			    	for(var i = 0 ; i < AppFwList.length; i++){
			    		if(initialfwbm == AppFwList[i].fwbm){
			    			fwbmhtml += "<option value=\""+ AppFwList[i].fwbm +"\" selected=\"selected\">"+ AppFwList[i].fwbm +"&nbsp;&nbsp;"+ AppFwList[i].fwmc +"</option>";
			    		}else{
			    			fwbmhtml += "<option value=\""+ AppFwList[i].fwbm +"\" >"+ AppFwList[i].fwbm +"&nbsp;&nbsp;"+ AppFwList[i].fwmc +"</option>";
			    		}
			    	}
	    		fwbmhtml += "</select>";
	    		$("#apptypeselect").empty();
	    		$("#apptypeselect").append(fwbmhtml);
	    	}
			$("#cancel").click(function(){
				//window.location.href="<%=request.getContextPath() %>/myportal/myportal_list.html";
				//modalClose();
				//return false;
				history.go(-1);
			});
            
            $(".close").click(function(){
                //window.location.href="<%=request.getContextPath() %>/myportal/myportal_list.html";
                //modalClose();
                //return false;
                history.go(-1);
            });
            
            var op = '${op}';
			var modelfwtbid =  '${model.tbid}';
			var modelfwtbdz =  '${model.tburl}';
			if(op!='add' && modelfwtbid == '' && modelfwtbdz != ''){
				$("#systemimage").show();
				$("#systemimageword").show();
				$("#myselfimage").hide();
				$("input[name='uploadMethod']").get(1).checked = true;
			}else{
				$("#systemimage").hide();
				$("#systemimageword").hide();
				$("#myselfimage").show();
				$("input[name='uploadMethod']").get(0).checked = true;
			}
			
			var tburl = '${model.tburl}';
			if(tburl.substring(0,9) == 'icoimages'){
				$("#systemimage").show();
				$("#systemimageword").show();
				$("#myselfimage").hide();
			}else{
				$("#systemimage").hide();
				$("#systemimageword").hide();
				$("#myselfimage").show();
			}
			$("input[name='uploadMethod']").click(function(){
				uploadmethod = $(this).val();
				if(uploadmethod == "myself"){
					$("#systemimage").hide();
					$("#systemimageword").hide();
					$("#myselfimage").show();
				}else{
					$("#systemimage").show();
					$("#systemimageword").show();
					$("#myselfimage").hide();
				}
			});
            
            
			var type = '${model.type}';
			var code = '${model.code}';
			if(type != '' && type != '0'){
				$("#apptypetr").hide();
				$("#fwdz").show();
			}
			
			$("#type").change(function(){
				var type = $(this).val();
				
				if(type == "0"){
					var fwbmhtml = "<select name=\"query.code\" class=\"form-control\" id=\"fwcode\">";
			    	for(var i = 0 ; i < AppFwList.length; i++){
		    			fwbmhtml += "<option value=\""+ AppFwList[i].fwbm +"\" >"+ AppFwList[i].fwbm +"&nbsp;&nbsp;"+ AppFwList[i].fwmc +"</option>";
			    	}
		    		fwbmhtml += "</select>";
		    		$("#apptypeselect").empty();
		    		$("#apptypeselect").append(fwbmhtml);
					$("#apptypetr").show();
					$("#fwdz").hide();
				}else{
					$("#apptypetr").hide();
					$("#fwdz").show();
				}
				
			});
			$("input[name='keyWord']").bind('input propertychange', function() {
				var keyWord = $(this).val();
				var imageName = $("input[name='imageName']").val();
				$.post("<%=request.getContextPath()%>/myportal/myportal_getImages.html",
						"keyWord="+keyWord+"&imageName="+imageName ,function(data){
                    if(data.success){
                    	console.log(data.imageHtml);
                    	$("#imageUL").empty();
                    	$("#imageUL").append(data.imageHtml);
                    }else{
                        errorAlert(data.text);
                        window.parent.scroll(0,0);
                    }
                            
            	},"json");
				return false;
			});
			
			$("#image").click(function(){
				
			});
			
			
			$(".iconclass").click(function(){
				$(this).find("input[name='icoPath']").attr("checked","checked");
			});
			
			
			
			$("#save_btn").click(function(){
				var name = $("#form_edit").find("input[name='query.name']").val();
				var fwtype = $("#type").val();
				var fwcode = $("#fwcode").val();
	
				
				if(name==''){
					errorAlert("名称为必填项");
					window.parent.scroll(0,0);
					return false;
				}
				/*if(fwtype != '0' && fwcode== ''){
					errorAlert("服务编码为必填项");
					return false;
				}*/
				var url = "<%=request.getContextPath()%>/myportal/myportal_save.html";
				if(fwtype != '0'){
					if(type == '0' || type == ''){
						url += "?code=0";
					}
					if(type != '' && type != '0'){
						url += "?code="+code;
					}
				}
				
				//var uploadmethod = $("input[name='uploadMethod']:checked").val();
				//if(uploadmethod == "fromsomewhere"){
				//	var icopath = $("input[name='icoPath']:checked").val();	
				//	if(icopath == undefined){
				//		errorAlert("请选择系统中的图标再保存!");
				//		window.parent.scroll(0,0);
				//		return false;
				//	}
				//}
				
				
				$.post(url,$("#form_edit").serialize(),function(data){
						var callback = function(){
							//reflashPage();
							//location.reload();
							top.xg_rightFrame.$('#form_list').submit();
						};
						if(data.success){
							//processDataCall(data, callback);
							//modalClose();
							window.location.href="<%=request.getContextPath() %>/myportal/myportal_list.html";
                            callback();
						}else{
							errorAlert(data.text);
							window.parent.scroll(0,0);
						}
						
				},"json");
				return false;
			});
			
			//进入页面初始化特色类别下拉列表
			initTslbs();
		});		
		
		//初始化特色类别下拉列表
		function initTslbs(){
			var url = "<%=request.getContextPath()%>/myportal/myportal_tslbList.html";
			$.get(url,function(data){
				var tslbOptionHtml = "<option value='0' selected='selected'>0</option>";
				if(parseInt(data.code)==1){
					var tslbs = data.data;
					if(!!tslbs){
						tslbOptionHtml="";
						for(var i=0;i<tslbs.length;i++){
							tslbOptionHtml += "<option value='"+tslbs[i].lbmc+"'>"+tslbs[i].lbmc+"</option>";
						}
					}
					$("#lbmc").append(tslbOptionHtml);
				}
		    },"json");
		}
	</script>
</head>

<body>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form id="form_edit">
        <input type="hidden" name="op" value="${op }"/>
        <input type="hidden" name="query.id" value="${model.id}"/>
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">我的门户服务维护</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 700px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
                
                <tbody>
                    
                    <tr>
                        <th width="30%">
                            <span class="red">*</span>服务类型
                        </th>
                        <td>
                        <s:select id="type" name="query.type" list="#{0:'APP服务',1:'WEB服务' }" value="#request.model.type" listKey="key" listValue="value" headerKey="" theme="simple" cssClass="form-control"></s:select>
                        </td>
                    </tr>
                    
                    <tr id="apptypetr">
                    	
                        <th id="codeth">
                            <span class="red">*</span>服务编码
                        </th>
                        
                        
                        <!--
                        <td id="apptypeinput">
                        	
                        	<input type="text" name="query.code" class="form-control" value="${model.code}" />
                       	</td>	
                        -->
                        
                        
                        <td id="apptypeselect">
	                        <%--<select name="query.code" class="form-control" id="fwcode">
	                        	<c:forEach var="fwbmunit" items="${fwbmList}">
	                        	 <option value="${fwbmunit.key }" <c:if test="${fwbmunit.key == model.code }"> selected="selected" </c:if>>${fwbmunit.key }&nbsp;&nbsp;${fwbmunit.text }</option>
	                        	</c:forEach>
	                        </select>
                        --%></td>	
                        
                        
                        </td>	
                        
                    </tr>
                    
                    
                    <tr id="fwdz" style="display: none;">
                        <th>
                            <span class="red"></span>服务地址
                        </th>
                        <td>
                        <input type="text" maxlength="200" placeholder="不要超过100个字符哦(^_^)"  name="query.addr" class="form-control" value="${model.addr}" />
                        
                        </td>
                    </tr>
                
                    <tr>
                    
                        <th width="30%">
                            <span class="red">*</span>名称
                        </th>
                        <td><input type="text" maxlength="20" placeholder="不要超过20个字哦(^_^)" name="query.name" class="form-control" value="${model.name }" /></td>
                    </tr>
                    
                    <tr id="tslb">
		                 <th width="30%" >
		                                                             特色类别
		                 </th>
		                  <td >
		                     <select id="lbmc" name="query.tsgn"  class="form-control">
		                      
		             		</select>
		                 </td>
		            </tr>
		            <tr id="isfx">
		                 <th width="30%" >
		                                                             是否分配到发现
		                 </th>
		                  <td >
		                     <select id="isfx" name="query.isfx"  class="form-control">
		                        <option value="0">否</option>
								<option value="1">是</option>		                        
		             		</select>
		                 </td>
		            </tr>
                    <tr>
                        <th>
                            <span class="red"></span>描述
                        </th>
                        <td>
                        <input type="text" maxlength="100" placeholder="不要超过100个字符哦(^_^)"  name="query.ms" class="form-control" value="${model.ms }" />
                        </td>
                    </tr>
                    
                    <tr>
                    <th>
                            <span class="red"></span>我的门户服务图标<br />
                            <span>
		                      	<br />
		                      	<input type="radio" value="myself" name="uploadMethod"/>自定义上传<br /><br />
		                      	<input type="radio" value="fromsomewhere" name="uploadMethod"/>系统中选择
	                        </span>
                        </th>
                        <td>
                        	<input type="hidden" name="imageName" value="${imageName }"/>
                        <div id="systemimageword">
                        	<input type="text" maxlength="20" placeholder="请输入需要查询的图标名称"  name="keyWord" class="form-control" value="${keyWord }" style="width: 100%;" />
                       	</div>
                        <div class="index_zjsy show-grid-15" style="height:400px; overflow:auto;border-style:solid; border-width:1px; border-color:#CCCCCC;margin-top: 5px;" id="systemimage">
                        
							<ul class="list-unstyled " id="imageUL">
							
							<c:forEach var="icon" items="${iconList}" varStatus="st">
                            
                            <li class="col-md-3 col-sm-3 col-xs-3 iconclass" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden;">
                            <img style="height: 50px;width: 50px;" src="<%=request.getContextPath() %>/${icon}">
								<h5>
								<input type="radio" value="${icon}" name="icoPath" <c:if test = "${model.tburl == icon}"> checked="checked"</c:if>/>
								<c:forEach var="fileName" items="${fileNameList}" varStatus="stitem">
									<c:if test="${st.index == stitem.index}">
										${fileName }
									</c:if>
								</c:forEach>
								
								</h5>
							</li>
								
                            </c:forEach>
                                         
								
							</ul>
							</div>
							<div  style="height:300px; overflow:auto;" id="myselfimage">
                      ${imageHtml }
                      </div>
                       
                        
                        <!--<input id="image" name="image" type="button" style="width:100px;" value="选择图片	">
                        -->
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
	<%--<form id="form_edit">
		<input type="hidden" name="op" value="${op }"/>
		<input type="hidden" name="query.id" value="${model.id}"/>
		  
		<table class="formlist">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">我的门户维护</font></span>
                        </th>
                    </tr>
                </thead>
        </table>
		<div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0" id="beanview">
				
				<tbody>
					
					<tr>
					
						<th>
							<span class="red">*</span>名称
						</th>
						<td><input type="text" name="query.name" class="text_nor" style="width:200px;" value="${model.name }" /></td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>描述
						</th>
						<td>
						<input type="text" name="query.ms" class="text_nor" style="width:200px;" value="${model.ms }" />
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>服务编码
						</th>
						<td>
						<input type="text" name="query.code" class="text_nor" style="width:200px;" value="${model.code}" />
						
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red">*</span>服务类型
						</th>
						<td>
						<s:select name="query.type" list="#{0:'APP服务',1:'WEB服务',2:'第三方服务' }" value="#request.model.type" listKey="key" listValue="value" headerKey="" theme="simple" cssStyle="width:100px;"></s:select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>服务地址
						</th>
						<td>
						<input type="text" name="query.addr" class="text_nor" style="width:200px;" value="${model.addr}" />
						
						</td>
					</tr>
				
					
					<tr>
					<th>
							<span class="red"></span>图标
						</th>
						<td>
						${imageHtml }
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
	</form> --%>
</body>
</html>
