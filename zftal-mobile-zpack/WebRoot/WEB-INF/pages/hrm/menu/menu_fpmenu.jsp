<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <style type="text/css">
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
    </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    
    <script type="text/javascript">
    $(function(){
     $(".pagination").css("background","none");
     $(".pagination").css("padding","0");
     $(".pagination").css("border","0");
     $(".pagination").css("margin","0");
            
     $('.body tr').find('input').bind('click',function(e){
     	var status = $(this).prop('checked');
     	if(status){
	   		$(this).removeAttr('checked');
     	}else{
     		$(this).prop('checked',true);
     	}
	 });
				
	 $('.body tr').bind('click',function(e){
	 var status = $(this).find('input').prop('checked');
	 if (status) {
		$(this).find('input').removeAttr('checked');
	 } else {
		$(this).find('input').prop('checked',true);
	 }
	 	
	});
            
      fillRows("10", "", "", false);//填充空行
      fillRows("10", "yi_head", "yi_body", false);//填充空行

    });
    
    function selectAllOrCancel(obj,name){//全选狂操作
    		var checks = document.getElementsByName(name);
			if(obj.checked){
				for ( var i = 0; i < checks.length; i++) {
					checks[i].checked = true;
				}
			}else{
				for ( var i = 0; i < checks.length; i++) {
					checks[i].checked = false;
				}
			}
    	
   	}
   	
   	function doRight(){
   		if($("input[id='weignmkdm']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中未分配服务选项！", "e");
                    return false;
                };
   		$.post(_path+"/menu/menu_insertMenuByJsdm.html",$("#search").serialize(),function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
   	}
   	
   	function doLeft(){
   		if($("input[id='yignmkdm']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中已分配服务选项！", "e");
                    return false;
                };
   		$.post(_path+"/menu/menu_deleteMenuByJsdm.html",$("#search").serialize(),function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
   	}
    
    </script>
    </head>
  <body>
  <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    <form action="<%=request.getContextPath() %>/menu/menu_fpmenu.html" name="search" id="search" method="post">
  <div class="container sl_all_bg" style="width: 100%;">

    <div class="toolbox" style="border: 0;padding: 0;margin: 0">
        <div class="btn-group" id="but_ancd">
			<input type="hidden" value="${query.jsid }" name="query.jsid"/> 
			<input type="hidden" value="${jsglModel.jsmc }" name="jsmcNew"/>       
            <span type="button" class="btn btn-default" id="btn_zj">当前角色为“<font color="green">${jsglModel.jsmc }</font>”，为当前角色分配菜单权限</span>
        </div>
    </div>
<br>
    <!-- Modal -->
    <div class="form-horizontal sl_all_form" >

        <div class="row">



            <div class="col-xs-3">
                <div class="form-group">
                    <label for="" class="col-xs-5 control-label">菜单名称</label>
                    <div class="col-xs-7">
                        <input maxlength="20" placeholder="最多20个字符" class="form-control" name="query.gnmkmc" value="${query.gnmkmc }"/>
                    </div>
                </div>
            </div>


           

            <div class="col-xs-3">
                <div class="form-group">

                    <div class="col-xs-7">
                        <button type="submit" class="btn btn-primary btn-sm" id="search_go" name="search">查询</button>
                    </div>
                </div>
            </div>

        </div>





            <div class="col-xs-6">
                <div class="form-group">
                </div>
            </div>
            
            
        </div>


    <!-- Modal end -->

    <Div class="row">


        <div class="col-sm-5" style="float: left" id="wei">
        
            <table class="table table-bordered table-hover">

                <thead>
                <tr class="">

                    <th colspan="2" style="text-align: center">
                        <font style="font-weight:bold;">未分配菜单列表</font>
                    </th>
                </tr>

                </thead>
                <thead id="list_head">
                <tr class="active">

                    <th><input type="checkbox" onclick="selectAllOrCancel(this,'weignmkdm');"/></th>


                    <th>菜单名称</th>



                </tr>

                </thead>

                <tbody id="list_body" class="body">

				<s:iterator value="pageList" var="menu" status="st">
	                <tr>
	                    <td><input id="weignmkdm" name="weignmkdm" type="checkbox" value="${menu.gnmkdm }"/></td>
	
	                    <td>${menu.gnmkmc }</td>
	
	
	                </tr>
				</s:iterator>


                </tbody>
				<tfoot>
                <tr>


                    <td colspan="2">
                            
					<ct:page pageList="${pageList }" query="${query }" />
					
					
                    </td>
                </tr>
				</tfoot>

            </table>
				
            
        </div>
        <div class="col-sm-1" style="text-align: center;" id="middle">
            <div class="toolbox" style="margin-top: 150%;margin-left: 70%">
                <div class="btn-group" id="but_ancd" style="vertical-align: middle;">
                    <button type="button" class="btn btn-default" id="btn_zj" onclick="doRight()">右移</button>
                    <br><br><br><br>
                    <button type="button" class="btn btn-default" id="btn_save_index" onclick="doLeft()">左移</button>
                </div>
            </div>
        </div>


        <div class="col-sm-5" style="float: right;height: 480px;margin-bottom: 5px;color: #799bb5;overflow-x: hidden;overflow-y: scroll;margin-right: 15px;padding-right: 0px;border-color: #000;">
            


            <table class="table table-bordered table-hover">

				<thead>
                <tr class="">

                    <th colspan="2" style="text-align: center">
                        <font style="font-weight:bold;">已分配菜单列表</font>
                    </th>
                </tr>
				
				</thead>
				
                <thead id="yi_head">
                <tr class="active">
                    <th><input type="checkbox" onclick="selectAllOrCancel(this,'yignmkdm');"/></th>

                    <th>菜单名称</th>



                </tr>

                </thead>

                <tbody id="yi_body" class="body">
                
                <s:iterator value="jsglModelList" var="menu" status="st">
                	
	                <tr>
	                    <td><input id="yignmkdm" name="yignmkdm" type="checkbox" value="${menu.gnmkdm }"/></td>
	
	                    <td>${menu.gnmkmc }</td>
	
	
	                </tr>
				</s:iterator>

                


                </tbody>

            </table>

                
        	</div>
    	</Div>
</div>
	</form>
  
	</body>
</html>