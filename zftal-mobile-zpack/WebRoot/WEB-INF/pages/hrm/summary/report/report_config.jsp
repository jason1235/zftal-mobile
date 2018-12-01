<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="code" uri="/WEB-INF/code.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#btn_zjxxl").click(function(){
				showWindow("增加","<%=request.getContextPath() %>/summary/reportconfig_add_report.html",330,330);
			});
			var res = false;
			$(".mes_list_con ul li").click(function(){
				if( $(this).attr("name") == null ) return;
				if( res ) return;
				location.href="<%=request.getContextPath() %>/summary/reportconfig_pre_view.html?id="+$(this).attr("name");
			});
			
			//信息类目录监听
			$(".mes_list_con li").hover(function(){
				res = false;
				if( $(this).attr("name") == null ) return;
				var $mes_list_tools=$("#mes_list_tools").html();
				$(this).append($mes_list_tools).css("position","relative").children(".mes_list_tools").show();

				//信息类目录修改
				$(".ico_edit_mes").click(function(){
					res = true;
					var guid = $(this).closest("li").attr("name");
					showWindow("修改","<%=request.getContextPath() %>/summary/reportconfig_modify_report.html?id="+guid,330,330);
				});

				//信息类目录删除
				$(".ico_delete_mes").click(function(){
					res = true;
					var guid = $(this).closest("li").attr("name");

					showConfirm("确定要删除吗？");

					$("#why_cancel").click(function(){
						divClose();
					});

					$("#why_sure").click(function(){
						$.post('<%=request.getContextPath() %>/summary/reportconfig_delete_report.html', 'id='+guid,function(data){
							var callback = function(){
								location.href="<%=request.getContextPath() %>/summary/reportconfig_list.html";
							};
							processDataCall(data,callback);
						},"json");
						return false;
					});
				});
			},function(){
				$(this).find(".mes_list_tools").remove();
			});
			//增加列
            $("#btn_xqzd").click(function(){
                if($("#currentId").val()==""){
                    showWarning("请选择报表");
                    
                    $("#war_sure").click(function(){
                        divClose();
                    });
                    return false;
                }
                showWindow("详情字段","<%=request.getContextPath() %>/summary/reportconfig_revConfig.html?id="+$("#currentId").val(),600,500);
            });
			
			//增加列
			$("#btn_zjl").click(function(){
				if($("#currentId").val()==""){
					showWarning("请选择报表");
					
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
				}
				showWindow("增加列","<%=request.getContextPath() %>/summary/reportconfig_add_column.html?id="+$("#currentId").val(),600,500);
			});
			
			//增加行
			$("#btn_zjh").click(function(){
				if($("#currentId").val()==""){
					showWarning("请选择报表");
					
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
				}
				showWindow("增加行","<%=request.getContextPath() %>/summary/reportconfig_add_row.html?id="+$("#currentId").val(),600,500);
			});
			
			$(".tab_szcd").hover(function(){
				var btnlisthtml;
				var fieldName=$(this).find("input[name=fieldName]").val();
				if($(this).attr("name")=="col"){
					btnlisthtml=$("#btn_list_col").html();
					$(this).append(btnlisthtml);
					var btnlist=$(this).find(".btn_list");
					btnlist.find(".btn_zy").click(function(){
						//左移
						location.href="<%=request.getContextPath() %>/summary/reportconfig_column_left.html?id="+$("#currentId").val()+"&item.fieldName="+fieldName;
					});
					btnlist.find(".btn_yy").click(function(){
						//右移
						location.href="<%=request.getContextPath() %>/summary/reportconfig_column_right.html?id="+$("#currentId").val()+"&item.fieldName="+fieldName;
					});
					btnlist.find(".btn_xg").click(function(){
						showWindow("修改列","<%=request.getContextPath() %>/summary/reportconfig_modify_column.html?id="+$("#currentId").val()+"&item.fieldName="+fieldName,300,230);
					});
					btnlist.find(".btn_sc").click(function(){
						//删除
						showConfirm("确定要删除吗？");

						$("#why_cancel").click(function(){
							divClose();
						});

						$("#why_sure").click(function(){
							$.post("<%=request.getContextPath() %>/summary/reportconfig_remove_column.html","id="+$("#currentId").val()+"&item.fieldName="+fieldName,function(data){
								var callback = function(){
									$("form:first").submit();
								};
								processDataCall(data,callback);
							},"json");
							return false;
						});
					});
					btnlist.find(".btn_dc").click(function(){
						//右边插入字段
						showWindow("增加右侧列","<%=request.getContextPath() %>/summary/reportconfig_add_column.html?id="+$("#currentId").val()+"&item.fieldName="+fieldName,300,230);
					});
				}else{
					btnlisthtml=$("#btn_list_row").html();
					$(this).append(btnlisthtml);
					var btnlist=$(this).find(".btn_list");
					var btnlist=$(this).find(".btn_list");
					btnlist.find(".btn_sy").click(function(){
						//上移
						location.href="<%=request.getContextPath() %>/summary/reportconfig_row_up.html?id="+$("#currentId").val()+"&item.fieldName="+fieldName;
					});
					btnlist.find(".btn_xy").click(function(){
						//下移
						location.href="<%=request.getContextPath() %>/summary/reportconfig_row_down.html?id="+$("#currentId").val()+"&item.fieldName="+fieldName;
					});
					btnlist.find(".btn_xg").click(function(){
						showWindow("修改行","<%=request.getContextPath() %>/summary/reportconfig_modify_row.html?id="+$("#currentId").val()+"&item.fieldName="+fieldName,300,230);
					});
					btnlist.find(".btn_sc").click(function(){
						//删除
						showConfirm("确定要删除吗？");

						$("#why_cancel").click(function(){
							divClose();
						});

						$("#why_sure").click(function(){
							$.post("<%=request.getContextPath() %>/summary/reportconfig_remove_row.html","id="+$("#currentId").val()+"&item.fieldName="+fieldName,function(data){
								var callback = function(){
									$("form:first").submit();
								};
								processDataCall(data,callback);
							},"json");
								
							return false;
						});
					});
					btnlist.find(".btn_dc").click(function(){
						//下边边插入字段
						showWindow("增加下方列","<%=request.getContextPath() %>/summary/reportconfig_add_row.html?id="+$("#currentId").val()+"&item.fieldName="+fieldName,300,230);
					});
				}
				
				$(this).find(".btn_list").css("display","block");
			},function(){
				$(this).find(".btn_list").remove();
			});
		});
	</script>
