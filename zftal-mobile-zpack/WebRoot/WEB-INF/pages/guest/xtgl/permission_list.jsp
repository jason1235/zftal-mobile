<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
  <%@ include file="/commons/head.ini" %>
</head>

<body id="blog-article-sidebar">


<div class="am-cf am-padding" >
    <div class="am-fl am-cf"  ><strong class="am-text-primary am-text-lg">用户菜单权限维护</strong> / <small>当前分配用户:${userName }</small></div>
</div>

<jsp:include page="/commons/modal.jsp" flush="true"></jsp:include>
<form class="am-form" action="<%=request.getContextPath() %>/guest/user_permissionList.html" method="post" name="search" id="search">

<input type="hidden" name="userId" value="${userId }"/>
<div class="am-g am-g-fixed blog-fixed">
    <div class="am-u-md-12 am-u-sm-12">

        <article class="am-g blog-entry-article">
            <div class="am-u-lg-5 am-u-md-12 am-u-sm-12">
            <div style="height:420px;overflow:auto">
            	<table class="am-table am-table-striped am-table-hover table-main" >
                <thead id="list_head">
                <tr>
                    <th class="table-title"><input type="checkbox" disabled="disabled"/></th>
                    <th class="table-title">功能代码</th>
                    <th class="table-title">菜单名称</th>
                </tr>
                </thead>
                <tbody id="list_body" >
	                <c:forEach items="${notPerList}" var="item"> 
	                	 <c:forEach items="${menuList}" var="menu"> 
		                	<c:if test="${item.gnmkdm eq menu.gnmkdm}">
				                <tr>
				                    <td><input id="id"  name="id" type="checkbox" value="${menu.gnmkdm }"></td>
				                    <td>${menu.gnmkdm }</td>
				                    <td>${menu.gnmkmc }</td>
				                </tr>
			                </c:if>
		                 </c:forEach> 
	                </c:forEach> 

                </tbody>
            	</table>
            
            <hr />
            </div>
            </div>
            
            <div class="am-u-lg-1 am-u-md-12 am-u-sm-12" style="margin-top: 150px;margin-left: 50px;">
            
            	<div class="am-btn-toolbar">
		            <div class="am-btn-group am-btn-group-xs">
		            	<button type="button" class="am-btn am-btn-default" id="btn_yy"  onclick="doRight()">右移</button>
		            	<br><br><br>
		                <button type="button" class="am-btn am-btn-default" id="btn_zy" onclick="doLeft()">左移</button>
		            </div>
		        </div>
            
            </div>
            
            <div class="am-u-lg-5 am-u-md-12 am-u-sm-12">
            <div style="height:420px;overflow:auto">
                <table class="am-table am-table-striped am-table-hover table-main">
                <thead id="list_head2">
                <tr>
                    <th class="table-check"><input type="checkbox" disabled="disabled"/></th>
                    <th class="table-title">功能代码</th>
                    <th class="table-title">菜单名称</th>
                </tr>
                </thead>
                <tbody id="list_body2" >
	                <c:forEach items="${permissionList}" var="item"> 
	                	 <c:forEach items="${menuList}" var="menu"> 
		                	<c:if test="${item.gnmkdm eq menu.gnmkdm}">
				                <tr>
				                    <td><input id="yiid"  name="yiid" type="checkbox" value="${menu.gnmkdm }"></td>
				                    <td>${menu.gnmkdm }</td>
				                    <td>${menu.gnmkmc }</td>
				                </tr>
			                </c:if>
		                 </c:forEach> 
	                </c:forEach> 

                </tbody>
            	</table>
            	</div>
            </div>
        </article>
    </div>
</div>   



</form>
 <script type="text/javascript">
		$(function(){
			fillRows("10", "", "", false);//填充空行
            fillRows("10", "list_head2", "list_body2", false);//填充空行
            $("#list_body tr").bind("click",function(e){
        	 	var check = $(this).find("input[name='id']").prop("checked");
       			if(check){
       			   $(this).find("input[name='id']").removeAttr("checked");
       			}else{
       			   $(this).find("input[name='id']").prop("checked",true);
       			   
       			}
        	});
        	
        	$("#list_body tr").find("input[name='id']").bind("click",function(e){
        			var check = $(this).prop("checked");
        			if(check){
        				$(this).removeAttr("checked");
        			}else{
        			   $(this).prop("checked",true);
        			}
        	 });
        	$("#list_body2 tr").bind("click",function(e){
        	 	var check = $(this).find("input[name='yiid']").prop("checked");
       			if(check){
       			   $(this).find("input[name='yiid']").removeAttr("checked");
       			}else{
       			   $(this).find("input[name='yiid']").prop("checked",true);
       			   
       			}
        	});
        	
        	$("#list_body2 tr").find("input[name='yiid']").bind("click",function(e){
        			var check = $(this).prop("checked");
        			if(check){
        				$(this).removeAttr("checked");
        			}else{
        			   $(this).prop("checked",true);
        			}
        	 });
            
        	
            

            $("#search_go").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            

        });

		function doRight(){
       		if($("input[name='id']:checked").length==0){
       			errorAlert("请先选中未分配菜单选项！", "e");
                return false;
            };
       		$.post("<%=request.getContextPath()%>/guest/user_addMenu.html",$("#search").serialize(),function(data){
                    if(data.success){
                    	$("#search").submit();
                    }else{
                    	errorAlert(data.text);
                    }
                },"json");
       	}
    	
    	function doLeft(){
       		if($("input[name='yiid']:checked").length==0){
       				errorAlert("请先选中未分配菜单选项！", "e");
                    return false;
            };
       		$.post("<%=request.getContextPath()%>/guest/user_deleteMenu.html",$("#search").serialize(),function(data){
                    if(data.success){
                    	$("#search").submit();
                    }else{
                    	errorAlert(data.text);
                    }
                },"json");
       	}
  </script>
</body>
</html>