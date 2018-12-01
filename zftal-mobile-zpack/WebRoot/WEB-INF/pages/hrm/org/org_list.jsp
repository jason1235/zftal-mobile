<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript">
		
		$(function(){
			
			$("#dryjbm").click(function(e) {
                showWindow("一级部门导入", "<%=request.getContextPath()%>/baseinfo/dept_upload.html", 480, 150 );
            });
            
			$(".college_title .up").live('click',function(){
				conId = "con_"+$(this)[0].id.split('_')[1];
				$(".demo_college #"+conId).hide();
				$(this).attr("class","down");
				$(this).text("展开");
				return false;
			})
			
			$(".college_title .down").live('click',function(){
				conId = "con_"+$(this)[0].id.split('_')[1];
				$(".demo_college #"+conId).show();
				$(this).attr("class","up");
				$(this).text("收起");
			})

			$(".demo_college li .college_li").live('mouseenter',function(){
				$(this).next().show();
				$(this).parent().css("position","relative");
			})
			
			$(".demo_college li .college_li").live('mouseleave',function(){
				$(this).next().hide();
				$(this).parent().css("position","");
			})

			$(".college_title .title_name").live('mouseenter',function(){
				$(this).next().show();
				$(this).parent().css("position","relative");
			})
			
			$(".college_title .title_name").live('mouseleave',function(){
				$(this).next().hide();
				$(this).parent().css("position","");
			})
			
			$(".explain_left01,.explain_right01").live('mouseenter',function(){
				$(this).show();
				$(this).parent().css("position","relative");
			})
			
			$(".explain_left01,.explain_right01").live('mouseleave',function(){
				$(this).hide();
				$(this).parent().css("position","");
			})
			
			$("#searchText").focusin(function(){
				var textVal = $(this).val();
				if(textVal == '' || textVal == '请输入关键字'){
					$(this).val('');
				}
			});
			$("#searchText").focusout(function(){
				var textVal = $(this).val();
				if($(this).val() == '' || $(this).val() == '请输入关键字'){
					$(this).val('请输入关键字');
				}
			});
			$("#searchText").keypress(function(e){
				if(e.keyCode == '13'){
					searchValidate();
				}
			});
			$("#searchText").keydown(function(e){
				
			});
			$("#searchText").keyup(function(e){
				
			});

			$("a[name='sc']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_span_' + id).next().hide();
				remove(id);
			})

			$("a[name='xg']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_span_' + id).next().hide();
				modify(id);
			})

			$("a[name='fq']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_span_' + id).next().hide();
				disuse(id);
			})

			$("a[name='qy']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_span_' + id).next().hide();
				use(id);
			})
			
			$("a[name='sc1']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_li_' + id).next().hide();
				remove(id);
			})

			$("a[name='xg1']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_li_' + id).next().hide();
				modify(id);
			})

			$("a[name='fq1']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_li_' + id).next().hide();
				disuse(id);
			})

			$("a[name='qy1']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_li_' + id).next().hide();
				use(id);
			})

			$("a[name='fqlb']").click(function(){
				$("input[name='query.disused']").val('true');
				searchDisused();
	//			searchValidate();
			})

			$("a[name='dqlb']").click(function(){
				$("input[name='query.disused']").val('false');
				searchValidate();
			})

			$("button[name='searchBtn']").click(function(){
				searchValidate();
			})

			$("a[name='rybd']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_span_' + id).next().hide();
				peopleMove(id);
			})

			$("a[name='rybd1']").click(function(){
				var id = $(this).closest("tr").attr("id");
				$('#college_li_' + id).next().hide();
				peopleMove(id);
			})
			
		});

		function add(parentId){
			showWindow("新增部门","<%=request.getContextPath()%>/org/org_edit.html?addIf=true&query.type=${query.type}&info.type=${query.type}&info.parent.oid=" + parentId, 640, 300);
		}

		function modify(id){
			showWindow("修改部门","<%=request.getContextPath()%>/org/org_edit.html?addIf=false&query.type=${query.type}&info.oid="+id, 640, 300);
		}
		
		function orgztree(type){
			showWindow("组织机构树","<%=request.getContextPath()%>/org/org_treePage.html?query.type="+type, 350, 450);
		}
		
		function orgztreeExport(type){
			window.open("<%=request.getContextPath()%>/org/org_orgztreeExport.html?query.type="+type);
		}

		function disuse(id){
			showConfirm("确定要废弃吗？");
			
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				disuseEntity(id);
			});
		}

		function disuseEntity(id){
			$.post('<%=request.getContextPath()%>/org/org_disuse.html',{oid:id},function(data){
				var callback = function(){
					searchValidate();
					//$("#li_"+id).remove();
				};
				processDataCall(data, callback);
			},"json");
		}

		function use(id){
			showConfirm("确定要启用吗？");
			
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				useEntity(id);
			});
		}

		function useEntity(id){
			$.post('<%=request.getContextPath()%>/org/org_use.html',{oid:id},function(data){
				var callback = function(){
					searchValidate();
					//$("#li_"+id).remove();
				};
				processDataCall(data, callback);
			},"json");
		}

		function remove(id){
			showConfirm("确定要删除吗？");
			
			$("#why_cancel").click(function(){
				divClose();
			});
	
			$("#why_sure").click(function(){
				delEntity(id);
			});
		}

		function delEntity(id){
			$.post('<%=request.getContextPath()%>/org/org_remove.html',{oid:id},function(data){
				var callback = function(){
					searchValidate();
				};
				processDataCall(data, callback);
			},"json");
		}

		function peopleMove(id){
			showWindow("人员部门变动","<%=request.getContextPath()%>/org/orgPeople_peopleList.html?oid="+id, 640, 330);
		}

	
		//搜索框内容验证
		function searchValidate(){
			if($("#searchText").val() == '请输入关键字'){
				$("#searchText").val(''); 
			}
			//alert($("input[name='supperOid']").val());
			$("#searchForm").submit();
		}
		
		function searchDisused(){
			$("#searchForm").attr("action","<%=request.getContextPath()%>/org/org_disusedlist.html");
			$("#searchForm").submit();
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
        
        function requestProgress(){
            $("#windown-content").unbind("ajaxStart");
            $.post('<%=request.getContextPath()%>/baseinfo/infoClassData_process.html',"",function(data){
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
            },"json");
        }
	</script>
  </head>
  <body>
  	<form action="<%=request.getContextPath()%>/org/org_list.html" name="searchForm" id="searchForm" method="post">
		<div class="searchbox_rs"> 
			<div class="search_rs" id="search_rs">
				<input id="searchText" style="width:340px;" class="text_nor text_nor_rs" name="query.commonProperty" type="text" <c:if test="${query.commonProperty == null || query.commonProperty == ''}"> value="请输入关键字" </c:if><c:if test="${query.commonProperty != null && query.commonProperty != ''}"> value="${query.commonProperty}" </c:if> />
			    <input type="hidden" name="query.type" value="${query.type }" />
			    <input type="hidden" name="query.disused" value="${query.disused }" />
			    <input type="hidden" name="supperOid" />
			    <button type="button" name="searchBtn">查询</button>
			  	<h1>说明： 可以按照部门名称、部门代码进行模糊搜索</h1>
			    <a class="ico_close03" href="#" title="关闭搜索" onmouseover="this.className='ico_close02'" onmouseout="this.className='ico_close03'" onclick="javascript:document.getElementById('search_rs').style.display='none';document.getElementById('tool_rs').style.display='none';javascript:document.getElementById('search_rs_btn').style.display='block'; return false;" ></a>
			</div>
			<div class="search_rs_btn" style="display:none;" title="打开搜索" id="search_rs_btn" onclick="javascript:document.getElementById('search_rs').style.display='block';document.getElementById('tool_rs').style.display='block';javascript:document.getElementById('search_rs_btn').style.display='none'">搜索</div>
		</div>
		
	</form>
    <div class="toolbox" id="tool_rs">
		<div class="buttonbox">
			<c:if test="${query.disused}">
				<a name="dqlb" class="btn_fh_rs" style="cursor: pointer; position:absolute; top:4px; right:5px; ">返回</a>
			</c:if>
			<c:if test="${!query.disused}">
				<c:if test="${fn:length(infoList) > 0}">
					<a name="fqlb" class="btn_hsz" style="cursor: pointer; position:absolute; top:4px; right:5px; ">废弃部门查询</a>
				</c:if>
				<ul>
					<c:if test="${flag}">
						<li><a name="tjyjbm" class="btn_zj" style="cursor: pointer;" onclick="add('');return false;">添加一级部门</a></li>
					</c:if>
				    <li><a name="ckzzjgs" class="btn_cx" style="cursor: pointer;" onclick="orgztree('${query.type}');return false;">本类别组织机构树</a></li>
                    <li><a name="ckzzjgs" class="btn_cx" style="cursor: pointer;" onclick="orgztree('');return false;">全部组织机构树</a></li>
                    <li><a class="btn_dc" style="cursor: pointer;" onclick="orgztreeExport('${query.type}');return false;">导出本类别组织机构树</a></li>
                    <li><a class="btn_dc" style="cursor: pointer;" onclick="orgztreeExport('');return false;">导出全部组织机构树</a></li>
                </ul>
			</c:if>
			
		</div>
		<c:if test="${!query.disused}">
		<div class="buttonbox">
            <ul>
                <c:if test="${flag}">
                    <li><a id="dryjbm" class="btn_dr" style="cursor: pointer;">导入一级部门</a></li>
                </c:if>
            </ul>
        </div>
        </c:if>
	  	<p class="toolbox_fot">
			<em></em>
		</p>
	</div>
		
	<div id="conDiv" class="demo_college">
		<c:if test="${fn:length(infoList) == 0}">
			<div class="page_prompt">
				<div class="page_promptcon">
				  <h3>温馨提示：</h3>
				  <p><font color="#0f5dc2">没有找到符合条件的组织机构信息</font></p>
				</div>
				<p>&nbsp;</p>
			</div>
		</c:if>
		<c:if test="${fn:length(infoList) > 0}">
		<c:forEach items="${infoList}" var="bean" varStatus="sta">
			<div>	
				<h3 class="college_title" id="college_title_${bean.oid }">
					<span id="college_span_${bean.oid}" name="college_span" class="title_name">
						<a href="#" style="text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;">${bean.name }(${bean.oid })</a>
					</span>
					<div class="explain_left01" style="display:none;top:24px;">
						<div class="explain_con">
							<table width="100%" border="0" class="explain_tab" >
								<tbody>
									<tr>
										<td><span class="title">部门代码：</span>${bean.oid }</td>
										<td><span class="title">部门名称：</span>${bean.name }</td>
									</tr>
									<tr>
										<td><span class="title">部门类别：</span>${ bean.orgTypeInfo.text }</td>
										<td><span class="title">创建时间：</span>${bean.createTimeText }</td>
									</tr>
									<tr>
										
										<td><span class="title">主管：</span>${bean.managerInfo.name }</td>
										<td><span class="title">负责人：</span>${bean.prinInfo.name }</td>
									</tr>
									<tr>
										<td><span class="title">单位全称：</span>${bean.dwqc }</td>
										<td><span class="title">所属总支：</span><ct:codeParse catalog="DM_DEF_SSZZ" code="${bean.sszz }"/></td>
									</tr>
									<tr>
										<td><span class="title">编制数：</span>${bean.bzs }</td>
										<td><span class="title">顺序码：</span>${bean.orderCode }</td>
									</tr>
									<tr>
										<td><span class="title">备注：</span>${bean.remark }</td>
										<td>
											<c:if test="${query.disused}">
												<span class="title">废弃时间：</span>${bean.disuseTimeText }
											</c:if>&nbsp;
										</td>
									</tr>
								</tbody>
								<c:if test="${flag}">
									<tfoot>
										<tr id="${bean.oid }">
											<td colspan="2">
												<c:if test="${!query.disused}">
													<a name="fq" href="#" class="name">废弃部门</a>
													<a name="rybd" href="#" class="name">人员变动</a>
													<a name="xg" href="#" class="name">修改</a>
												</c:if>
												<c:if test="${query.disused && bean.disused}">
													<a name="sc" href="#" class="name">彻底删除</a>
													<a name="qy" href="#" class="name">重新启用</a>
													<a name="rybd" href="#" class="name">人员变动</a>
												</c:if>
											</td>
										</tr>
									</tfoot>
								</c:if>
							</table>
						</div>
					</div>
					<a class="up" id="up_${bean.oid }" href="#">收起</a>
				</h3>
				<div id="con_${bean.oid }" name="con_name_${bean.name }" class="con after">
					<ul id="ul_${bean.oid }">
						<c:forEach items="${bean.children}" var="child" varStatus="sta_c">
							<li id="li_${child.oid }">
								<a class="college_li college_checkbox" href="#" id="college_li_${child.oid }" <c:if test="${supperOid != null && supperOid != '' && child.oid == supperOid}"> style="border:#9eccf0 1px solid; background:url(<%=stylePath %>/images/blue/college_bg3.gif) left center repeat-x; color:#00569d!important;"</c:if> >${child.name }(${child.oid })</a>
							<c:if test="${sta_c.index%5 < 3}">
								<div class="explain_left01" style="display:none;">
							</c:if>
							<c:if test="${sta_c.index%5 >= 3}">
								<div class="explain_right01" style="display:none;">
							</c:if>
									<div class="explain_con">
										<table width="100%" border="0" class="explain_tab">
											<tbody>
												<tr>
													<td><span class="title">部门代码：</span>${child.oid }</td>
													<td><span class="title">部门名称：</span>${child.name }</td>
												</tr>
												<tr>
													<td><span class="title">上级部门：</span>${child.parent.name }</td>
													<td><span class="title">部门类别：</span>${child.orgTypeInfo.text }</td>
												</tr>
												<tr>
													<td><span class="title">主管：</span>${child.managerInfo.name }</td>
													<td><span class="title">负责人：</span>${child.prinInfo.name }</td>
													
												</tr>
												<tr>
													<td><span class="title">顺序码：</span>${child.orderCode }</td>
													<td><span class="title">单位全称：</span>${child.dwqc }</td>
												</tr>
												<tr>
													<td><span class="title">所属总支：</span><ct:codeParse catalog="DM_DEF_SSZZ" code="${child.sszz }"/></td>
													<td><span class="title">编制数：</span>${child.bzs }</td>
												</tr>
												
												<tr>
												    <td><span class="title">创建时间：</span>${child.createTimeText }</td>
												    <c:if test="${query.disused}">
														<td>
															<span class="title">废弃时间：</span>${child.disuseTimeText }
														</td>
													</c:if>
												    <c:if test="${!query.disused}">
														<td>&nbsp;</td>
													</c:if>
												</tr>
											</tbody>
											<tfoot>
												<tr id="${child.oid }">
													<td colspan="2">
														<c:if test="${!query.disused}">
															<a name="xg1" href="#" class="name">修改</a>
															<a name="rybd1" href="#" class="name">人员变动</a>
															<a name="fq1" href="#" class="name">废弃部门</a>
														</c:if>
														<c:if test="${query.disused && child.disused}">
															<a name="sc1" href="#" class="name">彻底删除</a>
															<a name="rybd1" href="#" class="name">人员变动</a>
															<a name="qy1" href="#" class="name">重新启用</a>
														</c:if>
													</td>
												</tr>
											</tfoot>
										</table>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul> 
					
					<ul>
						<c:if test="${!query.disused}">
							<li><a name="zj" class="college_add" href="#" onclick="add('${bean.oid }')" >+ 添加</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</c:forEach>
		</c:if>
	</div>
	<c:if test="${flag}">		
		<div class="demo_add">
			<c:if test="${!query.disused}">
				<a name="zj" href="#" onclick="add('')">+ 添加</a>
			</c:if>
		</div>
	</c:if>
	<div style="height: 300px"></div>
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
            <button type="submit" id="closeInfoWindow" name="close" onclick="$('#tips').hide();">关     闭</button>
            <button type="button" id="viewInfoWindow" >处理中</button>
          </div>
          </td>
          </tr>
      </table>
    </div>
    </div>
  </body>
</html>
