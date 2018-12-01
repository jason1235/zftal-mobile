<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<%@include file="/commons/hrm/head.ini"%>

		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/date.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/lockTableTitle.js"></script>
		
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />

		<script type="text/javascript">
			$(function(){			
				//监听单击行
				$("tbody > tr[name='list_tr']").click(function(){			
					if($(this).is(".current")){
						$(this).removeAttr("class");
					}else{
						$(this).attr("class", "current");
					}
					//控制checkbox的选中状态
					var trcheckbox=$(this).find(":checkbox").get(0);
					trcheckbox.checked=!trcheckbox.checked;
				});
				//改变checkbox的事件
		        $("input[name='ids']").click(function(e){
		        	e.stopPropagation();
		            if($(this).is(":checked")){
		            	$(this).closest("tr").attr("class","current");
		            }else{
		            	$(this).closest("tr").removeAttr("class");
		            }
		        });
		        	
				$("#list_body tr").dblclick(function(){
					$("input[name='ids']").each(function(){
						this.checked=false;
					});
					$("tbody > tr[name='list_tr']").each(function(){
						$(this).removeAttr("class");
					});
					$(this).click();
					
					var id = $(this).attr("id");
					if(id==null){
                        id="";
                    }
					var gh = $(this).attr("gh");
					var editable=$("#editable").val();
					showWindow("修改","<%=request.getContextPath()%>/normal/casualStaffBatch_modify.html?classId="+"${classId}&instanceId="+id+"&gh="+gh+"&editable="+editable, 750, '${230+heightOffset}');
				});
				
				$("#btn_zj").click(function(){
					showWindow("增加","<%=request.getContextPath()%>/normal/casualStaffBatch_add.html?classId="+'${classId}', 750,'${230+heightOffset}');
				});
				$("#btn_xg").click(function(){
					var checked=$("input[id='ids']:checked");
					if(checked.length==0){
						alert("请选择行");
						return false;
					}else if(checked.length>1){
                        alert("只能对单行进行操作");
                        return false;
                    }
					var current = $("#list_body tr.current");
					var id = current.attr("id");
					if(id==null){
						id="";
					}
					var gh = current.attr("gh");
					var editable=$("#editable").val();
					showWindow("修改","<%=request.getContextPath()%>/normal/casualStaffBatch_modify.html?classId=${classId}&instanceId="+id+"&gh="+gh+"&editable="+editable, 750, '${230+heightOffset}');
				});
				$("#btn_plxg").click(function(){
                    var checked=$("input[id='ids']:checked");
                    if(checked.length==0){
                        alert("请选择行");
                        return false;
                    }
                    var ids = checked.serialize();
                    showWindow("批量修改","<%=request.getContextPath()%>/normal/casualStaffBatch_batchEditView.html?classId=${classId}&"+checked.serialize(), 500, 200);
                });
				$("#btn_fpgh").click(function(){
                    var checked=$("input[id='ids']:checked");
					if(checked.length==0){
						alert("请选择行");
						return false;
					}else if(checked.length>1){
                        alert("只能对单行进行操作");
                        return false;
                    }
					var current = $("#list_body tr.current");
					var id = current.attr("id");
					if(id==null){
						id="";
					}
					var gh = current.attr("gh");
					var editable=$("#editable").val();
					showWindow("分配工号","<%=request.getContextPath()%>/normal/casualStaffBatch_fpghInit.html?classId=${classId}&instanceId="+id+"&gh="+gh+"&editable="+editable, 300, 150);
				});
				$("#btn_sc").click(function(){
					var checked=$("input[id='ids']:checked");
                    if(checked.length==0){
                        alert("请选择行");
                        return false;
                    }else if(checked.length>1){
                        alert("只能对单行进行操作");
                        return false;
                    }
                    var current = $("#list_body tr.current");
					if(current==null){
						alert("请选择行");
						return false;
					}
					var id = current.attr("id");
					if(id==null || id==''){
						alert("该行已经无数据，不可删除");
						return false;
					}
					var gh = current.attr("gh");
					//删除
					showConfirm("确定要删除吗？");

					$("#why_cancel").click(function(){
						alertDivClose();
					});

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/normal/casualStaffBatch_remove.html?classId=${classId}&instanceId="+id+"&gh="+gh, null, function(data){
							var callback = function(){
								reflashPage();
							};
							
							processDataCall(data, callback);
						}, "json");
					});
				});
				$("#selectBtn").click(function(){
					reflashPage();
				});

			  $(".btn_dc").click(function(e){
		            var str = $("#selectForm").serialize();
		            window.open("<%=request.getContextPath()%>/normal/casualStaffBatch_export.html?classId=${classId}&"+str);
		      });
		      
		      $(".btn_dr").click(function(e){
		      		showWindow("数据导入", "<%=request.getContextPath()%>/baseinfo/infoClassData_uploadNoCheckGh.html?classId=${classId}", 480, 150 );		            
		      }); 			      
			      
		    var xmtrs=$("tr[name=list_tr]");
			for ( var i=0; i<xmtrs.length; i++){
				var xmtr = xmtrs[i];
				var tds = $(xmtr).closest("tr").find("td");
				var xmtd = tds[1];
			    var id = $(xmtr).closest("tr").attr("id");
			    var gh = $(xmtr).closest("tr").attr("gh");
			   
			    //var html1='<%=request.getContextPath()%>'+"/normal/casualStaffBatch_modify.html?instanceId="+id+"&gh="+gh+"&classId=${classId}";
			    
			    var html="<a style=\"color:#074695\" href=\"#\" onclick=\"win('"+id+"','"+gh+"')\" >"+$(xmtd).html()+ "</a>";
			    $(xmtd).html(html);
			}
			
			if("${showMore}"=='1'){
				$("div.more--item_bottom a").removeClass().addClass("up");
	   			$("div.more--item_bottom a").text("收起条件");
	   			$("#total").css("display","block");
	   			$("#showMore").val("1");
				$("div.more--item_bottom a").toggle(
					hideMore,showMore
			   	);
			}else{
				$("div.more--item_bottom a").toggle(
					showMore,hideMore
			   	);
			}
						           
            $("#closeInfoWindow").click(function(e){
            	reflashPage();
            });
            
            fillRows("20", "", "", false);//填充空行
            
			//FixTable("MyTable", 0, 790, null);
		});
		
		function showMore(){
   			$(this).removeClass().addClass("up");
   			$(this).text("收起条件");
   			$("#total").slideDown();
   			$("#showMore").val("1");
   		}
	   	function hideMore(){
   			$(this).removeClass().addClass("down");
   			$(this).text("展开条件");
   			$("#total").slideUp();
   			$("#showMore").val("-1");
   		}
			
		function reflashPage(){
			var valid=true;
			$("#selectForm input[fieldType='NUMBER']").each(function(){
				var val=$(this).val();
				if(val!=''&&!validateDigital(val)){
					var obj=$(this);
					alert(obj.parent().prev().text()+"，请输入有效的整数或小数！");
					obj.focus();
					valid=false;
					return false;
				}
			});
			if(!valid){
				return false;
			}
			//$("#selectForm").attr("action","<%=request.getContextPath()%>/normal/casualStaffBatch_list.html?classId="+"${classId}&modeType=right");
			$("#selectForm").submit();
		}
		
		function win(id,gh){
			var editable=$("#editable").val();
			showWindow("修改","<%=request.getContextPath()%>/normal/casualStaffBatch_modify.html?classId="+"${classId}&instanceId="+id+"&gh="+gh+"&editable="+editable, 750, '${230+heightOffset}');
		}

        function selectAllOrCancel(obj,name){//全选选择框操作
			var checks = document.getElementsByName(name);
			var body = document.getElementById("list_body");
			var tr = body.getElementsByTagName("tr");
			if(obj.checked){
				for ( var i = 0; i < checks.length; i++) {
					tr[i].className='current';
					checks[i].checked = true;
				}
			}else{
				for ( var i = 0; i < checks.length; i++) {
					tr[i].className='';
					checks[i].checked = false;
				}
			}
        }
           
		function refreshButton(){
			var checked=$("input[id='ids']:checked");
			$("div.buttonbox li").hide();
               $("#btn_zj").parent().show();
			if(checked.length==0){
				$("div.buttonbox li").show();
            }else if(checked.length==1){
            	$("#btn_plxg").parent().show();
            	$("#btn_xg").parent().show();
                   $("#btn_sc").parent().show();
               }else
               {
               	$("#btn_plxg").parent().show();
	        }
			$("#btn_dc").parent().show();
               <%--
               if(status == "NO_PASS_AUDITING"){
                   $("#btn_sx").parent().show();
               }--%>
           }
		function validateDigital(val){
			var regexp=/^((\-?(0|([1-9][0-9]*))\.[0-9]+)|(\-?[1-9][0-9]*)|0)$/;
			var flag=regexp.test(val);
			return flag;
		}
	   /*
        *排序回调函数
        */
        function callBackForSort(sortFieldName,asc){
            $("#sortFieldName").val(sortFieldName);
            $("#asc").val(asc);
            $("#selectForm").submit();
        }
        
        function requestProgress(){
			$("#windown-content").unbind("ajaxStart");
			$.ajax({type:"post",
				url:"<%=request.getContextPath()%>/baseinfo/infoClassData_process.html",
				success:function(data){
					if(data.success){
						$("#scroll").append(data.result.description);
						//var li = $("#scroll").find("li:last");
						$("#scroll").parent().scrollTop($("#scroll").height());
						if(data.progress != null){
							$("#progressTitle").text(data.progress.msg);
							$("#progressInfo").text(data.progress.percent);
							$("#progressbar").css("width",data.progress.percent);
						} 
						if(data.result.finish){//操作结束，出现关闭按钮
							$("#viewInfoWindow").fadeOut("normal",function(){
								$("#closeInfoWindow").fadeIn("normal");
							});
						}else{
							setTimeout("requestProgress()",200);//请求间隔200ms
						}
					}else{
						$("#scroll").append("<li><font color='red'>请求失败</font></li>");
					}						
				},
				datatType:"json",
				global:false
			});
		}
		
		function showProgress(){
			$("#scroll").empty();
			$("#progressTitle").empty();
			$("#progressInfo").empty();
			$("#viewInfoWindow").show();
			$("#closeInfoWindow").hide();
			$("#progressbar").css("width","0px");
			var left = ($(document).width()-600)/2;
			$("#tips").css({"left":left+"px","top":40+"px"});
			$("#tips").fadeIn("slow");
		}		        
		</script>
	</head>

	<body>
		<div class="formbox">
			<div class="toolbox">
				<div class="buttonbox">
					<ul>
							<c:if test="${fn:contains(permission,'_zj_')}">
								<li>
									<a id="btn_zj" class="btn_zj">增 加</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(permission,'_xg_')}">
								<li>
		                            <a id="btn_plxg" class="btn_xg">批量修改</a>
		                        </li>
								<li>
									<a id="btn_xg" class="btn_xg">修 改</a>
									<input type="hidden" value="couldModify" id="editable"/>
								</li>
								<li>
									<a id="btn_fpgh" class="btn_xg">分配工号</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(permission,'_sc_')}">
								<li>
									<a id="btn_sc" class="btn_sc">删除</a>
								</li>
							</c:if>
							<li><a id="btn_dc" class="btn_dc">导 出</a></li>
							<c:if test="${fn:contains(permission,'_zj_')}">
								<li>
									<a id="btn_dr" class="btn_dr">导 入</a>
								</li>
							</c:if>								
					</ul>
				</div>
			</div>
			<form id="selectForm" enctype="application/x-www-form-urlencoded" method="post" >
				 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
	             <input type="hidden" id="asc" name="asc" value="${asc}"/>
			     <input type="hidden" name='bol' value="true" />
			     <input type="hidden" id="showMore" name='showMore' value="${showMore }" />
				<div class="searchtab">
					<div class="comp_con">	
						<div class="search_advanced" id="total" style="display: none;">
						<table width="100%" border="0"> 
							<tbody>
								<c:forEach items="${conditions }" step="2" varStatus="vs">
									<tr>
										<c:forEach begin="${vs.index}" end="${vs.index+1}" var="idx">
											<c:if test="${fn:length(conditions)>idx }">
												<c:set var="item" value="${conditions[idx] }"/>
												<th>${item.name }</th>
												<td>
													<c:choose>
														<c:when test="${item.fieldType=='CODE'}">
															<ct:codePicker name="${item.fieldName }" catalog="${item.codeId }" code="${valuesMap[item.fieldName] }"/>
														</c:when>
														<c:when test="${item.fieldType=='DATE' ||item.fieldType=='MONTH' ||item.fieldType=='YEAR' }">
															<input type="text" name="${item.fieldName }" style="width: 110px;"
																class="Wdate" onfocus="WdatePicker({dateFmt:'${item.typeInfo.format}'})"
																value="${valuesMap[item.fieldName][0] }"/>&nbsp;至
															<input type="text" name="${item.fieldName }" style="width: 110px;"
																class="Wdate" onfocus="WdatePicker({dateFmt:'${item.typeInfo.format}'})"
																value="${valuesMap[item.fieldName][1] }"/>
														</c:when>
														<c:when test="${item.fieldType=='SIGLE_SEL'}">
															<select name="${item.fieldName }" style="width: 110px;">
																<option value=""></option>
																<option value="1" ${valuesMap[item.fieldName]=="1"?"selected=\"selected\"":""}>是</option>
																<option value="0" ${valuesMap[item.fieldName]=="0"?"selected=\"selected\"":""}>否</option>
															</select>
														</c:when>
														<c:otherwise>
															<input type="text" fieldType="${item.fieldType }" name="${item.fieldName }" style="width: 181px;" value="${valuesMap[item.fieldName] }"/>
														</c:otherwise>
													</c:choose>
												</td>
											</c:if>
										</c:forEach>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
						<!-- 伸缩按钮 -->
						<div class="more--item_bottom" style="clear:both;margin-bottom:5px"><p><a href="#" class="down">展开条件</a></p></div>	
					</div>
					<table width="100%" border="0">
						<tfoot>
				          <tr>
				            <td colspan="8">
				              <div class="btn">
				                <button class="btn_cx" type="button" id="selectBtn">查 询</button>
				              </div>
				           </td>
				          </tr>
				       </tfoot>
					</table>
				</div>
				
			 <h3 class="datetitle_01">
				 	<span>${query.clazz.name }<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以维护信息）</font></span>
			 </h3>
			<div class="con_overlfow">
				<table summary="" class="dateline tablenowrap" align="" width="100%"  id="MyTable">
					<thead id="list_head">
						<tr>
						    <td width="4%">
                                <input type="checkbox" onclick="selectAllOrCancel(this,'ids');" id="allCheckBoxDel"/>
                            </td>
							<c:forEach items="${viewables}" var="p">
								<c:if test="${p.classFiledName eq sortFieldName}">
				                    <td class="sort_title_current_${asc }" id="${p.classFiledName }">${p.name }
				                    </td>
				                </c:if>
				                <c:if test="${p.classFiledName != sortFieldName}">
				                    <td class="sort_title" id="${p.classFiledName }">${p.name }
				                    </td>
				                </c:if>
							</c:forEach>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${staffInfoDtos}" var="staffInfoDto">
							<tr name="list_tr" id="${staffInfoDto.dynaBean.values['globalid']}" gh="${staffInfoDto.overall.values['gh']}">
		                        <td><input type="checkbox" id='ids' name="ids" value="${staffInfoDto.dynaBean.values['globalid']}"/></td>
								<c:forEach items="${viewables}" var="infoProperty">
									<td>${staffInfoDto.valueMap[infoProperty.classFiledName]}</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div id="tips" style="position:absolute;z-index:2000;display:none;background: none repeat scroll 0 0 #ffffff">
	<div class="readme" style="margin:0px;" style="width:640px">
	  <h2>操作信息</h2>
	  <div style="height:370px;width:640px;overflow-x:hidden;overflow-y:auto;">
	  <ul id="scroll">
	  </ul>
	  </div>
	  <table style="width:640px;">
		  <tr style="background:#E8F0FB;">
		  <td width="70px">
		  <div style="float:left;margin:5px">
			<font id="progressTitle"></font>
		  </div>
		  </td>
		  <td width="400px">
		  <div style="border: 1px solid #AAAAAA;background:#ffffff;height:20px">
		  	<div id="progressbar" class="progressbar" style="width:0px"></div>
		  </div>
		  </td>
		  <td width="70px">
		  <div style="float:left;margin:5px">
			<font id="progressInfo"></font>
		  </div>
		  </td>
		  <td>
		  <div style="float:right;margin:5px">
			<button id="closeInfoWindow" name="close">关     闭</button>
			<button id="viewInfoWindow" >处理中</button>
		  </div>
		  </td>
		  </tr>
	  </table>
	</div>
	</div>
			
			<ct:page pageList="${staffInfoDtoPageList }" />
		</form>
		</div>
		<!-- button class="btn_dhk"  >对话框</button-->
	</body>
</html>