</head>

<body>
	<input type="hidden" name="id" id="currentId" value="${id }"/>
	
	<div id="mes_list_tools">
		<div class="mes_list_tools" style="display:none;">
			<span id="btn_xgxxl" class="ico_edit_mes"></span>
			<span id="btn_scxxl" class="ico_delete_mes"></span>
		</div>
	</div>
	<div id="btn_list_col">
		<div class="btn_list" style="display:none;">
	      <ul>
	        <li><a href="#" class="btn_zy">左移</a></li>
	        <li><a href="#" class="btn_yy">右移</a></li>
	        <li><a href="#" class="btn_xg">修改</a></li>
	        <li><a href="#" class="btn_sc">删除</a></li>
	        <li><a href="#" class="btn_dc">右边插入字段</a></li>
	      </ul>
	   	</div>
	</div>
	<div id="btn_list_row">
		<div class="btn_list" style="display:none;">
	      <ul>
	        <li><a href="#" class="btn_sy">上移</a></li>
	        <li><a href="#" class="btn_xy">下移</a></li>
	        <li><a href="#" class="btn_xg">修改</a></li>
	        <li><a href="#" class="btn_sc">删除</a></li>
	        <li><a href="#" class="btn_dc">下方插入字段</a></li>
	      </ul>
	   	</div>
	</div>
	
	<div class="selectbox" style="position:relative; z-index: 0;" >
		<ul class="datetitle_01">
			<li class="">报表列表</li>
		</ul>
		<div class="mes_list" id="mes_list">
			<div class="mes_list_con">
				<h2 name="reportName"><a href="#">报表</a></h2>
				<ul>
					<c:forEach items="${reportList}" var="report">
					<li name="${report.reportId }" editable="true" <c:if test="${report.reportId eq id }"> class="current"</c:if>><a href="#">${report.reportName }</a></li>
					</c:forEach>
					<li class="mes_add"><a id="btn_zjxxl" href="#"><span class="mes_add2" title="添加信息类">+ 添加</span></a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li>
					<a id="btn_zjl" class="btn_zj">增加列</a>
					<a></a>
					<a id="btn_zjh" class="btn_zj">增加行</a>
					<a></a>
					<a id="btn_xqzd" class="btn_xg">详情字段</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="formbox">
		<div class="reporttitle">${reportView.reportTitle }</div>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<c:forEach items="${reportView.titles }" var="title" varStatus="i">
						<c:if test="${i.index eq 0 }"><td style="width:150px">${title.name }</c:if>
						<c:if test="${i.index != 0 }">
						<td>
							<div name="col" class="tab_szcd" style="position: relative;float:none;display:block;" >
							<input type="hidden" name="fieldName" value="${title.fieldName}"/>
								<a class="ico_sz" href="#">${title.name }</a>
			                </div>
			            </c:if>
						</td>
					</c:forEach>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${reportView.keySet}" var="row" varStatus="i">
				<tr>
					<td>
						<div name="row" class="tab_szcd" style="position: relative;float:none;display:block;" >
						<input type="hidden" name="fieldName" value="${row.fieldName}"/>
							<a class="ico_sz" href="#">${row.name }</a>
		                </div>
		            </td>
					<c:forEach items="${reportView.itemValueMaps[row] }" var="col">
						<td>
							<c:if test="${col.value !='0' }">
								<a href="#" >
							</c:if>
							${col.value }
							<c:if test="${col.value !='0' }">
								</a>
							</c:if>
							<input id="condition1" type="hidden" value="${row.condition }"/>
							<input id="condition2" type="hidden" value="${col.condition }"/>
						</td>
					</c:forEach>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>