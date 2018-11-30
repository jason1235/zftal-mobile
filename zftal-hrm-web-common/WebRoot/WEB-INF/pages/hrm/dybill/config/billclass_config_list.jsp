<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini"%>
		  <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		
		<script type="text/javascript">
			$(function() {
				$(".tab_szcd").hover(function(){
					var classId=$(this).attr("id");
					if(classId != '' && classId != null){
						$(".btn_list").find(".btn_xg").css("display","none");
						$(".btn_list").find(".btn_sc").css("display","none");
						$(".btn_list").css("height","50px");
					}else{
						$(".btn_list").find(".btn_xg").css("display","block");
						$(".btn_list").find(".btn_sc").css("display","block");
						$(".btn_list").css("height","100px");
					}
					var btnlisthtml=$("#btn_list_item").html();
					$(this).append(btnlisthtml);
					var btnlist=$(this).find(".btn_list");
					var billConfigId=$("#billConfigId").val();
					var classId=$(this).closest(".demo_xxxx").attr("id");
					var propertyId=$(this).closest("th").attr("id");
					btnlist.find(".btn_zy").click(function(){
						//左移
						location.href="<%=request.getContextPath() %>/bill/config_xmlBillPropertyMoveLeft.html?spBillConfig.id="
								+billConfigId+"&xmlBillClassBean.id="+classId+"&xmlBillProperty.id="+propertyId;
					});
					btnlist.find(".btn_yy").click(function(){
						//右移
						location.href="<%=request.getContextPath() %>/bill/config_xmlBillPropertyMoveRight.html?spBillConfig.id="
								+billConfigId+"&xmlBillClassBean.id="+classId+"&xmlBillProperty.id="+propertyId;
					});
					btnlist.find(".btn_xg").click(function(){
						showWindow("修改属性","<%=request.getContextPath() %>/bill/config_modifyXmlBillProperty.html?spBillConfig.id="
								+billConfigId+"&xmlBillClassBean.id="+classId+"&xmlBillProperty.id="+propertyId,620,300);
					});
					btnlist.find(".btn_sc").click(function(){
						//删除
						showConfirm("确定要删除吗？");

						$("#why_cancel").click(function(){
							alertDivClose();
						});

						$("#why_sure").click(function(){
							$.post("<%=request.getContextPath() %>/bill/config_removeXmlBillProperty.html",
									"spBillConfig.id="+billConfigId+"&xmlBillClassBean.id="+classId+"&xmlBillProperty.id="+propertyId,function(data){
								tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
								
								$("#window-sure").click(function() {
									alertDivClose();
									
									if( data.success ) {
										window.location.reload();
									}
								});			
							}, "json");
			
							return false;
						});
					});
					$(this).find(".btn_list").css("display","block");
				},function(){
					$(this).find(".btn_list").remove();
				});
				$("#back").click(function(){
					location.href="<%=request.getContextPath() %>/bill/config_list.html";
				});
				$(".btn_scbd").click(function(){
					var billConfigId=$("#billConfigId").val();
					var classId=$(this).closest(".demo_xxxx").attr("id");
					//删除
					showConfirm("确定要删除吗？");

					$("#why_cancel").click(function(){
						alertDivClose();
					});

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/bill/config_removeXmlBillClass.html","spBillConfig.id="+billConfigId+"&xmlBillClassBean.id="+classId,function(data){
							tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
							
							$("#window-sure").click(function() {
								alertDivClose();
								
								if( data.success ) {
									window.location.reload();
								}
							});			
						}, "json");
		
						return false;
					});
				});
				$("#btn_zjbd").click(function(){
					var billConfigId=$("input[name='spBillConfig.id']").val();
					showWindow("增加表单","<%=request.getContextPath() %>/bill/config_addXmlBillClass.html?spBillConfig.id="+billConfigId,380,440);
				});
				$("#btn_yybd").click(function(){
					var billConfigId=$("input[name='spBillConfig.id']").val();
					showWindow("引用表单","<%=request.getContextPath() %>/bill/config_copyXmlBillClass.html?spBillConfig.id="+billConfigId,380,400);
				});
				
				$(".btn_xgbd").click(function(){
					var billConfigId=$("input[name='spBillConfig.id']").val();
					var classId=$(this).closest(".demo_xxxx").attr("id");
					showWindow("修改表单","<%=request.getContextPath() %>/bill/config_modifyXmlBillClass.html?spBillConfig.id="
							+billConfigId+"&xmlBillClassBean.id="+classId,380,405);
				});
				$(".btn_sybd").click(function(event){
					var billConfigId=$("input[name='spBillConfig.id']").val();
					var classId=$(this).closest(".demo_xxxx").attr("id");
					location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassMoveUp.html?spBillConfig.id="+billConfigId+"&xmlBillClassBean.id="+classId;
				});
				$(".btn_xybd").click(function(){
					var billConfigId=$("input[name='spBillConfig.id']").val();
					var classId=$(this).closest(".demo_xxxx").attr("id");
					location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassMoveDown.html?spBillConfig.id="+billConfigId+"&xmlBillClassBean.id="+classId;
				});
				$(".btn_sxxz").click(function(){
					var billConfigId=$("input[name='spBillConfig.id']").val();
					var classId=$(this).closest(".demo_xxxx").attr("id");
					showWindow("选择属性","<%=request.getContextPath() %>/bill/config_choicePropertyList.html?spBillConfig.id="
							+billConfigId+"&xmlBillClassBean.id="+classId,580,450);
				});
				$(".btn_sxtb").click(function(){
                    var billConfigId=$("input[name='spBillConfig.id']").val();
                    var classId=$(this).closest(".demo_xxxx").attr("id");
                    location.href="<%=request.getContextPath() %>/bill/config_syncXmlBillProperty.html?spBillConfig.id="
                        +billConfigId+"&xmlBillClassBean.id="+classId;
                });
				$(".btn_zjsx").click(function(){
					var billConfigId=$("input[name='spBillConfig.id']").val();
					var classId=$(this).closest(".demo_xxxx").attr("id");
					showWindow("增加属性","<%=request.getContextPath() %>/bill/config_addXmlBillProperty.html?spBillConfig.id="
							+billConfigId+"&xmlBillClassBean.id="+classId,620,300);
				});

				$(".btn_zqpz").click(function(){
                    var billConfigId=$("input[name='spBillConfig.id']").val();
                    var classId=$(this).closest(".demo_xxxx").attr("id");
                    showWindow("抓取配置","<%=request.getContextPath() %>/bill/config_defineCatchConfig.html?spBillConfig.id="
                            +billConfigId+"&xmlBillClassBean.id="+classId,380,450);
                });

				var version = $("#version").val("${version}");
                $("#version").change(function(){
                    var version = $("#version").val();
                	location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassList.html?spBillConfig.id=${spBillConfig.id}"
                    	+"&version="+version;
                })
                $("#btn_revert").click(function(){
                	var version = $("#version").val();
                	showConfirm("确定要恢复到版本【"+version+"】吗？");

                    $("#why_cancel").click(function(){
                        alertDivClose();
                    });
                    $("#why_sure").click(function(){
                    	$.post("<%=request.getContextPath() %>/bill/config_revertVersion.html","spBillConfig.id=${spBillConfig.id}"
                        +"&version="+version,function(data){
                                if( data.success ) {
                                	location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassList.html?spBillConfig.id=${spBillConfig.id}";
                                }else{
                                	alert(data.text);
                                }
                        }, "json");
                    });
                	
                });
                $("#btn_publish").click(function()
                {
                	$.post('<%=request.getContextPath() %>/bill/config_check.html', 'spBillConfig.id=${spBillConfig.id}', function(data){
                		showConfirm(data.text+"确定要发布吗？");

                        $("#why_cancel").click(function(){
                            alertDivClose();
                        });
            
                        $("#why_sure").click(function(){
                            $.post('<%=request.getContextPath() %>/bill/config_publish.html', 'spBillConfig.id=${spBillConfig.id}', function(data){
                                var callback = function(){
                                    location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassList.html?spBillConfig.id=${spBillConfig.id}";
                                };
                                
                                processDataCall(data, callback);
                            }, "json");
                        });
                    }, "json");
                    
                });
                if($("#version").val()!=''){
                	$(".btn_list").remove();
                	$(".college_title").find("a").remove();
                	$("#btn_yybd").parent().remove();
                	$("#btn_zjbd").parent().remove();
                	$("#btn_publish").parent().remove();
                }else{
                    $("#btn_revert").remove();
                }
			});
		
		</script>
	</head>
	<body>
		<div id="btn_list_item">
			<div class="btn_list" style="display:none; width:50px;height:100px;">
		      <ul>
		        <li><a href="#" class="btn_zy">左移</a></li>
		        <li><a href="#" class="btn_yy">右移</a></li>
		        <li><a href="#" class="btn_xg">修改</a></li>
		        <li><a href="#" class="btn_sc">删除</a></li>
		      </ul>
		   	</div>
		</div>
		<div class="toolbox">
			<!-- 按钮 -->
			<div class="buttonbox">
				<ul>
					<li><a id="btn_zjbd" href="#" class="btn_zj">增加表单</a></li>
					<li><a id="btn_yybd" href="#" class="btn_fz">引用表单</a></li>
					<li><a id="btn_publish" href="#" class="btn_fz">发布版本</a></li>
					<li>表单版本
					   <select id="version" name="version">
					       <option value="">即时</option>
					       <c:forEach items="${versionList}" var="v">
					           <option value="${v }">${v }</option>
					       </c:forEach>
					   </select>
					   <a id="btn_revert" href="#" class="btn_sx">恢复至该版本</a>
					</li>
				</ul>
				<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返回</a>
			</div>
			<p class="toolbox_fot">
				<em></em>
			</p>
		</div>
		<div id="infoContent">
			<input id="billConfigId" type="hidden" name="spBillConfig.id" value="${spBillConfig.id }"/>
			<c:forEach items="${billClasses }" var="billClass">
			<div class="demo_xxxx" id="${billClass.id }">
				<h3 class="college_title" style="cursor: pointer;">
					<a id="btn_scbd" class="btn_scbd" href="#">删除表单</a>
					<a id="btn_xgbd" class="btn_xgbd" href="#">修改表单</a>
					<a id="btn_xybd" class="btn_xybd" href="#">下移</a>
					<a id="btn_sybd" class="btn_sybd" href="#">上移</a>
					<span class="title_name">${billClass.name }</span>
					<c:if test="${billClass.classId == '' && billClass.choice}">
                        <a id="btn_zqpz" class="btn_zqpz" style="cursor: pointer;" href="#">自定义抓取配置</a>
                    </c:if>
					<c:if test="${billClass.classId eq '' }">
						<a id="btn_zjsx" class="btn_zjsx" style="cursor: pointer;" href="#">增加属性</a>
					</c:if>
					<c:if test="${billClass.classId != ''}">
						<a id="btn_sxxz" class="btn_sxxz" style="cursor: pointer;" href="#">选择属性</a>
						<a id="btn_sxtb" class="btn_sxtb" style="cursor: pointer;" href="#">同步属性</a>
					</c:if>
				</h3>
				<c:if test="${billClass.scanStyle eq 'LIST'}">
					<div class="con_overlfow" <c:if test="${billClass.maxLength < 4}">style="height:160px;"</c:if>>
					<table summary="" class="dateline tablenowrap" align="" width="100%">
				</c:if>
				<c:if test="${billClass.scanStyle eq 'TILE'}">
					<table width="100%" class="formlist" border="0" cellspacing="0" cellpadding="0">
				</c:if>
					<c:if test="${billClass.scanStyle eq 'LIST' }">
					<thead>
						<tr>
						<c:forEach items="${billClass.commonBillPropertys }" var="billProperty">
							<th id="${billProperty.id }" style="text-align: right;" >
							<div id="${billClass.classId}" class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
								<a class="ico_sz" href="#">${billProperty.name }</a>
			                </div>
							</th>
						</c:forEach>
						</tr>
					</thead>
					<c:forEach begin="0" end="${4 }">
					<tbody>
						<tr>
						<c:forEach items="${billClass.commonBillPropertys }" var="billProperty">
							<td> ####### </td>
						</c:forEach>
						</tr>
					</tbody>
					</c:forEach>
					</c:if>
					<c:if test="${billClass.scanStyle eq 'TILE' }">
