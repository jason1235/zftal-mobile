<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
	    var current = null;
		$(function(){

			//监听单击行
			$("tbody > tr").click(function(){
				var guid = $(this).attr("id");
				
				if( guid == null )
				{
					return;
				}
				
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			
			//监听双击行
			$("tbody > tr").dblclick(function(){
				var guid = $(this).attr("id");
				
				if( guid == null )
				{
					return;
				}
				
				modifyItem( guid );
			});

			//监听增加
			$(".btn_zj").click( function(){
				addItem();
			});

			//执行事件
            $("#btn_do_push").click( function(){
            	
                var guid = $(current).attr("id");
                if(current == null||guid == null) {
                    alert("请先选择需要执行的表单事件");
                    return false;
                }
            	showWindow("手工执行","<%=request.getContextPath() %>/baseinfo/pushInfo_push.html?config.id="+guid, 500, 400);
            });

          //执行事件
            $("#btn_do_inbill").click( function(){
                
                var guid = $(current).attr("id");
                if(current == null||guid == null) {
                    alert("请先选择需要执行的表单事件");
                    return false;
                }
                showWindow("反向推送","<%=request.getContextPath() %>/baseinfo/pushInfo_pushBill.html?config.id="+guid, 500, 400);
            });
			
			//增加表单
			function addItem()
			{
				showWindow("增加","<%=request.getContextPath() %>/bill/pushdata_edit_config.html?oper=add", 700, 400);
			}
	
			//修改表单
			function modifyItem(guid)
			{
				showWindow("修改","<%=request.getContextPath() %>/bill/pushdata_edit_config.html?oper=modify&config.id="+guid, 700, 400);
			}
			
			//配置表单
			function configItem(guid)
			{
				location.href="<%=request.getContextPath() %>/bill/pushdata_list_property.html?config.id="+guid;
			}
	
			//删除表单
			function removeItem(guid)
			{
				showConfirm("确定要删除吗？");
	
				$("#why_cancel").click(function(){
					alertDivClose();
				});
	
				$("#why_sure").click(function(){
					$.post('<%=request.getContextPath() %>/bill/pushdata_remove_config.html', 'config.id='+guid, function(data){
						var callback = function(){
							window.location.reload();
						};
						
						processDataCall(data, callback);
					}, "json");
				});
			}
	
			function operationList(){
				$("a[name='config']").click(function(){//行数据配置
					var guid = $(this).closest("tr").attr("id");
					configItem(guid);
				});
				$("a[name='modify']").click(function(){//行数据修改链接
					var guid = $(this).closest("tr").attr("id");
					modifyItem(guid);
				});
				$("a[name='del']").click(function(){//行数据删除链接
					var guid = $(this).closest("tr").attr("id");
					removeItem(guid);
				});
				$(".select_tools a").css("cursor","pointer");
				operationHover();
			}
			
			operationList();
			
			fillRows(15, '', '', false);

			$("button[name='close']").click(function(){
                $("#tips").fadeOut("slow",function(){
                    $("#scroll").empty();
                });
            });
		});


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
	<div class="toolbox" style="z-index:0;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" href="#" class="btn_zj">增加</a></li>
				<li><a id="btn_do_push" href="#" class="btn_down">执行</a></li>
				<li><a id="btn_do_inbill" href="#" class="btn_up">反向推送至表单</a></li>
			</ul>
		</div>
	</div>
	<div class="formbox">
		<h3 class="datetitle_01">
			<span>表单列表</span>
		</h3>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>序号</td>
					<td>事件名称</td>
					<td>表单名称</td>
					<td>表单类名称</td>
					<td>事件类型</td>
					<td>操作类型</td>
					<td>信息类名称</td>
					<td>数据库表名</td>
					<td width="12%">操作</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${configs}" var="bean" varStatus="i">
				<tr id="${bean.id }">
					<td>${i.index+1 }</td>
					<td>${bean.name }</td>
					<td>${bean.billConfigName }</td>
					<td>${bean.billClassName }</td>
					<td>${bean.eventType.value }</td>
					<td>${bean.eventOpType.value }</td>
					<td>${bean.infoClassName }</td>
					<td>${bean.localTable }</td>
					<td>
					  <div>
				      	<div class="current_item">
				        	<span class="item_text">属性绑定</span>
				        </div>
				        <div class="select_tools" style=" width:80px; display:none">
				            <ul>
				            	<li><a name="config" href="#" class="first1">属性绑定</a></li>
				                <li><a name="modify" href="#" class="last1">修改</a></li>
				                <li><a name="del" href="#" class="last1">删除</a></li>
				            </ul>
				        </div>
				      </div>
					</td>
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
</body>

</html>