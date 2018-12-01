<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
     $("#btn_ck").click(function(){//功能条编辑按钮
     		if($("input[name='id']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
                }	
                show();
            });
    
     $("#btn_zj").click(function(){//功能条增加按钮
			    add();
            });
            
     $("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
                }
           	removeData();
     });
     $("#btn_qy").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            	qiyong();
     });
     $("#btn_ty").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            	tingyong();
     });
            
     $("button[name='search']").click(function(e){//搜索按钮
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
     
	 
	$("#list_body tr").bind("click",function(e){
	 	var check = $(this).find("input[name='id']").prop("checked");
	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
		var status = $(this).find("#status").val();
			if(check){
			   $("#btn_qy").show();
			   $("#btn_ty").show();
			   $("#btn_sc").show();
			}else{
			   $(this).find("input[name='id']").prop("checked",true);
			   if (status=="1") {
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
	
	$("#list_body tr").find("input").bind("click",function(e){
			var check = $(this).prop("checked");
     		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			if(check){
			   
			}else{
			   $(this).prop("checked",true);
			}
	 });
				
	 
            
      //fillRows("20", "", "", false);//填充空行

    });
    
    function removeData(){
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/version/version_remove.html","query.id="+id,function(data){
                var callback = function(){
					$("#search").submit();
				};
							
				processCall(data, callback);
            },"json");
	}
	
	function tingyong(){
	       var status = $("input[name='id']:checked").closest("tr").find("input[name='status']").val();
	       if(status == 0){
		       openAlert("该记录已停用！", "e");
		   	   return false;
	       }
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/version/version_control.html","query.id="+id+"&query.checkStatus=0",function(data){
                var callback = function(){
					$("#search").submit();
				};
							
				processCall(data, callback);
            },"json");
	}
	function qiyong(){
		   var status = $("input[name='id']:checked").closest("tr").find("input[name='status']").val();
	       if(status == 1){
		       //alert("该记录已启用！");
		       openAlert("该记录已启用！", "e");
		   	   return false;
	       }
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/version/version_control.html","query.id="+id+"&query.checkStatus=1",function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
	}
	
	function show(){
        var id = $("input[name='id']:checked").val();
        var url = _path+"/version/version_modify.html?query.id=" + id;
        $("#myModal_toji").find(".modal-content").width(750);
        $("#myModal_toji").find(".modal-content").height(526);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
    }
    
    function add(){
        var url = _path+"/version/version_add.html";
        $("#myModal_toji").find(".modal-content").width(750);
        $("#myModal_toji").find(".modal-content").height(545);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
    }
    
    
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <div class="toolbox">
          <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_zj">增加</button>
              <button type="button" class="btn btn-default" id="btn_ck">修改</button>
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
              <button type="button" class="btn btn-default" id="btn_qy">启用</button>
              <button type="button" class="btn btn-default" id="btn_ty">停用</button>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath()%>/version/version_list.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">版本号</label>
                        <div class="col-xs-7">
                            <input id="xtbm" class="form-control" name="query.versionnum" value="${query.versionnum }"/>
                        </div>
                    </div>
                </div>
            </div>
          </div>
          <div class="row sl_aff_btn">
              <div class="col-xs-12">
                  <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
              </div>
          </div>
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>序号</th>
                    <th>版本号</th>
                    <th>平台</th>
                    <th>使用状态</th>
                    <th>下载</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" name="id" value="${menu.id }"/></td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.versionnum}</td>
                    <td class="autocut" >
	                    <c:if test="${menu.terrace eq '0'}">android</c:if>
	                    <c:if test="${menu.terrace eq '1'}">iphone</c:if>
	                    <c:if test="${menu.terrace eq '2'}">ipad</c:if>
                    </td>
                    <td class="autocut" >
                    	<c:if test="${menu.checkStatus eq '0'}">已停用</c:if>
	                    <c:if test="${menu.checkStatus eq '1'}">已启用</c:if>
                    <input type="hidden" name="status" value="${menu.checkStatus }" id="status"/>
                    </td>
                    <td class="autocut" ><a href="${menu.fileaddress }">下载</a></td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
	</body>
</html>