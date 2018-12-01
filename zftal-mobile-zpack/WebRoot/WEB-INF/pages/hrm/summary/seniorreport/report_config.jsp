<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="code" uri="/WEB-INF/code.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>

	<script type="text/javascript">
		$(function(){
			$("#btn_zjxxl").click(function(){
				showWindow("增加","<%=request.getContextPath() %>/summary/seniorreportconfig_add_report.html",330,330);
			});
			$("#btn_drbbmb").click(function(){
				showWindow("报表模板导入","<%=request.getContextPath() %>/summary/seniorreportconfig_toUpload.html?itemType=config",330,230);
            });
			var res = false;
			$(".mes_list_con ul li").click(function(){
				if( $(this).attr("name") == null ) return;
				if( res ) return;
				location.href="<%=request.getContextPath() %>/summary/seniorreportconfig_list.html?id="+$(this).attr("name");
			});

			$("#model_upload").click(function(){
				showWindow("导出模板上传","<%=request.getContextPath() %>/summary/seniorreportconfig_toUpload.html?id=${id}",330,180);
			});

			$("#cfg_download").click(function(){
                window.open("<%=request.getContextPath() %>/summary/seniorreportconfig_downloadCfg.html?id=${id}");
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
					showWindow("修改","<%=request.getContextPath() %>/summary/seniorreportconfig_modify_report.html?id="+guid,330,330);
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
						$.post('<%=request.getContextPath() %>/summary/seniorreportconfig_delete_report.html', 'id='+guid, function(data){
							var callback = function(){
								location.href="<%=request.getContextPath() %>/summary/seniorreportconfig_list.html";
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
			$("#btn_zjl").click(function(){
				if($("#currentId").val()==""){
					showWarning("请选择报表");
					
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
				}
				showWindow("增加列","<%=request.getContextPath() %>/summary/seniorreportconfig_add_column.html?id="+$("#currentId").val(),300,230);
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
				showWindow("增加行","<%=request.getContextPath() %>/summary/seniorreportconfig_add_row.html?id="+$("#currentId").val(),300,230);
			});
			
			$(".tab_szcd").hover(function(){
				var btnlisthtml;
				var fieldName=$(this).find("input[name=fieldName]").val();
				var name = $(this).attr("name");
					btnlisthtml=$("#btn_list_"+name).html();
					$(this).append(btnlisthtml);
					var btnlist=$(this).find(".btn_list");
					btnlist.find(".zy").click(function(){
						//左移
						methodAction(fieldName,name+"_left",false);
                        return false;
					});
					btnlist.find(".yy").click(function(){
						//右移
						methodAction(fieldName,name+"_right",false);
						return false;
					});
					btnlist.find(".sy").click(function(){
                        //上移
                        methodAction(fieldName,name+"_up",false);
                        return false;
                    });
					btnlist.find(".xy").click(function(){
                        //下移
                        methodAction(fieldName,name+"_down",false);
                        return false;
                    });
					btnlist.find(".xg").click(function(){
						showWindow("修改","<%=request.getContextPath() %>/summary/seniorreportconfig_modify_"+name+".html?id="+$("#currentId").val()+"&item.itemId="+fieldName,300,230);
					});
					btnlist.find(".sc").click(function(){
						//删除
						showConfirm("确定要删除吗？");

						$("#why_cancel").click(function(){
							divClose();
						});

						$("#why_sure").click(function(){
							methodAction(fieldName,"remove_"+name,true);
							return false;
						});
					});
					btnlist.find(".dc").click(function(){
						//插入子字段
						showWindow("增加子字段","<%=request.getContextPath() %>/summary/seniorreportconfig_add_"+name+".html?id="+$("#currentId").val()+"&pid="+fieldName,300,230);
					});
				$(this).find(".btn_list").css("display","block");
			},function(){
				$(this).find(".btn_list").remove();
			});
		});


		function methodAction(fieldName,method,showTip){
			$.post("<%=request.getContextPath() %>/summary/seniorreportconfig_"+method+".html","id="+$("#currentId").val()+"&item.itemId="+fieldName,function(data){
			    if(showTip){
	                tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
	                
	                $("#window-sure").click(function() {
	                    divClose();
	                    
	                    if( data.success ) {
	                        window.location.reload();
	                    }
	                });
			    }else{
			    	window.location.reload();
				}         
            }, "json");
		}
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
	<div id="btn_list_column">
		<div class="btn_list" style="display:none;width:50px;height:150px;">
	      <ul>
	        <li><a href="#" class="zy">左移</a></li>
	        <li><a href="#" class="yy">右移</a></li>
	        <li><a href="#" class="xg">修改</a></li>
	        <li><a href="#" class="sc">删除</a></li>
	        <li><a href="#" class="dc">增加子字段</a></li>
	      </ul>
	   	</div>
	</div>
	<div id="btn_list_row">
		<div class="btn_list" style="display:none;width:50px;height:150px;">
	      <ul>
	        <li><a href="#" class="sy">上移</a></li>
	        <li><a href="#" class="xy">下移</a></li>
	        <li><a href="#" class="xg">修改</a></li>
	        <li><a href="#" class="sc">删除</a></li>
	        <li><a href="#" class="dc">增加子字段</a></li>
	      </ul>
	   	</div>
	</div>
	
	<div class="selectbox" style="position:relative; z-index: 0;" >
		<div class="toolbox">
        <div class="buttonbox">
            <ul>
                <li>
                    <a id="btn_zjxxl" class="btn_zj" href="#">添加报表</a>
                    <a></a>
                    <a id="btn_drbbmb" class="btn_dr" href="#">导入报表</a>
                </li>
            </ul>
        </div>
    </div>
		<div class="mes_list" id="mes_list">
			    <c:forEach items="${types}" var="t">
			    <div class="mes_list_con">
				<h2 name="reportName"><a href="#">${t.text }报表</a></h2>
				<ul>
					<c:forEach items="${reportList}" var="report">
					<c:if test="${report.menuId==t.key}">
					   <li name="${report.reportId }" editable="true" <c:if test="${report.reportId eq id }"> class="current"</c:if>><a href="#">${report.reportName }</a></li>
					</c:if>
					</c:forEach>
				</ul>
				<div class="clear"></div>
				</div>
				</c:forEach>
				
		</div>
	</div>
	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li>
					<a id="btn_zjl" class="btn_zj">增加列</a>
					<a></a>
					<a id="btn_zjh" class="btn_zj">增加行</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="formbox">
		<div class="reporttitle">${reportView.reportTitle }
		<c:if test="${id != null&&id != ''}">
		<a id="cfg_download" style='font-size:12px;color: blue;'>[报表配置导出]</a>
		<a id="model_upload" style='font-size:12px;color: blue;'>[数据模板上传]</a>
		</c:if></div>
		<div style="">
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				${Title }
			</thead>
			<tbody id="list_body">
				${Body }
			</tbody>
		</table>
		</div>
	</div>
</body>
</html>