<%--					<c:forEach begin="0" end="${billClass.maxLength-1 }" varStatus="tms">--%>
					<tbody>
						<tr>
							<th colspan="${billClass.colNum * 2 }" style="text-align: left;">序号${tms.index+1 }</th>
						</tr>
						
						<c:forEach items="${billClass.imageBillPropertys }" var="billProperty">
							<tr>
								<th id="${billProperty.id }" style="text-align: right;">
									<div id="${billClass.classId}" class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
										<a class="ico_sz" href="#">${billProperty.name }</a>
				                	</div>
								</th>
								<td colspan="${billClass.colNum * 2 -1}" >
									<img src="<%=request.getContextPath() %>/file/file_image.html" style="width:${billProperty.width}px;height:${billProperty.height}px;"/>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(billClass.commonBillPropertys)==0}">
							<c:forEach items="${billClass.photoBillPropertys }" var="billProperty">
								<tr>
									<th id="${billProperty.id }" style="text-align: right;">
										<div id="${billClass.classId}" class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
											<a class="ico_sz" href="#">${billProperty.name }</a>
					                	</div>
									</th>
									<td colspan="${billClass.colNum * 2 -1}" >
										<img src="<%=request.getContextPath() %>/file/file_photo.html" style="width:${billProperty.width}px;height:${billProperty.height}px;"/>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:set var="photoRow" value="5"/>
					    <c:set var="usePhoto" value="0"/>
					    <c:if test="${fn:length(billClass.photoBillPropertys) >0}">
					        <c:set var="usePhoto" value="1"/>
					    </c:if>
					    <c:set var="comRow" value="0"/>
						<c:set var="cIndex" value="0"/>
						<c:forEach items="${billClass.commonBillPropertys }" step="${billClass.colNum - usePhoto }">
						<c:set var="rIndex" value="0"/>
						<!--按colNum换行-->
						<c:if test="${fn:length(billClass.commonBillPropertys)>cIndex}">
						<tr>
							<c:forEach items="${billClass.commonBillPropertys }" begin="${cIndex }" end="${cIndex+billClass.colNum-1 - usePhoto}"
								 var="billProperty">
							<th id="${billProperty.id }" style="text-align: right;">
								<div id="${billClass.classId}" class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
									<a class="ico_sz" href="#">${billProperty.name }</a>
			                	</div>
							</th>
							<td>
								#######
							</td>
							<c:set var="rIndex" value="${rIndex+1 }"/>
							</c:forEach>
							<c:if test="${cIndex==0}">
							     <c:forEach items="${billClass.photoBillPropertys }" var="billProperty">
                                    <th id="${billProperty.id }" style="text-align: right;"  rowspan="${photoRow }">
                                        <div id="${billClass.classId}" class="tab_szcd" style="float: none; display: block; position: relative;" name="col">
                                            <a class="ico_sz" href="#">${billProperty.name }</a>
                                        </div>
                                    </th>
                                    <td rowspan="${photoRow }" >
                                        <img src="<%=request.getContextPath() %>/file/file_photo.html" style="width:${billProperty.width}px;height:${billProperty.height}px;"/>
                                    </td>
                            </c:forEach>
			                </c:if>
							<!--填充空行-->
							<c:if test="${rIndex < billClass.colNum - usePhoto}">
								<c:forEach begin="${rIndex+1 }" end="${billClass.colNum }" >
								<th> </th> <td> </td>
								</c:forEach>
							</c:if>
						</tr>
						</c:if>
						<c:set var="cIndex" value="${cIndex+billClass.colNum - usePhoto}"/>
						<c:set var="comRow" value="${comRow+1}"/>
			            <c:if test="${comRow>=photoRow}">
			                 <c:set var="usePhoto" value="0"/>
			            </c:if>
						</c:forEach>
					</tbody>
<%--					</c:forEach>--%>
					</c:if>
				</table>
				<c:if test="${billClass.scanStyle eq 'LIST'}">
				</div>
				</c:if>
			</div>
			</c:forEach>
		</div>
	</body>
</html>
