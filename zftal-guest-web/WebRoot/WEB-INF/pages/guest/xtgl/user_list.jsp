<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
  <%@ include file="/commons/head.ini" %>
</head>

<body id="blog-article-sidebar">


<div class="am-cf am-padding" >
    <div class="am-fl am-cf"  ><strong class="am-text-primary am-text-lg">用户列表</strong> / <small>表格</small></div>
</div>

<jsp:include page="/commons/modal.jsp" flush="true"></jsp:include>
<form class="am-form" action="<%=request.getContextPath() %>/guest/user_selectAll.html" method="post" name="search" id="search">
<div class="am-g">
    <div class="am-u-sm-12 am-u-md-6">
        <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
            	<button type="button" class="am-btn am-btn-default" id="btn_zj"><span class="am-icon-plus"></span> 新增</button>
                <%--<button type="button" class="am-btn am-btn-default"><span class="am-icon-save"></span> 保存</button>--%>
                <button type="button" class="am-btn am-btn-default" id="btn_ck"><span class="am-icon-archive"></span> 编辑</button>
                <button type="button" class="am-btn am-btn-default" id="btn_sc"><span class="am-icon-trash-o"></span> 删除</button>
                <button type="button" class="am-btn am-btn-default" id="btn_qy"><span class="am-icon-archive"></span> 启用</button>
                <button type="button" class="am-btn am-btn-default" id="btn_ty"><span class="am-icon-archive"></span> 停用</button>
                <button type="button" class="am-btn am-btn-default" id="btn_mm"><span class="am-icon-archive"></span> 密码初始化</button>
            	<button type="button" class="am-btn am-btn-default" id="btn_menu"><span class="am-icon-archive"></span> 分配菜单权限</button>
            </div>
        </div>
    </div>
    <div class="am-u-sm-12 am-u-md-3">
        <div class="am-input-group am-input-group-sm">
       		
            <input type="text" class="am-form-field" placeholder="根据姓名搜索" name="query.name" value="${query.name }">
          <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="submit" id="search_go" name="search_go">搜索</button>
          </span>
        </div>
    </div>
</div>

<div class="am-g">
    <div class="am-u-sm-12">
        
            <table class="am-table am-table-striped am-table-hover table-main">
                <thead id="list_head">
                <tr>
                    <th class="table-check"><input type="checkbox" /></th>
                    <th class="table-title">用户id</th>
                    <th class="table-title">姓名</th>
                    <th class="table-type">是否启用</th>
                    <th class="table-author">手机号码</th>
                </tr>
                </thead>
                <tbody id="list_body" >
                <c:forEach items="${userList}" var="item"> 
                <tr>
                    <td><input id="id"  name="id" type="checkbox" value="${item.userId }"></td>
                    <td>${item.userId }</td>
                    <td>${item.name }</td>
                    <td>
                    	<c:if test="${item.sfqy eq '1'}">启用</c:if>
                    	<c:if test="${item.sfqy eq '0'}">停用</c:if>
                    	<input type="hidden" value="${item.sfqy }" name="item.sfqy"/>
                    </td>
                    <td>${item.tellphone }</td>
                </tr>
                </c:forEach> 
                
                
                
                
                

                </tbody>
            </table>
            <div class="am-cf">
                <ct:page pageList="${userList }" query="${query }" />
            </div>
            <hr />
            <p>注：.....</p>
        
    </div>	
</form>
 <script type="text/javascript">
		$(function(){
			
			fillRows("10", "", "", false);//填充空行
			$("#btn_menu").bind('click',function(e){
				var userId = $("input[id='id']:checked").val();
				window.location.href = "<%=request.getContextPath() %>/guest/user_permissionList.html?userId="+userId;
			});
			$("#btn_zj").bind('click',function(e){
				setIfram("40%","50%","500","800","<%=request.getContextPath() %>/guest/user_toadd.html");
	            $('#my-modal').modal();
	        });
			
			$("#btn_sc").bind('click',function(e){
                if($("input[id='id']:checked").length == 0){
						errorAlert("请选择需要操作的记录！");
	                    return false;
                }
				var id = $("input[id='id']:checked").val();
				$.post("<%=request.getContextPath() %>/guest/user_delete.html",
		                "query.userId=" + id  ,function(data){
								if(data.success){
									window.location.href="<%=request.getContextPath() %>/guest/user_selectAll.html";
								}
		                            
	            },"json");
			});
			
			$("#btn_qy").bind('click',function(e){
				var id = $("input[id='id']:checked").val();
				$.post("<%=request.getContextPath() %>/guest/user_control.html",
		                "query.userId=" + id+"&query.sfqy=1"  ,function(data){
								if(data.success){
									window.location.href="<%=request.getContextPath() %>/guest/user_selectAll.html";
								}
		                            
	            },"json");
				
			});
			
			$("#btn_ty").bind('click',function(e){
				var id = $("input[id='id']:checked").val();
				$.post("<%=request.getContextPath() %>/guest/user_control.html",
		                "query.userId=" + id+"&query.sfqy=0"  ,function(data){
								if(data.success){
									window.location.href="<%=request.getContextPath() %>/guest/user_selectAll.html";
								}
		                            
	            },"json");
			});
			
			$("#btn_mm").bind('click',function(e){
                if($("input[id='id']:checked").length == 0){
						errorAlert("请选择需要操作的记录！");
	                    return false;
                }
				var id = $("input[id='id']:checked").val();
				$.post("<%=request.getContextPath() %>/guest/user_updatePassword.html",
		                "query.userId=" + id  ,function(data){
								if(data.success){
									window.location.href="<%=request.getContextPath() %>/guest/user_selectAll.html";
								}
		                            
	            },"json")
				
			});
			$("#btn_ck").bind('click',function(e){
                if($("input[id='id']:checked").length == 0){
						errorAlert("请选择需要操作的记录！");
	                    return false;
                }
				var id = $("input[id='id']:checked").val();
				setIfram("40%","50%","500","800","<%=request.getContextPath() %>/guest/user_tomodeify.html?query.userId="+id);
	            $('#my-modal').modal();
				
			});
			
			$("#list_body tr").bind("click",function(e){
        	 	var check = $(this).find("input[name='id']").prop("checked");
        	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
        		var sfqy = $(this).find("input[name='item.sfqy']").val();
       			if(check){
       			   $("#btn_qy").show();
       			   $("#btn_ty").show();
       			   $("#btn_sc").show();
       			}else{
       			   $(this).find("input[name='id']").prop("checked",true);
       			   if (sfqy=="1") {
       					$("#btn_qy").hide();
       					$("#btn_ty").show();
       					$("#btn_sc").hide();
       			   } else {
       					$("#btn_qy").show();
       					$("#btn_ty").hide();
       					$("#btn_sc").show();
       						
       			   }
       			   
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
        	 
       	  	$("#search_go").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
			
		});
  </script>
</body>
</html>