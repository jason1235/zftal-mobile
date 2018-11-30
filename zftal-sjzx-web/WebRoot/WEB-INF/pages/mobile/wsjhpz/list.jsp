<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <%--<style type="text/css">
      .autocut {
          overflow:hidden;
          white-space:normal;
          text-overflow:ellipsis;
          -o-text-overflow:ellipsis;
          -icab-text-overflow:ellipsis;
          -khtml-text-overflow:ellipsis;
          -moz-text-overflow:ellipsis;
          -webkit-text-overflow:ellipsis;
      }

      .autocut:hover {
          overflow:visible;
          white-space:normal;
          word-wrap:break-word;
      }--%>
    </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
   
    $(function(){
    	$("#btn_zj").click(function(){
    		var url = _path+"/webservice/wsjhconfig_add.html";
    		window.location.href=url;
	        //$("#myModal_toji").find(".modal-content").width(950);
	        //$("#myModal_toji").find(".modal-content").height(695);
	        //$("#myFrame").attr("src", url);
	        //$("#myModal_toji").modal("show");
    	});
    	
    	$("#btn_xg").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请选择需要修改的WebService配置!", "e");
                    return false;
                }
            var name = $("input[id='id']:checked").closest("tr").find("#jhmc").html();
           	var url = _path+"/webservice/wsjhconfig_edit.html?name="+encodeURI(name);
           	window.location.href=url;
	        //$("#myModal_toji").find(".modal-content").width(950);
	       // $("#myModal_toji").find(".modal-content").height(695);
	        //$("#myFrame").attr("src", url);
	        //$("#myModal_toji").modal("show");
     	});
     	
     	$("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请选择需要删除的WebService配置!", "e");
                    return false;
                }
            var name = $("input[id='id']:checked").closest("tr").find("#jhmc").html();
            var isdsq = $("input[id='id']:checked").closest("tr").find("#isdsq").html();
            var time = new Date().getTime();
           	var url = _path+"/webservice/wsjhconfig_del.html";
	         $.post(url,"name="+encodeURI(name)+"&isdsq="+encodeURI(isdsq)+"&time="+time,function(data){
	         	var callback = function(){
					$("#search").submit();
				};
							
				processCall(data, callback);
				
                //if(data.success){
                //	$("#search_go").submit();
                //}else{
                //	openAlert(data.text, "e");
                    //errorAlert(data.text);
                //}
            },"json");
     	});
     	
     	$("#btn_ck").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请选择需要查看日志的配置!", "e");
                    return false;
                }
            var jhpzxh = $("input[name='id']:checked").val();
           	var url = _path+"/webservice/wsjhconfig_viewLog.html?query.wsjhxh="+jhpzxh;
           	window.location.href=url;
	        //$("#myModal_toji").find(".modal-content").width(950);
	        //$("#myModal_toji").find(".modal-content").height(695);
	        //$("#myFrame").attr("src", url);
	        //$("#myModal_toji").modal("show");
     	});
     	
     	$(".run").click(function(){
     		var name = $(this).closest("tr").find("#jhmc").html();
   			var url = _path+"/webservice/wsjhconfig_runWsjh.html";
     		$.post(url,"name="+encodeURI(name),function(data){
					if(data.success=='true'){
						alert(data.msg);
					}
				},"json");
     	});
     	
    	$("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
          });
    	$("#list_body tr").find("input[name='id']").removeAttr("checked");
    	$("#list_body tr").find("input").bind("click",function(e){
			var check = $(this).prop("checked");
     		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			if(check){
			   
			}else{
			   $(this).prop("checked",true);
			}
	 	});     
	     $('#list_body tr').bind('click',function(e){
			 var status = $(this).find('input[name="id"]').prop('checked');
			 $(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			 if (status) {
				$(this).find('input[name="id"]').removeAttr('checked');
				$(this).removeClass("current");
			 } else {
			    $(this).attr("class", "current");
				$(this).find('input[name="id"]').prop('checked',true);
			 }
		});
    });
    
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <div class="toolbox">
          <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_zj">增加</button>
              <button type="button" class="btn btn-default" id="btn_xg">修改</button>
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
              <!--<button type="button" class="btn btn-default" id="btn_ck">查看日志</button>
          --></div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath()%>/webservice/wsjhconfig_main.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-6 control-label">WebService交换配置名称：</label>
                        <div class="col-xs-6">
                            <input id="query_wsjhConfig_mc" class="form-control" name="query.wsjhmc" value="${query.wsjhmc }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-1">
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
                    </div>
                </div>
            </div>
          </div>
          
          
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>WebService交换配置名称</th>
                    <th>目的数据库</th>
                    <th>目的表</th>
                    <th>服务地址</th>
                    <th>命名空间</th>
                    <th>调用方法</th>
                    <th>是否定时</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" name="id" value="${menu.wsjhxh }"/></td>
                    <td class="autocut" id="jhmc">${menu.wsjhmc }</td>
                    <td class="autocut" >${menu.wsjhds }</td>
                    <td class="autocut" >${menu.wsjhb}</td>
                    <td class="autocut" >${menu.url}</td>
                    <td class="autocut" >${menu.namespace}</td>
                    <td class="autocut" >${menu.operate}</td>
                    
                    <td class="autocut" id="isdsq">${menu.isdzq}</td>
                    <td class="autocut" >
						<button type="button" class="btn btn-primary btn-sm run" id="btn_zx">执行</button>
					</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
 
	</body>
</html>