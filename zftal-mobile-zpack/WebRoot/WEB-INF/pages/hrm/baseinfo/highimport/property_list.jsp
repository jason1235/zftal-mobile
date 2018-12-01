<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
    <%@ include file="/commons/hrm/head.ini" %>
    <script>
    $(function(){
    	$("#btn_zj").click(function(){
    		showTopWin("<%=request.getContextPath()%>/baseinfo/property_select.html?property.mbid=${property.mbid}", "500", "500","yes" );
    		back2();	
    	});
    	
    	$("#btn_mb").click(function(){
    		downloadTemplate();
    	});
    	
    	$("#btn_dr").click(function(){
    		uploadEntity();
    	});
    	
    	$('a[class="btn_fh"]').click(function(){
			back();
		});
    	
	    $('a[name="sc"]').click(function(){
			showConfirm("确认要删除吗?");
			var id=$(this).closest("tr").attr("trKey");
			$("#why_cancel").click(function(){
				divClose();
			});

			$("#why_sure").click(function(){
				delRequest(id);
			});
	    });
    	    	
    	fillRows("20","","",false);
    });

	function delRequest(id){
		$.post(_path+'/baseinfo/property_delete.html',"property.id="+id,function(data){
			var callback = function(){
				$("#search").submit();
			};
			processDataCall(data,callback);
		},"json");
	}
	
	function back(){  //返回
		var content = '<form id="form1" method="post" action="<%=basePath%>/baseinfo/highImport_list.html">';
		content +='    </form>';
		$('body').append(content);
		$('#form1').submit();
		$('#form1').remove();
	}
	
	function back2(){
		var content = '<form id="form1" method="post" action="<%=basePath%>/baseinfo/property_list.html">';
		content +='	   <input type="hidden" name="template.id" value="${template.id }"/>'
		content +='    </form>';
		$('body').append(content);
		$('#form1').submit();
		$('#form1').remove();
	}
	
	function downloadTemplate(){//模板下载
		var mbid=$("#mbid").val();
		window.location.href = _path + "/baseinfo/property_downloadTemplate.html?property.mbid="+mbid;
	}
	
	function uploadEntity(){//数据导入
		var mbid=$("#mbid").val();
		showWindow("数据导入", "<%=request.getContextPath()%>/baseinfo/property_upload.html?property.mbid="+mbid, 480, 150 );
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
  <body><!--
	--><div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" class="btn_zj">增 加</a></li>
				<li><a id="btn_mb" class="btn_zj">模版下载</a></li>
				<li><a id="btn_dr" class="btn_zj">数据导入</a></li>
				<li><a onclick="return false;" class="btn_fh" href="#">返回</a></li>
			</ul>
		</div>

		<form id="search" method="post" action="<%=request.getContextPath()%>/baseinfo/property_list.html">
		<input type="hidden" id="mbid" name="property.mbid" value="${property.mbid }"/>
		<input type="hidden" name="template.id" value="${template.id }"/>
		<input type="hidden" name="mbmc" value="${mbmc }"/>
    	<h3 class="datetitle_01">
    		<span>高级导入模版列表${mbmc }</span>
    	</h3>
		<table width="100%" class="dateline" id="tiptab">
			<thead id="list_head">
				<tr>
					<td width="8%"><p align="center">序号</p></td>
					<td width="12%"><p align="center">信息类名称</p></td>
					<td width="20%"><p align="center">属性名称</p></td>
					<td width="20%"><p align="center">字段名称</p></td>
					<td width="20%"><p align="center">字段类型</p></td>
					<td width="12%"><p align="center">字段长度</p></td>
					<td width="8%"><p align="center">操作</p></td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${pageList}" var="property" varStatus="st">
				<tr name="tr" trKey="${property.id }">
					<td>${st.index+1 }</td>
					<td name="xxlmc">
					<c:forEach items="${infoClasses }" var="infoClass">
					<c:if test="${infoClass.guid eq property.xxlid}" >${infoClass.name }</c:if> 
					</c:forEach>
					</td>
					<td name="sxmc">${property.sxmc }</td>
					<td name="zdmc">${property.zdmc }</td>
					<td name="zdlx">${property.zdlx }</td>
					<td name="zdcd">${property.zdcd }</td>
					<td name="zdcd"><a name="sc" href="#" style="color:blue">删除</a></td>
				</tr>
				</c:forEach>
			</tbody>				
	  	</table>
	  	<div>
	  		<ct:page pageList="${pageList }" query="${property }" queryName="property"/>
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
  		</form>
  	</div>
  </body>
</html>