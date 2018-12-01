<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
  <%@ include file="/commons/head.ini" %>
</head>

<body id="blog-article-sidebar">


<div class="am-cf am-padding" >
    <div class="am-fl am-cf"  ><strong class="am-text-primary am-text-lg">菜单列表</strong> / <small>表格</small></div>
</div>

<jsp:include page="/commons/modal.jsp" flush="true"></jsp:include>
<form class="am-form" action="<%=request.getContextPath() %>/guest/menu_page.html" method="post" name="search" id="search">

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
            </div>
        </div>
    </div>
    <div class="am-u-sm-12 am-u-md-3">
        <div class="am-input-group am-input-group-sm">
            <input type="text" placeholder="根据父级功能代码搜索" id="xtbm" class="form-control" name="query.fjgndm" value="${query.fjgndm }">
            
        </div>
    </div>
    <div class="am-u-sm-12 am-u-md-3">
        <div class="am-input-group am-input-group-sm">
       		
            <input type="text" class="am-form-field" placeholder="根据功能模块名称搜索" name="query.gnmkmc" value="${query.gnmkmc }">
            
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
                    <th class="table-check"><input type="checkbox" disabled="disabled"/></th>
                    <th class="table-title">功能代码</th>
                    <th class="table-title">菜单名称</th>
                    <th class="table-type">是否启用</th>
                    <th class="table-author">显示顺序</th>
                </tr>
                </thead>
                <tbody id="list_body" >
                <c:forEach items="${menus}" var="item"> 
                <tr>
                    <td><input id="id"  name="id" type="checkbox" value="${item.gnmkdm }"></td>
                    <td>${item.gnmkdm }</td>
                    <td>${item.gnmkmc }</td>
                    <td>
                    	<c:if test="${item.sfqy eq '1'}">启用</c:if>
                    	<c:if test="${item.sfqy eq '0'}">停用</c:if>
                    	<input type="hidden" value="${item.sfqy }" name="item.sfqy"/></td>
                    <td>${item.xssx }</td>
                </tr>
                </c:forEach> 
                
                
                
                
                

                </tbody>
            </table>
            <div class="am-cf">
                <ct:page pageList="${menus }" query="${query }" />
            </div>
            <hr />
            <p>注：.....</p>
        
    </div>

</form>
 <script type="text/javascript">
		$(function(){
			fillRows("10", "", "", false);//填充空行
			 $("#btn_ck").click(function(){//功能条增加按钮
                view();
            });
            $("#btn_zj").click(function(){//功能条增加按钮
                add();
            });
            $("#btn_sc").bind("click",function(e){
            	if($("input[name='id']:checked").length==0){
            		errorAlert("请先选中操作行");
                    return false;
                }
                var id = $("input[name='id']:checked").val();
                $.post("<%=request.getContextPath()%>/guest/menu_remove.html","model.gnmkdm="+id,function(data){
                    if(data.success){
                    	refulsh();
                    }else{
                    	errorAlert(data.text);
                    }
                },"json");
            });
            $("#btn_ty").bind("click",function(e){
            	if($("input[name='id']:checked").length==0){
            		errorAlert("请先选中操作行");
                    return false;
                }
                var id = $("input[name='id']:checked").val();
                $.post("<%=request.getContextPath()%>/guest/menu_updateSfqy.html","model.gnmkdm="+id+"&model.sfqy=0",function(data){
                    if(data.success){
                    	refulsh();
                    }
                },"json");
            });
            $("#btn_qy").bind("click",function(e){
            	if($("input[name='id']:checked").length==0){
            		errorAlert("请先选中操作行");
                    return false;
                }
                var id = $("input[name='id']:checked").val();
                $.post("<%=request.getContextPath()%>/guest/menu_updateSfqy.html","model.gnmkdm="+id+"&model.sfqy=1",function(data){
                    if(data.success){
                    	refulsh();
                    }
                },"json");
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

            
            
            
            $('#list_body tr').dblclick(function(){//行数据双击事件
            	 var id = $(this).find("input[name='id']").val();
            	if(id.length<5){
            		var url = "<%=request.getContextPath()%>/guest/menu_page.html?query.fjgndm="+id;
            		window.location.href=url;
            	}
            });
            
            
            

            $("#search_go").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
            $("select[name='model.gnmkdm']").val('${model.gnmkdm}');
            //fillRows("20", "", "", false);//填充空行

        });

        function add(){
        	var url = "<%=request.getContextPath()%>/guest/menu_toEdit.html?model.fjgndm="+$("#xtbm").val();
        	setIfram("40%","50%","500","800",url);
            $('#my-modal').modal();
        }


        
	    function view(){
	        if($("input[name='id']:checked").length==0){
	        	errorAlert("请先选中操作行");
	            return false;
	        }
	        var id = $("input[name='id']:checked").val();
	        var url = "<%=request.getContextPath()%>/guest/menu_toEdit.html?model.gnmkdm="+id;
	        setIfram("40%","50%","500","800",url);
            $('#my-modal').modal();
	    }

	    function refulsh(){
	    	$("#search").attr("action","<%=request.getContextPath()%>/guest/menu_page.html");
            $("#search").attr("method","post");
            $("#search").submit();
		};
		
  </script>
</body>
</html>