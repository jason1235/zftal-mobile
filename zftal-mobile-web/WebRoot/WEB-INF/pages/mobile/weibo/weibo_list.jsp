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
     redirect_isNot = '${redirect_isNot}';
     if(redirect_isNot == '1'){
     	$(".ymPrompt_close",window.parent.document).click();
        $("#search",window.parent.document).submit();
     }
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
                showweibo();
     });
     
     $("#btn_zj").click(function(){//功能条增加按钮
			    add();
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
		var status = $(this).find("#status").val();
			//alert(check);
			if(check){
			   $("#btn_qy").show();
			   $("#btn_ty").show();
			   $("#btn_sc").show();
			}else{
			   $(this).find("input[name='id']").prop("checked",true);
			   //$(this).closest("tr").attr("class","current");
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
			//$(this).closest("tbody").find("tr").removeClass("current");
			if(check){
			   
			}else{
			   $(this).prop("checked",true);
			   //$(this).closest("tr").attr("class","current");
			}
	 });
      //fillRows("20", "", "", false);//填充空行
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
           	removeData();
     });
     $("#btn_qy").click(function(){//功能条删除按钮
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
            	qiyong();
     });
     $("#btn_ty").click(function(){//功能条删除按钮
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
            	tingyong();
     });
     $("#access").click(function(){
     		var id = $(this).closest("tr").find("input[name='id']").val();
	        var url = _path+"/weibo/weibo_access.html?model.wbbh=" + id;
	        $("#myModal_toji").find(".modal-content").width(750);
	        $("#myModal_toji").find(".modal-content").height(476);
	        $("#myFrame").attr("src", url);
	        $("#myModal_toji").modal("show");
	       
     })
    });
    function tingyong(){
	       var status = $("input[name='id']:checked").closest("tr").find("input[name='status']").val();
	       if(status == 0){
		       //alert("该记录已停用！");
		       openAlert("该记录已停用！", "e");
		   	   return false;
	       }
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/weibo/weibo_tingyong.html","model.wbbh="+id,function(data){
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
	       $.post(_path+"/weibo/weibo_qiyong.html","model.wbbh="+id,function(data){
                var callback = function(){
					$("#search").submit();
				};
							
				processCall(data, callback);
            },"json");
	}
    function removeData(){
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/weibo/weibo_remove.html","model.wbbh="+id,function(data){
                var callback = function(){
					$("#search").submit();
				};
				processCall(data, callback);
            },"json");
	}
    function add(){
        var url = _path+"/weibo/weibo_toAdd.html";
        $("#myModal_toji").find(".modal-content").width(750);
        $("#myModal_toji").find(".modal-content").height(495);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
    }
   function showweibo(){
   		var id = $("input[name='id']:checked").val();
        var url = _path+"/weibo/weibo_toModify.html?query.wbbh=" + id;
        $("#myModal_toji").find(".modal-content").width(750);
        $("#myModal_toji").find(".modal-content").height(476);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
   }
    
    </script>
    </head>
  <body>
  <c:if test="${redirect_isNot eq '1' }">
  		页面正在加载，请稍候......
  </c:if>
  <c:if test="${redirect_isNot != '1' }">
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
      
      <form action="<%=request.getContextPath()%>/weibo/weibo_list.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">微博名称</label>
                        <div class="col-xs-7">
                            <input id=wbmc class="form-control" name="query.wbmc" value="${query.wbmc }"/>
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
                    <th>序号</th>
                    <th>微博名称</th>
                    <th>微博ID</th>
                    <th>微博sercret</th>
                    <th>accesstoken</th>
                    <th>使用状态</th>
                    <th>剩余时间</th>
                    <th>accesstoken是否过期</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" name="id" value="${menu.wbbh }"/></td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.wbmc }</td>
                    <td class="autocut" >${menu.client_id}</td>
                    <td class="autocut" >${menu.client_sercret}</td>
                    <td class="autocut" >${menu.accesstoken}</td>
                    
                    <td class="autocut" >
	                    <c:if test="${menu.sfqy eq '1' }">启用</c:if>
	                    <c:if test="${menu.sfqy eq '0' }">停用</c:if>
	                    <input type="hidden" id="status" name="status" value="${menu.sfqy }"/>
                    </td> 
                     <td class="autocut" >
	                    ${menu.remaintime}
                    </td>
                    <td class="autocut" >
                    <span>
                    	<c:if test="${menu.istimeout != '0' }">
                    		<font color="red">过期</font>
                    		<button type="button" class="btn btn-default" id="access">授权</button>
                    	</c:if>
                    	
                    	<c:if test="${menu.istimeout eq '0' }"><font color="blue">未过期</font></c:if>
                   	</span>
                   	</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        
        
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
  
        </c:if>
	</body>
</html>