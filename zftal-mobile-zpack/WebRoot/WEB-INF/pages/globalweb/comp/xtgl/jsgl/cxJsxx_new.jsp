<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
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
      }
    </style>--%>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
     $("#btn_ck").click(function(){//功能条编辑按钮
          //alert($("input[name='id']:checked").length);
     		if($("input[name='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }	
            var id = $("input[name='id']:checked").val();
            var url = _path+"/xtgl/jsgl_edit.html?NewJsglQuery.jsdm=" + id;
            $("#myModal_toji").find(".modal-content").width(550);
            $("#myModal_toji").find(".modal-content").height(230);
            $("#myFrame").height(230);
            $("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
     });
    
     $("#btn_zj").click(function(){//功能条增加按钮
    	 var url = _path+"/xtgl/jsgl_edit.html";
         $("#myModal_toji").find(".modal-content").width(550);
         $("#myModal_toji").find(".modal-content").height(245);
         $("#myFrame").height(245);
         $("#myFrame").attr("src", url);
         $("#myModal_toji").modal("show");
     });
            
     $("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").closest("tr").find("input[name='procode']").val() == "999"){
            		//alert("不能删除移动门户！");
            		openAlert("不能删除移动门户！", "e");
                    return false;
            	};
            var id = $("input[name='id']:checked").val();
     	       $.post("<%=request.getContextPath()%>/xtgl/jsgl_remove.html","NewJsglQuery.jsdm="+id,function(data){
                     if(data.success){
                    	 var callback = function(){
     						//reflashPage();
     						location.reload();
     					};
                     	openAlert("删除成功", "s","auto", callback);
                     }else{
                         alert(data.text);
                     }
                 },"json");
     });
     $("#btn_fpfw").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            var id = $("input[name='id']:checked").val();
            var jsmc = $("input[name='id']:checked").closest("td").find("input[name='jsmc']").val();
            window.location.href="<%=request.getContextPath()%>/serviceManager/fwdyjs_fpfu.html?jsid="+id+"&jsmc="+jsmc;
            //fpfw();
     });
     $("#btn_fpmhfw").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            var id = $("input[name='id']:checked").val();
            var jsmc = $("input[name='id']:checked").closest("td").find("input[name='jsmc']").val();
            window.location.href="<%=request.getContextPath()%>/myportal/portalReRo_fpfu.html?jsid="+id+"&jsmc="+jsmc;
            //fpmhfw();
     });
     
     $("#btn_fpmenu").click(function(){//功能条删除按钮
        	if($("input[name='id']:checked").length==0){
                //alert("请先选中操作行！");
                openAlert("请先选中操作行！", "e");
                 return false;
             }
         if($("input[name='id']:checked").length>1){
                //alert("不能选中多行！");
                openAlert("不能选中多行！", "e");
                 return false;
             }
         var id = $("input[name='id']:checked").val();
         var jsmc = $("input[name='id']:checked").closest("td").find("input[name='jsmc']").val();
         window.location.href="<%=request.getContextPath()%>/menu/menu_fpmenu.html?query.jsid="+id+"&jsmc="+jsmc;
         //fpmhfw();
  	});
            
     $("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
     
	 
	$("#list_body tr").bind("click",function(e){
	 	var check = $(this).find("input[name='id']").prop("checked");
	 	//alert(check);
	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
		//$(this).closest("tbody").find("tr").removeClass("current");
		var procodeMax = $(this).find("input[name='procode']").val();
		var status = $(this).find("#status").val();
			//alert(check);
			if(check){
				//$("#btn_qy").show();
			   //$("#btn_ty").show();
			   //$("#btn_sc").show();
			}else{
			   $(this).find("input[name='id']").prop("checked",true);
			   //$(this).closest("tr").attr("class","current");
			   //if (status=="1") {
				//	$("#btn_qy").hide();
				//	$("#btn_ty").show();
				//	$("#btn_sc").hide();
			   //} else {
				//	$("#btn_qy").show();
				//	$("#btn_ty").hide();
				//	$("#btn_sc").show();
						
			   //}
			   
			}
	});
	
	$("#list_body tr").find("input").bind("click",function(e){
			var check = $(this).prop("checked");
     		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			//$(this).closest("tbody").find("tr").removeClass("current");
			if(check){
			   
			}else{
			   $(this).prop("checked",true);
			   //$(this).closest("tr").attr("class","current");
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
              <button type="button" class="btn btn-default" id="btn_ck">修改</button>
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
              <button type="button" class="btn btn-default" id="btn_fpfw">分配服务</button>
              <button type="button" class="btn btn-default" id="btn_fpmhfw">分配门户服务</button>
              <button type="button" class="btn btn-default" id="btn_fpmenu">分配菜单</button>
          </div>
      </div>
      <jsp:include page="/WEB-INF/pages/globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="xtgl/jsgl_cxJsxx.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                	<th style="width: 5%;">选择</th>
                    <th style="width: 5%;">序号</th>
                    <th style="width: 22%;">角色代码</th>
                    <th style="width: 22%;">角色名称</th>
                    <th style="width: 22%;">用户数</th>
                    <th style="width: 22%;">角色创建人</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="JsglModelList" var="menu" status="st">
                <tr name="tr" class="data pxtr">
                    <td>
                    	<input type="checkbox" id="id" name="id" value="${menu.jsdm }"/>
                    	<input type="hidden" id="jsmc" name="jsmc" value="${menu.jsmc }"/>
                    </td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.jsdm }</td>
                    <td class="autocut" >${menu.jsmc}</td>
                    <td class="autocut" >${menu.yhnum}</td>
                    <td class="autocut" >${menu.jscjrxm}</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${JsglModelList }" query="${NewJsglQuery }" queryName="NewJsglQuery"/>
      </form>
  </div>
	</body>
</html>