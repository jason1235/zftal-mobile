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
                show();
            });
    
     $("#btn_zj").click(function(){//功能条增加按钮
			    add();
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
          	removeData();
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
			   $("#btn_sc").show();
			}else{
			   $(this).find("input[name='id']").prop("checked",true);
			   //$(this).closest("tr").attr("class","current");
			   if (status=="1") {
					$("#btn_sc").hide();
			   } else {
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

    });
    function show(){
        var id = $("input[name='id']:checked").val();
        var url = _path+"/qrcode/qrcode_toModify.html?query.codeid=" + id;
        $("#myModal_toji").find(".modal-content").width(750);
        $("#myModal_toji").find(".modal-content").height(595);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
    }
    
    function add(){
        var url = _path+"/qrcode/qrcode_toAdd.html";
        $("#myModal_toji").find(".modal-content").width(750);
        $("#myModal_toji").find(".modal-content").height(595);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
    }
    
    function removeData(){
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/qrcode/qrcode_remove.html","query.codeid="+id,function(data){
             var callback = function(){
					$("#search").submit();
				};
							
				processCall(data, callback);
         },"json");
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
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath()%>/qrcode/qrcode_list.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">微信名称</label>
                        <div class="col-xs-7">
                            <input id="xtbm" class="form-control" name="query.codename" value="${query.codename }"/>
                        </div>
                        
                    </div>
                </div>
                <div class="col-xs-2">
		                  <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
	              </div>
            </div>
          </div>
          <div class="row sl_aff_btn">
              
          </div>
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>序号</th>
                    <th>微信名称</th>
                    <th>微信简介</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" name="id" value="${menu.codeid }"/></td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.codename }</td>
                    <td class="autocut" >${menu.codeintroduction}</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
	</body>
</html>